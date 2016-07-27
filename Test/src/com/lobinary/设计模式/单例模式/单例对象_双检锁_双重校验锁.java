package com.lobinary.设计模式.单例模式;

/**
 * 	DK 版本：JDK1.5 起
	是否 Lazy 初始化：是
	是否多线程安全：是
	实现难度：较复杂
	描述：这种方式采用双锁机制，安全且在多线程情况下能保持高性能。
	getInstance() 的性能对应用程序很关键。
 */
public class 单例对象_双检锁_双重校验锁 {
	
	private static 单例对象_双检锁_双重校验锁 全局对象;

	private 单例对象_双检锁_双重校验锁(){
		super();
	}
	
	/**
	 * 方法中需要使用同步锁 synchronized 防止多线程同时进入造成 instance 被多次实例化。
	 */
	public static 单例对象_双检锁_双重校验锁 获取对象(){
		if(全局对象==null){
			synchronized (单例对象_双检锁_双重校验锁.class){
				if(全局对象==null){
					全局对象 = new 单例对象_双检锁_双重校验锁();
				}
			}
		}
		return 全局对象;
	}
	
	public void 输出(){
		System.out.println("我是单例对象的普通方法");
	}
	
}
