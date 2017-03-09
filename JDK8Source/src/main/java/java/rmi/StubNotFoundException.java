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
 * A <code>StubNotFoundException</code> is thrown if a valid stub class
 * could not be found for a remote object when it is exported.
 * A <code>StubNotFoundException</code> may also be
 * thrown when an activatable object is registered via the
 * <code>java.rmi.activation.Activatable.register</code> method.
 *
 * <p>
 *  如果在导出远程对象时找不到有效的存根类,则会抛出<code> StubNotFoundException </code>。
 * 当通过<code> java.rmi.activation.Activatable.register </code>方法注册可激活对象时,也可能抛出<code> StubNotFoundExceptio
 * n </code>。
 *  如果在导出远程对象时找不到有效的存根类,则会抛出<code> StubNotFoundException </code>。
 * 
 * 
 * @author  Roger Riggs
 * @since   JDK1.1
 * @see     java.rmi.server.UnicastRemoteObject
 * @see     java.rmi.activation.Activatable
 */
public class StubNotFoundException extends RemoteException {

    /* indicate compatibility with JDK 1.1.x version of class */
    private static final long serialVersionUID = -7088199405468872373L;

    /**
     * Constructs a <code>StubNotFoundException</code> with the specified
     * detail message.
     *
     * <p>
     *  使用指定的详细消息构造<code> StubNotFoundException </code>。
     * 
     * 
     * @param s the detail message
     * @since JDK1.1
     */
    public StubNotFoundException(String s) {
        super(s);
    }

    /**
     * Constructs a <code>StubNotFoundException</code> with the specified
     * detail message and nested exception.
     *
     * <p>
     *  构造具有指定的详细消息和嵌套异常的<code> StubNotFoundException </code>。
     * 
     * @param s the detail message
     * @param ex the nested exception
     * @since JDK1.1
     */
    public StubNotFoundException(String s, Exception ex) {
        super(s, ex);
    }
}
