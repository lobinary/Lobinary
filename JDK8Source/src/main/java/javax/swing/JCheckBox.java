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

import javax.swing.plaf.*;
import javax.accessibility.*;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;


/**
 * An implementation of a check box -- an item that can be selected or
 * deselected, and which displays its state to the user.
 * By convention, any number of check boxes in a group can be selected.
 * See <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/button.html">How to Use Buttons, Check Boxes, and Radio Buttons</a>
 * in <em>The Java Tutorial</em>
 * for examples and information on using check boxes.
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
 * <p>
 *  复选框的实现 - 可以选择或取消选择的项,并向用户显示其状态。按照惯例,可以选择组中的任意数量的复选框。
 * 请参阅<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/button.html">如何在<em>中使用按钮,复选框和
 * 单选按钮</a> Java教程</em>,了解有关使用复选框的示例和信息。
 *  复选框的实现 - 可以选择或取消选择的项,并向用户显示其状态。按照惯例,可以选择组中的任意数量的复选框。
 * <p>
 *  可以通过<code> <a href="Action.html">操作</a> </code>配置按钮,并在某种程度上控制。
 * 除了直接配置按钮外,使用<code> Action </code>按钮还有许多好处。
 * 有关详情,请参阅<a href="Action.html#buttonActions"> Swing组件支持<code>操作</code> </a>,您可以在<a href ="https：// docs中找到更多信息.oracle.com / javase / tutorial / uiswing / misc / action.html">
 * 如何使用操作</a>,<em> Java教程</em>中的一节。
 * 除了直接配置按钮外,使用<code> Action </code>按钮还有许多好处。
 * <p>
 *  <strong>警告：</strong> Swing不是线程安全的。有关详情,请参阅<a href="package-summary.html#threading"> Swing的线程策略</a>。
 * <p>
 * <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @see JRadioButton
 *
 * @beaninfo
 *   attribute: isContainer false
 * description: A component which can be selected or deselected.
 *
 * @author Jeff Dinkins
 */
public class JCheckBox extends JToggleButton implements Accessible {

    /** Identifies a change to the flat property. */
    public static final String BORDER_PAINTED_FLAT_CHANGED_PROPERTY = "borderPaintedFlat";

    private boolean flat = false;

    /**
    /* <p>
    /* 
     * @see #getUIClassID
     * @see #readObject
     */
    private static final String uiClassID = "CheckBoxUI";


    /**
     * Creates an initially unselected check box button with no text, no icon.
     * <p>
     *  创建一个没有文本,没有图标的初始未选择的复选框按钮。
     * 
     */
    public JCheckBox () {
        this(null, null, false);
    }

    /**
     * Creates an initially unselected check box with an icon.
     *
     * <p>
     *  创建带有图标的初始未选中复选框。
     * 
     * 
     * @param icon  the Icon image to display
     */
    public JCheckBox(Icon icon) {
        this(null, icon, false);
    }

    /**
     * Creates a check box with an icon and specifies whether
     * or not it is initially selected.
     *
     * <p>
     *  创建带有图标的复选框,并指定是否最初选择它。
     * 
     * 
     * @param icon  the Icon image to display
     * @param selected a boolean value indicating the initial selection
     *        state. If <code>true</code> the check box is selected
     */
    public JCheckBox(Icon icon, boolean selected) {
        this(null, icon, selected);
    }

    /**
     * Creates an initially unselected check box with text.
     *
     * <p>
     *  创建带有文本的初始未选中复选框。
     * 
     * 
     * @param text the text of the check box.
     */
    public JCheckBox (String text) {
        this(text, null, false);
    }

    /**
     * Creates a check box where properties are taken from the
     * Action supplied.
     *
     * <p>
     *  创建一个复选框,其中从提供的操作中获取属性。
     * 
     * 
     * @since 1.3
     */
    public JCheckBox(Action a) {
        this();
        setAction(a);
    }


    /**
     * Creates a check box with text and specifies whether
     * or not it is initially selected.
     *
     * <p>
     *  创建一个带文本的复选框,并指定是否最初选择它。
     * 
     * 
     * @param text the text of the check box.
     * @param selected a boolean value indicating the initial selection
     *        state. If <code>true</code> the check box is selected
     */
    public JCheckBox (String text, boolean selected) {
        this(text, null, selected);
    }

    /**
     * Creates an initially unselected check box with
     * the specified text and icon.
     *
     * <p>
     *  创建具有指定文本和图标的初始未选中复选框。
     * 
     * 
     * @param text the text of the check box.
     * @param icon  the Icon image to display
     */
    public JCheckBox(String text, Icon icon) {
        this(text, icon, false);
    }

    /**
     * Creates a check box with text and icon,
     * and specifies whether or not it is initially selected.
     *
     * <p>
     *  创建一个带有文本和图标的复选框,并指定是否最初选择它。
     * 
     * 
     * @param text the text of the check box.
     * @param icon  the Icon image to display
     * @param selected a boolean value indicating the initial selection
     *        state. If <code>true</code> the check box is selected
     */
    public JCheckBox (String text, Icon icon, boolean selected) {
        super(text, icon, selected);
        setUIProperty("borderPainted", Boolean.FALSE);
        setHorizontalAlignment(LEADING);
    }

