package com.lobinary.java.多线程.concurrent.executorservice;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutor用法 {

	public static void main(String[] args) {
														//池大小、缓冲池大小、线程被submit后等待时间、等待时间单位、线程池容器
		ExecutorService threadPool = new ThreadPoolExecutor(2, 5, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10));
		for (int i = 0; i <100; i++) {
			threadPool.submit(new ThreadPoolExecutor用法_线程());
		}
		System.out.println("线程添加完毕");
	}
	
}

class ThreadPoolExecutor用法_线程 implements Runnable{

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName()+"hello");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
