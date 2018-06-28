package com.lobinary.框架.RabbitMQ.test;

import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

public class TestConsume  implements ChannelAwareMessageListener{

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println("hello:"+message);
    }

}
