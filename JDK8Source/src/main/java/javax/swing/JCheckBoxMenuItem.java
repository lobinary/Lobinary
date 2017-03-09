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
 * A menu item that can be selected or deselected. If selected, the menu
 * item typically appears with a checkmark next to it. If unselected or
 * deselected, the menu item appears without a checkmark. Like a regular
 * menu item, a check box menu item can have either text or a graphic
 * icon associated with it, or both.
 * <p>
 * Either <code>isSelected</code>/<code>setSelected</code> or
 * <code>getState</code>/<code>setState</code> can be used
 * to determine/specify the menu item's selection state. The
 * preferred methods are <code>isSelected</code> and
 * <code>setSelected</code>, which work for all menus and buttons.
 * The <code>getState</code> and <code>setState</code> methods exist for
 * compatibility with other component sets.
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
 * For further information and examples of using check box menu items,
 * see <a
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
 * description: A menu item which can be selected or deselected.
 *
 * <p>
 *  可以选择或取消选择的菜单项。如果选择,菜单项通常在其旁边带有复选标记。如果未选择或取消选择,菜单项将显示,不带复选标记。与常规菜单项一样,复选框菜单项可以具有与其相关联的文本或图形图标,或两者。
 * <p>
 *  <code> isSelected </code> / <code> setSelected </code>或<code> getState </code> / <code> setState </code>
 * 可用于确定/指定菜单项的选择状态。
 * 首选方法是<code> isSelected </code>和<code> setSelected </code>,它们适用于所有菜单和按钮。
 * 存在<code> getState </code>和<code> setState </code>方法与其他组件集的兼容性。
 * <p>
 *  菜单项可以通过<code> <a href="Action.html">操作</a> </code>进行配置,并在某种程度上受到控制。
 * 对菜单项使用<code> Action </code>除了直接配置菜单项之外还有许多好处。
 * 有关详情,请参阅<a href="Action.html#buttonActions"> Swing组件支持<code>操作</code> </a>,您可以在<a href ="https：// docs中找到更多信息.oracle.com / javase / tutorial / uiswing / misc / action.html">
 * 如何使用操作</a>,<em> Java教程</em>中的一节。
 * 对菜单项使用<code> Action </code>除了直接配置菜单项之外还有许多好处。
 * <p>
 * 有关使用复选框菜单项的详细信息和示例,请参见<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/menu.html">
 * 如何使用菜单</a>, Java教程</em>中的一个部分。
 * <p>
 *  <strong>警告：</strong> Swing不是线程安全的。有关详情,请参阅<a href="package-summary.html#threading"> Swing的线程策略</a>。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 *  @beaninfo属性：isContainer false description：可以选择或取消选择的菜单项。
 * 
 * 
 * @author Georges Saab
 * @author David Karlton
 */
