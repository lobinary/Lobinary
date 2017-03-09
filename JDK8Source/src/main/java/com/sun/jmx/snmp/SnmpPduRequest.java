/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2007, Oracle and/or its affiliates. All rights reserved.
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
 * Is used to represent <CODE>get</CODE>, <CODE>get-next</CODE>, <CODE>set</CODE>, <CODE>response</CODE> and <CODE>SNMPv2-trap</CODE> PDUs.
 * <P>
 * You will not usually need to use this class, except if you
 * decide to implement your own
 * {@link com.sun.jmx.snmp.SnmpPduFactory SnmpPduFactory} object.
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  用于表示<CODE> get </CODE>,<CODE> get-next </CODE>,<CODE> set </CODE>,<CODE> response </CODE> CODE> PDU。
 * <P>
 *  您通常不需要使用此类,除非您决定实现自己的{@link com.sun.jmx.snmp.SnmpPduFactory SnmpPduFactory}对象。
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 */

public class SnmpPduRequest extends SnmpPduPacket
    implements SnmpPduRequestType {
    private static final long serialVersionUID = 2218754017025258979L;


    /**
     * Error status. Statuses are defined in
     * {@link com.sun.jmx.snmp.SnmpDefinitions SnmpDefinitions}.
     * <p>
     *  错误状态。状态在{@link com.sun.jmx.snmp.SnmpDefinitions SnmpDefinitions}中定义。
     * 
     * 
     * @serial
     */
    public int errorStatus=0 ;


    /**
     * Error index. Remember that SNMP indices start from 1.
     * Thus the corresponding <CODE>SnmpVarBind</CODE> is
     * <CODE>varBindList[errorIndex-1]</CODE>.
     * <p>
     *  错误索引。记住SNMP索引从1开始。因此,相应的<CODE> SnmpVarBind </CODE>是<CODE> varBindList [errorIndex-1] </CODE>。
     * 
     * 
     * @serial
     */
    public int errorIndex=0 ;
    /**
     * Implements <CODE>SnmpPduRequestType</CODE> interface.
     *
     * <p>
     *  实现<CODE> SnmpPduRequestType </CODE>接口。
     * 
     * 
     * @since 1.5
     */
    public void setErrorIndex(int i) {
        errorIndex = i;
    }
    /**
     * Implements <CODE>SnmpPduRequestType</CODE> interface.
     *
     * <p>
     *  实现<CODE> SnmpPduRequestType </CODE>接口。
     * 
     * 
     * @since 1.5
     */
    public void setErrorStatus(int i) {
        errorStatus = i;
    }
    /**
     * Implements <CODE>SnmpPduRequestType</CODE> interface.
     *
     * <p>
     *  实现<CODE> SnmpPduRequestType </CODE>接口。
     * 
     * 
     * @since 1.5
     */
    public int getErrorIndex() { return errorIndex; }
    /**
     * Implements <CODE>SnmpPduRequestType</CODE> interface.
     *
     * <p>
     *  实现<CODE> SnmpPduRequestType </CODE>接口。
     * 
     * 
     * @since 1.5
     */
    public int getErrorStatus() { return errorStatus; }
    /**
     * Implements <CODE>SnmpAckPdu</CODE> interface.
     *
     * <p>
     *  实现<CODE> SnmpAckPdu </CODE>接口。
     * 
     * @since 1.5
     */
    public SnmpPdu getResponsePdu() {
        SnmpPduRequest result = new SnmpPduRequest();
        result.address = address;
        result.port = port;
        result.version = version;
        result.community = community;
        result.type = SnmpDefinitions.pduGetResponsePdu;
        result.requestId = requestId;
        result.errorStatus = SnmpDefinitions.snmpRspNoError;
        result.errorIndex = 0;

        return result;
    }
}
