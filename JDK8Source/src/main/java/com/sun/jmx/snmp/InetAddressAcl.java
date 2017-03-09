/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2012, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.jmx.snmp;

// java import
//
import java.net.InetAddress;
import java.util.Enumeration;

/**
 * Defines the IP address based ACL used by the SNMP protocol adaptor.
 * <p>
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  定义SNMP协议适配器使用的基于IP地址的ACL。
 * <p>
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 * 
 * @since 1.5
 */

public interface InetAddressAcl {

    /**
     * Returns the name of the ACL.
     *
     * <p>
     *  返回ACL的名称。
     * 
     * 
     * @return The name of the ACL.
     */
    public String getName();

    /**
     * Checks whether or not the specified host has <CODE>READ</CODE> access.
     *
     * <p>
     *  检查指定的主机是否具有<CODE> READ </CODE>访问权限。
     * 
     * 
     * @param address The host address to check.
     *
     * @return <CODE>true</CODE> if the host has read permission, <CODE>false</CODE> otherwise.
     */
    public boolean checkReadPermission(InetAddress address);

    /**
     * Checks whether or not the specified host and community have <CODE>READ</CODE> access.
     *
     * <p>
     *  检查指定的主机和群组是否具有<CODE> READ </CODE>访问权限。
     * 
     * 
     * @param address The host address to check.
     * @param community The community associated with the host.
     *
     * @return <CODE>true</CODE> if the pair (host, community) has read permission, <CODE>false</CODE> otherwise.
     */
    public boolean checkReadPermission(InetAddress address, String community);

    /**
     * Checks whether or not a community string is defined.
     *
     * <p>
     *  检查是否定义了社区字符串。
     * 
     * 
     * @param community The community to check.
     *
     * @return <CODE>true</CODE> if the community is known, <CODE>false</CODE> otherwise.
     */
    public boolean checkCommunity(String community);

    /**
     * Checks whether or not the specified host has <CODE>WRITE</CODE> access.
     *
     * <p>
     *  检查指定的主机是否具有<CODE> WRITE </CODE>访问权限。
     * 
     * 
     * @param address The host address to check.
     *
     * @return <CODE>true</CODE> if the host has write permission, <CODE>false</CODE> otherwise.
     */
    public boolean checkWritePermission(InetAddress address);

    /**
     * Checks whether or not the specified host and community have <CODE>WRITE</CODE> access.
     *
     * <p>
     *  检查指定的主机和社区是否具有<CODE> WRITE </CODE>访问权限。
     * 
     * 
     * @param address The host address to check.
     * @param community The community associated with the host.
     *
     * @return <CODE>true</CODE> if the pair (host, community) has write permission, <CODE>false</CODE> otherwise.
     */
    public boolean checkWritePermission(InetAddress address, String community);

    /**
     * Returns an enumeration of trap destinations.
     *
     * <p>
     *  返回陷阱目标的枚举。
     * 
     * 
     * @return An enumeration of the trap destinations (enumeration of <CODE>InetAddress</CODE>).
     */
    public Enumeration<InetAddress> getTrapDestinations();

    /**
     * Returns an enumeration of trap communities for a given host.
     *
     * <p>
     *  返回给定主机的陷阱社区的枚举。
     * 
     * 
     * @param address The address of the host.
     *
     * @return An enumeration of trap communities for a given host (enumeration of <CODE>String</CODE>).
     */
    public Enumeration<String> getTrapCommunities(InetAddress address);

    /**
     * Returns an enumeration of inform destinations.
     *
     * <p>
     *  返回通知目的地的枚举。
     * 
     * 
     * @return An enumeration of the inform destinations (enumeration of <CODE>InetAddress</CODE>).
     */
    public Enumeration<InetAddress> getInformDestinations();

    /**
     * Returns an enumeration of inform communities for a given host.
     *
     * <p>
     *  返回通知指定主机的社区的枚举。
     * 
     * @param address The address of the host.
     *
     * @return An enumeration of inform communities for a given host (enumeration of <CODE>String</CODE>).
     */
    public Enumeration<String> getInformCommunities(InetAddress address);
}
