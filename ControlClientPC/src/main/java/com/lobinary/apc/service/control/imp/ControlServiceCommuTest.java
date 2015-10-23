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
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
	DataOutputStream dos = null;
	DataInputStream dis = null;

	private BufferedReader in;
	private PrintWriter out;
	JTextArea ta = null;
	
	public static void main(String[] args){
		ControlServiceCommuTest window = new ControlServiceCommuTest();
		window.launchFrame();
//		window.connect();
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

			in = new BufferedReader(new InputStreamReader(servertome.getInputStream(), "UTF8"));
			out = new PrintWriter(servertome.getOutputStream(), true);
			InitialUtil.initial();
			Message message = MessageUtil.getNewRequestMessage(Constants.MESSAGE.TYPE.REQUEST_CONNECT);
			message.getMessageTitle().setSendClientId("newi1d");
			out.println(MessageUtil.message2String(message));

			for (int i = 0; i < 2; i++) {
				out.println("1");
				out.flush();
				System.out.println("发送信息："+1);
			}
			out.flush();
			String str = in.readLine();
			ta.setText(str);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}


