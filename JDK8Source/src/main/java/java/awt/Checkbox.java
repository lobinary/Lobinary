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

import java.awt.peer.CheckboxPeer;
import java.awt.event.*;
import java.util.EventListener;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import javax.accessibility.*;


/**
 * A check box is a graphical component that can be in either an
 * "on" (<code>true</code>) or "off" (<code>false</code>) state.
 * Clicking on a check box changes its state from
 * "on" to "off," or from "off" to "on."
 * <p>
 * The following code example creates a set of check boxes in
 * a grid layout:
 *
 * <hr><blockquote><pre>
 * setLayout(new GridLayout(3, 1));
 * add(new Checkbox("one", null, true));
 * add(new Checkbox("two"));
 * add(new Checkbox("three"));
 * </pre></blockquote><hr>
 * <p>
 * This image depicts the check boxes and grid layout
 * created by this code example:
 * <p>
 * <img src="doc-files/Checkbox-1.gif" alt="The following context describes the graphic."
 * style="float:center; margin: 7px 10px;">
 * <p>
 * The button labeled <code>one</code> is in the "on" state, and the
 * other two are in the "off" state. In this example, which uses the
 * <code>GridLayout</code> class, the states of the three check
 * boxes are set independently.
 * <p>
 * Alternatively, several check boxes can be grouped together under
 * the control of a single object, using the
 * <code>CheckboxGroup</code> class.
 * In a check box group, at most one button can be in the "on"
 * state at any given time. Clicking on a check box to turn it on
 * forces any other check box in the same group that is on
 * into the "off" state.
 *
 * <p>
 *  复选框是可以处于"开"(<code> true </code>)或"关闭"(<code> false </code>)状态的图形组件。
 * 单击复选框会将其状态从"on"更改为"off",或从"off"更改为"on"。
 * <p>
 *  以下代码示例在网格布局中创建一组复选框：
 * 
 *  <hr> <blockquote> <pre> setLayout(new GridLayout(3,1)); add(new Checkbox("one",null,true)); add(new 
 * Checkbox("two")); add(new Checkbox("three")); </pre> </blockquote> <hr>。
 * <p>
 *  此图像描述了此代码示例创建的复选框和网格布局：
 * <p>
 *  <img src ="doc-files / Checkbox-1.gif"alt ="以下上下文描述图形。
 * style="float:center; margin: 7px 10px;">
 * <p>
 *  标记为<code> one </code>的按钮处于"开启"状态,其他两个处于"关闭"状态。在这个使用<code> GridLayout </code>类的示例中,三个复选框的状态是独立设置的。
 * <p>
 *  或者,可以使用<code> CheckboxGroup </code>类在一个对象的控制下将多个复选框组合在一起。在复选框组中,在任何给定时间,至多一个按钮可以处于"开启"状态。
 * 单击复选框以将其打开将强制同一组中处于"关闭"状态的任何其他复选框。
 * 
 * 
 * @author      Sami Shaio
 * @see         java.awt.GridLayout
 * @see         java.awt.CheckboxGroup
 * @since       JDK1.0
 */
public class Checkbox extends Component implements ItemSelectable, Accessible {

    static {
        /* ensure that the necessary native libraries are loaded */
        Toolkit.loadLibraries();
        if (!GraphicsEnvironment.isHeadless()) {
            initIDs();
        }
    }

    /**
     * The label of the Checkbox.
     * This field can be null.
     * <p>
     *  复选框的标签。此字段可以为null。
     * 
     * 
     * @serial
     * @see #getLabel()
     * @see #setLabel(String)
     */
    String label;

    /**
     * The state of the <code>Checkbox</code>.
     * <p>
     *  <code>复选框</code>的状态。
     * 
     * 
     * @serial
     * @see #getState()
     * @see #setState(boolean)
     */
    boolean state;

    /**
     * The check box group.
         * This field can be null indicating that the checkbox
         * is not a group checkbox.
         * <p>
         * 复选框组。此字段可以为空,表示复选框不是组复选框。
         * 
         * 
         * @serial
     * @see #getCheckboxGroup()
     * @see #setCheckboxGroup(CheckboxGroup)
     */
    CheckboxGroup group;

