package com.lobinary.platform.constant;

/**
 * 
  *
  *	<pre>
  * @Description: 消息枚举
  * </pre>
  *
  * @ClassName: MessageEnums
  * @author Comsys-Lobinary
  * @date 2016年6月13日 上午11:46:50
  * @version V1.0.0
 */
public enum FileEnums {

	TYPE_TEXT("01","文本文件"),
	TYPE_MUSIC("02","音乐文件"),
	TYPE_EXCEL("03","表格文件"),
	TYPE_DOC("04","文档文件"),
	TYPE_IMAGE("05","图片文件"),
	TYPE_FILE("99","文件");
	
	private String code;
	
	private String desc;
	
	private FileEnums(String code,String desc){
		this.code=code;
		this.desc=desc;
	}
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	


}
