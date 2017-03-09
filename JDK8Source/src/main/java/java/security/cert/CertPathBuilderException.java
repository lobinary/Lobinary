/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * An exception indicating one of a variety of problems encountered when
 * building a certification path with a {@code CertPathBuilder}.
 * <p>
 * A {@code CertPathBuilderException} provides support for wrapping
 * exceptions. The {@link #getCause getCause} method returns the throwable,
 * if any, that caused this exception to be thrown.
 * <p>
 * <b>Concurrent Access</b>
 * <p>
 * Unless otherwise specified, the methods defined in this class are not
 * thread-safe. Multiple threads that need to access a single
 * object concurrently should synchronize amongst themselves and
 * provide the necessary locking. Multiple threads each manipulating
 * separate objects need not synchronize.
 *
 * <p>
 *  一个异常,指示使用{@code CertPathBuilder}构建认证路径时遇到的各种问题之一。
 * <p>
 *  {@code CertPathBuilderException}提供对包装异常的支持。 {@link #getCause getCause}方法返回引发此异常的throwable(如果有)。
 * <p>
 *  <b>并行访问</b>
 * <p>
 *  除非另有说明,否则此类中定义的方法不是线程安全的。需要并发访问单个对象的多个线程应在它们之间同步并提供必要的锁定。每个操作单独对象的多个线程不需要同步。
 * 
 * 
 * @see CertPathBuilder
 *
 * @since       1.4
 * @author      Sean Mullan
 */
public class CertPathBuilderException extends GeneralSecurityException {

    private static final long serialVersionUID = 5316471420178794402L;

    /**
     * Creates a {@code CertPathBuilderException} with {@code null}
     * as its detail message.
     * <p>
     *  使用{@code null}作为其详细信息创建{@code CertPathBuilderException}。
     * 
     */
    public CertPathBuilderException() {
        super();
    }

    /**
     * Creates a {@code CertPathBuilderException} with the given
     * detail message. The detail message is a {@code String} that
     * describes this particular exception in more detail.
     *
     * <p>
     *  使用给定的详细信息创建{@code CertPathBuilderException}。详细消息是{@code String},更详细地描述此特殊异常。
     * 
     * 
     * @param msg the detail message
     */
    public CertPathBuilderException(String msg) {
        super(msg);
    }

    /**
     * Creates a {@code CertPathBuilderException} that wraps the specified
     * throwable. This allows any exception to be converted into a
     * {@code CertPathBuilderException}, while retaining information
     * about the wrapped exception, which may be useful for debugging. The
     * detail message is set to ({@code cause==null ? null : cause.toString()})
     * (which typically contains the class and detail message of
     * cause).
     *
     * <p>
     *  创建一个包装指定的throwable的{@code CertPathBuilderException}。
     * 这允许任何异常转换为{@code CertPathBuilderException},同时保留关于被包装的异常的信息,这可能对调试有用。
     * 详细消息设置为({@code cause == null?null：cause.toString()})(通常包含原因的类和详细消息)。
     * 
     * 
     * @param cause the cause (which is saved for later retrieval by the
     * {@link #getCause getCause()} method). (A {@code null} value is
     * permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public CertPathBuilderException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates a {@code CertPathBuilderException} with the specified
     * detail message and cause.
     *
     * <p>
     * 
     * @param msg the detail message
     * @param  cause the cause (which is saved for later retrieval by the
     * {@link #getCause getCause()} method). (A {@code null} value is
     * permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public CertPathBuilderException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
