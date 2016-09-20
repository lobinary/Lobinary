package com.lobinary.工具类;

import javax.swing.JPanel;

import com.lobinary.实用工具.实用工具;

public abstract class 实用工具标签标准类 extends JPanel {

	private static final long serialVersionUID = -2173686947090693868L;
	
	public 实用工具标签标准类(){
		loadConfig();
	}
	
	public void loadConfig(){
		实用工具.log("准备加载配置信息");
	}
	
	public void saveConfig(){
		实用工具.log("准备保存配置信息");
	}

}
