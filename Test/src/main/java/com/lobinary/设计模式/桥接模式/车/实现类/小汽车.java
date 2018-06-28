package com.lobinary.设计模式.桥接模式.车.实现类;

import com.lobinary.设计模式.桥接模式.车.车;

public class 小汽车 extends 车 {

	@Override
	public void run() {
		super.run();
		System.out.print("小汽车 跑在 ");
	}

}
