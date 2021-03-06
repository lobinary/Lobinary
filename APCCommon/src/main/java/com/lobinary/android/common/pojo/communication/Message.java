package com.lobinary.android.common.pojo.communication;

import java.io.Serializable;

import com.lobinary.android.common.exception.APCSysException;

/**
 * 
 *	<pre> 
 *	报文交互总对象
 * 	</pre>
 * @author lobinary 
 * @since 2015年10月16日 下午10:36:54
 * @version V1.0.0 描述 : 创建该文件
 *
 */
public class Message implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4401385639582551338L;
	
	private long id;
	
	/**
	 * 报文头
	 */
	private MessageTitle messageTitle;
	
	/*
	 * 是请求报文 true 请求 false 返回
	 */
	public boolean isReq;
	
	/**
	 * 报文类型编码 Constants.Message.MESSAGE_TYPE_*
	 */
	private String messageType;
	
	/**
	 * 字符串形式消息实体
	 */
	private String messageString;
	
	/**
	 * 对象形式消息实体
	 */
	private Object messageObj;
	
	/**
	 * 命令对象实体
	 */
	private Command command;
	
	/**
	 * APC系统异常
	 */
	private APCSysException apcException;
	
	/**
	 * 远程方法
	 */
	private CommandParam remoteMethod;

	public MessageTitle getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(MessageTitle messageTitle) {
		this.messageTitle = messageTitle;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.pojo.communication.Message#messageType
	 * @return the messageType
	 */
	public String getMessageType() {
		return messageType;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.pojo.communication.Message#messageType
	 * @param messageType the messageType to set
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.pojo.communication.Message#messageObj
	 * @return the messageObj
	 */
	public Object getMessageObj() {
		return messageObj;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.pojo.communication.Message#messageObj
	 * @param messageObj the messageObj to set
	 */
	public void setMessageObj(Object messageObj) {
		this.messageObj = messageObj;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.pojo.communication.Message#messageString
	 * @return the messageString
	 */
	public String getMessageString() {
		return messageString;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.pojo.communication.Message#messageString
	 * @param messageString the messageString to set
	 */
	public void setMessageString(String messageString) {
		this.messageString = messageString;
	}

	public CommandParam getRemoteMethod() {
		return remoteMethod;
	}

	public void setRemoteMethod(CommandParam remoteMethod) {
		this.remoteMethod = remoteMethod;
	}
	
	public void addParam(String paramName,Object param){
		getCommand().getCommandParam().addParam("", "");
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.pojo.communication.Message#apcException
	 * @return the apcException
	 */
	public APCSysException getApcException() {
		return apcException;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.pojo.communication.Message#apcException
	 * @param apcException the apcException to set
	 */
	public void setApcException(APCSysException apcException) {
		this.apcException = apcException;
	}



}
