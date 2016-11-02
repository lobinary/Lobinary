package com.lobinary.框架.RabbitMQ.使用spring加载;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lobinary.java.多线程.TU;

public class 启动 {
	
	public static void main(String[] args) {
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:framework/RabbitMQ/spring-application.xml");
		MessageProducer messageProducer = beanFactory.getBean("messageProducer", MessageProducer.class);
		for (int i = 0; i < 10; i++) {
			messageProducer.sendMessage("what?"+i);
		}
		TU.s(1000);
		System.exit(1);
	}

}
