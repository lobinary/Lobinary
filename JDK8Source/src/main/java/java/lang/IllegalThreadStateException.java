/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1994, 2008, Oracle and/or its affiliates. All rights reserved.
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

package java.lang;

/**
 * Thrown to indicate that a thread is not in an appropriate state
 * for the requested operation. See, for example, the
 * <code>suspend</code> and <code>resume</code> methods in class
 * <code>Thread</code>.
 *
 * <p>
 *  抛出以指示线程对于所请求的操作不处于适当的状态。
 * 例如,参见<code> Thread </code>类中的<code> suspend </code>和<code> resume </code>方法。
 * 
 * 
 * @author  unascribed
 * @see     java.lang.Thread#resume()
 * @see     java.lang.Thread#suspend()
 * @since   JDK1.0
 */
public class IllegalThreadStateException extends IllegalArgumentException {
    private static final long serialVersionUID = -7626246362397460174L;

    /**
     * Constructs an <code>IllegalThreadStateException</code> with no
     * detail message.
     * <p>
     *  构造一个没有详细消息的<code> IllegalThreadStateException </code>。
     * 
     */
    public IllegalThreadStateException() {
        super();
    }

    /**
     * Constructs an <code>IllegalThreadStateException</code> with the
     * specified detail message.
     *
     * <p>
     *  使用指定的详细消息构造<code> IllegalThreadStateException </code>。
     * 
     * @param   s   the detail message.
     */
    public IllegalThreadStateException(String s) {
        super(s);
    }
}
