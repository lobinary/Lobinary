package com.lobinary.书籍.thinking_in_java.对象入门;

import java.util.Date;

public class 运算符 {
	
	public static void main(String[] args) {
		int a = 1 + 1*2 + 1;
		System.out.println("运算符优先级:\t\tint a=1+1*2+1; \t\t\t输出a="+a);
		Object o = 3;
		System.out.println("对象Object赋值:\t\tObject o=3; \t\t\t输出o="+o);
		
		int i = 1;
		i+=6;
		i*=2;
		System.out.println("+= *=的使用:\t\tint i=1; i+=6; i*=2; \t\t输出i="+i);
		
		int j = 1;
		j = 2 *- i;
		System.out.println("赋值符号[-]的使用:\t\tint j=1;j=2*-i;\t\t\t输出j="+j);
		
		int x = 1;
		x <<= 2;//提醒：>> 和 >>>的区别： >>代表只向右移位,>>>则代表移位的同时，将原有空缺位补零，而>>则不会保留空缺位
		System.out.println("位运算符[>>>]的使用:\tint x=1;x<<=2;\t\t\t输出x="+x + " , 说明:1的二进制是0001,左移位2个变成0100,结果为4");
		
		System.out.println("取反运算符[~]的使用: \t\t\t\t\t输出 ~8="+~8+" , ~1="+~1+" , ~-1="+~-1+" , ~0="+~0);
		//二进制：8421
		System.out.println("异或运算符[^]的使用:\t说明：相同位上,相同为0，不同为1\t输出8^5="+(8^5)+" , 说明:8是1000,5是0101,得出为：1101=13;布尔同理:(false^true)" + (false^true));
		
		System.out.print("字符串运算符[+]的使用:\t\t\t\t\t输出1+2+\"3\"+4+5=");
		System.out.println(1+2+"3"+4+5);
		
		do{
			System.out.println("执行体1");
		}while(1>a);
		
		while(1>a){
			System.out.println("执行体2");
		}
		
		System.out.println("======================================");
		extracted(1);
		System.out.println("======================================");
		extracted(2);
		System.out.println("======================================");
		extracted(3);
		System.out.println("======================================");
		extracted(0);
		System.out.println("======================================");
	}

	private static void extracted(int z) {
		switch (z) {
		case 1:
			System.out.println("z被case进行第一层处理");
			z++;
		case 2:
			System.out.println("z被case进行第二层处理");
			z++;
		case 3:
			System.out.println("z被case进行第三层处理");
			break;
		default:
			System.out.println("z值有错误");
			return;
		}
	}

}
