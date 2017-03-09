/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * This class represents a Network Interface address. In short it's an
 * IP address, a subnet mask and a broadcast address when the address is
 * an IPv4 one. An IP address and a network prefix length in the case
 * of IPv6 address.
 *
 * <p>
 *  此类表示网络接口地址。简而言之,当地址是IPv4时,它是IP地址,子网掩码和广播地址。在IPv6地址的情况下的IP地址和网络前缀长度。
 * 
 * 
 * @see java.net.NetworkInterface
 * @since 1.6
 */
public class InterfaceAddress {
    private InetAddress address = null;
    private Inet4Address broadcast = null;
    private short        maskLength = 0;

    /*
     * Package private constructor. Can't be built directly, instances are
     * obtained through the NetworkInterface class.
     * <p>
     *  包私有构造函数。无法直接构建,实例通过NetworkInterface类获取。
     * 
     */
    InterfaceAddress() {
    }

    /**
     * Returns an {@code InetAddress} for this address.
     *
     * <p>
     *  返回此地址的{@code InetAddress}。
     * 
     * 
     * @return the {@code InetAddress} for this address.
     */
    public InetAddress getAddress() {
        return address;
    }

    /**
     * Returns an {@code InetAddress} for the broadcast address
     * for this InterfaceAddress.
     * <p>
     * Only IPv4 networks have broadcast address therefore, in the case
     * of an IPv6 network, {@code null} will be returned.
     *
     * <p>
     *  返回此接口地址的广播地址的{@code InetAddress}。
     * <p>
     *  因此,只有IPv4网络具有广播地址,在IPv6网络的情况下,将返回{@code null}。
     * 
     * 
     * @return the {@code InetAddress} representing the broadcast
     *         address or {@code null} if there is no broadcast address.
     */
    public InetAddress getBroadcast() {
        return broadcast;
    }

    /**
     * Returns the network prefix length for this address. This is also known
     * as the subnet mask in the context of IPv4 addresses.
     * Typical IPv4 values would be 8 (255.0.0.0), 16 (255.255.0.0)
     * or 24 (255.255.255.0). <p>
     * Typical IPv6 values would be 128 (::1/128) or 10 (fe80::203:baff:fe27:1243/10)
     *
     * <p>
     *  返回此地址的网络前缀长度。这也称为IPv4地址上下文中的子网掩码。典型的IPv4值为8(255.0.0.0),16(255.255.0.0)或24(255.255.255.0)。
     *  <p>典型的IPv6值为128(:: 1/128)或10(fe80 :: 203：baff：fe27：1243/10)。
     * 
     * 
     * @return a {@code short} representing the prefix length for the
     *         subnet of that address.
     */
     public short getNetworkPrefixLength() {
        return maskLength;
    }

    /**
     * Compares this object against the specified object.
     * The result is {@code true} if and only if the argument is
     * not {@code null} and it represents the same interface address as
     * this object.
     * <p>
     * Two instances of {@code InterfaceAddress} represent the same
     * address if the InetAddress, the prefix length and the broadcast are
     * the same for both.
     *
     * <p>
     *  将此对象与指定的对象进行比较。结果是{@code true}当且仅当参数不是{@code null},并且它表示与此对象相同的接口地址。
     * <p>
     *  如果InetAddress,前缀长度和广播两者相同,则{@code InterfaceAddress}的两个实例表示相同的地址。
     * 
     * 
     * @param   obj   the object to compare against.
     * @return  {@code true} if the objects are the same;
     *          {@code false} otherwise.
     * @see     java.net.InterfaceAddress#hashCode()
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof InterfaceAddress)) {
            return false;
        }
        InterfaceAddress cmp = (InterfaceAddress) obj;
        if ( !(address == null ? cmp.address == null : address.equals(cmp.address)) )
            return false;
        if ( !(broadcast  == null ? cmp.broadcast == null : broadcast.equals(cmp.broadcast)) )
            return false;
        if (maskLength != cmp.maskLength)
            return false;
        return true;
    }

    /**
     * Returns a hashcode for this Interface address.
     *
     * <p>
     *  返回此接口地址的哈希码。
     * 
     * 
     * @return  a hash code value for this Interface address.
     */
    public int hashCode() {
        return address.hashCode() + ((broadcast != null) ? broadcast.hashCode() : 0) + maskLength;
    }

    /**
     * Converts this Interface address to a {@code String}. The
     * string returned is of the form: InetAddress / prefix length [ broadcast address ].
     *
     * <p>
     * 将此接口地址转换为{@code String}。返回的字符串形式为：InetAddress / prefix length [广播地址]。
     * 
     * @return  a string representation of this Interface address.
     */
    public String toString() {
        return address + "/" + maskLength + " [" + broadcast + "]";
    }

}
