package com.lobinary.test;

import java.util.HashMap;
import java.util.Map;


public class Test {
	
	public static void main(String[] args) {
	 	Map<String,String> patternMap = null;
		if(patternMap==null){
	 		patternMap = new HashMap<String,String>();
		 	patternMap.put("^(XS).+$", "sid-DC89EDDE-97F1-4E62-BA76-B06219006999");
		 	patternMap.put("^(DLSRUZY_XSZX).+$", "sid-A4EBD305-D8DE-4382-8D6C-C8B213879CFA");
		 	patternMap.put("^(XSDLS_HYX).+$", "sid-E0F54DF9-2978-4B2F-BD07-6C812D8F5E64");
		 	patternMap.put("^(LMRW|LMKJ|LMYJ).+$", "yeguankaitongfuhe");
		 	patternMap.put("^(TS4).+$", "sid-9B0256D4-F85F-48A3-819A-BDE75D2CF51D");
	 	}
	 	for(String pattern : patternMap.keySet()){
	 		String completeBaseNumber = "LMRWJDSF";
			boolean matchResult = completeBaseNumber.matches(pattern);
	 		if(matchResult){
	 			System.out.println(patternMap.get(pattern));
	 			return ;
	 		}
	 	}
	 	System.out.println("匹配失败");
	}

}
