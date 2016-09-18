package com.lobinary.实用工具.主窗口;

import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JLabel;

public class 关于我们弹出窗口 extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -680638894168473506L;

	public 关于我们弹出窗口(Frame owner, boolean modal) {
		super(owner, modal);
		this.显示();
	}

	/**
	 * Create the dialog.
	 */
	public void 显示() {
		setTitle("关于我们");
		setSize(320, 102);
		getContentPane().setLayout(null);
		int px = getParent().getX();
		int py = getParent().getY();
		int pw = getParent().getWidth();
		int ph = getParent().getHeight();
		System.out.println(pw+":"+ph);
		setLocation(px+(pw-this.getWidth())/2, py+(ph-this.getHeight())/2); 
		JLabel lbllobxxx = new JLabel("<html> &nbsp;&nbsp;&nbsp;&nbsp;实用工具由Lobxxx提供技术支持,主要针对日常生活的所有实用工具进行汇总,方便不同工具的使用</html>");
		lbllobxxx.setBounds(10, 10, 284, 44);
		getContentPane().add(lbllobxxx);
	}

}
