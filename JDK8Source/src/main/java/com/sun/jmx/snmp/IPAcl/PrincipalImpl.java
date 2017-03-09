/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2007, Oracle and/or its affiliates. All rights reserved.
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


package com.sun.jmx.snmp.IPAcl;



import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.Serializable;


/**
 * Principal represents a host.
 *
 * <p>
 *  Principal表示主机。
 * 
 */

class PrincipalImpl implements java.security.Principal, Serializable {
    private static final long serialVersionUID = -7910027842878976761L;

    private InetAddress[] add = null;

    /**
     * Constructs a principal with the local host.
     * <p>
     *  使用本地主机构造主体。
     * 
     */
    public PrincipalImpl () throws UnknownHostException {
        add = new InetAddress[1];
        add[0] = java.net.InetAddress.getLocalHost();
    }

    /**
     * Construct a principal using the specified host.
     * <P>
     * The host can be either:
     * <UL>
     * <LI> a host name
     * <LI> an IP address
     * </UL>
     *
     * <p>
     *  使用指定的主机构造主体。
     * <P>
     *  主机可以是：
     * <UL>
     *  <LI>主机名<LI>是IP地址
     * </UL>
     * 
     * 
     * @param hostName the host used to make the principal.
     */
    public PrincipalImpl(String hostName) throws UnknownHostException {
        if ((hostName.equals("localhost")) || (hostName.equals("127.0.0.1"))) {
            add = new InetAddress[1];
            add[0] = java.net.InetAddress.getByName(hostName);
        }
        else
            add = java.net.InetAddress.getAllByName( hostName );
    }

    /**
     * Constructs a principal using an Internet Protocol (IP) address.
     *
     * <p>
     *  使用Internet协议(IP)地址构造主体。
     * 
     * 
     * @param address the Internet Protocol (IP) address.
     */
    public PrincipalImpl(InetAddress address) {
        add = new InetAddress[1];
        add[0] = address;
    }

    /**
     * Returns the name of this principal.
     *
     * <p>
     *  返回此主体的名称。
     * 
     * 
     * @return the name of this principal.
     */
    public String getName() {
        return add[0].toString();
    }

    /**
     * Compares this principal to the specified object. Returns true if the
     * object passed in matches the principal
     * represented by the implementation of this interface.
     *
     * <p>
     *  将此主体与指定的对象进行比较。如果传入的对象与由此接口的实现表示的主体匹配,则返回true。
     * 
     * 
     * @param a the principal to compare with.
     * @return true if the principal passed in is the same as that encapsulated by this principal, false otherwise.
     */
    public boolean equals(Object a) {
        if (a instanceof PrincipalImpl){
            for(int i = 0; i < add.length; i++) {
                if(add[i].equals (((PrincipalImpl) a).getAddress()))
                    return true;
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * Returns a hashcode for this principal.
     *
     * <p>
     *  返回此主体的哈希码。
     * 
     * 
     * @return a hashcode for this principal.
     */
    public int hashCode(){
        return add[0].hashCode();
    }

    /**
     * Returns a string representation of this principal. In case of multiple address, the first one is returned.
     *
     * <p>
     *  返回此主体的字符串表示形式。在多地址的情况下,返回第一个地址。
     * 
     * 
     * @return a string representation of this principal.
     */
    public String toString() {
        return ("PrincipalImpl :"+add[0].toString());
    }

    /**
     * Returns the Internet Protocol (IP) address for this principal. In case of multiple address, the first one is returned.
     *
     * <p>
     *  返回此主体的Internet协议(IP)地址。在多地址的情况下,返回第一个地址。
     * 
     * 
     * @return the Internet Protocol (IP) address for this principal.
     */
    public InetAddress getAddress(){
        return add[0];
    }

    /**
     * Returns the Internet Protocol (IP) address for this principal. In case of multiple address, the first one is returned.
     *
     * <p>
     *  返回此主体的Internet协议(IP)地址。在多地址的情况下,返回第一个地址。
     * 
     * @return the array of Internet Protocol (IP) addresses for this principal.
     */
    public InetAddress[] getAddresses(){
        return add;
    }
}
