package com.lobinary.android.platform.remote;

/**
 * 交互业务接口
 * @author Lobinary
 *
 */
public interface CommunicationServiceInterface {
	
	/**
	 * 发送信息
	 * @param message
	 * @return
	 */
	public boolean sendMessage(String message);
	
	/**
	 * 发送对象
	 * @param obj
	 * @return
	 */
	public boolean sendObject(Object obj);

}
