package com.lobinary.书籍.thinking_in_java.对象入门.继承;

public class 子类 extends 父类 {
	
	public String 变量="子类变量值";
	

	public 子类(){
		System.out.println("子类构造方法");
		System.out.println("this.变量"+this.变量);
		System.out.println("this.方法()"+this.方法());
	}
	
	@Override
	public String 方法(){
		return "子类方法调用变量为："+变量;
	}

}
