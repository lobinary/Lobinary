package com.lobinary.java.jdk.jdk18.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class stream串行与并行速度比较案例 {

	public static void main(String[] args) {
		串行();
		
		并行();
	}

	private static void 并行() {
		List<String> list = new ArrayList<String>();
		for(int i=0;i<1000000;i++){
		double d = Math.random()*1000;
		list.add(d+"");
		}
		long start = System.nanoTime();//获取系统开始排序的时间点
		int count = (int)((Stream) list.stream().parallel()).sorted().count();
		long end = System.nanoTime();//获取系统结束排序的时间点
		long ms = TimeUnit.NANOSECONDS.toMillis(end-start);//得到并行排序所用的时间
		System.out.println(ms+"ms(并行)");
	}

	private static void 串行() {
		List<String> list = new ArrayList<String>();
		for(int i=0;i<1000000;i++){
			double d = Math.random()*1000;
			list.add(d+"");
		}
		long start = System.nanoTime();//获取系统开始排序的时间点
		int count= (int) ((Stream<?>) list.stream().sequential()).sorted().count();
		long end = System.nanoTime();//获取系统结束排序的时间点
		long ms = TimeUnit.NANOSECONDS.toMillis(end-start);//得到串行排序所用的时间
		System.out.println(ms+"ms(串行)");
	}

}
