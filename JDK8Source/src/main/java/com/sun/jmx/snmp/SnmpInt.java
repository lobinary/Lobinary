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



import com.sun.jmx.snmp.Enumerated;

/**
 * Represents an SNMP integer.
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  表示SNMP整数。
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 */

public class SnmpInt extends SnmpValue {
    private static final long serialVersionUID = -7163624758070343373L;

    // CONSTRUCTORS
    //-------------
    /**
     * Constructs a new <CODE>SnmpInt</CODE> from the specified integer value.
     * <p>
     *  从指定的整数值构造新的<CODE> SnmpInt </CODE>。
     * 
     * 
     * @param v The initialization value.
     * @exception IllegalArgumentException The specified value is smaller than <CODE>Integer.MIN_VALUE</CODE>
     * or larger than <CODE>Integer.MAX_VALUE</CODE>.
     */
    public SnmpInt(int v) throws IllegalArgumentException {
        if ( isInitValueValid(v) == false ) {
            throw new IllegalArgumentException() ;
        }
        value = (long)v ;
    }

    /**
     * Constructs a new <CODE>SnmpInt</CODE> from the specified <CODE>Integer</CODE> value.
     * <p>
     *  从指定的<CODE> Integer </CODE>值构造新的<CODE> SnmpInt </CODE>。
     * 
     * 
     * @param v The initialization value.
     * @exception IllegalArgumentException The specified value is smaller than <CODE>Integer.MIN_VALUE</CODE>
     * or larger than <CODE>Integer.MAX_VALUE</CODE>.
     */
    public SnmpInt(Integer v) throws IllegalArgumentException {
        this(v.intValue()) ;
    }

    /**
     * Constructs a new <CODE>SnmpInt</CODE> from the specified long value.
     * <p>
     *  从指定的长整型值构造新的<CODE> SnmpInt </CODE>。
     * 
     * 
     * @param v The initialization value.
     * @exception IllegalArgumentException The specified value is smaller than <CODE>Integer.MIN_VALUE</CODE>
     * or larger than <CODE>Integer.MAX_VALUE</CODE>.
     */
    public SnmpInt(long v) throws IllegalArgumentException {
        if ( isInitValueValid(v) == false ) {
            throw new IllegalArgumentException() ;
        }
        value = v ;
    }

    /**
     * Constructs a new <CODE>SnmpInt</CODE> from the specified <CODE>Long</CODE> value.
     * <p>
     *  从指定的<CODE>长</CODE>值构造新的<CODE> SnmpInt </CODE>。
     * 
     * 
     * @param v The initialization value.
     * @exception IllegalArgumentException The specified value is smaller than <CODE>Integer.MIN_VALUE</CODE>
     * or larger than <CODE>Integer.MAX_VALUE</CODE>.
     */
    public SnmpInt(Long v) throws IllegalArgumentException {
        this(v.longValue()) ;
    }

    /**
     * Constructs a new <CODE>SnmpInt</CODE> from the specified <CODE>Enumerated</CODE> value.
     * <p>
     *  从指定的<CODE>枚举</CODE>值构造新的<CODE> SnmpInt </CODE>。
     * 
     * 
     * @param v The initialization value.
     * @exception IllegalArgumentException The specified value is smaller than <CODE>Integer.MIN_VALUE</CODE>
     * or larger than <CODE>Integer.MAX_VALUE</CODE>.
     * @see Enumerated
     */
    public SnmpInt(Enumerated v) throws IllegalArgumentException {
        this(v.intValue()) ;
    }

    /**
     * Constructs a new <CODE>SnmpInt</CODE> from the specified boolean value.
     * This constructor applies rfc1903 rule:
     * <p><blockquote><pre>
     * TruthValue ::= TEXTUAL-CONVENTION
     *     STATUS       current
     *     DESCRIPTION
     *             "Represents a boolean value."
     *     SYNTAX       INTEGER { true(1), false(2) }
     * </pre></blockquote>
     * <p>
     *  从指定的布尔值构造新的<CODE> SnmpInt </CODE>。
     * 此构造函数应用rfc1903规则：<p> <blockquote> <pre> TruthValue :: = TEXTUAL-CONVENTION状态当前描述"表示一个布尔值。
     *  SYNTAX INTEGER {true(1),false(2)} </pre> </blockquote>。
     * 
     * 
     * @param v The initialization value.
     */
    public SnmpInt(boolean v) {
        value = v ? 1 : 2 ;
    }

