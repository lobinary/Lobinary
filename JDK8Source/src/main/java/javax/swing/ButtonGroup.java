/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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
import java.util.Vector;
import java.util.Enumeration;
import java.io.Serializable;

/**
 * This class is used to create a multiple-exclusion scope for
 * a set of buttons. Creating a set of buttons with the
 * same <code>ButtonGroup</code> object means that
 * turning "on" one of those buttons
 * turns off all other buttons in the group.
 * <p>
 * A <code>ButtonGroup</code> can be used with
 * any set of objects that inherit from <code>AbstractButton</code>.
 * Typically a button group contains instances of
 * <code>JRadioButton</code>,
 * <code>JRadioButtonMenuItem</code>,
 * or <code>JToggleButton</code>.
 * It wouldn't make sense to put an instance of
 * <code>JButton</code> or <code>JMenuItem</code>
 * in a button group
 * because <code>JButton</code> and <code>JMenuItem</code>
 * don't implement the selected state.
 * <p>
 * Initially, all buttons in the group are unselected.
 * <p>
 * For examples and further information on using button groups see
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/button.html#radiobutton">How to Use Radio Buttons</a>,
 * a section in <em>The Java Tutorial</em>.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans&trade;
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * <p>
 *  此类用于为一组按钮创建多重排除范围。创建一组具有相同<code> ButtonGroup </code>对象的按钮意味着打开其中一个按钮将关闭组中的所有其他按钮。
 * <p>
 *  <code> ButtonGroup </code>可以用于继承自<code> AbstractButton </code>的任何对象集合。
 * 通常,按钮组包含<code> JRadioButton </code>,<code> JRadioButtonMenuItem </code>或<code> JToggleButton </code>的
 * 实例。
 *  <code> ButtonGroup </code>可以用于继承自<code> AbstractButton </code>的任何对象集合。
 * 在按钮组中放置<code> JButton </code>或<code> JMenuItem </code>的实例没有意义,因为<code> JButton </code>和<code> JMenuIt
 * em </code>不实现所选状态。
 *  <code> ButtonGroup </code>可以用于继承自<code> AbstractButton </code>的任何对象集合。
 * <p>
 *  最初,组中的所有按钮都未选中。
 * <p>
 *  有关使用按钮组的示例和详细信息,请参阅<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/button.html#radiobutton">
 * 如何使用单选按钮</a>, Java教程</em>中的一个部分。
 * <p>
 * <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author Jeff Dinkins
 */
@SuppressWarnings("serial")
public class ButtonGroup implements Serializable {

    // the list of buttons participating in this group
    protected Vector<AbstractButton> buttons = new Vector<AbstractButton>();

    /**
     * The current selection.
     * <p>
     *  当前选择。
     * 
     */
    ButtonModel selection = null;

    /**
     * Creates a new <code>ButtonGroup</code>.
     * <p>
     *  创建一个新的<code> ButtonGroup </code>。
     * 
     */
    public ButtonGroup() {}

    /**
     * Adds the button to the group.
     * <p>
     *  将按钮添加到组。
     * 
     * 
     * @param b the button to be added
     */
    public void add(AbstractButton b) {
        if(b == null) {
            return;
        }
        buttons.addElement(b);

        if (b.isSelected()) {
            if (selection == null) {
                selection = b.getModel();
            } else {
                b.setSelected(false);
            }
        }

        b.getModel().setGroup(this);
    }

    /**
     * Removes the button from the group.
     * <p>
     *  从组中删除按钮。
     * 
     * 
     * @param b the button to be removed
     */
    public void remove(AbstractButton b) {
        if(b == null) {
            return;
        }
        buttons.removeElement(b);
        if(b.getModel() == selection) {
            selection = null;
        }
        b.getModel().setGroup(null);
    }

    /**
     * Clears the selection such that none of the buttons
     * in the <code>ButtonGroup</code> are selected.
     *
     * <p>
     *  清除选择,使得<code> ButtonGroup </code>中的所有按钮都不会被选中。
     * 
     * 
     * @since 1.6
     */
    public void clearSelection() {
        if (selection != null) {
            ButtonModel oldSelection = selection;
            selection = null;
            oldSelection.setSelected(false);
        }
    }

    /**
     * Returns all the buttons that are participating in
     * this group.
     * <p>
     *  返回参与此组的所有按钮。
     * 
     * 
     * @return an <code>Enumeration</code> of the buttons in this group
     */
    public Enumeration<AbstractButton> getElements() {
        return buttons.elements();
    }

    /**
     * Returns the model of the selected button.
     * <p>
     *  返回所选按钮的模型。
     * 
     * 
     * @return the selected button model
     */
    public ButtonModel getSelection() {
        return selection;
    }

    /**
     * Sets the selected value for the <code>ButtonModel</code>.
     * Only one button in the group may be selected at a time.
     * <p>
     *  为<code> ButtonModel </code>设置所选的值。一次只能选择组中的一个按钮。
     * 
     * 
     * @param m the <code>ButtonModel</code>
     * @param b <code>true</code> if this button is to be
     *   selected, otherwise <code>false</code>
     */
    public void setSelected(ButtonModel m, boolean b) {
        if (b && m != null && m != selection) {
            ButtonModel oldSelection = selection;
            selection = m;
            if (oldSelection != null) {
                oldSelection.setSelected(false);
            }
            m.setSelected(true);
        }
    }

    /**
     * Returns whether a <code>ButtonModel</code> is selected.
     * <p>
     *  返回是否选择了<code> ButtonModel </code>。
     * 
     * 
     * @return <code>true</code> if the button is selected,
     *   otherwise returns <code>false</code>
     */
    public boolean isSelected(ButtonModel m) {
        return (m == selection);
    }

    /**
     * Returns the number of buttons in the group.
     * <p>
     *  返回组中的按钮数。
     * 
     * @return the button count
     * @since 1.3
     */
    public int getButtonCount() {
        if (buttons == null) {
            return 0;
        } else {
            return buttons.size();
        }
    }

}
