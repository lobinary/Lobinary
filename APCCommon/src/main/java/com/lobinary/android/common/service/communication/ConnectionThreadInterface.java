package com.lobinary.android.common.service.communication;

import com.lobinary.android.common.pojo.communication.Message;
import com.lobinary.android.common.service.control.BaseServiceInterface;

/**
 * <pre>
 * 连接线程接口
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月24日 下午8:05:40
 * @version V1.0.0 描述 : 创建文件ConnectionThreadInterface
 * 
 *         
 * 
 */
public abstract class ConnectionThreadInterface extends Thread implements BaseServiceInterface{
	
	/**
	 * 
	 * <pre>
	 * 发送信息
	 * </pre>
	 *
	 * @param message 返回信息
	 * @return
	 */
	public abstract Message sendMessage(Message message);
	
	/**
	 * <pre>
	 * 关闭当前连接
	 * </pre>
	 *
	 * @return
	 */
	public abstract boolean shutDownConnection();

}
