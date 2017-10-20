/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.amqp.rabbit.config;

import org.w3c.dom.Element;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;

/**
 * @author Gary Russell
 * @since 1.0.1
 *
 */
public class RabbitNamespaceUtils {

	private static final String CONNECTION_FACTORY_ATTRIBUTE = "connection-factory";

	private static final String TASK_EXECUTOR_ATTRIBUTE = "task-executor";

	private static final String ERROR_HANDLER_ATTRIBUTE = "error-handler";

	private static final String ACKNOWLEDGE_ATTRIBUTE = "acknowledge";

	private static final String ACKNOWLEDGE_AUTO = "auto";

	private static final String ACKNOWLEDGE_MANUAL = "manual";

	private static final String ACKNOWLEDGE_NONE = "none";

	private static final String TRANSACTION_MANAGER_ATTRIBUTE = "transaction-manager";

	private static final String CONCURRENCY_ATTRIBUTE = "concurrency";

	private static final String MAX_CONCURRENCY_ATTRIBUTE = "max-concurrency";

	private static final String MIN_START_INTERVAL_ATTRIBUTE = "min-start-interval";

	private static final String MIN_STOP_INTERVAL_ATTRIBUTE = "min-stop-interval";

	private static final String MIN_CONSECUTIVE_ACTIVE_ATTRIBUTE = "min-consecutive-active";

	private static final String MIN_CONSECUTIVE_IDLE_ATTRIBUTE = "min-consecutive-idle";

	private static final String PREFETCH_ATTRIBUTE = "prefetch";

	private static final String RECEIVE_TIMEOUT_ATTRIBUTE = "receive-timeout";

	private static final String CHANNEL_TRANSACTED_ATTRIBUTE = "channel-transacted";

	private static final String TRANSACTION_SIZE_ATTRIBUTE = "transaction-size";

	private static final String PHASE_ATTRIBUTE = "phase";

	private static final String AUTO_STARTUP_ATTRIBUTE = "auto-startup";

	private static final String ADVICE_CHAIN_ATTRIBUTE = "advice-chain";

	private static final String REQUEUE_REJECTED_ATTRIBUTE = "requeue-rejected";

	private static final String RECOVERY_INTERVAL = "recovery-interval";


