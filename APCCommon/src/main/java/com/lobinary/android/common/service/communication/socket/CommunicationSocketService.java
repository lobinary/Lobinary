package com.lobinary.android.common.service.communication.socket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.android.common.constants.Constants;
import com.lobinary.android.common.pojo.communication.ConnectionBean;
import com.lobinary.android.common.pojo.communication.Message;
import com.lobinary.android.common.pojo.communication.MessageTitle;
import com.lobinary.android.common.service.communication.CommunicationServiceInterface;
import com.lobinary.android.common.util.NetUtil;
import com.lobinary.android.common.util.communication.MessageUtil;
import com.lobinary.android.common.util.factory.CommonFactory;

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

	public long connectionMapVersionId = 0;
	
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
					logger.debug("Socket业务交互类:准备开启服务器");
					serverSocket = new ServerSocket(PORT);

					logger.info("Socket业务交互类:Socket服务器启动成功,正在监听连接请求......");
					isRunnStatus = true;
					while (true) {
						try {
							Socket s = new Socket();
							s = serverSocket.accept();
							logger.info("Socket业务交互类:接收到连接请求:" + s.getRemoteSocketAddress());
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
									CommunicationSocketThread socketThread = new CommunicationSocketThread(s, true);
									socketThread.start();
								} catch (Exception e) {
									logger.error("Socket业务交互类:在创建与新客户端连接的过程中发生异常,异常原因如下:", e);
								}
								continue;
							}
						} catch (Exception e) {
							logger.error("Socket业务交互类:服务器接收到新连接后,与其建立连接过程中发生异常", e);
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
	public boolean refreshConnectableList() {
		
		new Thread() {
			@Override
			public void run() {
				List<Thread> runThreadList = new ArrayList<Thread>();
				long connectionMapVersionIdTemp = connectionMapVersionId+1;
				List<String> localIpList = NetUtil.getLocalIpList();
				for (String localIp : localIpList) {
					List<String> lanIpList = NetUtil.getLANIp(localIp);
					for (final String lanIp : lanIpList) {
						final ConnectionBean connectionBean = new ConnectionBean();
						connectionBean.ip = lanIp;
						connectionBean.refreshId = connectionMapVersionIdTemp;
						Thread t = new Thread() {
							@Override
							public void run() {
								try {
									Socket clientSocket = new Socket(connectionBean.ip, Constants.CONNECTION.PARAM.SOCKET_PORT);
									clientSocket.setSoTimeout(500);
									BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),
											Constants.CONNECTION.PARAM.SOCKET_ENCODING));
									PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

									Message message = MessageUtil.getNewRequestMessage(Constants.MESSAGE.TYPE.REQUEST_PING);
									out.println(MessageUtil.message2String(message));
									out.flush();

									String clientReturnMessageStr = in.readLine();
									Message clientReturnMessage = MessageUtil.string2Messag(clientReturnMessageStr);
									if (Constants.MESSAGE.TYPE.REQUEST_PING_SUCCESS.equals(clientReturnMessage.getMessageType())) {
										MessageTitle messageTitle = clientReturnMessage.getMessageTitle();
										connectionBean.setName(messageTitle.getSendClientName());
										connectionBean.setClientId(messageTitle.getSendClientId());
										connectionBean.setIp(lanIp);//用户返回ip不准
										CommonFactory.getCommunicationService().addConnection(connectionBean);
										logger.info("Socket交互业务类:获取可连接设备("+connectionBean.name+",IP:" + lanIp + ")");
									}
									clientSocket.close();
								} catch (Exception e) {
//									 logger.error("Socket交互业务类:获取可连接设备列表,尝试连接IP:"+lanIp+"失败");
								}
							}
						};
						runThreadList.add(t);
						t.start();
					}//单ip所有同级ip循环结束
				}//本地ip循环结束
				
				try {
					Thread.sleep(Constants.CONNECTION.CONNECTABLE_TIME_OUT);//睡眠5秒 如果连接依然没有连通 证明连接无效
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (Thread t : runThreadList) {
					t.destroy();
				}
			}
		}.start();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lobinary.android.common.service.communication.
	 * CommunicationServiceInterface
	 * #connect(com.lobinary.android.common.pojo.communication.ConnectionBean)
	 */
	@Override
	public void connect(ConnectionBean connectionBean) throws UnknownHostException, IOException {
		final String connIp = connectionBean.ip;
		Socket clientSocket = new Socket(connIp, Constants.CONNECTION.PARAM.SOCKET_PORT);
		CommunicationSocketThread socketThread = new CommunicationSocketThread(clientSocket, false);
		socketThread.start();
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
		return connectionBean.getConnectionThread().sendMessage(message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lobinary.android.common.service.communication.
	 * CommunicationServiceInterface
	 * #sendMessageToAll(com.lobinary.android.common.pojo.communication.Message)
	 */
	@Override
	public Map<ConnectionBean, Message> sendMessageToAll(final Message message) {
		final Map<ConnectionBean, Message> returnMap = new HashMap<ConnectionBean, Message>();
		List<Thread> threadList = new ArrayList<Thread>();
		for (final String key : connectionMap.keySet()) {
			Thread thread = new Thread() {
				@Override
				public void run() {
					try {
						ConnectionBean connectionBean = connectionMap.get(key);
						Message returnMessage = connectionBean.getConnectionThread().sendMessage(message);
						returnMap.put(connectionBean, returnMessage);
						super.run();
					} catch (Exception e) {
						logger.error("Socket业务交互类:在所有连接发送消息时,向" + key + "发送信息异常", e);
					}
				}
			};
			threadList.add(thread);
			thread.start();
		}
		while (true) {
			try {
				Thread.sleep(100);
				for (int i = 0; i < threadList.size(); i++) {
					if (!threadList.get(i).isAlive()) {
						threadList.remove(i);
						i--;//
					}
				}
				if (threadList.size() == 0) {
					break;
				}
			} catch (Exception e) {
				logger.error("Socket业务交互类:循环遍历时异常", e);
			}
		}
		return returnMap;
	}

	/**
	 * 具体注释请点击Also see
	 * 
	 * @see com.lobinary.android.common.service.communication.socket.CommunicationSocketService#connectionMap
	 * @return the connectionMap
	 */
	public static Map<String, ConnectionBean> getConnectionMap() {
		return connectionMap;
	}

	@Override
	public void addConnection(ConnectionBean connectionBean) {
		if(connectionMapVersionId<connectionBean.getRefreshId()){//新版本 将会更新versionid id按时间取
			connectionMapVersionId = connectionBean.getRefreshId();
			connectionMap.clear();
			logger.info("Socket业务交互类:新版本批次("+connectionMapVersionId+")连接更新中......");
		}
		if(connectionMapVersionId == connectionBean.getRefreshId()||connectionBean.getRefreshId()==0){
			connectionMap.put(connectionBean.clientId, connectionBean);
		}
		logger.info("Socket业务交互类:新连接(clientId:" + connectionBean.clientId + ")被添加,当前连接总数为:" + connectionMap.size() + "个");
	}
	
	/**
	 * 
	 * <pre>
	 * 移除所有处于未连接状态的connectionbean
	 * </pre>
	 *
	 */
	public void removeAllUnConnectionBean(){
		for (String key : connectionMap.keySet()) {
			ConnectionBean connectionBean = connectionMap.get(key);
			if(Constants.CONNECTION.STATUS_UNCONNECTION==connectionBean.status){
				connectionMap.remove(key);
			}
		}
	}
	
	/**
	 * 
	 * <pre>
	 * 获取已经连接的设备
	 * </pre>
	 *
	 * @return
	 */
	public List<ConnectionBean> getAlreadyConnectionBean(){
		List<ConnectionBean> list = new ArrayList<ConnectionBean>();
		for (String key : connectionMap.keySet()) {
			ConnectionBean connectionBean = connectionMap.get(key);
			if(Constants.CONNECTION.STATUS_CONNECTION==connectionBean.status){
				list.add(connectionMap.get(key));
			}
		}
		return list ;
	}

}
