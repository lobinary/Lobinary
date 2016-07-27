package com.lobinary.设计模式.桥接模式.车;

import com.lobinary.设计模式.桥接模式.路.路;

public abstract class 车 {

	public 路 道路;
	
	public void 跑(){
		run();
		after();
	}
	
	public void run(){
		
	}
	
	public void after(){
		道路.run();
	}
}
