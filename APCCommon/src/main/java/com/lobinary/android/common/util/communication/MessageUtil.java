package com.lobinary.android.common.util.communication;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.android.common.constants.CodeDescConstants;
import com.lobinary.android.common.constants.Constants;
import com.lobinary.android.common.exception.APCSysException;
import com.lobinary.android.common.pojo.communication.Command;
import com.lobinary.android.common.pojo.communication.Message;
import com.lobinary.android.common.pojo.communication.MessageTitle;
import com.lobinary.android.common.service.control.BaseServiceInterface;
import com.lobinary.android.common.util.ClientUtil;
import com.lobinary.android.common.util.factory.CommonFactory;

/**
 * <pre>
 * 报文工具类
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月21日 下午5:16:03
 * @version V1.0.0 描述 : 创建文件MessageUtil
 * 
 *         
 * 
 */
public class MessageUtil {


	private static Logger logger = LoggerFactory.getLogger(MessageUtil.class);
	
	public static MessageTranslatorInterface messageTranlator = CommonFactory.getMessageTranslator();
	private static BaseServiceInterface baseService = CommonFactory.getBaseService();
	
	/**
	 * 
	 * <pre>
	 * 是否是断开连接
	 * </pre>
	 *
	 * @param messageStr
	 * @return
	 */
	public static boolean isDisconnectionReqMessage(String messageStr){
		return Constants.MESSAGE.TYPE.DISCONNECT.equals(string2Messag(messageStr).getMessageType());
	}
	

	/**
	 * 
	 * <pre>
	 * 获取新请求报文实体
	 * </pre>
	 *
	 * @param messageType
	 * @return
	 */
	public static Message getNewRequestMessage(String messageType){
		
		if(messageType==null){
			messageType = Constants.MESSAGE.TYPE.COMMAND;
		}
		Message message = new Message();
		message.isReq = true;
		
		MessageTitle messageTitle = getMessageTitle();
		message.setMessageTitle(messageTitle);
		message.setMessageType(messageType);
		
		if(Constants.MESSAGE.TYPE.COMMAND.equals(messageType)){
			message.setCommand(new Command());
		}
		return message;
	}
	
	/**
	 * 
	 * <pre>
	 * 获取新返回报文实体
	 * </pre>
	 *
	 * @param messageType
	 * @return
	 */
	public static Message getNewResponseMessage(String messageType){
		Message message = new Message();
		message.isReq = false;
		MessageTitle messageTitle = getMessageTitle();
		message.setMessageTitle(messageTitle);
		message.setMessageType(messageType);
		
		if(Constants.MESSAGE.TYPE.REJECT_CONNECT.equals(messageType)){

		}else if(Constants.MESSAGE.TYPE.COMMAND.equals(messageType)){
			
		}
		return message;
	}



	/**
	 * <pre>
	 * 获取报文头
	 * </pre>
	 *
	 * @return
	 */
	public static MessageTitle getMessageTitle() {
		MessageTitle messageTitle = new MessageTitle();
		messageTitle.setSendClientId(ClientUtil.clientId);
		messageTitle.setSendClientIp(ClientUtil.clientIp);
		messageTitle.setSendClientName(ClientUtil.clientName);
		messageTitle.setSendTime(new Date());
		return messageTitle;
	}
	
	/**
	 * 
	 * <pre>
	 * 报文转字符串
	 * </pre>
	 *
	 * @param message
	 * @return
	 */
	public static String message2String(Message message){
		return messageTranlator.translate2String(message);
	}
	
	/**
	 * 
	 * <pre>
	 * 字符串转报文
	 * </pre>
	 *
	 * @param messageStr
	 * @return
	 */
	public static Message string2Messag(String messageStr){
		return messageTranlator.translate2Message(messageStr);
	}
	
	/**
	 * 
	 * <pre>
	 * 将字符串messge对象解析，并调用执行方法执行结束，生成返回字符串message
	 * </pre>
	 *
	 * @param messageStr
	 * @return
	 */
	public static String parseReqMessageStr2RespMessageStr(String messageStr){
		return message2String(parseRequestMessage(string2Messag(messageStr)));
	}
	
	/**
	 * 
	 * <pre>
	 * 解析请求报文，调用相关业务，返回 respon报文
	 * </pre>
	 *
	 * @return respon报文
	 */
	public static Message parseRequestMessage(String messageStr){
		return parseRequestMessage(string2Messag(messageStr));
	}
	
