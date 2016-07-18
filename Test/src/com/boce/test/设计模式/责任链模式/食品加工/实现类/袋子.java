package com.boce.test.设计模式.责任链模式.食品加工.实现类;

import com.boce.test.设计模式.责任链模式.食品加工.包装;

public class 袋子 extends 包装{

	@Override
	public void 包装() {
		System.out.println("打开袋子");
		if(下一层包装!=null)下一层包装.包装();
		System.out.println("封闭袋子");
	}

}
