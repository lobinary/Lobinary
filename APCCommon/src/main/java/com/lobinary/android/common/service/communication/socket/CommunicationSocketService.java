package com.lobinary.android.common.service.communication.socket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.android.common.constants.Constants;
import com.lobinary.android.common.pojo.communication.ConnectionBean;
import com.lobinary.android.common.pojo.communication.Message;
import com.lobinary.android.common.service.communication.CommunicationServiceInterface;
import com.lobinary.android.common.util.communication.MessageUtil;

/**
 * <pre>
 *  通信Socket 实现类
 * </pre>
 * 
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月22日 上午11:26:37
 * @version V1.0.0 描述 : 创建文件CommunicationSocketService
 * 
 * 
 * 
 */
public class CommunicationSocketService implements CommunicationServiceInterface {

	private static Logger logger = LoggerFactory.getLogger(CommunicationSocketService.class);

	/**
	 * socket端口号
	 */
	private int PORT = 6666;
	/**
	 * 是否处于暂停接受请求状态
	 */
	private boolean isPauseStatus = false;
	/**
	 * 服务器运行状态
	 */
	public boolean isRunnStatus = false;

	/**
	 * socket服务器实体对象
	 */
	ServerSocket serverSocket;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lobinary.android.common.service.communication.
	 * CommunicationServiceInterface#startServer()
	 */
	@Override
	public synchronized boolean startServer() {

		if (isRunnStatus) {
			return true;
		}
		new Thread() {

			@Override
			public void run() {
				super.run();
				try {
					logger.info("Socket业务交互类:准备开启服务器");
					serverSocket = new ServerSocket(PORT);

					Socket s = new Socket();
					logger.info("Socket业务交互类:Socket服务器启动成功,正在监听连接请求.");
					isRunnStatus = true;
					while (true) {

						s = serverSocket.accept();
						logger.info("Socket业务交互类:接收到连接请求");
						if (isPauseStatus) {
							try {
								logger.info("Socket业务交互类:因服务器处于暂停状态,拒绝了一个新连接请求.");
								DataOutputStream dos = new DataOutputStream(s.getOutputStream());
								Message respMessage = MessageUtil.getNewResponseMessage(Constants.MESSAGE.TYPE.REJECT_CONNECT);
								dos.writeUTF(MessageUtil.message2String(respMessage));
								dos.flush();
							} catch (Exception e) {
								logger.error("Socket业务交互类:拒绝新连接请求时发生异常,异常原因如下:", e);
							}
							continue;
						} else {

							try {
								CommunicationSocketThread socketThread = new CommunicationSocketThread(s);
								socketThread.start();
							} catch (Exception e) {
								logger.error("Socket业务交互类:在创建与新客户端连接的过程中发生异常,异常原因如下:", e);
							}
							continue;
						}
					}
				} catch (Exception e) {
					logger.error("Socket业务交互类:创建Socket失败,异常原因如下:", e);
				}

			}

		}.start();

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lobinary.android.common.service.communication.
	 * CommunicationServiceInterface#stopServer()
	 */
	@Override
	public boolean stopServer() {
		try {
			if (serverSocket != null) {
				serverSocket.close();
				isRunnStatus = false;
			}
		} catch (IOException e) {
			logger.error("关闭Socket服务器发生异常,异常原因如下:", e);
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lobinary.android.common.service.communication.
	 * CommunicationServiceInterface#pauseServer()
	 */
	@Override
	public boolean pauseServer() {
		isPauseStatus = true;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lobinary.android.common.service.communication.
	 * CommunicationServiceInterface#resumePausingServer()
	 */
	@Override
	public boolean resumePausingServer() {
		isPauseStatus = false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lobinary.android.common.service.communication.
	 * CommunicationServiceInterface#removeAllConnection()
	 */
	@Override
	public boolean removeAllConnection() {
		try {
			for (String key : connectionMap.keySet()) {
				connectionMap.get(key).shutDownConnection();
			}
		} catch (Exception e) {
			logger.error("Socket交互业务类:移除当前所有连接时发生异常,异常原因如下:", e);
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lobinary.android.common.service.communication.
	 * CommunicationServiceInterface#getCanConnecList()
	 */
	@Override
	public List<ConnectionBean> getCanConnectList() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lobinary.android.common.service.communication.
	 * CommunicationServiceInterface
	 * #connect(com.lobinary.android.common.pojo.communication.ConnectionBean)
	 */
	@Override
	public ConnectionBean connect(ConnectionBean connectionBean) {
		new Thread(){
			@Override
			public void run() {try {
				Socket servertome = new Socket("127.0.0.1",6666);
				BufferedReader in = new BufferedReader(new InputStreamReader(servertome.getInputStream(), "UTF8"));
				PrintWriter out = new PrintWriter(servertome.getOutputStream(), true);
				Message message = MessageUtil.getNewRequestMessage(Constants.MESSAGE.TYPE.REQUEST_CONNECT);
				message.getMessageTitle().setSendClientId("newi1d");
				out.println(MessageUtil.message2String(message));
				out.flush();
				String str = in.readLine();
				logger.info("#####与客户端连接成功,接收到客户端信息:"+str);
				

				while(true){
					logger.info("#####等待客户端反馈消息");
					String str2 = in.readLine();
					logger.info("#####与客户端连接成功,接收到客户端信息2:"+str2);
					out.println(str2);
					out.flush();
				}
				
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
				super.run();
			}
			
		}.start();
		
		return connectionBean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lobinary.android.common.service.communication.
	 * CommunicationServiceInterface
	 * #sendMessage(com.lobinary.android.common.pojo.communication.Message)
	 */
	@Override
	public Message sendMessage(ConnectionBean connectionBean, Message message) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lobinary.android.common.service.communication.
	 * CommunicationServiceInterface
	 * #sendMessageToAll(com.lobinary.android.common.pojo.communication.Message)
	 */
	@Override
	public List<Message> sendMessageToAll(Message message) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.service.communication.socket.CommunicationSocketService#connectionMap
	 * @return the connectionMap
	 */
	public static Map<String, ConnectionBean> getConnectionMap() {
		return connectionMap;
	}
	
	public static void addConnection(String clientId,ConnectionBean connectionBean ){
		connectionMap.put(clientId, connectionBean);
		logger.info("Socket业务交互类:新连接(clientId:"+clientId+")被添加,当前连接总数为:"+connectionMap.size()+"个");
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.service.communication.socket.CommunicationSocketService#connectionMap
	 * @param connectionMap the connectionMap to set
	 */
	public static void setConnectionMap(Map<String, ConnectionBean> connectionMap) {
		connectionMap = connectionMap;
	}

}
