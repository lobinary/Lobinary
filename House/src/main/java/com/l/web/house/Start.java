package com.l.web.house;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.l.web.house.service.catchsystem.impl.LinkedHouseImpl;
import com.lobinary.工具类.LU;
import com.mysql.cj.jdbc.Driver;

public class Start {
	
	public static void main(String[] args) throws Exception {
		LU.changeLogFile("/app/logs/house/house.log");
//		LU.l("欢迎访问房屋捕获系统1");
		boolean changed = LU.changeSystemOut2Log();
		System.out.println(changed);
//		LU.l("欢迎访问房屋捕获系统2");
		@SuppressWarnings("resource")
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/spring-application.xml");
		LinkedHouseImpl 链家房屋信息捕获 = (LinkedHouseImpl) ctx.getBean(LinkedHouseImpl.class);
		链家房屋信息捕获.捕获房屋信息();
//		System.out.println("欢迎访问房屋捕获系统3");
//		LU.l("欢迎访问房屋捕获系统4");
		
	}

}
