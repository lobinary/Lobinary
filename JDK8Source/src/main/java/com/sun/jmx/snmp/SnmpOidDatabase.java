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

// jmx import
//
import com.sun.jmx.snmp.SnmpOidTable;
import com.sun.jmx.snmp.SnmpOidRecord;
import com.sun.jmx.snmp.SnmpStatusException;

/**
 * Defines the minimal functionality that should be provided by
 * a class containing a set of <CODE>SnmpOidTable</CODE> objects containing metadata definitions for MIB variables.
 * Each <CODE>SnmpOidTable</CODE> should contain information on variables of one MIB.
 * The <CODE>SnmpOidDatabase</CODE> is a "repository" of <CODE>SnmpOidTable</CODE>.
 * It extends the <CODE>SnmpOidTable</CODE> interface in order to provide resolution of the MIB variables.
 * <P>
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  定义包含一组包含MIB变量的元数据定义的<CODE> SnmpOidTable </CODE>对象的类应提供的最小功能。
 * 每个<CODE> SnmpOidTable </CODE>应包含有关一个MIB的变量的信息。
 *  <CODE> SnmpOidDatabase </CODE>是<CODE> SnmpOidTable </CODE>的"存储库"。
 * 它扩展<CODE> SnmpOidTable </CODE>接口,以提供MIB变量的解析。
 * <P>
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 * 
 * @see com.sun.jmx.snmp.SnmpOidTable
 */

public interface SnmpOidDatabase extends SnmpOidTable {

    /**
     * Adds an <CODE>SnmpOidTable</CODE> object in this <CODE>SnmpOidDatabase</CODE>.
     * <p>
     *  在此<CODE> SnmpOidDatabase </CODE>中添加<CODE> SnmpOidTable </CODE>对象。
     * 
     * 
     * @param table The table to add.
     */
    public void add(SnmpOidTable table);

    /**
     * Removes an <CODE>SnmpOidTable</CODE> object from this <CODE>SnmpOidDatabase</CODE>.
     * <p>
     *  从此<CODE> SnmpOidDatabase </CODE>中删除<CODE> SnmpOidTable </CODE>对象。
     * 
     * 
     * @param table The table to be removed.
     */
    public void remove(SnmpOidTable table) throws SnmpStatusException;

    /**
     * Removes all the <CODE>SnmpOidTable</CODE> objects from this <CODE>SnmpOidDatabase</CODE>.
     * <p>
     *  从此<CODE> SnmpOidDatabase </CODE>中删除所有<CODE> SnmpOidTable </CODE>对象。
     * 
     */
    public void removeAll();

    /**
     * Searches for a MIB variable given its logical name and returns an <CODE>SnmpOidRecord</CODE>
     * object containing information on the variable.
     * <p>
     *  根据其逻辑名称搜索MIB变量,并返回包含变量信息的<CODE> SnmpOidRecord </CODE>对象。
     * 
     * 
     * @param name The name of the MIB variable.
     * @return The <CODE>SnmpOidRecord</CODE> object containing information on the variable.
     */
    public SnmpOidRecord resolveVarName(String name) throws SnmpStatusException ;

    /**
     * Searches for a MIB variable given its OID and returns an <CODE>SnmpOidRecord</CODE> object containing
     * information on the variable.
     * <p>
     *  根据其OID搜索MIB变量,并返回包含变量信息的<CODE> SnmpOidRecord </CODE>对象。
     * 
     * 
     * @param oid The OID of the MIB variable.
     * @return The <CODE>SnmpOidRecord</CODE> object containing information on the variable.
     */
    public SnmpOidRecord resolveVarOid(String oid) throws SnmpStatusException;

    /**
     * Returns a list that can be used to traverse all the entries of the <CODE>SnmpOidTable</CODE> objects
     * of this <CODE>SnmpOidDatabase</CODE>.
     * <p>
     *  返回可用于遍历此<CODE> SnmpOidDatabase </CODE>的<CODE> SnmpOidTable </CODE>对象的所有条目的列表。
     * 
     * @return A vector of <CODE>SnmpOidTable</CODE> objects containing all the elements of this <CODE>SnmpOidDatabase</CODE>.
     */
    public Vector<?> getAllEntries() ;
    // We can't specify Vector<SnmpOidTable> because the subinterface SnmpOidTable
    // overrides this method to return Vector<SnmpOidRecord>
}
