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

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.Serializable;
import java.util.EventListener;
import javax.swing.event.*;

/**
 * The default implementation of a <code>Button</code> component's data model.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing. As of 1.4, support for long term storage
 * of all JavaBeans&trade;
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * <p>
 *  组件的数据模型中默认实现了一个<code> Button </code>
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将不与未来的Swing版本兼容当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 * 支持长期存储所有JavaBeans&trade;已添加到<code> javabeans </code>包中请参见{@link javabeansXMLEncoder}。
 * 
 * 
 * @author Jeff Dinkins
 */
public class DefaultButtonModel implements ButtonModel, Serializable {

    /** The bitmask used to store the state of the button. */
    protected int stateMask = 0;

    /** The action command string fired by the button. */
    protected String actionCommand = null;

    /** The button group that the button belongs to. */
    protected ButtonGroup group = null;

    /** The button's mnemonic. */
    protected int mnemonic = 0;

    /**
     * Only one <code>ChangeEvent</code> is needed per button model
     * instance since the event's only state is the source property.
     * The source of events generated is always "this".
     * <p>
     * 因为事件的唯一状态是源属性,所以每个按钮模型实例只需要一个<code> ChangeEvent </code>。生成的事件源始终是"this"
     * 
     */
    protected transient ChangeEvent changeEvent = null;

    /** Stores the listeners on this model. */
    protected EventListenerList listenerList = new EventListenerList();

    // controls the usage of the MenuItem.disabledAreNavigable UIDefaults
    // property in the setArmed() method
    private boolean menuItem = false;

    /**
     * Constructs a <code>DefaultButtonModel</code>.
     *
     * <p>
     *  构造一个<code> DefaultButtonModel </code>
     * 
     */
    public DefaultButtonModel() {
        stateMask = 0;
        setEnabled(true);
    }

    /**
     * Identifies the "armed" bit in the bitmask, which
     * indicates partial commitment towards choosing/triggering
     * the button.
     * <p>
     *  标识位掩码中的"已设置"位,表示部分承诺选择/触发按钮
     * 
     */
    public final static int ARMED = 1 << 0;

    /**
     * Identifies the "selected" bit in the bitmask, which
     * indicates that the button has been selected. Only needed for
     * certain types of buttons - such as radio button or check box.
     * <p>
     *  标识位掩码中的"选定"位,表示已选择按钮仅某些类型的按钮需要 - 例如单选按钮或复选框
     * 
     */
    public final static int SELECTED = 1 << 1;

    /**
     * Identifies the "pressed" bit in the bitmask, which
     * indicates that the button is pressed.
     * <p>
     *  标识位掩码中的"按下"位,表示按下按钮
     * 
     */
    public final static int PRESSED = 1 << 2;

    /**
     * Identifies the "enabled" bit in the bitmask, which
     * indicates that the button can be selected by
     * an input device (such as a mouse pointer).
     * <p>
     *  标识位掩码中的"启用"位,表示该按钮可以由输入设备(例如鼠标指针)选择,
     * 
     */
    public final static int ENABLED = 1 << 3;

