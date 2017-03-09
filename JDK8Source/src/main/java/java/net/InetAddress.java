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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.ServiceLoader;
import java.security.AccessController;
import java.io.ObjectStreamException;
import java.io.ObjectStreamField;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectInputStream.GetField;
import java.io.ObjectOutputStream;
import java.io.ObjectOutputStream.PutField;
import sun.security.action.*;
import sun.net.InetAddressCachePolicy;
import sun.net.util.IPAddressUtil;
import sun.net.spi.nameservice.*;

/**
 * This class represents an Internet Protocol (IP) address.
 *
 * <p> An IP address is either a 32-bit or 128-bit unsigned number
 * used by IP, a lower-level protocol on which protocols like UDP and
 * TCP are built. The IP address architecture is defined by <a
 * href="http://www.ietf.org/rfc/rfc790.txt"><i>RFC&nbsp;790:
 * Assigned Numbers</i></a>, <a
 * href="http://www.ietf.org/rfc/rfc1918.txt"> <i>RFC&nbsp;1918:
 * Address Allocation for Private Internets</i></a>, <a
 * href="http://www.ietf.org/rfc/rfc2365.txt"><i>RFC&nbsp;2365:
 * Administratively Scoped IP Multicast</i></a>, and <a
 * href="http://www.ietf.org/rfc/rfc2373.txt"><i>RFC&nbsp;2373: IP
 * Version 6 Addressing Architecture</i></a>. An instance of an
 * InetAddress consists of an IP address and possibly its
 * corresponding host name (depending on whether it is constructed
 * with a host name or whether it has already done reverse host name
 * resolution).
 *
 * <h3> Address types </h3>
 *
 * <blockquote><table cellspacing=2 summary="Description of unicast and multicast address types">
 *   <tr><th valign=top><i>unicast</i></th>
 *       <td>An identifier for a single interface. A packet sent to
 *         a unicast address is delivered to the interface identified by
 *         that address.
 *
 *         <p> The Unspecified Address -- Also called anylocal or wildcard
 *         address. It must never be assigned to any node. It indicates the
 *         absence of an address. One example of its use is as the target of
 *         bind, which allows a server to accept a client connection on any
 *         interface, in case the server host has multiple interfaces.
 *
 *         <p> The <i>unspecified</i> address must not be used as
 *         the destination address of an IP packet.
 *
 *         <p> The <i>Loopback</i> Addresses -- This is the address
 *         assigned to the loopback interface. Anything sent to this
 *         IP address loops around and becomes IP input on the local
 *         host. This address is often used when testing a
 *         client.</td></tr>
 *   <tr><th valign=top><i>multicast</i></th>
 *       <td>An identifier for a set of interfaces (typically belonging
 *         to different nodes). A packet sent to a multicast address is
 *         delivered to all interfaces identified by that address.</td></tr>
 * </table></blockquote>
 *
 * <h4> IP address scope </h4>
 *
 * <p> <i>Link-local</i> addresses are designed to be used for addressing
 * on a single link for purposes such as auto-address configuration,
 * neighbor discovery, or when no routers are present.
 *
 * <p> <i>Site-local</i> addresses are designed to be used for addressing
 * inside of a site without the need for a global prefix.
 *
 * <p> <i>Global</i> addresses are unique across the internet.
 *
 * <h4> Textual representation of IP addresses </h4>
 *
 * The textual representation of an IP address is address family specific.
 *
 * <p>
 *
 * For IPv4 address format, please refer to <A
 * HREF="Inet4Address.html#format">Inet4Address#format</A>; For IPv6
 * address format, please refer to <A
 * HREF="Inet6Address.html#format">Inet6Address#format</A>.
 *
 * <P>There is a <a href="doc-files/net-properties.html#Ipv4IPv6">couple of
 * System Properties</a> affecting how IPv4 and IPv6 addresses are used.</P>
 *
 * <h4> Host Name Resolution </h4>
 *
 * Host name-to-IP address <i>resolution</i> is accomplished through
 * the use of a combination of local machine configuration information
 * and network naming services such as the Domain Name System (DNS)
 * and Network Information Service(NIS). The particular naming
 * services(s) being used is by default the local machine configured
 * one. For any host name, its corresponding IP address is returned.
 *
 * <p> <i>Reverse name resolution</i> means that for any IP address,
 * the host associated with the IP address is returned.
 *
 * <p> The InetAddress class provides methods to resolve host names to
 * their IP addresses and vice versa.
 *
 * <h4> InetAddress Caching </h4>
 *
 * The InetAddress class has a cache to store successful as well as
 * unsuccessful host name resolutions.
 *
 * <p> By default, when a security manager is installed, in order to
 * protect against DNS spoofing attacks,
 * the result of positive host name resolutions are
 * cached forever. When a security manager is not installed, the default
 * behavior is to cache entries for a finite (implementation dependent)
 * period of time. The result of unsuccessful host
 * name resolution is cached for a very short period of time (10
 * seconds) to improve performance.
 *
 * <p> If the default behavior is not desired, then a Java security property
 * can be set to a different Time-to-live (TTL) value for positive
 * caching. Likewise, a system admin can configure a different
 * negative caching TTL value when needed.
 *
 * <p> Two Java security properties control the TTL values used for
 *  positive and negative host name resolution caching:
 *
 * <blockquote>
 * <dl>
 * <dt><b>networkaddress.cache.ttl</b></dt>
 * <dd>Indicates the caching policy for successful name lookups from
 * the name service. The value is specified as as integer to indicate
 * the number of seconds to cache the successful lookup. The default
 * setting is to cache for an implementation specific period of time.
 * <p>
 * A value of -1 indicates "cache forever".
 * </dd>
 * <dt><b>networkaddress.cache.negative.ttl</b> (default: 10)</dt>
 * <dd>Indicates the caching policy for un-successful name lookups
 * from the name service. The value is specified as as integer to
 * indicate the number of seconds to cache the failure for
 * un-successful lookups.
 * <p>
 * A value of 0 indicates "never cache".
 * A value of -1 indicates "cache forever".
 * </dd>
 * </dl>
 * </blockquote>
 *
 * <p>
 *  此类表示Internet协议(IP)地址。
 * 
 *  <p> IP地址是由IP使用的32位或128位无符号数,IP是在其上构建诸如UDP和TCP的协议的较低级协议。
 *  IP地址架构由<a href="http://www.ietf.org/rfc/rfc790.txt"> <i> RFC 790：分配的号码</i> </a>,<a href ="http://www.ietf.org/rfc/rfc1918.txt">
 *  <i> RFC 1918：私人网际网路的地址分配</i> </a>,<a href ="http：// www .ietf.org / rfc / rfc2365.txt"> <i> RFC&nbsp
 * ; 2365：管理范围的IP组播</i> </a>和<a href ="http://www.ietf.org/rfc/ rfc2373.txt"> <i> RFC&nbsp; 2373：IP版本6寻址
 * 体系结构</i> </a>。
 *  <p> IP地址是由IP使用的32位或128位无符号数,IP是在其上构建诸如UDP和TCP的协议的较低级协议。
 *  InetAddress的实例由IP地址和可能的对应主机名组成(取决于它是用主机名构造的还是已经完成反向主机名解析)。
 * 
 *  <h3>地址类型</h3>
 * 
 *  <blockquote> <table cellspacing = 2 summary ="单播和多播地址类型的说明"> <tr> <th valign = top> <i> unicast </i>
 *  </th> <td> 。
 * 发送到单播地址的数据包传送到由该地址标识的接口。
 * 
 * <p>未指定的地址 - 也称为任何本地或通配符地址。它绝不能分配给任何节点。它表示没有地址。其使用的一个示例是作为bind的目标,这允许服务器在任何接口上接受客户端连接,以防服务器主机具有多个接口。
 * 
 *  <p>不得将<i>未指定的</i>地址用作IP数据包的目标地址。
 * 
 *  <p> <i>环回</i>地址 - 这是分配给回送接口的地址。发送到此IP地址的任何东西都会循环并变为本地主机上的IP输入。
 *  </td> </t> <tr> <th valign = top> <i>多播</i> </th> <td>一组接口的标识符通常属于不同的节点)。
 * 发送到多播地址的数据包将传递到由该地址标识的所有接口。</td> </tr> </table> </blockquote>。
 * 
 *  <h4> IP地址范围</h4>
 * 
 *  <p> <i>链路本地</i>地址被设计为用于在单个链路上寻址,以用于诸如自动地址配置,邻居发现或当没有路由器存在时。
 * 
 *  <p> <i>站点本地</i>地址设计为用于在站点内寻址,而不需要全局前缀。
 * 
 *  <p> <i>全球</i>地址在互联网上是唯一的。
 * 
 *  <h4> IP地址的文本表示</h4>
 * 
 *  IP地址的文本表示是地址系列特定的。
 * 
 * <p>
 * 
 * 有关IPv4地址格式,请参阅<A HREF="Inet4Address.html#format"> Inet4Address#format </A>;有关IPv6地址格式,请参阅<A HREF="Inet6Address.html#format">
 *  Inet6Address#format </A>。
 * 
 *  <P>有<a href="doc-files/net-properties.html#Ipv4IPv6">几个系统属性</a>会影响如何使用IPv4和IPv6地址。</P>
 * 
 *  <h4>主机名解析</h4>
 * 
 *  通过使用本地机器配置信息和诸如域名系统(DNS)和网络信息服务(NIS)的网络命名服务的组合来实现主机名到IP地址解析</i>。默认情况下,使用的特定命名服务是本地计算机配置的。
 * 对于任何主机名,将返回其相应的IP地址。
 * 
 *  <p> <i>反向名称解析</i>表示对于任何IP地址,将返回与该IP地址相关联的主机。
 * 
 *  <p> InetAddress类提供了将主机名解析为其IP地址的方法,反之亦然。
 * 
 *  <h4> InetAddress缓存</h4>
 * 
 *  InetAddress类具有高速缓存,用于存储成功以及主机名解析失败。
 * 
 * <p>默认情况下,安装了安全管理器时,为了防止DNS欺骗攻击,正主机名解析的结果将被永久缓存。当未安装安全管理器时,默认行为是针对有限(实现相关)时间段缓存条目。
 * 主机名解析失败的结果将被缓存非常短的时间(10秒)以提高性能。
 * 
 *  <p>如果不需要默认行为,那么可以将Java安全属性设置为正缓存的不同生存时间(TTL)值。同样,系统管理员可以在需要时配置不同的负缓存TTL值。
 * 
 *  <p>两个Java安全属性控制用于正负主机名解析缓存的TTL值：
 * 
 * <blockquote>
 * <dl>
 *  <dt> <b> networkaddress.cache.ttl </b> </dt> <dd>表示从名称服务成功名称查找的高速缓存策略。该值指定为整数以指示缓存成功查找的秒数。
 * 默认设置是缓存实施特定时间段。
 * <p>
 *  值-1表示"永远高速缓存"。
 * </dd>
 *  <dt> <b> networkaddress.cache.negative.ttl </b>(默认值：10)</dt> <dd>指示来自名称服务的未成功名称查找的高速缓存策略。
 * 该值指定为整数,表示缓存失败的未成功查找的秒数。
 * <p>
 *  值为0表示"从不缓存"。值-1表示"永远高速缓存"。
 * </dd>
 * </dl>
 * </blockquote>
 * 
 * 
 * @author  Chris Warth
 * @see     java.net.InetAddress#getByAddress(byte[])
 * @see     java.net.InetAddress#getByAddress(java.lang.String, byte[])
 * @see     java.net.InetAddress#getAllByName(java.lang.String)
 * @see     java.net.InetAddress#getByName(java.lang.String)
 * @see     java.net.InetAddress#getLocalHost()
 * @since JDK1.0
 */
