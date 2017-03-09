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
 * A runtime exception for Provider exceptions (such as
 * misconfiguration errors or unrecoverable internal errors),
 * which may be subclassed by Providers to
 * throw specialized, provider-specific runtime errors.
 *
 * <p>
 *  提供程序异常的运行时异常(例如配置错误或不可恢复的内部错误),可能由提供程序子类化以抛出专用的提供程序特定的运行时错误。
 * 
 * 
 * @author Benjamin Renaud
 */
public class ProviderException extends RuntimeException {

    private static final long serialVersionUID = 5256023526693665674L;

    /**
     * Constructs a ProviderException with no detail message. A
     * detail message is a String that describes this particular
     * exception.
     * <p>
     *  构造一个没有详细消息的ProviderException。详细消息是描述此特殊异常的字符串。
     * 
     */
    public ProviderException() {
        super();
    }

    /**
     * Constructs a ProviderException with the specified detail
     * message. A detail message is a String that describes this
     * particular exception.
     *
     * <p>
     *  构造具有指定详细消息的ProviderException。详细消息是描述此特殊异常的字符串。
     * 
     * 
     * @param s the detail message.
     */
    public ProviderException(String s) {
        super(s);
    }

    /**
     * Creates a {@code ProviderException} with the specified
     * detail message and cause.
     *
     * <p>
     *  创建具有指定的详细消息和原因的{@code ProviderException}。
     * 
     * 
     * @param message the detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method).
     * @param cause the cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A {@code null} value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     * @since 1.5
     */
    public ProviderException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a {@code ProviderException} with the specified cause
     * and a detail message of {@code (cause==null ? null : cause.toString())}
     * (which typically contains the class and detail message of
     * {@code cause}).
     *
     * <p>
     *  创建具有指定原因和{@code(cause == null?null：cause.toString())}(通常包含{@code cause}的类和详细信息)的详细消息的{@code ProviderException}
     *  。
     * 
     * @param cause the cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A {@code null} value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     * @since 1.5
     */
    public ProviderException(Throwable cause) {
        super(cause);
    }
}
