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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Future;
import java.io.IOException;
import java.net.SocketOption;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

/**
 * An asynchronous channel for stream-oriented connecting sockets.
 *
 * <p> Asynchronous socket channels are created in one of two ways. A newly-created
 * {@code AsynchronousSocketChannel} is created by invoking one of the {@link
 * #open open} methods defined by this class. A newly-created channel is open but
 * not yet connected. A connected {@code AsynchronousSocketChannel} is created
 * when a connection is made to the socket of an {@link AsynchronousServerSocketChannel}.
 * It is not possible to create an asynchronous socket channel for an arbitrary,
 * pre-existing {@link java.net.Socket socket}.
 *
 * <p> A newly-created channel is connected by invoking its {@link #connect connect}
 * method; once connected, a channel remains connected until it is closed.  Whether
 * or not a socket channel is connected may be determined by invoking its {@link
 * #getRemoteAddress getRemoteAddress} method. An attempt to invoke an I/O
 * operation upon an unconnected channel will cause a {@link NotYetConnectedException}
 * to be thrown.
 *
 * <p> Channels of this type are safe for use by multiple concurrent threads.
 * They support concurrent reading and writing, though at most one read operation
 * and one write operation can be outstanding at any time.
 * If a thread initiates a read operation before a previous read operation has
 * completed then a {@link ReadPendingException} will be thrown. Similarly, an
 * attempt to initiate a write operation before a previous write has completed
 * will throw a {@link WritePendingException}.
 *
 * <p> Socket options are configured using the {@link #setOption(SocketOption,Object)
 * setOption} method. Asynchronous socket channels support the following options:
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
 *     <td> {@link java.net.StandardSocketOptions#TCP_NODELAY TCP_NODELAY} </td>
 *     <td> Disable the Nagle algorithm </td>
 *   </tr>
 * </table>
 * </blockquote>
 * Additional (implementation specific) options may also be supported.
 *
 * <h2>Timeouts</h2>
 *
 * <p> The {@link #read(ByteBuffer,long,TimeUnit,Object,CompletionHandler) read}
 * and {@link #write(ByteBuffer,long,TimeUnit,Object,CompletionHandler) write}
 * methods defined by this class allow a timeout to be specified when initiating
 * a read or write operation. If the timeout elapses before an operation completes
 * then the operation completes with the exception {@link
 * InterruptedByTimeoutException}. A timeout may leave the channel, or the
 * underlying connection, in an inconsistent state. Where the implementation
 * cannot guarantee that bytes have not been read from the channel then it puts
 * the channel into an implementation specific <em>error state</em>. A subsequent
 * attempt to initiate a {@code read} operation causes an unspecified runtime
 * exception to be thrown. Similarly if a {@code write} operation times out and
 * the implementation cannot guarantee bytes have not been written to the
 * channel then further attempts to {@code write} to the channel cause an
 * unspecified runtime exception to be thrown. When a timeout elapses then the
 * state of the {@link ByteBuffer}, or the sequence of buffers, for the I/O
 * operation is not defined. Buffers should be discarded or at least care must
 * be taken to ensure that the buffers are not accessed while the channel remains
 * open. All methods that accept timeout parameters treat values less than or
 * equal to zero to mean that the I/O operation does not timeout.
 *
 * <p>
 *  用于面向流的连接套接字的异步通道。
 * 
 *  <p>异步套接字通道以两种方式之一创建。新创建的{@code AsynchronousSocketChannel}是通过调用此类定义的{@link #open open}方法之一创建的。
 * 新创建的频道已打开但尚未连接。
 * 当连接到{@link AsynchronousServerSocketChannel}的套接字时,创建一个连接的{@code AsynchronousSocketChannel}。
 * 不可能为任意的,预先存在的{@link java.net.Socket socket}创建异步套接字通道。
 * 
 *  <p>新创建的频道通过调用其{@link #connect connect}方法连接;一旦连接,通道保持连接直到其关闭。
 * 是否连接套接字通道可以通过调用其{@link #getRemoteAddress getRemoteAddress}方法来确定。
 * 尝试在未连接的通道上调用I / O操作将导致抛出{@link NotYetConnectedException}。
 * 
 * <p>此类型的通道可安全地用于多个并发线程。它们支持并行读取和写入,但是在任何时候最多只能有一个读取操作和一个写入操作。
 * 如果线程在上一个读操作完成之前启动读操作,那么将抛出{@link ReadPendingException}。
 * 类似地,在前一个写入完成之前尝试发起写入操作将抛出{@link WritePendingException}。
 * 
 *  <p>使用{@link #setOption(SocketOption,Object)setOption}方法配置套接字选项。异步套接字通道支持以下选项：
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
 *  <td> {@link java.net.StandardSocketOptions#TCP_NODELAY TCP_NODELAY} </td> <td>禁用Nagle算法</td>
 * </tr>
 * </table>
 * </blockquote>
 *  还可以支持附加(实现特定)选项。
 * 
 *  <h2>超时</h2>
 * 
 * <p>此类定义的{@link #read(ByteBuffer,long,TimeUnit,Object,CompletionHandler)read}和{@link #write(ByteBuffer,long,TimeUnit,Object,CompletionHandler)write}
 * 方法允许超时在启动读取或写入操作时指定。
 * 如果在操作完成之前超时超时,则操作将完成,具有异常{@link InterruptedByTimeoutException}。超时可能会使通道或底层连接处于不一致状态。
 * 在实现不能保证字节没有从通道中读取的情况下,那么它将通道置于实现特定的错误状态。随后尝试启动{@code read}操作会导致抛出未指定的运行时异常。
 * 类似地,如果{@code write}操作超时,并且实现不能保证字节尚未被写入通道,则进一步尝试对通道进行{@code write}会导致抛出未指定的运行时异常。
 * 当超时消失时,未定义I / O操作的{@link ByteBuffer}或缓冲区序列的状态。缓冲器应被丢弃或至少必须小心确保在通道保持打开时不访问缓冲器。
 * 接受超时参数的所有方法处理小于或等于零的值,以表示I / O操作不超时。
 * 
 * 
 * @since 1.7
 */

