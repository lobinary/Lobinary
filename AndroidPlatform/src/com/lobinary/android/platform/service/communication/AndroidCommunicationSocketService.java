package com.lobinary.android.platform.service.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.os.Handler;

import com.lobinary.android.common.constants.Constants;
import com.lobinary.android.common.pojo.communication.ConnectionBean;
import com.lobinary.android.common.pojo.communication.Message;
import com.lobinary.android.common.pojo.communication.MessageTitle;
import com.lobinary.android.common.service.communication.socket.CommunicationSocketService;
import com.lobinary.android.common.service.communication.socket.CommunicationSocketThread;
import com.lobinary.android.common.util.NetUtil;
import com.lobinary.android.common.util.communication.MessageUtil;
import com.lobinary.android.common.util.factory.CommonFactory;
import com.lobinary.android.platform.util.AndroidNetUtil;

public class AndroidCommunicationSocketService extends CommunicationSocketService{

	private static Logger logger = LoggerFactory.getLogger(AndroidCommunicationSocketService.class);
	
	public static Handler contactHandler;
	
	@Override
	public boolean refreshConnectableList() {
		new Thread() {
			@Override
			public void run() {
				removeAllUnConnectionBean();
				contactHandler.sendMessage(new android.os.Message());
				long connectionMapVersionIdTemp = connectionMapVersionId+1;//连接刷新临时ID
				List<String> localIpList = AndroidNetUtil.getLocalIpAddress();
				for (String localIp : localIpList) {
					List<String> lanIpList = NetUtil.getLANIp(localIp);
					for (final String lanIp : lanIpList) {
						final ConnectionBean connectionBean = new ConnectionBean();
						connectionBean.ip = lanIp;
						connectionBean.refreshId = connectionMapVersionIdTemp;
						new Thread() {
							@Override
							public void run() {
								try {
									Socket clientSocket = new Socket(connectionBean.ip, Constants.CONNECTION.PARAM.SOCKET_PORT);
									BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),
											Constants.CONNECTION.PARAM.SOCKET_ENCODING));
									PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

									Message message = MessageUtil.getNewRequestMessage(Constants.MESSAGE.TYPE.REQUEST_PING);
									logger.debug("安卓Socket交互类:发现可连接服务器("+connectionBean.ip+"),准备发送报文交换身份数据");
									try {
										out.println(MessageUtil.message2String(message));
										out.flush();
									} catch (Exception e) {
										logger.debug("安卓Socket交互类:向服务器("+connectionBean.ip+")发送数据失败,失败原因为:",e);
									}
									logger.debug("安卓Socket交互类:身份交换数据发送完成:"+MessageUtil.message2String(message));
									String clientReturnMessageStr = in.readLine();
									logger.debug("安卓Socket交互类:读取到服务器返回身份数据:"+clientReturnMessageStr);
									Message clientReturnMessage = MessageUtil.string2Messag(clientReturnMessageStr);
									if (Constants.MESSAGE.TYPE.REQUEST_PING_SUCCESS.equals(clientReturnMessage.getMessageType())) {
										MessageTitle messageTitle = clientReturnMessage.getMessageTitle();
										connectionBean.setName(messageTitle.getSendClientName());
										connectionBean.setClientId(messageTitle.getSendClientId());
										CommonFactory.getCommunicationService().addConnection(connectionBean);
										logger.info("Socket交互业务类:获取可连接设备("+connectionBean.name+",IP:" + lanIp + ")");
									}
									clientSocket.close();
								} catch (Exception e) {
//									 logger.error("Socket交互业务类:获取可连接设备列表,尝试连接IP:"+lanIp+"失败");
								}
							}
						}.start();
					}
				}
			}
		}.start();
		return true;
	}

	@Override
	public void addConnection(ConnectionBean connectionBean) {
		super.addConnection(connectionBean);
		contactHandler.sendMessage(new android.os.Message()); 
	}

	@Override
	public void connect(ConnectionBean connectionBean) throws UnknownHostException, IOException {
		CommunicationSocketThread socketThread = new CommunicationSocketThread(connectionBean, false);
		socketThread.start();
	}

	public void refreshContactList(){
		contactHandler.sendMessage(new android.os.Message()); 
	}
	
}
