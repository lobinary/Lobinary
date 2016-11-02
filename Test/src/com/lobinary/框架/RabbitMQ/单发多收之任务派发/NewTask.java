package com.lobinary.框架.RabbitMQ.单发多收之任务派发;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.lobinary.框架.RabbitMQ.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

/**
 * 
 * <pre>
 * 	单发送多接收（这个不是广播似的，而是比如派发5个任务 给2台机器，1台处理2个，1台处理3个）
  
		使用场景：一个发送端，多个接收端，如分布式的任务派发。为了保证消息发送的可靠性，不丢失消息，使消息持久化了。同时为了防止接收端在处理消息时down掉，只有在消息处理完成后才发送ack消息。
  	
  	发送端和[单发单收]不同点：
		1、使用“task_queue”声明了另一个Queue，因为RabbitMQ不容许声明2个相同名称、配置不同的Queue
		2、使"task_queue"的Queue的durable的属性为true，即使消息队列durable(持久性)
		3、使用MessageProperties.PERSISTENT_TEXT_PLAIN使消息durable(持久性)
		
		When RabbitMQ quits or crashes it will forget the queues and messages unless you tell it not to. 
		Two things are required to make sure that messages aren't lost: we need to mark both the queue and messages as durable.
 * </pre>
 *
 * @ClassName: NewTask
 * @author 919515134@qq.com
 * @date 2016年10月26日 下午2:19:01
 * @version V1.0.0
 */
public class NewTask {

	private static final String TASK_QUEUE_NAME = "task_queue";

	public static void main(String[] argv) throws Exception {

		ConnectionFactory factory = RabbitMQConfig.getFactory();
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);//第二个参数为true代表持久化消息队列queue


		for (int i = 0; i <5; i++) {
			String message = "task"+i;
			channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());// MessageProperties.PERSISTENT_TEXT_PLAIN 持久化这个消息
			System.out.println(" [x] Sent '" + message + "'");
		}
		
		channel.close();
		connection.close();
	}
	
}