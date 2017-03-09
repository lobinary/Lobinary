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

package java.net;

/**
 * Defines the <em>standard</em> socket options.
 *
 * <p> The {@link SocketOption#name name} of each socket option defined by this
 * class is its field name.
 *
 * <p> In this release, the socket options defined here are used by {@link
 * java.nio.channels.NetworkChannel network} channels in the {@link
 * java.nio.channels channels} package.
 *
 * <p>
 *  定义<em>标准</em>套接字选项。
 * 
 *  <p>此类定义的每个套接字选项的{@link SocketOption#name name}是其字段名称。
 * 
 *  <p>在此版本中,此处定义的套接字选项由{@link java.nio.channels渠道}包中的{@link java.nio.channels.NetworkChannel network}渠道
 * 使用。
 * 
 * 
 * @since 1.7
 */

public final class StandardSocketOptions {
    private StandardSocketOptions() { }

    // -- SOL_SOCKET --

    /**
     * Allow transmission of broadcast datagrams.
     *
     * <p> The value of this socket option is a {@code Boolean} that represents
     * whether the option is enabled or disabled. The option is specific to
     * datagram-oriented sockets sending to {@link java.net.Inet4Address IPv4}
     * broadcast addresses. When the socket option is enabled then the socket
     * can be used to send <em>broadcast datagrams</em>.
     *
     * <p> The initial value of this socket option is {@code FALSE}. The socket
     * option may be enabled or disabled at any time. Some operating systems may
     * require that the Java virtual machine be started with implementation
     * specific privileges to enable this option or send broadcast datagrams.
     *
     * <p>
     *  允许广播数据报的传输。
     * 
     *  <p>此套接字选项的值是一个{@code Boolean},表示该选项是启用还是禁用。
     * 该选项特定于面向数据报的套接字发送到{@link java.net.Inet4Address IPv4}广播地址。当启用套接字选项时,套接字可用于发送广播数据报。
     * 
     *  <p>此套接字选项的初始值为{@code FALSE}。可以随时启用或禁用套接字选项。某些操作系统可能需要使用实现特定的权限启动Java虚拟机以启用此选项或发送广播数据报。
     * 
     * 
     * @see <a href="http://www.ietf.org/rfc/rfc919.txt">RFC&nbsp;929:
     * Broadcasting Internet Datagrams</a>
     * @see DatagramSocket#setBroadcast
     */
    public static final SocketOption<Boolean> SO_BROADCAST =
        new StdSocketOption<Boolean>("SO_BROADCAST", Boolean.class);

    /**
     * Keep connection alive.
     *
     * <p> The value of this socket option is a {@code Boolean} that represents
     * whether the option is enabled or disabled. When the {@code SO_KEEPALIVE}
     * option is enabled the operating system may use a <em>keep-alive</em>
     * mechanism to periodically probe the other end of a connection when the
     * connection is otherwise idle. The exact semantics of the keep alive
     * mechanism is system dependent and therefore unspecified.
     *
     * <p> The initial value of this socket option is {@code FALSE}. The socket
     * option may be enabled or disabled at any time.
     *
     * <p>
     *  保持连接活动。
     * 
     * <p>此套接字选项的值是一个{@code Boolean},表示该选项是启用还是禁用。
     * 当启用{@code SO_KEEPALIVE}选项时,操作系统可以使用<em> keep-alive </em>机制,在连接空闲时定期探测连接的另一端。保持活动机制的确切语义是系统依赖的,因此未指定。
     * 
     *  <p>此套接字选项的初始值为{@code FALSE}。可以随时启用或禁用套接字选项。
     * 
     * 
     * @see <a href="http://www.ietf.org/rfc/rfc1122.txt">RFC&nbsp;1122
     * Requirements for Internet Hosts -- Communication Layers</a>
     * @see Socket#setKeepAlive
     */
    public static final SocketOption<Boolean> SO_KEEPALIVE =
        new StdSocketOption<Boolean>("SO_KEEPALIVE", Boolean.class);

