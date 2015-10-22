package com.lobinary.android.common.util.communication.impl;

import com.lobinary.android.common.pojo.communication.Message;
import com.lobinary.android.common.util.communication.MessageTranslatorInterface;

/**
 * 
 *	<pre> 
 *	报文json转换器
 * 	</pre>
 * @author lobinary 
 * @since 2015年10月18日 上午12:12:23
 * @version V1.0.0 描述 : 创建该文件
 *
 */
public class MessageJsonTranslator implements MessageTranslatorInterface {

	@Override
	public String translate2String(Message message) {
		return "hello";
	}

	@Override
	public Message translate2Message(String messageStr) {
		// TODO Auto-generated method stub
		return null;
	}

}
