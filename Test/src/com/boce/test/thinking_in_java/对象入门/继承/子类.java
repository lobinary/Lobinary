package com.boce.test.thinking_in_java.对象入门.继承;

public class 子类 extends 父类 {
	
	public String 变量="子类变量值";
	
	@Override
	public String 方法(){
		return "子类方法调用变量为："+变量;
	}

}
