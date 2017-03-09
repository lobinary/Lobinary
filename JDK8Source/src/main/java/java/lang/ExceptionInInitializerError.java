/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2000, Oracle and/or its affiliates. All rights reserved.
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

package java.lang;

/**
 * Signals that an unexpected exception has occurred in a static initializer.
 * An <code>ExceptionInInitializerError</code> is thrown to indicate that an
 * exception occurred during evaluation of a static initializer or the
 * initializer for a static variable.
 *
 * <p>As of release 1.4, this exception has been retrofitted to conform to
 * the general purpose exception-chaining mechanism.  The "saved throwable
 * object" that may be provided at construction time and accessed via
 * the {@link #getException()} method is now known as the <i>cause</i>,
 * and may be accessed via the {@link Throwable#getCause()} method, as well
 * as the aforementioned "legacy method."
 *
 * <p>
 *  表示在静态初始值设定程序中发生意外异常。抛出一个<code> ExceptionInInitializerError </code>以指示在评估静态初始化器或静态变量的初始化器时发生异常。
 * 
 *  <p>自版本1.4起,此异常已经过改进以符合通用的异常链接机制。
 * 可以在构建时提供并通过{@link #getException()}方法访问的"保存的可抛弃对象"现在称为<i>原因</i>,并且可以通过{@link Throwable #getCause()}方法,
 * 以及上述的"传统方法"。
 *  <p>自版本1.4起,此异常已经过改进以符合通用的异常链接机制。
 * 
 * 
 * @author  Frank Yellin
 * @since   JDK1.1
 */
public class ExceptionInInitializerError extends LinkageError {
    /**
     * Use serialVersionUID from JDK 1.1.X for interoperability
     * <p>
     *  使用JDK 1.1.X中的serialVersionUID实现互操作性
     * 
     */
    private static final long serialVersionUID = 1521711792217232256L;

    /**
     * This field holds the exception if the
     * ExceptionInInitializerError(Throwable thrown) constructor was
     * used to instantiate the object
     *
     * <p>
     *  如果ExceptionInInitializerError(Throwable thrown)构造函数用于实例化对象,此字段保存异常
     * 
     * 
     * @serial
     *
     */
    private Throwable exception;

    /**
     * Constructs an <code>ExceptionInInitializerError</code> with
     * <code>null</code> as its detail message string and with no saved
     * throwable object.
     * A detail message is a String that describes this particular exception.
     * <p>
     *  构造具有<code> null </code>作为其详细消息字符串的<code> ExceptionInInitializerError </code>,并且没有保存的throwable对象。
     * 详细消息是描述此特殊异常的字符串。
     * 
     */
    public ExceptionInInitializerError() {
        initCause(null);  // Disallow subsequent initCause
    }

    /**
     * Constructs a new <code>ExceptionInInitializerError</code> class by
     * saving a reference to the <code>Throwable</code> object thrown for
     * later retrieval by the {@link #getException()} method. The detail
     * message string is set to <code>null</code>.
     *
     * <p>
     *  通过保存对引发的<code> Throwable </code>对象来构造一个新的<code> ExceptionInInitializerError </code>类,以供以后通过{@link #getException()}
     * 方法检索。
     * 详细消息字符串设置为<code> null </code>。
     * 
     * 
     * @param thrown The exception thrown
     */
    public ExceptionInInitializerError(Throwable thrown) {
        initCause(null);  // Disallow subsequent initCause
        this.exception = thrown;
    }

    /**
     * Constructs an ExceptionInInitializerError with the specified detail
     * message string.  A detail message is a String that describes this
     * particular exception. The detail message string is saved for later
     * retrieval by the {@link Throwable#getMessage()} method. There is no
     * saved throwable object.
     *
     *
     * <p>
     * 构造具有指定的详细消息字符串的ExceptionInInitializerError。详细消息是描述此特殊异常的字符串。
     * 详细消息字符串保存以供以后通过{@link Throwable#getMessage()}方法检索。没有保存的可抛弃对象。
     * 
     * 
     * @param s the detail message
     */
    public ExceptionInInitializerError(String s) {
        super(s);
        initCause(null);  // Disallow subsequent initCause
    }

    /**
     * Returns the exception that occurred during a static initialization that
     * caused this error to be created.
     *
     * <p>This method predates the general-purpose exception chaining facility.
     * The {@link Throwable#getCause()} method is now the preferred means of
     * obtaining this information.
     *
     * <p>
     *  返回在导致创建此错误的静态初始化期间发生的异常。
     * 
     *  <p>此方法早于通用异常链接设施。 {@link Throwable#getCause()}方法现在是获取此信息的首选方法。
     * 
     * 
     * @return the saved throwable object of this
     *         <code>ExceptionInInitializerError</code>, or <code>null</code>
     *         if this <code>ExceptionInInitializerError</code> has no saved
     *         throwable object.
     */
    public Throwable getException() {
        return exception;
    }

    /**
     * Returns the cause of this error (the exception that occurred
     * during a static initialization that caused this error to be created).
     *
     * <p>
     * 
     * @return  the cause of this error or <code>null</code> if the
     *          cause is nonexistent or unknown.
     * @since   1.4
     */
    public Throwable getCause() {
        return exception;
    }
}
