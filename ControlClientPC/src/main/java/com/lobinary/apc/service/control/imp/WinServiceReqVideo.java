/*
 * @(#)WinServiceReqVideo.java     V1.0.0      @上午12:27:50
 *
 * 项目名称: ControlClientPC
 *
 * 更改 信息:
 *    作者        				   日期        			描述
 *    ============  	================  =======================================
 *    lobinary       	  2015年10月24日    	创建文件
 *
 */
package com.lobinary.apc.service.control.imp;

import com.lobinary.android.common.pojo.communication.Command;
import com.lobinary.android.common.pojo.communication.Message;
import com.lobinary.android.common.util.communication.MessageUtil;

/**
 * <pre>
 * 
 * </pre>
 * @author ljrxxx
 * @version V1.0.0 描述 : 创建文件WinServiceReqVideo
 * 
 *         
 * 
 */
public class WinServiceReqVideo {
	
		MessageUtil winReqVideoMsgU = new MessageUtil();
		
		public Message assembleWinReqVideoMsg(){
			Command winReqVideoCmd = new Command();
			Message winReqVideoMsg = winReqVideoMsgU.getNewRequestMessage("COMMAND");
			
			return winReqVideoMsg;
			
		}
		
}
