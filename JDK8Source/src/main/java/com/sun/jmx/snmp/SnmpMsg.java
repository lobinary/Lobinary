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

import com.sun.jmx.snmp.SnmpSecurityParameters;
// java imports
//
import java.util.Vector;
import java.net.InetAddress;


import com.sun.jmx.snmp.SnmpStatusException;
/**
 * A partially decoded representation of an SNMP packet. It contains
 * the information contained in any SNMP message (SNMPv1, SNMPv2 or
 * SNMPv3).
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  SNMP数据包的部分解码表示。它包含任何SNMP消息(SNMPv1,SNMPv2或SNMPv3)中包含的信息。
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>。
 * 
 * 
 * @since 1.5
 */
public abstract class SnmpMsg implements SnmpDefinitions {
    /**
     * The protocol version.
     * <P><CODE>decodeMessage</CODE> and <CODE>encodeMessage</CODE> do not
     * perform any check on this value.
     * <BR><CODE>decodeSnmpPdu</CODE> and <CODE>encodeSnmpPdu</CODE> only
     * accept  the values 0 (for SNMPv1), 1 (for SNMPv2) and 3 (for SNMPv3).
     * <p>
     *  协议版本。 <P> <CODE> decodeMessage </CODE>和<CODE> encodeMessage </CODE>不要对此值执行任何检查。
     *  <BR> <CODE> decodeSnmpPdu </CODE>和<CODE> encodeSnmpPdu </CODE>仅接受值0(对于SNMPv1),1(对于SNMPv2)和3(对于SNMPv3
     * )。
     *  协议版本。 <P> <CODE> decodeMessage </CODE>和<CODE> encodeMessage </CODE>不要对此值执行任何检查。
     * 
     */
    public int version = 0;

    /**
     * Encoding of the PDU.
     * <P>This is usually the BER encoding of the PDU's syntax
     * defined in RFC1157 and RFC1902. However, this can be authenticated
     * or encrypted data (but you need to implemented your own
     * <CODE>SnmpPduFactory</CODE> class).
     * <p>
     *  PDU的编码。 <P>这通常是RFC1157和RFC1902中定义的PDU语法的BER编码。
     * 然而,这可以是认证或加密的数据(但是你需要实现自己的<CODE> SnmpPduFactory </CODE>类)。
     * 
     */
    public byte[] data = null;

    /**
     * Number of useful bytes in the <CODE>data</CODE> field.
     * <p>
     *  <CODE>数据</CODE>字段中的有用字节数。
     * 
     */
    public int dataLength = 0;

    /**
     * Source or destination address.
     * <BR>For an incoming message it's the source.
     * For an outgoing message it's the destination.
     * <p>
     *  源或目标地址。 <BR>对于传入邮件,它是源。对于外发消息,它是目的地。
     * 
     */
    public InetAddress address = null;

    /**
     * Source or destination port.
     * <BR>For an incoming message it's the source.
     * For an outgoing message it's the destination.
     * <p>
     *  源或目标端口。 <BR>对于传入邮件,它是源。对于外发消息,它是目的地。
     * 
     */
    public int port = 0;
    /**
     * Security parameters. Contain informations according to Security Model (Usm, community string based, ...).
     * <p>
     *  安全参数。包含根据安全模型(Usm,基于社区字符串,...)的信息。
     * 
     */
    public SnmpSecurityParameters securityParameters = null;
    /**
     * Returns the encoded SNMP version present in the passed byte array.
     * <p>
     *  返回传递的字节数组中存在的已编码的SNMP版本。
     * 
     * 
     * @param data The unmarshalled SNMP message.
     * @return The SNMP version (0, 1 or 3).
     */
    public static int getProtocolVersion(byte[] data)
        throws SnmpStatusException {
        int version = 0;
        BerDecoder bdec = null;
        try {
            bdec = new BerDecoder(data);
            bdec.openSequence();
            version = bdec.fetchInteger();
        }
        catch(BerException x) {
            throw new SnmpStatusException("Invalid encoding") ;
        }
        try {
            bdec.closeSequence();
        }
        catch(BerException x) {
        }
        return version;
    }

    /**
     * Returns the associated request ID.
     * <p>
     *  返回关联的请求ID。
     * 
     * 
     * @param data The flat message.
     * @return The request ID.
     */
    public abstract int getRequestId(byte[] data) throws SnmpStatusException;

