package com.lobinary.test.常规辅助测试.工具;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.java.多线程.TU;
import com.lobinary.工具类.date.HttpUtils;

public class HttpTest {

	private final static Logger logger = LoggerFactory.getLogger(HttpTest.class);
	
	public static void main(String[] args) {
		循环访问网址();
//		定时间隔循环访问();
	}

	private static void 循环访问网址() {
		String 循环访问的网址 = "http://172.19.42.80:8080/WebTest/index.html";
		int 循环访问的次数 = 300;
		for (int i = 0; i < 循环访问的次数; i++) {
			long start = System.currentTimeMillis();
			HttpUtils.doGet(循环访问的网址);
			System.out.println("请求完毕，耗时:"+(System.currentTimeMillis()-start)+"毫秒");
		}
		logger.info("{}次循环访问{}结束",循环访问的网址,循环访问的次数);
	}
	
	private static void 定时间隔循环访问(){
		String 循环访问的网址 = "http://www.zhujiwu.com/api/cart.php?cmd=Free_vps";
		int 循环访问的次数 = 300;
		for (int i = 0; i < 循环访问的次数; i++) {
			String content = HttpUtils.doGet(循环访问的网址+i);
			TU.l("["+content+"]");
		}
		logger.info("{}次循环访问{}结束",循环访问的网址,循环访问的次数);
	}

}
