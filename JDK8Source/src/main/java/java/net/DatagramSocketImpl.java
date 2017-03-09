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

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InterruptedIOException;

/**
 * Abstract datagram and multicast socket implementation base class.
 * <p>
 *  抽象数据报和组播套接字实现基类。
 * 
 * 
 * @author Pavani Diwanji
 * @since  JDK1.1
 */

public abstract class DatagramSocketImpl implements SocketOptions {

    /**
     * The local port number.
     * <p>
     *  本地端口号。
     * 
     */
    protected int localPort;

    /**
     * The file descriptor object.
     * <p>
     *  文件描述符对象。
     * 
     */
    protected FileDescriptor fd;

    int dataAvailable() {
        // default impl returns zero, which disables the calling
        // functionality
        return 0;
    }

    /**
     * The DatagramSocket or MulticastSocket
     * that owns this impl
     * <p>
     *  拥有此impl的DatagramSocket或MulticastSocket
     * 
     */
    DatagramSocket socket;

    void setDatagramSocket(DatagramSocket socket) {
        this.socket = socket;
    }

    DatagramSocket getDatagramSocket() {
        return socket;
    }

    /**
     * Creates a datagram socket.
     * <p>
     *  创建数据报套接字。
     * 
     * 
     * @exception SocketException if there is an error in the
     * underlying protocol, such as a TCP error.
     */
    protected abstract void create() throws SocketException;

    /**
     * Binds a datagram socket to a local port and address.
     * <p>
     *  将数据报套接字绑定到本地端口和地址。
     * 
     * 
     * @param lport the local port
     * @param laddr the local address
     * @exception SocketException if there is an error in the
     * underlying protocol, such as a TCP error.
     */
    protected abstract void bind(int lport, InetAddress laddr) throws SocketException;

    /**
     * Sends a datagram packet. The packet contains the data and the
     * destination address to send the packet to.
     * <p>
     *  发送数据包。数据包包含要发送数据包的数据和目标地址。
     * 
     * 
     * @param p the packet to be sent.
     * @exception IOException if an I/O exception occurs while sending the
     * datagram packet.
     * @exception  PortUnreachableException may be thrown if the socket is connected
     * to a currently unreachable destination. Note, there is no guarantee that
     * the exception will be thrown.
     */
    protected abstract void send(DatagramPacket p) throws IOException;

    /**
     * Connects a datagram socket to a remote destination. This associates the remote
     * address with the local socket so that datagrams may only be sent to this destination
     * and received from this destination. This may be overridden to call a native
     * system connect.
     *
     * <p>If the remote destination to which the socket is connected does not
     * exist, or is otherwise unreachable, and if an ICMP destination unreachable
     * packet has been received for that address, then a subsequent call to
     * send or receive may throw a PortUnreachableException.
     * Note, there is no guarantee that the exception will be thrown.
     * <p>
     *  将数据报套接字连接到远程目标。这将远程地址与本地套接字相关联,使得数据报可以仅被发送到该目的地并从该目的地接收。这可以被覆盖以调用本地系统连接。
     * 
     *  <p>如果套接字连接到的远程目标不存在,或者以其他方式不可访问,并且如果已经为该地址接收到ICMP目的地不可达报文,则后续的发送或接收调用可能会抛出PortUnreachableException。
     * 注意,不能保证将抛出异常。
     * 
     * 
     * @param address the remote InetAddress to connect to
     * @param port the remote port number
     * @exception   SocketException may be thrown if the socket cannot be
     * connected to the remote destination
     * @since 1.4
     */
    protected void connect(InetAddress address, int port) throws SocketException {}

    /**
     * Disconnects a datagram socket from its remote destination.
     * <p>
     *  从其远程目标断开数据报套接字。
     * 
     * 
     * @since 1.4
     */
    protected void disconnect() {}

