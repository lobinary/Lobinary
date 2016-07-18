package com.boce.test.设计模式.责任链模式.日志输出器.实现类;

import com.boce.test.设计模式.责任链模式.日志输出器.日志输出器;

public class Console日志输出器  extends 日志输出器{

	public Console日志输出器(int 日志级别) {
		super(日志级别);
	}

	@Override
	public void 打印日志(String 日志内容) {
		System.out.println("Console日志打印:"+日志内容);
	}

}
