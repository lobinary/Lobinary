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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileDescriptor;

/**
 * The abstract class {@code SocketImpl} is a common superclass
 * of all classes that actually implement sockets. It is used to
 * create both client and server sockets.
 * <p>
 * A "plain" socket implements these methods exactly as
 * described, without attempting to go through a firewall or proxy.
 *
 * <p>
 *  抽象类{@code SocketImpl}是实际实现套接字的所有类的一个通用超类。它用于创建客户端和服务器套接字。
 * <p>
 *  "纯"套接字完全按照所描述的方式实现这些方法,而不尝试通过防火墙或代理。
 * 
 * 
 * @author  unascribed
 * @since   JDK1.0
 */
public abstract class SocketImpl implements SocketOptions {
    /**
     * The actual Socket object.
     * <p>
     *  实际的Socket对象。
     * 
     */
    Socket socket = null;
    ServerSocket serverSocket = null;

    /**
     * The file descriptor object for this socket.
     * <p>
     *  此套接字的文件描述符对象。
     * 
     */
    protected FileDescriptor fd;

    /**
     * The IP address of the remote end of this socket.
     * <p>
     *  此套接字的远程端的IP地址。
     * 
     */
    protected InetAddress address;

    /**
     * The port number on the remote host to which this socket is connected.
     * <p>
     *  连接此套接字的远程主机上的端口号。
     * 
     */
    protected int port;

    /**
     * The local port number to which this socket is connected.
     * <p>
     *  此套接字连接到的本地端口号。
     * 
     */
    protected int localport;

    /**
     * Creates either a stream or a datagram socket.
     *
     * <p>
     *  创建流或数据报套接字。
     * 
     * 
     * @param      stream   if {@code true}, create a stream socket;
     *                      otherwise, create a datagram socket.
     * @exception  IOException  if an I/O error occurs while creating the
     *               socket.
     */
    protected abstract void create(boolean stream) throws IOException;

    /**
     * Connects this socket to the specified port on the named host.
     *
     * <p>
     *  将此套接字连接到指定主机上的指定端口。
     * 
     * 
     * @param      host   the name of the remote host.
     * @param      port   the port number.
     * @exception  IOException  if an I/O error occurs when connecting to the
     *               remote host.
     */
    protected abstract void connect(String host, int port) throws IOException;

    /**
     * Connects this socket to the specified port number on the specified host.
     *
     * <p>
     *  将此套接字连接到指定主机上的指定端口号。
     * 
     * 
     * @param      address   the IP address of the remote host.
     * @param      port      the port number.
     * @exception  IOException  if an I/O error occurs when attempting a
     *               connection.
     */
    protected abstract void connect(InetAddress address, int port) throws IOException;

    /**
     * Connects this socket to the specified port number on the specified host.
     * A timeout of zero is interpreted as an infinite timeout. The connection
     * will then block until established or an error occurs.
     *
     * <p>
     *  将此套接字连接到指定主机上的指定端口号。超时为零被解释为无限超时。然后,连接将阻塞,直到建立或发生错误。
     * 
     * 
     * @param      address   the Socket address of the remote host.
     * @param     timeout  the timeout value, in milliseconds, or zero for no timeout.
     * @exception  IOException  if an I/O error occurs when attempting a
     *               connection.
     * @since 1.4
     */
    protected abstract void connect(SocketAddress address, int timeout) throws IOException;

    /**
     * Binds this socket to the specified local IP address and port number.
     *
     * <p>
     *  将此套接字绑定到指定的本地IP地址和端口号。
     * 
     * 
     * @param      host   an IP address that belongs to a local interface.
     * @param      port   the port number.
     * @exception  IOException  if an I/O error occurs when binding this socket.
     */
    protected abstract void bind(InetAddress host, int port) throws IOException;

    /**
     * Sets the maximum queue length for incoming connection indications
     * (a request to connect) to the {@code count} argument. If a
     * connection indication arrives when the queue is full, the
     * connection is refused.
     *
     * <p>
     *  将传入连接指示(连接请求)的最大队列长度设置为{@code count}参数。如果在队列已满时连接指示到达,则拒绝连接。
     * 
     * 
     * @param      backlog   the maximum length of the queue.
     * @exception  IOException  if an I/O error occurs when creating the queue.
     */
    protected abstract void listen(int backlog) throws IOException;

    /**
     * Accepts a connection.
     *
     * <p>
     *  接受连接。
     * 
     * 
     * @param      s   the accepted connection.
     * @exception  IOException  if an I/O error occurs when accepting the
     *               connection.
     */
    protected abstract void accept(SocketImpl s) throws IOException;

    /**
     * Returns an input stream for this socket.
     *
     * <p>
     *  返回此套接字的输入流。
     * 
     * 
     * @return     a stream for reading from this socket.
     * @exception  IOException  if an I/O error occurs when creating the
     *               input stream.
    */
    protected abstract InputStream getInputStream() throws IOException;

