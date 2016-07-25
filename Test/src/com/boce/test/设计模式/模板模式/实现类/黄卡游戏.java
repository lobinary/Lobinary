package com.boce.test.设计模式.模板模式.实现类;

import com.boce.test.设计模式.模板模式.游戏模板;

public class 黄卡游戏 extends 游戏模板{

	@Override
	public void 加载游戏() {
		System.out.println("打开黄卡游戏机,插入黄卡");
	}

	@Override
	public void 开始游戏() {
		System.out.println("拿起手柄玩游戏");
	}

	@Override
	public void 结束游戏() {
		System.out.println("拔掉电源,关闭游戏");
	}

}
