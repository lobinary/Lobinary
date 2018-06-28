package com.lobinary.书籍.thinking_in_java.隐藏实施过程;

import java.io.File;

public class 修饰符 {
	
	public static void main(String[] args) {
		修饰符模板 t = new 修饰符模板();
		t.friendly友好修饰符();//包内的任何类，包外的那些继承了此类的子类【不能访问】；
//		t.private私有修饰符();//除了类内部可访问，包内包外的任何类均不能访问；
		t.protected保护修饰符();//包内的任何类，包外的那些继承了此类的子类【能访问】；
		t.public公共修饰符();//包内及包外的任何类均可以访问；
	}

}
