package com.lobinary.工具类;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.工具类.http.HttpUtil;

// http://www.cnblogs.com/charlexu/p/3424963.html
public class GTU {

	private final static Logger log = LoggerFactory.getLogger(GTU.class);
	
	private static boolean 秘钥加载完毕 = false;
	private static long a = 4217964721L;
	private static long b = -3320670651L;
	private static int r = 413550;
	public static int 每次翻译的长度 = 700;
	private static Invocable invocable;
	static {
		try {
			// 得到一个ScriptEngine对象
					ScriptEngineManager maneger = new ScriptEngineManager();
					ScriptEngine engine = maneger.getEngineByName("JavaScript");
					// 读js文件
					String jsFile = "D:/Tool/Git/Lobinary/Test/src/com/lobinary/实用工具/Java源码注释翻译工具/GTU.js";
					FileInputStream fileInputStream = new FileInputStream(new File(jsFile));
					Reader scriptReader = new InputStreamReader(fileInputStream, "utf-8");
					// console.info(tk('fuck you'));
					try {
						engine.eval(scriptReader);
						if (engine instanceof Invocable) {
							// 调用JS方法
							invocable = (Invocable) engine;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					log.info("翻译工具加密JS加载完毕");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static long 总耗时时间 = 0;
	public static long 加签总耗时 = 0;
	public static long 请求总耗时 = 0;
	
	/**
	 * 当使用单个字符串翻译 t(String q) 的时候
	 * 经常会简单的几个单词也翻译一次
	 * 这样反反复复的http请求，占用了大量的时间
	 * 
		写入文件耗时：13
		翻译耗时：7660
		翻译总耗时:6707
		加签总耗时:1783
		请求总耗时:4844
		
		根据以上报告显示，请求耗时占用了一半的时间
		因此我们增加此方法，通过合并list中的请求，以此来缩减时间优化效率
		
		我们通过特殊分隔符，来分割翻译语句，这样就能达到合并翻译的目的
	 * @param q
	 * @return
	 * @throws Exception 
	 */
	public static List<String> t(List<String> q) throws Exception{
		if(q.size()==0)return q;
		log.info("**************************************准备翻译数据**************************************************");
		for (String 每个数据 : q) {
			log.info(每个数据);
		}
		log.info("**************************************准备翻译数据**************************************************");
		List<String> result = new ArrayList<String>();
		StringBuilder 翻译缓冲池 = new StringBuilder();
		/**
		 * 0xAAAA 为Tai系列字符
		 * Tai Viet is a Unicode block containing characters for writing the Tai languages Tai Dam, Tai Dón, and Thai Song.
		 * 
		 * 因此应该不会出现在java的注释中，所以我们根据此来分割,避免了注释中数据出现，被意外分割的可能性
		 */
//		String 分割标识符 = " .#"+(char)0xAAAA+"#. ";
		
		String 分割标识符 = "\n";
		for (String 每条翻译数据 : q) {
			if(翻译缓冲池.length()!=0)翻译缓冲池.append(分割标识符);
			翻译缓冲池.append(每条翻译数据.replace("\\n", "#n#"));
		}
		String 翻译后的数据 = 换行符翻译(翻译缓冲池.toString());//#ꪪ#
		String[] 翻译后的分组数据 = 翻译后的数据.split("\\\\n".trim());
		if(翻译后的分组数据.length==q.size()){
			for (String 分割后的数据 : 翻译后的分组数据) {
				result.add(分割后的数据.replace("#n#", "\\n"));
			}
		}else{
			log.info("*****************************原始/分割后数据***********************************************************************************************************");
			for (int i = 0; i < q.size(); i++) {
				log.info("原始:"+q.get(i));
				if(i<翻译后的分组数据.length)log.info("分割:"+翻译后的分组数据[i]);
			}
			log.info("*****************************原始/分割后数据***********************************************************************************************************");
			throw new Exception("翻译异常，分割后数据长度和原数据长度不一致(原"+q.size()+":译"+翻译后的分组数据.length+")");
		}
		return result;
	}

	
	/**
	 * 单独针对换行符翻译形式的数据进行翻译
	 * 以换行符为分割接线
	 * @param q
	 * @return
	 * @throws Exception
	 */
	public static String 换行符翻译(String q) throws Exception {
		long st = System.currentTimeMillis();
		if(q.length()==0) return "";
		log.info("准备翻译数据:"+q);
		String[] 分割后 = q.split("\\n");
		StringBuilder 最终翻译数据 = new StringBuilder();
		StringBuilder 本次翻译数据 = new StringBuilder();
		for (String 每段文字 : 分割后) {
			if(本次翻译数据.length()+每段文字.length()<每次翻译的长度){
				if(本次翻译数据.length()>0)本次翻译数据.append("\n");
				本次翻译数据.append(每段文字);
			}else{
				String 翻译后数据 = t(本次翻译数据.toString());
				本次翻译数据.delete(0, 本次翻译数据.length());
				本次翻译数据.append(每段文字);
				if(最终翻译数据.length()>0)最终翻译数据.append("\\n");
				最终翻译数据.append(翻译后数据);
			}
		}
		if(本次翻译数据.length()>0){
			String 翻译后数据 = t(本次翻译数据.toString());
			本次翻译数据.delete(0, 本次翻译数据.length());
			if(最终翻译数据.length()>0)最终翻译数据.append("\\n");
			最终翻译数据.append(翻译后数据);
		}
		log.info("最后翻译结果为:"+最终翻译数据.toString());
		总耗时时间 = 总耗时时间 + (System.currentTimeMillis()-st);
		return 最终翻译数据.toString();
	}
	
	public static String t(String q) throws Exception {
		long st = System.currentTimeMillis();
		if(q.length()==0) return "";
		//XXX 分割翻译丢数据
		//			   ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms
//		if(q.contains("ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms."))return "";
		
		StringBuilder 最终翻译数据 = new StringBuilder();
		String 本次翻译数据 = "";
		log.info("接收到原始翻译数据为:"+q);
		String[] 通过点分割数据 = q.split("\\.");
		
		for (int i = 0; i < 通过点分割数据.length; i++) {
			int 本句话长度 = 通过点分割数据[i].length();
			if(本次翻译数据.length()+本句话长度<每次翻译的长度){//小于长度 续数据
				本次翻译数据 = 本次翻译数据 + 通过点分割数据[i];
			}else{
				if(本次翻译数据.length()>0)最终翻译数据.append(translate(本次翻译数据));
				if(本句话长度>每次翻译的长度){
					for (int j = 0; j < (本句话长度/每次翻译的长度)+1; j++) {
						最终翻译数据.append(translate(通过点分割数据[i].substring((j)*每次翻译的长度,Math.min((j+1)*每次翻译的长度,本句话长度))));
					}
				}else{
					本次翻译数据 = "" + 通过点分割数据[i];
				}
			}
		}
		if(本次翻译数据.length()>0){
			最终翻译数据.append(translate(本次翻译数据));
		}
		String 翻译最终数据 = 最终翻译数据.toString();
		log.info("最后翻译结果为:"+翻译最终数据);
		总耗时时间 = 总耗时时间 + (System.currentTimeMillis()-st);
		return 翻译最终数据;
	}
	
	public static String translate(String q) throws Exception {
		String tk = 加签(q);
		q = URLEncoder.encode(q, "UTF-8");
		if(q.length()>1500)throw new Exception("请求长度超限,最大只允许:"+每次翻译的长度+",当前为:"+q.length());
//		//log.info(q);
		String url = "http://translate.google.cn/translate_a/single?client=t&sl=auto&tl=zh-CN&hl=zh-CN&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&ie=UTF-8&oe=UTF-8&source=btn&ssel=0&tsel=0&kc=1&tk="
				+ tk + "&q=" + q;
		String resp;
		try {
//			resp = HttpUtil.doGet(url);
//			resp = HttpUtil.sendGetRequest(url);
			System.out.println(url);
			long st = System.currentTimeMillis();
			resp = HttpUtil.sendGet(url,"UTF-8");
			请求总耗时 = 请求总耗时 + (System.currentTimeMillis()-st);
		} catch (Exception e) {
			throw new Exception("连接谷歌翻译服务器失败,请求长度为:"+q.length()+",请求网址为:"+url);
		}
		System.out.println("谷歌服务器返回的数据为:"+resp);
		log.info("单次翻译前数据为："+q);
		String 解析返回数据 = 解析返回数据(resp);
		log.info("单次翻译后数据为："+解析返回数据);
		return 解析返回数据;
	}

	private static String 解析返回数据(String j) throws Exception {
		log.info(j);
		
		StringBuffer 整合后翻译数据 = new StringBuffer();
		String[] 分割数据 = j.substring(3).split("\\],\\[");
		for (int i = 0; i < 分割数据.length; i++) {
			String s = 分割数据[i];
			System.out.println(s);
			
			if(s.startsWith("\"")){
				s = s.substring(1,s.lastIndexOf("\",\""));
				整合后翻译数据.append(translateUnicode(s));
			}else{
				break;
			}
		}
//		//log.info("========================================================================================");
		log.info(整合后翻译数据.toString());
		return 整合后翻译数据.toString()
				.replaceAll("“", "\"")
				.replaceAll("”", "\"")
				.replaceAll("</ ", "</")
				.replaceAll("＃", "#")
				.replaceAll("，", ",")
				.replaceAll("（", "(")
				.replaceAll("）", ")")
				.replaceAll("＆", "&")
				.replaceAll("？", "?");
	}
	
	

	/**
	 * 将字符串中的unicode编码进行转换
	 * @param s
	 * @return
	 */
	private static String translateUnicode(String s) {
		if(!s.contains("\\u"))return s;
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			if(s.length()-i<6){
				result.append(s.substring(i, s.length()));
				break;
			}
			String temp = s.substring(i, i+6);
			if(temp.startsWith("\\u")){
				int data = Integer.parseInt(temp.substring(2,6),16);
				result.append((char) data);
				i = i+5;
			}else{
				result.append(s.substring(i, i+1));
			}
			
		}
		return result.toString();
	}


	/**
	 * <p>
	 * {@code String}表示UTF-16格式的字符串，其中<em>补充字符</ em>由<em> surrogate\n   对</
	 * em>（请参阅<a href="Character.html#unicode"> Unicode\n   {@code Character}
	 * 类中的字符表示</a>\n   更多信息）。 索引值是指{@code char}代码单位，因此补充字符在{@code String}
	 * 中使用两个位置。
	 * <p>
	 * {@code String}类提供了处理方法\n   Unicode代码点（即，字符），以及用于处理Unicode代码单元（即，
	 * {@code char}）值的代码。
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		/**
		 * 测试翻译效果
		 */
		String ss = "[[[\"基本类型的含义如下：B字节有符号字节C字符字符D双精度双精度浮点数浮点数浮点型单精度浮点数I整数J长整数L \\u003cfullclassname\\u003e;\",\"The meaning of the base types is as follows: B byte signed byte C char character D double double precision IEEE float F float single precision IEEE float I int integer J long long integer L\\u003cfullclassname\\u003e;\",,,3],[\"给定类的对象S short signed short Z boolean true或false [\\u003cfield sig\\u003e array\\n \",\"an object of the given class S short signed short Z boolean true or false [\\u003cfield sig\\u003e  array\\n \",,,3],[\"此方法将此字符串转换为Java类型声明，如`String []'，并在解析类型无效时抛出`ClassFormatException'\\n \",\"This method converts this string into a Java type declaration such as `String[]' and throws a `ClassFormatException' when the parsed type is invalid\\n \",,,3],[\"字符码格式的签名，例如“C”或“[Ljava / lang / String;”\",\"signature in byte code format, eg \\\"C\\\" or \\\"[Ljava/lang/String;\\\"\",,,3],[\"分别\\n \",\"respectively\\n \",,,1],[\"将方法签名的类型作为字符值返回，如在\\u003cem\\u003e常量\\u003c/ em\\u003e中定义\\n \",\"Return type of method signature as a byte value as defined in \\u003cem\\u003eConstants\\u003c/em\\u003e\\n \",,,3],[\"将签名的类型作为字符值返回，如\\u003cem\\u003e常量\\u003c/ em\\u003e中定义\\n \",\"Return type of signature as a byte value as defined in \\u003cem\\u003eConstants\\u003c/em\\u003e\\n \",,,3],[\"将（有符号）字节转换为（无符号）短值，即所有负值变为正\",\"Convert (signed) byte to (unsigned) short value, ie, all negative values become positive\",,,3],[,,\"Jīběn lèixíng de hányì rúxià:B zì jié yǒu fúhào zì jié C zìfú zìfú D shuāng jīngdù shuāng jīngdù fú diǎnshù fú diǎnshù fú diǎn xíng dān jīngdù fú diǎnshù I zhěngshù J zhǎng zhěngshù L \\u003cfullclassname\\u003e; gěi dìng lèi de duìxiàng S short signed short Z boolean true huò false [\\u003cfield sig\\u003e array\\n cǐ fāngfǎ jiāng cǐ zìfú chuàn zhuǎnhuàn wèi Java lèixíng shēngmíng, rú `String []', bìng zài jiěxī lèixíng wúxiào shí pāo chū `ClassFormatException'\\n zìfú mǎ géshì de qiānmíng, lìrú “C” huò “[Ljava/ lang/ String;” fēnbié\\n jiāng fāngfǎ qiānmíng de lèixíng zuòwéi zìfú zhí fǎnhuí, rú zài \\u003cem\\u003echángliàng \\u003c/ em\\u003ezhōng dìngyì\\n jiāng qiānmíng de lèixíng zuòwéi zìfú zhí fǎnhuí, rú \\u003cem\\u003echángliàng \\u003c/ em\\u003ezhōng dìngyì\\n jiāng (yǒu fúhào) zì jié zhuǎnhuàn wèi (wú fúhào) duǎn zhí, jí suǒyǒu fù zhí biàn wèi zhèng\"]],,\"en\",,,[[\"The meaning of the base types is as follows: B byte signed byte C char character D double double precision IEEE float F float single precision IEEE float I int integer J long long integer L\\u003cfullclassname\\u003e;\",,[[\"基本类型的含义如下：B字节有符号字节C字符字符D双精度双精度浮点数浮点数浮点型单精度浮点数I整数J长整数L \\u003cfullclassname\\u003e;\",0,true,false],[\"基本类型的含义如下：B字节符号字节c char字符的字符D双双精度IEEE浮点˚F浮动单精度IEEE浮点I INT整数j长长整型→\\u003cfullclassname\\u003e;\",0,true,false]],[[0,205]],\"The meaning of the base types is as follows: B byte signed byte C char character D double double precision IEEE float F float single precision IEEE float I int integer J long long integer L\\u003cfullclassname\\u003e;\",0,0],[\"an object of the given class S short signed short Z boolean true or false [\\u003cfield sig\\u003e  array\",,[[\"给定类的对象S short signed short Z boolean true或false [\\u003cfield sig\\u003e array\",0,true,false],[\"给定类的对象短篇小说符号短ž布尔真或假的[\\u003c现场SIG\\u003e阵列\",0,true,false]],[[0,93]],\"an object of the given class S short signed short Z boolean true or false [\\u003cfield sig\\u003e  array\",0,0],[\"\\n \",,,[[0,3]],\"\\n \",0,0],[\"This method converts this string into a Java type declaration such as `String[]' and throws a `ClassFormatException' when the parsed type is invalid\",,[[\"此方法将此字符串转换为Java类型声明，如`String []'，并在解析类型无效时抛出`ClassFormatException'\",0,true,false],[\"这种方法这个字符串到Java类型声明转换，如`的String []'，并抛出一个`ClassFormatException“当解析类型无效\",0,true,false]],[[0,148]],\"This method converts this string into a Java type declaration such as `String[]' and throws a `ClassFormatException' when the parsed type is invalid\",0,0],[\"\\n \",,,[[0,3]],\"\\n \",0,0],[\"signature in byte code format, eg \\\"C\\\" or \\\"[Ljava/lang/String;\\\"\",,[[\"字符码格式的签名，例如“C”或“[Ljava / lang / String;”\",0,true,false],[\"签名字节码格式，例如“C”或“[Ljava /朗/字符串;”\",0,true,false]],[[0,62]],\"signature in byte code format, eg \\\"C\\\" or \\\"[Ljava/lang/String;\\\"\",0,0],[\"respectively\",,[[\"分别\",1000,true,false],[\"分别\",1000,true,false]],[[0,12]],\"respectively\",0,0],[\"\\n \",,,[[0,3]],\"\\n \",0,0],[\"Return type of method signature as a byte value as defined in \\u003cem\\u003eConstants\\u003c/em\\u003e\",,[[\"将方法签名的类型作为字符值返回，如在\\u003cem\\u003e常量\\u003c/ em\\u003e中定义\",0,true,false],[\"返回方法签名的类型作为字节值中定义的\\u003cem\\u003e常量\\u003c/ em\\u003e的\",0,true,false]],[[0,80]],\"Return type of method signature as a byte value as defined in \\u003cem\\u003eConstants\\u003c/em\\u003e\",0,0],[\"\\n \",,,[[0,3]],\"\\n \",0,0],[\"Return type of signature as a byte value as defined in \\u003cem\\u003eConstants\\u003c/em\\u003e\",,[[\"将签名的类型作为字符值返回，如\\u003cem\\u003e常量\\u003c/ em\\u003e中定义\",0,true,false],[\"返回签名的类型作为字节值中定义的\\u003cem\\u003e常量\\u003c/ em\\u003e的\",0,true,false]],[[0,73]],\"Return type of signature as a byte value as defined in \\u003cem\\u003eConstants\\u003c/em\\u003e\",0,0],[\"\\n \",,,[[0,3]],\"\\n \",0,0],[\"Convert (signed) byte to (unsigned) short value, ie, all negative values become positive\",,[[\"将（有符号）字节转换为（无符号）短值，即所有负值变为正\",0,true,false],[\"转换（符号）字节（无符号）短值，即，所有的负值变为正\",0,true,false]],[[0,88]],\"Convert (signed) byte to (unsigned) short value, ie, all negative values become positive\",0,0]],0.99448156,,[[\"en\"],,[0.99448156],[\"en\"]]]";
		String r = 解析返回数据(ss);
		System.out.println(r);
		
		
		
//		String[] a = ss.substring(3).split("\\],\\[");
//		List<String> 分段数据 = new ArrayList<String>();
//		for (int i = 0; i < a.length; i++) {
//			String s = a[i];
//			if(s.startsWith("\"")){
//				分段数据.add(s);
//			}else{
//				break;
//			}
//		}
//		O.o(分段数据, "测试数据");
		
/**
 * 翻译总长度1896
准备对数据进行加签2814908780,1382763536,413547,i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.
[[["我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。","i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want",,,3],["吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。 ","to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat",,,3],["something.i想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。","something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.",,,3],["我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。","i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want",,,3],["吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。 ","to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat",,,3],["something.i想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。","something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.",,,3],["我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。","i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want",,,3],["吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。 ","to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat",,,3],["something.i想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。我想吃东西。","something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.",,,3],["我想吃东西。我想吃东西。我想吃东西。我想吃东西。","i want to eat something.i want to eat something.i want to eat something.i want to eat something.",,,3],[,,"Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Something.I xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Something.I xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Something.I xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi. Wǒ xiǎng chī dōngxi."]],,"en",,,,0.9998613,,[["en"],,[0.9998613],["en"]]]
翻译总长度1920
准备对数据进行加签2814908780,1382763536,413547,i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.i want to eat something.
发送GET请求出现异常！java.io.IOException: Server returned HTTP response code: 400 for URL: http://translate.google.cn/translate_a/single?client=t&sl=auto&tl=zh-CN&hl=zh-CN&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&ie=UTF-8&oe=UTF-8&source=btn&ssel=0&tsel=0&kc=1&tk=453924.41551&q=i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.
Exception in thread "main" java.io.IOException: Server returned HTTP response code: 400 for URL: http://translate.google.cn/translate_a/single?client=t&sl=auto&tl=zh-CN&hl=zh-CN&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&ie=UTF-8&oe=UTF-8&source=btn&ssel=0&tsel=0&kc=1&tk=453924.41551&q=i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.i+want+to+eat+something.
	at sun.net.www.protocol.http.HttpURLConnection.getInputStream0(HttpURLConnection.java:1839)
	at sun.net.www.protocol.http.HttpURLConnection.getInputStream(HttpURLConnection.java:1440)
	at com.lobinary.工具类.http.HttpUtil.sendGet(HttpUtil.java:140)
	at com.lobinary.工具类.GTU.main(GTU.java:253)

以上测试结果表明，翻译长度达到1900后，就会出现错误，所以我们将翻译长度限定在1850，以此来优化翻译速度
 */
//		String o = "i want to eat something.";
//		String qq = "";
//		for (int i = 0; i < 100; i++) {
//			qq += o;
//			if(i>70){//
//				//log.info("q:" + q);
//				System.out.println("翻译总长度"+qq.length());
//				String tk = 加签(qq);
//				//log.info(tk);
//				String q = URLEncoder.encode(qq, "UTF-8");
//				//log.info(q);
//				String url = "http://translate.google.cn/translate_a/single?client=t&sl=auto&tl=zh-CN&hl=zh-CN&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&ie=UTF-8&oe=UTF-8&source=btn&ssel=0&tsel=0&kc=1&tk="
//						+ tk + "&q=" + q;
//				//log.info(url);
//				String resp = HttpUtil.sendGet(url, "UTF-8");
//				System.out.println(resp);
//			}
//			
//		}
		
		
		
		/*
		 * 
		 * [[["我想吃东西","i want to eat something",,,1],[,,"Wǒ xiǎng chī dōngxi"
		 * ]],,"en",,,[["i want to eat something"
		 * ,,[["我想吃东西",1000,true,false],["我想吃点东西",0,true,false]],[[0,23]],
		 * "i want to eat something"
		 * ,0,0]],0.29146308,,[["en"],,[0.29146308],["en"]],,,,,,[["want","to",
		 * "eat","something","want to","to eat","want to eat"]]]
		 */
//		log.info(resp);
		
		/**
		 * 测试list翻译效果
		 */
//		List<String> l = new ArrayList<String>();
//		l.add("i");
//		l.add("love\\n");
//		l.add("you");
//		List<String> t = t(l);
//		for (int i = 0; i < t.size(); i++) {
//			System.out.println(t.get(i));
//		}
		
//		/**
//		 * 测试超长数据
//		 */
//		String ts = "<tr><th>Key</th> <th>Description of Associated Value</th></tr> <tr><td><code>javaversion</code></td> <td>Java Runtime Environment version</td></tr> <tr><td><code>javavendor</code></td> <td>Java Runtime Environment vendor</td></tr> <tr><td><code>javavendorurl</code></td> <td>Java vendor URL</td></tr> <tr><td><code>javahome</code></td> <td>Java installation directory</td></tr> <tr><td><code>javavmspecificationversion</code></td> <td>Java Virtual Machine specification version</td></tr> <tr><td><code>javavmspecificationvendor</code></td> <td>Java Virtual Machine specification vendor</td></tr> <tr><td><code>javavmspecificationname</code></td> <td>Java Virtual Machine specification name</td></tr> <tr><td><code>javavmversion</code></td> <td>Java Virtual Machine implementation version</td></tr> <tr><td><code>javavmvendor</code></td> <td>Java Virtual Machine implementation vendor</td></tr> <tr><td><code>javavmname</code></td> <td>Java Virtual Machine implementation name</td></tr> <tr><td><code>javaspecificationversion</code></td> <td>Java Runtime Environment specification  version</td></tr> <tr><td><code>javaspecificationvendor</code></td> <td>Java Runtime Environment specification  vendor</td></tr> <tr><td><code>javaspecificationname</code></td> <td>Java Runtime Environment specification  name</td></tr> <tr><td><code>javaclass";
//		t(ts);
	}

	public static String 加签(String query) throws Exception {
		long st = System.currentTimeMillis();
		getTKK();
		System.out.println("准备对数据进行加签"+a+","+b+","+ r+","+ query);
		Object result = invocable.invokeFunction("getKey", a, b, r, query);
		加签总耗时 = 加签总耗时 + (System.currentTimeMillis()-st);
		return result.toString();
	}

	/**
	 * TKK=eval('((function(){var a\x3d2232569751;var b\x3d-1646195174;return
	 * 413305 413305.586374577 tk: 694560.841561 694560.841561
	 * 
	 * @return
	 * @throws Exception
	 */
	private static String getTKK() throws Exception {

		String 秘钥 = "" + r + "." + (a + b);
		if (秘钥加载完毕)
			return 秘钥; // 如果秘钥加载完成，就不需要二次加载秘钥

		String resp = HttpUtil.sendGetRequest("http://translate.google.cn");
		System.out.println("从Google获取TKK返回数据为:"+resp);
		resp = resp.substring(resp.indexOf("TKK=eval"));
		resp = resp.substring(0, resp.indexOf("+\\x27"));
		//log.info(resp);
		// TKK=eval('((function(){var a\x3d3119618383;var b\x3d-570961749;
		// TKK=eval('((function(){var a\x3d1343057443;var b\x3d1205599191;
		String[] 变量 = resp.split("var");
		for (String s : 变量) {
			//log.info(s);
			if (s.contains(";")) {
				if (s.contains("a")) {
					String as = s.trim().replace("a\\x3d", "").replace(";", "");
					//log.info("as:" + as);
					a = Long.parseLong(as);
				} else if (s.contains("b")) {
					String[] ss = s.split(";return ");
					for (String sss : ss) {
						if (sss.contains("b")) {
							b = Long.parseLong(sss.trim().replace("b\\x3d", "").replace(";", ""));
						} else {
							r = Integer.parseInt(sss.trim());
						}
					}

				}
			}
		}
		//log.info("a:" + a);
		//log.info("b:" + b);
		//log.info("r:" + r);
		if (a != 0 && b != 0 && r != 0) {
			log.info("获取TKK成功，秘钥信息为："+秘钥);
			秘钥加载完毕 = true;
			return 秘钥;
		} else {
			throw new Exception("获取TKK失败");
		}
	}

}
