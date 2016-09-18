package com.lobinary.java.多线程.concurrent.executorservice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.lobinary.java.多线程.TU;
import com.lobinary.算法.排序.SortUtil;

public class 线程池联合用法 {
	//池大小、缓冲池大小、线程被submit后等待时间、等待时间单位、线程池容器
	private final static ExecutorService refundQueryThreadPool = new ThreadPoolExecutor(1,1, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(20));;//20 全服务器只允许有20个线程同时访问退款中心查询退款记录
    CyclicBarrier barrier = new CyclicBarrier(2);
    
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		RefundQueryThread task = new RefundQueryThread();
		Future<List<String>> result = refundQueryThreadPool.submit(task);
		Future<List<String>> result2 = refundQueryThreadPool.submit(task);
		System.out.println("==========================");
//		SortUtil.out(result.get());
		System.out.println("==========================");
		TU.outTime();
		SortUtil.out(result2.get());
		System.out.println("==========================");
//		refundQueryThreadPool.shutdown();
		

		RefundQueryThread task2 = new RefundQueryThread();
		Future<List<String>> result3 = refundQueryThreadPool.submit(task2);
		Future<List<String>> result4 = refundQueryThreadPool.submit(task2);
		System.out.println("==========================");
//		SortUtil.out(result.get());
		System.out.println("==========================");
		SortUtil.out(result4.get());
		System.out.println("==========================");
	}

}

class RefundQueryThread implements Callable<List<String>>{

	@Override
	public List<String> call() throws Exception {
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < Math.random()*10; i++) {
			result.add(Thread.currentThread().getName()+":"+i);
		}
		TU.s(10000);
		return result;
	}
	
}
