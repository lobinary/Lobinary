/***** Lobxxx Translate Finished ******/
/*
 *
 * Copyright (c) 2007, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
// Copyright (c) 1995-96 by Cisco Systems, Inc.

package com.sun.jmx.snmp;

/**
 * Represents an entry of an {@link com.sun.jmx.snmp.SnmpOidTable SnmpOidTable}. It contains the name of the MIB variable,
 * the corresponding OID and its type.
 * The type is represented using one of the following:
 * <UL>
 *<LI>"C"       for <CODE>Counter32</CODE>
 *<LI>"C64"     for <CODE>Counter64</CODE>
 *<LI>"EN"      for <CODE>Table Entry</CODE>
 *<LI>"G"       for <CODE>Gauge32</CODE>
 *<LI>"I"       for <CODE>Integer32</CODE>
 *<LI>"ID"      for <CODE>OBJECT-IDENTITY</CODE>
 *<LI>"IP"      for <CODE>IpAddress</CODE>
 *<LI>"NT"      for <CODE>NOTIFICATION-TYPE</CODE>
 *<LI>"NU"      for <CODE>Null</CODE>
 *<LI>"O"       for <CODE>Opaque</CODE>
 *<LI>"OI"      for <CODE>Object Identifier</CODE>
 *<LI>"S"       for <CODE>String</CODE>
 *<LI>"T"       for <CODE>TimeTicks</CODE>
 *<LI>"TA"      for <CODE>Table</CODE>
 *<LI>"U"       for <CODE>Unsigned32</CODE>
 *</UL>
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  表示{@link com.sun.jmx.snmp.SnmpOidTable SnmpOidTable}的条目。它包含MIB变量的名称,对应的OID及其类型。类型使用以下之一表示：
 * <UL>
 *  LI>"C"代表<CODE>计数器32 </CODE> LI>"C64"代表<CODE>计数器64 </CODE> LI>"EN"代表< CODE> Gauge32 </CODE> LI>"I"代表<CODE>
 *  Integer32 </CODE> LI>"ID"代表<CODE> OBJECT-IDENTITY </CODE> LI> > LI>"NT"代表<CODE> NOTIFICATION-TYPE </CODE>
 *  LI>"NU"代表<CODE> Null </CODE> LI>"O"代表<CODE>不透明</CODE> LI> <CODE>对象标识符</CODE> LI>"S"代表<CODE>字符串</CODE>
 *  LI>"T"代表<CODE> TimeTicks </CODE> LI> CODE> LI>"U"代表<CODE> Unsigned32 </CODE>。
 * /UL>
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 * 
 * @see com.sun.jmx.snmp.SnmpOidTable
 */

public class SnmpOidRecord {

    /**
     * Creates an <CODE>SnmpOidRecord</CODE> with the specified MIB variable
     * name, OID and type.
     * <p>
     *  使用指定的MIB变量名称,OID和类型创建<CODE> SnmpOidRecord </CODE>。
     * 
     * 
     * @param name The logical name of the MIB variable.
     * @param oid The OID of the MIB variable.
     * @param type The type of the MIB variable.
     */
    public SnmpOidRecord(String name, String oid, String type) {
        this.name = name;
        this.oid = oid;
        this.type = type;
    }

    /**
     * Gets the logical name of the MIB variable.
     * <p>
     *  获取MIB变量的逻辑名称。
     * 
     * 
     * @return The MIB variable name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the OID of the MIB variable.
     * <p>
     *  获取MIB变量的OID。
     * 
     * 
     * @return The MIB variable OID.
     */
    public String getOid() {
        return oid;
    }

    /**
     * Gets the type of the MIB variable.
     * <p>
     *  获取MIB变量的类型。
     * 
     * 
     * @return The MIB variable type.
     */
    public String getType() {
        return type;
    }

    // PRIVATE VARIABLES

    /**
     * The MIB variable name.
     * <p>
     *  MIB变量名称。
     * 
     */
    private String name;
    /**
     * The MIB variable OID.
     * <p>
     *  MIB变量OID。
     * 
     */
    private String oid;
    /**
     * The MIB variable type.
     * <p>
     *  MIB变量类型。
     */
    private String type;
}
