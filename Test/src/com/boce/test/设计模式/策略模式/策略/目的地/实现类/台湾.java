package com.boce.test.设计模式.策略模式.策略.目的地.实现类;

import com.boce.test.设计模式.策略模式.策略.目的地.目的地策略;

public class 台湾 implements 目的地策略 {

	@Override
	public void 目的地() {
		System.out.println("到达台湾，和老婆开心玩7天环岛游");
	}

}
