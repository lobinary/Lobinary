package com.lobinary.设计模式.命令模式.请求者;

import com.lobinary.设计模式.命令模式.命令.命令;

/**
 * 
 * <pre>
 * 命令的请求者	
 * </pre>
 *
 * @ClassName: 录音机按钮
 * @author 919515134@qq.com
 * @date 2016年7月19日 上午10:15:30
 * @version V1.0.0
 */
public class 录音机按钮 {

	public 命令 播放音乐;
	public 命令 暂停音乐;
	public 命令 倒带; 
	
	public void 按下播放音乐按钮(){
		播放音乐.执行();
	}
	
	public void 按下暂停音乐按钮(){
		暂停音乐.执行();
	}
	
	public void 按下倒带按钮(){
		倒带.执行();
	}

	public void set播放音乐(命令 播放音乐) {
		this.播放音乐 = 播放音乐;
	}

	public void set暂停音乐(命令 暂停音乐) {
		this.暂停音乐 = 暂停音乐;
	}

	public void set倒带(命令 倒带) {
		this.倒带 = 倒带;
	}

}
