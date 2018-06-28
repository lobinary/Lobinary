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

/**
 * http://poj.org/problem?id=1005
 * @author Lobinary
 *
 */
public class Q_1005_点和圆关系问题_IThinkINeedaHouseboat {
	
	public static void main(String[] args) {
		AU.check();
//		Scanner cin = new Scanner(System.in);
//		run(cin);
	}
	
	public static void run(Scanner cin){
		int num = cin.nextInt();
		List<Double> 输入List = new ArrayList<Double>();
		for (int i = 0; i < num; i++) {
			double x = cin.nextDouble();
			double y = cin.nextDouble();
			double r = Math.sqrt((x*x+y*y));
			double s = Math.PI*r*r;
			BigDecimal l = new BigDecimal((s/50/2));
			l.setScale(10, RoundingMode.UP);
			System.out.println("Property "+(i+1)+": This property will begin eroding in year "+(l.intValue()+1)+".");
		}
		System.out.println("END OF OUTPUT.");
	}
	
	public static void oldrun(Scanner cin){
		int num = cin.nextInt();
		List<Double> 输入List = new ArrayList<Double>();
		for (int i = 0; i < num; i++) {
			double x = cin.nextDouble();
			double y = cin.nextDouble();
			double r = Math.sqrt((x*x+y*y));
			输入List.add(r);
		}

		int 排序后数据指针 = 0;
		int 年份 = 0;
		Map<Double,Integer> map = new HashMap<Double,Integer>();
		List<Double> 排序后的输入List = new ArrayList<Double>(输入List);
		Collections.sort(排序后的输入List);
		while(排序后数据指针<排序后的输入List.size()){
			年份++;
			// s = πr² , r=根号[s/π]
			double 当前半径 = Math.sqrt((年份*(50*2))/Math.PI);//*2是因为是半圆
			for(;排序后数据指针<排序后的输入List.size();排序后数据指针++){
				double 坐标点半径 = 排序后的输入List.get(排序后数据指针);
				if(坐标点半径<当前半径){
					map.put(坐标点半径, 年份);
				}else{
					break;
				}
			}
		}
		int i=0;
		for (double d :输入List) {
			i++;
			System.out.println("Property "+i+": This property will begin eroding in year "+map.get(d)+".");
		}
		System.out.println("END OF OUTPUT.");
	}

}
