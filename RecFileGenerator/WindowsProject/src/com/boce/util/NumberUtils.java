package com.boce.util;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NumberUtils {
	/**
	 * 元转分
	 * 如果还有比分更小单位，则4舍5入到分
	 * @param yuanAmt
	 * @return
	 * 
	 */
	
	static SecureRandom random = new SecureRandom();
	
	
	public static int yuanToFen(Float yuanAmt) {		
		String fenAmt = multiplication(yuanAmt.toString(), "100");
		return (int) Math.round(new Double(fenAmt));	
	}
	
	public static long yuanToFen(String yuanAmt) {
		yuanAmt = yuanAmt.replaceAll(",", "");
		String fenAmt = multiplication(yuanAmt, "100");
		return Math.round(new Double(fenAmt));		
	}
	public static String yuanToFenStr(String yuanAmt) {	
		yuanAmt = yuanAmt.replaceAll(",", "");
		return String.valueOf(yuanToFen(yuanAmt));		
	}
	
	/**
	 * 分转元
	 * @param fenAmt
	 * @return
	 */
	public static Float fenToYuan(Long fenAmt) {
		return divide(fenAmt.toString(), 100);
	}
	
	/**
	 * 分转元
	 * @param fenAmt
	 * @return
	 */
	public static Double fenToYuan(int fenAmt) {
		return divideToDouble(fenAmt+"", 100);
	}
	
	/**
	 * 分转元
	 * @param fenAmt
	 * @return
	 */
	public static Double fenToYuan(String fenAmt) {
		fenAmt = fenAmt.replaceAll(",", "");
		return divideToDouble(fenAmt, 100);
	}
	/**
	 * 分转元
	 * @param fenAmt
	 * @return 10,000.00
	 */
	public static String fenToYuanStr(String fenAmt) {
		fenAmt = fenAmt.replaceAll(",", "");
		return divideToDoubleStr(fenAmt, 100);
	}
	/**
	 * 分转元
	 * @param fenAmt
	 * @return 10000.00
	 */
	public static String fenToYuanFomatStr(String fenAmt) {
		fenAmt = fenAmt.replaceAll(",", "");
		String str = divideToDoubleStr(fenAmt, 100);
		return str.replaceAll(",", "");
	}
	
	
	/**
	 * 金额相加
	 * @param oldAmt
	 * @param fillAmt
	 * @return Float
	 */
	public static Float addToFloat(Float oldAmt,Float fillAmt){		
		BigDecimal bdAmt = new BigDecimal(oldAmt == null?"0":oldAmt.toString());
		BigDecimal bdcnt = new BigDecimal(fillAmt == null?"0":fillAmt.toString());
		BigDecimal total= bdAmt.add(bdcnt);
		return total.floatValue();
	}
	
	/**
	 * 金额相加
	 * @param oldAmt
	 * @param fillAmt
	 * @return Double
	 */
	public static Double addToDouble(String oldAmt,String fillAmt){		
		BigDecimal bdAmt = new BigDecimal(oldAmt);
		BigDecimal bdcnt = new BigDecimal(fillAmt);
		BigDecimal total= bdAmt.add(bdcnt);
		return total.doubleValue();
	}
	
	/**
	 * 金额相加
	 * @param oldAmt
	 * @param fillAmt
	 * @return Double
	 */
	public static Double addToDouble(Double oldAmt,Float fillAmt){		
		BigDecimal bdAmt = new BigDecimal(oldAmt);
		BigDecimal bdcnt = new BigDecimal(fillAmt);
		BigDecimal total= bdAmt.add(bdcnt);
		return total.doubleValue();
	}
	
	/**
	 * 相减
	 * @param subtractive 被减数
	 * @param extraction 减数
	 * @return
	 */
	public static Float subtract(Float subtractive,Float extraction){		
		BigDecimal bdAmt = new BigDecimal(subtractive == null?"0":subtractive.toString());
		BigDecimal bdAmt2 = new BigDecimal(extraction == null?"0":extraction.toString());
		BigDecimal surplus= bdAmt.subtract(bdAmt2);
		return surplus.floatValue();
	}
	
	/**
	 * 相减
	 * @param subtractive 被减数
	 * @param extraction 减数
	 * @return
	 */
	public static Double subtract(Double subtractive,Double extraction){		
		BigDecimal bdAmt = new BigDecimal(subtractive == null?"0":subtractive.toString());
		BigDecimal bdAmt2 = new BigDecimal(extraction == null?"0":extraction.toString());
		BigDecimal surplus= bdAmt.subtract(bdAmt2);
		return surplus.doubleValue();
	}
	
	/**
	 * 相减
	 * @param subtractive 被减数
	 * @param extraction 减数
	 * @return
	 */
	public static String subtract(String subtractive,String extraction){		
		BigDecimal bdAmt = new BigDecimal(subtractive == null?"0":subtractive.toString());
		BigDecimal bdAmt2 = new BigDecimal(extraction == null?"0":extraction.toString());
		BigDecimal surplus= bdAmt.subtract(bdAmt2);
		return surplus.toString();
	}
	
	/**
	 * 相除
	 * @param oldAmt 被除数，原有金额
	 * @param divisor 除数
	 * @return
	 */
	public static Float divide(String oldAmt,int divisor){		
		BigDecimal bdAmt = new BigDecimal(oldAmt);
		BigDecimal bdAmt2 = new BigDecimal(String.valueOf(divisor));
		BigDecimal surplus= bdAmt.divide(bdAmt2);
		return surplus.floatValue();
	}
	
	/**
	 * 相除
	 * @param oldAmt 被除数，原有金额
	 * @param divisor 除数
	 * @return
	 */
	public static Double divideToDouble(String oldAmt,int divisor){		
		BigDecimal bdAmt = new BigDecimal(oldAmt);
		BigDecimal bdAmt2 = new BigDecimal(String.valueOf(divisor));
		BigDecimal surplus= bdAmt.divide(bdAmt2);
		
		return surplus.doubleValue();
	}
	
	/**
	 * 相除(2位小数)
	 * @param oldAmt 被除数，原有金额
	 * @param divisor 除数
	 * @return
	 */
	public static String divideToDoubleStr(String oldAmt,int divisor){		
		BigDecimal bdAmt = new BigDecimal(oldAmt);
		BigDecimal bdAmt2 = new BigDecimal(String.valueOf(divisor));
		BigDecimal surplus= bdAmt.divide(bdAmt2);
		
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		return df.format(surplus.doubleValue());
	}
	
	/**
	 * 相乘
	 * @param multed
	 * @param fillAmt
	 * @return
	 */
	public static String multiplication(String multed,String fillAmt){		
		BigDecimal bdAmt = new BigDecimal(fillAmt);
		BigDecimal bdcnt = new BigDecimal(multed);
		BigDecimal total= bdAmt.multiply(bdcnt);		
		return total.toString();
	}
	
	/**
	 * 相乘
	 * @param multed
	 * @param fillAmt
	 * @return 小数点后为0时返回整数字符串
	 * 		   不为0则返回小数字符串
	 */
	public static String multiplication2(String multed,String fillAmt){
		String result = multiplication(multed,fillAmt);
		String [] s = result.split("\\.");
		if(s.length > 1){
			int a = new Integer(s[1]);
			if(a > 0){
				return result;
			}else{
				return s[0];
			}
		}
		return result;
	}
	
	/**
	 * 金额不足一分则返回一分
	 * 金额单位为元
	 * @param amt
	 * @return
	 */
	public static Float upToOneFen(Float amt){
		if(amt.floatValue() > 0 && amt.floatValue() < 0.01){
			return 0.01F;
		}else{
			return amt;
		}		
	}
	
	public static String rounding(String yuanAmt){		
		BigDecimal bdAmt = new BigDecimal(yuanAmt);
		BigDecimal bdAmt2 = new BigDecimal("1");
		BigDecimal surplus= bdAmt.divide(bdAmt2,2,BigDecimal.ROUND_HALF_UP);		
		return surplus.toString();		
	}
	
	/**
	 * 检查浮点数
	 * @param num
	 * @param type "0+":非负浮点数 "+":正浮点数 "-0":非正浮点数 "-":负浮点数 "":浮点数
	 * @return
	 */
	public static boolean checkFloat(String num,String type){
		String eL = "";
		if(type.equals("0+"))eL = "^\\d+(\\.\\d+)?$";//非负浮点数
		else if(type.equals("+"))eL = "^((\\d+\\.\\d*[1-9]\\d*)|(\\d*[1-9]\\d*\\.\\d+)|(\\d*[1-9]\\d*))$";//正浮点数
		else if(type.equals("-0"))eL = "^((-\\d+(\\.\\d+)?)|(0+(\\.0+)?))$";//非正浮点数
		else if(type.equals("-"))eL = "^(-((\\d+\\.\\d*[1-9]\\d*)|(\\d*[1-9]\\d*\\.\\d+)|(\\d*[1-9]\\d*)))$";//负浮点数
		else eL = "^(-?\\d+)(\\.\\d+)?$";//浮点数
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(num);
		boolean b = m.matches();
		return b;
	}
	
	/**
	 * 判断正整数
	 * @param num
	 * @return
	 */
	public static boolean checkInteger(String num){
		String el = "^[0-9]*[1-9][0-9]*$";
		Pattern p = Pattern.compile(el);
		Matcher m = p.matcher(num);
		boolean b = m.matches();
		return b;
	}
	
	public static boolean checkFloat(String num){
		return checkFloat(num, "+");
	}
	
	
	public static String getFlowNo(){
		
		String randomNum = String.valueOf(random.nextInt()); 
		return System.currentTimeMillis()+""+randomNum.substring(randomNum.length()-3);
	}

	public static void main(String[] args) {
		System.out.println(NumberUtils.getFlowNo());
//		System.out.println(NumberUtils.yuanToFenStr("200,000.05"));
//		System.out.println(NumberUtils.yuanToFen( new Float(100000.06)));
//		
//		System.out.println(NumberUtils.fenToYuanStr(("110,0000")));
//		System.out.println(NumberUtils.fenToYuanFomatStr(("110,0000")));
//		
//		DecimalFormat df = new DecimalFormat();
//		df.setMaximumFractionDigits(2);
//		df.setMinimumFractionDigits(2);
//		System.out.println(df.format(3.0));
//		System.out.println("------------");
//		SimpleDateFormat dds = new SimpleDateFormat("HHssmm");
//		System.out.println(dds.format(new Date()));
//		Calendar date=Calendar.getInstance();
//		System.out.println(date.getTime());
		
//		System.out.println(""+date.getHours()+date.getMinutes()+date.getSeconds());
	}
}
