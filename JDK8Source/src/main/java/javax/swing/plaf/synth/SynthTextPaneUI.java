/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing.plaf.synth;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.plaf.*;
import java.beans.PropertyChangeEvent;
import java.awt.*;

/**
 * Provides the look and feel for a styled text editor in the
 * Synth look and feel.
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
 *  在Synth外观中提供样式文本编辑器的外观和感觉。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author  Shannon Hickey
 * @since 1.7
 */
public class SynthTextPaneUI extends SynthEditorPaneUI {

    /**
     * Creates a UI for the JTextPane.
     *
     * <p>
     *  创建JTextPane的UI。
     * 
     * 
     * @param c the JTextPane object
     * @return the UI object
     */
    public static ComponentUI createUI(JComponent c) {
        return new SynthTextPaneUI();
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
    @Override
    protected String getPropertyPrefix() {
        return "TextPane";
    }

    /**
     * Installs the UI for a component.  This does the following
     * things.
     * <ol>
     * <li>
     * Sets opaqueness of the associated component according to its style,
     * if the opaque property has not already been set by the client program.
     * <li>
     * Installs the default caret and highlighter into the
     * associated component. These properties are only set if their
     * current value is either {@code null} or an instance of
     * {@link UIResource}.
     * <li>
     * Attaches to the editor and model.  If there is no
     * model, a default one is created.
     * <li>
     * Creates the view factory and the view hierarchy used
     * to represent the model.
     * </ol>
     *
     * <p>
     *  安装组件的UI。这做了以下事情。
     * <ol>
     * <li>
     *  根据其样式设置相关组件的不透明度,如果不透明属性尚未由客户端程序设置。
     * <li>
     *  将默认插入符号和荧光笔安装到关联的组件中。仅当这些属性的当前值为{@code null}或{@link UIResource}的实例时,才设置这些属性。
     * <li>
     *  附加到编辑器和模型。如果没有模型,则创建默认模型。
     * <li>
     *  创建视图工厂和用于表示模型的视图层次结构。
     * </ol>
     * 
     * 
     * @param c the editor component
     * @see javax.swing.plaf.basic.BasicTextUI#installUI
     * @see ComponentUI#installUI
     */
    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        updateForeground(c.getForeground());
        updateFont(c.getFont());
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
     * 当在相关联的JTextComponent上更改绑定属性时,将调用此方法。这是一个钩子,UI实现可以改变以反映UI如何显示JTextComponent子类的绑定属性。
     * 如果字体,前景或文档已更改,则相应的属性将设置为文档的默认样式。
     * 
     * 
     * @param evt the property change event
     */
    @Override
    protected void propertyChange(PropertyChangeEvent evt) {
        super.propertyChange(evt);

        String name = evt.getPropertyName();

        if (name.equals("foreground")) {
            updateForeground((Color)evt.getNewValue());
        } else if (name.equals("font")) {
            updateFont((Font)evt.getNewValue());
        } else if (name.equals("document")) {
            JComponent comp = getComponent();
            updateForeground(comp.getForeground());
            updateFont(comp.getFont());
        }
    }

    /**
     * Update the color in the default style of the document.
     *
     * <p>
     *  更新文档的默认样式中的颜色。
     * 
     * 
     * @param color the new color to use or null to remove the color attribute
     *              from the document's style
     */
    private void updateForeground(Color color) {
        StyledDocument doc = (StyledDocument)getComponent().getDocument();
        Style style = doc.getStyle(StyleContext.DEFAULT_STYLE);

        if (style == null) {
            return;
        }

        if (color == null) {
            style.removeAttribute(StyleConstants.Foreground);
        } else {
            StyleConstants.setForeground(style, color);
        }
    }

    /**
     * Update the font in the default style of the document.
     *
     * <p>
     *  更新文档的默认样式中的字体。
     * 
     * 
     * @param font the new font to use or null to remove the font attribute
     *             from the document's style
     */
    private void updateFont(Font font) {
        StyledDocument doc = (StyledDocument)getComponent().getDocument();
        Style style = doc.getStyle(StyleContext.DEFAULT_STYLE);

        if (style == null) {
            return;
        }

        if (font == null) {
            style.removeAttribute(StyleConstants.FontFamily);
            style.removeAttribute(StyleConstants.FontSize);
            style.removeAttribute(StyleConstants.Bold);
            style.removeAttribute(StyleConstants.Italic);
        } else {
            StyleConstants.setFontFamily(style, font.getName());
            StyleConstants.setFontSize(style, font.getSize());
            StyleConstants.setBold(style, font.isBold());
            StyleConstants.setItalic(style, font.isItalic());
        }
    }

    @Override
    void paintBackground(SynthContext context, Graphics g, JComponent c) {
        context.getPainter().paintTextPaneBackground(context, g, 0, 0,
                                                  c.getWidth(), c.getHeight());
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     */
    @Override
    public void paintBorder(SynthContext context, Graphics g, int x,
                            int y, int w, int h) {
        context.getPainter().paintTextPaneBorder(context, g, x, y, w, h);
    }
}
