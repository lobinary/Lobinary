package com.l.web.house.service.catchsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.l.web.house.service.catchsystem.impl.链家房屋信息捕获;

public class 本地捕获系统 {

	private final static Logger logger = LoggerFactory.getLogger(本地捕获系统.class);

	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/spring-application.xml");
		链家房屋信息捕获 链家房屋信息捕获 = (链家房屋信息捕获) ctx.getBean(链家房屋信息捕获.class);
		链家房屋信息捕获.捕获房屋信息();
	}
	
	public void test(String... a){
		for (String s : a) {
			System.out.println(s);
		}
	}
	
}
