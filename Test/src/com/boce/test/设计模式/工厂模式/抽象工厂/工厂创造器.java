package com.boce.test.设计模式.工厂模式.抽象工厂;

import com.boce.test.设计模式.工厂模式.抽象工厂.图形工厂.图形工厂;
import com.boce.test.设计模式.工厂模式.抽象工厂.水果工厂.水果工厂;

public class 工厂创造器 {

	public enum 种类 {
		水果, 图形
	}

	public static 抽象工厂 获取工厂(种类 工厂种类) throws Exception {
		switch (工厂种类) {
		case 水果:
			return new 水果工厂();
		case 图形:
			return new 图形工厂();
		default :
			throw new Exception("不支持的工厂类型");
		}
	}

}
