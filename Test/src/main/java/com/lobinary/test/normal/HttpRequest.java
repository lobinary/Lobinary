package com.lobinary.test.normal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class HttpRequest {

	public static void main(String[] args) throws InterruptedException, IOException {
		String s = null;

		//NET
		String url = "http://172.17.102.174:8005/wechat-app/gw/net";
		String param = "requestId=05240002";
		String result = sendPost(url, param);
		System.out.println("NET结果：["+result+"]");

		System.out.println("===========================================================================================================================================================================================================================");
		
		//EPOS
		String url2 = "http://172.17.102.174:8005/wechat-app/gw/epos";
		String param2 = "requestId=qijin201605300001&amountMin=0&amountMax=1000&startDate=2015-01-01&endDate=2016-06-21";
		String result2 = sendPost(url2, param2);
		System.out.println("EPOS结果：["+result2+"]");

		System.out.println("===========================================================================================================================================================================================================================");
		
		//ONE KEY	调用接口返回空
		String url3 = "http://172.17.102.174:8005/wechat-app/gw/one_key";
		String param3 = "phoneNO=15718802778=SH1466144661701&startDate=2016-01-01&endDate=2016-06-20";
		String result3 = sendPost(url3, param3);
		System.out.println("ONE KEY结果：["+result3+"]");

		System.out.println("===========================================================================================================================================================================================================================");
		
		//EXAM
		String url4 = "http://172.17.102.174:8005/wechat-app/gw/exam";
		String param4 = "requestId=SH1466144661701&amountMin=0&amountMax=1000&startDate=2016-06-06&endDate=2016-06-06";
		String result4 = sendPost(url4, param4);
		System.out.println("EXAM结果：["+result4+"]");

		System.out.println("===========================================================================================================================================================================================================================");
		
		//POS
		String url5 = "http://172.17.102.174:8005/wechat-app/gw/pos";
		
		String param5 = "posCATI=1&amountMin=0&amountMax=1000&startDate=2016-06-06&endDate=2016-06-06";
		String result5 = sendPost(url5, param5);
		
		System.out.println("POS结果：["+result5+"]");
		
//		result = sendGet(url, param );
//		System.out.println("GET结果：["+result+"]");
//		boolean lastResult = false;
//		int times = 10000;
//		outStr2File("准备进行" + times + "次抽奖！");
//		String successStr = "已中奖记录：空";
//		for (int i = 1; i <= times; i++) {
//			String result = sendGet("http://cellphonefront.unicompayment.com:55353/wocaifu/drwaAction.action?phoneType=1&phone=18612966769", null);
////			String result = sendGet("http://111.111.111.101:8080/", "");
//			System.out.println(result);
//			String b = "抽奖成功";
//			if(result.contains("运气不好")){
//				b="抽奖失败";
//			}else{
//				lastResult = true;
//				successStr = successStr + "-" + i + "-";
//				outStr2File(result);
//			}
//			System.out.println("第" + i + "次抽奖结果为:" + b + "," +  successStr);
//		}
//		if(lastResult){
//			System.out.println("恭喜，" + successStr + "次抽奖成功！");
//			outStr2File(times + "次抽奖结束，抽奖成功！");
//		}else{
//			System.out.println("抱歉，" + times + "次抽奖均失败！");
//			outStr2File(times + "次抽奖结束，抽奖失败！");
//		}
	}
	
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
//			connection.setConnectTimeout(1);
			connection.connect();
			
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
//				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	
	public static void outStr2File(String s) throws IOException{
		File file = new File("c:/t.txt");
		if(!file.exists()){
			file.createNewFile();
		}
		BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true)));
		br.append(s);
		br.append("\n");
		br.flush();
		br.close();
	}
}