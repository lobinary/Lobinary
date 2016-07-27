package com.lobinary.设计模式.责任链模式.日志输出器;

public abstract class 日志输出器 {
	
	public int 日志级别;
	
	public static int DEBUG = 1;
	public static int INFO = 2;
	public static int ERROR = 3;
	
	public 日志输出器(int 日志级别) {
		super();
		this.日志级别 = 日志级别;
	}

	private 日志输出器 下一个日志输出器;

	public void 设置下一个日志输出器(日志输出器 下一个日志输出器) {
		this.下一个日志输出器 = 下一个日志输出器;
	}
	
	public void 输出日志(int 日志级别,String 日志内容){
		if(this.日志级别<=日志级别){
			打印日志(日志内容);
		}
		if(下一个日志输出器!=null)
		下一个日志输出器.输出日志(日志级别, 日志内容);
	}
	
	public abstract void 打印日志(String 日志内容);
	
}
