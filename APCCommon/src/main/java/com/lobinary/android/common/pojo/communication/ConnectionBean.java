package com.lobinary.android.common.pojo.communication;

import com.lobinary.android.common.service.communication.ConnectionThreadInterface;
import com.lobinary.android.common.service.communication.socket.CommunicationSocketThread;

/**
 * <pre>
 * 连接对象
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月22日 下午12:49:33
 * @version V1.0.0 描述 : 创建文件ConnectionBean
 * 
 *         
 * 
 */
public class ConnectionBean {
	
	/**
	 * 客户端Id
	 */
	public String clientId;
	
	/**
	 * 连接名称
	 */
	public String name;
	
	/**
	 * 连接ip
	 */
	public String ip;
	
	/**
	 * 连接监控子线程
	 */
	private ConnectionThreadInterface connectionThread;

	/**
	 * 初始化
	 * @param initialMessage
	 * @param communicationSocketThread 
	 */
	public ConnectionBean(Message initialMessage, CommunicationSocketThread communicationSocketThread) {
		MessageTitle messageTitle = initialMessage.getMessageTitle();
		this.clientId = messageTitle.getSendClientId();
		this.name = messageTitle.getSendClientName();
		this.ip = messageTitle.getSendClientIp();
		this.connectionThread = communicationSocketThread;
	}

	/**
	 * 
	 */
	public ConnectionBean() {
		super();
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.pojo.communication.ConnectionBean#name
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.pojo.communication.ConnectionBean#name
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * <pre>
	 * 关闭当前连接
	 * </pre>
	 *
	 */
	public boolean shutDownConnection() {
		return connectionThread.shutDownConnection();
	}

	public ConnectionThreadInterface getConnectionThread() {
		return connectionThread;
	}

	public void setConnectionThread(ConnectionThreadInterface connectionThread) {
		this.connectionThread = connectionThread;
	}


}
