package com.boce.test.设计模式.组合实体模式;

import com.boce.test.设计模式.组合实体模式.实体.实体1;
import com.boce.test.设计模式.组合实体模式.实体.实体2;

public class 粗粒度对象 {

	private 实体1 实体1 = new 实体1();
	private 实体2 实体2 = new 实体2();
	
	public void 设置数据(String 数据1,String 数据2){
		实体1.set变量(数据1);
		实体2.set变量(数据2);
	}
	
	public String[] 获取数据(){
		return new String[]{实体1.get变量(),实体2.get变量()};
	}
	
	
}
