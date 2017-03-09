/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.io;

/**
 * Signals that an end of file or end of stream has been reached
 * unexpectedly during input.
 * <p>
 * This exception is mainly used by data input streams to signal end of
 * stream. Note that many other input operations return a special value on
 * end of stream rather than throwing an exception.
 *
 * <p>
 *  在输入期间意外地到达文件结束或流末尾的信号。
 * <p>
 *  此异常主要由数据输入流用于信号结束流。注意,许多其他输入操作在流末尾返回一个特殊值,而不是抛出异常。
 * 
 * 
 * @author  Frank Yellin
 * @see     java.io.DataInputStream
 * @see     java.io.IOException
 * @since   JDK1.0
 */
public
class EOFException extends IOException {
    private static final long serialVersionUID = 6433858223774886977L;

    /**
     * Constructs an <code>EOFException</code> with <code>null</code>
     * as its error detail message.
     * <p>
     *  构造具有<code> null </code>的<code> EOFException </code>作为其错误详细信息。
     * 
     */
    public EOFException() {
        super();
    }

    /**
     * Constructs an <code>EOFException</code> with the specified detail
     * message. The string <code>s</code> may later be retrieved by the
     * <code>{@link java.lang.Throwable#getMessage}</code> method of class
     * <code>java.lang.Throwable</code>.
     *
     * <p>
     *  使用指定的详细消息构造<code> EOFException </code>。
     * 稍后可以通过<code> java.lang.Throwable </code>类的<code> {@ link java.lang.Throwable#getMessage} </code>方法检索字
     * 符串<code> s </code>。
     * 
     * @param   s   the detail message.
     */
    public EOFException(String s) {
        super(s);
    }
}
