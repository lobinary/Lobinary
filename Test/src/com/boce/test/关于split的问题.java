package com.boce.test;

public class 关于split的问题 {
	
	public static void main(String[] args) {
		
		String[] ss = "1.2.3".split("\\.");
		System.out.println(ss.length);
		
//		String s = "0||11|||";
//		String[] sa = s.split("\\|");
//		System.out.print("start-");
//		int index = 0;
//		for(String ss : sa){
//			System.out.print("_><_" + index + "_" + ss);
//			index ++ ;
//		}
//		System.out.println("");
//		for(String ss : sa){
//			System.out.print(ss);
//		}
//		System.out.println("-end");
	}
	

}
