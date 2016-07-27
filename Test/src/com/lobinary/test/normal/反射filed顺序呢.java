package com.lobinary.test.normal;

import java.lang.reflect.Field;

public class 反射filed顺序呢 {
	
	public static void main(String[] args) {
		
		Field[] fields = User.class.getDeclaredFields();
		for(Field f : fields){
			System.out.println(f.getName());
		}
		
	}

}
