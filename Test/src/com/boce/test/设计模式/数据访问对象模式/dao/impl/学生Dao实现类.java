package com.boce.test.设计模式.数据访问对象模式.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.boce.test.设计模式.数据访问对象模式.学生;
import com.boce.test.设计模式.数据访问对象模式.dao.学生Dao;

public class 学生Dao实现类 implements 学生Dao {
	
	Map<String,学生> 学生数据库 = new HashMap<String,学生>();

	@Override
	public boolean 插入学生(学生 学生) {
		if(学生数据库.containsKey(学生.getName())){
			System.out.println("插入"+学生.getSex()+"学生"+学生.getName()+"失败,已经有其他人插入了该学生");
			return false;
		}
		学生数据库.put(学生.getName(), 学生);
		System.out.println("插入"+学生.getSex()+"学生"+学生.getName()+"成功，现在别人已经不能插入该学生了");
		return true;
	}

	@Override
	public boolean 更新学生(学生 学生) {

		if(学生数据库.containsKey(学生.getName())){
			学生数据库.put(学生.getName(), 学生);
			System.out.println("更新"+学生.getSex()+"学生"+学生.getName()+"成功");
			return true;
		}
		System.out.println("更新"+学生.getSex()+"学生"+学生.getName()+"失败，还没有人插该学生");
		return false;
	}

}
