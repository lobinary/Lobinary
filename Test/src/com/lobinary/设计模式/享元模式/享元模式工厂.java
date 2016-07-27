package com.lobinary.设计模式.享元模式;

import java.util.HashMap;
import java.util.Map;

public class 享元模式工厂 {
	
	private static Map<String,圆形> 圆形集合 = new HashMap<String,圆形>();
	
	public static 圆形 获取圆形(String 颜色){
		圆形 圆形 = 圆形集合.get(颜色);
		if(圆形==null){
			圆形 = new 圆形(颜色);
			圆形集合.put(颜色, 圆形);
		}
		return 圆形;
	}

}
