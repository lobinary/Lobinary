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

import java.io.ObjectStreamException;

/**
 * This class represents an Internet Protocol version 4 (IPv4) address.
 * Defined by <a href="http://www.ietf.org/rfc/rfc790.txt">
 * <i>RFC&nbsp;790: Assigned Numbers</i></a>,
 * <a href="http://www.ietf.org/rfc/rfc1918.txt">
 * <i>RFC&nbsp;1918: Address Allocation for Private Internets</i></a>,
 * and <a href="http://www.ietf.org/rfc/rfc2365.txt"><i>RFC&nbsp;2365:
 * Administratively Scoped IP Multicast</i></a>
 *
 * <h3> <A NAME="format">Textual representation of IP addresses</a> </h3>
 *
 * Textual representation of IPv4 address used as input to methods
 * takes one of the following forms:
 *
 * <blockquote><table cellpadding=0 cellspacing=0 summary="layout">
 * <tr><td>{@code d.d.d.d}</td></tr>
 * <tr><td>{@code d.d.d}</td></tr>
 * <tr><td>{@code d.d}</td></tr>
 * <tr><td>{@code d}</td></tr>
 * </table></blockquote>
 *
 * <p> When four parts are specified, each is interpreted as a byte of
 * data and assigned, from left to right, to the four bytes of an IPv4
 * address.

 * <p> When a three part address is specified, the last part is
 * interpreted as a 16-bit quantity and placed in the right most two
 * bytes of the network address. This makes the three part address
 * format convenient for specifying Class B net- work addresses as
 * 128.net.host.
 *
 * <p> When a two part address is supplied, the last part is
 * interpreted as a 24-bit quantity and placed in the right most three
 * bytes of the network address. This makes the two part address
 * format convenient for specifying Class A network addresses as
 * net.host.
 *
 * <p> When only one part is given, the value is stored directly in
 * the network address without any byte rearrangement.
 *
 * <p> For methods that return a textual representation as output
 * value, the first form, i.e. a dotted-quad string, is used.
 *
 * <h4> The Scope of a Multicast Address </h4>
 *
 * Historically the IPv4 TTL field in the IP header has doubled as a
 * multicast scope field: a TTL of 0 means node-local, 1 means
 * link-local, up through 32 means site-local, up through 64 means
 * region-local, up through 128 means continent-local, and up through
 * 255 are global. However, the administrative scoping is preferred.
 * Please refer to <a href="http://www.ietf.org/rfc/rfc2365.txt">
 * <i>RFC&nbsp;2365: Administratively Scoped IP Multicast</i></a>
 * <p>
 *  此类表示Internet协议版本4(IPv4)地址。
 * 由<a href="http://www.ietf.org/rfc/rfc790.txt"> <i> RFC 790：已分配号码</i> </a>定义,。
 * <a href="http://www.ietf.org/rfc/rfc1918.txt">
 *  <i> RFC&nbsp; 1918：私人网际网路的地址分配</i> </a>和<a href="http://www.ietf.org/rfc/rfc2365.txt"> <i> RFC&nbsp;
 *  2365 ：管理范围的IP组播</i> </a>。
 * 
 *  <h3> <A NAME="format"> IP地址的文字表示</a> </h3>
 * 
 *  用作方法输入的IPv4地址的文本表示采用以下形式之一：
 * 
 *  <blockquote> <table cellpadding = 0 cellspacing = 0 summary ="layout"> <tr> <td> {@ code dddd} </td>
 *  </tr> <tr> <td> {@ code ddd} </td > </tr> <tr> <td> {@ code dd} </td> </tr> <tr> <td> {@ code d} </td>
 *  </tr> </table> </blockquote >。
 * 
 *  <p>当指定四个部分时,每个部分都被解释为一个字节的数据,并从左到右分配给IPv4地址的四个字节。
 * 
 *  <p>指定三部分地址时,最后一部分被解释为16位数量,并放在网络地址的最右两个字节中。这使得三部分地址格式便于将B类网络地址指定为128.net.host。
 * 
 * <p>当提供两部分地址时,最后一部分被解释为24位数量,并放在网络地址的最右三个字节中。这使得两部分地址格式方便地将A类网络地址指定为net.host。
 * 
 *  <p>当只给出一个部分时,该值直接存储在网络地址中,不进行任何字节重新排列。
 * 
 *  <p>对于返回文本表示作为输出值的方法,使用第一种形式,即点分四元字符串。
 * 
 *  <h4>多播地址的范围</h4>
 * 
 *  历史上,IP报头中的IPv4 TTL字段已加倍为多播范围字段：TTL为0表示节点本地,1表示链路本地,上至32表示站点本地,上至64表示区域本地, 128表示大陆本地,并且上至255是全局的。
 * 但是,首选管理范围。请参阅<a href="http://www.ietf.org/rfc/rfc2365.txt"> <i> RFC&nbsp; 2365：管理范围的IP组播</i> </a>。
 * 
 * 
 * @since 1.4
 */