	/**
	 * 
	 * <pre>
	 * 解析请求报文，调用相关业务，返回 respon报文
	 * </pre>
	 *
	 * @return respon报文
	 */
	public static Message parseRequestMessage(Message message){
		Message respMessage = MessageUtil.migrate(message);
		try {
			String messageType = message.getMessageType();

			if(Constants.MESSAGE.TYPE.REQ_TIME.equals(messageType)){
				long currentTime = baseService.getCurrentTime();
				message.setMessageObj(currentTime);
			}else if(Constants.MESSAGE.TYPE.COMMAND.equals(messageType)){
				Command command = message.getCommand();
				String commandCode = command.getCommandCode();
				if(Constants.MESSAGE.COMMAND.CODE.REMOTE_METHOD.equals(commandCode)){
					String methodName = command.getRemoteMethodName();
					Class<?> clazz = baseService.getClass(); 
					List<Object> remoteMethodParam = command.getRemoteMethodParam();
					Method baseMethod = null;
					if(remoteMethodParam!=null){
						Class<?>[] paramClassArray = new Class[remoteMethodParam.size()];
						for (int i = 0; i < remoteMethodParam.size(); i++) {
							paramClassArray[i] = remoteMethodParam.get(i).getClass();
						}
						baseMethod = clazz.getDeclaredMethod(methodName,paramClassArray); 
						Object returnObj = baseMethod.invoke(baseService,remoteMethodParam.toArray()); 
						respMessage.setMessageObj(returnObj);
					}else{
						baseMethod = clazz.getDeclaredMethod(methodName); 
						Object returnObj = baseMethod.invoke(baseService); 
						respMessage.setMessageObj(returnObj);
					}

				}
				logger.info("报文工具类:接收到客户端调用命令,调用命令成功");
			}else{
				logger.info("报文工具类:接收到客户端调用命令,调用命令失败");
				throw new APCSysException(CodeDescConstants.SERVICE_MESSAGE_ERROR_MESSAGE_TYPE, "报文类型("+messageType+")错误");
			}
			
			respMessage.setMessageTitle(MessageUtil.getMessageTitle());//如果执行成功,将准备装载返回报文头
		} catch (Exception e) {
			logger.info("报文工具类:接收到客户端调用命令,调用命令失败",e);
			throw new APCSysException(CodeDescConstants.SERVICE_EXCEPTION, e);
		}
		return respMessage;
	}
	
	/**
	 * <pre>
	 * 根据请求数据,装配返回数据
	 * </pre>
	 *
	 * @param message
	 * @return
	 */
	private static Message migrate(Message message) {
		message.setMessageTitle(getMessageTitle());
		message.isReq = false;
		return message;
	}

	/**
	 * 
	 * <pre>
	 * 解析返回报文
	 * </pre>
	 *
	 * @param message
	 * @return
	 */
	public static Object parseResponseMessage(Message message){
		Object obj = null;
		String messageType = message.getMessageType();
		
		if(Constants.MESSAGE.TYPE.REQ_TIME.equals(messageType)){
			obj = message.getMessageObj();
		}else if(Constants.MESSAGE.TYPE.COMMAND.equals(messageType)){
			
		}else{
			throw new APCSysException(CodeDescConstants.SERVICE_MESSAGE_ERROR_MESSAGE_TYPE, "报文类型("+messageType+")错误");
		}
		
		message.setMessageTitle(MessageUtil.getMessageTitle());//如果执行成功,将准备装载返回报文头
		return obj;
	}

	/**
	 * <pre>
	 * 装配异常信息
	 * </pre>
	 *
	 * @param respMessage
	 * @param string
	 * @param javaException
	 * @param t
	 * @return
	 */
	public static Message assembleExceptionMessage(Message respMessage, String errDesc, CodeDescConstants exceptionCode,APCSysException apcException, Throwable t) {
		if(apcException==null){
			if(exceptionCode==null){
				apcException = new APCSysException(CodeDescConstants.PUB_SYSTEM_EXCEPTION);
			}else{
				apcException = new APCSysException(exceptionCode);
			}
		}
		apcException.setErrDesc(errDesc);
		apcException.setOrignalException(t);
		respMessage.setApcException(apcException);
		return respMessage;
	}
	

}
