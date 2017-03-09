/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2011, Oracle and/or its affiliates. All rights reserved.
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
 * An {@code Error} is a subclass of {@code Throwable}
 * that indicates serious problems that a reasonable application
 * should not try to catch. Most such errors are abnormal conditions.
 * The {@code ThreadDeath} error, though a "normal" condition,
 * is also a subclass of {@code Error} because most applications
 * should not try to catch it.
 * <p>
 * A method is not required to declare in its {@code throws}
 * clause any subclasses of {@code Error} that might be thrown
 * during the execution of the method but not caught, since these
 * errors are abnormal conditions that should never occur.
 *
 * That is, {@code Error} and its subclasses are regarded as unchecked
 * exceptions for the purposes of compile-time checking of exceptions.
 *
 * <p>
 *  {@code Error}是{@code Throwable}的子类,表示合理的应用程序不应该尝试捕获的严重问题。大多数这样的错误是异常条件。
 *  {@code ThreadDeath}错误虽然是"正常"条件,但也是{@code Error}的子类,因为大多数应用程序不应该尝试捕获它。
 * <p>
 *  一个方法不需要在其{@code throws}子句中声明{@code Error}的任何子类,它可能在方法执行期间抛出,但没有被捕获,因为这些错误是不应该发生的异常情况。
 * 
 *  也就是说,为了编译时检查异常,{@code Error}及其子类被视为未检查的异常。
 * 
 * 
 * @author  Frank Yellin
 * @see     java.lang.ThreadDeath
 * @jls 11.2 Compile-Time Checking of Exceptions
 * @since   JDK1.0
 */
public class Error extends Throwable {
    static final long serialVersionUID = 4980196508277280342L;

    /**
     * Constructs a new error with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     * <p>
     *  使用{@code null}作为其详细消息构造一个新错误。原因未初始化,并可能随后通过调用{@link #initCause}初始化。
     * 
     */
    public Error() {
        super();
    }

    /**
     * Constructs a new error with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * <p>
     *  使用指定的详细消息构造新错误。原因未初始化,并可能随后通过调用{@link #initCause}初始化。
     * 
     * 
     * @param   message   the detail message. The detail message is saved for
     *          later retrieval by the {@link #getMessage()} method.
     */
    public Error(String message) {
        super(message);
    }

    /**
     * Constructs a new error with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this error's detail message.
     *
     * <p>
     *  使用指定的详细消息和原因构造新错误。 <p>请注意,与{@code cause}相关联的详细信息</i>不会自动并入此错误的详细信息中。
     * 
     * 
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link #getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A {@code null} value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @since  1.4
     */
    public Error(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new error with the specified cause and a detail
     * message of {@code (cause==null ? null : cause.toString())} (which
     * typically contains the class and detail message of {@code cause}).
     * This constructor is useful for errors that are little more than
     * wrappers for other throwables.
     *
     * <p>
     * 使用指定的原因和{@code(cause == null?null：cause.toString())}(通常包含{@code cause}的类和详细信息)的详细消息构造一个新错误。
     * 这个构造函数对于其他throwables的包装器的错误很有用。
     * 
     * 
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A {@code null} value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @since  1.4
     */
    public Error(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new error with the specified detail message,
     * cause, suppression enabled or disabled, and writable stack
     * trace enabled or disabled.
     *
     * <p>
     * 
     * @param  message the detail message.
     * @param cause the cause.  (A {@code null} value is permitted,
     * and indicates that the cause is nonexistent or unknown.)
     * @param enableSuppression whether or not suppression is enabled
     *                          or disabled
     * @param writableStackTrace whether or not the stack trace should
     *                           be writable
     *
     * @since 1.7
     */
    protected Error(String message, Throwable cause,
                    boolean enableSuppression,
                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
