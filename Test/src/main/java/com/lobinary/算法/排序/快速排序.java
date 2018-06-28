package com.lobinary.算法.排序;

/**
 * 数组位置 1 2 3 4 5 6 7
 * 数组数值 5 7 9 3 7 4 8
 * 
 * 随意取一个值（多数情况下为首个值）作为标记位置值
 * 然后根据这个值将现在范围（起始标记）内的分为两边
 * 左边比标记位置值小，右边比标记位置值大，相等的随意一边
 * 然后根据左边、右边继续以此类推，直至剩下不需要继续拆分情况，结束
 * @see SortUtil
 *	
 */
public class 快速排序 {
	
	public static int[] sort(int[] a){
		System.out.println("快速排序开始");
		SortUtil.out(a);
		a = subsort优化1(a,0,a.length-1);
		SortUtil.out(a);
		System.out.println("快速排序结束");
		return a;
	}
	
	/**
	 * 子排序
	 * 填坑型 
	 * @param a
	 * @param startIndexTemp
	 * @param endIndexTemp
	 * @return
	 */
	public static int[] subsort(int[] a,int startIndex,int endIndex){
		int startIndexTemp = startIndex;
		int endIndexTemp = endIndex;
		if(endIndexTemp-startIndexTemp<=0){
			System.out.println("开始结束间隔过小("+startIndex+")，直接返回！");
			return a;
		}else{
			System.out.println("准备排序["+startIndex+"~"+endIndex+"]范围内数据");
		}
		int midValue = a[startIndexTemp];
		System.out.println("midValue= " + midValue);
		boolean fillStart = true;
		while(endIndexTemp-startIndexTemp!=0){
//			System.out.println("=========当前start位置："+ startIndexTemp + "，结束标签位置：" + endIndexTemp);
			if(fillStart){
				if(SortUtil.compare(midValue, a[endIndexTemp])){
					System.out.println("开始赋值-准备将"+endIndexTemp+"["+a[endIndexTemp]+"]赋值给" + startIndexTemp + "["+a[startIndexTemp]+"]");
					a[startIndexTemp] = SortUtil.fz(a[endIndexTemp]);
					startIndexTemp++;
					fillStart = false;
					SortUtil.out(a);
				}else{
					endIndexTemp--;
				}
			}else{
				if(SortUtil.compare(a[startIndexTemp],midValue )){
					System.out.println("结尾赋值准备将"+startIndexTemp+"["+a[startIndexTemp]+"]赋值给" + endIndexTemp + "["+a[endIndexTemp]+"]");
					a[endIndexTemp] = SortUtil.fz(a[startIndexTemp]);
					endIndexTemp--;
					fillStart = true;
					SortUtil.out(a);
				}else{
					startIndexTemp++;
				}
			}
		}
		System.out.println("1===========================================排序结束：startIndexTemp:"+ startIndexTemp);
		a[startIndexTemp] = SortUtil.fz(midValue);
		SortUtil.out(a);
		a = subsort(a,startIndex,startIndexTemp-1);
		a = subsort(a,endIndexTemp+1,endIndex);
		return a;
	}
	
	/**
	 * 子排序
	 * @param a
	 * @param startIndexTemp
	 * @param endIndexTemp
	 * @return
	 */
	public static int[] subsort优化1(int[] a,int startIndex,int endIndex){
		int startIndexTemp = startIndex;
		int endIndexTemp = endIndex;
		if(endIndexTemp-startIndexTemp<=0){
			System.out.println("开始结束间隔过小("+(endIndexTemp-startIndexTemp)+")，直接返回！");
			return a;
		}else if(endIndexTemp-startIndexTemp==1){
			System.out.println("开始结束间隔过小("+(endIndexTemp-startIndexTemp)+")，准备直接判断并返回！");
			if(SortUtil.compare(a[startIndexTemp], a[endIndexTemp])){
				SortUtil.swap(a, startIndex, endIndexTemp);
			}
			return a;
		}else{
			System.out.println("准备排序["+startIndex+"~"+endIndex+"]范围内数据");
		}
		int midValue = a[startIndex];
		System.out.println("midValue= " + midValue);
		startIndexTemp++;
		while(endIndexTemp-startIndexTemp!=0){
			System.out.println("=========当前start位置："+ startIndexTemp + "，结束标签位置：" + endIndexTemp);
			while(endIndexTemp>startIndexTemp&&SortUtil.compare(a[endIndexTemp],midValue))endIndexTemp--;
			while(endIndexTemp>startIndexTemp&&SortUtil.compare(midValue,a[startIndexTemp]))startIndexTemp++;
			if(endIndexTemp!=startIndexTemp){
				SortUtil.swap(a, startIndexTemp, endIndexTemp);
			}
		}
		SortUtil.swap(a, startIndex, endIndexTemp);
		System.out.print("2===========================================排序结束：startIndexTemp"+ startIndexTemp+ ",当前数据: ");
		SortUtil.out(a);
		a = subsort优化1(a,startIndex,startIndexTemp-1);
		a = subsort优化1(a,endIndexTemp+1,endIndex);
		return a;
	}

	public static void main(String[] args) {
		快速排序.sort(SortUtil.getArray());
	}

}
