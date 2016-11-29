package com.lobinary.实用工具.文件夹工具;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.lobinary.实用工具.实用工具;
import com.lobinary.实用工具.主窗口.实用工具标签标准类;

public class 文件夹扫描工具  extends 实用工具标签标准类{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1369722238823156189L;
	
	private static final String SCAN_NUM = "SCAN_NUM";
	private static final String STANDARD_CONFIG = "STANDARD_CONFIG";
	private static JTextArea logArea = new JTextArea();;
//	private File configFile;
	/**
	 * 配置Map
	 */
	Map<String,Object> configMap;
	
	Map<String,Boolean> scanMap = new LinkedHashMap<String,Boolean>();

	/**
	 * Create the application.
	 */
	public 文件夹扫描工具() {
		this.setSize(1280, 800);
		setLayout(null);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		
		JButton btnNewButton = new JButton("点击开始扫描目录");
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				log("准备开始第"+configMap.get(SCAN_NUM)+"次目录扫描...");
				updateConfig(SCAN_NUM,(Integer)configMap.get(SCAN_NUM)+1);
				updateScanList();
				checkData();
				log("共扫描到以上数据\n如想以此数据作为标准数据校验以后数据\n请点击“设为标准数据结构”按钮");
				
			}


		});
		add(btnNewButton, BorderLayout.NORTH);
		
		logArea.setSize(320, 450);
		logArea.setEditable(false);
		logArea.setText("欢迎使用L电脑目录变更扫描器!\n本扫描器帮助您扫描每个磁盘根目录的文件夹变更");
		add(new JScrollPane(logArea), BorderLayout.CENTER);
		
		JButton button = new JButton("设为标准数据结构");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateConfig(STANDARD_CONFIG, scanMap);
				log("已经设置为标准目录结构");
			}
		});
		add(button, BorderLayout.SOUTH);
	}

	/**
	 * 校验已扫描数据
	 */
	private void checkData() {
		@SuppressWarnings("unchecked")
		Map<String,Boolean> standardConfig = (Map<String, Boolean>) configMap.get(STANDARD_CONFIG);
		if(standardConfig==null){
			log("-----------------------以下为新增文件夹-----------------------------------------------------------------------------");
			for(String key:scanMap.keySet()){
				if(!scanMap.get(key)){
					log(key);
				}
			}
			log("-----------------------以上为新增文件夹-----------------------------------------------------------------------------");
			log("由于您还未设置标准数据结构\n默认扫描到所有数据均为新增数据");
			return;
		}
		log("-----------------------以下为减少文件夹-----------------------------------------------------------------------------");
		for(String key:standardConfig.keySet()){
			if(scanMap.containsKey(key)){
				scanMap.put(key, true);
			}else{
				log(key);
			}
		}
		log("-----------------------以上为减少文件夹-----------------------------------------------------------------------------");
		log("");
		log("-----------------------以下为新增文件夹-----------------------------------------------------------------------------");
		for(String key:scanMap.keySet()){
			if(!scanMap.get(key)){
				log(key);
			}
		}
		log("-----------------------以上为新增文件夹-----------------------------------------------------------------------------");
	}
	
	/**
	 * 更新配置文件
	 * @param key
	 * @param value
	 */
	private void updateConfig(String key, Object value) {
		configMap.put(key, value);
		saveConfig();
	}
	

	/**
	 * 打日志
	 * @param log
	 */
	public void log(String log){
		System.out.println("输出日志："+log);
		logArea.append("\n");
		logArea.append(log);
		实用工具.log(log);
	}

	/**
	 * 更新已扫描数据
	 */
	private void updateScanList() {
		scanMap.clear();
		List<String> diskList = scanDisk();
		for(String diskPath:diskList){
//			log("---------------------------------------------------------------------------------------------------------------");
			File disk = new File(diskPath);
			if(disk.exists()){
				for(String df:disk.list()){
//					log(diskPath+df);
					scanMap.put(diskPath+df,false);
				}
			}else{
				log("检测到空磁盘"+diskPath+"，不予以扫描！");
			}
		}
//		log("---------------------------------------------------------------------------------------------------------------");
	}
	
	/**
	 * 扫描根磁盘
	 * @return
	 */
	public List<String> scanDisk(){
//		FileSystemView fsv = FileSystemView.getFileSystemView();
		// 列出所有windows 磁盘
		File[] fs = File.listRoots();
		// 显示磁盘卷标
		List<String> diskList = new ArrayList<String>();
		for (int i = 0; i < fs.length; i++) {
	//		log(fsv.getSystemDisplayName(fs[i]));
//			log(fs[i].getAbsolutePath());
//			log(fs[i].getPath().replace("\\", "/"));
			diskList.add(fs[i].getPath().replace("\\", "/"));
	//		System.out.print("总大小" + FormetFileSize(fs[i].getTotalSpace()));
	//		System.out.println("剩余" + FormetFileSize(fs[i].getFreeSpace()));
		}
		return diskList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void loadConfig() {
		configMap = (Map<String, Object>) 实用工具.getConfig("文件夹扫描工具配置");
		if(configMap==null){
			configMap = new LinkedHashMap<String,Object>();
			configMap.put(SCAN_NUM, 1);
			实用工具.saveConfig("文件夹扫描工具配置", configMap);
		}
		log("文件夹扫描工具配置加载完毕");
	}

	@Override
	public void saveConfig() {
		实用工具.saveConfig("文件夹扫描工具配置", configMap);
		log("文件夹扫描工具配置保存完毕");
	}

}
