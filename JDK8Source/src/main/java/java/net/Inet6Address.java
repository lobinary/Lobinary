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
import java.io.ObjectStreamField;
import java.util.Enumeration;
import java.util.Arrays;

/**
 * This class represents an Internet Protocol version 6 (IPv6) address.
 * Defined by <a href="http://www.ietf.org/rfc/rfc2373.txt">
 * <i>RFC&nbsp;2373: IP Version 6 Addressing Architecture</i></a>.
 *
 * <h3> <A NAME="format">Textual representation of IP addresses</a> </h3>
 *
 * Textual representation of IPv6 address used as input to methods
 * takes one of the following forms:
 *
 * <ol>
 *   <li><p> <A NAME="lform">The preferred form</a> is x:x:x:x:x:x:x:x,
 *   where the 'x's are
 *   the hexadecimal values of the eight 16-bit pieces of the
 *   address. This is the full form.  For example,
 *
 *   <blockquote><table cellpadding=0 cellspacing=0 summary="layout">
 *   <tr><td>{@code 1080:0:0:0:8:800:200C:417A}<td></tr>
 *   </table></blockquote>
 *
 *   <p> Note that it is not necessary to write the leading zeros in
 *   an individual field. However, there must be at least one numeral
 *   in every field, except as described below.</li>
 *
 *   <li><p> Due to some methods of allocating certain styles of IPv6
 *   addresses, it will be common for addresses to contain long
 *   strings of zero bits. In order to make writing addresses
 *   containing zero bits easier, a special syntax is available to
 *   compress the zeros. The use of "::" indicates multiple groups
 *   of 16-bits of zeros. The "::" can only appear once in an address.
 *   The "::" can also be used to compress the leading and/or trailing
 *   zeros in an address. For example,
 *
 *   <blockquote><table cellpadding=0 cellspacing=0 summary="layout">
 *   <tr><td>{@code 1080::8:800:200C:417A}<td></tr>
 *   </table></blockquote>
 *
 *   <li><p> An alternative form that is sometimes more convenient
 *   when dealing with a mixed environment of IPv4 and IPv6 nodes is
 *   x:x:x:x:x:x:d.d.d.d, where the 'x's are the hexadecimal values
 *   of the six high-order 16-bit pieces of the address, and the 'd's
 *   are the decimal values of the four low-order 8-bit pieces of the
 *   standard IPv4 representation address, for example,
 *
 *   <blockquote><table cellpadding=0 cellspacing=0 summary="layout">
 *   <tr><td>{@code ::FFFF:129.144.52.38}<td></tr>
 *   <tr><td>{@code ::129.144.52.38}<td></tr>
 *   </table></blockquote>
 *
 *   <p> where "::FFFF:d.d.d.d" and "::d.d.d.d" are, respectively, the
 *   general forms of an IPv4-mapped IPv6 address and an
 *   IPv4-compatible IPv6 address. Note that the IPv4 portion must be
 *   in the "d.d.d.d" form. The following forms are invalid:
 *
 *   <blockquote><table cellpadding=0 cellspacing=0 summary="layout">
 *   <tr><td>{@code ::FFFF:d.d.d}<td></tr>
 *   <tr><td>{@code ::FFFF:d.d}<td></tr>
 *   <tr><td>{@code ::d.d.d}<td></tr>
 *   <tr><td>{@code ::d.d}<td></tr>
 *   </table></blockquote>
 *
 *   <p> The following form:
 *
 *   <blockquote><table cellpadding=0 cellspacing=0 summary="layout">
 *   <tr><td>{@code ::FFFF:d}<td></tr>
 *   </table></blockquote>
 *
 *   <p> is valid, however it is an unconventional representation of
 *   the IPv4-compatible IPv6 address,
 *
 *   <blockquote><table cellpadding=0 cellspacing=0 summary="layout">
 *   <tr><td>{@code ::255.255.0.d}<td></tr>
 *   </table></blockquote>
 *
 *   <p> while "::d" corresponds to the general IPv6 address
 *   "0:0:0:0:0:0:0:d".</li>
 * </ol>
 *
 * <p> For methods that return a textual representation as output
 * value, the full form is used. Inet6Address will return the full
 * form because it is unambiguous when used in combination with other
 * textual data.
 *
 * <h4> Special IPv6 address </h4>
 *
 * <blockquote>
 * <table cellspacing=2 summary="Description of IPv4-mapped address">
 * <tr><th valign=top><i>IPv4-mapped address</i></th>
 *         <td>Of the form::ffff:w.x.y.z, this IPv6 address is used to
 *         represent an IPv4 address. It allows the native program to
 *         use the same address data structure and also the same
 *         socket when communicating with both IPv4 and IPv6 nodes.
 *
 *         <p>In InetAddress and Inet6Address, it is used for internal
 *         representation; it has no functional role. Java will never
 *         return an IPv4-mapped address.  These classes can take an
 *         IPv4-mapped address as input, both in byte array and text
 *         representation. However, it will be converted into an IPv4
 *         address.</td></tr>
 * </table></blockquote>
 *
 * <h4><A NAME="scoped">Textual representation of IPv6 scoped addresses</a></h4>
 *
 * <p> The textual representation of IPv6 addresses as described above can be
 * extended to specify IPv6 scoped addresses. This extension to the basic
 * addressing architecture is described in [draft-ietf-ipngwg-scoping-arch-04.txt].
 *
 * <p> Because link-local and site-local addresses are non-global, it is possible
 * that different hosts may have the same destination address and may be
 * reachable through different interfaces on the same originating system. In
 * this case, the originating system is said to be connected to multiple zones
 * of the same scope. In order to disambiguate which is the intended destination
 * zone, it is possible to append a zone identifier (or <i>scope_id</i>) to an
 * IPv6 address.
 *
 * <p> The general format for specifying the <i>scope_id</i> is the following:
 *
 * <blockquote><i>IPv6-address</i>%<i>scope_id</i></blockquote>
 * <p> The IPv6-address is a literal IPv6 address as described above.
 * The <i>scope_id</i> refers to an interface on the local system, and it can be
 * specified in two ways.
 * <ol><li><i>As a numeric identifier.</i> This must be a positive integer
 * that identifies the particular interface and scope as understood by the
 * system. Usually, the numeric values can be determined through administration
 * tools on the system. Each interface may have multiple values, one for each
 * scope. If the scope is unspecified, then the default value used is zero.</li>
 * <li><i>As a string.</i> This must be the exact string that is returned by
 * {@link java.net.NetworkInterface#getName()} for the particular interface in
 * question. When an Inet6Address is created in this way, the numeric scope-id
 * is determined at the time the object is created by querying the relevant
 * NetworkInterface.</li></ol>
 *
 * <p> Note also, that the numeric <i>scope_id</i> can be retrieved from
 * Inet6Address instances returned from the NetworkInterface class. This can be
 * used to find out the current scope ids configured on the system.
 * <p>
 *  此类表示Internet协议版本6(IPv6)地址。
 * 由<a href="http://www.ietf.org/rfc/rfc2373.txt"> <i> RFC&nbsp; 2373：IP版本6寻址体系结构</i>定义。
 * 
 *  <h3> <A NAME="format"> IP地址的文字表示</a> </h3>
 * 
 *  用作方法输入的IPv6地址的文本表示采用以下形式之一：
 * 
 * <ol>
 *  <li> <p> <A NAME="lform">首选表单</a>是x：x：x：x：x：x：x：x,其中"x"是八个16的十六进制值位的地址。这是完整的形式。例如,
 * 
 *  <blockquote> <table cellpadding = 0 cellspacing = 0 summary ="layout"> <tr> <td> {@ code 1080：0：0：0：8：800：200C：417A}
 *  <td> <// table> </blockquote>。
 * 
 *  <p>请注意,不必在单个字段中写入前导零。但是,除非如下所述,否则每个字段中必须至少有一个数字。</li>
 * 
 *  <li> <p>由于分配某些样式的IPv6地址的一些方法,地址通常包含零位的长字符串。为了使包含零位的写地址更容易,可使用特殊语法来压缩零。使用"::"表示多组16位零。
 *  "::"只能在地址中出现一次。 "::"也可以用于压缩地址中的前导零和/或尾部零。例如,。
 * 
 * <blockquote> <table cellpadding = 0 cellspacing = 0 summary ="layout"> <tr> <td> {@ code 1080 :: 8：800：200C：417A}
 *  <td> </tr> </table> blockquote>。
 * 
 *  <li> <p>当处理IPv4和IPv6节点的混合环境时,有时更方便的另一种形式是x：x：x：x：x：x：dddd,其中'x是十六进制值六个高位16位地址,以及'd's是标准IPv4表示地址的四个低位
 * 8位段的十进制值,例如,。
 * 
 *  <blockquote> <table cellpadding = 0 cellspacing = 0 summary ="layout"> <tr> <td> {@ code :: FFFF：129.144.52.38}
 *  <td> </tr> <tr> <td> {@ code :: 129.144.52.38} <td> </tr> </table> </blockquote>。
 * 
 *  <p>其中":: FFFF：d.d.d.d"和":: d.d.d.d"分别是IPv4映射的IPv6地址和IPv4兼容的IPv6地址的一般形式。请注意,IPv4部分必须采用"d.d.d.d"形式。
 * 以下表单无效：。
 * 
 *  <blockquote> <table cellpadding = 0 cellspacing = 0 summary ="layout"> <tr> <td> {@ code :: FFFF：ddd}
 *  <td> </tr> <tr> <td> {@ code :: FFFF：dd} <td> </tr> <tr> <td> {@ code :: ddd} <td> </tr> <tr> <td> {@ code :: dd}
 *  <td> </tr > </table> </blockquote>。
 * 
 *  <p>以下表单：
 * 
 *  <blockquote> <table cellpadding = 0 cellspacing = 0 summary ="layout"> <tr> <td> {@ code :: FFFF：d} 
 * <td> </tr> </table> </blockquote>。
 * 
 *  <p>有效,但是它是IPv4兼容的IPv6地址的非常规表示,
 * 
 * <blockquote> <table cellpadding = 0 cellspacing = 0 summary ="layout"> <tr> <td> {@ code :: 255.255.0.d}
 *  <td> </tr> </table> </blockquote>。
 * 
 *  <p>,而":: d"对应于一般IPv6地址"0：0：0：0：0：0：0：d"
 * </ol>
 * 
 *  <p>对于将文本表示作为输出值的方法,将使用完整形式。 Inet6Address将返回完整表单,因为当与其他文本数据结合使用时,它是明确的。
 * 
 *  <h4>特殊IPv6地址</h4>
 * 
 * <blockquote>
 * <table cellspacing=2 summary="Description of IPv4-mapped address">
 *  <tr> <th> valign = top> <i> IPv4映射地址</i> </th> <td>格式为:: ffff：w.x.y.z时,此IPv6地址用于表示IPv4地址。
 * 它允许本地程序在与IPv4和IPv6节点通信时使用相同的地址数据结构以及相同的套接字。
 * 
 *  <p>在InetAddress和Inet6Address中,它用于内部表示;它没有功能作用。 Java永远不会返回IPv4映射地址。这些类可以采用IPv4映射地址作为输入,以字节数组和文本表示。
 * 但是,它将转换为IPv4地址。</td> </tr> </table> </blockquote>。
 * 
 *  <h4> <A NAME="scoped"> IPv6范围地址的文字表示</a> </h4>
 * 
 *  <p>如上所述的IPv6地址的文本表示可以扩展为指定IPv6范围地址。这种对基本寻址架构的扩展在[draft-ietf-ipngwg-scoping-arch-04.txt]中描述。
 * 
 * <p>由于链路本地和站点本地地址是非全局的,因此不同的主机可能具有相同的目的地址并且可能通过相同始发系统上的不同接口可达。在这种情况下,始发系统被称为连接到相同范围的多个区域。
 * 为了消除预定目的地区域的歧义,可以向IPv6地址附加区域标识符(或<scope_id </i>)。
 * 
 *  <p>指定<i> scope_id </i>的一般格式如下：
 * 
 *  <blockquote> <i> IPv6地址</i>％<i> scope_id </i> </blockquote> <p> IPv6地址是如上所述的文字IPv6地址。
 *  <i> scope_id </i>是指本地系统上的一个接口,它可以通过两种方式指定。 <ol> <li> <i>作为数字标识符。</i>这必须是一个正整数,用于标识系统理解的特定接口和范围。
 * 通常,可以通过系统上的管理工具确定数值。每个接口可以有多个值,每个范围一个值。如果范围未指定,则使用的默认值为零。</li> <li> <i>作为字符串。
 * </i>这必须是{@link java.net.NetworkInterface #getName()}。
 * 当以这种方式创建Inet6Address时,通过查询相关的NetworkInterface在创建对象时确定数字作用域ID。</li> </ol>。
 * 
 * <p>请注意,可以从NetworkInterface类返回的Inet6Address实例检索数字<i> scope_id </i>。这可以用于查找系统上配置的当前作用域ID。
 * 
 * 
 * @since 1.4
 */

