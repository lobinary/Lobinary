package com.lobinary.android.common.service.communication.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.android.common.pojo.communication.Message;
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
public class CommunicationSocketThread extends Thread {
	
	private static Logger logger = LoggerFactory.getLogger(CommunicationSocketThread.class);

	Socket clientSocket;
	private BufferedReader in;
	private PrintWriter out;

	public CommunicationSocketThread(Socket clientSocket) throws UnsupportedEncodingException, IOException {
		super();
		this.clientSocket = clientSocket;
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF8"));
		out = new PrintWriter(clientSocket.getOutputStream(), true);
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
			String line = in.readLine();
			while (line != null && line.trim().length() > 0) {
				String respMessageStr = MessageUtil.parseReqMessageStr2RespMessageStr(line);
				out.println(respMessageStr);
				out.flush();
				if(MessageUtil.isDisconnectionReqMessage(line)){
					break;//如果是断开连接请求，在返回同意断开连接后，关闭连接
				}
				line = in.readLine();
			}
			if(!clientSocket.isClosed()){
				clientSocket.close();
			}
		} catch (Exception e) {
			logger.error("Socket服务端监控客户端子线程:与客户端进行交互时发生异常！",e);
			//XXX 断开重连服务
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
	public boolean sendMessage(String message){
		try {
			out.println(message);
			out.flush();
		} catch (Exception e) {
			logger.error("Socket服务端监控客户端子线程:发送信息时发送异常！",e);
			return false;
		}
		return true;
	}

}
