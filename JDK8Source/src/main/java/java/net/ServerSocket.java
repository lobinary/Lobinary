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

import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;

/**
 * This class implements server sockets. A server socket waits for
 * requests to come in over the network. It performs some operation
 * based on that request, and then possibly returns a result to the requester.
 * <p>
 * The actual work of the server socket is performed by an instance
 * of the {@code SocketImpl} class. An application can
 * change the socket factory that creates the socket
 * implementation to configure itself to create sockets
 * appropriate to the local firewall.
 *
 * <p>
 *  此类实现服务器套接字。服务器套接字等待通过网络进入的请求。它基于该请求执行一些操作,然后可能将结果返回给请求者。
 * <p>
 *  服务器套接字的实际工作由{@code SocketImpl}类的实例执行。应用程序可以更改创建套接字实现的套接字工厂,以便自己配置为创建适合本地防火墙的套接字。
 * 
 * 
 * @author  unascribed
 * @see     java.net.SocketImpl
 * @see     java.net.ServerSocket#setSocketFactory(java.net.SocketImplFactory)
 * @see     java.nio.channels.ServerSocketChannel
 * @since   JDK1.0
 */
public
class ServerSocket implements java.io.Closeable {
    /**
     * Various states of this socket.
     * <p>
     *  此插座的各种状态。
     * 
     */
    private boolean created = false;
    private boolean bound = false;
    private boolean closed = false;
    private Object closeLock = new Object();

    /**
     * The implementation of this Socket.
     * <p>
     *  这个Socket的实现。
     * 
     */
    private SocketImpl impl;

    /**
     * Are we using an older SocketImpl?
     * <p>
     *  我们使用较老的SocketImpl吗?
     * 
     */
    private boolean oldImpl = false;

    /**
     * Package-private constructor to create a ServerSocket associated with
     * the given SocketImpl.
     * <p>
     *  Package-private构造函数创建与给定SocketImpl相关联的ServerSocket。
     * 
     */
    ServerSocket(SocketImpl impl) {
        this.impl = impl;
        impl.setServerSocket(this);
    }

    /**
     * Creates an unbound server socket.
     *
     * <p>
     *  创建未绑定的服务器套接字。
     * 
     * 
     * @exception IOException IO error when opening the socket.
     * @revised 1.4
     */
    public ServerSocket() throws IOException {
        setImpl();
    }

    /**
     * Creates a server socket, bound to the specified port. A port number
     * of {@code 0} means that the port number is automatically
     * allocated, typically from an ephemeral port range. This port
     * number can then be retrieved by calling {@link #getLocalPort getLocalPort}.
     * <p>
     * The maximum queue length for incoming connection indications (a
     * request to connect) is set to {@code 50}. If a connection
     * indication arrives when the queue is full, the connection is refused.
     * <p>
     * If the application has specified a server socket factory, that
     * factory's {@code createSocketImpl} method is called to create
     * the actual socket implementation. Otherwise a "plain" socket is created.
     * <p>
     * If there is a security manager,
     * its {@code checkListen} method is called
     * with the {@code port} argument
     * as its argument to ensure the operation is allowed.
     * This could result in a SecurityException.
     *
     *
     * <p>
     *  创建一个服务器套接字,绑定到指定的端口。端口号{@code 0}表示端口号被自动分配,通常来自临时端口范围。
     * 然后可以通过调用{@link #getLocalPort getLocalPort}来检索此端口号。
     * <p>
     *  传入连接指示(连接请求)的最大队列长度设置为{@code 50}。如果在队列已满时连接指示到达,则拒绝连接。
     * <p>
     *  如果应用程序指定了一个服务器套接字工厂,则调用该工厂的{@code createSocketImpl}方法来创建实际的套接字实现。否则创建一个"纯"套接字。
     * <p>
     * 如果有安全管理器,则会调用其{@code checkListen}方法,并使用{@code port}参数作为其参数,以确保允许操作。这可能导致SecurityException。
     * 
     * 
     * @param      port  the port number, or {@code 0} to use a port
     *                   number that is automatically allocated.
     *
     * @exception  IOException  if an I/O error occurs when opening the socket.
     * @exception  SecurityException
     * if a security manager exists and its {@code checkListen}
     * method doesn't allow the operation.
     * @exception  IllegalArgumentException if the port parameter is outside
     *             the specified range of valid port values, which is between
     *             0 and 65535, inclusive.
     *
     * @see        java.net.SocketImpl
     * @see        java.net.SocketImplFactory#createSocketImpl()
     * @see        java.net.ServerSocket#setSocketFactory(java.net.SocketImplFactory)
     * @see        SecurityManager#checkListen
     */
    public ServerSocket(int port) throws IOException {
        this(port, 50, null);
    }

