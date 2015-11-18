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
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import com.lobinary.android.common.pojo.communication.ConnectionBean;
import com.lobinary.android.common.service.communication.socket.CommunicationSocketService;

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
	private static int i = 0;
	
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
		tabbedPane.addTab("可连接设备", null, tabLogPanel, null);
		GridBagLayout gbl_tabLogPanel = new GridBagLayout();
		gbl_tabLogPanel.columnWidths = new int[]{532, 104, 104, 0};
		gbl_tabLogPanel.rowHeights = new int[]{54, 54, 54, 54, 54, 54, 54, 54, 0};
		gbl_tabLogPanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_tabLogPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		tabLogPanel.setLayout(gbl_tabLogPanel);
		
		/*******************************每个可连设备的标配***********************************/
//		JLabel connectableLabel = new JLabel("可连接设备1");
//		GridBagConstraints gbc_connectableLabel = new GridBagConstraints();
//		gbc_connectableLabel.fill = GridBagConstraints.VERTICAL;
//		gbc_connectableLabel.insets = new Insets(0, 0, 5, 5);
//		gbc_connectableLabel.gridx = 0;
//		gbc_connectableLabel.gridy = 0;
//		tabLogPanel.add(connectableLabel, gbc_connectableLabel);
//		
//		JButton connectableButton = new JButton("连接");
//		GridBagConstraints gbc_connectableButton = new GridBagConstraints();
//		gbc_connectableButton.fill = GridBagConstraints.BOTH;
//		gbc_connectableButton.insets = new Insets(0, 0, 5, 5);
//		gbc_connectableButton.gridx = 1;
//		gbc_connectableButton.gridy = 0;
//		tabLogPanel.add(connectableButton, gbc_connectableButton);
//		
//		JButton connectableDelButton = new JButton("删除");
//		GridBagConstraints gbc_connectableDelButton = new GridBagConstraints();
//		gbc_connectableDelButton.fill = GridBagConstraints.BOTH;
//		gbc_connectableDelButton.insets = new Insets(0, 0, 5, 0);
//		gbc_connectableDelButton.gridx = 2;
//		gbc_connectableDelButton.gridy = 0;
//		tabLogPanel.add(connectableDelButton, gbc_connectableDelButton);
		
		//粑粑问我除了map是不是不会用别的T.T
		//放置button的规则未完待续
		Map<String,JLabel> jlabelMap= new HashMap<String,JLabel>();
		Map<String,JButton> connectButton = new HashMap<String,JButton>();
		Map<String,JButton> deleteButton = new HashMap<String,JButton>();
		Map<String,GridBagConstraints> labelGridBag = new HashMap<String,GridBagConstraints>();
		Map<String,GridBagConstraints> connectButtonGridBag = new HashMap<String,GridBagConstraints>();
		Map<String,GridBagConstraints> deleteButtonGridBag = new HashMap<String,GridBagConstraints>();
		for (ConnectionBean conBean : CommunicationSocketService.connectionMap.values()) {
			String Name = "connectableEquip"+i;
			jlabelMap.put(Name, new JLabel());
			connectButton.put(Name, new JButton("连接"));
			deleteButton.put(Name, new JButton());
			
			labelGridBag.put(Name, new GridBagConstraints());
			labelGridBag.get(Name).fill = GridBagConstraints.VERTICAL;
			labelGridBag.get(Name).insets = new Insets(0, 0, 5, 5);
			labelGridBag.get(Name).gridx = 0;
			labelGridBag.get(Name).gridy = 0;
			tabLogPanel.add(jlabelMap.get(Name), labelGridBag.get(Name));
			
			connectButtonGridBag.put(Name, new GridBagConstraints());
			connectButtonGridBag.get(Name).fill = GridBagConstraints.BOTH;
			connectButtonGridBag.get(Name).insets = new Insets(0, 0, 5, 5);
			connectButtonGridBag.get(Name).gridx = 1;
			connectButtonGridBag.get(Name).gridy = 0;
			tabLogPanel.add(connectButton.get(Name), connectButtonGridBag.get(Name));
			
			deleteButtonGridBag.put(Name, new GridBagConstraints());
			deleteButtonGridBag.get(Name).fill = GridBagConstraints.BOTH;
			deleteButtonGridBag.get(Name).insets = new Insets(0, 0, 5, 0);
			deleteButtonGridBag.get(Name).gridx = 2;
			deleteButtonGridBag.get(Name).gridy = 0;
			tabLogPanel.add(deleteButton.get(Name), deleteButtonGridBag.get(Name));
			
			i++;
		}
		
		/*******************************每个可连设备的标配***********************************/
		
		JPanel contactsPanel = new JPanel();
		GridBagLayout gbl_contactsPanel = new GridBagLayout();
		gbl_contactsPanel.columnWidths = new int[]{532, 104, 104, 0};
		gbl_contactsPanel.rowHeights = new int[]{54, 54, 54, 54, 54, 54, 54, 54, 0};
		gbl_contactsPanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contactsPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contactsPanel.setLayout(gbl_contactsPanel);
		tabbedPane.addTab("通讯录", null, contactsPanel, null); 
		
		/*******************************每个好友的标配***********************************/

		
		JLabel label = new JLabel("通讯录设备1");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.fill = GridBagConstraints.VERTICAL;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		contactsPanel.add(label, gbc_label);
		
		JButton button = new JButton("连接");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.fill = GridBagConstraints.BOTH;
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 1;
		gbc_button.gridy = 0;
		contactsPanel.add(button, gbc_button);
		
		JButton contactDelButton = new JButton("删除");
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.fill = GridBagConstraints.BOTH;
		gbc_button_1.insets = new Insets(0, 0, 5, 0);
		gbc_button_1.gridx = 2;
		gbc_button_1.gridy = 0;
		contactsPanel.add(contactDelButton, gbc_button_1);
		/*******************************每个好友的标配***********************************/
		
		frame.getContentPane().add(tabbedPane);
		
		frame.setVisible(true);
	}
}
