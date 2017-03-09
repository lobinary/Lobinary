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

import java.util.EventListener;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

import javax.swing.plaf.*;
import javax.accessibility.*;

/**
 * An implementation of a radio button menu item.
 * A <code>JRadioButtonMenuItem</code> is
 * a menu item that is part of a group of menu items in which only one
 * item in the group can be selected. The selected item displays its
 * selected state. Selecting it causes any other selected item to
 * switch to the unselected state.
 * To control the selected state of a group of radio button menu items,
 * use a <code>ButtonGroup</code> object.
 * <p>
 * Menu items can be configured, and to some degree controlled, by
 * <code><a href="Action.html">Action</a></code>s.  Using an
 * <code>Action</code> with a menu item has many benefits beyond directly
 * configuring a menu item.  Refer to <a href="Action.html#buttonActions">
 * Swing Components Supporting <code>Action</code></a> for more
 * details, and you can find more information in <a
 * href="https://docs.oracle.com/javase/tutorial/uiswing/misc/action.html">How
 * to Use Actions</a>, a section in <em>The Java Tutorial</em>.
 * <p>
 * For further documentation and examples see
 * <a
 href="https://docs.oracle.com/javase/tutorial/uiswing/components/menu.html">How to Use Menus</a>,
 * a section in <em>The Java Tutorial.</em>
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
 * description: A component within a group of menu items which can be selected.
 *
 * <p>
 *  单选按钮菜单项的实现。 <code> JRadioButtonMenuItem </code>是菜单项,它是一组菜单项的一部分,其中只能选择组中的一个项。所选项目显示其选定状态。
 * 选择它会使任何其他所选项目切换到未选择状态。要控制一组单选按钮菜单项的选定状态,请使用<code> ButtonGroup </code>对象。
 * <p>
 *  菜单项可以通过<code> <a href="Action.html">操作</a> </code>进行配置,并在某种程度上受到控制。
 * 对菜单项使用<code> Action </code>除了直接配置菜单项之外还有许多好处。
 * 有关详情,请参阅<a href="Action.html#buttonActions"> Swing组件支持<code>操作</code> </a>,您可以在<a href ="https：// docs中找到更多信息.oracle.com / javase / tutorial / uiswing / misc / action.html">
 * 如何使用操作</a>,<em> Java教程</em>中的一节。
 * 对菜单项使用<code> Action </code>除了直接配置菜单项之外还有许多好处。
 * <p>
 *  有关其他文档和示例,请参阅<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/menu.html">如何使用菜单</a>
 * ,<em>中的一节。
 *  Java教程。</em>。
 * <p>
 *  <strong>警告：</strong> Swing不是线程安全的。有关详情,请参阅<a href="package-summary.html#threading"> Swing的线程策略</a>。
 * <p>
 * <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 *  @beaninfo属性：isContainer false description：可以选择的一组菜单项中的组件。
 * 
 * 
 * @author Georges Saab
 * @author David Karlton
 * @see ButtonGroup
 */
public class JRadioButtonMenuItem extends JMenuItem implements Accessible {
    /**
    /* <p>
    /* 
     * @see #getUIClassID
     * @see #readObject
     */
    private static final String uiClassID = "RadioButtonMenuItemUI";

    /**
     * Creates a <code>JRadioButtonMenuItem</code> with no set text or icon.
     * <p>
     *  创建一个没有设置文本或图标的<code> JRadioButtonMenuItem </code>。
     * 
     */
    public JRadioButtonMenuItem() {
        this(null, null, false);
    }

    /**
     * Creates a <code>JRadioButtonMenuItem</code> with an icon.
     *
     * <p>
     *  使用图标创建<code> JRadioButtonMenuItem </code>。
     * 
     * 
     * @param icon the <code>Icon</code> to display on the
     *          <code>JRadioButtonMenuItem</code>
     */
    public JRadioButtonMenuItem(Icon icon) {
        this(null, icon, false);
    }

    /**
     * Creates a <code>JRadioButtonMenuItem</code> with text.
     *
     * <p>
     *  使用文本创建<code> JRadioButtonMenuItem </code>。
     * 
     * 
     * @param text the text of the <code>JRadioButtonMenuItem</code>
     */
    public JRadioButtonMenuItem(String text) {
        this(text, null, false);
    }

    /**
     * Creates a radio button menu item whose properties are taken from the
     * <code>Action</code> supplied.
     *
     * <p>
     *  创建一个单选按钮菜单项,其属性取自提供的<code> Action </code>。
     * 
     * 
     * @param  a the <code>Action</code> on which to base the radio
     *          button menu item
     *
     * @since 1.3
     */
    public JRadioButtonMenuItem(Action a) {
        this();
        setAction(a);
    }

