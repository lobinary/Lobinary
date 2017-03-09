/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Represents an SNMP string.
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  表示SNMP字符串。
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 */

public class SnmpString extends SnmpValue {
    private static final long serialVersionUID = -7011986973225194188L;

    // CONSTRUCTORS
    //-------------
    /**
     * Constructs a new empty <CODE>SnmpString</CODE>.
     * <p>
     *  构造一个新的空<CODE> SnmpString </CODE>。
     * 
     */
    public SnmpString() {
        value = new byte[0] ;
    }

    /**
     * Constructs a new <CODE>SnmpString</CODE> from the specified bytes array.
     * <p>
     *  从指定的字节数组构造一个新的<CODE> SnmpString </CODE>。
     * 
     * 
     * @param v The bytes composing the string value.
     */
    public SnmpString(byte[] v) {
        value = v.clone() ;
    }

    /**
     * Constructs a new <CODE>SnmpString</CODE> from the specified <CODE>Bytes</CODE> array.
     * <p>
     *  从指定的<CODE> Bytes </CODE>数组构造新的<CODE> SnmpString </CODE>。
     * 
     * 
     * @param v The <CODE>Bytes</CODE> composing the string value.
     */
    public SnmpString(Byte[] v) {
        value = new byte[v.length] ;
        for (int i = 0 ; i < v.length ; i++) {
            value[i] = v[i].byteValue() ;
        }
    }

    /**
     * Constructs a new <CODE>SnmpString</CODE> from the specified <CODE>String</CODE> value.
     * <p>
     *  从指定的<CODE> String </CODE>值构造新的<CODE> SnmpString </CODE>。
     * 
     * 
     * @param v The initialization value.
     */
    public SnmpString(String v) {
        value = v.getBytes() ;
    }

    /**
     * Constructs a new <CODE>SnmpString</CODE> from the specified <CODE> InetAddress </Code>.
     * <p>
     *  从指定的<CODE> InetAddress </Code>构造新的<CODE> SnmpString </CODE>。
     * 
     * 
     * @param address The <CODE>InetAddress </CODE>.
     *
     * @since 1.5
     */
    public SnmpString(InetAddress address) {
        value = address.getAddress();
    }

    // PUBLIC METHODS
    //---------------

    /**
     * Converts the string value to its <CODE> InetAddress </CODE> form.
     * <p>
     *  将字符串值转换为其<CODE> InetAddress </CODE>表单。
     * 
     * 
     * @return an {@link InetAddress} defined by the string value.
     * @exception UnknownHostException If string value is not a legal address format.
     *
     * @since 1.5
     */
    public InetAddress inetAddressValue() throws UnknownHostException {
        return InetAddress.getByAddress(value);
    }

    /**
     * Converts the specified binary string into a character string.
     * <p>
     *  将指定的二进制字符串转换为字符串。
     * 
     * 
     * @param bin The binary string value to convert.
     * @return The character string representation.
     */
    public static String BinToChar(String bin) {
        char value[] = new char[bin.length()/8];
        int binLength = value.length;
        for (int i = 0; i < binLength; i++)
            value[i] = (char)Integer.parseInt(bin.substring(8*i, 8*i+8), 2);
        return new String(value);
    }

    /**
     * Converts the specified hexadecimal string into a character string.
     * <p>
     *  将指定的十六进制字符串转换为字符串。
     * 
     * 
     * @param hex The hexadecimal string value to convert.
     * @return The character string representation.
     */
    public static String HexToChar(String hex) {
        char value[] = new char[hex.length()/2];
        int hexLength = value.length;
        for (int i = 0; i < hexLength; i++)
            value[i] = (char)Integer.parseInt(hex.substring(2*i, 2*i+2), 16);
        return new String(value);
    }

    /**
     * Returns the bytes array of this <CODE>SnmpString</CODE>.
     * <p>
     *  返回此<CODE> SnmpString </CODE>的字节数组。
     * 
     * 
     * @return The value.
     */
    public byte[] byteValue() {
        return value.clone() ;
    }

    /**
     * Converts the string value to its array of <CODE>Bytes</CODE> form.
     * <p>
     *  将字符串值转换为<CODE> Bytes </CODE>表单的数组。
     * 
     * 
     * @return The array of <CODE>Bytes</CODE> representation of the value.
     */
    public Byte[] toByte() {
        Byte[] result = new Byte[value.length] ;
        for (int i = 0 ; i < value.length ; i++) {
            result[i] = new Byte(value[i]) ;
        }
        return result ;
    }

    /**
     * Converts the string value to its <CODE>String</CODE> form.
     * <p>
     *  将字符串值转换为其<CODE> String </CODE>表单。
     * 
     * 
     * @return The <CODE>String</CODE> representation of the value.
     */
    public String toString() {
        return new String(value) ;
    }

