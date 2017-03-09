/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2000, Oracle and/or its affiliates. All rights reserved.
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
* A user exception thrown when a policy error occurs.  A <code>PolicyError</code>
* exception may include one of the following policy error reason codes
* defined in the org.omg.CORBA package: BAD_POLICY, BAD_POLICY_TYPE,
* BAD_POLICY_VALUE, UNSUPPORTED_POLICY, UNSUPPORTED_POLICY_VALUE.
* <p>
*  发生策略错误时抛出的用户异常。
*  <code> PolicyError </code>异常可能包括org.omg.CORBA包中定义的以下策略错误原因代码之一：BAD_POLICY,BAD_POLICY_TYPE,BAD_POLICY
* _VALUE,UNSUPPORTED_POLICY,UNSUPPORTED_POLICY_VALUE。
*  发生策略错误时抛出的用户异常。
* 
*/

public final class PolicyError extends org.omg.CORBA.UserException {

    /**
     * The reason for the <code>PolicyError</code> exception being thrown.
     * <p>
     *  抛出<code> PolicyError </code>异常的原因。
     * 
     * 
     * @serial
     */
    public short reason;

    /**
     * Constructs a default <code>PolicyError</code> user exception
     * with no reason code and an empty reason detail message.
     * <p>
     *  构造默认的<code> PolicyError </code>用户异常,没有原因代码和空原因详细消息。
     * 
     */
    public PolicyError() {
        super();
    }

    /**
     * Constructs a <code>PolicyError</code> user exception
     * initialized with the given reason code and an empty reason detail message.
     * <p>
     *  构造使用给定原因代码和空原因详细消息初始化的<code> PolicyError </code>用户异常。
     * 
     * 
     * @param __reason the reason code.
     */
    public PolicyError(short __reason) {
        super();
        reason = __reason;
    }

    /**
     * Constructs a <code>PolicyError</code> user exception
     * initialized with the given reason detail message and reason code.
     * <p>
     *  构造使用给定原因详细消息和原因代码初始化的<code> PolicyError </code>用户异常。
     * 
     * @param reason_string the reason detail message.
     * @param __reason the reason code.
     */
    public PolicyError(String reason_string, short __reason) {
        super(reason_string);
        reason = __reason;
    }
}