    /**
     * Peek at the packet to see who it is from. Updates the specified {@code InetAddress}
     * to the address which the packet came from.
     * <p>
     *  偷看包,看看它是从哪里来的。将指定的{@code InetAddress}更新为数据包来自的地址。
     * 
     * 
     * @param i an InetAddress object
     * @return the port number which the packet came from.
     * @exception IOException if an I/O exception occurs
     * @exception  PortUnreachableException may be thrown if the socket is connected
     *       to a currently unreachable destination. Note, there is no guarantee that the
     *       exception will be thrown.
     */
    protected abstract int peek(InetAddress i) throws IOException;

    /**
     * Peek at the packet to see who it is from. The data is copied into the specified
     * {@code DatagramPacket}. The data is returned,
     * but not consumed, so that a subsequent peekData/receive operation
     * will see the same data.
     * <p>
     * 偷看包,看看它是从哪里来的。数据被复制到指定的{@code DatagramPacket}。数据被返回,但不被消耗,因此后续的peekData / receive操作将看到相同的数据。
     * 
     * 
     * @param p the Packet Received.
     * @return the port number which the packet came from.
     * @exception IOException if an I/O exception occurs
     * @exception  PortUnreachableException may be thrown if the socket is connected
     *       to a currently unreachable destination. Note, there is no guarantee that the
     *       exception will be thrown.
     * @since 1.4
     */
    protected abstract int peekData(DatagramPacket p) throws IOException;
    /**
     * Receive the datagram packet.
     * <p>
     *  接收数据包。
     * 
     * 
     * @param p the Packet Received.
     * @exception IOException if an I/O exception occurs
     * while receiving the datagram packet.
     * @exception  PortUnreachableException may be thrown if the socket is connected
     *       to a currently unreachable destination. Note, there is no guarantee that the
     *       exception will be thrown.
     */
    protected abstract void receive(DatagramPacket p) throws IOException;

    /**
     * Set the TTL (time-to-live) option.
     * <p>
     *  设置TTL(存活时间)选项。
     * 
     * 
     * @param ttl a byte specifying the TTL value
     *
     * @deprecated use setTimeToLive instead.
     * @exception IOException if an I/O exception occurs while setting
     * the time-to-live option.
     * @see #getTTL()
     */
    @Deprecated
    protected abstract void setTTL(byte ttl) throws IOException;

    /**
     * Retrieve the TTL (time-to-live) option.
     *
     * <p>
     *  检索TTL(生存时间)选项。
     * 
     * 
     * @exception IOException if an I/O exception occurs
     * while retrieving the time-to-live option
     * @deprecated use getTimeToLive instead.
     * @return a byte representing the TTL value
     * @see #setTTL(byte)
     */
    @Deprecated
    protected abstract byte getTTL() throws IOException;

    /**
     * Set the TTL (time-to-live) option.
     * <p>
     *  设置TTL(存活时间)选项。
     * 
     * 
     * @param ttl an {@code int} specifying the time-to-live value
     * @exception IOException if an I/O exception occurs
     * while setting the time-to-live option.
     * @see #getTimeToLive()
     */
    protected abstract void setTimeToLive(int ttl) throws IOException;

    /**
     * Retrieve the TTL (time-to-live) option.
     * <p>
     *  检索TTL(生存时间)选项。
     * 
     * 
     * @exception IOException if an I/O exception occurs
     * while retrieving the time-to-live option
     * @return an {@code int} representing the time-to-live value
     * @see #setTimeToLive(int)
     */
    protected abstract int getTimeToLive() throws IOException;

    /**
     * Join the multicast group.
     * <p>
     *  加入组播组。
     * 
     * 
     * @param inetaddr multicast address to join.
     * @exception IOException if an I/O exception occurs
     * while joining the multicast group.
     */
    protected abstract void join(InetAddress inetaddr) throws IOException;

    /**
     * Leave the multicast group.
     * <p>
     *  离开组播组。
     * 
     * 
     * @param inetaddr multicast address to leave.
     * @exception IOException if an I/O exception occurs
     * while leaving the multicast group.
     */
    protected abstract void leave(InetAddress inetaddr) throws IOException;

