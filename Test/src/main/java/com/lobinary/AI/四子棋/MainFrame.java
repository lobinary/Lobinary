package com.lobinary.AI.四子棋;
import com.lobinary.AI.四子棋.PlayerInfo;
import com.lobinary.AI.四子棋.SetDialog;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainFrame extends JFrame implements ActionListener{
	
	private PlayerInfo player1Info,player2Info;
	
	public GameInfo infoShow;
	
	private Chessboard board;
	
	
	private JPanel background;
	
	private Chess  gameChess;
	
	private ChessButton cButton[][];
	
	
	private SetDialog dialog;
	
	public int difficulty = 1;
	
	private AboutFrame about;
	
	private ExplainFrame explain;

	private int WinFlag = 0;
	
	public int Mode = 0;
	
	private int player=1;
	
	private int Row=8;
	private int Col=8;
	

	
	private JButton Again ;

	
	private JMenuBar mb;
	private JMenu mGame;
	private JMenu mAbout;
	private JMenuItem mBegin;       
	private JMenuItem mAgain;       
	private JMenuItem mSet;          
	private JMenuItem mExit;       
	private JMenuItem mabout;        
	private JMenuItem mExplain;
	
	
    public MainFrame(){	
    	
     Container container = this.getContentPane(); 

	 mb = new JMenuBar();
	 mGame = new JMenu(" Game ");	
	 mAbout = new JMenu(" About ");
	 mBegin = new JMenuItem("Begin    Ctrl+B"); 
	 mBegin.addActionListener(this); 
	 mSet = new JMenuItem(" Set   Ctrl+S");
	 mSet.addActionListener(this);
	 mAgain = new JMenuItem(" Again    Ctrl+A");
	 mAgain.addActionListener(this);
	 mExit = new JMenuItem(" Exit   Ctrl+Q");
	 mExit.addActionListener(this);
	 mabout = new JMenuItem(" About");
	 mabout.addActionListener(this);
	 mExplain = new JMenuItem(" Explain");
	 mExplain.addActionListener(this);
	
	 mb.add(mGame);	 
	 mb.add(mAbout);
	 mGame.add(mBegin);
	 mGame.addSeparator();
	 mGame.add(mSet);
	 mGame.addSeparator();
	 mGame.add(mAgain);
	 mGame.addSeparator();
	 mGame.add(mExit);
	 mAbout.add(mabout);
	 mAbout.add(mExplain);
	 
	
	 Again=new JButton("Again");
	 Again.addActionListener(this);
	 

	 

	 gameChess = new Chess(Row,Col);
	 player1Info = new PlayerInfo("Player1",Color.red);
	 cButton = new ChessButton[Row][Col];
	 player2Info = new PlayerInfo("Player2",Color.yellow);
	 background = new JPanel();
	 infoShow = new GameInfo();
	 GridLayout layout = new GridLayout(Row,Col);
	 board = new Chessboard(layout);
	 board.setBackground(Color.blue);
	 

	 about = new AboutFrame("About");
	 explain = new ExplainFrame("How to play");
    

	 
		 for(int i=0;i<Row;i++)
		{
			for(int j=0;j<Col;j++)
			{
				cButton[i][j] = new ChessButton();
				
				cButton[i][j].addActionListener(this);
				
				cButton[i][j].setEnabled(false);
				
				board.add(cButton[i][j]);
			}
		}
	 

	 container.add(Again);
	 container.add(board);
	 container.add(player1Info);
	 container.add(player2Info);
	 container.add(infoShow);
	 container.add(background);
	 container.setLayout(null);

	 Again.setBounds(430,120,100,30);
	 board.setBounds(170,200,8*50,8*50);
	 player1Info.setBounds(0, 0, 178, 100);
	 player2Info.setBounds(418,0, 178, 100);
	 infoShow.setBounds(180, 0, 238, 100);
	 background.setBounds(0,100,600,600);
	 this.setSize(602, 700);
	 this.setLocation(100, 100);
	 this.setJMenuBar(mb);
	 
    
     
		
	 
  }
  public static void main(String args[]){
	 
	  MainFrame frame = new MainFrame();
	  frame.setVisible(true);	 
	  frame.setTitle("Connection 4");
	  frame.setResizable(false);
	  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  
  }
  

 public void actionPerformed(ActionEvent e) {
	
	if(e.getSource().equals(mBegin)){
		gameStart();
	}
	
	if(e.getSource().equals(mAgain)){
		gameAgain();
	}
	
	if(e.getSource().equals(mSet)){
		gameSet();
	}
	
	if(e.getSource().equals(mExit)){
		dispose();
		
	}
	
	if(e.getSource().equals(Again)){
		gameAgain();
	}
	
	if(e.getSource().equals(mabout)){
		about.setVisible(true);
	}
	
	if(e.getSource().equals(mExplain)){
		explain.setVisible(true);
	}
	
	 for(int i=0;i<Row;i++)
		{
			for(int j=0;j<Col;j++)
			{  
				
				
				if(e.getSource().equals(cButton[i][j])){
					
					if(Mode==1){
						if(player==1&&cButton[i][j].hitFlag==0){							
							System.out.println("yeah1");
							cButton[gameChess.place(j)][j].setBackground(Color.red);
							cButton[gameChess.place(j)][j].hitFlag=player;
							gameChess.setChess(player, gameChess.place(j), j);							
							if(gameChess.IsWin(player, gameChess.place(j), j)){
								System.out.println("Win!!");
								infoShow.ShowWin();
								gameStop();
								
							}
						
							if(gameChess.IsDraw()){
								infoShow.ShowDraw();
								gameStop();
							} 
							gameChess.AddChess(j);
						
							player=2;
							infoShow.SetPlayer(player);
							infoShow.ShowMessage();
							
							
							}
							else 
							   if(player==2&&cButton[i][j].hitFlag==0){
								System.out.println("yeah2");
								cButton[gameChess.place(j)][j].setBackground(Color.yellow);
								cButton[gameChess.place(j)][j].hitFlag=player;
								gameChess.setChess(player, gameChess.place(j), j);
								if(gameChess.IsWin(player, gameChess.place(j), j)){
									System.out.println("Win!!");
									infoShow.ShowWin();
									gameStop();
								}
								if(gameChess.IsDraw()){
									infoShow.ShowDraw();
									gameStop();
								}
								gameChess.AddChess(j);
								player=1;
								infoShow.SetPlayer(player);
								infoShow.ShowMessage();
								
								
								}
						
					}
					
					   else {
						   if(player==1&&cButton[i][j].hitFlag==0){
								System.out.println("yeah1");
								cButton[gameChess.place(j)][j].setBackground(Color.red);
								cButton[gameChess.place(j)][j].hitFlag=player;
								gameChess.setChess(player, gameChess.place(j), j);
								if(gameChess.IsWin(player, gameChess.place(j), j)){
									System.out.println("Win!!");
									WinFlag = 1;
									infoShow.ShowWin();
									gameStop();
								}
								if(gameChess.IsDraw()){
									infoShow.ShowDraw();
									gameStop();
								}
								gameChess.AddChess(j);
								
								player=2;
								infoShow.SetPlayer(player);
								infoShow.ShowMessage();
								
								
								}		
						   if(player==2&&WinFlag==0){
						   System.out.println("cumputer");
						   int temp;
						   if(difficulty == 1){
						        temp = gameChess.AIplace();
						   } else {
							   temp = gameChess.ranPlace();
						   }
							cButton[gameChess.place(temp)][temp].setBackground(Color.yellow);
							cButton[gameChess.place(temp)][temp].hitFlag=player;
							gameChess.setChess(player, gameChess.place(temp), temp);
							if(gameChess.IsWin(player, gameChess.place(temp), temp)){
								System.out.println("Win!!");
								infoShow.ShowWin();
								gameStop();
							}
							if(gameChess.IsDraw()){
								infoShow.ShowDraw();
								gameStop();
							}
							gameChess.AddChess(temp);
							player=1; 
							infoShow.SetPlayer(player);
							infoShow.ShowMessage();
							
							
						   }
					   }
					
				}
				
			}
		}
	 
	 
			

	
}


public void gameSet() {
	// TODO Auto-generated method stub
	 dialog=new SetDialog(this,Mode,player1Info,player2Info,infoShow);
	 dialog.setVisible(true);
		
		
}
public void gameStart(){
	
	 player = 1;
	 gameChess = new Chess(Row,Col);
	 infoShow.ShowMessage();
	 for(int i=0;i<Row;i++)
		{
			for(int j=0;j<Col;j++)
			{
				cButton[i][j].setEnabled(true);		
			}
		}
	 
	 
 }
 
 public void gameAgain(){

	 
	 WinFlag = 0;
	 player = 1;
	 infoShow.SetPlayer(player);
	 infoShow.WinFlag = 0;
	 gameChess = new Chess(Row,Col);
	 infoShow.ShowMessage();
	 for(int i=0; i<Row; i++){//
		 for(int j=0; j<Col; j++){
			 cButton[i][j].setBackground(getBackground());
			 cButton[i][j].setEnabled(true);
			 cButton[i][j].hitFlag=0;
		 }
	 }
	 
	
 }

 public void gameStop(){

	 for(int i=0;i<Row;i++)
		{
			for(int j=0;j<Col;j++)
			{
				cButton[i][j].setEnabled(false);		
			}
		}
 }
 

 public void autoRun(int count){

	 if(count==50){
		 System.out.println("autoRun");
		   int temp = gameChess.ranPlace();
			
			cButton[gameChess.place(temp)][temp].hitFlag=player;
			gameChess.setChess(player, gameChess.place(temp), temp);
			gameChess.AddChess(temp);
			infoShow.SetPlayer(3-player);

			if(player==1){
				cButton[gameChess.place(temp)][temp].setBackground(Color.red);				
				player = 2;
				
				
			}else{
				cButton[gameChess.place(temp)][temp].setBackground(Color.yellow);
				player = 1;
				
			}

			if(gameChess.IsWin(player, gameChess.place(temp), temp)){
				System.out.println("Win!!");
				infoShow.ShowWin();
				gameStop();
			}

			if(gameChess.IsDraw()){
				infoShow.ShowDraw();
				gameStop();
			}						
			infoShow.ShowMessage();
	}
	
	 
 }
 

}




