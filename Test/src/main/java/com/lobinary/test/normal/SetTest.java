package com.lobinary.test.normal;

import java.util.HashSet;
import java.util.Set;

public class SetTest {

	public static void main(String[] args) {
		
		Set<Long> set = new HashSet<Long>();
		set.add(1l);System.out.println("add1");
		set.add(2l);System.out.println("add2");
		set.add(5l);System.out.println("add5");
		set.add(2l);System.out.println("add2");
		set.add(1l);System.out.println("add1");
		
		for(long l : set){
			System.out.println(l);
		}

	}

}
