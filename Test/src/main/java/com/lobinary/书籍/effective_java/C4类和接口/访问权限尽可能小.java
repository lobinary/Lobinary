package com.lobinary.书籍.effective_java.C4类和接口;

import java.awt.Dimension;

public class 访问权限尽可能小 {
	
	private final static String[] array;//final可以更改内部数据
	
	static{
		array = new String[5];//final可以更改内部数据
//		array = new String[5];//但是不能重新分配给其他对象或初始化对象
	}
	
	public static void main(String[] args) {
		
		array[0] = "a";
		for (String s : array) {
			System.out.println(s);
		}
//		Dimension d = null;//将域设为公有的反面教材，影响了性能
	}
	
	//当类中的变量为final的引用型的对象时，需要进行clone，
	//否则，如果把引用给类之外的其他类进行操作，那么这个final类就会被更改
	//这样就算是接口的设计的bug，这个final就是无效的，会引起很大的安全隐患
	public static String[] getArray(){
		
		return array.clone();
		
	}


}
