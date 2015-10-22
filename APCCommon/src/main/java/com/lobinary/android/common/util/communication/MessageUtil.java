package com.lobinary.android.common.util.communication;

import java.util.Date;

import com.lobinary.android.common.constants.Constants;
import com.lobinary.android.common.pojo.communication.Message;
import com.lobinary.android.common.pojo.communication.MessageTitle;

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
	
	public static String clientId;
	public static String clientIp;
	public static String clientName;
	
	
	
	public Message getNewRequestMessage(String messageType){
		Message message = new Message();
		
		MessageTitle messageTitle = getMessageTitle();
		
		message.setMessageTitle(messageTitle);
		
		message.setMessageType(messageType);
		
		if(Constants.MESSAGE.TYPE.REQ_TIME.equals(messageType)){
			return message;
		}else if(Constants.MESSAGE.TYPE.COMMAND.equals(messageType)){
			
		}
		return null;
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
		messageTitle.setSendClientId("");
		messageTitle.setSendClientIp("");
		messageTitle.setSendClientName("");
		messageTitle.setSendTime(new Date());
		return messageTitle;
	}
	
	
	

}
