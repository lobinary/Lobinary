package com.lobinary.android.common.service.communication.socket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.android.common.constants.Constants;
import com.lobinary.android.common.pojo.communication.Command;
import com.lobinary.android.common.pojo.communication.ConnectionBean;
import com.lobinary.android.common.pojo.communication.Message;
import com.lobinary.android.common.pojo.communication.MessageTitle;
import com.lobinary.android.common.pojo.model.Music;
import com.lobinary.android.common.service.control.BaseServiceInterface;
import com.lobinary.android.common.util.communication.MessageUtil;

/**
 * <pre>
 * Socket 监控线程 用来监听 接受的数据,并予以解析
 * </pre>
 * 
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月22日 下午2:58:56
 * @version V1.0.0 描述 : 创建文件CommunicationSocketThread
 * 
 * 
 * 
 */
public class CommunicationSocketThread extends Thread implements BaseServiceInterface{
	
	private static Logger logger = LoggerFactory.getLogger(CommunicationSocketThread.class);

	Socket clientSocket;
	private BufferedReader in;
	private PrintWriter out;
	private MessageTitle messageTitle;
	/**
	 * 是否重新连接
	 */
	private boolean isReconnect = true;

	public CommunicationSocketThread(Socket clientSocket) throws UnsupportedEncodingException, IOException {
		super();
		this.clientSocket = clientSocket;
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF8"));
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		logger.info("Socket服务端监控客户端子线程:收到客户端数据:客户端子线程初始化成功");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		super.run();
		try {
			
			String messageStr = in.readLine();
			logger.info("接收到客户端请求,请求报文为："+messageStr);
			Message message = MessageUtil.string2Messag(messageStr);
			Message respMessage = MessageUtil.getNewResponseMessage(Constants.MESSAGE.TYPE.ACCEPT_CONNECT);
			String respMsg = MessageUtil.message2String(respMessage);
			out.println(respMsg);
			out.flush();
			messageTitle = message.getMessageTitle();
			

			ConnectionBean connectionBean = new ConnectionBean();
			connectionBean.setSocketThread(this);
			CommunicationSocketService.addConnection(message.getMessageTitle().getSendClientId(), connectionBean);
			
			logger.info("Socket服务端监控客户端子线程:收到客户端数据:客户端("+messageTitle.getSendClientName()+")子线程启动成功");
			String line = in.readLine();
			while (line != null && line.trim().length() > 0) {
				logger.info("Socket服务端监控客户端子线程:收到客户端("+messageTitle.getSendClientName()+")数据:"+line);
//				
//				String respMessageStr = MessageUtil.parseReqMessageStr2RespMessageStr(line);
//				out.println(respMessageStr);
//				out.flush();
//				if(MessageUtil.isDisconnectionReqMessage(line)){
//					break;//如果是断开连接请求，在返回同意断开连接后，关闭连接
//				}
				line = in.readLine();
			}
			closeSocket();
		} catch (Exception e) {
			//XXX 断开重连服务
			if(isReconnect){
				logger.error("Socket服务端监控客户端子线程:与客户端进行交互时发生异常,准备重新连接！",e);
			}else{
				logger.error("Socket服务端监控客户端子线程:与"+messageTitle.getSendClientName()+"的连接被关闭！");
			}
		}
	}

	/**
	 * <pre>
	 * 关闭socket连接
	 * </pre>
	 *
	 * @throws IOException
	 */
	private void closeSocket() throws IOException {
		isReconnect = false;
		if(!clientSocket.isClosed()){
			clientSocket.close();
		}
	}
	
	/**
	 * 
	 * <pre>
	 * 发送信息到客户端
	 * </pre>
	 *
	 * @param message
	 * @return
	 */
	public Message sendMessage(String message){
		try {
			out.println(message);
			out.flush();
		} catch (Exception e) {
			logger.error("Socket服务端监控客户端子线程:发送信息时发送异常！",e);
			return null;
		}
		return null;
	}
	

	/**
	 * <pre>
	 * 
	 * </pre>
	 *
	 * @param requestMessage
	 * @return 
	 */
	public Message sendMessage(Message requestMessage) {
		return this.sendMessage(MessageUtil.message2String(requestMessage));
	}

	/**
	 * <pre>
	 * 
	 * </pre>
	 *
	 * @return
	 */
	public boolean shutDownConnection() {
		try {
			Message message = MessageUtil.getNewRequestMessage(Constants.MESSAGE.TYPE.DISCONNECT);
			out.println(message);
			out.flush();
			closeSocket();
		} catch (Exception e) {
			logger.error("Socket服务端监控客户端子线程:发送信息时发送异常！",e);
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.lobinary.android.common.service.control.BaseServiceInterface#getCurrentTime()
	 */
	@Override
	public long getCurrentTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.lobinary.android.common.service.control.BaseServiceInterface#shutDown(long)
	 */
	@Override
	public boolean shutDown(long delayTime) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lobinary.android.common.service.control.BaseServiceInterface#getMusicList(com.lobinary.android.common.pojo.communication.Message)
	 */
	@Override
	public List<Music> getMusicList(Message message) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lobinary.android.common.service.control.BaseServiceInterface#playMusic(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean playMusic(String player, String musicId) {
		Message requestMessage = getRemoteBaseMessage(Thread.currentThread().getStackTrace()[1].getMethodName());
		Command command = requestMessage.getCommand();
		command.add(player).add(musicId);
		sendMessage(requestMessage);
		return false;
	}
	

	/**
	 * 
	 * <pre>
	 * 获取远程方法基本message对象
	 * </pre>
	 *
	 * @param methodName
	 * @return
	 */
	public Message getRemoteBaseMessage(String methodName){
		Message message = MessageUtil.getNewRequestMessage(Constants.MESSAGE.TYPE.COMMAND);
		Command command = message.getCommand();
		command.setRemoteMethodName(methodName);
		return message;
	}
	
	

}
