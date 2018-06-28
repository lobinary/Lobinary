package com.lobinary.设计模式.空对象模式;

import java.util.HashMap;
import java.util.Map;

import com.lobinary.设计模式.空对象模式.图书.图书;
import com.lobinary.设计模式.空对象模式.图书.实现类.真实图书;
import com.lobinary.设计模式.空对象模式.图书.实现类.空图书;

public class 图书馆 {
	
	private static Map<String,图书> 图书馆仓库 = new HashMap<String,图书>();
	
	static{
		图书馆仓库.put("西游记", new 真实图书("西游记"));
		图书馆仓库.put("吕斌漂流记", new 真实图书("吕斌漂流记"));
	}
	
	public static 图书 获取图书(String 图书名称){
		图书 图书 = 图书馆仓库.get(图书名称);
		if(图书==null){
			图书 = new 空图书();
		}
		return 图书;
	}

}
