package com.lobinary.书籍.thinking_in_java.对象入门.继承;

public class 继承测试 {
	
	public static void main(String[] args) {
		父类 f = new 子类();
		System.out.println("变量："+f.变量);
		System.out.println("方法："+f.方法());
		
		System.out.println("=================================");
		
		子类 z = new 子类();
		System.out.println("变量："+z.变量);
		System.out.println("方法："+z.方法());
		
		char c = 'x';
		Character C = new Character(c);
		System.out.println(C);
	}

}
