package com.lobinary.test;

import java.util.HashSet;
import java.util.Set;

public class 随机数重复概率 {
	
	public static void main(String[] args) {
		for (int j = 0; j < 100; j++) {
			Set<Integer> s = new HashSet<Integer>();
			for (int i = 0; i < 100000; i++) {
				double r = Math.random()*10000000;
				s.add((int)r);
			}
			System.out.println(s.size());
		}
	}

}
