/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package org.springframework.amqp.rabbit.listener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.amqp.AmqpAuthenticationException;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactoryUtils;
import org.springframework.amqp.rabbit.connection.RabbitResourceHolder;
import org.springframework.amqp.rabbit.connection.RabbitUtils;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;
import org.springframework.amqp.rabbit.support.RabbitExceptionTranslator;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.utility.Utility;

/**
 * Specialized consumer encapsulating knowledge of the broker
 * connections and having its own lifecycle (start and stop).
 *
 * @author Mark Pollack
 * @author Dave Syer
 * @author Gary Russell
 * @author Casper Mout
 *
 */
public class BlockingQueueConsumer {

	private static Log logger = LogFactory.getLog(BlockingQueueConsumer.class);

	private final BlockingQueue<Delivery> queue;

	// When this is non-null the connection has been closed (should never happen in normal operation).
	private volatile ShutdownSignalException shutdown;

	private final String[] queues;

	private final int prefetchCount;

	private final boolean transactional;

	private Channel channel;

	private RabbitResourceHolder resourceHolder;

	private InternalConsumer consumer;

	private final AtomicBoolean cancelled = new AtomicBoolean(false);

	private volatile long shutdownTimeout;

	private final AtomicBoolean cancelReceived = new AtomicBoolean(false);

	private final AcknowledgeMode acknowledgeMode;

	private final ConnectionFactory connectionFactory;

	private final MessagePropertiesConverter messagePropertiesConverter;

	private final ActiveObjectCounter<BlockingQueueConsumer> activeObjectCounter;

	private final Map<String, Object> consumerArgs = new HashMap<String, Object>();

	private final boolean exclusive;

	private final Set<Long> deliveryTags = new LinkedHashSet<Long>();

	private final boolean defaultRequeuRejected;

	private final CountDownLatch suspendClientThread = new CountDownLatch(1);

	private final Collection<String> consumerTags = Collections.synchronizedSet(new HashSet<String>());

	private final Set<String> missingQueues = Collections.synchronizedSet(new HashSet<String>());

	private final long retryDeclarationInterval = 60000;

	private long lastRetryDeclaration;

	/**
	 * Create a consumer. The consumer must not attempt to use
	 * the connection factory or communicate with the broker
	 * until it is started. RequeueRejected defaults to true.
	 * @param connectionFactory The connection factory.
	 * @param messagePropertiesConverter The properties converter.
	 * @param activeObjectCounter The active object counter; used during shutdown.
	 * @param acknowledgeMode The acknowledgemode.
	 * @param transactional Whether the channel is transactional.
	 * @param prefetchCount The prefetch count.
	 * @param queues The queues.
	 */
	public BlockingQueueConsumer(ConnectionFactory connectionFactory,
			MessagePropertiesConverter messagePropertiesConverter,
			ActiveObjectCounter<BlockingQueueConsumer> activeObjectCounter, AcknowledgeMode acknowledgeMode,
			boolean transactional, int prefetchCount, String... queues) {
		this(connectionFactory, messagePropertiesConverter, activeObjectCounter,
				acknowledgeMode, transactional, prefetchCount, true, queues);
	}

	/**
	 * Create a consumer. The consumer must not attempt to use
	 * the connection factory or communicate with the broker
	 * until it is started.
	 * @param connectionFactory The connection factory.
	 * @param messagePropertiesConverter The properties converter.
	 * @param activeObjectCounter The active object counter; used during shutdown.
	 * @param acknowledgeMode The acknowledge mode.
	 * @param transactional Whether the channel is transactional.
	 * @param prefetchCount The prefetch count.
	 * @param defaultRequeueRejected true to reject requeued messages.
	 * @param queues The queues.
	 */
	public BlockingQueueConsumer(ConnectionFactory connectionFactory,
			MessagePropertiesConverter messagePropertiesConverter,
			ActiveObjectCounter<BlockingQueueConsumer> activeObjectCounter, AcknowledgeMode acknowledgeMode,
			boolean transactional, int prefetchCount, boolean defaultRequeueRejected, String... queues) {
		this(connectionFactory, messagePropertiesConverter, activeObjectCounter, acknowledgeMode, transactional,
				prefetchCount, defaultRequeueRejected, null, queues);
	}

