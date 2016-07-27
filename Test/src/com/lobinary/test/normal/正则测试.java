package com.lobinary.test.normal;

public class 正则测试 {

	public static void main(String[] args) {
		System.out.println(existsChineseCharacters("111"));
		System.out.println(existsChineseCharacters("haha"));
		System.out.println(existsChineseCharacters("aaa111"));
		System.out.println(existsChineseCharacters("aa汉子啊啊"));
	}
	
	public static boolean existsChineseCharacters(String s){
		boolean result = false;
		char[] charArray = s.toCharArray();
		for (Character c : charArray) {
			String cStr = c.toString();
			boolean flag = java.util.regex.Pattern.matches("[\u4E00-\u9FA5]", cStr);
			if(flag)result = true;
		}
		return result;
	}
	
}
