package com.lobinary.框架.RabbitMQ.简单消费者生产者;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.SerializationUtils;
 
 
/**
 * 
 * 功能概要：消息生产者
 * 
 * @author linbingwen
 * @since  2016年1月11日
 */
public class Producer extends EndPoint{
     
    public Producer(String endPointName) throws IOException, TimeoutException{
        super(endPointName);
    }
 
    public void sendMessage(Serializable object) throws IOException {
        channel.basicPublish("",endPointName, null, SerializationUtils.serialize(object));
    }  
}