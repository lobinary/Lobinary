package com.lobinary.书籍.effective_java;

import java.util.HashMap;
import java.util.Set;

public class 经典案例 {

	public static void main(String[] args) {

		String s = "a";
		String ss = "a";
		System.out.println(s==ss);
		
		HashMap<String, String> map = new HashMap<String,String>();
		map.put("a", "b");
		Set<String> keySet = map.keySet();
		map.put("a2", "b2");
		Set<String> keySet2 = map.keySet();
		System.out.println(keySet==keySet2);
		System.out.println(keySet.size() + " : " + keySet2.size());
		
		Object[] a = new Object[5];
		a[0] = "Stdfg";
		Object i = a[0];
		System.out.println(i);
		a[0] = null;
		System.out.println(i);
		
	}
	
}