    /**
     * Encodes this message and puts the result in the specified byte array.
     * For internal use only.
     *
     * <p>
     *  对此消息进行编码,并将结果放入指定的字节数组中。仅限内部使用。
     * 
     * 
     * @param outputBytes An array to receive the resulting encoding.
     *
     * @exception ArrayIndexOutOfBoundsException If the result does not fit
     *                                           into the specified array.
     */
    public abstract int encodeMessage(byte[] outputBytes)
        throws SnmpTooBigException;

     /**
     * Decodes the specified bytes and initializes this message.
     * For internal use only.
     *
     * <p>
     * 解码指定的字节并初始化此消息。仅限内部使用。
     * 
     * 
     * @param inputBytes The bytes to be decoded.
     *
     * @exception SnmpStatusException If the specified bytes are not a valid encoding.
     */
    public abstract void decodeMessage(byte[] inputBytes, int byteCount)
        throws SnmpStatusException;

     /**
     * Initializes this message with the specified <CODE>pdu</CODE>.
     * <P>
     * This method initializes the data field with an array of
     * <CODE>maxDataLength</CODE> bytes. It encodes the <CODE>pdu</CODE>.
     * The resulting encoding is stored in the data field
     * and the length of the encoding is stored in <CODE>dataLength</CODE>.
     * <p>
     * If the encoding length exceeds <CODE>maxDataLength</CODE>,
     * the method throws an exception.
     *
     * <p>
     *  使用指定的<CODE> pdu </CODE>初始化此消息。
     * <P>
     *  此方法使用<CODE> maxDataLength </CODE>字节数组初始化数据字段。它编码<CODE> pdu </CODE>。
     * 所得到的编码存储在数据字段中,并且编码的长度存储在<CODE> dataLength </CODE>中。
     * <p>
     *  如果编码长度超过<CODE> maxDataLength </CODE>,则该方法将抛出异常。
     * 
     * 
     * @param pdu The PDU to be encoded.
     * @param maxDataLength The maximum length permitted for the data field.
     *
     * @exception SnmpStatusException If the specified <CODE>pdu</CODE> is not valid.
     * @exception SnmpTooBigException If the resulting encoding does not fit
     * into <CODE>maxDataLength</CODE> bytes.
     * @exception ArrayIndexOutOfBoundsException If the encoding exceeds <CODE>maxDataLength</CODE>.
     */
    public abstract void encodeSnmpPdu(SnmpPdu pdu, int maxDataLength)
        throws SnmpStatusException, SnmpTooBigException;


    /**
     * Gets the PDU encoded in this message.
     * <P>
     * This method decodes the data field and returns the resulting PDU.
     *
     * <p>
     *  获取此消息中编码的PDU。
     * <P>
     *  此方法解码数据字段并返回生成的PDU。
     * 
     * 
     * @return The resulting PDU.
     * @exception SnmpStatusException If the encoding is not valid.
     */
    public abstract SnmpPdu decodeSnmpPdu()
        throws SnmpStatusException;

    /**
     * Dumps the content of a byte buffer using hexadecimal form.
     *
     * <p>
     *  使用十六进制形式转储字节缓冲区的内容。
     * 
     * 
     * @param b The buffer to dump.
     * @param offset The position of the first byte to be dumped.
     * @param len The number of bytes to be dumped starting from offset.
     *
     * @return The string containing the dump.
     */
    public static String dumpHexBuffer(byte [] b, int offset, int len) {
        StringBuffer buf = new StringBuffer(len << 1) ;
        int k = 1 ;
        int flen = offset + len ;

        for (int i = offset; i < flen ; i++) {
            int j = b[i] & 0xFF ;
            buf.append(Character.forDigit((j >>> 4) , 16)) ;
            buf.append(Character.forDigit((j & 0x0F), 16)) ;
            k++ ;
            if (k%16 == 0) {
                buf.append('\n') ;
                k = 1 ;
            } else
                buf.append(' ') ;
        }
        return buf.toString() ;
    }

    /**
     * Dumps this message in a string.
     *
     * <p>
     *  将此消息转储到字符串中。
     * 
     * 
     * @return The string containing the dump.
     */
    public String printMessage() {
        StringBuffer sb = new StringBuffer() ;
        sb.append("Version: ") ;
        sb.append(version) ;
        sb.append("\n") ;
        if (data == null) {
            sb.append("Data: null") ;
        }
        else {
            sb.append("Data: {\n") ;
            sb.append(dumpHexBuffer(data, 0, dataLength)) ;
            sb.append("\n}\n") ;
        }

        return sb.toString() ;
    }

