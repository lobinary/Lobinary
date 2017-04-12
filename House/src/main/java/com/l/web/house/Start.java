package com.l.web.house;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.l.web.house.service.catchsystem.impl.LinkedHouseImpl;

public class Start {
	
	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/spring-application.xml");
		LinkedHouseImpl 链家房屋信息捕获 = (LinkedHouseImpl) ctx.getBean(LinkedHouseImpl.class);
		链家房屋信息捕获.捕获房屋信息();
	}

}
