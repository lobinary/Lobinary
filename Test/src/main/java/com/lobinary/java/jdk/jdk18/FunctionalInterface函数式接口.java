package com.lobinary.java.jdk.jdk18;

public class FunctionalInterface函数式接口{
	
	public static void main(String[] args) {
		run(()->System.out.println("hello"));
	}
	
	public static void run(MyFunctionalInterface fi){
		fi.包含一个唯一抽象方法();
	}
	
}

@FunctionalInterface
interface MyFunctionalInterface {

	/**
	 * 函数式接口必须包含一个唯一的抽象方法，不可以多个，只允许一个
	 * 
	 * 其他常见的函数接口包括：
		java.lang.Runnable
		java.util.concurrent.Callable
		java.awt.event.ActionListener
		java.util.Comparator
	 */
	public abstract void 包含一个唯一抽象方法();
	
	//可以尝试打开注释看提示
//	public abstract void 加入有第二个方法();
}
