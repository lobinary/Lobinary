/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2014, Oracle and/or its affiliates. All rights reserved.
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

import java.io.IOException;
import java.util.Enumeration;

/**
 * The multicast datagram socket class is useful for sending
 * and receiving IP multicast packets.  A MulticastSocket is
 * a (UDP) DatagramSocket, with additional capabilities for
 * joining "groups" of other multicast hosts on the internet.
 * <P>
 * A multicast group is specified by a class D IP address
 * and by a standard UDP port number. Class D IP addresses
 * are in the range <CODE>224.0.0.0</CODE> to <CODE>239.255.255.255</CODE>,
 * inclusive. The address 224.0.0.0 is reserved and should not be used.
 * <P>
 * One would join a multicast group by first creating a MulticastSocket
 * with the desired port, then invoking the
 * <CODE>joinGroup(InetAddress groupAddr)</CODE>
 * method:
 * <PRE>
 * // join a Multicast group and send the group salutations
 * ...
 * String msg = "Hello";
 * InetAddress group = InetAddress.getByName("228.5.6.7");
 * MulticastSocket s = new MulticastSocket(6789);
 * s.joinGroup(group);
 * DatagramPacket hi = new DatagramPacket(msg.getBytes(), msg.length(),
 *                             group, 6789);
 * s.send(hi);
 * // get their responses!
 * byte[] buf = new byte[1000];
 * DatagramPacket recv = new DatagramPacket(buf, buf.length);
 * s.receive(recv);
 * ...
 * // OK, I'm done talking - leave the group...
 * s.leaveGroup(group);
 * </PRE>
 *
 * When one sends a message to a multicast group, <B>all</B> subscribing
 * recipients to that host and port receive the message (within the
 * time-to-live range of the packet, see below).  The socket needn't
 * be a member of the multicast group to send messages to it.
 * <P>
 * When a socket subscribes to a multicast group/port, it receives
 * datagrams sent by other hosts to the group/port, as do all other
 * members of the group and port.  A socket relinquishes membership
 * in a group by the leaveGroup(InetAddress addr) method.  <B>
 * Multiple MulticastSocket's</B> may subscribe to a multicast group
 * and port concurrently, and they will all receive group datagrams.
 * <P>
 * Currently applets are not allowed to use multicast sockets.
 *
 * <p>
 *  组播数据报套接字类对于发送和接收IP组播数据包很有用。 MulticastSocket是(UDP)DatagramSocket,具有用于加入互联网上其他多播主机的"组"的附加功能。
 * <P>
 *  组播组由D类IP地址和标准UDP端口号指定。 D类IP地址的范围为<CODE> 224.0.0.0 </CODE>至<CODE> 239.255.255.255 </CODE>(含)。
 * 地址224.0.0.0保留,不应使用。
 * <P>
 *  通过首先创建具有所需端口的MulticastSocket,然后调用<CODE> joinGroup(InetAddress groupAddr)</CODE>方法,将加入多播组：
 * <PRE>
 *  // join a Multicast group and send the group salutations ... String msg ="Hello"; InetAddress group 
 * = InetAddress.getByName("228.5.6.7"); MulticastSocket s = new MulticastSocket(6789); s.joinGroup(grou
 * p); DatagramPacket hi = new DatagramPacket(msg.getBytes(),msg.length(),group,6789); send(hi); //得到他们的
 * 反应！ byte [] buf = new byte [1000]; DatagramPacket recv = new DatagramPacket(buf,buf.length);接收(recv);
 *  ... //好的,我说话了 - 离开组... s.leaveGroup(group);。
 * </PRE>
 * 
 * 当向多播组发送消息时,<B>所有</B>订阅接收者到该主机和端口接收该消息(在分组的生存时间范围内,参见下文)。套接字不需要是多播组的成员向其发送消息。
 * <P>
 *  当套接字订阅多播组/端口时,它接收由其他主机发送到组/端口的数据报,组和端口的所有其他成员也是如此。套接字通过leaveGroup(InetAddress addr)方法释放组中的成员资格。
 *  <B>多个多播套接字</B>可以同时订阅多播组和端口,并且它们将全部接收组数据报。
 * <P>
 *  目前applet不允许使用多播套接字。
 * 
 * 
 * @author Pavani Diwanji
 * @since  JDK1.1
 */
