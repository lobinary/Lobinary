package com.lobinary.platform.constant;

public final class Constants {
	
	/**
	 * message信息内容格式-字符串格式
	 */
	public static final int MESSAGE_TYPE_STRING = 100001;
	/**
	 * message信息内容格式-对象格式
	 */
	public static final int MESSAGE_TYPE_OBJECT = 100002;
	
	/**
	 * message发送状态-未发送-初始化状态
	 */
	public static final int SEND_STATUS_INITIAL = 0;
	
	/**
	 * message发送状态-发送成功
	 */
	public static final int SEND_STATUS_SUCCESS = 1;
	
	/**
	 * message发送状态-发送失败
	 */
	public static final int SEND_STATUS_FAIL = -1;
	
}