    /**
     * Creates a server socket and binds it to the specified local port
     * number, with the specified backlog.
     * A port number of {@code 0} means that the port number is
     * automatically allocated, typically from an ephemeral port range.
     * This port number can then be retrieved by calling
     * {@link #getLocalPort getLocalPort}.
     * <p>
     * The maximum queue length for incoming connection indications (a
     * request to connect) is set to the {@code backlog} parameter. If
     * a connection indication arrives when the queue is full, the
     * connection is refused.
     * <p>
     * If the application has specified a server socket factory, that
     * factory's {@code createSocketImpl} method is called to create
     * the actual socket implementation. Otherwise a "plain" socket is created.
     * <p>
     * If there is a security manager,
     * its {@code checkListen} method is called
     * with the {@code port} argument
     * as its argument to ensure the operation is allowed.
     * This could result in a SecurityException.
     *
     * The {@code backlog} argument is the requested maximum number of
     * pending connections on the socket. Its exact semantics are implementation
     * specific. In particular, an implementation may impose a maximum length
     * or may choose to ignore the parameter altogther. The value provided
     * should be greater than {@code 0}. If it is less than or equal to
     * {@code 0}, then an implementation specific default will be used.
     * <P>
     *
     * <p>
     *  创建服务器套接字并将其绑定到指定的本地端口号,以及指定的待办事项。端口号{@code 0}表示端口号被自动分配,通常来自临时端口范围。
     * 然后可以通过调用{@link #getLocalPort getLocalPort}来检索此端口号。
     * <p>
     *  传入连接指示(连接请求)的最大队列长度设置为{@code backlog}参数。如果在队列已满时连接指示到达,则拒绝连接。
     * <p>
     *  如果应用程序指定了一个服务器套接字工厂,则调用该工厂的{@code createSocketImpl}方法来创建实际的套接字实现。否则创建一个"纯"套接字。
     * <p>
     *  如果有安全管理器,则会调用其{@code checkListen}方法,并使用{@code port}参数作为其参数,以确保允许操作。这可能导致SecurityException。
     * 
     * {@code backlog}参数是套接字上请求的最大挂起连接数。它的确切语义是实现特定的。具体地,实现可以施加最大长度,或者可以选择忽略参数等。提供的值应大于{@code 0}。
     * 如果它小于或等于{@code 0},那么将使用特定于实现的默认值。
     * <P>
     * 
     * 
     * @param      port     the port number, or {@code 0} to use a port
     *                      number that is automatically allocated.
     * @param      backlog  requested maximum length of the queue of incoming
     *                      connections.
     *
     * @exception  IOException  if an I/O error occurs when opening the socket.
     * @exception  SecurityException
     * if a security manager exists and its {@code checkListen}
     * method doesn't allow the operation.
     * @exception  IllegalArgumentException if the port parameter is outside
     *             the specified range of valid port values, which is between
     *             0 and 65535, inclusive.
     *
     * @see        java.net.SocketImpl
     * @see        java.net.SocketImplFactory#createSocketImpl()
     * @see        java.net.ServerSocket#setSocketFactory(java.net.SocketImplFactory)
     * @see        SecurityManager#checkListen
     */
    public ServerSocket(int port, int backlog) throws IOException {
        this(port, backlog, null);
    }

