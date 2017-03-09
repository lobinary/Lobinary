/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2001, Oracle and/or its affiliates. All rights reserved.
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

import java.io.*;
import java.net.*;

/**
 * An <code>RMIClientSocketFactory</code> instance is used by the RMI runtime
 * in order to obtain client sockets for RMI calls.  A remote object can be
 * associated with an <code>RMIClientSocketFactory</code> when it is
 * created/exported via the constructors or <code>exportObject</code> methods
 * of <code>java.rmi.server.UnicastRemoteObject</code> and
 * <code>java.rmi.activation.Activatable</code> .
 *
 * <p>An <code>RMIClientSocketFactory</code> instance associated with a remote
 * object will be downloaded to clients when the remote object's reference is
 * transmitted in an RMI call.  This <code>RMIClientSocketFactory</code> will
 * be used to create connections to the remote object for remote method calls.
 *
 * <p>An <code>RMIClientSocketFactory</code> instance can also be associated
 * with a remote object registry so that clients can use custom socket
 * communication with a remote object registry.
 *
 * <p>An implementation of this interface should be serializable and
 * should implement {@link Object#equals} to return <code>true</code> when
 * passed an instance that represents the same (functionally equivalent)
 * client socket factory, and <code>false</code> otherwise (and it should also
 * implement {@link Object#hashCode} consistently with its
 * <code>Object.equals</code> implementation).
 *
 * <p>
 *  RMI运行时使用<code> RMIClientSocketFactory </code>实例,以获取RMI调用的客户端套接字。
 * 当通过<code> java.rmi.server.UnicastRemoteObject </code>的构造函数或<code> exportObject </code>方法创建/导出时,远程对象可以
 * 与<code> RMIClientSocketFactory </code>和<code> java.rmi.activation.Activatable </code>。
 *  RMI运行时使用<code> RMIClientSocketFactory </code>实例,以获取RMI调用的客户端套接字。
 * 
 *  <p>与远程对象相关联的<code> RMIClientSocketFactory </code>实例将在远程对象的引用在RMI调用中传输时下载到客户端。
 * 这个<code> RMIClientSocketFactory </code>将用于创建远程对象的连接,用于远程方法调用。
 * 
 * 
 * @author  Ann Wollrath
 * @author  Peter Jones
 * @since   1.2
 * @see     java.rmi.server.UnicastRemoteObject
 * @see     java.rmi.activation.Activatable
 * @see     java.rmi.registry.LocateRegistry
 */
public interface RMIClientSocketFactory {

    /**
     * Create a client socket connected to the specified host and port.
     * <p>
     *  <p> <code> RMIClientSocketFactory </code>实例也可以与远程对象注册表相关联,以便客户端可以使用与远程对象注册表的自定义套接字通信。
     * 
     *  <p>此接口的实现应该是可序列化的,并且应该实现{@link Object#equals},以便在传递代表相同(功能等效的)客户端套接字工厂的实例时返回<code> true </code> > fa
     * lse </code>(并且它还应该实现{@link Object#hashCode}与其<code> Object.equals </code>实现一致)。
     * 
     * @param  host   the host name
     * @param  port   the port number
     * @return a socket connected to the specified host and port.
     * @exception IOException if an I/O error occurs during socket creation
     * @since 1.2
     */
    public Socket createSocket(String host, int port)
        throws IOException;
}
