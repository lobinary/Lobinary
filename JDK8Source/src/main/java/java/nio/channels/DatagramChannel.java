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
import java.net.ProtocolFamily;
import java.net.DatagramSocket;
import java.net.SocketOption;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.nio.channels.spi.SelectorProvider;

/**
 * A selectable channel for datagram-oriented sockets.
 *
 * <p> A datagram channel is created by invoking one of the {@link #open open} methods
 * of this class. It is not possible to create a channel for an arbitrary,
 * pre-existing datagram socket. A newly-created datagram channel is open but not
 * connected. A datagram channel need not be connected in order for the {@link #send
 * send} and {@link #receive receive} methods to be used.  A datagram channel may be
 * connected, by invoking its {@link #connect connect} method, in order to
 * avoid the overhead of the security checks are otherwise performed as part of
 * every send and receive operation.  A datagram channel must be connected in
 * order to use the {@link #read(java.nio.ByteBuffer) read} and {@link
 * #write(java.nio.ByteBuffer) write} methods, since those methods do not
 * accept or return socket addresses.
 *
 * <p> Once connected, a datagram channel remains connected until it is
 * disconnected or closed.  Whether or not a datagram channel is connected may
 * be determined by invoking its {@link #isConnected isConnected} method.
 *
 * <p> Socket options are configured using the {@link #setOption(SocketOption,Object)
 * setOption} method. A datagram channel to an Internet Protocol socket supports
 * the following options:
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
 *     <td> {@link java.net.StandardSocketOptions#SO_REUSEADDR SO_REUSEADDR} </td>
 *     <td> Re-use address </td>
 *   </tr>
 *   <tr>
 *     <td> {@link java.net.StandardSocketOptions#SO_BROADCAST SO_BROADCAST} </td>
 *     <td> Allow transmission of broadcast datagrams </td>
 *   </tr>
 *   <tr>
 *     <td> {@link java.net.StandardSocketOptions#IP_TOS IP_TOS} </td>
 *     <td> The Type of Service (ToS) octet in the Internet Protocol (IP) header </td>
 *   </tr>
 *   <tr>
 *     <td> {@link java.net.StandardSocketOptions#IP_MULTICAST_IF IP_MULTICAST_IF} </td>
 *     <td> The network interface for Internet Protocol (IP) multicast datagrams </td>
 *   </tr>
 *   <tr>
 *     <td> {@link java.net.StandardSocketOptions#IP_MULTICAST_TTL
 *       IP_MULTICAST_TTL} </td>
 *     <td> The <em>time-to-live</em> for Internet Protocol (IP) multicast
 *       datagrams </td>
 *   </tr>
 *   <tr>
 *     <td> {@link java.net.StandardSocketOptions#IP_MULTICAST_LOOP
 *       IP_MULTICAST_LOOP} </td>
 *     <td> Loopback for Internet Protocol (IP) multicast datagrams </td>
 *   </tr>
 * </table>
 * </blockquote>
 * Additional (implementation specific) options may also be supported.
 *
 * <p> Datagram channels are safe for use by multiple concurrent threads.  They
 * support concurrent reading and writing, though at most one thread may be
 * reading and at most one thread may be writing at any given time.  </p>
 *
 * <p>
 *  面向数据报的套接字的可选通道。
 * 
 *  <p>通过调用此类的{@link #open open}方法之一创建数据报通道。不可能为任意的,预先存在的数据报套接字创建通道。新创建的数据报通道已打开但未连接。
 * 不需要连接数据报信道,以便使用{@link #send send}和{@link #receive receive}方法。
 * 可以通过调用其{@link #connect connect}方法来连接数据报信道,以避免作为每个发送和接收操作的一部分执行安全检查的开销。
 * 为了使用{@link #read(java.nio.ByteBuffer)read}和{@link #write(java.nio.ByteBuffer)write}方法,必须连接数据报通道,因为这些方
 * 法不接受或返回套接字地址。
 * 可以通过调用其{@link #connect connect}方法来连接数据报信道,以避免作为每个发送和接收操作的一部分执行安全检查的开销。
 * 
 *  <p>连接后,数据报通道保持连接状态,直到断开或关闭。可以通过调用其{@link #isConnected isConnected}方法来确定数据报信道是否连接。
 * 
 *  <p>使用{@link #setOption(SocketOption,Object)setOption}方法配置套接字选项。到Internet协议套接字的数据报通道支持以下选项：
 * <blockquote>
 * <table border summary="Socket options">
 * <tr>
 *  <th>选项名称</th> <th>说明</th>
 * </tr>
 * <tr>
 * <td> {@link java.net.StandardSocketOptions#SO_SNDBUF SO_SNDBUF} </td> <td>套接字发送缓冲区的大小</td>
 * </tr>
 * <tr>
 *  <td> {@link java.net.StandardSocketOptions#SO_RCVBUF SO_RCVBUF} </td> <td>套接字接收缓冲区的大小</td>
 * </tr>
 * <tr>
 *  <td> {@link java.net.StandardSocketOptions#SO_REUSEADDR SO_REUSEADDR} </td> <td>重新使用地址</td>
 * </tr>
 * <tr>
 *  <td> {@link java.net.StandardSocketOptions#SO_BROADCAST SO_BROADCAST} </td> <td>允许传输广播数据报</td>
 * </tr>
 * <tr>
 *  <td> {@link java.net.StandardSocketOptions#IP_TOS IP_TOS} </td> <td> Internet协议(IP)头中的服务类型(ToS)八位字节</td>
 * 。
 * </tr>
 * <tr>
 *  <td> {@link java.net.StandardSocketOptions#IP_MULTICAST_IF IP_MULTICAST_IF} </td> <td>因特网协议(IP)多播数据报
 * 的网络接口</td>。
 * </tr>
 * <tr>
 *  <td> {@link java.net.StandardSocketOptions#IP_MULTICAST_TTL IP_MULTICAST_TTL} </td> <td>互联网协议(IP)多播数
 * 据报的<em>生存时间</em> </td>。
 * </tr>
 * <tr>
 *  <td> {@link java.net.StandardSocketOptions#IP_MULTICAST_LOOP IP_MULTICAST_LOOP} </td> <td>因特网协议(IP)多
 * 播数据报的环回</td>。
 * </tr>
 * </table>
 * </blockquote>
 *  还可以支持附加(实现特定)选项。
 * 
 *  <p>数据报通道可安全地用于多个并发线程。它们支持并发读取和写入,但是最多一个线程可能正在读取,并且最多一个线程可能在任何给定时间写入。 </p>
 * 
 * 
 * @author Mark Reinhold
 * @author JSR-51 Expert Group
 * @since 1.4
 */

