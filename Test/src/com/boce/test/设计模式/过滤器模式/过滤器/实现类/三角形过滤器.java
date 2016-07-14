package com.boce.test.设计模式.过滤器模式.过滤器.实现类;

import java.util.ArrayList;
import java.util.List;

import com.boce.test.设计模式.过滤器模式.图形;
import com.boce.test.设计模式.过滤器模式.过滤器.过滤器;

public class 三角形过滤器  implements 过滤器 {
	
	public List<图形> 筛选(List<图形> 图形集合){
	     List<图形> 图形集合结果集 = new ArrayList<图形>(); 
	     for (图形 图形 : 图形集合) {
			if(图形.边数==3){
				图形集合结果集.add(图形);
			}
		}
		return 图形集合结果集;
	}

}
