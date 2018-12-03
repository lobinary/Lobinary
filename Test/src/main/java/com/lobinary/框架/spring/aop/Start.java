package com.lobinary.框架.spring.aop;

import com.lobinary.框架.spring.aop.service.模拟业务类接口;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Start {

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("framework/spring/aop/spring-context.xml");
        模拟业务类接口 service = context.getBean(模拟业务类接口.class);
        String s = service.F1("rc");
        System.out.println("方法执行结束，返回："+s);
    }

}
