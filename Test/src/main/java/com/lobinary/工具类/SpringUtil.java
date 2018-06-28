package com.lobinary.工具类;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringUtil {

	private static ApplicationContext ctx;
	private static boolean isInit = false;
	
	public static <T> T getBean(Class<T> clazz){
		if(!isInit){
			ctx = new ClassPathXmlApplicationContext("bean.xml");
			isInit = true;
		}
		return ctx.getBean(clazz);
	}

}