	/**
	 * Create a consumer. The consumer must not attempt to use the
	 * connection factory or communicate with the broker
	 * until it is started.
	 * @param connectionFactory The connection factory.
	 * @param messagePropertiesConverter The properties converter.
	 * @param activeObjectCounter The active object counter; used during shutdown.
	 * @param acknowledgeMode The acknowledge mode.
	 * @param transactional Whether the channel is transactional.
	 * @param prefetchCount The prefetch count.
	 * @param defaultRequeueRejected true to reject requeued messages.
	 * @param consumerArgs The consumer arguments (e.g. x-priority).
	 * @param queues The queues.
	 */
	public BlockingQueueConsumer(ConnectionFactory connectionFactory,
			MessagePropertiesConverter messagePropertiesConverter,
			ActiveObjectCounter<BlockingQueueConsumer> activeObjectCounter, AcknowledgeMode acknowledgeMode,
			boolean transactional, int prefetchCount, boolean defaultRequeueRejected,
			Map<String, Object> consumerArgs, String... queues) {
		this(connectionFactory, messagePropertiesConverter, activeObjectCounter, acknowledgeMode, transactional,
				prefetchCount, defaultRequeueRejected, consumerArgs, false, queues);
	}

	/**
	 * Create a consumer. The consumer must not attempt to use
	 * the connection factory or communicate with the broker
	 * until it is started.
	 * @param connectionFactory The connection factory.
	 * @param messagePropertiesConverter The properties converter.
	 * @param activeObjectCounter The active object counter; used during shutdown.
	 * @param acknowledgeMode The acknowledge mode.
	 * @param transactional Whether the channel is transactional.
	 * @param prefetchCount The prefetch count.
	 * @param defaultRequeueRejected true to reject requeued messages.
	 * @param consumerArgs The consumer arguments (e.g. x-priority).
	 * @param exclusive true if the consumer is to be exclusive.
	 * @param queues The queues.
	 */
	public BlockingQueueConsumer(ConnectionFactory connectionFactory,
			MessagePropertiesConverter messagePropertiesConverter,
			ActiveObjectCounter<BlockingQueueConsumer> activeObjectCounter, AcknowledgeMode acknowledgeMode,
			boolean transactional, int prefetchCount, boolean defaultRequeueRejected,
			Map<String, Object> consumerArgs, boolean exclusive, String... queues) {
		this.connectionFactory = connectionFactory;
		this.messagePropertiesConverter = messagePropertiesConverter;
		this.activeObjectCounter = activeObjectCounter;
		this.acknowledgeMode = acknowledgeMode;
		this.transactional = transactional;
		this.prefetchCount = prefetchCount;
		this.defaultRequeuRejected = defaultRequeueRejected;
		if (consumerArgs != null && consumerArgs.size() > 0) {
			this.consumerArgs.putAll(consumerArgs);
		}
		this.exclusive = exclusive;
		this.queues = queues;
		this.queue = new LinkedBlockingQueue<Delivery>(prefetchCount);
	}

	public Channel getChannel() {
		return channel;
	}

	public String getConsumerTag() {
		return consumer.getConsumerTag();
	}

	/**
	 * Stop receiving new messages; drain the queue of any prefetched messages.
	 * @param shutdownTimeout how long (ms) to suspend the client thread.
	 */
	public final void setQuiesce(long shutdownTimeout) {
		this.shutdownTimeout = shutdownTimeout;
		this.cancelled.set(true);
		if (logger.isDebugEnabled()) {
			logger.debug("Quiescing consumer: " + this);
		}
	}

	/**
	 * Check if we are in shutdown mode and if so throw an exception.
	 */
	private void checkShutdown() {
		if (shutdown != null) {
			throw Utility.fixStackTrace(shutdown);
		}
	}

	/**
	 * If this is a non-POISON non-null delivery simply return it.
	 * If this is POISON we are in shutdown mode, throw
	 * shutdown. If delivery is null, we may be in shutdown mode. Check and see.
	 * @throws InterruptedException
	 */
	private Message handle(Delivery delivery) throws InterruptedException {
		if ((delivery == null && shutdown != null)) {
			throw shutdown;
		}
		if (delivery == null) {
			return null;
		}
		byte[] body = delivery.getBody();
		Envelope envelope = delivery.getEnvelope();

		MessageProperties messageProperties = this.messagePropertiesConverter.toMessageProperties(
				delivery.getProperties(), envelope, "UTF-8");
		messageProperties.setMessageCount(0);
		Message message = new Message(body, messageProperties);
		if (logger.isDebugEnabled()) {
			logger.debug("Received message: " + message);
		}
		deliveryTags.add(messageProperties.getDeliveryTag());
		return message;
	}

