package com.lobinary.框架.RabbitMQ.单发多收之发布订阅;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

import org.springframework.amqp.core.ExchangeTypes;

import com.lobinary.java.多线程.TU;
import com.lobinary.框架.RabbitMQ.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

/**
 * 
 * <pre>
 * 接收端：
    1、声明名为“logs”的exchange的，方式为"fanout"，和发送端一样。
    2、channel.queueDeclare().getQueue();该语句得到一个随机名称的Queue，该queue的类型为non-durable、exclusive、auto-delete的，将该queue绑定到上面的exchange上接收消息。
    3、注意binding queue的时候，channel.queueBind()的第三个参数Routing key为空，即所有的消息都接收。如果这个值不为空，在exchange type为“fanout”方式下该值被忽略！
 * </pre>
 *
 * @ClassName: ReceiveLogs
 * @author 919515134@qq.com
 * @date 2016年10月26日 下午3:53:25
 * @version V1.0.0
 */
public class ReceiveLogs {

	private static final String EXCHANGE_NAME = "logs";

	public static void main(String[] argv) throws Exception {

		ConnectionFactory factory = RabbitMQConfig.getFactory();
		factory.setHost(RabbitMQConfig.HOST);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		// 声明名为“logs”的exchange的，方式为"fanout"，和发送端一样。
		channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.FANOUT);

		/*
		 * channel.queueDeclare().getQueue();该语句得到一个随机名称的Queue，
		 * 该queue的类型为non-durable、exclusive、auto-delete的，
		 * 将该queue绑定到上面的exchange上接收消息。
		 */
		String queueName = channel.queueDeclare().getQueue();

		// 注意binding queue的时候，channel.queueBind()的第三个参数Routing key为空，即所有的消息都接收。
		// 如果这个值不为空，在exchange type为“fanout”方式下该值被忽略！(也就是在funout下，填和不填都一个效果)
		channel.queueBind(queueName, EXCHANGE_NAME, "");

		TU.l(" [*] Waiting for messages. To exit press CTRL+C");

		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, true, consumer);

		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			TU.l(" [x] Received '" + message + "'");
		}
	}
}