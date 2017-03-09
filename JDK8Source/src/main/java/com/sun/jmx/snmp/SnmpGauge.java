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
 * Represents an SNMP gauge.
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  表示SNMP规格。
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 */

public class SnmpGauge extends SnmpUnsignedInt {
    private static final long serialVersionUID = -8366622742122792945L;

    // CONSTRUCTORS
    //-------------
    /**
     * Constructs a new <CODE>SnmpGauge</CODE> from the specified integer value.
     * <p>
     *  从指定的整数值构造新的<CODE> SnmpGauge </CODE>。
     * 
     * 
     * @param v The initialization value.
     * @exception IllegalArgumentException The specified value is negative
     * or larger than {@link SnmpUnsignedInt#MAX_VALUE SnmpUnsignedInt.MAX_VALUE}.
     */
    public SnmpGauge(int v) throws IllegalArgumentException {
        super(v) ;
    }

    /**
     * Constructs a new <CODE>SnmpGauge</CODE> from the specified <CODE>Integer</CODE> value.
     * <p>
     *  从指定的<CODE> Integer </CODE>值构造新的<CODE> SnmpGauge </CODE>。
     * 
     * 
     * @param v The initialization value.
     * @exception IllegalArgumentException The specified value is negative
     * or larger than {@link SnmpUnsignedInt#MAX_VALUE SnmpUnsignedInt.MAX_VALUE}.
     */
    public SnmpGauge(Integer v) throws IllegalArgumentException {
        super(v) ;
    }

    /**
     * Constructs a new <CODE>SnmpGauge</CODE> from the specified long value.
     * <p>
     *  从指定的长整型值构造新的<CODE> SnmpGauge </CODE>。
     * 
     * 
     * @param v The initialization value.
     * @exception IllegalArgumentException The specified value is negative
     * or larger than {@link SnmpUnsignedInt#MAX_VALUE SnmpUnsignedInt.MAX_VALUE}.
     */
    public SnmpGauge(long v) throws IllegalArgumentException {
        super(v) ;
    }

    /**
     * Constructs a new <CODE>SnmpGauge</CODE> from the specified <CODE>Long</CODE> value.
     * <p>
     *  从指定的<CODE>长</CODE>值构造新的<CODE> SnmpGauge </CODE>。
     * 
     * 
     * @param v The initialization value.
     * @exception IllegalArgumentException The specified value is negative
     * or larger than {@link SnmpUnsignedInt#MAX_VALUE SnmpUnsignedInt.MAX_VALUE}.
     */
    public SnmpGauge(Long v) throws IllegalArgumentException {
        super(v) ;
    }

    // PUBLIC METHODS
    //---------------
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
    final static String name = "Gauge32" ;
}
