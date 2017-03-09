/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2012, Oracle and/or its affiliates. All rights reserved.
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
import java.util.Hashtable;


/**
 * This class is an internal class which is used to represent RowStatus
 * codes as defined in RFC 2579.
 *
 * It defines an additional code, <i>unspecified</i>, which is
 * implementation specific, and is used to identify
 * unspecified actions (when for instance the RowStatus variable
 * is not present in the varbind list) or uninitialized values.
 *
 * mibgen does not generate objects of this class but any variable
 * using the RowStatus textual convention can be converted into an
 * object of this class thanks to the
 * <code>EnumRowStatus(Enumerated valueIndex)</code> constructor.
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  这个类是一个内部类,用于表示RFC 2579中定义的RowStatus代码。
 * 
 *  它定义了一个附加的代码<i>未指定</i>,它是实现特定的,用于标识未指定的操作(例如,当RowStatus变量不存在于varbind列表中)或未初始化的值。
 * 
 *  mibgen不会生成此类的对象,但是任何使用RowStatus文本约定的变量都可以通过<code> EnumRowStatus(枚举valueIndex)</code>构造函数转换为此类的对象。
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 * 
 **/

public class EnumRowStatus extends Enumerated implements Serializable {
    private static final long serialVersionUID = 8966519271130162420L;

    /**
     * This value is SNMP Runtime implementation specific, and is used to identify
     * unspecified actions (when for instance the RowStatus variable
     * is not present in the varbind list) or uninitialized values.
     * <p>
     *  此值是特定于SNMP运行时实现的,并用于标识未指定的操作(例如,当RowStatus变量不存在于varbind列表中时)或未初始化的值。
     * 
     */
    public final static int unspecified   = 0;

    /**
     * This value corresponds to the <i>active</i> RowStatus, as defined in
     * RFC 2579 from SMIv2:
     * <ul>
     * <i>active</i> indicates that the conceptual row is available for
     * use by the managed device;
     * </ul>
     * <p>
     *  此值对应于来自SMIv2的RFC2579中定义的<i>活动</i> RowStatus：
     * <ul>
     *  <i>活动</i>指示概念性行可供受管理设备使用;
     * </ul>
     */
    public final static int active        = 1;

    /**
     * This value corresponds to the <i>notInService</i> RowStatus, as
     * defined in RFC 2579 from SMIv2:
     * <ul>
     * <i>notInService</i> indicates that the conceptual
     * row exists in the agent, but is unavailable for use by
     * the managed device; <i>notInService</i> has
     * no implication regarding the internal consistency of
     * the row, availability of resources, or consistency with
     * the current state of the managed device;
     * </ul>
     * <p>
     *  此值对应于来自SMIv2的RFC 2579中定义的<i> notInService </i> RowStatus：
     * <ul>
     * <i> notInService </i>表示概念性行存在于代理中,但不可供受管设备使用; <i> notInService </i>不涉及行的内部一致性,资源的可用性或与受管设备的当前状态的一致性;。
     * </ul>
     * 
     **/
    public final static int notInService  = 2;

    /**
     * This value corresponds to the <i>notReady</i> RowStatus, as defined
     * in RFC 2579 from SMIv2:
     * <ul>
     * <i>notReady</i> indicates that the conceptual row
     * exists in the agent, but is missing information
     * necessary in order to be available for use by the
     * managed device (i.e., one or more required columns in
     * the conceptual row have not been instantiated);
     * </ul>
     * <p>
     *  此值对应于来自SMIv2的RFC 2579中定义的<i> notReady </i> RowStatus：
     * <ul>
     *  <i> notReady </i>表示概念性行存在于代理中,但缺少必要的信息,以便可供受管理设备使用(即概念性行中的一个或多个必需列尚未实例化);
     * </ul>
     */
    public final static int notReady      = 3;

    /**
     * This value corresponds to the <i>createAndGo</i> RowStatus,
     * as defined in RFC 2579 from SMIv2:
     * <ul>
     * <i>createAndGo</i> is supplied by a management
     * station wishing to create a new instance of a
     * conceptual row and to have its status automatically set
     * to active, making it available for use by the managed
     * device;
     * </ul>
     * <p>
     *  此值对应于来自SMIv2的RFC 2579中定义的<i> createAndGo </i> RowStatus：
     * <ul>
     *  <i> createAndGo </i>由希望创建概念行的新实例的管理站提供,并使其状态自动设置为活动,使其可供受管设备使用;
     * </ul>
     */
    public final static int createAndGo   = 4;