    /**
     * The size of the socket send buffer.
     *
     * <p> The value of this socket option is an {@code Integer} that is the
     * size of the socket send buffer in bytes. The socket send buffer is an
     * output buffer used by the networking implementation. It may need to be
     * increased for high-volume connections. The value of the socket option is
     * a <em>hint</em> to the implementation to size the buffer and the actual
     * size may differ. The socket option can be queried to retrieve the actual
     * size.
     *
     * <p> For datagram-oriented sockets, the size of the send buffer may limit
     * the size of the datagrams that may be sent by the socket. Whether
     * datagrams larger than the buffer size are sent or discarded is system
     * dependent.
     *
     * <p> The initial/default size of the socket send buffer and the range of
     * allowable values is system dependent although a negative size is not
     * allowed. An attempt to set the socket send buffer to larger than its
     * maximum size causes it to be set to its maximum size.
     *
     * <p> An implementation allows this socket option to be set before the
     * socket is bound or connected. Whether an implementation allows the
     * socket send buffer to be changed after the socket is bound is system
     * dependent.
     *
     * <p>
     *  套接字的大小发送缓冲区。
     * 
     *  <p>此套接字选项的值是{@code Integer},它是套接字发送缓冲区的大小(以字节为单位)。套接字发送缓冲区是网络实现使用的输出缓冲区。可能需要增加大容量连接。
     * 套接字选项的值是对实现的<em>提示</em>来调整缓冲区的大小,实际大小可能不同。可以查询套接字选项以检索实际大小。
     * 
     *  <p>对于面向数据报的套接字,发送缓冲区的大小可能会限制套接字发送的数据报的大小。是否发送或丢弃大于缓冲区大小的数据报取决于系统。
     * 
     *  <p>套接字发送缓冲区的初始/默认大小和允许值的范围是系统相关的,尽管不允许使用负大小。尝试将套接字发送缓冲区设置为大于其最大大小时,会将其设置为其最大大小。
     * 
     * <p>实现允许在套接字绑定或连接之前设置此套接字选项。一个实现是否允许在套接字绑定之后更改套接字发送缓冲区取决于系统。
     * 
     * 
     * @see Socket#setSendBufferSize
     */
    public static final SocketOption<Integer> SO_SNDBUF =
        new StdSocketOption<Integer>("SO_SNDBUF", Integer.class);


    /**
     * The size of the socket receive buffer.
     *
     * <p> The value of this socket option is an {@code Integer} that is the
     * size of the socket receive buffer in bytes. The socket receive buffer is
     * an input buffer used by the networking implementation. It may need to be
     * increased for high-volume connections or decreased to limit the possible
     * backlog of incoming data. The value of the socket option is a
     * <em>hint</em> to the implementation to size the buffer and the actual
     * size may differ.
     *
     * <p> For datagram-oriented sockets, the size of the receive buffer may
     * limit the size of the datagrams that can be received. Whether datagrams
     * larger than the buffer size can be received is system dependent.
     * Increasing the socket receive buffer may be important for cases where
     * datagrams arrive in bursts faster than they can be processed.
     *
     * <p> In the case of stream-oriented sockets and the TCP/IP protocol, the
     * size of the socket receive buffer may be used when advertising the size
     * of the TCP receive window to the remote peer.
     *
     * <p> The initial/default size of the socket receive buffer and the range
     * of allowable values is system dependent although a negative size is not
     * allowed. An attempt to set the socket receive buffer to larger than its
     * maximum size causes it to be set to its maximum size.
     *
     * <p> An implementation allows this socket option to be set before the
     * socket is bound or connected. Whether an implementation allows the
     * socket receive buffer to be changed after the socket is bound is system
     * dependent.
     *
     * <p>
     *  套接字接收缓冲区的大小。
     * 
     *  <p>此套接字选项的值是{@code Integer},它是套接字接收缓冲区的大小(以字节为单位)。套接字接收缓冲区是网络实现使用的输入缓冲区。
     * 它可能需要增加大容量连接或减少以限制可能积压的输入数据。套接字选项的值是对实现的<em>提示</em>来调整缓冲区的大小,实际大小可能不同。
     * 
     *  <p>对于面向数据报的套接字,接收缓冲区的大小可能限制可以接收的数据报的大小。是否可以接收大于缓冲区大小的数据报是系统相关的。
     * 增加套接字接收缓冲区对于数据报以比它们可以被处理的突发更快地到达的情况可能是重要的。
     * 
     *  <p>在面向流的套接字和TCP / IP协议的情况下,当向远程对等方通告TCP接收窗口的大小时,可以使用套接字接收缓冲区的大小。
     * 
     *  <p>套接字接收缓冲区的初始/默认大小和允许值的范围是系统相关的,尽管不允许使用负大小。尝试将套接字接收缓冲区设置为大于其最大大小时,会将其设置为其最大大小。
     * 
     * <p>实现允许在套接字绑定或连接之前设置此套接字选项。一个实现是否允许在套接字绑定之后更改套接字接收缓冲区取决于系统。
     * 
     * 
     * @see <a href="http://www.ietf.org/rfc/rfc1323.txt">RFC&nbsp;1323: TCP
     * Extensions for High Performance</a>
     * @see Socket#setReceiveBufferSize
     * @see ServerSocket#setReceiveBufferSize
     */
    public static final SocketOption<Integer> SO_RCVBUF =
        new StdSocketOption<Integer>("SO_RCVBUF", Integer.class);

