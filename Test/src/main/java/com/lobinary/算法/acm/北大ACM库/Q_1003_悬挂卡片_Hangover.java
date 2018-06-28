package com.lobinary.算法.acm.北大ACM库;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.lobinary.算法.acm.AU;

public class Q_1003_悬挂卡片_Hangover {
	
	public static void main(String[] args) {
		AU.check();
	}
	public static void run(Scanner cin){
		String s = cin.nextLine();
		List<Double> 原始数据 = new ArrayList<Double>();
		while(!"0.00".equals(s)){
			double d = Double.parseDouble(s);
			原始数据.add(d);
			s = cin.nextLine();
		}
		Map<Double,Double> 结果数据  = new HashMap<Double,Double>();
		List<Double> 顺序数据 = new ArrayList<Double>(原始数据);
		Collections.sort(顺序数据);
//		System.out.println(dc.get(dc.size()-1));
//		System.out.println(dc.get(0));
		
		double 当前长度 = 0;
		double 卡片数量 = 0;
		int 当前判断到顺序数据的第几个数据 = 0;
		while(当前判断到顺序数据的第几个数据!=顺序数据.size()){
			卡片数量++;
			double sss = 1/(卡片数量+1);
			当前长度 += sss;
//			System.out.println(sss);
//			System.out.println(当前长度);
			for (; 当前判断到顺序数据的第几个数据 < 顺序数据.size(); 当前判断到顺序数据的第几个数据++) {
				if(当前长度>顺序数据.get(当前判断到顺序数据的第几个数据)){
//					System.out.println(顺序数据.get(当前判断到顺序数据的第几个数据)+"#"+ 卡片数量);
					结果数据.put(顺序数据.get(当前判断到顺序数据的第几个数据), 卡片数量);
					continue;
				}else{
					break;
				}
			}
		}
		for (Double d : 原始数据) {
			System.out.println(new Double(结果数据.get(d)).intValue()+" card(s)");
		}
	}
	
	/**
	 * bigdecimal解决方案
	 * @param cin
	 */
	public static void brun(Scanner cin){
		String s = cin.nextLine();
		List<Double> 原始数据 = new ArrayList<Double>();
		while(!"0.00".equals(s)){
			double d = Double.parseDouble(s);
			原始数据.add(d);
			s = cin.nextLine();
		}
		Map<Double,Integer> 结果数据  = new HashMap<Double,Integer>();
		List<Double> 顺序数据 = new ArrayList<Double>(原始数据);
		Collections.sort(顺序数据);
//		System.out.println(dc.get(dc.size()-1));
//		System.out.println(dc.get(0));
		
		BigDecimal 当前长度 = new BigDecimal(0);
		当前长度.setScale(20, RoundingMode.CEILING);
		int 卡片数量 = 0;
		int 当前判断到顺序数据的第几个数据 = 0;
		while(当前判断到顺序数据的第几个数据!=顺序数据.size()){
			卡片数量++;
			BigDecimal sss = new BigDecimal(1).divide(new BigDecimal(卡片数量+1), 20, RoundingMode.CEILING);
			sss.setScale(20, RoundingMode.CEILING);
			当前长度 = 当前长度.add(sss);
			for (; 当前判断到顺序数据的第几个数据 < 顺序数据.size(); 当前判断到顺序数据的第几个数据++) {
				if(当前长度.doubleValue()>顺序数据.get(当前判断到顺序数据的第几个数据)){
//					System.out.println(顺序数据.get(当前判断到顺序数据的第几个数据)+"#"+ 卡片数量);
					结果数据.put(顺序数据.get(当前判断到顺序数据的第几个数据), 卡片数量);
					continue;
				}else{
					break;
				}
			}
		}
		for (Double d : 原始数据) {
			System.out.println(结果数据.get(d)+" card(s)");
		}
	}

	

}