    /**
     * This value corresponds to the <i>createAndWait</i> RowStatus,
     * as defined in RFC 2579 from SMIv2:
     * <ul>
     * <i>createAndWait</i> is supplied by a management
     * station wishing to create a new instance of a
     * conceptual row (but not make it available for use by
     * the managed device);
     * </ul>
     * <p>
     *  此值对应于来自SMIv2的RFC 2579中定义的<i> createAndWait </i> RowStatus：
     * <ul>
     *  <i> createAndWait </i>由希望创建概念行的新实例(但不能使其可供受管设备使用)的管理站提供;
     * </ul>
     */
    public final static int createAndWait = 5;

    /**
     * This value corresponds to the <i>destroy</i> RowStatus, as defined in
     * RFC 2579 from SMIv2:
     * <ul>
     * <i>destroy</i> is supplied by a management station
     * wishing to delete all of the instances associated with
     * an existing conceptual row.
     * </ul>
     * <p>
     *  此值对应于来自SMIv2的RFC 2579中定义的<i> destroy </i> RowStatus：
     * <ul>
     *  <i> destroy </i>由希望删除与现有概念行相关联的所有实例的管理站提供。
     * </ul>
     */
    public final static int destroy       = 6;

    /**
     * Build an <code>EnumRowStatus</code> from an <code>int</code>.
     * <p>
     * 从<code> int </code>构建<code> EnumRowStatus </code>。
     * 
     * 
     * @param valueIndex should be either 0 (<i>unspecified</i>), or one of
     *        the values defined in RFC 2579.
     * @exception IllegalArgumentException if the given
     *            <code>valueIndex</code> is not valid.
     **/
    public EnumRowStatus(int valueIndex)
        throws IllegalArgumentException {
        super(valueIndex);
    }

    /**
     * Build an <code>EnumRowStatus</code> from an <code>Enumerated</code>.
     * <p>
     *  从<code>枚举</code>构建<code> EnumRowStatus </code>。
     * 
     * 
     * @param valueIndex should be either 0 (<i>unspecified</i>), or one of
     *        the values defined in RFC 2579.
     * @exception IllegalArgumentException if the given
     *            <code>valueIndex</code> is not valid.
     **/
    public EnumRowStatus(Enumerated valueIndex)
        throws IllegalArgumentException {
        this(valueIndex.intValue());
    }

    /**
     * Build an <code>EnumRowStatus</code> from a <code>long</code>.
     * <p>
     *  从<code> long </code>构建<code> EnumRowStatus </code>。
     * 
     * 
     * @param valueIndex should be either 0 (<i>unspecified</i>), or one of
     *        the values defined in RFC 2579.
     * @exception IllegalArgumentException if the given
     *            <code>valueIndex</code> is not valid.
     **/
    public EnumRowStatus(long valueIndex)
        throws IllegalArgumentException {
        this((int)valueIndex);
    }

    /**
     * Build an <code>EnumRowStatus</code> from an <code>Integer</code>.
     * <p>
     *  从<code> Integer </code>构建<code> EnumRowStatus </code>。
     * 
     * 
     * @param valueIndex should be either 0 (<i>unspecified</i>), or one of
     *        the values defined in RFC 2579.
     * @exception IllegalArgumentException if the given
     *            <code>valueIndex</code> is not valid.
     **/
    public EnumRowStatus(Integer valueIndex)
        throws IllegalArgumentException {
        super(valueIndex);
    }

    /**
     * Build an <code>EnumRowStatus</code> from a <code>Long</code>.
     * <p>
     *  从<code> Long </code>构建<code> EnumRowStatus </code>。
     * 
     * 
     * @param valueIndex should be either 0 (<i>unspecified</i>), or one of
     *        the values defined in RFC 2579.
     * @exception IllegalArgumentException if the given
     *            <code>valueIndex</code> is not valid.
     **/
    public EnumRowStatus(Long valueIndex)
        throws IllegalArgumentException {
        this(valueIndex.longValue());
    }

