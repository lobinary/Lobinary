package com.lobinary.test.normal;

import java.util.HashMap;
import java.util.Map;

public class 测试map取空置 {

	public static void main(String[] args) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("ll", "li");
		System.out.println(map.get("kong zhi ").toString());
	}

}
