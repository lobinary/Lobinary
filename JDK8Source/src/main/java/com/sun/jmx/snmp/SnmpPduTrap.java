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


package com.sun.jmx.snmp;



/**
 * Represents an SNMPv1-trap PDU.
 * <P>
 * You will not usually need to use this class, except if you
 * decide to implement your own
 * {@link com.sun.jmx.snmp.SnmpPduFactory SnmpPduFactory} object.
 * <P>
 * The <CODE>SnmpPduTrap</CODE> extends {@link com.sun.jmx.snmp.SnmpPduPacket SnmpPduPacket}
 * and defines attributes specific to an SNMPv1 trap (see RFC1157).
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  表示SNMPv1陷阱PDU。
 * <P>
 *  您通常不需要使用此类,除非您决定实现自己的{@link com.sun.jmx.snmp.SnmpPduFactory SnmpPduFactory}对象。
 * <P>
 *  <CODE> SnmpPduTrap </CODE>扩展{@link com.sun.jmx.snmp.SnmpPduPacket SnmpPduPacket},并定义特定于SNMPv1陷阱的属性(请
 * 参阅RFC1157)。
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 */

public class SnmpPduTrap extends SnmpPduPacket {
    private static final long serialVersionUID = -3670886636491433011L;

    /**
     * Enterprise object identifier.
     * <p>
     *  企业对象标识符。
     * 
     * 
     * @serial
     */
    public SnmpOid        enterprise ;

    /**
     * Agent address. If the agent address source was not an IPv4 one (eg : IPv6), this field is null.
     * <p>
     *  代理地址。如果代理地址源不是IPv4(例如：IPv6),则此字段为空。
     * 
     * 
     * @serial
     */
    public SnmpIpAddress  agentAddr ;

    /**
     * Generic trap number.
     * <BR>
     * The possible values are defined in
     * {@link com.sun.jmx.snmp.SnmpDefinitions#trapColdStart SnmpDefinitions}.
     * <p>
     *  通用陷阱编号。
     * <BR>
     *  可能的值在{@link com.sun.jmx.snmp.SnmpDefinitions#trapColdStart SnmpDefinitions}中定义。
     * 
     * 
     * @serial
     */
    public int            genericTrap ;

    /**
     * Specific trap number.
     * <p>
     *  特定陷阱号。
     * 
     * 
     * @serial
     */
    public int            specificTrap ;

    /**
     * Time-stamp.
     * <p>
     *  时间戳。
     * 
     * 
     * @serial
     */
    public long            timeStamp ;



    /**
     * Builds a new trap PDU.
     * <BR><CODE>type</CODE> and <CODE>version</CODE> fields are initialized with
     * {@link com.sun.jmx.snmp.SnmpDefinitions#pduV1TrapPdu pduV1TrapPdu}
     * and {@link com.sun.jmx.snmp.SnmpDefinitions#snmpVersionOne snmpVersionOne}.
     * <p>
     *  构建新的陷阱PDU。
     *  <BR> <CODE>类型</CODE>和<CODE>版本</CODE>字段使用{@link com.sun.jmx.snmp.SnmpDefinitions#pduV1TrapPdu pduV1TrapPdu}
     * 和{@link com.sun.jmx。
     */
    public SnmpPduTrap() {
        type = pduV1TrapPdu ;
        version = snmpVersionOne ;
    }
}
