package com.lobinary.源码.java.string源码;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.binary.BinaryCodec;
import org.apache.derby.iapi.util.UTF8Util;

public class String源码 {
	
	public final static int intV = 0x10FFFF;

	public static void main(String[] args) throws UnsupportedEncodingException {
		String s = new String("abcdefg");
		System.out.println(s);
		System.out.println(new String(s.getBytes(),3,2));
		int[] iarray = {97,98,99};
		System.out.println(new String(iarray,0,3));
		System.out.println(new String(iarray,1,1));
		System.out.println("codePointAt:"+s.charAt(3)+":"+s.codePointAt(3));
		System.out.println((int)'h');// 	110	1000 
		System.out.println((int)'汉');// 	110 1100 0100 1001
//		System.out.println((int)'𪚥');// 	因为属于陌生汉字，这个汉字属于16位之外的补充层的汉字，因此需要32位存储，那么就需要两个 char，所以就会报错
		System.out.println("汉 的长度为："+"汉".length());
		System.out.println("𪚥 的长度为："+"𪚥".length());
		System.out.println("李(length):"+new Character('李').SIZE);
		System.out.println("李(int):"+(int)'李');
		System.out.println("李(binary):"+Integer.toBinaryString((int)'李'));
		byte[] bytes = "李".getBytes("UTF-8");
		System.out.print("李(UTF-8):");
		for (int i = 0; i < bytes.length; i++) {
//			System.out.print("["+Integer.toBinaryString(bytes[i]) + "] ");
			System.out.print(Integer.toBinaryString(bytes[i]).substring(24, 32) + " ");
		}
		System.out.println();
		System.out.println("李(UTF-8):11100110 10011101 10001110");
		//输出所有字符对应关系
//		for (int i = 0; i <100000; i++) {
//			System.out.print((char)i);
//			if(i%100==0)System.out.println();
//		}
	}
	
}