    transient ItemListener itemListener;

    private static final String base = "checkbox";
    private static int nameCounter = 0;

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
    private static final long serialVersionUID = 7270714317450821763L;

    /**
     * Helper function for setState and CheckboxGroup.setSelectedCheckbox
     * Should remain package-private.
     * <p>
     *  setState和CheckboxGroup.setSelectedCheckbox的辅助函数应该保持package-private。
     * 
     */
    void setStateInternal(boolean state) {
        this.state = state;
        CheckboxPeer peer = (CheckboxPeer)this.peer;
        if (peer != null) {
            peer.setState(state);
        }
    }

    /**
     * Creates a check box with an empty string for its label.
     * The state of this check box is set to "off," and it is not
     * part of any check box group.
     * <p>
     *  创建一个带有其标签的空字符串的复选框。此复选框的状态设置为"关闭",并且不是任何复选框组的一部分。
     * 
     * 
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public Checkbox() throws HeadlessException {
        this("", false, null);
    }

    /**
     * Creates a check box with the specified label.  The state
     * of this check box is set to "off," and it is not part of
     * any check box group.
     *
     * <p>
     *  创建具有指定标签的复选框。此复选框的状态设置为"关闭",并且不是任何复选框组的一部分。
     * 
     * 
     * @param     label   a string label for this check box,
     *                        or <code>null</code> for no label.
     * @exception HeadlessException if
     *      <code>GraphicsEnvironment.isHeadless</code>
     *      returns <code>true</code>
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public Checkbox(String label) throws HeadlessException {
        this(label, false, null);
    }

    /**
     * Creates a check box with the specified label
     * and sets the specified state.
     * This check box is not part of any check box group.
     *
     * <p>
     *  创建具有指定标签的复选框,并设置指定的状态。此复选框不是任何复选框组的一部分。
     * 
     * 
     * @param     label   a string label for this check box,
     *                        or <code>null</code> for no label
     * @param     state    the initial state of this check box
     * @exception HeadlessException if
     *     <code>GraphicsEnvironment.isHeadless</code>
     *     returns <code>true</code>
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public Checkbox(String label, boolean state) throws HeadlessException {
        this(label, state, null);
    }

    /**
     * Constructs a Checkbox with the specified label, set to the
     * specified state, and in the specified check box group.
     *
     * <p>
     *  构造具有指定标签,设置为指定状态和指定复选框组的复选框。
     * 
     * 
     * @param     label   a string label for this check box,
     *                        or <code>null</code> for no label.
     * @param     state   the initial state of this check box.
     * @param     group   a check box group for this check box,
     *                           or <code>null</code> for no group.
     * @exception HeadlessException if
     *     <code>GraphicsEnvironment.isHeadless</code>
     *     returns <code>true</code>
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @since     JDK1.1
     */
    public Checkbox(String label, boolean state, CheckboxGroup group)
        throws HeadlessException {
        GraphicsEnvironment.checkHeadless();
        this.label = label;
        this.state = state;
        this.group = group;
        if (state && (group != null)) {
            group.setSelectedCheckbox(this);
        }
    }

    /**
     * Creates a check box with the specified label, in the specified
     * check box group, and set to the specified state.
     *
     * <p>
     *  在指定的复选框组中创建具有指定标签的复选框,并设置为指定的状态。
     * 
     * 
     * @param     label   a string label for this check box,
     *                        or <code>null</code> for no label.
     * @param     group   a check box group for this check box,
     *                           or <code>null</code> for no group.
     * @param     state   the initial state of this check box.
     * @exception HeadlessException if
     *    <code>GraphicsEnvironment.isHeadless</code>
     *    returns <code>true</code>
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @since     JDK1.1
     */
    public Checkbox(String label, CheckboxGroup group, boolean state)
        throws HeadlessException {
        this(label, state, group);
    }

