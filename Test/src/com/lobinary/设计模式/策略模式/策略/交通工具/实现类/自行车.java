package com.lobinary.设计模式.策略模式.策略.交通工具.实现类;

import com.lobinary.设计模式.策略模式.策略.交通工具.交通工具策略;

public class 自行车 implements 交通工具策略 {

	@Override
	public void 出发() {
		System.out.print("骑自行车出行");
	}

}
