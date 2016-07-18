package com.boce.test.normal;

import java.util.HashMap;
import java.util.Map;

public class 获取Map的空key返回结果 {

	public static void main(String[] args) {
		Map<String,String> map = new HashMap<String,String>();
		System.out.println(map.get("a"));
	}
	
}
