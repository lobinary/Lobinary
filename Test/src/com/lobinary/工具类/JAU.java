package com.lobinary.工具类;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.lobinary.工具类.file.FileUtil;

public class JAU {
	
	private static List<String> 注释终止符号 = new ArrayList<String>();
	
	static{
		注释终止符号.add("*/");
		注释终止符号.add("@ClassName");
		注释终止符号.add("@author");
		注释终止符号.add("@date");
		注释终止符号.add("@version");
		注释终止符号.add("@param");
		注释终止符号.add("@see");
		注释终止符号.add("@since");
	}

	public static void main(String[] args) throws IOException {
		File f = new File("c:/test/test2.java");
		List<String> list = FileUtil.readLine2List(f);
//		for (String s : list) {
//			System.out.println(s);
//		}
		List<List<String>> 注释数据List = 抓取注释数据(list);
		for (List<String> 单个注释数据 : 注释数据List) {
			System.out.println("#########  V V V V V V V V V V V V 单个注释数据 V V V V V V V V V V V V V V  ###############");
			for (String 单个注释数据的每行 : 单个注释数据) {
				System.out.println(单个注释数据的每行);
			}
			System.out.println("#########  A A A A A A A A A A A A 单个注释数据 A A A A A A A A A A A A A A  ###############");
		}
	}

	private static List<List<String>> 抓取注释数据(List<String> java文件数据List) {
		boolean 注释开始 = false;
		List<List<String>> 注释数据List = new ArrayList<List<String>>();
		List<String> 注释数据 = new ArrayList<String>();
		for (int i=0;i<java文件数据List.size();i++) {
			String 每行数据 = java文件数据List.get(i);
			if(包含注释终止符号(每行数据)){
				if(注释开始){
					注释开始 = false;
					注释数据List.add(注释数据);
					注释数据 = new ArrayList<String>();
				}
			}
			if(注释开始){
				注释数据.add(每行数据);
			}
			if(每行数据.contains("/**")||每行数据.contains("/*")){
				if(!每行数据.endsWith("*/"))注释开始 = true;//XXX 此处没有翻译单行注释数据，如： /** **/ , /* */ ,// 这三类的数据
			}
		}
		return 注释数据List;
	}
	
	private static boolean 包含注释终止符号(String s){
		for(String 终止符号:注释终止符号){
			if(s.contains(终止符号))return true;
		}
		return false;
	}
	
}
