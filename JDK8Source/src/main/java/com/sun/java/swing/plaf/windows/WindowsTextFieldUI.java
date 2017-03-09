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

package com.sun.java.swing.plaf.windows;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.*;
import javax.swing.*;
import javax.swing.plaf.UIResource;
import sun.swing.DefaultLookup;



/**
 * Provides the Windows look and feel for a text field.  This
 * is basically the following customizations to the default
 * look-and-feel.
 * <ul>
 * <li>The border is beveled (using the standard control color).
 * <li>The background is white by default.
 * <li>The highlight color is a dark color, blue by default.
 * <li>The foreground color is high contrast in the selected
 *  area, white by default.  The unselected foreground is black.
 * <li>The cursor blinks at about 1/2 second intervals.
 * <li>The entire value is selected when focus is gained.
 * <li>Shift-left-arrow and shift-right-arrow extend selection
 * <li>Ctrl-left-arrow and ctrl-right-arrow act like home and
 *   end respectively.
 * </ul>
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases.  The current serialization support is appropriate
 * for short term storage or RMI between applications running the same
 * version of Swing.  A future release of Swing will provide support for
 * long term persistence.
 *
 * <p>
 *  提供文本字段的Windows外观。这基本上是对默认外观的以下自定义。
 * <ul>
 *  <li>边框有斜角(使用标准控制颜色)。 <li>默认情况下,背景为白色。 <li>突出显示的颜色是深色,默认为蓝色。 <li>前景色在所选区域中为高对比度,默认为白色。未选择的前景是黑色的。
 *  <li>光标以约1/2秒的间隔闪烁。 <li>在获得焦点时选择整个值。
 *  <li> Shift-left-arrow和shift-right-arrow扩展选择<li> Ctrl-left-arrow和ctrl-right-arrow分别表示为home和end。
 * </ul>
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 * 未来的Swing版本将为长期持久性提供支持。
 * 
 * 
 * @author  Timothy Prinzing
 */
public class WindowsTextFieldUI extends BasicTextFieldUI
{
    /**
     * Creates a UI for a JTextField.
     *
     * <p>
     *  为JTextField创建一个UI。
     * 
     * 
     * @param c the text field
     * @return the UI
     */
    public static ComponentUI createUI(JComponent c) {
        return new WindowsTextFieldUI();
    }

    /**
     * Paints a background for the view.  This will only be
     * called if isOpaque() on the associated component is
     * true.  The default is to paint the background color
     * of the component.
     *
     * <p>
     *  为视图绘制背景。只有当相关组件上的isOpaque()为true时,才会调用此方法。默认是绘制组件的背景颜色。
     * 
     * 
     * @param g the graphics context
     */
    protected void paintBackground(Graphics g) {
        super.paintBackground(g);
    }

    /**
     * Creates the caret for a field.
     *
     * <p>
     *  为字段创建插入符号。
     * 
     * 
     * @return the caret
     */
    protected Caret createCaret() {
        return new WindowsFieldCaret();
    }

    /**
     * WindowsFieldCaret has different scrolling behavior than
     * DefaultCaret.
     * <p>
     *  WindowsFieldCaret具有与DefaultCaret不同的滚动行为。
     * 
     */
    static class WindowsFieldCaret extends DefaultCaret implements UIResource {

        public WindowsFieldCaret() {
            super();
        }

        /**
         * Adjusts the visibility of the caret according to
         * the windows feel which seems to be to move the
         * caret out into the field by about a quarter of
         * a field length if not visible.
         * <p>
         * 根据窗口感觉调整插入符的可见性,这似乎是将插入符号移出字段大约四分之一字段长度(如果不可见)。
         * 
         */
        protected void adjustVisibility(Rectangle r) {
            SwingUtilities.invokeLater(new SafeScroller(r));
        }

        /**
         * Gets the painter for the Highlighter.
         *
         * <p>
         *  获取荧光笔的画家。
         * 
         * @return the painter
         */
        protected Highlighter.HighlightPainter getSelectionPainter() {
            return WindowsTextUI.WindowsPainter;
        }


        private class SafeScroller implements Runnable {
            SafeScroller(Rectangle r) {
                this.r = r;
            }

            public void run() {
                JTextField field = (JTextField) getComponent();
                if (field != null) {
                    TextUI ui = field.getUI();
                    int dot = getDot();
                    // PENDING: We need to expose the bias in DefaultCaret.
                    Position.Bias bias = Position.Bias.Forward;
                    Rectangle startRect = null;
                    try {
                        startRect = ui.modelToView(field, dot, bias);
                    } catch (BadLocationException ble) {}

                    Insets i = field.getInsets();
                    BoundedRangeModel vis = field.getHorizontalVisibility();
                    int x = r.x + vis.getValue() - i.left;
                    int quarterSpan = vis.getExtent() / 4;
                    if (r.x < i.left) {
                        vis.setValue(x - quarterSpan);
                    } else if (r.x + r.width > i.left + vis.getExtent()) {
                        vis.setValue(x - (3 * quarterSpan));
                    }
                    // If we scroll, our visual location will have changed,
                    // but we won't have updated our internal location as
                    // the model hasn't changed. This checks for the change,
                    // and if necessary, resets the internal location.
                    if (startRect != null) {
                        try {
                            Rectangle endRect;
                            endRect = ui.modelToView(field, dot, bias);
                            if (endRect != null && !endRect.equals(startRect)){
                                damage(endRect);
                            }
                        } catch (BadLocationException ble) {}
                    }
                }
            }

            private Rectangle r;
        }
    }

}
