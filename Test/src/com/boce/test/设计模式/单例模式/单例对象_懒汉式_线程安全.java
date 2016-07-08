package com.boce.test.设计模式.单例模式;

/**
 * 	是否 Lazy 初始化：是
	是否多线程安全：是
	实现难度：易
	描述：这种方式具备很好的 lazy loading，能够在多线程中很好的工作，但是，效率很低，99% 情况下不需要同步。
	优点：第一次调用才初始化，避免内存浪费。
	缺点：必须加锁 synchronized 才能保证单例，但加锁会影响效率。
	getInstance() 的性能对应用程序不是很关键（该方法使用不太频繁）。
 */
public class 单例对象_懒汉式_线程安全 {
	
	private static 单例对象_懒汉式_线程安全 全局对象;

	private 单例对象_懒汉式_线程安全(){
		super();
	}
	
	/**
	 * 方法中需要使用同步锁 synchronized 防止多线程同时进入造成 instance 被多次实例化。
	 */
	public static synchronized 单例对象_懒汉式_线程安全 获取对象(){
		if(全局对象==null){
			全局对象 = new 单例对象_懒汉式_线程安全();
		}
		return 全局对象;
	}
	
	public void 输出(){
		System.out.println("我是单例对象的普通方法");
	}
	
}