public final
class Inet6Address extends InetAddress {
    final static int INADDRSZ = 16;

    /*
     * cached scope_id - for link-local address use only.
     * <p>
     *  缓存的scope_id  - 仅供链路本地地址使用。
     * 
     */
    private transient int cached_scope_id;  // 0

    private class Inet6AddressHolder {

        private Inet6AddressHolder() {
            ipaddress = new byte[INADDRSZ];
        }

        private Inet6AddressHolder(
            byte[] ipaddress, int scope_id, boolean scope_id_set,
            NetworkInterface ifname, boolean scope_ifname_set)
        {
            this.ipaddress = ipaddress;
            this.scope_id = scope_id;
            this.scope_id_set = scope_id_set;
            this.scope_ifname_set = scope_ifname_set;
            this.scope_ifname = ifname;
        }

        /**
         * Holds a 128-bit (16 bytes) IPv6 address.
         * <p>
         *  保存128位(16字节)IPv6地址。
         * 
         */
        byte[] ipaddress;

        /**
         * scope_id. The scope specified when the object is created. If the object
         * is created with an interface name, then the scope_id is not determined
         * until the time it is needed.
         * <p>
         *  scope_id。创建对象时指定的作用域。如果使用接口名称创建对象,则在需要之前不会确定scope_id。
         * 
         */
        int scope_id;  // 0

