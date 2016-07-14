package com.boce.test.设计模式.适配器模型.播放器实现类.视频播放器.视频播放器实现类;

import com.boce.test.设计模式.适配器模型.播放器实现类.视频播放器.视频播放器;

public class 视频AVI播放器 implements 视频播放器 {
	
	public void 播放(String 视频路径){
		System.out.println("视频avi("+视频路径+")播放中");
	}

}
