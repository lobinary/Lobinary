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
 * An <code>RMIServerSocketFactory</code> instance is used by the RMI runtime
 * in order to obtain server sockets for RMI calls.  A remote object can be
 * associated with an <code>RMIServerSocketFactory</code> when it is
 * created/exported via the constructors or <code>exportObject</code> methods
 * of <code>java.rmi.server.UnicastRemoteObject</code> and
 * <code>java.rmi.activation.Activatable</code> .
 *
 * <p>An <code>RMIServerSocketFactory</code> instance associated with a remote
 * object is used to obtain the <code>ServerSocket</code> used to accept
 * incoming calls from clients.
 *
 * <p>An <code>RMIServerSocketFactory</code> instance can also be associated
 * with a remote object registry so that clients can use custom socket
 * communication with a remote object registry.
 *
 * <p>An implementation of this interface
 * should implement {@link Object#equals} to return <code>true</code> when
 * passed an instance that represents the same (functionally equivalent)
 * server socket factory, and <code>false</code> otherwise (and it should also
 * implement {@link Object#hashCode} consistently with its
 * <code>Object.equals</code> implementation).
 *
 * <p>
 *  RMI运行时使用<code> RMIServerSocketFactory </code>实例,以获取RMI调用的服务器套接字。
 * 当通过<code> java.rmi.server.UnicastRemoteObject </code>的构造函数或<code> exportObject </code>方法创建/导出时,远程对象可以
 * 与<code> RMIServerSocketFactory </code>和<code> java.rmi.activation.Activatable </code>。
 *  RMI运行时使用<code> RMIServerSocketFactory </code>实例,以获取RMI调用的服务器套接字。
 * 
 *  <p>与远程对象相关联的<code> RMIServerSocketFactory </code>实例用于获取用于接受来自客户端的传入呼叫的​​<code> ServerSocket </code>。
 * 
 *  <p> <code> RMIServerSocketFactory </code>实例也可以与远程对象注册表相关联,以便客户端可以使用与远程对象注册表的自定义套接字通信。
 * 
 * @author  Ann Wollrath
 * @author  Peter Jones
 * @since   1.2
 * @see     java.rmi.server.UnicastRemoteObject
 * @see     java.rmi.activation.Activatable
 * @see     java.rmi.registry.LocateRegistry
 */
public interface RMIServerSocketFactory {

    /**
     * Create a server socket on the specified port (port 0 indicates
     * an anonymous port).
     * <p>
     * 
     *  <p>这个接口的实现应该实现{@link Object#equals},以便在传递代表相同(功能等效的)服务器套接字工厂的实例时返回<code> true </code>,<code> false <代码>
     * 否则(它也应该实现{@link Object#hashCode}与其<code> Object.equals </code>实现一致)。
     * 
     * 
     * @param  port the port number
     * @return the server socket on the specified port
     * @exception IOException if an I/O error occurs during server socket
     * creation
     * @since 1.2
     */
    public ServerSocket createServerSocket(int port)
        throws IOException;
}
