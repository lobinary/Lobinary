package com.lobinary.test.normal;

public class 循环跳转 {

	public static void main(String[] args) {
		int j = 3;
		for (int i = 0; i < j; i++) {
			int l = 内层循环(j);
			System.out.println(l);
		}
	}

	private static int 内层循环(int j) {
		for (int k = 0; k < j; k++) {
			for (int k2 = 0; k2 < j; k2++) {
				//寻找到结果准备return
				return 5;
			}
		}
		return 0;
	}
	
}
