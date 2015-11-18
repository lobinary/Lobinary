/*
 * @(#)WindowsCommunicationSocketService.java     V1.0.0      @上午11:32:04
 *
 * 项目名称: ControlClientPC
 *
 * 更改 信息:
 *    作者        				   日期        			描述
 *    ============  	================  =======================================
 *    lobinary       	  2015年10月30日    	创建文件
 *
 */
package com.lobinary.apc.service.communication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.android.common.constants.Constants;
import com.lobinary.android.common.pojo.communication.ConnectionBean;
import com.lobinary.android.common.pojo.communication.Message;
import com.lobinary.android.common.pojo.communication.MessageTitle;
import com.lobinary.android.common.service.communication.CommunicationServiceInterface;
import com.lobinary.android.common.service.communication.socket.CommunicationSocketService;
import com.lobinary.android.common.util.NetUtil;
import com.lobinary.android.common.util.communication.MessageUtil;
import com.lobinary.android.common.util.factory.CommonFactory;
import com.lobinary.apc.util.initial.InitialUtil;

/**
 * <pre>
 * 
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月30日 上午11:32:04
 * @version V1.0.0 描述 : 创建文件WindowsCommunicationSocketService
 * 
 *         
 * 
 */
public class WindowsCommunicationSocketService extends CommunicationSocketService {
	ConnectionBean conBean;
	private static Logger logger = LoggerFactory.getLogger(CommunicationSocketService.class);
	
	public void GetConnectionBean(){
		
		
	}
	@Override
	public void addConnection(ConnectionBean conBean) {
		super.addConnection(conBean);
	}

	@Override
	public List<ConnectionBean> getAlreadyConnectionBean() {
		return super.getAlreadyConnectionBean();
	}

	@Override
	public boolean refreshConnectableList() {
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
//					runThreadList.add(t);
//					t.start();
				}//单ip所有同级ip循环结束
			}//本地ip循环结束
				
		try {
			Thread.sleep(Constants.CONNECTION.CONNECTABLE_TIME_OUT);//睡眠5秒 如果连接依然没有连通 证明连接无效
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return true;
	}
	
	public static void main(String[] args) {
		InitialUtil.initial();
		WindowsCommunicationSocketService test = new WindowsCommunicationSocketService();
		test.refreshConnectableList();
		System.out.println(connectionMap);
	}
	
	
}
