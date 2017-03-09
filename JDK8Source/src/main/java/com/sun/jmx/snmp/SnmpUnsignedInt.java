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
 * Is the base for all SNMP syntaxes based on unsigned integers.
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  是基于无符号整数的所有SNMP语法的基础。
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 */

public abstract class SnmpUnsignedInt extends SnmpInt {

    /**
     * The largest value of the type <code>unsigned int</code> (2^32 - 1).
     * <p>
     *  类型<code> unsigned int </code>(2 ^ 32  -  1)的最大值。
     * 
     */
    public static final long   MAX_VALUE = 0x0ffffffffL;

    // CONSTRUCTORS
    //-------------
    /**
     * Constructs a new <CODE>SnmpUnsignedInt</CODE> from the specified integer value.
     * <p>
     *  从指定的整数值构造新的<CODE> SnmpUnsignedInt </CODE>。
     * 
     * 
     * @param v The initialization value.
     * @exception IllegalArgumentException The specified value is negative
     * or larger than {@link #MAX_VALUE SnmpUnsignedInt.MAX_VALUE}.
     */
    public SnmpUnsignedInt(int v) throws IllegalArgumentException {
        super(v);
    }

    /**
     * Constructs a new <CODE>SnmpUnsignedInt</CODE> from the specified <CODE>Integer</CODE> value.
     * <p>
     *  从指定的<CODE> Integer </CODE>值构造新的<CODE> SnmpUnsignedInt </CODE>。
     * 
     * 
     * @param v The initialization value.
     * @exception IllegalArgumentException The specified value is negative
     * or larger than {@link #MAX_VALUE SnmpUnsignedInt.MAX_VALUE}.
     */
    public SnmpUnsignedInt(Integer v) throws IllegalArgumentException {
        super(v);
    }

    /**
     * Constructs a new <CODE>SnmpUnsignedInt</CODE> from the specified long value.
     * <p>
     *  从指定的长整型值构造新的<CODE> SnmpUnsignedInt </CODE>。
     * 
     * 
     * @param v The initialization value.
     * @exception IllegalArgumentException The specified value is negative
     * or larger than {@link #MAX_VALUE SnmpUnsignedInt.MAX_VALUE}.
     */
    public SnmpUnsignedInt(long v) throws IllegalArgumentException {
        super(v);
    }

    /**
     * Constructs a new <CODE>SnmpUnsignedInt</CODE> from the specified <CODE>Long</CODE> value.
     * <p>
     *  从指定的<CODE>长</CODE>值构造新的<CODE> SnmpUnsignedInt </CODE>。
     * 
     * 
     * @param v The initialization value.
     * @exception IllegalArgumentException The specified value is negative
     * or larger than {@link #MAX_VALUE SnmpUnsignedInt.MAX_VALUE}.
     */
    public SnmpUnsignedInt(Long v) throws IllegalArgumentException {
        super(v);
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
    public String getTypeName() {
        return name ;
    }

    /**
     * This method has been defined to allow the sub-classes
     * of SnmpInt to perform their own control at intialization time.
     * <p>
     *  此方法已被定义为允许SnmpInt的子类在初始化时执行它们自己的控制。
     * 
     */
    boolean isInitValueValid(int v) {
        if ((v < 0) || (v > SnmpUnsignedInt.MAX_VALUE)) {
            return false;
        }
        return true;
    }

    /**
     * This method has been defined to allow the sub-classes
     * of SnmpInt to perform their own control at intialization time.
     * <p>
     *  此方法已被定义为允许SnmpInt的子类在初始化时执行它们自己的控制。
     * 
     */
    boolean isInitValueValid(long v) {
        if ((v < 0) || (v > SnmpUnsignedInt.MAX_VALUE)) {
            return false;
        }
        return true;
    }

    // VARIABLES
    //----------
    /**
     * Name of the type.
     * <p>
     *  类型的名称。
     */
    final static String name = "Unsigned32" ;
}
