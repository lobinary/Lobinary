package com.lobinary.设计模式.外观模式;

import com.lobinary.设计模式.外观模式.图形.图形;
import com.lobinary.设计模式.外观模式.图形.实现类.三角形;
import com.lobinary.设计模式.外观模式.图形.实现类.圆形;

public class 外观类 {
	
	private 图形 三角形;
	private 图形 圆形;
	
	public 外观类() {
		super();
		this.三角形 = new 三角形();
		this.圆形 = new 圆形();
	}
	
	public void 画三角形(){
		三角形.画();
	}

	public void 画圆形(){
		圆形.画();
	}

}
