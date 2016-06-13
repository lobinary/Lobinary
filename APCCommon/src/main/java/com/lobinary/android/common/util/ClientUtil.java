package com.lobinary.android.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.android.common.constants.Constants;
import com.lobinary.android.common.util.date.DateUtil;

/**
 * <pre>
 * 客户端工具类
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015-11-17 下午4:09:34
 * @version V1.0.0 描述 : 创建文件ClientUtil
 * 
 *         
 * 
 */
public class ClientUtil {

	private static Logger logger = LoggerFactory.getLogger(ClientUtil.class);
	
	public static String clientId;
	public static String clientIp;
	public static String clientName;
	
	static{
		initialClientInfo();
	}
	
	public static void saveClientName(String newClientName){
		logger.debug("获取到客户端名称被更改为："+clientName);
		clientName = newClientName;
		PropertiesUtil.saveFileValue(Constants.CLIENT.CLIENT_NAME, clientName);
	}


	/**
	 * 
	 * <pre>
	 * 初始化客户端信息
	 * </pre>
	 *
	 */
	public static void initialClientInfo() {
		clientId = (String) PropertiesUtil.getFileValue(Constants.CLIENT.CLIENT_ID);
		if(clientId==null){
			clientId = generateClientId();
			clientName = clientId;
			PropertiesUtil.saveFileValue(Constants.CLIENT.CLIENT_ID, clientId);
			PropertiesUtil.saveFileValue(Constants.CLIENT.CLIENT_NAME, clientName);
		}
		clientName = (String) PropertiesUtil.getFileValue(Constants.CLIENT.CLIENT_NAME);
		logger.info("报文工具类初始化完成:客户端名称"+clientName+"客户端Id:"+clientId);
	}

	/**
	 * <pre>
	 * 生成客户端ID
	 * </pre>
	 *
	 * @return
	 */
	private static String generateClientId() {
		return DateUtil.getCurrDateTime("yyyyMMddhh24mmssSSS");
	}
}
