package com.boce.test.设计模式.建造者模式.食品.汉堡.实现类;

import com.boce.test.设计模式.建造者模式.食品.汉堡.抽象汉堡;

public class 香辣鸡腿煲 extends 抽象汉堡 {

	@Override
	public String 获取名称() {
		return "香辣鸡腿煲";
	}

	@Override
	public int 获取价格() {
		return 13;
	}

}