    /**
     * Constructs a name for this component.  Called by
     * <code>getName</code> when the name is <code>null</code>.
     *
     * <p>
     *  构造此组件的名称。当名称为<code> null </code>时,由<code> getName </code>调用。
     * 
     * 
     * @return a name for this component
     */
    String constructComponentName() {
        synchronized (Checkbox.class) {
            return base + nameCounter++;
        }
    }

    /**
     * Creates the peer of the Checkbox. The peer allows you to change the
     * look of the Checkbox without changing its functionality.
     *
     * <p>
     *  创建复选框的对等项。对等体允许您更改复选框的外观,而不更改其功能。
     * 
     * 
     * @see     java.awt.Toolkit#createCheckbox(java.awt.Checkbox)
     * @see     java.awt.Component#getToolkit()
     */
    public void addNotify() {
        synchronized (getTreeLock()) {
            if (peer == null)
                peer = getToolkit().createCheckbox(this);
            super.addNotify();
        }
    }

    /**
     * Gets the label of this check box.
     *
     * <p>
     *  获取此复选框的标签。
     * 
     * 
     * @return   the label of this check box, or <code>null</code>
     *                  if this check box has no label.
     * @see      #setLabel(String)
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets this check box's label to be the string argument.
     *
     * <p>
     *  将此复选框的标签设置为字符串参数。
     * 
     * 
     * @param    label   a string to set as the new label, or
     *                        <code>null</code> for no label.
     * @see      #getLabel
     */
    public void setLabel(String label) {
        boolean testvalid = false;

        synchronized (this) {
            if (label != this.label && (this.label == null ||
                                        !this.label.equals(label))) {
                this.label = label;
                CheckboxPeer peer = (CheckboxPeer)this.peer;
                if (peer != null) {
                    peer.setLabel(label);
                }
                testvalid = true;
            }
        }

        // This could change the preferred size of the Component.
        if (testvalid) {
            invalidateIfValid();
        }
    }

    /**
     * Determines whether this check box is in the "on" or "off" state.
     * The boolean value <code>true</code> indicates the "on" state,
     * and <code>false</code> indicates the "off" state.
     *
     * <p>
     *  确定此复选框是处于"打开"还是"关闭"状态。布尔值<code> true </code>表示"on"状态,<code> false </code>表示"off"状态。
     * 
     * 
     * @return    the state of this check box, as a boolean value
     * @see       #setState
     */
    public boolean getState() {
        return state;
    }

    /**
     * Sets the state of this check box to the specified state.
     * The boolean value <code>true</code> indicates the "on" state,
     * and <code>false</code> indicates the "off" state.
     *
     * <p>Note that this method should be primarily used to
     * initialize the state of the checkbox.  Programmatically
     * setting the state of the checkbox will <i>not</i> trigger
     * an <code>ItemEvent</code>.  The only way to trigger an
     * <code>ItemEvent</code> is by user interaction.
     *
     * <p>
     * 将此复选框的状态设置为指定的状态。布尔值<code> true </code>表示"on"状态,<code> false </code>表示"off"状态。
     * 
     *  <p>请注意,此方法应主要用于初始化复选框的状态。以编程方式设置复选框的状态<i>不会</i>触发<code> ItemEvent </code>。
     * 触发<code> ItemEvent </code>的唯一方法是通过用户交互。
     * 
     * 
     * @param     state   the boolean state of the check box
     * @see       #getState
     */
    public void setState(boolean state) {
        /* Cannot hold check box lock when calling group.setSelectedCheckbox. */
        CheckboxGroup group = this.group;
        if (group != null) {
            if (state) {
                group.setSelectedCheckbox(this);
            } else if (group.getSelectedCheckbox() == this) {
                state = true;
            }
        }
        setStateInternal(state);
    }

    /**
     * Returns an array (length 1) containing the checkbox
     * label or null if the checkbox is not selected.
     * <p>
     *  返回包含复选框标签的数组(长度1),如果未选中复选框,则返回null。
     * 
     * 
     * @see ItemSelectable
     */
    public Object[] getSelectedObjects() {
        if (state) {
            Object[] items = new Object[1];
            items[0] = label;
            return items;
        }
        return null;
    }

