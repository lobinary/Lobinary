package com.lobinary.设计模式.建造者模式.食品.汉堡.实现类;

import com.lobinary.设计模式.建造者模式.食品.汉堡.抽象汉堡;

public class 吉士汉堡 extends 抽象汉堡 {

	@Override
	public String 获取名称() {
		return "吉士汉堡";
	}

	@Override
	public int 获取价格() {
		return 9;
	}

}
