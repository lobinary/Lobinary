/***** Lobxxx Translate Finished ******/
/*
 *
 * Copyright (c) 2007, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
// Copyright (c) 1995-96 by Cisco Systems, Inc.

package com.sun.jmx.snmp;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * Is the fully decoded representation of an SNMP packet.
 * <P>
 * You will not usually need to use this class, except if you
 * decide to implement your own
 * {@link com.sun.jmx.snmp.SnmpPduFactory SnmpPduFactory} object.
 * <P>
 * Classes are derived from <CODE>SnmpPduPacket</CODE> to
 * represent the different forms of SNMP packets
 * ({@link com.sun.jmx.snmp.SnmpPduRequest SnmpPduRequest},
 * {@link com.sun.jmx.snmp.SnmpPduTrap SnmpPduTrap},
 * {@link com.sun.jmx.snmp.SnmpPduBulk SnmpPduBulk}).
 * <BR>The <CODE>SnmpPduPacket</CODE> class defines the attributes
 * common to every form of SNMP packets.
 *
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  是SNMP数据包的完全解码表示。
 * <P>
 *  您通常不需要使用此类,除非您决定实现自己的{@link com.sun.jmx.snmp.SnmpPduFactory SnmpPduFactory}对象。
 * <P>
 *  类派生自<CODE> SnmpPduPacket </CODE>,表示不同形式的SNMP包({@link com.sun.jmx.snmp.SnmpPduRequest SnmpPduRequest}
 * ,{@link com.sun.jmx.snmp.SnmpPduTrap SnmpPduTrap} ,{@link com.sun.jmx.snmp.SnmpPduBulk SnmpPduBulk})。
 *  <BR> <CODE> SnmpPduPacket </CODE>类定义了每种形式的SNMP数据包通用的属性。
 * 
 * @see SnmpMessage
 * @see SnmpPduFactory
 *
 */

public abstract class SnmpPduPacket extends SnmpPdu implements Serializable {
    /**
     * The pdu community string.
     * <p>
     * 
     *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
     * 
     */
    public byte[] community ;
}
