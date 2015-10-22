/*
 * @(#)ControlClientPCWindows.java     V1.0.0      @下午1:55:41
 *
 * 项目名称: ControlClientPC
 *
 * 更改 信息:
 *    作者        				   日期        			描述
 *    ============  	================  =======================================
 *    lobinary       	  2015年10月12日    	创建文件
 *
 */
package com.lobinary.apc.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.android.common.service.communication.CommunicationServiceInterface;
import com.lobinary.android.common.service.communication.socket.CommunicationSocketThread;
import com.lobinary.android.common.util.factory.CommonFactory;
import com.lobinary.android.common.util.log.LogUtil;
import com.lobinary.apc.util.initial.InitialUtil;

/**
 * <pre>
 * 互控系统-PC端窗口
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月12日 下午1:55:41
 * @version V1.0.0 描述 : 创建文件ControlClientPCWindows
 * 
 *         
 * 
 */
public class ControlClientPCWindows {
	
	private static Logger logger = LoggerFactory.getLogger(ControlClientPCWindows.class);

	private JFrame frame;
	private Image logo;
	public static JTextArea logTextArea = new JTextArea();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InitialUtil.initial();
					ControlClientPCWindows window = new ControlClientPCWindows();
					CommunicationServiceInterface communicationService = CommonFactory.getCommunicationService();
					communicationService.startServer();
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ControlClientPCWindows() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(400, 200, 859, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//		javax.swing.ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("pic/apc-logo.png"));
		
//		Toolkit kit =Toolkit.getDefaultToolkit();
//		Image image=kit.createImage(getClass().getResource("pic/apc-logo.png"));
//		frame.setIconImage(image);
		try {
			logo = ImageIO.read(this.getClass().getResource("/pic/apc-logo.png"));
			frame.setIconImage(logo);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		frame.setTitle("安卓PC互控系统-PC客户端");
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(frame, popupMenu);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("菜单");
		menuBar.add(fileMenu);
		
		JMenuItem openPropertiesMenuItem = new JMenuItem("打开配置信息");
		fileMenu.add(openPropertiesMenuItem);
		
		JMenuItem savePropertiesMenuItem = new JMenuItem("保存配置信息");
		fileMenu.add(savePropertiesMenuItem);
		
		JMenu helpMenu = new JMenu("帮助");
		menuBar.add(helpMenu);
		
		JMenuItem aboutAPCMenuItem = new JMenuItem("关于安卓PC互控系统");
		aboutAPCMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImageIcon ii = new ImageIcon(this.getClass().getResource("/pic/apc-logo-200.png"));
				String space = "                                    ";
				JOptionPane.showMessageDialog(frame, "欢迎使用安卓PC互控系统 V1.0版本\n"
						+ "安卓PC互控系统旨在帮助用户\n通过手机和电脑实现相互控制的功能\n功能将更加可定制化、人性化！\n "+space+"---by 刘婧然 吕斌","关于安卓PC互控系统",JOptionPane.OK_OPTION,ii);
			}
		});
		helpMenu.add(aboutAPCMenuItem);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setToolTipText("");
		panel.add(tabbedPane);
		
		JPanel tabControlPanel = new JPanel();
		tabControlPanel.setBackground(Color.WHITE);
		tabControlPanel.setSize(500, 500);
		tabbedPane.addTab("状态面板", null, tabControlPanel, "tip....");
		tabControlPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel statusPanel = new JPanel();
		tabControlPanel.add(statusPanel, BorderLayout.NORTH);
		statusPanel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.BLUE);
		statusPanel.add(panel_1);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		JToggleButton tglbtnNewToggleButton = new JToggleButton("安卓客户端连接状态(断开)");
		panel_1.add(tglbtnNewToggleButton);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.ORANGE);
		statusPanel.add(panel_2);
		panel_2.setLayout(new GridLayout(1, 0, 0, 0));
		
		JToggleButton tglbtnNewToggleButton_1 = new JToggleButton("呼叫婧然");
		panel_2.add(tglbtnNewToggleButton_1);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.CYAN);
		statusPanel.add(panel_3);
		panel_3.setLayout(new GridLayout(1, 0, 0, 0));
		
		JToggleButton commutest = new JToggleButton("输出日志测试按钮");
		commutest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.info("香一个！");
				//ControlServiceCommunication sendmsg = new ControlServiceCommunication();
			}
		});
		panel_3.add(commutest);
		
		JPanel panel_4 = new JPanel();
		statusPanel.add(panel_4);
		panel_4.setLayout(new GridLayout(0, 1, 0, 0));
		
		JToggleButton tglbtnNewToggleButton_4 = new JToggleButton("显示器开关(开启)");
		panel_4.add(tglbtnNewToggleButton_4);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.RED);
		statusPanel.add(panel_5);
		panel_5.setLayout(new GridLayout(0, 1, 0, 0));
		
		JToggleButton btnNewButton_1 = new JToggleButton("New button");
		panel_5.add(btnNewButton_1);
		
		JPanel panel_6 = new JPanel();
		statusPanel.add(panel_6);
		panel_6.setLayout(new GridLayout(1, 0, 0, 0));
		
		JToggleButton btnNewButton_2 = new JToggleButton("New button");
		panel_6.add(btnNewButton_2);
		
		JPanel panel_7 = new JPanel();
		statusPanel.add(panel_7);
		panel_7.setLayout(new BorderLayout(0, 0));
		
		JToggleButton toggleButton = new JToggleButton("关机");
		panel_7.add(toggleButton, BorderLayout.CENTER);
		
		JPanel panel_8 = new JPanel();
		statusPanel.add(panel_8);
		panel_8.setLayout(new GridLayout(1, 0, 0, 0));
		
		JToggleButton btnNewButton_3 = new JToggleButton("New button");
		panel_8.add(btnNewButton_3);
		
		JPanel panel_9 = new JPanel();
		panel_9.setBackground(Color.MAGENTA);
		statusPanel.add(panel_9);
		panel_9.setLayout(new GridLayout(0, 1, 0, 0));
		
		JToggleButton btnNewButton_4 = new JToggleButton("New button");
		panel_9.add(btnNewButton_4);
		
		JPanel operatePanel = new JPanel();
		operatePanel.setBackground(Color.WHITE);
		operatePanel.setSize(500, 500);
		tabbedPane.addTab("操作面板", null, operatePanel, null);
		operatePanel.setLayout(new BoxLayout(operatePanel, BoxLayout.X_AXIS));
		
		JPanel controlPanel = new JPanel();
		operatePanel.add(controlPanel, BorderLayout.NORTH);
		controlPanel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel panel_31 = new JPanel();
		panel_31.setBackground(Color.YELLOW);
		controlPanel.add(panel_31);
		panel_31.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton sendmsgButton = new JButton("发送短信");
		panel_31.add(sendmsgButton);
		
		JPanel panel_32 = new JPanel();
		panel_32.setBackground(Color.YELLOW);
		controlPanel.add(panel_32);
		panel_32.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton playMusicButton = new JButton("播放音乐");
		panel_32.add(playMusicButton);
		
		JPanel panel_33 = new JPanel();
		panel_33.setBackground(Color.YELLOW);
		controlPanel.add(panel_33);
		panel_33.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton printScreenButton = new JButton("截取屏幕");
		panel_33.add(printScreenButton);
		operatePanel.add(controlPanel, BorderLayout.NORTH);
		controlPanel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel panel_34 = new JPanel();
		panel_34.setBackground(Color.RED);
		controlPanel.add(panel_34);
		panel_34.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton viewPhotoButton = new JButton("浏览照片");
		panel_34.add(viewPhotoButton);
		
		JPanel panel_35 = new JPanel();
		panel_35.setBackground(Color.RED);
		controlPanel.add(panel_35);
		panel_35.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton powerOffButton = new JButton("关机");
		panel_35.add(powerOffButton);
		
		JPanel panel_36 = new JPanel();
		panel_36.setBackground(Color.RED);
		controlPanel.add(panel_36);
		panel_36.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton cameraButton = new JButton("摄像头控制");
		panel_36.add(cameraButton);
		
		JPanel tabLogPanel = new JPanel();
		tabbedPane.addTab("日志面板", null, tabLogPanel, null);
		tabLogPanel.setLayout(new BorderLayout(0, 0));
		
		logTextArea.setEditable(false);
		logTextArea.setLineWrap(true);
		tabLogPanel.add(logTextArea);
		
		
		
		
		
		JPanel controlPanel2 = new JPanel();
		
		
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
