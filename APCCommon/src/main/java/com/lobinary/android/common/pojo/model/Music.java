/*
 * @(#)Music.java     V1.0.0      @上午10:39:54
 *
 * 项目名称: ControlClientPC
 *
 * 更改 信息:
 *    作者        				   日期        			描述
 *    ============  	================  =======================================
 *    lobinary       	  2015年10月23日    	创建文件
 *
 */
package com.lobinary.android.common.pojo.model;

import java.util.Random;

/**
 * <pre>
 * 音乐实体
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月23日 上午10:39:54
 * @version V1.0.0 描述 : 创建文件Music
 * 
 *         
 * 
 */
public class Music extends Model{
	
	private long id;
	
	private String name;
	
	private String type;
	
	private String location;
	
	private String playTime;


	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.pojo.model.Music#id
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.pojo.model.Music#id
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.pojo.model.Music#name
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.pojo.model.Music#name
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.pojo.model.Music#type
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.pojo.model.Music#type
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.pojo.model.Music#location
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.pojo.model.Music#location
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.pojo.model.Music#playTime
	 * @return the playTime
	 */
	public String getPlayTime() {
		return playTime;
	}

	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.android.common.pojo.model.Music#playTime
	 * @param playTime the playTime to set
	 */
	public void setPlayTime(String playTime) {
		this.playTime = playTime;
	}
	

}
