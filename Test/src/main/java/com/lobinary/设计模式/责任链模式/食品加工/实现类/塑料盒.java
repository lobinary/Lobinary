package com.lobinary.设计模式.责任链模式.食品加工.实现类;

import com.lobinary.设计模式.责任链模式.食品加工.包装;

public class 塑料盒 extends 包装{

	@Override
	public void 包装() {
		System.out.println("打开塑料盒");
		if(下一层包装!=null)下一层包装.包装();
		System.out.println("将塑料盒塞入袋子");
	}

}
