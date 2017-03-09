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
import java.net.Socket;
import java.net.SocketOption;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.nio.channels.spi.SelectorProvider;

/**
 * A selectable channel for stream-oriented connecting sockets.
 *
 * <p> A socket channel is created by invoking one of the {@link #open open}
 * methods of this class.  It is not possible to create a channel for an arbitrary,
 * pre-existing socket. A newly-created socket channel is open but not yet
 * connected.  An attempt to invoke an I/O operation upon an unconnected
 * channel will cause a {@link NotYetConnectedException} to be thrown.  A
 * socket channel can be connected by invoking its {@link #connect connect}
 * method; once connected, a socket channel remains connected until it is
 * closed.  Whether or not a socket channel is connected may be determined by
 * invoking its {@link #isConnected isConnected} method.
 *
 * <p> Socket channels support <i>non-blocking connection:</i>&nbsp;A socket
 * channel may be created and the process of establishing the link to the
 * remote socket may be initiated via the {@link #connect connect} method for
 * later completion by the {@link #finishConnect finishConnect} method.
 * Whether or not a connection operation is in progress may be determined by
 * invoking the {@link #isConnectionPending isConnectionPending} method.
 *
 * <p> Socket channels support <i>asynchronous shutdown,</i> which is similar
 * to the asynchronous close operation specified in the {@link Channel} class.
 * If the input side of a socket is shut down by one thread while another
 * thread is blocked in a read operation on the socket's channel, then the read
 * operation in the blocked thread will complete without reading any bytes and
 * will return <tt>-1</tt>.  If the output side of a socket is shut down by one
 * thread while another thread is blocked in a write operation on the socket's
 * channel, then the blocked thread will receive an {@link
 * AsynchronousCloseException}.
 *
 * <p> Socket options are configured using the {@link #setOption(SocketOption,Object)
 * setOption} method. Socket channels support the following options:
 * <blockquote>
 * <table border summary="Socket options">
 *   <tr>
 *     <th>Option Name</th>
 *     <th>Description</th>
 *   </tr>
 *   <tr>
 *     <td> {@link java.net.StandardSocketOptions#SO_SNDBUF SO_SNDBUF} </td>
 *     <td> The size of the socket send buffer </td>
 *   </tr>
 *   <tr>
 *     <td> {@link java.net.StandardSocketOptions#SO_RCVBUF SO_RCVBUF} </td>
 *     <td> The size of the socket receive buffer </td>
 *   </tr>
 *   <tr>
 *     <td> {@link java.net.StandardSocketOptions#SO_KEEPALIVE SO_KEEPALIVE} </td>
 *     <td> Keep connection alive </td>
 *   </tr>
 *   <tr>
 *     <td> {@link java.net.StandardSocketOptions#SO_REUSEADDR SO_REUSEADDR} </td>
 *     <td> Re-use address </td>
 *   </tr>
 *   <tr>
 *     <td> {@link java.net.StandardSocketOptions#SO_LINGER SO_LINGER} </td>
 *     <td> Linger on close if data is present (when configured in blocking mode
 *          only) </td>
 *   </tr>
 *   <tr>
 *     <td> {@link java.net.StandardSocketOptions#TCP_NODELAY TCP_NODELAY} </td>
 *     <td> Disable the Nagle algorithm </td>
 *   </tr>
 * </table>
 * </blockquote>
 * Additional (implementation specific) options may also be supported.
 *
 * <p> Socket channels are safe for use by multiple concurrent threads.  They
 * support concurrent reading and writing, though at most one thread may be
 * reading and at most one thread may be writing at any given time.  The {@link
 * #connect connect} and {@link #finishConnect finishConnect} methods are
 * mutually synchronized against each other, and an attempt to initiate a read
 * or write operation while an invocation of one of these methods is in
 * progress will block until that invocation is complete.  </p>
 *
 * <p>
 *  用于面向流的连接插座的可选通道。
 * 
 *  <p>套接字通道是通过调用此类的{@link #open open}方法之一创建的。不能为任意预先存在的套接字创建通道。新创建的套接字通道已打开但尚未连接。
 * 尝试在未连接的通道上调用I / O操作将导致抛出{@link NotYetConnectedException}。
 * 套接字通道可以通过调用其{@link #connect connect}方法来连接;一旦连接,插座通道保持连接直到其关闭。
 * 是否连接套接字通道可以通过调用其{@link #isConnected isConnected}方法来确定。
 * 
 *  <p>套接字通道支持<i>非阻塞连接：</i>&nbsp;可以创建套接字通道,并且可以通过{@link #connect connect}方法启动建立到远程套接字的链接的过程稍后由{@link #finishConnect finishConnect}
 * 方法完成。
 * 是否正在进行连接操作可以通过调用{@link #isConnectionPending isConnectionPending}方法来确定。
 * 
 * <p>套接字通道支持<i>异步关闭</i>,这与{@link Channel}类中指定的异步关闭操作类似。
 * 如果套接字的输入端被一个线程关闭,而另一个线程在套接字通道上的读操作中被阻塞,那么阻塞线程中的读操作将在不读取任何字节的情况下完成,并将返回<tt> -1 < / tt>。
 * 如果套接字的输出端被一个线程关闭,而另一个线程在套接字的通道上的写操作中被阻塞,那么被阻塞的线程将接收{@link AsynchronousCloseException}。
 * 
 *  <p>使用{@link #setOption(SocketOption,Object)setOption}方法配置套接字选项。套接字通道支持以下选项：
 * <blockquote>
 * <table border summary="Socket options">
 * <tr>
 *  <th>选项名称</th> <th>说明</th>
 * </tr>
 * <tr>
 *  <td> {@link java.net.StandardSocketOptions#SO_SNDBUF SO_SNDBUF} </td> <td>套接字发送缓冲区的大小</td>
 * </tr>
 * <tr>
 *  <td> {@link java.net.StandardSocketOptions#SO_RCVBUF SO_RCVBUF} </td> <td>套接字接收缓冲区的大小</td>
 * </tr>
 * <tr>
 *  <td> {@link java.net.StandardSocketOptions#SO_KEEPALIVE SO_KEEPALIVE} </td> <td>保持连接活动</td>
 * </tr>
 * <tr>
 *  <td> {@link java.net.StandardSocketOptions#SO_REUSEADDR SO_REUSEADDR} </td> <td>重新使用地址</td>
 * </tr>
 * <tr>
 *  <td> {@link java.net.StandardSocketOptions#SO_LINGER SO_LINGER} </td> <td>如果数据存在,则关闭(仅在阻止模式下配置)</td>
 * 。
 * </tr>
 * <tr>
 * <td> {@link java.net.StandardSocketOptions#TCP_NODELAY TCP_NODELAY} </td> <td>禁用Nagle算法</td>
 * </tr>
 * </table>
 * </blockquote>
 *  还可以支持附加(实现特定)选项。
 * 
 *  <p>套接字通道可安全地用于多个并发线程。它们支持并发读取和写入,但是最多一个线程可能正在读取,并且最多一个线程可能在任何给定时间写入。
 *  {@link #connect connect}和{@link #finishConnect finishConnect}方法彼此相互同步,并且在调用这些方法之一时尝试启动读取或写入操作将阻塞,直到该
 * 调用做完了。
 *  <p>套接字通道可安全地用于多个并发线程。它们支持并发读取和写入,但是最多一个线程可能正在读取,并且最多一个线程可能在任何给定时间写入。 </p>。
 * 
 * 
 * @author Mark Reinhold
 * @author JSR-51 Expert Group
 * @since 1.4
 */

