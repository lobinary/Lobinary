/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 1999, Oracle and/or its affiliates. All rights reserved.
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

package org.omg.CORBA.TypeCodePackage;

/**
 * Provides the <code>TypeCode</code> operations <code>member_name()</code>,
 * <code>member_type()</code>, and <code>member_label</code>.
 * These methods
 * raise <code>Bounds</code> when the index parameter is greater than or equal
 * to the number of members constituting the type.
 *
 * <p>
 *  提供<code> TypeCode </code>操作<code> member_name()</code>,<code> member_type()</code>和<code> member_lab
 * el </code>。
 * 当索引参数大于或等于构成类型的成员数量时,这些方法会产生<code> Bounds </code>。
 * 
 * 
 * @since   JDK1.2
 */

public final class Bounds extends org.omg.CORBA.UserException {

    /**
     * Constructs a <code>Bounds</code> exception with no reason message.
     * <p>
     *  构造一个没有原因消息的<code> Bounds </code>异常。
     * 
     */
    public Bounds() {
        super();
    }

    /**
     * Constructs a <code>Bounds</code> exception with the specified
     * reason message.
     * <p>
     *  使用指定的原因消息构造<code> Bounds </code>异常。
     * 
     * @param reason the String containing a reason message
     */
    public Bounds(String reason) {
        super(reason);
    }
}
