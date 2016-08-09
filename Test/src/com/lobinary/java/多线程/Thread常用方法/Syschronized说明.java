package com.lobinary.java.多线程.Thread常用方法;

public class Syschronized说明 {
	
	public static void main(String[] args) {
		String s = "a";
		/**
		 * 	synchronized保证的是synchronized块级别的互斥性和可见性。
			块级别的互斥性：当有一个线程获得synchronized的锁之后，其他线程不能进入这个块，而只能等获得锁的线程执行完毕之后，在进入这个块。
			块级别的可见性：在多线程环境下，当一个线程进入synchronized块后，其修改的变量值在其他线程当中能够看到这个值。
			基于以上以上两个特性，synchronized关键字能够保证多线程安全，这是真正意义上线程安全。
		 */
		synchronized (Syschronized说明.class) {
			System.out.println(s);
			s = "b";
			System.out.println(s);
		}
		System.out.println(s);
	}

}