public
class MulticastSocket extends DatagramSocket {

    /**
     * Used on some platforms to record if an outgoing interface
     * has been set for this socket.
     * <p>
     *  在某些平台上使用,以记录是否为此套接字设置了传出接口。
     * 
     */
    private boolean interfaceSet;

    /**
     * Create a multicast socket.
     *
     * <p>If there is a security manager,
     * its {@code checkListen} method is first called
     * with 0 as its argument to ensure the operation is allowed.
     * This could result in a SecurityException.
     * <p>
     * When the socket is created the
     * {@link DatagramSocket#setReuseAddress(boolean)} method is
     * called to enable the SO_REUSEADDR socket option.
     *
     * <p>
     *  创建组播套接字。
     * 
     *  <p>如果有安全管理器,则首先调用其{@code checkListen}方法,其中的参数为0,以确保允许操作。这可能导致SecurityException。
     * <p>
     *  当创建套接字时,调用{@link DatagramSocket#setReuseAddress(boolean)}方法来启用SO_REUSEADDR套接字选项。
     * 
     * 
     * @exception IOException if an I/O exception occurs
     * while creating the MulticastSocket
     * @exception  SecurityException  if a security manager exists and its
     *             {@code checkListen} method doesn't allow the operation.
     * @see SecurityManager#checkListen
     * @see java.net.DatagramSocket#setReuseAddress(boolean)
     */
    public MulticastSocket() throws IOException {
        this(new InetSocketAddress(0));
    }

    /**
     * Create a multicast socket and bind it to a specific port.
     *
     * <p>If there is a security manager,
     * its {@code checkListen} method is first called
     * with the {@code port} argument
     * as its argument to ensure the operation is allowed.
     * This could result in a SecurityException.
     * <p>
     * When the socket is created the
     * {@link DatagramSocket#setReuseAddress(boolean)} method is
     * called to enable the SO_REUSEADDR socket option.
     *
     * <p>
     *  创建组播套接字并绑定到特定端口。
     * 
     *  <p>如果有安全管理器,则会先使用{@code portList}参数作为其参数来调用其{@code checkListen}方法,以确保允许操作。这可能导致SecurityException。
     * <p>
     * 当创建套接字时,调用{@link DatagramSocket#setReuseAddress(boolean)}方法来启用SO_REUSEADDR套接字选项。
     * 
     * 
     * @param port port to use
     * @exception IOException if an I/O exception occurs
     * while creating the MulticastSocket
     * @exception  SecurityException  if a security manager exists and its
     *             {@code checkListen} method doesn't allow the operation.
     * @see SecurityManager#checkListen
     * @see java.net.DatagramSocket#setReuseAddress(boolean)
     */
    public MulticastSocket(int port) throws IOException {
        this(new InetSocketAddress(port));
    }

    /**
     * Create a MulticastSocket bound to the specified socket address.
     * <p>
     * Or, if the address is {@code null}, create an unbound socket.
     *
     * <p>If there is a security manager,
     * its {@code checkListen} method is first called
     * with the SocketAddress port as its argument to ensure the operation is allowed.
     * This could result in a SecurityException.
     * <p>
     * When the socket is created the
     * {@link DatagramSocket#setReuseAddress(boolean)} method is
     * called to enable the SO_REUSEADDR socket option.
     *
     * <p>
     *  创建一个绑定到指定套接字地址的MulticastSocket。
     * <p>
     *  或者,如果地址是{@code null},请创建一个未绑定的套接字。
     * 
     *  <p>如果有安全管理器,则首先使用SocketAddress端口作为其参数来调用其{@code checkListen}方法,以确保允许操作。这可能导致SecurityException。
     * <p>
     *  当创建套接字时,调用{@link DatagramSocket#setReuseAddress(boolean)}方法来启用SO_REUSEADDR套接字选项。
     * 
     * 
     * @param bindaddr Socket address to bind to, or {@code null} for
     *                 an unbound socket.
     * @exception IOException if an I/O exception occurs
     * while creating the MulticastSocket
     * @exception  SecurityException  if a security manager exists and its
     *             {@code checkListen} method doesn't allow the operation.
     * @see SecurityManager#checkListen
     * @see java.net.DatagramSocket#setReuseAddress(boolean)
     *
     * @since 1.4
     */
    public MulticastSocket(SocketAddress bindaddr) throws IOException {
        super((SocketAddress) null);

        // Enable SO_REUSEADDR before binding
        setReuseAddress(true);

        if (bindaddr != null) {
            try {
                bind(bindaddr);
            } finally {
                if (!isBound())
                    close();
            }
        }
    }