public final
class Inet4Address extends InetAddress {
    final static int INADDRSZ = 4;

    /** use serialVersionUID from InetAddress, but Inet4Address instance
     *  is always replaced by an InetAddress instance before being
     * <p>
     *  始终由InetAddress实例替代
     * 
     * 
     *  serialized */
    private static final long serialVersionUID = 3286316764910316507L;

    /*
     * Perform initializations.
     * <p>
     *  执行初始化。
     * 
     */
    static {
        init();
    }

    Inet4Address() {
        super();
        holder().hostName = null;
        holder().address = 0;
        holder().family = IPv4;
    }

    Inet4Address(String hostName, byte addr[]) {
        holder().hostName = hostName;
        holder().family = IPv4;
        if (addr != null) {
            if (addr.length == INADDRSZ) {
                int address  = addr[3] & 0xFF;
                address |= ((addr[2] << 8) & 0xFF00);
                address |= ((addr[1] << 16) & 0xFF0000);
                address |= ((addr[0] << 24) & 0xFF000000);
                holder().address = address;
            }
        }
    }
    Inet4Address(String hostName, int address) {
        holder().hostName = hostName;
        holder().family = IPv4;
        holder().address = address;
    }

    /**
     * Replaces the object to be serialized with an InetAddress object.
     *
     * <p>
     *  用InetAddress对象替换要序列化的对象。
     * 
     * 
     * @return the alternate object to be serialized.
     *
     * @throws ObjectStreamException if a new object replacing this
     * object could not be created
     */
    private Object writeReplace() throws ObjectStreamException {
        // will replace the to be serialized 'this' object
        InetAddress inet = new InetAddress();
        inet.holder().hostName = holder().getHostName();
        inet.holder().address = holder().getAddress();

        /**
         * Prior to 1.4 an InetAddress was created with a family
         * based on the platform AF_INET value (usually 2).
         * For compatibility reasons we must therefore write the
         * the InetAddress with this family.
         * <p>
         *  在1.4之前,InetAddress是基于平台AF_INET值(通常为2)创建的。出于兼容性原因,我们必须将InetAddress写入此系列。
         * 
         */
        inet.holder().family = 2;

        return inet;
    }

    /**
     * Utility routine to check if the InetAddress is an
     * IP multicast address. IP multicast address is a Class D
     * address i.e first four bits of the address are 1110.
     * <p>
     *  用于检查InetAddress是否是IP多播地址的实用程序。 IP多播地址是D类地址,即地址的前四位是1110。
     * 
     * 
     * @return a {@code boolean} indicating if the InetAddress is
     * an IP multicast address
     * @since   JDK1.1
     */
    public boolean isMulticastAddress() {
        return ((holder().getAddress() & 0xf0000000) == 0xe0000000);
    }

