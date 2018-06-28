package com.lobinary.设计模式.代理模式.虚拟代理.图形.实现类;

import com.lobinary.设计模式.代理模式.虚拟代理.图形.图形;


public class 圆形  implements 图形{
	
	public 圆形(){
		System.out.println("圆形初始化");
	}

	@Override
	public void 画() {
		System.out.println("我是圆形");
	}

}
