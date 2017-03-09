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



import java.io.Serializable;

/**
 * Is an abstract representation of an SNMP Value.
 * All classes provided for dealing with SNMP types should derive from this
 * class.
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  是SNMP值的抽象表示。为处理SNMP类型而提供的所有类都应该从这个类派生。
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 */

public abstract class SnmpValue implements Cloneable, Serializable, SnmpDataTypeEnums {

    /**
     * Returns a <CODE>String</CODE> form containing ASN.1 tagging information.
     * <p>
     *  返回包含ASN.1标记信息的<CODE>字符串</CODE>表单。
     * 
     * 
     * @return The <CODE>String</CODE> form.
     */
    public String toAsn1String() {
        return "[" + getTypeName() + "] " + toString();
    }

    /**
     * Returns the value encoded as an OID.
     * The method is particularly useful when dealing with indexed table made of
     * several SNMP variables.
     * <p>
     *  返回以OID编码的值。该方法在处理由几个SNMP变量组成的索引表时特别有用。
     * 
     * 
     * @return The value encoded as an OID.
     */
    public abstract SnmpOid toOid() ;

    /**
     * Returns a textual description of the object.
     * <p>
     *  返回对象的文本描述。
     * 
     * 
     * @return ASN.1 textual description.
     */
    public abstract String getTypeName() ;

    /**
     * Same as clone, but you cannot perform cloning using this object because
     * clone is protected. This method should call <CODE>clone()</CODE>.
     * <p>
     *  与克隆相同,但是您无法使用此对象执行克隆,因为克隆是受保护的。此方法应调用<CODE> clone()</CODE>。
     * 
     * 
     * @return The <CODE>SnmpValue</CODE> clone.
     */
    public abstract SnmpValue duplicate() ;

    /**
     * This method returns <CODE>false</CODE> by default and is redefined
     * in the {@link com.sun.jmx.snmp.SnmpNull} class.
     * <p>
     *  此方法默认返回<CODE> false </CODE>,并在{@link com.sun.jmx.snmp.SnmpNull}类中重新定义。
     * 
     */
    public boolean isNoSuchObjectValue() {
        return false;
    }

    /**
     * This method returns <CODE>false</CODE> by default and is redefined
     * in the {@link com.sun.jmx.snmp.SnmpNull} class.
     * <p>
     *  此方法默认返回<CODE> false </CODE>,并在{@link com.sun.jmx.snmp.SnmpNull}类中重新定义。
     * 
     */
    public boolean isNoSuchInstanceValue() {
        return false;
    }

    /**
     * This method returns <CODE>false</CODE> by default and is redefined
     * in the {@link com.sun.jmx.snmp.SnmpNull} class.
     * <p>
     *  此方法默认返回<CODE> false </CODE>,并在{@link com.sun.jmx.snmp.SnmpNull}类中重新定义。
     */
    public boolean isEndOfMibViewValue() {
        return false;
    }
}
