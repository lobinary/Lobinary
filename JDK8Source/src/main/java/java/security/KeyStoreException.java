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
 * This is the generic KeyStore exception.
 *
 * <p>
 *  这是一般的KeyStore异常。
 * 
 * 
 * @author Jan Luehe
 *
 *
 * @since 1.2
 */

public class KeyStoreException extends GeneralSecurityException {

    private static final long serialVersionUID = -1119353179322377262L;

    /**
     * Constructs a KeyStoreException with no detail message.  (A
     * detail message is a String that describes this particular
     * exception.)
     * <p>
     *  构造一个没有详细消息的KeyStoreException。 (详细消息是描述此特殊异常的字符串。)
     * 
     */
    public KeyStoreException() {
        super();
    }

    /**
     * Constructs a KeyStoreException with the specified detail
     * message.  (A detail message is a String that describes this
     * particular exception.)
     *
     * <p>
     *  构造具有指定详细消息的KeyStoreException。 (详细消息是描述此特殊异常的字符串。)
     * 
     * 
     * @param msg the detail message.
     */
   public KeyStoreException(String msg) {
       super(msg);
    }

    /**
     * Creates a {@code KeyStoreException} with the specified
     * detail message and cause.
     *
     * <p>
     *  使用指定的详细消息和原因创建{@code KeyStoreException}。
     * 
     * 
     * @param message the detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method).
     * @param cause the cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A {@code null} value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     * @since 1.5
     */
    public KeyStoreException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a {@code KeyStoreException} with the specified cause
     * and a detail message of {@code (cause==null ? null : cause.toString())}
     * (which typically contains the class and detail message of
     * {@code cause}).
     *
     * <p>
     *  创建具有指定原因的{@code KeyStoreException}和{@code(cause == null?null：cause.toString())}的详细消息(通常包含{@code cause}
     * 的类和详细信息) 。
     * 
     * @param cause the cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A {@code null} value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     * @since 1.5
     */
    public KeyStoreException(Throwable cause) {
        super(cause);
    }
}