public
class InetAddress implements java.io.Serializable {
    /**
     * Specify the address family: Internet Protocol, Version 4
     * <p>
     * 指定地址系列：Internet协议,版本4
     * 
     * 
     * @since 1.4
     */
    static final int IPv4 = 1;

    /**
     * Specify the address family: Internet Protocol, Version 6
     * <p>
     *  指定地址系列：Internet协议,版本6
     * 
     * 
     * @since 1.4
     */
    static final int IPv6 = 2;

    /* Specify address family preference */
    static transient boolean preferIPv6Address = false;

    static class InetAddressHolder {

        InetAddressHolder() {}

        InetAddressHolder(String hostName, int address, int family) {
            this.hostName = hostName;
            this.address = address;
            this.family = family;
        }

        void init(String hostName, int family) {
            this.hostName = hostName;
            if (family != -1) {
                this.family = family;
            }
        }

        String hostName;

        String getHostName() {
            return hostName;
        }

        /**
         * Holds a 32-bit IPv4 address.
         * <p>
         *  保存32位IPv4地址。
         * 
         */
        int address;

        int getAddress() {
            return address;
        }

        /**
         * Specifies the address family type, for instance, '1' for IPv4
         * addresses, and '2' for IPv6 addresses.
         * <p>
         *  指定地址系列类型,例如,IPv4地址为"1",IPv6地址为"2"。
         * 
         */
        int family;

        int getFamily() {
            return family;
        }
    }

    /* Used to store the serializable fields of InetAddress */
    final transient InetAddressHolder holder;

    InetAddressHolder holder() {
        return holder;
    }

    /* Used to store the name service provider */
    private static List<NameService> nameServices = null;

    /* Used to store the best available hostname */
    private transient String canonicalHostName = null;

    /** use serialVersionUID from JDK 1.0.2 for interoperability */
    private static final long serialVersionUID = 3286316764910316507L;

    /*
     * Load net library into runtime, and perform initializations.
     * <p>
     *  将网络库加载到运行时,并执行初始化。
     * 
     */
    static {
        preferIPv6Address = java.security.AccessController.doPrivileged(
            new GetBooleanAction("java.net.preferIPv6Addresses")).booleanValue();
        AccessController.doPrivileged(
            new java.security.PrivilegedAction<Void>() {
                public Void run() {
                    System.loadLibrary("net");
                    return null;
                }
            });
        init();
    }

    /**
     * Constructor for the Socket.accept() method.
     * This creates an empty InetAddress, which is filled in by
     * the accept() method.  This InetAddress, however, is not
     * put in the address cache, since it is not created by name.
     * <p>
     *  Socket.accept()方法的构造方法。这将创建一个空的InetAddress,它由accept()方法填充。但是,此InetAddress不会放在地址高速缓存中,因为它不是按名称创建的。
     * 
     */
    InetAddress() {
        holder = new InetAddressHolder();
    }

    /**
     * Replaces the de-serialized object with an Inet4Address object.
     *
     * <p>
     *  用Inet4Address对象替换反序列化的对象。
     * 
     * 
     * @return the alternate object to the de-serialized object.
     *
     * @throws ObjectStreamException if a new object replacing this
     * object could not be created
     */
    private Object readResolve() throws ObjectStreamException {
        // will replace the deserialized 'this' object
        return new Inet4Address(holder().getHostName(), holder().getAddress());
    }

    /**
     * Utility routine to check if the InetAddress is an
     * IP multicast address.
     * <p>
     *  用于检查InetAddress是否是IP多播地址的实用程序。
     * 
     * 
     * @return a {@code boolean} indicating if the InetAddress is
     * an IP multicast address
     * @since   JDK1.1
     */
    public boolean isMulticastAddress() {
        return false;
    }

    /**
     * Utility routine to check if the InetAddress in a wildcard address.
     * <p>
     *  用于检查通配符地址中的InetAddress的实用程序。
     * 
     * 
     * @return a {@code boolean} indicating if the Inetaddress is
     *         a wildcard address.
     * @since 1.4
     */
    public boolean isAnyLocalAddress() {
        return false;
    }

    /**
     * Utility routine to check if the InetAddress is a loopback address.
     *
     * <p>
     *  用于检查InetAddress是否是环回地址的实用程序。
     * 
     * 
     * @return a {@code boolean} indicating if the InetAddress is
     * a loopback address; or false otherwise.
     * @since 1.4
     */
    public boolean isLoopbackAddress() {
        return false;
    }

