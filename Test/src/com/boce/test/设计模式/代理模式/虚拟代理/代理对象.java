package com.boce.test.设计模式.代理模式.虚拟代理;

import com.boce.test.设计模式.代理模式.虚拟代理.图形.图形;
import com.boce.test.设计模式.代理模式.虚拟代理.图形.实现类.圆形;

/**
 * 该代理模式实现延迟加载
 */
public class 代理对象 implements 图形{
	
	private 图形 图形;

	public 代理对象() {
		super();
		System.out.println("代理对象初始化完毕");
	}

	@Override
	public void 画() {
		if(图形==null){
			图形 = new 圆形();
		}
		图形.画();
	}

}
