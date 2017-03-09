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

package java.net;

import java.lang.annotation.Native;

/**
 * Interface of methods to get/set socket options.  This interface is
 * implemented by: <B>SocketImpl</B> and  <B>DatagramSocketImpl</B>.
 * Subclasses of these should override the methods
 * of this interface in order to support their own options.
 * <P>
 * The methods and constants which specify options in this interface are
 * for implementation only.  If you're not subclassing SocketImpl or
 * DatagramSocketImpl, <B>you won't use these directly.</B> There are
 * type-safe methods to get/set each of these options in Socket, ServerSocket,
 * DatagramSocket and MulticastSocket.
 * <P>
 * <p>
 *  获取/设置套接字选项的方法接口。此接口由以下实现：<B> SocketImpl </B>和<B> DatagramSocketImpl </B>。这些子类应该覆盖此接口的方法,以支持自己的选项。
 * <P>
 *  在此接口中指定选项的方法和常量仅用于实现。如果你不是继承SocketImpl或DatagramSocketImpl,<B>你不会直接使用这些。
 * </B>有Sockets,ServerSocket,DatagramSocket和MulticastSocket中的每个选项的类型安全的方法。
 * <P>
 * 
 * @author David Brown
 */


public interface SocketOptions {

    /**
     * Enable/disable the option specified by <I>optID</I>.  If the option
     * is to be enabled, and it takes an option-specific "value",  this is
     * passed in <I>value</I>.  The actual type of value is option-specific,
     * and it is an error to pass something that isn't of the expected type:
     * <BR><PRE>
     * SocketImpl s;
     * ...
     * s.setOption(SO_LINGER, new Integer(10));
     *    // OK - set SO_LINGER w/ timeout of 10 sec.
     * s.setOption(SO_LINGER, new Double(10));
     *    // ERROR - expects java.lang.Integer
     *</PRE>
     * If the requested option is binary, it can be set using this method by
     * a java.lang.Boolean:
     * <BR><PRE>
     * s.setOption(TCP_NODELAY, new Boolean(true));
     *    // OK - enables TCP_NODELAY, a binary option
     * </PRE>
     * <BR>
     * Any option can be disabled using this method with a Boolean(false):
     * <BR><PRE>
     * s.setOption(TCP_NODELAY, new Boolean(false));
     *    // OK - disables TCP_NODELAY
     * s.setOption(SO_LINGER, new Boolean(false));
     *    // OK - disables SO_LINGER
     * </PRE>
     * <BR>
     * For an option that has a notion of on and off, and requires
     * a non-boolean parameter, setting its value to anything other than
     * <I>Boolean(false)</I> implicitly enables it.
     * <BR>
     * Throws SocketException if the option is unrecognized,
     * the socket is closed, or some low-level error occurred
     * <BR>
     * <p>
     *  启用/禁用由<I> optID </I>指定的选项。如果要启用该选项,并且它采用特定于选项的"值",则会在<I>值</I>中传递。
     * 值的实际类型是特定于选项的,传递不是预期类型的​​内容是一个错误：<BR> <PRE> SocketImpl s; ... s.setOption(SO_LINGER,new Integer(10));
     *  // OK  -  set SO_LINGER w / timeout of 10 sec。
     *  启用/禁用由<I> optID </I>指定的选项。如果要启用该选项,并且它采用特定于选项的"值",则会在<I>值</I>中传递。
     *  s.setOption(SO_LINGER,new Double(10)); // ERROR  -  expects java.lang.Integer。
     * /PRE>
     *  如果请求的选项是二进制,可以使用此方法通过java.lang.Boolean设置：<BR> <PRE> s.setOption(TCP_NODELAY,new Boolean(true)); // O
     * K  - 启用TCP_NODELAY,一个二进制选项。
     * </PRE>
     * <BR>
     * 任何选项都可以使用此方法使用布尔(false)禁用：<BR> <PRE> s.setOption(TCP_NODELAY,new Boolean(false)); // OK  - 禁用TCP_NODE
     * LAY s.setOption(SO_LINGER,new Boolean(false)); // OK  - 禁用SO_LINGER。
     * </PRE>
     * <BR>
     *  对于具有开和关概念并且需要非布尔参数的选项,将其值设置为除<I> Boolean(false)</I>之外的任何值都隐含地启用它。
     * <BR>
     *  如果选项无法识别,套接字关闭或发生了一些低级错误,则抛出SocketException
     * <BR>
     * 
     * @param optID identifies the option
     * @param value the parameter of the socket option
     * @throws SocketException if the option is unrecognized,
     * the socket is closed, or some low-level error occurred
     * @see #getOption(int)
     */
    public void
        setOption(int optID, Object value) throws SocketException;

