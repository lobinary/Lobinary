/*
 * @(#)ControlServiceCommunication.java     V1.0.0      @����12:12:32
 *
 * ��Ŀ����: ControlClientPC
 *
 * ���� ��Ϣ:
 *    ����        				   ����        			����
 *    ============  	================  =======================================
 *    lobinary       	  2015��10��13��    	�����ļ�
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
 * @author ����lvb3@chinaunicom.cn
 * @since 2015��10��13�� ����12:12:32
 * @version V1.0.0 ���� : �����ļ�ControlServiceCommunication
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
			System.out.println("δ�ܽ���socket����");
		}
		
		socket = new Socket[max];
		
		try {
			while((socket[i]=serverSocket.accept())!=null){
				temp = i;
				i++;
				//��ʼһ���߳�
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("δ�����ӵ������ͻ���");
		}
	}
	
	
	public void run(){
		
	}
}
