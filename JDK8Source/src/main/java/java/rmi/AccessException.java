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
 * An <code>AccessException</code> is thrown by certain methods of the
 * <code>java.rmi.Naming</code> class (specifically <code>bind</code>,
 * <code>rebind</code>, and <code>unbind</code>) and methods of the
 * <code>java.rmi.activation.ActivationSystem</code> interface to
 * indicate that the caller does not have permission to perform the action
 * requested by the method call.  If the method was invoked from a non-local
 * host, then an <code>AccessException</code> is thrown.
 *
 * <p>
 *  <code> java.rmi.Naming </code>类(特别是<code> bind </code>,<code> rebind </code>)的某些方法抛出<code> AccessExc
 * eption </code> <code> unbind </code>)和<code> java.rmi.activation.ActivationSystem </code>接口的方法,以指示调用者
 * 没有执行方法调用所请求的操作的权限。
 * 如果从非本地主机调用该方法,则会抛出<code> AccessException </code>。
 * 
 * 
 * @author  Ann Wollrath
 * @author  Roger Riggs
 * @since   JDK1.1
 * @see     java.rmi.Naming
 * @see     java.rmi.activation.ActivationSystem
 */
public class AccessException extends java.rmi.RemoteException {

    /* indicate compatibility with JDK 1.1.x version of class */
     private static final long serialVersionUID = 6314925228044966088L;

    /**
     * Constructs an <code>AccessException</code> with the specified
     * detail message.
     *
     * <p>
     *  构造具有指定详细消息的<code> AccessException </code>。
     * 
     * 
     * @param s the detail message
     * @since JDK1.1
     */
    public AccessException(String s) {
        super(s);
    }

    /**
     * Constructs an <code>AccessException</code> with the specified
     * detail message and nested exception.
     *
     * <p>
     *  构造具有指定的详细消息和嵌套异常的<code> AccessException </code>。
     * 
     * @param s the detail message
     * @param ex the nested exception
     * @since JDK1.1
     */
    public AccessException(String s, Exception ex) {
        super(s, ex);
    }
}
