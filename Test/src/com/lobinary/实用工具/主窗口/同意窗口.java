package com.lobinary.实用工具.主窗口;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;

public class 同意窗口 extends JDialog {

	public boolean 是否同意 = false;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -680638894168473501L;
	JLabel infoLable = new JLabel();

	public 同意窗口(Frame owner, boolean modal) {
		super(owner, modal);
		setName("同意窗口");
		是否同意 = false;
		setSize(252, 104);
		getContentPane().setLayout(null);
		int px = getParent().getX();
		int py = getParent().getY();
		int pw = getParent().getWidth();
		int ph = getParent().getHeight();
		setLocation(px+(pw-this.getWidth())/2, py+(ph-this.getHeight())/2); 
		infoLable.setBounds(10, 10, 284, 23);
		getContentPane().add(infoLable);

		JButton 同意按钮 = new JButton("准了");
		同意按钮.setBounds(20, 33, 90, 23);
		同意按钮.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				是否同意 = true;
				同意按钮.getParent().getParent().getParent().getParent().setVisible(false);
			}
		});
		getContentPane().add(同意按钮);
		JButton 不同意按钮 = new JButton("辣也不行");
		不同意按钮.setBounds(120, 33, 90, 23);
		不同意按钮.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				不同意按钮.getParent().getParent().getParent().getParent().setVisible(false);
			}
		});

//		getContentPane().addKeyListener(new KeyAdapter() {
//
//			@Override
//			public void keyPressed(KeyEvent e) {
//				System.out.println("e:"+e.getKeyCode());
//				if(e.getKeyCode() == KeyEvent.VK_ENTER){
//					是否同意 = true;
//					同意按钮.getParent().getParent().getParent().getParent().setVisible(false);
//				}
//				if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
//					同意按钮.getParent().getParent().getParent().getParent().setVisible(false);
//				}
//			}
//		
//		});
		getContentPane().add(不同意按钮);
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
