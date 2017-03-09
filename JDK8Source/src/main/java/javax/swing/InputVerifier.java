/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package javax.swing;

import java.util.*;

/**
 * The purpose of this class is to help clients support smooth focus
 * navigation through GUIs with text fields. Such GUIs often need
 * to ensure that the text entered by the user is valid (for example,
 * that it's in
 * the proper format) before allowing the user to navigate out of
 * the text field. To do this, clients create a subclass of
 * <code>InputVerifier</code> and, using <code>JComponent</code>'s
 * <code>setInputVerifier</code> method,
 * attach an instance of their subclass to the <code>JComponent</code> whose input they
 * want to validate. Before focus is transfered to another Swing component
 * that requests it, the input verifier's <code>shouldYieldFocus</code> method is
 * called.  Focus is transfered only if that method returns <code>true</code>.
 * <p>
 * The following example has two text fields, with the first one expecting
 * the string "pass" to be entered by the user. If that string is entered in
 * the first text field, then the user can advance to the second text field
 * either by clicking in it or by pressing TAB. However, if another string
 * is entered in the first text field, then the user will be unable to
 * transfer focus to the second text field.
 *
 * <pre>
 * import java.awt.*;
 * import java.util.*;
 * import java.awt.event.*;
 * import javax.swing.*;
 *
 * // This program demonstrates the use of the Swing InputVerifier class.
 * // It creates two text fields; the first of the text fields expects the
 * // string "pass" as input, and will allow focus to advance out of it
 * // only after that string is typed in by the user.
 *
 * public class VerifierTest extends JFrame {
 *     public VerifierTest() {
 *         JTextField tf1 = new JTextField ("Type \"pass\" here");
 *         getContentPane().add (tf1, BorderLayout.NORTH);
 *         tf1.setInputVerifier(new PassVerifier());
 *
 *         JTextField tf2 = new JTextField ("TextField2");
 *         getContentPane().add (tf2, BorderLayout.SOUTH);
 *
 *         WindowListener l = new WindowAdapter() {
 *             public void windowClosing(WindowEvent e) {
 *                 System.exit(0);
 *             }
 *         };
 *         addWindowListener(l);
 *     }
 *
 *     class PassVerifier extends InputVerifier {
 *         public boolean verify(JComponent input) {
 *             JTextField tf = (JTextField) input;
 *             return "pass".equals(tf.getText());
 *         }
 *     }
 *
 *     public static void main(String[] args) {
 *         Frame f = new VerifierTest();
 *         f.pack();
 *         f.setVisible(true);
 *     }
 * }
 * </pre>
 *
 * <p>
 *  这个类的目的是帮助客户端通过GUI与文本字段的平滑聚焦导航。这样的GUI通常需要确保在允许用户导航出文本字段之前,由用户输入的文本是有效的(例如,其以正确的格式)。
 * 为此,客户端创建<code> InputVerifier </code>的子类,并使用<code> JComponent </code>的<code> setInputVerifier </code>方
 * 法,将其子类的实例附加到<code > JComponent </code>,它们的输入要验证。
 *  这个类的目的是帮助客户端通过GUI与文本字段的平滑聚焦导航。这样的GUI通常需要确保在允许用户导航出文本字段之前,由用户输入的文本是有效的(例如,其以正确的格式)。
 * 在将焦点转移到请求它的另一个Swing组件之前,输入验证器的<code> shouldYieldFocus </code>方法被调用。
 * 仅当该方法返回<code> true </code>时,才会传输焦点。
 * <p>
 *  以下示例具有两个文本字段,第一个期望字符串"pass"由用户输入。如果在第一个文本字段中输入了该字符串,则用户可以通过单击或按TAB键前进到第二个文本字段。
 * 但是,如果在第一个文本字段中输入了另一个字符串,则用户将无法将焦点转移到第二个文本字段。
 * 
 * <pre>
 *  import java.awt。*; import java.util。*; import java.awt.event。*; import javax.swing。*;
 * 
 * //这个程序演示了使用Swing InputVerifier类。 //它创建两个文本字段;第一个文本字段期望//字符串"pass"作为输入,并且将允许焦点提前出来//只有在用户输入该字符串之后。
 * 
 *  public class VerifierTest extends JFrame {public VerifierTest(){JTextField tf1 = new JTextField("Type \"pass \"here"); getContentPane()。
 * add(tf1,BorderLayout.NORTH); tf1.setInputVerifier(new PassVerifier());。
 * 
 *  JTextField tf2 = new JTextField("TextField2"); getContentPane()。add(tf2,BorderLayout.SOUTH);
 * 
 *  @since 1.3
 */


public abstract class InputVerifier {

  /**
   * Checks whether the JComponent's input is valid. This method should
   * have no side effects. It returns a boolean indicating the status
   * of the argument's input.
   *
   * <p>
   * 
   *  WindowListener l = new WindowAdapter(){public void windowClosing(WindowEvent e){System.exit(0); }}; 
   * addWindowListener(l); }}。
   * 
   *  类PassVerifier扩展InputVerifier {public boolean verify(JComponent input){JTextField tf =(JTextField)input; return"pass".equals(tf.getText()); }
   * }。
   * 
   *  public static void main(String [] args){Frame f = new VerifierTest(); f.pack(); f.setVisible(true); }
   * }。
   * 
   * @param input the JComponent to verify
   * @return <code>true</code> when valid, <code>false</code> when invalid
   * @see JComponent#setInputVerifier
   * @see JComponent#getInputVerifier
   *
   */

  public abstract boolean verify(JComponent input);


  /**
   * Calls <code>verify(input)</code> to ensure that the input is valid.
   * This method can have side effects. In particular, this method
   * is called when the user attempts to advance focus out of the
   * argument component into another Swing component in this window.
   * If this method returns <code>true</code>, then the focus is transfered
   * normally; if it returns <code>false</code>, then the focus remains in
   * the argument component.
   *
   * <p>
   * </pre>
   * 
   * 
   * @param input the JComponent to verify
   * @return <code>true</code> when valid, <code>false</code> when invalid
   * @see JComponent#setInputVerifier
   * @see JComponent#getInputVerifier
   *
   */

  public boolean shouldYieldFocus(JComponent input) {
    return verify(input);
  }

}
