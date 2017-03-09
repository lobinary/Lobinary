/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2001, Oracle and/or its affiliates. All rights reserved.
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
 * A <code>ServerError</code> is thrown as a result of a remote method
 * invocation when an <code>Error</code> is thrown while processing
 * the invocation on the server, either while unmarshalling the arguments,
 * executing the remote method itself, or marshalling the return value.
 *
 * A <code>ServerError</code> instance contains the original
 * <code>Error</code> that occurred as its cause.
 *
 * <p>
 *  当在处理服务器上的调用时抛出<code> Error </code>时,作为远程​​方法调用的结果,抛出一个<code> ServerError </code>,即在解组参数时执行远程方法本身,或编组
 * 返回值。
 * 
 *  <code> ServerError </code>实例包含作为其原因发生的原始<code>错误</code>。
 * 
 * @author  Ann Wollrath
 * @since   JDK1.1
 */
public class ServerError extends RemoteException {

    /* indicate compatibility with JDK 1.1.x version of class */
    private static final long serialVersionUID = 8455284893909696482L;

    /**
     * Constructs a <code>ServerError</code> with the specified
     * detail message and nested error.
     *
     * <p>
     * 
     * 
     * @param s the detail message
     * @param err the nested error
     * @since JDK1.1
     */
    public ServerError(String s, Error err) {
        super(s, err);
    }
}
