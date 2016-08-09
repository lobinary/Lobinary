package com.lobinary.java.多线程.concurrent.ReadWriteLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.lobinary.java.多线程.TU;

/**
 * 
 * <pre>
 * 读写锁用法
 * 
 * 	读写锁的有点就在于 多线程可以同时持有读锁，但是只能同时有一个在执行写锁，
 * 当有读锁时，想写，必须等到读锁完成才能执行写操作
 * </pre>
 *
 * @ClassName: ReadWriteLock用法
 * @author 919515134@qq.com
 * @date 2016年8月4日 上午10:32:36
 * @version V1.0.0
 */
public class ReadWriteLock用法 {
	
	/**
	 * 我们的写操作2s，读操作1s延迟
	 * 所以一旦一起跳出5条读，就代表可以多线程同时读
	 * 可以看到写的时候不能读，读的时候，可以有很多同时读，但是不能有写
	 * 
	 * 一般而言，读写锁是用来提升并发程序性能的锁分离技术的成果。Java中的ReadWriteLock是Java 5 中新增的一个接口，
	 * 一个ReadWriteLock维护一对关联的锁，一个用于只读操作一个用于写。在没有写线程的情况下一个读锁可能会同时被多个读线程持有。
	 * 写锁是独占的，你可以使用JDK中的ReentrantReadWriteLock来实现这个规则，它最多支持65535个写锁和65535个读锁
	 * 
	 * 运行结果如下：
	  
	  	main准备写值：hello1
		main准备写值：hello2
		pool-1-thread-2准备读取值
		pool-1-thread-1准备读取值
		pool-1-thread-4准备读取值
		pool-1-thread-3准备读取值
		pool-1-thread-5准备读取值
		pool-1-thread-2读取到数据hello2
		pool-1-thread-3读取到数据hello2
		pool-1-thread-4读取到数据hello2
		pool-1-thread-1读取到数据hello2
		main准备写值：hello1
		pool-1-thread-5读取到数据hello2
		main准备写值：hello2
		
	 */
	public static void main(String[] args) {
		读写锁对象 读写锁对象 = new 读写锁对象();
		Thread.currentThread().setName("ReadWriteLock用法主线程");// 给你的线程起个有意义的名字。
		//这样可以方便找bug或追踪。OrderProcessor, QuoteProcessor or TradeProcessor 
		//这种名字比 Thread-1. Thread-2 and Thread-3 好多了，给线程起一个和它要完成的任务相关的名字，所有的主要框架甚至JDK都遵循这个最佳实践。

		读写锁对象.setS("hello1");
		读写锁对象.setS("hello2");
		TU.start(ReadWriteLock用法线程.class, 5, 读写锁对象);
		读写锁对象.setS("hello1");
		读写锁对象.setS("hello2");
	}

	public static class ReadWriteLock用法线程 implements Runnable {

		读写锁对象 读写锁对象;
		static int i;

		public ReadWriteLock用法线程(读写锁对象 读写锁对象) {
			super();
			this.读写锁对象 = 读写锁对象;
		}

		@Override
		public void run() {
			Thread.currentThread().setName("ReadWriteLock用法线程"+i++);
			TU.l("读取到数据" + 读写锁对象.getS());
		}

	}

	public static class 读写锁对象 {

		private String s;
		private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
		private Lock readLock = readWriteLock.readLock();
		private Lock writeLock = readWriteLock.writeLock();

		public String getS() {
			readLock.lock();
			System.out.println(Thread.currentThread().getName() + "准备读取值");
			TU.s(1000);
			readLock.unlock();
			return s;
		}

		public void setS(String s) {
			writeLock.lock();
			System.out.println(Thread.currentThread().getName() + "准备写值：" + s);
			this.s = s;
			TU.s(2000);
			writeLock.unlock();
		}

	}

}
