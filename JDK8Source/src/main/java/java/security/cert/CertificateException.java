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

package java.security.cert;

import java.security.GeneralSecurityException;

/**
 * This exception indicates one of a variety of certificate problems.
 *
 * <p>
 *  此异常表示各种证书问题之一。
 * 
 * 
 * @author Hemma Prafullchandra
 * @see Certificate
 */
public class CertificateException extends GeneralSecurityException {

    private static final long serialVersionUID = 3192535253797119798L;

    /**
     * Constructs a certificate exception with no detail message. A detail
     * message is a String that describes this particular exception.
     * <p>
     *  构造一个没有详细消息的证书异常。详细消息是描述此特殊异常的字符串。
     * 
     */
    public CertificateException() {
        super();
    }

    /**
     * Constructs a certificate exception with the given detail
     * message. A detail message is a String that describes this
     * particular exception.
     *
     * <p>
     *  构造具有给定详细消息的证书异常。详细消息是描述此特殊异常的字符串。
     * 
     * 
     * @param msg the detail message.
     */
    public CertificateException(String msg) {
        super(msg);
    }

    /**
     * Creates a {@code CertificateException} with the specified
     * detail message and cause.
     *
     * <p>
     *  使用指定的详细消息和原因创建{@code CertificateException}。
     * 
     * 
     * @param message the detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method).
     * @param cause the cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A {@code null} value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     * @since 1.5
     */
    public CertificateException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a {@code CertificateException} with the specified cause
     * and a detail message of {@code (cause==null ? null : cause.toString())}
     * (which typically contains the class and detail message of
     * {@code cause}).
     *
     * <p>
     *  创建具有指定原因和{@code(cause == null?null：cause.toString())}(通常包含{@code cause}的类和详细信息)的详细消息的{@code CertificateException}
     *  。
     * 
     * @param cause the cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A {@code null} value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     * @since 1.5
     */
    public CertificateException(Throwable cause) {
        super(cause);
    }
}
