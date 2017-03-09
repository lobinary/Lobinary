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
/*
 * Licensed Materials - Property of IBM
 * RMI-IIOP v1.0
 * Copyright IBM Corp. 1998 1999  All Rights Reserved
 *
 * <p>
 *  许可的材料 -  IBM RMI-IIOP v1.0的属性版权所有IBM Corp. 1998 1999保留所有权利
 * 
 */

package org.omg.CORBA.portable;
/**
 * The org.omg.CORBA.portable.UnknownException is used for reporting
 * unknown exceptions between ties and ORBs and between ORBs and stubs.
 * It provides a Java representation of an UNKNOWN system exception
 * that has an UnknownExceptionInfo service context.
 * If the CORBA system exception org.omg.CORBA.portable.UnknownException
 * is thrown, then the stub does one of the following:
 * (1) Translates it to org.omg.CORBA.UNKNOWN.
 * (2) Translates it to the nested exception that the UnknownException contains.
 * (3) Passes it on directly to the user.
 * <p>
 *  org.omg.CORBA.portable.UnknownException用于报告绑定和ORB之间以及ORB和存根之间的未知异常。
 * 它提供具有UnknownExceptionInfo服务上下文的UNKNOWN系统异常的Java表示形式。
 * 如果抛出CORBA系统异常org.omg.CORBA.portable.UnknownException,那么存根将执行以下操作之一：(1)将其转换为org.omg.CORBA.UNKNOWN。
 *  (2)将其转换为UnknownException包含的嵌套异常。 (3)将其直接传递给用户。
 * 
 */
public class UnknownException extends org.omg.CORBA.SystemException {
    /**
     * A throwable--the original exception that was wrapped in a CORBA
     * UnknownException.
     * <p>
     *  throwable  - 被包装在CORBA UnknownException中的原始异常。
     * 
     */
    public Throwable originalEx;
    /**
     * Constructs an UnknownException object.
     * <p>
     *  构造一个UnknownException对象。
     * 
     * @param ex a Throwable object--to be wrapped in this exception.
     */
    public UnknownException(Throwable ex) {
        super("", 0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
        originalEx = ex;
    }
}
