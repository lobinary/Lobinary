package com.boce.test.设计模式.业务代表模式;

import com.boce.test.设计模式.业务代表模式.业务.业务接口;

public class 业务代表 {
	
	private int 业务类型;
	
	public 业务代表(int 业务类型) {
		super();
		this.业务类型 = 业务类型;
	}

	public void 执行业务(){
		业务接口 业务类 = 业务选择器.获取业务类(业务类型);
		业务类.调用业务();
	}

}