    /**
     * Returns an output stream for this socket.
     *
     * <p>
     *  返回此套接字的输出流。
     * 
     * 
     * @return     an output stream for writing to this socket.
     * @exception  IOException  if an I/O error occurs when creating the
     *               output stream.
     */
    protected abstract OutputStream getOutputStream() throws IOException;

    /**
     * Returns the number of bytes that can be read from this socket
     * without blocking.
     *
     * <p>
     * 返回可以从此套接字读取但不阻塞的字节数。
     * 
     * 
     * @return     the number of bytes that can be read from this socket
     *             without blocking.
     * @exception  IOException  if an I/O error occurs when determining the
     *               number of bytes available.
     */
    protected abstract int available() throws IOException;

    /**
     * Closes this socket.
     *
     * <p>
     *  关闭此套接字。
     * 
     * 
     * @exception  IOException  if an I/O error occurs when closing this socket.
     */
    protected abstract void close() throws IOException;

    /**
     * Places the input stream for this socket at "end of stream".
     * Any data sent to this socket is acknowledged and then
     * silently discarded.
     *
     * If you read from a socket input stream after invoking this method on the
     * socket, the stream's {@code available} method will return 0, and its
     * {@code read} methods will return {@code -1} (end of stream).
     *
     * <p>
     *  将此套接字的输入流放置在"流结束"。发送到此套接字的任何数据都将被确认,然后被静默丢弃。
     * 
     *  如果你在套接字上调用这个方法之后从套接字输入流中读取,流的{@code available}方法将返回0,它的{@code read}方法将返回{@code -1}(流结束)。
     * 
     * 
     * @exception IOException if an I/O error occurs when shutting down this
     * socket.
     * @see java.net.Socket#shutdownOutput()
     * @see java.net.Socket#close()
     * @see java.net.Socket#setSoLinger(boolean, int)
     * @since 1.3
     */
    protected void shutdownInput() throws IOException {
      throw new IOException("Method not implemented!");
    }

    /**
     * Disables the output stream for this socket.
     * For a TCP socket, any previously written data will be sent
     * followed by TCP's normal connection termination sequence.
     *
     * If you write to a socket output stream after invoking
     * shutdownOutput() on the socket, the stream will throw
     * an IOException.
     *
     * <p>
     *  禁用此套接字的输出流。对于TCP套接字,任何先前写入的数据将被发送,之后是TCP的正常连接终止序列。
     * 
     *  如果在套接字上调用shutdownOutput()之后写入套接字输出流,流将抛出一个IOException异常。
     * 
     * 
     * @exception IOException if an I/O error occurs when shutting down this
     * socket.
     * @see java.net.Socket#shutdownInput()
     * @see java.net.Socket#close()
     * @see java.net.Socket#setSoLinger(boolean, int)
     * @since 1.3
     */
    protected void shutdownOutput() throws IOException {
      throw new IOException("Method not implemented!");
    }

    /**
     * Returns the value of this socket's {@code fd} field.
     *
     * <p>
     *  返回此套接字的{@code fd}字段的值。
     * 
     * 
     * @return  the value of this socket's {@code fd} field.
     * @see     java.net.SocketImpl#fd
     */
    protected FileDescriptor getFileDescriptor() {
        return fd;
    }

    /**
     * Returns the value of this socket's {@code address} field.
     *
     * <p>
     *  返回此套接字的{@code address}字段的值。
     * 
     * 
     * @return  the value of this socket's {@code address} field.
     * @see     java.net.SocketImpl#address
     */
    protected InetAddress getInetAddress() {
        return address;
    }

    /**
     * Returns the value of this socket's {@code port} field.
     *
     * <p>
     *  返回此套接字的{@code port}字段的值。
     * 
     * 
     * @return  the value of this socket's {@code port} field.
     * @see     java.net.SocketImpl#port
     */
    protected int getPort() {
        return port;
    }

    /**
     * Returns whether or not this SocketImpl supports sending
     * urgent data. By default, false is returned
     * unless the method is overridden in a sub-class
     *
     * <p>
     *  返回此SocketImpl是否支持发送紧急数据。默认情况下,返回false,除非该方法在子类中被覆盖
     * 
     * 
     * @return  true if urgent data supported
     * @see     java.net.SocketImpl#address
     * @since 1.4
     */
    protected boolean supportsUrgentData () {
        return false; // must be overridden in sub-class
    }

    /**
     * Send one byte of urgent data on the socket.
     * The byte to be sent is the low eight bits of the parameter
     * <p>
     *  在套接字上发送一个字节的紧急数据。要发送的字节是参数的低8位
     * 
     * 
     * @param data The byte of data to send
     * @exception IOException if there is an error
     *  sending the data.
     * @since 1.4
     */
    protected abstract void sendUrgentData (int data) throws IOException;

    /**
     * Returns the value of this socket's {@code localport} field.
     *
     * <p>
     *  返回此套接字的{@code localport}字段的值。
     * 
     * 
     * @return  the value of this socket's {@code localport} field.
     * @see     java.net.SocketImpl#localport
     */
    protected int getLocalPort() {
        return localport;
    }

