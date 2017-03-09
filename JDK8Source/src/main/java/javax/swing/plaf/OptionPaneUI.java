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

package javax.swing.plaf;

import javax.swing.JOptionPane;

/**
 * Pluggable look and feel interface for JOptionPane.
 *
 * <p>
 *  可插拔的外观和接口的JOptionPane。
 * 
 * 
 * @author Scott Violet
 */

public abstract class OptionPaneUI extends ComponentUI
{
    /**
     * Requests the component representing the default value to have
     * focus.
     * <p>
     *  请求表示默认值的组件具有焦点。
     * 
     */
    public abstract void selectInitialValue(JOptionPane op);

    /**
     * Returns true if the user has supplied instances of Component for
     * either the options or message.
     * <p>
     *  如果用户为选项或消息提供了Component的实例,则返回true。
     */
    public abstract boolean containsCustomComponents(JOptionPane op);
}
