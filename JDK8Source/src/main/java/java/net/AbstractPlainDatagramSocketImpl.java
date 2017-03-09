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
import java.security.AccessController;
import sun.net.ResourceManager;

/**
 * Abstract datagram and multicast socket implementation base class.
 * Note: This is not a public class, so that applets cannot call
 * into the implementation directly and hence cannot bypass the
 * security checks present in the DatagramSocket and MulticastSocket
 * classes.
 *
 * <p>
 *  抽象数据报和组播套接字实现基类。注意：这不是一个公共类,所以applet不能直接调用到实现,因此不能绕过DatagramSocket和MulticastSocket类中存在的安全检查。
 * 
 * 
 * @author Pavani Diwanji
 */

abstract class AbstractPlainDatagramSocketImpl extends DatagramSocketImpl
{
    /* timeout value for receive() */
    int timeout = 0;
    boolean connected = false;
    private int trafficClass = 0;
    protected InetAddress connectedAddress = null;
    private int connectedPort = -1;

    private static final String os = AccessController.doPrivileged(
        new sun.security.action.GetPropertyAction("os.name")
    );

    /**
     * flag set if the native connect() call not to be used
     * <p>
     *  如果本机connect()调用不使用,则设置标志
     * 
     */
    private final static boolean connectDisabled = os.contains("OS X");

    /**
     * Load net library into runtime.
     * <p>
     *  将网络库加载到运行时。
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

    /**
     * Creates a datagram socket
     * <p>
     *  创建数据报套接字
     * 
     */
    protected synchronized void create() throws SocketException {
        ResourceManager.beforeUdpCreate();
        fd = new FileDescriptor();
        try {
            datagramSocketCreate();
        } catch (SocketException ioe) {
            ResourceManager.afterUdpClose();
            fd = null;
            throw ioe;
        }
    }

    /**
     * Binds a datagram socket to a local port.
     * <p>
     *  将数据报套接字绑定到本地端口。
     * 
     */
    protected synchronized void bind(int lport, InetAddress laddr)
        throws SocketException {
        bind0(lport, laddr);
    }

    protected abstract void bind0(int lport, InetAddress laddr)
        throws SocketException;

    /**
     * Sends a datagram packet. The packet contains the data and the
     * destination address to send the packet to.
     * <p>
     *  发送数据包。数据包包含要发送数据包的数据和目标地址。
     * 
     * 
     * @param p the packet to be sent.
     */
    protected abstract void send(DatagramPacket p) throws IOException;

    /**
     * Connects a datagram socket to a remote destination. This associates the remote
     * address with the local socket so that datagrams may only be sent to this destination
     * and received from this destination.
     * <p>
     *  将数据报套接字连接到远程目标。这将远程地址与本地套接字相关联,使得数据报可以仅被发送到该目的地并从该目的地接收。
     * 
     * 
     * @param address the remote InetAddress to connect to
     * @param port the remote port number
     */
    protected void connect(InetAddress address, int port) throws SocketException {
        connect0(address, port);
        connectedAddress = address;
        connectedPort = port;
        connected = true;
    }

    /**
     * Disconnects a previously connected socket. Does nothing if the socket was
     * not connected already.
     * <p>
     *  断开以前连接的插座。如果套接字未连接,则不执行任何操作。
     * 
     */
    protected void disconnect() {
        disconnect0(connectedAddress.holder().getFamily());
        connected = false;
        connectedAddress = null;
        connectedPort = -1;
    }

    /**
     * Peek at the packet to see who it is from.
     * <p>
     *  偷看包,看看它是从哪里来的。
     * 
     * 
     * @param i the address to populate with the sender address
     */
    protected abstract int peek(InetAddress i) throws IOException;
    protected abstract int peekData(DatagramPacket p) throws IOException;
    /**
     * Receive the datagram packet.
     * <p>
     *  接收数据包。
     * 
     * 
     * @param p the packet to receive into
     */
    protected synchronized void receive(DatagramPacket p)
        throws IOException {
        receive0(p);
    }

    protected abstract void receive0(DatagramPacket p)
        throws IOException;

    /**
     * Set the TTL (time-to-live) option.
     * <p>
     *  设置TTL(存活时间)选项。
     * 
     * 
     * @param ttl TTL to be set.
     */
    protected abstract void setTimeToLive(int ttl) throws IOException;

    /**
     * Get the TTL (time-to-live) option.
     * <p>
     *  获取TTL(生存时间)选项。
     * 
     */
    protected abstract int getTimeToLive() throws IOException;

    /**
     * Set the TTL (time-to-live) option.
     * <p>
     *  设置TTL(存活时间)选项。
     * 
     * 
     * @param ttl TTL to be set.
     */
    @Deprecated
    protected abstract void setTTL(byte ttl) throws IOException;

    /**
     * Get the TTL (time-to-live) option.
     * <p>
     *  获取TTL(生存时间)选项。
     * 
     */
    @Deprecated
    protected abstract byte getTTL() throws IOException;

    /**
     * Join the multicast group.
     * <p>
     *  加入组播组。
     * 
     * 
     * @param inetaddr multicast address to join.
     */
    protected void join(InetAddress inetaddr) throws IOException {
        join(inetaddr, null);
    }

    /**
     * Leave the multicast group.
     * <p>
     *  离开组播组。
     * 
     * 
     * @param inetaddr multicast address to leave.
     */
    protected void leave(InetAddress inetaddr) throws IOException {
        leave(inetaddr, null);
    }
    /**
     * Join the multicast group.
     * <p>
     *  加入组播组。
     * 
     * 
     * @param mcastaddr multicast address to join.
     * @param netIf specifies the local interface to receive multicast
     *        datagram packets
     * @throws  IllegalArgumentException if mcastaddr is null or is a
     *          SocketAddress subclass not supported by this socket
     * @since 1.4
     */

