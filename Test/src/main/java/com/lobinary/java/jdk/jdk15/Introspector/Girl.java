package com.lobinary.java.jdk.jdk15.Introspector;

import java.util.ArrayList;
import java.util.List;


public class Girl {
	
	private String name;
	private int age;
	private boolean fat;
	private List<String> friends = new ArrayList<String>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public void addFriend(String friendName){
		friends.add(friendName);
	}
	
	public void removeFriend(String friendName){
		friends.remove(friendName);
	}
	public boolean isFat() {
		return fat;
	}
	public void setFat(boolean fat) {
		this.fat = fat;
	}

}
