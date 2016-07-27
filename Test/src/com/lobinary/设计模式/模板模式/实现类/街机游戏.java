package com.lobinary.设计模式.模板模式.实现类;

import com.lobinary.设计模式.模板模式.游戏模板;

public class 街机游戏 extends 游戏模板{

	@Override
	public void 加载游戏() {
		System.out.println("进入游戏厅");
	}

	@Override
	public void 开始游戏() {
		System.out.println("投入游戏币,开始玩游戏");
	}

	@Override
	public void 结束游戏() {
		System.out.println("离开游戏厅");
	}

}
