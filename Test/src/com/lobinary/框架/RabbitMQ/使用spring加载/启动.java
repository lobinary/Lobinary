package com.lobinary.框架.RabbitMQ.使用spring加载;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lobinary.java.多线程.TU;

public class 启动 {
	
	public static void main(String[] args) {
//        sendMessageBySpringConfig();
        sendAndReceiveMessageBySpringCode();
	}

    private static void sendMessageBySpringConfig() {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:framework/RabbitMQ/spring-application.xml");
		MessageProducer messageProducer = beanFactory.getBean("messageProducer", MessageProducer.class);
		for (int i = 0; i < 10; i++) {
			messageProducer.sendMessage("what?"+i);
		}
		TU.s(1000);
		System.exit(1);
    }
	
	/**
	 * 通过spring手法消息的流程
	 * 
	 * @since add by bin.lv 2017年8月14日 下午5:52:04
	 */
	public static void sendAndReceiveMessageBySpringCode(){
	    ConnectionFactory connectionFactory = new CachingConnectionFactory("localhost",5672);//配置rabbitmq的地址与端口
	    AmqpAdmin admin = new RabbitAdmin(connectionFactory);//通过factory，初始化amqpAdmin，用来动态配置queue或exchange
	    admin.declareQueue(new Queue("myqueue"));//声明一个queue
	    AmqpTemplate template = new RabbitTemplate(connectionFactory);//生成template
	    template.convertAndSend("myqueue", "fooMessage");//发送message：foo，到queue：myqueue
	    String foo = (String) template.receiveAndConvert("myqueue");//接受message从myqueue
	    System.out.println(foo);
	}

}