    /**
     * Determines this check box's group.
     * <p>
     *  确定此复选框的组。
     * 
     * 
     * @return     this check box's group, or <code>null</code>
     *               if the check box is not part of a check box group.
     * @see        #setCheckboxGroup(CheckboxGroup)
     */
    public CheckboxGroup getCheckboxGroup() {
        return group;
    }

    /**
     * Sets this check box's group to the specified check box group.
     * If this check box is already in a different check box group,
     * it is first taken out of that group.
     * <p>
     * If the state of this check box is <code>true</code> and the new
     * group already has a check box selected, this check box's state
     * is changed to <code>false</code>.  If the state of this check
     * box is <code>true</code> and the new group has no check box
     * selected, this check box becomes the selected checkbox for
     * the new group and its state is <code>true</code>.
     *
     * <p>
     *  将此复选框的组设置为指定的复选框组。如果此复选框已在不同的复选框组中,则首先从该组中取出。
     * <p>
     *  如果此复选框的状态为<code> true </code>,并且新组已选中复选框,则此复选框的状态将更改为<code> false </code>。
     * 如果此复选框的状态为<code> true </code>,并且新组未选中复选框,则此复选框将成为新组的所选复选框,其状态为<code> true </code>。
     * 
     * 
     * @param     g   the new check box group, or <code>null</code>
     *                to remove this check box from any check box group
     * @see       #getCheckboxGroup
     */
    public void setCheckboxGroup(CheckboxGroup g) {
        CheckboxGroup oldGroup;
        boolean oldState;

        /* Do nothing if this check box has already belonged
         * to the check box group g.
         * <p>
         *  到复选框组g。
         * 
         */
        if (this.group == g) {
            return;
        }

        synchronized (this) {
            oldGroup = this.group;
            oldState = getState();

            this.group = g;
            CheckboxPeer peer = (CheckboxPeer)this.peer;
            if (peer != null) {
                peer.setCheckboxGroup(g);
            }
            if (this.group != null && getState()) {
                if (this.group.getSelectedCheckbox() != null) {
                    setState(false);
                } else {
                    this.group.setSelectedCheckbox(this);
                }
            }
        }

        /* Locking check box below could cause deadlock with
         * CheckboxGroup's setSelectedCheckbox method.
         *
         * Fix for 4726853 by kdm@sparc.spb.su
         * Here we should check if this check box was selected
         * in the previous group and set selected check box to
         * null for that group if so.
         * <p>
         *  CheckboxGroup的setSelectedCheckbox方法。
         * 
         *  修复为4726853由kdm@sparc.spb.su在这里,我们应该检查是否选中此复选框在上一组,并将所选复选框为空组的如果是。
         * 
         */
        if (oldGroup != null && oldState) {
            oldGroup.setSelectedCheckbox(null);
        }
    }

    /**
     * Adds the specified item listener to receive item events from
     * this check box.  Item events are sent to listeners in response
     * to user input, but not in response to calls to setState().
     * If l is null, no exception is thrown and no action is performed.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * <p>
     * 添加指定的项目侦听器以从此复选框接收项目事件。项事件发送到侦听器以响应用户输入,但不响应对setState()的调用。如果l为null,则不抛出异常,并且不执行任何操作。
     *  <p>有关AWT的线程模型的详细信息,请参阅<a href="doc-files/AWTThreadIssues.html#ListenersThreads"> AWT线程问题</a>。
     * 
     * 
     * @param         l    the item listener
     * @see           #removeItemListener
     * @see           #getItemListeners
     * @see           #setState
     * @see           java.awt.event.ItemEvent
     * @see           java.awt.event.ItemListener
     * @since         JDK1.1
     */
    public synchronized void addItemListener(ItemListener l) {
        if (l == null) {
            return;
        }
        itemListener = AWTEventMulticaster.add(itemListener, l);
        newEventsOnly = true;
    }

