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
 * Exception  indicates that the request carried a null transaction context,
 * but an active transaction is required.<P>
 * It contains a minor code, which gives information about
 * what caused the exception, and a completion status. It may also contain
 * a string describing the exception.
 * The OMG Transaction Service specfication has details.
 *
 * <p>
 *  异常表示请求携带一个空事务上下文,但需要一个活动事务。它包含一个次要代码,它提供有关导致异常的原因以及完成状态的信息。它还可以包含描述异常的字符串。 OMG事务服务规范有详细信息。
 * 
 * 
 * @see <A href="../../../../technotes/guides/idl/jidlExceptions.html">documentation on
 * Java&nbsp;IDL exceptions</A>
 */

public final class TRANSACTION_REQUIRED extends SystemException {
    /**
     * Constructs a <code>TRANSACTION_REQUIRED</code> exception with a default minor code
     * of 0, a completion state of CompletionStatus.COMPLETED_NO,
     * and a null description.
     * <p>
     *  构造一个具有默认次要代码0,完成状态为CompletionStatus.COMPLETED_NO和空描述的<code> TRANSACTION_REQUIRED </code>异常。
     * 
     */
    public TRANSACTION_REQUIRED() {
        this("");
    }

    /**
     * Constructs a <code>TRANSACTION_REQUIRED</code> exception with the specified
     * description message, a minor code of 0, and a completion state of COMPLETED_NO.
     * <p>
     *  使用指定的描述消息构造一个<code> TRANSACTION_REQUIRED </code>异常,次要代码为0,完成状态为COMPLETED_NO。
     * 
     * 
     * @param s the String containing a detail message
     */
    public TRANSACTION_REQUIRED(String s) {
        this(s, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs a <code>TRANSACTION_REQUIRED</code> exception with the specified
     * minor code and completion status.
     * <p>
     *  构造具有指定的次要代码和完成状态的<code> TRANSACTION_REQUIRED </code>异常。
     * 
     * 
     * @param minor the minor code
     * @param completed the completion status
     */
    public TRANSACTION_REQUIRED(int minor, CompletionStatus completed) {
        this("", minor, completed);
    }

    /**
     * Constructs a <code>TRANSACTION_REQUIRED</code> exception with the specified description
     * message, minor code, and completion status.
     * <p>
     *  使用指定的描述消息,次要代码和完成状态构造<code> TRANSACTION_REQUIRED </code>异常。
     * 
     * @param s the String containing a description message
     * @param minor the minor code
     * @param completed the completion status
     */
    public TRANSACTION_REQUIRED(String s, int minor, CompletionStatus completed) {
        super(s, minor, completed);
    }
}
