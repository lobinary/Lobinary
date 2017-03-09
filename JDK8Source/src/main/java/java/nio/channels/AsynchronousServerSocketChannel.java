/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.nio.channels.spi.*;
import java.net.SocketOption;
import java.net.SocketAddress;
import java.util.concurrent.Future;
import java.io.IOException;

/**
 * An asynchronous channel for stream-oriented listening sockets.
 *
 * <p> An asynchronous server-socket channel is created by invoking the
 * {@link #open open} method of this class.
 * A newly-created asynchronous server-socket channel is open but not yet bound.
 * It can be bound to a local address and configured to listen for connections
 * by invoking the {@link #bind(SocketAddress,int) bind} method. Once bound,
 * the {@link #accept(Object,CompletionHandler) accept} method
 * is used to initiate the accepting of connections to the channel's socket.
 * An attempt to invoke the <tt>accept</tt> method on an unbound channel will
 * cause a {@link NotYetBoundException} to be thrown.
 *
 * <p> Channels of this type are safe for use by multiple concurrent threads
 * though at most one accept operation can be outstanding at any time.
 * If a thread initiates an accept operation before a previous accept operation
 * has completed then an {@link AcceptPendingException} will be thrown.
 *
 * <p> Socket options are configured using the {@link #setOption(SocketOption,Object)
 * setOption} method. Channels of this type support the following options:
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
 * <p> <b>Usage Example:</b>
 * <pre>
 *  final AsynchronousServerSocketChannel listener =
 *      AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(5000));
 *
 *  listener.accept(null, new CompletionHandler&lt;AsynchronousSocketChannel,Void&gt;() {
 *      public void completed(AsynchronousSocketChannel ch, Void att) {
 *          // accept the next connection
 *          listener.accept(null, this);
 *
 *          // handle this connection
 *          handle(ch);
 *      }
 *      public void failed(Throwable exc, Void att) {
 *          ...
 *      }
 *  });
 * </pre>
 *
 * <p>
 *  用于面向流的监听套接字的异步通道。
 * 
 *  <p>通过调用此类的{@link #open open}方法创建异步服务器套接字通道。新创建的异步服务器套接字通道已打开但尚未绑定。
 * 它可以绑定到本地地址并配置为通过调用{@link #bind(SocketAddress,int)bind}方法侦听连接。
 * 一旦绑定,{@link #accept(Object,CompletionHandler)accept}方法用于启动接受到通道套接字的连接。
 * 尝试在未绑定的频道上调用<tt> accept </tt>方法将导致抛出{@link NotYetBoundException}。
 * 
 *  <p>此类型的通道可安全地用于多个并发线程,但最多只有一个接受操作可以随时出现。
 * 如果线程在上一个接受操作完成之前启动一个接受操作,那么将抛出{@link AcceptPendingException}。
 * 
 *  <p>使用{@link #setOption(SocketOption,Object)setOption}方法配置套接字选项。此类型的通道支持以下选项：
 * <blockquote>
 * <table border summary="Socket options">
 * <tr>
 *  <th>选项名称</th> <th>说明</th>
 * </tr>
 * <tr>
 *  <td> {@link java.net.StandardSocketOptions#SO_RCVBUF SO_RCVBUF} </td> <td>套接字接收缓冲区的大小</td>
 * </tr>
 * <tr>
 * <td> {@link java.net.StandardSocketOptions#SO_REUSEADDR SO_REUSEADDR} </td> <td>重新使用地址</td>
 * </tr>
 * </table>
 * </blockquote>
 *  还可以支持附加(实现特定)选项。
 * 
 *  <p> <b>使用示例：</b>
 * <pre>
 *  final AsynchronousServerSocketChannel listener = AsynchronousServerSocketChannel.open()。
 * bind(new InetSocketAddress(5000));。
 * 
 *  listener.accept(null,new CompletionHandler&lt; AsynchronousSocketChannel,Void&gt;(){public void completed(AsynchronousSocketChannel ch,Void att){// accept the next connection listener.accept(null,this);。
 * 
 *  //处理这个连接句柄(ch); } public void failed(Throwable exc,Void att){...}});
 * </pre>
 * 
 * 
 * @since 1.7
 */

