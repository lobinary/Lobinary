package com.lobinary.书籍.effective_java.C2创建和销毁对象;

public class 使用枚举单例 {

	public static void main(String[] args) {
		String s = 单例枚举.单例对象.拼接字符串("X", "Y");
		System.out.println(s);
	}
	
}

enum 单例枚举 {

	单例对象;
	
	public String 拼接字符串(String a,String b){
		return a+b;
	}
}