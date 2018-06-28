package com.lobinary.源码.spring.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CoreMainMethod {
	
	private static ApplicationContext ctx;

	public static void main(String[] args) {
		System.out.println("hello");
		ctx = new ClassPathXmlApplicationContext("bean.xml");
		Person p = ctx.getBean("person",Person.class);//创建bean的引用对象
        p.info();
        System.out.println(ctx.getBean("&person"));
	}

}
