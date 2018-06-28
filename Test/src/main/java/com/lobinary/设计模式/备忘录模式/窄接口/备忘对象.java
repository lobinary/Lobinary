package com.lobinary.设计模式.备忘录模式.窄接口;

/**
 * 将发起人（Originator）对象的内战状态存储起来。 备忘录可以根据发起人对象的判断来决定存储多少发起人（Originator）对象的内部状态。
 */
public class 备忘对象 {

	private String state;

	/**
	 * 工厂方法，返回一个新的备忘录对象
	 * 注意：【此处可以用clone技术】
	 */
	public 普通对象 创建备忘录对象() {
		return new 普通对象(state);
	}

	/**
	 * 将发起人恢复到备忘录对象所记载的状态
	 */
	public void 从备忘对象保存库恢复数据(普通对象 memento) {
		this.state = memento.getState();
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
		System.out.println("当前状态：" + this.state);
	}

}