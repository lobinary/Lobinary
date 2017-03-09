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

package java.rmi.server;

/**
 * An <code>ExportException</code> is a <code>RemoteException</code>
 * thrown if an attempt to export a remote object fails.  A remote object is
 * exported via the constructors and <code>exportObject</code> methods of
 * <code>java.rmi.server.UnicastRemoteObject</code> and
 * <code>java.rmi.activation.Activatable</code>.
 *
 * <p>
 *  如果尝试导出远程对象失败,则<code> ExportException </code>是抛出的<code> RemoteException </code>。
 * 远程对象通过<code> java.rmi.server.UnicastRemoteObject </code>和<code> java.rmi.activation.Activatable </code>
 * 的构造函数和<code> exportObject </code>方法导出。
 *  如果尝试导出远程对象失败,则<code> ExportException </code>是抛出的<code> RemoteException </code>。
 * 
 * 
 * @author  Ann Wollrath
 * @since   JDK1.1
 * @see java.rmi.server.UnicastRemoteObject
 * @see java.rmi.activation.Activatable
 */
public class ExportException extends java.rmi.RemoteException {

    /* indicate compatibility with JDK 1.1.x version of class */
    private static final long serialVersionUID = -9155485338494060170L;

    /**
     * Constructs an <code>ExportException</code> with the specified
     * detail message.
     *
     * <p>
     *  使用指定的详细消息构造一个<code> ExportException </code>。
     * 
     * 
     * @param s the detail message
     * @since JDK1.1
     */
    public ExportException(String s) {
        super(s);
    }

    /**
     * Constructs an <code>ExportException</code> with the specified
     * detail message and nested exception.
     *
     * <p>
     *  构造具有指定的详细消息和嵌套异常的<code> ExportException </code>。
     * 
     * @param s the detail message
     * @param ex the nested exception
     * @since JDK1.1
     */
    public ExportException(String s, Exception ex) {
        super(s, ex);
    }

}