    /**
     * Create a server with the specified port, listen backlog, and
     * local IP address to bind to.  The <i>bindAddr</i> argument
     * can be used on a multi-homed host for a ServerSocket that
     * will only accept connect requests to one of its addresses.
     * If <i>bindAddr</i> is null, it will default accepting
     * connections on any/all local addresses.
     * The port must be between 0 and 65535, inclusive.
     * A port number of {@code 0} means that the port number is
     * automatically allocated, typically from an ephemeral port range.
     * This port number can then be retrieved by calling
     * {@link #getLocalPort getLocalPort}.
     *
     * <P>If there is a security manager, this method
     * calls its {@code checkListen} method
     * with the {@code port} argument
     * as its argument to ensure the operation is allowed.
     * This could result in a SecurityException.
     *
     * The {@code backlog} argument is the requested maximum number of
     * pending connections on the socket. Its exact semantics are implementation
     * specific. In particular, an implementation may impose a maximum length
     * or may choose to ignore the parameter altogther. The value provided
     * should be greater than {@code 0}. If it is less than or equal to
     * {@code 0}, then an implementation specific default will be used.
     * <P>
     * <p>
     *  创建具有指定端口,侦听待办事项和要绑定的本地IP地址的服务器。 <i> bindAddr </i>参数可用于多宿主主机上的ServerSocket,它只接受连接请求到它的一个地址。
     * 如果<i> bindAddr </i>为空,它将默认接受任何/所有本地地址的连接。端口必须介于0和65535之间(包括0和65535)。
     * 端口号{@code 0}表示端口号被自动分配,通常来自临时端口范围。然后可以通过调用{@link #getLocalPort getLocalPort}来检索此端口号。
     * 
     *  <P>如果有安全管理器,此方法将使用{@code port}参数作为其参数来调用其{@code checkListen}方法,以确保允许操作。这可能导致SecurityException。
     * 
     * {@code backlog}参数是套接字上请求的最大挂起连接数。它的确切语义是实现特定的。具体地,实现可以施加最大长度,或者可以选择忽略参数等。提供的值应大于{@code 0}。
     * 如果它小于或等于{@code 0},那么将使用特定于实现的默认值。
     * <P>
     * 
     * @param port  the port number, or {@code 0} to use a port
     *              number that is automatically allocated.
     * @param backlog requested maximum length of the queue of incoming
     *                connections.
     * @param bindAddr the local InetAddress the server will bind to
     *
     * @throws  SecurityException if a security manager exists and
     * its {@code checkListen} method doesn't allow the operation.
     *
     * @throws  IOException if an I/O error occurs when opening the socket.
     * @exception  IllegalArgumentException if the port parameter is outside
     *             the specified range of valid port values, which is between
     *             0 and 65535, inclusive.
     *
     * @see SocketOptions
     * @see SocketImpl
     * @see SecurityManager#checkListen
     * @since   JDK1.1
     */
    public ServerSocket(int port, int backlog, InetAddress bindAddr) throws IOException {
        setImpl();
        if (port < 0 || port > 0xFFFF)
            throw new IllegalArgumentException(
                       "Port value out of range: " + port);
        if (backlog < 1)
          backlog = 50;
        try {
            bind(new InetSocketAddress(bindAddr, port), backlog);
        } catch(SecurityException e) {
            close();
            throw e;
        } catch(IOException e) {
            close();
            throw e;
        }
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
     * @throws SocketException if creation fails.
     * @since 1.4
     */
    SocketImpl getImpl() throws SocketException {
        if (!created)
            createImpl();
        return impl;
    }

    private void checkOldImpl() {
        if (impl == null)
            return;
        // SocketImpl.connect() is a protected method, therefore we need to use
        // getDeclaredMethod, therefore we need permission to access the member
        try {
            AccessController.doPrivileged(
                new PrivilegedExceptionAction<Void>() {
                    public Void run() throws NoSuchMethodException {
                        impl.getClass().getDeclaredMethod("connect",
                                                          SocketAddress.class,
                                                          int.class);
                        return null;
                    }
                });
        } catch (java.security.PrivilegedActionException e) {
            oldImpl = true;
        }
    }

    private void setImpl() {
        if (factory != null) {
            impl = factory.createSocketImpl();
            checkOldImpl();
        } else {
            // No need to do a checkOldImpl() here, we know it's an up to date
            // SocketImpl!
            impl = new SocksSocketImpl();
        }
        if (impl != null)
            impl.setServerSocket(this);
    }

    /**
     * Creates the socket implementation.
     *
     * <p>
     *  创建套接字实现。
     * 
     * 
     * @throws IOException if creation fails
     * @since 1.4
     */
    void createImpl() throws SocketException {
        if (impl == null)
            setImpl();
        try {
            impl.create(true);
            created = true;
        } catch (IOException e) {
            throw new SocketException(e.getMessage());
        }
    }

    /**
     *
     * Binds the {@code ServerSocket} to a specific address
     * (IP address and port number).
     * <p>
     * If the address is {@code null}, then the system will pick up
     * an ephemeral port and a valid local address to bind the socket.
     * <p>
     * <p>
     *  将{@code ServerSocket}绑定到特定地址(IP地址和端口号)。
     * <p>
     *  如果地址是{@code null},那么系统将拾取一个临时端口和一个有效的本地地址来绑定套接字。
     * <p>
     * 
     * @param   endpoint        The IP address and port number to bind to.
     * @throws  IOException if the bind operation fails, or if the socket
     *                     is already bound.
     * @throws  SecurityException       if a {@code SecurityManager} is present and
     * its {@code checkListen} method doesn't allow the operation.
     * @throws  IllegalArgumentException if endpoint is a
     *          SocketAddress subclass not supported by this socket
     * @since 1.4
     */
    public void bind(SocketAddress endpoint) throws IOException {
        bind(endpoint, 50);
    }

    /**
     *
     * Binds the {@code ServerSocket} to a specific address
     * (IP address and port number).
     * <p>
     * If the address is {@code null}, then the system will pick up
     * an ephemeral port and a valid local address to bind the socket.
     * <P>
     * The {@code backlog} argument is the requested maximum number of
     * pending connections on the socket. Its exact semantics are implementation
     * specific. In particular, an implementation may impose a maximum length
     * or may choose to ignore the parameter altogther. The value provided
     * should be greater than {@code 0}. If it is less than or equal to
     * {@code 0}, then an implementation specific default will be used.
     * <p>
     *  将{@code ServerSocket}绑定到特定地址(IP地址和端口号)。
     * <p>
     *  如果地址是{@code null},那么系统将拾取一个临时端口和一个有效的本地地址来绑定套接字。
     * <P>
     *  {@code backlog}参数是套接字上请求的最大挂起连接数。它的确切语义是实现特定的。具体地,实现可以施加最大长度,或者可以选择忽略参数等。提供的值应大于{@code 0}。
     * 如果它小于或等于{@code 0},那么将使用特定于实现的默认值。
     * 
     * 
     * @param   endpoint        The IP address and port number to bind to.
     * @param   backlog         requested maximum length of the queue of
     *                          incoming connections.
     * @throws  IOException if the bind operation fails, or if the socket
     *                     is already bound.
     * @throws  SecurityException       if a {@code SecurityManager} is present and
     * its {@code checkListen} method doesn't allow the operation.
     * @throws  IllegalArgumentException if endpoint is a
     *          SocketAddress subclass not supported by this socket
     * @since 1.4
     */
    public void bind(SocketAddress endpoint, int backlog) throws IOException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        if (!oldImpl && isBound())
            throw new SocketException("Already bound");
        if (endpoint == null)
            endpoint = new InetSocketAddress(0);
        if (!(endpoint instanceof InetSocketAddress))
            throw new IllegalArgumentException("Unsupported address type");
        InetSocketAddress epoint = (InetSocketAddress) endpoint;
        if (epoint.isUnresolved())
            throw new SocketException("Unresolved address");
        if (backlog < 1)
          backlog = 50;
        try {
            SecurityManager security = System.getSecurityManager();
            if (security != null)
                security.checkListen(epoint.getPort());
            getImpl().bind(epoint.getAddress(), epoint.getPort());
            getImpl().listen(backlog);
            bound = true;
        } catch(SecurityException e) {
            bound = false;
            throw e;
        } catch(IOException e) {
            bound = false;
            throw e;
        }
    }