	/**
	 * Main application-side API: wait for the next message delivery and return it.
	 * @return the next message
	 * @throws InterruptedException if an interrupt is received while waiting
	 * @throws ShutdownSignalException if the connection is shut down while waiting
	 */
	public Message nextMessage() throws InterruptedException, ShutdownSignalException {
		logger.trace("Retrieving delivery for " + this);
		return handle(queue.take());
	}

	/**
	 * Main application-side API: wait for the next message delivery and return it.
	 * @param timeout timeout in millisecond
	 * @return the next message or null if timed out
	 * @throws InterruptedException if an interrupt is received while waiting
	 * @throws ShutdownSignalException if the connection is shut down while waiting
	 */
	public Message nextMessage(long timeout) throws InterruptedException, ShutdownSignalException {
		if (logger.isDebugEnabled()) {
			logger.debug("Retrieving delivery for " + this);
		}
		checkShutdown();
		if (this.missingQueues.size() > 0) {
			checkMissingQueues();
		}
		Message message = handle(queue.poll(timeout, TimeUnit.MILLISECONDS));
		if (message == null && cancelReceived.get()) {
			throw new ConsumerCancelledException();
		}
		return message;
	}

	/*
	 * Check to see if missing queues are now available; use a separate channel so the main
	 * channel is not closed by the broker if the declaration fails.
	 */
	private void checkMissingQueues() {
		long now = System.currentTimeMillis();
		if (now - this.retryDeclarationInterval > this.lastRetryDeclaration) {
			synchronized(this.missingQueues) {
				Iterator<String> iterator = this.missingQueues.iterator();
				while (iterator.hasNext()) {
					boolean available = true;
					String queue = iterator.next();
					Channel channel = null;
					try {
						channel = this.connectionFactory.createConnection().createChannel(false);
						channel.queueDeclarePassive(queue);
						if (logger.isInfoEnabled()) {
							logger.info("Queue '" + queue + "' is now available");
						}
					}
					catch (IOException e) {
						available = false;
						if (logger.isWarnEnabled()) {
							logger.warn("Queue '" + queue + "' is still not available");
						}
					}
					finally {
						if (channel != null) {
							try {
								channel.close();
							}
							catch (IOException e) {
								//Ignore it
							} catch (TimeoutException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
						}
					}
					if (available) {
						try {
							this.consumeFromQueue(queue);
							iterator.remove();
						}
						catch (IOException e) {
							throw RabbitExceptionTranslator.convertRabbitAccessException(e);
						}
					}
				}
			}
			this.lastRetryDeclaration = now;
		}
	}

	public void start() throws AmqpException {
		if (logger.isDebugEnabled()) {
			logger.debug("Starting consumer " + this);
		}
		try {
			this.resourceHolder = ConnectionFactoryUtils.getTransactionalResourceHolder(connectionFactory, transactional);
			this.channel = resourceHolder.getChannel();
		}
		catch (AmqpAuthenticationException e) {
			throw new FatalListenerStartupException("Authentication failure", e);
		}
		this.consumer = new InternalConsumer(channel);
		this.deliveryTags.clear();
		this.activeObjectCounter.add(this);

		// mirrored queue might be being moved
		int passiveDeclareTries = 3;
		do {
			try {
				attemptPassiveDeclarations();
				if (passiveDeclareTries < 3 && logger.isInfoEnabled()) {
					logger.info("Queue declaration succeeded after retrying");
				}
				passiveDeclareTries = 0;
			}
			catch (DeclarationException e) {
				if (passiveDeclareTries > 0 && channel.isOpen()) {
					if (logger.isWarnEnabled()) {
						logger.warn("Queue declaration failed; retries left=" + (passiveDeclareTries-1), e);
						try {
							Thread.sleep(5000);
						}
						catch (InterruptedException e1) {
							Thread.currentThread().interrupt();
						}
					}
				}
				else if (e.getFailedQueues().size() < this.queues.length) {
					if (logger.isWarnEnabled()) {
						logger.warn("Not all queues are available; only listening on those that are - configured: "
								+ Arrays.asList(this.queues) + "; not available: " + e.getFailedQueues());
					}
					this.missingQueues.addAll(e.getFailedQueues());
					this.lastRetryDeclaration = System.currentTimeMillis();
				}
				else {
					this.activeObjectCounter.release(this);
					throw new QueuesNotAvailableException("Cannot prepare queue for listener. "
							+ "Either the queue doesn't exist or the broker will not allow us to use it.", e);
				}
			}
		}
		while (passiveDeclareTries-- > 0);

		if (!acknowledgeMode.isAutoAck()) {
			// Set basicQos before calling basicConsume (otherwise if we are not acking the broker
			// will send blocks of 100 messages)
			try {
				channel.basicQos(prefetchCount);
			}
			catch (IOException e) {
				this.activeObjectCounter.release(this);
				throw new FatalListenerStartupException("Cannot set basicQos.", e);
			}
		}


		try {
			for (String queueName : queues) {
				if (!this.missingQueues.contains(queueName)) {
					consumeFromQueue(queueName);
				}
			}
		}
		catch (IOException e) {
			throw RabbitExceptionTranslator.convertRabbitAccessException(e);
		}
	}

	private void consumeFromQueue(String queue) throws IOException {
		this.channel.basicConsume(queue, this.acknowledgeMode.isAutoAck(), "", false, this.exclusive,
				this.consumerArgs, this.consumer);
		if (logger.isDebugEnabled()) {
			logger.debug("Started on queue '" + queue + "': " + this);
		}
	}

	private void attemptPassiveDeclarations() {
		DeclarationException failures = null;
		for (String queueName : this.queues) {
			try {
				this.channel.queueDeclarePassive(queueName);
			}
			catch (IOException e) {
				if (logger.isWarnEnabled()) {
					logger.warn("Failed to declare queue:" + queueName);
				}
				if (failures == null) {
					failures = new DeclarationException();
				}
				failures.addFailedQueue(queueName);
			}
		}
		if (failures != null) {
			throw failures;
		}
	}

	public void stop() {
		this.cancelled.set(true);
		this.suspendClientThread.countDown();
		if (consumer != null && consumer.getChannel() != null && this.consumerTags.size() > 0
				&& !this.cancelReceived.get()) {
			try {
				RabbitUtils.closeMessageConsumer(this.consumer.getChannel(), this.consumerTags, this.transactional);
			}
			catch (Exception e) {
				if (logger.isDebugEnabled()) {
					logger.debug("Error closing consumer", e);
				}
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Closing Rabbit Channel: " + channel);
		}
		RabbitUtils.setPhysicalCloseRequired(true);
		ConnectionFactoryUtils.releaseResources(this.resourceHolder);
		deliveryTags.clear();
		consumer = null;
	}

	private class InternalConsumer extends DefaultConsumer {

		public InternalConsumer(Channel channel) {
			super(channel);
		}

		@Override
		public void handleConsumeOk(String consumerTag) {
			super.handleConsumeOk(consumerTag);
			synchronized(BlockingQueueConsumer.this.consumerTags) {
				BlockingQueueConsumer.this.consumerTags.add(consumerTag);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("ConsumeOK : " + BlockingQueueConsumer.this);
			}
		}

		@Override
		public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
			if (logger.isDebugEnabled()) {
				if (RabbitUtils.isNormalShutdown(sig)) {
					logger.debug("Received shutdown signal for consumer tag=" + consumerTag + ": " + sig.getMessage());
				}
				else {
					logger.debug("Received shutdown signal for consumer tag=" + consumerTag, sig);
				}
			}
			shutdown = sig;
			// The delivery tags will be invalid if the channel shuts down
			deliveryTags.clear();
			activeObjectCounter.release(BlockingQueueConsumer.this);
		}

		@Override
		public void handleCancel(String consumerTag) throws IOException {
			if (logger.isWarnEnabled()) {
				logger.warn("Cancel received for " + consumerTag + "; " + BlockingQueueConsumer.this);
			}
			synchronized (BlockingQueueConsumer.this.consumerTags) {
				BlockingQueueConsumer.this.consumerTags.remove(consumerTag);
			}
			BlockingQueueConsumer.this.cancelReceived.set(true);
		}

		@Override
		public void handleCancelOk(String consumerTag) {
			if (logger.isDebugEnabled()) {
				logger.debug("Received cancellation notice for tag " + consumerTag + "; " + BlockingQueueConsumer.this);
			}
			synchronized(BlockingQueueConsumer.this.consumerTags) {
				BlockingQueueConsumer.this.consumerTags.remove(consumerTag);
				if (BlockingQueueConsumer.this.consumerTags.isEmpty()) {
					// Signal to the container that we have been cancelled
					activeObjectCounter.release(BlockingQueueConsumer.this);
					if (logger.isDebugEnabled()) {
						logger.debug("Terminating; active consumers now : " + activeObjectCounter.getCount()
								+ " consumer: " + BlockingQueueConsumer.this);
					}
				}
			}
		}

		@Override
		public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
				throws IOException {
			if (cancelled.get()) {
				try {
					BlockingQueueConsumer.this.suspendClientThread.await(
							BlockingQueueConsumer.this.shutdownTimeout, TimeUnit.MILLISECONDS);
					// AcknowlwdgeMode.NONE message will be lost
					return;
				}
				catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					throw new IOException(e);
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Storing delivery for " + BlockingQueueConsumer.this);
			}
			try {
				queue.put(new Delivery(envelope, properties, body));
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

	}

	/**
	 * Encapsulates an arbitrary message - simple "bean" holder structure.
	 */
	private static class Delivery {

		private final Envelope envelope;
		private final AMQP.BasicProperties properties;
		private final byte[] body;

		public Delivery(Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
			this.envelope = envelope;
			this.properties = properties;
			this.body = body;
		}

		public Envelope getEnvelope() {
			return envelope;
		}

		public BasicProperties getProperties() {
			return properties;
		}

		public byte[] getBody() {
			return body;
		}
	}

	@SuppressWarnings("serial")
	public class DeclarationException extends AmqpException {

		public DeclarationException() {
			super("Failed to declare queue(s):");
		}

		private final List<String> failedQueues = new ArrayList<String>();

		void addFailedQueue(String queue) {
			this.failedQueues.add(queue);
		}

		public List<String> getFailedQueues() {
			return this.failedQueues;
		}

		@Override
		public String getMessage() {
			return super.getMessage() + this.failedQueues.toString();
		}

	}

	@Override
	public String toString() {
		return "Consumer: tags=[" + (this.consumerTags.toString()) + "], channel=" + channel
				+ ", acknowledgeMode=" + acknowledgeMode + " local queue size=" + queue.size();
	}

	/**
	 * Perform a rollback, handling rollback exceptions properly.
	 * @param ex the thrown application exception or error
	 * @throws Exception in case of a rollback error
	 */
	public void rollbackOnExceptionIfNecessary(Throwable ex) throws Exception {

		boolean ackRequired = !acknowledgeMode.isAutoAck() && !acknowledgeMode.isManual();
		try {
			if (transactional) {
				if (logger.isDebugEnabled()) {
					logger.debug("Initiating transaction rollback on application exception: " + ex);
				}
				RabbitUtils.rollbackIfNecessary(channel);
			}
			if (ackRequired) {
				// We should always requeue if the container was stopping
				boolean shouldRequeue = this.defaultRequeuRejected ||
						ex instanceof MessageRejectedWhileStoppingException;
				Throwable t = ex;
				while (shouldRequeue && t != null) {
					if (t instanceof AmqpRejectAndDontRequeueException) {
						shouldRequeue = false;
					}
					t = t.getCause();
				}
				if (logger.isDebugEnabled()) {
					logger.debug("Rejecting messages (requeue=" + shouldRequeue + ")");
				}
				for (Long deliveryTag : deliveryTags) {
					// With newer RabbitMQ brokers could use basicNack here...
					channel.basicReject(deliveryTag, shouldRequeue);
				}
				if (transactional) {
					// Need to commit the reject (=nack)
					RabbitUtils.commitIfNecessary(channel);
				}
			}
		} catch (Exception e) {
			logger.error("Application exception overridden by rollback exception", ex);
			throw e;
		} finally {
			deliveryTags.clear();
		}
	}

	/**
	 * Perform a commit or message acknowledgement, as appropriate.
	 * @param locallyTransacted Whether the channel is locally transacted.
	 * @throws IOException Any IOException.
	 * @return true if at least one delivery tag exists.
	 */
	public boolean commitIfNecessary(boolean locallyTransacted) throws IOException {

		if (deliveryTags.isEmpty()) {
			return false;
		}

		try {

			boolean ackRequired = !acknowledgeMode.isAutoAck() && !acknowledgeMode.isManual();

			if (ackRequired) {

				if (transactional && !locallyTransacted) {

					// Not locally transacted but it is transacted so it
					// could be synchronized with an external transaction
					for (Long deliveryTag : deliveryTags) {
						ConnectionFactoryUtils.registerDeliveryTag(connectionFactory, channel, deliveryTag);
					}

				} else {
					long deliveryTag = new ArrayList<Long>(deliveryTags).get(deliveryTags.size() - 1);
					channel.basicAck(deliveryTag, true);
				}
			}

			if (locallyTransacted) {
				// For manual acks we still need to commit
				RabbitUtils.commitIfNecessary(channel);
			}

		} finally {
			deliveryTags.clear();
		}

		return true;

	}

}
