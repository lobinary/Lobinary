package com.boce.windows;

import javax.swing.JTextField; 
import javax.swing.JButton; 
import javax.swing.JPanel; 
import java.awt.event.ActionListener; 
import java.awt.event.ActionEvent; 
import javax.swing.JFileChooser; 
import javax.swing.JFrame; 
public class SystemOS extends JFrame implements ActionListener{ 
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
JPanel pnlMain; 
  JTextField txtfile; 
  JButton btnSelect; 
  JFileChooser fc = new JFileChooser(); 
  
  public SystemOS() { 
    pnlMain=new JPanel(); 
    this.getContentPane().add(pnlMain); 
    txtfile=new JTextField(10); 
    btnSelect =new JButton("选择"); 
    btnSelect.addActionListener(this); 
    pnlMain.add(txtfile); 
    pnlMain.add(btnSelect); 
  } 

  public void actionPerformed(ActionEvent e){ 
    if(e.getSource()==btnSelect){ 
/* 
    这是尤为重要的。因为JFileChooser默认的是选择文件，而需要选目录。 
    故要将DIRECTORIES_ONLY装入模型 
另外，若选择文件，则无需此句 
*/ 
      fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
      int intRetVal = fc.showOpenDialog(this); 
      if( intRetVal == JFileChooser.APPROVE_OPTION){ 
        txtfile.setText(fc.getSelectedFile().getPath()); 
      } 
    } 
  } 

  public static void main(String[] args){ 
    JFrame f = new SystemOS(); 
    f.setSize(200,300); 
    f.setVisible(true); 
  } 
} 