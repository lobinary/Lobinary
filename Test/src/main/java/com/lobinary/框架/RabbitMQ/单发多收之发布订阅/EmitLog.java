package com.lobinary.框架.RabbitMQ.单发多收之发布订阅;

import org.springframework.amqp.core.ExchangeTypes;

import com.lobinary.java.多线程.TU;
import com.lobinary.框架.RabbitMQ.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 
 * <pre>
 * 	使用场景：发布、订阅模式，发送端发送广播消息，多个接收端接收。
 * 
 * 发送消息到一个名为“logs”的exchange上，使用“fanout”方式发送，即广播消息，不需要使用queue，发送端不需要关心谁接收。
 * </pre>
 *
 * @ClassName: EmitLog
 * @author 919515134@qq.com
 * @date 2016年10月26日 下午3:50:31
 * @version V1.0.0
 */
public class EmitLog {

	private static final String EXCHANGE_NAME = "logs";

	public static void main(String[] argv) throws Exception {

		ConnectionFactory factory = RabbitMQConfig.getFactory();
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.FANOUT);

		for (int i = 0; i < 5; i++) {
			String message = "广播消息" + i;
			channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
			TU.l(" [x] 发送广播消息：'" + message + "'");
		}

		channel.close();
		connection.close();
	}

}