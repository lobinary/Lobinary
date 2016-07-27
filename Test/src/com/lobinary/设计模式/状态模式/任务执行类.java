package com.lobinary.设计模式.状态模式;

import com.lobinary.设计模式.状态模式.状态.任务状态;
import com.lobinary.设计模式.状态模式.状态.实现类.车停止;
import com.lobinary.设计模式.状态模式.状态.实现类.车点火;

public class 任务执行类 {
	
	private int 任务步骤 = 0;
	
	任务状态 任务状态;
	
	public void 执行一步任务(任务参数 任务参数){
		if(任务步骤==0){
			任务状态 = new 车停止();
		}else if(任务步骤==1){
			任务状态 = new 车点火();
		}
		任务状态.当前状态执行的任务(任务参数);
		任务步骤++;
	}

}
