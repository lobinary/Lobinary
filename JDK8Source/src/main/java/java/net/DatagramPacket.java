/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * This class represents a datagram packet.
 * <p>
 * Datagram packets are used to implement a connectionless packet
 * delivery service. Each message is routed from one machine to
 * another based solely on information contained within that packet.
 * Multiple packets sent from one machine to another might be routed
 * differently, and might arrive in any order. Packet delivery is
 * not guaranteed.
 *
 * <p>
 *  这个类表示一个数据包。
 * <p>
 *  数据报分组用于实现无连接分组传递服务。每个消息仅仅基于包中包含的信息从一个机器路由到另一个机器。从一个机器发送到另一个机器的多个分组可能被不同地路由,并且可能以任何顺序到达。不保证包交付。
 * 
 * 
 * @author  Pavani Diwanji
 * @author  Benjamin Renaud
 * @since   JDK1.0
 */
public final
class DatagramPacket {

    /**
     * Perform class initialization
     * <p>
     *  执行类初始化
     * 
     */
    static {
        java.security.AccessController.doPrivileged(
            new java.security.PrivilegedAction<Void>() {
                public Void run() {
                    System.loadLibrary("net");
                    return null;
                }
            });
        init();
    }

    /*
     * The fields of this class are package-private since DatagramSocketImpl
     * classes needs to access them.
     * <p>
     *  这个类的字段是package-private,因为DatagramSocketImpl类需要访问它们。
     * 
     */
    byte[] buf;
    int offset;
    int length;
    int bufLength;
    InetAddress address;
    int port;

    /**
     * Constructs a {@code DatagramPacket} for receiving packets of
     * length {@code length}, specifying an offset into the buffer.
     * <p>
     * The {@code length} argument must be less than or equal to
     * {@code buf.length}.
     *
     * <p>
     *  构造{@code DatagramPacket}以接收长度为{@code length}的数据包,指定缓冲区中的偏移量。
     * <p>
     *  {@code length}参数必须小于或等于{@code buf.length}。
     * 
     * 
     * @param   buf      buffer for holding the incoming datagram.
     * @param   offset   the offset for the buffer
     * @param   length   the number of bytes to read.
     *
     * @since 1.2
     */
    public DatagramPacket(byte buf[], int offset, int length) {
        setData(buf, offset, length);
        this.address = null;
        this.port = -1;
    }

    /**
     * Constructs a {@code DatagramPacket} for receiving packets of
     * length {@code length}.
     * <p>
     * The {@code length} argument must be less than or equal to
     * {@code buf.length}.
     *
     * <p>
     *  构造用于接收长度为{@code length}的数据包的{@code DatagramPacket}。
     * <p>
     *  {@code length}参数必须小于或等于{@code buf.length}。
     * 
     * 
     * @param   buf      buffer for holding the incoming datagram.
     * @param   length   the number of bytes to read.
     */
    public DatagramPacket(byte buf[], int length) {
        this (buf, 0, length);
    }

    /**
     * Constructs a datagram packet for sending packets of length
     * {@code length} with offset {@code ioffset}to the
     * specified port number on the specified host. The
     * {@code length} argument must be less than or equal to
     * {@code buf.length}.
     *
     * <p>
     *  构造一个数据报数据包,​​用于将长度为{@code length},偏移量为{@code ioffset}的数据包发送到指定主机上指定的端口号。
     *  {@code length}参数必须小于或等于{@code buf.length}。
     * 
     * 
     * @param   buf      the packet data.
     * @param   offset   the packet data offset.
     * @param   length   the packet data length.
     * @param   address  the destination address.
     * @param   port     the destination port number.
     * @see java.net.InetAddress
     *
     * @since 1.2
     */
    public DatagramPacket(byte buf[], int offset, int length,
                          InetAddress address, int port) {
        setData(buf, offset, length);
        setAddress(address);
        setPort(port);
    }

    /**
     * Constructs a datagram packet for sending packets of length
     * {@code length} with offset {@code ioffset}to the
     * specified port number on the specified host. The
     * {@code length} argument must be less than or equal to
     * {@code buf.length}.
     *
     * <p>
     *  构造一个数据报数据包,​​用于将长度为{@code length},偏移量为{@code ioffset}的数据包发送到指定主机上指定的端口号。
     *  {@code length}参数必须小于或等于{@code buf.length}。
     * 
     * 
     * @param   buf      the packet data.
     * @param   offset   the packet data offset.
     * @param   length   the packet data length.
     * @param   address  the destination socket address.
     * @throws  IllegalArgumentException if address type is not supported
     * @see java.net.InetAddress
     *
     * @since 1.4
     */
    public DatagramPacket(byte buf[], int offset, int length, SocketAddress address) {
        setData(buf, offset, length);
        setSocketAddress(address);
    }

