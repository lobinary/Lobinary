/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.net;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.security.PrivilegedAction;

/**
 * This class implements client sockets (also called just
 * "sockets"). A socket is an endpoint for communication
 * between two machines.
 * <p>
 * The actual work of the socket is performed by an instance of the
 * {@code SocketImpl} class. An application, by changing
 * the socket factory that creates the socket implementation,
 * can configure itself to create sockets appropriate to the local
 * firewall.
 *
 * <p>
 *  此类实现客户端套接字(也称为"套接字")。套接字是两台机器之间通信的端点。
 * <p>
 *  套接字的实际工作由{@code SocketImpl}类的实例执行。应用程序通过更改创建套接字实现的套接字工厂,可以配置自己创建适合本地防火墙的套接字。
 * 
 * 
 * @author  unascribed
 * @see     java.net.Socket#setSocketImplFactory(java.net.SocketImplFactory)
 * @see     java.net.SocketImpl
 * @see     java.nio.channels.SocketChannel
 * @since   JDK1.0
 */
public
class Socket implements java.io.Closeable {
    /**
     * Various states of this socket.
     * <p>
     *  此插座的各种状态。
     * 
     */
    private boolean created = false;
    private boolean bound = false;
    private boolean connected = false;
    private boolean closed = false;
    private Object closeLock = new Object();
    private boolean shutIn = false;
    private boolean shutOut = false;

    /**
     * The implementation of this Socket.
     * <p>
     *  这个Socket的实现。
     * 
     */
    SocketImpl impl;

    /**
     * Are we using an older SocketImpl?
     * <p>
     *  我们使用较老的SocketImpl吗?
     * 
     */
    private boolean oldImpl = false;

    /**
     * Creates an unconnected socket, with the
     * system-default type of SocketImpl.
     *
     * <p>
     *  创建一个未连接的套接字,系统默认类型为SocketImpl。
     * 
     * 
     * @since   JDK1.1
     * @revised 1.4
     */
    public Socket() {
        setImpl();
    }

    /**
     * Creates an unconnected socket, specifying the type of proxy, if any,
     * that should be used regardless of any other settings.
     * <P>
     * If there is a security manager, its {@code checkConnect} method
     * is called with the proxy host address and port number
     * as its arguments. This could result in a SecurityException.
     * <P>
     * Examples:
     * <UL> <LI>{@code Socket s = new Socket(Proxy.NO_PROXY);} will create
     * a plain socket ignoring any other proxy configuration.</LI>
     * <LI>{@code Socket s = new Socket(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("socks.mydom.com", 1080)));}
     * will create a socket connecting through the specified SOCKS proxy
     * server.</LI>
     * </UL>
     *
     * <p>
     *  创建未连接的套接字,指定应使用的代理类型(如果有),而不考虑任何其他设置。
     * <P>
     *  如果有安全管理器,则会调用其{@code checkConnect}方法,并使用代理主机地址和端口号作为其参数。这可能导致SecurityException。
     * <P>
     *  示例：<UL> <LI> {@ code Socket s = new Socket(Proxy.NO_PROXY);}将创建一个简单的套接字,忽略任何其他代理配置</LI> <LI> {@ code Socket s = new Socket新的代理(Proxy.Type.SOCKS,新的InetSocketAddress("socks.mydom.com",1080)));}
     * 将创建一个套接字通过指定的SOCKS代理服务器连接。
     * </LI>。
     * </UL>
     * 
     * 
     * @param proxy a {@link java.net.Proxy Proxy} object specifying what kind
     *              of proxying should be used.
     * @throws IllegalArgumentException if the proxy is of an invalid type
     *          or {@code null}.
     * @throws SecurityException if a security manager is present and
     *                           permission to connect to the proxy is
     *                           denied.
     * @see java.net.ProxySelector
     * @see java.net.Proxy
     *
     * @since   1.5
     */
    public Socket(Proxy proxy) {
        // Create a copy of Proxy as a security measure
        if (proxy == null) {
            throw new IllegalArgumentException("Invalid Proxy");
        }
        Proxy p = proxy == Proxy.NO_PROXY ? Proxy.NO_PROXY
                                          : sun.net.ApplicationProxy.create(proxy);
        Proxy.Type type = p.type();
        if (type == Proxy.Type.SOCKS || type == Proxy.Type.HTTP) {
            SecurityManager security = System.getSecurityManager();
            InetSocketAddress epoint = (InetSocketAddress) p.address();
            if (epoint.getAddress() != null) {
                checkAddress (epoint.getAddress(), "Socket");
            }
            if (security != null) {
                if (epoint.isUnresolved())
                    epoint = new InetSocketAddress(epoint.getHostName(), epoint.getPort());
                if (epoint.isUnresolved())
                    security.checkConnect(epoint.getHostName(), epoint.getPort());
                else
                    security.checkConnect(epoint.getAddress().getHostAddress(),
                                  epoint.getPort());
            }
            impl = type == Proxy.Type.SOCKS ? new SocksSocketImpl(p)
                                            : new HttpConnectSocketImpl(p);
            impl.setSocket(this);
        } else {
            if (p == Proxy.NO_PROXY) {
                if (factory == null) {
                    impl = new PlainSocketImpl();
                    impl.setSocket(this);
                } else
                    setImpl();
            } else
                throw new IllegalArgumentException("Invalid Proxy");
        }
    }

    /**
     * Creates an unconnected Socket with a user-specified
     * SocketImpl.
     * <P>
     * <p>
     *  使用用户指定的SocketImpl创建未连接的套接字。
     * <P>
     * 
     * @param impl an instance of a <B>SocketImpl</B>
     * the subclass wishes to use on the Socket.
     *
     * @exception SocketException if there is an error in the underlying protocol,
     * such as a TCP error.
     * @since   JDK1.1
     */
    protected Socket(SocketImpl impl) throws SocketException {
        this.impl = impl;
        if (impl != null) {
            checkOldImpl();
            this.impl.setSocket(this);
        }
    }

    /**
     * Creates a stream socket and connects it to the specified port
     * number on the named host.
     * <p>
     * If the specified host is {@code null} it is the equivalent of
     * specifying the address as
     * {@link java.net.InetAddress#getByName InetAddress.getByName}{@code (null)}.
     * In other words, it is equivalent to specifying an address of the
     * loopback interface. </p>
     * <p>
     * If the application has specified a server socket factory, that
     * factory's {@code createSocketImpl} method is called to create
     * the actual socket implementation. Otherwise a "plain" socket is created.
     * <p>
     * If there is a security manager, its
     * {@code checkConnect} method is called
     * with the host address and {@code port}
     * as its arguments. This could result in a SecurityException.
     *
     * <p>
     *  创建流套接字并将其连接到指定主机上的指定端口号。
     * <p>
     * 如果指定的主机是{@code null},它相当于指定地址为{@link java.net.InetAddress#getByName InetAddress.getByName}{@code(null)}
     * 。
     * 换句话说,它等同于指定回送接口的地址。 </p>。
     * <p>
     *  如果应用程序指定了一个服务器套接字工厂,则调用该工厂的{@code createSocketImpl}方法来创建实际的套接字实现。否则创建一个"纯"套接字。
     * <p>
     *  如果有安全管理器,则会使用主机地址和{@code port}作为其参数调用其{@code checkConnect}方法。这可能导致SecurityException。
     * 
     * 
     * @param      host   the host name, or {@code null} for the loopback address.
     * @param      port   the port number.
     *
     * @exception  UnknownHostException if the IP address of
     * the host could not be determined.
     *
     * @exception  IOException  if an I/O error occurs when creating the socket.
     * @exception  SecurityException  if a security manager exists and its
     *             {@code checkConnect} method doesn't allow the operation.
     * @exception  IllegalArgumentException if the port parameter is outside
     *             the specified range of valid port values, which is between
     *             0 and 65535, inclusive.
     * @see        java.net.Socket#setSocketImplFactory(java.net.SocketImplFactory)
     * @see        java.net.SocketImpl
     * @see        java.net.SocketImplFactory#createSocketImpl()
     * @see        SecurityManager#checkConnect
     */
    public Socket(String host, int port)
        throws UnknownHostException, IOException
    {
        this(host != null ? new InetSocketAddress(host, port) :
             new InetSocketAddress(InetAddress.getByName(null), port),
             (SocketAddress) null, true);
    }

    /**
     * Creates a stream socket and connects it to the specified port
     * number at the specified IP address.
     * <p>
     * If the application has specified a socket factory, that factory's
     * {@code createSocketImpl} method is called to create the
     * actual socket implementation. Otherwise a "plain" socket is created.
     * <p>
     * If there is a security manager, its
     * {@code checkConnect} method is called
     * with the host address and {@code port}
     * as its arguments. This could result in a SecurityException.
     *
     * <p>
     *  创建流套接字并将其连接到指定IP地址处的指定端口号。
     * <p>
     *  如果应用程序指定了套接字工厂,那么将调用该工厂的{@code createSocketImpl}方法来创建实际的套接字实现。否则创建一个"纯"套接字。
     * <p>
     *  如果有安全管理器,则会使用主机地址和{@code port}作为其参数调用其{@code checkConnect}方法。这可能导致SecurityException。
     * 
     * 
     * @param      address   the IP address.
     * @param      port      the port number.
     * @exception  IOException  if an I/O error occurs when creating the socket.
     * @exception  SecurityException  if a security manager exists and its
     *             {@code checkConnect} method doesn't allow the operation.
     * @exception  IllegalArgumentException if the port parameter is outside
     *             the specified range of valid port values, which is between
     *             0 and 65535, inclusive.
     * @exception  NullPointerException if {@code address} is null.
     * @see        java.net.Socket#setSocketImplFactory(java.net.SocketImplFactory)
     * @see        java.net.SocketImpl
     * @see        java.net.SocketImplFactory#createSocketImpl()
     * @see        SecurityManager#checkConnect
     */
    public Socket(InetAddress address, int port) throws IOException {
        this(address != null ? new InetSocketAddress(address, port) : null,
             (SocketAddress) null, true);
    }

