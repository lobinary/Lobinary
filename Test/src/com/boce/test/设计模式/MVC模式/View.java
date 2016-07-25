package com.boce.test.设计模式.MVC模式;

public class View {
	
	public void show(Model model){
		System.out.println("姓名:" + model.getName());
		System.out.println("性别:" + model.getSex());
	}

}
