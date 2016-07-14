package com.boce.test.设计模式.过滤器模式;

public class 图形 {
	
	public String 图形名称;
	public int 边数;
	public String 颜色;

	public 图形(String 图形名称, int 边数, String 颜色) {
		super();
		this.图形名称 = 图形名称;
		this.边数 = 边数;
		this.颜色 = 颜色;
	}

	@Override
	public String toString() {
		return "图形 [图形名称=" + 图形名称 + ", 边数=" + 边数 + ", 颜色=" + 颜色 + "]";
	}
	
}
