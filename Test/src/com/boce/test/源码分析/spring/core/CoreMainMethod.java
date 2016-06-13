package com.boce.test.源码分析.spring.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CoreMainMethod {
	
	private static ApplicationContext ctx;

	public static void main(String[] args) {
		ctx = new ClassPathXmlApplicationContext("bean.xml");
		Person p = ctx.getBean("person",Person.class);//创建bean的引用对象
        p.info();
	}

}
