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
 * Represents an SNMP null value.
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  表示SNMP空值。 <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 */

public class SnmpNull extends SnmpValue {
    private static final long serialVersionUID = 1783782515994279177L;

    // CONSTRUCTORS
    //-------------
    /**
     * Constructs a new <CODE>SnmpNull</CODE>.
     * <p>
     *  构造新的<CODE> SnmpNull </CODE>。
     * 
     */
    public SnmpNull() {
        tag = NullTag ;
    }

    /**
     * Constructs a new <CODE>SnmpNull</CODE>.
     * <BR>For mibgen private use only.
     * <p>
     *  构造新的<CODE> SnmpNull </CODE>。 <BR>仅供mibgen私人使用。
     * 
     */
    public SnmpNull(String dummy) {
        this();
    }

    /**
     * Constructs a new <CODE>SnmpNull</CODE> from the specified tag value.
     * <p>
     *  从指定的标签值构造新的<CODE> SnmpNull </CODE>。
     * 
     * 
     * @param t The initialization value.
     */
    public SnmpNull(int t) {
        tag = t ;
    }

    // PUBLIC METHODS
    //---------------
    /**
     * Returns the tag value of this <CODE>SnmpNull</CODE>.
     * <p>
     *  返回此<CODE> SnmpNull </CODE>的标签值。
     * 
     * 
     * @return The value.
     */
    public int getTag() {
        return tag ;
    }

    /**
     * Converts the <CODE>NULL</CODE> value to its ASN.1 <CODE>String</CODE> form.
     * When the tag is not the universal one, it is preprended
     * to the <CODE>String</CODE> form.
     * <p>
     *  将<CODE> NULL </CODE>值转换为其ASN.1 <CODE> String </CODE>表单。当标记不是通用标记时,它会预先转到<CODE> String </CODE>表单。
     * 
     * 
     * @return The <CODE>String</CODE> representation of the value.
     */
    public String toString() {
        String result = "" ;
        if (tag != 5) {
            result += "[" + tag + "] " ;
        }
        result += "NULL" ;
        switch(tag) {
        case errNoSuchObjectTag :
            result += " (noSuchObject)" ;
            break ;

        case errNoSuchInstanceTag :
            result += " (noSuchInstance)" ;
            break ;

        case errEndOfMibViewTag :
            result += " (endOfMibView)" ;
            break ;
        }
        return result ;
    }

    /**
     * Converts the <CODE>NULL</CODE> value to its <CODE>SnmpOid</CODE> form.
     * Normally, a <CODE>NULL</CODE> value cannot be used as an index value,
     * this method triggers an exception.
     * <p>
     *  将<CODE> NULL </CODE>值转换为其<CODE> SnmpOid </CODE>表单。通常,<CODE> NULL </CODE>值不能用作索引值,此方法触发异常。
     * 
     * 
     * @return The OID representation of the value.
     */
    public SnmpOid toOid() {
        throw new IllegalArgumentException() ;
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
     * Clones the <CODE>SnmpNull</CODE> object, making a copy of its data.
     * <p>
     *  克隆<CODE> SnmpNull </CODE>对象,创建其数据的副本。
     * 
     * 
     * @return The object clone.
     */
    final synchronized public Object clone() {
        SnmpNull  newclone = null ;
        try {
            newclone = (SnmpNull) super.clone() ;
            newclone.tag = tag ;
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

    /**
     * Checks if this <CODE>SnmpNull</CODE> object corresponds to a <CODE>noSuchObject</CODE> value.
     * <p>
     *  检查此<CODE> SnmpNull </CODE>对象是否对应于<CODE> noSuchObject </CODE>值。
     * 
     * 
     * @return <CODE>true</CODE> if the tag equals {@link com.sun.jmx.snmp.SnmpDataTypeEnums#errNoSuchObjectTag},
     * <CODE>false</CODE> otherwise.
     */
    public boolean isNoSuchObjectValue() {
        return (tag == SnmpDataTypeEnums.errNoSuchObjectTag);
    }

    /**
     * Checks if this <CODE>SnmpNull</CODE> object corresponds to a <CODE>noSuchInstance</CODE> value.
     * <p>
     *  检查此<CODE> SnmpNull </CODE>对象是否对应于<CODE> noSuchInstance </CODE>值。
     * 
     * 
     * @return <CODE>true</CODE> if the tag equals {@link com.sun.jmx.snmp.SnmpDataTypeEnums#errNoSuchInstanceTag},
     * <CODE>false</CODE> otherwise.
     */
    public boolean isNoSuchInstanceValue() {
        return (tag == SnmpDataTypeEnums.errNoSuchInstanceTag);
    }

    /**
     * Checks if this <CODE>SnmpNull</CODE> object corresponds to an <CODE>endOfMibView</CODE> value.
     * <p>
     *  检查此<CODE> SnmpNull </CODE>对象是否对应于<CODE> endOfMibView </CODE>值。
     * 
     * 
     * @return <CODE>true</CODE> if the tag equals {@link com.sun.jmx.snmp.SnmpDataTypeEnums#errEndOfMibViewTag},
     * <CODE>false</CODE> otherwise.
     */
    public boolean isEndOfMibViewValue() {
        return (tag == SnmpDataTypeEnums.errEndOfMibViewTag);
    }

    // VARIABLES
    //----------
    /**
     * Name of the type.
     * <p>
     *  类型的名称。
     * 
     */
    final static String name = "Null" ;

    /**
     * This is the tag of the NULL value. By default, it is the universal tag value.
     * <p>
     *  这是NULL值的标记。默认情况下,它是通用标记值。
     */
    private int tag = 5 ;
}
