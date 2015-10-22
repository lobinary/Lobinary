package com.lobinary.android.common.service.control;

import com.lobinary.android.common.pojo.communication.Command;

/**
 * <pre>
 * 基本业务执行接口
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月22日 上午10:09:43
 * @version V1.0.0 描述 : 创建文件BaseServiceInterface
 * 
 *         
 * 
 */
public interface BaseServiceInterface {
	
	/**
	 * 
	 * <pre>
	 * 获取当前本机时间
	 * </pre>
	 *
	 * @return
	 */
	public long getCurrentTime();
	
	/**
	 * 
	 * <pre>
	 * 关机
	 * </pre>
	 *
	 * @param delayTime
	 * @return
	 */
	public boolean shutDown(long delayTime);
	
	/**
	 * 
	 * <pre>
	 * 播放歌曲
	 * </pre>
	 *
	 * @param player
	 * @param musicName
	 * @return
	 */
	public boolean playMusic(Command command);

}