public abstract class DatagramChannel
    extends AbstractSelectableChannel
    implements ByteChannel, ScatteringByteChannel, GatheringByteChannel, MulticastChannel
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
    protected DatagramChannel(SelectorProvider provider) {
        super(provider);
    }

    /**
     * Opens a datagram channel.
     *
     * <p> The new channel is created by invoking the {@link
     * java.nio.channels.spi.SelectorProvider#openDatagramChannel()
     * openDatagramChannel} method of the system-wide default {@link
     * java.nio.channels.spi.SelectorProvider} object.  The channel will not be
     * connected.
     *
     * <p> The {@link ProtocolFamily ProtocolFamily} of the channel's socket
     * is platform (and possibly configuration) dependent and therefore unspecified.
     * The {@link #open(ProtocolFamily) open} allows the protocol family to be
     * selected when opening a datagram channel, and should be used to open
     * datagram channels that are intended for Internet Protocol multicasting.
     *
     * <p>
     *  打开数据报通道。
     * 
     * <p>新频道是通过调用系统级默认{@link java.nio.channels.spi.SelectorProvider}对象的{@link java.nio.channels.spi.SelectorProvider#openDatagramChannel()openDatagramChannel}
     * 方法创建的。
     * 通道不会连接。
     * 
     *  <p>通道套接字的{@link ProtocolFamily ProtocolFamily}是平台(可能是配置)相关的,因此未指定。
     *  {@link #open(ProtocolFamily)open}允许在打开数据报通道时选择协议系列,并且应该用于打开用于Internet协议多播的数据报通道。
     * 
     * 
     * @return  A new datagram channel
     *
     * @throws  IOException
     *          If an I/O error occurs
     */
    public static DatagramChannel open() throws IOException {
        return SelectorProvider.provider().openDatagramChannel();
    }

    /**
     * Opens a datagram channel.
     *
     * <p> The {@code family} parameter is used to specify the {@link
     * ProtocolFamily}. If the datagram channel is to be used for IP multicasting
     * then this should correspond to the address type of the multicast groups
     * that this channel will join.
     *
     * <p> The new channel is created by invoking the {@link
     * java.nio.channels.spi.SelectorProvider#openDatagramChannel(ProtocolFamily)
     * openDatagramChannel} method of the system-wide default {@link
     * java.nio.channels.spi.SelectorProvider} object.  The channel will not be
     * connected.
     *
     * <p>
     *  打开数据报通道。
     * 
     *  <p> {@code family}参数用于指定{@link ProtocolFamily}。如果数据报信道要用于IP多播,则这应该对应于该信道将加入的多播组的地址类型。
     * 
     *  <p>新频道是通过调用系统级默认的{@link java.nio.channels.spi.SelectorProvider#openDatagramChannel(ProtocolFamily)openDatagramChannel}
     * 方法创建的{@link java.nio.channels.spi.SelectorProvider}目的。
     * 通道不会连接。
     * 
     * 
     * @param   family
     *          The protocol family
     *
     * @return  A new datagram channel
     *
     * @throws  UnsupportedOperationException
     *          If the specified protocol family is not supported. For example,
     *          suppose the parameter is specified as {@link
     *          java.net.StandardProtocolFamily#INET6 StandardProtocolFamily.INET6}
     *          but IPv6 is not enabled on the platform.
     * @throws  IOException
     *          If an I/O error occurs
     *
     * @since   1.7
     */
    public static DatagramChannel open(ProtocolFamily family) throws IOException {
        return SelectorProvider.provider().openDatagramChannel(family);
    }

    /**
     * Returns an operation set identifying this channel's supported
     * operations.
     *
     * <p> Datagram channels support reading and writing, so this method
     * returns <tt>(</tt>{@link SelectionKey#OP_READ} <tt>|</tt>&nbsp;{@link
     * SelectionKey#OP_WRITE}<tt>)</tt>.  </p>
     *
     * <p>
     *  返回标识此通道支持的操作的操作集。
     * 
     *  <p>数据报通道支持读写,因此此方法返回<tt>(</tt> {@ link SelectionKey#OP_READ} <tt> | </tt>&nbsp; {@ link SelectionKey#OP_WRITE}
     *  <tt> </tt>。
     *  </p>。
     * 
     * 
     * @return  The valid-operation set
     */
    public final int validOps() {
        return (SelectionKey.OP_READ
                | SelectionKey.OP_WRITE);
    }


    // -- Socket-specific operations --

    /**
    /* <p>
    /* 
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
    public abstract DatagramChannel bind(SocketAddress local)
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
    public abstract <T> DatagramChannel setOption(SocketOption<T> name, T value)
        throws IOException;

    /**
     * Retrieves a datagram socket associated with this channel.
     *
     * <p> The returned object will not declare any public methods that are not
     * declared in the {@link java.net.DatagramSocket} class.  </p>
     *
     * <p>
     *  检索与此通道关联的数据报套接字。
     * 
     * <p>返回的对象不会声明任何未在{@link java.net.DatagramSocket}类中声明的公共方法。 </p>
     * 
     * 
     * @return  A datagram socket associated with this channel
     */
    public abstract DatagramSocket socket();

    /**
     * Tells whether or not this channel's socket is connected.
     *
     * <p>
     *  告诉这个通道的插座是否连接。
     * 
     * 
     * @return  {@code true} if, and only if, this channel's socket
     *          is {@link #isOpen open} and connected
     */
    public abstract boolean isConnected();

    /**
     * Connects this channel's socket.
     *
     * <p> The channel's socket is configured so that it only receives
     * datagrams from, and sends datagrams to, the given remote <i>peer</i>
     * address.  Once connected, datagrams may not be received from or sent to
     * any other address.  A datagram socket remains connected until it is
     * explicitly disconnected or until it is closed.
     *
     * <p> This method performs exactly the same security checks as the {@link
     * java.net.DatagramSocket#connect connect} method of the {@link
     * java.net.DatagramSocket} class.  That is, if a security manager has been
     * installed then this method verifies that its {@link
     * java.lang.SecurityManager#checkAccept checkAccept} and {@link
     * java.lang.SecurityManager#checkConnect checkConnect} methods permit
     * datagrams to be received from and sent to, respectively, the given
     * remote address.
     *
     * <p> This method may be invoked at any time.  It will not have any effect
     * on read or write operations that are already in progress at the moment
     * that it is invoked. If this channel's socket is not bound then this method
     * will first cause the socket to be bound to an address that is assigned
     * automatically, as if invoking the {@link #bind bind} method with a
     * parameter of {@code null}. </p>
     *
     * <p>
     *  连接此通道的插座。
     * 
     *  <p>通道的套接字配置为只接收来自给定远程对等体地址的数据报,并将数据报发送到给定远程对等体地址。一旦连接,可以不从任何其他地址接收或发送数据报。数据报套接字保持连接,直到它明确断开或直到它被关闭。
     * 
     *  <p>此方法执行与{@link java.net.DatagramSocket}类的{@link java.net.DatagramSocket#connect connect}方法完全相同的安全检查
     * 。
     * 也就是说,如果已经安装了安全管理器,那么此方法将验证其{@link java.lang.SecurityManager#checkAccept checkAccept}和{@link java.lang.SecurityManager#checkConnect checkConnect}
     * 方法是否允许接收和发送数据报分别到给定的远程地址。
     * 
     *  <p>此方法可能随时被调用。它不会对在调用时已经在进行的读取或写入操作产生任何影响。
     * 如果这个通道的套接字没有绑定,那么这个方法将首先导致套接字绑定到一个自动分配的地址,就好像使用参数{@code null}调用{@link #bind bind}方法。 </p>。
     * 
     * 
     * @param  remote
     *         The remote address to which this channel is to be connected
     *
     * @return  This datagram channel
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
     * @throws  SecurityException
     *          If a security manager has been installed
     *          and it does not permit access to the given remote address
     *
     * @throws  IOException
     *          If some other I/O error occurs
     */
    public abstract DatagramChannel connect(SocketAddress remote)
        throws IOException;

    /**
     * Disconnects this channel's socket.
     *
     * <p> The channel's socket is configured so that it can receive datagrams
     * from, and sends datagrams to, any remote address so long as the security
     * manager, if installed, permits it.
     *
     * <p> This method may be invoked at any time.  It will not have any effect
     * on read or write operations that are already in progress at the moment
     * that it is invoked.
     *
     * <p> If this channel's socket is not connected, or if the channel is
     * closed, then invoking this method has no effect.  </p>
     *
     * <p>
     *  断开此通道的插座。
     * 
     * <p>通道的套接字配置为只要安全管理器允许,就可以从任何远程地址接收数据报,并将数据报发送到任何远程地址。
     * 
     *  <p>此方法可能随时被调用。它不会对在调用时已经在进行的读取或写入操作产生任何影响。
     * 
     *  <p>如果此频道的套接字未连接,或者频道已关闭,则调用此方法无效。 </p>
     * 
     * 
     * @return  This datagram channel
     *
     * @throws  IOException
     *          If some other I/O error occurs
     */
    public abstract DatagramChannel disconnect() throws IOException;

    /**
     * Returns the remote address to which this channel's socket is connected.
     *
     * <p>
     *  返回此通道的套接字所连接的远程地址。
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

    /**
     * Receives a datagram via this channel.
     *
     * <p> If a datagram is immediately available, or if this channel is in
     * blocking mode and one eventually becomes available, then the datagram is
     * copied into the given byte buffer and its source address is returned.
     * If this channel is in non-blocking mode and a datagram is not
     * immediately available then this method immediately returns
     * <tt>null</tt>.
     *
     * <p> The datagram is transferred into the given byte buffer starting at
     * its current position, as if by a regular {@link
     * ReadableByteChannel#read(java.nio.ByteBuffer) read} operation.  If there
     * are fewer bytes remaining in the buffer than are required to hold the
     * datagram then the remainder of the datagram is silently discarded.
     *
     * <p> This method performs exactly the same security checks as the {@link
     * java.net.DatagramSocket#receive receive} method of the {@link
     * java.net.DatagramSocket} class.  That is, if the socket is not connected
     * to a specific remote address and a security manager has been installed
     * then for each datagram received this method verifies that the source's
     * address and port number are permitted by the security manager's {@link
     * java.lang.SecurityManager#checkAccept checkAccept} method.  The overhead
     * of this security check can be avoided by first connecting the socket via
     * the {@link #connect connect} method.
     *
     * <p> This method may be invoked at any time.  If another thread has
     * already initiated a read operation upon this channel, however, then an
     * invocation of this method will block until the first operation is
     * complete. If this channel's socket is not bound then this method will
     * first cause the socket to be bound to an address that is assigned
     * automatically, as if invoking the {@link #bind bind} method with a
     * parameter of {@code null}. </p>
     *
     * <p>
     *  通过此通道接收数据报。
     * 
     *  <p>如果数据报立即可用,或者此通道处于阻塞模式,并且最终变为可用,则将数据报复制到给定的字节缓冲区中,并返回其源地址。
     * 如果此通道处于非阻塞模式,并且数据报不立即可用,则此方法立即返回<tt> null </tt>。
     * 
     *  <p>数据报从其当前位置开始传送到给定的字节缓冲区,如同通过常规的{@link ReadableByteChannel#read(java.nio.ByteBuffer)read}操作。
     * 如果在缓冲器中剩余的字节少于保持数据报所需的字节,则数据报的剩余部分被静默地丢弃。
     * 
     * <p>此方法执行与{@link java.net.DatagramSocket}类的{@link java.net.DatagramSocket#receive receive}方法完全相同的安全检查。
     * 也就是说,如果套接字未连接到特定的远程地址,并且安装了安全管理器,那么对于接收的每个数据报,此方法将验证源地址和端口号是否被安全管理器的{@link java.lang.SecurityManager #checkAccept checkAccept}
     * 方法。
     * 可以通过首先通过{@link #connect connect}方法连接套接字来避免此安全检查的开销。
     * 
     *  <p>此方法可能随时被调用。如果另一个线程已经在该通道上启动了读取操作,则该方法的调用将阻塞,直到第一操作完成。
     * 如果这个通道的套接字没有绑定,那么这个方法将首先导致套接字绑定到一个自动分配的地址,就好像使用参数{@code null}调用{@link #bind bind}方法。 </p>。
     * 
     * 
     * @param  dst
     *         The buffer into which the datagram is to be transferred
     *
     * @return  The datagram's source address,
     *          or <tt>null</tt> if this channel is in non-blocking mode
     *          and no datagram was immediately available
     *
     * @throws  ClosedChannelException
     *          If this channel is closed
     *
     * @throws  AsynchronousCloseException
     *          If another thread closes this channel
     *          while the read operation is in progress
     *
     * @throws  ClosedByInterruptException
     *          If another thread interrupts the current thread
     *          while the read operation is in progress, thereby
     *          closing the channel and setting the current thread's
     *          interrupt status
     *
     * @throws  SecurityException
     *          If a security manager has been installed
     *          and it does not permit datagrams to be accepted
     *          from the datagram's sender
     *
     * @throws  IOException
     *          If some other I/O error occurs
     */
    public abstract SocketAddress receive(ByteBuffer dst) throws IOException;

    /**
     * Sends a datagram via this channel.
     *
     * <p> If this channel is in non-blocking mode and there is sufficient room
     * in the underlying output buffer, or if this channel is in blocking mode
     * and sufficient room becomes available, then the remaining bytes in the
     * given buffer are transmitted as a single datagram to the given target
     * address.
     *
     * <p> The datagram is transferred from the byte buffer as if by a regular
     * {@link WritableByteChannel#write(java.nio.ByteBuffer) write} operation.
     *
     * <p> This method performs exactly the same security checks as the {@link
     * java.net.DatagramSocket#send send} method of the {@link
     * java.net.DatagramSocket} class.  That is, if the socket is not connected
     * to a specific remote address and a security manager has been installed
     * then for each datagram sent this method verifies that the target address
     * and port number are permitted by the security manager's {@link
     * java.lang.SecurityManager#checkConnect checkConnect} method.  The
     * overhead of this security check can be avoided by first connecting the
     * socket via the {@link #connect connect} method.
     *
     * <p> This method may be invoked at any time.  If another thread has
     * already initiated a write operation upon this channel, however, then an
     * invocation of this method will block until the first operation is
     * complete. If this channel's socket is not bound then this method will
     * first cause the socket to be bound to an address that is assigned
     * automatically, as if by invoking the {@link #bind bind} method with a
     * parameter of {@code null}. </p>
     *
     * <p>
     *  通过此通道发送数据报。
     * 
     *  <p>如果此通道处于非阻塞模式,并且在底层输出缓冲区中有足够的空间,或者如果此通道处于阻塞模式并且有足够的空间可用,则给定缓冲区中的剩余字节将作为单个数据报到给定的目标地址。
     * 
     * <p>数据报从字节缓冲区传送,就像是通过常规的{@link WritableByteChannel#write(java.nio.ByteBuffer)write}操作。
     * 
     *  <p>此方法执行与{@link java.net.DatagramSocket}类的{@link java.net.DatagramSocket#send send}方法完全相同的安全检查。
     * 也就是说,如果套接字没有连接到特定的远程地址,并且已经安装了安全管理器,那么对于发送的每个数据报,此方法将验证目标地址和端口号是否被安全管理器的{@link java.lang.SecurityManager #checkConnect checkConnect}
     * 方法。
     *  <p>此方法执行与{@link java.net.DatagramSocket}类的{@link java.net.DatagramSocket#send send}方法完全相同的安全检查。
     * 可以通过首先通过{@link #connect connect}方法连接套接字来避免此安全检查的开销。
     * 
     *  <p>此方法可能随时被调用。如果另一个线程已经在该通道上启动了写操作,则该方法的调用将被阻塞,直到第一操作完成。
     * 如果这个通道的套接字没有绑定,那么这个方法将首先导致套接字被绑定到一个自动分配的地址,就像调用{@link #bind bind}方法使用参数{@code null}。 </p>。
     * 
     * 
     * @param  src
     *         The buffer containing the datagram to be sent
     *
     * @param  target
     *         The address to which the datagram is to be sent
     *
     * @return   The number of bytes sent, which will be either the number
     *           of bytes that were remaining in the source buffer when this
     *           method was invoked or, if this channel is non-blocking, may be
     *           zero if there was insufficient room for the datagram in the
     *           underlying output buffer
     *
     * @throws  ClosedChannelException
     *          If this channel is closed
     *
     * @throws  AsynchronousCloseException
     *          If another thread closes this channel
     *          while the read operation is in progress
     *
     * @throws  ClosedByInterruptException
     *          If another thread interrupts the current thread
     *          while the read operation is in progress, thereby
     *          closing the channel and setting the current thread's
     *          interrupt status
     *
     * @throws  SecurityException
     *          If a security manager has been installed
     *          and it does not permit datagrams to be sent
     *          to the given address
     *
     * @throws  IOException
     *          If some other I/O error occurs
     */
    public abstract int send(ByteBuffer src, SocketAddress target)
        throws IOException;


    // -- ByteChannel operations --

    /**
     * Reads a datagram from this channel.
     *
     * <p> This method may only be invoked if this channel's socket is
     * connected, and it only accepts datagrams from the socket's peer.  If
     * there are more bytes in the datagram than remain in the given buffer
     * then the remainder of the datagram is silently discarded.  Otherwise
     * this method behaves exactly as specified in the {@link
     * ReadableByteChannel} interface.  </p>
     *
     * <p>
     *  从此通道读取数据报。
     * 
     * <p>此方法只能在此通道的套接字连接时调用,并且只接受来自套接字对等体的数据报。如果数据报中有更多的字节而不是保留在给定的缓冲器中,则数据报的剩余部分被静默地丢弃。
     * 否则,此方法的行为与{@link ReadableByteChannel}接口中指定的完全相同。 </p>。
     * 
     * 
     * @throws  NotYetConnectedException
     *          If this channel's socket is not connected
     */
    public abstract int read(ByteBuffer dst) throws IOException;

    /**
     * Reads a datagram from this channel.
     *
     * <p> This method may only be invoked if this channel's socket is
     * connected, and it only accepts datagrams from the socket's peer.  If
     * there are more bytes in the datagram than remain in the given buffers
     * then the remainder of the datagram is silently discarded.  Otherwise
     * this method behaves exactly as specified in the {@link
     * ScatteringByteChannel} interface.  </p>
     *
     * <p>
     *  从此通道读取数据报。
     * 
     *  <p>此方法只能在此通道的套接字连接时调用,并且只接受来自套接字对等体的数据报。如果数据报中有更多字节而不是保留在给定的缓冲器中,则数据报的剩余部分被静默地丢弃。
     * 否则,此方法的行为与{@link ScatteringByteChannel}接口中指定的完全相同。 </p>。
     * 
     * 
     * @throws  NotYetConnectedException
     *          If this channel's socket is not connected
     */
    public abstract long read(ByteBuffer[] dsts, int offset, int length)
        throws IOException;

    /**
     * Reads a datagram from this channel.
     *
     * <p> This method may only be invoked if this channel's socket is
     * connected, and it only accepts datagrams from the socket's peer.  If
     * there are more bytes in the datagram than remain in the given buffers
     * then the remainder of the datagram is silently discarded.  Otherwise
     * this method behaves exactly as specified in the {@link
     * ScatteringByteChannel} interface.  </p>
     *
     * <p>
     *  从此通道读取数据报。
     * 
     *  <p>此方法只能在此通道的套接字连接时调用,并且只接受来自套接字对等体的数据报。如果数据报中有更多字节而不是保留在给定的缓冲器中,则数据报的剩余部分被静默地丢弃。
     * 否则,此方法的行为与{@link ScatteringByteChannel}接口中指定的完全相同。 </p>。
     * 
     * 
     * @throws  NotYetConnectedException
     *          If this channel's socket is not connected
     */
    public final long read(ByteBuffer[] dsts) throws IOException {
        return read(dsts, 0, dsts.length);
    }

    /**
     * Writes a datagram to this channel.
     *
     * <p> This method may only be invoked if this channel's socket is
     * connected, in which case it sends datagrams directly to the socket's
     * peer.  Otherwise it behaves exactly as specified in the {@link
     * WritableByteChannel} interface.  </p>
     *
     * <p>
     *  将数据报写入此通道。
     * 
     *  <p>此方法只能在此通道的套接字连接时调用,在这种情况下,它会将数据报直接发送到套接字的对等端。否则,它的行为与{@link WritableByteChannel}接口中指定的完全相同。
     *  </p>。
     * 
     * 
     * @throws  NotYetConnectedException
     *          If this channel's socket is not connected
     */
    public abstract int write(ByteBuffer src) throws IOException;

    /**
     * Writes a datagram to this channel.
     *
     * <p> This method may only be invoked if this channel's socket is
     * connected, in which case it sends datagrams directly to the socket's
     * peer.  Otherwise it behaves exactly as specified in the {@link
     * GatheringByteChannel} interface.  </p>
     *
     * <p>
     *  将数据报写入此通道。
     * 
     * <p>此方法只能在此通道的套接字连接时调用,在这种情况下,它会将数据报直接发送到套接字的对等端。否则,它的行为与{@link GatheringByteChannel}接口中指定的完全相同。
     *  </p>。
     * 
     * 
     * @return   The number of bytes sent, which will be either the number
     *           of bytes that were remaining in the source buffer when this
     *           method was invoked or, if this channel is non-blocking, may be
     *           zero if there was insufficient room for the datagram in the
     *           underlying output buffer
     *
     * @throws  NotYetConnectedException
     *          If this channel's socket is not connected
     */
    public abstract long write(ByteBuffer[] srcs, int offset, int length)
        throws IOException;

    /**
     * Writes a datagram to this channel.
     *
     * <p> This method may only be invoked if this channel's socket is
     * connected, in which case it sends datagrams directly to the socket's
     * peer.  Otherwise it behaves exactly as specified in the {@link
     * GatheringByteChannel} interface.  </p>
     *
     * <p>
     *  将数据报写入此通道。
     * 
     *  <p>此方法只能在此通道的套接字连接时调用,在这种情况下,它会将数据报直接发送到套接字的对等端。否则,它的行为与{@link GatheringByteChannel}接口中指定的完全相同。
     *  </p>。
     * 
     * 
     * @return   The number of bytes sent, which will be either the number
     *           of bytes that were remaining in the source buffer when this
     *           method was invoked or, if this channel is non-blocking, may be
     *           zero if there was insufficient room for the datagram in the
     *           underlying output buffer
     *
     * @throws  NotYetConnectedException
     *          If this channel's socket is not connected
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