    /**
     * Creates a socket and connects it to the specified remote host on
     * the specified remote port. The Socket will also bind() to the local
     * address and port supplied.
     * <p>
     * If the specified host is {@code null} it is the equivalent of
     * specifying the address as
     * {@link java.net.InetAddress#getByName InetAddress.getByName}{@code (null)}.
     * In other words, it is equivalent to specifying an address of the
     * loopback interface. </p>
     * <p>
     * A local port number of {@code zero} will let the system pick up a
     * free port in the {@code bind} operation.</p>
     * <p>
     * If there is a security manager, its
     * {@code checkConnect} method is called
     * with the host address and {@code port}
     * as its arguments. This could result in a SecurityException.
     *
     * <p>
     *  创建套接字并将其连接到指定的远程端口上的指定远程主机。 Socket还将bind()绑定到所提供的本地地址和端口。
     * <p>
     * 如果指定的主机是{@code null},它相当于指定地址为{@link java.net.InetAddress#getByName InetAddress.getByName}{@code(null)}
     * 。
     * 换句话说,它等同于指定回送接口的地址。 </p>。
     * <p>
     *  {@code zero}的本地端口号将使系统在{@code bind}操作中选择一个空闲端口。</p>
     * <p>
     *  如果有安全管理器,则会使用主机地址和{@code port}作为其参数调用其{@code checkConnect}方法。这可能导致SecurityException。
     * 
     * 
     * @param host the name of the remote host, or {@code null} for the loopback address.
     * @param port the remote port
     * @param localAddr the local address the socket is bound to, or
     *        {@code null} for the {@code anyLocal} address.
     * @param localPort the local port the socket is bound to, or
     *        {@code zero} for a system selected free port.
     * @exception  IOException  if an I/O error occurs when creating the socket.
     * @exception  SecurityException  if a security manager exists and its
     *             {@code checkConnect} method doesn't allow the connection
     *             to the destination, or if its {@code checkListen} method
     *             doesn't allow the bind to the local port.
     * @exception  IllegalArgumentException if the port parameter or localPort
     *             parameter is outside the specified range of valid port values,
     *             which is between 0 and 65535, inclusive.
     * @see        SecurityManager#checkConnect
     * @since   JDK1.1
     */
    public Socket(String host, int port, InetAddress localAddr,
                  int localPort) throws IOException {
        this(host != null ? new InetSocketAddress(host, port) :
               new InetSocketAddress(InetAddress.getByName(null), port),
             new InetSocketAddress(localAddr, localPort), true);
    }

    /**
     * Creates a socket and connects it to the specified remote address on
     * the specified remote port. The Socket will also bind() to the local
     * address and port supplied.
     * <p>
     * If the specified local address is {@code null} it is the equivalent of
     * specifying the address as the AnyLocal address
     * (see {@link java.net.InetAddress#isAnyLocalAddress InetAddress.isAnyLocalAddress}{@code ()}).
     * <p>
     * A local port number of {@code zero} will let the system pick up a
     * free port in the {@code bind} operation.</p>
     * <p>
     * If there is a security manager, its
     * {@code checkConnect} method is called
     * with the host address and {@code port}
     * as its arguments. This could result in a SecurityException.
     *
     * <p>
     *  创建一个套接字并将其连接到指定远程端口上的指定远程地址。 Socket还将bind()绑定到所提供的本地地址和端口。
     * <p>
     *  如果指定的本地地址是{@code null},它相当于指定地址作为AnyLocal地址(参见{@link java.net.InetAddress#isAnyLocalAddress InetAddress.isAnyLocalAddress}
     * {@code()})。
     * <p>
     *  {@code zero}的本地端口号将使系统在{@code bind}操作中选择一个空闲端口。</p>
     * <p>
     *  如果有安全管理器,则会使用主机地址和{@code port}作为其参数调用其{@code checkConnect}方法。这可能导致SecurityException。
     * 
     * 
     * @param address the remote address
     * @param port the remote port
     * @param localAddr the local address the socket is bound to, or
     *        {@code null} for the {@code anyLocal} address.
     * @param localPort the local port the socket is bound to or
     *        {@code zero} for a system selected free port.
     * @exception  IOException  if an I/O error occurs when creating the socket.
     * @exception  SecurityException  if a security manager exists and its
     *             {@code checkConnect} method doesn't allow the connection
     *             to the destination, or if its {@code checkListen} method
     *             doesn't allow the bind to the local port.
     * @exception  IllegalArgumentException if the port parameter or localPort
     *             parameter is outside the specified range of valid port values,
     *             which is between 0 and 65535, inclusive.
     * @exception  NullPointerException if {@code address} is null.
     * @see        SecurityManager#checkConnect
     * @since   JDK1.1
     */
    public Socket(InetAddress address, int port, InetAddress localAddr,
                  int localPort) throws IOException {
        this(address != null ? new InetSocketAddress(address, port) : null,
             new InetSocketAddress(localAddr, localPort), true);
    }

    /**
     * Creates a stream socket and connects it to the specified port
     * number on the named host.
     * <p>
     * If the specified host is {@code null} it is the equivalent of
     * specifying the address as
     * {@link java.net.InetAddress#getByName InetAddress.getByName}{@code (null)}.
     * In other words, it is equivalent to specifying an address of the
     * loopback interface. </p>
     * <p>
     * If the stream argument is {@code true}, this creates a
     * stream socket. If the stream argument is {@code false}, it
     * creates a datagram socket.
     * <p>
     * If the application has specified a server socket factory, that
     * factory's {@code createSocketImpl} method is called to create
     * the actual socket implementation. Otherwise a "plain" socket is created.
     * <p>
     * If there is a security manager, its
     * {@code checkConnect} method is called
     * with the host address and {@code port}
     * as its arguments. This could result in a SecurityException.
     * <p>
     * If a UDP socket is used, TCP/IP related socket options will not apply.
     *
     * <p>
     *  创建流套接字并将其连接到指定主机上的指定端口号。
     * <p>
     * 如果指定的主机是{@code null},它相当于指定地址为{@link java.net.InetAddress#getByName InetAddress.getByName}{@code(null)}
     * 。
     * 换句话说,它等同于指定回送接口的地址。 </p>。
     * <p>
     *  如果stream参数是{@code true},这将创建一个流套接字。如果stream参数是{@code false},它会创建一个数据报套接字。
     * <p>
     *  如果应用程序指定了一个服务器套接字工厂,则调用该工厂的{@code createSocketImpl}方法来创建实际的套接字实现。否则创建一个"纯"套接字。
     * <p>
     *  如果有安全管理器,则会使用主机地址和{@code port}作为其参数调用其{@code checkConnect}方法。这可能导致SecurityException。
     * <p>
     *  如果使用UDP套接字,TCP / IP相关套接字选项将不适用。
     * 
     * 
     * @param      host     the host name, or {@code null} for the loopback address.
     * @param      port     the port number.
     * @param      stream   a {@code boolean} indicating whether this is
     *                      a stream socket or a datagram socket.
     * @exception  IOException  if an I/O error occurs when creating the socket.
     * @exception  SecurityException  if a security manager exists and its
     *             {@code checkConnect} method doesn't allow the operation.
     * @exception  IllegalArgumentException if the port parameter is outside
     *             the specified range of valid port values, which is between
     *             0 and 65535, inclusive.
     * @see        java.net.Socket#setSocketImplFactory(java.net.SocketImplFactory)
     * @see        java.net.SocketImpl
     * @see        java.net.SocketImplFactory#createSocketImpl()
     * @see        SecurityManager#checkConnect
     * @deprecated Use DatagramSocket instead for UDP transport.
     */
    @Deprecated
    public Socket(String host, int port, boolean stream) throws IOException {
        this(host != null ? new InetSocketAddress(host, port) :
               new InetSocketAddress(InetAddress.getByName(null), port),
             (SocketAddress) null, stream);
    }

    /**
     * Creates a socket and connects it to the specified port number at
     * the specified IP address.
     * <p>
     * If the stream argument is {@code true}, this creates a
     * stream socket. If the stream argument is {@code false}, it
     * creates a datagram socket.
     * <p>
     * If the application has specified a server socket factory, that
     * factory's {@code createSocketImpl} method is called to create
     * the actual socket implementation. Otherwise a "plain" socket is created.
     *
     * <p>If there is a security manager, its
     * {@code checkConnect} method is called
     * with {@code host.getHostAddress()} and {@code port}
     * as its arguments. This could result in a SecurityException.
     * <p>
     * If UDP socket is used, TCP/IP related socket options will not apply.
     *
     * <p>
     *  创建套接字并将其连接到指定IP地址处的指定端口号。
     * <p>
     *  如果stream参数是{@code true},这将创建一个流套接字。如果stream参数是{@code false},它会创建一个数据报套接字。
     * <p>
     *  如果应用程序指定了一个服务器套接字工厂,则调用该工厂的{@code createSocketImpl}方法来创建实际的套接字实现。否则创建一个"纯"套接字。
     * 
     *  <p>如果有安全管理员,则会使用{@code host.getHostAddress()}和{@code port}作为其参数调用其{@code checkConnect}方法。
     * 这可能导致SecurityException。
     * <p>
     * 如果使用UDP套接字,TCP / IP相关套接字选项将不适用。
     * 
     * 
     * @param      host     the IP address.
     * @param      port      the port number.
     * @param      stream    if {@code true}, create a stream socket;
     *                       otherwise, create a datagram socket.
     * @exception  IOException  if an I/O error occurs when creating the socket.
     * @exception  SecurityException  if a security manager exists and its
     *             {@code checkConnect} method doesn't allow the operation.
     * @exception  IllegalArgumentException if the port parameter is outside
     *             the specified range of valid port values, which is between
     *             0 and 65535, inclusive.
     * @exception  NullPointerException if {@code host} is null.
     * @see        java.net.Socket#setSocketImplFactory(java.net.SocketImplFactory)
     * @see        java.net.SocketImpl
     * @see        java.net.SocketImplFactory#createSocketImpl()
     * @see        SecurityManager#checkConnect
     * @deprecated Use DatagramSocket instead for UDP transport.
     */
    @Deprecated
    public Socket(InetAddress host, int port, boolean stream) throws IOException {
        this(host != null ? new InetSocketAddress(host, port) : null,
             new InetSocketAddress(0), stream);
    }

