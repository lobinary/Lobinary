package com.lobinary.AI.四子棋;
import javax.swing.*;
import java.awt.*;

public class GameInfo extends JPanel {
 
	private String Player1Name="Player1";
	 
	private String Player2Name="Player2";
	 
	private int player=1;
	
	public int WinFlag=0;
	
	public int EqualFlag=0;
	
	//public int count = 5;
	
	private JLabel label=new JLabel("");
	
	private JLabel label1=new JLabel("");
	
	

	
    JPanel panel;
	
	public GameInfo()
	{
		
		panel=new JPanel();
		Font font=new Font("宋体",Font.BOLD,16);
		Font font1=new Font("宋体",Font.BOLD,14);
		label.setFont(font);
		label1.setFont(font1);
		this.setLayout(null);
		
		
		
		this.add(label);
		this.add(label1);
		this.add(panel);
		label.setBounds(10,25,120,30);
		label1.setBounds(10,45,120,30);
		panel.setBounds(0,0,240,100);
	}
	 
	public void ShowMessage() 
	{
		if(WinFlag==0&&EqualFlag==0)
		{
			if(player==1)
			{
				label.setText(Player1Name+"play");
				
			
			}
			else
			{
				label.setText(Player2Name+"play");
	
			}
		}
	}
	 
	/*public void ShowTime(int count){
		if(WinFlag==0&&EqualFlag==0)
		{label1.setText("Time:"+count);}
	}*/
	
	public void SetP1Name(String Name) {
		Player1Name=Name;
	}
	 
	public void SetP2Name(String Name) {
		Player2Name=Name;
	}
	 
	public void SetPlayer(int id) 
	{
		player=id;
	}
	
	public void ShowWin()
	{
		WinFlag=1;
		if(player==1)
		{
			label.setText(Player1Name+"Win");
			
	
		}
		else
		{
			label.setText(Player2Name+"Win");
			
	
		}
		
	}
	
	public void ShowDraw()
	{
		
			label.setText("Equal!");
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
 
