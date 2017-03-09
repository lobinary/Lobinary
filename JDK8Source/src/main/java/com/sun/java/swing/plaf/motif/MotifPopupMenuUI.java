/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2005, Oracle and/or its affiliates. All rights reserved.
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

import sun.swing.SwingUtilities2;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.BasicPopupMenuUI;


/**
 * A Motif L&F implementation of PopupMenuUI.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases.  The current serialization support is appropriate
 * for short term storage or RMI between applications running the same
 * version of Swing.  A future release of Swing will provide support for
 * long term persistence.
 *
 * <p>
 *  一个Motif L&F实现PopupMenuUI。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 * 未来的Swing版本将为长期持久性提供支持。
 * 
 * @author Georges Saab
 * @author Rich Schiavi
 */
public class MotifPopupMenuUI extends BasicPopupMenuUI {
    private static Border border = null;
    private Font titleFont = null;

    public static ComponentUI createUI(JComponent x) {
        return new MotifPopupMenuUI();
    }

    /* This has to deal with the fact that the title may be wider than
       the widest child component.
    /* <p>
    /* 
       */
    public Dimension getPreferredSize(JComponent c) {
        LayoutManager layout = c.getLayout();
        Dimension d = layout.preferredLayoutSize(c);
        String title = ((JPopupMenu)c).getLabel();
        if (titleFont == null) {
            UIDefaults table = UIManager.getLookAndFeelDefaults();
            titleFont = table.getFont("PopupMenu.font");
        }
        FontMetrics fm = c.getFontMetrics(titleFont);
        int         stringWidth = 0;

        if (title!=null) {
            stringWidth += SwingUtilities2.stringWidth(c, fm, title);
        }

        if (d.width < stringWidth) {
            d.width = stringWidth + 8;
            Insets i = c.getInsets();
            if (i!=null) {
                d.width += i.left + i.right;
            }
            if (border != null) {
                i = border.getBorderInsets(c);
                d.width += i.left + i.right;
            }

            return d;
        }
        return null;
    }

    protected ChangeListener createChangeListener(JPopupMenu m) {
        return new ChangeListener() {
            public void stateChanged(ChangeEvent e) {}
        };
    }

    public boolean isPopupTrigger(MouseEvent e) {
        return ((e.getID()==MouseEvent.MOUSE_PRESSED)
                && ((e.getModifiers() & MouseEvent.BUTTON3_MASK)!=0));
    }
}
