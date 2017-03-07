package com.lobinary.工具类;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
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
	
	private static boolean 秘钥加载完毕 = true;
	private static long a = 4217964721L;
	private static long b = -3320670651L;
	private static int r = 413550;
	private static int 每次翻译的长度 = 1600;
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
			翻译缓冲池.append(每条翻译数据);
		}
		String 翻译后的数据 = 换行符翻译(翻译缓冲池.toString());//#ꪪ#
		String[] 翻译后的分组数据 = 翻译后的数据.split("\\\\n".trim());
		if(翻译后的分组数据.length==q.size()){
			for (String 分割后的数据 : 翻译后的分组数据) {
				result.add(分割后的数据);
			}
		}else{
			log.info("*****************************原始/分割后数据***********************************************************************************************************");
			for (int i = 0; i < q.size(); i++) {
				log.info(q.get(i));
				if(i<翻译后的分组数据.length)log.info(翻译后的分组数据[i]);
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
				String 翻译后数据 = translate(本次翻译数据.toString());
				本次翻译数据.delete(0, 本次翻译数据.length());
				本次翻译数据.append(每段文字);
				if(最终翻译数据.length()>0)最终翻译数据.append("\\n");
				最终翻译数据.append(翻译后数据);
			}
		}
		if(本次翻译数据.length()>0){
			String 翻译后数据 = translate(本次翻译数据.toString());
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
		String 本次翻译数据 = null;
		int 上一个点的位置 = 0;//用于记录上一个点的位置
		int 上次截取的最后位置 = 0;//用于记录上次截取后的最后位置
		log.info("接收到原始翻译数据为:"+q);
		for (int i = 0; i < q.length(); i++) {
			String s = q.substring(i,i+1);
			if(s.equals(".")){
				i++;
				if(i - 上次截取的最后位置  > 每次翻译的长度){//当前点到上一个点的长度是不是超长了
					if(上一个点的位置==上次截取的最后位置){//如果超长了，并且上次截取的位置等于上一个点
						//这个时候只能强制截取，因为单句已经超长，并且更新上一个点的位置
						本次翻译数据 = q.substring(上次截取的最后位置,上次截取的最后位置+每次翻译的长度);
						上次截取的最后位置 = 上次截取的最后位置+每次翻译的长度;
						上一个点的位置 = 上次截取的最后位置;
					}else{//如果超长了，但是上一个点和最后截取的点不相等，那么就证明我们这个点已经是第二句或者更多句的话，
						//那么我们就截取上到一句话，上一个点的位置不需要动，只需要更改最后截取位置就行
						本次翻译数据 = q.substring(上次截取的最后位置,上一个点的位置);
						上次截取的最后位置 = 上一个点的位置;
					}
					最终翻译数据.append(translate(本次翻译数据));
					i = 上次截取的最后位置;
				}
				上一个点的位置 = i;
			}else{
				if(每次翻译的长度<(i-上次截取的最后位置)){
					本次翻译数据 = q.substring(上次截取的最后位置,上一个点的位置);
					最终翻译数据.append(translate(本次翻译数据));
					上次截取的最后位置 = 上一个点的位置;
				}
			}
			
			if(i == q.length()-1){
				if(上次截取的最后位置!=i){
					本次翻译数据 = q.substring(上次截取的最后位置,i+1);
					最终翻译数据.append(translate(本次翻译数据));
					上次截取的最后位置 = i;
				}
			}
		}
		if(上次截取的最后位置==0){
			最终翻译数据.append(translate(q));
		}
		String 翻译最终数据 = 最终翻译数据.toString();
		log.info("最后翻译结果为:"+翻译最终数据);
		总耗时时间 = 总耗时时间 + (System.currentTimeMillis()-st);
		return 翻译最终数据;
	}
	
	public static String translate(String q) throws Exception {
		String tk = 加签(q);
		q = URLEncoder.encode(q, "UTF-8");
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
			throw new Exception("连接谷歌翻译服务器失败,请求网址为:"+url);
		}
		System.out.println("谷歌服务器返回的数据为:"+resp);
		log.info("单次翻译前数据为："+q);
		String 解析返回数据 = 解析返回数据(resp);
		log.info("单次翻译后数据为："+解析返回数据);
		return 解析返回数据;
	}

	private static String 解析返回数据(String j) throws Exception {
		boolean end = false;
		StringBuilder sb = new StringBuilder();
		int index = 2;
		log.info(j);
//		System.out.print("  ");
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
//		log.info(sb.toString());
//		log.info("==========================================================================================================");
//		for (String sss : 分段数据) {
//			log.info(sss);
//		}
//		log.info("==========================================================================================================");
//		log.info(分段数据.size());
		StringBuffer 整合后翻译数据 = new StringBuffer();
		for (int i = 0; i < 分段数据.size(); i++) {
			String 单条翻译数据 = 分段数据.get(i);
//			log.info(i+":"+单条翻译数据);
			if(单条翻译数据.startsWith("[\"")){
				if(!单条翻译数据.contains("\",\""))throw new Exception("发现未知格式翻译数据:"+单条翻译数据);
				String 翻译数据 = 单条翻译数据.substring(2,单条翻译数据.indexOf("\",\""));
//				log.info(翻译数据);
				整合后翻译数据.append(翻译数据);
			}else{
				if(i==分段数据.size()-1){
					break;
				}else{
					throw new Exception("发现未知格式翻译数据:"+单条翻译数据);
				}
			}
		}
//		//log.info("========================================================================================");
//		//log.info(整合后翻译数据.toString());
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
		//log.info(resp);
		
		/**
		 * 测试list翻译效果
		 */
		List<String> l = new ArrayList<String>();
		l.add("i");
		l.add("love");
		l.add("you");
		List<String> t = t(l);
		for (int i = 0; i < t.size(); i++) {
			System.out.println(t.get(i));
		}
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
