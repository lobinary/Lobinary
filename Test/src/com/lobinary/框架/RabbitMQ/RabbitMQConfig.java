package com.lobinary.框架.RabbitMQ;

import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQConfig {

	public final static String HOST = "127.0.0.1";

	public static ConnectionFactory getFactory() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(RabbitMQConfig.HOST);
		factory.setUsername("test");
		factory.setPassword("123qwe");
		return factory;
	}

}