    /**
     * Utility routine to check if the InetAddress is an link local address.
     *
     * <p>
     *  用于检查InetAddress是否是链路本地地址的实用程序。
     * 
     * 
     * @return a {@code boolean} indicating if the InetAddress is
     * a link local address; or false if address is not a link local unicast address.
     * @since 1.4
     */
    public boolean isLinkLocalAddress() {
        return false;
    }

    /**
     * Utility routine to check if the InetAddress is a site local address.
     *
     * <p>
     *  用于检查InetAddress是否是站点本地地址的实用程序。
     * 
     * 
     * @return a {@code boolean} indicating if the InetAddress is
     * a site local address; or false if address is not a site local unicast address.
     * @since 1.4
     */
    public boolean isSiteLocalAddress() {
        return false;
    }

    /**
     * Utility routine to check if the multicast address has global scope.
     *
     * <p>
     *  用于检查多播地址是否具有全局范围的实用程序。
     * 
     * 
     * @return a {@code boolean} indicating if the address has
     *         is a multicast address of global scope, false if it is not
     *         of global scope or it is not a multicast address
     * @since 1.4
     */
    public boolean isMCGlobal() {
        return false;
    }

    /**
     * Utility routine to check if the multicast address has node scope.
     *
     * <p>
     *  用于检查多播地址是否具有节点范围的实用程序。
     * 
     * 
     * @return a {@code boolean} indicating if the address has
     *         is a multicast address of node-local scope, false if it is not
     *         of node-local scope or it is not a multicast address
     * @since 1.4
     */
    public boolean isMCNodeLocal() {
        return false;
    }

    /**
     * Utility routine to check if the multicast address has link scope.
     *
     * <p>
     *  用于检查多播地址是否具有链路范围的实用程序。
     * 
     * 
     * @return a {@code boolean} indicating if the address has
     *         is a multicast address of link-local scope, false if it is not
     *         of link-local scope or it is not a multicast address
     * @since 1.4
     */
    public boolean isMCLinkLocal() {
        return false;
    }

    /**
     * Utility routine to check if the multicast address has site scope.
     *
     * <p>
     *  用于检查多播地址是否具有站点范围的实用程序。
     * 
     * 
     * @return a {@code boolean} indicating if the address has
     *         is a multicast address of site-local scope, false if it is not
     *         of site-local scope or it is not a multicast address
     * @since 1.4
     */
    public boolean isMCSiteLocal() {
        return false;
    }

    /**
     * Utility routine to check if the multicast address has organization scope.
     *
     * <p>
     *  用于检查多播地址是否具有组织范围的实用程序。
     * 
     * 
     * @return a {@code boolean} indicating if the address has
     *         is a multicast address of organization-local scope,
     *         false if it is not of organization-local scope
     *         or it is not a multicast address
     * @since 1.4
     */
    public boolean isMCOrgLocal() {
        return false;
    }


    /**
     * Test whether that address is reachable. Best effort is made by the
     * implementation to try to reach the host, but firewalls and server
     * configuration may block requests resulting in a unreachable status
     * while some specific ports may be accessible.
     * A typical implementation will use ICMP ECHO REQUESTs if the
     * privilege can be obtained, otherwise it will try to establish
     * a TCP connection on port 7 (Echo) of the destination host.
     * <p>
     * The timeout value, in milliseconds, indicates the maximum amount of time
     * the try should take. If the operation times out before getting an
     * answer, the host is deemed unreachable. A negative value will result
     * in an IllegalArgumentException being thrown.
     *
     * <p>
     * 测试该地址是否可访问。实施时尽力实现到达主机,但是防火墙和服务器配置可以阻止导致不可达状态的请求,而某些特定端口可以被访问。
     * 如果可以获得特权,典型的实现将使用ICMP ECHO REQUEST,否则它将尝试在目的地主机的端口7(Echo)上建立TCP连接。
     * <p>
     *  超时值(以毫秒为单位)表示尝试应采用的最长时间。如果操作在获取答案之前超时,则认为主机不可达。负值将导致抛出IllegalArgumentException。
     * 
     * 
     * @param   timeout the time, in milliseconds, before the call aborts
     * @return a {@code boolean} indicating if the address is reachable.
     * @throws IOException if a network error occurs
     * @throws  IllegalArgumentException if {@code timeout} is negative.
     * @since 1.5
     */
    public boolean isReachable(int timeout) throws IOException {
        return isReachable(null, 0 , timeout);
    }

    /**
     * Test whether that address is reachable. Best effort is made by the
     * implementation to try to reach the host, but firewalls and server
     * configuration may block requests resulting in a unreachable status
     * while some specific ports may be accessible.
     * A typical implementation will use ICMP ECHO REQUESTs if the
     * privilege can be obtained, otherwise it will try to establish
     * a TCP connection on port 7 (Echo) of the destination host.
     * <p>
     * The {@code network interface} and {@code ttl} parameters
     * let the caller specify which network interface the test will go through
     * and the maximum number of hops the packets should go through.
     * A negative value for the {@code ttl} will result in an
     * IllegalArgumentException being thrown.
     * <p>
     * The timeout value, in milliseconds, indicates the maximum amount of time
     * the try should take. If the operation times out before getting an
     * answer, the host is deemed unreachable. A negative value will result
     * in an IllegalArgumentException being thrown.
     *
     * <p>
     *  测试该地址是否可访问。通过实现尽力实现到达主机,但是防火墙和服务器配置可以阻止导致不可达状态的请求,而某些特定端口可以被访问。
     * 如果可以获得特权,典型的实现将使用ICMP ECHO REQUEST,否则它将尝试在目的地主机的端口7(Echo)上建立TCP连接。
     * <p>
     *  {@code network interface}和{@code ttl}参数让调用者指定测试将通过哪个网络接口和数据包应该经过的最大跳数。
     *  {@code ttl}的负值将导致抛出IllegalArgumentException。
     * <p>
     * 超时值(以毫秒为单位)表示尝试应采用的最长时间。如果操作在获取答案之前超时,则认为主机不可达。负值将导致抛出IllegalArgumentException。
     * 
     * 
     * @param   netif   the NetworkInterface through which the
     *                    test will be done, or null for any interface
     * @param   ttl     the maximum numbers of hops to try or 0 for the
     *                  default
     * @param   timeout the time, in milliseconds, before the call aborts
     * @throws  IllegalArgumentException if either {@code timeout}
     *                          or {@code ttl} are negative.
     * @return a {@code boolean}indicating if the address is reachable.
     * @throws IOException if a network error occurs
     * @since 1.5
     */
    public boolean isReachable(NetworkInterface netif, int ttl,
                               int timeout) throws IOException {
        if (ttl < 0)
            throw new IllegalArgumentException("ttl can't be negative");
        if (timeout < 0)
            throw new IllegalArgumentException("timeout can't be negative");

        return impl.isReachable(this, timeout, netif, ttl);
    }

    /**
     * Gets the host name for this IP address.
     *
     * <p>If this InetAddress was created with a host name,
     * this host name will be remembered and returned;
     * otherwise, a reverse name lookup will be performed
     * and the result will be returned based on the system
     * configured name lookup service. If a lookup of the name service
     * is required, call
     * {@link #getCanonicalHostName() getCanonicalHostName}.
     *
     * <p>If there is a security manager, its
     * {@code checkConnect} method is first called
     * with the hostname and {@code -1}
     * as its arguments to see if the operation is allowed.
     * If the operation is not allowed, it will return
     * the textual representation of the IP address.
     *
     * <p>
     *  获取此IP地址的主机名。
     * 
     *  <p>如果此InetAddress是使用主机名创建的,则此主机名将被记住并返回;否则,将执行反向名称查找,并将根据系统配置的名称查找服务返回结果。
     * 如果需要查找名称服务,请调用{@link #getCanonicalHostName()getCanonicalHostName}。
     * 
     *  <p>如果有安全管理器,则首先使用主机名和{@code -1}作为其参数调用其{@code checkConnect}方法,以查看是否允许操作。如果不允许操作,它将返回IP地址的文本表示。
     * 
     * 
     * @return  the host name for this IP address, or if the operation
     *    is not allowed by the security check, the textual
     *    representation of the IP address.
     *
     * @see InetAddress#getCanonicalHostName
     * @see SecurityManager#checkConnect
     */
    public String getHostName() {
        return getHostName(true);
    }

