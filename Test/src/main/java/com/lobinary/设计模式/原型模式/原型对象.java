package com.lobinary.设计模式.原型模式;

public class 原型对象 implements Cloneable{
	
	public int x;
	
	public 原型对象子对象 y;

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public Object 深度克隆() throws CloneNotSupportedException {
		原型对象 clone = (原型对象) super.clone();
		clone.y = new 原型对象子对象();
		return clone;
	}
	
	@Override
	public String toString() {
		return "原型对象 [x=" + x + ", y=" + y + "]";
	}

}