    /**
     * Removes the specified item listener so that the item listener
     * no longer receives item events from this check box.
     * If l is null, no exception is thrown and no action is performed.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * <p>
     *  删除指定的项目侦听器,以便项目侦听器不再从此复选框接收项目事件。如果l为null,则不抛出异常,并且不执行任何操作。
     *  <p>有关AWT的线程模型的详细信息,请参阅<a href="doc-files/AWTThreadIssues.html#ListenersThreads"> AWT线程问题</a>。
     * 
     * 
     * @param         l    the item listener
     * @see           #addItemListener
     * @see           #getItemListeners
     * @see           java.awt.event.ItemEvent
     * @see           java.awt.event.ItemListener
     * @since         JDK1.1
     */
    public synchronized void removeItemListener(ItemListener l) {
        if (l == null) {
            return;
        }
        itemListener = AWTEventMulticaster.remove(itemListener, l);
    }

    /**
     * Returns an array of all the item listeners
     * registered on this checkbox.
     *
     * <p>
     *  返回在此复选框上注册的所有项目侦听器的数组。
     * 
     * 
     * @return all of this checkbox's <code>ItemListener</code>s
     *         or an empty array if no item
     *         listeners are currently registered
     *
     * @see           #addItemListener
     * @see           #removeItemListener
     * @see           java.awt.event.ItemEvent
     * @see           java.awt.event.ItemListener
     * @since 1.4
     */
    public synchronized ItemListener[] getItemListeners() {
        return getListeners(ItemListener.class);
    }

    /**
     * Returns an array of all the objects currently registered
     * as <code><em>Foo</em>Listener</code>s
     * upon this <code>Checkbox</code>.
     * <code><em>Foo</em>Listener</code>s are registered using the
     * <code>add<em>Foo</em>Listener</code> method.
     *
     * <p>
     * You can specify the <code>listenerType</code> argument
     * with a class literal, such as
     * <code><em>Foo</em>Listener.class</code>.
     * For example, you can query a
     * <code>Checkbox</code> <code>c</code>
     * for its item listeners with the following code:
     *
     * <pre>ItemListener[] ils = (ItemListener[])(c.getListeners(ItemListener.class));</pre>
     *
     * If no such listeners exist, this method returns an empty array.
     *
     * <p>
     *  返回当前在此<code>复选框</code>上注册为<code> <em> Foo </em>侦听器</code>的所有对象的数组。
     * 使用<code> add <em> </em>侦听器</code>方法注册<code> <em> </em>侦听器</code>。
     * 
     * <p>
     *  您可以使用类文字指定<code> listenerType </code>参数,例如<code> <em> Foo </em> Listener.class </code>。
     * 例如,您可以使用以下代码查询其项目侦听器的<code>复选框</code> <code> c </code>：。
     * 
     *  <pre> ItemListener [] ils =(ItemListener [])(c.getListeners(ItemListener.class)); </pre>
     * 
     *  如果不存在此类侦听器,则此方法将返回一个空数组。
     * 
     * 
     * @param listenerType the type of listeners requested; this parameter
     *          should specify an interface that descends from
     *          <code>java.util.EventListener</code>
     * @return an array of all objects registered as
     *          <code><em>Foo</em>Listener</code>s on this checkbox,
     *          or an empty array if no such
     *          listeners have been added
     * @exception ClassCastException if <code>listenerType</code>
     *          doesn't specify a class or interface that implements
     *          <code>java.util.EventListener</code>
     *
     * @see #getItemListeners
     * @since 1.3
     */
    public <T extends EventListener> T[] getListeners(Class<T> listenerType) {
        EventListener l = null;
        if  (listenerType == ItemListener.class) {
            l = itemListener;
        } else {
            return super.getListeners(listenerType);
        }
        return AWTEventMulticaster.getListeners(l, listenerType);
    }

    // REMIND: remove when filtering is done at lower level
    boolean eventEnabled(AWTEvent e) {
        if (e.id == ItemEvent.ITEM_STATE_CHANGED) {
            if ((eventMask & AWTEvent.ITEM_EVENT_MASK) != 0 ||
                itemListener != null) {
                return true;
            }
            return false;
        }
        return super.eventEnabled(e);
    }