    protected void joinGroup(SocketAddress mcastaddr, NetworkInterface netIf)
        throws IOException {
        if (mcastaddr == null || !(mcastaddr instanceof InetSocketAddress))
            throw new IllegalArgumentException("Unsupported address type");
        join(((InetSocketAddress)mcastaddr).getAddress(), netIf);
    }

    protected abstract void join(InetAddress inetaddr, NetworkInterface netIf)
        throws IOException;

    /**
     * Leave the multicast group.
     * <p>
     *  离开组播组。
     * 
     * 
     * @param mcastaddr  multicast address to leave.
     * @param netIf specified the local interface to leave the group at
     * @throws  IllegalArgumentException if mcastaddr is null or is a
     *          SocketAddress subclass not supported by this socket
     * @since 1.4
     */
    protected void leaveGroup(SocketAddress mcastaddr, NetworkInterface netIf)
        throws IOException {
        if (mcastaddr == null || !(mcastaddr instanceof InetSocketAddress))
            throw new IllegalArgumentException("Unsupported address type");
        leave(((InetSocketAddress)mcastaddr).getAddress(), netIf);
    }

    protected abstract void leave(InetAddress inetaddr, NetworkInterface netIf)
        throws IOException;

    /**
     * Close the socket.
     * <p>
     *  关闭套接字。
     * 
     */
    protected void close() {
        if (fd != null) {
            datagramSocketClose();
            ResourceManager.afterUdpClose();
            fd = null;
        }
    }

    protected boolean isClosed() {
        return (fd == null) ? true : false;
    }

    protected void finalize() {
        close();
    }

    /**
     * set a value - since we only support (setting) binary options
     * here, o must be a Boolean
     * <p>
     *  设置一个值 - 因为我们只支持(设置)二进制选项在这里,o必须是一个布尔值
     * 
     */

     public void setOption(int optID, Object o) throws SocketException {
         if (isClosed()) {
             throw new SocketException("Socket Closed");
         }
         switch (optID) {
            /* check type safety b4 going native.  These should never
             * fail, since only java.Socket* has access to
             * PlainSocketImpl.setOption().
             * <p>
             *  失败,因为只有java.Socket *可以访问PlainSocketImpl.setOption()。
             * 
             */
         case SO_TIMEOUT:
             if (o == null || !(o instanceof Integer)) {
                 throw new SocketException("bad argument for SO_TIMEOUT");
             }
             int tmp = ((Integer) o).intValue();
             if (tmp < 0)
                 throw new IllegalArgumentException("timeout < 0");
             timeout = tmp;
             return;
         case IP_TOS:
             if (o == null || !(o instanceof Integer)) {
                 throw new SocketException("bad argument for IP_TOS");
             }
             trafficClass = ((Integer)o).intValue();
             break;
         case SO_REUSEADDR:
             if (o == null || !(o instanceof Boolean)) {
                 throw new SocketException("bad argument for SO_REUSEADDR");
             }
             break;
         case SO_BROADCAST:
             if (o == null || !(o instanceof Boolean)) {
                 throw new SocketException("bad argument for SO_BROADCAST");
             }
             break;
         case SO_BINDADDR:
             throw new SocketException("Cannot re-bind Socket");
         case SO_RCVBUF:
         case SO_SNDBUF:
             if (o == null || !(o instanceof Integer) ||
                 ((Integer)o).intValue() < 0) {
                 throw new SocketException("bad argument for SO_SNDBUF or " +
                                           "SO_RCVBUF");
             }
             break;
         case IP_MULTICAST_IF:
             if (o == null || !(o instanceof InetAddress))
                 throw new SocketException("bad argument for IP_MULTICAST_IF");
             break;
         case IP_MULTICAST_IF2:
             if (o == null || !(o instanceof NetworkInterface))
                 throw new SocketException("bad argument for IP_MULTICAST_IF2");
             break;
         case IP_MULTICAST_LOOP:
             if (o == null || !(o instanceof Boolean))
                 throw new SocketException("bad argument for IP_MULTICAST_LOOP");
             break;
         default:
             throw new SocketException("invalid option: " + optID);
         }
         socketSetOption(optID, o);
     }

    /*
     * get option's state - set or not
     * <p>
     * 获取选项的状态 - 设置与否
     */

    public Object getOption(int optID) throws SocketException {
        if (isClosed()) {
            throw new SocketException("Socket Closed");
        }

        Object result;

        switch (optID) {
            case SO_TIMEOUT:
                result = new Integer(timeout);
                break;

            case IP_TOS:
                result = socketGetOption(optID);
                if ( ((Integer)result).intValue() == -1) {
                    result = new Integer(trafficClass);
                }
                break;

            case SO_BINDADDR:
            case IP_MULTICAST_IF:
            case IP_MULTICAST_IF2:
            case SO_RCVBUF:
            case SO_SNDBUF:
            case IP_MULTICAST_LOOP:
            case SO_REUSEADDR:
            case SO_BROADCAST:
                result = socketGetOption(optID);
                break;

            default:
                throw new SocketException("invalid option: " + optID);
        }

        return result;
    }

    protected abstract void datagramSocketCreate() throws SocketException;
    protected abstract void datagramSocketClose();
    protected abstract void socketSetOption(int opt, Object val)
        throws SocketException;
    protected abstract Object socketGetOption(int opt) throws SocketException;

    protected abstract void connect0(InetAddress address, int port) throws SocketException;
    protected abstract void disconnect0(int family);

    protected boolean nativeConnectDisabled() {
        return connectDisabled;
    }

    native int dataAvailable();
    private static native void init();
}
