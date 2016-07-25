package com.boce.test.thinking_in_java.对象入门.继承;

public class 父类 {
	
	public String 变量="父类变量值";

	public 父类(){
		System.out.println("父类构造方法");
		System.out.println("this.变量"+this.变量);
		System.out.println("this.方法()"+this.方法());
	}
	
	public String 方法(){
		return "父类方法调用变量为："+变量;
	}

}