    /**
     * Returns the hostname for this address.
     * If the host is equal to null, then this address refers to any
     * of the local machine's available network addresses.
     * this is package private so SocketPermission can make calls into
     * here without a security check.
     *
     * <p>If there is a security manager, this method first
     * calls its {@code checkConnect} method
     * with the hostname and {@code -1}
     * as its arguments to see if the calling code is allowed to know
     * the hostname for this IP address, i.e., to connect to the host.
     * If the operation is not allowed, it will return
     * the textual representation of the IP address.
     *
     * <p>
     *  返回此地址的主机名。如果主机等于空,则该地址指的是本地机器的任何可用网络地址。这是包私人所以SocketPermission可以调用到这里没有安全检查。
     * 
     *  <p>如果有安全管理员,则此方法首先使用主机名和{@code -1}作为其参数调用其{@code checkConnect}方法,以查看调用代码是否被允许知道此IP地址的主机名,即连接到主机。
     * 如果不允许操作,它将返回IP地址的文本表示。
     * 
     * 
     * @return  the host name for this IP address, or if the operation
     *    is not allowed by the security check, the textual
     *    representation of the IP address.
     *
     * @param check make security check if true
     *
     * @see SecurityManager#checkConnect
     */
    String getHostName(boolean check) {
        if (holder().getHostName() == null) {
            holder().hostName = InetAddress.getHostFromNameService(this, check);
        }
        return holder().getHostName();
    }

    /**
     * Gets the fully qualified domain name for this IP address.
     * Best effort method, meaning we may not be able to return
     * the FQDN depending on the underlying system configuration.
     *
     * <p>If there is a security manager, this method first
     * calls its {@code checkConnect} method
     * with the hostname and {@code -1}
     * as its arguments to see if the calling code is allowed to know
     * the hostname for this IP address, i.e., to connect to the host.
     * If the operation is not allowed, it will return
     * the textual representation of the IP address.
     *
     * <p>
     * 获取此IP地址的完全限定域名。最佳努力方法,意味着我们可能无法返回FQDN取决于底层系统配置。
     * 
     *  <p>如果有安全管理员,则此方法首先使用主机名和{@code -1}作为其参数调用其{@code checkConnect}方法,以查看调用代码是否被允许知道此IP地址的主机名,即连接到主机。
     * 如果不允许操作,它将返回IP地址的文本表示。
     * 
     * 
     * @return  the fully qualified domain name for this IP address,
     *    or if the operation is not allowed by the security check,
     *    the textual representation of the IP address.
     *
     * @see SecurityManager#checkConnect
     *
     * @since 1.4
     */
    public String getCanonicalHostName() {
        if (canonicalHostName == null) {
            canonicalHostName =
                InetAddress.getHostFromNameService(this, true);
        }
        return canonicalHostName;
    }

    /**
     * Returns the hostname for this address.
     *
     * <p>If there is a security manager, this method first
     * calls its {@code checkConnect} method
     * with the hostname and {@code -1}
     * as its arguments to see if the calling code is allowed to know
     * the hostname for this IP address, i.e., to connect to the host.
     * If the operation is not allowed, it will return
     * the textual representation of the IP address.
     *
     * <p>
     *  返回此地址的主机名。
     * 
     *  <p>如果有安全管理员,则此方法首先使用主机名和{@code -1}作为其参数调用其{@code checkConnect}方法,以查看调用代码是否被允许知道此IP地址的主机名,即连接到主机。
     * 如果不允许操作,它将返回IP地址的文本表示。
     * 
     * 
     * @return  the host name for this IP address, or if the operation
     *    is not allowed by the security check, the textual
     *    representation of the IP address.
     *
     * @param check make security check if true
     *
     * @see SecurityManager#checkConnect
     */
    private static String getHostFromNameService(InetAddress addr, boolean check) {
        String host = null;
        for (NameService nameService : nameServices) {
            try {
                // first lookup the hostname
                host = nameService.getHostByAddr(addr.getAddress());

                /* check to see if calling code is allowed to know
                 * the hostname for this IP address, ie, connect to the host
                 * <p>
                 *  该IP地址的主机名,即连接到主机
                 * 
                 */
                if (check) {
                    SecurityManager sec = System.getSecurityManager();
                    if (sec != null) {
                        sec.checkConnect(host, -1);
                    }
                }

                /* now get all the IP addresses for this hostname,
                 * and make sure one of them matches the original IP
                 * address. We do this to try and prevent spoofing.
                 * <p>
                 *  并确保其中一个与原始IP地址匹配。我们这样做是为了试图防止欺骗。
                 * 
                 */

                InetAddress[] arr = InetAddress.getAllByName0(host, check);
                boolean ok = false;

                if(arr != null) {
                    for(int i = 0; !ok && i < arr.length; i++) {
                        ok = addr.equals(arr[i]);
                    }
                }

                //XXX: if it looks a spoof just return the address?
                if (!ok) {
                    host = addr.getHostAddress();
                    return host;
                }

                break;

            } catch (SecurityException e) {
                host = addr.getHostAddress();
                break;
            } catch (UnknownHostException e) {
                host = addr.getHostAddress();
                // let next provider resolve the hostname
            }
        }

        return host;
    }

    /**
     * Returns the raw IP address of this {@code InetAddress}
     * object. The result is in network byte order: the highest order
     * byte of the address is in {@code getAddress()[0]}.
     *
     * <p>
     *  返回此{@code InetAddress}对象的原始IP地址。结果是以网络字节顺序：地址的最高位字节在{@code getAddress()[0]}中。
     * 
     * 
     * @return  the raw IP address of this object.
     */
    public byte[] getAddress() {
        return null;
    }

    /**
     * Returns the IP address string in textual presentation.
     *
     * <p>
     *  返回文本演示中的IP地址字符串。
     * 
     * 
     * @return  the raw IP address in a string format.
     * @since   JDK1.0.2
     */
    public String getHostAddress() {
        return null;
     }

    /**
     * Returns a hashcode for this IP address.
     *
     * <p>
     *  返回此IP地址的哈希码。
     * 
     * 
     * @return  a hash code value for this IP address.
     */
    public int hashCode() {
        return -1;
    }

    /**
     * Compares this object against the specified object.
     * The result is {@code true} if and only if the argument is
     * not {@code null} and it represents the same IP address as
     * this object.
     * <p>
     * Two instances of {@code InetAddress} represent the same IP
     * address if the length of the byte arrays returned by
     * {@code getAddress} is the same for both, and each of the
     * array components is the same for the byte arrays.
     *
     * <p>
     * 将此对象与指定的对象进行比较。结果是{@code true}当且仅当参数不是{@code null},它表示与此对象相同的IP地址。
     * <p>
     *  {@code InetAddress}的两个实例表示相同的IP地址,如果{@code getAddress}返回的字节数组的长度相同,并且每个数组组件对于字节数组是相同的。
     * 
     * 
     * @param   obj   the object to compare against.
     * @return  {@code true} if the objects are the same;
     *          {@code false} otherwise.
     * @see     java.net.InetAddress#getAddress()
     */
    public boolean equals(Object obj) {
        return false;
    }

    /**
     * Converts this IP address to a {@code String}. The
     * string returned is of the form: hostname / literal IP
     * address.
     *
     * If the host name is unresolved, no reverse name service lookup
     * is performed. The hostname part will be represented by an empty string.
     *
     * <p>
     *  将此IP地址转换为{@code String}。返回的字符串格式为：hostname / literal IP address。
     * 
     *  如果主机名未解析,则不执行反向名称服务查找。主机名部分将由空字符串表示。
     * 
     * 
     * @return  a string representation of this IP address.
     */
    public String toString() {
        String hostName = holder().getHostName();
        return ((hostName != null) ? hostName : "")
            + "/" + getHostAddress();
    }

    /*
     * Cached addresses - our own litle nis, not!
     * <p>
     *  缓存地址 - 我们自己的litle nis,不是！
     * 
     */
    private static Cache addressCache = new Cache(Cache.Type.Positive);

    private static Cache negativeCache = new Cache(Cache.Type.Negative);

    private static boolean addressCacheInit = false;

