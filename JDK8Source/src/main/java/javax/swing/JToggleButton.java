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

import javax.swing.event.*;
import javax.swing.plaf.*;
import javax.accessibility.*;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;


/**
 * An implementation of a two-state button.
 * The <code>JRadioButton</code> and <code>JCheckBox</code> classes
 * are subclasses of this class.
 * For information on using them see
 * <a
 href="https://docs.oracle.com/javase/tutorial/uiswing/components/button.html">How to Use Buttons, Check Boxes, and Radio Buttons</a>,
 * a section in <em>The Java Tutorial</em>.
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
 * @beaninfo
 *   attribute: isContainer false
 * description: An implementation of a two-state button.
 *
 * <p>
 *  双状态按钮的实现。 <code> JRadioButton </code>和<code> JCheckBox </code>类是此类的子类。
 * 有关使用方法的信息,请参阅<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/button.html">如何使用按钮,
 * 复选框和单选按钮</a>, Java教程</em>中的一个部分。
 *  双状态按钮的实现。 <code> JRadioButton </code>和<code> JCheckBox </code>类是此类的子类。
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
 *  @beaninfo属性：isContainer false描述：两状态按钮的实现。
 * 
 * 
 * @see JRadioButton
 * @see JCheckBox
 * @author Jeff Dinkins
 */
public class JToggleButton extends AbstractButton implements Accessible {

    /**
    /* <p>
    /* 
     * @see #getUIClassID
     * @see #readObject
     */
    private static final String uiClassID = "ToggleButtonUI";

    /**
     * Creates an initially unselected toggle button
     * without setting the text or image.
     * <p>
     *  创建初始未选择的切换按钮,而不设置文本或图像。
     * 
     */
    public JToggleButton () {
        this(null, null, false);
    }

    /**
     * Creates an initially unselected toggle button
     * with the specified image but no text.
     *
     * <p>
     *  创建具有指定图像但没有文本的初始未选择的切换按钮。
     * 
     * 
     * @param icon  the image that the button should display
     */
    public JToggleButton(Icon icon) {
        this(null, icon, false);
    }

    /**
     * Creates a toggle button with the specified image
     * and selection state, but no text.
     *
     * <p>
     *  创建具有指定图像和选择状态,但没有文本的切换按钮。
     * 
     * 
     * @param icon  the image that the button should display
     * @param selected  if true, the button is initially selected;
     *                  otherwise, the button is initially unselected
     */
    public JToggleButton(Icon icon, boolean selected) {
        this(null, icon, selected);
    }

    /**
     * Creates an unselected toggle button with the specified text.
     *
     * <p>
     *  创建具有指定文本的未选择的切换按钮。
     * 
     * 
     * @param text  the string displayed on the toggle button
     */
    public JToggleButton (String text) {
        this(text, null, false);
    }

    /**
     * Creates a toggle button with the specified text
     * and selection state.
     *
     * <p>
     *  创建具有指定文本和选择状态的切换按钮。
     * 
     * 
     * @param text  the string displayed on the toggle button
     * @param selected  if true, the button is initially selected;
     *                  otherwise, the button is initially unselected
     */
    public JToggleButton (String text, boolean selected) {
        this(text, null, selected);
    }

    /**
     * Creates a toggle button where properties are taken from the
     * Action supplied.
     *
     * <p>
     *  创建切换按钮,其中从提供的操作中获取属性。
     * 
     * 
     * @since 1.3
     */
    public JToggleButton(Action a) {
        this();
        setAction(a);
    }

    /**
     * Creates a toggle button that has the specified text and image,
     * and that is initially unselected.
     *
     * <p>
     *  创建具有指定文本和图像的切换按钮,该按钮最初未选中。
     * 
     * 
     * @param text the string displayed on the button
     * @param icon  the image that the button should display
     */
    public JToggleButton(String text, Icon icon) {
        this(text, icon, false);
    }

