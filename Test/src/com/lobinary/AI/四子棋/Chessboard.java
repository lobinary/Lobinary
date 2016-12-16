package com.lobinary.AI.ËÄ×ÓÆå;
import java.awt.*;

import javax.swing.*;
public class Chessboard extends JPanel {
  int xNums,yNums;
  GridLayout boardSize;
  
  public void setBoardSize(int x,int y){
	  boardSize = new GridLayout(x,y);
  }
  
  public Chessboard(GridLayout boardSize){
	 super(boardSize);
	 xNums = boardSize.getColumns();
	 yNums = boardSize.getRows();
  }
  
 
  
  public void paintComponent(Graphics g){
	  super.paintComponent(g);
	  
	  g.setColor(Color.black);
	  
	  int height = this.getHeight();
	  int width = this.getWidth();
	  g.drawRect(0, 0, width-1, height-1);
	  
	 for(int i=1;i<yNums;i++)
	 {		 		  
		  g.drawLine(0, height/yNums*i, width, height/yNums*i);		
	  }	  
	  for(int i=1;i<xNums;i++)
	  {		 		
		  g.drawLine( width/xNums*i,0,width/xNums*i , height);		 
	  }

  }
  
 
}