    private Socket(SocketAddress address, SocketAddress localAddr,
                   boolean stream) throws IOException {
        setImpl();

        // backward compatibility
        if (address == null)
            throw new NullPointerException();

        try {
            createImpl(stream);
            if (localAddr != null)
                bind(localAddr);
            connect(address);
        } catch (IOException | IllegalArgumentException | SecurityException e) {
            try {
                close();
            } catch (IOException ce) {
                e.addSuppressed(ce);
            }
            throw e;
        }
    }

    /**
     * Creates the socket implementation.
     *
     * <p>
     *  创建套接字实现。
     * 
     * 
     * @param stream a {@code boolean} value : {@code true} for a TCP socket,
     *               {@code false} for UDP.
     * @throws IOException if creation fails
     * @since 1.4
     */
     void createImpl(boolean stream) throws SocketException {
        if (impl == null)
            setImpl();
        try {
            impl.create(stream);
            created = true;
        } catch (IOException e) {
            throw new SocketException(e.getMessage());
        }
    }

    private void checkOldImpl() {
        if (impl == null)
            return;
        // SocketImpl.connect() is a protected method, therefore we need to use
        // getDeclaredMethod, therefore we need permission to access the member

        oldImpl = AccessController.doPrivileged
                                (new PrivilegedAction<Boolean>() {
            public Boolean run() {
                Class<?> clazz = impl.getClass();
                while (true) {
                    try {
                        clazz.getDeclaredMethod("connect", SocketAddress.class, int.class);
                        return Boolean.FALSE;
                    } catch (NoSuchMethodException e) {
                        clazz = clazz.getSuperclass();
                        // java.net.SocketImpl class will always have this abstract method.
                        // If we have not found it by now in the hierarchy then it does not
                        // exist, we are an old style impl.
                        if (clazz.equals(java.net.SocketImpl.class)) {
                            return Boolean.TRUE;
                        }
                    }
                }
            }
        });
    }

    /**
     * Sets impl to the system-default type of SocketImpl.
     * <p>
     *  将impl设置为SocketImpl的系统默认类型。
     * 
     * 
     * @since 1.4
     */
    void setImpl() {
        if (factory != null) {
            impl = factory.createSocketImpl();
            checkOldImpl();
        } else {
            // No need to do a checkOldImpl() here, we know it's an up to date
            // SocketImpl!
            impl = new SocksSocketImpl();
        }
        if (impl != null)
            impl.setSocket(this);
    }


    /**
     * Get the {@code SocketImpl} attached to this socket, creating
     * it if necessary.
     *
     * <p>
     *  获取{@code SocketImpl}附加到此套接字,如果必要创建它。
     * 
     * 
     * @return  the {@code SocketImpl} attached to that ServerSocket.
     * @throws SocketException if creation fails
     * @since 1.4
     */
    SocketImpl getImpl() throws SocketException {
        if (!created)
            createImpl(true);
        return impl;
    }

    /**
     * Connects this socket to the server.
     *
     * <p>
     *  将此套接字连接到服务器。
     * 
     * 
     * @param   endpoint the {@code SocketAddress}
     * @throws  IOException if an error occurs during the connection
     * @throws  java.nio.channels.IllegalBlockingModeException
     *          if this socket has an associated channel,
     *          and the channel is in non-blocking mode
     * @throws  IllegalArgumentException if endpoint is null or is a
     *          SocketAddress subclass not supported by this socket
     * @since 1.4
     * @spec JSR-51
     */
    public void connect(SocketAddress endpoint) throws IOException {
        connect(endpoint, 0);
    }

