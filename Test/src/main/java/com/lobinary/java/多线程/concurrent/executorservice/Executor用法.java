package com.lobinary.java.多线程.concurrent.executorservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Executor用法 {
	private final static ExecutorService threadPool = Executors.newFixedThreadPool(2);//newSingleThreadExecutor();
	
	public static void main(String[] args) {
		for (int i = 0; i <100; i++) {
			/*	两个方法都可以向线程池提交任务，
				 *	execute()方法的返回类型是void，它定义在Executor接口中, 
				 *	submit()方法可以返回持有计算结果的Future对象，它定义在ExecutorService接口中，它扩展了Executor接口，
				 *	其它线程池类像ThreadPoolExecutor和ScheduledThreadPoolExecutor都有这些方法。
			*/	
			threadPool.execute(new Executor用法_线程());
			Future<?> future = threadPool.submit(new Executor用法_线程());
		}
		System.out.println("线程添加完毕");
//		threadPool.shutdown();
	}

}


class Executor用法_线程 implements Runnable{

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName()+"hello"+System.currentTimeMillis());
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}