    /**
     * Converts the string value to its <CODE>SnmpOid</CODE> form.
     * <p>
     *  将字符串值转换为其<CODE> SnmpOid </CODE>表单。
     * 
     * 
     * @return The OID representation of the value.
     */
    public SnmpOid toOid() {
        long[] ids = new long[value.length] ;
        for (int i = 0 ; i < value.length ; i++) {
            ids[i] = (long)(value[i] & 0xFF) ;
        }
        return new SnmpOid(ids) ;
    }

    /**
     * Extracts the string from an index OID and returns its
     * value converted as an <CODE>SnmpOid</CODE>.
     * <p>
     *  从索引OID提取字符串,并返回其转换为<CODE> SnmpOid </CODE>的值。
     * 
     * 
     * @param index The index array.
     * @param start The position in the index array.
     * @return The OID representing the string value.
     * @exception SnmpStatusException There is no string value
     * available at the start position.
     */
    public static SnmpOid toOid(long[] index, int start) throws SnmpStatusException {
        try {
            if (index[start] > Integer.MAX_VALUE) {
                throw new SnmpStatusException(SnmpStatusException.noSuchName) ;
            }
            int strLen = (int)index[start++] ;
            long[] ids = new long[strLen] ;
            for (int i = 0 ; i < strLen ; i++) {
                ids[i] = index[start + i] ;
            }
            return new SnmpOid(ids) ;
        }
        catch(IndexOutOfBoundsException e) {
            throw new SnmpStatusException(SnmpStatusException.noSuchName) ;
        }
    }

    /**
     * Scans an index OID, skips the string value and returns the position
     * of the next value.
     * <p>
     *  扫描索引OID,跳过字符串值并返回下一个值的位置。
     * 
     * 
     * @param index The index array.
     * @param start The position in the index array.
     * @return The position of the next value.
     * @exception SnmpStatusException There is no string value
     * available at the start position.
     */
    public static int nextOid(long[] index, int start) throws SnmpStatusException {
        try {
            if (index[start] > Integer.MAX_VALUE) {
                throw new SnmpStatusException(SnmpStatusException.noSuchName) ;
            }
            int strLen = (int)index[start++] ;
            start += strLen ;
            if (start <= index.length) {
                return start ;
            }
            else {
                throw new SnmpStatusException(SnmpStatusException.noSuchName) ;
            }
        }
        catch(IndexOutOfBoundsException e) {
            throw new SnmpStatusException(SnmpStatusException.noSuchName) ;
        }
    }

    /**
     * Appends an <CODE>SnmpOid</CODE> representing an <CODE>SnmpString</CODE> to another OID.
     * <p>
     *  将代表<CODE> SnmpString </CODE>的<CODE> SnmpOid </CODE>附加到另一个OID。
     * 
     * 
     * @param source An OID representing an <CODE>SnmpString</CODE> value.
     * @param dest Where source should be appended.
     */
    public static void appendToOid(SnmpOid source, SnmpOid dest) {
        dest.append(source.getLength()) ;
        dest.append(source) ;
    }

    /**
     * Performs a clone action. This provides a workaround for the
     * <CODE>SnmpValue</CODE> interface.
     * <p>
     *  执行克隆操作。这为<CODE> SnmpValue </CODE>接口提供了一个解决方法。
     * 
     * 
     * @return The SnmpValue clone.
     */
    final synchronized public SnmpValue duplicate() {
        return (SnmpValue) clone() ;
    }

    /**
     * Clones the <CODE>SnmpString</CODE> object, making a copy of its data.
     * <p>
     * 克隆<CODE> SnmpString </CODE>对象,创建其数据的副本。
     * 
     * 
     * @return The object clone.
     */
    synchronized public Object clone() {
        SnmpString newclone = null ;

        try {
            newclone = (SnmpString) super.clone() ;
            newclone.value = new byte[value.length] ;
            System.arraycopy(value, 0, newclone.value, 0, value.length) ;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e) ; // vm bug.
        }
        return newclone ;
    }

    /**
     * Returns a textual description of the type object.
     * <p>
     *  返回类型对象的文本描述。
     * 
     * 
     * @return ASN.1 textual description.
     */
    public String getTypeName() {
        return name ;
    }

    // VARIABLES
    //----------
    /**
     * Name of the type.
     * <p>
     *  类型的名称。
     * 
     */
    final static String name = "String" ;

    /**
     * This is the bytes array of the string value.
     * <p>
     *  这是字符串值的字节数组。
     * 
     * @serial
     */
    protected byte[] value = null ;
}
