package com.lobinary.platform.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * 配置文件工具类
 * 
 * @author lvbin 
 * @since 2014年10月21日 下午5:05:54
 * @version V1.0.0 Description : Create this file
 * 
 * IssueCore:CheckAccSysService.java
 *         
 *
 */
public class PropertiesUtil {
	
	private static Properties properties;
	
	static{
	try {
		String propFile = "system.properties";
        properties = new Properties();
        InputStream file = PropertiesUtil.class.getClassLoader().getResourceAsStream(propFile);
		properties.load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getValue(String key){
		try {
			return properties.get(key).toString();
		} catch (NullPointerException e) {
			return "未发现该配置信息";
		}
		
	}

}
