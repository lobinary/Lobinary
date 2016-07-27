package com.lobinary.设计模式.责任链模式.日志输出器.实现类;

import com.lobinary.设计模式.责任链模式.日志输出器.日志输出器;

public class 文件日志输出器  extends 日志输出器{

	public 文件日志输出器(int 日志级别) {
		super(日志级别);
	}

	@Override
	public void 打印日志(String 日志内容) {
		System.out.println("文件日志打印:"+日志内容);
	}

}
