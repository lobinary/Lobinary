package com.lobinary.java.多线程;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TU {
	
	public static void main(String[] args) {
		new Thread(() -> System.out.println("Hi")).start();
		System.out.println("hello");
		List<String> list = new ArrayList<String>();
		list.stream();
	}
	
	static class 线程名称 extends Thread{
         
        @Override
        public void run() {
        	
        }
    }
	
	/**
	 * 睡眠
	 * @param l
	 */
	public static void s(long l){
		try {
			Thread.sleep(l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void start(Class<?> runnableClazz, int threadNum, Object... params) {
		ExecutorService threadPool = Executors.newFixedThreadPool(threadNum);
		Runnable thread = null;
		try {
			if(params.length==0){
				thread = (Runnable) runnableClazz.newInstance();
			}else{
				Class<?>[] paramsClass = new Class<?>[params.length];
				for(int i=0;i<params.length;++i){  
					paramsClass[i]=params[i].getClass();//返回类信息  
//	                System.out.println("classParas[i]="+paramsClass[i]);  
	            } 
				Constructor<?> constructor = runnableClazz.getConstructor(paramsClass);
				thread = (Runnable) constructor.newInstance(params);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < threadNum; i++) {
			threadPool.submit(thread);
		}
		threadPool.shutdown();
	}

	public static void l(String theadName,String log) {
		Thread.currentThread().setName(theadName);
		System.out.println(Thread.currentThread().getName()+ " : " + log);
	}
	
	public static void l(Object log) {
		String name = Thread.currentThread().getName();
		if(name.equals("main")){
			name = "线程["+System.currentTimeMillis()+"]";
			Thread.currentThread().setName(name);
		}
		System.out.println(name+ " : " + log);
	}

	public static void outTime() {
		Thread t = new Thread(){

			@Override
			public void run() {
				int i = 0;
				while(true){
					System.out.print(":"+i++);
					s(1000);
				}
			}
			
		};
		t.setDaemon(true);
		t.start();
	}
	

}
