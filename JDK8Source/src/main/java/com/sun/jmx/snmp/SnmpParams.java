/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2003, Oracle and/or its affiliates. All rights reserved.
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

import com.sun.jmx.snmp.SnmpDefinitions;

/**
 * This class is the base class of all parameters that are used when making SNMP requests to an <CODE>SnmpPeer</CODE>.
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  此类是在向<CODE> SnmpPeer </CODE>发出SNMP请求时使用的所有参数的基类。 <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。
 * </b> </p>。
 * 
 * 
 * @since 1.5
 */
public abstract class SnmpParams implements SnmpDefinitions {
    private int protocolVersion = snmpVersionOne;
    SnmpParams(int version) {
        protocolVersion = version;
    }

    SnmpParams() {}
    /**
     * Checks whether parameters are in place for an SNMP <CODE>set</CODE> operation.
     * <p>
     *  检查是否已为SNMP <CODE>设置</CODE>操作设置了参数。
     * 
     * 
     * @return <CODE>true</CODE> if parameters are in place, <CODE>false</CODE> otherwise.
     */
    public abstract boolean allowSnmpSets();
    /**
     * Returns the version of the protocol to use.
     * The returned value is:
     * <UL>
     * <LI>{@link com.sun.jmx.snmp.SnmpDefinitions#snmpVersionOne snmpVersionOne} if the protocol is SNMPv1
     * <LI>{@link com.sun.jmx.snmp.SnmpDefinitions#snmpVersionTwo snmpVersionTwo} if the protocol is SNMPv2
     * <LI>{@link com.sun.jmx.snmp.SnmpDefinitions#snmpVersionThree snmpVersionThree} if the protocol is SNMPv3
     * </UL>
     * <p>
     *  返回要使用的协议的版本。返回值为：
     * <UL>
     *  <LI> {@ link com.sun.jmx.snmp.SnmpDefinitions#snmpVersionOne snmpVersionOne}如果协议是SNMPv1 <LI> {@ link com.sun.jmx.snmp.SnmpDefinitions#snmpVersionTwo snmpVersionTwo}
     * ,如果协议是SNMPv2 <LI > {@ link com.sun.jmx.snmp.SnmpDefinitions#snmpVersionThree snmpVersionThree}如果协议是SN
     * MPv3。
     * </UL>
     * 
     * @return The version of the protocol to use.
     */
    public int getProtocolVersion() {
        return protocolVersion ;
    }

    /**
     * Sets the version of the protocol to be used.
     * The version should be identified using the definitions
     * contained in
     * {@link com.sun.jmx.snmp.SnmpDefinitions SnmpDefinitions}.
     * <BR>For instance if you wish to use SNMPv2, you can call the method as follows:
     * <BLOCKQUOTE><PRE>
     * setProtocolVersion(SnmpDefinitions.snmpVersionTwo);
     * </PRE></BLOCKQUOTE>
     * <p>
     * 
     * @param protocolversion The version of the protocol to be used.
     */

    public void setProtocolVersion(int protocolversion) {
        this.protocolVersion = protocolversion ;
    }
}
