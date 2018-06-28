package com.lobinary.算法.线性表;

public interface 线性表<T> {

	public void 增加(int i, T t);
	
	public void 删除(int i);
	
	public void 更改(int i,T t);
	
	public T 查找(int i);
	
}
