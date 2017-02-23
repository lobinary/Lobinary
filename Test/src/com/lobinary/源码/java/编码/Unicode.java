package com.lobinary.源码.java.编码;

import com.lobinary.java.多线程.TU;

public class Unicode {

	public static void main(String[] args) {
		//详见有道云笔记学习数据
		
		//java 存储字符串采用的是UTF-16编码
		String 𠀁 = "𠀁A";//	1101 1000,0100 0000,	1101 1100,0000 0001,		0000 0000,0100 0001
						//				55360						56321					65
		System.out.println(𠀁);
		char[] chars = 𠀁.toCharArray();
		System.out.println("长度为："+chars.length);
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
//			System.out.println(c);
			System.out.println("char["+i+"]："+(int)c);
		}
		System.out.println("位置[0]的UniCode代码："+(int)𠀁.codePointAt(0));//codePointAt方法为拿到0个元素的Unicode代码，支持UTF-16的代理对模式
		System.out.println("位置[1]的UniCode代码："+(int)𠀁.codePointAt(1));//codePointAt方法为拿到0个元素的Unicode代码，支持UTF-16的代理对模式
		System.out.println("位置[2]的UniCode代码："+(int)𠀁.codePointAt(2));//codePointAt方法为拿到0个元素的Unicode代码，支持UTF-16的代理对模式
		
		//针对代理对的数据解析方法如下：
		/**
		 * ((high - MIN_HIGH_SURROGATE) << 10)
                 + (low - MIN_LOW_SURROGATE)
                 + MIN_SUPPLEMENTARY_CODE_POINT;
		 */
		char 高代理对  = 55360;//1101 1000,0100 0000
		char 低代理对 = 56321;//1101 1100,0000 0001
		System.out.println("0000 0000 0000 0000 1101 1000 0100 0000");
		System.out.println(BMUtil.转换成Unicode二进制(((高代理对-Character.MIN_HIGH_SURROGATE)<<10)
				+(低代理对-Character.MIN_LOW_SURROGATE)+Character.MIN_SUPPLEMENTARY_CODE_POINT));
		
		/**
		 * ((high << 10) + low) + (MIN_SUPPLEMENTARY_CODE_POINT
                                       - (MIN_HIGH_SURROGATE << 10)
                                       - MIN_LOW_SURROGATE)
		 */
		System.out.println(BMUtil.转换成Unicode二进制(((高代理对 << 10) + 低代理对) + (Character.MIN_SUPPLEMENTARY_CODE_POINT
                - (Character.MIN_HIGH_SURROGATE << 10)
                - Character.MIN_LOW_SURROGATE)));

		System.out.println("准备测试倒计时！");
		TU.s(10000);
		System.out.println("开始");
		long 执行测试 = 10000000000l;
		long start = System.currentTimeMillis();
		for (long i = 0; i < 执行测试; i++) {
			/*
			 *	执行流程如下：
			 *		0000 0000 0000 0000 1101 1000 0100 0000	-	0000 0000 0000 0000 1101 1000 0000 0000
			 *		0000 0000 0000 0000 0000 0000 0100 0000 <<	10
			 *	  	0000 0000 0000 0001 0000 0000 0000 0000
			 *		
			 * 		0000 0000 0000 0000 1101 1100 0000 0001	-	0000 0000 0000 0000 1101 1100 0000 0000
			 * +	0000 0000 0000 0000 0000 0000 0000 0001
			 * 
			 * 	   	0000 0000 0000 0001 0000 0000 0000 0001
			 * +  	0000 0000 0000 0001 0000 0000 0000 0000
			 * 	   	0000 0000 0000 0010 0000 0000 0000 0001
			 * 		
			 */
			int l = ((高代理对-Character.MIN_HIGH_SURROGATE)<<10)+(低代理对-Character.MIN_LOW_SURROGATE)+Character.MIN_SUPPLEMENTARY_CODE_POINT;
		}
		long end = System.currentTimeMillis();
		System.out.println("耗时："+(end-start)+"毫秒");
		start = System.currentTimeMillis();
		for (long i = 0; i < 执行测试; i++) {
			
			/*
			 * 执行流程如下：
			 *		0000 0000 0000 0000 1101 1000 0100 0000	<<	10
			 *		0000 0011 0110 0001 0000 0000 0000 0000 + 0000 0000 0000 0000 1101 1100 0000 0001
			 *		0000 0011 0110 0001 1101 1100 0000 0001 + 1 0000 0000 0000 0000
			 *		0000 0011 0110 0010 1101 1100 0000 0001
			 *
			 *		0000 0000 0000 0000 1101 1000 0000 0000	<< 10
			 *	-	0000 0011 0110 0000 0000 0000 0000 0000
			 *
			 *	-	0000 0000 0000 0000 1101 1100 0000 0000
			 *		0000 0000 0000 0010 1101 1100 0000 0001
			 *	-	0000 0000 0000 0000 1101 1100 0000 0000
			 *		0000 0000 0000 0010 0000 0000 0000 0001
			 * 	 
			 */
			int l = ((高代理对 << 10) + 低代理对) + (Character.MIN_SUPPLEMENTARY_CODE_POINT 
					- (Character.MIN_HIGH_SURROGATE << 10)
	                - Character.MIN_LOW_SURROGATE);
		}
		end = System.currentTimeMillis();
		System.out.println("耗时："+(end-start)+"毫秒");
	}

}
