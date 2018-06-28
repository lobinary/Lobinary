package com.lobinary.设计模式.适配器模型.播放器实现类.视频播放器;

import com.lobinary.设计模式.适配器模型.播放器;
import com.lobinary.设计模式.适配器模型.播放器实现类.视频播放器.视频播放器实现类.视频AVI播放器;
import com.lobinary.设计模式.适配器模型.播放器实现类.视频播放器.视频播放器实现类.视频MP4播放器;

public class 视频播放器适配器  implements 播放器{
	
	视频播放器 视频播放器实现类;
	
	public 视频播放器适配器(String 文件类型){
		if(文件类型.equals("AVI")){
			视频播放器实现类 = new 视频AVI播放器();
		}else if(文件类型.equals("MP4")){
			视频播放器实现类 = new 视频MP4播放器();
		}
	}

	@Override
	public void 播放(String 文件类型, String 文件路径) {
		视频播放器实现类.播放(文件路径);
	}

}
