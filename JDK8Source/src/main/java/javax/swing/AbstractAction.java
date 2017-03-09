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
import java.beans.*;
import java.util.Hashtable;
import java.util.Enumeration;
import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.AccessController;
import javax.swing.event.SwingPropertyChangeSupport;
import sun.security.action.GetPropertyAction;

/**
 * This class provides default implementations for the JFC <code>Action</code>
 * interface. Standard behaviors like the get and set methods for
 * <code>Action</code> object properties (icon, text, and enabled) are defined
 * here. The developer need only subclass this abstract class and
 * define the <code>actionPerformed</code> method.
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
 *  这个类提供了JFC <code> Action </code>接口的默认实现。在此定义了<code> Action </code>对象属性(图标,文本和启用)的标准行为,如get和set方法。
 * 开发人员只需要子类化这个抽象类并定义<code> actionPerformed </code>方法。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author Georges Saab
 * @see Action
 */
public abstract class AbstractAction implements Action, Cloneable, Serializable
{
    /**
     * Whether or not actions should reconfigure all properties on null.
     * <p>
     *  操作是否应在null上重新配置所有属性。
     * 
     */
    private static Boolean RECONFIGURE_ON_NULL;

    /**
     * Specifies whether action is enabled; the default is true.
     * <p>
     *  指定是否启用操作;默认值为true。
     * 
     */
    protected boolean enabled = true;


    /**
     * Contains the array of key bindings.
     * <p>
     *  包含键绑定数组。
     * 
     */
    private transient ArrayTable arrayTable;

    /**
     * Whether or not to reconfigure all action properties from the
     * specified event.
     * <p>
     *  是否从指定的事件重新配置所有操作属性。
     * 
     */
    static boolean shouldReconfigure(PropertyChangeEvent e) {
        if (e.getPropertyName() == null) {
            synchronized(AbstractAction.class) {
                if (RECONFIGURE_ON_NULL == null) {
                    RECONFIGURE_ON_NULL = Boolean.valueOf(
                        AccessController.doPrivileged(new GetPropertyAction(
                        "swing.actions.reconfigureOnNull", "false")));
                }
                return RECONFIGURE_ON_NULL;
            }
        }
        return false;
    }

    /**
     * Sets the enabled state of a component from an Action.
     *
     * <p>
     *  从"操作"中设置组件的启用状态。
     * 
     * 
     * @param c the Component to set the enabled state on
     * @param a the Action to set the enabled state from, may be null
     */
    static void setEnabledFromAction(JComponent c, Action a) {
        c.setEnabled((a != null) ? a.isEnabled() : true);
    }

    /**
     * Sets the tooltip text of a component from an Action.
     *
     * <p>
     *  从"操作"中设置组件的工具提示文本。
     * 
     * 
     * @param c the Component to set the tooltip text on
     * @param a the Action to set the tooltip text from, may be null
     */
    static void setToolTipTextFromAction(JComponent c, Action a) {
        c.setToolTipText(a != null ?
                         (String)a.getValue(Action.SHORT_DESCRIPTION) : null);
    }

    static boolean hasSelectedKey(Action a) {
        return (a != null && a.getValue(Action.SELECTED_KEY) != null);
    }

    static boolean isSelected(Action a) {
        return Boolean.TRUE.equals(a.getValue(Action.SELECTED_KEY));
    }



    /**
     * Creates an {@code Action}.
     * <p>
     *  创建{@code Action}。
     * 
     */
    public AbstractAction() {
    }

    /**
     * Creates an {@code Action} with the specified name.
     *
     * <p>
     *  创建具有指定名称的{@code Action}。
     * 
     * 
     * @param name the name ({@code Action.NAME}) for the action; a
     *        value of {@code null} is ignored
     */
    public AbstractAction(String name) {
        putValue(Action.NAME, name);
    }

    /**
     * Creates an {@code Action} with the specified name and small icon.
     *
     * <p>
     *  创建具有指定名称和小图标的{@code Action}。
     * 
     * 
     * @param name the name ({@code Action.NAME}) for the action; a
     *        value of {@code null} is ignored
     * @param icon the small icon ({@code Action.SMALL_ICON}) for the action; a
     *        value of {@code null} is ignored
     */
    public AbstractAction(String name, Icon icon) {
        this(name);
        putValue(Action.SMALL_ICON, icon);
    }

