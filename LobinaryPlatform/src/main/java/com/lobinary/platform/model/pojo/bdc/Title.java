package com.lobinary.platform.model.pojo.bdc;

/**
 * 
 * <pre>
 * 	英文标题页面
 * </pre>
 *
 * @ClassName: EnglishTitle
 * @author 919515134@qq.com
 * @date 2016年7月21日 下午4:08:32
 * @version V1.0.0
 */
public class Title {
	
	public Title(String title, String publishTime, String summary) {
		super();
		this.title = title;
		this.publishTime = publishTime;
		this.summary = summary;
	}
	
	/**
	 * id
	 */
	private long id;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 发布时间
	 */
	private String publishTime;
	
	/**
	 * 概要
	 */
	private String summary;
	
	/**
	 * 概要图片url
	 */
	private String pictureUrl;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

}
