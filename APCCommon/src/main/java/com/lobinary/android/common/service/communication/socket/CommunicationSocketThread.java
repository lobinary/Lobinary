package com.lobinary.android.common.service.communication.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.android.common.constants.CodeDescConstants;
import com.lobinary.android.common.constants.Constants;
import com.lobinary.android.common.exception.APCSysException;
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
	Map<Long,Thread> waitThread = new HashMap<Long,Thread>();
	Map<Long,Message> waitDealMessage = new HashMap<Long,Message>();
	
	/**
	 * 是否重新连接
	 */
	private boolean isReconnect = true;

	private long num = 1;

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
			Message initialMessage = MessageUtil.string2Messag(messageStr);
			Message respMessage = MessageUtil.getNewResponseMessage(Constants.MESSAGE.TYPE.ACCEPT_CONNECT);
			String respMsg = MessageUtil.message2String(respMessage);
			out.println(respMsg);
			out.flush();
			messageTitle = initialMessage.getMessageTitle();
			

			ConnectionBean connectionBean = new ConnectionBean();
			connectionBean.setSocketThread(this);
			CommunicationSocketService.addConnection(initialMessage.getMessageTitle().getSendClientId(), connectionBean);
			
			logger.info("Socket服务端监控客户端子线程:收到客户端数据:客户端("+messageTitle.getSendClientName()+")子线程启动成功");
			String line = in.readLine();
			while (line != null && line.trim().length() > 0) {
				logger.info("Socket服务端监控客户端子线程:收到客户端("+messageTitle.getSendClientName()+")数据:"+line);
				Message returnMessage = MessageUtil.string2Messag(line);
				if(MessageUtil.isDisconnectionReqMessage(line)){
					break;//如果是断开连接请求，在返回同意断开连接后，关闭连接
				}else if(returnMessage.isReq){
					String respMessageStr = MessageUtil.parseReqMessageStr2RespMessageStr(line);
					out.println(respMessageStr);
					out.flush();
				}else{
					long messageId = returnMessage.getId();
					waitDealMessage.put(messageId, returnMessage);
					Thread t = waitThread.get(messageId);
					logger.info("t:"+t+".messageId:"+messageId);
					t.interrupt();
				}
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
	 * 获取等待处理信息
	 * </pre>
	 *
	 * @param messageId
	 * @return
	 */
	public Message getWaitDealMessage(Long messageId){
		Message returnMessage = waitDealMessage.get(messageId);
		waitDealMessage.remove(messageId);
		waitThread.remove(messageId);
		return returnMessage;
	}
	
	/**
	 * 
	 * <pre>
	 * 发送信息到客户端,发方法的message需要配置id，来识别返回message
	 * </pre>
	 *
	 * @param message
	 * @return
	 */
	public boolean sendMessage(String message){
		try {
			out.println(message);
			out.flush();
		} catch (Exception e) {
			logger.error("Socket服务端监控客户端子线程:发送信息时发送异常！",e);
			throw new APCSysException(CodeDescConstants.SERVICE_MESSAGE_SEND_FAIL,e);
		}
		return true;
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
		long messageId = getUniqeMessageId();
		requestMessage.setId(messageId);
		this.sendMessage(MessageUtil.message2String(requestMessage));
		waitThread.put(messageId, Thread.currentThread());
		logger.info("******发送信息成功messageId:"+messageId);
		try {
			this.sleep(10000);
		} catch (InterruptedException e) {
			logger.info("线程被打断，准备获取待处理信息");
		}
		Message returnMessage = waitDealMessage.get(messageId);
		if(returnMessage.getId()==messageId){
			logger.info("########恭喜成功接受预定返回报文#########id:"+messageId);
		}else{
			logger.error("!!!!!!!警告：返回报文为异常报文!!!!!!!!!");
		}
		return returnMessage;
	}

	/**
	 * <pre>
	 * 
	 * </pre>
	 *
	 * @return
	 */
	private long getUniqeMessageId() {
		num++;
		return System.currentTimeMillis() + num;
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
		Message message = sendMessage(requestMessage);
		boolean result = (Boolean) message.getMessageObj();
		return result;
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
