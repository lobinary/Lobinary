package com.lobinary.设计模式.组合实体模式;

public class 使用类 {
	
	组合实体 组合实体 = new 组合实体();
	
	public void 打印对象(){
		for(String s:组合实体.获取数据()){
			System.out.println("数据："+s);
		}
	}
	
	public void 设置数据(String 数据1,String 数据2){
		组合实体.设置数据(数据1, 数据2);
	}
	
}
