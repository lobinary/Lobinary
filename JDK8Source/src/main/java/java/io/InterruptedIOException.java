/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
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
 * Signals that an I/O operation has been interrupted. An
 * <code>InterruptedIOException</code> is thrown to indicate that an
 * input or output transfer has been terminated because the thread
 * performing it was interrupted. The field {@link #bytesTransferred}
 * indicates how many bytes were successfully transferred before
 * the interruption occurred.
 *
 * <p>
 *  表示I / O操作已中断。抛出<code> InterruptedIOException </code>以指示输入或输出传输已终止,因为执行它的线程已中断。
 * 字段{@link #bytesTransferred}指示在中断发生之前成功传输了多少字节。
 * 
 * 
 * @author  unascribed
 * @see     java.io.InputStream
 * @see     java.io.OutputStream
 * @see     java.lang.Thread#interrupt()
 * @since   JDK1.0
 */
public
class InterruptedIOException extends IOException {
    private static final long serialVersionUID = 4020568460727500567L;

    /**
     * Constructs an <code>InterruptedIOException</code> with
     * <code>null</code> as its error detail message.
     * <p>
     *  构造具有<code> null </code>作为其错误详细消息的<code> InterruptedIOException </code>。
     * 
     */
    public InterruptedIOException() {
        super();
    }

    /**
     * Constructs an <code>InterruptedIOException</code> with the
     * specified detail message. The string <code>s</code> can be
     * retrieved later by the
     * <code>{@link java.lang.Throwable#getMessage}</code>
     * method of class <code>java.lang.Throwable</code>.
     *
     * <p>
     *  使用指定的详细消息构造一个<code> InterruptedIOException </code>。
     * 稍后可以通过<code> java.lang.Throwable </code>类的<code> {@ link java.lang.Throwable#getMessage} </code>方法检索字
     * 符串<code> s </code>。
     *  使用指定的详细消息构造一个<code> InterruptedIOException </code>。
     * 
     * @param   s   the detail message.
     */
    public InterruptedIOException(String s) {
        super(s);
    }

    /**
     * Reports how many bytes had been transferred as part of the I/O
     * operation before it was interrupted.
     *
     * <p>
     * 
     * 
     * @serial
     */
    public int bytesTransferred = 0;
}
