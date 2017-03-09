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


import java.io.Serializable;
import java.net.InetAddress;
/**
 * Is the fully decoded representation of an SNMP packet.
 * <P>
 * Classes are derived from <CODE>SnmpPdu</CODE> to
 * represent the different forms of SNMP packets
 * ({@link com.sun.jmx.snmp.SnmpPduPacket SnmpPduPacket},
 * {@link com.sun.jmx.snmp.SnmpScopedPduPacket SnmpScopedPduPacket})
 * <BR>The <CODE>SnmpPdu</CODE> class defines the attributes
 * common to every form of SNMP packets.
 *
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  是SNMP数据包的完全解码表示。
 * <P>
 *  类派生自<CODE> SnmpPdu </CODE>,表示不同形式的SNMP包({@link com.sun.jmx.snmp.SnmpPduPacket SnmpPduPacket},{@link com.sun.jmx.snmp.SnmpScopedPduPacket SnmpScopedPduPacket}
 *  )<BR> <CODE> SnmpPdu </CODE>类定义了每种形式的SNMP数据包的公共属性。
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 * 
 * @see SnmpMessage
 * @see SnmpPduFactory
 *
 * @since 1.5
 */
public abstract class SnmpPdu implements SnmpDefinitions, Serializable {

    /**
     * PDU type. Types are defined in
     * {@link com.sun.jmx.snmp.SnmpDefinitions SnmpDefinitions}.
     * <p>
     *  PDU类型。类型在{@link com.sun.jmx.snmp.SnmpDefinitions SnmpDefinitions}中定义。
     * 
     * 
     * @serial
     */
    public int type=0 ;

    /**
     * Protocol version. Versions are defined in
     * {@link com.sun.jmx.snmp.SnmpDefinitions SnmpDefinitions}.
     * <p>
     *  协议版本。版本在{@link com.sun.jmx.snmp.SnmpDefinitions SnmpDefinitions}中定义。
     * 
     * 
     * @serial
     */
    public int version=0 ;

    /**
     * List of variables.
     * <p>
     *  变量列表。
     * 
     * 
     * @serial
     */
    public SnmpVarBind[] varBindList ;


    /**
     * Request identifier.
     * Note that this field is not used by <CODE>SnmpPduTrap</CODE>.
     * <p>
     *  请求标识符。请注意,<CODE> SnmpPduTrap </CODE>不使用此字段。
     * 
     * 
     * @serial
     */
    public int requestId=0 ;

    /**
     * Source or destination address.
     * <P>For an incoming PDU it's the source.
     * <BR>For an outgoing PDU it's the destination.
     * <p>
     *  源或目标地址。 <P>对于传入的PDU,它是源。 <BR>对于传出的PDU,它是目的地。
     * 
     * 
     * @serial
     */
    public InetAddress address ;

    /**
     * Source or destination port.
     * <P>For an incoming PDU it's the source.
     * <BR>For an outgoing PDU it's the destination.
     * <p>
     *  源或目标端口。 <P>对于传入的PDU,它是源。 <BR>对于传出的PDU,它是目的地。
     * 
     * 
     * @serial
     */
    public int port=0 ;

    /**
     * Returns the <CODE>String</CODE> representation of a PDU type.
     * For instance, if the PDU type is <CODE>SnmpDefinitions.pduGetRequestPdu</CODE>,
     * the method will return "SnmpGet".
     * <p>
     *  返回PDU类型的<CODE> String </CODE>表示形式。
     * 例如,如果PDU类型为<CODE> SnmpDefinitions.pduGetRequestPdu </CODE>,则该方法将返回"SnmpGet"。
     * 
     * @param cmd The integer representation of the PDU type.
     * @return The <CODE>String</CODE> representation of the PDU type.
     */
    public static String pduTypeToString(int cmd) {
        switch (cmd) {
        case pduGetRequestPdu :
            return "SnmpGet" ;
        case pduGetNextRequestPdu :
            return "SnmpGetNext" ;
        case pduWalkRequest :
            return "SnmpWalk(*)" ;
        case pduSetRequestPdu :
            return "SnmpSet" ;
        case pduGetResponsePdu :
            return "SnmpResponse" ;
        case pduV1TrapPdu :
            return "SnmpV1Trap" ;
        case pduV2TrapPdu :
            return "SnmpV2Trap" ;
        case pduGetBulkRequestPdu :
            return "SnmpGetBulk" ;
        case pduInformRequestPdu :
            return "SnmpInform" ;
        }
        return "Unknown Command = " + cmd ;
    }
}
