/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
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
 * Represents an SNMP 64bits counter.
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  表示SNMP 64位计数器。
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 */

public class SnmpCounter64 extends SnmpValue {
    private static final long serialVersionUID = 8784850650494679937L;

    // CONSTRUCTORS
    //-------------
    /**
     * Constructs a new <CODE>SnmpCounter64</CODE> from the specified long value.
     * <p>
     *  从指定的长整型值构造一个新的<CODE> SnmpCounter64 </CODE>。
     * 
     * 
     * @param v The initialization value.
     * @exception IllegalArgumentException The specified value is negative
     * or larger than <CODE>Long.MAX_VALUE</CODE>.
     */
    public SnmpCounter64(long v) throws IllegalArgumentException {

        // NOTE:
        // The max value for a counter64 variable is 2^64 - 1.
        // The max value for a Long is 2^63 - 1.
        // All the allowed values for a conuter64 variable cannot be covered !!!
        //
        if ((v < 0) || (v > Long.MAX_VALUE)) {
            throw new IllegalArgumentException() ;
        }
        value = v ;
    }

    /**
     * Constructs a new <CODE>SnmpCounter64</CODE> from the specified <CODE>Long</CODE> value.
     * <p>
     *  从指定的<CODE>长</CODE>值构造新的<CODE> SnmpCounter64 </CODE>。
     * 
     * 
     * @param v The initialization value.
     * @exception IllegalArgumentException The specified value is negative
     * or larger than <CODE>Long.MAX_VALUE</CODE>.
     */
    public SnmpCounter64(Long v) throws IllegalArgumentException {
        this(v.longValue()) ;
    }

    // PUBLIC METHODS
    //---------------
    /**
     * Returns the counter value of this <CODE>SnmpCounter64</CODE>.
     * <p>
     *  返回此<CODE> SnmpCounter64 </CODE>的计数器值。
     * 
     * 
     * @return The value.
     */
    public long longValue() {
        return value ;
    }

    /**
     * Converts the counter value to its <CODE>Long</CODE> form.
     * <p>
     *  将计数器值转换为其<CODE>长</CODE>格式。
     * 
     * 
     * @return The <CODE>Long</CODE> representation of the value.
     */
    public Long toLong() {
        return new Long(value) ;
    }

    /**
     * Converts the counter value to its integer form.
     * <p>
     *  将计数器值转换为其整数形式。
     * 
     * 
     * @return The integer representation of the value.
     */
    public int intValue() {
        return (int)value ;
    }

    /**
     * Converts the counter value to its <CODE>Integer</CODE> form.
     * <p>
     *  将计数器值转换为其<CODE>整数</CODE>形式。
     * 
     * 
     * @return The <CODE>Integer</CODE> representation of the value.
     */
    public Integer toInteger() {
        return new Integer((int)value) ;
    }

    /**
     * Converts the counter value to its <CODE>String</CODE> form.
     * <p>
     *  将计数器值转换为其<CODE>字符串</CODE>表单。
     * 
     * 
     * @return The <CODE>String</CODE> representation of the value.
     */
    public String toString() {
        return String.valueOf(value) ;
    }

    /**
     * Converts the counter value to its <CODE>SnmpOid</CODE> form.
     * <p>
     *  将计数器值转换为其<CODE> SnmpOid </CODE>表单。
     * 
     * 
     * @return The OID representation of the value.
     */
    public SnmpOid toOid() {
        return new SnmpOid(value) ;
    }

    /**
     * Extracts the counter from an index OID and returns its
     * value converted as an <CODE>SnmpOid</CODE>.
     * <p>
     *  从索引OID提取计数器,并返回其转换为<CODE> SnmpOid </CODE>的值。
     * 
     * 
     * @param index The index array.
     * @param start The position in the index array.
     * @return The OID representing the counter value.
     * @exception SnmpStatusException There is no counter value
     * available at the start position.
     */
    public static SnmpOid toOid(long[] index, int start) throws SnmpStatusException {
        try {
            return new SnmpOid(index[start]) ;
        }
        catch(IndexOutOfBoundsException e) {
            throw new SnmpStatusException(SnmpStatusException.noSuchName) ;
        }
    }

    /**
     * Scans an index OID, skips the counter value and returns the position
     * of the next value.
     * <p>
     *  扫描索引OID,跳过计数器值并返回下一个值的位置。
     * 
     * 
     * @param index The index array.
     * @param start The position in the index array.
     * @return The position of the next value.
     * @exception SnmpStatusException There is no counter value
     * available at the start position.
     */
    public static int nextOid(long[] index, int start) throws SnmpStatusException {
        if (start >= index.length) {
            throw new SnmpStatusException(SnmpStatusException.noSuchName) ;
        }
        else {
            return start + 1 ;
        }
    }

    /**
     * Appends an <CODE>SnmpOid</CODE> representing an <CODE>SnmpCounter64</CODE> to another OID.
     * <p>
     *  将代表<CODE> SnmpCounter64 </CODE>的<CODE> SnmpOid </CODE>附加到另一个OID。
     * 
     * 
     * @param source An OID representing an <CODE>SnmpCounter64</CODE> value.
     * @param dest Where source should be appended.
     */
    public static void appendToOid(SnmpOid source, SnmpOid dest) {
        if (source.getLength() != 1) {
            throw new IllegalArgumentException() ;
        }
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
        return (SnmpValue)clone() ;
    }

    /**
     * Clones the <CODE>SnmpCounter64</CODE> object, making a copy of its data.
     * <p>
     *  克隆<CODE> SnmpCounter64 </CODE>对象,创建其数据的副本。
     * 
     * 
     * @return The object clone.
     */
    final synchronized public Object clone() {
        SnmpCounter64  newclone = null ;
        try {
            newclone = (SnmpCounter64) super.clone() ;
            newclone.value = value ;
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
    final public String getTypeName() {
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
    final static String name = "Counter64" ;

    /**
     * This is where the value is stored. This long is positive.
     * <p>
     *  这是存储值的位置。这长期是积极的。
     * 
     * @serial
     */
    private long value = 0 ;
}
