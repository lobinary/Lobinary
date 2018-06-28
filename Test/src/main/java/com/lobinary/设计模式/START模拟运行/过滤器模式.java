package com.lobinary.设计模式.START模拟运行;

import java.util.ArrayList;
import java.util.List;

import com.lobinary.设计模式.过滤器模式.图形;
import com.lobinary.设计模式.过滤器模式.过滤器.实现类.And过滤器;
import com.lobinary.设计模式.过滤器模式.过滤器.实现类.OR过滤器;
import com.lobinary.设计模式.过滤器模式.过滤器.实现类.四边形过滤器;
import com.lobinary.设计模式.过滤器模式.过滤器.实现类.红色过滤器;
import com.lobinary.设计模式.过滤器模式.过滤器.实现类.蓝色过滤器;

/**
 * 	过滤器模式
		过滤器模式（Filter Pattern）或标准模式（Criteria Pattern）是一种设计模式，
		这种模式允许开发人员使用不同的标准来过滤一组对象，通过逻辑运算以解耦的方式把它们连接起来。这种类型的设计模式属于结构型模式，它结合多个标准来获得单一标准。
	实现
		我们将创建一个 Person 对象、Criteria 接口和实现了该接口的实体类，来过滤 Person 对象的列表。
		CriteriaPatternDemo，我们的演示类使用 Criteria 对象，基于各种标准和它们的结合来过滤 Person 对象的列表。
 */
public class 过滤器模式 {
	
	public static void main(String[] args) {
		List<图形> 图形集合 = new ArrayList<图形>();

		图形集合.add(new 图形("红色三角形", 3, "红色"));
		图形集合.add(new 图形("蓝色三角形", 3, "蓝色"));
		图形集合.add(new 图形("粉色三角形", 3, "粉色"));

		图形集合.add(new 图形("红色四边形", 4, "红色"));
		图形集合.add(new 图形("蓝色四边形", 4, "蓝色"));
		图形集合.add(new 图形("粉色四边形", 4, "粉色"));

		图形集合.add(new 图形("红色六边形", 6, "红色"));
		图形集合.add(new 图形("蓝色六边形", 6, "蓝色"));
		图形集合.add(new 图形("粉色六边形", 6, "粉色"));

		红色过滤器 红色过滤器 = new 红色过滤器();
		蓝色过滤器 蓝色过滤器 = new 蓝色过滤器();
		
		四边形过滤器 四边形过滤器 = new 四边形过滤器();
		
		And过滤器 红And四边形过滤器 = new And过滤器(红色过滤器,四边形过滤器);
		OR过滤器 红OR蓝过滤器 = new OR过滤器(红色过滤器,蓝色过滤器);

		print("红色过滤器筛选结果",红色过滤器.筛选(图形集合));
		print("红And四边形过滤器筛选结果",红And四边形过滤器.筛选(图形集合));
		print("红OR蓝过滤器筛选结果",红OR蓝过滤器.筛选(图形集合));
		
	}

	private static void print(String 标题,List<图形> 筛选) {
		System.out.println(标题);
		for (图形 图形 : 筛选) {
			System.out.println(图形);
		}
		System.out.println("========================================================");
	}

}
