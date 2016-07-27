package com.lobinary.test.normal;

import java.util.ArrayList;
import java.util.List;

/**
 * ����������֤�����뷽�����е�List�ڷ����������ı�,�Ƿ��ⲿ��������Ÿı�
 * ʵ��֤��,�ı䷽�����д����list,�ⲿ��listҲ����Ÿı�
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
