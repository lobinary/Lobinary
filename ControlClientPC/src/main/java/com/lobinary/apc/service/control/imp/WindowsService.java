/*
 * @(#)WindowsService.java     V1.0.0      @下午11:20:59
 *
 * 项目名称: ControlClientPC
 *
 * 更改 信息:
 *    作者        				   日期        			描述
 *    ============  	================  =======================================
 *    lobinary       	  2015年10月22日    	创建文件
 *
 */
package com.lobinary.apc.service.control.imp;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.android.common.pojo.communication.Message;
import com.lobinary.android.common.pojo.model.Music;
import com.lobinary.android.common.service.control.BaseServiceInterface;
import com.lobinary.apc.windows.ControlClientPCWindows;

/**
 * <pre>
 * windows业务实现类
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月22日 下午11:20:59
 * @version V1.0.0 描述 : 创建文件WindowsService
 * 
 *         
 * 
 */
public class WindowsService implements BaseServiceInterface {
	
	private static Logger logger = LoggerFactory.getLogger(ControlClientPCWindows.class);

	/* (non-Javadoc)
	 * @see com.lobinary.android.common.service.control.BaseServiceInterface#getCurrentTime()
	 */
	@Override
	public long getCurrentTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.lobinary.android.common.service.control.BaseServiceInterface#shutDown(long)
	 */
	@Override
	public boolean shutDown(long delayTime) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lobinary.android.common.service.control.BaseServiceInterface#playMusic(com.lobinary.android.common.pojo.communication.Command)
	 */
	@Override
	public boolean playMusic(String player,String musicId) {
		try {
//			CommandParam commandParam = command.getCommandParam();
//			String player = commandParam.player;
//			String musicName = commandParam.musicName;
			String musicPath = "I:\\KuGou\\黑涩会美眉-123木头人.mp3";
			Runtime.getRuntime().exec("cmd /c start "   +   musicPath.replaceAll(" ", "\" \""));
			//调用酷狗播放歌曲
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.lobinary.android.common.service.control.BaseServiceInterface#getMusicList(com.lobinary.android.common.pojo.communication.Command)
	 */
	@Override
	public List<Music> getMusicList(Message message) {
		String musicFolderStr = ControlClientPCWindows.musicFolderText.getText();
		File mf = new File(musicFolderStr);
		File[] files = mf.listFiles();
		for(File f : files){
			logger.info("获取音乐列表业务:找到文件,文件名称:"+f.getName()+",文件大小:"+f.getTotalSpace()+"文件路径:"+f.getPath());
		}
		return null;
	}
	
}
