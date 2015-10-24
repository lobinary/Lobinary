package com.lobinary.android.common.util.communication;

import java.io.IOException;

import com.lobinary.android.common.constants.Constants;
import com.lobinary.android.common.pojo.communication.Command;
import com.lobinary.android.common.pojo.communication.Message;
import com.lobinary.android.common.pojo.communication.MessageTitle;

/**
 * <pre>
 * 
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月24日 下午12:55:59
 * @version V1.0.0 描述 : 创建文件GetNewRequestMessageUtil
 * 
 *         
 * 
 */
public class GetNewRequestCommandUtil extends Command{
	String Command;
	
	/**
	 * <pre>
	 * 播放音乐test
	 * </pre>
	 *
	 */
	private void play() {
		// TODO Auto-generated method stub
		try {
				String musicPath = "F:\\KGMusic\\Kugou";
				Runtime.getRuntime().exec("cmd /c start "   +   musicPath.replaceAll(" ", "\" \""));
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.util.communication.GetNewRequestCommandUtil#Command
	 * @return the command
	 */
	public String getCommand() {
		return Command;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.util.communication.GetNewRequestCommandUtil#Command
	 * @param command the command to set
	 */
	public void setCommand(String command) {
		Command = command;
	}
}
