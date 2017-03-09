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

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.io.IOException;
import java.net.ProtocolFamily;             // javadoc
import java.net.StandardProtocolFamily;     // javadoc
import java.net.StandardSocketOptions;      // javadoc

/**
 * A network channel that supports Internet Protocol (IP) multicasting.
 *
 * <p> IP multicasting is the transmission of IP datagrams to members of
 * a <em>group</em> that is zero or more hosts identified by a single destination
 * address.
 *
 * <p> In the case of a channel to an {@link StandardProtocolFamily#INET IPv4} socket,
 * the underlying operating system supports <a href="http://www.ietf.org/rfc/rfc2236.txt">
 * <i>RFC&nbsp;2236: Internet Group Management Protocol, Version 2 (IGMPv2)</i></a>.
 * It may optionally support source filtering as specified by <a
 * href="http://www.ietf.org/rfc/rfc3376.txt"> <i>RFC&nbsp;3376: Internet Group
 * Management Protocol, Version 3 (IGMPv3)</i></a>.
 * For channels to an {@link StandardProtocolFamily#INET6 IPv6} socket, the equivalent
 * standards are <a href="http://www.ietf.org/rfc/rfc2710.txt"> <i>RFC&nbsp;2710:
 * Multicast Listener Discovery (MLD) for IPv6</i></a> and <a
 * href="http://www.ietf.org/rfc/rfc3810.txt"> <i>RFC&nbsp;3810: Multicast Listener
 * Discovery Version 2 (MLDv2) for IPv6</i></a>.
 *
 * <p> The {@link #join(InetAddress,NetworkInterface)} method is used to
 * join a group and receive all multicast datagrams sent to the group. A channel
 * may join several multicast groups and may join the same group on several
 * {@link NetworkInterface interfaces}. Membership is dropped by invoking the {@link
 * MembershipKey#drop drop} method on the returned {@link MembershipKey}. If the
 * underlying platform supports source filtering then the {@link MembershipKey#block
 * block} and {@link MembershipKey#unblock unblock} methods can be used to block or
 * unblock multicast datagrams from particular source addresses.
 *
 * <p> The {@link #join(InetAddress,NetworkInterface,InetAddress)} method
 * is used to begin receiving datagrams sent to a group whose source address matches
 * a given source address. This method throws {@link UnsupportedOperationException}
 * if the underlying platform does not support source filtering.  Membership is
 * <em>cumulative</em> and this method may be invoked again with the same group
 * and interface to allow receiving datagrams from other source addresses. The
 * method returns a {@link MembershipKey} that represents membership to receive
 * datagrams from the given source address. Invoking the key's {@link
 * MembershipKey#drop drop} method drops membership so that datagrams from the
 * source address can no longer be received.
 *
 * <h2>Platform dependencies</h2>
 *
 * The multicast implementation is intended to map directly to the native
 * multicasting facility. Consequently, the following items should be considered
 * when developing an application that receives IP multicast datagrams:
 *
 * <ol>
 *
 * <li><p> The creation of the channel should specify the {@link ProtocolFamily}
 * that corresponds to the address type of the multicast groups that the channel
 * will join. There is no guarantee that a channel to a socket in one protocol
 * family can join and receive multicast datagrams when the address of the
 * multicast group corresponds to another protocol family. For example, it is
 * implementation specific if a channel to an {@link StandardProtocolFamily#INET6 IPv6}
 * socket can join an {@link StandardProtocolFamily#INET IPv4} multicast group and receive
 * multicast datagrams sent to the group. </p></li>
 *
 * <li><p> The channel's socket should be bound to the {@link
 * InetAddress#isAnyLocalAddress wildcard} address. If the socket is bound to
 * a specific address, rather than the wildcard address then it is implementation
 * specific if multicast datagrams are received by the socket. </p></li>
 *
 * <li><p> The {@link StandardSocketOptions#SO_REUSEADDR SO_REUSEADDR} option should be
 * enabled prior to {@link NetworkChannel#bind binding} the socket. This is
 * required to allow multiple members of the group to bind to the same
 * address. </p></li>
 *
 * </ol>
 *
 * <p> <b>Usage Example:</b>
 * <pre>
 *     // join multicast group on this interface, and also use this
 *     // interface for outgoing multicast datagrams
 *     NetworkInterface ni = NetworkInterface.getByName("hme0");
 *
 *     DatagramChannel dc = DatagramChannel.open(StandardProtocolFamily.INET)
 *         .setOption(StandardSocketOptions.SO_REUSEADDR, true)
 *         .bind(new InetSocketAddress(5000))
 *         .setOption(StandardSocketOptions.IP_MULTICAST_IF, ni);
 *
 *     InetAddress group = InetAddress.getByName("225.4.5.6");
 *
 *     MembershipKey key = dc.join(group, ni);
 * </pre>
 *
 * <p>
 *  支持Internet协议(IP)多播的网络通道。
 * 
 *  <p> IP多播是将IP数据报传输到由单个目的地地址标识的零个或多个主机的<em>组</em>成员。
 * 
 *  <p>如果是{@link StandardProtocolFamily#INET IPv4}套接字的频道,底层操作系统支持<a href="http://www.ietf.org/rfc/rfc2236.txt">
 *  <i > RFC 2236：Internet组管理协议第2版(IGMPv2)</i> </a>。
 * 它可以选择性地支持<a href="http://www.ietf.org/rfc/rfc3376.txt"> <i> RFC 3376：Internet组管理协议第3版(IGMPv3)</i> </a>
 * 。
 * 对于{@link StandardProtocolFamily#INET6 IPv6}套接字的频道,等效标准为<a href="http://www.ietf.org/rfc/rfc2710.txt">
 *  <i> RFC 2710：多播侦听器发现(MLD)for IPv6 </i> </a>和<a href="http://www.ietf.org/rfc/rfc3810.txt"> <i> RFC 3
 * 810：Multicast Listener Discovery Version 2(MLDv2 )for IPv6 </i> </a>。
 * 
 * <p> {@link #join(InetAddress,NetworkInterface)}方法用于加入组并接收发送到组的所有组播数据报。
 * 通道可以加入多个多播组,并且可以在几个{@link NetworkInterface接口}上加入同一组。
 * 通过在返回的{@link MembershipKey}上调用{@link MembershipKey#drop drop}方法来删除成员资格。
 * 如果底层平台支持源过滤,那么可以使用{@link MembershipKey#block block}和{@link MembershipKey#unblock unblock}方法来阻止或取消阻止来自
 * 特定源地址的组播数据报。
 * 通过在返回的{@link MembershipKey}上调用{@link MembershipKey#drop drop}方法来删除成员资格。
 * 
 *  <p> {@link #join(InetAddress,NetworkInterface,InetAddress)}方法用于开始接收发送到源地址与给定源地址匹配的组的数据报。
 * 如果底层平台不支持源过滤,此方法会抛出{@link UnsupportedOperationException}。
 * 成员资格是<em>累积</em>,并且可以使用相同的组和接口再次调用此方法,以允许从其他源地址接收数据报。该方法返回表示从给定源地址接收数据报的成员资格的{@link MembershipKey}。
 * 调用密钥的{@link MembershipKey#drop drop}方法删除成员资格,以便不再能收到来自源地址的数据报。
 * 
 *  <h2>平台相关性</h2>
 * 
 * 多播实现旨在直接映射到本地多播设施。因此,在开发接收IP组播数据报的应用程序时,应考虑以下项目：
 * 
 * <ol>
 * 
 *  <li> <p>创建频道时应指定与该频道将加入的多播组的地址类型相对应的{@link ProtocolFamily}。
 * 当多播组的地址对应于另一协议族时,不能保证一个协议族中的套接字的信道能够加入和接收多播数据报。
 * 例如,如果{@link StandardProtocolFamily#INET6 IPv6}套接字的通道可以加入{@link StandardProtocolFamily#INET IPv4}组播组并接
 * 收发送到组的组播数据报,则它是实现特定的。
 * 当多播组的地址对应于另一协议族时,不能保证一个协议族中的套接字的信道能够加入和接收多播数据报。 </p> </li>。
 * 
 *  <li> <p>频道的套接字应绑定到{@link InetAddress#isAnyLocalAddress通配符}地址。
 * 如果套接字绑定到特定地址,而不是通配符地址,则它是实现特定的,如果多播数据报被套接字接收。 </p> </li>。
 * 
 *  <li> <p> {@link StandardSocketOptions#SO_REUSEADDR SO_REUSEADDR}选项应在套接字{@link NetworkChannel#bind binding}
 * 之前启用。
 * 
 * @since 1.7
 */

