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

import java.beans.ConstructorProperties;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.plaf.*;
import javax.swing.event.*;
import javax.accessibility.*;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;


/**
 * An implementation of a "push" button.
  * <p>
 * Buttons can be configured, and to some degree controlled, by
 * <code><a href="Action.html">Action</a></code>s.  Using an
 * <code>Action</code> with a button has many benefits beyond directly
 * configuring a button.  Refer to <a href="Action.html#buttonActions">
 * Swing Components Supporting <code>Action</code></a> for more
 * details, and you can find more information in <a
 * href="https://docs.oracle.com/javase/tutorial/uiswing/misc/action.html">How
 * to Use Actions</a>, a section in <em>The Java Tutorial</em>.
 * <p>
 * See <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/button.html">How to Use Buttons, Check Boxes, and Radio Buttons</a>
 * in <em>The Java Tutorial</em>
 * for information and examples of using buttons.
 * <p>
 * <strong>Warning:</strong> Swing is not thread safe. For more
 * information see <a
 * href="package-summary.html#threading">Swing's Threading
 * Policy</a>.
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
 * @beaninfo
 *   attribute: isContainer false
 * description: An implementation of a \"push\" button.
 *
 * <p>
 *  一个"push"按钮的实现。
 * <p>
 *  可以通过<code> <a href="Action.html">操作</a> </code>配置按钮,并在某种程度上控制。
 * 除了直接配置按钮外,使用<code> Action </code>按钮还有许多好处。
 * 有关详情,请参阅<a href="Action.html#buttonActions"> Swing组件支持<code>操作</code> </a>,您可以在<a href ="https：// docs中找到更多信息.oracle.com / javase / tutorial / uiswing / misc / action.html">
 * 如何使用操作</a>,<em> Java教程</em>中的一节。
 * 除了直接配置按钮外,使用<code> Action </code>按钮还有许多好处。
 * <p>
 *  请参阅<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/button.html">如何在<em>中使用按钮,复选框
 * 和单选按钮</a> Java教程</em>,了解使用按钮的信息和示例。
 * <p>
 *  <strong>警告：</strong> Swing不是线程安全的。有关详情,请参阅<a href="package-summary.html#threading"> Swing的线程策略</a>。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * @beaninfo属性：isContainer false description：实现一个"push"按钮。
 * 
 * 
 * @author Jeff Dinkins
 */
@SuppressWarnings("serial")
public class JButton extends AbstractButton implements Accessible {

    /**
    /* <p>
    /* 
     * @see #getUIClassID
     * @see #readObject
     */
    private static final String uiClassID = "ButtonUI";

    /**
     * Creates a button with no set text or icon.
     * <p>
     *  创建没有设置文本或图标的按钮。
     * 
     */
    public JButton() {
        this(null, null);
    }

    /**
     * Creates a button with an icon.
     *
     * <p>
     *  创建带有图标的按钮。
     * 
     * 
     * @param icon  the Icon image to display on the button
     */
    public JButton(Icon icon) {
        this(null, icon);
    }

    /**
     * Creates a button with text.
     *
     * <p>
     *  创建具有文本的按钮。
     * 
     * 
     * @param text  the text of the button
     */
    @ConstructorProperties({"text"})
    public JButton(String text) {
        this(text, null);
    }

    /**
     * Creates a button where properties are taken from the
     * <code>Action</code> supplied.
     *
     * <p>
     *  创建一个按钮,其中的属性取自提供的<code> Action </code>。
     * 
     * 
     * @param a the <code>Action</code> used to specify the new button
     *
     * @since 1.3
     */
    public JButton(Action a) {
        this();
        setAction(a);
    }

    /**
     * Creates a button with initial text and an icon.
     *
     * <p>
     *  创建具有初始文本和图标的按钮。
     * 
     * 
     * @param text  the text of the button
     * @param icon  the Icon image to display on the button
     */
    public JButton(String text, Icon icon) {
        // Create the model
        setModel(new DefaultButtonModel());

        // initialize
        init(text, icon);
    }

    /**
     * Resets the UI property to a value from the current look and
     * feel.
     *
     * <p>
     *  将UI属性重置为当前外观的值。
     * 
     * 
     * @see JComponent#updateUI
     */
    public void updateUI() {
        setUI((ButtonUI)UIManager.getUI(this));
    }


    /**
     * Returns a string that specifies the name of the L&amp;F class
     * that renders this component.
     *
     * <p>
     *  返回一个字符串,指定呈现此组件的L&amp; F类的名称。
     * 
     * 
     * @return the string "ButtonUI"
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     * @beaninfo
     *        expert: true
     *   description: A string that specifies the name of the L&amp;F class.
     */
    public String getUIClassID() {
        return uiClassID;
    }


    /**
     * Gets the value of the <code>defaultButton</code> property,
     * which if <code>true</code> means that this button is the current
     * default button for its <code>JRootPane</code>.
     * Most look and feels render the default button
     * differently, and may potentially provide bindings
     * to access the default button.
     *
     * <p>
     *  获取<code> defaultButton </code>属性的值,如果<code> true </code>表示此按钮是其<code> JRootPane </code>的当前默认按钮。
     * 大多数外观和感觉呈现的默认按钮不同,并可能提供绑定访问默认按钮。
     * 
     * 
     * @return the value of the <code>defaultButton</code> property
     * @see JRootPane#setDefaultButton
     * @see #isDefaultCapable
     * @beaninfo
     *  description: Whether or not this button is the default button
     */
    public boolean isDefaultButton() {
        JRootPane root = SwingUtilities.getRootPane(this);
        if (root != null) {
            return root.getDefaultButton() == this;
        }
        return false;
    }

