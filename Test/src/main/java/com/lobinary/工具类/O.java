package com.lobinary.工具类;

import java.awt.Color;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class O {
	
	private static String 上方名称;
	
	public static void f(String 名称){
		if(上方名称==null){
			System.out.println("====================================="+名称+"==================================================================");
		}else if(名称==null||上方名称.equals(名称)){
			System.out.println("====================================="+上方名称+"==================================================================");
		}else{
			System.out.println("====================================="+上方名称+"==================================================================");
			System.out.println();System.out.println();
			System.out.println("====================================="+名称+"==================================================================");
		}
		上方名称 = 名称;
	}

	public static void show(String ss, Object o) {
		System.out.println(ss + "\t ===> \t "+ o);
	}

	public static void o(Object[] list,String title){
		System.out.println("******************************************"+title+"************************************************");
		for (Object o : list) {
			System.out.println(o);
		}
		System.out.println("******************************************"+title+"************************************************");
	}
	public static void o(Collection<?> list,String title){
		System.out.println("******************************************"+title+"************************************************");
		for (Object o : list) {
			System.out.println(o);
		}
		System.out.println("******************************************"+title+"************************************************");
	}
	
	public static void o(Map<?,?> map,String title){
		System.out.println("******************************************"+title+"************************************************");
		for (Object k : map.keySet()) {
			System.out.println(""+k+":"+map.get(k));
		}
		System.out.println("******************************************"+title+"************************************************");
	}
	
	

}
