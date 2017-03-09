/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * An <code>RMISocketFactory</code> instance is used by the RMI runtime
 * in order to obtain client and server sockets for RMI calls.  An
 * application may use the <code>setSocketFactory</code> method to
 * request that the RMI runtime use its socket factory instance
 * instead of the default implementation.
 *
 * <p>The default socket factory implementation performs a
 * three-tiered approach to creating client sockets. First, a direct
 * socket connection to the remote VM is attempted.  If that fails
 * (due to a firewall), the runtime uses HTTP with the explicit port
 * number of the server.  If the firewall does not allow this type of
 * communication, then HTTP to a cgi-bin script on the server is used
 * to POST the RMI call. The HTTP tunneling mechanisms are disabled by
 * default. This behavior is controlled by the {@code java.rmi.server.disableHttp}
 * property, whose default value is {@code true}. Setting this property's
 * value to {@code false} will enable the HTTP tunneling mechanisms.
 *
 * <p><strong>Deprecated: HTTP Tunneling.</strong> <em>The HTTP tunneling mechanisms
 * described above, specifically HTTP with an explicit port and HTTP to a
 * cgi-bin script, are deprecated. These HTTP tunneling mechanisms are
 * subject to removal in a future release of the platform.</em>
 *
 * <p>The default socket factory implementation creates server sockets that
 * are bound to the wildcard address, which accepts requests from all network
 * interfaces.
 *
 * @implNote
 * <p>You can use the {@code RMISocketFactory} class to create a server socket that
 * is bound to a specific address, restricting the origin of requests. For example,
 * the following code implements a socket factory that binds server sockets to an IPv4
 * loopback address. This restricts RMI to processing requests only from the local host.
 *
 * <pre>{@code
 *     class LoopbackSocketFactory extends RMISocketFactory {
 *         public ServerSocket createServerSocket(int port) throws IOException {
 *             return new ServerSocket(port, 5, InetAddress.getByName("127.0.0.1"));
 *         }
 *
 *         public Socket createSocket(String host, int port) throws IOException {
 *             // just call the default client socket factory
 *             return RMISocketFactory.getDefaultSocketFactory()
 *                                    .createSocket(host, port);
 *         }
 *     }
 *
 *     // ...
 *
 *     RMISocketFactory.setSocketFactory(new LoopbackSocketFactory());
 * }</pre>
 *
 * Set the {@code java.rmi.server.hostname} system property
 * to {@code 127.0.0.1} to ensure that the generated stubs connect to the right
 * network interface.
 *
 * <p>
 *  RMI运行时使用<code> RMISocketFactory </code>实例,以获取RMI调用的客户端和服务器套接字。
 * 应用程序可以使用<code> setSocketFactory </code>方法请求RMI运行时使用其套接字工厂实例,而不是默认实现。
 * 
 *  <p>默认套接字工厂实现执行三层方法来创建客户端套接字。首先,尝试直接套接字连接到远程VM。如果失败(由于防火墙),运行时使用HTTP和服务器的显式端口号。
 * 如果防火墙不允许此类型的通信,则使用HTTP到服务器上的cgi-bin脚本来POST RMI调用。默认情况下禁用HTTP隧道机制。
 * 此行为由{@code java.rmi.server.disableHttp}属性控制,其默认值为{@code true}。将此属性的值设置为{@code false}将启用HTTP隧道机制。
 * 
 *  <p> <strong>已弃用：HTTP隧道。</strong> <em>上述HTTP隧道机制,特别是带有显式端口的HTTP和到cgi-bin脚本的HTTP已弃用。
 * 这些HTTP隧道机制将在平台的未来版本中删除。</em>。
 * 
 * <p>默认套接字工厂实现创建绑定到通配符地址的服务器套接字,它接受来自所有网络接口的请求。
 * 
 *  @implNote <p>您可以使用{@code RMISocketFactory}类来创建绑定到特定地址的服务器套接字,限制请求的来源。
 * 例如,以下代码实现了将服务器套接字绑定到IPv4环回地址的套接字工厂。这将RMI限制为仅处理来自本地主机的请求。
 * 
 *  <end> {@ code} LoopbackSocketFactory extends RMISocketFactory {public ServerSocket createServerSocket(int port)throws IOException {return new ServerSocket(port,5,InetAddress.getByName("127.0.0.1")); }
 * }。
 * 
 *  public Socket createSocket(String host,int port)throws IOException {//只调用默认的客户端套接字工厂返回RMISocketFactory.getDefaultSocketFactory().createSocket(host,port); }
 * }。
 * 
 *  // ...
 * 
 *  RMISocketFactory.setSocketFactory(new LoopbackSocketFactory()); } </pre>
 * 
 *  将{@code java.rmi.server.hostname}系统属性设置为{@code 127.0.0.1},以确保生成的存根连接到正确的网络接口。
 * 
 * 
 * @author  Ann Wollrath
 * @author  Peter Jones
 * @since   JDK1.1
 */
