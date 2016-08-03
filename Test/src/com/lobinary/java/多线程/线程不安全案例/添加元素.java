package com.lobinary.java.多线程.线程不安全案例;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 
 * 线程不安全：
 * 		
 * 		LinkedList
 * 		ArrayList 
 * 		HashMap
 * 		StringBuilder
 * 
 * 线程安全：
 * 		
 * 		Vector
 *  	HashTable
 *  	StringBuffer
 * 
 * 
 */
public class 添加元素 {
	
	/**
	 * 线程不安全的list在操作的时候很容易出现一些并发错误，包括在拓展长度的时候继续添加造成的ArrayIndexOutOfBoundsException等
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		ConcurrentLinkedQueue<Integer> cl = new ConcurrentLinkedQueue<Integer>();
		List<Integer> list = new ArrayList<Integer>();
		int j = 100;
		for (int i = 0; i < j; i++) {
			new 多线程(list,cl).start();
		}
		Thread.sleep(1000);
		System.out.println("理论长度为："+j*1000+" , 线程不安全的ArrayList最终长度:"+list.size()+" , 线程安全的ConcurrentLinkedQueue最终长度:"+cl.size());
		Thread.sleep(1000);
		System.out.println("理论长度为："+j*1000+" , 线程不安全的ArrayList最终长度:"+list.size()+" , 线程安全的ConcurrentLinkedQueue最终长度:"+cl.size());
	}

}


class 多线程 extends Thread{

	List<Integer> list = null;
	ConcurrentLinkedQueue<Integer> cl = null;

	public 多线程(List<Integer> list, ConcurrentLinkedQueue<Integer> cl) {
		super();
		this.list = list;
		this.cl = cl;
	}

	@Override
	public void run() {
		super.run();
		for (int i = 0; i < 1000; i++) {
			cl.add(i);
		}
		for (int i = 0; i < 1000; i++) {
//			synchronized (list) {//加上锁就会安全
				list.add(i);
//			}
		}
	}
	
}