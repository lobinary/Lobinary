package com.lobinary.AI.四子棋;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;


public class ExplainFrame extends JFrame implements MouseListener{
	private JTextPane jTextPanel;
	private JPanel panel;
	private JButton exit;
	public ExplainFrame(String strName){
		super(strName);
		this.setSize(320, 250);
		this.setLocation(200, 200);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		 jTextPanel = new JTextPane();
		 panel = new JPanel();
		 jTextPanel.setBorder(null);
		    jTextPanel.setEditable(false);
		    jTextPanel.setText(
		    		
		    		"Have a Good Time here !!!\n"
		    		
		    		);
		    exit = new JButton(" Quit ");
			exit.addMouseListener((MouseListener) this);
			
			panel.add(jTextPanel);
			panel.add(exit);
		    this.setContentPane(panel);

	}
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