    /**
     * Processes events on this check box.
     * If the event is an instance of <code>ItemEvent</code>,
     * this method invokes the <code>processItemEvent</code> method.
     * Otherwise, it calls its superclass's <code>processEvent</code> method.
     * <p>Note that if the event parameter is <code>null</code>
     * the behavior is unspecified and may result in an
     * exception.
     *
     * <p>
     * 在此复选框处理事件。如果事件是<code> ItemEvent </code>的实例,则此方法调用<code> processItemEvent </code>方法。
     * 否则,它调用其超类的<code> processEvent </code>方法。 <p>请注意,如果事件参数为<code> null </code>,则此行为未指定,并可能导致异常。
     * 
     * 
     * @param         e the event
     * @see           java.awt.event.ItemEvent
     * @see           #processItemEvent
     * @since         JDK1.1
     */
    protected void processEvent(AWTEvent e) {
        if (e instanceof ItemEvent) {
            processItemEvent((ItemEvent)e);
            return;
        }
        super.processEvent(e);
    }

    /**
     * Processes item events occurring on this check box by
     * dispatching them to any registered
     * <code>ItemListener</code> objects.
     * <p>
     * This method is not called unless item events are
     * enabled for this component. Item events are enabled
     * when one of the following occurs:
     * <ul>
     * <li>An <code>ItemListener</code> object is registered
     * via <code>addItemListener</code>.
     * <li>Item events are enabled via <code>enableEvents</code>.
     * </ul>
     * <p>Note that if the event parameter is <code>null</code>
     * the behavior is unspecified and may result in an
     * exception.
     *
     * <p>
     *  通过将项目事件分派到任何已注册的<code> ItemListener </code>对象来处理此复选框上发生的项目事件。
     * <p>
     *  除非为此组件启用项目事件,否则不会调用此方法。当发生以下情况之一时,将启用项目事件：
     * <ul>
     *  <li> <code> ItemListener </code>对象通过<code> addItemListener </code>注册。
     *  <li>项目事件通过<code> enableEvents </code>启用。
     * </ul>
     *  <p>请注意,如果事件参数为<code> null </code>,则此行为未指定,并可能导致异常。
     * 
     * 
     * @param       e the item event
     * @see         java.awt.event.ItemEvent
     * @see         java.awt.event.ItemListener
     * @see         #addItemListener
     * @see         java.awt.Component#enableEvents
     * @since       JDK1.1
     */
    protected void processItemEvent(ItemEvent e) {
        ItemListener listener = itemListener;
        if (listener != null) {
            listener.itemStateChanged(e);
        }
    }

    /**
     * Returns a string representing the state of this <code>Checkbox</code>.
     * This method is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not be
     * <code>null</code>.
     *
     * <p>
     *  返回表示此<c>复选框</code>的状态的字符串。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return    the parameter string of this check box
     */
    protected String paramString() {
        String str = super.paramString();
        String label = this.label;
        if (label != null) {
            str += ",label=" + label;
        }
        return str + ",state=" + state;
    }


    /* Serialization support.
    /* <p>
     */

    /*
     * Serialized data version
     * <p>
     *  序列化数据版本
     * 
     * 
     * @serial
     */
    private int checkboxSerializedDataVersion = 1;

    /**
     * Writes default serializable fields to stream.  Writes
     * a list of serializable <code>ItemListeners</code>
     * as optional data.  The non-serializable
     * <code>ItemListeners</code> are detected and
     * no attempt is made to serialize them.
     *
     * <p>
     *  将缺省可序列化字段写入流。将可序列化的<code> ItemListeners </code>列表写为可选数据。
     * 检测到不可序列化的<code> ItemListeners </code>,并且不尝试将它们序列化。
     * 
     * 
     * @param s the <code>ObjectOutputStream</code> to write
     * @serialData <code>null</code> terminated sequence of 0
     *   or more pairs; the pair consists of a <code>String</code>
     *   and an <code>Object</code>; the <code>String</code> indicates
     *   the type of object and is one of the following:
     *   <code>itemListenerK</code> indicating an
     *     <code>ItemListener</code> object
     *
     * @see AWTEventMulticaster#save(ObjectOutputStream, String, EventListener)
     * @see java.awt.Component#itemListenerK
     * @see #readObject(ObjectInputStream)
     */
    private void writeObject(ObjectOutputStream s)
      throws java.io.IOException
    {
      s.defaultWriteObject();

      AWTEventMulticaster.save(s, itemListenerK, itemListener);
      s.writeObject(null);
    }

