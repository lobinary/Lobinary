/*
 * @(#)StatusPanel.java     V1.0.0      @下午3:00:06
 *
 * 项目名称: ControlClientPC
 *
 * 更改 信息:
 *    作者        				   日期        			描述
 *    ============  	================  =======================================
 *    lobinary       	  2015年10月12日    	创建文件
 *
 */
package com.lobinary.apc.pojo.model.windows;

import javax.swing.JPanel;

/**
 * <pre>
 * PC客户端窗口状态按钮面板
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月12日 下午3:00:06
 * @version V1.0.0 描述 : 创建文件StatusPanel
 * 
 *         
 * 
 */
public class StatusMenuPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2302528139726314806L;
	
	private String menuText;

	public StatusMenuPanel(String menuText) {
		super();
		this.menuText = menuText;
	}

	
}
