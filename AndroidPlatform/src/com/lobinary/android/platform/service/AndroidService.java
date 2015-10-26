package com.lobinary.android.platform.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.android.common.pojo.communication.Message;
import com.lobinary.android.common.pojo.model.Music;
import com.lobinary.android.common.service.control.BaseServiceInterface;

public class AndroidService implements BaseServiceInterface{

	private static Logger logger = LoggerFactory.getLogger(AndroidService.class);

	@Override
	public long getCurrentTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean shutDown(long delayTime) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Music> getMusicList(Message message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean playMusic(String player, String musicId) {
		logger.info("安卓业务类:准备播放音乐");
		return false;
	}
	
}
