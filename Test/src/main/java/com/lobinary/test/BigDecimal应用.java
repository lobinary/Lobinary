package com.lobinary.test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimal应用 {
	
	public static void main(String[] args) {
		BigDecimal b = new BigDecimal(1);
		b.setScale(20, RoundingMode.CEILING);//bigdecimal 计算的时候，精度设置非常重要，如果需要计算准确的话，那么需要将每一个bigdecimal的数据都加上精度
		System.out.println(b.divide(new BigDecimal(3), 10, RoundingMode.CEILING));
		
		double a = 1;
		double c = 3;
		System.out.println(a/c);
	}

}
