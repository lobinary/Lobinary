package com.lobinary.android.platform.activity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.lobinary.android.platform.R;
import com.lobinary.android.platform.constants.Constants;

public class TestMessageActivity extends Activity{

	Socket serverhome = null;
	DataInputStream in = null;
	DataOutputStream dos = null;
	DataInputStream dis = null;
	Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		handler = new Handler(){
	        	public void handleMessage(Message msg){
	            	EditText testMessageList = (EditText) findViewById(R.id.testMessageList);
	        		testMessageList.setText(testMessageList.getText()+"\n"+msg.obj);
	        	}
	       };
	        
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_message_layout);

        final Button testSendMessageButton = (Button) findViewById(R.id.testSendMessageButton);
        
        testSendMessageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
		        EditText testMessageText = (EditText) findViewById(R.id.testMessageText);
				String msg = testMessageText.getText().toString();
				if(msg.length()==0){
					outMessage("请您输入信息内容，不允许发送空信息");
				}else{
					outMessage(msg);
					sendMsg2Server(msg);
					testMessageText.setText("");
				}
			}
		});
        

        final Button connectServerButton = (Button) findViewById(R.id.connectServerButton);
        connectServerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				connect();
			}
		});
        
	}
	
	public final void outMessage(final String message){
		EditText testMessageList = (EditText) findViewById(R.id.testMessageList);
		testMessageList.setText(testMessageList.getText()+"\n"+message);
	}
	

	final Runnable mUpdateResults = new Runnable() {
		
        public void run() {
//        	EditText testMessageList = (EditText) findViewById(R.id.testMessageList);
//    		testMessageList.setText(testMessageList.getText()+"\n"+message);
        	Log.i(Constants.LOG.LOG_TAG, "授权成功");
        }
    };
    
	private void connect(){
		if(serverhome!=null){
			outMessage("您已经处于连接状态");
			return;
		}
		new Thread(){
			
			public void sendMsg(String msgInfo){
				Message msg = new Message();  
				msg.obj = msgInfo;
	            handler.sendMessage(msg);  
			}
			
			@Override
			public void run() {
				super.run();
				try {
		            sendMsg("准备连接服务器......");
					Log.i(Constants.LOG.LOG_TAG, "准备连接服务器");
					serverhome = new Socket("111.111.111.103",6666);
					sendMsg("连接成功");
//					testMessageList.setText(testMessageList.getText()+"\n"+"连接服务器成功");
					dos = new DataOutputStream(serverhome.getOutputStream());
					dos.writeUTF("android request connection");
					dis = new DataInputStream(serverhome.getInputStream());
					String str = dis.readUTF();
					Log.i(Constants.LOG.LOG_TAG, "接收到服务器消息："+str);
//					testMessageList.setText(testMessageList.getText()+"\n"+"收到服务器消息:"+str);
					sendMsg("收到服务器消息:"+str);
					
				} catch (Exception e) {
					Log.e(Constants.LOG.LOG_TAG, "连接服务器异常：",e);
//					outMessage("连接错误："+e.getMessage());
				}
			}
		}.start();
		
	
	}
	
	private void sendMsg2Server(String msg){
		try {
			if(serverhome==null){
				outMessage("当前处于未连接状态，无法发送信息！");
			}else{
				dos.writeUTF(msg);
			}
		} catch (IOException e) {
			outMessage("发送信息错误："+e.getMessage());
		}
	}
	
	 
	public Handler getHandler(){
		return this.handler;
	}
	
}