    /**
     * Connects this socket to the server with a specified timeout value.
     * A timeout of zero is interpreted as an infinite timeout. The connection
     * will then block until established or an error occurs.
     *
     * <p>
     *  使用指定的超时值将此套接字连接到服务器。超时为零被解释为无限超时。然后,连接将阻塞,直到建立或发生错误。
     * 
     * 
     * @param   endpoint the {@code SocketAddress}
     * @param   timeout  the timeout value to be used in milliseconds.
     * @throws  IOException if an error occurs during the connection
     * @throws  SocketTimeoutException if timeout expires before connecting
     * @throws  java.nio.channels.IllegalBlockingModeException
     *          if this socket has an associated channel,
     *          and the channel is in non-blocking mode
     * @throws  IllegalArgumentException if endpoint is null or is a
     *          SocketAddress subclass not supported by this socket
     * @since 1.4
     * @spec JSR-51
     */
    public void connect(SocketAddress endpoint, int timeout) throws IOException {
        if (endpoint == null)
            throw new IllegalArgumentException("connect: The address can't be null");

        if (timeout < 0)
          throw new IllegalArgumentException("connect: timeout can't be negative");

        if (isClosed())
            throw new SocketException("Socket is closed");

        if (!oldImpl && isConnected())
            throw new SocketException("already connected");

        if (!(endpoint instanceof InetSocketAddress))
            throw new IllegalArgumentException("Unsupported address type");

        InetSocketAddress epoint = (InetSocketAddress) endpoint;
        InetAddress addr = epoint.getAddress ();
        int port = epoint.getPort();
        checkAddress(addr, "connect");

        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            if (epoint.isUnresolved())
                security.checkConnect(epoint.getHostName(), port);
            else
                security.checkConnect(addr.getHostAddress(), port);
        }
        if (!created)
            createImpl(true);
        if (!oldImpl)
            impl.connect(epoint, timeout);
        else if (timeout == 0) {
            if (epoint.isUnresolved())
                impl.connect(addr.getHostName(), port);
            else
                impl.connect(addr, port);
        } else
            throw new UnsupportedOperationException("SocketImpl.connect(addr, timeout)");
        connected = true;
        /*
         * If the socket was not bound before the connect, it is now because
         * the kernel will have picked an ephemeral port & a local address
         * <p>
         *  如果套接字在连接之前没有绑定,现在是因为内核将选择一个临时端口和一个本地地址
         * 
         */
        bound = true;
    }

    /**
     * Binds the socket to a local address.
     * <P>
     * If the address is {@code null}, then the system will pick up
     * an ephemeral port and a valid local address to bind the socket.
     *
     * <p>
     *  将套接字绑定到本地地址。
     * <P>
     *  如果地址是{@code null},那么系统将拾取一个临时端口和一个有效的本地地址来绑定套接字。
     * 
     * 
     * @param   bindpoint the {@code SocketAddress} to bind to
     * @throws  IOException if the bind operation fails, or if the socket
     *                     is already bound.
     * @throws  IllegalArgumentException if bindpoint is a
     *          SocketAddress subclass not supported by this socket
     * @throws  SecurityException  if a security manager exists and its
     *          {@code checkListen} method doesn't allow the bind
     *          to the local port.
     *
     * @since   1.4
     * @see #isBound
     */
    public void bind(SocketAddress bindpoint) throws IOException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        if (!oldImpl && isBound())
            throw new SocketException("Already bound");

        if (bindpoint != null && (!(bindpoint instanceof InetSocketAddress)))
            throw new IllegalArgumentException("Unsupported address type");
        InetSocketAddress epoint = (InetSocketAddress) bindpoint;
        if (epoint != null && epoint.isUnresolved())
            throw new SocketException("Unresolved address");
        if (epoint == null) {
            epoint = new InetSocketAddress(0);
        }
        InetAddress addr = epoint.getAddress();
        int port = epoint.getPort();
        checkAddress (addr, "bind");
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkListen(port);
        }
        getImpl().bind (addr, port);
        bound = true;
    }

    private void checkAddress (InetAddress addr, String op) {
        if (addr == null) {
            return;
        }
        if (!(addr instanceof Inet4Address || addr instanceof Inet6Address)) {
            throw new IllegalArgumentException(op + ": invalid address type");
        }
    }

    /**
     * set the flags after an accept() call.
     * <p>
     *  在accept()调用后设置标志。
     * 
     */
    final void postAccept() {
        connected = true;
        created = true;
        bound = true;
    }

    void setCreated() {
        created = true;
    }

    void setBound() {
        bound = true;
    }

    void setConnected() {
        connected = true;
    }

    /**
     * Returns the address to which the socket is connected.
     * <p>
     * If the socket was connected prior to being {@link #close closed},
     * then this method will continue to return the connected address
     * after the socket is closed.
     *
     * <p>
     *  返回套接字连接的地址。
     * <p>
     *  如果套接字在{@link #close closed}之前连接,则该方法将在套接字关闭后继续返回连接的地址。
     * 
     * 
     * @return  the remote IP address to which this socket is connected,
     *          or {@code null} if the socket is not connected.
     */
    public InetAddress getInetAddress() {
        if (!isConnected())
            return null;
        try {
            return getImpl().getInetAddress();
        } catch (SocketException e) {
        }
        return null;
    }

    /**
     * Gets the local address to which the socket is bound.
     * <p>
     * If there is a security manager set, its {@code checkConnect} method is
     * called with the local address and {@code -1} as its arguments to see
     * if the operation is allowed. If the operation is not allowed,
     * the {@link InetAddress#getLoopbackAddress loopback} address is returned.
     *
     * <p>
     *  获取套接字绑定到的本地地址。
     * <p>
     *  如果存在安全管理器集,则会使用本地地址和{@code -1}作为其参数来调用其{@code checkConnect}方法,以查看是否允许该操作。
     * 如果不允许该操作,则返回{@link InetAddress#getLoopbackAddress loopback}地址。
     * 
     * 
     * @return the local address to which the socket is bound,
     *         the loopback address if denied by the security manager, or
     *         the wildcard address if the socket is closed or not bound yet.
     * @since   JDK1.1
     *
     * @see SecurityManager#checkConnect
     */
    public InetAddress getLocalAddress() {
        // This is for backward compatibility
        if (!isBound())
            return InetAddress.anyLocalAddress();
        InetAddress in = null;
        try {
            in = (InetAddress) getImpl().getOption(SocketOptions.SO_BINDADDR);
            SecurityManager sm = System.getSecurityManager();
            if (sm != null)
                sm.checkConnect(in.getHostAddress(), -1);
            if (in.isAnyLocalAddress()) {
                in = InetAddress.anyLocalAddress();
            }
        } catch (SecurityException e) {
            in = InetAddress.getLoopbackAddress();
        } catch (Exception e) {
            in = InetAddress.anyLocalAddress(); // "0.0.0.0"
        }
        return in;
    }

    /**
     * Returns the remote port number to which this socket is connected.
     * <p>
     * If the socket was connected prior to being {@link #close closed},
     * then this method will continue to return the connected port number
     * after the socket is closed.
     *
     * <p>
     *  返回此套接字连接的远程端口号。
     * <p>
     * 如果在{@link #close closed}之前连接了套接字,则该方法将在套接字关闭后继续返回连接的端口号。
     * 
     * 
     * @return  the remote port number to which this socket is connected, or
     *          0 if the socket is not connected yet.
     */
    public int getPort() {
        if (!isConnected())
            return 0;
        try {
            return getImpl().getPort();
        } catch (SocketException e) {
            // Shouldn't happen as we're connected
        }
        return -1;
    }

    /**
     * Returns the local port number to which this socket is bound.
     * <p>
     * If the socket was bound prior to being {@link #close closed},
     * then this method will continue to return the local port number
     * after the socket is closed.
     *
     * <p>
     *  返回此套接字绑定到的本地端口号。
     * <p>
     *  如果套接字在{@link #close closed}之前被绑定,则该方法将在套接字关闭后继续返回本地端口号。
     * 
     * 
     * @return  the local port number to which this socket is bound or -1
     *          if the socket is not bound yet.
     */
    public int getLocalPort() {
        if (!isBound())
            return -1;
        try {
            return getImpl().getLocalPort();
        } catch(SocketException e) {
            // shouldn't happen as we're bound
        }
        return -1;
    }

    /**
     * Returns the address of the endpoint this socket is connected to, or
     * {@code null} if it is unconnected.
     * <p>
     * If the socket was connected prior to being {@link #close closed},
     * then this method will continue to return the connected address
     * after the socket is closed.
     *

     * <p>
     *  返回此套接字连接到的端点的地址,如果未连接,则返回{@code null}。
     * <p>
     *  如果套接字在{@link #close closed}之前连接,则该方法将在套接字关闭后继续返回连接的地址。
     * 
     * 
     * @return a {@code SocketAddress} representing the remote endpoint of this
     *         socket, or {@code null} if it is not connected yet.
     * @see #getInetAddress()
     * @see #getPort()
     * @see #connect(SocketAddress, int)
     * @see #connect(SocketAddress)
     * @since 1.4
     */
    public SocketAddress getRemoteSocketAddress() {
        if (!isConnected())
            return null;
        return new InetSocketAddress(getInetAddress(), getPort());
    }

    /**
     * Returns the address of the endpoint this socket is bound to.
     * <p>
     * If a socket bound to an endpoint represented by an
     * {@code InetSocketAddress } is {@link #close closed},
     * then this method will continue to return an {@code InetSocketAddress}
     * after the socket is closed. In that case the returned
     * {@code InetSocketAddress}'s address is the
     * {@link InetAddress#isAnyLocalAddress wildcard} address
     * and its port is the local port that it was bound to.
     * <p>
     * If there is a security manager set, its {@code checkConnect} method is
     * called with the local address and {@code -1} as its arguments to see
     * if the operation is allowed. If the operation is not allowed,
     * a {@code SocketAddress} representing the
     * {@link InetAddress#getLoopbackAddress loopback} address and the local
     * port to which this socket is bound is returned.
     *
     * <p>
     *  返回此套接字绑定到的端点的地址。
     * <p>
     *  如果绑定到由{@code InetSocketAddress}表示的端点的套接字是{@link #close closed},则该方法将在套接字关闭后继续返回{@code InetSocketAddress}
     * 。
     * 在这种情况下,返回的{@code InetSocketAddress}的地址是{@link InetAddress#isAnyLocalAddress通配符}地址,其端口是其绑定到的本地端口。
     * <p>
     *  如果存在安全管理器集,则会使用本地地址和{@code -1}作为其参数来调用其{@code checkConnect}方法,以查看是否允许该操作。
     * 如果不允许该操作,则返回表示{@link InetAddress#getLoopbackAddress loopback}地址和该套接字绑定到的本地端口的{@code SocketAddress}。
     * 
     * 
     * @return a {@code SocketAddress} representing the local endpoint of
     *         this socket, or a {@code SocketAddress} representing the
     *         loopback address if denied by the security manager, or
     *         {@code null} if the socket is not bound yet.
     *
     * @see #getLocalAddress()
     * @see #getLocalPort()
     * @see #bind(SocketAddress)
     * @see SecurityManager#checkConnect
     * @since 1.4
     */

    public SocketAddress getLocalSocketAddress() {
        if (!isBound())
            return null;
        return new InetSocketAddress(getLocalAddress(), getLocalPort());
    }

    /**
     * Returns the unique {@link java.nio.channels.SocketChannel SocketChannel}
     * object associated with this socket, if any.
     *
     * <p> A socket will have a channel if, and only if, the channel itself was
     * created via the {@link java.nio.channels.SocketChannel#open
     * SocketChannel.open} or {@link
     * java.nio.channels.ServerSocketChannel#accept ServerSocketChannel.accept}
     * methods.
     *
     * <p>
     * 返回与此套接字相关联的唯一{@link java.nio.channels.SocketChannel SocketChannel}对象(如果有)。
     * 
     *  <p>套接字会有一个频道,如果且仅当频道本身是通过{@link java.nio.channels.SocketChannel#open SocketChannel.open}或{@link java.nio.channels.ServerSocketChannel#接受ServerSocketChannel.accept}
     * 方法。
     * 
     * 
     * @return  the socket channel associated with this socket,
     *          or {@code null} if this socket was not created
     *          for a channel
     *
     * @since 1.4
     * @spec JSR-51
     */
    public SocketChannel getChannel() {
        return null;
    }

    /**
     * Returns an input stream for this socket.
     *
     * <p> If this socket has an associated channel then the resulting input
     * stream delegates all of its operations to the channel.  If the channel
     * is in non-blocking mode then the input stream's {@code read} operations
     * will throw an {@link java.nio.channels.IllegalBlockingModeException}.
     *
     * <p>Under abnormal conditions the underlying connection may be
     * broken by the remote host or the network software (for example
     * a connection reset in the case of TCP connections). When a
     * broken connection is detected by the network software the
     * following applies to the returned input stream :-
     *
     * <ul>
     *
     *   <li><p>The network software may discard bytes that are buffered
     *   by the socket. Bytes that aren't discarded by the network
     *   software can be read using {@link java.io.InputStream#read read}.
     *
     *   <li><p>If there are no bytes buffered on the socket, or all
     *   buffered bytes have been consumed by
     *   {@link java.io.InputStream#read read}, then all subsequent
     *   calls to {@link java.io.InputStream#read read} will throw an
     *   {@link java.io.IOException IOException}.
     *
     *   <li><p>If there are no bytes buffered on the socket, and the
     *   socket has not been closed using {@link #close close}, then
     *   {@link java.io.InputStream#available available} will
     *   return {@code 0}.
     *
     * </ul>
     *
     * <p> Closing the returned {@link java.io.InputStream InputStream}
     * will close the associated socket.
     *
     * <p>
     *  返回此套接字的输入流。
     * 
     *  <p>如果此套接字具有关联的通道,则生成的输入流将其所有操作委派给通道。
     * 如果通道处于非阻塞模式,那么输入流的{@code read}操作将抛出一个{@link java.nio.channels.IllegalBlockingModeException}。
     * 
     *  <p>在异常情况下,底层连接可能被远程主机或网络软件断开(例如在TCP连接的情况下重置连接)。当网络软件检测到断开的连接时,以下内容适用于返回的输入流： - 
     * 
     * <ul>
     * 
     *  <li> <p>网络软件可能丢弃由套接字缓冲的字节。未被网络软件丢弃的字节可以使用{@link java.io.InputStream#read read}读取。
     * 
     *  <li> <p>如果套接字上没有缓冲的字节,或者所有缓冲的字节都被{@link java.io.InputStream#read read}占用,那么所有后续的调用{@link java.io.InputStream #read read}
     * 会抛出一个{@link java.io.IOException IOException}。
     * 
     * <li> <p>如果没有缓冲在套接字上的字节,并且套接字没有使用{@link #close close}关闭,那么{@link java.io.InputStream#available available}
     * 将返回{@code 0}。
     * 
     * </ul>
     * 
     *  <p>关闭返回的{@link java.io.InputStream InputStream}将关闭相关的套接字。
     * 
     * 
     * @return     an input stream for reading bytes from this socket.
     * @exception  IOException  if an I/O error occurs when creating the
     *             input stream, the socket is closed, the socket is
     *             not connected, or the socket input has been shutdown
     *             using {@link #shutdownInput()}
     *
     * @revised 1.4
     * @spec JSR-51
     */
    public InputStream getInputStream() throws IOException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        if (!isConnected())
            throw new SocketException("Socket is not connected");
        if (isInputShutdown())
            throw new SocketException("Socket input is shutdown");
        final Socket s = this;
        InputStream is = null;
        try {
            is = AccessController.doPrivileged(
                new PrivilegedExceptionAction<InputStream>() {
                    public InputStream run() throws IOException {
                        return impl.getInputStream();
                    }
                });
        } catch (java.security.PrivilegedActionException e) {
            throw (IOException) e.getException();
        }
        return is;
    }

    /**
     * Returns an output stream for this socket.
     *
     * <p> If this socket has an associated channel then the resulting output
     * stream delegates all of its operations to the channel.  If the channel
     * is in non-blocking mode then the output stream's {@code write}
     * operations will throw an {@link
     * java.nio.channels.IllegalBlockingModeException}.
     *
     * <p> Closing the returned {@link java.io.OutputStream OutputStream}
     * will close the associated socket.
     *
     * <p>
     *  返回此套接字的输出流。
     * 
     *  <p>如果此套接字具有关联的通道,则生成的输出流将其所有操作委派给通道。
     * 如果通道处于非阻塞模式,那么输出流的{@code write}操作将抛出一个{@link java.nio.channels.IllegalBlockingModeException}。
     * 
     *  <p>关闭返回的{@link java.io.OutputStream OutputStream}将关闭相关的套接字。
     * 
     * 
     * @return     an output stream for writing bytes to this socket.
     * @exception  IOException  if an I/O error occurs when creating the
     *               output stream or if the socket is not connected.
     * @revised 1.4
     * @spec JSR-51
     */
    public OutputStream getOutputStream() throws IOException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        if (!isConnected())
            throw new SocketException("Socket is not connected");
        if (isOutputShutdown())
            throw new SocketException("Socket output is shutdown");
        final Socket s = this;
        OutputStream os = null;
        try {
            os = AccessController.doPrivileged(
                new PrivilegedExceptionAction<OutputStream>() {
                    public OutputStream run() throws IOException {
                        return impl.getOutputStream();
                    }
                });
        } catch (java.security.PrivilegedActionException e) {
            throw (IOException) e.getException();
        }
        return os;
    }

    /**
     * Enable/disable {@link SocketOptions#TCP_NODELAY TCP_NODELAY}
     * (disable/enable Nagle's algorithm).
     *
     * <p>
     *  启用/禁用{@link SocketOptions#TCP_NODELAY TCP_NODELAY}(禁用/启用Nagle的算法)。
     * 
     * 
     * @param on {@code true} to enable TCP_NODELAY,
     * {@code false} to disable.
     *
     * @exception SocketException if there is an error
     * in the underlying protocol, such as a TCP error.
     *
     * @since   JDK1.1
     *
     * @see #getTcpNoDelay()
     */
    public void setTcpNoDelay(boolean on) throws SocketException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        getImpl().setOption(SocketOptions.TCP_NODELAY, Boolean.valueOf(on));
    }

    /**
     * Tests if {@link SocketOptions#TCP_NODELAY TCP_NODELAY} is enabled.
     *
     * <p>
     *  测试是否启用{@link SocketOptions#TCP_NODELAY TCP_NODELAY}。
     * 
     * 
     * @return a {@code boolean} indicating whether or not
     *         {@link SocketOptions#TCP_NODELAY TCP_NODELAY} is enabled.
     * @exception SocketException if there is an error
     * in the underlying protocol, such as a TCP error.
     * @since   JDK1.1
     * @see #setTcpNoDelay(boolean)
     */
    public boolean getTcpNoDelay() throws SocketException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        return ((Boolean) getImpl().getOption(SocketOptions.TCP_NODELAY)).booleanValue();
    }

    /**
     * Enable/disable {@link SocketOptions#SO_LINGER SO_LINGER} with the
     * specified linger time in seconds. The maximum timeout value is platform
     * specific.
     *
     * The setting only affects socket close.
     *
     * <p>
     *  以指定的逗留时间(以秒为单位)启用/禁用{@link SocketOptions#SO_LINGER SO_LINGER}。最大超时值是特定于平台的。
     * 
     *  该设置仅影响套接字关闭。
     * 
     * 
     * @param on     whether or not to linger on.
     * @param linger how long to linger for, if on is true.
     * @exception SocketException if there is an error
     * in the underlying protocol, such as a TCP error.
     * @exception IllegalArgumentException if the linger value is negative.
     * @since JDK1.1
     * @see #getSoLinger()
     */
    public void setSoLinger(boolean on, int linger) throws SocketException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        if (!on) {
            getImpl().setOption(SocketOptions.SO_LINGER, new Boolean(on));
        } else {
            if (linger < 0) {
                throw new IllegalArgumentException("invalid value for SO_LINGER");
            }
            if (linger > 65535)
                linger = 65535;
            getImpl().setOption(SocketOptions.SO_LINGER, new Integer(linger));
        }
    }

    /**
     * Returns setting for {@link SocketOptions#SO_LINGER SO_LINGER}.
     * -1 returns implies that the
     * option is disabled.
     *
     * The setting only affects socket close.
     *
     * <p>
     *  返回{@link SocketOptions#SO_LINGER SO_LINGER}的设置。 -1返回意味着该选项被禁用。
     * 
     *  该设置仅影响套接字关闭。
     * 
     * 
     * @return the setting for {@link SocketOptions#SO_LINGER SO_LINGER}.
     * @exception SocketException if there is an error
     * in the underlying protocol, such as a TCP error.
     * @since   JDK1.1
     * @see #setSoLinger(boolean, int)
     */
    public int getSoLinger() throws SocketException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        Object o = getImpl().getOption(SocketOptions.SO_LINGER);
        if (o instanceof Integer) {
            return ((Integer) o).intValue();
        } else {
            return -1;
        }
    }

    /**
     * Send one byte of urgent data on the socket. The byte to be sent is the lowest eight
     * bits of the data parameter. The urgent byte is
     * sent after any preceding writes to the socket OutputStream
     * and before any future writes to the OutputStream.
     * <p>
     *  在套接字上发送一个字节的紧急数据。要发送的字节是数据参数的最低8位。紧急字节在对套接字OutputStream的任何先前写入之后和在任何将来写入OutputStream之前发送。
     * 
     * 
     * @param data The byte of data to send
     * @exception IOException if there is an error
     *  sending the data.
     * @since 1.4
     */
    public void sendUrgentData (int data) throws IOException  {
        if (!getImpl().supportsUrgentData ()) {
            throw new SocketException ("Urgent data not supported");
        }
        getImpl().sendUrgentData (data);
    }

    /**
     * Enable/disable {@link SocketOptions#SO_OOBINLINE SO_OOBINLINE}
     * (receipt of TCP urgent data)
     *
     * By default, this option is disabled and TCP urgent data received on a
     * socket is silently discarded. If the user wishes to receive urgent data, then
     * this option must be enabled. When enabled, urgent data is received
     * inline with normal data.
     * <p>
     * Note, only limited support is provided for handling incoming urgent
     * data. In particular, no notification of incoming urgent data is provided
     * and there is no capability to distinguish between normal data and urgent
     * data unless provided by a higher level protocol.
     *
     * <p>
     * 启用/停用{@link SocketOptions#SO_OOBINLINE SO_OOBINLINE}(接收TCP紧急数据)
     * 
     *  默认情况下,此选项被禁用,并且套接字上接收的TCP紧急数据将被静默丢弃。如果用户希望接收紧急数据,则必须启用此选项。启用时,紧急数据与正常数据内联接收。
     * <p>
     *  注意,仅提供有限的支持来处理传入的紧急数据。特别地,不提供进入的紧急数据的通知,并且除非由更高级协议提供,否则不存在区分正常数据和紧急数据的能力。
     * 
     * 
     * @param on {@code true} to enable
     *           {@link SocketOptions#SO_OOBINLINE SO_OOBINLINE},
     *           {@code false} to disable.
     *
     * @exception SocketException if there is an error
     * in the underlying protocol, such as a TCP error.
     *
     * @since   1.4
     *
     * @see #getOOBInline()
     */
    public void setOOBInline(boolean on) throws SocketException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        getImpl().setOption(SocketOptions.SO_OOBINLINE, Boolean.valueOf(on));
    }

    /**
     * Tests if {@link SocketOptions#SO_OOBINLINE SO_OOBINLINE} is enabled.
     *
     * <p>
     *  测试是否启用{@link SocketOptions#SO_OOBINLINE SO_OOBINLINE}。
     * 
     * 
     * @return a {@code boolean} indicating whether or not
     *         {@link SocketOptions#SO_OOBINLINE SO_OOBINLINE}is enabled.
     *
     * @exception SocketException if there is an error
     * in the underlying protocol, such as a TCP error.
     * @since   1.4
     * @see #setOOBInline(boolean)
     */
    public boolean getOOBInline() throws SocketException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        return ((Boolean) getImpl().getOption(SocketOptions.SO_OOBINLINE)).booleanValue();
    }

    /**
     *  Enable/disable {@link SocketOptions#SO_TIMEOUT SO_TIMEOUT}
     *  with the specified timeout, in milliseconds. With this option set
     *  to a non-zero timeout, a read() call on the InputStream associated with
     *  this Socket will block for only this amount of time.  If the timeout
     *  expires, a <B>java.net.SocketTimeoutException</B> is raised, though the
     *  Socket is still valid. The option <B>must</B> be enabled
     *  prior to entering the blocking operation to have effect. The
     *  timeout must be {@code > 0}.
     *  A timeout of zero is interpreted as an infinite timeout.
     *
     * <p>
     *  以指定的超时值(以毫秒为单位)启用/禁用{@link SocketOptions#SO_TIMEOUT SO_TIMEOUT}。
     * 使用此选项设置为非零超时,与此套接字相关联的InputStream上的read()调用将仅阻塞此时间量。
     * 如果超时到期,则会引发一个<B> java.net.SocketTimeoutException </B>,但Socket仍然有效。必须先启用选项<B> </B>,然后才能进入阻止操作才能生效。
     * 超时必须为{@code> 0}。超时为零被解释为无限超时。
     * 
     * 
     * @param timeout the specified timeout, in milliseconds.
     * @exception SocketException if there is an error
     * in the underlying protocol, such as a TCP error.
     * @since   JDK 1.1
     * @see #getSoTimeout()
     */
    public synchronized void setSoTimeout(int timeout) throws SocketException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        if (timeout < 0)
          throw new IllegalArgumentException("timeout can't be negative");

        getImpl().setOption(SocketOptions.SO_TIMEOUT, new Integer(timeout));
    }

    /**
     * Returns setting for {@link SocketOptions#SO_TIMEOUT SO_TIMEOUT}.
     * 0 returns implies that the option is disabled (i.e., timeout of infinity).
     *
     * <p>
     *  返回{@link SocketOptions#SO_TIMEOUT SO_TIMEOUT}的设置。 0返回意味着该选项被禁用(即,无限超时)。
     * 
     * 
     * @return the setting for {@link SocketOptions#SO_TIMEOUT SO_TIMEOUT}
     * @exception SocketException if there is an error
     * in the underlying protocol, such as a TCP error.
     *
     * @since   JDK1.1
     * @see #setSoTimeout(int)
     */
    public synchronized int getSoTimeout() throws SocketException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        Object o = getImpl().getOption(SocketOptions.SO_TIMEOUT);
        /* extra type safety */
        if (o instanceof Integer) {
            return ((Integer) o).intValue();
        } else {
            return 0;
        }
    }

    /**
     * Sets the {@link SocketOptions#SO_SNDBUF SO_SNDBUF} option to the
     * specified value for this {@code Socket}.
     * The {@link SocketOptions#SO_SNDBUF SO_SNDBUF} option is used by the
     * platform's networking code as a hint for the size to set the underlying
     * network I/O buffers.
     *
     * <p>Because {@link SocketOptions#SO_SNDBUF SO_SNDBUF} is a hint,
     * applications that want to verify what size the buffers were set to
     * should call {@link #getSendBufferSize()}.
     *
     * <p>
     * 将{@link SocketOptions#SO_SNDBUF SO_SNDBUF}选项设置为此{@code Socket}的指定值。
     *  {@link SocketOptions#SO_SNDBUF SO_SNDBUF}选项由平台的网络代码用作设置底层网络I / O缓冲区大小的提示。
     * 
     *  <p>因为{@link SocketOptions#SO_SNDBUF SO_SNDBUF}是一个提示,要验证缓冲区大小设置为什么的应用程序应调用{@link #getSendBufferSize()}
     * 。
     * 
     * 
     * @exception SocketException if there is an error
     * in the underlying protocol, such as a TCP error.
     *
     * @param size the size to which to set the send buffer
     * size. This value must be greater than 0.
     *
     * @exception IllegalArgumentException if the
     * value is 0 or is negative.
     *
     * @see #getSendBufferSize()
     * @since 1.2
     */
    public synchronized void setSendBufferSize(int size)
    throws SocketException{
        if (!(size > 0)) {
            throw new IllegalArgumentException("negative send size");
        }
        if (isClosed())
            throw new SocketException("Socket is closed");
        getImpl().setOption(SocketOptions.SO_SNDBUF, new Integer(size));
    }

    /**
     * Get value of the {@link SocketOptions#SO_SNDBUF SO_SNDBUF} option
     * for this {@code Socket}, that is the buffer size used by the platform
     * for output on this {@code Socket}.
     * <p>
     *  为此{@code Socket}获取{@link SocketOptions#SO_SNDBUF SO_SNDBUF}选项的值,这是平台在此{@code Socket}上输出所使用的缓冲区大小。
     * 
     * 
     * @return the value of the {@link SocketOptions#SO_SNDBUF SO_SNDBUF}
     *         option for this {@code Socket}.
     *
     * @exception SocketException if there is an error
     * in the underlying protocol, such as a TCP error.
     *
     * @see #setSendBufferSize(int)
     * @since 1.2
     */
    public synchronized int getSendBufferSize() throws SocketException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        int result = 0;
        Object o = getImpl().getOption(SocketOptions.SO_SNDBUF);
        if (o instanceof Integer) {
            result = ((Integer)o).intValue();
        }
        return result;
    }

    /**
     * Sets the {@link SocketOptions#SO_RCVBUF SO_RCVBUF} option to the
     * specified value for this {@code Socket}. The
     * {@link SocketOptions#SO_RCVBUF SO_RCVBUF} option is
     * used by the platform's networking code as a hint for the size to set
     * the underlying network I/O buffers.
     *
     * <p>Increasing the receive buffer size can increase the performance of
     * network I/O for high-volume connection, while decreasing it can
     * help reduce the backlog of incoming data.
     *
     * <p>Because {@link SocketOptions#SO_RCVBUF SO_RCVBUF} is a hint,
     * applications that want to verify what size the buffers were set to
     * should call {@link #getReceiveBufferSize()}.
     *
     * <p>The value of {@link SocketOptions#SO_RCVBUF SO_RCVBUF} is also used
     * to set the TCP receive window that is advertized to the remote peer.
     * Generally, the window size can be modified at any time when a socket is
     * connected. However, if a receive window larger than 64K is required then
     * this must be requested <B>before</B> the socket is connected to the
     * remote peer. There are two cases to be aware of:
     * <ol>
     * <li>For sockets accepted from a ServerSocket, this must be done by calling
     * {@link ServerSocket#setReceiveBufferSize(int)} before the ServerSocket
     * is bound to a local address.<p></li>
     * <li>For client sockets, setReceiveBufferSize() must be called before
     * connecting the socket to its remote peer.</li></ol>
     * <p>
     *  将{@link SocketOptions#SO_RCVBUF SO_RCVBUF}选项设置为此{@code Socket}的指定值。
     * 平台的网络代码使用{@link SocketOptions#SO_RCVBUF SO_RCVBUF}选项作为设置底层网络I / O缓冲区的大小的提示。
     * 
     *  <p>增加接收缓冲区大小可以提高网络I / O的高容量连接性能,而减少它可以帮助减少传入数据的积压。
     * 
     *  <p>因为{@link SocketOptions#SO_RCVBUF SO_RCVBUF}是一个提示,应用程序想要验证缓冲区的大小应该调用{@link #getReceiveBufferSize()}
     * 。
     * 
     * <p> {@link SocketOptions#SO_RCVBUF SO_RCVBUF}的值也用于设置通告给远程对等体的TCP接收窗口。通常,窗口大小可以在连接套接字时随时修改。
     * 然而,如果需要大于64K的接收窗口,则必须在</B>套接字连接到远程对等体之前请求这个。有两种情况需要注意：。
     * <ol>
     *  <li>对于从ServerSocket接受的套接字,必须在ServerSocket绑定到本地地址之前调用{@link ServerSocket#setReceiveBufferSize(int)}。
     * <p> </li> <li>对于客户端套接字,在将套接字连接到其远程对等端之前,必须调用setReceiveBufferSize()。</li> </ol>。
     * 
     * 
     * @param size the size to which to set the receive buffer
     * size. This value must be greater than 0.
     *
     * @exception IllegalArgumentException if the value is 0 or is
     * negative.
     *
     * @exception SocketException if there is an error
     * in the underlying protocol, such as a TCP error.
     *
     * @see #getReceiveBufferSize()
     * @see ServerSocket#setReceiveBufferSize(int)
     * @since 1.2
     */
    public synchronized void setReceiveBufferSize(int size)
    throws SocketException{
        if (size <= 0) {
            throw new IllegalArgumentException("invalid receive size");
        }
        if (isClosed())
            throw new SocketException("Socket is closed");
        getImpl().setOption(SocketOptions.SO_RCVBUF, new Integer(size));
    }

    /**
     * Gets the value of the {@link SocketOptions#SO_RCVBUF SO_RCVBUF} option
     * for this {@code Socket}, that is the buffer size used by the platform
     * for input on this {@code Socket}.
     *
     * <p>
     *  获取此{@code Socket}的{@link SocketOptions#SO_RCVBUF SO_RCVBUF}选项的值,这是平台用于在此{@code Socket}上输入的缓冲区大小。
     * 
     * 
     * @return the value of the {@link SocketOptions#SO_RCVBUF SO_RCVBUF}
     *         option for this {@code Socket}.
     * @exception SocketException if there is an error
     * in the underlying protocol, such as a TCP error.
     * @see #setReceiveBufferSize(int)
     * @since 1.2
     */
    public synchronized int getReceiveBufferSize()
    throws SocketException{
        if (isClosed())
            throw new SocketException("Socket is closed");
        int result = 0;
        Object o = getImpl().getOption(SocketOptions.SO_RCVBUF);
        if (o instanceof Integer) {
            result = ((Integer)o).intValue();
        }
        return result;
    }

    /**
     * Enable/disable {@link SocketOptions#SO_KEEPALIVE SO_KEEPALIVE}.
     *
     * <p>
     *  启用/停用{@link SocketOptions#SO_KEEPALIVE SO_KEEPALIVE}。
     * 
     * 
     * @param on  whether or not to have socket keep alive turned on.
     * @exception SocketException if there is an error
     * in the underlying protocol, such as a TCP error.
     * @since 1.3
     * @see #getKeepAlive()
     */
    public void setKeepAlive(boolean on) throws SocketException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        getImpl().setOption(SocketOptions.SO_KEEPALIVE, Boolean.valueOf(on));
    }

    /**
     * Tests if {@link SocketOptions#SO_KEEPALIVE SO_KEEPALIVE} is enabled.
     *
     * <p>
     *  测试是否启用{@link SocketOptions#SO_KEEPALIVE SO_KEEPALIVE}。
     * 
     * 
     * @return a {@code boolean} indicating whether or not
     *         {@link SocketOptions#SO_KEEPALIVE SO_KEEPALIVE} is enabled.
     * @exception SocketException if there is an error
     * in the underlying protocol, such as a TCP error.
     * @since   1.3
     * @see #setKeepAlive(boolean)
     */
    public boolean getKeepAlive() throws SocketException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        return ((Boolean) getImpl().getOption(SocketOptions.SO_KEEPALIVE)).booleanValue();
    }

    /**
     * Sets traffic class or type-of-service octet in the IP
     * header for packets sent from this Socket.
     * As the underlying network implementation may ignore this
     * value applications should consider it a hint.
     *
     * <P> The tc <B>must</B> be in the range {@code 0 <= tc <=
     * 255} or an IllegalArgumentException will be thrown.
     * <p>Notes:
     * <p>For Internet Protocol v4 the value consists of an
     * {@code integer}, the least significant 8 bits of which
     * represent the value of the TOS octet in IP packets sent by
     * the socket.
     * RFC 1349 defines the TOS values as follows:
     *
     * <UL>
     * <LI><CODE>IPTOS_LOWCOST (0x02)</CODE></LI>
     * <LI><CODE>IPTOS_RELIABILITY (0x04)</CODE></LI>
     * <LI><CODE>IPTOS_THROUGHPUT (0x08)</CODE></LI>
     * <LI><CODE>IPTOS_LOWDELAY (0x10)</CODE></LI>
     * </UL>
     * The last low order bit is always ignored as this
     * corresponds to the MBZ (must be zero) bit.
     * <p>
     * Setting bits in the precedence field may result in a
     * SocketException indicating that the operation is not
     * permitted.
     * <p>
     * As RFC 1122 section 4.2.4.2 indicates, a compliant TCP
     * implementation should, but is not required to, let application
     * change the TOS field during the lifetime of a connection.
     * So whether the type-of-service field can be changed after the
     * TCP connection has been established depends on the implementation
     * in the underlying platform. Applications should not assume that
     * they can change the TOS field after the connection.
     * <p>
     * For Internet Protocol v6 {@code tc} is the value that
     * would be placed into the sin6_flowinfo field of the IP header.
     *
     * <p>
     *  在从此Socket发送的数据包的IP报头中设置流量类或服务类型八位字节。由于底层网络实现可能忽略这个值,应用程序应该考虑一个提示。
     * 
     * <p> tc <B>必须</b>在{@code 0 <= tc <= 255}的范围内,否则将抛出IllegalArgumentException。
     *  <p>注意：<p>对于因特网协议v4,值由{@code integer}组成,其最低有效位表示套接字发送的IP包中TOS八位位组的值。 RFC 1349定义TOS值如下：。
     * 
     * <UL>
     *  <LI> <CODE> IPTOS_LOWCOST(0x02)</CODE> </LI> <LI> <CODE> IPTOS_RELIABILITY(0x04)</CODE> </LI> <LI> <CODE>
     *  IPTOS_THROUGHPUT </LI> <LI> <CODE> IPTOS_LOWDELAY(0x10)</CODE> </LI>。
     * </UL>
     *  最后的低阶位总是被忽略,因为这对应于MBZ(必须为零)位。
     * <p>
     *  设置优先级字段中的位可能导致SocketException,表示不允许操作。
     * <p>
     *  正如RFC 1122第4.2.4.2节所指出的,一个合规的TCP实现应该,但不是必须让应用程序在连接的生存期内改变TOS字段。
     * 因此,在TCP连接已经建立之后是否可以改变服务类型字段取决于在底层平台中的实现。应用程序不应假定它们可以在连接后更改TOS字段。
     * <p>
     *  对于Internet协议v6,{@code tc}是将放置到IP头的sin6_flowinfo字段中的值。
     * 
     * 
     * @param tc        an {@code int} value for the bitset.
     * @throws SocketException if there is an error setting the
     * traffic class or type-of-service
     * @since 1.4
     * @see #getTrafficClass
     * @see SocketOptions#IP_TOS
     */
    public void setTrafficClass(int tc) throws SocketException {
        if (tc < 0 || tc > 255)
            throw new IllegalArgumentException("tc is not in range 0 -- 255");

        if (isClosed())
            throw new SocketException("Socket is closed");
        getImpl().setOption(SocketOptions.IP_TOS, new Integer(tc));
    }

    /**
     * Gets traffic class or type-of-service in the IP header
     * for packets sent from this Socket
     * <p>
     * As the underlying network implementation may ignore the
     * traffic class or type-of-service set using {@link #setTrafficClass(int)}
     * this method may return a different value than was previously
     * set using the {@link #setTrafficClass(int)} method on this Socket.
     *
     * <p>
     *  在从此Socket发送的数据包的IP报头中获取流量类或服务类型
     * <p>
     * 由于底层网络实现可以使用{@link #setTrafficClass(int)}忽略流量类或服务类型集合,该方法可能返回不同于使用{@link #setTrafficClass(int)}方法设置的值
     * 在这个套接字上。
     * 
     * 
     * @return the traffic class or type-of-service already set
     * @throws SocketException if there is an error obtaining the
     * traffic class or type-of-service value.
     * @since 1.4
     * @see #setTrafficClass(int)
     * @see SocketOptions#IP_TOS
     */
    public int getTrafficClass() throws SocketException {
        return ((Integer) (getImpl().getOption(SocketOptions.IP_TOS))).intValue();
    }

    /**
     * Enable/disable the {@link SocketOptions#SO_REUSEADDR SO_REUSEADDR}
     * socket option.
     * <p>
     * When a TCP connection is closed the connection may remain
     * in a timeout state for a period of time after the connection
     * is closed (typically known as the {@code TIME_WAIT} state
     * or {@code 2MSL} wait state).
     * For applications using a well known socket address or port
     * it may not be possible to bind a socket to the required
     * {@code SocketAddress} if there is a connection in the
     * timeout state involving the socket address or port.
     * <p>
     * Enabling {@link SocketOptions#SO_REUSEADDR SO_REUSEADDR}
     * prior to binding the socket using {@link #bind(SocketAddress)} allows
     * the socket to be bound even though a previous connection is in a timeout
     * state.
     * <p>
     * When a {@code Socket} is created the initial setting
     * of {@link SocketOptions#SO_REUSEADDR SO_REUSEADDR} is disabled.
     * <p>
     * The behaviour when {@link SocketOptions#SO_REUSEADDR SO_REUSEADDR} is
     * enabled or disabled after a socket is bound (See {@link #isBound()})
     * is not defined.
     *
     * <p>
     *  启用/停用{@link SocketOptions#SO_REUSEADDR SO_REUSEADDR}套接字选项。
     * <p>
     *  当TCP连接关闭时,连接可能在连接关闭后保持在超时状态一段时间(通常称为{@code TIME_WAIT}状态或{@code 2MSL}等待状态)。
     * 对于使用众所周知的套接字地址或端口的应用程序,如果存在涉及套接字地址或端口的超时状态的连接,则可能无法将套接字绑定到所需的{@code SocketAddress}。
     * <p>
     *  在使用{@link #bind(SocketAddress)}绑定套接字之前启用{@link SocketOptions#SO_REUSEADDR SO_REUSEADDR}允许绑定套接字,即使前一个
     * 连接处于超时状态。
     * <p>
     *  当创建{@code Socket}时,将禁用{@link SocketOptions#SO_REUSEADDR SO_REUSEADDR}的初始设置。
     * <p>
     *  在绑定套接字后启用或禁用{@link SocketOptions#SO_REUSEADDR SO_REUSEADDR}时的行为(请参阅{@link #isBound()})未定义。
     * 
     * 
     * @param on  whether to enable or disable the socket option
     * @exception SocketException if an error occurs enabling or
     *            disabling the {@link SocketOptions#SO_REUSEADDR SO_REUSEADDR}
     *            socket option, or the socket is closed.
     * @since 1.4
     * @see #getReuseAddress()
     * @see #bind(SocketAddress)
     * @see #isClosed()
     * @see #isBound()
     */
    public void setReuseAddress(boolean on) throws SocketException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        getImpl().setOption(SocketOptions.SO_REUSEADDR, Boolean.valueOf(on));
    }

    /**
     * Tests if {@link SocketOptions#SO_REUSEADDR SO_REUSEADDR} is enabled.
     *
     * <p>
     *  测试是否启用{@link SocketOptions#SO_REUSEADDR SO_REUSEADDR}。
     * 
     * 
     * @return a {@code boolean} indicating whether or not
     *         {@link SocketOptions#SO_REUSEADDR SO_REUSEADDR} is enabled.
     * @exception SocketException if there is an error
     * in the underlying protocol, such as a TCP error.
     * @since   1.4
     * @see #setReuseAddress(boolean)
     */
    public boolean getReuseAddress() throws SocketException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        return ((Boolean) (getImpl().getOption(SocketOptions.SO_REUSEADDR))).booleanValue();
    }

    /**
     * Closes this socket.
     * <p>
     * Any thread currently blocked in an I/O operation upon this socket
     * will throw a {@link SocketException}.
     * <p>
     * Once a socket has been closed, it is not available for further networking
     * use (i.e. can't be reconnected or rebound). A new socket needs to be
     * created.
     *
     * <p> Closing this socket will also close the socket's
     * {@link java.io.InputStream InputStream} and
     * {@link java.io.OutputStream OutputStream}.
     *
     * <p> If this socket has an associated channel then the channel is closed
     * as well.
     *
     * <p>
     *  关闭此套接字。
     * <p>
     *  任何在I / O操作中被阻塞的线程将抛出一个{@link SocketException}。
     * <p>
     * 一旦套接字已经关闭,它不能用于进一步的网络使用(即不能重新连接或重新绑定)。需要创建一个新的套接字。
     * 
     *  <p>关闭此套接字也会关闭套接字的{@link java.io.InputStream InputStream}和{@link java.io.OutputStream OutputStream}。
     * 
     *  <p>如果此套接字具有相关联的频道,则频道也会关闭。
     * 
     * 
     * @exception  IOException  if an I/O error occurs when closing this socket.
     * @revised 1.4
     * @spec JSR-51
     * @see #isClosed
     */
    public synchronized void close() throws IOException {
        synchronized(closeLock) {
            if (isClosed())
                return;
            if (created)
                impl.close();
            closed = true;
        }
    }

    /**
     * Places the input stream for this socket at "end of stream".
     * Any data sent to the input stream side of the socket is acknowledged
     * and then silently discarded.
     * <p>
     * If you read from a socket input stream after invoking this method on the
     * socket, the stream's {@code available} method will return 0, and its
     * {@code read} methods will return {@code -1} (end of stream).
     *
     * <p>
     *  将此套接字的输入流放置在"流结束"。发送到套接字的输入流端的任何数据都会被确认,然后被静默丢弃。
     * <p>
     *  如果你在套接字上调用这个方法之后从套接字输入流中读取,流的{@code available}方法将返回0,它的{@code read}方法将返回{@code -1}(流结束)。
     * 
     * 
     * @exception IOException if an I/O error occurs when shutting down this
     * socket.
     *
     * @since 1.3
     * @see java.net.Socket#shutdownOutput()
     * @see java.net.Socket#close()
     * @see java.net.Socket#setSoLinger(boolean, int)
     * @see #isInputShutdown
     */
    public void shutdownInput() throws IOException
    {
        if (isClosed())
            throw new SocketException("Socket is closed");
        if (!isConnected())
            throw new SocketException("Socket is not connected");
        if (isInputShutdown())
            throw new SocketException("Socket input is already shutdown");
        getImpl().shutdownInput();
        shutIn = true;
    }

    /**
     * Disables the output stream for this socket.
     * For a TCP socket, any previously written data will be sent
     * followed by TCP's normal connection termination sequence.
     *
     * If you write to a socket output stream after invoking
     * shutdownOutput() on the socket, the stream will throw
     * an IOException.
     *
     * <p>
     *  禁用此套接字的输出流。对于TCP套接字,任何先前写入的数据将被发送,之后是TCP的正常连接终止序列。
     * 
     *  如果在套接字上调用shutdownOutput()之后写入套接字输出流,流将抛出一个IOException异常。
     * 
     * 
     * @exception IOException if an I/O error occurs when shutting down this
     * socket.
     *
     * @since 1.3
     * @see java.net.Socket#shutdownInput()
     * @see java.net.Socket#close()
     * @see java.net.Socket#setSoLinger(boolean, int)
     * @see #isOutputShutdown
     */
    public void shutdownOutput() throws IOException
    {
        if (isClosed())
            throw new SocketException("Socket is closed");
        if (!isConnected())
            throw new SocketException("Socket is not connected");
        if (isOutputShutdown())
            throw new SocketException("Socket output is already shutdown");
        getImpl().shutdownOutput();
        shutOut = true;
    }

    /**
     * Converts this socket to a {@code String}.
     *
     * <p>
     *  将此套接字转换为{@code String}。
     * 
     * 
     * @return  a string representation of this socket.
     */
    public String toString() {
        try {
            if (isConnected())
                return "Socket[addr=" + getImpl().getInetAddress() +
                    ",port=" + getImpl().getPort() +
                    ",localport=" + getImpl().getLocalPort() + "]";
        } catch (SocketException e) {
        }
        return "Socket[unconnected]";
    }

    /**
     * Returns the connection state of the socket.
     * <p>
     * Note: Closing a socket doesn't clear its connection state, which means
     * this method will return {@code true} for a closed socket
     * (see {@link #isClosed()}) if it was successfuly connected prior
     * to being closed.
     *
     * <p>
     *  返回套接字的连接状态。
     * <p>
     *  注意：关闭套接字不会清除其连接状态,这意味着如果在关闭之前连接成功,此方法将返回{@code true}一个封闭套接字(参见{@link #isClosed()})。
     * 
     * 
     * @return true if the socket was successfuly connected to a server
     * @since 1.4
     */
    public boolean isConnected() {
        // Before 1.3 Sockets were always connected during creation
        return connected || oldImpl;
    }

    /**
     * Returns the binding state of the socket.
     * <p>
     * Note: Closing a socket doesn't clear its binding state, which means
     * this method will return {@code true} for a closed socket
     * (see {@link #isClosed()}) if it was successfuly bound prior
     * to being closed.
     *
     * <p>
     *  返回套接字的绑定状态。
     * <p>
     * 注意：关闭套接字不会清除其绑定状态,这意味着如果在关闭套接字之前已成功绑定,则此方法将返回{@code true}一个关闭的套接字(见{@link #isClosed()})。
     * 
     * 
     * @return true if the socket was successfuly bound to an address
     * @since 1.4
     * @see #bind
     */
    public boolean isBound() {
        // Before 1.3 Sockets were always bound during creation
        return bound || oldImpl;
    }

    /**
     * Returns the closed state of the socket.
     *
     * <p>
     *  返回套接字的关闭状态。
     * 
     * 
     * @return true if the socket has been closed
     * @since 1.4
     * @see #close
     */
    public boolean isClosed() {
        synchronized(closeLock) {
            return closed;
        }
    }

    /**
     * Returns whether the read-half of the socket connection is closed.
     *
     * <p>
     *  返回套接字连接的read-half是否关闭。
     * 
     * 
     * @return true if the input of the socket has been shutdown
     * @since 1.4
     * @see #shutdownInput
     */
    public boolean isInputShutdown() {
        return shutIn;
    }

    /**
     * Returns whether the write-half of the socket connection is closed.
     *
     * <p>
     *  返回套接字连接的半写是否关闭。
     * 
     * 
     * @return true if the output of the socket has been shutdown
     * @since 1.4
     * @see #shutdownOutput
     */
    public boolean isOutputShutdown() {
        return shutOut;
    }

    /**
     * The factory for all client sockets.
     * <p>
     *  所有客户端插座的工厂。
     * 
     */
    private static SocketImplFactory factory = null;

    /**
     * Sets the client socket implementation factory for the
     * application. The factory can be specified only once.
     * <p>
     * When an application creates a new client socket, the socket
     * implementation factory's {@code createSocketImpl} method is
     * called to create the actual socket implementation.
     * <p>
     * Passing {@code null} to the method is a no-op unless the factory
     * was already set.
     * <p>If there is a security manager, this method first calls
     * the security manager's {@code checkSetFactory} method
     * to ensure the operation is allowed.
     * This could result in a SecurityException.
     *
     * <p>
     *  设置应用程序的客户端套接字实现工厂。工厂只能指定一次。
     * <p>
     *  当应用程序创建一个新的客户端套接字时,将调用套接字实现工厂的{@code createSocketImpl}方法来创建实际的套接字实现。
     * <p>
     *  将{@code null}传递给方法是一个无操作,除非工厂已经设置。 <p>如果有安全管理员,此方法会先调用安全管理员的{@code checkSetFactory}方法,以确保允许操作。
     * 这可能导致SecurityException。
     * 
     * 
     * @param      fac   the desired factory.
     * @exception  IOException  if an I/O error occurs when setting the
     *               socket factory.
     * @exception  SocketException  if the factory is already defined.
     * @exception  SecurityException  if a security manager exists and its
     *             {@code checkSetFactory} method doesn't allow the operation.
     * @see        java.net.SocketImplFactory#createSocketImpl()
     * @see        SecurityManager#checkSetFactory
     */
    public static synchronized void setSocketImplFactory(SocketImplFactory fac)
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
     * Sets performance preferences for this socket.
     *
     * <p> Sockets use the TCP/IP protocol by default.  Some implementations
     * may offer alternative protocols which have different performance
     * characteristics than TCP/IP.  This method allows the application to
     * express its own preferences as to how these tradeoffs should be made
     * when the implementation chooses from the available protocols.
     *
     * <p> Performance preferences are described by three integers
     * whose values indicate the relative importance of short connection time,
     * low latency, and high bandwidth.  The absolute values of the integers
     * are irrelevant; in order to choose a protocol the values are simply
     * compared, with larger values indicating stronger preferences. Negative
     * values represent a lower priority than positive values. If the
     * application prefers short connection time over both low latency and high
     * bandwidth, for example, then it could invoke this method with the values
     * {@code (1, 0, 0)}.  If the application prefers high bandwidth above low
     * latency, and low latency above short connection time, then it could
     * invoke this method with the values {@code (0, 1, 2)}.
     *
     * <p> Invoking this method after this socket has been connected
     * will have no effect.
     *
     * <p>
     *  设置此套接字的性能首选项。
     * 
     *  <p>套接字默认使用TCP / IP协议。一些实现可以提供具有与TCP / IP不同的性能特征的替代协议。该方法允许应用表达其自己关于当实现从可用协议选择时如何进行这些折衷的偏好。
     * 
     * <p>性能偏好由三个整数描述,其值指示短连接时间,低延迟和高带宽的相对重要性。整数的绝对值是不相关的;为了选择协议,简单地比较值,较大的值指示较强的偏好。负值表示比正值低的优先级。
     * 
     * @param  connectionTime
     *         An {@code int} expressing the relative importance of a short
     *         connection time
     *
     * @param  latency
     *         An {@code int} expressing the relative importance of low
     *         latency
     *
     * @param  bandwidth
     *         An {@code int} expressing the relative importance of high
     *         bandwidth
     *
     * @since 1.5
     */
    public void setPerformancePreferences(int connectionTime,
                                          int latency,
                                          int bandwidth)
    {
        /* Not implemented yet */
    }
}