    /**
     * Re-use address.
     *
     * <p> The value of this socket option is a {@code Boolean} that represents
     * whether the option is enabled or disabled. The exact semantics of this
     * socket option are socket type and system dependent.
     *
     * <p> In the case of stream-oriented sockets, this socket option will
     * usually determine whether the socket can be bound to a socket address
     * when a previous connection involving that socket address is in the
     * <em>TIME_WAIT</em> state. On implementations where the semantics differ,
     * and the socket option is not required to be enabled in order to bind the
     * socket when a previous connection is in this state, then the
     * implementation may choose to ignore this option.
     *
     * <p> For datagram-oriented sockets the socket option is used to allow
     * multiple programs bind to the same address. This option should be enabled
     * when the socket is to be used for Internet Protocol (IP) multicasting.
     *
     * <p> An implementation allows this socket option to be set before the
     * socket is bound or connected. Changing the value of this socket option
     * after the socket is bound has no effect. The default value of this
     * socket option is system dependent.
     *
     * <p>
     *  重复使用地址。
     * 
     *  <p>此套接字选项的值是一个{@code Boolean},表示该选项是启用还是禁用。这个套接字选项的确切语义是套接字类型和系统依赖。
     * 
     *  <p>在面向流的套接字的情况下,当涉及套接字地址的先前连接处于TIME_WAIT </em>状态时,该套接字选项通常将确定套接字是否可以绑定到套接字地址。
     * 在语义不同的实现上,并且当前一个连接处于此状态时,不需要启用套接字选项以便绑定套接字,则实现可以选择忽略此选项。
     * 
     *  <p>对于面向数据报的套接字,socket选项用于允许多个程序绑定到同一个地址。当套接字用于互联网协议(IP)多播时,应启用此选项。
     * 
     *  <p>实现允许在套接字绑定或连接之前设置此套接字选项。在套接字绑定后更改此套接字选项的值没有任何效果。此套接字选项的默认值是系统相关的。
     * 
     * 
     * @see <a href="http://www.ietf.org/rfc/rfc793.txt">RFC&nbsp;793: Transmission
     * Control Protocol</a>
     * @see ServerSocket#setReuseAddress
     */
    public static final SocketOption<Boolean> SO_REUSEADDR =
        new StdSocketOption<Boolean>("SO_REUSEADDR", Boolean.class);