    /**
     * Constructs a datagram packet for sending packets of length
     * {@code length} to the specified port number on the specified
     * host. The {@code length} argument must be less than or equal
     * to {@code buf.length}.
     *
     * <p>
     * 构造用于将长度为{@code length}的数据包发送到指定主机上指定的端口号的数据报数据包。 {@code length}参数必须小于或等于{@code buf.length}。
     * 
     * 
     * @param   buf      the packet data.
     * @param   length   the packet length.
     * @param   address  the destination address.
     * @param   port     the destination port number.
     * @see     java.net.InetAddress
     */
    public DatagramPacket(byte buf[], int length,
                          InetAddress address, int port) {
        this(buf, 0, length, address, port);
    }

    /**
     * Constructs a datagram packet for sending packets of length
     * {@code length} to the specified port number on the specified
     * host. The {@code length} argument must be less than or equal
     * to {@code buf.length}.
     *
     * <p>
     *  构造用于将长度为{@code length}的数据包发送到指定主机上指定的端口号的数据报数据包。 {@code length}参数必须小于或等于{@code buf.length}。
     * 
     * 
     * @param   buf      the packet data.
     * @param   length   the packet length.
     * @param   address  the destination address.
     * @throws  IllegalArgumentException if address type is not supported
     * @since 1.4
     * @see     java.net.InetAddress
     */
    public DatagramPacket(byte buf[], int length, SocketAddress address) {
        this(buf, 0, length, address);
    }

    /**
     * Returns the IP address of the machine to which this datagram is being
     * sent or from which the datagram was received.
     *
     * <p>
     *  返回此数据报发送到或从其接收数据报的机器的IP地址。
     * 
     * 
     * @return  the IP address of the machine to which this datagram is being
     *          sent or from which the datagram was received.
     * @see     java.net.InetAddress
     * @see #setAddress(java.net.InetAddress)
     */
    public synchronized InetAddress getAddress() {
        return address;
    }

    /**
     * Returns the port number on the remote host to which this datagram is
     * being sent or from which the datagram was received.
     *
     * <p>
     *  返回此数据报发送到或从其接收数据报的远程主机上的端口号。
     * 
     * 
     * @return  the port number on the remote host to which this datagram is
     *          being sent or from which the datagram was received.
     * @see #setPort(int)
     */
    public synchronized int getPort() {
        return port;
    }

    /**
     * Returns the data buffer. The data received or the data to be sent
     * starts from the {@code offset} in the buffer,
     * and runs for {@code length} long.
     *
     * <p>
     *  返回数据缓冲区。接收的数据或要发送的数据从缓冲区中的{@code offset}开始,并运行{@code length} long。
     * 
     * 
     * @return  the buffer used to receive or  send data
     * @see #setData(byte[], int, int)
     */
    public synchronized byte[] getData() {
        return buf;
    }

    /**
     * Returns the offset of the data to be sent or the offset of the
     * data received.
     *
     * <p>
     *  返回要发送的数据的偏移量或接收到的数据的偏移量。
     * 
     * 
     * @return  the offset of the data to be sent or the offset of the
     *          data received.
     *
     * @since 1.2
     */
    public synchronized int getOffset() {
        return offset;
    }

    /**
     * Returns the length of the data to be sent or the length of the
     * data received.
     *
     * <p>
     *  返回要发送的数据的长度或接收的数据的长度。
     * 
     * 
     * @return  the length of the data to be sent or the length of the
     *          data received.
     * @see #setLength(int)
     */
    public synchronized int getLength() {
        return length;
    }

    /**
     * Set the data buffer for this packet. This sets the
     * data, length and offset of the packet.
     *
     * <p>
     *  设置此数据包的数据缓冲区。这将设置数据包的数据,长度和偏移量。
     * 
     * 
     * @param buf the buffer to set for this packet
     *
     * @param offset the offset into the data
     *
     * @param length the length of the data
     *       and/or the length of the buffer used to receive data
     *
     * @exception NullPointerException if the argument is null
     *
     * @see #getData
     * @see #getOffset
     * @see #getLength
     *
     * @since 1.2
     */
    public synchronized void setData(byte[] buf, int offset, int length) {
        /* this will check to see if buf is null */
        if (length < 0 || offset < 0 ||
            (length + offset) < 0 ||
            ((length + offset) > buf.length)) {
            throw new IllegalArgumentException("illegal length or offset");
        }
        this.buf = buf;
        this.length = length;
        this.bufLength = length;
        this.offset = offset;
    }