public abstract class RMISocketFactory
        implements RMIClientSocketFactory, RMIServerSocketFactory
{

    /** Client/server socket factory to be used by RMI runtime */
    private static RMISocketFactory factory = null;
    /** default socket factory used by this RMI implementation */
    private static RMISocketFactory defaultSocketFactory;
    /** Handler for socket creation failure */
    private static RMIFailureHandler handler = null;

    /**
     * Constructs an <code>RMISocketFactory</code>.
     * <p>
     *  构造一个<code> RMISocketFactory </code>。
     * 
     * 
     * @since JDK1.1
     */
    public RMISocketFactory() {
        super();
    }

    /**
     * Creates a client socket connected to the specified host and port.
     * <p>
     *  创建连接到指定主机和端口的客户端套接字。
     * 
     * 
     * @param  host   the host name
     * @param  port   the port number
     * @return a socket connected to the specified host and port.
     * @exception IOException if an I/O error occurs during socket creation
     * @since JDK1.1
     */
    public abstract Socket createSocket(String host, int port)
        throws IOException;

    /**
     * Create a server socket on the specified port (port 0 indicates
     * an anonymous port).
     * <p>
     *  在指定端口上创建服务器套接字(端口0表示匿名端口)。
     * 
     * 
     * @param  port the port number
     * @return the server socket on the specified port
     * @exception IOException if an I/O error occurs during server socket
     * creation
     * @since JDK1.1
     */
    public abstract ServerSocket createServerSocket(int port)
        throws IOException;

    /**
     * Set the global socket factory from which RMI gets sockets (if the
     * remote object is not associated with a specific client and/or server
     * socket factory). The RMI socket factory can only be set once. Note: The
     * RMISocketFactory may only be set if the current security manager allows
     * setting a socket factory; if disallowed, a SecurityException will be
     * thrown.
     * <p>
     * 设置RMI从中获取套接字的全局套接字工厂(如果远程对象与特定的客户端和/或服务器套接字工厂没有关联)。 RMI套接字工厂只能设置一次。
     * 注意：RMISocketFactory只能在当前安全管理器允许设置套接字工厂时设置;如果不允许,将抛出SecurityException。
     * 
     * 
     * @param fac the socket factory
     * @exception IOException if the RMI socket factory is already set
     * @exception  SecurityException  if a security manager exists and its
     *             <code>checkSetFactory</code> method doesn't allow the operation.
     * @see #getSocketFactory
     * @see java.lang.SecurityManager#checkSetFactory()
     * @since JDK1.1
     */
    public synchronized static void setSocketFactory(RMISocketFactory fac)
        throws IOException
    {
        if (factory != null) {
            throw new SocketException("factory already defined");
        }
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkSetFactory();
        }
        factory = fac;
    }

    /**
     * Returns the socket factory set by the <code>setSocketFactory</code>
     * method. Returns <code>null</code> if no socket factory has been
     * set.
     * <p>
     *  返回由<code> setSocketFactory </code>方法设置的套接字工厂。如果没有设置套接字工厂,则返回<code> null </code>。
     * 
     * 
     * @return the socket factory
     * @see #setSocketFactory(RMISocketFactory)
     * @since JDK1.1
     */
    public synchronized static RMISocketFactory getSocketFactory()
    {
        return factory;
    }

    /**
     * Returns a reference to the default socket factory used
     * by this RMI implementation.  This will be the factory used
     * by the RMI runtime when <code>getSocketFactory</code>
     * returns <code>null</code>.
     * <p>
     *  返回对此RMI实现使用的默认套接字工厂的引用。这将是RMI运行时在<code> getSocketFactory </code>返回<code> null </code>时使用的工厂。
     * 
     * 
     * @return the default RMI socket factory
     * @since JDK1.1
     */
    public synchronized static RMISocketFactory getDefaultSocketFactory() {
        if (defaultSocketFactory == null) {
            defaultSocketFactory =
                new sun.rmi.transport.proxy.RMIMasterSocketFactory();
        }
        return defaultSocketFactory;
    }

    /**
     * Sets the failure handler to be called by the RMI runtime if server
     * socket creation fails.  By default, if no failure handler is installed
     * and server socket creation fails, the RMI runtime does attempt to
     * recreate the server socket.
     *
     * <p>If there is a security manager, this method first calls
     * the security manager's <code>checkSetFactory</code> method
     * to ensure the operation is allowed.
     * This could result in a <code>SecurityException</code>.
     *
     * <p>
     *  如果服务器套接字创建失败,则设置要由RMI运行时调用的失败处理程序。默认情况下,如果未安装故障处理程序并且服务器套接字创建失败,则RMI运行时会尝试重新创建服务器套接字。
     * 
     *  <p>如果有安全管理器,此方法首先调用安全管理器的<code> checkSetFactory </code>方法,以确保允许操作。
     * 这可能导致<code> SecurityException </code>。
     * 
     * @param fh the failure handler
     * @throws  SecurityException  if a security manager exists and its
     *          <code>checkSetFactory</code> method doesn't allow the
     *          operation.
     * @see #getFailureHandler
     * @see java.rmi.server.RMIFailureHandler#failure(Exception)
     * @since JDK1.1
     */
    public synchronized static void setFailureHandler(RMIFailureHandler fh)
    {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkSetFactory();
        }
        handler = fh;
    }

    /**
     * Returns the handler for socket creation failure set by the
     * <code>setFailureHandler</code> method.
     * <p>
     * 
     * 
     * @return the failure handler
     * @see #setFailureHandler(RMIFailureHandler)
     * @since JDK1.1
     */
    public synchronized static RMIFailureHandler getFailureHandler()
    {
        return handler;
    }
}
