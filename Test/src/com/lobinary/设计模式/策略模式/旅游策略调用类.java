package com.lobinary.设计模式.策略模式;

import com.lobinary.设计模式.策略模式.策略.交通工具.交通工具策略;
import com.lobinary.设计模式.策略模式.策略.目的地.目的地策略;

public class 旅游策略调用类 {
	
	private 交通工具策略 交通工具;
	private 目的地策略 目的地;
	
	public 旅游策略调用类(交通工具策略 交通工具, 目的地策略 目的地) {
		super();
		this.交通工具 = 交通工具;
		this.目的地 = 目的地;
	}
	
	public void 开始玩(){
		交通工具.出发();
		System.out.print(",");
		目的地.目的地();
	}
	

}
