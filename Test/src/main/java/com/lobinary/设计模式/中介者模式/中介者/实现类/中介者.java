package com.lobinary.设计模式.中介者模式.中介者.实现类;

import com.lobinary.设计模式.中介者模式.中介者.中介者抽象类;
import com.lobinary.设计模式.中介者模式.同事类.同事类抽象类;

public class 中介者 extends 中介者抽象类{

	public 中介者(同事类抽象类 同事类a, 同事类抽象类 同事类b) {
		super(同事类a, 同事类b);
	}

	@Override
	public void changeA(int num) {
		同事类A.setNum(num*100);
	}

	@Override
	public void changeB(int num) {
		同事类B.setNum(num/100);
	}

}
