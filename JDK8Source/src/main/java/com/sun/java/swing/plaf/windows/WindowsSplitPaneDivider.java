/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, Oracle and/or its affiliates. All rights reserved.
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
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;


/**
 * Divider used for Windows split pane.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases.  The current serialization support is appropriate
 * for short term storage or RMI between applications running the same
 * version of Swing.  A future release of Swing will provide support for
 * long term persistence.
 *
 * <p>
 *  分隔线用于Windows分割窗格。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 * 未来的Swing版本将为长期持久性提供支持。
 * 
 * 
 * @author Jeff Dinkins
 */
public class WindowsSplitPaneDivider extends BasicSplitPaneDivider
{

    /**
     * Creates a new Windows SplitPaneDivider
     * <p>
     *  创建新的Windows SplitPaneDivider
     * 
     */
    public WindowsSplitPaneDivider(BasicSplitPaneUI ui) {
        super(ui);
    }

    /**
      * Paints the divider.
      * <p>
      *  涂抹分隔线。
      */
    public void paint(Graphics g) {
        Color bgColor = (splitPane.hasFocus()) ?
                            UIManager.getColor("SplitPane.shadow") :
                            getBackground();
        Dimension size = getSize();

        if(bgColor != null) {
            g.setColor(bgColor);
            g.fillRect(0, 0, size.width, size.height);
        }
        super.paint(g);
    }
}
