package com.lobinary.源码.java.编码;

public class BMUtil {

	public static String outBinary(byte[] byteArray){
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		for (int i = 0; i < byteArray.length; i++) {
			byte bb = byteArray[i];
			String b = byte2BinaryString(bb);
			if(i==0){
				sb.append(b);
			}else{
				sb.append(","+b);
			}
		}
		sb.append("}");
		return sb.toString();
	}
	
	public static String byte2BinaryString(byte b){
		String result = "";
		String binary32String = Integer.toBinaryString(b);
//		System.out.println(binary32String+";");
		if(binary32String.length()<8){
			result = beforeZero(binary32String, 8);
		}else{
			result = binary32String.substring(binary32String.length()-8,binary32String.length());
		}
		return result.substring(0,4)+ " " + result.substring(4,8);
	}
	
	public static String beforeZero(String ori,int length){
		String result = "";
		for (int i = 0; i < length - ori.length(); i++) {
			result += "0";
		}
		return result + ori;
	}

	public static String 转换成Unicode二进制(int iA) {
		String 二进制数字 = Integer.toBinaryString(iA);
		String 拼合成32位的二进制 = beforeZero(二进制数字,32);
		return 拼合成32位的二进制.substring(0,4)+" "
				+ 拼合成32位的二进制.substring(4,8)+" "
				+ 拼合成32位的二进制.substring(8,12)+" "
				+ 拼合成32位的二进制.substring(12,16)+" "
				+ 拼合成32位的二进制.substring(16,20)+" "
				+ 拼合成32位的二进制.substring(20,24)+" "
				+ 拼合成32位的二进制.substring(24,28)+" "
				+ 拼合成32位的二进制.substring(28,32);
	}

	public static String char2BinaryStr(int i,int length) {
		String result = beforeZero(Integer.toBinaryString(i),length);
		return result;
	}
	
}
