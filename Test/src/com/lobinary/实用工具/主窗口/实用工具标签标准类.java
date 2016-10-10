package com.lobinary.实用工具.主窗口;

import javax.swing.JPanel;

import com.lobinary.实用工具.实用工具;

public abstract class 实用工具标签标准类 extends JPanel {

	private static final long serialVersionUID = -2173686947090693868L;
	
	private String tabName = this.getClass().getSimpleName();
	
	public 实用工具标签标准类(){
		loadConfig();
	}
	
	public void loadConfig(){
		实用工具.log(tabName+":暂无加载配置信息功能");
	}
	
	public void loadDefaultConfig(){
		实用工具.log(tabName+":暂无加载默认配置信息功能");
	}
	
	public void saveConfig(){
		实用工具.log(tabName+":暂无保存配置信息功能");
	}

}
