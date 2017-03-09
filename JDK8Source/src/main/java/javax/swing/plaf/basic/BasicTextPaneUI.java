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
package javax.swing.plaf.basic;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.plaf.*;
import javax.swing.border.*;


/**
 * Provides the look and feel for a styled text editor.
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
 *  提供样式文本编辑器的外观和感觉。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author  Timothy Prinzing
 */
public class BasicTextPaneUI extends BasicEditorPaneUI {

    /**
     * Creates a UI for the JTextPane.
     *
     * <p>
     *  创建JTextPane的UI。
     * 
     * 
     * @param c the JTextPane object
     * @return the UI
     */
    public static ComponentUI createUI(JComponent c) {
        return new BasicTextPaneUI();
    }

    /**
     * Creates a new BasicTextPaneUI.
     * <p>
     *  创建一个新的BasicTextPaneUI。
     * 
     */
    public BasicTextPaneUI() {
        super();
    }

    /**
     * Fetches the name used as a key to lookup properties through the
     * UIManager.  This is used as a prefix to all the standard
     * text properties.
     *
     * <p>
     *  获取用作通过UIManager查找属性的键的名称。这用作所有标准文本属性的前缀。
     * 
     * 
     * @return the name ("TextPane")
     */
    protected String getPropertyPrefix() {
        return "TextPane";
    }

    public void installUI(JComponent c) {
        super.installUI(c);
    }

    /**
     * This method gets called when a bound property is changed
     * on the associated JTextComponent.  This is a hook
     * which UI implementations may change to reflect how the
     * UI displays bound properties of JTextComponent subclasses.
     * If the font, foreground or document has changed, the
     * the appropriate property is set in the default style of
     * the document.
     *
     * <p>
     *  当在相关联的JTextComponent上更改绑定属性时,将调用此方法。这是一个钩子,UI实现可以改变以反映UI如何显示JTextComponent子类的绑定属性。
     * 如果字体,前景或文档已更改,则相应的属性将设置为文档的默认样式。
     * 
     * @param evt the property change event
     */
    protected void propertyChange(PropertyChangeEvent evt) {
        super.propertyChange(evt);
    }
}
