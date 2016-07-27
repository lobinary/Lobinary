package com.lobinary.设计模式.单例模式;

/**
 * 
 * 
	1、懒汉式，线程不安全
	是否 Lazy 初始化：是
	是否多线程安全：否
	实现难度：易
	描述：这种方式是最基本的实现方式，这种实现最大的问题就是不支持多线程。因为没有加锁 synchronized，所以严格意义上它并不算单例模式。
	这种方式 lazy loading 很明显，不要求线程安全，在多线程不能正常工作。
 */
public class 单例对象_懒汉式_线程不安全 {
	
	private static 单例对象_懒汉式_线程不安全 全局对象;

	private 单例对象_懒汉式_线程不安全(){
		super();
	}
	
	public static 单例对象_懒汉式_线程不安全 获取对象(){
		if(全局对象==null){
			全局对象 = new 单例对象_懒汉式_线程不安全();
		}
		return 全局对象;
	}
	
	public void 输出(){
		System.out.println("我是单例对象的普通方法");
	}
	
}
