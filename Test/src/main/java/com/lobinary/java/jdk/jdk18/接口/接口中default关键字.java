package com.lobinary.java.jdk.jdk18.接口;

public class 接口中default关键字{
	
	public static void main(String[] args) {
		new 实现类().run();
	}
	
}

interface 接口 {

	default void run(){
		System.out.println("接口的default方法");
	}
	
}

class 实现类 implements 接口{
	
}
