package com.lobinary.java.多线程.concurrent;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 引用
	An iterator is considered fail-fast  if it throws a ConcurrentModificationException under either of the following two conditions: 
	   1. In multithreaded processing: if one thread is trying to modify a Collection while another thread is iterating over it. 
	   2. In single-threaded or in multithreaded processing: if after the creation of the Iterator, 
	   the container is modified at any time by any method other than the Iterator's own remove or add methods.
	
	   
	来自ibm developerworks上对java.util.concurrent包的说明片段： 
	      java.util 包中的集合类都返回 fail-fast 迭代器，这意味着它们假设线程在集合内容中进行迭代时，集合不会更改它的内容。如果 fail-fast 迭代器检测到在迭代过程中进行了更改操作，那么它会抛出 ConcurrentModificationException ，这是不可控异常。 
	      在迭代过程中不更改集合的要求通常会对许多并发应用程序造成不便。相反，比较好的是它允许并发修改并确保迭代器只要进行合理操作，就可以提供集合的一致视图，如 java.util.concurrent 集合类中的迭代器所做的那样。 
	     java.util.concurrent 集合返回的迭代器称为弱一致的（weakly consistent）迭代器。对于这些类，如果元素自从迭代开始已经删除，且尚未由 next() 方法返回，那么它将不返回到调用者。如果元素自迭代开始已经添加，那么它可能返回调用者，也可能不返回。在一次迭代中，无论如何更改底层集合，元素不会被返回两次。 
 */
public class ConcurrentHashMap用法 {
	
	/**
	 * HashMap线程不安全  ConcurrentHashMap线程安全
	 * 
	 * HashMap在多线程执行put操作的时候，容易出现死循环在HashMap.class 的484行，可以通过debug看执行线程，偶尔会有几个线程停下来，点击工具栏里的“||”暂停按钮，就可以跳转到循环位置
	 */
	public static void main(String[] args) throws InterruptedException {
		System.out.println("ready");
		ConcurrentHashMap<String,String> cMap = new ConcurrentHashMap<String,String>(100);//代表并发度是100，有100个线程同时访问
		HashMap<String,String> map = new HashMap<String,String>();
		for (int i = 0; i < 100; i++) {
			new ConcurrentHashMap线程(cMap).start();
			new HashMap线程(map).start();
		}
		/**
		 * 运行结果显示 CurrentHashMap具有线程安全性
		  	2000
			1933
		 */
		
		Thread.sleep(10000);
		System.out.println(cMap.size());
		System.out.println(map.size());		
		Thread.sleep(10000);
		System.out.println(cMap.size());
		System.out.println(map.size());		
		Thread.sleep(10000);
		System.out.println(cMap.size());
		System.out.println(map.size());
		System.out.println("准备强制退出");
		System.exit(0);
		System.out.println("强制退出完毕");
	}
	
}

class ConcurrentHashMap线程 extends Thread{
	
	ConcurrentHashMap<String,String> cMap;
	
	public ConcurrentHashMap线程(ConcurrentHashMap<String, String> cMap) {
		super();
		this.cMap = cMap;
	}

	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			
			try {
				cMap.put(""+Math.random()*10000000, "2");
//				System.out.println(Thread.currentThread().getName()+"插入数据2");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName()+"循环结束");
	}
}



class HashMap线程 extends Thread{
	
	HashMap<String,String> map;
	
	public HashMap线程(HashMap<String, String> map) {
		super();
		this.map = map;
	}

	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			try {
				map.put(""+Math.random()*10000000, "1");
//				System.out.println(Thread.currentThread().getName()+"插入数据1");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName()+"循环结束");
	}
}