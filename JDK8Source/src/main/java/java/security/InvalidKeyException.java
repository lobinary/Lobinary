/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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


package java.security;

/**
 * This is the exception for invalid Keys (invalid encoding, wrong
 * length, uninitialized, etc).
 *
 * <p>
 *  这是无效键(无效编码,长度错误,未初始化等)的例外。
 * 
 * 
 * @author Benjamin Renaud
 */

public class InvalidKeyException extends KeyException {

    private static final long serialVersionUID = 5698479920593359816L;

    /**
     * Constructs an InvalidKeyException with no detail message. A
     * detail message is a String that describes this particular
     * exception.
     * <p>
     *  构造一个InvalidKeyException,没有详细消息。详细消息是描述此特殊异常的字符串。
     * 
     */
    public InvalidKeyException() {
        super();
    }

    /**
     * Constructs an InvalidKeyException with the specified detail
     * message. A detail message is a String that describes this
     * particular exception.
     *
     * <p>
     *  构造具有指定详细消息的InvalidKeyException。详细消息是描述此特殊异常的字符串。
     * 
     * 
     * @param msg the detail message.
     */
    public InvalidKeyException(String msg) {
        super(msg);
    }

    /**
     * Creates a {@code InvalidKeyException} with the specified
     * detail message and cause.
     *
     * <p>
     *  使用指定的详细消息和原因创建{@code InvalidKeyException}。
     * 
     * 
     * @param message the detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method).
     * @param cause the cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A {@code null} value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     * @since 1.5
     */
    public InvalidKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a {@code InvalidKeyException} with the specified cause
     * and a detail message of {@code (cause==null ? null : cause.toString())}
     * (which typically contains the class and detail message of
     * {@code cause}).
     *
     * <p>
     *  创建具有指定原因的{@code InvalidKeyException}和{@code(cause == null?null：cause.toString())}的详细消息(通常包含{@code cause}
     * 的类和详细信息) 。
     * 
     * @param cause the cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A {@code null} value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     * @since 1.5
     */
    public InvalidKeyException(Throwable cause) {
        super(cause);
    }
}
