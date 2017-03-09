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
 * A socket option associated with a socket.
 *
 * <p> In the {@link java.nio.channels channels} package, the {@link
 * java.nio.channels.NetworkChannel} interface defines the {@link
 * java.nio.channels.NetworkChannel#setOption(SocketOption,Object) setOption}
 * and {@link java.nio.channels.NetworkChannel#getOption(SocketOption) getOption}
 * methods to set and query the channel's socket options.
 *
 * <p>
 *  与套接字相关联的套接字选项。
 * 
 *  <p>在{@link java.nio.channels渠道}包中,{@link java.nio.channels.NetworkChannel}接口定义了{@link java.nio.channels.NetworkChannel#setOption(SocketOption,Object)setOption }
 * 和{@link java.nio.channels.NetworkChannel#getOption(SocketOption)getOption}方法来设置和查询通道的套接字选项。
 * 
 * 
 * @param   <T>     The type of the socket option value.
 *
 * @since 1.7
 *
 * @see StandardSocketOptions
 */

public interface SocketOption<T> {

    /**
     * Returns the name of the socket option.
     *
     * <p>
     *  返回套接字选项的名称。
     * 
     * 
     * @return the name of the socket option
     */
    String name();

    /**
     * Returns the type of the socket option value.
     *
     * <p>
     *  返回套接字选项值的类型。
     * 
     * @return the type of the socket option value
     */
    Class<T> type();
}
