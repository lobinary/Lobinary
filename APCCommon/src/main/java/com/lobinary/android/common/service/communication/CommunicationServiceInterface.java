package com.lobinary.android.common.service.communication;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lobinary.android.common.pojo.communication.ConnectionBean;
import com.lobinary.android.common.pojo.communication.Message;

/**
 * <pre>
 * 安卓客户端通信业务类
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月12日 下午1:20:48
 * @version V1.0.0 描述 : 创建文件AndoridClientCommunicatorInterface
 * 
 *         
 * 
 */
public interface CommunicationServiceInterface {
	
	/**
	 * <pre>
	 * 连接列表 
	 * key:clientId 
	 * value:连接实体
	 */
	public static Map<String, ConnectionBean> connectionMap = new HashMap<String, ConnectionBean>();
	
	/**
	 * 
	 * <pre>
	 * 启动服务器
	 * </pre>
	 *
	 * @return
	 */
	public boolean startServer();
	
	/**
	 * 
	 * <pre>
	 * 关闭服务器
	 * </pre>
	 *
	 * @return
	 */
	public boolean stopServer();
	
	/**
	 * 
	 * <pre>
	 * 暂停服务器连接的请求(原始连接继续运行)
	 * </pre>
	 *
	 * @return
	 */
	public boolean pauseServer();
	
	/**
	 * 
	 * <pre>
	 * 恢复暂停中的服务器
	 * </pre>
	 *
	 * @return
	 */
	public boolean resumePausingServer();
	
	/**
	 * 
	 * <pre>
	 * 移除所有已连接通信
	 * </pre>
	 *
	 * @return
	 */
	public boolean removeAllConnection();
	
	/**
	 * 
	 * <pre>
	 * 刷新可以连接的服务器List
	 * <font color="red"><b>(连接list会不定时有变动,可能需要协助定时刷新服务,具体实现需要考虑该问题)</b></font>
	 * </pre>
	 *
	 * @return 服务器列表
	 */
	public boolean refreshConnectableList();
	
	/**
	 * 
	 * <pre>
	 * 连接指定服务器 想操作已经建立的连接 请在 {@link #connectionMap} 中查找
	 * </pre>
	 *
	 * @param connectionBean
	 * @return
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public void connect(ConnectionBean connectionBean) throws UnknownHostException, IOException;
	
	/**
	 * 
	 * <pre>
	 * 发送报文信息
	 * </pre>
	 *
	 * @param message
	 * @return
	 */
	public Message sendMessage(ConnectionBean connectionBean,Message message);
	
	/**
	 * 
	 * <pre>
	 * 向所有连接发送消息
	 * </pre>
	 *
	 * @param message 不同连接返回的不同信息
	 * @return
	 */
	public Map<ConnectionBean,Message> sendMessageToAll(Message message);
	
	
	
}
