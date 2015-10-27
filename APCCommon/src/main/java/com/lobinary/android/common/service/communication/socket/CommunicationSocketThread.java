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
import com.lobinary.android.common.service.communication.ConnectionThreadInterface;
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
public class CommunicationSocketThread extends ConnectionThreadInterface{
	
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

	/**
	 * 构造方法
	 * @param clientSocket
	 * @param isReceiveRequest 是否是接受的请求 true是    false不是，是主动发起的连接请求
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public CommunicationSocketThread(Socket clientSocket,boolean isReceiveRequest) throws UnsupportedEncodingException, IOException {
		super();

		this.clientSocket = clientSocket;
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),Constants.CONNECTION.PARAM.SOCKET_ENCODING));
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		
		if(isReceiveRequest){
			logger.debug("Socket服务端监控客户端子线程:收到客户端数据:客户端子线程初始化成功");
		}else{
			Message reqMessage = MessageUtil.getNewResponseMessage(Constants.MESSAGE.TYPE.REQUEST_CONNECT);
			String respMsg = MessageUtil.message2String(reqMessage);
			out.println(respMsg);
			out.flush();
			logger.info("Socket服务端监控客户端子线程:向客户端发起连接请求成功");
		}
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
			logger.debug("Socket服务端监控客户端子线程:接收到客户端请求,请求报文为："+messageStr);
			Message initialMessage = null;
			Message respMessage;
			try {
				initialMessage = MessageUtil.string2Messag(messageStr);
				if(Constants.MESSAGE.TYPE.REQUEST_PING.equals(initialMessage.getMessageType())){
					respMessage = MessageUtil.getNewResponseMessage(Constants.MESSAGE.TYPE.REQUEST_PING_SUCCESS);
					String respMsg = MessageUtil.message2String(respMessage);
					sendLastMsg(respMsg);
				}else if(Constants.MESSAGE.TYPE.REQUEST_CONNECT.equals(initialMessage.getMessageType())){

					respMessage = MessageUtil.getNewResponseMessage(Constants.MESSAGE.TYPE.ACCEPT_CONNECT);
					String respMsg = MessageUtil.message2String(respMessage);
					out.println(respMsg);
					out.flush();
					
					messageTitle = initialMessage.getMessageTitle();
					ConnectionBean connectionBean = new ConnectionBean(initialMessage,this);
					CommunicationSocketService.addConnection(connectionBean);
					
					establishConnection(initialMessage);
				}else if(Constants.MESSAGE.TYPE.ACCEPT_CONNECT.equals(initialMessage.getMessageType())){

					messageTitle = initialMessage.getMessageTitle();
					ConnectionBean connectionBean = new ConnectionBean(initialMessage,this);
					CommunicationSocketService.addConnection(connectionBean);
					
					establishConnection(initialMessage);
				}else{
					respMessage = MessageUtil.getNewResponseMessage(Constants.MESSAGE.TYPE.EXCEPTION);
					String respMsg = MessageUtil.message2String(MessageUtil.assembleExceptionMessage(respMessage, null, CodeDescConstants.SERVICE_MESSAGE_ERROR_MESSAGE_TYPE,null, null));
					sendLastMsg(respMsg);
				}
			} catch (APCSysException e) {
				logger.error("Socket服务端监控客户端子线程:请求报文未知格式,予以拒绝连接");
				respMessage = MessageUtil.getNewResponseMessage(Constants.MESSAGE.TYPE.REJECT_CONNECT);
				respMessage.setMessageString(MessageUtil.message2String(MessageUtil.assembleExceptionMessage(respMessage,null,null,e,null)));
			}
		} catch (Exception e) {
			//XXX 断开重连服务
			if(isReconnect){
				logger.error("Socket服务端监控客户端子线程:与客户端进行交互时发生异常,准备重新连接！",e);
			}else{
				logger.error("Socket服务端监控客户端子线程:与"+messageTitle.getSendClientName()+"的连接被关闭！",e);
			}
		}
	}

	/**
	 * <pre>
	 * 发送最后的信息，并在关闭当前连接后，关闭线程
	 * </pre>
	 *
	 * @param respMsg
	 */
	private void sendLastMsg(String respMsg) {
		out.println(respMsg);
		out.flush();
		closeConnection();
	}

	/**
	 * <pre>
	 * 建立连接
	 * </pre>
	 *
	 * @param initialMessage
	 * @throws IOException
	 */
	private void establishConnection(Message initialMessage) throws IOException {
		
		logger.info("Socket服务端监控客户端子线程:收到客户端数据:客户端("+messageTitle.getSendClientName()+")子线程启动成功");
		String line = in.readLine();
		while (line != null && line.trim().length() > 0) {
			logger.info("Socket服务端监控客户端子线程:收到客户端("+messageTitle.getSendClientName()+")数据:"+line);
			Message returnMessage = MessageUtil.string2Messag(line);
			if(MessageUtil.isDisconnectionReqMessage(line)){
				break;//如果是断开连接请求，在返回同意断开连接后，关闭连接
			}else if(returnMessage.isReq){
				String respMessageStr = MessageUtil.parseReqMessageStr2RespMessageStr(line);
				sendLastMsg(respMessageStr);
			}else{
				long messageId = returnMessage.getId();
				waitDealMessage.put(messageId, returnMessage);
				Thread t = waitThread.get(messageId);
				logger.info("t:"+t+".messageId:"+messageId);
				t.interrupt();
			}
			line = in.readLine();
		}
		closeConnection();
	}

	/**
	 * <pre>
	 * 关闭socket连接
	 * </pre>
	 *
	 * @throws IOException
	 */
	private void closeConnection() {
		isReconnect = false;
		if(!clientSocket.isClosed()){
			try {
				clientSocket.close();
			} catch (IOException e) {
				logger.error("Socket服务端监控客户端子线程:关闭当前连接时发生异常,予以忽略",e);
			}
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
		logger.info("@@@@@@@@@@本次返回returnMessage:"+returnMessage+"waitDealMessage:"+waitDealMessage.size()+"条,waitThread:"+waitThread.size());
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
			sendLastMsg(message);
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
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			logger.info("线程被打断，准备获取待处理信息");
		}
		Message returnMessage = getWaitDealMessage(messageId);
		if(returnMessage.getId()==messageId){
			logger.info("########恭喜成功接受预定返回报文#########id:"+messageId);
		}else{
			logger.error("!!!!!!!警告：返回报文为异常报文!!!!!!!!!");
		}
		return returnMessage;
	}

	/**
	 * <pre>
	 * 获取唯一信息id,用于识别发送和返回消息
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
			closeConnection();
		} catch (Exception e) {
			logger.error("Socket服务端监控客户端子线程:关闭连接时发送异常！",e);
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
	 * 获取远程方法基本message对象,一般用于生成调用远程baseService的请求报文用
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