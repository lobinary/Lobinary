package com.lobinary.算法.acm.北大ACM库;

import java.math.BigDecimal;
import java.util.Scanner;

import org.junit.Test;

import com.lobinary.算法.acm.AU;

/**
 * 
 * http://poj.org/problem?id=1001&lang=zh-CN
 * 求高精度幂
	Time Limit: 500MS		Memory Limit: 10000K
	Total Submissions: 168506		Accepted: 40809
	Description
	
	对数值很大、精度很高的数进行高精度计算是一类十分常见的问题。比如，对国债进行计算就是属于这类问题。 
	
	现在要你解决的问题是：对一个实数R( 0.0 < R < 99.999 )，要求写程序精确计算 R 的 n 次方(Rn)，其中n 是整数并且 0 < n <= 25。
	Input
	
	T输入包括多组 R 和 n。 R 的值占第 1 到第 6 列，n 的值占第 8 和第 9 列。
	Output
	
	对于每组输入，要求输出一行，该行包含精确的 R 的 n 次方。输出需要去掉前导的 0 后不要的 0 。如果输出是整数，不要输出小数点。
	Sample Input
	
	95.123 12
	0.4321 20
	5.1234 15
	6.7592  9
	98.999 10
	1.0100 12
	Sample Output
	
	548815620517731830194541.899025343415715973535967221869852721
	.00000005148554641076956121994511276767154838481760200726351203835429763013462401
	43992025569.928573701266488041146654993318703707511666295476720493953024
	29448126.764121021618164430206909037173276672
	90429072743629540498.107596019456651774561044010001
	1.126825030131969720661201
	Source
 * @author Lobinary
 *
 */
public class 求高精度幂 {
	
	public static void main(String[] args) throws Exception {
		Scanner cin = new Scanner(System.in);
		AU.check();
	}
	
	public static void run(Scanner cin){
		while(cin.hasNext()){
	        BigDecimal a = cin.nextBigDecimal();
	        int b = cin.nextInt();
	        System.out.println(cal(a,b));
		}
	}
	
	/**
	 * 最后解决方案
	 * @param a
	 * @param b
	 * @return
	 */
	private static String cal(BigDecimal a,int b) {
		BigDecimal r = a.pow(b).stripTrailingZeros();
		String s = r.toPlainString();
		if(s.startsWith("0"))s = s.replaceFirst("0", "");
		return s;
	}
	
	/**
	 * 初始解决方案-后因为答案错误，不知道具体错误原因
	 * @param a
	 * @return
	 */
	private static String cal(String a) {
		BigDecimal ba = new BigDecimal(a.substring(0,6).trim());
		BigDecimal bb = new BigDecimal(a.substring(7,9).trim());
		BigDecimal r = new BigDecimal(1);
		for (int i = 0; i < bb.intValue(); i++) {
			r = r.multiply(ba);
		}
		String s = r.toPlainString();
		if(s.startsWith("0"))s = s.replaceFirst("0", "");
		return s.replaceAll("0+$", "");
	}
	
	@Test
	public void test(){
		String[] i = {
				"95.123 12"
			,"0.4321 20"
			,"5.1234 15"
			,"6.7592  9"
			,"98.999 10"
			,"1.0100 12"};	
		String[] r = {
				"548815620517731830194541.899025343415715973535967221869852721"
				,".00000005148554641076956121994511276767154838481760200726351203835429763013462401"
				,"43992025569.928573701266488041146654993318703707511666295476720493953024"
				,"29448126.764121021618164430206909037173276672"
				,"90429072743629540498.107596019456651774561044010001"
				,"1.126825030131969720661201"
				};
		for (int j = 0; j < i.length; j++) {
			String cal = cal(i[j]);
			if(!cal.equals(r[j])){
				/**
				 * 
				 * 理论结果：.00000005148554641076956121994511276767154838481760200726351203835429763013462401
				 * 实际结果：.00000005148554641076956121994511276767154838481760200726351203835429763013462401
				 */
				System.out.println("计算"+i[j]+"有问题");
				System.out.println("理论结果："+r[j]);
				System.out.println("实际结果："+cal);
			}else{
				System.out.println("计算"+i[j]+"运行通过");
			}
		}
	}
	

}
