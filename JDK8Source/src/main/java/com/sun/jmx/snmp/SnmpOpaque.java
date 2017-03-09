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
 * Is used to represent an SNMP value.
 * The <CODE>Opaque</CODE> type is defined in RFC 1155.
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  用于表示SNMP值。 <CODE>不透明</CODE>类型在RFC 1155中定义。
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 */

public class SnmpOpaque extends SnmpString {
    private static final long serialVersionUID = 380952213936036664L;

    // CONSTRUCTORS
    //-------------
    /**
     * Constructs a new <CODE>SnmpOpaque</CODE> from the specified bytes array.
     * <p>
     *  从指定的字节数组构造一个新的<CODE> SnmpOpaque </CODE>。
     * 
     * 
     * @param v The bytes composing the opaque value.
     */
    public SnmpOpaque(byte[] v) {
        super(v) ;
    }

    /**
     * Constructs a new <CODE>SnmpOpaque</CODE> with the specified <CODE>Bytes</CODE> array.
     * <p>
     *  使用指定的<CODE> Bytes </CODE>数组构造新的<CODE> SnmpOpaque </CODE>。
     * 
     * 
     * @param v The <CODE>Bytes</CODE> composing the opaque value.
     */
    public SnmpOpaque(Byte[] v) {
        super(v) ;
    }

    /**
     * Constructs a new <CODE>SnmpOpaque</CODE> from the specified <CODE>String</CODE> value.
     * <p>
     *  从指定的<CODE> String </CODE>值构造新的<CODE> SnmpOpaque </CODE>。
     * 
     * 
     * @param v The initialization value.
     */
    public SnmpOpaque(String v) {
        super(v) ;
    }

    // PUBLIC METHODS
    //---------------
    /**
     * Converts the opaque to its <CODE>String</CODE> form, that is, a string of
     * bytes expressed in hexadecimal form.
     * <p>
     *  将opaque转换为其<CODE> String </CODE>形式,即以十六进制形式表示的字节字符串。
     * 
     * 
     * @return The <CODE>String</CODE> representation of the value.
     */
    public String toString() {
        StringBuffer result = new StringBuffer() ;
        for (int i = 0 ; i < value.length ; i++) {
            byte b = value[i] ;
            int n = (b >= 0) ? b : b + 256 ;
            result.append(Character.forDigit(n / 16, 16)) ;
            result.append(Character.forDigit(n % 16, 16)) ;
        }
        return result.toString() ;
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

    // VARIABLES
    //----------
    /**
     * Name of the type.
     * <p>
     *  类型的名称。
     */
    final static String name = "Opaque" ;
}
