package com.lobinary.test.normal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//316300
public class 信用卡年度计算器 {

	public static void main(String[] args) {
		int 初始资金 = 23000;//1月1借1w
		int 总还款金额 = 0;

		int 次月还款额 = 计算次月还款额(初始资金);//2月1还* ，重新借*
//		System.out.println("2月1还上月借款：10000元+利息：150，现借款：10150元以还当前本金加利息，总还款金额：10150元");
		次月还款额 = 初始资金;
		for (int i = 1; i < 13; i++) {
			int 临时次月还款额 = 计算次月还款额(次月还款额);
			总还款金额 += 临时次月还款额;
			System.out.println(i+"月1还上月借款："+次月还款额+"元+利息："+(临时次月还款额-次月还款额)+"，现借款："+临时次月还款额+"元以还当前本金加利息，总还款金额："+总还款金额+"元");
			次月还款额 = 临时次月还款额;
		}
	}

	private static int 计算次月还款额(int i) {
		return (i*10150)/10000;
	}
	
}
