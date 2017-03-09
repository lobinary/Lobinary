/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2008, Oracle and/or its affiliates. All rights reserved.
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

package java.net;

/**
 * Signals that an error occurred while attempting to bind a
 * socket to a local address and port.  Typically, the port is
 * in use, or the requested local address could not be assigned.
 *
 * <p>
 *  表示尝试将套接字绑定到本地地址和端口时发生错误。通常,端口正在使用中,或者无法分配请求的本地地址。
 * 
 * 
 * @since   JDK1.1
 */

public class BindException extends SocketException {
    private static final long serialVersionUID = -5945005768251722951L;

    /**
     * Constructs a new BindException with the specified detail
     * message as to why the bind error occurred.
     * A detail message is a String that gives a specific
     * description of this error.
     * <p>
     *  构造一个新的BindException与指定的详细消息,为什么发生绑定错误。详细消息是一个字符串,给出此错误的特定描述。
     * 
     * 
     * @param msg the detail message
     */
    public BindException(String msg) {
        super(msg);
    }

    /**
     * Construct a new BindException with no detailed message.
     * <p>
     *  构造一个没有详细消息的新BindException。
     */
    public BindException() {}
}