    /**
     * For SNMP Runtime private use only.
     * <p>
     *  仅供SNMP Runtime私人使用。
     * 
     */
    public void encodeVarBindList(BerEncoder benc,
                                  SnmpVarBind[] varBindList)
        throws SnmpStatusException, SnmpTooBigException {
        //
        // Remember: the encoder does backward encoding
        //
        int encodedVarBindCount = 0 ;
        try {
            benc.openSequence() ;
            if (varBindList != null) {
                for (int i = varBindList.length - 1 ; i >= 0 ; i--) {
                    SnmpVarBind bind = varBindList[i] ;
                    if (bind != null) {
                        benc.openSequence() ;
                        encodeVarBindValue(benc, bind.value) ;
                        benc.putOid(bind.oid.longValue()) ;
                        benc.closeSequence() ;
                        encodedVarBindCount++ ;
                    }
                }
            }
            benc.closeSequence() ;
        }
        catch(ArrayIndexOutOfBoundsException x) {
            throw new SnmpTooBigException(encodedVarBindCount) ;
        }
    }

    /**
     * For SNMP Runtime private use only.
     * <p>
     *  仅供SNMP Runtime私人使用。
     * 
     */
    void encodeVarBindValue(BerEncoder benc,
                            SnmpValue v)throws SnmpStatusException {
        if (v == null) {
            benc.putNull() ;
        }
        else if (v instanceof SnmpIpAddress) {
            benc.putOctetString(((SnmpIpAddress)v).byteValue(), SnmpValue.IpAddressTag) ;
        }
        else if (v instanceof SnmpCounter) {
            benc.putInteger(((SnmpCounter)v).longValue(), SnmpValue.CounterTag) ;
        }
        else if (v instanceof SnmpGauge) {
            benc.putInteger(((SnmpGauge)v).longValue(), SnmpValue.GaugeTag) ;
        }
        else if (v instanceof SnmpTimeticks) {
            benc.putInteger(((SnmpTimeticks)v).longValue(), SnmpValue.TimeticksTag) ;
        }
        else if (v instanceof SnmpOpaque) {
            benc.putOctetString(((SnmpOpaque)v).byteValue(), SnmpValue.OpaqueTag) ;
        }
        else if (v instanceof SnmpInt) {
            benc.putInteger(((SnmpInt)v).intValue()) ;
        }
        else if (v instanceof SnmpString) {
            benc.putOctetString(((SnmpString)v).byteValue()) ;
        }
        else if (v instanceof SnmpOid) {
            benc.putOid(((SnmpOid)v).longValue()) ;
        }
        else if (v instanceof SnmpCounter64) {
            if (version == snmpVersionOne) {
                throw new SnmpStatusException("Invalid value for SNMP v1 : " + v) ;
            }
            benc.putInteger(((SnmpCounter64)v).longValue(), SnmpValue.Counter64Tag) ;
        }
        else if (v instanceof SnmpNull) {
            int tag = ((SnmpNull)v).getTag() ;
            if ((version == snmpVersionOne) && (tag != SnmpValue.NullTag)) {
                throw new SnmpStatusException("Invalid value for SNMP v1 : " + v) ;
            }
            if ((version == snmpVersionTwo) &&
                (tag != SnmpValue.NullTag) &&
                (tag != SnmpVarBind.errNoSuchObjectTag) &&
                (tag != SnmpVarBind.errNoSuchInstanceTag) &&
                (tag != SnmpVarBind.errEndOfMibViewTag)) {
                throw new SnmpStatusException("Invalid value " + v) ;
            }
            benc.putNull(tag) ;
        }
        else {
            throw new SnmpStatusException("Invalid value " + v) ;
        }

    }


    /**
     * For SNMP Runtime private use only.
     * <p>
     *  仅供SNMP Runtime私人使用。
     * 
     */
    public SnmpVarBind[] decodeVarBindList(BerDecoder bdec)
        throws BerException {
            bdec.openSequence() ;
            Vector<SnmpVarBind> tmp = new Vector<SnmpVarBind>() ;
            while (bdec.cannotCloseSequence()) {
                SnmpVarBind bind = new SnmpVarBind() ;
                bdec.openSequence() ;
                bind.oid = new SnmpOid(bdec.fetchOid()) ;
                bind.setSnmpValue(decodeVarBindValue(bdec)) ;
                bdec.closeSequence() ;
                tmp.addElement(bind) ;
            }
            bdec.closeSequence() ;
            SnmpVarBind[] varBindList= new SnmpVarBind[tmp.size()] ;
            tmp.copyInto(varBindList);
            return varBindList ;
        }


