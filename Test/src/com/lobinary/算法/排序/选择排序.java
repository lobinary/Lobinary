package com.lobinary.算法.排序;

/**
 * 	基本思想：
	在要排序的一组数中，选出最小（或者最大）的一个数与第1个位置的数交换；然后在剩下的数当中再找最小（或者最大）的与第2个位置的数交换，依次类推，直到第n-1个元素（倒数第二个数）和第n个元素（最后一个数）比较为止。
	简单选择排序的示例：
	操作方法：
	第一趟，从n 个记录中找出关键码最小的记录与第一个记录交换；
	第二趟，从第二个记录开始的n-1 个记录中再选出关键码最小的记录与第二个记录交换；
	以此类推.....
	第i 趟，则从第i 个记录开始的n-i+1 个记录中选出关键码最小的记录与第i 个记录交换，
	直到整个序列按关键码有序。
 */
public class 选择排序 {
	
	/**
	 * 简单选择排序-仅选择出最小值依次排序
	 * @param a
	 * @return
	 */
	public static int[] sort(int[] a){
		System.out.println("选择排序(小)开始");
		SortUtil.out(a);
		for (int j = 0; j < a.length-1; j++) {
			int minNum = j;//我们应该赋值的是最小数字的位置，而不是最小数字的值
			for (int i = j+1; i < a.length; i++) {
				if(SortUtil.compare(a[minNum],a[i])){
					minNum = i;
//					System.out.println("minNum="+a[minNum]);
				}
			}
			SortUtil.swap(a, j, minNum);
//			System.out.println("==============a["+j+"]===="+a[j]);
		}
		SortUtil.out(a);
		System.out.println("选择排序(小)结束");
		return a;
	}
	
	/**
	 * 简单排序优化-寻找出最大和最小
	 * @param a
	 * @return
	 */
	public static int[] sort2(int[] a){
		System.out.println("选择排序(大小)开始");
		SortUtil.out(a);
		for (int j = 0; j < a.length/2; j++) {
			int minNum = j;//我们应该赋值的是最小数字的位置，而不是最小数字的值
			int maxNum = a.length-1-j;
			for (int i = j+1; i < a.length-j; i++) {
				if(SortUtil.compare(a[minNum],a[i])){
					minNum = i;
//					System.out.println("minNum="+a[minNum]);
					continue;
				}
				if(SortUtil.compare(a[i],a[maxNum])){
					maxNum = i;
//					System.out.println("maxNum="+a[maxNum]);
				}
			}
			SortUtil.swap(a, j, minNum);
			SortUtil.swap(a, a.length-1-j, maxNum);
//			System.out.println("==============a["+j+"]===="+a[j]);
//			System.out.println("==============a["+(a.length-1-j)+"]===="+a[a.length-1-j]);
//			SortUtil.out(a);
		}
		SortUtil.out(a);
		System.out.println("选择排序(大小)结束");
		return a;
	}


	public static void main(String[] args) {
		选择排序.sort2(SortUtil.getArray());
	}
}
