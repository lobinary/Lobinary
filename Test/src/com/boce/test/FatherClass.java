package com.boce.test;

public abstract class FatherClass implements SuperInteface{
	
	public String fatherName = "father";

	@Override
	public String getName() {
		return fatherName;
	}

}
