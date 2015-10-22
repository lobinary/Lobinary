package com.lobinary.android.common.util.communication;

import java.util.HashMap;
import java.util.Map;

import com.lobinary.android.common.pojo.communication.Command;

/**
 * <pre>
 * 命令工具
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月22日 下午9:52:20
 * @version V1.0.0 描述 : 创建文件CommanUtil
 * 
 *         
 * 
 */
public class CommandUtil {
	
	/**
	 * 
	 * <pre>
	 * 获取播放歌曲命令
	 * </pre>
	 *
	 * @param player
	 * @param musicName
	 * @return
	 */
	public Command playMusic(String player,String musicName){
		Command command = new Command();
		command.setCommandCode("1000");
		command.setCommandDesc("播放歌曲");
		command.setDelayTime(0);
		Map<String, String> commandContentMap = new HashMap<String, String>();
		commandContentMap.put("player", player);
		commandContentMap.put("musicName", musicName);
		command.setCommandContentMap(commandContentMap );
		return command;
	}
	
}
