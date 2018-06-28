package com.lobinary.AI.四子棋;

import javax.swing.JPanel;




import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.JTextField;
import java.awt.event.KeyEvent;
import javax.swing.JButton;

import javax.swing.JRadioButton;
import java.awt.*;
import javax.swing.*;

public class SetDialog extends JDialog {

	private static final long serialVersionUID = 1L;//

	private JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JTextField jTextField = null;
	
	

	private JLabel jLabel2 = null;

	private JButton jButton = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JTextField jTextField1 = null;

	private JLabel jLabel5 = null;

	private JButton jButton1 = null;

	private JLabel jLabel6 = null;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;
	
	private JLabel jLabel7 = null;
	
	private JRadioButton jRadioButton2 = null;
	
	private JRadioButton jRadioButton3 = null;

	private JButton jButton2 = null;

	private JButton jButton3 = null;
	

	public String user1name,user2name;
	public Color user1color,user2color;
	
	
	
	public PlayerInfo user1,user2;
	public GameInfo info;
	public int PlayMode;
	public MainFrame frame;
	
	ButtonGroup group=new ButtonGroup();  
	ButtonGroup group1=new ButtonGroup();

	
	
	
	public SetDialog(MainFrame frame,int playmode,PlayerInfo player1,PlayerInfo player2,GameInfo info) {
		
		this.frame = frame;
		this.PlayMode=playmode;
		this.user1 = player1;
		this.user2 = player2;
		this.info=info;
		initialize();
	}

	

	private void initialize() {
		group.add(getJRadioButton());
		group.add(getJRadioButton1());
		group1.add(getJRadioButton2());
		group1.add(getJRadioButton3());
		getJRadioButton1().setSelected(true);
		getJRadioButton3().setSelected(true);
		this.setTitle("INITIAL");
		this.setSize(300,400);

        this.setLocation(200,200);
		
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(25, 130, 61, 18));
			jLabel6.setText("Play Mode:");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(25, 200, 61, 18));
			jLabel7.setText("Difficulty:");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(155, 94, 53, 17));
			jLabel5.setText("Color:");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(23, 97, 38, 16));
			jLabel4.setText("Name:");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(24, 71, 65, 17));
			jLabel3.setText("Player2 Information:");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(158, 43, 49, 17));
			jLabel2.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel2.setText("Color:");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(24, 42, 37, 17));
			jLabel1.setText("Name:");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(24, 16, 64, 17));
			jLabel.setText("Player1 Information:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setName("Set");
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(getJTextField1(), null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(getJRadioButton(), null);
			jContentPane.add(getJRadioButton1(), null);
			jContentPane.add(getJRadioButton2(), null);
			jContentPane.add(getJRadioButton3(), null);
			jContentPane.add(getJButton2(), null);
			jContentPane.add(getJButton3(), null);
		}
		return jContentPane;
	}

	private JRadioButton getJRadioButton3() {
		if (jRadioButton3 == null) {
			jRadioButton3 = new JRadioButton();
			jRadioButton3.setBounds(new Rectangle(25, 230, 82, 21));
			jRadioButton3.setText("Hard");
			jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
				}
			});
		}
		return jRadioButton3;
	}


	private JRadioButton getJRadioButton2() {
		if (jRadioButton2 == null) {
			jRadioButton2 = new JRadioButton();
			jRadioButton2.setBounds(new Rectangle(150, 230, 84, 19));
			jRadioButton2.setText("Easy");
			jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
				}
			});
		}
		return jRadioButton2;
	}


	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField("I");
			jTextField.setBounds(new Rectangle(73, 43, 62, 17));
		}
		return jTextField;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(200, 43, 63, 18));
			jButton.setBackground(Color.red);
			
			
		}
		return jButton;
	}

	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */

	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField("Computer");
			jTextField1.setBounds(new Rectangle(74, 95, 62, 17));
		}
		return jTextField1;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(200, 93, 62, 19));
			
			jButton1.setBackground(Color.yellow);
			
		}
		return jButton1;
	}

	/**
	 * This method initializes jRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(new Rectangle(25, 158, 84, 19));
			jRadioButton.setText("Two Player");
			jRadioButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jTextField.setText("Player1");
					jTextField1.setText("Player2");
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()

				}
			});
		}
		return jRadioButton;
	}

	/**
	 * This method initializes jRadioButton1	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
    
	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setBounds(new Rectangle(134, 157, 82, 21));
			jRadioButton1.setText("Computer");
			jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jTextField.setText("I");
					jTextField1.setText("Computer");
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()

				}
			});
		}
		return jRadioButton1;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */

	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new Rectangle(60, 270, 68, 23));
			jButton2.setText("Yes");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				user1name=getJTextField().getText();
				user2name=getJTextField1().getText();
				

				user1.SetName(user1name);
				user1.SetColor(user1color);
				
				user2.SetName(user2name);
				user2.SetColor(user2color);


				
				user1.m_NameCtrl.setText("Player:"+user1name);
				
				
				user2.m_NameCtrl.setText("Player:"+user2name);
				
				
				info.SetP1Name(user1name);
				info.SetP2Name(user2name);

                  if(jRadioButton.isSelected())
				{
					PlayMode=1;
					
					
				}
				else if(jRadioButton1.isSelected())
				{
					PlayMode=0;
					if(jRadioButton2.isSelected()){
						frame.difficulty = 0;
					}
					else if(jRadioButton3.isSelected()){
						frame.difficulty = 1;
					}
				}  
                  frame.Mode = PlayMode; 
				
				dispose();
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jButton3	
	 * 	
	 * @return javax.swing.JButton	
	 */
	//JButton
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(new Rectangle(160, 270, 71, 25));
			jButton3.setText("Cancel");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); 
					// TODO Auto-generated Event stub actionPerformed()				
				   dispose();
				}
			});
			
		}
		return jButton3;
	}
	

	public String setUser1Name()
	{
		return user1name;
	}
	
	

}  