    /**
     * Returns the local address of this server socket.
     * <p>
     * If the socket was bound prior to being {@link #close closed},
     * then this method will continue to return the local address
     * after the socket is closed.
     * <p>
     * If there is a security manager set, its {@code checkConnect} method is
     * called with the local address and {@code -1} as its arguments to see
     * if the operation is allowed. If the operation is not allowed,
     * the {@link InetAddress#getLoopbackAddress loopback} address is returned.
     *
     * <p>
     *  返回此服务器套接字的本地地址。
     * <p>
     * 如果套接字在{@link #close closed}之前被绑定,则该方法将在套接字关闭后继续返回本地地址。
     * <p>
     *  如果存在安全管理器集,则会使用本地地址和{@code -1}作为其参数来调用其{@code checkConnect}方法,以查看是否允许该操作。
     * 如果不允许该操作,则返回{@link InetAddress#getLoopbackAddress loopback}地址。
     * 
     * 
     * @return  the address to which this socket is bound,
     *          or the loopback address if denied by the security manager,
     *          or {@code null} if the socket is unbound.
     *
     * @see SecurityManager#checkConnect
     */
    public InetAddress getInetAddress() {
        if (!isBound())
            return null;
        try {
            InetAddress in = getImpl().getInetAddress();
            SecurityManager sm = System.getSecurityManager();
            if (sm != null)
                sm.checkConnect(in.getHostAddress(), -1);
            return in;
        } catch (SecurityException e) {
            return InetAddress.getLoopbackAddress();
        } catch (SocketException e) {
            // nothing
            // If we're bound, the impl has been created
            // so we shouldn't get here
        }
        return null;
    }

    /**
     * Returns the port number on which this socket is listening.
     * <p>
     * If the socket was bound prior to being {@link #close closed},
     * then this method will continue to return the port number
     * after the socket is closed.
     *
     * <p>
     *  返回此套接字正在侦听的端口号。
     * <p>
     *  如果套接字在{@link #close closed}之前绑定,则此方法将在套接字关闭后继续返回端口号。
     * 
     * 
     * @return  the port number to which this socket is listening or
     *          -1 if the socket is not bound yet.
     */
    public int getLocalPort() {
        if (!isBound())
            return -1;
        try {
            return getImpl().getLocalPort();
        } catch (SocketException e) {
            // nothing
            // If we're bound, the impl has been created
            // so we shouldn't get here
        }
        return -1;
    }

