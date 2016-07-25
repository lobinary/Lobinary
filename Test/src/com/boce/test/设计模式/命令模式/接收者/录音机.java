package com.boce.test.设计模式.命令模式.接收者;

/**
 * 
 * <pre>
 * 	录音机就是命令的接收者
 * </pre>
 *
 * @ClassName: 录音机
 * @author 919515134@qq.com
 * @date 2016年7月19日 上午10:11:35
 * @version V1.0.0
 */
public class 录音机 {
	
	public void 播放(){
		System.out.println("开始播放音乐");
	}

	
	public void 暂停(){
		System.out.println("暂停播放音乐");
	}

	
	public void 倒带(){
		System.out.println("开始倒带");
	}

}
