/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved.
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
 * Common superclass of exceptions thrown by reflective operations in
 * core reflection.
 *
 * <p>
 *  反射操作在核心反射中抛出的异常的常见超类。
 * 
 * 
 * @see LinkageError
 * @since 1.7
 */
public class ReflectiveOperationException extends Exception {
    static final long serialVersionUID = 123456789L;

    /**
     * Constructs a new exception with {@code null} as its detail
     * message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     * <p>
     *  使用{@code null}作为其详细消息构造新的异常。原因未初始化,并可能随后通过调用{@link #initCause}初始化。
     * 
     */
    public ReflectiveOperationException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     * The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     *
     * <p>
     *  使用指定的详细消息构造新的异常。原因未初始化,并可能随后通过调用{@link #initCause}初始化。
     * 
     * 
     * @param   message   the detail message. The detail message is saved for
     *          later retrieval by the {@link #getMessage()} method.
     */
    public ReflectiveOperationException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message
     * and cause.
     *
     * <p>Note that the detail message associated with
     * {@code cause} is <em>not</em> automatically incorporated in
     * this exception's detail message.
     *
     * <p>
     *  构造具有指定的详细消息和原因的新异常。
     * 
     *  <p>请注意,与{@code cause}相关联的详细信息<em> </em>不会自动合并到此例外的详细信息中。
     * 
     * 
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link #getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A {@code null} value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public ReflectiveOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause and a detail
     * message of {@code (cause==null ? null : cause.toString())} (which
     * typically contains the class and detail message of {@code cause}).
     *
     * <p>
     * 
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A {@code null} value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public ReflectiveOperationException(Throwable cause) {
        super(cause);
    }
}
