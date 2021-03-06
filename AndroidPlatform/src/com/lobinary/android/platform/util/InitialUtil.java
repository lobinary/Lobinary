/*
 * @(#)InitialUtil.java     V1.0.0      @下午1:52:39
 *
 * 项目名称: ControlClientPC
 *
 * 更改 信息:
 *    作者        				   日期        			描述
 *    ============  	================  =======================================
 *    lobinary       	  2015年10月22日    	创建文件
 *
 */
package com.lobinary.android.platform.util;

import com.lobinary.android.common.util.communication.impl.MessageJsonTranslator;
import com.lobinary.android.common.util.factory.CommonFactory;
import com.lobinary.android.common.util.log.LogUtil;
import com.lobinary.android.platform.service.communication.AndroidCommunicationSocketService;

/**
 * <pre>
 * 初始化工具类
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月22日 下午1:52:39
 * @version V1.0.0 描述 : 创建文件InitialUtil
 * 
 *         
 * 
 */
public class InitialUtil {
	
	static{
		System.out.println("初始化工具类准备初始化相关配置");
		
		CommonFactory.setLogUtil(new AndroidLogUtil());//需要最先装配日志工具,否则日志输出将造成空指针
		CommonFactory.SYSTEM_CODE = com.lobinary.android.common.constants.Constants.SYSTEM_CODE_ANDROID;
		if(!AndroidFileUtil.isExist(CommonFactory.storeFileName)){
			CommonFactory.androidNewStoreFile = true;
		}
		CommonFactory.storeFile = AndroidFileUtil.getFile(CommonFactory.storeFileName);
		CommonFactory.setCommunicationService(new AndroidCommunicationSocketService());
		CommonFactory.setMessageTranslator(new MessageJsonTranslator());
//		CommonFactory.setBaseService(new AndroidService());

//		PropertiesUtil.getPropertiesValue("test");
		

		LogUtil.out("初始化工具类:相关配置信息初始化完成！");
		
	}
	
	public static boolean initial(){
		return true;
	}

}
