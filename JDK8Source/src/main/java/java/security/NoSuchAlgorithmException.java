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
 * This exception is thrown when a particular cryptographic algorithm is
 * requested but is not available in the environment.
 *
 * <p>
 *  当请求特定的加密算法但在环境中不可用时抛出此异常。
 * 
 * 
 * @author Benjamin Renaud
 */

public class NoSuchAlgorithmException extends GeneralSecurityException {

    private static final long serialVersionUID = -7443947487218346562L;

    /**
     * Constructs a NoSuchAlgorithmException with no detail
     * message. A detail message is a String that describes this
     * particular exception.
     * <p>
     *  构造没有详细消息的NoSuchAlgorithmException。详细消息是描述此特殊异常的字符串。
     * 
     */
    public NoSuchAlgorithmException() {
        super();
    }

    /**
     * Constructs a NoSuchAlgorithmException with the specified
     * detail message. A detail message is a String that describes
     * this particular exception, which may, for example, specify which
     * algorithm is not available.
     *
     * <p>
     *  构造具有指定详细消息的NoSuchAlgorithmException。详细消息是描述该特定异常的字符串,其可以例如指定哪个算法不可用。
     * 
     * 
     * @param msg the detail message.
     */
    public NoSuchAlgorithmException(String msg) {
        super(msg);
    }

    /**
     * Creates a {@code NoSuchAlgorithmException} with the specified
     * detail message and cause.
     *
     * <p>
     *  使用指定的详细消息和原因创建{@code NoSuchAlgorithmException}。
     * 
     * 
     * @param message the detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method).
     * @param cause the cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A {@code null} value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     * @since 1.5
     */
    public NoSuchAlgorithmException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a {@code NoSuchAlgorithmException} with the specified cause
     * and a detail message of {@code (cause==null ? null : cause.toString())}
     * (which typically contains the class and detail message of
     * {@code cause}).
     *
     * <p>
     *  创建具有指定原因和{@code(cause == null?null：cause.toString())}(通常包含{@code cause}的类和详细信息)的详细消息的{@code NoSuchAlgorithmException}
     *  。
     * 
     * @param cause the cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A {@code null} value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     * @since 1.5
     */
    public NoSuchAlgorithmException(Throwable cause) {
        super(cause);
    }
}
