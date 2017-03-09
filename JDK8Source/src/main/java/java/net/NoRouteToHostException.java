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
 * Signals that an error occurred while attempting to connect a
 * socket to a remote address and port.  Typically, the remote
 * host cannot be reached because of an intervening firewall, or
 * if an intermediate router is down.
 *
 * <p>
 *  表示尝试将套接字连接到远程地址和端口时发生错误。通常,由于中间防火墙或中间路由器关闭,无法访问远程主机。
 * 
 * 
 * @since   JDK1.1
 */
public class NoRouteToHostException extends SocketException {
    private static final long serialVersionUID = -1897550894873493790L;

    /**
     * Constructs a new NoRouteToHostException with the specified detail
     * message as to why the remote host cannot be reached.
     * A detail message is a String that gives a specific
     * description of this error.
     * <p>
     *  使用指定的详细消息构造新的NoRouteToHostException,以了解无法访问远程主机的原因。详细消息是一个字符串,给出此错误的特定描述。
     * 
     * 
     * @param msg the detail message
     */
    public NoRouteToHostException(String msg) {
        super(msg);
    }

    /**
     * Construct a new NoRouteToHostException with no detailed message.
     * <p>
     *  构造一个没有详细消息的新NoRouteToHostException。
     */
    public NoRouteToHostException() {}
}