    /**
     * Sets the <code>borderPaintedFlat</code> property,
     * which gives a hint to the look and feel as to the
     * appearance of the check box border.
     * This is usually set to <code>true</code> when a
     * <code>JCheckBox</code> instance is used as a
     * renderer in a component such as a <code>JTable</code> or
     * <code>JTree</code>.  The default value for the
     * <code>borderPaintedFlat</code> property is <code>false</code>.
     * This method fires a property changed event.
     * Some look and feels might not implement flat borders;
     * they will ignore this property.
     *
     * <p>
     * 设置<code> borderPaintedFlat </code>属性,它提供了对复选框边框外观的感觉。
     * 当<code> JCheckBox </code>实例用作组件中的渲染器,如<code> JTable </code>或<code> JTree </code>时,通常设置为<code> true </code>
     * 代码>。
     * 设置<code> borderPaintedFlat </code>属性,它提供了对复选框边框外观的感觉。
     *  <code> borderPaintedFlat </code>属性的默认值为<code> false </code>。此方法触发属性更改事件。一些外观和感觉可能不实现平面边界;他们将忽略此属性。
     * 
     * 
     * @param b <code>true</code> requests that the border be painted flat;
     *          <code>false</code> requests normal borders
     * @see #isBorderPaintedFlat
     * @beaninfo
     *        bound: true
     *    attribute: visualUpdate true
     *  description: Whether the border is painted flat.
     * @since 1.3
     */
    public void setBorderPaintedFlat(boolean b) {
        boolean oldValue = flat;
        flat = b;
        firePropertyChange(BORDER_PAINTED_FLAT_CHANGED_PROPERTY, oldValue, flat);
        if (b != oldValue) {
            revalidate();
            repaint();
        }
    }

    /**
     * Gets the value of the <code>borderPaintedFlat</code> property.
     *
     * <p>
     *  获取<code> borderPaintedFlat </code>属性的值。
     * 
     * 
     * @return the value of the <code>borderPaintedFlat</code> property
     * @see #setBorderPaintedFlat
     * @since 1.3
     */
    public boolean isBorderPaintedFlat() {
        return flat;
    }

    /**
     * Resets the UI property to a value from the current look and feel.
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
     * @return the string "CheckBoxUI"
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     * @beaninfo
     *        expert: true
     *   description: A string that specifies the name of the L&amp;F class
     */
    public String getUIClassID() {
        return uiClassID;
    }


    /**
     * The icon for checkboxs comes from the look and feel,
     * not the Action; this is overriden to do nothing.
     * <p>
     *  复选框的图标来自外观和感觉,而不是Action;这是overriden什么也不做。
     * 
     */
    void setIconFromAction(Action a) {
    }

     /*
      * See readObject and writeObject in JComponent for more
      * information about serialization in Swing.
      * <p>
      *  有关Swing中序列化的更多信息,请参阅JComponent中的readObject和writeObject。
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
     * See JComponent.readObject() for information about serialization
     * in Swing.
     * <p>
     *  有关Swing中序列化的信息,请参阅JComponent.readObject()。
     * 
     */
    private void readObject(ObjectInputStream s)
        throws IOException, ClassNotFoundException
    {
        s.defaultReadObject();
        if (getUIClassID().equals(uiClassID)) {
            updateUI();
        }
    }


    /**
     * Returns a string representation of this JCheckBox. This method
     * is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     * specific new aspects of the JFC components.
     *
     * <p>
     *  返回此JCheckBox的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。返回的字符串可能为空,但可能不是<code> null </code>。
     *  JFC组件的具体新方面。
     * 
     * 
     * @return  a string representation of this JCheckBox.
     */
    protected String paramString() {
        return super.paramString();
    }

/////////////////
// Accessibility support
////////////////

    /**
     * Gets the AccessibleContext associated with this JCheckBox.
     * For JCheckBoxes, the AccessibleContext takes the form of an
     * AccessibleJCheckBox.
     * A new AccessibleJCheckBox instance is created if necessary.
     *
     * <p>
     * 获取与此JCheckBox相关联的AccessibleContext。对于JCheckBoxes,AccessibleContext采用AccessibleJCheckBox的形式。
     * 如果需要,将创建一个新的AccessibleJCheckBox实例。
     * 
     * 
     * @return an AccessibleJCheckBox that serves as the
     *         AccessibleContext of this JCheckBox
     * @beaninfo
     *       expert: true
     *  description: The AccessibleContext associated with this CheckBox.
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJCheckBox();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>JCheckBox</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to check box user-interface
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
     *  此类实现<code> JCheckBox </code>类的辅助功能支持。它提供了适用于复选框用户界面元素的Java辅助功能API的实现。
     * <p>
     *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     */
    protected class AccessibleJCheckBox extends AccessibleJToggleButton {

        /**
         * Get the role of this object.
         *
         * <p>
         * 
         * 
         * @return an instance of AccessibleRole describing the role of the object
         * @see AccessibleRole
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.CHECK_BOX;
        }

    } // inner class AccessibleJCheckBox
}
