package com.lobinary.andorid.test;

import com.lobinary.android.common.constants.Constants.MESSAGE.COMMAND;
import com.lobinary.android.common.pojo.communication.Message;
import com.lobinary.android.common.util.communication.MessageUtil;

/**
 * <pre>
 * 
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月27日 下午10:55:01
 * @version V1.0.0 描述 : 创建文件Test
 * 
 *         
 * 
 */
public class Test {
	MessageUtil mu = new MessageUtil();
	public void haha(){
		Message msg = mu.getNewRequestMessage("COMMAND");
		
	}
}
