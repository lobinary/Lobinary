package com.lobinary.书籍.effective_java.C3对于所有对象都通用的方法;

import java.util.Arrays;

/**
 * 
 * <pre>
 * 	Comparable接口使用
 * </pre>
 *
 * @ClassName: Comparable接口
 * @author 919515134@qq.com
 * @date 2016年12月5日 下午4:14:57
 * @version V1.0.0
 */
public class Comparable接口 implements Comparable<Comparable接口>{

	private int value;
	
	public static void main(String[] args) {
		Comparable接口[] list = new Comparable接口[3];
		list[0] = new Comparable接口(3);
		list[1] = new Comparable接口(1);
		list[2] = new Comparable接口(2);
		for (Comparable接口 comparable接口 : list) {
			System.out.println(comparable接口);
		}
		System.out.println("=========================");
		Arrays.sort(list);
		for (Comparable接口 comparable接口 : list) {
			System.out.println(comparable接口);
		}
		
	}

	public Comparable接口(int value) {
		super();
		this.value = value;
	}

	@Override
	public int compareTo(Comparable接口 o) {
		if(this.value>o.value)return 1;
		if(this.value==o.value)return 0;
		return -1;
	}

	@Override
	public String toString() {
		return "[value=" + value + "]";
	}

}
