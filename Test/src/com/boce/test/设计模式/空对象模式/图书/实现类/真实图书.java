package com.boce.test.设计模式.空对象模式.图书.实现类;

import com.boce.test.设计模式.空对象模式.图书.图书;

public class 真实图书 extends 图书{
	
	public 真实图书(String 图书名称){
		this.图书名称 = 图书名称;
	}

	@Override
	public void 展示内容() {
		System.out.println("这本书的名字是《"+图书名称+"》,内容为一个浪漫的故事");
	}

	@Override
	public boolean 是否存在该图书() {
		return true;
	}
	
}
