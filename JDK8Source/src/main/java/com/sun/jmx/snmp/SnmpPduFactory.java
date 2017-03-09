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
 * Defines the interface of the object in charge of encoding and decoding SNMP packets.
 * <P>
 * You will not usually need to use this interface, except if you
 * decide to replace the default implementation <CODE>SnmpPduFactoryBER</CODE>.
 * <P>
 * An <CODE>SnmpPduFactory</CODE> object is attached to an
 * {@link com.sun.jmx.snmp.daemon.SnmpAdaptorServer SNMP protocol adaptor}
 * or an {@link com.sun.jmx.snmp.SnmpPeer SnmpPeer}.
 * It is used each time an SNMP packet needs to be encoded or decoded.
 * <BR>{@link com.sun.jmx.snmp.SnmpPduFactoryBER SnmpPduFactoryBER} is the default
 * implementation.
 * It simply applies the standard ASN.1 encoding and decoding
 * on the bytes of the SNMP packet.
 * <P>
 * It's possible to implement your own <CODE>SnmpPduFactory</CODE>
 * object and to add authentication and/or encryption to the
 * default encoding/decoding process.
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  定义负责编码和解码SNMP数据包的对象的接口。
 * <P>
 *  通常不需要使用此接口,除非您决定替换默认实现<CODE> SnmpPduFactoryBER </CODE>。
 * <P>
 *  <CODE> SnmpPduFactory </CODE>对象附加到{@link com.sun.jmx.snmp.daemon.SnmpAdaptorServer SNMP协议适配器}或{@link com.sun.jmx.snmp.SnmpPeer SnmpPeer}
 * 。
 * 每次需要对SNMP数据包进行编码或解码时使用。 <BR> {@link com.sun.jmx.snmp.SnmpPduFactoryBER SnmpPduFactoryBER}是默认实现。
 * 它简单地对SNMP数据包的字节应用标准的ASN.1编码和解码。
 * <P>
 *  可以实现自己的<CODE> SnmpPduFactory </CODE>对象,并向默认编码/解码过程添加认证和/或加密。
 * 
 * 
 * @see SnmpPduFactory
 * @see SnmpPduPacket
 * @see SnmpMessage
 *
 */

public interface SnmpPduFactory {

    /**
     * Decodes the specified <CODE>SnmpMsg</CODE> and returns the
     * resulting <CODE>SnmpPdu</CODE>. If this method returns
     * <CODE>null</CODE>, the message will be considered unsafe
     * and will be dropped.
     *
     * <p>
     *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
     * 
     * 
     * @param msg The <CODE>SnmpMsg</CODE> to be decoded.
     * @return Null or a fully initialized <CODE>SnmpPdu</CODE>.
     * @exception SnmpStatusException If the encoding is invalid.
     *
     * @since 1.5
     */
    public SnmpPdu decodeSnmpPdu(SnmpMsg msg) throws SnmpStatusException ;

    /**
     * Encodes the specified <CODE>SnmpPdu</CODE> and
     * returns the resulting <CODE>SnmpMsg</CODE>. If this
     * method returns null, the specified <CODE>SnmpPdu</CODE>
     * will be dropped and the current SNMP request will be
     * aborted.
     *
     * <p>
     *  解码指定的<CODE> SnmpMsg </CODE>,并返回结果<CODE> SnmpPdu </CODE>。
     * 如果此方法返回<CODE> null </CODE>,则该邮件将被视为不安全,并将被删除。
     * 
     * 
     * @param p The <CODE>SnmpPdu</CODE> to be encoded.
     * @param maxDataLength The size limit of the resulting encoding.
     * @return Null or a fully encoded <CODE>SnmpMsg</CODE>.
     * @exception SnmpStatusException If <CODE>pdu</CODE> contains
     *            illegal values and cannot be encoded.
     * @exception SnmpTooBigException If the resulting encoding does not
     *            fit into <CODE>maxPktSize</CODE> bytes.
     *
     * @since 1.5
     */
    public SnmpMsg encodeSnmpPdu(SnmpPdu p, int maxDataLength)
        throws SnmpStatusException, SnmpTooBigException ;
}
