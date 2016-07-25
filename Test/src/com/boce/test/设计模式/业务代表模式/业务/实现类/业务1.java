package com.boce.test.设计模式.业务代表模式.业务.实现类;

import com.boce.test.设计模式.业务代表模式.业务.业务接口;

public class 业务1 implements 业务接口 {

	@Override
	public void 调用业务() {
		System.out.println("业务1执行中");
	}

}