    /**
     * Utility routine to check if the InetAddress in a wildcard address.
     * <p>
     * 用于检查通配符地址中的InetAddress的实用程序。
     * 
     * 
     * @return a {@code boolean} indicating if the Inetaddress is
     *         a wildcard address.
     * @since 1.4
     */
    public boolean isAnyLocalAddress() {
        return holder().getAddress() == 0;
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
        /* 127.x.x.x */
        byte[] byteAddr = getAddress();
        return byteAddr[0] == 127;
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
        // link-local unicast in IPv4 (169.254.0.0/16)
        // defined in "Documenting Special Use IPv4 Address Blocks
        // that have been Registered with IANA" by Bill Manning
        // draft-manning-dsua-06.txt
        int address = holder().getAddress();
        return (((address >>> 24) & 0xFF) == 169)
            && (((address >>> 16) & 0xFF) == 254);
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
        // refer to RFC 1918
        // 10/8 prefix
        // 172.16/12 prefix
        // 192.168/16 prefix
        int address = holder().getAddress();
        return (((address >>> 24) & 0xFF) == 10)
            || ((((address >>> 24) & 0xFF) == 172)
                && (((address >>> 16) & 0xF0) == 16))
            || ((((address >>> 24) & 0xFF) == 192)
                && (((address >>> 16) & 0xFF) == 168));
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
        // 224.0.1.0 to 238.255.255.255
        byte[] byteAddr = getAddress();
        return ((byteAddr[0] & 0xff) >= 224 && (byteAddr[0] & 0xff) <= 238 ) &&
            !((byteAddr[0] & 0xff) == 224 && byteAddr[1] == 0 &&
              byteAddr[2] == 0);
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
        // unless ttl == 0
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
        // 224.0.0/24 prefix and ttl == 1
        int address = holder().getAddress();
        return (((address >>> 24) & 0xFF) == 224)
            && (((address >>> 16) & 0xFF) == 0)
            && (((address >>> 8) & 0xFF) == 0);
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
        // 239.255/16 prefix or ttl < 32
        int address = holder().getAddress();
        return (((address >>> 24) & 0xFF) == 239)
            && (((address >>> 16) & 0xFF) == 255);
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
        // 239.192 - 239.195
        int address = holder().getAddress();
        return (((address >>> 24) & 0xFF) == 239)
            && (((address >>> 16) & 0xFF) >= 192)
            && (((address >>> 16) & 0xFF) <= 195);
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
        int address = holder().getAddress();
        byte[] addr = new byte[INADDRSZ];

        addr[0] = (byte) ((address >>> 24) & 0xFF);
        addr[1] = (byte) ((address >>> 16) & 0xFF);
        addr[2] = (byte) ((address >>> 8) & 0xFF);
        addr[3] = (byte) (address & 0xFF);
        return addr;
    }

    /**
     * Returns the IP address string in textual presentation form.
     *
     * <p>
     *  以文本表示形式返回IP地址字符串。
     * 
     * 
     * @return  the raw IP address in a string format.
     * @since   JDK1.0.2
     */
    public String getHostAddress() {
        return numericToTextFormat(getAddress());
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
        return holder().getAddress();
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
     *  将此对象与指定的对象进行比较。结果是{@code true}当且仅当参数不是{@code null},它表示与此对象相同的IP地址。
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
        return (obj != null) && (obj instanceof Inet4Address) &&
            (((InetAddress)obj).holder().getAddress() == holder().getAddress());
    }

    // Utilities
    /*
     * Converts IPv4 binary address into a string suitable for presentation.
     *
     * <p>
     *  将IPv4二进制地址转换为适合呈现的字符串。
     * 
     * 
     * @param src a byte array representing an IPv4 numeric address
     * @return a String representing the IPv4 address in
     *         textual representation format
     * @since 1.4
     */

    static String numericToTextFormat(byte[] src)
    {
        return (src[0] & 0xff) + "." + (src[1] & 0xff) + "." + (src[2] & 0xff) + "." + (src[3] & 0xff);
    }

    /**
     * Perform class load-time initializations.
     * <p>
     *  执行类装入时初始化。
     */
    private static native void init();
}
