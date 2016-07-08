package com.boce.test.设计模式.建造者模式.食品.冷饮;

import com.boce.test.设计模式.建造者模式.包装.包装;
import com.boce.test.设计模式.建造者模式.包装.实现类.杯子包装;
import com.boce.test.设计模式.建造者模式.食品.食品;

public abstract class 抽象冷饮 implements 食品{

	@Override
	public int 获取价格() {
		return 7;//冷饮都是7元
	}

	@Override
	public 包装 获取包装() {
		return new 杯子包装();//冷饮包装都是杯子
	}

}
