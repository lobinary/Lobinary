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
import javax.swing.text.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.BasicTextPaneUI;

/**
 * Provides the look and feel for a styled text editor.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases.  The current serialization support is appropriate
 * for short term storage or RMI between applications running the same
 * version of Swing.  A future release of Swing will provide support for
 * long term persistence.
 *
 * <p>
 *  提供样式文本编辑器的外观和感觉。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 * 未来的Swing版本将为长期持久性提供支持。
 * 
 * 
 * @author  Timothy Prinzing
 */
public class MotifTextPaneUI extends BasicTextPaneUI {

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
        return new MotifTextPaneUI();
    }

    /**
     * Creates the object to use for a caret.  By default an
     * instance of MotifTextUI.MotifCaret is created.  This method
     * can be redefined to provide something else that implements
     * the Caret interface.
     *
     * <p>
     *  创建用于插入符的对象。默认情况下创建MotifTextUI.MotifCaret的实例。可以重新定义此方法以提供实现Caret界面的其他内容。
     * 
     * @return the caret object
     */
    protected Caret createCaret() {
        return MotifTextUI.createCaret();
    }

}
