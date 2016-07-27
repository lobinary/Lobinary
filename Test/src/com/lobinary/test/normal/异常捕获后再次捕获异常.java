package com.lobinary.test.normal;

public class 异常捕获后再次捕获异常 {
	
	public static void main(String[] args) {
		System.out.println(test());
	}
	
	public static String test(){
		String s = "a";
		try {
			throwExc();
		} catch (Exception e) {
			try {
				s = "b";
				throwExc();
			} catch (Exception e1) {
				s = "c";
				return s;
			}
		}
		s = "d";
		return s;
	}
	
	private static String throwExc() throws Exception{
		throw new Exception("抛出异常");
	}

}


