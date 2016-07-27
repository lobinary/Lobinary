package com.lobinary.设计模式.过滤器模式.过滤器.实现类;

import java.util.List;

import com.lobinary.设计模式.过滤器模式.图形;
import com.lobinary.设计模式.过滤器模式.过滤器.过滤器;

public class OR过滤器 implements 过滤器{
	
	过滤器 过滤器1;
	过滤器 过滤器2;

	public OR过滤器(过滤器 过滤器1, 过滤器 过滤器2) {
		super();
		this.过滤器1 = 过滤器1;
		this.过滤器2 = 过滤器2;
	}

	@Override
	public List<图形> 筛选(List<图形> 图形集合) {
		List<图形> 筛选 = 过滤器1.筛选(图形集合);
		List<图形> 筛选2 = 过滤器2.筛选(图形集合);
		for (图形 图形 : 筛选) {
			if(!筛选2.contains(图形)){
				筛选2.add(图形);
			}
		}
		return 筛选2;
	}
	
}
