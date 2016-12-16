package com.lobinary.AI.四子棋;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

class AboutFrame extends JFrame implements MouseListener{
	private JPanel aboutPane;
	private JLabel msg;
	private JLabel msg1;
	private JLabel msg2;
	private JButton exit;

	public AboutFrame(String strName) {
		super(strName);
		setSize(250, 170);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		aboutPane = new JPanel();
		msg = new JLabel("人工智能课程设计");
		msg1 = new JLabel(" Enjoy The Game!           ");
		msg2 = new JLabel(" Vision 1.0                ");
		exit = new JButton(" 退出");
		exit.addMouseListener(this);
		aboutPane.add(msg);
		aboutPane.add(msg1);
		aboutPane.add(msg2);
		aboutPane.add(exit);

		setContentPane(aboutPane);
		setLocation(250,220);
	}


	// the event handle to deal with the mouse click
	public void mouseClicked(MouseEvent e) {
		dispose();	
	}

	public void mousePressed(MouseEvent e) {
		
	}
	public void mouseReleased(MouseEvent e) {
		
	}
	public void mouseExited(MouseEvent e) {
	

	}
	public void mouseEntered(MouseEvent e) {
		

	}


}
