/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2010, Oracle and/or its affiliates. All rights reserved.
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

package java.util.zip;

import java.io.IOException;

/**
 * Signals that a Zip exception of some sort has occurred.
 *
 * <p>
 *  表示发生了某种类型的Zip异常。
 * 
 * 
 * @author  unascribed
 * @see     java.io.IOException
 * @since   JDK1.0
 */

public
class ZipException extends IOException {
    private static final long serialVersionUID = 8000196834066748623L;

    /**
     * Constructs a <code>ZipException</code> with <code>null</code>
     * as its error detail message.
     * <p>
     *  构造具有<code> null </code>的<code> ZipException </code>作为其错误详细信息。
     * 
     */
    public ZipException() {
        super();
    }

    /**
     * Constructs a <code>ZipException</code> with the specified detail
     * message.
     *
     * <p>
     *  使用指定的详细消息构造<code> ZipException </code>。
     * 
     * @param   s   the detail message.
     */

    public ZipException(String s) {
        super(s);
    }
}