    void setSocket(Socket soc) {
        this.socket = soc;
    }

    Socket getSocket() {
        return socket;
    }

    void setServerSocket(ServerSocket soc) {
        this.serverSocket = soc;
    }

    ServerSocket getServerSocket() {
        return serverSocket;
    }

    /**
     * Returns the address and port of this socket as a {@code String}.
     *
     * <p>
     *  以{@code String}形式返回此套接字的地址和端口。
     * 
     * 
     * @return  a string representation of this socket.
     */
    public String toString() {
        return "Socket[addr=" + getInetAddress() +
            ",port=" + getPort() + ",localport=" + getLocalPort()  + "]";
    }

    void reset() throws IOException {
        address = null;
        port = 0;
        localport = 0;
    }

    /**
     * Sets performance preferences for this socket.
     *
     * <p> Sockets use the TCP/IP protocol by default.  Some implementations
     * may offer alternative protocols which have different performance
     * characteristics than TCP/IP.  This method allows the application to
     * express its own preferences as to how these tradeoffs should be made
     * when the implementation chooses from the available protocols.
     *
     * <p> Performance preferences are described by three integers
     * whose values indicate the relative importance of short connection time,
     * low latency, and high bandwidth.  The absolute values of the integers
     * are irrelevant; in order to choose a protocol the values are simply
     * compared, with larger values indicating stronger preferences. Negative
     * values represent a lower priority than positive values. If the
     * application prefers short connection time over both low latency and high
     * bandwidth, for example, then it could invoke this method with the values
     * {@code (1, 0, 0)}.  If the application prefers high bandwidth above low
     * latency, and low latency above short connection time, then it could
     * invoke this method with the values {@code (0, 1, 2)}.
     *
     * By default, this method does nothing, unless it is overridden in a
     * a sub-class.
     *
     * <p>
     *  设置此套接字的性能首选项。
     * 
     * <p>套接字默认使用TCP / IP协议。一些实现可以提供具有与TCP / IP不同的性能特征的替代协议。该方法允许应用表达其自己关于当实现从可用协议选择时如何进行这些折衷的偏好。
     * 
     *  <p>性能偏好由三个整数描述,其值指示短连接时间,低延迟和高带宽的相对重要性。整数的绝对值是不相关的;为了选择协议,简单地比较值,较大的值指示较强的偏好。负值表示比正值低的优先级。
     * 
     * @param  connectionTime
     *         An {@code int} expressing the relative importance of a short
     *         connection time
     *
     * @param  latency
     *         An {@code int} expressing the relative importance of low
     *         latency
     *
     * @param  bandwidth
     *         An {@code int} expressing the relative importance of high
     *         bandwidth
     *
     * @since 1.5
     */
    protected void setPerformancePreferences(int connectionTime,
                                          int latency,
                                          int bandwidth)
    {
        /* Not implemented yet */
    }

    <T> void setOption(SocketOption<T> name, T value) throws IOException {
        if (name == StandardSocketOptions.SO_KEEPALIVE) {
            setOption(SocketOptions.SO_KEEPALIVE, value);
        } else if (name == StandardSocketOptions.SO_SNDBUF) {
            setOption(SocketOptions.SO_SNDBUF, value);
        } else if (name == StandardSocketOptions.SO_RCVBUF) {
            setOption(SocketOptions.SO_RCVBUF, value);
        } else if (name == StandardSocketOptions.SO_REUSEADDR) {
            setOption(SocketOptions.SO_REUSEADDR, value);
        } else if (name == StandardSocketOptions.SO_LINGER) {
            setOption(SocketOptions.SO_LINGER, value);
        } else if (name == StandardSocketOptions.IP_TOS) {
            setOption(SocketOptions.IP_TOS, value);
        } else if (name == StandardSocketOptions.TCP_NODELAY) {
            setOption(SocketOptions.TCP_NODELAY, value);
        } else {
            throw new UnsupportedOperationException("unsupported option");
        }
    }

    <T> T getOption(SocketOption<T> name) throws IOException {
        if (name == StandardSocketOptions.SO_KEEPALIVE) {
            return (T)getOption(SocketOptions.SO_KEEPALIVE);
        } else if (name == StandardSocketOptions.SO_SNDBUF) {
            return (T)getOption(SocketOptions.SO_SNDBUF);
        } else if (name == StandardSocketOptions.SO_RCVBUF) {
            return (T)getOption(SocketOptions.SO_RCVBUF);
        } else if (name == StandardSocketOptions.SO_REUSEADDR) {
            return (T)getOption(SocketOptions.SO_REUSEADDR);
        } else if (name == StandardSocketOptions.SO_LINGER) {
            return (T)getOption(SocketOptions.SO_LINGER);
        } else if (name == StandardSocketOptions.IP_TOS) {
            return (T)getOption(SocketOptions.IP_TOS);
        } else if (name == StandardSocketOptions.TCP_NODELAY) {
            return (T)getOption(SocketOptions.TCP_NODELAY);
        } else {
            throw new UnsupportedOperationException("unsupported option");
        }
    }
}