    /**
     * Gets the <code>Object</code> associated with the specified key.
     *
     * <p>
     *  获取与指定键相关联的<code> Object </code>。
     * 
     * 
     * @param key a string containing the specified <code>key</code>
     * @return the binding <code>Object</code> stored with this key; if there
     *          are no keys, it will return <code>null</code>
     * @see Action#getValue
     */
    public Object getValue(String key) {
        if (key == "enabled") {
            return enabled;
        }
        if (arrayTable == null) {
            return null;
        }
        return arrayTable.get(key);
    }

    /**
     * Sets the <code>Value</code> associated with the specified key.
     *
     * <p>
     * 设置与指定键相关联的<code> Value </code>。
     * 
     * 
     * @param key  the <code>String</code> that identifies the stored object
     * @param newValue the <code>Object</code> to store using this key
     * @see Action#putValue
     */
    public void putValue(String key, Object newValue) {
        Object oldValue = null;
        if (key == "enabled") {
            // Treat putValue("enabled") the same way as a call to setEnabled.
            // If we don't do this it means the two may get out of sync, and a
            // bogus property change notification would be sent.
            //
            // To avoid dependencies between putValue & setEnabled this
            // directly changes enabled. If we instead called setEnabled
            // to change enabled, it would be possible for stack
            // overflow in the case where a developer implemented setEnabled
            // in terms of putValue.
            if (newValue == null || !(newValue instanceof Boolean)) {
                newValue = false;
            }
            oldValue = enabled;
            enabled = (Boolean)newValue;
        } else {
            if (arrayTable == null) {
                arrayTable = new ArrayTable();
            }
            if (arrayTable.containsKey(key))
                oldValue = arrayTable.get(key);
            // Remove the entry for key if newValue is null
            // else put in the newValue for key.
            if (newValue == null) {
                arrayTable.remove(key);
            } else {
                arrayTable.put(key,newValue);
            }
        }
        firePropertyChange(key, oldValue, newValue);
    }

    /**
     * Returns true if the action is enabled.
     *
     * <p>
     *  如果启用操作,则返回true。
     * 
     * 
     * @return true if the action is enabled, false otherwise
     * @see Action#isEnabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets whether the {@code Action} is enabled. The default is {@code true}.
     *
     * <p>
     *  设置是否启用{@code Action}。默认值为{@code true}。
     * 
     * 
     * @param newValue  {@code true} to enable the action, {@code false} to
     *                  disable it
     * @see Action#setEnabled
     */
    public void setEnabled(boolean newValue) {
        boolean oldValue = this.enabled;

        if (oldValue != newValue) {
            this.enabled = newValue;
            firePropertyChange("enabled",
                               Boolean.valueOf(oldValue), Boolean.valueOf(newValue));
        }
    }


    /**
     * Returns an array of <code>Object</code>s which are keys for
     * which values have been set for this <code>AbstractAction</code>,
     * or <code>null</code> if no keys have values set.
     * <p>
     *  返回一个<code> Object </code>的数组,如果没有键设置值,则该数组是为<code> AbstractAction </code>或<code> null </code>设置值的键。
     * 
     * 
     * @return an array of key objects, or <code>null</code> if no
     *                  keys have values set
     * @since 1.3
     */
    public Object[] getKeys() {
        if (arrayTable == null) {
            return null;
        }
        Object[] keys = new Object[arrayTable.size()];
        arrayTable.getKeys(keys);
        return keys;
    }

    /**
     * If any <code>PropertyChangeListeners</code> have been registered, the
     * <code>changeSupport</code> field describes them.
     * <p>
     *  如果已经注册了任何<code> PropertyChangeListeners </code>,则<code> changeSupport </code>字段描述它们。
     * 
     */
    protected SwingPropertyChangeSupport changeSupport;