        /**
         * This will be set to true when the scope_id field contains a valid
         * integer scope_id.
         * <p>
         *  当scope_id字段包含有效的整数scope_id时,将设置为true。
         * 
         */
        boolean scope_id_set;  // false

        /**
         * scoped interface. scope_id is derived from this as the scope_id of the first
         * address whose scope is the same as this address for the named interface.
         * <p>
         *  作用域接口。 scope_id派生自此作为作用域与指定接口的此地址相同的第一个地址的scope_id。
         * 
         */
        NetworkInterface scope_ifname;  // null

        /**
         * set if the object is constructed with a scoped
         * interface instead of a numeric scope id.
         * <p>
         *  如果对象是使用作用域接口而不是数字作用域ID构造的,则设置。
         * 
         */
        boolean scope_ifname_set; // false;

        void setAddr(byte addr[]) {
            if (addr.length == INADDRSZ) { // normal IPv6 address
                System.arraycopy(addr, 0, ipaddress, 0, INADDRSZ);
            }
        }

        void init(byte addr[], int scope_id) {
            setAddr(addr);

            if (scope_id >= 0) {
                this.scope_id = scope_id;
                this.scope_id_set = true;
            }
        }

        void init(byte addr[], NetworkInterface nif)
            throws UnknownHostException
        {
            setAddr(addr);

            if (nif != null) {
                this.scope_id = deriveNumericScope(ipaddress, nif);
                this.scope_id_set = true;
                this.scope_ifname = nif;
                this.scope_ifname_set = true;
            }
        }

