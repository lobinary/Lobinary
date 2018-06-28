package com.lobinary.设计模式.装饰器模式.装饰器.使用者;

import com.lobinary.设计模式.装饰器模式.图形;
import com.lobinary.设计模式.装饰器模式.装饰器.图形装饰器;

public class 蓝色图形 extends 图形装饰器{

	public 蓝色图形(图形 标准图形实现类) {
		super(标准图形实现类);
	}

	@Override
	public void 画() {
		System.out.print("蓝色");
		super.标准图形实现类.画();
	}

}
