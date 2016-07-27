package com.lobinary.设计模式.工厂模式.普通工厂.水果工厂;

public class 水果工厂 {
	
	public enum 种类{
		西瓜,香蕉,草莓,榴莲
	}

	
	public static 水果 获取水果(种类 type) throws Exception{
		if(种类.西瓜==type){
			return new 西瓜();
		}else if(种类.草莓==type){
			return new 草莓();
		}else if(种类.香蕉==type){
			return new 香蕉();
		}
		throw new Exception("不支持的水果类型");
	}

}