	public static BeanDefinition parseContainer(Element containerEle, ParserContext parserContext) {
		RootBeanDefinition containerDef = new RootBeanDefinition(SimpleMessageListenerContainer.class);
		containerDef.setSource(parserContext.extractSource(containerEle));

		String connectionFactoryBeanName = "rabbitConnectionFactory";
		if (containerEle.hasAttribute(CONNECTION_FACTORY_ATTRIBUTE)) {
			connectionFactoryBeanName = containerEle.getAttribute(CONNECTION_FACTORY_ATTRIBUTE);
			if (!StringUtils.hasText(connectionFactoryBeanName)) {
				parserContext.getReaderContext().error(
						"Listener container 'connection-factory' attribute contains empty value.", containerEle);
			}
		}
		if (StringUtils.hasText(connectionFactoryBeanName)) {
			containerDef.getPropertyValues().add("connectionFactory",
					new RuntimeBeanReference(connectionFactoryBeanName));
		}

		String taskExecutorBeanName = containerEle.getAttribute(TASK_EXECUTOR_ATTRIBUTE);
		if (StringUtils.hasText(taskExecutorBeanName)) {
			containerDef.getPropertyValues().add("taskExecutor", new RuntimeBeanReference(taskExecutorBeanName));
		}

		String errorHandlerBeanName = containerEle.getAttribute(ERROR_HANDLER_ATTRIBUTE);
		if (StringUtils.hasText(errorHandlerBeanName)) {
			containerDef.getPropertyValues().add("errorHandler", new RuntimeBeanReference(errorHandlerBeanName));
		}

		AcknowledgeMode acknowledgeMode = parseAcknowledgeMode(containerEle, parserContext);
		if (acknowledgeMode != null) {
			containerDef.getPropertyValues().add("acknowledgeMode", acknowledgeMode);
		}

		String transactionManagerBeanName = containerEle.getAttribute(TRANSACTION_MANAGER_ATTRIBUTE);
		if (StringUtils.hasText(transactionManagerBeanName)) {
			containerDef.getPropertyValues().add("transactionManager",
					new RuntimeBeanReference(transactionManagerBeanName));
		}

		String concurrency = containerEle.getAttribute(CONCURRENCY_ATTRIBUTE);
		if (StringUtils.hasText(concurrency)) {
			containerDef.getPropertyValues().add("concurrentConsumers", new TypedStringValue(concurrency));
		}

		String maxConcurrency = containerEle.getAttribute(MAX_CONCURRENCY_ATTRIBUTE);
		if (StringUtils.hasText(maxConcurrency)) {
			containerDef.getPropertyValues().add("maxConcurrentConsumers", new TypedStringValue(maxConcurrency));
		}

		String minStartInterval = containerEle.getAttribute(MIN_START_INTERVAL_ATTRIBUTE);
		if (StringUtils.hasText(minStartInterval)) {
			containerDef.getPropertyValues().add("startConsumerMinInterval", new TypedStringValue(minStartInterval));
		}

		String minStopInterval = containerEle.getAttribute(MIN_STOP_INTERVAL_ATTRIBUTE);
		if (StringUtils.hasText(minStopInterval)) {
			containerDef.getPropertyValues().add("stopConsumerMinInterval", new TypedStringValue(minStopInterval));
		}

		String minConsecutiveMessages = containerEle.getAttribute(MIN_CONSECUTIVE_ACTIVE_ATTRIBUTE);
		if (StringUtils.hasText(minConsecutiveMessages)) {
			containerDef.getPropertyValues().add("consecutiveActiveTrigger", new TypedStringValue(minConsecutiveMessages));
		}

		String minConsecutiveIdle = containerEle.getAttribute(MIN_CONSECUTIVE_IDLE_ATTRIBUTE);
		if (StringUtils.hasText(minConsecutiveIdle)) {
			containerDef.getPropertyValues().add("consecutiveIdleTrigger", new TypedStringValue(minConsecutiveIdle));
		}

		String prefetch = containerEle.getAttribute(PREFETCH_ATTRIBUTE);
		if (StringUtils.hasText(prefetch)) {
			containerDef.getPropertyValues().add("prefetchCount", new TypedStringValue(prefetch));
		}

		String receiveTimeout = containerEle.getAttribute(RECEIVE_TIMEOUT_ATTRIBUTE);
		if (StringUtils.hasText(receiveTimeout)) {
			containerDef.getPropertyValues().add("receiveTimeout", new TypedStringValue(receiveTimeout));
		}

		String channelTransacted = containerEle.getAttribute(CHANNEL_TRANSACTED_ATTRIBUTE);
		if (StringUtils.hasText(channelTransacted)) {
			// Note: a placeholder will pass this test, but if it resolves to true, it will be caught during container initialization
			if (acknowledgeMode.isAutoAck() && channelTransacted.equalsIgnoreCase("true")) {
				parserContext.getReaderContext().error(
						"Listener Container - cannot set channel-transacted with acknowledge='NONE'", containerEle);
			}
			containerDef.getPropertyValues().add("channelTransacted", new TypedStringValue(channelTransacted));
		}

		String transactionSize = containerEle.getAttribute(TRANSACTION_SIZE_ATTRIBUTE);
		if (StringUtils.hasText(transactionSize)) {
			containerDef.getPropertyValues().add("txSize", new TypedStringValue(transactionSize));
		}

		String requeueRejected = containerEle.getAttribute(REQUEUE_REJECTED_ATTRIBUTE);
		if (StringUtils.hasText(requeueRejected)) {
			containerDef.getPropertyValues().add("defaultRequeueRejected", new TypedStringValue(requeueRejected));
		}

		String phase = containerEle.getAttribute(PHASE_ATTRIBUTE);
		if (StringUtils.hasText(phase)) {
			containerDef.getPropertyValues().add("phase", phase);
		}

		String autoStartup = containerEle.getAttribute(AUTO_STARTUP_ATTRIBUTE);
		if (StringUtils.hasText(autoStartup)) {
			containerDef.getPropertyValues().add("autoStartup", new TypedStringValue(autoStartup));
		}

		String adviceChain = containerEle.getAttribute(ADVICE_CHAIN_ATTRIBUTE);
		if (StringUtils.hasText(adviceChain)) {
			containerDef.getPropertyValues().add("adviceChain", new RuntimeBeanReference(adviceChain));
		}

		String recoveryInterval = containerEle.getAttribute(RECOVERY_INTERVAL);
		if (StringUtils.hasText(recoveryInterval)) {
			containerDef.getPropertyValues().add("recoveryInterval", new TypedStringValue(recoveryInterval));
		}

		return containerDef;
	}

	private static AcknowledgeMode parseAcknowledgeMode(Element ele, ParserContext parserContext) {
		AcknowledgeMode acknowledgeMode = null;
		String acknowledge = ele.getAttribute(ACKNOWLEDGE_ATTRIBUTE);
		if (StringUtils.hasText(acknowledge)) {
			if (ACKNOWLEDGE_AUTO.equals(acknowledge)) {
				acknowledgeMode = AcknowledgeMode.AUTO;
			} else if (ACKNOWLEDGE_MANUAL.equals(acknowledge)) {
				acknowledgeMode = AcknowledgeMode.MANUAL;
			} else if (ACKNOWLEDGE_NONE.equals(acknowledge)) {
				acknowledgeMode = AcknowledgeMode.NONE;
			} else {
				parserContext.getReaderContext().error(
						"Invalid listener container 'acknowledge' setting [" + acknowledge
								+ "]: only \"auto\", \"manual\", and \"none\" supported.", ele);
			}
			return acknowledgeMode;
		} else {
			return null;
		}
	}

}
