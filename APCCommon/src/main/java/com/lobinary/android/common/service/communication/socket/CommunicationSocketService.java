package com.lobinary.android.common.service.communication.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
	private Map<String, ConnectionBean> connectionMap = new HashMap<String, ConnectionBean>();

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
								DataInputStream dis = new DataInputStream(s.getInputStream());
								String messageStr = dis.readUTF();
								Message message = MessageUtil.string2Messag(messageStr);
								logger.debug("Socket业务交互类:已经将请求转换为Message对象："+message);
								DataOutputStream dos = new DataOutputStream(s.getOutputStream());
								Message respMessage = MessageUtil.getNewResponseMessage(Constants.MESSAGE.TYPE.ACCEPT_CONNECT);
								String respMsg = MessageUtil.message2String(respMessage);
								logger.debug("Socket业务交互类:准备返回同意连接信息:"+respMsg);
								dos.writeUTF(respMsg);
								dos.flush();
								logger.debug("Socket业务交互类:发送信息成功");
								ConnectionBean connectionBean = new ConnectionBean();
								CommunicationSocketThread socketThread = new CommunicationSocketThread(s, message.getMessageTitle());
								socketThread.start();
								connectionBean.setSocketThread(socketThread);
								connectionMap.put(message.getMessageTitle().getSendClientName(), connectionBean);
								logger.info("Socket业务交互类:与客户端(" + message.getMessageTitle().getSendClientName() + ")连接建立成功！");
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
		// TODO Auto-generated method stub
		return null;
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

}