public abstract class SocketChannel
    extends AbstractSelectableChannel
    implements ByteChannel, ScatteringByteChannel, GatheringByteChannel, NetworkChannel
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
    protected SocketChannel(SelectorProvider provider) {
        super(provider);
    }

    /**
     * Opens a socket channel.
     *
     * <p> The new channel is created by invoking the {@link
     * java.nio.channels.spi.SelectorProvider#openSocketChannel
     * openSocketChannel} method of the system-wide default {@link
     * java.nio.channels.spi.SelectorProvider} object.  </p>
     *
     * <p>
     *  打开套接字通道。
     * 
     *  <p>新频道是通过调用系统级默认{@link java.nio.channels.spi.SelectorProvider}对象的{@link java.nio.channels.spi.SelectorProvider#openSocketChannel openSocketChannel}
     * 方法创建的。
     *  </p>。
     * 
     * 
     * @return  A new socket channel
     *
     * @throws  IOException
     *          If an I/O error occurs
     */
    public static SocketChannel open() throws IOException {
        return SelectorProvider.provider().openSocketChannel();
    }

    /**
     * Opens a socket channel and connects it to a remote address.
     *
     * <p> This convenience method works as if by invoking the {@link #open()}
     * method, invoking the {@link #connect(SocketAddress) connect} method upon
     * the resulting socket channel, passing it <tt>remote</tt>, and then
     * returning that channel.  </p>
     *
     * <p>
     *  打开套接字通道并将其连接到远程地址。
     * 
     *  <p>这种方便的方法好像是通过调用{@link #open()}方法,在产生的套接字通道上调用{@link #connect(SocketAddress)connect}方法,传递<tt> remot
     * e </tt >,然后返回该通道。
     *  </p>。
     * 
     * 
     * @param  remote
     *         The remote address to which the new channel is to be connected
     *
     * @return  A new, and connected, socket channel
     *
     * @throws  AsynchronousCloseException
     *          If another thread closes this channel
     *          while the connect operation is in progress
     *
     * @throws  ClosedByInterruptException
     *          If another thread interrupts the current thread
     *          while the connect operation is in progress, thereby
     *          closing the channel and setting the current thread's
     *          interrupt status
     *
     * @throws  UnresolvedAddressException
     *          If the given remote address is not fully resolved
     *
     * @throws  UnsupportedAddressTypeException
     *          If the type of the given remote address is not supported
     *
     * @throws  SecurityException
     *          If a security manager has been installed
     *          and it does not permit access to the given remote endpoint
     *
     * @throws  IOException
     *          If some other I/O error occurs
     */
    public static SocketChannel open(SocketAddress remote)
        throws IOException
    {
        SocketChannel sc = open();
        try {
            sc.connect(remote);
        } catch (Throwable x) {
            try {
                sc.close();
            } catch (Throwable suppressed) {
                x.addSuppressed(suppressed);
            }
            throw x;
        }
        assert sc.isConnected();
        return sc;
    }

    /**
     * Returns an operation set identifying this channel's supported
     * operations.
     *
     * <p> Socket channels support connecting, reading, and writing, so this
     * method returns <tt>(</tt>{@link SelectionKey#OP_CONNECT}
     * <tt>|</tt>&nbsp;{@link SelectionKey#OP_READ} <tt>|</tt>&nbsp;{@link
     * SelectionKey#OP_WRITE}<tt>)</tt>.  </p>
     *
     * <p>
     *  返回标识此通道支持的操作的操作集。
     * 
     * <p>套接字频道支持连接,读取和写入,因此此方法返回<tt>(</tt> {@ link SelectionKey#OP_CONNECT} <tt> | </tt>&nbsp; {@ link SelectionKey#OP_READ}
     *  tt> | </tt>&nbsp; {@ link SelectionKey#OP_WRITE} <tt>)</tt>。
     *  </p>。
     * 
     * 
     * @return  The valid-operation set
     */
    public final int validOps() {
        return (SelectionKey.OP_READ
                | SelectionKey.OP_WRITE
                | SelectionKey.OP_CONNECT);
    }


    // -- Socket-specific operations --

    /**
    /* <p>
    /* 
     * @throws  ConnectionPendingException
     *          If a non-blocking connect operation is already in progress on
     *          this channel
     * @throws  AlreadyBoundException               {@inheritDoc}
     * @throws  UnsupportedAddressTypeException     {@inheritDoc}
     * @throws  ClosedChannelException              {@inheritDoc}
     * @throws  IOException                         {@inheritDoc}
     * @throws  SecurityException
     *          If a security manager has been installed and its
     *          {@link SecurityManager#checkListen checkListen} method denies
     *          the operation
     *
     * @since 1.7
     */
    @Override
    public abstract SocketChannel bind(SocketAddress local)
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
    @Override
    public abstract <T> SocketChannel setOption(SocketOption<T> name, T value)
        throws IOException;

    /**
     * Shutdown the connection for reading without closing the channel.
     *
     * <p> Once shutdown for reading then further reads on the channel will
     * return {@code -1}, the end-of-stream indication. If the input side of the
     * connection is already shutdown then invoking this method has no effect.
     *
     * <p>
     *  关闭连接以进行读取,而不关闭通道。
     * 
     *  <p>一旦关闭读取,则通道上的进一步读取将返回{@code -1},流末端指示。如果连接的输入端已经关闭,则调用此方法不起作用。
     * 
     * 
     * @return  The channel
     *
     * @throws  NotYetConnectedException
     *          If this channel is not yet connected
     * @throws  ClosedChannelException
     *          If this channel is closed
     * @throws  IOException
     *          If some other I/O error occurs
     *
     * @since 1.7
     */
    public abstract SocketChannel shutdownInput() throws IOException;

    /**
     * Shutdown the connection for writing without closing the channel.
     *
     * <p> Once shutdown for writing then further attempts to write to the
     * channel will throw {@link ClosedChannelException}. If the output side of
     * the connection is already shutdown then invoking this method has no
     * effect.
     *
     * <p>
     *  关闭用于写入的连接,而不关闭通道。
     * 
     *  <p>一旦关闭写入,则进一步尝试写入通道将抛出{@link ClosedChannelException}。如果连接的输出端已经关闭,那么调用此方法没有效果。
     * 
     * 
     * @return  The channel
     *
     * @throws  NotYetConnectedException
     *          If this channel is not yet connected
     * @throws  ClosedChannelException
     *          If this channel is closed
     * @throws  IOException
     *          If some other I/O error occurs
     *
     * @since 1.7
     */
    public abstract SocketChannel shutdownOutput() throws IOException;

    /**
     * Retrieves a socket associated with this channel.
     *
     * <p> The returned object will not declare any public methods that are not
     * declared in the {@link java.net.Socket} class.  </p>
     *
     * <p>
     *  检索与此通道关联的套接字。
     * 
     *  <p>返回的对象不会声明任何未在{@link java.net.Socket}类中声明的公共方法。 </p>
     * 
     * 
     * @return  A socket associated with this channel
     */
    public abstract Socket socket();

    /**
     * Tells whether or not this channel's network socket is connected.
     *
     * <p>
     *  告诉这个通道的网络插座是否连接。
     * 
     * 
     * @return  <tt>true</tt> if, and only if, this channel's network socket
     *          is {@link #isOpen open} and connected
     */
    public abstract boolean isConnected();

    /**
     * Tells whether or not a connection operation is in progress on this
     * channel.
     *
     * <p>
     *  告知此通道上的连接操作是否正在进行。
     * 
     * 
     * @return  <tt>true</tt> if, and only if, a connection operation has been
     *          initiated on this channel but not yet completed by invoking the
     *          {@link #finishConnect finishConnect} method
     */
    public abstract boolean isConnectionPending();

    /**
     * Connects this channel's socket.
     *
     * <p> If this channel is in non-blocking mode then an invocation of this
     * method initiates a non-blocking connection operation.  If the connection
     * is established immediately, as can happen with a local connection, then
     * this method returns <tt>true</tt>.  Otherwise this method returns
     * <tt>false</tt> and the connection operation must later be completed by
     * invoking the {@link #finishConnect finishConnect} method.
     *
     * <p> If this channel is in blocking mode then an invocation of this
     * method will block until the connection is established or an I/O error
     * occurs.
     *
     * <p> This method performs exactly the same security checks as the {@link
     * java.net.Socket} class.  That is, if a security manager has been
     * installed then this method verifies that its {@link
     * java.lang.SecurityManager#checkConnect checkConnect} method permits
     * connecting to the address and port number of the given remote endpoint.
     *
     * <p> This method may be invoked at any time.  If a read or write
     * operation upon this channel is invoked while an invocation of this
     * method is in progress then that operation will first block until this
     * invocation is complete.  If a connection attempt is initiated but fails,
     * that is, if an invocation of this method throws a checked exception,
     * then the channel will be closed.  </p>
     *
     * <p>
     *  连接此通道的插座。
     * 
     * <p>如果此通道处于非阻塞模式,则调用此方法将启动非阻塞连接操作。如果立即建立连接,就像使用本地连接一样,则此方法返回<tt> true </tt>。
     * 否则,此方法返回<tt> false </tt>,并且连接操作必须稍后通过调用{@link #finishConnect finishConnect}方法完成。
     * 
     *  <p>如果此通道处于阻塞模式,则调用此方法将阻塞,直到建立连接或发生I / O错误。
     * 
     *  <p>此方法执行与{@link java.net.Socket}类完全相同的安全检查。
     * 也就是说,如果安装了安全管理器,则此方法将验证其{@link java.lang.SecurityManager#checkConnect checkConnect}方法是否允许连接到给定远程端点的地址
     * 和端口号。
     *  <p>此方法执行与{@link java.net.Socket}类完全相同的安全检查。
     * 
     *  <p>此方法可能随时被调用。如果在调用此方法的同时调用此通道上的读取或写入操作,则该操作将首先阻塞,直到此调用完成。
     * 如果连接尝试启动但失败,也就是说,如果调用此方法抛出一个已检查的异常,则通道将关闭。 </p>。
     * 
     * 
     * @param  remote
     *         The remote address to which this channel is to be connected
     *
     * @return  <tt>true</tt> if a connection was established,
     *          <tt>false</tt> if this channel is in non-blocking mode
     *          and the connection operation is in progress
     *
     * @throws  AlreadyConnectedException
     *          If this channel is already connected
     *
     * @throws  ConnectionPendingException
     *          If a non-blocking connection operation is already in progress
     *          on this channel
     *
     * @throws  ClosedChannelException
     *          If this channel is closed
     *
     * @throws  AsynchronousCloseException
     *          If another thread closes this channel
     *          while the connect operation is in progress
     *
     * @throws  ClosedByInterruptException
     *          If another thread interrupts the current thread
     *          while the connect operation is in progress, thereby
     *          closing the channel and setting the current thread's
     *          interrupt status
     *
     * @throws  UnresolvedAddressException
     *          If the given remote address is not fully resolved
     *
     * @throws  UnsupportedAddressTypeException
     *          If the type of the given remote address is not supported
     *
     * @throws  SecurityException
     *          If a security manager has been installed
     *          and it does not permit access to the given remote endpoint
     *
     * @throws  IOException
     *          If some other I/O error occurs
     */
    public abstract boolean connect(SocketAddress remote) throws IOException;

    /**
     * Finishes the process of connecting a socket channel.
     *
     * <p> A non-blocking connection operation is initiated by placing a socket
     * channel in non-blocking mode and then invoking its {@link #connect
     * connect} method.  Once the connection is established, or the attempt has
     * failed, the socket channel will become connectable and this method may
     * be invoked to complete the connection sequence.  If the connection
     * operation failed then invoking this method will cause an appropriate
     * {@link java.io.IOException} to be thrown.
     *
     * <p> If this channel is already connected then this method will not block
     * and will immediately return <tt>true</tt>.  If this channel is in
     * non-blocking mode then this method will return <tt>false</tt> if the
     * connection process is not yet complete.  If this channel is in blocking
     * mode then this method will block until the connection either completes
     * or fails, and will always either return <tt>true</tt> or throw a checked
     * exception describing the failure.
     *
     * <p> This method may be invoked at any time.  If a read or write
     * operation upon this channel is invoked while an invocation of this
     * method is in progress then that operation will first block until this
     * invocation is complete.  If a connection attempt fails, that is, if an
     * invocation of this method throws a checked exception, then the channel
     * will be closed.  </p>
     *
     * <p>
     *  完成连接套接字通道的过程。
     * 
     * <p>通过将套接字通道置于非阻塞模式,然后调用其{@link #connect connect}方法,启动非阻塞连接操作。
     * 一旦建立连接或者尝试失败,套接字通道将变为可连接的,并且可以调用该方法来完成连接序列。如果连接操作失败,则调用此方法将导致适当的{@link java.io.IOException}被抛出。
     * 
     *  <p>如果此频道已连接,则此方法不会阻止,并会立即返回<tt> true </tt>。如果此通道处于非阻塞模式,则如果连接进程尚未完成,则此方法将返回<tt> false </tt>。
     * 如果此通道处于阻塞模式,则此方法将阻塞,直到连接完成或失败,并且将始终返回<tt> true </tt>或抛出一个描述失败的已检查异常。
     * 
     *  <p>此方法可能随时被调用。如果在调用此方法的同时调用此通道上的读取或写入操作,则该操作将首先阻塞,直到此调用完成。如果连接尝试失败,也就是说,如果此方法的调用引发检查异常,则通道将关闭。
     *  </p>。
     * 
     * 
     * @return  <tt>true</tt> if, and only if, this channel's socket is now
     *          connected
     *
     * @throws  NoConnectionPendingException
     *          If this channel is not connected and a connection operation
     *          has not been initiated
     *
     * @throws  ClosedChannelException
     *          If this channel is closed
     *
     * @throws  AsynchronousCloseException
     *          If another thread closes this channel
     *          while the connect operation is in progress
     *
     * @throws  ClosedByInterruptException
     *          If another thread interrupts the current thread
     *          while the connect operation is in progress, thereby
     *          closing the channel and setting the current thread's
     *          interrupt status
     *
     * @throws  IOException
     *          If some other I/O error occurs
     */
    public abstract boolean finishConnect() throws IOException;

    /**
     * Returns the remote address to which this channel's socket is connected.
     *
     * <p> Where the channel is bound and connected to an Internet Protocol
     * socket address then the return value from this method is of type {@link
     * java.net.InetSocketAddress}.
     *
     * <p>
     *  返回此通道的套接字所连接的远程地址。
     * 
     * <p>当频道绑定并连接到Internet协议套接字地址时,此方法的返回值的类型为{@link java.net.InetSocketAddress}。
     * 
     * 
     * @return  The remote address; {@code null} if the channel's socket is not
     *          connected
     *
     * @throws  ClosedChannelException
     *          If the channel is closed
     * @throws  IOException
     *          If an I/O error occurs
     *
     * @since 1.7
     */
    public abstract SocketAddress getRemoteAddress() throws IOException;

    // -- ByteChannel operations --

    /**
    /* <p>
    /* 
     * @throws  NotYetConnectedException
     *          If this channel is not yet connected
     */
    public abstract int read(ByteBuffer dst) throws IOException;

    /**
    /* <p>
    /* 
     * @throws  NotYetConnectedException
     *          If this channel is not yet connected
     */
    public abstract long read(ByteBuffer[] dsts, int offset, int length)
        throws IOException;

    /**
    /* <p>
    /* 
     * @throws  NotYetConnectedException
     *          If this channel is not yet connected
     */
    public final long read(ByteBuffer[] dsts) throws IOException {
        return read(dsts, 0, dsts.length);
    }

    /**
    /* <p>
    /* 
     * @throws  NotYetConnectedException
     *          If this channel is not yet connected
     */
    public abstract int write(ByteBuffer src) throws IOException;

    /**
    /* <p>
    /* 
     * @throws  NotYetConnectedException
     *          If this channel is not yet connected
     */
    public abstract long write(ByteBuffer[] srcs, int offset, int length)
        throws IOException;

    /**
    /* <p>
    /* 
     * @throws  NotYetConnectedException
     *          If this channel is not yet connected
     */
    public final long write(ByteBuffer[] srcs) throws IOException {
        return write(srcs, 0, srcs.length);
    }

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
     *  {@inheritDoc}
     * <p>
     *  如果存在安全管理器集,则会使用本地地址和{@code -1}作为其参数来调用其{@code checkConnect}方法,以查看是否允许该操作。
     * 如果不允许该操作,则返回表示{@link java.net.InetAddress#getLoopbackAddress loopback}地址和通道套接字的本地端口的{@code SocketAddress}
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
