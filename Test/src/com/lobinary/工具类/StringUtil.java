package com.lobinary.工具类;


/**
 * 
 * 字符串工具类
 * 
 * @author lvbin 
 * @since 2014年12月3日 下午2:58:15
 * @version V1.0.0 Description : Create this file
 * 
 *         
 *
 */
public class StringUtil {
	
	/**
	 * 字符串转成报文byte数组-自动添加5位长度字符
	 * @return
	 */
	public static byte[] string2Message(String s){
		int length = s.getBytes().length + 5;
		s = beforeFillChar("" + length,'0',5) + s;
		return s.getBytes();
	}
	
	/**
	 * 前填字符串
	 * @param c
	 * @param length
	 * @return
	 */
	public static String beforeFillChar(String s,char c,int length){
		if(s.length()>length){
			return s;
		}
		for(int i=s.length();i<length;i++){
			s = c + s ;
		}
		return s;
	}
	
	public static void main(String[] args) {
		byte[] s = string2Message("00001haha诶哟我去");
		System.out.println(new String(s));
	}
	
	
	/**
	 * 从字符串中根据前后缀，获取中间字符串
	 * @param pre
	 * @param rear
	 */
	public static String catchStringFromString(String str,String pre,String rear) {
		String result = "";
		try {
			int start = str.indexOf(pre);
			int end = str.indexOf(rear);
			if(start<end){
				result = str.substring(start+pre.length(),end);
			}
		} catch (Exception e) {
			System.out.println("从字符串中捕获参数异常，异常原因为：" + e);
		}
		return result;
	}

	public static boolean isEmpty(String 名称) {
		return 名称==null||名称.length()==0;
	}
	
}
