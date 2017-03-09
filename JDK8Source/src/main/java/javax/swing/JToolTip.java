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
import java.util.Objects;


/**
 * Used to display a "Tip" for a Component. Typically components provide api
 * to automate the process of using <code>ToolTip</code>s.
 * For example, any Swing component can use the <code>JComponent</code>
 * <code>setToolTipText</code> method to specify the text
 * for a standard tooltip. A component that wants to create a custom
 * <code>ToolTip</code>
 * display can override <code>JComponent</code>'s <code>createToolTip</code>
 * method and use a subclass of this class.
 * <p>
 * See <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/tooltip.html">How to Use Tool Tips</a>
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
 * <p>
 *  用于显示组件的"提示"。通常组件提供api来自动使用<code> ToolTip </code>的过程。
 * 例如,任何Swing组件都可以使用<code> JComponent </code> <code> setToolTipText </code>方法来指定标准工具提示的文本。
 * 想要创建自定义<code> ToolTip </code>显示的组件可以覆盖<code> JComponent </code>的<code> createToolTip </code>方法,并使用此类的
 * 子类。
 * 例如,任何Swing组件都可以使用<code> JComponent </code> <code> setToolTipText </code>方法来指定标准工具提示的文本。
 * <p>
 *  请参阅<em> Java教程</em>中的<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/tooltip.html">
 * 如何使用工具提示</a>进一步文档。
 * <p>
 *  <strong>警告：</strong> Swing不是线程安全的。有关详情,请参阅<a href="package-summary.html#threading"> Swing的线程策略</a>。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @see JComponent#setToolTipText
 * @see JComponent#createToolTip
 * @author Dave Moore
 * @author Rich Shiavi
 */
@SuppressWarnings("serial")
public class JToolTip extends JComponent implements Accessible {
    /**
    /* <p>
    /* 
     * @see #getUIClassID
     * @see #readObject
     */
    private static final String uiClassID = "ToolTipUI";

    String tipText;
    JComponent component;

    /** Creates a tool tip. */
    public JToolTip() {
        setOpaque(true);
        updateUI();
    }

    /**
     * Returns the L&amp;F object that renders this component.
     *
     * <p>
     *  返回呈现此组件的L&amp; F对象。
     * 
     * 
     * @return the <code>ToolTipUI</code> object that renders this component
     */
    public ToolTipUI getUI() {
        return (ToolTipUI)ui;
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
        setUI((ToolTipUI)UIManager.getUI(this));
    }


    /**
     * Returns the name of the L&amp;F class that renders this component.
     *
     * <p>
     * 返回呈现此组件的L&amp; F类的名称。
     * 
     * 
     * @return the string "ToolTipUI"
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     */
    public String getUIClassID() {
        return uiClassID;
    }


    /**
     * Sets the text to show when the tool tip is displayed.
     * The string <code>tipText</code> may be <code>null</code>.
     *
     * <p>
     *  设置显示工具提示时显示的文本。字符串<code> tipText </code>可以是<code> null </code>。
     * 
     * 
     * @param tipText the <code>String</code> to display
     * @beaninfo
     *    preferred: true
     *        bound: true
     *  description: Sets the text of the tooltip
     */
    public void setTipText(String tipText) {
        String oldValue = this.tipText;
        this.tipText = tipText;
        firePropertyChange("tiptext", oldValue, tipText);

        if (!Objects.equals(oldValue, tipText)) {
            revalidate();
            repaint();
        }
    }

    /**
     * Returns the text that is shown when the tool tip is displayed.
     * The returned value may be <code>null</code>.
     *
     * <p>
     *  返回显示工具提示时显示的文本。返回的值可以是<code> null </code>。
     * 
     * 
     * @return the <code>String</code> that is displayed
     */
    public String getTipText() {
        return tipText;
    }

    /**
     * Specifies the component that the tooltip describes.
     * The component <code>c</code> may be <code>null</code>
     * and will have no effect.
     * <p>
     * This is a bound property.
     *
     * <p>
     *  指定工具提示描述的组件。组件<code> c </code>可能是<code> null </code>,并且没有效果。
     * <p>
     *  这是一个bound属性。
     * 
     * 
     * @param c the <code>JComponent</code> being described
     * @see JComponent#createToolTip
     * @beaninfo
     *       bound: true
     * description: Sets the component that the tooltip describes.
     */
    public void setComponent(JComponent c) {
        JComponent oldValue = this.component;

        component = c;
        firePropertyChange("component", oldValue, c);
    }

    /**
     * Returns the component the tooltip applies to.
     * The returned value may be <code>null</code>.
     *
     * <p>
     *  返回工具提示适用于的组件。返回的值可以是<code> null </code>。
     * 
     * 
     * @return the component that the tooltip describes
     *
     * @see JComponent#createToolTip
     */
    public JComponent getComponent() {
        return component;
    }

    /**
     * Always returns true since tooltips, by definition,
     * should always be on top of all other windows.
     * <p>
     *  始终返回true,因为根据定义,工具提示应始终在所有其他窗口之上。
     * 
     */
    // package private
    boolean alwaysOnTop() {
        return true;
    }


    /**
     * See <code>readObject</code> and <code>writeObject</code>
     * in <code>JComponent</code> for more
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
     * Returns a string representation of this <code>JToolTip</code>.
     * This method
     * is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     *
     * <p>
     *  返回此<code> JToolTip </code>的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return  a string representation of this <code>JToolTip</code>
     */
    protected String paramString() {
        String tipTextString = (tipText != null ?
                                tipText : "");

        return super.paramString() +
        ",tipText=" + tipTextString;
    }


/////////////////
// Accessibility support
////////////////

    /**
     * Gets the AccessibleContext associated with this JToolTip.
     * For tool tips, the AccessibleContext takes the form of an
     * AccessibleJToolTip.
     * A new AccessibleJToolTip instance is created if necessary.
     *
     * <p>
     *  获取与此JToolTip相关联的AccessibleContext。对于工具提示,AccessibleContext采用AccessibleJToolTip的形式。
     * 如果需要,将创建一个新的AccessibleJToolTip实例。
     * 
     * 
     * @return an AccessibleJToolTip that serves as the
     *         AccessibleContext of this JToolTip
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJToolTip();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>JToolTip</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to tool tip user-interface elements.
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
     *  此类实现了对<code> JToolTip </code>类的辅助功能支持。它提供了适用于工具提示用户界面元素的Java辅助功能API的实现。
     * <p>
     * <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     * 
     */
    @SuppressWarnings("serial")
    protected class AccessibleJToolTip extends AccessibleJComponent {

        /**
         * Get the accessible description of this object.
         *
         * <p>
         *  获取此对象的可访问描述。
         * 
         * 
         * @return a localized String describing this object.
         */
        public String getAccessibleDescription() {
            String description = accessibleDescription;

            // fallback to client property
            if (description == null) {
                description = (String)getClientProperty(AccessibleContext.ACCESSIBLE_DESCRIPTION_PROPERTY);
            }
            if (description == null) {
                description = getTipText();
            }
            return description;
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
            return AccessibleRole.TOOL_TIP;
        }
    }
}
