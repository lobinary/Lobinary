package com.lobinary.normal.cav.service;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lobinary.normal.cav.util.HttpUtil;

public class BtSearchService {

	public List<String> search(String searchName) throws SocketTimeoutException{
		List<String> resultList = new ArrayList<String>();
		resultList.addAll(searchFromBtBook(searchName));
		return resultList;
	}
	
	public List<String> searchFromBtBook(String searchName) throws SocketTimeoutException{
		List<String> resultList = new ArrayList<String>();
		Map<String,String> paramMap = new HashMap<String,String>();
		String rs = HttpUtil.sendPostRequest("http://www.btbook.net/search/复仇者联盟2.html", paramMap, "UTF-8", "UTF-8");
		String[] rsArray = rs.split("<div class=\"search-item\">");
		System.out.println("+++++++++++++++++++++++++++");
		for(String s : rsArray){
			System.out.println(s);
			System.out.println("=============================");
		}
		System.out.println("+++++++++++++++++++++++++++");
		return resultList;
	}
	
	public static void main(String[] args) throws SocketTimeoutException {
		BtSearchService bs = new BtSearchService();
		bs.search("");
	}
	
}
