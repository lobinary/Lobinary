package com.lobinary.AI.ËÄ×ÓÆå;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.*;

import javax.swing.*;

class ChessButton extends JButton 
{ 
	int hitFlag=0;
   

   public ChessButton()
   {
	   super();
	   Dimension size = getPreferredSize();
	   size.width = Math.max(size.width,
	   size.height);
	    size.height = size.width/3;
	   setPreferredSize(size);
	   
	  

	   setContentAreaFilled(false);
	   this.addMouseListener(new MouseAdapter(){
		  
	       public void mousePressed(MouseEvent arg0)
	       {
	    	  
	    	   
	       }
	   });
   }
   
  
   
   public void setHit(int i)
   {
	   hitFlag=i;
   }
   
   protected void paintComponent(Graphics g) {	    
   g.setColor(this.getBackground());
   g.fillOval(2, 2, getSize().width-4,getSize().height-4);  
   }
   
   
} 

