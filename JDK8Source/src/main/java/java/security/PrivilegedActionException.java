/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * This exception is thrown by
 * {@code doPrivileged(PrivilegedExceptionAction)} and
 * {@code doPrivileged(PrivilegedExceptionAction,
 * AccessControlContext context)} to indicate
 * that the action being performed threw a checked exception.  The exception
 * thrown by the action can be obtained by calling the
 * {@code getException} method.  In effect, an
 * {@code PrivilegedActionException} is a "wrapper"
 * for an exception thrown by a privileged action.
 *
 * <p>As of release 1.4, this exception has been retrofitted to conform to
 * the general purpose exception-chaining mechanism.  The "exception thrown
 * by the privileged computation" that is provided at construction time and
 * accessed via the {@link #getException()} method is now known as the
 * <i>cause</i>, and may be accessed via the {@link Throwable#getCause()}
 * method, as well as the aforementioned "legacy method."
 *
 * <p>
 *  {@code doPrivileged(PrivilegedExceptionAction)}和{@code doPrivileged(PrivilegedExceptionAction,AccessControlContext context)}
 * 抛出此异常,以指示正在执行的操作抛出已检查的异常。
 * 可以通过调用{@code getException}方法来获取操作抛出的异常。实际上,{@code PrivilegedActionException}是特权操作抛出的异常的"包装器"。
 * 
 *  <p>自版本1.4起,此异常已经过改进以符合通用的异常链接机制。
 * 在构建时提供并通过{@link #getException()}方法访问的"特权计算抛出的异常"现在称为<i> cause </i>,可以通过{@链接Throwable#getCause()}方法,以及
 * 上述"遗留方法"。
 *  <p>自版本1.4起,此异常已经过改进以符合通用的异常链接机制。
 * 
 * 
 * @see PrivilegedExceptionAction
 * @see AccessController#doPrivileged(PrivilegedExceptionAction)
 * @see AccessController#doPrivileged(PrivilegedExceptionAction,AccessControlContext)
 */
public class PrivilegedActionException extends Exception {
    // use serialVersionUID from JDK 1.2.2 for interoperability
    private static final long serialVersionUID = 4724086851538908602L;

    /**
    /* <p>
    /* 
     * @serial
     */
    private Exception exception;

    /**
     * Constructs a new PrivilegedActionException &quot;wrapping&quot;
     * the specific Exception.
     *
     * <p>
     *  构造新的PrivilegedActionException"wrapping"具体异常。
     * 
     * 
     * @param exception The exception thrown
     */
    public PrivilegedActionException(Exception exception) {
        super((Throwable)null);  // Disallow initCause
        this.exception = exception;
    }

    /**
     * Returns the exception thrown by the privileged computation that
     * resulted in this {@code PrivilegedActionException}.
     *
     * <p>This method predates the general-purpose exception chaining facility.
     * The {@link Throwable#getCause()} method is now the preferred means of
     * obtaining this information.
     *
     * <p>
     *  返回导致此{@code PrivilegedActionException}的特权计算抛出的异常。
     * 
     *  <p>此方法早于通用异常链接设施。 {@link Throwable#getCause()}方法现在是获取此信息的首选方法。
     * 
     * 
     * @return the exception thrown by the privileged computation that
     *         resulted in this {@code PrivilegedActionException}.
     * @see PrivilegedExceptionAction
     * @see AccessController#doPrivileged(PrivilegedExceptionAction)
     * @see AccessController#doPrivileged(PrivilegedExceptionAction,
     *                                            AccessControlContext)
     */
    public Exception getException() {
        return exception;
    }

    /**
     * Returns the cause of this exception (the exception thrown by
     * the privileged computation that resulted in this
     * {@code PrivilegedActionException}).
     *
     * <p>
     * 
     * @return  the cause of this exception.
     * @since   1.4
     */
    public Throwable getCause() {
        return exception;
    }

    public String toString() {
        String s = getClass().getName();
        return (exception != null) ? (s + ": " + exception.toString()) : s;
    }
}
