package com.boce.test.排序;

/**
 * 
 * 从左往右
 * 1和2比 之后交换
 * 2和三比之后交换
 * 然后重新来
 *
 */
public class 冒泡排序 {
	
	public static int[] sort(int[] a){
		System.out.println("冒泡排序开始");
		SortUtil.out(a);
		for (int j = 0; j < a.length-1; j++) {
			for (int i = 0; i < a.length-1; i++) {
				if(SortUtil.compare(a[i],a[i+1])){
					SortUtil.swap(a, i, i+1);
				}
			}
		}
		SortUtil.out(a);
		System.out.println("冒泡排序结束");
		return a;
	}


	public static void main(String[] args) {
		冒泡排序.sort(SortUtil.getArray());
		System.out.println("");
	}
}
