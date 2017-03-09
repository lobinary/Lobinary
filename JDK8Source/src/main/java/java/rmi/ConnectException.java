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
 * A <code>ConnectException</code> is thrown if a connection is refused
 * to the remote host for a remote method call.
 *
 * <p>
 *  如果远程方法调用的连接被拒绝到远程主机,则会抛出<code> ConnectException </code>。
 * 
 * 
 * @author  Ann Wollrath
 * @since   JDK1.1
 */
public class ConnectException extends RemoteException {

    /* indicate compatibility with JDK 1.1.x version of class */
     private static final long serialVersionUID = 4863550261346652506L;

    /**
     * Constructs a <code>ConnectException</code> with the specified
     * detail message.
     *
     * <p>
     *  使用指定的详细消息构造<code> ConnectException </code>。
     * 
     * 
     * @param s the detail message
     * @since JDK1.1
     */
    public ConnectException(String s) {
        super(s);
    }

    /**
     * Constructs a <code>ConnectException</code> with the specified
     * detail message and nested exception.
     *
     * <p>
     *  使用指定的详细消息和嵌套异常构造<code> ConnectException </code>。
     * 
     * @param s the detail message
     * @param ex the nested exception
     * @since JDK1.1
     */
    public ConnectException(String s, Exception ex) {
        super(s, ex);
    }
}
