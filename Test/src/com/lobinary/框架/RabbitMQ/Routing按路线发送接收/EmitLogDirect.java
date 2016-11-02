package com.lobinary.框架.RabbitMQ.Routing按路线发送接收;
import org.springframework.amqp.core.ExchangeTypes;

import com.lobinary.java.多线程.TU;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 
 * <pre>
 * 	发送端和场景3的区别：
		1、exchange的type为ExchangeTypes.DIRECT
		2、发送消息的时候加入了routing key
 * </pre>
 *
 * @ClassName: EmitLogDirect
 * @author 919515134@qq.com
 * @date 2016年10月26日 下午4:53:40
 * @version V1.0.0
 */
public class EmitLogDirect {

  private static final String EXCHANGE_NAME = "direct_logs";

  public static void main(String[] argv) throws Exception {

    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    //exchange的type为ExchangeTypes.DIRECT
    channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.DIRECT);

    for (int i = 0; i < 5; i++) {
        String severity = "自定义的routeKey"+i;
        String message = "消息内容,该消息来自"+severity;
        //发送消息的时候加入了routing key
        channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());
        TU.l(" [x] Sent '" + severity + "':'" + message + "'");

	}
    channel.close();
    connection.close();
  }
  
}