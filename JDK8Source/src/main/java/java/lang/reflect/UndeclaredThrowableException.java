/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2006, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.reflect;

/**
 * Thrown by a method invocation on a proxy instance if its invocation
 * handler's {@link InvocationHandler#invoke invoke} method throws a
 * checked exception (a {@code Throwable} that is not assignable
 * to {@code RuntimeException} or {@code Error}) that
 * is not assignable to any of the exception types declared in the
 * {@code throws} clause of the method that was invoked on the
 * proxy instance and dispatched to the invocation handler.
 *
 * <p>An {@code UndeclaredThrowableException} instance contains
 * the undeclared checked exception that was thrown by the invocation
 * handler, and it can be retrieved with the
 * {@code getUndeclaredThrowable()} method.
 * {@code UndeclaredThrowableException} extends
 * {@code RuntimeException}, so it is an unchecked exception
 * that wraps a checked exception.
 *
 * <p>As of release 1.4, this exception has been retrofitted to
 * conform to the general purpose exception-chaining mechanism.  The
 * "undeclared checked exception that was thrown by the invocation
 * handler" that may be provided at construction time and accessed via
 * the {@link #getUndeclaredThrowable()} method is now known as the
 * <i>cause</i>, and may be accessed via the {@link
 * Throwable#getCause()} method, as well as the aforementioned "legacy
 * method."
 *
 * <p>
 *  如果代理实例的调用处理程序的{@link InvocationHandler#invoke invoke}方法抛出一个已检查的异常(无法分配给{@code RuntimeException}或{@code Error}
 * 的{@code Throwable}),则通过代理实例上的方法调用抛出,不能分配给在代理实例上调用并分派给调用处理程序的方法的{@code throws}子句中声明的任何异常类型。
 * 
 *  <p> {@code UndeclaredThrowableException}实例包含调用处理程序抛出的未声明的已检查异常,可以使用{@code getUndeclaredThrowable()}方
 * 法检索该异常。
 *  {@code UndeclaredThrowableException} extends {@code RuntimeException},因此它是一个未检查的异常,它包含一个已检查的异常。
 * 
 *  <p>自版本1.4起,此异常已经过改进以符合通用的异常链接机制。
 * 可以在构建时提供并通过{@link #getUndeclaredThrowable()}方法访问的"由调用处理程序抛出的"未声明的检查异常现在称为<i>原因</i>,并且可以是通过{@link Throwable#getCause()}
 * 方法以及上述"传统方法"访问。
 *  <p>自版本1.4起,此异常已经过改进以符合通用的异常链接机制。
 * 
 * 
 * @author      Peter Jones
 * @see         InvocationHandler
 * @since       1.3
 */
public class UndeclaredThrowableException extends RuntimeException {
    static final long serialVersionUID = 330127114055056639L;

    /**
     * the undeclared checked exception that was thrown
     * <p>
     *  抛出的未声明的已检查异常
     * 
     * 
     * @serial
     */
    private Throwable undeclaredThrowable;

    /**
     * Constructs an {@code UndeclaredThrowableException} with the
     * specified {@code Throwable}.
     *
     * <p>
     * 使用指定的{@code Throwable}构造一个{@code UndeclaredThrowableException}。
     * 
     * 
     * @param   undeclaredThrowable the undeclared checked exception
     *          that was thrown
     */
    public UndeclaredThrowableException(Throwable undeclaredThrowable) {
        super((Throwable) null);  // Disallow initCause
        this.undeclaredThrowable = undeclaredThrowable;
    }

    /**
     * Constructs an {@code UndeclaredThrowableException} with the
     * specified {@code Throwable} and a detail message.
     *
     * <p>
     *  使用指定的{@code Throwable}和详细消息构造{@code UndeclaredThrowableException}。
     * 
     * 
     * @param   undeclaredThrowable the undeclared checked exception
     *          that was thrown
     * @param   s the detail message
     */
    public UndeclaredThrowableException(Throwable undeclaredThrowable,
                                        String s)
    {
        super(s, null);  // Disallow initCause
        this.undeclaredThrowable = undeclaredThrowable;
    }

    /**
     * Returns the {@code Throwable} instance wrapped in this
     * {@code UndeclaredThrowableException}, which may be {@code null}.
     *
     * <p>This method predates the general-purpose exception chaining facility.
     * The {@link Throwable#getCause()} method is now the preferred means of
     * obtaining this information.
     *
     * <p>
     *  返回包含在此{@code UndeclaredThrowableException}中的{@code Throwable}实例,它可能是{@code null}。
     * 
     *  <p>此方法早于通用异常链接设施。 {@link Throwable#getCause()}方法现在是获取此信息的首选方法。
     * 
     * 
     * @return the undeclared checked exception that was thrown
     */
    public Throwable getUndeclaredThrowable() {
        return undeclaredThrowable;
    }

    /**
     * Returns the cause of this exception (the {@code Throwable}
     * instance wrapped in this {@code UndeclaredThrowableException},
     * which may be {@code null}).
     *
     * <p>
     * 
     * @return  the cause of this exception.
     * @since   1.4
     */
    public Throwable getCause() {
        return undeclaredThrowable;
    }
}
