package com.lobinary.书籍.thinking_in_java.隐藏实施过程;

import org.springframework.context.ApplicationContext;

/**
 * 
  *
  *	想通过同路径下引用lib下代码，看是否需要import
  *@see java.util.logging.同路径类
 */
public class 同路径包引用其他lib下代码 {
	
	public static void main(String[] args) {
		System.out.println(ApplicationContext.class);//此时引用需要import org.springframework.context.ApplicationContext;
		//但是请注意，java不允许报名以java开头，比如  package java.同路径包引用其他lib下代码  那么此时会报错 java.lang.SecurityException:Prohibited package name: java.util.logging
	}

}
