/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.security.sasl;

import java.io.IOException;

/**
 * This class represents an error that has occurred when using SASL.
 *
 * <p>
 *  此类表示在使用SASL时发生的错误。
 * 
 * 
 * @since 1.5
 *
 * @author Rosanna Lee
 * @author Rob Weltman
 */

public class SaslException extends IOException {
    /**
     * The possibly null root cause exception.
     * <p>
     *  可能为空的根本原因异常。
     * 
     * 
     * @serial
     */
    // Required for serialization interoperability with JSR 28
    private Throwable _exception;

    /**
     * Constructs a new instance of {@code SaslException}.
     * The root exception and the detailed message are null.
     * <p>
     *  构造{@code SaslException}的新实例。根异常和详细消息为null。
     * 
     */
    public SaslException () {
        super();
    }

    /**
     * Constructs a new instance of {@code SaslException} with a detailed message.
     * The root exception is null.
     * <p>
     *  使用详细消息构造{@code SaslException}的新实例。根异常为null。
     * 
     * 
     * @param detail A possibly null string containing details of the exception.
     *
     * @see java.lang.Throwable#getMessage
     */
    public SaslException (String detail) {
        super(detail);
    }

    /**
     * Constructs a new instance of {@code SaslException} with a detailed message
     * and a root exception.
     * For example, a SaslException might result from a problem with
     * the callback handler, which might throw a NoSuchCallbackException if
     * it does not support the requested callback, or throw an IOException
     * if it had problems obtaining data for the callback. The
     * SaslException's root exception would be then be the exception thrown
     * by the callback handler.
     *
     * <p>
     *  构造具有详细消息和根异常的{@code SaslException}的新实例。
     * 例如,SaslException可能来自回调处理程序的问题,如果它不支持请求的回调,它可能会抛出一个NoSuchCallbackException,或者如果获取回调的数据有问题,则抛出IOExcepti
     * on。
     *  构造具有详细消息和根异常的{@code SaslException}的新实例。 SaslException的根异常将是由回调处理程序抛出的异常。
     * 
     * 
     * @param detail A possibly null string containing details of the exception.
     * @param ex A possibly null root exception that caused this exception.
     *
     * @see java.lang.Throwable#getMessage
     * @see #getCause
     */
    public SaslException (String detail, Throwable ex) {
        super(detail);
        if (ex != null) {
            initCause(ex);
        }
    }

    /*
     * Override Throwable.getCause() to ensure deserialized object from
     * JSR 28 would return same value for getCause() (i.e., _exception).
     * <p>
     *  重写Throwable.getCause()以确保来自JSR 28的反序列化对象将为getCause()返回相同的值(即_exception)。
     * 
     */
    public Throwable getCause() {
        return _exception;
    }

    /*
     * Override Throwable.initCause() to match getCause() by updating
     * _exception as well.
     * <p>
     *  覆盖Throwable.initCause()以通过更新_exception也匹配getCause()。
     * 
     */
    public Throwable initCause(Throwable cause) {
        super.initCause(cause);
        _exception = cause;
        return this;
    }

    /**
     * Returns the string representation of this exception.
     * The string representation contains
     * this exception's class name, its detailed message, and if
     * it has a root exception, the string representation of the root
     * exception. This string representation
     * is meant for debugging and not meant to be interpreted
     * programmatically.
     * <p>
     *  返回此异常的字符串表示形式。字符串表示包含此异常的类名称及其详细消息,如果它有根异常,则表示根异常的字符串表示。此字符串表示形式用于调试,而不是以编程方式解释。
     * 
     * @return The non-null string representation of this exception.
     * @see java.lang.Throwable#getMessage
     */
    // Override Throwable.toString() to conform to JSR 28
    public String toString() {
        String answer = super.toString();
        if (_exception != null && _exception != this) {
            answer += " [Caused by " + _exception.toString() + "]";
        }
        return answer;
    }

    /** Use serialVersionUID from JSR 28 RI for interoperability */
    private static final long serialVersionUID = 4579784287983423626L;
}