    /**
     * For SNMP Runtime private use only.
     * <p>
     *  仅供SNMP Runtime私人使用。
     */
    SnmpValue decodeVarBindValue(BerDecoder bdec)
        throws BerException {
        SnmpValue result = null ;
        int tag = bdec.getTag() ;

        // bugId 4641696 : RuntimeExceptions must be transformed in
        //                 BerException.
        switch(tag) {

            //
            // Simple syntax
            //
        case BerDecoder.IntegerTag :
            try {
                result = new SnmpInt(bdec.fetchInteger()) ;
            } catch(RuntimeException r) {
                throw new BerException();
                // BerException("Can't build SnmpInt from decoded value.");
            }
            break ;
        case BerDecoder.OctetStringTag :
            try {
                result = new SnmpString(bdec.fetchOctetString()) ;
            } catch(RuntimeException r) {
                throw new BerException();
                // BerException("Can't build SnmpString from decoded value.");
            }
            break ;
        case BerDecoder.OidTag :
            try {
                result = new SnmpOid(bdec.fetchOid()) ;
            } catch(RuntimeException r) {
                throw new BerException();
                // BerException("Can't build SnmpOid from decoded value.");
            }
            break ;
        case BerDecoder.NullTag :
            bdec.fetchNull() ;
            try {
                result = new SnmpNull() ;
            } catch(RuntimeException r) {
                throw new BerException();
                // BerException("Can't build SnmpNull from decoded value.");
            }
            break ;

            //
            // Application syntax
            //
        case SnmpValue.IpAddressTag :
            try {
                result = new SnmpIpAddress(bdec.fetchOctetString(tag)) ;
            } catch (RuntimeException r) {
                throw new  BerException();
              // BerException("Can't build SnmpIpAddress from decoded value.");
            }
            break ;
        case SnmpValue.CounterTag :
            try {
                result = new SnmpCounter(bdec.fetchIntegerAsLong(tag)) ;
            } catch(RuntimeException r) {
                throw new BerException();
                // BerException("Can't build SnmpCounter from decoded value.");
            }
            break ;
        case SnmpValue.GaugeTag :
            try {
                result = new SnmpGauge(bdec.fetchIntegerAsLong(tag)) ;
            } catch(RuntimeException r) {
                throw new BerException();
                // BerException("Can't build SnmpGauge from decoded value.");
            }
            break ;
        case SnmpValue.TimeticksTag :
            try {
                result = new SnmpTimeticks(bdec.fetchIntegerAsLong(tag)) ;
            } catch(RuntimeException r) {
                throw new BerException();
             // BerException("Can't build SnmpTimeticks from decoded value.");
            }
            break ;
        case SnmpValue.OpaqueTag :
            try {
                result = new SnmpOpaque(bdec.fetchOctetString(tag)) ;
            } catch(RuntimeException r) {
                throw new BerException();
                // BerException("Can't build SnmpOpaque from decoded value.");
            }
            break ;

            //
            // V2 syntaxes
            //
        case SnmpValue.Counter64Tag :
            if (version == snmpVersionOne) {
                throw new BerException(BerException.BAD_VERSION) ;
            }
            try {
                result = new SnmpCounter64(bdec.fetchIntegerAsLong(tag)) ;
            } catch(RuntimeException r) {
                throw new BerException();
             // BerException("Can't build SnmpCounter64 from decoded value.");
            }
            break ;

        case SnmpVarBind.errNoSuchObjectTag :
            if (version == snmpVersionOne) {
                throw new BerException(BerException.BAD_VERSION) ;
            }
            bdec.fetchNull(tag) ;
            result = SnmpVarBind.noSuchObject ;
            break ;

        case SnmpVarBind.errNoSuchInstanceTag :
            if (version == snmpVersionOne) {
                throw new BerException(BerException.BAD_VERSION) ;
            }
            bdec.fetchNull(tag) ;
            result = SnmpVarBind.noSuchInstance ;
            break ;

        case SnmpVarBind.errEndOfMibViewTag :
            if (version == snmpVersionOne) {
                throw new BerException(BerException.BAD_VERSION) ;
            }
            bdec.fetchNull(tag) ;
            result = SnmpVarBind.endOfMibView ;
            break ;

        default:
            throw new BerException() ;

        }

        return result ;
    }

}