        String getHostAddress() {
            String s = numericToTextFormat(ipaddress);
            if (scope_ifname != null) { /* must check this first */
                s = s + "%" + scope_ifname.getName();
            } else if (scope_id_set) {
                s = s + "%" + scope_id;
            }
            return s;
        }

        public boolean equals(Object o) {
            if (! (o instanceof Inet6AddressHolder)) {
                return false;
            }
            Inet6AddressHolder that = (Inet6AddressHolder)o;

            return Arrays.equals(this.ipaddress, that.ipaddress);
        }

        public int hashCode() {
            if (ipaddress != null) {

                int hash = 0;
                int i=0;
                while (i<INADDRSZ) {
                    int j=0;
                    int component=0;
                    while (j<4 && i<INADDRSZ) {
                        component = (component << 8) + ipaddress[i];
                        j++;
                        i++;
                    }
                    hash += component;
                }
                return hash;

            } else {
                return 0;
            }
        }

        boolean isIPv4CompatibleAddress() {
            if ((ipaddress[0] == 0x00) && (ipaddress[1] == 0x00) &&
                (ipaddress[2] == 0x00) && (ipaddress[3] == 0x00) &&
                (ipaddress[4] == 0x00) && (ipaddress[5] == 0x00) &&
                (ipaddress[6] == 0x00) && (ipaddress[7] == 0x00) &&
                (ipaddress[8] == 0x00) && (ipaddress[9] == 0x00) &&
                (ipaddress[10] == 0x00) && (ipaddress[11] == 0x00))  {
                return true;
            }
            return false;
        }

        boolean isMulticastAddress() {
            return ((ipaddress[0] & 0xff) == 0xff);
        }

        boolean isAnyLocalAddress() {
            byte test = 0x00;
            for (int i = 0; i < INADDRSZ; i++) {
                test |= ipaddress[i];
            }
            return (test == 0x00);
        }

        boolean isLoopbackAddress() {
            byte test = 0x00;
            for (int i = 0; i < 15; i++) {
                test |= ipaddress[i];
            }
            return (test == 0x00) && (ipaddress[15] == 0x01);
        }

        boolean isLinkLocalAddress() {
            return ((ipaddress[0] & 0xff) == 0xfe
                    && (ipaddress[1] & 0xc0) == 0x80);
        }


        boolean isSiteLocalAddress() {
            return ((ipaddress[0] & 0xff) == 0xfe
                    && (ipaddress[1] & 0xc0) == 0xc0);
        }

        boolean isMCGlobal() {
            return ((ipaddress[0] & 0xff) == 0xff
                    && (ipaddress[1] & 0x0f) == 0x0e);
        }

        boolean isMCNodeLocal() {
            return ((ipaddress[0] & 0xff) == 0xff
                    && (ipaddress[1] & 0x0f) == 0x01);
        }

        boolean isMCLinkLocal() {
            return ((ipaddress[0] & 0xff) == 0xff
                    && (ipaddress[1] & 0x0f) == 0x02);
        }

        boolean isMCSiteLocal() {
            return ((ipaddress[0] & 0xff) == 0xff
                    && (ipaddress[1] & 0x0f) == 0x05);
        }

        boolean isMCOrgLocal() {
            return ((ipaddress[0] & 0xff) == 0xff
                    && (ipaddress[1] & 0x0f) == 0x08);
        }
    }

    private final transient Inet6AddressHolder holder6;

    private static final long serialVersionUID = 6880410070516793377L;

    // Perform native initialization
    static { init(); }

    Inet6Address() {
        super();
        holder.init(null, IPv6);
        holder6 = new Inet6AddressHolder();
    }

    /* checking of value for scope_id should be done by caller
     * scope_id must be >= 0, or -1 to indicate not being set
     * <p>
     *  scope_id必须> = 0,或-1表示未设置
     * 
     */
    Inet6Address(String hostName, byte addr[], int scope_id) {
        holder.init(hostName, IPv6);
        holder6 = new Inet6AddressHolder();
        holder6.init(addr, scope_id);
    }

    Inet6Address(String hostName, byte addr[]) {
        holder6 = new Inet6AddressHolder();
        try {
            initif (hostName, addr, null);
        } catch (UnknownHostException e) {} /* cant happen if ifname is null */
    }

