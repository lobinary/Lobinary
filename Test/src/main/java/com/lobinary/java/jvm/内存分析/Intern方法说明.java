package com.lobinary.java.jvm.内存分析;

public class Intern方法说明 {

	public static void main(String[] args) {
		test1();
	}

	/**
	 * 1.6为 false
	 * 1.7以后为true
	 * 
	 * JDK1.7更改过substring的实现，
	 * "在1.6中，这个方法只会在标识现有字符串的字符数组上给一个窗口来表示结果字符串，但是不会创建一个新的字符串对象",
	 * 所以常量池里没有截取后的字符串，s2.intern()返回的是个新的引用，
	 * 所以是false；1.7之后substring会创建一个新的字符串，
	 * 所以常量池里应该已经包含了截取后的字符串，s2.intern()返回的也是s2的引用，
	 * 所以结果是true
	 */
	private static void test1() {
		 String s1 = "sss111";
	        String s2 = s1.substring(0, 3);
	        String s3 = s2.intern();
	        System.out.println(s2 == s3);
	}
}
