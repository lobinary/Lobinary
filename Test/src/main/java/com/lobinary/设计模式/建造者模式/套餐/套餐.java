package com.lobinary.设计模式.建造者模式.套餐;

import java.util.ArrayList;
import java.util.List;

import com.lobinary.设计模式.建造者模式.食品.食品;

public class 套餐 {
	
	public String 套餐名称;
	
	public List<食品> 食品列表 = new ArrayList<食品>();
	
	public void 添加食品(食品 食品){
		食品列表.add(食品);
	}
	
	public int 获取套餐价格(){
		int 总价格 = 0;
		for (食品 食品 : 食品列表) {
			总价格 += 食品.获取价格();
		}
		return 总价格;
	}
	
	public void 输出套餐信息(){
		System.out.println("套餐名称:"+套餐名称);
		for (食品 食品 : 食品列表) {
			System.out.println("==========================================");
			System.out.println("\t食品名称:"+食品.获取名称());
			System.out.println("\t食品价格:"+食品.获取价格());
			System.out.println("\t食品包装:"+食品.获取包装().获取包装材料());
		}
		
	}

}
