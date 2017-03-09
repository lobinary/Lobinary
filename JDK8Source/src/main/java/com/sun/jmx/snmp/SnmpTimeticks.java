/***** Lobxxx Translate Finished ******/
/*
 *
 * Copyright (c) 2007, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
// Copyright (c) 1995-96 by Cisco Systems, Inc.

package com.sun.jmx.snmp;

/**
 * Contains an <CODE>SnmpTimeTick</CODE> value which
 * has units of 1/100th of a second.
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 *
 * <p>
 *  包含<CODE> SnmpTimeTick </CODE>值,单位为1/100秒。
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 */

public class SnmpTimeticks extends SnmpUnsignedInt {

    // CONSTRUCTORS
    //-------------
    /**
     * Constructs a new <CODE>SnmpTimeticks</CODE> from the specified
     * integer value.
     * <p>
     *  从指定的整数值构造新的<CODE> SnmpTimeticks </CODE>。
     * 
     * 
     * @param v The initialization value.
     * @exception IllegalArgumentException The specified value is negative.
     */
    public SnmpTimeticks(int v) throws IllegalArgumentException {
        super(v) ;
    }

    /**
     * Constructs a new <CODE>SnmpTimeticks</CODE> from the specified
     * <CODE>Integer</CODE> value.
     * <p>
     *  从指定的<CODE> Integer </CODE>值构造新的<CODE> SnmpTimeticks </CODE>。
     * 
     * 
     * @param v The initialization value.
     * @exception IllegalArgumentException The specified value is negative.
     */
    public SnmpTimeticks(Integer v) throws IllegalArgumentException {
        super(v) ;
    }

    /**
     * Constructs a new <CODE>SnmpTimeticks</CODE> from the specified long
     * value.
     * <p>If the specified value is greater than {@link
     * SnmpUnsignedInt#MAX_VALUE SnmpUnsignedInt.MAX_VALUE}, the SnmpTimeTicks
     * will be initialized with <code>v%(SnmpUnsignedInt.MAX_VALUE+1)</code>.
     * <p>
     *  从指定的长整型值构造新的<CODE> SnmpTimeticks </CODE>。
     *  <p>如果指定的值大于{@link SnmpUnsignedInt#MAX_VALUE SnmpUnsignedInt.MAX_VALUE},则SnmpTimeTicks将使用<code> v％(Sn
     * mpUnsignedInt.MAX_VALUE + 1)</code>进行初始化。
     *  从指定的长整型值构造新的<CODE> SnmpTimeticks </CODE>。
     * 
     * 
     * @param v The initialization value.
     * @exception IllegalArgumentException if the specified value is negative.
     */
    public SnmpTimeticks(long v) throws IllegalArgumentException {
        super(((v>0)?v&SnmpUnsignedInt.MAX_VALUE:v)) ;
    }

    /**
     * Constructs a new <CODE>SnmpTimeticks</CODE> from the specified
     * <CODE>Long</CODE> value.
     * <p>If the specified value is greater than {@link
     * SnmpUnsignedInt#MAX_VALUE SnmpUnsignedInt.MAX_VALUE}, the SnmpTimeTicks
     * will be initialized with <code>v%(SnmpUnsignedInt.MAX_VALUE+1)</code>.
     * <p>
     *  从指定的<CODE>长</CODE>值构造新的<CODE> SnmpTimeticks </CODE>。
     *  <p>如果指定的值大于{@link SnmpUnsignedInt#MAX_VALUE SnmpUnsignedInt.MAX_VALUE},则SnmpTimeTicks将使用<code> v％(Sn
     * mpUnsignedInt.MAX_VALUE + 1)</code>进行初始化。
     *  从指定的<CODE>长</CODE>值构造新的<CODE> SnmpTimeticks </CODE>。
     * 
     * 
     * @param v The initialization value.
     * @exception IllegalArgumentException if the specified value is negative.
     */
    public SnmpTimeticks(Long v) throws IllegalArgumentException {
        this(v.longValue()) ;
    }

    // PUBLIC METHODS
    //---------------
    /**
     * Parses the specified long value with time units and
     * returns a <CODE>String</CODE> of the form <CODE>d days hh:mm:ss</CODE>.
     * <p>
     *  以时间单位解析指定的长整型值,并返回<CODE> d days hh：mm：ss </CODE>形式的<CODE>字符串</CODE>。
     * 
     * 
     * @param timeticks The value to be parsed.
     * @return The <CODE>String</CODE> representation of the value.
     */
    final static public String printTimeTicks(long timeticks) {
        int seconds, minutes, hours, days;
        StringBuffer buf = new StringBuffer() ;

        timeticks /= 100;
        days = (int)(timeticks / (60 * 60 * 24));
        timeticks %= (60 * 60 * 24);

        hours = (int)(timeticks / (60 * 60)) ;
        timeticks %= (60 * 60);

        minutes = (int)(timeticks / 60) ;
        seconds = (int)(timeticks % 60) ;

        if (days == 0) {
            buf.append(hours + ":" + minutes + ":" + seconds) ;
            return buf.toString() ;
        }
        if (days == 1) {
            buf.append("1 day ") ;
        } else {
            buf.append(days + " days ") ;
        }
        buf.append(hours + ":" + minutes + ":" + seconds) ;
        return buf.toString() ;
    }

    /**
     * Converts the timeticks value to its <CODE>String</CODE> form.
     * The format of the returned <CODE>String</CODE> is <CODE>d days hh:mm:ss</CODE>.
     * <BR>Note: this method simply calls the {@link #printTimeTicks printTimeTicks} method.
     * <p>
     *  将时间表值转换为其<CODE>字符串</CODE>表单。返回的<CODE> String </CODE>的格式为<CODE> d days hh：mm：ss </CODE>。
     *  <BR>注意：此方法仅调用{@link #printTimeTicks printTimeTicks}方法。
     * 
     * 
     * @return The <CODE>String</CODE> representation of the value.
     */
    final public String toString() {
        return printTimeTicks(value) ;
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
        return name;
    }

    // VARIABLES
    //----------
    /**
     * Name of the type.
     * <p>
     *  类型的名称。
     */
    final static String name = "TimeTicks" ;
    static final private long serialVersionUID = -5486435222360030630L;
}
