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

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
	
	//int max = 10;
	//int i = 0;
	int temp;
	ServerSocket serverSocket;
	//Socket socket[];
	private DataOutputStream sendstr = null;
	
	public static void main(String[] args) {
		new ControlServiceCommunication();
	}
	
	public ControlServiceCommunication(){
		try {
			serverSocket = new ServerSocket(6666);
			System.out.println("服务器socket已建立");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("can't create serversocket");
		}
		
		Socket s = new Socket();
		try {
			s=serverSocket.accept();
			System.out.println("客户端已连接");
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("can't accept client's socket");
		}
		
		while(s!=null){
			/*temp = i;
			i++;
			CommuThread ct = new CommuThread();
			Thread commu = new Thread(ct);
			commu.start();*/
			DataOutputStream inp = null;
			String out = "hey boy";
			try {
				inp.writeUTF(out);
				send(out);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void send(String str){
		try {
			sendstr.writeUTF(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/*class CommuThread implements Runnable{
		private DataOutputStream oup = null;
		
		@Override
		public void run(){
			String out = "hey boy";
			try {
				oup.writeUTF(out);
				send(out);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}*/

	
}
