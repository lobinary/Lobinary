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

import javax.swing.plaf.*;
import javax.accessibility.*;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;


/**
 * <code>JSeparator</code> provides a general purpose component for
 * implementing divider lines - most commonly used as a divider
 * between menu items that breaks them up into logical groupings.
 * Instead of using <code>JSeparator</code> directly,
 * you can use the <code>JMenu</code> or <code>JPopupMenu</code>
 * <code>addSeparator</code> method to create and add a separator.
 * <code>JSeparator</code>s may also be used elsewhere in a GUI
 * wherever a visual divider is useful.
 *
 * <p>
 *
 * For more information and examples see
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
 *      attribute: isContainer false
 *    description: A divider between menu items.
 *
 * <p>
 *  <code> JSeparator </code>提供了一个用于实现分隔线的通用组件 - 最常用作菜单项之间的分隔符,将其分成逻辑分组。
 * 您可以使用<code> JMenu </code>或<code> JPopupMenu </code> <code> addSeparator </code>方法来创建和添加分隔符,而不是直接使用<code>
 *  JSeparator </code> <code> JSeparator </code>也可以在GUI的其他地方使用,只要可视化分隔符是有用的。
 *  <code> JSeparator </code>提供了一个用于实现分隔线的通用组件 - 最常用作菜单项之间的分隔符,将其分成逻辑分组。
 * 
 * <p>
 * 
 *  有关详情和示例,请参阅<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/menu.html">如何使用菜单</a>
 * ,<em>中的一节。
 *  Java教程。</em>。
 * <p>
 *  <strong>警告：</strong> Swing不是线程安全的。有关详情,请参阅<a href="package-summary.html#threading"> Swing的线程策略</a>。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 *  @beaninfo属性：isContainer false描述：菜单项之间的分隔符。
 * 
 * 
 * @author Georges Saab
 * @author Jeff Shapiro
 */
@SuppressWarnings("serial")
public class JSeparator extends JComponent implements SwingConstants, Accessible
{
    /**
    /* <p>
    /* 
     * @see #getUIClassID
     * @see #readObject
     */
    private static final String uiClassID = "SeparatorUI";

    private int orientation = HORIZONTAL;

    /** Creates a new horizontal separator. */
    public JSeparator()
    {
        this( HORIZONTAL );
    }

    /**
     * Creates a new separator with the specified horizontal or
     * vertical orientation.
     *
     * <p>
     * 以指定的水平或垂直方向创建新的分隔符。
     * 
     * 
     * @param orientation an integer specifying
     *          <code>SwingConstants.HORIZONTAL</code> or
     *          <code>SwingConstants.VERTICAL</code>
     * @exception IllegalArgumentException if <code>orientation</code>
     *          is neither <code>SwingConstants.HORIZONTAL</code> nor
     *          <code>SwingConstants.VERTICAL</code>
     */
    public JSeparator( int orientation )
    {
        checkOrientation( orientation );
        this.orientation = orientation;
        setFocusable(false);
        updateUI();
    }

    /**
     * Returns the L&amp;F object that renders this component.
     *
     * <p>
     *  返回呈现此组件的L&amp; F对象。
     * 
     * 
     * @return the SeparatorUI object that renders this component
     */
    public SeparatorUI getUI() {
        return (SeparatorUI)ui;
    }

    /**
     * Sets the L&amp;F object that renders this component.
     *
     * <p>
     *  设置呈现此组件的L&amp; F对象。
     * 
     * 
     * @param ui  the SeparatorUI L&amp;F object
     * @see UIDefaults#getUI
     * @beaninfo
     *        bound: true
     *       hidden: true
     *    attribute: visualUpdate true
     *  description: The UI object that implements the Component's LookAndFeel.
     */
    public void setUI(SeparatorUI ui) {
        super.setUI(ui);
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
        setUI((SeparatorUI)UIManager.getUI(this));
    }


    /**
     * Returns the name of the L&amp;F class that renders this component.
     *
     * <p>
     *  返回呈现此组件的L&amp; F类的名称。
     * 
     * 
     * @return the string "SeparatorUI"
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
     * Returns the orientation of this separator.
     *
     * <p>
     *  返回此分隔符的方向。
     * 
     * 
     * @return   The value of the orientation property, one of the
     *           following constants defined in <code>SwingConstants</code>:
     *           <code>VERTICAL</code>, or
     *           <code>HORIZONTAL</code>.
     *
     * @see SwingConstants
     * @see #setOrientation
     */
    public int getOrientation() {
        return this.orientation;
    }

    /**
     * Sets the orientation of the separator.
     * The default value of this property is HORIZONTAL.
     * <p>
     *  设置分隔符的方向。此属性的默认值为HORIZONTAL。
     * 
     * 
     * @param orientation  either <code>SwingConstants.HORIZONTAL</code>
     *                  or <code>SwingConstants.VERTICAL</code>
     * @exception IllegalArgumentException  if <code>orientation</code>
     *          is neither <code>SwingConstants.HORIZONTAL</code>
     *          nor <code>SwingConstants.VERTICAL</code>
     *
     * @see SwingConstants
     * @see #getOrientation
     * @beaninfo
     *        bound: true
     *    preferred: true
     *         enum: HORIZONTAL SwingConstants.HORIZONTAL
     *               VERTICAL   SwingConstants.VERTICAL
     *    attribute: visualUpdate true
     *  description: The orientation of the separator.
     */
    public void setOrientation( int orientation ) {
        if (this.orientation == orientation) {
            return;
        }
        int oldValue = this.orientation;
        checkOrientation( orientation );
        this.orientation = orientation;
        firePropertyChange("orientation", oldValue, orientation);
        revalidate();
        repaint();
    }

    private void checkOrientation( int orientation )
    {
        switch ( orientation )
        {
            case VERTICAL:
            case HORIZONTAL:
                break;
            default:
                throw new IllegalArgumentException( "orientation must be one of: VERTICAL, HORIZONTAL" );
        }
    }


    /**
     * Returns a string representation of this <code>JSeparator</code>.
     * This method
     * is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     *
     * <p>
     *  返回此<code> JSeparator </code>的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return  a string representation of this <code>JSeparator</code>
     */
    protected String paramString() {
        String orientationString = (orientation == HORIZONTAL ?
                                    "HORIZONTAL" : "VERTICAL");

        return super.paramString() +
        ",orientation=" + orientationString;
    }

/////////////////
// Accessibility support
////////////////

    /**
     * Gets the AccessibleContext associated with this JSeparator.
     * For separators, the AccessibleContext takes the form of an
     * AccessibleJSeparator.
     * A new AccessibleJSeparator instance is created if necessary.
     *
     * <p>
     *  获取与此JSeparator相关联的AccessibleContext。对于分隔符,AccessibleContext采用AccessibleJSeparator的形式。
     * 如果需要,将创建一个新的AccessibleJSeparator实例。
     * 
     * 
     * @return an AccessibleJSeparator that serves as the
     *         AccessibleContext of this JSeparator
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJSeparator();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>JSeparator</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to separator user-interface elements.
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
     *  此类实现<code> JSeparator </code>类的辅助功能支持。它提供了适用于分隔符用户界面元素的Java辅助功能API的实现。
     * <p>
     * <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     */
    @SuppressWarnings("serial")
    protected class AccessibleJSeparator extends AccessibleJComponent {

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
            return AccessibleRole.SEPARATOR;
        }
    }
}
