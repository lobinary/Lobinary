package com.lobinary.test.normal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Replace使用正则表达式 {

	public static void main(String[] args) {
		String s = "{A1}{7}";
		Pattern p = Pattern.compile(".*(\\d+).*");
		Matcher m = p.matcher(s);
		if (m.matches()) {
			System.out.println("随机数位数: " + m.group(1));
			System.out.println("原始表达式: " + m.group(0));
		}
//
//		Pattern p1 = Pattern.compile(".*(A\\d+).*");
//		Matcher m1 = p1.matcher(s);
//		if (m1.matches()) {
//			System.out.println("随机字母: " + m1.group(1));
//			System.out.println("原始表达式: " + m1.group(0));
//		}
//		
//		String l = s.replaceFirst("\\{\\d+\\}*","1234567");
//		l = l.replaceFirst("\\{A\\d+\\}*","B");
//		System.out.println(s);
//		System.out.println(l);
		

//		String posVoucherNoRegular = "{A2}{6}";
//		//从规则中解析长度 规则为{A1}{7},则提取出7这个数字
//		int numberLength = Integer.parseInt(Pattern.compile(".*(\\d+).*").matcher(posVoucherNoRegular ).group(1));
//		int abcLength = Integer.parseInt(Pattern.compile(".*(A\\d+).*").matcher(posVoucherNoRegular).group(1).replaceAll("A", ""));
//		//通过规则对随机数部分进行替换 如:A{7},替换{7}为A1234567
//		String voucherNo = posVoucherNoRegular.replaceFirst("\\{\\d+\\}", randomNumber(numberLength));
//		voucherNo = posVoucherNoRegular.replaceFirst("\\{A\\d+\\}*", randomAbC(abcLength));
//		System.out.println(voucherNo);
	}
	
	
	
	/**
	 * 生成随机的大小写字母
	 * @param numberLength
	 * @return
	 */
	private static String randomAbC(int length) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * <pre>
	 * 随机生成7位数字
	 * 一亿数据8秒
	 * 效率最高
	 * @param length
	 * @return
	 */
	public static String random1234567(){
//		return ""+(int)(Math.random() * (9000000) + 1000000);//一亿数据7200毫秒   但因损失首位为0的精度，因此暂时使用下方方案
		return randomNumber(10000000, 7);
	}
	
	/**
	 * <pre>
	 * 随机生成7位数字
	 * 一亿数据8秒
	 * 效率最高
	 * @param length
	 * @return
	 */
	public static String random12345678(){
//		return ""+(int)(Math.random() * (9000000) + 1000000);//一亿数据7200毫秒   但因损失首位为0的精度，因此暂时使用下方方案
		return randomNumber(10000000, 7);
	}
	
	/**
	 * <pre>
	 * 固定长度的随机数字
	 * 一亿数据33秒
	 * 效率低
	 * @param length
	 * @return
	 */
	public static String randomNumber(int length){
		String r = String.valueOf((int)((Math.random())*(Math.pow(10, length))));//
		return beforeFillZero(r, length);
	}

	/**
	 * <pre>
	 * 生成最小值位数的随机数 
	 * 效率比指定长度方法randomNumber(int length)更高
	 * 一亿数据18秒
	 * 效率中等
	 * @param minValue
	 * @return
	 */
	public static String randomNumber(long minValue){
		String r = String.valueOf((int)((Math.random())*minValue));//一亿数据18500毫秒
		return beforeFillZero(r, String.valueOf(minValue).length());
	}


	/**
	 * <pre>
	 * 生成指定位数的随机数 
	 * 效率比指定长度方法randomNumber(int length)更高
	 * 一亿数据11秒
	 * 效率高
	 * 
	 * @param zero 手动计算的带0的值  例如长度为3时，此处应该为1000
	 * @param length 长度	长度为3，则为3
	 * @return
	 */
	public static String randomNumber(long zero,int length){
		String r = String.valueOf((int)((Math.random())*zero));//一亿数据11000毫秒
		return beforeFillZero(r, length);
	}
	
	/**
	 * 前补0
	 * @param ori 原字符串
	 * @param length 长度
	 * @return
	 */
	private static String beforeFillZero(String ori,long length){
		if(ori.length()==length)return ori;
		StringBuilder sb = new StringBuilder();
		for (int i = ori.length(); i < length; i++) {
			sb.append(0);
		}
		sb.append(ori);
		return sb.toString();
	}
	
	/**
	 * 随机数字+字母
	 * @param length
	 * @return
	 */
//	public static String randomABC(int length){
//		return null;
//	}

}
