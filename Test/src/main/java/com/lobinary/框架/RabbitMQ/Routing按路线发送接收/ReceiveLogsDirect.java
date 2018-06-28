package com.lobinary.框架.RabbitMQ.Routing按路线发送接收;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

import org.springframework.amqp.core.ExchangeTypes;

import com.lobinary.java.多线程.TU;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

/**
 * 
 * <pre>
 * 	接收端和场景3的区别：
		在绑定queue和exchange的时候使用了routing key，即从该exchange上只接收routing key指定的消息。
 * </pre>
 *
 * @ClassName: ReceiveLogsDirect
 * @author 919515134@qq.com
 * @date 2016年10月26日 下午5:06:39
 * @version V1.0.0
 */
public class ReceiveLogsDirect {

  private static final String EXCHANGE_NAME = "direct_logs";

  public static void main(String[] argv) throws Exception {

    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.DIRECT);
    String queueName = channel.queueDeclare().getQueue();
    
    String[] routKey = {"自定义的routeKey2","自定义的routeKey3"};
    
    for(String severity : routKey){    
      channel.queueBind(queueName, EXCHANGE_NAME, severity);
      TU.l("绑定"+severity+"成功");
    }
    
    TU.l(" [*] Waiting for messages. To exit press CTRL+C");

    QueueingConsumer consumer = new QueueingConsumer(channel);
    channel.basicConsume(queueName, true, consumer);

    while (true) {
      QueueingConsumer.Delivery delivery = consumer.nextDelivery();
      String message = new String(delivery.getBody());
      String routingKey = delivery.getEnvelope().getRoutingKey();

      System.out.println(" [x] Received '" + routingKey + "':'" + message + "'");   
    }
  }
}