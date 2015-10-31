package com.lobinary.android.platform.service;

import java.util.List;

import com.lobinary.android.common.pojo.communication.Message;
import com.lobinary.android.common.pojo.model.Music;
import com.lobinary.android.common.service.control.BaseServiceInterface;

public class AndroidService implements BaseServiceInterface{

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
		return false;
	}

	@Override
	public boolean cancelShutDown() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean increaseVoice() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean decreaseVoice() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean shutDown() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
