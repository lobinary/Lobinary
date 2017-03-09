/***** Lobxxx Translate Finished ******/
/*
 *
 * Copyright (c) 2007, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
// Copyright (c) 1995-96 by Cisco Systems, Inc.

package com.sun.jmx.snmp;

// java import
//
import java.util.Vector;


/**
 * Defines the minimum functionality that should be provided by
 * a class containing metadata definitions for variables of a MIB.
 * A name can be resolved against a table of MIB variables.
 * Each entry in the table is an <CODE>SnmpOidRecord</CODE> object that contains a name, a dot-separated OID string,
 * and the corresponding SMI type of the variable.
 * <P>
 * If you need to load a specific <CODE>SnmpOidTable</CODE>, just call the static method
 * {@link com.sun.jmx.snmp.SnmpOid#setSnmpOidTable <CODE>SnmpOid.setSnmpOidTable(<I>myOidTable</I>)</CODE>}.
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  定义包含MIB变量的元数据定义的类应提供的最低功能。可以针对MIB变量的表来解析名称。
 * 表中的每个条目都是一个<CODE> SnmpOidRecord </CODE>对象,其中包含名称,点分隔的OID字符串和变量的相应SMI类型。
 * <P>
 *  如果需要加载特定的<CODE> SnmpOidTable </CODE>,只需调用静态方法{@link com.sun.jmx.snmp.SnmpOid#setSnmpOidTable <CODE> SnmpOid.setSnmpOidTable(<I> myOidTable </I> )</CODE>}
 * 。
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 * 
 * @see com.sun.jmx.snmp.SnmpOidRecord
 *
 *
 */


public interface SnmpOidTable {

    /**
     * Searches for a MIB variable given its logical name and returns an {@link com.sun.jmx.snmp.SnmpOidRecord} object
     * containing information on the variable.
     *
     * <p>
     *  根据其逻辑名称搜索MIB变量,并返回包含变量信息的{@link com.sun.jmx.snmp.SnmpOidRecord}对象。
     * 
     * 
     * @param name The name of the MIB variable.
     * @return The <CODE>SnmpOidRecord</CODE> object containing information on the variable.
     * @exception SnmpStatusException If the variable is not found.
     */
    public SnmpOidRecord resolveVarName(String name)
        throws SnmpStatusException;


    /**
     * Searches for a MIB variable given its OID and returns an {@link com.sun.jmx.snmp.SnmpOidRecord} object
     * containing information on the variable.
     *
     * <p>
     *  根据其OID搜索MIB变量,并返回包含变量信息的{@link com.sun.jmx.snmp.SnmpOidRecord}对象。
     * 
     * 
     * @param oid The OID of the MIB variable.
     * @return The <CODE>SnmpOidRecord</CODE> object containing information
     *         on the variable.
     * @exception SnmpStatusException If the variable is not found.
     */
    public SnmpOidRecord resolveVarOid(String oid)
        throws SnmpStatusException;

    /**
     * Returns a list that can be used to traverse all the entries this <CODE>SnmpOidTable</CODE>.
     * <p>
     *  返回可用于遍历此<CODE> SnmpOidTable </CODE>中的所有条目的列表。
     * 
     * @return A Vector of {@link com.sun.jmx.snmp.SnmpOidRecord} objects.
     */
    public Vector<?> getAllEntries();
}
