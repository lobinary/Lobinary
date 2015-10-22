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

import com.lobinary.android.common.pojo.communication.Command;
import com.lobinary.android.common.pojo.communication.CommandParam;
import com.lobinary.android.common.service.control.BaseServiceInterface;

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
	public boolean playMusic(Command command) {
		CommandParam commandParam = command.getCommandParam();
		String player = commandParam.player;
		String musicName = commandParam.musicName;
		//调用酷狗播放歌曲
		return false;
	}

}
