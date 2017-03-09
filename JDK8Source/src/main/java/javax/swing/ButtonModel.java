/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2006, Oracle and/or its affiliates. All rights reserved.
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


import java.awt.event.*;
import java.awt.*;
import javax.swing.event.*;

/**
 * State model for buttons.
 * <p>
 * This model is used for regular buttons, as well as check boxes
 * and radio buttons, which are special kinds of buttons. In practice,
 * a button's UI takes the responsibility of calling methods on its
 * model to manage the state, as detailed below:
 * <p>
 * In simple terms, pressing and releasing the mouse over a regular
 * button triggers the button and causes and <code>ActionEvent</code>
 * to be fired. The same behavior can be produced via a keyboard key
 * defined by the look and feel of the button (typically the SPACE BAR).
 * Pressing and releasing this key while the button has
 * focus will give the same results. For check boxes and radio buttons, the
 * mouse or keyboard equivalent sequence just described causes the button
 * to become selected.
 * <p>
 * In details, the state model for buttons works as follows
 * when used with the mouse:
 * <br>
 * Pressing the mouse on top of a button makes the model both
 * armed and pressed. As long as the mouse remains down,
 * the model remains pressed, even if the mouse moves
 * outside the button. On the contrary, the model is only
 * armed while the mouse remains pressed within the bounds of
 * the button (it can move in or out of the button, but the model
 * is only armed during the portion of time spent within the button).
 * A button is triggered, and an <code>ActionEvent</code> is fired,
 * when the mouse is released while the model is armed
 * - meaning when it is released over top of the button after the mouse
 * has previously been pressed on that button (and not already released).
 * Upon mouse release, the model becomes unarmed and unpressed.
 * <p>
 * In details, the state model for buttons works as follows
 * when used with the keyboard:
 * <br>
 * Pressing the look and feel defined keyboard key while the button
 * has focus makes the model both armed and pressed. As long as this key
 * remains down, the model remains in this state. Releasing the key sets
 * the model to unarmed and unpressed, triggers the button, and causes an
 * <code>ActionEvent</code> to be fired.
 *
 * <p>
 *  按钮的状态模型。
 * <p>
 *  此模型用于常规按钮,以及复选框和单选按钮,这是特殊类型的按钮。实际上,按钮的UI负责调用其模型上的方法来管理状态,如下所述：
 * <p>
 *  简单来说,在常规按钮上按下和释放鼠标会触发按钮,并导致<code> ActionEvent </code>被触发。相同的行为可以通过由按钮(通常为空间条)的外观和感觉限定的键盘键产生。
 * 当按钮具有焦点时按下和释放此键将给出相同的结果。对于复选框和单选按钮,刚刚描述的鼠标或键盘等效序列使得按钮被选择。
 * <p>
 *  详细地说,当与鼠标一起使用时,按钮的状态模型如下工作：
 * <br>
 * 将鼠标按在按钮的顶部使模型被布防和按下。只要鼠标保持按下,即使鼠标移动到按钮之外,模型仍然保持按下状态。
 * 相反,模型仅在鼠标保持按压在按钮的边界内时被布防(它可以移入或移出按钮,但是模型仅在按钮内花费的时间部分期间被布防)。
 * 触发按钮,并且当模型被布防时释放鼠标时触发<code> ActionEvent </code>(意味着在鼠标先前已按下该按钮之后释放到按钮顶部)并且尚未发布)。一旦鼠标释放,模型变得没有武器和未压缩。
 * <p>
 *  详细地,当与键盘一起使用时,按钮的状态模型如下工作：
 * <br>
 *  当按钮具有焦点时按下外观和感觉定义的键盘琴键,使得模型被布防和按下。只要此键保持关闭,模型将保持此状态。
 * 释放键将模型设置为非武装和未压缩,触发按钮,并导致<code> ActionEvent </code>被触发。
 * 
 * 
 * @author Jeff Dinkins
 */
public interface ButtonModel extends ItemSelectable {

    /**
     * Indicates partial commitment towards triggering the
     * button.
     *
     * <p>
     *  表示部分承诺触发按钮。
     * 
     * 
     * @return <code>true</code> if the button is armed,
     *         and ready to be triggered
     * @see #setArmed
     */
    boolean isArmed();

    /**
     * Indicates if the button has been selected. Only needed for
     * certain types of buttons - such as radio buttons and check boxes.
     *
     * <p>
     *  指示是否已选择按钮。只需要某些类型的按钮 - 例如单选按钮和复选框。
     * 
     * 
     * @return <code>true</code> if the button is selected
     */
    boolean isSelected();

    /**
     * Indicates if the button can be selected or triggered by
     * an input device, such as a mouse pointer.
     *
     * <p>
     *  指示是否可以通过输入设备(例如鼠标指针)选择或触发按钮。
     * 
     * 
     * @return <code>true</code> if the button is enabled
     */
    boolean isEnabled();

