package com.lobinary.设计模式.组合实体模式;

public class 组合实体 {
	
	粗粒度对象 粗粒度对象 = new 粗粒度对象();
	
	public void 设置数据(String 数据1,String 数据2){
		粗粒度对象.设置数据(数据1, 数据2);
	}
	
	public String[] 获取数据(){
		return 粗粒度对象.获取数据();
	}
	

}
