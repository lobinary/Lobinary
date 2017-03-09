/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * CRL (Certificate Revocation List) Exception.
 *
 * <p>
 *  CRL(证书吊销列表)异常。
 * 
 * 
 * @author Hemma Prafullchandra
 */
public class CRLException extends GeneralSecurityException {

    private static final long serialVersionUID = -6694728944094197147L;

   /**
     * Constructs a CRLException with no detail message. A
     * detail message is a String that describes this particular
     * exception.
     * <p>
     *  构造一个没有详细消息的CRLException。详细消息是描述此特殊异常的字符串。
     * 
     */
    public CRLException() {
        super();
    }

    /**
     * Constructs a CRLException with the specified detail
     * message. A detail message is a String that describes this
     * particular exception.
     *
     * <p>
     *  构造具有指定详细消息的CRLException。详细消息是描述此特殊异常的字符串。
     * 
     * 
     * @param message the detail message.
     */
    public CRLException(String message) {
        super(message);
    }

    /**
     * Creates a {@code CRLException} with the specified
     * detail message and cause.
     *
     * <p>
     *  使用指定的详细消息和原因创建{@code CRLException}。
     * 
     * 
     * @param message the detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method).
     * @param cause the cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A {@code null} value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     * @since 1.5
     */
    public CRLException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a {@code CRLException} with the specified cause
     * and a detail message of {@code (cause==null ? null : cause.toString())}
     * (which typically contains the class and detail message of
     * {@code cause}).
     *
     * <p>
     *  创建具有指定原因的{@code CRLException}和{@code(cause == null?null：cause.toString())}的详细消息(通常包含{@code cause}的类和
     * 详细消息) 。
     * 
     * @param cause the cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A {@code null} value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     * @since 1.5
     */
    public CRLException(Throwable cause) {
        super(cause);
    }
}
