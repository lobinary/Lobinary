/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2003, Oracle and/or its affiliates. All rights reserved.
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

package java.rmi.server;

/**
 * A <code>ServerCloneException</code> is thrown if a remote exception occurs
 * during the cloning of a <code>UnicastRemoteObject</code>.
 *
 * <p>As of release 1.4, this exception has been retrofitted to conform to
 * the general purpose exception-chaining mechanism.  The "nested exception"
 * that may be provided at construction time and accessed via the public
 * {@link #detail} field is now known as the <i>cause</i>, and may be
 * accessed via the {@link Throwable#getCause()} method, as well as
 * the aforementioned "legacy field."
 *
 * <p>Invoking the method {@link Throwable#initCause(Throwable)} on an
 * instance of <code>ServerCloneException</code> always throws {@link
 * IllegalStateException}.
 *
 * <p>
 *  如果在克隆<code> UnicastRemoteObject </code>期间发生远程异常,则会抛出<code> ServerCloneException </code>。
 * 
 *  <p>自版本1.4起,此异常已经过改进以符合通用的异常链接机制。
 * 可以在构建时提供并通过公共{@link #detail}字段访问的"嵌套异常"现在称为<i>原因</i>,并且可以通过{@link Throwable#getCause ()}方法,以及前述的"遗留字段
 * "。
 *  <p>自版本1.4起,此异常已经过改进以符合通用的异常链接机制。
 * 
 *  <p>在<code> ServerCloneException </code>的实例上调用方法{@link Throwable#initCause(Throwable)}总是引发{@link IllegalStateException}
 * 。
 * 
 * 
 * @author  Ann Wollrath
 * @since   JDK1.1
 * @see     java.rmi.server.UnicastRemoteObject#clone()
 */
public class ServerCloneException extends CloneNotSupportedException {

    /**
     * The cause of the exception.
     *
     * <p>This field predates the general-purpose exception chaining facility.
     * The {@link Throwable#getCause()} method is now the preferred means of
     * obtaining this information.
     *
     * <p>
     *  异常的原因。
     * 
     *  <p>此字段早于通用的例外链接设施。 {@link Throwable#getCause()}方法现在是获取此信息的首选方法。
     * 
     * 
     * @serial
     */
    public Exception detail;

    /* indicate compatibility with JDK 1.1.x version of class */
    private static final long serialVersionUID = 6617456357664815945L;

    /**
     * Constructs a <code>ServerCloneException</code> with the specified
     * detail message.
     *
     * <p>
     *  使用指定的详细消息构造<code> ServerCloneException </code>。
     * 
     * 
     * @param s the detail message.
     */
    public ServerCloneException(String s) {
        super(s);
        initCause(null);  // Disallow subsequent initCause
    }

    /**
     * Constructs a <code>ServerCloneException</code> with the specified
     * detail message and cause.
     *
     * <p>
     *  构造具有指定的详细消息和原因的<code> ServerCloneException </code>。
     * 
     * 
     * @param s the detail message.
     * @param cause the cause
     */
    public ServerCloneException(String s, Exception cause) {
        super(s);
        initCause(null);  // Disallow subsequent initCause
        detail = cause;
    }

    /**
     * Returns the detail message, including the message from the cause, if
     * any, of this exception.
     *
     * <p>
     *  返回详细消息,包括此异常的原因(如果有)的消息。
     * 
     * 
     * @return the detail message
     */
    public String getMessage() {
        if (detail == null)
            return super.getMessage();
        else
            return super.getMessage() +
                "; nested exception is: \n\t" +
                detail.toString();
    }

    /**
     * Returns the cause of this exception.  This method returns the value
     * of the {@link #detail} field.
     *
     * <p>
     *  返回此异常的原因。此方法返回{@link #detail}字段的值。
     * 
     * @return  the cause, which may be <tt>null</tt>.
     * @since   1.4
     */
    public Throwable getCause() {
        return detail;
    }
}
