package com.lobinary.书籍.effective_java.C2创建和销毁对象;

/**
 * 
 * <pre>
 * 不要错误的认为：对象的创建过程耗费的资源是昂贵的，相反，一些小的对象的初始化过程是非常小的，jvm基本可以忽略不计这些
 * 本文只是警告：不要忽视自动装箱的过程，防止效率过低	
 * </pre>
 *
 * @ClassName: 自动装箱效率
 * @author 919515134@qq.com
 * @date 2016年12月1日 上午10:04:55
 * @version V1.0.0
 */
public class 自动装箱效率 {

	public static void main(String[] args) {

		longTest();
		LongTest();
		/*
		  	long计算结果为：2305843005992468481耗时：1438
			Long结果为：2305843005992468481耗时：13300
		 */
		
	}

	private static void longTest() {
		long s = System.currentTimeMillis();
		long sum = 0l;
		for (long i = 0; i < Integer.MAX_VALUE; i++) {
			sum += i;
		}
		long e = System.currentTimeMillis();
		System.out.println("long计算结果为："+sum+"耗时："+(e-s));
	}

	private static void LongTest() {
		long s = System.currentTimeMillis();
		Long sum = 0l;
		for (long i = 0; i < Integer.MAX_VALUE; i++) {
			sum += i;
		}
		long e = System.currentTimeMillis();
		System.out.println("Long结果为："+sum+"耗时："+(e-s));
	}
	
}