    /**
     * Returns the address of the endpoint this socket is bound to.
     * <p>
     * If the socket was bound prior to being {@link #close closed},
     * then this method will continue to return the address of the endpoint
     * after the socket is closed.
     * <p>
     * If there is a security manager set, its {@code checkConnect} method is
     * called with the local address and {@code -1} as its arguments to see
     * if the operation is allowed. If the operation is not allowed,
     * a {@code SocketAddress} representing the
     * {@link InetAddress#getLoopbackAddress loopback} address and the local
     * port to which the socket is bound is returned.
     *
     * <p>
     *  返回此套接字绑定到的端点的地址。
     * <p>
     *  如果在{@link #close closed}之前绑定套接字,则该方法将在套接字关闭后继续返回端点的地址。
     * <p>
     *  如果存在安全管理器集,则会使用本地地址和{@code -1}作为其参数来调用其{@code checkConnect}方法,以查看是否允许该操作。
     * 如果不允许该操作,则返回表示{@link InetAddress#getLoopbackAddress loopback}地址和套接字绑定到的本地端口的{@code SocketAddress}。
     * 
     * 
     * @return a {@code SocketAddress} representing the local endpoint of
     *         this socket, or a {@code SocketAddress} representing the
     *         loopback address if denied by the security manager,
     *         or {@code null} if the socket is not bound yet.
     *
     * @see #getInetAddress()
     * @see #getLocalPort()
     * @see #bind(SocketAddress)
     * @see SecurityManager#checkConnect
     * @since 1.4
     */

    public SocketAddress getLocalSocketAddress() {
        if (!isBound())
            return null;
        return new InetSocketAddress(getInetAddress(), getLocalPort());
    }

