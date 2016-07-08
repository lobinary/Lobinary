package com.boce.test.设计模式.工厂模式.抽象工厂;

import com.boce.test.设计模式.工厂模式.抽象工厂.图形工厂.图形;
import com.boce.test.设计模式.工厂模式.抽象工厂.水果工厂.水果;

public abstract class 抽象工厂 {

	public enum 种类{
		西瓜,香蕉,草莓,榴莲,
		正方形,长方形,三角形,圆
	}

	public abstract 水果 获取水果(抽象工厂.种类 type)throws Exception;
	public abstract 图形 获取图形(抽象工厂.种类 type)throws Exception;
	
}
