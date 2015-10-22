package com.lobinary.android.common.service.communication.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.android.common.constants.Constants;
import com.lobinary.android.common.pojo.communication.ConnectionBean;
import com.lobinary.android.common.pojo.communication.Message;
import com.lobinary.android.common.service.communication.CommunicationServiceInterface;
import com.lobinary.android.common.util.communication.MessageUtil;
import com.lobinary.android.common.util.log.LogUtil;

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
	 * 是否处于关闭服务器状态
	 */
	private boolean isStopStatus = false;
	int temp = 2;
	ServerSocket serverSocket;
	public DataOutputStream sendstr = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lobinary.android.common.service.communication.
	 * CommunicationServiceInterface#startServer()
	 */
	@Override
	public boolean startServer() {
		try {
			logger.info("准备开启服务器");
			serverSocket = new ServerSocket(PORT);

			Socket s = new Socket();
			logger.info("Socket服务器启动成功,正在监听连接请求.");
			isStopStatus = false;
			while (true) {

				s = serverSocket.accept();
				logger.info("接收到连接请求");
				if (isPauseStatus) {
					logger.info("因服务器处于暂停状态,准备反馈拒绝连接请求");
					DataOutputStream dos = new DataOutputStream(s.getOutputStream());
					Message respMessage = MessageUtil.getNewResponseMessage(Constants.MESSAGE.TYPE.REJECT_CONNECT);
					dos.writeUTF(MessageUtil.message2String(respMessage));
					dos.flush();
					continue;
				} else {

					DataInputStream dis = new DataInputStream(s.getInputStream());
					String messageStr = dis.readUTF();
					Message message = MessageUtil.string2Messag(messageStr);

					DataOutputStream dos = new DataOutputStream(s.getOutputStream());
					Message respMessage = MessageUtil.getNewResponseMessage(Constants.MESSAGE.TYPE.ACCEPT_CONNECT);
					dos.writeUTF(MessageUtil.message2String(respMessage));
					dos.flush();

					new CommunicationSocketThread(s).start();
					logger.info("与客户端(" + message.getMessageTitle().getSendClientName() + ")连接建立成功！");
					continue;
				}

			}
		} catch (Exception e) {
			logger.error("业务交互类:创建Socket失败,失败原因为:", e);
		}
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
			if(serverSocket!=null){
				serverSocket.close();
				isStopStatus = true;
			}
		} catch (IOException e) {
			logger.error("关闭Socket服务器发生异常：",e);
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
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lobinary.android.common.service.communication.
	 * CommunicationServiceInterface#removeAllConnection()
	 */
	@Override
	public boolean removeAllConnection() {
		// TODO Auto-generated method stub
		return false;
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
	 * #sendMessage(com.lobinary.android.common.pojo.communication.Message)
	 */
	@Override
	public Message sendMessage(Message message) {
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
