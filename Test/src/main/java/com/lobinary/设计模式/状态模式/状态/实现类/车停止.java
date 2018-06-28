package com.lobinary.设计模式.状态模式.状态.实现类;

import com.lobinary.设计模式.状态模式.任务参数;
import com.lobinary.设计模式.状态模式.状态.任务状态;

public class 车停止 implements 任务状态{

	@Override
	public void 当前状态执行的任务(任务参数 任务参数) {
		System.out.println("车停止中.准备点火");
		
	}

}
