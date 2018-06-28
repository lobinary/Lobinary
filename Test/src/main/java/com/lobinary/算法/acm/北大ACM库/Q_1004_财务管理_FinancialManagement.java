package com.lobinary.算法.acm.北大ACM库;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;

import com.lobinary.算法.acm.AU;

/**
 * 本程序知识点在求平均数和四舍五入和数字格式化的技术
 * 格式化使用DecimalFormat
 * @author Lobinary
 *
 */
public class Q_1004_财务管理_FinancialManagement {
	
	public static void main(String[] args) {
		AU.check();
//		Scanner cin = new Scanner(System.in);
//		run(cin);
	}

	public static void run(Scanner cin){
		float sum = 0;
		while (cin.hasNext()) {
			float p = cin.nextFloat();
			sum += p;
		}
		DecimalFormat df = new DecimalFormat(".00"); 
		System.out.println("$"+df.format(sum/12));
	}
	
	public static void brun(Scanner cin){
		BigDecimal sum = new BigDecimal(0);
		sum.setScale(2, RoundingMode.DOWN);
		while (cin.hasNext()) {
			BigDecimal p = new BigDecimal(cin.nextDouble());
			p.setScale(2, RoundingMode.DOWN);
			sum = sum.add(p);
		}
		BigDecimal tt = new BigDecimal(12);
		tt.setScale(2, RoundingMode.DOWN);
		BigDecimal r = sum.divide(tt);
		r.setScale(2, RoundingMode.DOWN);
		System.out.println("$"+r.doubleValue());
	}



}
