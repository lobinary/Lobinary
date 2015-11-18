package com.boce.test;

import java.util.ArrayList;
import java.util.List;

/**
 * 本测试用于证明传入方法体中的List在方法体中如果被改变,是否外部参数会随着改变
 * 实验证明,改变方法体中传入的list,外部的list也会跟着改变
 * @author Boce
 *
 */
public class ListTest {

	public static void main(String[] args) {
		ListTest lt = new ListTest();
		List<User> userList = new ArrayList<User>();
		User u = new User();
		u.setName("boce");
		u.setAge(8);
		userList.add(u);
		System.out.println(userList.get(0).getName());
		List<User> userListed = lt.changeList(userList);
		System.out.println(userList.get(0).getName());
		System.out.println(userListed.get(0).getName());
	}
	
	public List<User> changeList(List<User> list){
		list.get(0).setName("changed");
		return list;
	}
	

	
}
