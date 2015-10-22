package com.lobinary.android.common.util.communication.impl;

import com.google.gson.Gson;
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

	Gson gson = new Gson();

	@Override
	public String translate2String(Message message) {
		return gson.toJson(message);
	}

	@Override
	public Message translate2Message(String messageStr) {
		return gson.fromJson(messageStr, Message.class);
	}

}
