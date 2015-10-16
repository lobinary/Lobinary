package com.lobinary.android.common.pojo.communication;

import java.io.Serializable;

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
	
	/**
	 * 报文头
	 */
	private MessageTitle messageTitle;
	
	/**
	 * 字符串形式的消息内容
	 */
	private String messageContent;
	
	/**
	 * 命令对象实体
	 */
	private Command command;
	

	public MessageTitle getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(MessageTitle messageTitle) {
		this.messageTitle = messageTitle;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

}