    /**
     * Supports reporting bound property changes.  This method can be called
     * when a bound property has changed and it will send the appropriate
     * <code>PropertyChangeEvent</code> to any registered
     * <code>PropertyChangeListeners</code>.
     * <p>
     *  支持报告绑定的属性更改。
     * 当绑定属性改变时,可以调用这个方法,并且它将向任何注册的<code> PropertyChangeListeners </code>发送适当的<code> PropertyChangeEvent </code>
     * 。
     *  支持报告绑定的属性更改。
     * 
     */
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        if (changeSupport == null ||
            (oldValue != null && newValue != null && oldValue.equals(newValue))) {
            return;
        }
        changeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }


    /**
     * Adds a <code>PropertyChangeListener</code> to the listener list.
     * The listener is registered for all properties.
     * <p>
     * A <code>PropertyChangeEvent</code> will get fired in response to setting
     * a bound property, e.g. <code>setFont</code>, <code>setBackground</code>,
     * or <code>setForeground</code>.
     * Note that if the current component is inheriting its foreground,
     * background, or font from its container, then no event will be
     * fired in response to a change in the inherited property.
     *
     * <p>
     *  向侦听器列表中添加<code> PropertyChangeListener </code>。侦听器为所有属性注册。
     * <p>
     *  一个<code> PropertyChangeEvent </code>会响应设置绑定的属性,例如。
     *  <code> setFont </code>,<code> setBackground </code>或<code> setForeground </code>。
     * 请注意,如果当前组件从其容器继承其前景,背景或字体,则不会触发任何事件以响应继承的属性中的更改。
     * 
     * 
     * @param listener  The <code>PropertyChangeListener</code> to be added
     *
     * @see Action#addPropertyChangeListener
     */
    public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
        if (changeSupport == null) {
            changeSupport = new SwingPropertyChangeSupport(this);
        }
        changeSupport.addPropertyChangeListener(listener);
    }


    /**
     * Removes a <code>PropertyChangeListener</code> from the listener list.
     * This removes a <code>PropertyChangeListener</code> that was registered
     * for all properties.
     *
     * <p>
     *  从侦听器列表中删除<code> PropertyChangeListener </code>。这将删除为所有属性注册的<code> PropertyChangeListener </code>。
     * 
     * 
     * @param listener  the <code>PropertyChangeListener</code> to be removed
     *
     * @see Action#removePropertyChangeListener
     */
    public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
        if (changeSupport == null) {
            return;
        }
        changeSupport.removePropertyChangeListener(listener);
    }


    /**
     * Returns an array of all the <code>PropertyChangeListener</code>s added
     * to this AbstractAction with addPropertyChangeListener().
     *
     * <p>
     *  返回使用addPropertyChangeListener()添加到此AbstractAction的所有<code> PropertyChangeListener </code>数组。
     * 
     * 
     * @return all of the <code>PropertyChangeListener</code>s added or an empty
     *         array if no listeners have been added
     * @since 1.4
     */
    public synchronized PropertyChangeListener[] getPropertyChangeListeners() {
        if (changeSupport == null) {
            return new PropertyChangeListener[0];
        }
        return changeSupport.getPropertyChangeListeners();
    }


    /**
     * Clones the abstract action. This gives the clone
     * its own copy of the key/value list,
     * which is not handled for you by <code>Object.clone()</code>.
     * <p>
     * 克隆抽象动作。这给克隆自己的键/值列表的副本,它不是由<code> Object.clone()</code>处理的。
     * 
     **/

    protected Object clone() throws CloneNotSupportedException {
        AbstractAction newAction = (AbstractAction)super.clone();
        synchronized(this) {
            if (arrayTable != null) {
                newAction.arrayTable = (ArrayTable)arrayTable.clone();
            }
        }
        return newAction;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        // Store the default fields
        s.defaultWriteObject();

        // And the keys
        ArrayTable.writeArrayTable(s, arrayTable);
    }

    private void readObject(ObjectInputStream s) throws ClassNotFoundException,
        IOException {
        s.defaultReadObject();
        for (int counter = s.readInt() - 1; counter >= 0; counter--) {
            putValue((String)s.readObject(), s.readObject());
        }
    }
}
