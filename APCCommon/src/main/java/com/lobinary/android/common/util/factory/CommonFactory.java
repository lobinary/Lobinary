package com.lobinary.android.common.util.factory;

import java.io.File;

import com.lobinary.android.common.constants.Constants;
import com.lobinary.android.common.service.communication.CommunicationServiceInterface;
import com.lobinary.android.common.service.control.BaseServiceInterface;
import com.lobinary.android.common.util.communication.MessageTranslatorInterface;
import com.lobinary.android.common.util.log.LogUtilInterface;

/**
 * <pre>
 * 公共工厂(类似于spring存储对象)
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月22日 下午1:36:20
 * @version V1.0.0 描述 : 创建文件CommonFactory
 * 
 *         
 * 
 */
public class CommonFactory {
	
	public static String storeFileName = "apc_store_file.apc";
	public static File storeFile;
	public static String SYSTEM_CODE = Constants.SYSTEM_CODE_WINDOWS;//默认windows
	
	/**
	 * 日志工具类
	 */
	private static LogUtilInterface logUtil;
	/**
	 * 报文转换器
	 */
	private static MessageTranslatorInterface messageTranslator;
	
	/**
	 * 基本控制业务实现类
	 */
	private static BaseServiceInterface baseService;
	
	/**
	 * 交互业务类
	 */
	private static CommunicationServiceInterface communicationService;

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.util.factory.CommonFactory#logUtil
	 * @return the logUtil
	 */
	public static LogUtilInterface getLogUtil() {
		return logUtil;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.util.factory.CommonFactory#logUtil
	 * @param logUtil the logUtil to set
	 */
	public static void setLogUtil(LogUtilInterface logUtil) {
		CommonFactory.logUtil = logUtil;
		System.out.println("日志工具类初始化完成："+logUtil);
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.util.factory.CommonFactory#messageTranslator
	 * @return the messageTranslator
	 */
	public static MessageTranslatorInterface getMessageTranslator() {
		return messageTranslator;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.util.factory.CommonFactory#messageTranslator
	 * @param messageTranslator the messageTranslator to set
	 */
	public static void setMessageTranslator(MessageTranslatorInterface messageTranslator) {
		CommonFactory.messageTranslator = messageTranslator;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.util.factory.CommonFactory#baseService
	 * @return the baseService
	 */
	public static BaseServiceInterface getBaseService() {
		return baseService;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.util.factory.CommonFactory#baseService
	 * @param baseService the baseService to set
	 */
	public static void setBaseService(BaseServiceInterface baseService) {
		CommonFactory.baseService = baseService;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.util.factory.CommonFactory#communicationService
	 * @return the communicationService
	 */
	public static CommunicationServiceInterface getCommunicationService() {
		return communicationService;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.util.factory.CommonFactory#communicationService
	 * @param communicationService the communicationService to set
	 */
	public static void setCommunicationService(CommunicationServiceInterface communicationService) {
		CommonFactory.communicationService = communicationService;
	}

	
}
