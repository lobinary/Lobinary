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

package java.rmi;

/**
 * A <code>RemoteException</code> is the common superclass for a number of
 * communication-related exceptions that may occur during the execution of a
 * remote method call.  Each method of a remote interface, an interface that
 * extends <code>java.rmi.Remote</code>, must list
 * <code>RemoteException</code> in its throws clause.
 *
 * <p>As of release 1.4, this exception has been retrofitted to conform to
 * the general purpose exception-chaining mechanism.  The "wrapped remote
 * exception" that may be provided at construction time and accessed via
 * the public {@link #detail} field is now known as the <i>cause</i>, and
 * may be accessed via the {@link Throwable#getCause()} method, as well as
 * the aforementioned "legacy field."
 *
 * <p>Invoking the method {@link Throwable#initCause(Throwable)} on an
 * instance of <code>RemoteException</code> always throws {@link
 * IllegalStateException}.
 *
 * <p>
 *  <code> RemoteException </code>是在执行远程方法调用期间可能发生的大量与通信有关的异常的通用超类。
 * 远程接口的每个方法,扩展<code> java.rmi.Remote </code>的接口,必须在其throws子句中列出<code> RemoteException </code>。
 * 
 *  <p>自版本1.4起,此异常已经过改进以符合通用的异常链接机制。
 * 可以在构建时提供并通过公共{@link #detail}字段访问的"封装的远程异常"现在被称为<i> cause </i>,并且可以经由{@link Throwable# getCause()}方法,以
 * 及上述"遗留字段"。
 *  <p>自版本1.4起,此异常已经过改进以符合通用的异常链接机制。
 * 
 *  <p>在<code> RemoteException </code>的实例上调用方法{@link Throwable#initCause(Throwable)}总是引发{@link IllegalStateException}
 * 。
 * 
 * 
 * @author  Ann Wollrath
 * @since   JDK1.1
 */
public class RemoteException extends java.io.IOException {

    /* indicate compatibility with JDK 1.1.x version of class */
    private static final long serialVersionUID = -5148567311918794206L;

    /**
     * The cause of the remote exception.
     *
     * <p>This field predates the general-purpose exception chaining facility.
     * The {@link Throwable#getCause()} method is now the preferred means of
     * obtaining this information.
     *
     * <p>
     *  远程异常的原因。
     * 
     *  <p>此字段早于通用的例外链接设施。 {@link Throwable#getCause()}方法现在是获取此信息的首选方法。
     * 
     * 
     * @serial
     */
    public Throwable detail;

    /**
     * Constructs a <code>RemoteException</code>.
     * <p>
     *  构造一个<code> RemoteException </code>。
     * 
     */
    public RemoteException() {
        initCause(null);  // Disallow subsequent initCause
    }

    /**
     * Constructs a <code>RemoteException</code> with the specified
     * detail message.
     *
     * <p>
     *  用指定的详细消息构造一个<code> RemoteException </code>。
     * 
     * 
     * @param s the detail message
     */
    public RemoteException(String s) {
        super(s);
        initCause(null);  // Disallow subsequent initCause
    }

    /**
     * Constructs a <code>RemoteException</code> with the specified detail
     * message and cause.  This constructor sets the {@link #detail}
     * field to the specified <code>Throwable</code>.
     *
     * <p>
     * 构造具有指定的详细消息和原因的<code> RemoteException </code>。此构造函数将{@link #detail}字段设置为指定的<code> Throwable </code>。
     * 
     * 
     * @param s the detail message
     * @param cause the cause
     */
    public RemoteException(String s, Throwable cause) {
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
        if (detail == null) {
            return super.getMessage();
        } else {
            return super.getMessage() + "; nested exception is: \n\t" +
                detail.toString();
        }
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