    /**
     * Linger on close if data is present.
     *
     * <p> The value of this socket option is an {@code Integer} that controls
     * the action taken when unsent data is queued on the socket and a method
     * to close the socket is invoked. If the value of the socket option is zero
     * or greater, then it represents a timeout value, in seconds, known as the
     * <em>linger interval</em>. The linger interval is the timeout for the
     * {@code close} method to block while the operating system attempts to
     * transmit the unsent data or it decides that it is unable to transmit the
     * data. If the value of the socket option is less than zero then the option
     * is disabled. In that case the {@code close} method does not wait until
     * unsent data is transmitted; if possible the operating system will transmit
     * any unsent data before the connection is closed.
     *
     * <p> This socket option is intended for use with sockets that are configured
     * in {@link java.nio.channels.SelectableChannel#isBlocking() blocking} mode
     * only. The behavior of the {@code close} method when this option is
     * enabled on a non-blocking socket is not defined.
     *
     * <p> The initial value of this socket option is a negative value, meaning
     * that the option is disabled. The option may be enabled, or the linger
     * interval changed, at any time. The maximum value of the linger interval
     * is system dependent. Setting the linger interval to a value that is
     * greater than its maximum value causes the linger interval to be set to
     * its maximum value.
     *
     * <p>
     *  如果数据存在,则关闭。
     * 
     * <p>此套接字选项的值是一个{@code Integer},用于控制未发送的数据在套接字上排队时采取的操作,以及调用关闭套接字的方法。
     * 如果套接字选项的值为零或更大,则它表示超时值(以秒为单位,称为<l> linger interval </em>)。
     * 逗留间隔是{@code close}方法在操作系统尝试传输未发送的数据或者它决定不能发送数据时阻塞的超时。如果套接字选项的值小于零,则禁用该选项。
     * 在这种情况下,{@code close}方法不会等待未发送的数据被发送;如果可能,操作系统将在连接关闭之前传输任何未发送的数据。
     * 
     *  <p>此套接字选项仅适用于在{@link java.nio.channels.SelectableChannel#isBlocking()blocking}模式中配置的套接字。
     * 未定义在非阻塞套接字上启用此选项时,{@code close}方法的行为。
     * 
     *  <p>此套接字选项的初始值为负值,表示该选项已禁用。可以在任何时候启用该选项,或者更改停顿间隔。延迟间隔的最大值是系统相关的。将延音间隔设置为大于其最大值的值会导致将延音间隔设置为其最大值。
     * 
     * 
     * @see Socket#setSoLinger
     */
    public static final SocketOption<Integer> SO_LINGER =
        new StdSocketOption<Integer>("SO_LINGER", Integer.class);


    // -- IPPROTO_IP --

    /**
     * The Type of Service (ToS) octet in the Internet Protocol (IP) header.
     *
     * <p> The value of this socket option is an {@code Integer} representing
     * the value of the ToS octet in IP packets sent by sockets to an {@link
     * StandardProtocolFamily#INET IPv4} socket. The interpretation of the ToS
     * octet is network specific and is not defined by this class. Further
     * information on the ToS octet can be found in <a
     * href="http://www.ietf.org/rfc/rfc1349.txt">RFC&nbsp;1349</a> and <a
     * href="http://www.ietf.org/rfc/rfc2474.txt">RFC&nbsp;2474</a>. The value
     * of the socket option is a <em>hint</em>. An implementation may ignore the
     * value, or ignore specific values.
     *
     * <p> The initial/default value of the TOS field in the ToS octet is
     * implementation specific but will typically be {@code 0}. For
     * datagram-oriented sockets the option may be configured at any time after
     * the socket has been bound. The new value of the octet is used when sending
     * subsequent datagrams. It is system dependent whether this option can be
     * queried or changed prior to binding the socket.
     *
     * <p> The behavior of this socket option on a stream-oriented socket, or an
     * {@link StandardProtocolFamily#INET6 IPv6} socket, is not defined in this
     * release.
     *
     * <p>
     *  Internet协议(IP)头中的服务类型(ToS)八位字节。
     * 
     * <p>此套接字选项的值是{@code Integer},表示套接字发送到{@link StandardProtocolFamily#INET IPv4}套接字的IP数据包中的ToS八位位组的值。
     *  ToS字节的解释是网络特定的,并且不由该类定义。
     * 有关ToS八位字节的详细信息,请参见<a href="http://www.ietf.org/rfc/rfc1349.txt"> RFC 1349 </a>和<a href ="http：// www .ietf.org / rfc / rfc2474.txt">
     *  RFC&nbsp; 2474 </a>。
     *  ToS字节的解释是网络特定的,并且不由该类定义。套接字选项的值是一个<em>提示</em>。实现可以忽略该值,或忽略特定值。
     * 
     *  <p> ToS八位字节中TOS字段的初始值/默认值是特定于实现的,但通常为{@code 0}。对于面向数据报的套接字,可以在套接字绑定后的任何时间配置选项。当发送后续数据报时,使用八位字节的新值。
     * 在绑定套接字之前,是否可以查询或更改此选项是系统相关的。
     * 
     *  <p>此版本中没有定义流式接口套接字或{@link StandardProtocolFamily#INET6 IPv6}套接字上此套接字选项的行为。
     * 
     * 
     * @see DatagramSocket#setTrafficClass
     */
    public static final SocketOption<Integer> IP_TOS =
        new StdSocketOption<Integer>("IP_TOS", Integer.class);