    /**
     * Identifies the "rollover" bit in the bitmask, which
     * indicates that the mouse is over the button.
     * <p>
     * 标识位掩码中的"翻转"位,表示鼠标在按钮上方
     * 
     */
    public final static int ROLLOVER = 1 << 4;

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public void setActionCommand(String actionCommand) {
        this.actionCommand = actionCommand;
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public String getActionCommand() {
        return actionCommand;
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public boolean isArmed() {
        return (stateMask & ARMED) != 0;
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public boolean isSelected() {
        return (stateMask & SELECTED) != 0;
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public boolean isEnabled() {
        return (stateMask & ENABLED) != 0;
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public boolean isPressed() {
        return (stateMask & PRESSED) != 0;
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public boolean isRollover() {
        return (stateMask & ROLLOVER) != 0;
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public void setArmed(boolean b) {
        if(isMenuItem() &&
                UIManager.getBoolean("MenuItem.disabledAreNavigable")) {
            if ((isArmed() == b)) {
                return;
            }
        } else {
            if ((isArmed() == b) || !isEnabled()) {
                return;
            }
        }

        if (b) {
            stateMask |= ARMED;
        } else {
            stateMask &= ~ARMED;
        }

        fireStateChanged();
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public void setEnabled(boolean b) {
        if(isEnabled() == b) {
            return;
        }

        if (b) {
            stateMask |= ENABLED;
        } else {
            stateMask &= ~ENABLED;
            // unarm and unpress, just in case
            stateMask &= ~ARMED;
            stateMask &= ~PRESSED;
        }


        fireStateChanged();
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public void setSelected(boolean b) {
        if (this.isSelected() == b) {
            return;
        }

        if (b) {
            stateMask |= SELECTED;
        } else {
            stateMask &= ~SELECTED;
        }

        fireItemStateChanged(
                new ItemEvent(this,
                              ItemEvent.ITEM_STATE_CHANGED,
                              this,
                              b ?  ItemEvent.SELECTED : ItemEvent.DESELECTED));

        fireStateChanged();

    }


    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public void setPressed(boolean b) {
        if((isPressed() == b) || !isEnabled()) {
            return;
        }

        if (b) {
            stateMask |= PRESSED;
        } else {
            stateMask &= ~PRESSED;
        }

        if(!isPressed() && isArmed()) {
            int modifiers = 0;
            AWTEvent currentEvent = EventQueue.getCurrentEvent();
            if (currentEvent instanceof InputEvent) {
                modifiers = ((InputEvent)currentEvent).getModifiers();
            } else if (currentEvent instanceof ActionEvent) {
                modifiers = ((ActionEvent)currentEvent).getModifiers();
            }
            fireActionPerformed(
                new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
                                getActionCommand(),
                                EventQueue.getMostRecentEventTime(),
                                modifiers));
        }

        fireStateChanged();
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public void setRollover(boolean b) {
        if((isRollover() == b) || !isEnabled()) {
            return;
        }

        if (b) {
            stateMask |= ROLLOVER;
        } else {
            stateMask &= ~ROLLOVER;
        }

        fireStateChanged();
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public void setMnemonic(int key) {
        mnemonic = key;
        fireStateChanged();
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public int getMnemonic() {
        return mnemonic;
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public void addChangeListener(ChangeListener l) {
        listenerList.add(ChangeListener.class, l);
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public void removeChangeListener(ChangeListener l) {
        listenerList.remove(ChangeListener.class, l);
    }

    /**
     * Returns an array of all the change listeners
     * registered on this <code>DefaultButtonModel</code>.
     *
     * <p>
     *  返回在此<code> DefaultButtonModel </code>上注册的所有更改侦听器的数组
     * 
     * 
     * @return all of this model's <code>ChangeListener</code>s
     *         or an empty
     *         array if no change listeners are currently registered
     *
     * @see #addChangeListener
     * @see #removeChangeListener
     *
     * @since 1.4
     */
    public ChangeListener[] getChangeListeners() {
        return listenerList.getListeners(ChangeListener.class);
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is created lazily.
     *
     * <p>
     *  通知所有对此事件类型的通知感兴趣的所有侦听器事件实例是延迟创建的
     * 
     * 
     * @see EventListenerList
     */
    protected void fireStateChanged() {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ChangeListener.class) {
                // Lazily create the event:
                if (changeEvent == null)
                    changeEvent = new ChangeEvent(this);
                ((ChangeListener)listeners[i+1]).stateChanged(changeEvent);
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public void addActionListener(ActionListener l) {
        listenerList.add(ActionListener.class, l);
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public void removeActionListener(ActionListener l) {
        listenerList.remove(ActionListener.class, l);
    }

    /**
     * Returns an array of all the action listeners
     * registered on this <code>DefaultButtonModel</code>.
     *
     * <p>
     *  返回在此<code> DefaultButtonModel </code>上注册的所有操作侦听器的数组
     * 
     * 
     * @return all of this model's <code>ActionListener</code>s
     *         or an empty
     *         array if no action listeners are currently registered
     *
     * @see #addActionListener
     * @see #removeActionListener
     *
     * @since 1.4
     */
    public ActionListener[] getActionListeners() {
        return listenerList.getListeners(ActionListener.class);
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.
     *
     * <p>
     *  通知所有已注册有关此事件类型的通知的收件人
     * 
     * 
     * @param e the <code>ActionEvent</code> to deliver to listeners
     * @see EventListenerList
     */
    protected void fireActionPerformed(ActionEvent e) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ActionListener.class) {
                // Lazily create the event:
                // if (changeEvent == null)
                // changeEvent = new ChangeEvent(this);
                ((ActionListener)listeners[i+1]).actionPerformed(e);
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public void addItemListener(ItemListener l) {
        listenerList.add(ItemListener.class, l);
    }

    /**
     * {@inheritDoc}
     * <p>
     * {@inheritDoc}
     * 
     */
    public void removeItemListener(ItemListener l) {
        listenerList.remove(ItemListener.class, l);
    }

    /**
     * Returns an array of all the item listeners
     * registered on this <code>DefaultButtonModel</code>.
     *
     * <p>
     *  返回在此<code> DefaultButtonModel </code>上注册的所有项目监听器的数组
     * 
     * 
     * @return all of this model's <code>ItemListener</code>s
     *         or an empty
     *         array if no item listeners are currently registered
     *
     * @see #addItemListener
     * @see #removeItemListener
     *
     * @since 1.4
     */
    public ItemListener[] getItemListeners() {
        return listenerList.getListeners(ItemListener.class);
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.
     *
     * <p>
     *  通知所有已注册有关此事件类型的通知的收件人
     * 
     * 
     * @param e the <code>ItemEvent</code> to deliver to listeners
     * @see EventListenerList
     */
    protected void fireItemStateChanged(ItemEvent e) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ItemListener.class) {
                // Lazily create the event:
                // if (changeEvent == null)
                // changeEvent = new ChangeEvent(this);
                ((ItemListener)listeners[i+1]).itemStateChanged(e);
            }
        }
    }

    /**
     * Returns an array of all the objects currently registered as
     * <code><em>Foo</em>Listener</code>s
     * upon this model.
     * <code><em>Foo</em>Listener</code>s
     * are registered using the <code>add<em>Foo</em>Listener</code> method.
     * <p>
     * You can specify the <code>listenerType</code> argument
     * with a class literal, such as <code><em>Foo</em>Listener.class</code>.
     * For example, you can query a <code>DefaultButtonModel</code>
     * instance <code>m</code>
     * for its action listeners
     * with the following code:
     *
     * <pre>ActionListener[] als = (ActionListener[])(m.getListeners(ActionListener.class));</pre>
     *
     * If no such listeners exist,
     * this method returns an empty array.
     *
     * <p>
     *  返回当前注册为<code> <em> Foo </em>侦听器</code>的所有对象的数组</code>在此模型<code> <em> </em> <code>添加<em> </em>侦听器</code>
     * 方法。
     * <p>
     *  您可以使用类文字指定<code> listenerType </code>参数,例如<code> <em> Foo </em> Listenerclass </code>例如,可以查询<code> D
     * efaultButtonModel </code > instance <code> m </code>为它的动作监听器使用以下代码：。
     * 
     *  <pre> ActionListener [] als =(ActionListener [])(mgetListeners(ActionListenerclass)); </pre>
     * 
     * 
     * @param listenerType  the type of listeners requested;
     *          this parameter should specify an interface
     *          that descends from <code>java.util.EventListener</code>
     * @return an array of all objects registered as
     *          <code><em>Foo</em>Listener</code>s
     *          on this model,
     *          or an empty array if no such
     *          listeners have been added
     * @exception ClassCastException if <code>listenerType</code> doesn't
     *          specify a class or interface that implements
     *          <code>java.util.EventListener</code>
     *
     * @see #getActionListeners
     * @see #getChangeListeners
     * @see #getItemListeners
     *
     * @since 1.3
     */
    public <T extends EventListener> T[] getListeners(Class<T> listenerType) {
        return listenerList.getListeners(listenerType);
    }

    /** Overridden to return <code>null</code>. */
    public Object[] getSelectedObjects() {
        return null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * 如果不存在此类侦听器,则此方法将返回一个空数组
     * 
     */
    public void setGroup(ButtonGroup group) {
        this.group = group;
    }

    /**
     * Returns the group that the button belongs to.
     * Normally used with radio buttons, which are mutually
     * exclusive within their group.
     *
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @return the <code>ButtonGroup</code> that the button belongs to
     *
     * @since 1.3
     */
    public ButtonGroup getGroup() {
        return group;
    }

    boolean isMenuItem() {
        return menuItem;
    }

    void setMenuItem(boolean menuItem) {
        this.menuItem = menuItem;
    }
}
