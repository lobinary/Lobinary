package com.boce.test;

public class Instanceof用法 {
	
	public static void main(String[] args) {
		
		Teacher t = new Teacher();
		Person p = new Person();
		System.out.println(Teacher.class.isInstance(t));
		System.out.println(Teacher.class==t.getClass());
		System.out.println(Person.class.isInstance(t));
		System.out.println(p.getClass().isInstance(t));
		System.out.println(Teacher.class==t.getClass());
//		System.out.println(Person.class==t.getClass());//报错语句
		if(t instanceof Person){
			System.out.println("t instanceof Person");
		}
	}

}