    /**
     * Join the multicast group.
     * <p>
     *  加入组播组。
     * 
     * 
     * @param mcastaddr address to join.
     * @param netIf specifies the local interface to receive multicast
     *        datagram packets
     * @throws IOException if an I/O exception occurs while joining
     * the multicast group
     * @since 1.4
     */
    protected abstract void joinGroup(SocketAddress mcastaddr,
                                      NetworkInterface netIf)
        throws IOException;

    /**
     * Leave the multicast group.
     * <p>
     *  离开组播组。
     * 
     * 
     * @param mcastaddr address to leave.
     * @param netIf specified the local interface to leave the group at
     * @throws IOException if an I/O exception occurs while leaving
     * the multicast group
     * @since 1.4
     */
    protected abstract void leaveGroup(SocketAddress mcastaddr,
                                       NetworkInterface netIf)
        throws IOException;

    /**
     * Close the socket.
     * <p>
     *  关闭套接字。
     * 
     */
    protected abstract void close();

    /**
     * Gets the local port.
     * <p>
     *  获取本地端口。
     * 
     * 
     * @return an {@code int} representing the local port value
     */
    protected int getLocalPort() {
        return localPort;
    }

    <T> void setOption(SocketOption<T> name, T value) throws IOException {
        if (name == StandardSocketOptions.SO_SNDBUF) {
            setOption(SocketOptions.SO_SNDBUF, value);
        } else if (name == StandardSocketOptions.SO_RCVBUF) {
            setOption(SocketOptions.SO_RCVBUF, value);
        } else if (name == StandardSocketOptions.SO_REUSEADDR) {
            setOption(SocketOptions.SO_REUSEADDR, value);
        } else if (name == StandardSocketOptions.IP_TOS) {
            setOption(SocketOptions.IP_TOS, value);
        } else if (name == StandardSocketOptions.IP_MULTICAST_IF &&
            (getDatagramSocket() instanceof MulticastSocket)) {
            setOption(SocketOptions.IP_MULTICAST_IF2, value);
        } else if (name == StandardSocketOptions.IP_MULTICAST_TTL &&
            (getDatagramSocket() instanceof MulticastSocket)) {
            if (! (value instanceof Integer)) {
                throw new IllegalArgumentException("not an integer");
            }
            setTimeToLive((Integer)value);
        } else if (name == StandardSocketOptions.IP_MULTICAST_LOOP &&
            (getDatagramSocket() instanceof MulticastSocket)) {
            setOption(SocketOptions.IP_MULTICAST_LOOP, value);
        } else {
            throw new UnsupportedOperationException("unsupported option");
        }
    }

    <T> T getOption(SocketOption<T> name) throws IOException {
        if (name == StandardSocketOptions.SO_SNDBUF) {
            return (T) getOption(SocketOptions.SO_SNDBUF);
        } else if (name == StandardSocketOptions.SO_RCVBUF) {
            return (T) getOption(SocketOptions.SO_RCVBUF);
        } else if (name == StandardSocketOptions.SO_REUSEADDR) {
            return (T) getOption(SocketOptions.SO_REUSEADDR);
        } else if (name == StandardSocketOptions.IP_TOS) {
            return (T) getOption(SocketOptions.IP_TOS);
        } else if (name == StandardSocketOptions.IP_MULTICAST_IF &&
            (getDatagramSocket() instanceof MulticastSocket)) {
            return (T) getOption(SocketOptions.IP_MULTICAST_IF2);
        } else if (name == StandardSocketOptions.IP_MULTICAST_TTL &&
            (getDatagramSocket() instanceof MulticastSocket)) {
            Integer ttl = getTimeToLive();
            return (T)ttl;
        } else if (name == StandardSocketOptions.IP_MULTICAST_LOOP &&
            (getDatagramSocket() instanceof MulticastSocket)) {
            return (T) getOption(SocketOptions.IP_MULTICAST_LOOP);
        } else {
            throw new UnsupportedOperationException("unsupported option");
        }
    }

    /**
     * Gets the datagram socket file descriptor.
     * <p>
     *  获取数据报套接字文件描述符。
     * 
     * @return a {@code FileDescriptor} object representing the datagram socket
     * file descriptor
     */
    protected FileDescriptor getFileDescriptor() {
        return fd;
    }
}
