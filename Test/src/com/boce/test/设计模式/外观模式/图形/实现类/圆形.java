package com.boce.test.设计模式.外观模式.图形.实现类;

import com.boce.test.设计模式.外观模式.图形.图形;

public class 圆形  implements 图形{

	@Override
	public void 画() {
		System.out.println("我是圆形");
	}

}
