package com.lobinary.实用工具.主窗口;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JPanel;

import com.lobinary.实用工具.实用工具;
import com.lobinary.工具类.file.FileUtil;

public abstract class 实用工具标签标准类 extends JPanel {

	private static final long serialVersionUID = -2173686947090693868L;
	
	private String tabName = this.getClass().getSimpleName();
	Alert窗口 alert窗口 = new Alert窗口(实用工具.主窗口框架,true);
	同意窗口 同意窗口 = new 同意窗口(实用工具.主窗口框架,true);
	public 实用工具标签标准类(){
		实用工具.log("========================================      准备加载"+tabName+"配置信息\t========================================");
		loadConfig();
		实用工具.log("========================================      "+tabName+"配置信息加载结束\t========================================");
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
	
	public void out(Object o) {
		if(File.class.isInstance(o)){
			try {
				List<String> readLine2List = FileUtil.readLine2List((File)o);
				for (String s : readLine2List) {
					实用工具.log(tabName+":"+s);
				}
			} catch (IOException e) {
				实用工具.log(tabName+":输出文件("+((File)o).getAbsolutePath()+")异常"+e.getMessage());
			}
		}else{
			实用工具.log(tabName+":"+o.toString());
		}
		
	}
	
	public void alert(String title,String info){
		alert窗口.显示(title, info);
		out(title+","+info);
	}

	
	public void alert(String info){
		alert窗口.显示("温馨提示", info);
	}
	
	public boolean confirm(String title,String info){
		同意窗口.显示(title, info);
		out(title+","+info+","+同意窗口.是否同意);
		return 同意窗口.是否同意;
	}
	
	public boolean confirm(String info){
		return confirm("温馨提示",info);
	}

}
