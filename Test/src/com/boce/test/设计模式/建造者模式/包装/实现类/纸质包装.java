package com.boce.test.设计模式.建造者模式.包装.实现类;

import com.boce.test.设计模式.建造者模式.包装.包装;

public class 纸质包装 implements 包装{

	@Override
	public String 获取包装材料() {
		return "纸质";
	}

}
