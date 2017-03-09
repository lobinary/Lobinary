/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2008, Oracle and/or its affiliates. All rights reserved.
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
 * Signals that a timeout has occurred on a socket read or accept.
 *
 * <p>
 *  表示套接字读取或接受时发生超时。
 * 
 * 
 * @since   1.4
 */

public class SocketTimeoutException extends java.io.InterruptedIOException {
    private static final long serialVersionUID = -8846654841826352300L;

    /**
     * Constructs a new SocketTimeoutException with a detail
     * message.
     * <p>
     *  构造一个新的SocketTimeoutException与详细消息。
     * 
     * 
     * @param msg the detail message
     */
    public SocketTimeoutException(String msg) {
        super(msg);
    }

    /**
     * Construct a new SocketTimeoutException with no detailed message.
     * <p>
     *  构造一个没有详细消息的新SocketTimeoutException。
     */
    public SocketTimeoutException() {}
}