    static InetAddress[]    unknown_array; // put THIS in cache

    static InetAddressImpl  impl;

    private static final HashMap<String, Void> lookupTable = new HashMap<>();

    /**
     * Represents a cache entry
     * <p>
     *  表示高速缓存条目
     * 
     */
    static final class CacheEntry {

        CacheEntry(InetAddress[] addresses, long expiration) {
            this.addresses = addresses;
            this.expiration = expiration;
        }

        InetAddress[] addresses;
        long expiration;
    }

    /**
     * A cache that manages entries based on a policy specified
     * at creation time.
     * <p>
     *  基于创建时指定的策略管理条目的高速缓存。
     * 
     */
    static final class Cache {
        private LinkedHashMap<String, CacheEntry> cache;
        private Type type;

        enum Type {Positive, Negative};

        /**
         * Create cache
         * <p>
         *  创建缓存
         * 
         */
        public Cache(Type type) {
            this.type = type;
            cache = new LinkedHashMap<String, CacheEntry>();
        }

        private int getPolicy() {
            if (type == Type.Positive) {
                return InetAddressCachePolicy.get();
            } else {
                return InetAddressCachePolicy.getNegative();
            }
        }

        /**
         * Add an entry to the cache. If there's already an
         * entry then for this host then the entry will be
         * replaced.
         * <p>
         *  向高速缓存中添加条目。如果已经有一个条目,那么对于这个主机,该条目将被替换。
         * 
         */
        public Cache put(String host, InetAddress[] addresses) {
            int policy = getPolicy();
            if (policy == InetAddressCachePolicy.NEVER) {
                return this;
            }

            // purge any expired entries

            if (policy != InetAddressCachePolicy.FOREVER) {

                // As we iterate in insertion order we can
                // terminate when a non-expired entry is found.
                LinkedList<String> expired = new LinkedList<>();
                long now = System.currentTimeMillis();
                for (String key : cache.keySet()) {
                    CacheEntry entry = cache.get(key);

                    if (entry.expiration >= 0 && entry.expiration < now) {
                        expired.add(key);
                    } else {
                        break;
                    }
                }

                for (String key : expired) {
                    cache.remove(key);
                }
            }

            // create new entry and add it to the cache
            // -- as a HashMap replaces existing entries we
            //    don't need to explicitly check if there is
            //    already an entry for this host.
            long expiration;
            if (policy == InetAddressCachePolicy.FOREVER) {
                expiration = -1;
            } else {
                expiration = System.currentTimeMillis() + (policy * 1000);
            }
            CacheEntry entry = new CacheEntry(addresses, expiration);
            cache.put(host, entry);
            return this;
        }

        /**
         * Query the cache for the specific host. If found then
         * return its CacheEntry, or null if not found.
         * <p>
         *  查询特定主机的缓存。如果找到,则返回其CacheEntry,如果找不到则返回null。
         * 
         */
        public CacheEntry get(String host) {
            int policy = getPolicy();
            if (policy == InetAddressCachePolicy.NEVER) {
                return null;
            }
            CacheEntry entry = cache.get(host);

            // check if entry has expired
            if (entry != null && policy != InetAddressCachePolicy.FOREVER) {
                if (entry.expiration >= 0 &&
                    entry.expiration < System.currentTimeMillis()) {
                    cache.remove(host);
                    entry = null;
                }
            }

            return entry;
        }
    }

    /*
     * Initialize cache and insert anyLocalAddress into the
     * unknown array with no expiry.
     * <p>
     *  初始化缓存并将anyLocalAddress插入到未知数组中,并且没有到期。
     * 
     */
    private static void cacheInitIfNeeded() {
        assert Thread.holdsLock(addressCache);
        if (addressCacheInit) {
            return;
        }
        unknown_array = new InetAddress[1];
        unknown_array[0] = impl.anyLocalAddress();

        addressCache.put(impl.anyLocalAddress().getHostName(),
                         unknown_array);

        addressCacheInit = true;
    }

    /*
     * Cache the given hostname and addresses.
     * <p>
     *  缓存给定的主机名和地址。
     * 
     */
    private static void cacheAddresses(String hostname,
                                       InetAddress[] addresses,
                                       boolean success) {
        hostname = hostname.toLowerCase();
        synchronized (addressCache) {
            cacheInitIfNeeded();
            if (success) {
                addressCache.put(hostname, addresses);
            } else {
                negativeCache.put(hostname, addresses);
            }
        }
    }

    /*
     * Lookup hostname in cache (positive & negative cache). If
     * found return addresses, null if not found.
     * <p>
     *  在缓存中查找主机名(正负缓存)。如果找到返回地址,则返回null(如果找不到)。
     * 
     */
    private static InetAddress[] getCachedAddresses(String hostname) {
        hostname = hostname.toLowerCase();

        // search both positive & negative caches

        synchronized (addressCache) {
            cacheInitIfNeeded();

            CacheEntry entry = addressCache.get(hostname);
            if (entry == null) {
                entry = negativeCache.get(hostname);
            }

            if (entry != null) {
                return entry.addresses;
            }
        }

        // not found
        return null;
    }

    private static NameService createNSProvider(String provider) {
        if (provider == null)
            return null;

        NameService nameService = null;
        if (provider.equals("default")) {
            // initialize the default name service
            nameService = new NameService() {
                public InetAddress[] lookupAllHostAddr(String host)
                    throws UnknownHostException {
                    return impl.lookupAllHostAddr(host);
                }
                public String getHostByAddr(byte[] addr)
                    throws UnknownHostException {
                    return impl.getHostByAddr(addr);
                }
            };
        } else {
            final String providerName = provider;
            try {
                nameService = java.security.AccessController.doPrivileged(
                    new java.security.PrivilegedExceptionAction<NameService>() {
                        public NameService run() {
                            Iterator<NameServiceDescriptor> itr =
                                ServiceLoader.load(NameServiceDescriptor.class)
                                    .iterator();
                            while (itr.hasNext()) {
                                NameServiceDescriptor nsd = itr.next();
                                if (providerName.
                                    equalsIgnoreCase(nsd.getType()+","
                                        +nsd.getProviderName())) {
                                    try {
                                        return nsd.createNameService();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        System.err.println(
                                            "Cannot create name service:"
                                             +providerName+": " + e);
                                    }
                                }
                            }

                            return null;
                        }
                    }
                );
            } catch (java.security.PrivilegedActionException e) {
            }
        }

        return nameService;
    }

    static {
        // create the impl
        impl = InetAddressImplFactory.create();

        // get name service if provided and requested
        String provider = null;;
        String propPrefix = "sun.net.spi.nameservice.provider.";
        int n = 1;
        nameServices = new ArrayList<NameService>();
        provider = AccessController.doPrivileged(
                new GetPropertyAction(propPrefix + n));
        while (provider != null) {
            NameService ns = createNSProvider(provider);
            if (ns != null)
                nameServices.add(ns);

            n++;
            provider = AccessController.doPrivileged(
                    new GetPropertyAction(propPrefix + n));
        }

        // if not designate any name services provider,
        // create a default one
        if (nameServices.size() == 0) {
            NameService ns = createNSProvider("default");
            nameServices.add(ns);
        }
    }