    /**
     * The network interface for Internet Protocol (IP) multicast datagrams.
     *
     * <p> The value of this socket option is a {@link NetworkInterface} that
     * represents the outgoing interface for multicast datagrams sent by the
     * datagram-oriented socket. For {@link StandardProtocolFamily#INET6 IPv6}
     * sockets then it is system dependent whether setting this option also
     * sets the outgoing interface for multicast datagrams sent to IPv4
     * addresses.
     *
     * <p> The initial/default value of this socket option may be {@code null}
     * to indicate that outgoing interface will be selected by the operating
     * system, typically based on the network routing tables. An implementation
     * allows this socket option to be set after the socket is bound. Whether
     * the socket option can be queried or changed prior to binding the socket
     * is system dependent.
     *
     * <p>
     *  用于互联网协议(IP)组播数据报的网络接口。
     * 
     * <p>此套接字选项的值为{@link NetworkInterface},表示面向数据报的套接字发送的组播数据报的出接口。
     * 对于{@link StandardProtocolFamily#INET6 IPv6}套接字,则系统依赖于是否设置此选项也设置发送到IPv4地址的组播数据报的出接口。
     * 
     *  <p>此套接字选项的初始值/默认值可以是{@code null},以指示出站接口将由操作系统选择,通常基于网络路由表。实现允许在套接字绑定后设置此套接字选项。
     * 在绑定套接字之前是否可以查询或更改套接字选项是系统相关的。
     * 
     * 
     * @see java.nio.channels.MulticastChannel
     * @see MulticastSocket#setInterface
     */
    public static final SocketOption<NetworkInterface> IP_MULTICAST_IF =
        new StdSocketOption<NetworkInterface>("IP_MULTICAST_IF", NetworkInterface.class);

    /**
     * The <em>time-to-live</em> for Internet Protocol (IP) multicast datagrams.
     *
     * <p> The value of this socket option is an {@code Integer} in the range
     * {@code 0 <= value <= 255}. It is used to control the scope of multicast
     * datagrams sent by the datagram-oriented socket.
     * In the case of an {@link StandardProtocolFamily#INET IPv4} socket
     * the option is the time-to-live (TTL) on multicast datagrams sent by the
     * socket. Datagrams with a TTL of zero are not transmitted on the network
     * but may be delivered locally. In the case of an {@link
     * StandardProtocolFamily#INET6 IPv6} socket the option is the
     * <em>hop limit</em> which is number of <em>hops</em> that the datagram can
     * pass through before expiring on the network. For IPv6 sockets it is
     * system dependent whether the option also sets the <em>time-to-live</em>
     * on multicast datagrams sent to IPv4 addresses.
     *
     * <p> The initial/default value of the time-to-live setting is typically
     * {@code 1}. An implementation allows this socket option to be set after
     * the socket is bound. Whether the socket option can be queried or changed
     * prior to binding the socket is system dependent.
     *
     * <p>
     *  互联网协议(IP)组播数据报的<em>生存时间</em>。
     * 
     * <p>此套接字选项的值为{@code Integer},范围为{@code 0 <= value <= 255}。它用于控制由面向数据报的套接字发送的组播数据报的范围。
     * 在{@link StandardProtocolFamily#INET IPv4}套接字的情况下,该选项是套接字发送的组播数据报的生存时间(TTL)。
     *  TTL为零的数据报不在网络上传输,而是可以在本地传送。
     * 在{@link StandardProtocolFamily#INET6 IPv6}套接字的情况下,选项是在数据报在网络上过期之前可以经过的<hop>跳数</em>的<em>跳数限制</em> 。
     * 对于IPv6套接字,系统取决于该选项是否也设置发送到IPv4地址的组播数据报上的生存时间。
     * 
     *  <p>生存时间设置的初始值/默认值通常为{@code 1}。实现允许在套接字绑定后设置此套接字选项。在绑定套接字之前是否可以查询或更改套接字选项是系统相关的。
     * 
     * 
     * @see java.nio.channels.MulticastChannel
     * @see MulticastSocket#setTimeToLive
     */
    public static final SocketOption<Integer> IP_MULTICAST_TTL =
        new StdSocketOption<Integer>("IP_MULTICAST_TTL", Integer.class);

