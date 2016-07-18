package com.boce.test.normal;

public class 异常 {
	
	public static void main(String[] args) {
		try {
			Object o = null;
			System.out.println(o.toString());
		} catch (Exception e) {
			System.out.println(e.getCause());
			System.out.println(e);
			System.out.println(e.getLocalizedMessage());
			System.out.println(e.getCause());
		}
	}

}
