package com.boce.test.设计模式.装饰器模式.实现类;

import com.boce.test.设计模式.装饰器模式.图形;


public class 三角形 implements 图形 {

	@Override
	public void 画() {
		System.out.println("三角形绘制中......");
	}

}
