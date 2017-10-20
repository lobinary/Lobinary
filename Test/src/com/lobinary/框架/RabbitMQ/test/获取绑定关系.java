package com.lobinary.框架.RabbitMQ.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lobinary.工具类.JU;

public class 获取绑定关系 {

    public static void main(String[] argv) throws Exception {
        ApplicationContext beanFactory = new ClassPathXmlApplicationContext("classpath:framework/RabbitMQ/spring-application.xml");
//        CachingConnectionFactory cf = beanFactory.getBean("connectionFactory", CachingConnectionFactory.class);
//        System.out.println(JU.toJSONString(cf));
//        System.out.println("===========================================");
//        RabbitTemplate rt = beanFactory.getBean("rabbitMQTemplate", RabbitTemplate.class);
//        System.out.println(rt);
//        System.out.println("===========================================");
//        rt.convertAndSend("hello");
//        System.out.println("===========================================");
//        System.out.println(JU.toJSONString(rt));
//        Binding bean = (Binding) beanFactory.getBean("org.springframework.amqp.rabbit.config.BindingFactoryBean#0");
//        System.out.println(JU.toJSONString(bean));
//        System.out.println("===========================================");

        Map<String, Binding> map = beanFactory.getBeansOfType(Binding.class);
        
        for(String key:map.keySet()){
            System.out.println(key + " : " + map.get(key));
        }

        System.out.println("========================================");
        Map<String, SimpleMessageListenerContainer> maps = beanFactory.getBeansOfType(SimpleMessageListenerContainer.class);
        
        for(String key:maps.keySet()){
            System.out.println(key + " : " + JU.toJSONString(maps.get(key)));
            SimpleMessageListenerContainer s = maps.get(key);
            MessageListenerAdapter ml = (MessageListenerAdapter) s.getMessageListener();
            Field field = ml.getClass().getDeclaredField("delegate");
            field.setAccessible(true);  
            Object delegate = field.get(ml);  
            System.out.println(delegate);                            //print:just for test  
            System.out.println(JU.toJSONString(delegate));
        }
        
        System.out.println("end");
        

        Map<String, SimpleMessageListenerContainer> listeners = beanFactory.getBeansOfType(SimpleMessageListenerContainer.class);
        for (SimpleMessageListenerContainer listener : listeners.values()) {
            MessageListenerAdapter ml = (MessageListenerAdapter) listener.getMessageListener();
            Field field = ml.getClass().getDeclaredField("delegate");
            field.setAccessible(true);  
            Object delegate = field.get(ml);  
            try {
//                Method method = delegate.getClass().getMethod("onMessage", Message.class,Channel.class);
                Method m = ChannelAwareMessageListener.class.getMethods()[0];
                Method method = delegate.getClass().getMethod(m.getName(), m.getParameterTypes());
                
                System.out.println(delegate);                            //print:just for test  
                System.out.println(JU.toJSONString(delegate));
                System.out.println(delegate.getClass());
                System.out.println(method);
            } catch (Exception e) {
                System.out.println("不匹配的监听器："+delegate);
            }
        }
    }
}

