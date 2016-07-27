package com.lobinary.书籍.thinking_in_java.对象入门;

public class 类边界 {
	
	public static boolean isOn;
	private static boolean pri;
	protected static boolean pro;
	
	public static void outPublic(){
		System.out.println(isOn);
		System.out.println(pri);
		System.out.println(pro);
	}
	
	private static void outPrivate(){
		System.out.println(isOn);
		System.out.println(pri);
		System.out.println(pro);
	}
	
	protected static void outProtected(){
		System.out.println(isOn);
		System.out.println(pri);
		System.out.println(pro);
	}

}
