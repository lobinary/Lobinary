/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * This class represents a proxy setting, typically a type (http, socks) and
 * a socket address.
 * A {@code Proxy} is an immutable object.
 *
 * <p>
 *  此类表示代理设置,通常是类型(http,socks)和套接字地址。 {@code Proxy}是一个不可变的对象。
 * 
 * 
 * @see     java.net.ProxySelector
 * @author Yingxian Wang
 * @author Jean-Christophe Collet
 * @since   1.5
 */
public class Proxy {

    /**
     * Represents the proxy type.
     *
     * <p>
     *  表示代理类型。
     * 
     * 
     * @since 1.5
     */
    public enum Type {
        /**
         * Represents a direct connection, or the absence of a proxy.
         * <p>
         *  表示直接连接或缺少代理。
         * 
         */
        DIRECT,
        /**
         * Represents proxy for high level protocols such as HTTP or FTP.
         * <p>
         *  表示高级协议(如HTTP或FTP)的代理。
         * 
         */
        HTTP,
        /**
         * Represents a SOCKS (V4 or V5) proxy.
         * <p>
         *  表示SOCKS(V4或V5)代理。
         * 
         */
        SOCKS
    };

    private Type type;
    private SocketAddress sa;

    /**
     * A proxy setting that represents a {@code DIRECT} connection,
     * basically telling the protocol handler not to use any proxying.
     * Used, for instance, to create sockets bypassing any other global
     * proxy settings (like SOCKS):
     * <P>
     * {@code Socket s = new Socket(Proxy.NO_PROXY);}
     *
     * <p>
     *  代表{@code DIRECT}连接的代理设置,基本上告诉协议处理程序不要使用任何代理。例如,用于创建绕过任何其他全局代理设置(如SOCKS)的套接字：
     * <P>
     *  {@code Socket s = new Socket(Proxy.NO_PROXY);}
     * 
     */
    public final static Proxy NO_PROXY = new Proxy();

    // Creates the proxy that represents a {@code DIRECT} connection.
    private Proxy() {
        type = Type.DIRECT;
        sa = null;
    }

    /**
     * Creates an entry representing a PROXY connection.
     * Certain combinations are illegal. For instance, for types Http, and
     * Socks, a SocketAddress <b>must</b> be provided.
     * <P>
     * Use the {@code Proxy.NO_PROXY} constant
     * for representing a direct connection.
     *
     * <p>
     *  创建表示PROXY连接的条目。某些组合是非法的。例如,对于类型Http和Socks,必须提供SocketAddress <b> </b>。
     * <P>
     *  使用{@code Proxy.NO_PROXY}常量来表示直接连接。
     * 
     * 
     * @param type the {@code Type} of the proxy
     * @param sa the {@code SocketAddress} for that proxy
     * @throws IllegalArgumentException when the type and the address are
     * incompatible
     */
    public Proxy(Type type, SocketAddress sa) {
        if ((type == Type.DIRECT) || !(sa instanceof InetSocketAddress))
            throw new IllegalArgumentException("type " + type + " is not compatible with address " + sa);
        this.type = type;
        this.sa = sa;
    }

    /**
     * Returns the proxy type.
     *
     * <p>
     *  返回代理类型。
     * 
     * 
     * @return a Type representing the proxy type
     */
    public Type type() {
        return type;
    }

    /**
     * Returns the socket address of the proxy, or
     * {@code null} if its a direct connection.
     *
     * <p>
     *  返回代理的套接字地址,如果是直接连接则返回{@code null}。
     * 
     * 
     * @return a {@code SocketAddress} representing the socket end
     *         point of the proxy
     */
    public SocketAddress address() {
        return sa;
    }

    /**
     * Constructs a string representation of this Proxy.
     * This String is constructed by calling toString() on its type
     * and concatenating " @ " and the toString() result from its address
     * if its type is not {@code DIRECT}.
     *
     * <p>
     *  构造此代理的字符串表示。这个字符串是通过调用toString()类型和连接"@"和toString()结果从它的地址,如果其类型不是{@code DIRECT}构造。
     * 
     * 
     * @return  a string representation of this object.
     */
    public String toString() {
        if (type() == Type.DIRECT)
            return "DIRECT";
        return type() + " @ " + address();
    }

        /**
     * Compares this object against the specified object.
     * The result is {@code true} if and only if the argument is
     * not {@code null} and it represents the same proxy as
     * this object.
     * <p>
     * Two instances of {@code Proxy} represent the same
     * address if both the SocketAddresses and type are equal.
     *
     * <p>
     *  将此对象与指定的对象进行比较。结果是{@code true}当且仅当参数不是{@code null},它代表与此对象相同的代理。
     * <p>
     * 如果SocketAddresses和type都相等,{@code Proxy}的两个实例表示相同的地址。
     * 
     * 
     * @param   obj   the object to compare against.
     * @return  {@code true} if the objects are the same;
     *          {@code false} otherwise.
     * @see java.net.InetSocketAddress#equals(java.lang.Object)
     */
    public final boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Proxy))
            return false;
        Proxy p = (Proxy) obj;
        if (p.type() == type()) {
            if (address() == null) {
                return (p.address() == null);
            } else
                return address().equals(p.address());
        }
        return false;
    }

    /**
     * Returns a hashcode for this Proxy.
     *
     * <p>
     * 
     * @return  a hash code value for this Proxy.
     */
    public final int hashCode() {
        if (address() == null)
            return type().hashCode();
        return type().hashCode() + address().hashCode();
    }
}
