package com.lobinary.书籍.thinking_in_java.对象入门;

public class 类边界测试 {

	public static void main(String[] args) {
		System.out.println(类边界.isOn);
//		System.out.println(类边界.pri);//The field 类边界.pri is not visible ,，at com.boce.test.thinking_in_java.对象入门.测试.main(测试.java:7)
		System.out.println(类边界.pro);
		System.out.println(类边界.pro = true);
		System.out.println(类边界.pro);
		
		System.out.println("============下outPublic=================");
		类边界.outPublic();

		System.out.println("============下outProtected=================");
//		类边界.outPrivate();//The method outPrivate() from the type 类边界 is not visible,at com.boce.test.thinking_in_java.对象入门.测试.main(测试.java:13)
		类边界.outProtected();
	}

}