    /**
     * Creates an InetAddress based on the provided host name and IP address.
     * No name service is checked for the validity of the address.
     *
     * <p> The host name can either be a machine name, such as
     * "{@code java.sun.com}", or a textual representation of its IP
     * address.
     * <p> No validity checking is done on the host name either.
     *
     * <p> If addr specifies an IPv4 address an instance of Inet4Address
     * will be returned; otherwise, an instance of Inet6Address
     * will be returned.
     *
     * <p> IPv4 address byte array must be 4 bytes long and IPv6 byte array
     * must be 16 bytes long
     *
     * <p>
     *  基于提供的主机名和IP地址创建InetAddress。未检查地址的有效性的名称服务。
     * 
     * <p>主机名可以是机器名,例如"{@code java.sun.com}",或其IP地址的文本表示。 <p>也不会对主机名执行有效性检查。
     * 
     *  <p>如果addr指定IPv4地址,则将返回Inet4Address的实例;否则,将返回Inet6Address的实例。
     * 
     *  <p> IPv4地址字节数组必须为4字节长,IPv6字节数组必须为16字节长
     * 
     * 
     * @param host the specified host
     * @param addr the raw IP address in network byte order
     * @return  an InetAddress object created from the raw IP address.
     * @exception  UnknownHostException  if IP address is of illegal length
     * @since 1.4
     */
    public static InetAddress getByAddress(String host, byte[] addr)
        throws UnknownHostException {
        if (host != null && host.length() > 0 && host.charAt(0) == '[') {
            if (host.charAt(host.length()-1) == ']') {
                host = host.substring(1, host.length() -1);
            }
        }
        if (addr != null) {
            if (addr.length == Inet4Address.INADDRSZ) {
                return new Inet4Address(host, addr);
            } else if (addr.length == Inet6Address.INADDRSZ) {
                byte[] newAddr
                    = IPAddressUtil.convertFromIPv4MappedAddress(addr);
                if (newAddr != null) {
                    return new Inet4Address(host, newAddr);
                } else {
                    return new Inet6Address(host, addr);
                }
            }
        }
        throw new UnknownHostException("addr is of illegal length");
    }


    /**
     * Determines the IP address of a host, given the host's name.
     *
     * <p> The host name can either be a machine name, such as
     * "{@code java.sun.com}", or a textual representation of its
     * IP address. If a literal IP address is supplied, only the
     * validity of the address format is checked.
     *
     * <p> For {@code host} specified in literal IPv6 address,
     * either the form defined in RFC 2732 or the literal IPv6 address
     * format defined in RFC 2373 is accepted. IPv6 scoped addresses are also
     * supported. See <a href="Inet6Address.html#scoped">here</a> for a description of IPv6
     * scoped addresses.
     *
     * <p> If the host is {@code null} then an {@code InetAddress}
     * representing an address of the loopback interface is returned.
     * See <a href="http://www.ietf.org/rfc/rfc3330.txt">RFC&nbsp;3330</a>
     * section&nbsp;2 and <a href="http://www.ietf.org/rfc/rfc2373.txt">RFC&nbsp;2373</a>
     * section&nbsp;2.5.3. </p>
     *
     * <p>
     *  确定主机的IP地址,给定主机名称。
     * 
     *  <p>主机名可以是机器名,例如"{@code java.sun.com}",或其IP地址的文本表示。如果提供了文本IP地址,则只检查地址格式的有效性。
     * 
     *  <p>对于以字面IPv6地址指定的{@code host},接受RFC 2732中定义的格式或RFC 2373中定义的字面IPv6地址格式。还支持IPv6范围地址。
     * 有关IPv6范围地址的说明,请参见<a href="Inet6Address.html#scoped">此处</a>。
     * 
     *  <p>如果主机是{@code null},则返回表示回送接口地址的{@code InetAddress}。
     * 请参见<a href="http://www.ietf.org/rfc/rfc3330.txt"> RFC 3330 </a>部分2和<a href ="http://www.ietf.org/rfc /rfc2373.txt">
     * RFC&nbsp;2373 </a>部分2.5.3。
     *  <p>如果主机是{@code null},则返回表示回送接口地址的{@code InetAddress}。 </p>。
     * 
     * 
     * @param      host   the specified host, or {@code null}.
     * @return     an IP address for the given host name.
     * @exception  UnknownHostException  if no IP address for the
     *               {@code host} could be found, or if a scope_id was specified
     *               for a global IPv6 address.
     * @exception  SecurityException if a security manager exists
     *             and its checkConnect method doesn't allow the operation
     */
    public static InetAddress getByName(String host)
        throws UnknownHostException {
        return InetAddress.getAllByName(host)[0];
    }

    // called from deployment cache manager
    private static InetAddress getByName(String host, InetAddress reqAddr)
        throws UnknownHostException {
        return InetAddress.getAllByName(host, reqAddr)[0];
    }

    /**
     * Given the name of a host, returns an array of its IP addresses,
     * based on the configured name service on the system.
     *
     * <p> The host name can either be a machine name, such as
     * "{@code java.sun.com}", or a textual representation of its IP
     * address. If a literal IP address is supplied, only the
     * validity of the address format is checked.
     *
     * <p> For {@code host} specified in <i>literal IPv6 address</i>,
     * either the form defined in RFC 2732 or the literal IPv6 address
     * format defined in RFC 2373 is accepted. A literal IPv6 address may
     * also be qualified by appending a scoped zone identifier or scope_id.
     * The syntax and usage of scope_ids is described
     * <a href="Inet6Address.html#scoped">here</a>.
     * <p> If the host is {@code null} then an {@code InetAddress}
     * representing an address of the loopback interface is returned.
     * See <a href="http://www.ietf.org/rfc/rfc3330.txt">RFC&nbsp;3330</a>
     * section&nbsp;2 and <a href="http://www.ietf.org/rfc/rfc2373.txt">RFC&nbsp;2373</a>
     * section&nbsp;2.5.3. </p>
     *
     * <p> If there is a security manager and {@code host} is not
     * null and {@code host.length() } is not equal to zero, the
     * security manager's
     * {@code checkConnect} method is called
     * with the hostname and {@code -1}
     * as its arguments to see if the operation is allowed.
     *
     * <p>
     *  给定主机的名称,根据系统上配置的名称服务返回其IP地址的数组。
     * 
     * <p>主机名可以是机器名,例如"{@code java.sun.com}",或其IP地址的文本表示。如果提供了文本IP地址,则只检查地址格式的有效性。
     * 
     *  <p>对于在<i>文字IPv6地址</i>中指定的{@code host},接受RFC 2732中定义的格式或RFC 2373中定义的字面IPv6地址格式。
     * 还可以通过附加范围区域标识符或scope_id来限定文字IPv6地址。 <a href="Inet6Address.html#scoped">此处</a>介绍了scope_ids的语法和用法。
     *  <p>如果主机是{@code null},则返回表示回送接口地址的{@code InetAddress}。
     * 请参见<a href="http://www.ietf.org/rfc/rfc3330.txt"> RFC 3330 </a>部分2和<a href ="http://www.ietf.org/rfc /rfc2373.txt">
     * RFC&nbsp;2373 </a>部分2.5.3。
     *  <p>如果主机是{@code null},则返回表示回送接口地址的{@code InetAddress}。 </p>。
     * 
     *  <p>如果有安全管理员且{@code host}不为null,且{@code host.length()}不等于零,则会调用安全管理员的{@code checkConnect}方法,并使用主机名和{ @code -1}
     * 作为其参数,以查看是否允许操作。
     * 
     * 
     * @param      host   the name of the host, or {@code null}.
     * @return     an array of all the IP addresses for a given host name.
     *
     * @exception  UnknownHostException  if no IP address for the
     *               {@code host} could be found, or if a scope_id was specified
     *               for a global IPv6 address.
     * @exception  SecurityException  if a security manager exists and its
     *               {@code checkConnect} method doesn't allow the operation.
     *
     * @see SecurityManager#checkConnect
     */
    public static InetAddress[] getAllByName(String host)
        throws UnknownHostException {
        return getAllByName(host, null);
    }

