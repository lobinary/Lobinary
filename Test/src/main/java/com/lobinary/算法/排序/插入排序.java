package com.lobinary.算法.排序;

/**
 * 
 * 从第一个开始判断
 * 依次通过和前方数据判断
 * 直至固定到应有位置
 * 执行在下方位置
 * 
 * 效率：
 * 时间复杂度：O（n²）.
 * 其他的插入排序有二分插入排序，2-路插入排序。
 * 
 * @see SortUtil
 *	
 */
public class 插入排序 {
	
	public static int[] sort(int[] a){
		System.out.println("插入排序开始");
		SortUtil.out(a);
		int j=0;
		for (int i = 1; i < a.length; i++) {
			j=i;
			while(j>=1&&SortUtil.compare(a[j-1],a[j])){
				a = SortUtil.swap(a, j, j-1);
				j--;
			}
		}
		SortUtil.out(a);
		System.out.println("插入排序结束");
		return a;
	}

	public static void main(String[] args) {
		插入排序.sort(SortUtil.getArray());
	}

}