public class JCheckBoxMenuItem extends JMenuItem implements SwingConstants,
        Accessible
{
    /**
    /* <p>
    /* 
     * @see #getUIClassID
     * @see #readObject
     */
    private static final String uiClassID = "CheckBoxMenuItemUI";

    /**
     * Creates an initially unselected check box menu item with no set text or icon.
     * <p>
     *  创建未设置文本或图标的初始未选中的复选框菜单项。
     * 
     */
    public JCheckBoxMenuItem() {
        this(null, null, false);
    }

    /**
     * Creates an initially unselected check box menu item with an icon.
     *
     * <p>
     *  创建带有图标的初始未选中的复选框菜单项。
     * 
     * 
     * @param icon the icon of the CheckBoxMenuItem.
     */
    public JCheckBoxMenuItem(Icon icon) {
        this(null, icon, false);
    }

    /**
     * Creates an initially unselected check box menu item with text.
     *
     * <p>
     *  创建带有文本的初始未选中的复选框菜单项。
     * 
     * 
     * @param text the text of the CheckBoxMenuItem
     */
    public JCheckBoxMenuItem(String text) {
        this(text, null, false);
    }

    /**
     * Creates a menu item whose properties are taken from the
     * Action supplied.
     *
     * <p>
     *  创建其属性取自提供的操作的菜单项。
     * 
     * 
     * @since 1.3
     */
    public JCheckBoxMenuItem(Action a) {
        this();
        setAction(a);
    }

    /**
     * Creates an initially unselected check box menu item with the specified text and icon.
     *
     * <p>
     *  创建具有指定文本和图标的初始未选中的复选框菜单项。
     * 
     * 
     * @param text the text of the CheckBoxMenuItem
     * @param icon the icon of the CheckBoxMenuItem
     */
    public JCheckBoxMenuItem(String text, Icon icon) {
        this(text, icon, false);
    }

    /**
     * Creates a check box menu item with the specified text and selection state.
     *
     * <p>
     *  创建具有指定文本和选择状态的复选框菜单项。
     * 
     * 
     * @param text the text of the check box menu item.
     * @param b the selected state of the check box menu item
     */
    public JCheckBoxMenuItem(String text, boolean b) {
        this(text, null, b);
    }

    /**
     * Creates a check box menu item with the specified text, icon, and selection state.
     *
     * <p>
     *  创建具有指定文本,图标和选择状态的复选框菜单项。
     * 
     * 
     * @param text the text of the check box menu item
     * @param icon the icon of the check box menu item
     * @param b the selected state of the check box menu item
     */
    public JCheckBoxMenuItem(String text, Icon icon, boolean b) {
        super(text, icon);
        setModel(new JToggleButton.ToggleButtonModel());
        setSelected(b);
        setFocusable(false);
    }

    /**
     * Returns the name of the L&amp;F class
     * that renders this component.
     *
     * <p>
     *  返回呈现此组件的L&amp; F类的名称。
     * 
     * 
     * @return "CheckBoxMenuItemUI"
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     */
    public String getUIClassID() {
        return uiClassID;
    }

     /**
      * Returns the selected-state of the item. This method
      * exists for AWT compatibility only.  New code should
      * use isSelected() instead.
      *
      * <p>
      * 返回项目的选定状态。此方法仅适用于AWT兼容性。新代码应该使用isSelected()。
      * 
      * 
      * @return true  if the item is selected
      */
    public boolean getState() {
        return isSelected();
    }

    /**
     * Sets the selected-state of the item. This method
     * exists for AWT compatibility only.  New code should
     * use setSelected() instead.
     *
     * <p>
     *  设置项目的选定状态。此方法仅适用于AWT兼容性。新代码应该使用setSelected()。
     * 
     * 
     * @param b  a boolean value indicating the item's
     *           selected-state, where true=selected
     * @beaninfo
     * description: The selection state of the check box menu item
     *      hidden: true
     */
    public synchronized void setState(boolean b) {
        setSelected(b);
    }


    /**
     * Returns an array (length 1) containing the check box menu item
     * label or null if the check box is not selected.
     *
     * <p>
     *  返回包含复选框菜单项标签的数组(长度1),如果未选中该复选框,则返回null。
     * 
     * 
     * @return an array containing one Object -- the text of the menu item
     *         -- if the item is selected; otherwise null
     */
    public Object[] getSelectedObjects() {
        if (isSelected() == false)
            return null;
        Object[] selectedObjects = new Object[1];
        selectedObjects[0] = getText();
        return selectedObjects;
    }

    /**
     * See readObject() and writeObject() in JComponent for more
     * information about serialization in Swing.
     * <p>
     *  有关Swing中序列化的更多信息,请参阅JComponent中的readObject()和writeObject()。
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
     * Returns a string representation of this JCheckBoxMenuItem. This method
     * is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     *
     * <p>
     *  返回此JCheckBoxMenuItem的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return  a string representation of this JCheckBoxMenuItem.
     */
    protected String paramString() {
        return super.paramString();
    }

    /**
     * Overriden to return true, JCheckBoxMenuItem supports
     * the selected state.
     * <p>
     *  覆盖返回true,JCheckBoxMenuItem支持所选状态。
     * 
     */
    boolean shouldUpdateSelectedStateFromAction() {
        return true;
    }

/////////////////
// Accessibility support
////////////////

    /**
     * Gets the AccessibleContext associated with this JCheckBoxMenuItem.
     * For JCheckBoxMenuItems, the AccessibleContext takes the form of an
     * AccessibleJCheckBoxMenuItem.
     * A new AccessibleJCheckBoxMenuItem instance is created if necessary.
     *
     * <p>
     *  获取与此JCheckBoxMenuItem关联的AccessibleContext。
     * 对于JCheckBoxMenuItems,AccessibleContext采用AccessibleJCheckBoxMenuItem的形式。
     * 如果需要,将创建一个新的AccessibleJCheckBoxMenuItem实例。
     * 
     * 
     * @return an AccessibleJCheckBoxMenuItem that serves as the
     *         AccessibleContext of this AccessibleJCheckBoxMenuItem
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJCheckBoxMenuItem();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>JCheckBoxMenuItem</code> class.  It provides an implementation
     * of the Java Accessibility API appropriate to checkbox menu item
     * user-interface elements.
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
     *  此类实现<code> JCheckBoxMenuItem </code>类的辅助功能支持。它提供了适用于复选框菜单项用户界面元素的Java辅助功能API的实现。
     * <p>
     * <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     */
    protected class AccessibleJCheckBoxMenuItem extends AccessibleJMenuItem {
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
            return AccessibleRole.CHECK_BOX;
        }
    } // inner class AccessibleJCheckBoxMenuItem
}
