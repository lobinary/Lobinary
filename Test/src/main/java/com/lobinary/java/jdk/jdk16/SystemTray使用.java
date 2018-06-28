package com.lobinary.java.jdk.jdk16;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.lobinary.java.jdk.jdk16.systemtray.TrayFactory;

public class SystemTray使用 {
	static boolean 未生成下方图标 = true;
	static JFrame 初始化窗口;
	
	public static void main(String[] args) {
		初始化窗口 = 初始化窗口();
	}
	
	public static JFrame 初始化窗口(){
		JFrame frame = new JFrame();
		frame.setTitle("测试窗口");
		frame.setSize(400, 300);
		frame.setLocation(800, 200);
		JPanel p = new JPanel();
		JButton b = new JButton("点击按钮后台运作");
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(未生成下方图标){
					TrayFactory.createTray(初始化窗口,"/image/logo.jpg", "提示文字");
					未生成下方图标 = false;
				}
				frame.setVisible(false);
				System.out.println("窗口被隐藏，已经在后台运行中，点击右下角图标显示窗口");
			}
		});
		p.add(b);
		frame.add(p);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				System.exit(1);
			}
			
		});
		return frame;
	}

}