    Inet6Address (String hostName, byte addr[], NetworkInterface nif)
        throws UnknownHostException
    {
        holder6 = new Inet6AddressHolder();
        initif (hostName, addr, nif);
    }

    Inet6Address (String hostName, byte addr[], String ifname)
        throws UnknownHostException
    {
        holder6 = new Inet6AddressHolder();
        initstr (hostName, addr, ifname);
    }

    /**
     * Create an Inet6Address in the exact manner of {@link
     * InetAddress#getByAddress(String,byte[])} except that the IPv6 scope_id is
     * set to the value corresponding to the given interface for the address
     * type specified in {@code addr}. The call will fail with an
     * UnknownHostException if the given interface does not have a numeric
     * scope_id assigned for the given address type (eg. link-local or site-local).
     * See <a href="Inet6Address.html#scoped">here</a> for a description of IPv6
     * scoped addresses.
     *
     * <p>
     *  除了将IPv6 scope_id设置为对应于在{@code addr}中指定的地址类型的给定接口的值之外,以{@link InetAddress#getByAddress(String,byte [])}
     * 的确切方式创建Inet6Address。
     * 如果给定接口没有为给定地址类型分配数字scope_id(例如,link-local或site-local),则调用将失败并返回UnknownHostException。
     * 有关IPv6范围地址的说明,请参见<a href="Inet6Address.html#scoped">此处</a>。
     * 
     * 
     * @param host the specified host
     * @param addr the raw IP address in network byte order
     * @param nif an interface this address must be associated with.
     * @return  an Inet6Address object created from the raw IP address.
     * @throws  UnknownHostException
     *          if IP address is of illegal length, or if the interface does not
     *          have a numeric scope_id assigned for the given address type.
     *
     * @since 1.5
     */
    public static Inet6Address getByAddress(String host, byte[] addr,
                                            NetworkInterface nif)
        throws UnknownHostException
    {
        if (host != null && host.length() > 0 && host.charAt(0) == '[') {
            if (host.charAt(host.length()-1) == ']') {
                host = host.substring(1, host.length() -1);
            }
        }
        if (addr != null) {
            if (addr.length == Inet6Address.INADDRSZ) {
                return new Inet6Address(host, addr, nif);
            }
        }
        throw new UnknownHostException("addr is of illegal length");
    }

    /**
     * Create an Inet6Address in the exact manner of {@link
     * InetAddress#getByAddress(String,byte[])} except that the IPv6 scope_id is
     * set to the given numeric value. The scope_id is not checked to determine
     * if it corresponds to any interface on the system.
     * See <a href="Inet6Address.html#scoped">here</a> for a description of IPv6
     * scoped addresses.
     *
     * <p>
     * 除了将IPv6 scope_id设置为给定的数值之外,以{@link InetAddress#getByAddress(String,byte [])}的确切方式创建Inet6Address。
     * 不检查scope_id以确定它是否对应于系统上的任何接口。有关IPv6范围地址的说明,请参见<a href="Inet6Address.html#scoped">此处</a>。
     * 
     * 
     * @param host the specified host
     * @param addr the raw IP address in network byte order
     * @param scope_id the numeric scope_id for the address.
     * @return  an Inet6Address object created from the raw IP address.
     * @throws  UnknownHostException  if IP address is of illegal length.
     *
     * @since 1.5
     */
    public static Inet6Address getByAddress(String host, byte[] addr,
                                            int scope_id)
        throws UnknownHostException
    {
        if (host != null && host.length() > 0 && host.charAt(0) == '[') {
            if (host.charAt(host.length()-1) == ']') {
                host = host.substring(1, host.length() -1);
            }
        }
        if (addr != null) {
            if (addr.length == Inet6Address.INADDRSZ) {
                return new Inet6Address(host, addr, scope_id);
            }
        }
        throw new UnknownHostException("addr is of illegal length");
    }

    private void initstr(String hostName, byte addr[], String ifname)
        throws UnknownHostException
    {
        try {
            NetworkInterface nif = NetworkInterface.getByName (ifname);
            if (nif == null) {
                throw new UnknownHostException ("no such interface " + ifname);
            }
            initif (hostName, addr, nif);
        } catch (SocketException e) {
            throw new UnknownHostException ("SocketException thrown" + ifname);
        }
    }

    private void initif(String hostName, byte addr[], NetworkInterface nif)
        throws UnknownHostException
    {
        int family = -1;
        holder6.init(addr, nif);

        if (addr.length == INADDRSZ) { // normal IPv6 address
            family = IPv6;
        }
        holder.init(hostName, family);
    }

    /* check the two Ipv6 addresses and return false if they are both
     * non global address types, but not the same.
     * (ie. one is sitelocal and the other linklocal)
     * return true otherwise.
     * <p>
     *  非全局地址类型,但不一样。 (即一个是sitelocal和另一个linklocal)否则返回true。
     * 
     */

    private static boolean isDifferentLocalAddressType(
        byte[] thisAddr, byte[] otherAddr) {

        if (Inet6Address.isLinkLocalAddress(thisAddr) &&
                !Inet6Address.isLinkLocalAddress(otherAddr)) {
            return false;
        }
        if (Inet6Address.isSiteLocalAddress(thisAddr) &&
                !Inet6Address.isSiteLocalAddress(otherAddr)) {
            return false;
        }
        return true;
    }

