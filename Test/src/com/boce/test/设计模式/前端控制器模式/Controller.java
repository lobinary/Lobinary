package com.boce.test.设计模式.前端控制器模式;

public class Controller {
	
	Dispatcher dispatcher = new Dispatcher();

	public void 记录访问记录(String requestURL){
		System.out.println("访问网址为:"+requestURL);
	}
	
	public void request(String requestURL){
		记录访问记录(requestURL);
		dispatcher.dispatcher(requestURL);
	}
	
}
