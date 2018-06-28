package com.lobinary.设计模式.责任链模式.食品加工.实现类;

import com.lobinary.设计模式.责任链模式.食品加工.包装;

public class 饼干 extends 包装{

	@Override
	public void 包装() {
		System.out.println("从流水线拿起饼干");
		if(下一层包装!=null)下一层包装.包装();
		System.out.println("将饼干放入盒子");
	}

}
