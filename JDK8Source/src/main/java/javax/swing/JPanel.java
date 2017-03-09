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

import javax.swing.plaf.*;
import javax.accessibility.*;

import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;


/**
 * <code>JPanel</code> is a generic lightweight container.
 * For examples and task-oriented documentation for JPanel, see
 * <a
 href="https://docs.oracle.com/javase/tutorial/uiswing/components/panel.html">How to Use Panels</a>,
 * a section in <em>The Java Tutorial</em>.
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
 * description: A generic lightweight container.
 *
 * <p>
 *  <code> JPanel </code>是一个通用的轻量级容器。
 * 有关JPanel的示例和面向任务的文档,请参见<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/panel.html">
 * 如何使用面板</a>,在Java教程</em>中。
 *  <code> JPanel </code>是一个通用的轻量级容器。
 * <p>
 *  <strong>警告：</strong> Swing不是线程安全的。有关详情,请参阅<a href="package-summary.html#threading"> Swing的线程策略</a>。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 *  @beaninfo描述：一个通用的轻量级容器。
 * 
 * 
 * @author Arnaud Weber
 * @author Steve Wilson
 */
public class JPanel extends JComponent implements Accessible
{
    /**
    /* <p>
    /* 
     * @see #getUIClassID
     * @see #readObject
     */
    private static final String uiClassID = "PanelUI";

    /**
     * Creates a new JPanel with the specified layout manager and buffering
     * strategy.
     *
     * <p>
     *  使用指定的布局管理器和缓冲策略创建一个新的JPanel。
     * 
     * 
     * @param layout  the LayoutManager to use
     * @param isDoubleBuffered  a boolean, true for double-buffering, which
     *        uses additional memory space to achieve fast, flicker-free
     *        updates
     */
    public JPanel(LayoutManager layout, boolean isDoubleBuffered) {
        setLayout(layout);
        setDoubleBuffered(isDoubleBuffered);
        setUIProperty("opaque", Boolean.TRUE);
        updateUI();
    }

    /**
     * Create a new buffered JPanel with the specified layout manager
     *
     * <p>
     *  使用指定的布局管理器创建新的缓冲JPanel
     * 
     * 
     * @param layout  the LayoutManager to use
     */
    public JPanel(LayoutManager layout) {
        this(layout, true);
    }

    /**
     * Creates a new <code>JPanel</code> with <code>FlowLayout</code>
     * and the specified buffering strategy.
     * If <code>isDoubleBuffered</code> is true, the <code>JPanel</code>
     * will use a double buffer.
     *
     * <p>
     *  使用<code> FlowLayout </code>和指定的缓冲策略创建新的<code> JPanel </code>。
     * 如果<code> isDoubleBuffered </code>为true,则<code> JPanel </code>将使用双缓冲区。
     * 
     * 
     * @param isDoubleBuffered  a boolean, true for double-buffering, which
     *        uses additional memory space to achieve fast, flicker-free
     *        updates
     */
    public JPanel(boolean isDoubleBuffered) {
        this(new FlowLayout(), isDoubleBuffered);
    }

    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     * <p>
     *  使用双缓冲区和流布局创建新的<code> JPanel </code>。
     * 
     */
    public JPanel() {
        this(true);
    }

    /**
     * Resets the UI property with a value from the current look and feel.
     *
     * <p>
     * 使用当前外观的值重置UI属性。
     * 
     * 
     * @see JComponent#updateUI
     */
    public void updateUI() {
        setUI((PanelUI)UIManager.getUI(this));
    }

    /**
     * Returns the look and feel (L&amp;amp;F) object that renders this component.
     *
     * <p>
     *  返回呈现此组件的外观和感觉(L&amp; amp; F)对象。
     * 
     * 
     * @return the PanelUI object that renders this component
     * @since 1.4
     */
    public PanelUI getUI() {
        return (PanelUI)ui;
    }


    /**
     * Sets the look and feel (L&amp;F) object that renders this component.
     *
     * <p>
     *  设置呈现此组件的外观和感觉(L&amp; F)对象。
     * 
     * 
     * @param ui  the PanelUI L&amp;F object
     * @see UIDefaults#getUI
     * @since 1.4
     * @beaninfo
     *        bound: true
     *       hidden: true
     *    attribute: visualUpdate true
     *  description: The UI object that implements the Component's LookAndFeel.
     */
    public void setUI(PanelUI ui) {
        super.setUI(ui);
    }

    /**
     * Returns a string that specifies the name of the L&amp;F class
     * that renders this component.
     *
     * <p>
     *  返回一个字符串,指定呈现此组件的L&amp; F类的名称。
     * 
     * 
     * @return "PanelUI"
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
     * Returns a string representation of this JPanel. This method
     * is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     *
     * <p>
     *  返回此JPanel的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return  a string representation of this JPanel.
     */
    protected String paramString() {
        return super.paramString();
    }

/////////////////
// Accessibility support
////////////////

    /**
     * Gets the AccessibleContext associated with this JPanel.
     * For JPanels, the AccessibleContext takes the form of an
     * AccessibleJPanel.
     * A new AccessibleJPanel instance is created if necessary.
     *
     * <p>
     *  获取与此JPanel关联的AccessibleContext。对于JPanels,AccessibleContext采用AccessibleJPanel的形式。
     * 如果需要,将创建一个新的AccessibleJPanel实例。
     * 
     * 
     * @return an AccessibleJPanel that serves as the
     *         AccessibleContext of this JPanel
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJPanel();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>JPanel</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to panel user-interface
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
     *  此类实现<code> JPanel </code>类的辅助功能支持。它提供了适用于面板用户界面元素的Java辅助功能API的实现。
     * <p>
     *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     */
    protected class AccessibleJPanel extends AccessibleJComponent {

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
            return AccessibleRole.PANEL;
        }
    }
}
