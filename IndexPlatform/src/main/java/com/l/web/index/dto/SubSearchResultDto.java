package com.l.web.index.dto;

import java.io.Serializable;

/**
 * 
 * <pre>
 * 搜索结果详细数据
 * </pre>
 *
 * @ClassName: SubSearchResultDto
 * @author 919515134@qq.com
 * @date 2016年11月3日 上午9:46:12
 * @version V1.0.0
 */
public class SubSearchResultDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1048826512816803624L;

	/**
	 * 展示值
	 */
	private String displayStr;
	/**
	 * 具体值
	 */
	private String valueStr;
	/**
	 * 附属数据
	 */
	private String data;
	/**
	 * 备注
	 */
	private String remark;

	public String getDisplayStr() {
		return displayStr;
	}

	public void setDisplayStr(String displayStr) {
		this.displayStr = displayStr;
	}

	public String getValueStr() {
		return valueStr;
	}

	public void setValueStr(String valueStr) {
		this.valueStr = valueStr;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public boolean match(String s) {
		if(this.displayStr.contains(s)) return true;
		return false;
	}

}
