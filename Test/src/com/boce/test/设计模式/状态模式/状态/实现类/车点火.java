package com.boce.test.设计模式.状态模式.状态.实现类;

import com.boce.test.设计模式.状态模式.任务参数;
import com.boce.test.设计模式.状态模式.状态.任务状态;

public class 车点火 implements 任务状态{

	@Override
	public void 当前状态执行的任务(任务参数 任务参数) {
		System.out.println("车已点火，踩离合，挂当中......");
		
	}

}