    /**
     * Gets the value of the <code>defaultCapable</code> property.
     *
     * <p>
     *  获取<code> defaultCapable </code>属性的值。
     * 
     * 
     * @return the value of the <code>defaultCapable</code> property
     * @see #setDefaultCapable
     * @see #isDefaultButton
     * @see JRootPane#setDefaultButton
     */
    public boolean isDefaultCapable() {
        return defaultCapable;
    }

    /**
     * Sets the <code>defaultCapable</code> property,
     * which determines whether this button can be
     * made the default button for its root pane.
     * The default value of the <code>defaultCapable</code>
     * property is <code>true</code> unless otherwise
     * specified by the look and feel.
     *
     * <p>
     *  设置<code> defaultCapable </code>属性,它确定此按钮是否可以作为其根窗格的默认按钮。
     * 除非另外指定的外观和感觉,<code> defaultCapable </code>属性的默认值为<code> true </code>。
     * 
     * 
     * @param defaultCapable <code>true</code> if this button will be
     *        capable of being the default button on the
     *        <code>RootPane</code>; otherwise <code>false</code>
     * @see #isDefaultCapable
     * @beaninfo
     *        bound: true
     *    attribute: visualUpdate true
     *  description: Whether or not this button can be the default button
     */
    public void setDefaultCapable(boolean defaultCapable) {
        boolean oldDefaultCapable = this.defaultCapable;
        this.defaultCapable = defaultCapable;
        firePropertyChange("defaultCapable", oldDefaultCapable, defaultCapable);
    }

    /**
     * Overrides <code>JComponent.removeNotify</code> to check if
     * this button is currently set as the default button on the
     * <code>RootPane</code>, and if so, sets the <code>RootPane</code>'s
     * default button to <code>null</code> to ensure the
     * <code>RootPane</code> doesn't hold onto an invalid button reference.
     * <p>
     *  覆盖<code> JComponent.removeNotify </code>以检查此按钮是否当前设置为<code> RootPane </code>上的默认按钮,如果是,则设置<code> Roo
     * tPane </code>默认按钮到<code> null </code>,以确保<code> RootPane </code>不会保存到无效的按钮引用。
     * 
     */
    public void removeNotify() {
        JRootPane root = SwingUtilities.getRootPane(this);
        if (root != null && root.getDefaultButton() == this) {
            root.setDefaultButton(null);
        }
        super.removeNotify();
    }

    /**
     * See readObject() and writeObject() in JComponent for more
     * information about serialization in Swing.
     * <p>
     * 有关Swing中序列化的更多信息,请参阅JComponent中的readObject()和writeObject()。
     * 
     */
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        if (getUIClassID().equals(uiClassID)) {
            byte count = JComponent.getWriteObjCounter(this);
            JComponent.setWriteObjCounter(this, --count);
            if (count == 0 && ui != null) {
                ui.installUI(this);
            }
        }
    }


    /**
     * Returns a string representation of this <code>JButton</code>.
     * This method is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     *
     * <p>
     *  返回此<code> JButton </code>的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return  a string representation of this <code>JButton</code>
     */
    protected String paramString() {
        String defaultCapableString = (defaultCapable ? "true" : "false");

        return super.paramString() +
            ",defaultCapable=" + defaultCapableString;
    }


/////////////////
// Accessibility support
////////////////

    /**
     * Gets the <code>AccessibleContext</code> associated with this
     * <code>JButton</code>. For <code>JButton</code>s,
     * the <code>AccessibleContext</code> takes the form of an
     * <code>AccessibleJButton</code>.
     * A new <code>AccessibleJButton</code> instance is created if necessary.
     *
     * <p>
     *  获取与此<code> JButton </code>关联的<code> AccessibleContext </code>。
     * 对于<code> JButton </code>,<code> AccessibleContext </code>采用<code> AccessibleJButton </code>的形式。
     * 如果需要,将创建一个新的<code> AccessibleJButton </code>实例。
     * 
     * 
     * @return an <code>AccessibleJButton</code> that serves as the
     *         <code>AccessibleContext</code> of this <code>JButton</code>
     * @beaninfo
     *       expert: true
     *  description: The AccessibleContext associated with this Button.
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJButton();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>JButton</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to button user-interface
     * elements.
     * <p>
     * <strong>Warning:</strong>
     * Serialized objects of this class will not be compatible with
     * future Swing releases. The current serialization support is
     * appropriate for short term storage or RMI between applications running
     * the same version of Swing.  As of 1.4, support for long term storage
     * of all JavaBeans&trade;
     * has been added to the <code>java.beans</code> package.
     * Please see {@link java.beans.XMLEncoder}.
     * <p>
     *  此类实现了<code> JButton </code>类的辅助功能支持。它提供了适用于按钮用户界面元素的Java辅助功能API的实现。
     * <p>
     *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     */
    @SuppressWarnings("serial")
    protected class AccessibleJButton extends AccessibleAbstractButton {

        /**
         * Get the role of this object.
         *
         * <p>
         * 
         * 
         * @return an instance of AccessibleRole describing the role of the
         * object
         * @see AccessibleRole
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.PUSH_BUTTON;
        }
    } // inner class AccessibleJButton
}
