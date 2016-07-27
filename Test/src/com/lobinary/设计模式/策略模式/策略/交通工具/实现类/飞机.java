package com.lobinary.设计模式.策略模式.策略.交通工具.实现类;

import com.lobinary.设计模式.策略模式.策略.交通工具.交通工具策略;

public class 飞机 implements 交通工具策略 {

	@Override
	public void 出发() {
		System.out.print("坐飞机出发咯");
	}

}
