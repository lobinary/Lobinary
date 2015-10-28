package com.lobinary.android.platform.service.communication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.android.common.constants.Constants;
import com.lobinary.android.common.pojo.communication.ConnectionBean;
import com.lobinary.android.common.pojo.communication.Message;
import com.lobinary.android.common.pojo.communication.MessageTitle;
import com.lobinary.android.common.service.communication.socket.CommunicationSocketService;
import com.lobinary.android.common.util.NetUtil;
import com.lobinary.android.common.util.communication.MessageUtil;
import com.lobinary.android.platform.util.AndroidNetUtil;

public class AndroidCommunicationSocketService extends CommunicationSocketService{

	private static Logger logger = LoggerFactory.getLogger(AndroidCommunicationSocketService.class);
	
	@Override
	public boolean refreshConnectableList() {
		new Thread() {
			@Override
			public void run() {
				List<String> localIpList = AndroidNetUtil.getLocalIpAddress();
				for (String localIp : localIpList) {
					List<String> lanIpList = NetUtil.getLANIp(localIp);
					for (final String lanIp : lanIpList) {
						final ConnectionBean connectionBean = new ConnectionBean();
						connectionBean.ip = lanIp;
						new Thread() {
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
										connectionMap.put(connectionBean.clientId, connectionBean);
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

}
