package com.lobinary.设计模式.工厂模式.抽象工厂.图形工厂;

import com.lobinary.设计模式.工厂模式.抽象工厂.抽象工厂;
import com.lobinary.设计模式.工厂模式.抽象工厂.水果工厂.水果;

public class 图形工厂 extends 抽象工厂{
	
	@Override
	public 图形 获取图形(种类 type) throws Exception{
		if(种类.正方形==type){
			return new 正方形();
		}else if(种类.三角形==type){
			return new 三角形();
		}else if(种类.圆==type){
			return new 圆();
		}
		throw new Exception("不支持的水果类型");
	}

	@Override
	public 水果 获取水果(种类 type) {
		return null;
	}

}
