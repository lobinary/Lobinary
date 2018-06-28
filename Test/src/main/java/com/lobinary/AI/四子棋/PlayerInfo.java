package com.lobinary.AI.四子棋;
import javax.imageio.ImageIO; //玩家信息显示
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;



public class PlayerInfo extends JPanel {
 
	public String m_Name;
	 
	public Color m_Color;
	 
	public JLabel m_NameCtrl;
	 
	public JLabel m_ColorCtrl;
	
	public ChessButton m_ColorBtn;
	

	
	JPanel panel;
	
	public PlayerInfo(String name,Color color)
	{
		m_Name=name;
		m_Color=color;
		
		InitUser();
	}
	 
	public void SetName(String name) {
		m_Name=name;
	}
	 
	public void SetColor(Color color) {
		m_Color=color;
	}
	 
	public void InitUser()
	{
		Font font=new Font("宋体",Font.BOLD,16);
		if(m_NameCtrl==null)
		{
			m_NameCtrl=new JLabel();
			m_NameCtrl.setFont(font);
			m_NameCtrl.setText("Player:"+m_Name);
		}
		
		if(m_ColorCtrl==null)
		{
			m_ColorCtrl=new JLabel();
			m_ColorCtrl.setFont(font);
			m_ColorCtrl.setText("Color:");
		}
		m_ColorBtn=new ChessButton();
		m_ColorBtn.setBackground(m_Color);
		panel=new JPanel();
		
		this.setLayout(null);
		this.add(m_NameCtrl);
		this.add(m_ColorCtrl);
		this.add(m_ColorBtn);
		this.add(panel);
		m_NameCtrl.setBounds(10,10,150,30);
		m_ColorCtrl.setBounds(10,30,150,30);
		m_ColorBtn.setBounds(60,60,30,30);
		panel.setBounds(0,0,178,100);
	}
	
	public void UpdateUserInfo()
	{
		Font font=new Font("宋体",Font.BOLD,16);
		
			m_NameCtrl=new JLabel();
			m_NameCtrl.setFont(font);
			m_NameCtrl.setText("Player:"+m_Name);
		
			m_ColorCtrl=new JLabel();
			m_ColorCtrl.setFont(font);
			m_ColorCtrl.setText("Color:");
		
		m_ColorBtn=new ChessButton();
		m_ColorBtn.setBackground(m_Color);
		panel=new JPanel();
		
		this.setLayout(null);
		this.add(m_NameCtrl);
		this.add(m_ColorCtrl);
		this.add(m_ColorBtn);
		this.add(panel);
		m_NameCtrl.setBounds(10,10,150,30);
		m_ColorCtrl.setBounds(10,30,150,30);
		m_ColorBtn.setBounds(60,60,30,30);
		panel.setBounds(0,0,180,100);
	}
	protected void paintComponent(Graphics g)
	{
		
		
		
		super.paintComponent(g);
		int width=getWidth();
		int height=getHeight();
		
		g.setColor(Color.black);
		g.drawRect(0, 0, width-1, height-1);
		
		
	}
}
 
