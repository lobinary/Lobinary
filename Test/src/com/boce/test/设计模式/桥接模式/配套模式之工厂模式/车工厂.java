package com.boce.test.设计模式.桥接模式.配套模式之工厂模式;

import com.boce.test.设计模式.桥接模式.路.实现类.乡间小路;
import com.boce.test.设计模式.桥接模式.路.实现类.高速公路;
import com.boce.test.设计模式.桥接模式.车.车;
import com.boce.test.设计模式.桥接模式.车.实现类.大卡车;
import com.boce.test.设计模式.桥接模式.车.实现类.小汽车;

public class 车工厂 {
	
	public enum 种类{
		小汽车乡间小路,小汽车高速公路,大卡车乡间小路,大卡车高速公路,火车高速公路
	}

	
	public static 车 获取车(种类 type) throws Exception{
		if(种类.小汽车乡间小路==type){
			小汽车 小汽车 = new 小汽车();
			小汽车.道路 = new 乡间小路();
			return 小汽车;
		}else if(种类.小汽车高速公路==type){
			小汽车 小汽车 = new 小汽车();
			小汽车.道路 = new 高速公路();
			return 小汽车;
		}else if(种类.大卡车乡间小路==type){
			大卡车 大卡车 = new 大卡车();
			大卡车.道路 = new 乡间小路();
			return 大卡车;
		}else if(种类.大卡车高速公路==type){
			大卡车 大卡车 = new 大卡车();
			大卡车.道路 = new 高速公路();
			return 大卡车;
		}
		throw new Exception("不支持的车类型");
	}

}