    /**
     * Creates a radio button menu item with the specified text
     * and <code>Icon</code>.
     *
     * <p>
     *  创建具有指定文本和<code>图标</code>的单选按钮菜单项。
     * 
     * 
     * @param text the text of the <code>JRadioButtonMenuItem</code>
     * @param icon the icon to display on the <code>JRadioButtonMenuItem</code>
     */
    public JRadioButtonMenuItem(String text, Icon icon) {
        this(text, icon, false);
    }

    /**
     * Creates a radio button menu item with the specified text
     * and selection state.
     *
     * <p>
     *  创建具有指定文本和选择状态的单选按钮菜单项。
     * 
     * 
     * @param text the text of the <code>CheckBoxMenuItem</code>
     * @param selected the selected state of the <code>CheckBoxMenuItem</code>
     */
    public JRadioButtonMenuItem(String text, boolean selected) {
        this(text);
        setSelected(selected);
    }

    /**
     * Creates a radio button menu item with the specified image
     * and selection state, but no text.
     *
     * <p>
     *  创建具有指定图像和选择状态,但没有文本的单选按钮菜单项。
     * 
     * 
     * @param icon  the image that the button should display
     * @param selected  if true, the button is initially selected;
     *                  otherwise, the button is initially unselected
     */
    public JRadioButtonMenuItem(Icon icon, boolean selected) {
        this(null, icon, selected);
    }

    /**
     * Creates a radio button menu item that has the specified
     * text, image, and selection state.  All other constructors
     * defer to this one.
     *
     * <p>
     *  创建具有指定文本,图像和选择状态的单选按钮菜单项。所有其他构造函数都遵循这个。
     * 
     * 
     * @param text  the string displayed on the radio button
     * @param icon  the image that the button should display
     */
    public JRadioButtonMenuItem(String text, Icon icon, boolean selected) {
        super(text, icon);
        setModel(new JToggleButton.ToggleButtonModel());
        setSelected(selected);
        setFocusable(false);
    }

    /**
     * Returns the name of the L&amp;F class that renders this component.
     *
     * <p>
     *  返回呈现此组件的L&amp; F类的名称。
     * 
     * 
     * @return the string "RadioButtonMenuItemUI"
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     */
    public String getUIClassID() {
        return uiClassID;
    }

    /**
     * See <code>readObject</code> and <code>writeObject</code> in
     * <code>JComponent</code> for more
     * information about serialization in Swing.
     * <p>
     *  有关Swing中序列化的更多信息,请参阅<code> readComponent </code>中的<code> readObject </code>和<code> writeObject </code>
     * 。
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
     * Returns a string representation of this
     * <code>JRadioButtonMenuItem</code>.  This method
     * is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     *
     * <p>
     * 返回此<code> JRadioButtonMenuItem </code>的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return  a string representation of this
     *          <code>JRadioButtonMenuItem</code>
     */
    protected String paramString() {
        return super.paramString();
    }

    /**
     * Overriden to return true, JRadioButtonMenuItem supports
     * the selected state.
     * <p>
     *  覆盖返回true,JRadioButtonMenuItem支持所选状态。
     * 
     */
    boolean shouldUpdateSelectedStateFromAction() {
        return true;
    }

/////////////////
// Accessibility support
////////////////

    /**
     * Gets the AccessibleContext associated with this JRadioButtonMenuItem.
     * For JRadioButtonMenuItems, the AccessibleContext takes the form of an
     * AccessibleJRadioButtonMenuItem.
     * A new AccessibleJRadioButtonMenuItem instance is created if necessary.
     *
     * <p>
     *  获取与此JRadioButtonMenuItem关联的AccessibleContext。
     * 对于JRadioButtonMenuItems,AccessibleContext采用AccessibleJRadioButtonMenuItem的形式。
     * 如果需要,将创建一个新的AccessibleJRadioButtonMenuItem实例。
     * 
     * 
     * @return an AccessibleJRadioButtonMenuItem that serves as the
     *         AccessibleContext of this JRadioButtonMenuItem
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJRadioButtonMenuItem();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>JRadioButtonMenuItem</code> class.  It provides an
     * implementation of the Java Accessibility API appropriate to
     * <code>JRadioButtonMenuItem</code> user-interface elements.
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
     *  此类实现<code> JRadioButtonMenuItem </code>类的辅助功能支持。
     * 它提供了适用于<code> JRadioButtonMenuItem </code>用户界面元素的Java可访问性API的实现。
     * <p>
     *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     */
    protected class AccessibleJRadioButtonMenuItem extends AccessibleJMenuItem {
        /**
         * Get the role of this object.
         *
         * <p>
         * 
         * 
         * @return an instance of AccessibleRole describing the role of the
         * object
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.RADIO_BUTTON;
        }
    } // inner class AccessibleJRadioButtonMenuItem
}
