package com.lobinary.设计模式.责任链模式.食品加工;

public abstract class 包装 {
	
	public 包装 下一层包装;
	
	public abstract void 包装();

}
