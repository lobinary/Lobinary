package com.lobinary.设计模式.模板模式;

public abstract class 游戏模板 {
	
	public final void 玩游戏(){
		加载游戏();
		开始游戏();
		结束游戏();
	}

	public abstract void 加载游戏();

	public abstract void 开始游戏();

	public abstract void 结束游戏();
	
	

}
