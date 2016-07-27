package com.lobinary.设计模式.工厂模式.抽象工厂.水果工厂;

import com.lobinary.设计模式.工厂模式.抽象工厂.抽象工厂;
import com.lobinary.设计模式.工厂模式.抽象工厂.图形工厂.图形;

public class 水果工厂 extends 抽象工厂 {

	@Override
	public 水果 获取水果(种类 type) throws Exception{
		if(种类.西瓜==type){
			return new 西瓜();
		}else if(种类.草莓==type){
			return new 草莓();
		}else if(种类.香蕉==type){
			return new 香蕉();
		}
		throw new Exception("不支持的水果类型");
	}

	@Override
	public 图形 获取图形(种类 type) throws Exception {
		return null;
	}

}
