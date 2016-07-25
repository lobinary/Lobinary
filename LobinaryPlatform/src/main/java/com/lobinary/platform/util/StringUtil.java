package com.lobinary.platform.util;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * 字符串操作类
 */
public class StringUtil {

	/** 
	 * 字符串分隔，保留空串 
	 * 例如: if: String test = "a|b||c||"; then: String[] result = {"a","b","","c","",""};
	 * 
	 * @param sourceString 待分隔字符串
	 * @param delimiter    分隔符
	 * @return String[]
	 */
	public static String[] split(String sourceString, String delimiter) {
		// 校验传入参数
		if (sourceString == null || (sourceString.trim()).equals(""))
			return (new String[0]);
		if (delimiter == null)
			return null;
		
		// 定义变量
		String str = null;
		int intPos = 0;
		ArrayList<String> arrayList = new ArrayList<String>();
		String[] strRet = new String[1];

		// begin split
		intPos = sourceString.indexOf(delimiter);
		String strTemp = "";
		while (intPos != -1) {
			if (intPos != 0) {
				// process ESC
				if (sourceString.substring(intPos - 1, intPos).equals("\\")) {
					strTemp = strTemp + sourceString.substring(0, intPos - 1)
							+ delimiter;
					sourceString = sourceString.substring(intPos + 1);
					intPos = sourceString.indexOf(delimiter);
					continue;
				}
			}

			str = strTemp.equals("") ? sourceString.substring(0, intPos).trim()
					: strTemp + sourceString.substring(0, intPos).trim();
			sourceString = sourceString.substring(intPos + 1);
			arrayList.add(str);
			strTemp = "";
			intPos = sourceString.indexOf(delimiter);
		}
		arrayList.add(sourceString.trim());

		// transfer ArrayList to String[]
		strRet = new String[arrayList.size()];
		for (int i = 0; i < arrayList.size(); i++) {
			strRet[i] = (String) arrayList.get(i);
		}

		return strRet;
	}

	/**
	 * 字符串分隔，不保留空串
	 * 例如: if: String test = "a|b||c||"; then: String[] result = {"a","b","c"};
	 * 
	 * @param sourceString 待分隔字符串
	 * @param delimiter    分隔符
	 * @return String[]
	 */
	public static String[] splitIgnoreEmptyFields(String sourceString, String delimiter) {
		// check input parameters
		if (sourceString == null)
			return (new String[0]);
		if (delimiter == null)
			return null;
		if ((sourceString.trim()).equals(""))
			return (new String[0]);

		// begin split the input string
		StringTokenizer st = new StringTokenizer(sourceString, delimiter);
		String[] tResult = new String[st.countTokens()];
		int i = 0;
		while (st.hasMoreTokens()) {
			tResult[i++] = st.nextToken();
		}

		return tResult;
	}

