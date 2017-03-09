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

package com.sun.java.swing.plaf.windows;

import java.awt.*;
import java.beans.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.*;
import javax.swing.plaf.ComponentUI;

import static com.sun.java.swing.plaf.windows.TMSchema.*;
import static com.sun.java.swing.plaf.windows.XPStyle.Skin;

/**
 * Windows rendition of the component.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases.  The current serialization support is appropriate
 * for short term storage or RMI between applications running the same
 * version of Swing.  A future release of Swing will provide support for
 * long term persistence.
 * <p>
 *  Windows组件的翻译。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 */
public class WindowsInternalFrameUI extends BasicInternalFrameUI
{
    XPStyle xp = XPStyle.getXP();

    public void installDefaults() {
        super.installDefaults();

        if (xp != null) {
            frame.setBorder(new XPBorder());
        } else {
            frame.setBorder(UIManager.getBorder("InternalFrame.border"));
        }
    }

    public void installUI(JComponent c)   {
        super.installUI(c);

        LookAndFeel.installProperty(c, "opaque",
                                    xp == null? Boolean.TRUE : Boolean.FALSE);
    }

    public void uninstallDefaults() {
        frame.setBorder(null);
        super.uninstallDefaults();
    }

    public static ComponentUI createUI(JComponent b)    {
        return new WindowsInternalFrameUI((JInternalFrame)b);
    }

    public WindowsInternalFrameUI(JInternalFrame w){
        super(w);
    }

    protected DesktopManager createDesktopManager(){
        return new WindowsDesktopManager();
    }

    protected JComponent createNorthPane(JInternalFrame w) {
        titlePane = new WindowsInternalFrameTitlePane(w);
        return titlePane;
    }

    private class XPBorder extends AbstractBorder {
        private Skin leftSkin   = xp.getSkin(frame, Part.WP_FRAMELEFT);
        private Skin rightSkin  = xp.getSkin(frame, Part.WP_FRAMERIGHT);
        private Skin bottomSkin = xp.getSkin(frame, Part.WP_FRAMEBOTTOM);

        /**
        /* <p>
        /* 未来的Swing版本将为长期持久性提供支持。
        /* 
        /* 
         * @param x the x position of the painted border
         * @param y the y position of the painted border
         * @param width the width of the painted border
         * @param height the height of the painted border
         */
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            State state = ((JInternalFrame)c).isSelected() ? State.ACTIVE : State.INACTIVE;
            int topBorderHeight  = (titlePane != null) ? titlePane.getSize().height : 0;

            bottomSkin.paintSkin(g, 0, height-bottomSkin.getHeight(),
                                 width, bottomSkin.getHeight(),
                                 state);

            leftSkin.paintSkin(g, 0, topBorderHeight-1,
                               leftSkin.getWidth(), height-topBorderHeight-bottomSkin.getHeight()+2,
                               state);

            rightSkin.paintSkin(g, width-rightSkin.getWidth(), topBorderHeight-1,
                                rightSkin.getWidth(), height-topBorderHeight-bottomSkin.getHeight()+2,
                                state);

        }

        public Insets getBorderInsets(Component c, Insets insets) {
            insets.top    = 4;
            insets.left   = leftSkin.getWidth();
            insets.right  = rightSkin.getWidth();
            insets.bottom = bottomSkin.getHeight();

            return insets;
        }

        public boolean isBorderOpaque() {
            return true;
        }
    }

}
