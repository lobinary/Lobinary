package com.lobinary.android.common.util.communication;

import com.lobinary.android.common.pojo.communication.Message;

/**
 * 
 *	<pre> 
 *	 报文转换工具类接口
 * 	</pre>
 * @author lobinary 
 * @since 2015年10月18日 上午12:06:35
 * @version V1.0.0 描述 : 创建该文件
 *
 */
public interface MessageTranslatorInterface {
	
	
	/**
	 * 将报文转换成字符串
	 * @param message
	 * @return
	 */
	public String translate2String(Message message);
	
	/**
	 * 将字符串转换成报文实体类
	 * @param messageStr
	 * @return
	 */
	public Message translate2Message(String messageStr);

}
