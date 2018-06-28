package com.lobinary.设计模式.访问者模式;


public interface 电脑使用者接口 {
	public void 使用电脑(电脑实现类 computer);
	public void 使用鼠标(鼠标 mouse);
	public void 使用键盘(键盘 keyboard);
}