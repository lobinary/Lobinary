/*
 * @(#)ControlServiceCommuTest.java     V1.0.0      @下午11:07:16
 *
 * 项目名称: ControlClientPC
 *
 * 更改 信息:
 *    作者        				   日期        			描述
 *    ============  	================  =======================================
 *    lobinary       	  2015年10月15日    	创建文件
 *
 */
package com.lobinary.apc.service.control.imp;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextArea;

import com.lobinary.android.common.constants.Constants;
import com.lobinary.android.common.pojo.communication.Message;
import com.lobinary.android.common.util.communication.MessageUtil;
import com.lobinary.apc.util.initial.InitialUtil;

/**
 * <pre>
 * 
 * </pre>
 * @author 西小邪：ljrxxx@aliyun.com
 * @since 2015年10月15日 下午11:07:16
 * @version V1.0.0 描述 : 创建文件ControlServiceCommuTest
 * 
 *         
 * 
 */
public class ControlServiceCommuTest extends Frame{
	
	Socket servertome = null;
	DataInputStream in = null;
	DataOutputStream dos = null;
	DataInputStream dis = null;
	JTextArea ta = null;
	
	public static void main(String[] args){
		ControlServiceCommuTest window = new ControlServiceCommuTest();
		window.launchFrame();
	}
	
	public void launchFrame(){
		this.setSize(400, 400);
		ta = new JTextArea();
		add(ta,BorderLayout.NORTH);
		
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		this.setVisible(true);
		
		connect();
	}
	
	private void connect(){
		
		try {
			servertome = new Socket("127.0.0.1",6666);
			dos = new DataOutputStream(servertome.getOutputStream());
			dis = new DataInputStream(servertome.getInputStream());
			InitialUtil.initial();
			Message message = MessageUtil.getNewRequestMessage(Constants.MESSAGE.TYPE.REQUEST_CONNECT);
			dos.writeUTF(MessageUtil.message2String(message));
			String str = dis.readUTF();
			ta.setText(str);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}