    private static InetAddress[] getAllByName(String host, InetAddress reqAddr)
        throws UnknownHostException {

        if (host == null || host.length() == 0) {
            InetAddress[] ret = new InetAddress[1];
            ret[0] = impl.loopbackAddress();
            return ret;
        }

        boolean ipv6Expected = false;
        if (host.charAt(0) == '[') {
            // This is supposed to be an IPv6 literal
            if (host.length() > 2 && host.charAt(host.length()-1) == ']') {
                host = host.substring(1, host.length() -1);
                ipv6Expected = true;
            } else {
                // This was supposed to be a IPv6 address, but it's not!
                throw new UnknownHostException(host + ": invalid IPv6 address");
            }
        }

        // if host is an IP address, we won't do further lookup
        if (Character.digit(host.charAt(0), 16) != -1
            || (host.charAt(0) == ':')) {
            byte[] addr = null;
            int numericZone = -1;
            String ifname = null;
            // see if it is IPv4 address
            addr = IPAddressUtil.textToNumericFormatV4(host);
            if (addr == null) {
                // This is supposed to be an IPv6 literal
                // Check if a numeric or string zone id is present
                int pos;
                if ((pos=host.indexOf ("%")) != -1) {
                    numericZone = checkNumericZone (host);
                    if (numericZone == -1) { /* remainder of string must be an ifname */
                        ifname = host.substring (pos+1);
                    }
                }
                if ((addr = IPAddressUtil.textToNumericFormatV6(host)) == null && host.contains(":")) {
                    throw new UnknownHostException(host + ": invalid IPv6 address");
                }
            } else if (ipv6Expected) {
                // Means an IPv4 litteral between brackets!
                throw new UnknownHostException("["+host+"]");
            }
            InetAddress[] ret = new InetAddress[1];
            if(addr != null) {
                if (addr.length == Inet4Address.INADDRSZ) {
                    ret[0] = new Inet4Address(null, addr);
                } else {
                    if (ifname != null) {
                        ret[0] = new Inet6Address(null, addr, ifname);
                    } else {
                        ret[0] = new Inet6Address(null, addr, numericZone);
                    }
                }
                return ret;
            }
        } else if (ipv6Expected) {
            // We were expecting an IPv6 Litteral, but got something else
            throw new UnknownHostException("["+host+"]");
        }
        return getAllByName0(host, reqAddr, true);
    }

    /**
     * Returns the loopback address.
     * <p>
     * The InetAddress returned will represent the IPv4
     * loopback address, 127.0.0.1, or the IPv6 loopback
     * address, ::1. The IPv4 loopback address returned
     * is only one of many in the form 127.*.*.*
     *
     * <p>
     *  返回环回地址。
     * <p>
     *  返回的InetAddress将表示IPv4环回地址127.0.0.1或IPv6环回地址:: 1。返回的IPv4回送地址只是许多其中的一个,格式为127。*。*。*
     * 
     * 
     * @return  the InetAddress loopback instance.
     * @since 1.7
     */
    public static InetAddress getLoopbackAddress() {
        return impl.loopbackAddress();
    }


    /**
     * check if the literal address string has %nn appended
     * returns -1 if not, or the numeric value otherwise.
     *
     * %nn may also be a string that represents the displayName of
     * a currently available NetworkInterface.
     * <p>
     *  检查文本地址字符串是否带有％nn,如果没有,则返回-1,否则返回-1。
     * 
     * ％nn也可以是表示当前可用的NetworkInterface的displayName的字符串。
     * 
     */
    private static int checkNumericZone (String s) throws UnknownHostException {
        int percent = s.indexOf ('%');
        int slen = s.length();
        int digit, zone=0;
        if (percent == -1) {
            return -1;
        }
        for (int i=percent+1; i<slen; i++) {
            char c = s.charAt(i);
            if (c == ']') {
                if (i == percent+1) {
                    /* empty per-cent field */
                    return -1;
                }
                break;
            }
            if ((digit = Character.digit (c, 10)) < 0) {
                return -1;
            }
            zone = (zone * 10) + digit;
        }
        return zone;
    }

    private static InetAddress[] getAllByName0 (String host)
        throws UnknownHostException
    {
        return getAllByName0(host, true);
    }

    /**
     * package private so SocketPermission can call it
     * <p>
     *  package private使SocketPermission可以调用它
     * 
     */
    static InetAddress[] getAllByName0 (String host, boolean check)
        throws UnknownHostException  {
        return getAllByName0 (host, null, check);
    }

    private static InetAddress[] getAllByName0 (String host, InetAddress reqAddr, boolean check)
        throws UnknownHostException  {

        /* If it gets here it is presumed to be a hostname */
        /* Cache.get can return: null, unknownAddress, or InetAddress[] */

        /* make sure the connection to the host is allowed, before we
         * give out a hostname
         * <p>
         *  给出一个主机名
         * 
         */
        if (check) {
            SecurityManager security = System.getSecurityManager();
            if (security != null) {
                security.checkConnect(host, -1);
            }
        }

        InetAddress[] addresses = getCachedAddresses(host);

        /* If no entry in cache, then do the host lookup */
        if (addresses == null) {
            addresses = getAddressesFromNameService(host, reqAddr);
        }

        if (addresses == unknown_array)
            throw new UnknownHostException(host);

        return addresses.clone();
    }

    private static InetAddress[] getAddressesFromNameService(String host, InetAddress reqAddr)
        throws UnknownHostException
    {
        InetAddress[] addresses = null;
        boolean success = false;
        UnknownHostException ex = null;

        // Check whether the host is in the lookupTable.
        // 1) If the host isn't in the lookupTable when
        //    checkLookupTable() is called, checkLookupTable()
        //    would add the host in the lookupTable and
        //    return null. So we will do the lookup.
        // 2) If the host is in the lookupTable when
        //    checkLookupTable() is called, the current thread
        //    would be blocked until the host is removed
        //    from the lookupTable. Then this thread
        //    should try to look up the addressCache.
        //     i) if it found the addresses in the
        //        addressCache, checkLookupTable()  would
        //        return the addresses.
        //     ii) if it didn't find the addresses in the
        //         addressCache for any reason,
        //         it should add the host in the
        //         lookupTable and return null so the
        //         following code would do  a lookup itself.
        if ((addresses = checkLookupTable(host)) == null) {
            try {
                // This is the first thread which looks up the addresses
                // this host or the cache entry for this host has been
                // expired so this thread should do the lookup.
                for (NameService nameService : nameServices) {
                    try {
                        /*
                         * Do not put the call to lookup() inside the
                         * constructor.  if you do you will still be
                         * allocating space when the lookup fails.
                         * <p>
                         *  不要在构造函数中调用lookup()。如果你这样做,当查找失败时,你仍然会分配空间。
                         * 
                         */

                        addresses = nameService.lookupAllHostAddr(host);
                        success = true;
                        break;
                    } catch (UnknownHostException uhe) {
                        if (host.equalsIgnoreCase("localhost")) {
                            InetAddress[] local = new InetAddress[] { impl.loopbackAddress() };
                            addresses = local;
                            success = true;
                            break;
                        }
                        else {
                            addresses = unknown_array;
                            success = false;
                            ex = uhe;
                        }
                    }
                }

                // More to do?
                if (reqAddr != null && addresses.length > 1 && !addresses[0].equals(reqAddr)) {
                    // Find it?
                    int i = 1;
                    for (; i < addresses.length; i++) {
                        if (addresses[i].equals(reqAddr)) {
                            break;
                        }
                    }
                    // Rotate
                    if (i < addresses.length) {
                        InetAddress tmp, tmp2 = reqAddr;
                        for (int j = 0; j < i; j++) {
                            tmp = addresses[j];
                            addresses[j] = tmp2;
                            tmp2 = tmp;
                        }
                        addresses[i] = tmp2;
                    }
                }
                // Cache the address.
                cacheAddresses(host, addresses, success);

                if (!success && ex != null)
                    throw ex;

            } finally {
                // Delete host from the lookupTable and notify
                // all threads waiting on the lookupTable monitor.
                updateLookupTable(host);
            }
        }

        return addresses;
    }


    private static InetAddress[] checkLookupTable(String host) {
        synchronized (lookupTable) {
            // If the host isn't in the lookupTable, add it in the
            // lookuptable and return null. The caller should do
            // the lookup.
            if (lookupTable.containsKey(host) == false) {
                lookupTable.put(host, null);
                return null;
            }

            // If the host is in the lookupTable, it means that another
            // thread is trying to look up the addresses of this host.
            // This thread should wait.
            while (lookupTable.containsKey(host)) {
                try {
                    lookupTable.wait();
                } catch (InterruptedException e) {
                }
            }
        }

        // The other thread has finished looking up the addresses of
        // the host. This thread should retry to get the addresses
        // from the addressCache. If it doesn't get the addresses from
        // the cache, it will try to look up the addresses itself.
        InetAddress[] addresses = getCachedAddresses(host);
        if (addresses == null) {
            synchronized (lookupTable) {
                lookupTable.put(host, null);
                return null;
            }
        }

        return addresses;
    }

    private static void updateLookupTable(String host) {
        synchronized (lookupTable) {
            lookupTable.remove(host);
            lookupTable.notifyAll();
        }
    }

