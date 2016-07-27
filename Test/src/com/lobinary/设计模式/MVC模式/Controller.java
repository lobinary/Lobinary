package com.lobinary.设计模式.MVC模式;

public class Controller {
	
	private Model model;
	private View view;
	
	public Controller(Model model, View view) {
		super();
		this.model = model;
		this.view = view;
	}
	
	public void 接收到请求(){
		view.show(model);
	}

}