    private static int deriveNumericScope (byte[] thisAddr, NetworkInterface ifc) throws UnknownHostException {
        Enumeration<InetAddress> addresses = ifc.getInetAddresses();
        while (addresses.hasMoreElements()) {
            InetAddress addr = addresses.nextElement();
            if (!(addr instanceof Inet6Address)) {
                continue;
            }
            Inet6Address ia6_addr = (Inet6Address)addr;
            /* check if site or link local prefixes match */
            if (!isDifferentLocalAddressType(thisAddr, ia6_addr.getAddress())){
                /* type not the same, so carry on searching */
                continue;
            }
            /* found a matching address - return its scope_id */
            return ia6_addr.getScopeId();
        }
        throw new UnknownHostException ("no scope_id found");
    }

    private int deriveNumericScope (String ifname) throws UnknownHostException {
        Enumeration<NetworkInterface> en;
        try {
            en = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            throw new UnknownHostException ("could not enumerate local network interfaces");
        }
        while (en.hasMoreElements()) {
            NetworkInterface ifc = en.nextElement();
            if (ifc.getName().equals (ifname)) {
                return deriveNumericScope(holder6.ipaddress, ifc);
            }
        }
        throw new UnknownHostException ("No matching address found for interface : " +ifname);
    }

    /**
    /* <p>
    /* 
     * @serialField ipaddress byte[]
     * @serialField scope_id int
     * @serialField scope_id_set boolean
     * @serialField scope_ifname_set boolean
     * @serialField ifname String
     */

    private static final ObjectStreamField[] serialPersistentFields = {
         new ObjectStreamField("ipaddress", byte[].class),
         new ObjectStreamField("scope_id", int.class),
         new ObjectStreamField("scope_id_set", boolean.class),
         new ObjectStreamField("scope_ifname_set", boolean.class),
         new ObjectStreamField("ifname", String.class)
    };

    private static final long FIELDS_OFFSET;
    private static final sun.misc.Unsafe UNSAFE;

    static {
        try {
            sun.misc.Unsafe unsafe = sun.misc.Unsafe.getUnsafe();
            FIELDS_OFFSET = unsafe.objectFieldOffset(
                    Inet6Address.class.getDeclaredField("holder6"));
            UNSAFE = unsafe;
        } catch (ReflectiveOperationException e) {
            throw new Error(e);
        }
    }

    /**
     * restore the state of this object from stream
     * including the scope information, only if the
     * scoped interface name is valid on this system
     * <p>
     *  仅当此系统上的作用域接口名称有效时,才从包含作用域信息的流中恢复此对象的状态
     * 
     */
    private void readObject(ObjectInputStream s)
        throws IOException, ClassNotFoundException {
        NetworkInterface scope_ifname = null;

        if (getClass().getClassLoader() != null) {
            throw new SecurityException ("invalid address type");
        }

        ObjectInputStream.GetField gf = s.readFields();
        byte[] ipaddress = (byte[])gf.get("ipaddress", null);
        int scope_id = (int)gf.get("scope_id", -1);
        boolean scope_id_set = (boolean)gf.get("scope_id_set", false);
        boolean scope_ifname_set = (boolean)gf.get("scope_ifname_set", false);
        String ifname = (String)gf.get("ifname", null);

        if (ifname != null && !"".equals (ifname)) {
            try {
                scope_ifname = NetworkInterface.getByName(ifname);
                if (scope_ifname == null) {
                    /* the interface does not exist on this system, so we clear
                    /* <p>
                    /* 
                     * the scope information completely */
                    scope_id_set = false;
                    scope_ifname_set = false;
                    scope_id = 0;
                } else {
                    scope_ifname_set = true;
                    try {
                        scope_id = deriveNumericScope (ipaddress, scope_ifname);
                    } catch (UnknownHostException e) {
                        // typically should not happen, but it may be that
                        // the machine being used for deserialization has
                        // the same interface name but without IPv6 configured.
                    }
                }
            } catch (SocketException e) {}
        }

        /* if ifname was not supplied, then the numeric info is used */

        ipaddress = ipaddress.clone();

        // Check that our invariants are satisfied
        if (ipaddress.length != INADDRSZ) {
            throw new InvalidObjectException("invalid address length: "+
                                             ipaddress.length);
        }

        if (holder.getFamily() != IPv6) {
            throw new InvalidObjectException("invalid address family type");
        }

        Inet6AddressHolder h = new Inet6AddressHolder(
            ipaddress, scope_id, scope_id_set, scope_ifname, scope_ifname_set
        );

        UNSAFE.putObject(this, FIELDS_OFFSET, h);
    }