	/**
	 * 判断传入的字符串格式 只允许数字、小数点后带一位或两位的数字字符串
	 * 
	 * @param String  str
	 * @return boolean
	 */
	public static boolean IsNum(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) < '0' || str.charAt(i) > '9') {
				if (str.charAt(i) == '.' && i != 0 && i < str.length() - 1
						&& str.length() >= 3 && (str.length() - i <= 3)) {
					continue;
				}

				return false;
			}

		}
		return true;
	}

	/**
	 * 将传入的金额转换为如￥88,888.00格式
	 * 
	 * @param account  金额数量
	 * @return
	 */
	public static String numberFormat(long account) {
		Locale locale = Locale.CHINA;
		NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
		return nf.format(account);
	}

	/**
	 * 将传入的金额转换为如￥88,888.00格式
	 * 
	 * @param account  金额数量
	 * @return
	 */
	public static String numberFormat(int account) {
		Locale locale = Locale.CHINA;
		NumberFormat nf = NumberFormat.getCurrencyInstance(locale);

		return nf.format(account);
	}

	/**
	 * 将传入的金额转换为如￥88,888.00格式
	 * 
	 * @param account  金额数量
	 * @return
	 */
	public static String numberFormat(Object account) {
		Locale locale = Locale.CHINA;
		NumberFormat nf = NumberFormat.getCurrencyInstance(locale);

		return nf.format(account);
	}

	/**
	 * 将传入的金额转换为如￥88,888.00格式
	 * 
	 * @param account  金额数量
	 * @return
	 */
	public static String numberFormat(double account) {
		Locale locale = Locale.CHINA;

		NumberFormat nf = NumberFormat.getCurrencyInstance(locale);

		return nf.format(account);
	}

	/**
	 * 判断字符串是否为中文
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isChinese(String str) {
		str = toGb2312(str);
		byte b[];
		try {
			b = str.getBytes("ISO8859_1");
			for (int i = 0; i < b.length; i++) {
				byte b1 = b[i];
				if (b1 < 0 || b1 == 63) {
					return true;
				}

			}
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 用getBytes(encoding)：返回字符串的一个byte数组 当b[0]为 63时，应该是转码错误 A、不乱码的汉字字符串：
	 * 1、encoding用GB2312时，每byte是负数； 2、encoding用ISO8859_1时，b[i]全是63。 B、乱码的汉字字符串：
	 * 1、encoding用ISO8859_1时，每byte也是负数； 2、encoding用GB2312时，b[i]大部分是63。 C、英文字符串
	 * 1、encoding用ISO8859_1和GB2312时，每byte都大于0； <p/>
	 * 总结：给定一个字符串，用getBytes("iso8859_1") 1、如果b[i]有63，不用转码； A-2
	 * 2、如果b[i]全大于0，那么为英文字符串，不用转码； B-1 3、如果b[i]有小于0的，那么已经乱码，要转码。 C-1
	 */
	public static String toGb2312(String str) {
		if (str == null) {
			return null;
		}
		String retStr = str;
		byte b[];
		try {
			b = str.getBytes("ISO8859_1");
			for (int i = 0; i < b.length; i++) {
				byte b1 = b[i];
				if (b1 == 63) {
					break;
				} else if (b1 > 0) {
					continue;// 2
				} else if (b1 < 0) { // 不可能为0，0为字符串结束符
					retStr = new String(b, "GB2312");
					break;
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			// To change body of catch statement use File | Settings | File
			// Templates.
		}
		return retStr;
	}

	/**
	 * 将相关字符串前面补0，补为制定的位数 如果大于等于指定的位数，返回原值
	 * 
	 * @param num 补充后的长度
	 * @param str 待补充的字符串
	 * @return
	 */
	public static String addZero(int num, String str) {
		StringBuffer bf = new StringBuffer(str);
		int strLenght = str.length();
		
		if (strLenght >= num) {
			return str;
		} else {
			for (int i = strLenght; i < num; i++) {

				StringBuffer bf1 = new StringBuffer("0");
				bf = bf1.append(bf);

			}
			return bf.toString();
		}
	}
	
	/**
	 * 根据开始和结束标签截取中间字符串内容，不包含开始及结束标签
	 * 
	 * @param str		待截取的字符串
	 * @param beginTag	开始标签
	 * @param endTag	结束标签
	 * @return
	 */
	public static String splitString(String str, String beginTag, String endTag) {
		String result = "";
		String[] r1 = str.split(beginTag);
		String[] r2 = r1[1].split(endTag);
		result = r2[0];
		return result;
	}
	
	/**
	 * 根据开始和结束标签截取开始标签与结束标签间的字符串内容，包含开始及结束标签
	 * 
	 * @param str		待截取的字符串
	 * @param beginTag	开始标签
	 * @param endTag	结束标签
	 * @return
	 */
	public static String tagString(String str, String beginTag, String endTag) {
		String result = "";
		String[] r1 = str.split(beginTag);
		String[] r2 = r1[1].split(endTag);
		result = beginTag + r2[0] + endTag;
		return result;
	}
	
	/**
	 * 判断字符串是否存在字符数组中
	 * 
	 * @param str	待比对字符串
	 * @param array	字符数组
	 * @return true-存在；false-不存在
	 */
	public static boolean searchInArray(String str, String array[]){
		for(String one : array){
			if(one.equals(str)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断字符串是否为空
	 * 
	 * @param str	待判断字符串
	 * @param flag	是否对空格进行trim true-trim后判断；false-直接判断
	 * @return boolean true-为空；false-非空
	 */
	public static boolean isBlank(String str, boolean flag) {
		if(flag)
			return (str == null || str.trim().length() < 1);
		else
			return (str == null || str.length() < 1);
	}
	
	/**
	 * 将字符串中的字母转换成大写
	 * 
	 * @param aString 待处理的字符串
	 * @return 字母转换成大写的字符串
	 */
	public static String toUpperCase(String aString){
		return aString.toUpperCase();
	}
	
	/**
	 * 将字符串中的字母转换成小写 
	 * 
	 * @param aString 待处理的字符串
	 * @return 字母转换成小写的字符串
	 */
	public static String toLowerCase(String aString){
		return aString.toLowerCase();
	}
	
	/**
	 * 将字符串中的特殊字符转换成字母F
	 * 
	 * @param aString 待处理的字符串
	 * @return 转换后的字符串
	 */
	public static String toLetterOrDigit(String aString){
		StringBuffer tString = new StringBuffer();
		for (int i = 0;i < aString.length();i++){
			if (Character.isLetterOrDigit(aString.charAt(i))){
				tString.append(aString.charAt(i)); 
			}else{
				tString.append("F"); 
			}
		}
		return tString.toString();
	}

	/**
	 * 将字符串中的非字母转换成字母  特殊字符转换成M；数字转换为0-9对应A-J
	 * 
	 * @param aString 待处理的字符串
	 * @return 转换后的字符串
	 */
	public static String toLetter(String aString){
		StringBuffer tString = new StringBuffer();
		for (int i = 0;i < aString.length();i++){
			if (Character.isLetter(aString.charAt(i))){
				tString.append(aString.charAt(i)); 
			}else if(Character.isDigit(aString.charAt(i))){
				switch(aString.charAt(i)){
					case '0':	{tString.append("A") ;break;}
					case '1':	{tString.append("B"); break;}
					case '2':	{tString.append("C"); break;}
					case '3':	{tString.append("D"); break;}
					case '4':	{tString.append("E"); break;}
					case '5':	{tString.append("F"); break;}
					case '6':	{tString.append("G"); break;}
					case '7':	{tString.append("H"); break;}
					case '8':	{tString.append("I"); break;}
					case '9':	{tString.append("J"); break;}
				}
			}else{
				tString.append("M"); 
			}
		}
		return tString.toString();
	}
	
	/**
	 * 把null转化为空字符串
	 * @author pengxuan
	 * @param str
	 * @return
	 */
	public static String convertNull(String str){
		return (null == str)?"":str;
	}
	/**
	 * 取字符串绝对值，如"-234"转换成"234"，如果不是负的则不转换
	 * @param str
	 * @return
	 */
	public static String absStr(String str){
		if(str!=null&&!str.equals("")&&str.startsWith("-")){
			return str.substring(1, str.length());
		}else{
			return str;
		}
	}
	
	/**
	 * 前填字符串
	 * @param orignalStr
	 * @param length
	 * @param add
	 * @return
	 */
	public static String fillStringBefore(String orignalStr, char c, int length) {
		StringBuffer sb = new StringBuffer();
		orignalStr = (null == orignalStr)?"":orignalStr;
		

		for(int i = 0; i < length - orignalStr.length(); i++) {
			sb.append(c);
		}
		sb.append(orignalStr);
		return sb.toString();
	}
	
	/**
	 * 后填字符串
	 * @param orignalStr
	 * @param length
	 * @param align
	 * @param c
	 * @return
	 */
	public static String fillStringAfter(String orignalStr, char c, int length) {
		StringBuffer sb = new StringBuffer();
		orignalStr = (null == orignalStr)?"":orignalStr;
		
		sb.append(orignalStr);
		for(int i = 0; i < length - orignalStr.length(); i++) {
			sb.append(c);
		}

		return sb.toString();
	}
}
