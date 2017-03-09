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
 * A <code>ServerException</code> is thrown as a result of a remote method
 * invocation when a <code>RemoteException</code> is thrown while processing
 * the invocation on the server, either while unmarshalling the arguments or
 * executing the remote method itself.
 *
 * A <code>ServerException</code> instance contains the original
 * <code>RemoteException</code> that occurred as its cause.
 *
 * <p>
 *  当在处理服务器上的调用时抛出<code> RemoteException </code>时,作为远程​​方法调用的结果,抛出一个<code> ServerException </code>,当解组参数
 * 或执行远程方法本身。
 * 
 *  <code> ServerException </code>实例包含作为其原因发生的原始<code> RemoteException </code>。
 * 
 * 
 * @author  Ann Wollrath
 * @since   JDK1.1
 */
public class ServerException extends RemoteException {

    /* indicate compatibility with JDK 1.1.x version of class */
    private static final long serialVersionUID = -4775845313121906682L;

    /**
     * Constructs a <code>ServerException</code> with the specified
     * detail message.
     *
     * <p>
     *  使用指定的详细消息构造<code> ServerException </code>。
     * 
     * 
     * @param s the detail message
     * @since JDK1.1
     */
    public ServerException(String s) {
        super(s);
    }

    /**
     * Constructs a <code>ServerException</code> with the specified
     * detail message and nested exception.
     *
     * <p>
     *  构造具有指定的详细消息和嵌套异常的<code> ServerException </code>。
     * 
     * @param s the detail message
     * @param ex the nested exception
     * @since JDK1.1
     */
    public ServerException(String s, Exception ex) {
        super(s, ex);
    }
}
