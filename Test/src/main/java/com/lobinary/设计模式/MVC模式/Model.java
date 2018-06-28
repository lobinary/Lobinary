package com.lobinary.设计模式.MVC模式;

public class Model {
	
	private String name;
	private String sex;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Model(String name, String sex) {
		super();
		this.name = name;
		this.sex = sex;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

}
