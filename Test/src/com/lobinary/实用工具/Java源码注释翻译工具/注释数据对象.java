package com.lobinary.实用工具.Java源码注释翻译工具;

public class 注释数据对象 {

	public 注释数据对象(int 本行属性,String 本行内容) {
		super();
		this.本行属性 = 本行属性;
		this.本行内容 = 本行内容;
	}
	public final static int 标签 = 1;
	public final static int 文本 = 2;
	public final static int 空行 = 3;
	
	private String 本行内容;
	private int 本行属性;//标签,文字,空行
	
	public String get本行内容() {
		return 本行内容;
	}
	public void set本行内容(String 本行内容) {
		this.本行内容 = 本行内容;
	}
	public int get本行属性() {
		return 本行属性;
	}
	public void set本行属性(int 本行属性) {
		this.本行属性 = 本行属性;
	}
	@Override
	public String toString() {
		return "注释数据对象 [本行内容=" + 本行内容 + ", 本行属性=" + 本行属性 + "]";
	}

}
