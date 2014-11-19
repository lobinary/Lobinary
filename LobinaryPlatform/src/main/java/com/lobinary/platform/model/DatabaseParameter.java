package com.lobinary.platform.model;

public class DatabaseParameter {

	public DatabaseParameter() {
	}

	/**
	 * 原始初始方法
	 * 
	 * @param name
	 * @param type
	 * @param value
	 * @param isEmpty
	 * @param position
	 */
	public DatabaseParameter(String name, String type, String value, boolean isEmpty, String position) {
		this.name = name;
		this.type = type;
		this.value = value;
		this.isEmpty = isEmpty;
		this.position = position;
	}

	/**
	 * 是使用此方法，默认isEmpty是true，表示参数一定可用
	 * 
	 * @param name
	 *            参数名
	 * @param value
	 *            参数值
	 */
	public DatabaseParameter(String name, Object value) {
		this.name = name;
		this.value = value;
		this.isEmpty = false;
		this.position = "where";
		if (value != null) {
			String typeTemp = value.getClass().getSimpleName().toLowerCase();
			// 通过获取getReturnType和getClass的type值中int格式的值不统一，因此此处予以统一为int格式
			// 其余格式获取值名称均相同分别为：float,double,date,long,string
			if (typeTemp.equals("integer"))
				typeTemp = "int";
			this.type = typeTemp;
		} else {
			this.isEmpty = true;
		}
	}

	/**
	 * 负责针对带有set一类的新型ParameterMap，position为参数位置<br/>
	 * position格式：set 、 where 、 group 、 order等全小写
	 * 
	 * @param name
	 * @param value
	 * @param position
	 */
	public DatabaseParameter(String name, Object value, String position) {
		this.name = name;
		this.value = value;
		this.position = position;
		this.isEmpty = false;
		if (value != null) {
			String typeTemp = value.getClass().getSimpleName().toLowerCase();
			// 通过获取getReturnType和getClass的type值中int格式的值不统一，因此此处予以统一为int格式
			// 其余格式获取值名称均相同分别为：float,double,date,long,string
			if (typeTemp.equals("integer"))
				typeTemp = "int";
			this.type = typeTemp;
		} else {
			this.isEmpty = true;
		}
	}

	/**
	 * 参数名称
	 */
	private String name;
	/**
	 * 参数格式<br/>
	 * 均为小写字母<br/>
	 * 格式为：string ，int ，long , float ，double ，date <br/>
	 */
	private String type;
	/**
	 * 参数值
	 */
	private Object value;
	/**
	 * 参数是否为null，或为无视值
	 */
	private boolean isEmpty;

	/**
	 * 参数位置set 、 where 、 group 、 order等全小写<br/>
	 * 默认值为'where'
	 */
	private String position = "where";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public boolean getIsEmpty() {
		return isEmpty;
	}

	public void setIsEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	public String toString() {
		return "name:	" + name + "type : " + type + "value:" + value + "isEmpty:" + isEmpty;
	}

}