    /**
     * Fetch the value of an option.
     * Binary options will return java.lang.Boolean(true)
     * if enabled, java.lang.Boolean(false) if disabled, e.g.:
     * <BR><PRE>
     * SocketImpl s;
     * ...
     * Boolean noDelay = (Boolean)(s.getOption(TCP_NODELAY));
     * if (noDelay.booleanValue()) {
     *     // true if TCP_NODELAY is enabled...
     * ...
     * }
     * </PRE>
     * <P>
     * For options that take a particular type as a parameter,
     * getOption(int) will return the parameter's value, else
     * it will return java.lang.Boolean(false):
     * <PRE>
     * Object o = s.getOption(SO_LINGER);
     * if (o instanceof Integer) {
     *     System.out.print("Linger time is " + ((Integer)o).intValue());
     * } else {
     *   // the true type of o is java.lang.Boolean(false);
     * }
     * </PRE>
     *
     * <p>
     *  获取选项的值。
     * 二进制选项将返回java.lang.Boolean(true)(如果启用),java.lang.Boolean(false)if disabled,eg .: <BR> <PRE> SocketImpl
     *  s; ... Boolean noDelay =(Boolean)(s.getOption(TCP_NODELAY)); if(noDelay.booleanValue()){//如果TCP_NODELAY被启用,则为true ... ...}
     * 。
     *  获取选项的值。
     * </PRE>
     * <P>
     *  对于采用特定类型作为参数的选项,getOption(int)将返回参数的值,否则将返回java.lang.Boolean(false)：
     * <PRE>
     *  Object o = s.getOption(SO_LINGER); if(o instanceof Integer){System.out.print("Linger time is"+((Integer)o).intValue()); }
     *  else {// o的真实类型是java.lang.Boolean(false); }}。
     * </PRE>
     * 
     * 
     * @param optID an {@code int} identifying the option to fetch
     * @return the value of the option
     * @throws SocketException if the socket is closed
     * @throws SocketException if <I>optID</I> is unknown along the
     *         protocol stack (including the SocketImpl)
     * @see #setOption(int, java.lang.Object)
     */
    public Object getOption(int optID) throws SocketException;

    /**
     * The java-supported BSD-style options.
     * <p>
     *  java支持的BSD样式选项。
     * 
     */

    /**
     * Disable Nagle's algorithm for this connection.  Written data
     * to the network is not buffered pending acknowledgement of
     * previously written data.
     *<P>
     * Valid for TCP only: SocketImpl.
     *
     * <p>
     *  禁用此连接的Nagle算法。到网络的写入数据不被缓冲,等待对先前写入的数据的确认。
     * P>
     *  仅对TCP有效：SocketImpl。
     * 
     * 
     * @see Socket#setTcpNoDelay
     * @see Socket#getTcpNoDelay
     */

    @Native public final static int TCP_NODELAY = 0x0001;

    /**
     * Fetch the local address binding of a socket (this option cannot
     * be "set" only "gotten", since sockets are bound at creation time,
     * and so the locally bound address cannot be changed).  The default local
     * address of a socket is INADDR_ANY, meaning any local address on a
     * multi-homed host.  A multi-homed host can use this option to accept
     * connections to only one of its addresses (in the case of a
     * ServerSocket or DatagramSocket), or to specify its return address
     * to the peer (for a Socket or DatagramSocket).  The parameter of
     * this option is an InetAddress.
     * <P>
     * This option <B>must</B> be specified in the constructor.
     * <P>
     * Valid for: SocketImpl, DatagramSocketImpl
     *
     * <p>
     * 获取套接字的本地地址绑定(此选项不能"设置"只有"gotten",因为套接字在创建时绑定,因此本地绑定的地址不能更改)。套接字的默认本地地址是INADDR_ANY,表示多宿主主机上的任何本地地址。
     * 多宿主主机可以使用此选项接受只与其中一个地址的连接(在ServerSocket或DatagramSocket的情况下),或者指定其对等体的返回地址(对于Socket或DatagramSocket)。
     * 此选项的参数是InetAddress。
     * <P>
     *  此选项<B>必须</B>在构造函数中指定。
     * <P>
     *  适用于：SocketImpl,DatagramSocketImpl
     * 
     * 
     * @see Socket#getLocalAddress
     * @see DatagramSocket#getLocalAddress
     */

    @Native public final static int SO_BINDADDR = 0x000F;

    /** Sets SO_REUSEADDR for a socket.  This is used only for MulticastSockets
     * in java, and it is set by default for MulticastSockets.
     * <P>
     * Valid for: DatagramSocketImpl
     * <p>
     *  在java中,并且默认情况下为MulticastSockets设置。
     * <P>
     *  适用于：DatagramSocketImpl
     * 
     */

    @Native public final static int SO_REUSEADDR = 0x04;

