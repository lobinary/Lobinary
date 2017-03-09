/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 1998, Oracle and/or its affiliates. All rights reserved.
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
 * An <code>UnknownHostException</code> is thrown if a
 * <code>java.net.UnknownHostException</code> occurs while creating
 * a connection to the remote host for a remote method call.
 *
 * <p>
 *  如果在为远程方法调用创建到远程主机的连接时发生<code> java.net.UnknownHostException </code>,则会抛出<code> UnknownHostException 
 * </code>。
 * 
 * 
 * @since   JDK1.1
 */
public class UnknownHostException extends RemoteException {

    /* indicate compatibility with JDK 1.1.x version of class */
     private static final long serialVersionUID = -8152710247442114228L;

    /**
     * Constructs an <code>UnknownHostException</code> with the specified
     * detail message.
     *
     * <p>
     *  使用指定的详细消息构造<code> UnknownHostException </code>。
     * 
     * 
     * @param s the detail message
     * @since JDK1.1
     */
    public UnknownHostException(String s) {
        super(s);
    }

    /**
     * Constructs an <code>UnknownHostException</code> with the specified
     * detail message and nested exception.
     *
     * <p>
     *  构造具有指定的详细消息和嵌套异常的<code> UnknownHostException </code>。
     * 
     * @param s the detail message
     * @param ex the nested exception
     * @since JDK1.1
     */
    public UnknownHostException(String s, Exception ex) {
        super(s, ex);
    }
}
