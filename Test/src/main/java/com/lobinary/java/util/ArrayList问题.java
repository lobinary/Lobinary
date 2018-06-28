package com.lobinary.java.util;

import java.util.ArrayList;
import java.util.List;

public class ArrayList问题 {
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("00");
		list.set(0, "00");
		list.set(2, "22");//如果没有该位置会报错
			
		System.out.println(list.get(0));
	}

}