    /**
     * Returns an {@code InetAddress} object given the raw IP address .
     * The argument is in network byte order: the highest order
     * byte of the address is in {@code getAddress()[0]}.
     *
     * <p> This method doesn't block, i.e. no reverse name service lookup
     * is performed.
     *
     * <p> IPv4 address byte array must be 4 bytes long and IPv6 byte array
     * must be 16 bytes long
     *
     * <p>
     *  给定原始IP地址时返回{@code InetAddress}对象。参数是网络字节顺序：地址的最高位字节在{@code getAddress()[0]}中。
     * 
     *  <p>此方法不阻止,即不执行反向名称服务查找。
     * 
     *  <p> IPv4地址字节数组必须为4字节长,IPv6字节数组必须为16字节长
     * 
     * 
     * @param addr the raw IP address in network byte order
     * @return  an InetAddress object created from the raw IP address.
     * @exception  UnknownHostException  if IP address is of illegal length
     * @since 1.4
     */
    public static InetAddress getByAddress(byte[] addr)
        throws UnknownHostException {
        return getByAddress(null, addr);
    }

    private static InetAddress cachedLocalHost = null;
    private static long cacheTime = 0;
    private static final long maxCacheTime = 5000L;
    private static final Object cacheLock = new Object();

    /**
     * Returns the address of the local host. This is achieved by retrieving
     * the name of the host from the system, then resolving that name into
     * an {@code InetAddress}.
     *
     * <P>Note: The resolved address may be cached for a short period of time.
     * </P>
     *
     * <p>If there is a security manager, its
     * {@code checkConnect} method is called
     * with the local host name and {@code -1}
     * as its arguments to see if the operation is allowed.
     * If the operation is not allowed, an InetAddress representing
     * the loopback address is returned.
     *
     * <p>
     *  返回本地主机的地址。这是通过从系统检索主机的名称,然后将该名称解析为{@code InetAddress}来实现的。
     * 
     *  <P>注意：解析的地址可能会缓存一段很短的时间。
     * </P>
     * 
     *  <p>如果有安全管理器,则会使用本地主机名和{@code -1}作为其参数来调用其{@code checkConnect}方法,以查看是否允许操作。
     * 如果不允许该操作,则返回表示回送地址的InetAddress。
     * 
     * 
     * @return     the address of the local host.
     *
     * @exception  UnknownHostException  if the local host name could not
     *             be resolved into an address.
     *
     * @see SecurityManager#checkConnect
     * @see java.net.InetAddress#getByName(java.lang.String)
     */
    public static InetAddress getLocalHost() throws UnknownHostException {

        SecurityManager security = System.getSecurityManager();
        try {
            String local = impl.getLocalHostName();

            if (security != null) {
                security.checkConnect(local, -1);
            }

            if (local.equals("localhost")) {
                return impl.loopbackAddress();
            }

            InetAddress ret = null;
            synchronized (cacheLock) {
                long now = System.currentTimeMillis();
                if (cachedLocalHost != null) {
                    if ((now - cacheTime) < maxCacheTime) // Less than 5s old?
                        ret = cachedLocalHost;
                    else
                        cachedLocalHost = null;
                }

                // we are calling getAddressesFromNameService directly
                // to avoid getting localHost from cache
                if (ret == null) {
                    InetAddress[] localAddrs;
                    try {
                        localAddrs =
                            InetAddress.getAddressesFromNameService(local, null);
                    } catch (UnknownHostException uhe) {
                        // Rethrow with a more informative error message.
                        UnknownHostException uhe2 =
                            new UnknownHostException(local + ": " +
                                                     uhe.getMessage());
                        uhe2.initCause(uhe);
                        throw uhe2;
                    }
                    cachedLocalHost = localAddrs[0];
                    cacheTime = now;
                    ret = localAddrs[0];
                }
            }
            return ret;
        } catch (java.lang.SecurityException e) {
            return impl.loopbackAddress();
        }
    }

    /**
     * Perform class load-time initializations.
     * <p>
     *  执行类装入时初始化。
     * 
     */
    private static native void init();


    /*
     * Returns the InetAddress representing anyLocalAddress
     * (typically 0.0.0.0 or ::0)
     * <p>
     *  返回表示anyLocalAddress(通常为0.0.0.0或:: 0)的InetAddress,
     * 
     */
    static InetAddress anyLocalAddress() {
        return impl.anyLocalAddress();
    }

    /*
     * Load and instantiate an underlying impl class
     * <p>
     *  加载和实例化一个底层impl类
     * 
     */
    static InetAddressImpl loadImpl(String implName) {
        Object impl = null;

        /*
         * Property "impl.prefix" will be prepended to the classname
         * of the implementation object we instantiate, to which we
         * delegate the real work (like native methods).  This
         * property can vary across implementations of the java.
         * classes.  The default is an empty String "".
         * <p>
         * 属性"impl.prefix"将被添加到我们实例化的实现对象的类名,我们委托实际工作(像本地方法)。这个属性可以随着java的实现而变化。类。默认是一个空字符串""。
         * 
         */
        String prefix = AccessController.doPrivileged(
                      new GetPropertyAction("impl.prefix", ""));
        try {
            impl = Class.forName("java.net." + prefix + implName).newInstance();
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found: java.net." + prefix +
                               implName + ":\ncheck impl.prefix property " +
                               "in your properties file.");
        } catch (InstantiationException e) {
            System.err.println("Could not instantiate: java.net." + prefix +
                               implName + ":\ncheck impl.prefix property " +
                               "in your properties file.");
        } catch (IllegalAccessException e) {
            System.err.println("Cannot access class: java.net." + prefix +
                               implName + ":\ncheck impl.prefix property " +
                               "in your properties file.");
        }

        if (impl == null) {
            try {
                impl = Class.forName(implName).newInstance();
            } catch (Exception e) {
                throw new Error("System property impl.prefix incorrect");
            }
        }

        return (InetAddressImpl) impl;
    }

    private void readObjectNoData (ObjectInputStream s) throws
                         IOException, ClassNotFoundException {
        if (getClass().getClassLoader() != null) {
            throw new SecurityException ("invalid address type");
        }
    }

    private static final long FIELDS_OFFSET;
    private static final sun.misc.Unsafe UNSAFE;

    static {
        try {
            sun.misc.Unsafe unsafe = sun.misc.Unsafe.getUnsafe();
            FIELDS_OFFSET = unsafe.objectFieldOffset(
                InetAddress.class.getDeclaredField("holder")
            );
            UNSAFE = unsafe;
        } catch (ReflectiveOperationException e) {
            throw new Error(e);
        }
    }

    private void readObject (ObjectInputStream s) throws
                         IOException, ClassNotFoundException {
        if (getClass().getClassLoader() != null) {
            throw new SecurityException ("invalid address type");
        }
        GetField gf = s.readFields();
        String host = (String)gf.get("hostName", null);
        int address= gf.get("address", 0);
        int family= gf.get("family", 0);
        InetAddressHolder h = new InetAddressHolder(host, address, family);
        UNSAFE.putObject(this, FIELDS_OFFSET, h);
    }

    /* needed because the serializable fields no longer exist */

    /**
    /* <p>
    /* 
     * @serialField hostName String
     * @serialField address int
     * @serialField family int
     */
    private static final ObjectStreamField[] serialPersistentFields = {
        new ObjectStreamField("hostName", String.class),
        new ObjectStreamField("address", int.class),
        new ObjectStreamField("family", int.class),
    };

    private void writeObject (ObjectOutputStream s) throws
                        IOException {
        if (getClass().getClassLoader() != null) {
            throw new SecurityException ("invalid address type");
        }
        PutField pf = s.putFields();
        pf.put("hostName", holder().getHostName());
        pf.put("address", holder().getAddress());
        pf.put("family", holder().getFamily());
        s.writeFields();
    }
}

/*
 * Simple factory to create the impl
 * <p>
 *  简单工厂创建impl
 */
class InetAddressImplFactory {

    static InetAddressImpl create() {
        return InetAddress.loadImpl(isIPv6Supported() ?
                                    "Inet6AddressImpl" : "Inet4AddressImpl");
    }

    static native boolean isIPv6Supported();
}
