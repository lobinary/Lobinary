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
 * An <code>UnmarshalException</code> can be thrown while unmarshalling the
 * parameters or results of a remote method call if any of the following
 * conditions occur:
 * <ul>
 * <li> if an exception occurs while unmarshalling the call header
 * <li> if the protocol for the return value is invalid
 * <li> if a <code>java.io.IOException</code> occurs unmarshalling
 * parameters (on the server side) or the return value (on the client side).
 * <li> if a <code>java.lang.ClassNotFoundException</code> occurs during
 * unmarshalling parameters or return values
 * <li> if no skeleton can be loaded on the server-side; note that skeletons
 * are required in the 1.1 stub protocol, but not in the 1.2 stub protocol.
 * <li> if the method hash is invalid (i.e., missing method).
 * <li> if there is a failure to create a remote reference object for
 * a remote object's stub when it is unmarshalled.
 * </ul>
 *
 * <p>
 *  如果发生以下任何情况,则在解组远程方法调用的参数或结果时可能会抛出<code> UnmarshalException </code>：
 * <ul>
 *  <li>如果返回值的协议无效,则解除调用头部<li>时出现异常<li>如果发生解码参数(服务器端)的<code> java.io.IOException </code>或返回值(在客户端)。
 *  <li>如果在解组参数或返回值期间发生<code> java.lang.ClassNotFoundException </code> <li>如果无法在服务器端加载骨架;注意在1.1 stub协议中需
 * 要骨架,但在1.2 stub协议中不需要。
 *  <li>如果返回值的协议无效,则解除调用头部<li>时出现异常<li>如果发生解码参数(服务器端)的<code> java.io.IOException </code>或返回值(在客户端)。
 *  <li>如果方法哈希无效(即,缺少方法)。 <li>如果在解除远程对象的存根时为远程对象的存根创建远程引用对象失败。
 * </ul>
 * 
 * @author  Ann Wollrath
 * @since   JDK1.1
 */
public class UnmarshalException extends RemoteException {

    /* indicate compatibility with JDK 1.1.x version of class */
    private static final long serialVersionUID = 594380845140740218L;

    /**
     * Constructs an <code>UnmarshalException</code> with the specified
     * detail message.
     *
     * <p>
     * 
     * 
     * @param s the detail message
     * @since JDK1.1
     */
    public UnmarshalException(String s) {
        super(s);
    }

    /**
     * Constructs an <code>UnmarshalException</code> with the specified
     * detail message and nested exception.
     *
     * <p>
     *  使用指定的详细消息构造一个<code> UnmarshalException </code>。
     * 
     * 
     * @param s the detail message
     * @param ex the nested exception
     * @since JDK1.1
     */
    public UnmarshalException(String s, Exception ex) {
        super(s, ex);
    }
}
