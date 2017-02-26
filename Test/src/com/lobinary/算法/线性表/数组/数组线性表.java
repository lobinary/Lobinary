package com.lobinary.算法.线性表.数组;

import java.util.Arrays;

import com.lobinary.算法.线性表.线性表;

public class 数组线性表<T> implements 线性表<T>{
	
	private final static int DEFAULT_SIZE = 10;
	
	Object[] tArray = new Object[DEFAULT_SIZE];
	int index = 0;
	
	public static void main(String[] args) {
		线性表<String> list = new 数组线性表<String>();
		System.out.println(list);
		list.增加(2,"A");
	}

	@Override
	public String toString() {
		return "数组线性表 [tArray=" + Arrays.toString(tArray) + "]";
	}

	/**
	 * 复杂度为 n
	 */
	@Override
	public void 增加(int i, T t) {
		
		//判断数组长度是否够用 需要算上增加的
		if(index==tArray.length-2)tArray = Arrays.copyOf(tArray, tArray.length*2);
		
		//将存储数据的位置向后移动
		for(int j = index;j>i;j--){
			tArray[j+1] = tArray[j]; 
		}
		
		//将存储数据存入指定位置
		tArray[i] = t;
	}

	@Override
	public void 删除(int i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void 更改(int i, T t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T 查找(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
