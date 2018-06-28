package com.lobinary.设计模式.观察者模式.观察者.实现类;

import com.lobinary.设计模式.观察者模式.观察者.观察者;

public class 十进制观察者 extends 观察者{

	public 十进制观察者(com.lobinary.设计模式.观察者模式.变动类 变动类) {
		super(变动类);
	}

	@Override
	public void 下达通知(int 变动属性) {
		System.out.println("十进制观察者:"+变动属性);
	}

}
