package com.lobinary.设计模式.业务代表模式;

import com.lobinary.设计模式.业务代表模式.业务.业务接口;
import com.lobinary.设计模式.业务代表模式.业务.实现类.业务1;
import com.lobinary.设计模式.业务代表模式.业务.实现类.业务2;

public class 业务选择器 {
	
	public static 业务接口 获取业务类(int 业务类型){
		if(业务类型==1){
			return new 业务1();
		}else{
			return new 业务2();
		}
	}

}
