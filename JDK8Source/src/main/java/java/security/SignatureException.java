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
 * This is the generic Signature exception.
 *
 * <p>
 *  这是通用的签名异常。
 * 
 * 
 * @author Benjamin Renaud
 */

public class SignatureException extends GeneralSecurityException {

    private static final long serialVersionUID = 7509989324975124438L;

    /**
     * Constructs a SignatureException with no detail message. A
     * detail message is a String that describes this particular
     * exception.
     * <p>
     *  构造一个没有详细消息的SignatureException。详细消息是描述此特殊异常的字符串。
     * 
     */
    public SignatureException() {
        super();
    }

    /**
     * Constructs a SignatureException with the specified detail
     * message.  A detail message is a String that describes this
     * particular exception.
     *
     * <p>
     *  构造具有指定详细消息的SignatureException。详细消息是描述此特殊异常的字符串。
     * 
     * 
     * @param msg the detail message.
     */
    public SignatureException(String msg) {
        super(msg);
    }

    /**
     * Creates a {@code SignatureException} with the specified
     * detail message and cause.
     *
     * <p>
     *  使用指定的详细消息和原因创建{@code SignatureException}。
     * 
     * 
     * @param message the detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method).
     * @param cause the cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A {@code null} value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     * @since 1.5
     */
    public SignatureException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a {@code SignatureException} with the specified cause
     * and a detail message of {@code (cause==null ? null : cause.toString())}
     * (which typically contains the class and detail message of
     * {@code cause}).
     *
     * <p>
     *  创建具有指定原因和{@code(cause == null?null：cause.toString())}(通常包含{@code cause}的类和详细信息)的详细消息的{@code SignatureException}
     *  。
     * 
     * @param cause the cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A {@code null} value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     * @since 1.5
     */
    public SignatureException(Throwable cause) {
        super(cause);
    }
}
