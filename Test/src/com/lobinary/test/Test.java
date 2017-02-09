package com.lobinary.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {
	
	private String a;
	private String b;

	public static void main(String[] args) throws Exception {
		
//		String r = "^(?!.*(\\.js|\\.css|receive|access3|\\.png|\\.ico|\\.jpg|\\.img)).*$";
//		System.out.println("http://localhost:8080/wechat-app/access3".matches(r));
		
		/*
		 * 测试Calendar的设置功能set(Calendar.YEAR, 2015);
		 * 结论是Calendar初始化是以当前时间初始化的，当我设置了年份之后，他就会给年份改了
		 */
		Calendar startInstance = Calendar.getInstance();
		startInstance.set(Calendar.YEAR, 2015);
		startInstance.set(Calendar.DAY_OF_YEAR, 364);
		System.out.println(startInstance.getTime().toLocaleString());
		
		/**
		 * 测试汉字大写
		 */
		String s = "a我爱你bCDeF";
		System.out.println(s.toUpperCase());
		
		
		/**
		 *测试时间格式
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-ddHHmmss");
		String format = sdf.format(new Date());
		System.out.println(format);
		
		/**
		 * 测试偏移量
		 * 测试结果，subString不支持偏移量形式截取，只支持准确位置截取
		 * 
		 * 测试原因：
		 * Redis LRANGE命令将返回存储在key列表的特定元素。
		 * 偏移量开始和停止是从0开始的索引，0是第一元素(该列表的头部)，1是列表的下一个元素。
		 * 这些偏移量也可以是表示开始在列表的末尾偏移负数。例如，-1是该列表的最后一个元素，-2倒数第二个，等等。
		 * 	redis 127.0.0.1:6379> LPUSH list1 "foo"
			(integer) 1
			redis 127.0.0.1:6379> LPUSH list1 "bar"
			(integer) 2
			redis 127.0.0.1:6379> LPUSHX list1 "bar"
			(integer) 0
			redis 127.0.0.1:6379> LRANGE list1 0 -1
			1) "foo"
			2) "bar"
			3) "bar
		 * 
		 */
		String 偏移量测试字符串 = "abcdefg";
//		System.out.println(偏移量测试字符串.substring(1, -2));
		
	}
	
	

	@Override
	public String toString() {
		return ga(a,b);
	}

	private static String ga(String c ,String d) {
		return c + d;
	}	
	
	

}