    /**
     * Reads the <code>ObjectInputStream</code> and if it
     * isn't <code>null</code> adds a listener to receive
     * item events fired by the <code>Checkbox</code>.
     * Unrecognized keys or values will be ignored.
     *
     * <p>
     * 读取<code> ObjectInputStream </code>,如果不是<code> null </code>添加了一个侦听器来接收由<code>复选框</code>触发的项目事件。
     * 无法识别的键或值将被忽略。
     * 
     * 
     * @param s the <code>ObjectInputStream</code> to read
     * @exception HeadlessException if
     *   <code>GraphicsEnvironment.isHeadless</code> returns
     *   <code>true</code>
     * @serial
     * @see #removeItemListener(ItemListener)
     * @see #addItemListener(ItemListener)
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see #writeObject(ObjectOutputStream)
     */
    private void readObject(ObjectInputStream s)
      throws ClassNotFoundException, IOException, HeadlessException
    {
      GraphicsEnvironment.checkHeadless();
      s.defaultReadObject();

      Object keyOrNull;
      while(null != (keyOrNull = s.readObject())) {
        String key = ((String)keyOrNull).intern();

        if (itemListenerK == key)
          addItemListener((ItemListener)(s.readObject()));

        else // skip value for unrecognized key
          s.readObject();
      }
    }

    /**
     * Initialize JNI field and method ids
     * <p>
     *  初始化JNI字段和方法标识
     * 
     */
    private static native void initIDs();


/////////////////
// Accessibility support
////////////////


