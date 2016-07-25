package com.boce.test.设计模式.备忘录模式.窄接口;

public class 普通对象 {

	private String state;

	public 普通对象(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}