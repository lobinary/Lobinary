package com.lobinary.设计模式.过滤器模式.过滤器.实现类;

import java.util.List;

import com.lobinary.设计模式.过滤器模式.图形;
import com.lobinary.设计模式.过滤器模式.过滤器.过滤器;

public class And过滤器 implements 过滤器{
	
	过滤器 过滤器1;
	过滤器 过滤器2;

	public And过滤器(过滤器 过滤器1, 过滤器 过滤器2) {
		super();
		this.过滤器1 = 过滤器1;
		this.过滤器2 = 过滤器2;
	}

	@Override
	public List<图形> 筛选(List<图形> 图形集合) {
		List<图形> 筛选 = 过滤器1.筛选(图形集合);
		List<图形> 筛选2 = 过滤器2.筛选(筛选);
		return 筛选2;
	}
	
}
