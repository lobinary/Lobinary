package com.lobinary.test;

import java.util.regex.Pattern;


public class Test {
	
	public static void main(String[] args) {
		String s = "测试19线上-预付费卡(电商)";
		String r = "^[a-zA-Z0-9_()——\\u4e00-\\u9fa5\\-?\\[?\\]]+$";
		System.out.println(s.matches(r));
	}

}
