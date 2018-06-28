package com.lobinary.源码.java.编码;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;

public class UTF_16 {

	public static void main(String[] args) throws UnsupportedEncodingException {
		输出字符信息("A");
		System.out.println("========================================================");
		String s = "𠀁";
		输出字符信息(s);
	}

	private static void 输出字符信息(String sA) {
		System.out.println("字符长度为："+sA.length());
		int iA = sA.codePointAt(0);
		System.out.println("字符："+sA);
		System.out.println("A的Unicode十进制代码："+iA);
		System.out.println("A的Unicode十六进制代码："+Integer.toHexString(iA));
		System.out.println("A的Unicode二进制代码："+BMUtil.转换成Unicode二进制(iA));
		byte[] utf8BytesA = sA.getBytes(Charset.forName("UTF-8"));
		System.out.println("A的Unicode的UTF-8代码："+BMUtil.outBinary(utf8BytesA));
		byte[] utf16BytesA = sA.getBytes(Charset.forName("UTF-16"));
		System.out.println(Arrays.toString(utf16BytesA));
		System.out.println("A的Unicode的UTF-16代码(前边的1111 1110,1111 1111标识EF，是BOM，标识LE)："+BMUtil.outBinary(utf16BytesA));
		byte[] utf32BytesA = sA.getBytes(Charset.forName("UTF-32"));
		System.out.println(Arrays.toString(utf32BytesA));
		System.out.println("A的Unicode的UTF-32代码："+BMUtil.outBinary(utf32BytesA));
//		test("11110111");
		//1111 0000 1010 0000 1000 0000 1000 0001
		//1111 0000,1010 0000,1000 0000,1000 0001
		//1111 1110 1111 1111 0000 0000 0100 0001
		//1111 1110,1111 1111,0000 0000,0100 0001
		
		//1111 1110 1111 1111 1101 1000 0100 0000 1101 1100 0000 0001
		//1111 1110,1111 1111,1101 1000,0100 0000,1101 1100,0000 0001
	}
	
	public static void test(String s){
		int x = 0; 
		for(char c: s.toCharArray()) 
		     x = x * 2 + (c == '1' ? 1 : 0); 
		System.out.println(x); 
	}
	
}
