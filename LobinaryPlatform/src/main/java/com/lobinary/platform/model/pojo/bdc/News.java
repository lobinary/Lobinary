package com.lobinary.platform.model.pojo.bdc;

/**
 * 
 * <pre>
 * 新闻实体类	
 * </pre>
 *
 * @ClassName: News
 * @author 919515134@qq.com
 * @date 2016年7月21日 下午4:39:21
 * @version V1.0.0
 */
public class News {
	
	/**
	 * 标题信息
	 */
	private Title title;
	
	/**
	 * 正文
	 */
	private String context;

	public Title getTitle() {
		return title;
	}

	public void setTitleView(Title title) {
		this.title = title;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

}
