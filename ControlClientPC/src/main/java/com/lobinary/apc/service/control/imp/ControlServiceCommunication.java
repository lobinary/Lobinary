/*
 * @(#)ControlServiceCommunication.java     V1.0.0      @上午12:12:32
 *
 * 项目名称: ControlClientPC
 *
 * 更改 信息:
 *    作者        				   日期        			描述
 *    ============  	================  =======================================
 *    lobinary       	  2015年10月13日    	创建文件
 *
 */
package com.lobinary.apc.service.control.imp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <pre>
 * 
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月13日 上午12:12:32
 * @version V1.0.0 描述 : 创建文件ControlServiceCommunication
 * 
 *         
 * 
 */
public class ControlServiceCommunication {
	
	int max = 10;
	int i = 0;
	int temp;
	ServerSocket serverSocket;
	Socket socket[];
	
	public ControlServiceCommunication(){
		try {
			serverSocket = new ServerSocket(6666);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("未能建立socket连接");
		}
		
		socket = new Socket[max];
		
		try {
			while((socket[i]=serverSocket.accept())!=null){
				temp = i;
				i++;
				//开始一个线程
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("未能连接到其他客户端");
		}
	}
	
	
	public void run(){
		
	}
}
