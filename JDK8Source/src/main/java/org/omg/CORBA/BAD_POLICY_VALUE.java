/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2001, Oracle and/or its affiliates. All rights reserved.
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

package org.omg.CORBA;

/**
* Contains the value used to indicate a policy value that is
* incorrect for a valid policy type in a call to the
* <code>create_policy</code> method defined in the ORB class.
*
* <p>
*  包含用于指示在调用ORB类中定义的<code> create_policy </code>方法时对有效策略类型不正确的策略值的值。
* 
*/
public interface BAD_POLICY_VALUE {
    /**
    * The value used to represent a bad policy value error
    * in a <code>PolicyError</code> exception.
    * <p>
    *  用于在<code> PolicyError </code>异常中表示不良策略值错误的值。
    * 
    * @see org.omg.CORBA.PolicyError
    */
    final short value = (short) (3L);
};
