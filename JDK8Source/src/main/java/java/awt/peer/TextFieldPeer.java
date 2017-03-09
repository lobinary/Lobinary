/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 1998, Oracle and/or its affiliates. All rights reserved.
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
package java.awt.peer;

import java.awt.Dimension;
import java.awt.TextField;

/**
 * The peer interface for {@link TextField}.
 *
 * The peer interfaces are intended only for use in porting
 * the AWT. They are not intended for use by application
 * developers, and developers should not implement peers
 * nor invoke any of the peer methods directly on the peer
 * instances.
 * <p>
 *  {@link TextField}的对等接口。
 * 
 *  对等接口仅用于移植AWT。它们不是供应用程序开发人员使用的,并且开发人员不应实现对等体,也不应直接在对等实例上调用任何对等方法。
 * 
 */
public interface TextFieldPeer extends TextComponentPeer {

    /**
     * Sets the echo character.
     *
     * <p>
     *  设置回显字符。
     * 
     * 
     * @param echoChar the echo character to set
     *
     * @see TextField#getEchoChar()
     */
    void setEchoChar(char echoChar);

    /**
     * Returns the preferred size of the text field with the specified number
     * of columns.
     *
     * <p>
     *  返回具有指定列数的文本字段的首选大小。
     * 
     * 
     * @param columns the number of columns
     *
     * @return the preferred size of the text field
     *
     * @see TextField#getPreferredSize(int)
     */
    Dimension getPreferredSize(int columns);

    /**
     * Returns the minimum size of the text field with the specified number
     * of columns.
     *
     * <p>
     *  返回具有指定列数的文本字段的最小大小。
     * 
     * @param columns the number of columns
     *
     * @return the minimum size of the text field
     *
     * @see TextField#getMinimumSize(int)
     */
    Dimension getMinimumSize(int columns);

}
