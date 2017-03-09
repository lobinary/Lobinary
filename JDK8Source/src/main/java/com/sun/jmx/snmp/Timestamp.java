/***** Lobxxx Translate Finished ******/
/*
 *
 * Copyright (c) 2007, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
// Copyright (c) 1995-96 by Cisco Systems, Inc.

package com.sun.jmx.snmp;

// java imports
//
import java.util.Date;

/**
 * This class is used by the {@link com.sun.jmx.snmp.SnmpVarBindList SnmpVarBindList} object.
 * An <CODE>SnmpVarBindList</CODE> time stamp object represents the time stamp when the list was updated
 * with the response variables.
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  此类由{@link com.sun.jmx.snmp.SnmpVarBindList SnmpVarBindList}对象使用。
 *  <CODE> SnmpVarBindList </CODE>时间戳记对象表示使用响应变量更新列表时的时间戳记。
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>。
 * 
 */

public class Timestamp implements java.io.Serializable {
    private static final long serialVersionUID = -242456119149401823L;

    // PRIVATE VARIABLES
    //------------------

    /**
     * The time (in hundreds of a second) since the network management portion of the system
     * was last re-initialized.
     * <p>
     *  自从系统的网络管理部分最后被重新初始化以来的时间(几百秒)。
     * 
     */
    private long sysUpTime ;

    /**
     * A <CODE>long</CODE> representing the current date.
     * <p>
     *  代表当前日期的<CODE>长</CODE>。
     * 
     */
    private long crtime ;

    /**
     * The <CODE>SnmpTimeticks</CODE> object corresponding to the <CODE>TimeStamp</CODE> object.
     * <p>
     *  对应于<CODE> TimeStamp </CODE>对象的<CODE> SnmpTimeticks </CODE>对象。
     * 
     */
    private SnmpTimeticks uptimeCache = null ;


    // CONSTRUCTORS
    //-------------

    /**
     * The default constructor. <CODE>Sysuptime</CODE> is 0.
     * This simply indicates when this object was created.
     * <p>
     *  默认构造函数。 <CODE> Sysuptime </CODE>为0.这只是表示创建此对象的时间。
     * 
     */
    public Timestamp() {
        crtime = System.currentTimeMillis() ;
    }

    /**
     * Creates a <CODE>TimeStamp</CODE> object using the user parameters.
     * <p>
     *  使用用户参数创建<CODE> TimeStamp </CODE>对象。
     * 
     * 
     * @param uptime The time (in hundredths of a second) since the
     * network management portion of the system was last re-initialized.
     * @param when The current time.
     */
    public Timestamp(long uptime, long when) {
        sysUpTime = uptime ;
        crtime = when ;
    }

    /**
     * Creates a <CODE>TimeStamp</CODE> object using the user parameters.
     * <p>
     *  使用用户参数创建<CODE> TimeStamp </CODE>对象。
     * 
     * 
     * @param uptime The time (in hundredths of a second) since the
     * network management portion of the system was last re-initialized.
     */
    public Timestamp(long uptime) {
        sysUpTime = uptime ;
        crtime = System.currentTimeMillis() ;
    }


    // GETTER/SETTER
    //--------------

    /**
     * Gets the <CODE>SnmpTimeticks</CODE> object corresponding to the <CODE>TimeStamp</CODE> object.
     * <p>
     *  获取与<CODE> TimeStamp </CODE>对象相对应的<CODE> SnmpTimeticks </CODE>对象。
     * 
     * 
     * @return The <CODE>SnmpTimeticks</CODE> object.
     */
    final public synchronized SnmpTimeticks getTimeTicks() {
        if (uptimeCache == null)
            uptimeCache = new SnmpTimeticks((int)sysUpTime) ;
        return uptimeCache ;
    }

    /**
     * Gets the time (in hundredths of a second) since the network management portion of the system
     * was last re-initialized.
     * <p>
     *  自从系统的网络管理部分最后重新初始化以来,获取时间(百分之一秒)。
     * 
     * 
     * @return The <CODE>sysUpTime</CODE>.
     */
    final public long getSysUpTime() {
        return sysUpTime ;
    }

    /**
     * Gets the current date.
     * <p>
     *  获取当前日期。
     * 
     * 
     * @return A <CODE>Date</CODE> object representing the current date.
     */
    final public synchronized Date getDate() {
        return new Date(crtime) ;
    }

    /**
     * Gets the current date.
     * <p>
     *  获取当前日期。
     * 
     * 
     * @return A <CODE>long</CODE> representing the current date.
     */
    final public long getDateTime() {
        return crtime ;
    }

    /**
     * Returns a <CODE>String</CODE> representation of the <CODE>TimeStamp</CODE> object.
     * <p>
     *  返回<CODE> TimeStamp </CODE>对象的<CODE> String </CODE>表示形式。
     * 
     * @return A <CODE>String</CODE> representation of the <CODE>TimeStamp</CODE> object.
     */
    final public String toString() {
        StringBuffer buf = new StringBuffer() ;
        buf.append("{SysUpTime = " + SnmpTimeticks.printTimeTicks(sysUpTime)) ;
        buf.append("} {Timestamp = " + getDate().toString() + "}") ;
        return buf.toString() ;
    }
}
