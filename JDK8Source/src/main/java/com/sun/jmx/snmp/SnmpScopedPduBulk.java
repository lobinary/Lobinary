/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2006, Oracle and/or its affiliates. All rights reserved.
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
 * Represents a <CODE>get-bulk</CODE> PDU as defined in RFC 1448.
 * <P>
 * <P>
 * The <CODE>SnmpSocpedPduBulk</CODE> extends {@link com.sun.jmx.snmp.SnmpScopedPduPacket SnmpScopedPduPacket}
 * and defines attributes specific to the <CODE>get-bulk</CODE> PDU (see RFC 1448).
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  表示RFC 1448中定义的<CODE> get-bulk </CODE> PDU。
 * <P>
 * <P>
 *  <CODE> SnmpSocpedPduBulk </CODE>扩展{@link com.sun.jmx.snmp.SnmpScopedPduPacket SnmpScopedPduPacket}并定
 * 义<CODE> get-bulk </CODE> PDU特有的属性(请参阅RFC 1448)。
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 * 
 * @since 1.5
 */

public class SnmpScopedPduBulk extends SnmpScopedPduPacket
    implements SnmpPduBulkType {
    private static final long serialVersionUID = -1648623646227038885L;

    /**
     * The <CODE>non-repeaters</CODE> value.
     * <p>
     *  <CODE>非中继器</CODE>值。
     * 
     * 
     * @serial
     */
    int            nonRepeaters;


    /**
     * The <CODE>max-repetitions</CODE> value.
     * <p>
     *  <CODE> max-repeatedries </CODE>值。
     * 
     * 
     * @serial
     */
    int            maxRepetitions;

    public SnmpScopedPduBulk() {
        type = pduGetBulkRequestPdu;
        version = snmpVersionThree;
    }

    /**
     * The <CODE>max-repetitions</CODE> setter.
     * <p>
     *  <CODE> max-repetitions </CODE>设置器。
     * 
     * 
     * @param max Maximum repetition.
     */
    public void setMaxRepetitions(int max) {
        maxRepetitions = max;
    }

    /**
     * The <CODE>non-repeaters</CODE> setter.
     * <p>
     *  <CODE>非中继器</CODE>设置器。
     * 
     * 
     * @param nr Non repeaters.
     */
    public void setNonRepeaters(int nr) {
        nonRepeaters = nr;
    }

    /**
     * The <CODE>max-repetitions</CODE> getter.
     * <p>
     *  <CODE> max-repeatedries </CODE> getter。
     * 
     * 
     * @return Maximum repetition.
     */
    public int getMaxRepetitions() { return maxRepetitions; }

    /**
     * The <CODE>non-repeaters</CODE> getter.
     * <p>
     *  <CODE>非中继器</CODE> getter。
     * 
     * 
     * @return Non repeaters.
     */
    public int getNonRepeaters() { return nonRepeaters; }

    /**
     * Generates the pdu to use for response.
     * <p>
     *  生成pdu用于响应。
     * 
     * @return Response pdu.
     */
    public SnmpPdu getResponsePdu() {
        SnmpScopedPduRequest result = new SnmpScopedPduRequest();
        result.address = address ;
        result.port = port ;
        result.version = version ;
        result.requestId = requestId;
        result.msgId = msgId;
        result.msgMaxSize = msgMaxSize;
        result.msgFlags = msgFlags;
        result.msgSecurityModel = msgSecurityModel;
        result.contextEngineId = contextEngineId;
        result.contextName = contextName;
        result.securityParameters = securityParameters;
        result.type = pduGetResponsePdu ;
        result.errorStatus = SnmpDefinitions.snmpRspNoError ;
        result.errorIndex = 0 ;
        return result;
    }
}
