package com.boce.test.设计模式.桥接模式.车.实现类;

import com.boce.test.设计模式.桥接模式.车.车;

public class 大卡车 extends 车 {

	@Override
	public void run() {
		super.run();
		System.out.print("大卡车 跑在 ");
	}

}
