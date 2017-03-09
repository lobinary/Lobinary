/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * This interface defines a factory for datagram socket implementations. It
 * is used by the classes {@code DatagramSocket} to create actual socket
 * implementations.
 *
 * <p>
 *  此接口为数据报套接字实现定义了一个工厂。它由类{@code DatagramSocket}用于创建实际的套接字实现。
 * 
 * 
 * @author  Yingxian Wang
 * @see     java.net.DatagramSocket
 * @since   1.3
 */
public
interface DatagramSocketImplFactory {
    /**
     * Creates a new {@code DatagramSocketImpl} instance.
     *
     * <p>
     *  创建一个新的{@code DatagramSocketImpl}实例。
     * 
     * @return  a new instance of {@code DatagramSocketImpl}.
     * @see     java.net.DatagramSocketImpl
     */
    DatagramSocketImpl createDatagramSocketImpl();
}
