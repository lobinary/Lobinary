package com.boce.test.设计模式.拦截过滤器模式.filter;

public class TargetJSP {
	
	public void show(String request){
		System.out.println("目标JSP根据"+request+"装配数据完毕，准备展示给用户");
	}

}
