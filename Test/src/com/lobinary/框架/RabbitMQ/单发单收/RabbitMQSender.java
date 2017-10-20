package com.lobinary.框架.RabbitMQ.单发单收;


import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.lobinary.框架.RabbitMQ.RabbitMQConfig;
import com.rabbitmq.client.Channel;
/**
 * 
 * <pre>
 * 	http://www.open-open.com/lib/view/open1453350095355.html
 * http://www.cnblogs.com/luxiaoxun/p/3918054.html
 * </pre>
 *
 * @ClassName: RabbitMQ简单使用
 * @author 919515134@qq.com
 * @date 2016年10月26日 上午9:45:51
 * @version V1.0.0
 */
public class RabbitMQSender {

	static String routingKey = "routingKey1.#";//绑定的id
    static String exchange = "lobinary-topic-exchange";//绑定Exchange名
    static String queue = "queue2";//绑定的queue名
    
    
	public static void main(String[] args) throws IOException, TimeoutException {
		//1.3、消息队列的使用过程
		
		//1. 客户端连接到消息队列服务器，打开一个channel。
		//2. 客户端声明一个exchange，并设置相关属性。
		//3. 客户端声明一个queue，并设置相关属性。
		//4. 客户端使用routing key，在exchange和queue之间建立好绑定关系。
		//5. 客户端投递消息到exchange。
		//6. exchange接收到消息后，就根据消息的key和已经设置的binding，进行消息路由，将消息投递到一个或多个队列里
		   
	    ConnectionFactory factory = RabbitMQConfig.getFactory();
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();
	    
	    //声明绑定关系，一次即可
//	   	 声明绑定关系(channel);
		
	    String message = "message1";
	    channel.basicPublish(exchange, routingKey, null, message.getBytes());
	    System.out.println(" 消息发送器： 发送消息 '" + message + "' 完毕");
	    
	    if(channel.isOpen())channel.close();
	    if(connection.isOpen())connection.close();
	}

	private static void 声明绑定关系(Channel channel) throws IOException {
	    channel.exchangeDelete(exchange);//如果有就删除 重新建立
		channel.exchangeDeclare(exchange, "direct", true, false, false, null);
		channel.queueDeclare(queue, false, false, false, null);
		channel.queueBind(queue, exchange, routingKey);
		System.out.println("绑定完成");
	}

}
