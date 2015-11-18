/*
 * @(#)AndroidClientCommunicatorInterface.java     V1.0.0      @下午1:31:00
 *
 * 项目名称: ControlClientPC
 *
 * 更改 信息:
 *    作者        				   日期        			描述
 *    ============  	================  =======================================
 *    lobinary       	  2015年10月12日    	创建文件
 *
 */
package com.lobinary.apc.remote.android;

/**
 * <pre>
 * 安卓客户端通信接口
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月12日 下午1:31:00
 * @version V1.0.0 描述 : 创建文件AndroidClientCommunicator
 * 
 *         
 * 
 */
public interface AndroidClientCommunicatorInterface {

	/**
	 * 
	 * <pre>
	 * 向安卓客户端发送字符串报文
	 * </pre>
	 *
	 * @param messge 发送内容
	 * @return 返回内容
	 */
	public String sendMessage(String messge);
	
}