    /**
     * Loopback for Internet Protocol (IP) multicast datagrams.
     *
     * <p> The value of this socket option is a {@code Boolean} that controls
     * the <em>loopback</em> of multicast datagrams. The value of the socket
     * option represents if the option is enabled or disabled.
     *
     * <p> The exact semantics of this socket options are system dependent.
     * In particular, it is system dependent whether the loopback applies to
     * multicast datagrams sent from the socket or received by the socket.
     * For {@link StandardProtocolFamily#INET6 IPv6} sockets then it is
     * system dependent whether the option also applies to multicast datagrams
     * sent to IPv4 addresses.
     *
     * <p> The initial/default value of this socket option is {@code TRUE}. An
     * implementation allows this socket option to be set after the socket is
     * bound. Whether the socket option can be queried or changed prior to
     * binding the socket is system dependent.
     *
     * <p>
     *  互联网协议(IP)组播数据报的环回。
     * 
     *  <p>此套接字选项的值是一个{@code Boolean},用于控制组播数据报的<em> loopback </em>。套接字选项的值表示该选项是启用还是禁用。
     * 
     * <p>这个套接字选项的确切语义是系统依赖的。特别地,系统依赖于环回是否适用于从套接字发送或由套接字接收的多播数据报。
     * 对于{@link StandardProtocolFamily#INET6 IPv6}套接字,系统依赖于该选项是否也适用于发送到IPv4地址的多播数据报。
     * 
     *  <p>此套接字选项的初始值/默认值为{@code TRUE}。实现允许在套接字绑定后设置此套接字选项。在绑定套接字之前是否可以查询或更改套接字选项是系统相关的。
     * 
     * 
     * @see java.nio.channels.MulticastChannel
     *  @see MulticastSocket#setLoopbackMode
     */
    public static final SocketOption<Boolean> IP_MULTICAST_LOOP =
        new StdSocketOption<Boolean>("IP_MULTICAST_LOOP", Boolean.class);


    // -- IPPROTO_TCP --

    /**
     * Disable the Nagle algorithm.
     *
     * <p> The value of this socket option is a {@code Boolean} that represents
     * whether the option is enabled or disabled. The socket option is specific to
     * stream-oriented sockets using the TCP/IP protocol. TCP/IP uses an algorithm
     * known as <em>The Nagle Algorithm</em> to coalesce short segments and
     * improve network efficiency.
     *
     * <p> The default value of this socket option is {@code FALSE}. The
     * socket option should only be enabled in cases where it is known that the
     * coalescing impacts performance. The socket option may be enabled at any
     * time. In other words, the Nagle Algorithm can be disabled. Once the option
     * is enabled, it is system dependent whether it can be subsequently
     * disabled. If it cannot, then invoking the {@code setOption} method to
     * disable the option has no effect.
     *
     * <p>
     * 
     * @see <a href="http://www.ietf.org/rfc/rfc1122.txt">RFC&nbsp;1122:
     * Requirements for Internet Hosts -- Communication Layers</a>
     * @see Socket#setTcpNoDelay
     */
    public static final SocketOption<Boolean> TCP_NODELAY =
        new StdSocketOption<Boolean>("TCP_NODELAY", Boolean.class);


    private static class StdSocketOption<T> implements SocketOption<T> {
        private final String name;
        private final Class<T> type;
        StdSocketOption(String name, Class<T> type) {
            this.name = name;
            this.type = type;
        }
        @Override public String name() { return name; }
        @Override public Class<T> type() { return type; }
        @Override public String toString() { return name; }
    }
}