    /**
     * Sets SO_BROADCAST for a socket. This option enables and disables
     * the ability of the process to send broadcast messages. It is supported
     * for only datagram sockets and only on networks that support
     * the concept of a broadcast message (e.g. Ethernet, token ring, etc.),
     * and it is set by default for DatagramSockets.
     * <p>
     *  为套接字设置SO_BROADCAST。此选项启用和禁用进程发送广播消息的能力。
     * 它仅支持数据报套接字,并且仅支持支持广播消息(例如以太网,令牌环等)的概念的网络,并且对于DatagramSockets,它被默认设置。
     * 
     * 
     * @since 1.4
     */

    @Native public final static int SO_BROADCAST = 0x0020;

    /** Set which outgoing interface on which to send multicast packets.
     * Useful on hosts with multiple network interfaces, where applications
     * want to use other than the system default.  Takes/returns an InetAddress.
     * <P>
     * Valid for Multicast: DatagramSocketImpl
     *
     * <p>
     *  在具有多个网络接口的主机上有用,其中应用程序要使用除系统默认值之外的其他网络接口。获取/返回InetAddress。
     * <P>
     *  适用于多播：DatagramSocketImpl
     * 
     * 
     * @see MulticastSocket#setInterface(InetAddress)
     * @see MulticastSocket#getInterface()
     */

    @Native public final static int IP_MULTICAST_IF = 0x10;

    /** Same as above. This option is introduced so that the behaviour
     *  with IP_MULTICAST_IF will be kept the same as before, while
     *  this new option can support setting outgoing interfaces with either
     *  IPv4 and IPv6 addresses.
     *
     *  NOTE: make sure there is no conflict with this
     * <p>
     *  与IP_MULTICAST_IF将保持与以前相同,而此新选项可以支持使用IPv4和IPv6地址设置传出接口。
     * 
     *  注意：确保没有与此冲突
     * 
     * 
     * @see MulticastSocket#setNetworkInterface(NetworkInterface)
     * @see MulticastSocket#getNetworkInterface()
     * @since 1.4
     */
    @Native public final static int IP_MULTICAST_IF2 = 0x1f;

    /**
     * This option enables or disables local loopback of multicast datagrams.
     * This option is enabled by default for Multicast Sockets.
     * <p>
     * 此选项启用或禁用组播数据报的本地环回。默认情况下,此选项为多播套接字启用。
     * 
     * 
     * @since 1.4
     */

    @Native public final static int IP_MULTICAST_LOOP = 0x12;

    /**
     * This option sets the type-of-service or traffic class field
     * in the IP header for a TCP or UDP socket.
     * <p>
     *  此选项设置TCP或UDP套接字的IP头中的服务类型或流量类字段。
     * 
     * 
     * @since 1.4
     */

    @Native public final static int IP_TOS = 0x3;

    /**
     * Specify a linger-on-close timeout.  This option disables/enables
     * immediate return from a <B>close()</B> of a TCP Socket.  Enabling
     * this option with a non-zero Integer <I>timeout</I> means that a
     * <B>close()</B> will block pending the transmission and acknowledgement
     * of all data written to the peer, at which point the socket is closed
     * <I>gracefully</I>.  Upon reaching the linger timeout, the socket is
     * closed <I>forcefully</I>, with a TCP RST. Enabling the option with a
     * timeout of zero does a forceful close immediately. If the specified
     * timeout value exceeds 65,535 it will be reduced to 65,535.
     * <P>
     * Valid only for TCP: SocketImpl
     *
     * <p>
     *  指定关闭超时。此选项禁用/启用从TCP套接字的<B> close()</B>立即返回。
     * 使用非零整数<I>超时</I>启用此选项意味着<B> close()</B>将阻止等待写入对等体的所有数据的传输和确认,此时套接字正常关闭<I> </I>。
     * 在达到停留超时时,套接字用TCP RST强制关闭</I>。启用超时为零的选项将立即强制关闭。如果指定的超时值超过65,535,它将减少到65,535。
     * <P>
     *  仅对TCP：SocketImpl有效
     * 
     * 
     * @see Socket#setSoLinger
     * @see Socket#getSoLinger
     */
    @Native public final static int SO_LINGER = 0x0080;

    /** Set a timeout on blocking Socket operations:
     * <PRE>
     * ServerSocket.accept();
     * SocketInputStream.read();
     * DatagramSocket.receive();
     * </PRE>
     *
     * <P> The option must be set prior to entering a blocking
     * operation to take effect.  If the timeout expires and the
     * operation would continue to block,
     * <B>java.io.InterruptedIOException</B> is raised.  The Socket is
     * not closed in this case.
     *
     * <P> Valid for all sockets: SocketImpl, DatagramSocketImpl
     *
     * <p>
     * <PRE>
     *  ServerSocket.accept(); SocketInputStream.read(); DatagramSocket.receive();
     * </PRE>
     * 
     *  <P>在输入阻止操作生效之前,必须设置该选项。如果超时到期并且操作将继续阻塞,则会引发java.io.InterruptedIOException </B>。在这种情况下套接字不关闭。
     * 
     *  <P>对所有套接字有效：SocketImpl,DatagramSocketImpl
     * 
     * 
     * @see Socket#setSoTimeout
     * @see ServerSocket#setSoTimeout
     * @see DatagramSocket#setSoTimeout
     */
    @Native public final static int SO_TIMEOUT = 0x1006;