    /**
     * default behavior is overridden in order to write the
     * scope_ifname field as a String, rather than a NetworkInterface
     * which is not serializable
     * <p>
     *  默认行为被覆盖,以便将scope_ifname字段写为String,而不是不可序列化的NetworkInterface
     * 
     */
    private synchronized void writeObject(ObjectOutputStream s)
        throws IOException
    {
            String ifname = null;

        if (holder6.scope_ifname != null) {
            ifname = holder6.scope_ifname.getName();
            holder6.scope_ifname_set = true;
        }
        ObjectOutputStream.PutField pfields = s.putFields();
        pfields.put("ipaddress", holder6.ipaddress);
        pfields.put("scope_id", holder6.scope_id);
        pfields.put("scope_id_set", holder6.scope_id_set);
        pfields.put("scope_ifname_set", holder6.scope_ifname_set);
        pfields.put("ifname", ifname);
        s.writeFields();
    }

    /**
     * Utility routine to check if the InetAddress is an IP multicast
     * address. 11111111 at the start of the address identifies the
     * address as being a multicast address.
     *
     * <p>
     *  用于检查InetAddress是否是IP多播地址的实用程序。 11111111在地址开始时将该地址标识为多播地址。
     * 
     * 
     * @return a {@code boolean} indicating if the InetAddress is an IP
     *         multicast address
     *
     * @since JDK1.1
     */
    @Override
    public boolean isMulticastAddress() {
        return holder6.isMulticastAddress();
    }

    /**
     * Utility routine to check if the InetAddress in a wildcard address.
     *
     * <p>
     *  用于检查通配符地址中的InetAddress的实用程序。
     * 
     * 
     * @return a {@code boolean} indicating if the Inetaddress is
     *         a wildcard address.
     *
     * @since 1.4
     */
    @Override
    public boolean isAnyLocalAddress() {
        return holder6.isAnyLocalAddress();
    }

    /**
     * Utility routine to check if the InetAddress is a loopback address.
     *
     * <p>
     *  用于检查InetAddress是否是环回地址的实用程序。
     * 
     * 
     * @return a {@code boolean} indicating if the InetAddress is a loopback
     *         address; or false otherwise.
     *
     * @since 1.4
     */
    @Override
    public boolean isLoopbackAddress() {
        return holder6.isLoopbackAddress();
    }

    /**
     * Utility routine to check if the InetAddress is an link local address.
     *
     * <p>
     *  用于检查InetAddress是否是链路本地地址的实用程序。
     * 
     * 
     * @return a {@code boolean} indicating if the InetAddress is a link local
     *         address; or false if address is not a link local unicast address.
     *
     * @since 1.4
     */
    @Override
    public boolean isLinkLocalAddress() {
        return holder6.isLinkLocalAddress();
    }

    /* static version of above */
    static boolean isLinkLocalAddress(byte[] ipaddress) {
        return ((ipaddress[0] & 0xff) == 0xfe
                && (ipaddress[1] & 0xc0) == 0x80);
    }

    /**
     * Utility routine to check if the InetAddress is a site local address.
     *
     * <p>
     *  用于检查InetAddress是否是站点本地地址的实用程序。
     * 
     * 
     * @return a {@code boolean} indicating if the InetAddress is a site local
     *         address; or false if address is not a site local unicast address.
     *
     * @since 1.4
     */
    @Override
    public boolean isSiteLocalAddress() {
        return holder6.isSiteLocalAddress();
    }

    /* static version of above */
    static boolean isSiteLocalAddress(byte[] ipaddress) {
        return ((ipaddress[0] & 0xff) == 0xfe
                && (ipaddress[1] & 0xc0) == 0xc0);
    }

    /**
     * Utility routine to check if the multicast address has global scope.
     *
     * <p>
     *  用于检查多播地址是否具有全局范围的实用程序。
     * 
     * 
     * @return a {@code boolean} indicating if the address has is a multicast
     *         address of global scope, false if it is not of global scope or
     *         it is not a multicast address
     *
     * @since 1.4
     */
    @Override
    public boolean isMCGlobal() {
        return holder6.isMCGlobal();
    }

    /**
     * Utility routine to check if the multicast address has node scope.
     *
     * <p>
     *  用于检查多播地址是否具有节点范围的实用程序。
     * 
     * 
     * @return a {@code boolean} indicating if the address has is a multicast
     *         address of node-local scope, false if it is not of node-local
     *         scope or it is not a multicast address
     *
     * @since 1.4
     */
    @Override
    public boolean isMCNodeLocal() {
        return holder6.isMCNodeLocal();
    }

    /**
     * Utility routine to check if the multicast address has link scope.
     *
     * <p>
     *  用于检查多播地址是否具有链路范围的实用程序。
     * 
     * 
     * @return a {@code boolean} indicating if the address has is a multicast
     *         address of link-local scope, false if it is not of link-local
     *         scope or it is not a multicast address
     *
     * @since 1.4
     */
    @Override
    public boolean isMCLinkLocal() {
        return holder6.isMCLinkLocal();
    }

    /**
     * Utility routine to check if the multicast address has site scope.
     *
     * <p>
     *  用于检查多播地址是否具有站点范围的实用程序。
     * 
     * 
     * @return a {@code boolean} indicating if the address has is a multicast
     *         address of site-local scope, false if it is not  of site-local
     *         scope or it is not a multicast address
     *
     * @since 1.4
     */
    @Override
    public boolean isMCSiteLocal() {
        return holder6.isMCSiteLocal();
    }