    /**
     * The lock on the socket's TTL. This is for set/getTTL and
     * send(packet,ttl).
     * <p>
     *  锁定套接字的TTL。这是为set / getTTL和send(packet,ttl)。
     * 
     */
    private Object ttlLock = new Object();

    /**
     * The lock on the socket's interface - used by setInterface
     * and getInterface
     * <p>
     *  套接字接口上的锁 - 由setInterface和getInterface使用
     * 
     */
    private Object infLock = new Object();

    /**
     * The "last" interface set by setInterface on this MulticastSocket
     * <p>
     *  在此MulticastSocket上的setInterface设置的"last"接口
     * 
     */
    private InetAddress infAddress = null;


    /**
     * Set the default time-to-live for multicast packets sent out
     * on this {@code MulticastSocket} in order to control the
     * scope of the multicasts.
     *
     * <p>The ttl is an <b>unsigned</b> 8-bit quantity, and so <B>must</B> be
     * in the range {@code 0 <= ttl <= 0xFF }.
     *
     * <p>
     *  设置此{@code MulticastSocket}上发送的组播数据包的默认生存时间,以控制组播范围。
     * 
     *  <p> ttl是<b>无符号</b> 8位数,因此<B>必须在{@code 0 <= ttl <= 0xFF}的范围内。
     * 
     * 
     * @param ttl the time-to-live
     * @exception IOException if an I/O exception occurs
     * while setting the default time-to-live value
     * @deprecated use the setTimeToLive method instead, which uses
     * <b>int</b> instead of <b>byte</b> as the type for ttl.
     * @see #getTTL()
     */
    @Deprecated
    public void setTTL(byte ttl) throws IOException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        getImpl().setTTL(ttl);
    }

    /**
     * Set the default time-to-live for multicast packets sent out
     * on this {@code MulticastSocket} in order to control the
     * scope of the multicasts.
     *
     * <P> The ttl <B>must</B> be in the range {@code  0 <= ttl <=
     * 255} or an {@code IllegalArgumentException} will be thrown.
     * Multicast packets sent with a TTL of {@code 0} are not transmitted
     * on the network but may be delivered locally.
     *
     * <p>
     *  设置此{@code MulticastSocket}上发送的组播数据包的默认生存时间,以控制组播范围。
     * 
     *  <p> ttl <B>必须</b>在{@code 0 <= ttl <= 255}的范围内,否则将抛出{@code IllegalArgumentException}。
     * 使用TTL {@code 0}发送的组播数据包不在网络上传输,但可以本地传递。
     * 
     * 
     * @param  ttl
     *         the time-to-live
     *
     * @throws  IOException
     *          if an I/O exception occurs while setting the
     *          default time-to-live value
     *
     * @see #getTimeToLive()
     */
    public void setTimeToLive(int ttl) throws IOException {
        if (ttl < 0 || ttl > 255) {
            throw new IllegalArgumentException("ttl out of range");
        }
        if (isClosed())
            throw new SocketException("Socket is closed");
        getImpl().setTimeToLive(ttl);
    }

    /**
     * Get the default time-to-live for multicast packets sent out on
     * the socket.
     *
     * <p>
     * 获取套接字上发送的组播数据包的默认生存时间。
     * 
     * 
     * @exception IOException if an I/O exception occurs
     * while getting the default time-to-live value
     * @return the default time-to-live value
     * @deprecated use the getTimeToLive method instead, which returns
     * an <b>int</b> instead of a <b>byte</b>.
     * @see #setTTL(byte)
     */
    @Deprecated
    public byte getTTL() throws IOException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        return getImpl().getTTL();
    }

    /**
     * Get the default time-to-live for multicast packets sent out on
     * the socket.
     * <p>
     *  获取套接字上发送的组播数据包的默认生存时间。
     * 
     * 
     * @exception IOException if an I/O exception occurs while
     * getting the default time-to-live value
     * @return the default time-to-live value
     * @see #setTimeToLive(int)
     */
    public int getTimeToLive() throws IOException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        return getImpl().getTimeToLive();
    }

    /**
     * Joins a multicast group. Its behavior may be affected by
     * {@code setInterface} or {@code setNetworkInterface}.
     *
     * <p>If there is a security manager, this method first
     * calls its {@code checkMulticast} method
     * with the {@code mcastaddr} argument
     * as its argument.
     *
     * <p>
     *  加入组播组。它的行为可能受到{@code setInterface}或{@code setNetworkInterface}的影响。
     * 
     *  <p>如果有安全管理员,此方法首先使用{@code mcastaddr}参数作为其参数调用其{@code checkMulticast}方法。
     * 
     * 
     * @param mcastaddr is the multicast address to join
     *
     * @exception IOException if there is an error joining
     * or when the address is not a multicast address.
     * @exception  SecurityException  if a security manager exists and its
     * {@code checkMulticast} method doesn't allow the join.
     *
     * @see SecurityManager#checkMulticast(InetAddress)
     */
    public void joinGroup(InetAddress mcastaddr) throws IOException {
        if (isClosed()) {
            throw new SocketException("Socket is closed");
        }

        checkAddress(mcastaddr, "joinGroup");
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkMulticast(mcastaddr);
        }

        if (!mcastaddr.isMulticastAddress()) {
            throw new SocketException("Not a multicast address");
        }

        /**
         * required for some platforms where it's not possible to join
         * a group without setting the interface first.
         * <p>
         *  对于某些平台,如果无法首先设置接口而无法加入群组,则需要。
         * 
         */
        NetworkInterface defaultInterface = NetworkInterface.getDefault();

        if (!interfaceSet && defaultInterface != null) {
            setNetworkInterface(defaultInterface);
        }

        getImpl().join(mcastaddr);
    }

    /**
     * Leave a multicast group. Its behavior may be affected by
     * {@code setInterface} or {@code setNetworkInterface}.
     *
     * <p>If there is a security manager, this method first
     * calls its {@code checkMulticast} method
     * with the {@code mcastaddr} argument
     * as its argument.
     *
     * <p>
     *  离开组播组。它的行为可能受到{@code setInterface}或{@code setNetworkInterface}的影响。
     * 
     *  <p>如果有安全管理员,此方法首先使用{@code mcastaddr}参数作为其参数调用其{@code checkMulticast}方法。
     * 
     * 
     * @param mcastaddr is the multicast address to leave
     * @exception IOException if there is an error leaving
     * or when the address is not a multicast address.
     * @exception  SecurityException  if a security manager exists and its
     * {@code checkMulticast} method doesn't allow the operation.
     *
     * @see SecurityManager#checkMulticast(InetAddress)
     */
    public void leaveGroup(InetAddress mcastaddr) throws IOException {
        if (isClosed()) {
            throw new SocketException("Socket is closed");
        }

        checkAddress(mcastaddr, "leaveGroup");
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkMulticast(mcastaddr);
        }

        if (!mcastaddr.isMulticastAddress()) {
            throw new SocketException("Not a multicast address");
        }

        getImpl().leave(mcastaddr);
    }

    /**
     * Joins the specified multicast group at the specified interface.
     *
     * <p>If there is a security manager, this method first
     * calls its {@code checkMulticast} method
     * with the {@code mcastaddr} argument
     * as its argument.
     *
     * <p>
     *  在指定的接口加入指定的组播组。
     * 
     *  <p>如果有安全管理员,此方法首先使用{@code mcastaddr}参数作为其参数调用其{@code checkMulticast}方法。
     * 
     * 
     * @param mcastaddr is the multicast address to join
     * @param netIf specifies the local interface to receive multicast
     *        datagram packets, or <i>null</i> to defer to the interface set by
     *       {@link MulticastSocket#setInterface(InetAddress)} or
     *       {@link MulticastSocket#setNetworkInterface(NetworkInterface)}
     *
     * @exception IOException if there is an error joining
     * or when the address is not a multicast address.
     * @exception  SecurityException  if a security manager exists and its
     * {@code checkMulticast} method doesn't allow the join.
     * @throws  IllegalArgumentException if mcastaddr is null or is a
     *          SocketAddress subclass not supported by this socket
     *
     * @see SecurityManager#checkMulticast(InetAddress)
     * @since 1.4
     */
    public void joinGroup(SocketAddress mcastaddr, NetworkInterface netIf)
        throws IOException {
        if (isClosed())
            throw new SocketException("Socket is closed");

        if (mcastaddr == null || !(mcastaddr instanceof InetSocketAddress))
            throw new IllegalArgumentException("Unsupported address type");

        if (oldImpl)
            throw new UnsupportedOperationException();

        checkAddress(((InetSocketAddress)mcastaddr).getAddress(), "joinGroup");
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkMulticast(((InetSocketAddress)mcastaddr).getAddress());
        }

        if (!((InetSocketAddress)mcastaddr).getAddress().isMulticastAddress()) {
            throw new SocketException("Not a multicast address");
        }

        getImpl().joinGroup(mcastaddr, netIf);
    }

    /**
     * Leave a multicast group on a specified local interface.
     *
     * <p>If there is a security manager, this method first
     * calls its {@code checkMulticast} method
     * with the {@code mcastaddr} argument
     * as its argument.
     *
     * <p>
     *  在指定的本地接口上离开组播组。
     * 
     *  <p>如果有安全管理员,此方法首先使用{@code mcastaddr}参数作为其参数调用其{@code checkMulticast}方法。
     * 
     * 
     * @param mcastaddr is the multicast address to leave
     * @param netIf specifies the local interface or <i>null</i> to defer
     *             to the interface set by
     *             {@link MulticastSocket#setInterface(InetAddress)} or
     *             {@link MulticastSocket#setNetworkInterface(NetworkInterface)}
     * @exception IOException if there is an error leaving
     * or when the address is not a multicast address.
     * @exception  SecurityException  if a security manager exists and its
     * {@code checkMulticast} method doesn't allow the operation.
     * @throws  IllegalArgumentException if mcastaddr is null or is a
     *          SocketAddress subclass not supported by this socket
     *
     * @see SecurityManager#checkMulticast(InetAddress)
     * @since 1.4
     */
    public void leaveGroup(SocketAddress mcastaddr, NetworkInterface netIf)
        throws IOException {
        if (isClosed())
            throw new SocketException("Socket is closed");

        if (mcastaddr == null || !(mcastaddr instanceof InetSocketAddress))
            throw new IllegalArgumentException("Unsupported address type");

        if (oldImpl)
            throw new UnsupportedOperationException();

        checkAddress(((InetSocketAddress)mcastaddr).getAddress(), "leaveGroup");
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkMulticast(((InetSocketAddress)mcastaddr).getAddress());
        }

        if (!((InetSocketAddress)mcastaddr).getAddress().isMulticastAddress()) {
            throw new SocketException("Not a multicast address");
        }

        getImpl().leaveGroup(mcastaddr, netIf);
     }

    /**
     * Set the multicast network interface used by methods
     * whose behavior would be affected by the value of the
     * network interface. Useful for multihomed hosts.
     * <p>
     *  设置行为受网络接口值影响的方法使用的组播网络接口。适用于多宿主主机。
     * 
     * 
     * @param inf the InetAddress
     * @exception SocketException if there is an error in
     * the underlying protocol, such as a TCP error.
     * @see #getInterface()
     */
    public void setInterface(InetAddress inf) throws SocketException {
        if (isClosed()) {
            throw new SocketException("Socket is closed");
        }
        checkAddress(inf, "setInterface");
        synchronized (infLock) {
            getImpl().setOption(SocketOptions.IP_MULTICAST_IF, inf);
            infAddress = inf;
            interfaceSet = true;
        }
    }

    /**
     * Retrieve the address of the network interface used for
     * multicast packets.
     *
     * <p>
     *  检索用于组播数据包的网络接口的地址。
     * 
     * 
     * @return An {@code InetAddress} representing
     *  the address of the network interface used for
     *  multicast packets.
     *
     * @exception SocketException if there is an error in
     * the underlying protocol, such as a TCP error.
     *
     * @see #setInterface(java.net.InetAddress)
     */
    public InetAddress getInterface() throws SocketException {
        if (isClosed()) {
            throw new SocketException("Socket is closed");
        }
        synchronized (infLock) {
            InetAddress ia =
                (InetAddress)getImpl().getOption(SocketOptions.IP_MULTICAST_IF);

            /**
             * No previous setInterface or interface can be
             * set using setNetworkInterface
             * <p>
             *  没有以前的setInterface或接口可以使用setNetworkInterface设置
             * 
             */
            if (infAddress == null) {
                return ia;
            }

            /**
             * Same interface set with setInterface?
             * <p>
             * 使用setInterface设置相同的接口?
             * 
             */
            if (ia.equals(infAddress)) {
                return ia;
            }

            /**
             * Different InetAddress from what we set with setInterface
             * so enumerate the current interface to see if the
             * address set by setInterface is bound to this interface.
             * <p>
             *  不同的InetAddress从我们用setInterface设置,所以枚举当前接口看看setInterface设置的地址是否绑定到此接口。
             * 
             */
            try {
                NetworkInterface ni = NetworkInterface.getByInetAddress(ia);
                Enumeration<InetAddress> addrs = ni.getInetAddresses();
                while (addrs.hasMoreElements()) {
                    InetAddress addr = addrs.nextElement();
                    if (addr.equals(infAddress)) {
                        return infAddress;
                    }
                }

                /**
                 * No match so reset infAddress to indicate that the
                 * interface has changed via means
                 * <p>
                 *  没有匹配,因此复位infAddress以指示接口已经通过手段更改
                 * 
                 */
                infAddress = null;
                return ia;
            } catch (Exception e) {
                return ia;
            }
        }
    }

    /**
     * Specify the network interface for outgoing multicast datagrams
     * sent on this socket.
     *
     * <p>
     *  指定在此套接字上发送的出站组播数据报的网络接口。
     * 
     * 
     * @param netIf the interface
     * @exception SocketException if there is an error in
     * the underlying protocol, such as a TCP error.
     * @see #getNetworkInterface()
     * @since 1.4
     */
    public void setNetworkInterface(NetworkInterface netIf)
        throws SocketException {

        synchronized (infLock) {
            getImpl().setOption(SocketOptions.IP_MULTICAST_IF2, netIf);
            infAddress = null;
            interfaceSet = true;
        }
    }

    /**
     * Get the multicast network interface set.
     *
     * <p>
     *  获取组播网络接口集。
     * 
     * 
     * @exception SocketException if there is an error in
     * the underlying protocol, such as a TCP error.
     * @return the multicast {@code NetworkInterface} currently set
     * @see #setNetworkInterface(NetworkInterface)
     * @since 1.4
     */
    public NetworkInterface getNetworkInterface() throws SocketException {
        NetworkInterface ni
            = (NetworkInterface)getImpl().getOption(SocketOptions.IP_MULTICAST_IF2);
        if ((ni.getIndex() == 0) || (ni.getIndex() == -1)) {
            InetAddress[] addrs = new InetAddress[1];
            addrs[0] = InetAddress.anyLocalAddress();
            return new NetworkInterface(addrs[0].getHostName(), 0, addrs);
        } else {
            return ni;
        }
    }

    /**
     * Disable/Enable local loopback of multicast datagrams
     * The option is used by the platform's networking code as a hint
     * for setting whether multicast data will be looped back to
     * the local socket.
     *
     * <p>Because this option is a hint, applications that want to
     * verify what loopback mode is set to should call
     * {@link #getLoopbackMode()}
     * <p>
     *  禁用/启用组播数据报的本地环回此选项由平台的网络代码用作设置是否将组播数据循环回本地套接字的提示。
     * 
     *  <p>因为这个选项是一个提示,应用程序想要验证什么回送模式设置应该调用{@link #getLoopbackMode()}
     * 
     * 
     * @param disable {@code true} to disable the LoopbackMode
     * @throws SocketException if an error occurs while setting the value
     * @since 1.4
     * @see #getLoopbackMode
     */
    public void setLoopbackMode(boolean disable) throws SocketException {
        getImpl().setOption(SocketOptions.IP_MULTICAST_LOOP, Boolean.valueOf(disable));
    }

    /**
     * Get the setting for local loopback of multicast datagrams.
     *
     * <p>
     *  获取组播数据报文的本地环回设置。
     * 
     * 
     * @throws SocketException  if an error occurs while getting the value
     * @return true if the LoopbackMode has been disabled
     * @since 1.4
     * @see #setLoopbackMode
     */
    public boolean getLoopbackMode() throws SocketException {
        return ((Boolean)getImpl().getOption(SocketOptions.IP_MULTICAST_LOOP)).booleanValue();
    }

    /**
     * Sends a datagram packet to the destination, with a TTL (time-
     * to-live) other than the default for the socket.  This method
     * need only be used in instances where a particular TTL is desired;
     * otherwise it is preferable to set a TTL once on the socket, and
     * use that default TTL for all packets.  This method does <B>not
     * </B> alter the default TTL for the socket. Its behavior may be
     * affected by {@code setInterface}.
     *
     * <p>If there is a security manager, this method first performs some
     * security checks. First, if {@code p.getAddress().isMulticastAddress()}
     * is true, this method calls the
     * security manager's {@code checkMulticast} method
     * with {@code p.getAddress()} and {@code ttl} as its arguments.
     * If the evaluation of that expression is false,
     * this method instead calls the security manager's
     * {@code checkConnect} method with arguments
     * {@code p.getAddress().getHostAddress()} and
     * {@code p.getPort()}. Each call to a security manager method
     * could result in a SecurityException if the operation is not allowed.
     *
     * <p>
     *  使用除套接字的默认值之外的TTL(存活时间)将数据报数据包发送到目标。该方法仅需要在需要特定TTL的情况下使用;否则最好在套接字上设置一次TTL,并对所有数据包使用默认TTL。
     * 此方法</b>不会</b>更改套接字的默认TTL。它的行为可能会受到{@code setInterface}的影响。
     * 
     * <p>如果有安全管理员,此方法首先执行一些安全检查。首先,如果{@code p.getAddress()。
     * isMulticastAddress()}为true,此方法调用安全管理器的{@code checkMulticast}方法{@code p.getAddress()}和{@code ttl}作为其参数
     * 
     * @param p is the packet to be sent. The packet should contain
     * the destination multicast ip address and the data to be sent.
     * One does not need to be the member of the group to send
     * packets to a destination multicast address.
     * @param ttl optional time to live for multicast packet.
     * default ttl is 1.
     *
     * @exception IOException is raised if an error occurs i.e
     * error while setting ttl.
     * @exception  SecurityException  if a security manager exists and its
     *             {@code checkMulticast} or {@code checkConnect}
     *             method doesn't allow the send.
     *
     * @deprecated Use the following code or its equivalent instead:
     *  ......
     *  int ttl = mcastSocket.getTimeToLive();
     *  mcastSocket.setTimeToLive(newttl);
     *  mcastSocket.send(p);
     *  mcastSocket.setTimeToLive(ttl);
     *  ......
     *
     * @see DatagramSocket#send
     * @see DatagramSocket#receive
     * @see SecurityManager#checkMulticast(java.net.InetAddress, byte)
     * @see SecurityManager#checkConnect
     */
    @Deprecated
    public void send(DatagramPacket p, byte ttl)
        throws IOException {
            if (isClosed())
                throw new SocketException("Socket is closed");
            checkAddress(p.getAddress(), "send");
            synchronized(ttlLock) {
                synchronized(p) {
                    if (connectState == ST_NOT_CONNECTED) {
                        // Security manager makes sure that the multicast address
                        // is allowed one and that the ttl used is less
                        // than the allowed maxttl.
                        SecurityManager security = System.getSecurityManager();
                        if (security != null) {
                            if (p.getAddress().isMulticastAddress()) {
                                security.checkMulticast(p.getAddress(), ttl);
                            } else {
                                security.checkConnect(p.getAddress().getHostAddress(),
                                                      p.getPort());
                            }
                        }
                    } else {
                        // we're connected
                        InetAddress packetAddress = null;
                        packetAddress = p.getAddress();
                        if (packetAddress == null) {
                            p.setAddress(connectedAddress);
                            p.setPort(connectedPort);
                        } else if ((!packetAddress.equals(connectedAddress)) ||
                                   p.getPort() != connectedPort) {
                            throw new SecurityException("connected address and packet address" +
                                                        " differ");
                        }
                    }
                    byte dttl = getTTL();
                    try {
                        if (ttl != dttl) {
                            // set the ttl
                            getImpl().setTTL(ttl);
                        }
                        // call the datagram method to send
                        getImpl().send(p);
                    } finally {
                        // set it back to default
                        if (ttl != dttl) {
                            getImpl().setTTL(dttl);
                        }
                    }
                } // synch p
            }  //synch ttl
    } //method
}
