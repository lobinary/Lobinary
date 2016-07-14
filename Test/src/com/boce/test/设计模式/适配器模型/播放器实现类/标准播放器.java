package com.boce.test.设计模式.适配器模型.播放器实现类;

import com.boce.test.设计模式.适配器模型.播放器;
import com.boce.test.设计模式.适配器模型.播放器实现类.视频播放器.视频播放器适配器;

/**
 * 标准播放器只会播放音频文件
 */
public class 标准播放器 implements 播放器{
	
	视频播放器适配器 视频播放器;
	
	@Override
	public void 播放(String 文件类型,String 文件路径) {
		if(文件类型.equals("MP3")){
			System.out.println("音频mp3("+文件路径+")播放中");
		}else if(文件类型.equals("MP4")||文件类型.equals("AVI")){
			视频播放器 = new 视频播放器适配器(文件类型);
			视频播放器.播放(文件类型, 文件路径);
		}else{
			System.out.println("不支持的文件格式：["+文件类型+"]");
		}
	}

}
