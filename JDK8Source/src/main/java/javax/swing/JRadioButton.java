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
 * An implementation of a radio button -- an item that can be selected or
 * deselected, and which displays its state to the user.
 * Used with a {@link ButtonGroup} object to create a group of buttons
 * in which only one button at a time can be selected. (Create a ButtonGroup
 * object and use its <code>add</code> method to include the JRadioButton objects
 * in the group.)
 * <blockquote>
 * <strong>Note:</strong>
 * The ButtonGroup object is a logical grouping -- not a physical grouping.
 * To create a button panel, you should still create a {@link JPanel} or similar
 * container-object and add a {@link javax.swing.border.Border} to it to set it off from surrounding
 * components.
 * </blockquote>
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
 * for further documentation.
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
 * description: A component which can display it's state as selected or deselected.
 *
 * <p>
 *  单选按钮的实现 - 可以选择或取消选择的项目,并向用户显示其状态。与{@link ButtonGroup}对象一起使用以创建一组按钮,其中每次只能选择一个按钮。
 *  (创建ButtonGroup对象并使用<code> add </code>方法将JRadioButton对象包括在组中)。
 * <blockquote>
 *  <strong>注意</strong>：ButtonGroup对象是一个逻辑分组,而不是物理分组。
 * 要创建按钮面板,您应该仍然创建一个{@link JPanel}或类似的容器对象,并向其添加一个{@link javax.swing.border.Border}以将其从周围的组件中移除。
 * </blockquote>
 * <p>
 *  可以通过<code> <a href="Action.html">操作</a> </code>配置按钮,并在某种程度上控制。
 * 除了直接配置按钮外,使用<code> Action </code>按钮还有许多好处。
 * 有关详情,请参阅<a href="Action.html#buttonActions"> Swing组件支持<code>操作</code> </a>,您可以在<a href ="https：// docs中找到更多信息.oracle.com / javase / tutorial / uiswing / misc / action.html">
 * 如何使用操作</a>,<em> Java教程</em>中的一节。
 * 除了直接配置按钮外,使用<code> Action </code>按钮还有许多好处。
 * <p>
 *  请参阅<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/button.html">如何在<em>中使用按钮,复选框
 * 和单选按钮</a> Java教程</em>以获取更多文档。
 * <p>
 * <strong>警告：</strong> Swing不是线程安全的。有关详情,请参阅<a href="package-summary.html#threading"> Swing的线程策略</a>。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 *  @beaninfo属性：isContainer false description：可以显示其状态为选中或取消选择的组件。
 * 
 * 
 * @see ButtonGroup
 * @see JCheckBox
 * @author Jeff Dinkins
 */
public class JRadioButton extends JToggleButton implements Accessible {

    /**
    /* <p>
    /* 
     * @see #getUIClassID
     * @see #readObject
     */
    private static final String uiClassID = "RadioButtonUI";


    /**
     * Creates an initially unselected radio button
     * with no set text.
     * <p>
     *  创建未设置文本的初始未选择单选按钮。
     * 
     */
    public JRadioButton () {
        this(null, null, false);
    }

    /**
     * Creates an initially unselected radio button
     * with the specified image but no text.
     *
     * <p>
     *  创建具有指定图像但没有文本的初始未选定单选按钮。
     * 
     * 
     * @param icon  the image that the button should display
     */
    public JRadioButton(Icon icon) {
        this(null, icon, false);
    }

    /**
     * Creates a radiobutton where properties are taken from the
     * Action supplied.
     *
     * <p>
     *  创建一个单选按钮,其中的属性取自所提供的操作。
     * 
     * 
     * @since 1.3
     */
    public JRadioButton(Action a) {
        this();
        setAction(a);
    }

    /**
     * Creates a radio button with the specified image
     * and selection state, but no text.
     *
     * <p>
     *  创建具有指定图像和选择状态,但没有文本的单选按钮。
     * 
     * 
     * @param icon  the image that the button should display
     * @param selected  if true, the button is initially selected;
     *                  otherwise, the button is initially unselected
     */
    public JRadioButton(Icon icon, boolean selected) {
        this(null, icon, selected);
    }

    /**
     * Creates an unselected radio button with the specified text.
     *
     * <p>
     *  创建具有指定文本的未选定单选按钮。
     * 
     * 
     * @param text  the string displayed on the radio button
     */
    public JRadioButton (String text) {
        this(text, null, false);
    }

    /**
     * Creates a radio button with the specified text
     * and selection state.
     *
     * <p>
     *  创建具有指定文本和选择状态的单选按钮。
     * 
     * 
     * @param text  the string displayed on the radio button
     * @param selected  if true, the button is initially selected;
     *                  otherwise, the button is initially unselected
     */
    public JRadioButton (String text, boolean selected) {
        this(text, null, selected);
    }

    /**
     * Creates a radio button that has the specified text and image,
     * and that is initially unselected.
     *
     * <p>
     *  创建具有指定文本和图像且最初未选择的单选按钮。
     * 
     * 
     * @param text  the string displayed on the radio button
     * @param icon  the image that the button should display
     */
    public JRadioButton(String text, Icon icon) {
        this(text, icon, false);
    }

    /**
     * Creates a radio button that has the specified text, image,
     * and selection state.
     *
     * <p>
     *  创建具有指定文本,图像和选择状态的单选按钮。
     * 
     * 
     * @param text  the string displayed on the radio button
     * @param icon  the image that the button should display
     */
    public JRadioButton (String text, Icon icon, boolean selected) {
        super(text, icon, selected);
        setBorderPainted(false);
        setHorizontalAlignment(LEADING);
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
     * Returns the name of the L&amp;F class
     * that renders this component.
     *
     * <p>
     *  返回呈现此组件的L&amp; F类的名称。
     * 
     * 
     * @return String "RadioButtonUI"
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
     * The icon for radio buttons comes from the look and feel,
     * not the Action.
     * <p>
     * 单选按钮的图标来自外观和感觉,而不是动作。
     * 
     */
    void setIconFromAction(Action a) {
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
     * Returns a string representation of this JRadioButton. This method
     * is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     *
     * <p>
     *  返回此JRadioButton的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return  a string representation of this JRadioButton.
     */
    protected String paramString() {
        return super.paramString();
    }


/////////////////
// Accessibility support
////////////////


    /**
     * Gets the AccessibleContext associated with this JRadioButton.
     * For JRadioButtons, the AccessibleContext takes the form of an
     * AccessibleJRadioButton.
     * A new AccessibleJRadioButton instance is created if necessary.
     *
     * <p>
     *  获取与此JRadioButton相关联的AccessibleContext。对于JRadioButton,AccessibleContext采用AccessibleJRadioButton的形式。
     * 如果需要,将创建一个新的AccessibleJRadioButton实例。
     * 
     * 
     * @return an AccessibleJRadioButton that serves as the
     *         AccessibleContext of this JRadioButton
     * @beaninfo
     *       expert: true
     *  description: The AccessibleContext associated with this Button
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJRadioButton();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>JRadioButton</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to radio button
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
     *  此类实现<code> JRadioButton </code>类的辅助功能支持。它提供了适用于单选按钮用户界面元素的Java可访问性API的实现。
     * <p>
     *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     */
    protected class AccessibleJRadioButton extends AccessibleJToggleButton {

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
            return AccessibleRole.RADIO_BUTTON;
        }

    } // inner class AccessibleJRadioButton
}
