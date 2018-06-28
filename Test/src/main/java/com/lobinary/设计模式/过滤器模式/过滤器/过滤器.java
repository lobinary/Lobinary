package com.lobinary.设计模式.过滤器模式.过滤器;

import java.util.List;

import com.lobinary.设计模式.过滤器模式.图形;

public interface 过滤器 {
	
	public List<图形> 筛选(List<图形> 图形集合);
	
}
