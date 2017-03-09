/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.ObjectStreamField;

/**
 *
 * This class implements an IP Socket Address (IP address + port number)
 * It can also be a pair (hostname + port number), in which case an attempt
 * will be made to resolve the hostname. If resolution fails then the address
 * is said to be <I>unresolved</I> but can still be used on some circumstances
 * like connecting through a proxy.
 * <p>
 * It provides an immutable object used by sockets for binding, connecting, or
 * as returned values.
 * <p>
 * The <i>wildcard</i> is a special local IP address. It usually means "any"
 * and can only be used for {@code bind} operations.
 *
 * <p>
 *  此类实现IP套接字地址(IP地址+端口号)它也可以是一对(主机名+端口号),在这种情况下,将尝试解析主机名。
 * 如果解析失败,则该地址被认为是<I>未解析</I>,但仍然可以在某些情况下使用,例如通过代理连接。
 * <p>
 *  它提供了一个由套接字用于绑定,连接或返回值的不可变对象。
 * <p>
 *  通配符</i>是特殊的本地IP地址。它通常意味着"任何",并且只能用于{@code bind}操作。
 * 
 * 
 * @see java.net.Socket
 * @see java.net.ServerSocket
 * @since 1.4
 */
public class InetSocketAddress
    extends SocketAddress
{
    // Private implementation class pointed to by all public methods.
    private static class InetSocketAddressHolder {
        // The hostname of the Socket Address
        private String hostname;
        // The IP address of the Socket Address
        private InetAddress addr;
        // The port number of the Socket Address
        private int port;

        private InetSocketAddressHolder(String hostname, InetAddress addr, int port) {
            this.hostname = hostname;
            this.addr = addr;
            this.port = port;
        }

        private int getPort() {
            return port;
        }

        private InetAddress getAddress() {
            return addr;
        }

        private String getHostName() {
            if (hostname != null)
                return hostname;
            if (addr != null)
                return addr.getHostName();
            return null;
        }

        private String getHostString() {
            if (hostname != null)
                return hostname;
            if (addr != null) {
                if (addr.holder().getHostName() != null)
                    return addr.holder().getHostName();
                else
                    return addr.getHostAddress();
            }
            return null;
        }

        private boolean isUnresolved() {
            return addr == null;
        }

        @Override
        public String toString() {
            if (isUnresolved()) {
                return hostname + ":" + port;
            } else {
                return addr.toString() + ":" + port;
            }
        }

        @Override
        public final boolean equals(Object obj) {
            if (obj == null || !(obj instanceof InetSocketAddressHolder))
                return false;
            InetSocketAddressHolder that = (InetSocketAddressHolder)obj;
            boolean sameIP;
            if (addr != null)
                sameIP = addr.equals(that.addr);
            else if (hostname != null)
                sameIP = (that.addr == null) &&
                    hostname.equalsIgnoreCase(that.hostname);
            else
                sameIP = (that.addr == null) && (that.hostname == null);
            return sameIP && (port == that.port);
        }

        @Override
        public final int hashCode() {
            if (addr != null)
                return addr.hashCode() + port;
            if (hostname != null)
                return hostname.toLowerCase().hashCode() + port;
            return port;
        }
    }

    private final transient InetSocketAddressHolder holder;

    private static final long serialVersionUID = 5076001401234631237L;

    private static int checkPort(int port) {
        if (port < 0 || port > 0xFFFF)
            throw new IllegalArgumentException("port out of range:" + port);
        return port;
    }

    private static String checkHost(String hostname) {
        if (hostname == null)
            throw new IllegalArgumentException("hostname can't be null");
        return hostname;
    }

    /**
     * Creates a socket address where the IP address is the wildcard address
     * and the port number a specified value.
     * <p>
     * A valid port value is between 0 and 65535.
     * A port number of {@code zero} will let the system pick up an
     * ephemeral port in a {@code bind} operation.
     * <p>
     * <p>
     *  创建套接字地址,其中IP地址是通配符地址,端口号是指定的值。
     * <p>
     *  有效的端口值介于0和65535之间。{@code zero}的端口号将让系统在{@code bind}操作中选取一个临时端口。
     * <p>
     * 
     * @param   port    The port number
     * @throws IllegalArgumentException if the port parameter is outside the specified
     * range of valid port values.
     */
    public InetSocketAddress(int port) {
        this(InetAddress.anyLocalAddress(), port);
    }

    /**
     *
     * Creates a socket address from an IP address and a port number.
     * <p>
     * A valid port value is between 0 and 65535.
     * A port number of {@code zero} will let the system pick up an
     * ephemeral port in a {@code bind} operation.
     * <P>
     * A {@code null} address will assign the <i>wildcard</i> address.
     * <p>
     * <p>
     *  从IP地址和端口号创建套接字地址。
     * <p>
     *  有效的端口值介于0和65535之间。{@code zero}的端口号将让系统在{@code bind}操作中选取一个临时端口。
     * <P>
     *  {@code null}地址将分配<i>通配符</i>地址。
     * <p>
     * 
     * @param   addr    The IP address
     * @param   port    The port number
     * @throws IllegalArgumentException if the port parameter is outside the specified
     * range of valid port values.
     */
    public InetSocketAddress(InetAddress addr, int port) {
        holder = new InetSocketAddressHolder(
                        null,
                        addr == null ? InetAddress.anyLocalAddress() : addr,
                        checkPort(port));
    }

    /**
     *
     * Creates a socket address from a hostname and a port number.
     * <p>
     * An attempt will be made to resolve the hostname into an InetAddress.
     * If that attempt fails, the address will be flagged as <I>unresolved</I>.
     * <p>
     * If there is a security manager, its {@code checkConnect} method
     * is called with the host name as its argument to check the permission
     * to resolve it. This could result in a SecurityException.
     * <P>
     * A valid port value is between 0 and 65535.
     * A port number of {@code zero} will let the system pick up an
     * ephemeral port in a {@code bind} operation.
     * <P>
     * <p>
     *  从主机名和端口号创建套接字地址。
     * <p>
     *  将尝试将主机名解析为InetAddress。如果该尝试失败,则地址将标记为<I>未解析</I>。
     * <p>
     * 如果有安全管理器,则会调用其{@code checkConnect}方法,并以主机名作为其参数,以检查解析它的权限。这可能导致SecurityException。
     * <P>
     *  有效的端口值介于0和65535之间。{@code zero}的端口号将让系统在{@code bind}操作中选取一个临时端口。
     * <P>
     * 
     * @param   hostname the Host name
     * @param   port    The port number
     * @throws IllegalArgumentException if the port parameter is outside the range
     * of valid port values, or if the hostname parameter is <TT>null</TT>.
     * @throws SecurityException if a security manager is present and
     *                           permission to resolve the host name is
     *                           denied.
     * @see     #isUnresolved()
     */
    public InetSocketAddress(String hostname, int port) {
        checkHost(hostname);
        InetAddress addr = null;
        String host = null;
        try {
            addr = InetAddress.getByName(hostname);
        } catch(UnknownHostException e) {
            host = hostname;
        }
        holder = new InetSocketAddressHolder(host, addr, checkPort(port));
    }

    // private constructor for creating unresolved instances
    private InetSocketAddress(int port, String hostname) {
        holder = new InetSocketAddressHolder(hostname, null, port);
    }

    /**
     *
     * Creates an unresolved socket address from a hostname and a port number.
     * <p>
     * No attempt will be made to resolve the hostname into an InetAddress.
     * The address will be flagged as <I>unresolved</I>.
     * <p>
     * A valid port value is between 0 and 65535.
     * A port number of {@code zero} will let the system pick up an
     * ephemeral port in a {@code bind} operation.
     * <P>
     * <p>
     *  从主机名和端口号创建未解析的套接字地址。
     * <p>
     *  不会尝试将主机名解析为InetAddress。地址将标记为<I>未解决</I>。
     * <p>
     *  有效的端口值介于0和65535之间。{@code zero}的端口号将让系统在{@code bind}操作中选取一个临时端口。
     * <P>
     * 
     * @param   host    the Host name
     * @param   port    The port number
     * @throws IllegalArgumentException if the port parameter is outside
     *                  the range of valid port values, or if the hostname
     *                  parameter is <TT>null</TT>.
     * @see     #isUnresolved()
     * @return  a {@code InetSocketAddress} representing the unresolved
     *          socket address
     * @since 1.5
     */
    public static InetSocketAddress createUnresolved(String host, int port) {
        return new InetSocketAddress(checkPort(port), checkHost(host));
    }

    /**
    /* <p>
    /* 
     * @serialField hostname String
     * @serialField addr InetAddress
     * @serialField port int
     */
    private static final ObjectStreamField[] serialPersistentFields = {
         new ObjectStreamField("hostname", String.class),
         new ObjectStreamField("addr", InetAddress.class),
         new ObjectStreamField("port", int.class)};

    private void writeObject(ObjectOutputStream out)
        throws IOException
    {
        // Don't call defaultWriteObject()
         ObjectOutputStream.PutField pfields = out.putFields();
         pfields.put("hostname", holder.hostname);
         pfields.put("addr", holder.addr);
         pfields.put("port", holder.port);
         out.writeFields();
     }

    private void readObject(ObjectInputStream in)
        throws IOException, ClassNotFoundException
    {
        // Don't call defaultReadObject()
        ObjectInputStream.GetField oisFields = in.readFields();
        final String oisHostname = (String)oisFields.get("hostname", null);
        final InetAddress oisAddr = (InetAddress)oisFields.get("addr", null);
        final int oisPort = oisFields.get("port", -1);

        // Check that our invariants are satisfied
        checkPort(oisPort);
        if (oisHostname == null && oisAddr == null)
            throw new InvalidObjectException("hostname and addr " +
                                             "can't both be null");

        InetSocketAddressHolder h = new InetSocketAddressHolder(oisHostname,
                                                                oisAddr,
                                                                oisPort);
        UNSAFE.putObject(this, FIELDS_OFFSET, h);
    }

    private void readObjectNoData()
        throws ObjectStreamException
    {
        throw new InvalidObjectException("Stream data required");
    }

    private static final long FIELDS_OFFSET;
    private static final sun.misc.Unsafe UNSAFE;
    static {
        try {
            sun.misc.Unsafe unsafe = sun.misc.Unsafe.getUnsafe();
            FIELDS_OFFSET = unsafe.objectFieldOffset(
                    InetSocketAddress.class.getDeclaredField("holder"));
            UNSAFE = unsafe;
        } catch (ReflectiveOperationException e) {
            throw new Error(e);
        }
    }

    /**
     * Gets the port number.
     *
     * <p>
     *  获取端口号。
     * 
     * 
     * @return the port number.
     */
    public final int getPort() {
        return holder.getPort();
    }

    /**
     *
     * Gets the {@code InetAddress}.
     *
     * <p>
     *  获取{@code InetAddress}。
     * 
     * 
     * @return the InetAdress or {@code null} if it is unresolved.
     */
    public final InetAddress getAddress() {
        return holder.getAddress();
    }

    /**
     * Gets the {@code hostname}.
     * Note: This method may trigger a name service reverse lookup if the
     * address was created with a literal IP address.
     *
     * <p>
     *  获取{@code主机名}。注意：如果地址是使用文字IP地址创建的,则此方法可以触发名称服务反向查找。
     * 
     * 
     * @return  the hostname part of the address.
     */
    public final String getHostName() {
        return holder.getHostName();
    }

    /**
     * Returns the hostname, or the String form of the address if it
     * doesn't have a hostname (it was created using a literal).
     * This has the benefit of <b>not</b> attempting a reverse lookup.
     *
     * <p>
     *  返回主机名或地址的字符串形式(如果它没有主机名(使用文字创建))。这有利于<b>不</b>尝试反向查找。
     * 
     * 
     * @return the hostname, or String representation of the address.
     * @since 1.7
     */
    public final String getHostString() {
        return holder.getHostString();
    }

    /**
     * Checks whether the address has been resolved or not.
     *
     * <p>
     *  检查地址是否已解析。
     * 
     * 
     * @return {@code true} if the hostname couldn't be resolved into
     *          an {@code InetAddress}.
     */
    public final boolean isUnresolved() {
        return holder.isUnresolved();
    }

    /**
     * Constructs a string representation of this InetSocketAddress.
     * This String is constructed by calling toString() on the InetAddress
     * and concatenating the port number (with a colon). If the address
     * is unresolved then the part before the colon will only contain the hostname.
     *
     * <p>
     *  构造此InetSocketAddress的字符串表示形式。此String通过调用InetAddress上的toString()并连接端口号(使用冒号)构造。
     * 如果地址未解析,冒号之前的部分将只包含主机名。
     * 
     * 
     * @return  a string representation of this object.
     */
    @Override
    public String toString() {
        return holder.toString();
    }

    /**
     * Compares this object against the specified object.
     * The result is {@code true} if and only if the argument is
     * not {@code null} and it represents the same address as
     * this object.
     * <p>
     * Two instances of {@code InetSocketAddress} represent the same
     * address if both the InetAddresses (or hostnames if it is unresolved) and port
     * numbers are equal.
     * If both addresses are unresolved, then the hostname and the port number
     * are compared.
     *
     * Note: Hostnames are case insensitive. e.g. "FooBar" and "foobar" are
     * considered equal.
     *
     * <p>
     * 将此对象与指定的对象进行比较。结果是{@code true}当且仅当参数不是{@code null},并且它表示与此对象相同的地址。
     * <p>
     *  如果InetAddresses(或主机名,如果它未解决)和端口号相等,则{@code InetSocketAddress}的两个实例表示相同的地址。如果两个地址都未解析,则比较主机名和端口号。
     * 
     *  注意：主机名不区分大小写。例如"FooBar"和"foobar"被认为是相等的。
     * 
     * @param   obj   the object to compare against.
     * @return  {@code true} if the objects are the same;
     *          {@code false} otherwise.
     * @see java.net.InetAddress#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(Object obj) {
        if (obj == null || !(obj instanceof InetSocketAddress))
            return false;
        return holder.equals(((InetSocketAddress) obj).holder);
    }

    /**
     * Returns a hashcode for this socket address.
     *
     * <p>
     * 
     * 
     * @return  a hash code value for this socket address.
     */
    @Override
    public final int hashCode() {
        return holder.hashCode();
    }
}
