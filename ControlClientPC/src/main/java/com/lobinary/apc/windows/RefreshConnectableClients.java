/*
 * @(#)RefreshConnectableClient.java     V1.0.0      @下午3:51:27
 *
 * 项目名称: ControlClientPC
 *
 * 更改 信息:
 *    作者        				   日期        			描述
 *    ============  	================  =======================================
 *    lobinary       	  2015年10月30日    	创建文件
 *
 */
package com.lobinary.apc.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 * <pre>
 * 
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月30日 下午3:51:27
 * @version V1.0.0 描述 : 创建文件RefreshConnectableClient
 * 
 *         
 * 
 */
public class RefreshConnectableClients {
	private JFrame frame;
	private Image logo;
	
	RefreshConnectableClients(){
		initialize();
	}
	
	public static void main(String[] args) {
		new RefreshConnectableClients();
	}
	
	private void initialize(){
		frame = new JFrame();
		frame.setBounds(300, 100, 759, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			logo = ImageIO.read(this.getClass().getResource("/pic/apc-logo.png"));
			frame.setIconImage(logo);
			frame.setTitle("获取可连接设备");
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame.setIconImage(logo);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		JPanel tabLogPanel = new JPanel();
		tabLogPanel.setBackground(Color.GREEN);
		tabLogPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.gridx=0;  
        gbc.gridy=0;  
        gbc.gridwidth=2;  
        gbc.gridheight=1;   
        gbc.anchor=GridBagConstraints.NORTHWEST;  
        gbc.fill=GridBagConstraints.NONE;  
        gbc.insets=new Insets(0,0,0,0);  
          
          
		
		JScrollPane scrollPane = new JScrollPane();
		tabLogPanel.add(scrollPane);
		tabbedPane.addTab("可连接设备", null, tabLogPanel, null);
		
		JPanel contactsPanel = new JPanel();
		JScrollPane scrollPane2 = new JScrollPane();
		contactsPanel.add(scrollPane2);
		tabbedPane.addTab("通讯录", null, contactsPanel, null);
		
		JPanel conListPanel = new JPanel();
		conListPanel.setBackground(Color.BLUE);
		tabLogPanel.add(conListPanel,gbc);
		
		JPanel conPanel1 = new JPanel();
		conPanel1.setBackground(Color.YELLOW);
		conListPanel.add(conPanel1);
		conPanel1.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton conEquip1 = new JButton("可连接设备1");
		conPanel1.setLayout(new FlowLayout());
		conPanel1.add(conEquip1);
		
		frame.add(tabbedPane);
		
		frame.setVisible(true);
	}
}
