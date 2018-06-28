package com.lobinary.test;

public class 工资计算器 {
	
	public static void main(String[] args) {
		int sum = 0;
		int 工资 = 6000;
		for (int i = 23*12; i < 1200; i++) {
			if(i%12==0)工资+=333.333;
			sum += 工资;
		}
		System.out.println(sum);
		
		
		int sum2 = 0;
		int 工资2 = 7000;
		for (int i = 26*12; i < 1200; i++) {
			if(i%12==0)工资2+=333.333;
			sum2 += 工资2;
		}
		System.out.println(sum2);
	}

}