public interface MulticastChannel
    extends NetworkChannel
{
    /**
     * Closes this channel.
     *
     * <p> If the channel is a member of a multicast group then the membership
     * is {@link MembershipKey#drop dropped}. Upon return, the {@link
     * MembershipKey membership-key} will be {@link MembershipKey#isValid
     * invalid}.
     *
     * <p> This method otherwise behaves exactly as specified by the {@link
     * Channel} interface.
     *
     * <p>
     * 这是必需的,以允许组的多个成员绑定到同一个地址。 </p> </li>。
     * 
     * </ol>
     * 
     *  <p> <b>使用示例：</b>
     * <pre>
     * //加入此接口上的组播组,并使用此//接口发送组播数据报NetworkInterface ni = NetworkInterface.getByName("hme0");
     * 
     *  DatagramChannel dc = DatagramChannel.open(StandardProtocolFamily.INET).setOption(StandardSocketOptio
     * ns.SO_REUSEADDR,true).bind(new InetSocketAddress(5000)).setOption(StandardSocketOptions.IP_MULTICAST_
     * IF,ni);。
     * 
     *  InetAddress group = InetAddress.getByName("225.4.5.6");
     * 
     *  MembershipKey key = dc.join(group,ni);
     * </pre>
     * 
     * 
     * @throws  IOException
     *          If an I/O error occurs
     */
    @Override void close() throws IOException;

    /**
     * Joins a multicast group to begin receiving all datagrams sent to the group,
     * returning a membership key.
     *
     * <p> If this channel is currently a member of the group on the given
     * interface to receive all datagrams then the membership key, representing
     * that membership, is returned. Otherwise this channel joins the group and
     * the resulting new membership key is returned. The resulting membership key
     * is not {@link MembershipKey#sourceAddress source-specific}.
     *
     * <p> A multicast channel may join several multicast groups, including
     * the same group on more than one interface. An implementation may impose a
     * limit on the number of groups that may be joined at the same time.
     *
     * <p>
     *  关闭此频道。
     * 
     *  <p>如果信道是多播组的成员,则成员资格是{@link MembershipKey#drop drop}。
     * 退回时,{@link MembershipKey会员密钥}将为{@link MembershipKey#isValid invalid}。
     * 
     *  <p>此方法的行为与{@link Channel}界面中指定的完全相同。
     * 
     * 
     * @param   group
     *          The multicast address to join
     * @param   interf
     *          The network interface on which to join the group
     *
     * @return  The membership key
     *
     * @throws  IllegalArgumentException
     *          If the group parameter is not a {@link InetAddress#isMulticastAddress
     *          multicast} address, or the group parameter is an address type
     *          that is not supported by this channel
     * @throws  IllegalStateException
     *          If the channel already has source-specific membership of the
     *          group on the interface
     * @throws  UnsupportedOperationException
     *          If the channel's socket is not an Internet Protocol socket
     * @throws  ClosedChannelException
     *          If this channel is closed
     * @throws  IOException
     *          If an I/O error occurs
     * @throws  SecurityException
     *          If a security manager is set, and its
     *          {@link SecurityManager#checkMulticast(InetAddress) checkMulticast}
     *          method denies access to the multiast group
     */
    MembershipKey join(InetAddress group, NetworkInterface interf)
        throws IOException;

    /**
     * Joins a multicast group to begin receiving datagrams sent to the group
     * from a given source address.
     *
     * <p> If this channel is currently a member of the group on the given
     * interface to receive datagrams from the given source address then the
     * membership key, representing that membership, is returned. Otherwise this
     * channel joins the group and the resulting new membership key is returned.
     * The resulting membership key is {@link MembershipKey#sourceAddress
     * source-specific}.
     *
     * <p> Membership is <em>cumulative</em> and this method may be invoked
     * again with the same group and interface to allow receiving datagrams sent
     * by other source addresses to the group.
     *
     * <p>
     *  加入多播组以开始接收发送到组的所有数据报,返回成员资格键。
     * 
     *  <p>如果此频道目前是给定接口上的组的成员,以接收所有数据报,则返回表示成员资格的成员资格密钥。否则,此通道加入组,并返回生成的新成员资格密钥。
     * 生成的成员资格密钥不是{@link MembershipKey#sourceAddress源特定的}。
     * 
     *  <p>多播信道可以加入多个多播组,包括多个接口上的同一组。实现可以对可以同时加入的组的数量施加限制。
     * 
     * 
     * @param   group
     *          The multicast address to join
     * @param   interf
     *          The network interface on which to join the group
     * @param   source
     *          The source address
     *
     * @return  The membership key
     *
     * @throws  IllegalArgumentException
     *          If the group parameter is not a {@link
     *          InetAddress#isMulticastAddress multicast} address, the
     *          source parameter is not a unicast address, the group
     *          parameter is an address type that is not supported by this channel,
     *          or the source parameter is not the same address type as the group
     * @throws  IllegalStateException
     *          If the channel is currently a member of the group on the given
     *          interface to receive all datagrams
     * @throws  UnsupportedOperationException
     *          If the channel's socket is not an Internet Protocol socket or
     *          source filtering is not supported
     * @throws  ClosedChannelException
     *          If this channel is closed
     * @throws  IOException
     *          If an I/O error occurs
     * @throws  SecurityException
     *          If a security manager is set, and its
     *          {@link SecurityManager#checkMulticast(InetAddress) checkMulticast}
     *          method denies access to the multiast group
     */
    MembershipKey join(InetAddress group, NetworkInterface interf, InetAddress source)
        throws IOException;
}
