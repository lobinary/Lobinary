package com.lobinary.设计模式.桥接模式.路.实现类;

import com.lobinary.设计模式.桥接模式.路.路;

public class 高速公路 extends 路 {

	@Override
	public void run() {
		super.run();
		System.out.println("高速公路");
	}

	
	
}
