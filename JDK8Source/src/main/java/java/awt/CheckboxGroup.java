/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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
package java.awt;

/**
 * The <code>CheckboxGroup</code> class is used to group together
 * a set of <code>Checkbox</code> buttons.
 * <p>
 * Exactly one check box button in a <code>CheckboxGroup</code> can
 * be in the "on" state at any given time. Pushing any
 * button sets its state to "on" and forces any other button that
 * is in the "on" state into the "off" state.
 * <p>
 * The following code example produces a new check box group,
 * with three check boxes:
 *
 * <hr><blockquote><pre>
 * setLayout(new GridLayout(3, 1));
 * CheckboxGroup cbg = new CheckboxGroup();
 * add(new Checkbox("one", cbg, true));
 * add(new Checkbox("two", cbg, false));
 * add(new Checkbox("three", cbg, false));
 * </pre></blockquote><hr>
 * <p>
 * This image depicts the check box group created by this example:
 * <p>
 * <img src="doc-files/CheckboxGroup-1.gif"
 * alt="Shows three checkboxes, arranged vertically, labeled one, two, and three. Checkbox one is in the on state."
 * style="float:center; margin: 7px 10px;">
 * <p>
 * <p>
 *  <code> CheckboxGroup </code>类用于将一组<code>复选框</code>按钮分组在一起。
 * <p>
 *  在任何给定时间,<code> CheckboxGroup </code>中的一个复选框按钮可以处于"打开"状态。按下任何按钮将其状态设置为"开",并且将处于"开"状态的任何其他按钮强制为"关"状态。
 * <p>
 *  以下代码示例生成一个新的复选框组,其中包含三个复选框：
 * 
 *  <hr> <blockquote> <pre> setLayout(new GridLayout(3,1)); CheckboxGroup cbg = new CheckboxGroup(); add
 * (new Checkbox("one",cbg,true)); add(new Checkbox("two",cbg,false)); add(new Checkbox("three",cbg,fals
 * e)); </pre> </blockquote> <hr>。
 * <p>
 *  此图像描述了此示例创建的复选框组：
 * <p>
 *  <img src ="doc-files / CheckboxGroup-1.gif"alt ="显示三个垂直排列的复选框,标记为一个,两个和三个,复选框一处于打开状态。
 * style="float:center; margin: 7px 10px;">
 * <p>
 * 
 * @author      Sami Shaio
 * @see         java.awt.Checkbox
 * @since       JDK1.0
 */
public class CheckboxGroup implements java.io.Serializable {
    /**
     * The current choice.
     * <p>
     *  当前选择。
     * 
     * 
     * @serial
     * @see #getCurrent()
     * @see #setCurrent(Checkbox)
     */
    Checkbox selectedCheckbox = null;

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
    private static final long serialVersionUID = 3729780091441768983L;

    /**
     * Creates a new instance of <code>CheckboxGroup</code>.
     * <p>
     *  创建<code> CheckboxGroup </code>的新实例。
     * 
     */
    public CheckboxGroup() {
    }

    /**
     * Gets the current choice from this check box group.
     * The current choice is the check box in this
     * group that is currently in the "on" state,
     * or <code>null</code> if all check boxes in the
     * group are off.
     * <p>
     *  从此复选框组获取当前选择。当前选择是此组中当前处于"打开"状态的复选框,或者如果组中的所有复选框都关闭,则选择<code> null </code>。
     * 
     * 
     * @return   the check box that is currently in the
     *                 "on" state, or <code>null</code>.
     * @see      java.awt.Checkbox
     * @see      java.awt.CheckboxGroup#setSelectedCheckbox
     * @since    JDK1.1
     */
    public Checkbox getSelectedCheckbox() {
        return getCurrent();
    }

    /**
    /* <p>
    /* 
     * @deprecated As of JDK version 1.1,
     * replaced by <code>getSelectedCheckbox()</code>.
     */
    @Deprecated
    public Checkbox getCurrent() {
        return selectedCheckbox;
    }

    /**
     * Sets the currently selected check box in this group
     * to be the specified check box.
     * This method sets the state of that check box to "on" and
     * sets all other check boxes in the group to be off.
     * <p>
     * If the check box argument is <tt>null</tt>, all check boxes
     * in this check box group are deselected. If the check box argument
     * belongs to a different check box group, this method does
     * nothing.
     * <p>
     * 将此组中当前选中的复选框设置为指定的复选框。此方法将该复选框的状态设置为"on",并将组中的所有其他复选框设置为关闭。
     * <p>
     *  如果复选框参数为<tt> null </tt>,则取消选中此复选框组中的所有复选框。如果复选框参数属于不同的复选框组,则此方法不执行任何操作。
     * 
     * 
     * @param     box   the <code>Checkbox</code> to set as the
     *                      current selection.
     * @see      java.awt.Checkbox
     * @see      java.awt.CheckboxGroup#getSelectedCheckbox
     * @since    JDK1.1
     */
    public void setSelectedCheckbox(Checkbox box) {
        setCurrent(box);
    }

    /**
    /* <p>
    /* 
     * @deprecated As of JDK version 1.1,
     * replaced by <code>setSelectedCheckbox(Checkbox)</code>.
     */
    @Deprecated
    public synchronized void setCurrent(Checkbox box) {
        if (box != null && box.group != this) {
            return;
        }
        Checkbox oldChoice = this.selectedCheckbox;
        this.selectedCheckbox = box;
        if (oldChoice != null && oldChoice != box && oldChoice.group == this) {
            oldChoice.setState(false);
        }
        if (box != null && oldChoice != box && !box.getState()) {
            box.setStateInternal(true);
        }
    }

    /**
     * Returns a string representation of this check box group,
     * including the value of its current selection.
     * <p>
     * 
     * @return    a string representation of this check box group.
     */
    public String toString() {
        return getClass().getName() + "[selectedCheckbox=" + selectedCheckbox + "]";
    }

}
