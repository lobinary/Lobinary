package com.lobinary.设计模式.空对象模式.图书.实现类;

import com.lobinary.设计模式.空对象模式.图书.图书;

public class 空图书 extends 图书 {

	@Override
	public void 展示内容() {
		System.out.println("对不起,没有发现这本书！");
	}

	@Override
	public boolean 是否存在该图书() {
		return false;
	}

}
