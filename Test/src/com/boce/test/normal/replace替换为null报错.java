package com.boce.test.normal;

public class replace替换为null报错 {
	
	public static void main(String[] args) {
		String s = "ab${c}de${c}f";
		String c = s.replace("${c}", "-");
		System.out.println(c);
		s = "08:35:00";
		System.out.println(s.replace(":", ""));
	}

}
