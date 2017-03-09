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

import java.net.SocketOption;
import java.net.SocketAddress;
import java.util.Set;
import java.io.IOException;

/**
 * A channel to a network socket.
 *
 * <p> A channel that implements this interface is a channel to a network
 * socket. The {@link #bind(SocketAddress) bind} method is used to bind the
 * socket to a local {@link SocketAddress address}, the {@link #getLocalAddress()
 * getLocalAddress} method returns the address that the socket is bound to, and
 * the {@link #setOption(SocketOption,Object) setOption} and {@link
 * #getOption(SocketOption) getOption} methods are used to set and query socket
 * options.  An implementation of this interface should specify the socket options
 * that it supports.
 *
 * <p> The {@link #bind bind} and {@link #setOption setOption} methods that do
 * not otherwise have a value to return are specified to return the network
 * channel upon which they are invoked. This allows method invocations to be
 * chained. Implementations of this interface should specialize the return type
 * so that method invocations on the implementation class can be chained.
 *
 * <p>
 *  通道到网络套接字。
 * 
 *  <p>实现此接口的通道是网络套接字的通道。
 *  {@link #bind(SocketAddress)bind}方法用于将套接字绑定到本地{@link SocketAddress地址},{@link #getLocalAddress()getLocalAddress}
 * 方法返回套接字绑定的地址, {@link #setOption(SocketOption,Object)setOption}和{@link #getOption(SocketOption)getOption}
 * 方法用于设置和查询套接字选项。
 *  <p>实现此接口的通道是网络套接字的通道。这个接口的实现应该指定它支持的套接字选项。
 * 
 *  <p>指定了没有返回值的{@link #bind bind}和{@link #setOption setOption}方法返回调用它们的网络通道。这允许方法调用链接。
 * 此接口的实现应专门化返回类型,以便可以链接对实现类的方法调用。
 * 
 * 
 * @since 1.7
 */

public interface NetworkChannel
    extends Channel
{
    /**
     * Binds the channel's socket to a local address.
     *
     * <p> This method is used to establish an association between the socket and
     * a local address. Once an association is established then the socket remains
     * bound until the channel is closed. If the {@code local} parameter has the
     * value {@code null} then the socket will be bound to an address that is
     * assigned automatically.
     *
     * <p>
     *  将通道的套接字绑定到本地地址。
     * 
     *  <p>此方法用于在套接字和本地地址之间建立关联。一旦建立了关联,则套接字保持绑定,直到信道被关闭。
     * 如果{@code local}参数的值为{@code null},那么套接字将绑定到一个自动分配的地址。
     * 
     * 
     * @param   local
     *          The address to bind the socket, or {@code null} to bind the socket
     *          to an automatically assigned socket address
     *
     * @return  This channel
     *
     * @throws  AlreadyBoundException
     *          If the socket is already bound
     * @throws  UnsupportedAddressTypeException
     *          If the type of the given address is not supported
     * @throws  ClosedChannelException
     *          If the channel is closed
     * @throws  IOException
     *          If some other I/O error occurs
     * @throws  SecurityException
     *          If a security manager is installed and it denies an unspecified
     *          permission. An implementation of this interface should specify
     *          any required permissions.
     *
     * @see #getLocalAddress
     */
    NetworkChannel bind(SocketAddress local) throws IOException;

    /**
     * Returns the socket address that this channel's socket is bound to.
     *
     * <p> Where the channel is {@link #bind bound} to an Internet Protocol
     * socket address then the return value from this method is of type {@link
     * java.net.InetSocketAddress}.
     *
     * <p>
     * 返回此通道的套接字绑定到的套接字地址。
     * 
     *  <p>当频道是{@link #bind bound}到Internet协议套接字地址时,此方法的返回值的类型为{@link java.net.InetSocketAddress}。
     * 
     * 
     * @return  The socket address that the socket is bound to, or {@code null}
     *          if the channel's socket is not bound
     *
     * @throws  ClosedChannelException
     *          If the channel is closed
     * @throws  IOException
     *          If an I/O error occurs
     */
    SocketAddress getLocalAddress() throws IOException;

    /**
     * Sets the value of a socket option.
     *
     * <p>
     *  设置套接字选项的值。
     * 
     * 
     * @param   <T>
     *          The type of the socket option value
     * @param   name
     *          The socket option
     * @param   value
     *          The value of the socket option. A value of {@code null} may be
     *          a valid value for some socket options.
     *
     * @return  This channel
     *
     * @throws  UnsupportedOperationException
     *          If the socket option is not supported by this channel
     * @throws  IllegalArgumentException
     *          If the value is not a valid value for this socket option
     * @throws  ClosedChannelException
     *          If this channel is closed
     * @throws  IOException
     *          If an I/O error occurs
     *
     * @see java.net.StandardSocketOptions
     */
    <T> NetworkChannel setOption(SocketOption<T> name, T value) throws IOException;

    /**
     * Returns the value of a socket option.
     *
     * <p>
     *  返回套接字选项的值。
     * 
     * 
     * @param   <T>
     *          The type of the socket option value
     * @param   name
     *          The socket option
     *
     * @return  The value of the socket option. A value of {@code null} may be
     *          a valid value for some socket options.
     *
     * @throws  UnsupportedOperationException
     *          If the socket option is not supported by this channel
     * @throws  ClosedChannelException
     *          If this channel is closed
     * @throws  IOException
     *          If an I/O error occurs
     *
     * @see java.net.StandardSocketOptions
     */
    <T> T getOption(SocketOption<T> name) throws IOException;

    /**
     * Returns a set of the socket options supported by this channel.
     *
     * <p> This method will continue to return the set of options even after the
     * channel has been closed.
     *
     * <p>
     *  返回此通道支持的一组套接字选项。
     * 
     * 
     * @return  A set of the socket options supported by this channel
     */
    Set<SocketOption<?>> supportedOptions();
}
