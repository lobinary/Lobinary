/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2006, Oracle and/or its affiliates. All rights reserved.
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
 * The CORBA <code>WrongTransaction</code> user-defined exception.
 * This exception is thrown only by the methods
 * <code>Request.get_response</code>
 * and <code>ORB.get_next_response</code> when they are invoked
 * from a transaction scope that is different from the one in
 * which the client originally sent the request.
 * See the OMG Transaction Service Specification for details.
 *
 * <p>
 *  CORBA <code> WrongTransaction </code>用户定义的异常。
 * 当它们从与客户端最初发送的事务范围不同的事务范围中调用时,此异常仅由方法<code> Request.get_response </code>和<code> ORB.get_next_response 
 * </code>请求。
 *  CORBA <code> WrongTransaction </code>用户定义的异常。有关详细信息,请参阅OMG事务服务规范。
 * 
 * 
 * @see <A href="../../../../technotes/guides/idl/jidlExceptions.html">documentation on
 * Java&nbsp;IDL exceptions</A>
 */

public final class WrongTransaction extends UserException {
    /**
     * Constructs a WrongTransaction object with an empty detail message.
     * <p>
     *  构造具有空详细消息的WrongTransaction对象。
     * 
     */
    public WrongTransaction() {
        super(WrongTransactionHelper.id());
    }

    /**
     * Constructs a WrongTransaction object with the given detail message.
     * <p>
     *  构造具有给定详细消息的WrongTransaction对象。
     * 
     * @param reason The detail message explaining what caused this exception to be thrown.
     */
    public WrongTransaction(String reason) {
        super(WrongTransactionHelper.id() + "  " + reason);
    }
}
