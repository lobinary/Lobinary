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
package com.lobinary.apc.util.initial;

import java.io.File;

import com.lobinary.android.common.service.communication.socket.CommunicationSocketService;
import com.lobinary.android.common.util.PropertiesUtil;
import com.lobinary.android.common.util.communication.MessageUtil;
import com.lobinary.android.common.util.communication.impl.MessageJsonTranslator;
import com.lobinary.android.common.util.factory.CommonFactory;
import com.lobinary.android.common.util.log.LogUtil;
import com.lobinary.apc.service.control.imp.WindowsService;
import com.lobinary.apc.util.log.WindowsLogUtil;

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
		CommonFactory.storeFile = new File(CommonFactory.storeFileName);
		System.out.println("配置工具类文件初始化完成:"+CommonFactory.storeFile);
		CommonFactory.setLogUtil(new WindowsLogUtil());//需要最先装配日志工具,否则日志输出将造成空指针
		
		CommonFactory.setCommunicationService(new CommunicationSocketService());
		CommonFactory.setMessageTranslator(new MessageJsonTranslator());
		CommonFactory.setBaseService(new WindowsService());

		PropertiesUtil.getPropertiesValue("test");
		LogUtil.out("初始化工具类:相关配置信息初始化完成！");
		
		MessageUtil.clientName = "Windows互控客户端";
		
	}
	
	public static boolean initial(){
		return true;
	}

}
