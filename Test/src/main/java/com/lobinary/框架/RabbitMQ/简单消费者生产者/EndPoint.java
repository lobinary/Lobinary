package com.lobinary.框架.RabbitMQ.简单消费者生产者;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.lobinary.框架.RabbitMQ.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
 
/**
 * 
 * 功能概要： EndPoint类型的队列
 * 
 * @author linbingwen
 * @since  2016年1月11日
 */
public abstract class EndPoint{
     
    protected Channel channel;
    protected Connection connection;
    protected String endPointName;
     
    public EndPoint(String endpointName) throws IOException, TimeoutException{
         this.endPointName = endpointName;
         
         //Create a connection factory
         ConnectionFactory factory = RabbitMQConfig.getFactory();
         
         //getting a connection
         connection = factory.newConnection();
         
         //creating a channel
         channel = connection.createChannel();
         
         //declaring a queue for this channel. If queue does not exist,
         //it will be created on the server.
         channel.queueDeclare(endpointName, false, false, false, null);
    }
     
     
    /**
     * 关闭channel和connection。并非必须，因为隐含是自动调用的。
     * @throws IOException
     * @throws TimeoutException 
     */
     public void close() throws IOException, TimeoutException{
         this.channel.close();
         this.connection.close();
     }
}
