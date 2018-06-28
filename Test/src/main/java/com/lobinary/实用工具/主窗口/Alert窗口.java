package com.lobinary.实用工具.主窗口;

import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JButton;

public class Alert窗口 extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -680638894168472506L;
	JLabel infoLable = new JLabel("");

	public Alert窗口(Frame owner, boolean modal) {
		super(owner, modal);
		setName("Alert窗口");
		setSize(400, 104);
		getContentPane().setLayout(null);
		int px = getParent().getX();
		int py = getParent().getY();
		int pw = getParent().getWidth();
		int ph = getParent().getHeight();
		setLocation(px+(pw-this.getWidth())/2, py+(ph-this.getHeight())/2); 
		infoLable.setBounds(10, 10, 400, 23);
		getContentPane().add(infoLable);
		
		JButton 关闭按钮 = new JButton("朕知道了");
		关闭按钮.setBounds(120, 33, 93, 23);
		关闭按钮.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Container parent = 关闭按钮.getParent().getParent().getParent().getParent();
				System.out.println("parent:"+parent.getName());
				parent.setVisible(false);
			}
		});
		//以下代码不好使，暂时不知道解决方案，待以后处理
//		addKeyListener(new KeyAdapter() {
//
//			@Override
//			public void keyPressed(KeyEvent e) {
//				if(e.getKeyCode() == KeyEvent.VK_ENTER){
//					关闭按钮.getParent().getParent().getParent().getParent().setVisible(false);
//				}
//			}
//		
//		});
		getContentPane().add(关闭按钮);
	}

	/**
	 * Create the dialog.
	 */
	public void 显示(String title,String info) {
		setTitle(title);
		infoLable.setText(info);
		this.setVisible(true);
	}

}
