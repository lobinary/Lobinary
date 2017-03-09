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
import java.util.Objects;
import java.util.Vector;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.Hashtable;

//RI import
import static com.sun.jmx.defaults.JmxProperties.SNMP_LOGGER;

/**
 * Contains metadata definitions for MIB variables.
 * A name can be resolved against a table of MIB variables.
 * Each entry in the table is an <CODE>SnmpOidRecord</CODE> object that contains a name, a dot-separated OID string,
 * and the corresponding SMI type of the variable.
 * <P>
 * If you need to load a specific <CODE>SnmpOidTable</CODE>, just call the static method
 * {@link com.sun.jmx.snmp.SnmpOid#setSnmpOidTable <CODE>SnmpOid.setSnmpOidTable(<I>myOidTable</I>)</CODE>}.
 * <P>
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  包含MIB变量的元数据定义。可以针对MIB变量的表来解析名称。
 * 表中的每个条目都是一个<CODE> SnmpOidRecord </CODE>对象,其中包含名称,点分隔的OID字符串和变量的相应SMI类型。
 * <P>
 *  如果需要加载特定的<CODE> SnmpOidTable </CODE>,只需调用静态方法{@link com.sun.jmx.snmp.SnmpOid#setSnmpOidTable <CODE> SnmpOid.setSnmpOidTable(<I> myOidTable </I> )</CODE>}
 * 。
 * <P>
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 * 
 * @see com.sun.jmx.snmp.SnmpOidRecord
 *
 */

public class SnmpOidTableSupport implements SnmpOidTable {

    /**
     * Creates an <CODE>SnmpOidTableSupport</CODE> with the specified name.
     * This name identifies the MIB to which belong  the MIB variables contained
     * in this <CODE>SnmpOidTableSupport</CODE> object.
     * <p>
     *  创建具有指定名称的<CODE> SnmpOidTableSupport </CODE>。
     * 此名称标识属于此<CODE> SnmpOidTableSupport </CODE>对象中包含的MIB变量的MIB。
     * 
     * 
     * @param name The OID table name.
     */
    public SnmpOidTableSupport(String name) {
        myName=name;
    }

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
    @Override
    public SnmpOidRecord resolveVarName(String name) throws SnmpStatusException {

        SnmpOidRecord var  = oidStore.get(name) ;
        if (var != null) {
            return var;
        } else {
            throw new SnmpStatusException("Variable name <" + name + "> not found in Oid repository") ;
        }
    }

    /**
     * Searches for a MIB variable given its OID and returns an {@link com.sun.jmx.snmp.SnmpOidRecord} object
     * containing information on the variable.
     *
     * <p>
     *  根据其OID搜索MIB变量,并返回包含变量信息的{@link com.sun.jmx.snmp.SnmpOidRecord}对象。
     * 
     * 
     * @param oid The OID of the MIB variable.
     * @return The <CODE>SnmpOidRecord</CODE> object containing information on the variable.
     * @exception SnmpStatusException If the variable is not found.
     */
    @Override
    public SnmpOidRecord resolveVarOid(String oid) throws SnmpStatusException {

        // Try to see if the variable name is actually an OID to resolve.
        //
        int index = oid.indexOf('.') ;
        if (index < 0) {
            throw new SnmpStatusException("Variable oid <" + oid + "> not found in Oid repository") ;
        }
        if (index == 0) {
            // The oid starts with a '.' ala CMU.
            //
            oid= oid.substring(1, oid.length());
        }

        // Go through the oidStore ... Good luck !
        //
        for(Enumeration<SnmpOidRecord> list= oidStore.elements(); list.hasMoreElements(); ) {
            SnmpOidRecord element= list.nextElement();
            if (element.getOid().equals(oid))
                return element;
        }

        throw new SnmpStatusException("Variable oid <" + oid + "> not found in Oid repository") ;
    }

    /**
     * Returns a list that can be used to traverse all the entries in this <CODE>SnmpOidTable</CODE>.
     * <p>
     *  返回可用于遍历此<CODE> SnmpOidTable </CODE>中的所有条目的列表。
     * 
     * 
     * @return A vector of {@link com.sun.jmx.snmp.SnmpOidRecord} objects.
     */
    @Override
    public Vector<SnmpOidRecord> getAllEntries() {

        Vector<SnmpOidRecord> elementsVector = new Vector<>();
        // get the locally defined elements ...
        for (Enumeration<SnmpOidRecord> e = oidStore.elements();
             e.hasMoreElements(); ) {
            elementsVector.addElement(e.nextElement());
        }
        return elementsVector ;
    }

    /**
     * Loads a list of variables into the storage area,
     * which is kept in memory. If you have new MIB variables,
     * this method can be called to load them.
     * <p>
     *  将变量列表加载到存储区域中,该区域保存在内存中。如果有新的MIB变量,可以调用此方法来加载它们。
     * 
     * 
     * @param mibs The list of variables to load.
     */
    public synchronized void loadMib(SnmpOidRecord[] mibs) {
        try {
            for (int i = 0; ; i++) {
                SnmpOidRecord s = mibs[i] ;
                if (SNMP_LOGGER.isLoggable(Level.FINER)) {
                    SNMP_LOGGER.logp(Level.FINER,
                            SnmpOidTableSupport.class.getName(),
                            "loadMib", "Load " + s.getName());
                }
                oidStore.put(s.getName(), s) ;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }

    /**
     * Checks if the specified <CODE>Object</CODE> is equal to this <CODE>SnmpOidTableSupport</CODE>.
     * <p>
     * 检查指定的<CODE>对象</CODE>是否等于此<CODE> SnmpOidTableSupport </CODE>。
     * 
     * 
     * @param object The <CODE>Object</CODE> to be compared.
     * @return <CODE>true</CODE> if <CODE>object</CODE> is an <CODE>SnmpOidTableSupport</CODE> instance and equals to this,
     * <CODE>false</CODE> otherwise.
     */
    @Override
    public boolean equals(Object object) {

        if (!(object instanceof SnmpOidTableSupport)) {
            return false;
        }
        SnmpOidTableSupport val = (SnmpOidTableSupport) object;
        return myName.equals(val.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(myName);
    }

    /**
     * Returns the name identifying this <CODE>SnmpOidTableSupport</CODE> object.
     * <p>
     *  返回标识此<CODE> SnmpOidTableSupport </CODE>对象的名称。
     * 
     * 
     * @return The OID table name.
     */
    public String getName() {
        return myName;
    }
    /*
     * ------------------------------------------
     *   PRIVATE METHODS
     * ------------------------------------------
     * <p>
     *  ------------------------------------------私有方法------ ------------------------------------
     */



    private Hashtable<String, SnmpOidRecord> oidStore = new Hashtable<>();
    private String myName;
}
