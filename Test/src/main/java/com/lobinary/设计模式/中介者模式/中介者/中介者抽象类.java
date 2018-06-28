package com.lobinary.设计模式.中介者模式.中介者;

import com.lobinary.设计模式.中介者模式.同事类.同事类抽象类;

public abstract class 中介者抽象类 {

	protected 同事类抽象类 同事类A;
	protected 同事类抽象类 同事类B;
	
	public 中介者抽象类(同事类抽象类 同事类a, 同事类抽象类 同事类b) {
		super();
		同事类A = 同事类a;
		同事类B = 同事类b;
	}
	
	public abstract void changeA(int num);
	
	public abstract void changeB(int num);

}
