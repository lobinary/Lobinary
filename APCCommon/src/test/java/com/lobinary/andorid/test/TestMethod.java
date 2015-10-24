package com.lobinary.andorid.test;

import java.io.IOException;

import com.lobinary.android.common.pojo.communication.Message;
import com.lobinary.android.common.util.communication.MessageUtil;

public class TestMethod {
	MessageUtil playMusic = new MessageUtil();
	String sendGoods;
	Message receiveGoods = null;
	MessageUtil receiveGoodsTpt = null;//receiveGoodsTransportation
	String command;
	
	public void testMusic(){
			Message pm = playMusic.getNewRequestMessage("COMMAND");
			sendGoods = playMusic.message2String(pm);
			receiveGoods = receiveGoodsTpt.string2Messag(sendGoods);
			command = receiveGoods.getCommand().getCommandContent();
			try {
				Runtime.getRuntime().exec(command.replaceAll(" ", "\" \""));
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public static void main(String[] args) {
		TestMethod haha = new TestMethod();
		haha.testMusic();
	}

}
