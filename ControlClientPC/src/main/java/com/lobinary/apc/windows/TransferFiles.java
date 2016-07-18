/*
 * @(#)TransferFiles.java     V1.0.0      @下午10:41:40
 *
 * 项目名称: ControlClientPC
 *
 * 更改 信息:
 *    作者        				   日期        			描述
 *    ============  	================  =======================================
 *    lobinary       	  2015年11月15日    	创建文件
 *
 */
package com.lobinary.apc.windows;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 * <pre>
 * 
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年11月15日 下午10:41:40
 * @version V1.0.0 描述 : 创建文件TransferFiles
 * 
 *         
 * 
 */
public class TransferFiles {
	private JFrame frame = null;
	public String fileName = new String();
	
	public static void main(String[] args) {
		TransferFiles aa = new TransferFiles();
		aa.initialize();
	}
	
	private void initialize(){
		frame = new JFrame("文件传输");
		
		
		/******/
		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		jFileChooser.setCurrentDirectory(new java.io.File("F:\\"));
		int result = jFileChooser.showOpenDialog(frame);
		if(result==JFileChooser.APPROVE_OPTION){
			fileName=jFileChooser.getSelectedFile().getName();
		}
		/******/
		
//		frame.add(jFileChooser);
		frame.pack();
		frame.setVisible(true);
	}
}
