/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2007, Oracle and/or its affiliates. All rights reserved.
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


package com.sun.jmx.snmp.agent;



// jmx imports
//
import javax.management.Notification;
import javax.management.ObjectName;

/**
 * Represents a notification emitted when an
 * entry is added or deleted from an SNMP table.
 * <P>
 * The <CODE>SnmpTableEntryNotification</CODE> object contains
 * the reference to the entry added or removed from the table.
 * <P>
 * The list of notifications fired by the <CODE>SnmpMibTable</CODE> is
 * the following:
 * <UL>
 * <LI>A new entry has been added to the SNMP table.
 * <LI>An existing entry has been removed from the SNMP table.
  </UL>
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  表示当从SNMP表添加或删除条目时发出的通知。
 * <P>
 *  <CODE> SnmpTableEntryNotification </CODE>对象包含对从表中添加或删除的条目的引用。
 * <P>
 *  由<CODE> SnmpMibTable </CODE>触发的通知列表如下：
 * <UL>
 *  <LI>已将新条目添加到SNMP表中。 <LI>现有条目已从SNMP表中删除。
 * </UL>
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 */

public class SnmpTableEntryNotification extends Notification {

    /**
     * Creates and initializes a table entry notification object.
     *
     * <p>
     *  创建和初始化表条目通知对象。
     * 
     * 
     * @param type The notification type.
     * @param source The notification producer.
     * @param sequenceNumber The notification sequence number within the
     *                  source object.
     * @param timeStamp The notification emission date.
     * @param entry     The entry object (may be null if the entry is
     *                  registered in the MBeanServer).
     * @param entryName The ObjectName entry object (may be null if the
     *                  entry is not registered in the MBeanServer).
     * @since 1.5
     */
    SnmpTableEntryNotification(String type, Object source,
                               long sequenceNumber, long timeStamp,
                               Object entry, ObjectName entryName) {

        super(type, source, sequenceNumber, timeStamp);
        this.entry = entry;
        this.name  = entryName;
    }

    /**
     * Gets the entry object.
     * May be null if the entry is registered in the MBeanServer, and the
     * MIB is using the generic MetaData (see mibgen).
     *
     * <p>
     *  获取条目对象。如果条目在MBeanServer中注册,并且MIB正在使用通用MetaData(请参阅mibgen),则可以为null。
     * 
     * 
     * @return The entry.
     */
    public Object getEntry() {
        return entry;
    }

    /**
     * Gets the ObjectName of the entry.
     * May be null if the entry is not registered in the MBeanServer.
     *
     * <p>
     *  获取条目的ObjectName。如果条目未在MBeanServer中注册,则可以为null。
     * 
     * 
     * @return The ObjectName of the entry.
     * @since 1.5
     */
    public ObjectName getEntryName() {
        return name;
    }

    // PUBLIC VARIABLES
    //-----------------

    /**
     * Notification type denoting that a new entry has been added to the
     * SNMP table.
     * <BR>The value of this notification type is
     * <CODE>jmx.snmp.table.entry.added</CODE>.
     * <p>
     *  通知类型,表示已将新条目添加到SNMP表中。 <BR>此通知类型的值为<CODE> jmx.snmp.table.entry.added </CODE>。
     * 
     */
    public static final String SNMP_ENTRY_ADDED =
        "jmx.snmp.table.entry.added";

    /**
     * Notification type denoting that an entry has been removed from the
     * SNMP table.
     * <BR>The value of this notification type is
     * <CODE>jmx.snmp.table.entry.removed</CODE>.
     * <p>
     *  表示已从SNMP表中删除条目的通知类型。 <BR>此通知类型的值为<CODE> jmx.snmp.table.entry.removed </CODE>。
     * 
     */
    public static final String SNMP_ENTRY_REMOVED =
        "jmx.snmp.table.entry.removed";

    // PRIVATE VARIABLES
    //------------------

    /**
     * The entry object.
     * <p>
     *  条目对象。
     * 
     * 
     * @serial
     */
    private final Object entry;

    /**
     * The entry name.
     * <p>
     *  条目名称。
     * 
     * @serial
     * @since 1.5
     */
    private final ObjectName name;

    // Ensure compatibility
    //
    private static final long serialVersionUID = 5832592016227890252L;
}
