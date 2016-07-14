package com.boce.test.设计模式.装饰器模式.装饰器;

import com.boce.test.设计模式.装饰器模式.图形;

/**
 * 图形装饰器的核心
 * 
 */
public abstract class 图形装饰器  implements 图形{
	
	public 图形 标准图形实现类;

	public 图形装饰器(图形 标准图形实现类) {
		super();
		this.标准图形实现类 = 标准图形实现类;
	}

	@Override
	public void 画() {
		标准图形实现类.画();
	}

}
