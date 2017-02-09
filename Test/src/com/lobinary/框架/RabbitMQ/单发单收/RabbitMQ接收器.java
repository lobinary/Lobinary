package com.lobinary.框架.RabbitMQ.单发单收;
import com.lobinary.框架.RabbitMQ.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class RabbitMQ接收器 {
    
    public static void main(String[] argv) throws Exception {

    ConnectionFactory factory = RabbitMQConfig.getFactory();
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare("queueName", false, false, false, null);
    System.out.println(" 接收器：正在等待消息中......");

    String exchange名称 = "com.l.test.rabbitmq.exchangename";
    QueueingConsumer consumer = new QueueingConsumer(channel);
    channel.basicConsume("queueName", true, consumer);
    
    while (true) {
      QueueingConsumer.Delivery delivery = consumer.nextDelivery();
      String message = new String(delivery.getBody());
      System.out.println(" 接收器：接收到消息[ " + message + "]");
    }
  }
}