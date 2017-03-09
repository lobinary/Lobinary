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

package com.sun.java.swing.plaf.windows;

import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;
import javax.swing.*;
import javax.swing.text.Caret;


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
 * 未来的Swing版本将为长期持久性提供支持。
 * 
 */
public class WindowsTextPaneUI extends BasicTextPaneUI
{
    /**
     * Creates a UI for a JTextPane.
     *
     * <p>
     *  创建JTextPane的UI。
     * 
     * 
     * @param c the styled text component
     * @return the UI
     */
    public static ComponentUI createUI(JComponent c) {
        return new WindowsTextPaneUI();
    }

    /**
     * Creates the object to use for a caret.  By default an
     * instance of WindowsCaret is created.  This method
     * can be redefined to provide something else that implements
     * the InputPosition interface or a subclass of DefaultCaret.
     *
     * <p>
     *  创建用于插入符的对象。默认情况下创建WindowsCaret的实例。此方法可以重新定义,以提供其他实现InputPosition接口或DefaultCaret的子类。
     * 
     * @return the caret object
     */
    protected Caret createCaret() {
        return new WindowsTextUI.WindowsCaret();
    }
}
