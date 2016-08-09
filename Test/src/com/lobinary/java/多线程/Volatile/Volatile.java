package com.lobinary.java.多线程.Volatile;

import java.util.concurrent.atomic.AtomicInteger;

public class Volatile {
	
	/**
		一旦一个共享变量（类的成员变量、类的静态成员变量）被volatile修饰之后，那么就具备了两层语义：
		　　1）保证了不同线程对这个变量进行操作时的可见性，即一个线程修改了某个变量的值，这新值对其他线程来说是立即可见的。
		　　2）禁止进行指令重排序。
		
		在java 1.5的java.util.concurrent.atomic包下提供了一些原子操作类，
		即对基本数据类型的 自增（加1操作），自减（减1操作）、以及加法操作（加一个数），
		减法操作（减一个数）进行了封装，保证这些操作是原子性操作。
		atomic是利用CAS来实现原子性操作的（Compare And Swap），
		CAS实际上是利用处理器提供的CMPXCHG指令实现的，而处理器执行CMPXCHG指令是一个原子性操作。
	 */
	public volatile static AtomicInteger count = new AtomicInteger(0);
	 
    public static void inc() {
 
        //这里延迟1毫秒，使得结果明显
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
        }
 
        count.getAndIncrement();
    }
 
    /**
     * 在 java 垃圾回收整理一文中，描述了jvm运行时刻内存的分配。其中有一个内存区域是jvm虚拟机栈，每一个线程运行时都有一个线程栈，
		线程栈保存了线程运行时候变量值信息。当线程访问某一个对象时候值的时候，首先通过对象的引用找到对应在堆内存的变量的值，然后把堆内存
		变量的具体值load到线程本地内存中，建立一个变量副本，之后线程就不再和对象在堆内存变量值有任何关系，而是直接修改副本变量的值，
		在修改完之后的某一个时刻（线程退出之前），自动把线程变量副本的值回写到对象在堆中变量。这样在堆中的对象的值就产生变化了。
		
     */
    public static void main(String[] args) throws InterruptedException {
 
        //同时启动1000个线程，去进行i++计算，看看实际结果
 
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                	Volatile.inc();
                }
            }).start();
        }
 
        //这里每次运行的值都有可能不同,可能为1000
        Thread.sleep(1000);
        System.out.println("运行结果:Counter.count=" + Volatile.count);
    }
    
}
