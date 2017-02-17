package com.lobinary.源码.java.string源码;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class String源码 {

	public static void main(String[] args) {
		String s = new String("abcdefg");
		System.out.println(s);
		System.out.println(new String(s.getBytes(),3,2));
		
		System.out.println(-1>>>1);//1000 0001   -->补码	1111 1111 -->移位	111 1111 1111 1111 1111 1111 1111 1111	 
		System.out.println(1>>>1);																//	111	1111 1111 1111 1111 1111 1111 1111
		//2 4 8 16 24 32 64 128 256 
		
		System.out.println(~(2));//10
	}
	
}
