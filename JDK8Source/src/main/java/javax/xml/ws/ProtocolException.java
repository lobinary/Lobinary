/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2010, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.ws;

/** The <code>ProtocolException</code> class is a
 *  base class for exceptions related to a specific protocol binding. Subclasses
 *  are used to communicate protocol level fault information to clients and may
 *  be used on the server to control the protocol specific fault representation.
 *
 * <p>
 *  与特定协议绑定相关的异常的基类。子类用于向客户端传达协议级故障信息,并且可以在服务器上使用以控制协议特定的故障表示。
 * 
 * 
 *  @since JAX-WS 2.0
**/
public class ProtocolException extends WebServiceException {
    /**
     * Constructs a new protocol exception with <code>null</code> as its detail message. The
     * cause is not initialized, and may subsequently be initialized by a call
     * to <code>Throwable.initCause(java.lang.Throwable)</code>.
     * <p>
     *  使用<code> null </code>作为其详细消息构造新的协议异常。
     * 原因未初始化,并且可能随后通过调用<code> Throwable.initCause(java.lang.Throwable)</code>进行初始化。
     * 
     */
    public ProtocolException() {
        super();
    }

    /**
     * Constructs a new protocol exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to <code>Throwable.initCause(java.lang.Throwable)</code>.
     *
     * <p>
     *  使用指定的详细消息构造新的协议异常。原因未初始化,并且可能随后通过调用<code> Throwable.initCause(java.lang.Throwable)</code>来初始化。
     * 
     * 
     * @param message the detail message. The detail message is saved for later
     *   retrieval by the Throwable.getMessage() method.
     */
    public ProtocolException(String message) {
        super(message);
    }

    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.
     *
     * Note that the detail message associated with  cause is not automatically
     * incorporated in  this runtime exception's detail message.
     *
     * <p>
     *  使用指定的详细消息和原因构造新的运行时异常。
     * 
     *  请注意,与cause相关联的详细消息不会自动并入此运行时异常的详细消息中。
     * 
     * 
     * @param message the detail message (which is saved for later retrieval  by
     *   the Throwable.getMessage() method).
     * @param cause the cause (which is saved for later retrieval by the
     * <code>Throwable.getCause()</code> method). (A <code>null</code> value is  permitted, and indicates
     * that the cause is nonexistent or  unknown.)
     */
    public ProtocolException(String message,  Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new runtime exception with the specified cause and a  detail
     * message of <code>(cause==null ? null : cause.toString())</code>  (which typically
     * contains the class and detail message of  cause). This constructor is
     * useful for runtime exceptions  that are little more than wrappers for
     * other throwables.
     *
     * <p>
     * 
     * @param cause the cause (which is saved for later retrieval by the
     * <code>Throwable.getCause()</code> method). (A <code>null</code> value is  permitted, and indicates
     * that the cause is nonexistent or  unknown.)
     */
    public ProtocolException(Throwable cause) {
        super(cause);
    }
}
