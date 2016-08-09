package com.lobinary.算法.排序;

import java.util.List;

/**
 * 
  *
  *	<pre>
  * @Description: 排序工具
  * </pre>
  *
  * @ClassName: SortUtil
  * @author Comsys-Lobinary
  * @date 2016年7月4日 下午4:15:21
  * @version V1.0.0
 */
public class SortUtil {
	
	public static int 交换次数 = 0;
	public static int 比较次数 = 0;
	public static int 赋值次数 = 0;
	
	public static void main(String[] args) {
		插入排序.sort(getArray());
		outParam();
		resetParam();
		冒泡排序.sort(getArray());
		outParam();
		resetParam();
		选择排序.sort(getArray());
		outParam();
		resetParam();
		选择排序.sort2(getArray());
		outParam();
		resetParam();
		快速排序.sort(getArray());
		outParam();
	}

	private static void outParam() {
		System.out.println("排序参数统计： 比较次数:"+比较次数+"次,交换次数:"+交换次数+"次,赋值次数:"+赋值次数+"次");
	}

	private static void resetParam() {
		交换次数=0;
		比较次数=0;
	}
	
	public static int[] getArray(){
		int[] r = {5,4,6,3,7,2,8,1,9,0};
		return r;
	}
	
	/**
	 * 交换位置
	 * @param a
	 * @param x
	 * @param y
	 * @return
	 */
	public static int[] swap(int[] a,int x,int y){
		交换次数++;
		@SuppressWarnings("unused")
		String s = getString(a,x,y);
//		System.out.print(交换次数+"准备交换数组{"+s+"}的"+x+","+y+"位置,");
		int b = fz(a[x]);
		a[x] = fz(a[y]);
		a[y] = fz(b);
		s = getString(a,x,y);
//		System.out.println("交换后数据为{"+s+"}");
		return a;
	}
	
	/**
	 * 输出数组
	 * @param o
	 */
	@SuppressWarnings("unchecked")
	public static void out(Object o){
		StringBuffer sb = new StringBuffer();
		if(List.class.isInstance(o)){
			for (Object oo:(List<String>) o) {
				sb.append(oo+",");
			}
		}else if(int[].class.isInstance(o)){
			for (Object oo:(int[])o) {
				sb.append(oo+",");
			}
		}else if(long[].class.isInstance(o)){
			for (Object oo:(long[])o) {
				sb.append(oo+",");
			}
		}
		System.out.println(sb.toString().substring(0,sb.length()-1));
	}
	
	/**
	 * 输出数组
	 * @param o
	 */
	@SuppressWarnings("unchecked")
	public static String getString(Object o,int x,int y){
		StringBuffer sb = new StringBuffer();
		if(List.class.isInstance(o)){
			for (int i = 0; i < ((List<String>) o).size(); i++) {
				if(i==x||i==y){
					sb.append("【").append(((List<String>) o).get(i)+"],");
				}else{
					sb.append(((List<String>) o).get(i)+",");
				}
			}
		}else if(int[].class.isInstance(o)){
			for (int i = 0; i < ((int[]) o).length; i++) {
				if(i==x||i==y){
					sb.append("【").append(((int[]) o)[i]+"").append("】,");
				}else{
					sb.append(((int[]) o)[i]+",");
				}
			}
		}
		return sb.toString().substring(0,sb.length()-1);
	}
	

	/**
	 * i>j=true
	 * @param i
	 * @param j
	 * @return
	 */
	public static boolean compare(int i,int j) {
		比较次数++;
//		System.out.println(""+i + ">" + j + "=" + (i>j));
		return i>j;
	}
	
	/**
	 * 赋值  统计赋值次数
	 * @param i
	 * @return
	 */
	public static int fz(int i){
		赋值次数++;
		return i;
	}

}
