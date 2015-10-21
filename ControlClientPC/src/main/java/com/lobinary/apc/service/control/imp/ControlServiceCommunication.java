/*
 * @(#)ControlServiceCommunication.java     V1.0.0      @����12:12:32
 *
 * ��Ŀ����: ControlClientPC
 *
 * ���� ��Ϣ:
 *    ����        				   ����        			����
 *    ============  	================  =======================================
 *    kimchi      	  2015��10��13��    	�����ļ�
 *
 */
package com.lobinary.apc.service.control.imp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.lobinary.apc.util.common.file.LogUtil;

public class ControlServiceCommunication {

	int temp = 2;
	ServerSocket serverSocket;
	DataOutputStream dos = null;
	DataInputStream dis = null;
	public DataOutputStream sendstr = null;
	
	//Map connect = new HashMap();

	public static void main(String[] args) {
		new ControlServiceCommunication();
	}

	public ControlServiceCommunication() {
		try {
			serverSocket = new ServerSocket(6666);
			System.out.println("服务器socket已建立");
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("can't create serversocket");
		}

		Socket s = new Socket();
		while (true) {
			try {
				s = serverSocket.accept();
				dos = new DataOutputStream(s.getOutputStream());
				dis = new DataInputStream(s.getInputStream());
				System.out.println("客户端已连接");
			} catch (Exception e1) {
				e1.printStackTrace();
				break;
			}

			// 发送消息
			String out = "hey boy";
			try {
				dos.writeUTF(out);
				dos.flush();
			} catch (IOException e) {
				//e.printStackTrace();
			}

//			while(true){
//				// 接收消息
//				try {
//					String str = dis.readUTF();
//					System.out.println(str);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
			
		}
	}
}