    /**
     * Set a hint the size of the underlying buffers used by the
     * platform for outgoing network I/O. When used in set, this is a
     * suggestion to the kernel from the application about the size of
     * buffers to use for the data to be sent over the socket. When
     * used in get, this must return the size of the buffer actually
     * used by the platform when sending out data on this socket.
     *
     * Valid for all sockets: SocketImpl, DatagramSocketImpl
     *
     * <p>
     * 设置提示平台为传出网络I / O使用的底层缓冲区的大小。当在集合中使用时,这是从应用程序到内核的建议,关于用于通过套接字发送的数据的缓冲区的大小。
     * 当在get中使用时,这必须返回在此套接字上发送数据时平台实际使用的缓冲区的大小。
     * 
     *  适用于所有套接字：SocketImpl,DatagramSocketImpl
     * 
     * 
     * @see Socket#setSendBufferSize
     * @see Socket#getSendBufferSize
     * @see DatagramSocket#setSendBufferSize
     * @see DatagramSocket#getSendBufferSize
     */
    @Native public final static int SO_SNDBUF = 0x1001;

    /**
     * Set a hint the size of the underlying buffers used by the
     * platform for incoming network I/O. When used in set, this is a
     * suggestion to the kernel from the application about the size of
     * buffers to use for the data to be received over the
     * socket. When used in get, this must return the size of the
     * buffer actually used by the platform when receiving in data on
     * this socket.
     *
     * Valid for all sockets: SocketImpl, DatagramSocketImpl
     *
     * <p>
     *  设置提示平台为传入网络I / O使用的底层缓冲区的大小。当在集合中使用时,这是从应用程序到内核的建议,关于要用于通过套接字接收的数据的缓冲区的大小。
     * 当在get中使用时,这必须返回平台在接收此套接字上的数据时实际使用的缓冲区的大小。
     * 
     *  适用于所有套接字：SocketImpl,DatagramSocketImpl
     * 
     * 
     * @see Socket#setReceiveBufferSize
     * @see Socket#getReceiveBufferSize
     * @see DatagramSocket#setReceiveBufferSize
     * @see DatagramSocket#getReceiveBufferSize
     */
    @Native public final static int SO_RCVBUF = 0x1002;

    /**
     * When the keepalive option is set for a TCP socket and no data
     * has been exchanged across the socket in either direction for
     * 2 hours (NOTE: the actual value is implementation dependent),
     * TCP automatically sends a keepalive probe to the peer. This probe is a
     * TCP segment to which the peer must respond.
     * One of three responses is expected:
     * 1. The peer responds with the expected ACK. The application is not
     *    notified (since everything is OK). TCP will send another probe
     *    following another 2 hours of inactivity.
     * 2. The peer responds with an RST, which tells the local TCP that
     *    the peer host has crashed and rebooted. The socket is closed.
     * 3. There is no response from the peer. The socket is closed.
     *
     * The purpose of this option is to detect if the peer host crashes.
     *
     * Valid only for TCP socket: SocketImpl
     *
     * <p>
     * 当为TCP套接字设置keepalive选项并且没有数据在任何方向上通过套接字交换2小时(注意：实际值是实现相关的),TCP自动向对等体发送保持连接探测。此探针是对等体必须响应的TCP段。
     * 期望三个响应中的一个：1.对等体以预期的ACK响应。应用程序不会通知(因为一切正常)。 TCP将在另外2小时不活动之后发送另一个探测。
     *  2.对等体用RST响应,该RST通知本地TCP对等主机已经崩溃并重新启动。插座关闭。没有来自对等体的响应。插座关闭。
     * 
     *  此选项的目的是检测对等主机是否崩溃。
     * 
     * 
     * @see Socket#setKeepAlive
     * @see Socket#getKeepAlive
     */
    @Native public final static int SO_KEEPALIVE = 0x0008;

    /**
     * When the OOBINLINE option is set, any TCP urgent data received on
     * the socket will be received through the socket input stream.
     * When the option is disabled (which is the default) urgent data
     * is silently discarded.
     *
     * <p>
     *  仅对TCP套接字有效：SocketImpl
     * 
     * 
     * @see Socket#setOOBInline
     * @see Socket#getOOBInline
     */
    @Native public final static int SO_OOBINLINE = 0x1003;
}
