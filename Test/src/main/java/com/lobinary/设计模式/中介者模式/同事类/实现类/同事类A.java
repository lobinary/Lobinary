package com.lobinary.设计模式.中介者模式.同事类.实现类;

import com.lobinary.设计模式.中介者模式.中介者.中介者抽象类;
import com.lobinary.设计模式.中介者模式.同事类.同事类抽象类;

public class 同事类A extends 同事类抽象类 {

	@Override
	public void changeNum(int num, 中介者抽象类 中介者) {
		setNum(num);
		中介者.changeB(num);
	}

}
