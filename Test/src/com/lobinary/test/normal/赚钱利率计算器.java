package com.lobinary.test.normal;

public class 赚钱利率计算器 {
	
	public static void main(String[] args) {
		
		double 本金 = 2000000;
		double 银行20年后收钱 = 3140000;
		int 贷款年份 = 20;

		double 利息 = 0.03d;
		while(true){
			利息 += 0.0001d;
			double 手里资金 = 本金;
			for (int i = 0; i < 贷款年份*12; i++) {
				手里资金 = 手里资金 - (银行20年后收钱/贷款年份/12) + (手里资金*利息/12);
//				System.out.println(手里资金);
			}
			System.out.println("当前利息为："+利息+"，最后剩余资金为："+手里资金);
			if(手里资金>0){
				System.out.println("当前利率为："+利息);
				break;
			}
		}
		
	}

}

