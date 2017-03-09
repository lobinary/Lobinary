/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.nio.channels;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketOption;
import java.net.SocketAddress;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.nio.channels.spi.SelectorProvider;

/**
 * A selectable channel for stream-oriented listening sockets.
 *
 * <p> A server-socket channel is created by invoking the {@link #open() open}
 * method of this class.  It is not possible to create a channel for an arbitrary,
 * pre-existing {@link ServerSocket}. A newly-created server-socket channel is
 * open but not yet bound.  An attempt to invoke the {@link #accept() accept}
 * method of an unbound server-socket channel will cause a {@link NotYetBoundException}
 * to be thrown. A server-socket channel can be bound by invoking one of the
 * {@link #bind(java.net.SocketAddress,int) bind} methods defined by this class.
 *
 * <p> Socket options are configured using the {@link #setOption(SocketOption,Object)
 * setOption} method. Server-socket channels support the following options:
 * <blockquote>
 * <table border summary="Socket options">
 *   <tr>
 *     <th>Option Name</th>
 *     <th>Description</th>
 *   </tr>
 *   <tr>
 *     <td> {@link java.net.StandardSocketOptions#SO_RCVBUF SO_RCVBUF} </td>
 *     <td> The size of the socket receive buffer </td>
 *   </tr>
 *   <tr>
 *     <td> {@link java.net.StandardSocketOptions#SO_REUSEADDR SO_REUSEADDR} </td>
 *     <td> Re-use address </td>
 *   </tr>
 * </table>
 * </blockquote>
 * Additional (implementation specific) options may also be supported.
 *
 * <p> Server-socket channels are safe for use by multiple concurrent threads.
 * </p>
 *
 * <p>
 *  面向流的监听套接字的可选通道。
 * 
 *  <p>通过调用此类的{@link #open()open}方法创建服务器套接字通道。不可能为任意的,预先存在的{@link ServerSocket}创建一个通道。
 * 新创建的服务器套接字通道已打开但尚未绑定。尝试调用未绑定的服务器套接字通道的{@link #accept()accept}方法将导致抛出{@link NotYetBoundException}。
 * 可以通过调用由此类定义的{@link #bind(java.net.SocketAddress,int)bind}方法之一来绑定服务器套接字通道。
 * 
 *  <p>使用{@link #setOption(SocketOption,Object)setOption}方法配置套接字选项。服务器插槽通道支持以下选项：
 * <blockquote>
 * <table border summary="Socket options">
 * <tr>
 *  <th>选项名称</th> <th>说明</th>
 * </tr>
 * <tr>
 *  <td> {@link java.net.StandardSocketOptions#SO_RCVBUF SO_RCVBUF} </td> <td>套接字接收缓冲区的大小</td>
 * </tr>
 * <tr>
 *  <td> {@link java.net.StandardSocketOptions#SO_REUSEADDR SO_REUSEADDR} </td> <td>重新使用地址</td>
 * </tr>
 * </table>
 * </blockquote>
 *  还可以支持附加(实现特定)选项。
 * 
 *  <p>服务器插槽通道可安全地用于多个并发线程。
 * </p>
 * 
 * 
 * @author Mark Reinhold
 * @author JSR-51 Expert Group
 * @since 1.4
 */

