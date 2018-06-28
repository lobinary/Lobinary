package com.lobinary.设计模式.观察者模式.观察者;

import com.lobinary.设计模式.观察者模式.变动类;

public abstract class 观察者 {
	
	protected 变动类 变动类;

	public 观察者(变动类 变动类) {
		super();
		this.变动类 = 变动类;
		this.变动类.添加观察者(this);
	}

	public abstract void 下达通知(int 变动属性);
	
}
