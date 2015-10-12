package com.lobinary.apc.service.control;

import com.lobinary.apc.constants.Constants;

/**
 * <pre>
 * 控制业务接口
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月12日 下午1:17:12
 * @version V1.0.0 描述 : 创建文件ControlServiceInterface
 * 
 *         
 * 
 */
public interface ControlServiceInterface {
	
	/**
	 * 
	 * <pre>
	 * 关闭显示器
	 * </pre>
	 *
	 * @return
	 */
	public boolean shutdownScreen();
	
	/**
	 * 
	 * <pre>
	 * 启动常用类型 应用程序
	 * </pre>
	 *
	 * @param applicationCode {@link Constants.APPLICATION_CODE_MUSIC }
	 * @return 是否启动成功
	 */
	public boolean startApplication(String applicationCode);
	
	/**
	 * 
	 * <pre>
	 * 关闭电脑
	 * </pre>
	 *
	 * @param countDownTime 倒计时时间，单位毫秒
	 * @return
	 */
	public boolean shutdownComputer(long countDownTime);

}
