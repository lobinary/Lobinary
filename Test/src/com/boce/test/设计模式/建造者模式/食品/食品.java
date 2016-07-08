package com.boce.test.设计模式.建造者模式.食品;

import com.boce.test.设计模式.建造者模式.包装.包装;

public interface 食品 {

	public String 获取名称();
	public int 获取价格();
	public 包装 获取包装();
	
}