public abstract class AsynchronousServerSocketChannel
    implements AsynchronousChannel, NetworkChannel
{
    private final AsynchronousChannelProvider provider;

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
    protected AsynchronousServerSocketChannel(AsynchronousChannelProvider provider) {
        this.provider = provider;
    }

    /**
     * Returns the provider that created this channel.
     *
     * <p>
     *  返回创建此渠道的提供商。
     * 
     * 
     * @return  The provider that created this channel
     */
    public final AsynchronousChannelProvider provider() {
        return provider;
    }

    /**
     * Opens an asynchronous server-socket channel.
     *
     * <p> The new channel is created by invoking the {@link
     * java.nio.channels.spi.AsynchronousChannelProvider#openAsynchronousServerSocketChannel
     * openAsynchronousServerSocketChannel} method on the {@link
     * java.nio.channels.spi.AsynchronousChannelProvider} object that created
     * the given group. If the group parameter is <tt>null</tt> then the
     * resulting channel is created by the system-wide default provider, and
     * bound to the <em>default group</em>.
     *
     * <p>
     *  打开异步服务器套接字通道。
     * 
     *  <p>新频道是通过在创建给定组的{@link java.nio.channels.spi.AsynchronousChannelProvider}对象上调用{@link java.nio.channels.spi.AsynchronousChannelProvider#openAsynchronousServerSocketChannel openAsynchronousServerSocketChannel}
     * 方法创建的。
     * 如果group参数为<tt> null </tt>,则生成的频道由系统级默认提供商创建,并绑定到默认组</em>。
     * 
     * 
     * @param   group
     *          The group to which the newly constructed channel should be bound,
     *          or <tt>null</tt> for the default group
     *
     * @return  A new asynchronous server socket channel
     *
     * @throws  ShutdownChannelGroupException
     *          If the channel group is shutdown
     * @throws  IOException
     *          If an I/O error occurs
     */
    public static AsynchronousServerSocketChannel open(AsynchronousChannelGroup group)
        throws IOException
    {
        AsynchronousChannelProvider provider = (group == null) ?
            AsynchronousChannelProvider.provider() : group.provider();
        return provider.openAsynchronousServerSocketChannel(group);
    }

    /**
     * Opens an asynchronous server-socket channel.
     *
     * <p> This method returns an asynchronous server socket channel that is
     * bound to the <em>default group</em>. This method is equivalent to evaluating
     * the expression:
     * <blockquote><pre>
     * open((AsynchronousChannelGroup)null);
     * </pre></blockquote>
     *
     * <p>
     *  打开异步服务器套接字通道。
     * 
     *  <p>此方法返回绑定到<em>默认组</em>的异步服务器套接字通道。
     * 这个方法等同于计算表达式：<blockquote> <pre> open((AsynchronousChannelGroup)null); </pre> </blockquote>。
     * 
     * 
     * @return  A new asynchronous server socket channel
     *
     * @throws  IOException
     *          If an I/O error occurs
     */
    public static AsynchronousServerSocketChannel open()
        throws IOException
    {
        return open(null);
    }

    /**
     * Binds the channel's socket to a local address and configures the socket to
     * listen for connections.
     *
     * <p> An invocation of this method is equivalent to the following:
     * <blockquote><pre>
     * bind(local, 0);
     * </pre></blockquote>
     *
     * <p>
     * 将通道的套接字绑定到本地地址,并将套接字配置为侦听连接。
     * 
     *  <p>此方法的调用等效于以下内容：<blockquote> <pre> bind(local,0); </pre> </blockquote>
     * 
     * 
     * @param   local
     *          The local address to bind the socket, or <tt>null</tt> to bind
     *          to an automatically assigned socket address
     *
     * @return  This channel
     *
     * @throws  AlreadyBoundException               {@inheritDoc}
     * @throws  UnsupportedAddressTypeException     {@inheritDoc}
     * @throws  SecurityException                   {@inheritDoc}
     * @throws  ClosedChannelException              {@inheritDoc}
     * @throws  IOException                         {@inheritDoc}
     */
    public final AsynchronousServerSocketChannel bind(SocketAddress local)
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
     * bound until the associated channel is closed.
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
     *  <p>此方法用于在套接字和本地地址之间建立关联。一旦建立了关联,则套接字保持绑定,直到关联的频道关闭。
     * 
     *  <p> {@code backlog}参数是套接字上挂起连接的最大数量。它的确切语义是实现特定的。具体地,实现可以施加最大长度,或者可以选择忽略参数等。
     * 如果{@code backlog}参数的值为{@code 0}或负值,则使用特定于实现的默认值。
     * 
     * 
     * @param   local
     *          The local address to bind the socket, or {@code null} to bind
     *          to an automatically assigned socket address
     * @param   backlog
     *          The maximum number of pending connections
     *
     * @return  This channel
     *
     * @throws  AlreadyBoundException
     *          If the socket is already bound
     * @throws  UnsupportedAddressTypeException
     *          If the type of the given address is not supported
     * @throws  SecurityException
     *          If a security manager has been installed and its {@link
     *          SecurityManager#checkListen checkListen} method denies the operation
     * @throws  ClosedChannelException
     *          If the channel is closed
     * @throws  IOException
     *          If some other I/O error occurs
     */
    public abstract AsynchronousServerSocketChannel bind(SocketAddress local, int backlog)
        throws IOException;

    /**
    /* <p>
    /* 
     * @throws  IllegalArgumentException                {@inheritDoc}
     * @throws  ClosedChannelException                  {@inheritDoc}
     * @throws  IOException                             {@inheritDoc}
     */
    public abstract <T> AsynchronousServerSocketChannel setOption(SocketOption<T> name, T value)
        throws IOException;

    /**
     * Accepts a connection.
     *
     * <p> This method initiates an asynchronous operation to accept a
     * connection made to this channel's socket. The {@code handler} parameter is
     * a completion handler that is invoked when a connection is accepted (or
     * the operation fails). The result passed to the completion handler is
     * the {@link AsynchronousSocketChannel} to the new connection.
     *
     * <p> When a new connection is accepted then the resulting {@code
     * AsynchronousSocketChannel} will be bound to the same {@link
     * AsynchronousChannelGroup} as this channel. If the group is {@link
     * AsynchronousChannelGroup#isShutdown shutdown} and a connection is accepted,
     * then the connection is closed, and the operation completes with an {@code
     * IOException} and cause {@link ShutdownChannelGroupException}.
     *
     * <p> To allow for concurrent handling of new connections, the completion
     * handler is not invoked directly by the initiating thread when a new
     * connection is accepted immediately (see <a
     * href="AsynchronousChannelGroup.html#threading">Threading</a>).
     *
     * <p> If a security manager has been installed then it verifies that the
     * address and port number of the connection's remote endpoint are permitted
     * by the security manager's {@link SecurityManager#checkAccept checkAccept}
     * method. The permission check is performed with privileges that are restricted
     * by the calling context of this method. If the permission check fails then
     * the connection is closed and the operation completes with a {@link
     * SecurityException}.
     *
     * <p>
     *  接受连接。
     * 
     *  <p>此方法启动异步操作以接受对此通道的套接字所做的连接。 {@code handler}参数是在接受连接(或操作失败)时调用的完成处理程序。
     * 传递给完成处理程序的结果是到新连接的{@link AsynchronousSocketChannel}。
     * 
     * <p>当接受新连接时,生成的{@code AsynchronousSocketChannel}将绑定到与此通道相同的{@link AsynchronousChannelGroup}。
     * 如果组是{@link AsynchronousChannelGroup#isShutdown shutdown}并且连接被接受,则连接将关闭,操作将使用{@code IOException}完成并导致{@link ShutdownChannelGroupException}
     * 。
     * <p>当接受新连接时,生成的{@code AsynchronousSocketChannel}将绑定到与此通道相同的{@link AsynchronousChannelGroup}。
     * 
     *  <p>要允许并发处理新连接,当立即接受新连接时,启动线程不会直接调用完成处理程序(请参见<a href="AsynchronousChannelGroup.html#threading">线程</a>
     * ) 。
     * 
     *  <p>如果安装了安全管理器,则它会验证安全管理器的{@link SecurityManager#checkAccept checkAccept}方法是否允许连接的远程端点的地址和端口号。
     * 权限检查使用受此方法的调用上下文限制的权限执行。如果权限检查失败,则连接将关闭,操作将使用{@link SecurityException}完成。
     * 
     * 
     * @param   <A>
     *          The type of the attachment
     * @param   attachment
     *          The object to attach to the I/O operation; can be {@code null}
     * @param   handler
     *          The handler for consuming the result
     *
     * @throws  AcceptPendingException
     *          If an accept operation is already in progress on this channel
     * @throws  NotYetBoundException
     *          If this channel's socket has not yet been bound
     * @throws  ShutdownChannelGroupException
     *          If the channel group has terminated
     */
    public abstract <A> void accept(A attachment,
                                    CompletionHandler<AsynchronousSocketChannel,? super A> handler);

    /**
     * Accepts a connection.
     *
     * <p> This method initiates an asynchronous operation to accept a
     * connection made to this channel's socket. The method behaves in exactly
     * the same manner as the {@link #accept(Object, CompletionHandler)} method
     * except that instead of specifying a completion handler, this method
     * returns a {@code Future} representing the pending result. The {@code
     * Future}'s {@link Future#get() get} method returns the {@link
     * AsynchronousSocketChannel} to the new connection on successful completion.
     *
     * <p>
     * 
     * @return  a {@code Future} object representing the pending result
     *
     * @throws  AcceptPendingException
     *          If an accept operation is already in progress on this channel
     * @throws  NotYetBoundException
     *          If this channel's socket has not yet been bound
     */
    public abstract Future<AsynchronousSocketChannel> accept();

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
     *  接受连接。
     * 
     * <p>此方法启动异步操作以接受对此通道的套接字建立的连接。
     * 该方法的行为方式与{@link #accept(Object,CompletionHandler)}方法完全相同,只是不是指定完成处理程序,而是返回表示未决结果的{@code Future}。
     *  {@code Future}的{@link Future#get()get}方法会在成功完成时向新连接返回{@link AsynchronousSocketChannel}。
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