public abstract class AsynchronousSocketChannel
    implements AsynchronousByteChannel, NetworkChannel
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
    protected AsynchronousSocketChannel(AsynchronousChannelProvider provider) {
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
     * Opens an asynchronous socket channel.
     *
     * <p> The new channel is created by invoking the {@link
     * AsynchronousChannelProvider#openAsynchronousSocketChannel
     * openAsynchronousSocketChannel} method on the {@link
     * AsynchronousChannelProvider} that created the group. If the group parameter
     * is {@code null} then the resulting channel is created by the system-wide
     * default provider, and bound to the <em>default group</em>.
     *
     * <p>
     * 打开异步套接字通道。
     * 
     *  <p>新频道是通过在创建组的{@link AsynchronousChannelProvider}上调用{@link AsynchronousChannelProvider#openAsynchronousSocketChannel openAsynchronousSocketChannel}
     * 方法创建的。
     * 如果group参数为{@code null},则生成的渠道由系统级默认提供商创建,并绑定到默认组</em>。
     * 
     * 
     * @param   group
     *          The group to which the newly constructed channel should be bound,
     *          or {@code null} for the default group
     *
     * @return  A new asynchronous socket channel
     *
     * @throws  ShutdownChannelGroupException
     *          If the channel group is shutdown
     * @throws  IOException
     *          If an I/O error occurs
     */
    public static AsynchronousSocketChannel open(AsynchronousChannelGroup group)
        throws IOException
    {
        AsynchronousChannelProvider provider = (group == null) ?
            AsynchronousChannelProvider.provider() : group.provider();
        return provider.openAsynchronousSocketChannel(group);
    }

    /**
     * Opens an asynchronous socket channel.
     *
     * <p> This method returns an asynchronous socket channel that is bound to
     * the <em>default group</em>.This method is equivalent to evaluating the
     * expression:
     * <blockquote><pre>
     * open((AsynchronousChannelGroup)null);
     * </pre></blockquote>
     *
     * <p>
     *  打开异步套接字通道。
     * 
     *  <p>此方法返回绑定到<em>默认组</em>的异步套接字通道。
     * 此方法等同于计算表达式：<blockquote> <pre> open((AsynchronousChannelGroup)null); </pre> </blockquote>。
     * 
     * 
     * @return  A new asynchronous socket channel
     *
     * @throws  IOException
     *          If an I/O error occurs
     */
    public static AsynchronousSocketChannel open()
        throws IOException
    {
        return open(null);
    }


    // -- socket options and related --

    /**
    /* <p>
    /* 
     * @throws  ConnectionPendingException
     *          If a connection operation is already in progress on this channel
     * @throws  AlreadyBoundException               {@inheritDoc}
     * @throws  UnsupportedAddressTypeException     {@inheritDoc}
     * @throws  ClosedChannelException              {@inheritDoc}
     * @throws  IOException                         {@inheritDoc}
     * @throws  SecurityException
     *          If a security manager has been installed and its
     *          {@link SecurityManager#checkListen checkListen} method denies
     *          the operation
     */
    @Override
    public abstract AsynchronousSocketChannel bind(SocketAddress local)
        throws IOException;

    /**
    /* <p>
    /* 
     * @throws  IllegalArgumentException                {@inheritDoc}
     * @throws  ClosedChannelException                  {@inheritDoc}
     * @throws  IOException                             {@inheritDoc}
     */
    @Override
    public abstract <T> AsynchronousSocketChannel setOption(SocketOption<T> name, T value)
        throws IOException;

    /**
     * Shutdown the connection for reading without closing the channel.
     *
     * <p> Once shutdown for reading then further reads on the channel will
     * return {@code -1}, the end-of-stream indication. If the input side of the
     * connection is already shutdown then invoking this method has no effect.
     * The effect on an outstanding read operation is system dependent and
     * therefore not specified. The effect, if any, when there is data in the
     * socket receive buffer that has not been read, or data arrives subsequently,
     * is also system dependent.
     *
     * <p>
     *  关闭连接以进行读取,而不关闭通道。
     * 
     *  <p>一旦关闭读取,则通道上的进一步读取将返回{@code -1},流末端指示。如果连接的输入端已经关闭,则调用此方法不起作用。对未完成的读操作的影响是系统相关的,因此没有指定。
     * 如果套接字接收缓冲器中有尚未读取的数据或数据随后到达,则效果(如果有的话)也与系统相关。
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
     */
    public abstract AsynchronousSocketChannel shutdownInput() throws IOException;

    /**
     * Shutdown the connection for writing without closing the channel.
     *
     * <p> Once shutdown for writing then further attempts to write to the
     * channel will throw {@link ClosedChannelException}. If the output side of
     * the connection is already shutdown then invoking this method has no
     * effect. The effect on an outstanding write operation is system dependent
     * and therefore not specified.
     *
     * <p>
     *  关闭用于写入的连接,而不关闭通道。
     * 
     * <p>一旦关闭写入,则进一步尝试写入通道将抛出{@link ClosedChannelException}。如果连接的输出端已经关闭,那么调用此方法没有效果。
     * 对未完成的写操作的影响是系统相关的,因此没有指定。
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
     */
    public abstract AsynchronousSocketChannel shutdownOutput() throws IOException;

    // -- state --

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
     *  <p>当频道绑定并连接到Internet协议套接字地址时,此方法的返回值的类型为{@link java.net.InetSocketAddress}。
     * 
     * 
     * @return  The remote address; {@code null} if the channel's socket is not
     *          connected
     *
     * @throws  ClosedChannelException
     *          If the channel is closed
     * @throws  IOException
     *          If an I/O error occurs
     */
    public abstract SocketAddress getRemoteAddress() throws IOException;

    // -- asynchronous operations --

    /**
     * Connects this channel.
     *
     * <p> This method initiates an operation to connect this channel. The
     * {@code handler} parameter is a completion handler that is invoked when
     * the connection is successfully established or connection cannot be
     * established. If the connection cannot be established then the channel is
     * closed.
     *
     * <p> This method performs exactly the same security checks as the {@link
     * java.net.Socket} class.  That is, if a security manager has been
     * installed then this method verifies that its {@link
     * java.lang.SecurityManager#checkConnect checkConnect} method permits
     * connecting to the address and port number of the given remote endpoint.
     *
     * <p>
     *  连接此通道。
     * 
     *  <p>此方法启动连接此通道的操作。 {@code handler}参数是在成功建立连接或无法建立连接时调用的完成处理程序。如果无法建立连接,则通道关闭。
     * 
     *  <p>此方法执行与{@link java.net.Socket}类完全相同的安全检查。
     * 也就是说,如果安装了安全管理器,则此方法将验证其{@link java.lang.SecurityManager#checkConnect checkConnect}方法是否允许连接到给定远程端点的地址
     * 和端口号。
     *  <p>此方法执行与{@link java.net.Socket}类完全相同的安全检查。
     * 
     * 
     * @param   <A>
     *          The type of the attachment
     * @param   remote
     *          The remote address to which this channel is to be connected
     * @param   attachment
     *          The object to attach to the I/O operation; can be {@code null}
     * @param   handler
     *          The handler for consuming the result
     *
     * @throws  UnresolvedAddressException
     *          If the given remote address is not fully resolved
     * @throws  UnsupportedAddressTypeException
     *          If the type of the given remote address is not supported
     * @throws  AlreadyConnectedException
     *          If this channel is already connected
     * @throws  ConnectionPendingException
     *          If a connection operation is already in progress on this channel
     * @throws  ShutdownChannelGroupException
     *          If the channel group has terminated
     * @throws  SecurityException
     *          If a security manager has been installed
     *          and it does not permit access to the given remote endpoint
     *
     * @see #getRemoteAddress
     */
    public abstract <A> void connect(SocketAddress remote,
                                     A attachment,
                                     CompletionHandler<Void,? super A> handler);

    /**
     * Connects this channel.
     *
     * <p> This method initiates an operation to connect this channel. This
     * method behaves in exactly the same manner as the {@link
     * #connect(SocketAddress, Object, CompletionHandler)} method except that
     * instead of specifying a completion handler, this method returns a {@code
     * Future} representing the pending result. The {@code Future}'s {@link
     * Future#get() get} method returns {@code null} on successful completion.
     *
     * <p>
     *  连接此通道。
     * 
     * <p>此方法启动连接此通道的操作。
     * 此方法的行为方式与{@link #connect(SocketAddress,Object,CompletionHandler)}方法完全相同,只是此方法不返回指定完成处理程序,而是返回表示待处理结果的
     * {@code Future}。
     * <p>此方法启动连接此通道的操作。成功完成后,{@code Future}的{@link Future#get()get}方法返回{@code null}。
     * 
     * 
     * @param   remote
     *          The remote address to which this channel is to be connected
     *
     * @return  A {@code Future} object representing the pending result
     *
     * @throws  UnresolvedAddressException
     *          If the given remote address is not fully resolved
     * @throws  UnsupportedAddressTypeException
     *          If the type of the given remote address is not supported
     * @throws  AlreadyConnectedException
     *          If this channel is already connected
     * @throws  ConnectionPendingException
     *          If a connection operation is already in progress on this channel
     * @throws  SecurityException
     *          If a security manager has been installed
     *          and it does not permit access to the given remote endpoint
     */
    public abstract Future<Void> connect(SocketAddress remote);

    /**
     * Reads a sequence of bytes from this channel into the given buffer.
     *
     * <p> This method initiates an asynchronous read operation to read a
     * sequence of bytes from this channel into the given buffer. The {@code
     * handler} parameter is a completion handler that is invoked when the read
     * operation completes (or fails). The result passed to the completion
     * handler is the number of bytes read or {@code -1} if no bytes could be
     * read because the channel has reached end-of-stream.
     *
     * <p> If a timeout is specified and the timeout elapses before the operation
     * completes then the operation completes with the exception {@link
     * InterruptedByTimeoutException}. Where a timeout occurs, and the
     * implementation cannot guarantee that bytes have not been read, or will not
     * be read from the channel into the given buffer, then further attempts to
     * read from the channel will cause an unspecific runtime exception to be
     * thrown.
     *
     * <p> Otherwise this method works in the same manner as the {@link
     * AsynchronousByteChannel#read(ByteBuffer,Object,CompletionHandler)}
     * method.
     *
     * <p>
     *  从该通道读取一个字节序列到给定的缓冲区。
     * 
     *  <p>此方法启动异步读取操作,以将字节序列从此通道读取到给定缓冲区中。 {@code handler}参数是在读取操作完成(或失败)时调用的完成处理程序。
     * 传递给完成处理程序的结果是读取的字节数或{@code -1}如果没有字节可以读取,因为通道已达到流结束。
     * 
     *  <p>如果指定了超时,并且操作完成之前超时已过,则操作将完成,并显示异常{@link InterruptedByTimeoutException}。
     * 在发生超时的情况下,实现不能保证字节没有被读取,或者不会从通道读取到给定缓冲器中,则从通道读取的进一步尝试将导致抛出非特定的运行时异常。
     * 
     *  <p>否则,此方法的工作方式与{@link AsynchronousByteChannel#read(ByteBuffer,Object,CompletionHandler)}方法相同。
     * 
     * 
     * @param   <A>
     *          The type of the attachment
     * @param   dst
     *          The buffer into which bytes are to be transferred
     * @param   timeout
     *          The maximum time for the I/O operation to complete
     * @param   unit
     *          The time unit of the {@code timeout} argument
     * @param   attachment
     *          The object to attach to the I/O operation; can be {@code null}
     * @param   handler
     *          The handler for consuming the result
     *
     * @throws  IllegalArgumentException
     *          If the buffer is read-only
     * @throws  ReadPendingException
     *          If a read operation is already in progress on this channel
     * @throws  NotYetConnectedException
     *          If this channel is not yet connected
     * @throws  ShutdownChannelGroupException
     *          If the channel group has terminated
     */
    public abstract <A> void read(ByteBuffer dst,
                                  long timeout,
                                  TimeUnit unit,
                                  A attachment,
                                  CompletionHandler<Integer,? super A> handler);

    /**
    /* <p>
    /* 
     * @throws  IllegalArgumentException        {@inheritDoc}
     * @throws  ReadPendingException            {@inheritDoc}
     * @throws  NotYetConnectedException
     *          If this channel is not yet connected
     * @throws  ShutdownChannelGroupException
     *          If the channel group has terminated
     */
    @Override
    public final <A> void read(ByteBuffer dst,
                               A attachment,
                               CompletionHandler<Integer,? super A> handler)
    {
        read(dst, 0L, TimeUnit.MILLISECONDS, attachment, handler);
    }

    /**
    /* <p>
    /* 
     * @throws  IllegalArgumentException        {@inheritDoc}
     * @throws  ReadPendingException            {@inheritDoc}
     * @throws  NotYetConnectedException
     *          If this channel is not yet connected
     */
    @Override
    public abstract Future<Integer> read(ByteBuffer dst);

    /**
     * Reads a sequence of bytes from this channel into a subsequence of the
     * given buffers. This operation, sometimes called a <em>scattering read</em>,
     * is often useful when implementing network protocols that group data into
     * segments consisting of one or more fixed-length headers followed by a
     * variable-length body. The {@code handler} parameter is a completion
     * handler that is invoked when the read operation completes (or fails). The
     * result passed to the completion handler is the number of bytes read or
     * {@code -1} if no bytes could be read because the channel has reached
     * end-of-stream.
     *
     * <p> This method initiates a read of up to <i>r</i> bytes from this channel,
     * where <i>r</i> is the total number of bytes remaining in the specified
     * subsequence of the given buffer array, that is,
     *
     * <blockquote><pre>
     * dsts[offset].remaining()
     *     + dsts[offset+1].remaining()
     *     + ... + dsts[offset+length-1].remaining()</pre></blockquote>
     *
     * at the moment that the read is attempted.
     *
     * <p> Suppose that a byte sequence of length <i>n</i> is read, where
     * <tt>0</tt>&nbsp;<tt>&lt;</tt>&nbsp;<i>n</i>&nbsp;<tt>&lt;=</tt>&nbsp;<i>r</i>.
     * Up to the first <tt>dsts[offset].remaining()</tt> bytes of this sequence
     * are transferred into buffer <tt>dsts[offset]</tt>, up to the next
     * <tt>dsts[offset+1].remaining()</tt> bytes are transferred into buffer
     * <tt>dsts[offset+1]</tt>, and so forth, until the entire byte sequence
     * is transferred into the given buffers.  As many bytes as possible are
     * transferred into each buffer, hence the final position of each updated
     * buffer, except the last updated buffer, is guaranteed to be equal to
     * that buffer's limit. The underlying operating system may impose a limit
     * on the number of buffers that may be used in an I/O operation. Where the
     * number of buffers (with bytes remaining), exceeds this limit, then the
     * I/O operation is performed with the maximum number of buffers allowed by
     * the operating system.
     *
     * <p> If a timeout is specified and the timeout elapses before the operation
     * completes then it completes with the exception {@link
     * InterruptedByTimeoutException}. Where a timeout occurs, and the
     * implementation cannot guarantee that bytes have not been read, or will not
     * be read from the channel into the given buffers, then further attempts to
     * read from the channel will cause an unspecific runtime exception to be
     * thrown.
     *
     * <p>
     * 将来自该通道的字节序列读取到给定缓冲器的子序列中。当实现将数据分组成由一个或多个固定长度的报头和随后的可变长度主体组成的段的网络协议时,该操作(有时被称为<em>散射读取</em>)通常是有用的。
     *  {@code handler}参数是在读取操作完成(或失败)时调用的完成处理程序。传递给完成处理程序的结果是读取的字节数或{@code -1}如果没有字节可以读取,因为通道已达到流结束。
     * 
     *  <p>该方法从该通道开始读取多达r个字节,其中<i>是在给定缓冲器阵列的指定子序列中剩余的字节的总数,那是,
     * 
     *  <blockquote> <pre> dsts [offset] .remaining()+ dsts [offset + 1] .remaining()+ ... + dsts [offset + 
     * length-1] .remaining()</。
     * 
     *  在尝试读取的时刻。
     * 
     * <p>假设读取一个长度为n的字节序列,其中<tt> 0 </tt> <tt> </tt>&lt; / n >&nbsp; <tt>&lt; = </tt>&nbsp; <i> r </i>。
     * 直到该序列的第一个<tt> dsts [offset] .remaining()</tt>字节传输到缓冲区<tt> dsts [offset] </tt>,直到下一个<tt> dsts [ 尽可能多的字
     * 节被传送到每个缓冲器,因此除了最后更新的缓冲器之外,每个更新的缓冲器的最终位置被保证等于该缓冲器的限制。
     * <p>假设读取一个长度为n的字节序列,其中<tt> 0 </tt> <tt> </tt>&lt; / n >&nbsp; <tt>&lt; = </tt>&nbsp; <i> r </i>。
     * 底层操作系统可以对可以在I / O操作中使用的缓冲器的数量施加限制。如果缓冲区数(剩余字节数)超过此限制,那么将使用操作系统允许的最大缓冲区数执行I / O操作。
     * 
     *  <p>如果指定了超时,并且操作完成之前超时已过,那么将使用异常{@link InterruptedByTimeoutException}完成。
     * 在发生超时的情况下,实现不能保证字节未被读取,或者不会从通道读取到给定缓冲器中,则从通道读取的进一步尝试将导致抛出非特定的运行时异常。
     * 
     * 
     * @param   <A>
     *          The type of the attachment
     * @param   dsts
     *          The buffers into which bytes are to be transferred
     * @param   offset
     *          The offset within the buffer array of the first buffer into which
     *          bytes are to be transferred; must be non-negative and no larger than
     *          {@code dsts.length}
     * @param   length
     *          The maximum number of buffers to be accessed; must be non-negative
     *          and no larger than {@code dsts.length - offset}
     * @param   timeout
     *          The maximum time for the I/O operation to complete
     * @param   unit
     *          The time unit of the {@code timeout} argument
     * @param   attachment
     *          The object to attach to the I/O operation; can be {@code null}
     * @param   handler
     *          The handler for consuming the result
     *
     * @throws  IndexOutOfBoundsException
     *          If the pre-conditions for the {@code offset}  and {@code length}
     *          parameter aren't met
     * @throws  IllegalArgumentException
     *          If the buffer is read-only
     * @throws  ReadPendingException
     *          If a read operation is already in progress on this channel
     * @throws  NotYetConnectedException
     *          If this channel is not yet connected
     * @throws  ShutdownChannelGroupException
     *          If the channel group has terminated
     */
    public abstract <A> void read(ByteBuffer[] dsts,
                                  int offset,
                                  int length,
                                  long timeout,
                                  TimeUnit unit,
                                  A attachment,
                                  CompletionHandler<Long,? super A> handler);

    /**
     * Writes a sequence of bytes to this channel from the given buffer.
     *
     * <p> This method initiates an asynchronous write operation to write a
     * sequence of bytes to this channel from the given buffer. The {@code
     * handler} parameter is a completion handler that is invoked when the write
     * operation completes (or fails). The result passed to the completion
     * handler is the number of bytes written.
     *
     * <p> If a timeout is specified and the timeout elapses before the operation
     * completes then it completes with the exception {@link
     * InterruptedByTimeoutException}. Where a timeout occurs, and the
     * implementation cannot guarantee that bytes have not been written, or will
     * not be written to the channel from the given buffer, then further attempts
     * to write to the channel will cause an unspecific runtime exception to be
     * thrown.
     *
     * <p> Otherwise this method works in the same manner as the {@link
     * AsynchronousByteChannel#write(ByteBuffer,Object,CompletionHandler)}
     * method.
     *
     * <p>
     *  从给定缓冲区向此通道写入一个字节序列。
     * 
     * <p>此方法启动异步写入操作,从给定缓冲区向此通道写入一系列字节。 {@code handler}参数是在写操作完成(或失败)时调用的完成处理程序。传递给完成处理程序的结果是写入的字节数。
     * 
     *  <p>如果指定了超时,并且操作完成之前超时已过,那么将使用异常{@link InterruptedByTimeoutException}完成。
     * 在发生超时的情况下,实现不能保证字节没有被写入,或者不会从给定缓冲器写入通道,则进一步尝试写入通道将导致抛出非特定的运行时异常。
     * 
     *  <p>否则,此方法的工作方式与{@link AsynchronousByteChannel#write(ByteBuffer,Object,CompletionHandler)}方法相同。
     * 
     * 
     * @param   <A>
     *          The type of the attachment
     * @param   src
     *          The buffer from which bytes are to be retrieved
     * @param   timeout
     *          The maximum time for the I/O operation to complete
     * @param   unit
     *          The time unit of the {@code timeout} argument
     * @param   attachment
     *          The object to attach to the I/O operation; can be {@code null}
     * @param   handler
     *          The handler for consuming the result
     *
     * @throws  WritePendingException
     *          If a write operation is already in progress on this channel
     * @throws  NotYetConnectedException
     *          If this channel is not yet connected
     * @throws  ShutdownChannelGroupException
     *          If the channel group has terminated
     */
    public abstract <A> void write(ByteBuffer src,
                                   long timeout,
                                   TimeUnit unit,
                                   A attachment,
                                   CompletionHandler<Integer,? super A> handler);

    /**
    /* <p>
    /* 
     * @throws  WritePendingException          {@inheritDoc}
     * @throws  NotYetConnectedException
     *          If this channel is not yet connected
     * @throws  ShutdownChannelGroupException
     *          If the channel group has terminated
     */
    @Override
    public final <A> void write(ByteBuffer src,
                                A attachment,
                                CompletionHandler<Integer,? super A> handler)

    {
        write(src, 0L, TimeUnit.MILLISECONDS, attachment, handler);
    }

    /**
    /* <p>
    /* 
     * @throws  WritePendingException       {@inheritDoc}
     * @throws  NotYetConnectedException
     *          If this channel is not yet connected
     */
    @Override
    public abstract Future<Integer> write(ByteBuffer src);

    /**
     * Writes a sequence of bytes to this channel from a subsequence of the given
     * buffers. This operation, sometimes called a <em>gathering write</em>, is
     * often useful when implementing network protocols that group data into
     * segments consisting of one or more fixed-length headers followed by a
     * variable-length body. The {@code handler} parameter is a completion
     * handler that is invoked when the write operation completes (or fails).
     * The result passed to the completion handler is the number of bytes written.
     *
     * <p> This method initiates a write of up to <i>r</i> bytes to this channel,
     * where <i>r</i> is the total number of bytes remaining in the specified
     * subsequence of the given buffer array, that is,
     *
     * <blockquote><pre>
     * srcs[offset].remaining()
     *     + srcs[offset+1].remaining()
     *     + ... + srcs[offset+length-1].remaining()</pre></blockquote>
     *
     * at the moment that the write is attempted.
     *
     * <p> Suppose that a byte sequence of length <i>n</i> is written, where
     * <tt>0</tt>&nbsp;<tt>&lt;</tt>&nbsp;<i>n</i>&nbsp;<tt>&lt;=</tt>&nbsp;<i>r</i>.
     * Up to the first <tt>srcs[offset].remaining()</tt> bytes of this sequence
     * are written from buffer <tt>srcs[offset]</tt>, up to the next
     * <tt>srcs[offset+1].remaining()</tt> bytes are written from buffer
     * <tt>srcs[offset+1]</tt>, and so forth, until the entire byte sequence is
     * written.  As many bytes as possible are written from each buffer, hence
     * the final position of each updated buffer, except the last updated
     * buffer, is guaranteed to be equal to that buffer's limit. The underlying
     * operating system may impose a limit on the number of buffers that may be
     * used in an I/O operation. Where the number of buffers (with bytes
     * remaining), exceeds this limit, then the I/O operation is performed with
     * the maximum number of buffers allowed by the operating system.
     *
     * <p> If a timeout is specified and the timeout elapses before the operation
     * completes then it completes with the exception {@link
     * InterruptedByTimeoutException}. Where a timeout occurs, and the
     * implementation cannot guarantee that bytes have not been written, or will
     * not be written to the channel from the given buffers, then further attempts
     * to write to the channel will cause an unspecific runtime exception to be
     * thrown.
     *
     * <p>
     *  从给定缓冲区的子序列向该通道写入一个字节序列。当实现将数据分组成由一个或多个固定长度的报头以及随后的可变长度主体组成的段的网络协议时,该操作(有时被称为<em>收集写入</em>)通常是有用的。
     *  {@code handler}参数是在写操作完成(或失败)时调用的完成处理程序。传递给完成处理程序的结果是写入的字节数。
     * 
     * <p>该方法开始向该通道写入多达r个字节,其中</i>是在给定缓冲器阵列的指定子序列中剩余的字节的总数,那是,
     * 
     *  <blockquote> <pre> srcs [offset] .remaining()+ srcs [offset + 1] .remaining()+ ... + srcs [offset + 
     * length-1] .remaining()</。
     * 
     *  在尝试写入的时刻。
     * 
     *  <p>假设写入了长度为<i> n的字节序列,其中<tt> 0 </tt> <tt> </tt>&lt; / n >&nbsp; <tt>&lt; = </tt>&nbsp; <i> r </i>。
     * 直到该序列的第一个<tt> srcs [offset] .remaining()</tt>字节从缓冲区<tt> srcs [offset] </tt>写入,直到下一个<tt> srcs [ 从每个缓冲器
     * 写入尽可能多的字节,因此除了最后更新的缓冲器之外,每个更新的缓冲器的最终位置被保证等于该缓冲器的极限。
     *  <p>假设写入了长度为<i> n的字节序列,其中<tt> 0 </tt> <tt> </tt>&lt; / n >&nbsp; <tt>&lt; = </tt>&nbsp; <i> r </i>。
     * 
     * @param   <A>
     *          The type of the attachment
     * @param   srcs
     *          The buffers from which bytes are to be retrieved
     * @param   offset
     *          The offset within the buffer array of the first buffer from which
     *          bytes are to be retrieved; must be non-negative and no larger
     *          than {@code srcs.length}
     * @param   length
     *          The maximum number of buffers to be accessed; must be non-negative
     *          and no larger than {@code srcs.length - offset}
     * @param   timeout
     *          The maximum time for the I/O operation to complete
     * @param   unit
     *          The time unit of the {@code timeout} argument
     * @param   attachment
     *          The object to attach to the I/O operation; can be {@code null}
     * @param   handler
     *          The handler for consuming the result
     *
     * @throws  IndexOutOfBoundsException
     *          If the pre-conditions for the {@code offset}  and {@code length}
     *          parameter aren't met
     * @throws  WritePendingException
     *          If a write operation is already in progress on this channel
     * @throws  NotYetConnectedException
     *          If this channel is not yet connected
     * @throws  ShutdownChannelGroupException
     *          If the channel group has terminated
     */
    public abstract <A> void write(ByteBuffer[] srcs,
                                   int offset,
                                   int length,
                                   long timeout,
                                   TimeUnit unit,
                                   A attachment,
                                   CompletionHandler<Long,? super A> handler);

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
     * 底层操作系统可以对可以在I / O操作中使用的缓冲器的数量施加限制。如果缓冲区数(剩余字节数)超过此限制,那么将使用操作系统允许的最大缓冲区数执行I / O操作。
     * 
     * <p>如果指定了超时,并且操作完成之前超时已过,那么将使用异常{@link InterruptedByTimeoutException}完成。
     * 在超时发生时,实现不能保证字节没有被写入,或者不会从给定的缓冲区写入通道,则进一步尝试写入通道将导致抛出非特定的运行时异常。
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
    public abstract SocketAddress getLocalAddress() throws IOException;
}
