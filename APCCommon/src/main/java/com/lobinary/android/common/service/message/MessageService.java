package com.lobinary.android.common.service.message;

import com.lobinary.android.common.constants.CodeDescConstants;
import com.lobinary.android.common.constants.Constants;
import com.lobinary.android.common.exception.APCSysException;
import com.lobinary.android.common.pojo.communication.Message;
import com.lobinary.android.common.util.communication.MessageUtil;

/**
 * <pre>
 * 报文业务类
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月21日 下午5:12:33
 * @version V1.0.0 描述 : 创建文件MessageService
 * 
 *         
 * 
 */
public class MessageService {
	
	/**
	 * 基本业务接口(需根据不同客户端装配自己的实现方法)
	 */
	BaseServiceInterface baseService;
	
	/**
	 * 
	 * <pre>
	 * 解析请求报文，调用相关业务，返回 respon报文
	 * </pre>
	 *
	 * @return respon报文
	 */
	public Message parseRequestMessage(Message message){
		
		String messageType = message.getMessageType();
		
		if(Constants.MESSAGE.TYPE.REQ_TIME.equals(messageType)){
			long currentTime = baseService.getCurrentTime();
			message.setMessageObj(currentTime);
		}else if(Constants.MESSAGE.TYPE.COMMAND.equals(messageType)){
			
		}else{
			throw new APCSysException(CodeDescConstants.SERVICE_MESSAGE_ERROR_MESSAGE_TYPE, "报文类型("+messageType+")错误");
		}
		
		message.setMessageTitle(MessageUtil.getMessageTitle());//如果执行成功,将准备装载返回报文头
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
	public Object parseResponseMessage(Message message){
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

}