public abstract class ServerSocketChannel
    extends AbstractSelectableChannel
    implements NetworkChannel
{

    /**
     * Initializes a new instance of this class.
     *
     * <p>
     *  初始化此类的新实例。
     * 
     * 
     * @param  provider
     *         The provider that created this channel
     */
    protected ServerSocketChannel(SelectorProvider provider) {
        super(provider);
    }

    /**
     * Opens a server-socket channel.
     *
     * <p> The new channel is created by invoking the {@link
     * java.nio.channels.spi.SelectorProvider#openServerSocketChannel
     * openServerSocketChannel} method of the system-wide default {@link
     * java.nio.channels.spi.SelectorProvider} object.
     *
     * <p> The new channel's socket is initially unbound; it must be bound to a
     * specific address via one of its socket's {@link
     * java.net.ServerSocket#bind(SocketAddress) bind} methods before
     * connections can be accepted.  </p>
     *
     * <p>
     *  打开服务器套接字通道。
     * 
     * <p>新频道是通过调用系统级默认{@link java.nio.channels.spi.SelectorProvider}对象的{@link java.nio.channels.spi.SelectorProvider#openServerSocketChannel openServerSocketChannel}
     * 方法创建的。
     * 
     *  <p>新频道的套接字最初未绑定;它必须通过其套接字的{@link java.net.ServerSocket#bind(SocketAddress)bind}方法绑定到特定地址,然后才能接受连接。
     *  </p>。
     * 
     * 
     * @return  A new socket channel
     *
     * @throws  IOException
     *          If an I/O error occurs
     */
    public static ServerSocketChannel open() throws IOException {
        return SelectorProvider.provider().openServerSocketChannel();
    }

    /**
     * Returns an operation set identifying this channel's supported
     * operations.
     *
     * <p> Server-socket channels only support the accepting of new
     * connections, so this method returns {@link SelectionKey#OP_ACCEPT}.
     * </p>
     *
     * <p>
     *  返回标识此通道支持的操作的操作集。
     * 
     *  <p>服务器套接字通道仅支持接受新连接,因此此方法返回{@link SelectionKey#OP_ACCEPT}。
     * </p>
     * 
     * 
     * @return  The valid-operation set
     */
    public final int validOps() {
        return SelectionKey.OP_ACCEPT;
    }


    // -- ServerSocket-specific operations --

    /**
     * Binds the channel's socket to a local address and configures the socket
     * to listen for connections.
     *
     * <p> An invocation of this method is equivalent to the following:
     * <blockquote><pre>
     * bind(local, 0);
     * </pre></blockquote>
     *
     * <p>
     *  将通道的套接字绑定到本地地址,并将套接字配置为侦听连接。
     * 
     *  <p>此方法的调用等效于以下内容：<blockquote> <pre> bind(local,0); </pre> </blockquote>
     * 
     * 
     * @param   local
     *          The local address to bind the socket, or {@code null} to bind
     *          to an automatically assigned socket address
     *
     * @return  This channel
     *
     * @throws  AlreadyBoundException               {@inheritDoc}
     * @throws  UnsupportedAddressTypeException     {@inheritDoc}
     * @throws  ClosedChannelException              {@inheritDoc}
     * @throws  IOException                         {@inheritDoc}
     * @throws  SecurityException
     *          If a security manager has been installed and its {@link
     *          SecurityManager#checkListen checkListen} method denies the
     *          operation
     *
     * @since 1.7
     */
    public final ServerSocketChannel bind(SocketAddress local)
        throws IOException
    {
        return bind(local, 0);
    }

    /**
     * Binds the channel's socket to a local address and configures the socket to
     * listen for connections.
     *
     * <p> This method is used to establish an association between the socket and
     * a local address. Once an association is established then the socket remains
     * bound until the channel is closed.
     *
     * <p> The {@code backlog} parameter is the maximum number of pending
     * connections on the socket. Its exact semantics are implementation specific.
     * In particular, an implementation may impose a maximum length or may choose
     * to ignore the parameter altogther. If the {@code backlog} parameter has
     * the value {@code 0}, or a negative value, then an implementation specific
     * default is used.
     *
     * <p>
     *  将通道的套接字绑定到本地地址,并将套接字配置为侦听连接。
     * 
     *  <p>此方法用于在套接字和本地地址之间建立关联。一旦建立了关联,则套接字保持绑定,直到信道被关闭。
     * 
     * <p> {@code backlog}参数是套接字上挂起连接的最大数量。它的确切语义是实现特定的。具体地,实现可以施加最大长度,或者可以选择忽略参数等。
     * 如果{@code backlog}参数的值为{@code 0}或负值,则使用特定于实现的默认值。
     * 
     * 
     * @param   local
     *          The address to bind the socket, or {@code null} to bind to an
     *          automatically assigned socket address
     * @param   backlog
     *          The maximum number of pending connections
     *
     * @return  This channel
     *
     * @throws  AlreadyBoundException
     *          If the socket is already bound
     * @throws  UnsupportedAddressTypeException
     *          If the type of the given address is not supported
     * @throws  ClosedChannelException
     *          If this channel is closed
     * @throws  IOException
     *          If some other I/O error occurs
     * @throws  SecurityException
     *          If a security manager has been installed and its {@link
     *          SecurityManager#checkListen checkListen} method denies the
     *          operation
     *
     * @since 1.7
     */
    public abstract ServerSocketChannel bind(SocketAddress local, int backlog)
        throws IOException;

    /**
    /* <p>
    /* 
     * @throws  UnsupportedOperationException           {@inheritDoc}
     * @throws  IllegalArgumentException                {@inheritDoc}
     * @throws  ClosedChannelException                  {@inheritDoc}
     * @throws  IOException                             {@inheritDoc}
     *
     * @since 1.7
     */
    public abstract <T> ServerSocketChannel setOption(SocketOption<T> name, T value)
        throws IOException;

    /**
     * Retrieves a server socket associated with this channel.
     *
     * <p> The returned object will not declare any public methods that are not
     * declared in the {@link java.net.ServerSocket} class.  </p>
     *
     * <p>
     *  检索与此通道关联的服务器套接字。
     * 
     *  <p>返回的对象不会声明任何未在{@link java.net.ServerSocket}类中声明的公共方法。 </p>
     * 
     * 
     * @return  A server socket associated with this channel
     */
    public abstract ServerSocket socket();

    /**
     * Accepts a connection made to this channel's socket.
     *
     * <p> If this channel is in non-blocking mode then this method will
     * immediately return <tt>null</tt> if there are no pending connections.
     * Otherwise it will block indefinitely until a new connection is available
     * or an I/O error occurs.
     *
     * <p> The socket channel returned by this method, if any, will be in
     * blocking mode regardless of the blocking mode of this channel.
     *
     * <p> This method performs exactly the same security checks as the {@link
     * java.net.ServerSocket#accept accept} method of the {@link
     * java.net.ServerSocket} class.  That is, if a security manager has been
     * installed then for each new connection this method verifies that the
     * address and port number of the connection's remote endpoint are
     * permitted by the security manager's {@link
     * java.lang.SecurityManager#checkAccept checkAccept} method.  </p>
     *
     * <p>
     *  接受与此通道的套接字建立的连接。
     * 
     *  <p>如果此通道处于非阻塞模式,则如果没有未决连接,则此方法将立即返回<tt> null </tt>。否则它将无限期阻塞,直到有新的连接可用或发生I / O错误。
     * 
     *  <p>此方法返回的套接字通道(如果有)将处于阻塞模式,无论此通道的阻止模式如何。
     * 
     *  <p>此方法执行与{@link java.net.ServerSocket}类的{@link java.net.ServerSocket#accept accept}方法完全相同的安全检查。
     * 也就是说,如果已经安装了安全管理器,那么对于每个新连接,此方法将验证安全管理器的{@link java.lang.SecurityManager#checkAccept checkAccept}方法是否
     * 允许连接的远程端点的地址和端口号。
     *  <p>此方法执行与{@link java.net.ServerSocket}类的{@link java.net.ServerSocket#accept accept}方法完全相同的安全检查。
     * 
     * @return  The socket channel for the new connection,
     *          or <tt>null</tt> if this channel is in non-blocking mode
     *          and no connection is available to be accepted
     *
     * @throws  ClosedChannelException
     *          If this channel is closed
     *
     * @throws  AsynchronousCloseException
     *          If another thread closes this channel
     *          while the accept operation is in progress
     *
     * @throws  ClosedByInterruptException
     *          If another thread interrupts the current thread
     *          while the accept operation is in progress, thereby
     *          closing the channel and setting the current thread's
     *          interrupt status
     *
     * @throws  NotYetBoundException
     *          If this channel's socket has not yet been bound
     *
     * @throws  SecurityException
     *          If a security manager has been installed
     *          and it does not permit access to the remote endpoint
     *          of the new connection
     *
     * @throws  IOException
     *          If some other I/O error occurs
     */
    public abstract SocketChannel accept() throws IOException;

    /**
     * {@inheritDoc}
     * <p>
     * If there is a security manager set, its {@code checkConnect} method is
     * called with the local address and {@code -1} as its arguments to see
     * if the operation is allowed. If the operation is not allowed,
     * a {@code SocketAddress} representing the
     * {@link java.net.InetAddress#getLoopbackAddress loopback} address and the
     * local port of the channel's socket is returned.
     *
     * <p>
     *  </p>。
     * 
     * 
     * @return  The {@code SocketAddress} that the socket is bound to, or the
     *          {@code SocketAddress} representing the loopback address if
     *          denied by the security manager, or {@code null} if the
     *          channel's socket is not bound
     *
     * @throws  ClosedChannelException     {@inheritDoc}
     * @throws  IOException                {@inheritDoc}
     */
    @Override
    public abstract SocketAddress getLocalAddress() throws IOException;

}