    /**
     * Listens for a connection to be made to this socket and accepts
     * it. The method blocks until a connection is made.
     *
     * <p>A new Socket {@code s} is created and, if there
     * is a security manager,
     * the security manager's {@code checkAccept} method is called
     * with {@code s.getInetAddress().getHostAddress()} and
     * {@code s.getPort()}
     * as its arguments to ensure the operation is allowed.
     * This could result in a SecurityException.
     *
     * <p>
     *  监听要对此套接字进行的连接并接受它。该方法阻塞直到建立连接。
     * 
     * <p>创建了一个新的Socket {@code s},如果有安全管理器,则使用{@code s.getInetAddress()。
     * getHostAddress()}和{@ code}调用安全管理器的{@code checkAccept}代码s.getPort()}作为其参数,以确保允许操作。
     * 这可能导致SecurityException。
     * 
     * 
     * @exception  IOException  if an I/O error occurs when waiting for a
     *               connection.
     * @exception  SecurityException  if a security manager exists and its
     *             {@code checkAccept} method doesn't allow the operation.
     * @exception  SocketTimeoutException if a timeout was previously set with setSoTimeout and
     *             the timeout has been reached.
     * @exception  java.nio.channels.IllegalBlockingModeException
     *             if this socket has an associated channel, the channel is in
     *             non-blocking mode, and there is no connection ready to be
     *             accepted
     *
     * @return the new Socket
     * @see SecurityManager#checkAccept
     * @revised 1.4
     * @spec JSR-51
     */
    public Socket accept() throws IOException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        if (!isBound())
            throw new SocketException("Socket is not bound yet");
        Socket s = new Socket((SocketImpl) null);
        implAccept(s);
        return s;
    }

    /**
     * Subclasses of ServerSocket use this method to override accept()
     * to return their own subclass of socket.  So a FooServerSocket
     * will typically hand this method an <i>empty</i> FooSocket.  On
     * return from implAccept the FooSocket will be connected to a client.
     *
     * <p>
     *  ServerSocket的子类使用此方法重写accept()以返回其自己的套接字子类。所以一个FooServerSocket通常会把这个方法作为一个空的</i> FooSocket。
     * 在从implAccept返回时,FooSocket将被连接到客户端。
     * 
     * 
     * @param s the Socket
     * @throws java.nio.channels.IllegalBlockingModeException
     *         if this socket has an associated channel,
     *         and the channel is in non-blocking mode
     * @throws IOException if an I/O error occurs when waiting
     * for a connection.
     * @since   JDK1.1
     * @revised 1.4
     * @spec JSR-51
     */
    protected final void implAccept(Socket s) throws IOException {
        SocketImpl si = null;
        try {
            if (s.impl == null)
              s.setImpl();
            else {
                s.impl.reset();
            }
            si = s.impl;
            s.impl = null;
            si.address = new InetAddress();
            si.fd = new FileDescriptor();
            getImpl().accept(si);

            SecurityManager security = System.getSecurityManager();
            if (security != null) {
                security.checkAccept(si.getInetAddress().getHostAddress(),
                                     si.getPort());
            }
        } catch (IOException e) {
            if (si != null)
                si.reset();
            s.impl = si;
            throw e;
        } catch (SecurityException e) {
            if (si != null)
                si.reset();
            s.impl = si;
            throw e;
        }
        s.impl = si;
        s.postAccept();
    }

    /**
     * Closes this socket.
     *
     * Any thread currently blocked in {@link #accept()} will throw
     * a {@link SocketException}.
     *
     * <p> If this socket has an associated channel then the channel is closed
     * as well.
     *
     * <p>
     *  关闭此套接字。
     * 
     *  任何当前在{@link #accept()}中屏蔽的帖子都会抛出{@link SocketException}。
     * 
     *  <p>如果此套接字具有相关联的频道,则频道也会关闭。
     * 
     * 
     * @exception  IOException  if an I/O error occurs when closing the socket.
     * @revised 1.4
     * @spec JSR-51
     */
    public void close() throws IOException {
        synchronized(closeLock) {
            if (isClosed())
                return;
            if (created)
                impl.close();
            closed = true;
        }
    }

    /**
     * Returns the unique {@link java.nio.channels.ServerSocketChannel} object
     * associated with this socket, if any.
     *
     * <p> A server socket will have a channel if, and only if, the channel
     * itself was created via the {@link
     * java.nio.channels.ServerSocketChannel#open ServerSocketChannel.open}
     * method.
     *
     * <p>
     *  返回与此套接字关联的唯一{@link java.nio.channels.ServerSocketChannel}对象(如果有)。
     * 
     *  <p>当且仅当通道本身是通过{@link java.nio.channels.ServerSocketChannel#open ServerSocketChannel.open}方法创建时,服务器套接
     * 字才有一个通道。
     * 
     * 
     * @return  the server-socket channel associated with this socket,
     *          or {@code null} if this socket was not created
     *          for a channel
     *
     * @since 1.4
     * @spec JSR-51
     */
    public ServerSocketChannel getChannel() {
        return null;
    }

    /**
     * Returns the binding state of the ServerSocket.
     *
     * <p>
     *  返回ServerSocket的绑定状态。
     * 
     * 
     * @return true if the ServerSocket successfully bound to an address
     * @since 1.4
     */
    public boolean isBound() {
        // Before 1.3 ServerSockets were always bound during creation
        return bound || oldImpl;
    }

    /**
     * Returns the closed state of the ServerSocket.
     *
     * <p>
     *  返回ServerSocket的关闭状态。
     * 
     * 
     * @return true if the socket has been closed
     * @since 1.4
     */
    public boolean isClosed() {
        synchronized(closeLock) {
            return closed;
        }
    }

    /**
     * Enable/disable {@link SocketOptions#SO_TIMEOUT SO_TIMEOUT} with the
     * specified timeout, in milliseconds.  With this option set to a non-zero
     * timeout, a call to accept() for this ServerSocket
     * will block for only this amount of time.  If the timeout expires,
     * a <B>java.net.SocketTimeoutException</B> is raised, though the
     * ServerSocket is still valid.  The option <B>must</B> be enabled
     * prior to entering the blocking operation to have effect.  The
     * timeout must be {@code > 0}.
     * A timeout of zero is interpreted as an infinite timeout.
     * <p>
     * 以指定的超时值(以毫秒为单位)启用/禁用{@link SocketOptions#SO_TIMEOUT SO_TIMEOUT}。
     * 将此选项设置为非零超时,对此ServerSocket的accept()调用将仅阻止此时间量。
     * 如果超时到期,则会引发一个<B> java.net.SocketTimeoutException </B>,虽然ServerSocket仍然有效。
     * 必须先启用选项<B> </B>,然后才能进入阻止操作才能生效。超时必须为{@code> 0}。超时为零被解释为无限超时。
     * 
     * 
     * @param timeout the specified timeout, in milliseconds
     * @exception SocketException if there is an error in
     * the underlying protocol, such as a TCP error.
     * @since   JDK1.1
     * @see #getSoTimeout()
     */
    public synchronized void setSoTimeout(int timeout) throws SocketException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        getImpl().setOption(SocketOptions.SO_TIMEOUT, new Integer(timeout));
    }

    /**
     * Retrieve setting for {@link SocketOptions#SO_TIMEOUT SO_TIMEOUT}.
     * 0 returns implies that the option is disabled (i.e., timeout of infinity).
     * <p>
     *  检索{@link SocketOptions#SO_TIMEOUT SO_TIMEOUT}的设置。 0返回意味着该选项被禁用(即,无限超时)。
     * 
     * 
     * @return the {@link SocketOptions#SO_TIMEOUT SO_TIMEOUT} value
     * @exception IOException if an I/O error occurs
     * @since   JDK1.1
     * @see #setSoTimeout(int)
     */
    public synchronized int getSoTimeout() throws IOException {
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
     * Enabling {@link SocketOptions#SO_REUSEADDR SO_REUSEADDR} prior to
     * binding the socket using {@link #bind(SocketAddress)} allows the socket
     * to be bound even though a previous connection is in a timeout state.
     * <p>
     * When a {@code ServerSocket} is created the initial setting
     * of {@link SocketOptions#SO_REUSEADDR SO_REUSEADDR} is not defined.
     * Applications can use {@link #getReuseAddress()} to determine the initial
     * setting of {@link SocketOptions#SO_REUSEADDR SO_REUSEADDR}.
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
     * 当创建{@code ServerSocket}时,未定义{@link SocketOptions#SO_REUSEADDR SO_REUSEADDR}的初始设置。
     * 应用程序可以使用{@link #getReuseAddress()}确定{@link SocketOptions#SO_REUSEADDR SO_REUSEADDR}的初始设置。
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
     * @see #isBound()
     * @see #isClosed()
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
     * Returns the implementation address and implementation port of
     * this socket as a {@code String}.
     * <p>
     * If there is a security manager set, its {@code checkConnect} method is
     * called with the local address and {@code -1} as its arguments to see
     * if the operation is allowed. If the operation is not allowed,
     * an {@code InetAddress} representing the
     * {@link InetAddress#getLoopbackAddress loopback} address is returned as
     * the implementation address.
     *
     * <p>
     *  以{@code String}形式返回此套接字的实现地址和实现端口。
     * <p>
     *  如果存在安全管理器集,则会使用本地地址和{@code -1}作为其参数来调用其{@code checkConnect}方法,以查看是否允许该操作。
     * 如果不允许该操作,则返回表示{@link InetAddress#getLoopbackAddress loopback}地址的{@code InetAddress}作为实现地址。
     * 
     * 
     * @return  a string representation of this socket.
     */
    public String toString() {
        if (!isBound())
            return "ServerSocket[unbound]";
        InetAddress in;
        if (System.getSecurityManager() != null)
            in = InetAddress.getLoopbackAddress();
        else
            in = impl.getInetAddress();
        return "ServerSocket[addr=" + in +
                ",localport=" + impl.getLocalPort()  + "]";
    }

    void setBound() {
        bound = true;
    }

    void setCreated() {
        created = true;
    }

    /**
     * The factory for all server sockets.
     * <p>
     *  所有服务器套接字的工厂。
     * 
     */
    private static SocketImplFactory factory = null;

    /**
     * Sets the server socket implementation factory for the
     * application. The factory can be specified only once.
     * <p>
     * When an application creates a new server socket, the socket
     * implementation factory's {@code createSocketImpl} method is
     * called to create the actual socket implementation.
     * <p>
     * Passing {@code null} to the method is a no-op unless the factory
     * was already set.
     * <p>
     * If there is a security manager, this method first calls
     * the security manager's {@code checkSetFactory} method
     * to ensure the operation is allowed.
     * This could result in a SecurityException.
     *
     * <p>
     *  设置应用程序的服务器套接字实现工厂。工厂只能指定一次。
     * <p>
     *  当应用程序创建新的服务器套接字时,将调用套接字实现工厂的{@code createSocketImpl}方法来创建实际的套接字实现。
     * <p>
     *  将{@code null}传递给方法是一个无操作,除非工厂已经设置。
     * <p>
     * 如果有安全管理器,则此方法首先调用安全管理器的{@code checkSetFactory}方法,以确保允许操作。这可能导致SecurityException。
     * 
     * 
     * @param      fac   the desired factory.
     * @exception  IOException  if an I/O error occurs when setting the
     *               socket factory.
     * @exception  SocketException  if the factory has already been defined.
     * @exception  SecurityException  if a security manager exists and its
     *             {@code checkSetFactory} method doesn't allow the operation.
     * @see        java.net.SocketImplFactory#createSocketImpl()
     * @see        SecurityManager#checkSetFactory
     */
    public static synchronized void setSocketFactory(SocketImplFactory fac) throws IOException {
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
     * Sets a default proposed value for the
     * {@link SocketOptions#SO_RCVBUF SO_RCVBUF} option for sockets
     * accepted from this {@code ServerSocket}. The value actually set
     * in the accepted socket must be determined by calling
     * {@link Socket#getReceiveBufferSize()} after the socket
     * is returned by {@link #accept()}.
     * <p>
     * The value of {@link SocketOptions#SO_RCVBUF SO_RCVBUF} is used both to
     * set the size of the internal socket receive buffer, and to set the size
     * of the TCP receive window that is advertized to the remote peer.
     * <p>
     * It is possible to change the value subsequently, by calling
     * {@link Socket#setReceiveBufferSize(int)}. However, if the application
     * wishes to allow a receive window larger than 64K bytes, as defined by RFC1323
     * then the proposed value must be set in the ServerSocket <B>before</B>
     * it is bound to a local address. This implies, that the ServerSocket must be
     * created with the no-argument constructor, then setReceiveBufferSize() must
     * be called and lastly the ServerSocket is bound to an address by calling bind().
     * <p>
     * Failure to do this will not cause an error, and the buffer size may be set to the
     * requested value but the TCP receive window in sockets accepted from
     * this ServerSocket will be no larger than 64K bytes.
     *
     * <p>
     *  为从{@code ServerSocket}接受的套接字设置{@link SocketOptions#SO_RCVBUF SO_RCVBUF}选项的默认建议值。
     * 在套接字由{@link #accept()}返回之后,必须通过调用{@link Socket#getReceiveBufferSize()}来确定在接受的套接字中实际设置的值。
     * <p>
     *  {@link SocketOptions#SO_RCVBUF SO_RCVBUF}的值既用于设置内部套接字接收缓冲区的大小,也用于设置通告给远程对等体的TCP接收窗口的大小。
     * <p>
     *  可以通过调用{@link Socket#setReceiveBufferSize(int)}来随后更改该值。
     * 但是,如果应用程序希望允许大于64K字节的接收窗口(如RFC1323定义的那样),那么在绑定到本地地址之前,必须在ServerSocket <B>之前设置建议的值。
     * 这意味着ServerSocket必须使用无参数构造函数创建,然后必须调用setReceiveBufferSize(),最后通过调用bind()将ServerSocket绑定到地址。
     * <p>
     *  不这样做不会导致错误,并且缓冲区大小可以设置为请求的值,但从此ServerSocket接受的套接字中的TCP接收窗口将不大于64K字节。
     * 
     * 
     * @exception SocketException if there is an error
     * in the underlying protocol, such as a TCP error.
     *
     * @param size the size to which to set the receive buffer
     * size. This value must be greater than 0.
     *
     * @exception IllegalArgumentException if the
     * value is 0 or is negative.
     *
     * @since 1.4
     * @see #getReceiveBufferSize
     */
     public synchronized void setReceiveBufferSize (int size) throws SocketException {
        if (!(size > 0)) {
            throw new IllegalArgumentException("negative receive size");
        }
        if (isClosed())
            throw new SocketException("Socket is closed");
        getImpl().setOption(SocketOptions.SO_RCVBUF, new Integer(size));
    }

    /**
     * Gets the value of the {@link SocketOptions#SO_RCVBUF SO_RCVBUF} option
     * for this {@code ServerSocket}, that is the proposed buffer size that
     * will be used for Sockets accepted from this {@code ServerSocket}.
     *
     * <p>Note, the value actually set in the accepted socket is determined by
     * calling {@link Socket#getReceiveBufferSize()}.
     * <p>
     * 获取此{@code ServerSocket}的{@link SocketOptions#SO_RCVBUF SO_RCVBUF}选项的值,这是将用于从此{@code ServerSocket}接受的套
     * 接字的建议缓冲区大小。
     * 
     *  <p>注意,在接受的套接字中实际设置的值是通过调用{@link Socket#getReceiveBufferSize()}来确定的。
     * 
     * 
     * @return the value of the {@link SocketOptions#SO_RCVBUF SO_RCVBUF}
     *         option for this {@code Socket}.
     * @exception SocketException if there is an error
     *            in the underlying protocol, such as a TCP error.
     * @see #setReceiveBufferSize(int)
     * @since 1.4
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
     * Sets performance preferences for this ServerSocket.
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
     * compared, with larger values indicating stronger preferences.  If the
     * application prefers short connection time over both low latency and high
     * bandwidth, for example, then it could invoke this method with the values
     * {@code (1, 0, 0)}.  If the application prefers high bandwidth above low
     * latency, and low latency above short connection time, then it could
     * invoke this method with the values {@code (0, 1, 2)}.
     *
     * <p> Invoking this method after this socket has been bound
     * will have no effect. This implies that in order to use this capability
     * requires the socket to be created with the no-argument constructor.
     *
     * <p>
     *  设置此ServerSocket的性能首选项。
     * 
     *  <p>套接字默认使用TCP / IP协议。一些实现可以提供具有与TCP / IP不同的性能特征的替代协议。该方法允许应用表达其自己关于当实现从可用协议选择时如何进行这些折衷的偏好。
     * 
     *  <p>性能偏好由三个整数描述,其值指示短连接时间,低延迟和高带宽的相对重要性。整数的绝对值是不相关的;为了选择协议,简单地比较值,较大的值指示较强的偏好。
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
