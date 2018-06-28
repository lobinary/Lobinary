package com.lobinary.java.多线程.线程间协作;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class 生产者消费者Semaphore {


	/**
	 *  Semaphore 就是一个锁一样，acquire获取锁，然后release释放锁
	 */
	public static void main(String[] args) {
		System.out.println("hello");

		Semaphore 生产者标识 = new Semaphore(1);
		Semaphore 消费者标识 = new Semaphore(0);
		List<Long> list = new ArrayList<Long>();
		for (int i = 0; i <50; i++) {
			new 生产者1(消费者标识,生产者标识,list).start();
			new 消费者1(消费者标识,生产者标识,list).start();
		}
	}

	

}

class 生产者1 extends Thread {

	private Semaphore 消费者标识;
	private Semaphore 生产者标识;
	private List<Long> list;
	static long i = 0;
	
	public 生产者1(Semaphore 消费者标识, Semaphore 生产者标识, List<Long> list) {
		super();
		this.消费者标识 = 消费者标识;
		this.生产者标识 = 生产者标识;
		this.list = list;
	}

	@Override
	public void run() {
		super.run();
		while (true) {
			try {
				Thread.sleep(2000);
				生产者标识.acquire();
				list.add(i);
				i++;
				System.out.println(Thread.currentThread().getName() + "生产者插入数据:" + i);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally{
				消费者标识.release();
			}
		}
	}

}

class 消费者1 extends Thread {


	private Semaphore 消费者标识;
	private Semaphore 生产者标识;
	private List<Long> list;

	public 消费者1(Semaphore 消费者标识, Semaphore 生产者标识, List<Long> list) {
		super();
		this.消费者标识 = 消费者标识;
		this.生产者标识 = 生产者标识;
		this.list = list;
	}
	
	@Override
	public void run() {
		super.run();
		while (true) {
			try {
				消费者标识.acquire();
				Long poll = list.remove(0);
				System.out.println(Thread.currentThread().getName() + "消费者取出值：" + poll);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				生产者标识.release();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
