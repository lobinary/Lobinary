package com.lobinary.算法.acm;

import java.util.Scanner;

public class Main {
	

	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		run(cin);
	}
	


	public static void run(Scanner cin){
		int caseNum = 0;
		while(cin.hasNext()){
			caseNum++;
			//它们的周期长度为23天、28天和33天
			int p = cin.nextInt()%23;//23
			int e = cin.nextInt()%28;//28
			int i = cin.nextInt()%33;//33
			int d = cin.nextInt();
			if(p==-1&&e==-1&&i==-1&&d==-1){
				break;
			}
			int m = -1;
			while(true){
				m++;
//				int ti = (i+33*m)%365;
				int p高潮日期 = i+33*m;
				boolean tp = (p高潮日期-p)%23==0;
				boolean te = (p高潮日期-e)%28==0;
				int 当前日期 = p高潮日期-d;
				if(tp&&te&&当前日期>d){
					System.out.println("Case "+caseNum+": the next triple peak occurs in "+当前日期+" days.");
					break;
				}
			}
		}
		
	}
}
