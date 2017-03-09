/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2004, Oracle and/or its affiliates. All rights reserved.
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
 * InvocationTargetException is a checked exception that wraps
 * an exception thrown by an invoked method or constructor.
 *
 * <p>As of release 1.4, this exception has been retrofitted to conform to
 * the general purpose exception-chaining mechanism.  The "target exception"
 * that is provided at construction time and accessed via the
 * {@link #getTargetException()} method is now known as the <i>cause</i>,
 * and may be accessed via the {@link Throwable#getCause()} method,
 * as well as the aforementioned "legacy method."
 *
 * <p>
 *  InvocationTargetException是一个检查的异常,它包含由调用的方法或构造函数抛出的异常。
 * 
 *  <p>自版本1.4起,此异常已经过改进以符合通用的异常链接机制。
 * 在构建时提供并通过{@link #getTargetException()}方法访问的"目标异常"现在称为<i>原因</i>,并且可以通过{@link Throwable#getCause ()}方法,
 * 以及上述"遗留方法"。
 *  <p>自版本1.4起,此异常已经过改进以符合通用的异常链接机制。
 * 
 * 
 * @see Method
 * @see Constructor
 */
public class InvocationTargetException extends ReflectiveOperationException {
    /**
     * Use serialVersionUID from JDK 1.1.X for interoperability
     * <p>
     *  使用JDK 1.1.X中的serialVersionUID实现互操作性
     * 
     */
    private static final long serialVersionUID = 4085088731926701167L;

     /**
     * This field holds the target if the
     * InvocationTargetException(Throwable target) constructor was
     * used to instantiate the object
     *
     * <p>
     *  如果InvocationTargetException(Throwable目标)构造函数用于实例化对象,则此字段保存目标
     * 
     * 
     * @serial
     *
     */
    private Throwable target;

    /**
     * Constructs an {@code InvocationTargetException} with
     * {@code null} as the target exception.
     * <p>
     *  使用{@code null}作为目标异常构造{@code InvocationTargetException}。
     * 
     */
    protected InvocationTargetException() {
        super((Throwable)null);  // Disallow initCause
    }

    /**
     * Constructs a InvocationTargetException with a target exception.
     *
     * <p>
     *  构造具有目标异常的InvocationTargetException。
     * 
     * 
     * @param target the target exception
     */
    public InvocationTargetException(Throwable target) {
        super((Throwable)null);  // Disallow initCause
        this.target = target;
    }

    /**
     * Constructs a InvocationTargetException with a target exception
     * and a detail message.
     *
     * <p>
     *  构造具有目标异常和详细消息的InvocationTargetException。
     * 
     * 
     * @param target the target exception
     * @param s      the detail message
     */
    public InvocationTargetException(Throwable target, String s) {
        super(s, null);  // Disallow initCause
        this.target = target;
    }

    /**
     * Get the thrown target exception.
     *
     * <p>This method predates the general-purpose exception chaining facility.
     * The {@link Throwable#getCause()} method is now the preferred means of
     * obtaining this information.
     *
     * <p>
     *  获取抛出的目标异常。
     * 
     *  <p>此方法早于通用异常链接设施。 {@link Throwable#getCause()}方法现在是获取此信息的首选方法。
     * 
     * 
     * @return the thrown target exception (cause of this exception).
     */
    public Throwable getTargetException() {
        return target;
    }

    /**
     * Returns the cause of this exception (the thrown target exception,
     * which may be {@code null}).
     *
     * <p>
     * 
     * @return  the cause of this exception.
     * @since   1.4
     */
    public Throwable getCause() {
        return target;
    }
}