    /**
     * Utility routine to check if the multicast address has organization scope.
     *
     * <p>
     * 用于检查多播地址是否具有组织范围的实用程序。
     * 
     * 
     * @return a {@code boolean} indicating if the address has is a multicast
     *         address of organization-local scope, false if it is not of
     *         organization-local scope or it is not a multicast address
     *
     * @since 1.4
     */
    @Override
    public boolean isMCOrgLocal() {
        return holder6.isMCOrgLocal();
    }
    /**
     * Returns the raw IP address of this {@code InetAddress} object. The result
     * is in network byte order: the highest order byte of the address is in
     * {@code getAddress()[0]}.
     *
     * <p>
     *  返回此{@code InetAddress}对象的原始IP地址。结果是以网络字节顺序：地址的最高位字节在{@code getAddress()[0]}中。
     * 
     * 
     * @return  the raw IP address of this object.
     */
    @Override
    public byte[] getAddress() {
        return holder6.ipaddress.clone();
    }

    /**
     * Returns the numeric scopeId, if this instance is associated with
     * an interface. If no scoped_id is set, the returned value is zero.
     *
     * <p>
     *  如果此实例与接口相关联,则返回数字scopeId。如果未设置scoped_id,则返回的值为零。
     * 
     * 
     * @return the scopeId, or zero if not set.
     *
     * @since 1.5
     */
     public int getScopeId() {
        return holder6.scope_id;
     }

    /**
     * Returns the scoped interface, if this instance was created with
     * with a scoped interface.
     *
     * <p>
     *  返回作用域接口(如果此实例是使用作用域接口创建的)。
     * 
     * 
     * @return the scoped interface, or null if not set.
     * @since 1.5
     */
     public NetworkInterface getScopedInterface() {
        return holder6.scope_ifname;
     }

    /**
     * Returns the IP address string in textual presentation. If the instance
     * was created specifying a scope identifier then the scope id is appended
     * to the IP address preceded by a "%" (per-cent) character. This can be
     * either a numeric value or a string, depending on which was used to create
     * the instance.
     *
     * <p>
     *  返回文本演示中的IP地址字符串。如果创建的实例指定了作用域标识符,那么作用域ID将附加到以"％"(百分之一)字符开头的IP地址。这可以是数字值或字符串,具体取决于用于创建实例的值。
     * 
     * 
     * @return  the raw IP address in a string format.
     */
    @Override
    public String getHostAddress() {
        return holder6.getHostAddress();
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
    @Override
    public int hashCode() {
        return holder6.hashCode();
    }

    /**
     * Compares this object against the specified object. The result is {@code
     * true} if and only if the argument is not {@code null} and it represents
     * the same IP address as this object.
     *
     * <p> Two instances of {@code InetAddress} represent the same IP address
     * if the length of the byte arrays returned by {@code getAddress} is the
     * same for both, and each of the array components is the same for the byte
     * arrays.
     *
     * <p>
     *  将此对象与指定的对象进行比较。结果是{@code true}当且仅当参数不是{@code null},它表示与此对象相同的IP地址。
     * 
     *  <p> {@code InetAddress}的两个实例表示相同的IP地址,如果{@code getAddress}返回的字节数组的长度相同,并且每个数组组件对于字节数组都是相同的。
     * 
     * 
     * @param   obj   the object to compare against.
     *
     * @return  {@code true} if the objects are the same; {@code false} otherwise.
     *
     * @see     java.net.InetAddress#getAddress()
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Inet6Address))
            return false;

        Inet6Address inetAddr = (Inet6Address)obj;

        return holder6.equals(inetAddr.holder6);
    }

    /**
     * Utility routine to check if the InetAddress is an
     * IPv4 compatible IPv6 address.
     *
     * <p>
     *  用于检查InetAddress是否为IPv4兼容的IPv6地址的实用程序。
     * 
     * 
     * @return a {@code boolean} indicating if the InetAddress is an IPv4
     *         compatible IPv6 address; or false if address is IPv4 address.
     *
     * @since 1.4
     */
    public boolean isIPv4CompatibleAddress() {
        return holder6.isIPv4CompatibleAddress();
    }

    // Utilities
    private final static int INT16SZ = 2;

    /*
     * Convert IPv6 binary address into presentation (printable) format.
     *
     * <p>
     *  将IPv6二进制地址转换为表示(可打印)格式。
     * 
     * 
     * @param src a byte array representing the IPv6 numeric address
     * @return a String representing an IPv6 address in
     *         textual representation format
     * @since 1.4
     */
    static String numericToTextFormat(byte[] src) {
        StringBuilder sb = new StringBuilder(39);
        for (int i = 0; i < (INADDRSZ / INT16SZ); i++) {
            sb.append(Integer.toHexString(((src[i<<1]<<8) & 0xff00)
                                          | (src[(i<<1)+1] & 0xff)));
            if (i < (INADDRSZ / INT16SZ) -1 ) {
               sb.append(":");
            }
        }
        return sb.toString();
    }

    /**
     * Perform class load-time initializations.
     * <p>
     *  执行类装入时初始化。
     */
    private static native void init();
}