    /**
     * Creates a toggle button with the specified text, image, and
     * selection state.
     *
     * <p>
     *  创建具有指定文本,图像和选择状态的切换按钮。
     * 
     * 
     * @param text the text of the toggle button
     * @param icon  the image that the button should display
     * @param selected  if true, the button is initially selected;
     *                  otherwise, the button is initially unselected
     */
    public JToggleButton (String text, Icon icon, boolean selected) {
        // Create the model
        setModel(new ToggleButtonModel());

        model.setSelected(selected);

        // initialize
        init(text, icon);
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
     * Returns a string that specifies the name of the l&amp;f class
     * that renders this component.
     *
     * <p>
     *  返回一个字符串,指定呈现此组件的l&amp; f类的名称。
     * 
     * 
     * @return String "ToggleButtonUI"
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     * @beaninfo
     *  description: A string that specifies the name of the L&amp;F class
     */
    public String getUIClassID() {
        return uiClassID;
    }


    /**
     * Overriden to return true, JToggleButton supports
     * the selected state.
     * <p>
     *  覆盖返回true,JToggleButton支持所选状态。
     * 
     */
    boolean shouldUpdateSelectedStateFromAction() {
        return true;
    }

    // *********************************************************************

    /**
     * The ToggleButton model
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
     *  ToggleButton模型
     * <p>
     * <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     * 
     */
    public static class ToggleButtonModel extends DefaultButtonModel {

        /**
         * Creates a new ToggleButton Model
         * <p>
         *  创建一个新的ToggleButton模型
         * 
         */
        public ToggleButtonModel () {
        }

        /**
         * Checks if the button is selected.
         * <p>
         *  检查是否选择了按钮。
         * 
         */
        public boolean isSelected() {
//              if(getGroup() != null) {
//                  return getGroup().isSelected(this);
//              } else {
                return (stateMask & SELECTED) != 0;
//              }
        }


        /**
         * Sets the selected state of the button.
         * <p>
         *  设置按钮的选定状态。
         * 
         * 
         * @param b true selects the toggle button,
         *          false deselects the toggle button.
         */
        public void setSelected(boolean b) {
            ButtonGroup group = getGroup();
            if (group != null) {
                // use the group model instead
                group.setSelected(this, b);
                b = group.isSelected(this);
            }

            if (isSelected() == b) {
                return;
            }

            if (b) {
                stateMask |= SELECTED;
            } else {
                stateMask &= ~SELECTED;
            }

            // Send ChangeEvent
            fireStateChanged();

            // Send ItemEvent
            fireItemStateChanged(
                    new ItemEvent(this,
                                  ItemEvent.ITEM_STATE_CHANGED,
                                  this,
                                  this.isSelected() ?  ItemEvent.SELECTED : ItemEvent.DESELECTED));

        }

        /**
         * Sets the pressed state of the toggle button.
         * <p>
         *  设置切换按钮的按下状态。
         * 
         */
        public void setPressed(boolean b) {
            if ((isPressed() == b) || !isEnabled()) {
                return;
            }

            if (b == false && isArmed()) {
                setSelected(!this.isSelected());
            }

            if (b) {
                stateMask |= PRESSED;
            } else {
                stateMask &= ~PRESSED;
            }

            fireStateChanged();

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

        }
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
     * Returns a string representation of this JToggleButton. This method
     * is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     *
     * <p>
     *  返回此JToggleButton的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return  a string representation of this JToggleButton.
     */
    protected String paramString() {
        return super.paramString();
    }


/////////////////
// Accessibility support
////////////////

    /**
     * Gets the AccessibleContext associated with this JToggleButton.
     * For toggle buttons, the AccessibleContext takes the form of an
     * AccessibleJToggleButton.
     * A new AccessibleJToggleButton instance is created if necessary.
     *
     * <p>
     *  获取与此JToggleButton相关联的AccessibleContext。对于切换按钮,AccessibleContext采用AccessibleJToggleButton的形式。
     * 如果需要,将创建一个新的AccessibleJToggleButton实例。
     * 
     * 
     * @return an AccessibleJToggleButton that serves as the
     *         AccessibleContext of this JToggleButton
     * @beaninfo
     *       expert: true
     *  description: The AccessibleContext associated with this ToggleButton.
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJToggleButton();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>JToggleButton</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to toggle button user-interface
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
     *  此类实现<code> JToggleButton </code>类的辅助功能支持。它提供了适用于切换按钮用户界面元素的Java辅助功能API的实现。
     * <p>
     * <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     * 
     */
    protected class AccessibleJToggleButton extends AccessibleAbstractButton
            implements ItemListener {

        public AccessibleJToggleButton() {
            super();
            JToggleButton.this.addItemListener(this);
        }

        /**
         * Fire accessible property change events when the state of the
         * toggle button changes.
         * <p>
         *  当切换按钮的状态更改时,触发可触及的属性更改事件。
         * 
         */
        public void itemStateChanged(ItemEvent e) {
            JToggleButton tb = (JToggleButton) e.getSource();
            if (JToggleButton.this.accessibleContext != null) {
                if (tb.isSelected()) {
                    JToggleButton.this.accessibleContext.firePropertyChange(
                            AccessibleContext.ACCESSIBLE_STATE_PROPERTY,
                            null, AccessibleState.CHECKED);
                } else {
                    JToggleButton.this.accessibleContext.firePropertyChange(
                            AccessibleContext.ACCESSIBLE_STATE_PROPERTY,
                            AccessibleState.CHECKED, null);
                }
            }
        }

        /**
         * Get the role of this object.
         *
         * <p>
         *  获取此对象的作用。
         * 
         * @return an instance of AccessibleRole describing the role of the
         * object
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.TOGGLE_BUTTON;
        }
    } // inner class AccessibleJToggleButton
}
