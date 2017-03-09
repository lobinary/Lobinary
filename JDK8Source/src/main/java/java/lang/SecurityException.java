/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2003, Oracle and/or its affiliates. All rights reserved.
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
 * Thrown by the security manager to indicate a security violation.
 *
 * <p>
 *  由安全管理器引发以指示安全违规。
 * 
 * 
 * @author  unascribed
 * @see     java.lang.SecurityManager
 * @since   JDK1.0
 */
public class SecurityException extends RuntimeException {

    private static final long serialVersionUID = 6878364983674394167L;

    /**
     * Constructs a <code>SecurityException</code> with no detail  message.
     * <p>
     *  构造一个没有详细消息的<code> SecurityException </code>。
     * 
     */
    public SecurityException() {
        super();
    }

    /**
     * Constructs a <code>SecurityException</code> with the specified
     * detail message.
     *
     * <p>
     *  使用指定的详细消息构造一个<code> SecurityException </code>。
     * 
     * 
     * @param   s   the detail message.
     */
    public SecurityException(String s) {
        super(s);
    }

    /**
     * Creates a <code>SecurityException</code> with the specified
     * detail message and cause.
     *
     * <p>
     *  创建具有指定的详细消息和原因的<code> SecurityException </code>。
     * 
     * 
     * @param message the detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method).
     * @param cause the cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A <tt>null</tt> value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     * @since 1.5
     */
    public SecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a <code>SecurityException</code> with the specified cause
     * and a detail message of <tt>(cause==null ? null : cause.toString())</tt>
     * (which typically contains the class and detail message of
     * <tt>cause</tt>).
     *
     * <p>
     *  创建具有指定原因和<tt>(cause == null?null：cause.toString())</tt>的详细消息的<code> SecurityException </code>(通常包含类和
     * 详细消息<tt>原因</tt>)。
     * 
     * @param cause the cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A <tt>null</tt> value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     * @since 1.5
     */
    public SecurityException(Throwable cause) {
        super(cause);
    }
}
