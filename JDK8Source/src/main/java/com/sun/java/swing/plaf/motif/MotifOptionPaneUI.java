/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 1998, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.java.swing.plaf.motif;

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import javax.swing.plaf.ComponentUI;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

/**
 * Provides the CDE/Motif look and feel for a JOptionPane.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases.  The current serialization support is appropriate
 * for short term storage or RMI between applications running the same
 * version of Swing.  A future release of Swing will provide support for
 * long term persistence.
 *
 * <p>
 *  提供JOptionPane的CDE / Motif外观。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 * 未来的Swing版本将为长期持久性提供支持。
 * 
 * 
 * @author Scott Violet
 */
public class MotifOptionPaneUI extends BasicOptionPaneUI
{
    /**
      * Creates a new MotifOptionPaneUI instance.
      * <p>
      *  创建一个新的MotifOptionPaneUI实例。
      * 
      */
    public static ComponentUI createUI(JComponent x) {
        return new MotifOptionPaneUI();
    }

    /**
     * Creates and returns a Container containin the buttons. The buttons
     * are created by calling <code>getButtons</code>.
     * <p>
     *  创建并返回包含在按钮中的Container。这些按钮是通过调用<code> getButtons </code>创建的。
     * 
     */
    protected Container createButtonArea() {
        Container          b = super.createButtonArea();

        if(b != null && b.getLayout() instanceof ButtonAreaLayout) {
            ((ButtonAreaLayout)b.getLayout()).setCentersChildren(false);
        }
        return b;
    }

    /**
     * Returns null, CDE/Motif does not impose a minimum size.
     * <p>
     *  返回null,CDE / Motif不强加最小大小。
     * 
     */
    public Dimension getMinimumOptionPaneSize() {
        return null;
    }

    protected Container createSeparator() {
        return new JPanel() {

            public Dimension getPreferredSize() {
                return new Dimension(10, 2);
            }

            public void paint(Graphics g) {
                int width = getWidth();
                g.setColor(Color.darkGray);
                g.drawLine(0, 0, width, 0);
                g.setColor(Color.white);
                g.drawLine(0, 1, width, 1);
            }
        };
    }

    /**
     * Creates and adds a JLabel representing the icon returned from
     * <code>getIcon</code> to <code>top</code>. This is messaged from
     * <code>createMessageArea</code>
     * <p>
     *  创建并添加一个表示从<code> getIcon </code>返回<code> top </code>的图标的JLabel。这是从<code> createMessageArea </code>
     */
    protected void addIcon(Container top) {
        /* Create the icon. */
        Icon                  sideIcon = getIcon();

        if (sideIcon != null) {
            JLabel            iconLabel = new JLabel(sideIcon);

            iconLabel.setVerticalAlignment(SwingConstants.CENTER);
            top.add(iconLabel, "West");
        }
    }

}
