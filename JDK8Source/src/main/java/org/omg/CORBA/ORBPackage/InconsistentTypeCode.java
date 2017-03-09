/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 1999, Oracle and/or its affiliates. All rights reserved.
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


package org.omg.CORBA.ORBPackage;

/**
 * InconsistentTypeCode is thrown when an attempt is made to create a
 * dynamic any with a type code that does not match the particular
 * subclass of <code>DynAny</code>.
 * <p>
 *  当尝试使用与<code> DynAny </code>的特定子类不匹配的类型代码创建动态any时,抛出InconsistentTypeCode。
 * 
 */
public final class InconsistentTypeCode
    extends org.omg.CORBA.UserException {
    /**
     * Constructs an <code>InconsistentTypeCode</code> user exception
     * with no reason message.
     * <p>
     *  构造一个没有原因消息的<code> InconsistentTypeCode </code>用户异常。
     * 
    */
    public InconsistentTypeCode() {
        super();
    }

    /**
    * Constructs an <code>InconsistentTypeCode</code> user exception
    * with the specified reason message.
    * <p>
    *  使用指定的原因消息构造<code> InconsistentTypeCode </code>用户异常。
    * 
    * @param reason The String containing a reason message
    */
    public InconsistentTypeCode(String reason) {
        super(reason);
    }
}
