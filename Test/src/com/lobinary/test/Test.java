package com.lobinary.test;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Test {
	
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
	
		
		System.out.println(new BigDecimal("123456").toString());
		
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		Calendar c1 = Calendar.getInstance();
		System.out.println(c==c1);
	
		
		System.out.println("==============");
		
		
		System.out.println(URLEncoder.encode("i love you"));
		
		long a = 3397762296L;
		long b = -253483058L;
		long t = (a+b);
		System.out.println();
		System.out.println(t);
		System.out.println(a^=t);
		System.out.println("a+b="+(a+b));
		
		long aa = -1834548137;
		long bb = 3144279238L;
		System.out.println("xx:"+(int)(aa^bb));
		System.out.println("====================================================================================================");
		String j = "[[[\"阅读字符流的抽象类。\",\"Abstract class for reading character streams.\",,,3],[\"子类必须实现的唯一方法是读取（char []，int，int）和close（）。\",\"The only methods that a subclass must implement are read(char[], int, int) and close().\",,,3],[\"然而，大多数子类将覆盖这里定义的一些方法，以提供更高的效率，附加的功能或两者。\",\"Most subclasses, however, will override some of the methods defined here in order to provide higher efficiency, additional functionality, or both.\",,,3],[,,\"Yuèdú zìfú liú de chōuxiàng lèi. Zi lèi bìxū shíxiàn de wéiyī fāngfǎ shì dòu qǔ (char [],int,int) hé close(). Rán'ér, dà duōshù zi lèi jiāng fùgài zhèlǐ dìngyì de yīxiē fāngfǎ, yǐ tígōng gèng gāo de xiàolǜ, fùjiā de gōngnéng huò liǎng zhě.\"]],,\"en\",,,[[\"Abstract class for reading character streams.\",,[[\"阅读字符流的抽象类。\",0,true,false],[\"抽象类用于读取字符流。\",0,true,false]],[[0,45]],\"Abstract class for reading character streams.\",0,0],[\"The only methods that a subclass must implement are read(char[], int, int) and close().\",,[[\"子类必须实现的唯一方法是读取（char []，int，int）和close（）。\",0,true,false],[\"一个子类必须实现的唯一方法是阅读（的char []，INT，INT）和close（）。\",0,true,false]],[[0,87]],\"The only methods that a subclass must implement are read(char[], int, int) and close().\",0,0],[\"Most subclasses, however, will override some of the methods defined here in order to provide higher efficiency, additional functionality, or both.\",,[[\"然而，大多数子类将覆盖这里定义的一些方法，以提供更高的效率，附加的功能或两者。\",0,true,false],[\"大多数的子类，但是，将覆盖某些以便提供更高的效率，额外的功能，或两者在这里定义的方法。\",0,true,false]],[[0,146]],\"Most subclasses, however, will override some of the methods defined here in order to provide higher efficiency, additional functionality, or both.\",0,0]],0.99554771,,[[\"en\"],,[0.99554771],[\"en\"]]]";
		boolean end = false;
		StringBuilder sb = new StringBuilder();
		int index = 2;
		System.out.println(j);
		System.out.print("  ");
		int 左括号数量 = 1;
		List<String> 分段数据 = new ArrayList<String>();
		while(!end){
			String jc = j.substring(index, index+1);
			if(jc.equals("["))左括号数量++;
			if(jc.equals("]"))左括号数量--;
			if(左括号数量==0)break;
			
			if(jc.equals("\\")){
				index++;
				jc = j.substring(index, index+1);
				switch (jc) {
				case "u":
					index++;
					jc = j.substring(index, index+4);
					int data = Integer.parseInt(jc, 16);
			         sb.append((char) data);
			         index += 3;
					break;
				case "n":
					sb.append("\\n");
					break;
				case "\"":
					sb.append("\"");
					break;
				default:
					sb.append(jc);
					break;
				}
			}else{
				sb.append(jc);
			}
			if(左括号数量==1&&!sb.toString().equals(",")){
				分段数据.add(sb.toString());
				sb = new StringBuilder();
			}else if(sb.toString().equals(",")){
				sb = new StringBuilder();
			}
			index++;
			if(index>=j.length())break;
		}
		System.out.println(sb.toString());
		System.out.println("==========================================================================================================");
		for (String sss : 分段数据) {
			System.out.println(sss);
		}
		System.out.println("==========================================================================================================");
		System.out.println(分段数据.size());
		StringBuffer 整合后翻译数据 = new StringBuffer();
		for (int i = 0; i < 分段数据.size(); i++) {
			String 单条翻译数据 = 分段数据.get(i);
			System.out.println(i+":"+单条翻译数据);
			if(单条翻译数据.startsWith("[\"")){
				if(!单条翻译数据.contains("\",\""))throw new Exception("发现未知格式翻译数据:"+单条翻译数据);
				String 翻译数据 = 单条翻译数据.substring(2,单条翻译数据.indexOf("\",\""));
				System.out.println(翻译数据);
				整合后翻译数据.append(翻译数据);
			}else{
				if(i==分段数据.size()-1){
					break;
				}else{
					throw new Exception("发现未知格式翻译数据:"+单条翻译数据);
				}
			}
		}
		System.out.println("========================================================================================");
		System.out.println(整合后翻译数据.toString());
		
		
		StringBuilder sbb = new StringBuilder();
		sbb.append("test");
		System.out.println("test".equals(sbb));
		System.out.println(sbb.equals("test"));
		System.out.println("sbb:"+sbb);
		sbb.delete(0, sbb.length());
		System.out.println(sbb.length());
	}
	
}
