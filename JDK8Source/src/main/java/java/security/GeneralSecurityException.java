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

package java.security;

/**
 * The {@code GeneralSecurityException} class is a generic
 * security exception class that provides type safety for all the
 * security-related exception classes that extend from it.
 *
 * <p>
 *  {@code GeneralSecurityException}类是一个通用安全异常类,为从其延伸的所有与安全相关的异常类提供类型安全性。
 * 
 * 
 * @author Jan Luehe
 */

public class GeneralSecurityException extends Exception {

    private static final long serialVersionUID = 894798122053539237L;

    /**
     * Constructs a GeneralSecurityException with no detail message.
     * <p>
     *  构造一个没有详细消息的GeneralSecurityException。
     * 
     */
    public GeneralSecurityException() {
        super();
    }

    /**
     * Constructs a GeneralSecurityException with the specified detail
     * message.
     * A detail message is a String that describes this particular
     * exception.
     *
     * <p>
     *  构造具有指定详细消息的GeneralSecurityException。详细消息是描述此特殊异常的字符串。
     * 
     * 
     * @param msg the detail message.
     */
    public GeneralSecurityException(String msg) {
        super(msg);
    }

    /**
     * Creates a {@code GeneralSecurityException} with the specified
     * detail message and cause.
     *
     * <p>
     *  使用指定的详细消息和原因创建{@code GeneralSecurityException}。
     * 
     * 
     * @param message the detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method).
     * @param cause the cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A {@code null} value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     * @since 1.5
     */
    public GeneralSecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a {@code GeneralSecurityException} with the specified cause
     * and a detail message of {@code (cause==null ? null : cause.toString())}
     * (which typically contains the class and detail message of
     * {@code cause}).
     *
     * <p>
     *  创建具有指定原因的{@code GeneralSecurityException}和{@code(cause == null?null：cause.toString())}的详细消息(通常包含{@code cause}
     * 的类和详细信息) 。
     * 
     * @param cause the cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A {@code null} value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     * @since 1.5
     */
    public GeneralSecurityException(Throwable cause) {
        super(cause);
    }
}