    /**
     * Gets the AccessibleContext associated with this Checkbox.
     * For checkboxes, the AccessibleContext takes the form of an
     * AccessibleAWTCheckbox.
     * A new AccessibleAWTCheckbox is created if necessary.
     *
     * <p>
     *  获取与此复选框相关联的AccessibleContext。对于复选框,AccessibleContext采用AccessibleAWTCheckbox的形式。
     * 如有必要,将创建一个新的AccessibleAWTCheckbox。
     * 
     * 
     * @return an AccessibleAWTCheckbox that serves as the
     *         AccessibleContext of this Checkbox
     * @since 1.3
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleAWTCheckbox();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>Checkbox</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to checkbox user-interface elements.
     * <p>
     *  此类实现<code>复选框</code>类的辅助功能支持。它提供了适用于checkbox用户界面元素的Java辅助功能API的实现。
     * 
     * 
     * @since 1.3
     */
    protected class AccessibleAWTCheckbox extends AccessibleAWTComponent
        implements ItemListener, AccessibleAction, AccessibleValue
    {
        /*
         * JDK 1.3 serialVersionUID
         * <p>
         *  JDK 1.3 serialVersionUID
         * 
         */
        private static final long serialVersionUID = 7881579233144754107L;

        public AccessibleAWTCheckbox() {
            super();
            Checkbox.this.addItemListener(this);
        }

        /**
         * Fire accessible property change events when the state of the
         * toggle button changes.
         * <p>
         *  当切换按钮的状态更改时,触发可触及的属性更改事件。
         * 
         */
        public void itemStateChanged(ItemEvent e) {
            Checkbox cb = (Checkbox) e.getSource();
            if (Checkbox.this.accessibleContext != null) {
                if (cb.getState()) {
                    Checkbox.this.accessibleContext.firePropertyChange(
                            AccessibleContext.ACCESSIBLE_STATE_PROPERTY,
                            null, AccessibleState.CHECKED);
                } else {
                    Checkbox.this.accessibleContext.firePropertyChange(
                            AccessibleContext.ACCESSIBLE_STATE_PROPERTY,
                            AccessibleState.CHECKED, null);
                }
            }
        }

        /**
         * Get the AccessibleAction associated with this object.  In the
         * implementation of the Java Accessibility API for this class,
         * return this object, which is responsible for implementing the
         * AccessibleAction interface on behalf of itself.
         *
         * <p>
         *  获取与此对象关联的AccessibleAction。在为该类实现Java辅助功能API时,返回此对象,该对象负责代表自身实现AccessibleAction接口。
         * 
         * 
         * @return this object
         */
        public AccessibleAction getAccessibleAction() {
            return this;
        }

        /**
         * Get the AccessibleValue associated with this object.  In the
         * implementation of the Java Accessibility API for this class,
         * return this object, which is responsible for implementing the
         * AccessibleValue interface on behalf of itself.
         *
         * <p>
         *  获取与此对象关联的AccessibleValue。在为该类实现Java Accessibility API时,返回此对象,该对象负责代表自身实现AccessibleValue接口。
         * 
         * 
         * @return this object
         */
        public AccessibleValue getAccessibleValue() {
            return this;
        }

        /**
         * Returns the number of Actions available in this object.
         * If there is more than one, the first one is the "default"
         * action.
         *
         * <p>
         *  返回此对象中可用的操作数。如果有多个,第一个是"默认"动作。
         * 
         * 
         * @return the number of Actions in this object
         */
        public int getAccessibleActionCount() {
            return 0;  //  To be fully implemented in a future release
        }

        /**
         * Return a description of the specified action of the object.
         *
         * <p>
         *  返回对象的指定操作的描述。
         * 
         * 
         * @param i zero-based index of the actions
         */
        public String getAccessibleActionDescription(int i) {
            return null;  //  To be fully implemented in a future release
        }

        /**
         * Perform the specified Action on the object
         *
         * <p>
         *  对对象执行指定的Action
         * 
         * 
         * @param i zero-based index of actions
         * @return true if the the action was performed; else false.
         */
        public boolean doAccessibleAction(int i) {
            return false;    //  To be fully implemented in a future release
        }

        /**
         * Get the value of this object as a Number.  If the value has not been
         * set, the return value will be null.
         *
         * <p>
         * 将此对象的值作为数字获取。如果未设置该值,则返回值将为null。
         * 
         * 
         * @return value of the object
         * @see #setCurrentAccessibleValue
         */
        public Number getCurrentAccessibleValue() {
            return null;  //  To be fully implemented in a future release
        }

        /**
         * Set the value of this object as a Number.
         *
         * <p>
         *  将此对象的值设置为Number。
         * 
         * 
         * @return True if the value was set; else False
         * @see #getCurrentAccessibleValue
         */
        public boolean setCurrentAccessibleValue(Number n) {
            return false;  //  To be fully implemented in a future release
        }

        /**
         * Get the minimum value of this object as a Number.
         *
         * <p>
         *  获取此对象的最小值作为数字。
         * 
         * 
         * @return Minimum value of the object; null if this object does not
         * have a minimum value
         * @see #getMaximumAccessibleValue
         */
        public Number getMinimumAccessibleValue() {
            return null;  //  To be fully implemented in a future release
        }

        /**
         * Get the maximum value of this object as a Number.
         *
         * <p>
         *  获取此对象的最大值作为数字。
         * 
         * 
         * @return Maximum value of the object; null if this object does not
         * have a maximum value
         * @see #getMinimumAccessibleValue
         */
        public Number getMaximumAccessibleValue() {
            return null;  //  To be fully implemented in a future release
        }

        /**
         * Get the role of this object.
         *
         * <p>
         *  获取此对象的作用。
         * 
         * 
         * @return an instance of AccessibleRole describing the role of
         * the object
         * @see AccessibleRole
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.CHECK_BOX;
        }

        /**
         * Get the state set of this object.
         *
         * <p>
         *  获取此对象的状态集。
         * 
         * @return an instance of AccessibleState containing the current state
         * of the object
         * @see AccessibleState
         */
        public AccessibleStateSet getAccessibleStateSet() {
            AccessibleStateSet states = super.getAccessibleStateSet();
            if (getState()) {
                states.add(AccessibleState.CHECKED);
            }
            return states;
        }


    } // inner class AccessibleAWTCheckbox

}