    /**
     * Indicates if the button is pressed.
     *
     * <p>
     *  指示是否按下按钮。
     * 
     * 
     * @return <code>true</code> if the button is pressed
     */
    boolean isPressed();

    /**
     * Indicates that the mouse is over the button.
     *
     * <p>
     *  表示鼠标在按钮上方。
     * 
     * 
     * @return <code>true</code> if the mouse is over the button
     */
    boolean isRollover();

    /**
     * Marks the button as armed or unarmed.
     *
     * <p>
     * 将按钮标记为已启用或未启用。
     * 
     * 
     * @param b whether or not the button should be armed
     */
    public void setArmed(boolean b);

    /**
     * Selects or deselects the button.
     *
     * <p>
     *  选择或取消选择按钮。
     * 
     * 
     * @param b <code>true</code> selects the button,
     *          <code>false</code> deselects the button
     */
    public void setSelected(boolean b);

    /**
     * Enables or disables the button.
     *
     * <p>
     *  启用或禁用按钮。
     * 
     * 
     * @param b whether or not the button should be enabled
     * @see #isEnabled
     */
    public void setEnabled(boolean b);

    /**
     * Sets the button to pressed or unpressed.
     *
     * <p>
     *  将按钮设置为按下或未按下。
     * 
     * 
     * @param b whether or not the button should be pressed
     * @see #isPressed
     */
    public void setPressed(boolean b);

    /**
     * Sets or clears the button's rollover state
     *
     * <p>
     *  设置或清除按钮的翻转状态
     * 
     * 
     * @param b whether or not the button is in the rollover state
     * @see #isRollover
     */
    public void setRollover(boolean b);

    /**
     * Sets the keyboard mnemonic (shortcut key or
     * accelerator key) for the button.
     *
     * <p>
     *  设置按钮的键盘助记符(快捷键或加速键)。
     * 
     * 
     * @param key an int specifying the accelerator key
     */
    public void setMnemonic(int key);

    /**
     * Gets the keyboard mnemonic for the button.
     *
     * <p>
     *  获取按钮的键盘助记符。
     * 
     * 
     * @return an int specifying the accelerator key
     * @see #setMnemonic
     */
    public int  getMnemonic();

    /**
     * Sets the action command string that gets sent as part of the
     * <code>ActionEvent</code> when the button is triggered.
     *
     * <p>
     *  设置在触发按钮时作为<code> ActionEvent </code>的一部分发送的操作命令字符串。
     * 
     * 
     * @param s the <code>String</code> that identifies the generated event
     * @see #getActionCommand
     * @see java.awt.event.ActionEvent#getActionCommand
     */
    public void setActionCommand(String s);

    /**
     * Returns the action command string for the button.
     *
     * <p>
     *  返回按钮的操作命令字符串。
     * 
     * 
     * @return the <code>String</code> that identifies the generated event
     * @see #setActionCommand
     */
    public String getActionCommand();

    /**
     * Identifies the group the button belongs to --
     * needed for radio buttons, which are mutually
     * exclusive within their group.
     *
     * <p>
     *  标识按钮所属的组 - 单选按钮所需的组,它们在组内是互斥的。
     * 
     * 
     * @param group the <code>ButtonGroup</code> the button belongs to
     */
    public void setGroup(ButtonGroup group);

    /**
     * Adds an <code>ActionListener</code> to the model.
     *
     * <p>
     *  向模型中添加<code> ActionListener </code>。
     * 
     * 
     * @param l the listener to add
     */
    void addActionListener(ActionListener l);

    /**
     * Removes an <code>ActionListener</code> from the model.
     *
     * <p>
     *  从模型中删除<code> ActionListener </code>。
     * 
     * 
     * @param l the listener to remove
     */
    void removeActionListener(ActionListener l);

    /**
     * Adds an <code>ItemListener</code> to the model.
     *
     * <p>
     *  向模型中添加<code> ItemListener </code>。
     * 
     * 
     * @param l the listener to add
     */
    void addItemListener(ItemListener l);

    /**
     * Removes an <code>ItemListener</code> from the model.
     *
     * <p>
     *  从模型中删除<code> ItemListener </code>。
     * 
     * 
     * @param l the listener to remove
     */
    void removeItemListener(ItemListener l);

    /**
     * Adds a <code>ChangeListener</code> to the model.
     *
     * <p>
     *  向模型中添加<code> ChangeListener </code>。
     * 
     * 
     * @param l the listener to add
     */
    void addChangeListener(ChangeListener l);

    /**
     * Removes a <code>ChangeListener</code> from the model.
     *
     * <p>
     *  从模型中删除<code> ChangeListener </code>。
     * 
     * @param l the listener to remove
     */
    void removeChangeListener(ChangeListener l);

}
