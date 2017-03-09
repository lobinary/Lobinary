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
 * Represents an SNMP IpAddress.
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  表示SNMP IpAddress。
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 */

public class SnmpIpAddress extends SnmpOid {
    private static final long serialVersionUID = 7204629998270874474L;

    // CONSTRUCTORS
    //-------------
    /**
     * Constructs a new <CODE>SnmpIpAddress</CODE> from the specified bytes array.
     * <p>
     *  从指定的字节数组构造一个新的<CODE> SnmpIpAddress </CODE>。
     * 
     * 
     * @param bytes The four bytes composing the address.
     * @exception IllegalArgumentException The length of the array is not equal to four.
     */
    public SnmpIpAddress(byte[] bytes) throws IllegalArgumentException {
        buildFromByteArray(bytes);
    }

    /**
     * Constructs a new <CODE>SnmpIpAddress</CODE> from the specified long value.
     * <p>
     *  从指定的长整型值构造新的<CODE> SnmpIpAddress </CODE>。
     * 
     * 
     * @param addr The initialization value.
     */
    public SnmpIpAddress(long addr) {
        int address = (int)addr ;
        byte[] ipaddr = new byte[4];

        ipaddr[0] = (byte) ((address >>> 24) & 0xFF);
        ipaddr[1] = (byte) ((address >>> 16) & 0xFF);
        ipaddr[2] = (byte) ((address >>> 8) & 0xFF);
        ipaddr[3] = (byte) (address & 0xFF);

        buildFromByteArray(ipaddr);
    }

    /**
     * Constructs a new <CODE>SnmpIpAddress</CODE> from a dot-formatted <CODE>String</CODE>.
     * The dot-formatted <CODE>String</CODE> is formulated x.x.x.x .
     * <p>
     *  从点格式的<CODE>字符串</CODE>构造新的<CODE> SnmpIpAddress </CODE>。点格式<CODE> String </CODE>以x.x.x.x表示。
     * 
     * 
     * @param dotAddress The initialization value.
     * @exception IllegalArgumentException The string does not correspond to an ip address.
     */
    public SnmpIpAddress(String dotAddress) throws IllegalArgumentException {
        super(dotAddress) ;
        if ((componentCount > 4) ||
            (components[0] > 255) ||
            (components[1] > 255) ||
            (components[2] > 255) ||
            (components[3] > 255)) {
            throw new IllegalArgumentException(dotAddress) ;
        }
    }

    /**
     * Constructs a new <CODE>SnmpIpAddress</CODE> from four long values.
     * <p>
     *  从四个长值构造新的<CODE> SnmpIpAddress </CODE>。
     * 
     * 
     * @param b1 Byte 1.
     * @param b2 Byte 2.
     * @param b3 Byte 3.
     * @param b4 Byte 4.
     * @exception IllegalArgumentException A value is outside of [0-255].
     */
    public SnmpIpAddress(long b1, long b2, long b3, long b4) {
        super(b1, b2, b3, b4) ;
        if ((components[0] > 255) ||
            (components[1] > 255) ||
            (components[2] > 255) ||
            (components[3] > 255)) {
            throw new IllegalArgumentException() ;
        }
    }

    // PUBLIC METHODS
    //---------------
    /**
     * Converts the address value to its byte array form.
     * <p>
     *  将地址值转换为其字节数组形式。
     * 
     * 
     * @return The byte array representation of the value.
     */
    public byte[] byteValue() {
        byte[] result = new byte[4] ;
        result[0] = (byte)components[0] ;
        result[1] = (byte)components[1] ;
        result[2] = (byte)components[2] ;
        result[3] = (byte)components[3] ;

        return result ;
    }

    /**
     * Converts the address to its <CODE>String</CODE> form.
     * Same as <CODE>toString()</CODE>. Exists only to follow a naming scheme.
     * <p>
     *  将地址转换为其<CODE>字符串</CODE>表单。与<CODE> toString()</CODE>相同。存在只遵循命名方案。
     * 
     * 
     * @return The <CODE>String</CODE> representation of the value.
     */
    public String stringValue() {
        return toString() ;
    }

    /**
     * Extracts the ip address from an index OID and returns its
     * value converted as an <CODE>SnmpOid</CODE>.
     * <p>
     *  从索引OID提取IP地址,并返回其转换为<CODE> SnmpOid </CODE>的值。
     * 
     * 
     * @param index The index array.
     * @param start The position in the index array.
     * @return The OID representing the ip address value.
     * @exception SnmpStatusException There is no ip address value
     * available at the start position.
     */
    public static SnmpOid toOid(long[] index, int start) throws SnmpStatusException {
        if (start + 4 <= index.length) {
            try {
                return new SnmpOid(
                                   index[start],
                                   index[start+1],
                                   index[start+2],
                                   index[start+3]) ;
            }
            catch(IllegalArgumentException e) {
                throw new SnmpStatusException(SnmpStatusException.noSuchName) ;
            }
        }
        else {
            throw new SnmpStatusException(SnmpStatusException.noSuchName) ;
        }
    }

    /**
     * Scans an index OID, skips the address value and returns the position
     * of the next value.
     * <p>
     *  扫描索引OID,跳过地址值并返回下一个值的位置。
     * 
     * 
     * @param index The index array.
     * @param start The position in the index array.
     * @return The position of the next value.
     * @exception SnmpStatusException There is no address value
     * available at the start position.
     */
    public static int nextOid(long[] index, int start) throws SnmpStatusException {
        if (start + 4 <= index.length) {
            return start + 4 ;
        }
        else {
            throw new SnmpStatusException(SnmpStatusException.noSuchName) ;
        }
    }

    /**
     * Appends an <CODE>SnmpOid</CODE> representing an <CODE>SnmpIpAddress</CODE> to another OID.
     * <p>
     *  将代表<CODE> SnmpIpAddress </CODE>的<CODE> SnmpOid </CODE>附加到另一个OID。
     * 
     * 
     * @param source An OID representing an <CODE>SnmpIpAddress</CODE> value.
     * @param dest Where source should be appended.
     */
    public static void appendToOid(SnmpOid source, SnmpOid dest) {
        if (source.getLength() != 4) {
            throw new IllegalArgumentException() ;
        }
        dest.append(source) ;
    }

    /**
     * Returns a textual description of the type object.
     * <p>
     *  返回类型对象的文本描述。
     * 
     * 
     * @return ASN.1 textual description.
     */
    final public String getTypeName() {
        return name ;
    }

    // PRIVATE METHODS
    //----------------
    /**
     * Build Ip address from byte array.
     * <p>
     *  从字节数组构建Ip地址。
     * 
     */
    private void buildFromByteArray(byte[] bytes) {
        if (bytes.length != 4) {
            throw new IllegalArgumentException() ;
        }
        components = new long[4] ;
        componentCount= 4;
        components[0] = (bytes[0] >= 0) ? bytes[0] : bytes[0] + 256 ;
        components[1] = (bytes[1] >= 0) ? bytes[1] : bytes[1] + 256 ;
        components[2] = (bytes[2] >= 0) ? bytes[2] : bytes[2] + 256 ;
        components[3] = (bytes[3] >= 0) ? bytes[3] : bytes[3] + 256 ;
    }

    // VARIABLES
    //----------
    /**
     * Name of the type.
     * <p>
     *  类型的名称。
     */
    final static String name = "IpAddress" ;
}
