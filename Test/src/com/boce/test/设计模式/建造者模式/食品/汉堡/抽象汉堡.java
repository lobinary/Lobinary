package com.boce.test.设计模式.建造者模式.食品.汉堡;

import com.boce.test.设计模式.建造者模式.包装.包装;
import com.boce.test.设计模式.建造者模式.包装.实现类.纸质包装;
import com.boce.test.设计模式.建造者模式.食品.食品;

public abstract class 抽象汉堡 implements 食品{

	@Override
	public 包装 获取包装() {
		return new 纸质包装();//汉堡都是纸质包装
	}

}
