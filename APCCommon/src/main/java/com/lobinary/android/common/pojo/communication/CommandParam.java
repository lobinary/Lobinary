package com.lobinary.android.common.pojo.communication;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 命令参数
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月22日 下午10:17:35
 * @version V1.0.0 描述 : 创建文件CommandParam
 * 
 *         
 * 
 */
public class CommandParam {
	

	/**
	 * 命令内容Map
	 */
	private Map<String,Object> commandContentMap = new HashMap<String,Object>();

	/**
	 * 播放器
	 */
	public String player;
	
	/**
	 * 音乐名称
	 */
	public String musicName;
	
	/**
	 * 
	 * <pre>
	 * 添加参数
	 * </pre>
	 *
	 * @param paramName
	 * @param paramValue
	 */
	public void addParam(String paramName,Object paramValue){
		commandContentMap.put(paramName, paramValue);
	}
	
	/**
	 * 
	 * <pre>
	 * 获取参数
	 * </pre>
	 *
	 * @param paramName
	 * @return
	 */
	public Object getParam(String paramName){
		return commandContentMap.get(paramName);
	}
	
}