    /**
     * Build an <code>EnumRowStatus</code> with <i>unspecified</i> value.
     * <p>
     *  使用<i>未指定</i>值构建<code> EnumRowStatus </code>。
     * 
     * 
     **/
    public EnumRowStatus()
        throws IllegalArgumentException {
        this(unspecified);
    }

    /**
     * Build an <code>EnumRowStatus</code> from a <code>String</code>.
     * <p>
     *  从<code> String </code>构建<code> EnumRowStatus </code>。
     * 
     * 
     * @param x should be either "unspecified", or one of
     *        the values defined in RFC 2579 ("active", "notReady", etc...)
     * @exception IllegalArgumentException if the given String
     *            <code>x</code> is not valid.
     **/
    public EnumRowStatus(String x)
        throws IllegalArgumentException {
        super(x);
    }

    /**
     * Build an <code>EnumRowStatus</code> from an <code>SnmpInt</code>.
     * <p>
     *  从<code> SnmpInt </code>构建<code> EnumRowStatus </code>。
     * 
     * 
     * @param valueIndex should be either 0 (<i>unspecified</i>), or one of
     *        the values defined in RFC 2579.
     * @exception IllegalArgumentException if the given
     *            <code>valueIndex</code> is not valid.
     **/
    public EnumRowStatus(SnmpInt valueIndex)
        throws IllegalArgumentException {
        this(valueIndex.intValue());
    }

    /**
     * Build an SnmpValue from this object.
     *
     * <p>
     *  从此对象构建SnmpValue。
     * 
     * 
     * @exception IllegalArgumentException if this object holds an
     *            <i>unspecified</i> value.
     * @return an SnmpInt containing this object value.
     **/
    public SnmpInt toSnmpValue()
        throws IllegalArgumentException {
        if (value == unspecified)
            throw new
        IllegalArgumentException("`unspecified' is not a valid SNMP value.");
        return new SnmpInt(value);
    }

    /**
     * Check that the given <code>value</code> is valid.
     *
     * Valid values are:
     * <ul><li><i>unspecified(0)</i></li>
     *     <li><i>active(1)</i></li>
     *     <li><i>notInService(2)</i></li>
     *     <li><i>notReady(3)</i></li>
     *     <li><i>createAndGo(4)</i></li>
     *     <li><i>createAndWait(5)</i></li>
     *     <li><i>destroy(6)</i></li>
     * </ul>
     *
     * <p>
     *  检查给定的<code>值</code>是否有效。
     * 
     *  有效值为：<ul> <li> <i>未指定(0)</i> </li> <li> <i>活动(1)</i> </li> <li> (2)</i> </li> <li> <i> notReady(3)</i>
     * 
     **/
    static public boolean isValidValue(int value) {
        if (value < 0) return false;
        if (value > 6) return false;
        return true;
    }

    // Documented in Enumerated
    //
    @Override
    protected Hashtable<Integer, String> getIntTable() {
        return EnumRowStatus.getRSIntTable();
    }

    // Documented in Enumerated
    //
    @Override
    protected Hashtable<String, Integer> getStringTable() {
        return  EnumRowStatus.getRSStringTable();
    }

    static Hashtable<Integer, String> getRSIntTable() {
        return intTable ;
    }

    static Hashtable<String, Integer> getRSStringTable() {
        return stringTable ;
    }

    // Initialize the mapping tables.
    //
    final static Hashtable<Integer, String> intTable = new Hashtable<>();
    final static Hashtable<String, Integer> stringTable = new Hashtable<>();
    static  {
        intTable.put(new Integer(0), "unspecified");
        intTable.put(new Integer(3), "notReady");
        intTable.put(new Integer(6), "destroy");
        intTable.put(new Integer(2), "notInService");
        intTable.put(new Integer(5), "createAndWait");
        intTable.put(new Integer(1), "active");
        intTable.put(new Integer(4), "createAndGo");
        stringTable.put("unspecified", new Integer(0));
        stringTable.put("notReady", new Integer(3));
        stringTable.put("destroy", new Integer(6));
        stringTable.put("notInService", new Integer(2));
        stringTable.put("createAndWait", new Integer(5));
        stringTable.put("active", new Integer(1));
        stringTable.put("createAndGo", new Integer(4));
    }


}
