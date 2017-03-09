/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, Oracle and/or its affiliates. All rights reserved.
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

package javax.imageio;

import java.io.IOException;

/**
 * An exception class used for signaling run-time failure of reading
 * and writing operations.
 *
 * <p> In addition to a message string, a reference to another
 * <code>Throwable</code> (<code>Error</code> or
 * <code>Exception</code>) is maintained.  This reference, if
 * non-<code>null</code>, refers to the event that caused this
 * exception to occur.  For example, an <code>IOException</code> while
 * reading from a <code>File</code> would be stored there.
 *
 * <p>
 *  用于表示读取和写入操作的运行时失败的异常类。
 * 
 *  <p>除了消息字符串之外,还维护对另一个<code> Throwable </code>(<code>错误</code>或<code>异常</code>)的引用。
 * 此引用(如果非<code> null </code>)指引发此异常的事件。例如,当从<code> File </code>读取时,<code> IOException </code>将存储在那里。
 * 
 */
public class IIOException extends IOException {

    /**
     * Constructs an <code>IIOException</code> with a given message
     * <code>String</code>.  No underlying cause is set;
     * <code>getCause</code> will return <code>null</code>.
     *
     * <p>
     *  用给定的消息<code> String </code>构造一个<code> IIOException </code>。
     * 没有设置根本原因; <code> getCause </code>将返回<code> null </code>。
     * 
     * 
     * @param message the error message.
     *
     * @see #getMessage
     */
    public IIOException(String message) {
        super(message);
    }

    /**
     * Constructs an <code>IIOException</code> with a given message
     * <code>String</code> and a <code>Throwable</code> that was its
     * underlying cause.
     *
     * <p>
     * 
     * @param message the error message.
     * @param cause the <code>Throwable</code> (<code>Error</code> or
     * <code>Exception</code>) that caused this exception to occur.
     *
     * @see #getCause
     * @see #getMessage
     */
    public IIOException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
}