    // PUBLIC METHODS
    //---------------
    /**
     * Returns the long value of this <CODE>SnmpInt</CODE>.
     * <p>
     *  返回此<CODE> SnmpInt </CODE>的长整型值。
     * 
     * 
     * @return The value.
     */
    public long longValue() {
        return value ;
    }

    /**
     * Converts the integer value to its <CODE>Long</CODE> form.
     * <p>
     *  将整数值转换为其<CODE>长</CODE>形式。
     * 
     * 
     * @return The <CODE>Long</CODE> representation of the value.
     */
    public Long toLong() {
        return new Long(value) ;
    }

    /**
     * Converts the integer value to its integer form.
     * <p>
     *  将整数值转换为其整数形式。
     * 
     * 
     * @return The integer representation of the value.
     */
    public int intValue() {
        return (int) value ;
    }

    /**
     * Converts the integer value to its <CODE>Integer</CODE> form.
     * <p>
     *  将整数值转换为其<CODE>整数</CODE>形式。
     * 
     * 
     * @return The <CODE>Integer</CODE> representation of the value.
     */
    public Integer toInteger() {
        return new Integer((int)value) ;
    }

    /**
     * Converts the integer value to its <CODE>String</CODE> form.
     * <p>
     *  将整数值转换为其<CODE>字符串</CODE>表单。
     * 
     * 
     * @return The <CODE>String</CODE> representation of the value.
     */
    public String toString() {
        return String.valueOf(value) ;
    }

    /**
     * Converts the integer value to its <CODE>SnmpOid</CODE> form.
     * <p>
     *  将整数值转换为其<CODE> SnmpOid </CODE>表单。
     * 
     * 
     * @return The OID representation of the value.
     */
    public SnmpOid toOid() {
        return new SnmpOid(value) ;
    }

    /**
     * Extracts the integer from an index OID and returns its
     * value converted as an <CODE>SnmpOid</CODE>.
     * <p>
     *  从索引OID提取整数,并返回其转换为<CODE> SnmpOid </CODE>的值。
     * 
     * 
     * @param index The index array.
     * @param start The position in the index array.
     * @return The OID representing the integer value.
     * @exception SnmpStatusException There is no integer value
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
     * Scans an index OID, skips the integer value and returns the position
     * of the next value.
     * <p>
     * 扫描索引OID,跳过整数值并返回下一个值的位置。
     * 
     * 
     * @param index The index array.
     * @param start The position in the index array.
     * @return The position of the next value.
     * @exception SnmpStatusException There is no integer value
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
     * Appends an <CODE>SnmpOid</CODE> representing an <CODE>SnmpInt</CODE> to another OID.
     * <p>
     *  将代表<CODE> SnmpInt </CODE>的<CODE> SnmpOid </CODE>附加到另一个OID。
     * 
     * 
     * @param source An OID representing an <CODE>SnmpInt</CODE> value.
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
     * @return The <CODE>SnmpValue</CODE> clone.
     */
    final synchronized public SnmpValue duplicate() {
        return (SnmpValue) clone() ;
    }

    /**
     * Clones the <CODE>SnmpInt</CODE> object, making a copy of its data.
     * <p>
     *  克隆<CODE> SnmpInt </CODE>对象,创建其数据的副本。
     * 
     * 
     * @return The object clone.
     */
    final synchronized public Object clone() {
        SnmpInt  newclone = null ;
        try {
            newclone = (SnmpInt) super.clone() ;
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
        if ((v < Integer.MIN_VALUE) || (v > Integer.MAX_VALUE)) {
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
        if ((v < Integer.MIN_VALUE) || (v > Integer.MAX_VALUE)) {
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
     * 
     */
    final static String name = "Integer32" ;

    /**
     * This is where the value is stored. This long is signed.
     * <p>
     *  这是存储值的位置。这个长签名。
     * 
     * @serial
     */
    protected long value = 0 ;
}