    /**
     * Sets the IP address of the machine to which this datagram
     * is being sent.
     * <p>
     *  设置要发送此数据报的机器的IP地址。
     * 
     * 
     * @param iaddr the {@code InetAddress}
     * @since   JDK1.1
     * @see #getAddress()
     */
    public synchronized void setAddress(InetAddress iaddr) {
        address = iaddr;
    }

    /**
     * Sets the port number on the remote host to which this datagram
     * is being sent.
     * <p>
     *  设置要发送此数据报的远程主机上的端口号。
     * 
     * 
     * @param iport the port number
     * @since   JDK1.1
     * @see #getPort()
     */
    public synchronized void setPort(int iport) {
        if (iport < 0 || iport > 0xFFFF) {
            throw new IllegalArgumentException("Port out of range:"+ iport);
        }
        port = iport;
    }

    /**
     * Sets the SocketAddress (usually IP address + port number) of the remote
     * host to which this datagram is being sent.
     *
     * <p>
     *  设置此数据报发送到的远程主机的SocketAddress(通常为IP地址+端口号)。
     * 
     * 
     * @param address the {@code SocketAddress}
     * @throws  IllegalArgumentException if address is null or is a
     *          SocketAddress subclass not supported by this socket
     *
     * @since 1.4
     * @see #getSocketAddress
     */
    public synchronized void setSocketAddress(SocketAddress address) {
        if (address == null || !(address instanceof InetSocketAddress))
            throw new IllegalArgumentException("unsupported address type");
        InetSocketAddress addr = (InetSocketAddress) address;
        if (addr.isUnresolved())
            throw new IllegalArgumentException("unresolved address");
        setAddress(addr.getAddress());
        setPort(addr.getPort());
    }

    /**
     * Gets the SocketAddress (usually IP address + port number) of the remote
     * host that this packet is being sent to or is coming from.
     *
     * <p>
     *  获取此数据包发送到或来自的远程主机的SocketAddress(通常为IP地址+端口号)。
     * 
     * 
     * @return the {@code SocketAddress}
     * @since 1.4
     * @see #setSocketAddress
     */
    public synchronized SocketAddress getSocketAddress() {
        return new InetSocketAddress(getAddress(), getPort());
    }

    /**
     * Set the data buffer for this packet. With the offset of
     * this DatagramPacket set to 0, and the length set to
     * the length of {@code buf}.
     *
     * <p>
     * 设置此数据包的数据缓冲区。将此DatagramPacket的偏移量设置为0,并将长度设置为{@code buf}的长度。
     * 
     * 
     * @param buf the buffer to set for this packet.
     *
     * @exception NullPointerException if the argument is null.
     *
     * @see #getLength
     * @see #getData
     *
     * @since JDK1.1
     */
    public synchronized void setData(byte[] buf) {
        if (buf == null) {
            throw new NullPointerException("null packet buffer");
        }
        this.buf = buf;
        this.offset = 0;
        this.length = buf.length;
        this.bufLength = buf.length;
    }

    /**
     * Set the length for this packet. The length of the packet is
     * the number of bytes from the packet's data buffer that will be
     * sent, or the number of bytes of the packet's data buffer that
     * will be used for receiving data. The length must be lesser or
     * equal to the offset plus the length of the packet's buffer.
     *
     * <p>
     *  设置此数据包的长度。分组的长度是将要发送的分组的数据缓冲区的字节数,或者将用于接收数据的分组的数据缓冲区的字节数。长度必须小于或等于偏移量加上包缓冲区的长度。
     * 
     * 
     * @param length the length to set for this packet.
     *
     * @exception IllegalArgumentException if the length is negative
     * of if the length is greater than the packet's data buffer
     * length.
     *
     * @see #getLength
     * @see #setData
     *
     * @since JDK1.1
     */
    public synchronized void setLength(int length) {
        if ((length + offset) > buf.length || length < 0 ||
            (length + offset) < 0) {
            throw new IllegalArgumentException("illegal length");
        }
        this.length = length;
        this.bufLength = this.length;
    }

    /**
     * Perform class load-time initializations.
     * <p>
     *  执行类装入时初始化。
     */
    private native static void init();
}
