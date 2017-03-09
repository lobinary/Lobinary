/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2006, Oracle and/or its affiliates. All rights reserved.
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
 * The CORBA <code>TRANSACTION_UNAVAILABLE</code> exception is thrown
 * by the ORB when it cannot process a transaction service context because
 * its connection to the Transaction Service has been abnormally terminated.
 *
 * It contains a minor code, which gives information about
 * what caused the exception, and a completion status. It may also contain
 * a string describing the exception.
 * The OMG CORBA core 2.4 specification has details.
 *
 * <p>
 *  当ORB无法处理事务服务上下文时,会抛出CORBA <code> TRANSACTION_UNAVAILABLE </code>异常,因为它与事务服务的连接已异常终止。
 * 
 *  它包含一个次要代码,它提供了导致异常的原因以及完成状态的信息。它还可以包含描述异常的字符串。 OMG CORBA核心2.4规范有细节。
 * 
 * 
 * @see <A href="../../../../technotes/guides/idl/jidlExceptions.html">documentation on
 * Java&nbsp;IDL exceptions</A>
 */

public final class TRANSACTION_UNAVAILABLE extends SystemException {
    /**
     * Constructs a <code>TRANSACTION_UNAVAILABLE</code> exception
     * with a default minor code of 0, a completion state of
     * CompletionStatus.COMPLETED_NO, and a null description.
     * <p>
     *  构造具有默认次要代码0,完成状态CompletionStatus.COMPLETED_NO和空描述的<code> TRANSACTION_UNAVAILABLE </code>异常。
     * 
     */
    public TRANSACTION_UNAVAILABLE() {
        this("");
    }

    /**
     * Constructs a <code>TRANSACTION_UNAVAILABLE</code> exception with the
     * specifieddescription message, a minor code of 0, and a completion state
     * of COMPLETED_NO.
     * <p>
     *  使用指定的描述消息构造一个<code> TRANSACTION_UNAVAILABLE </code>异常,次要代码为0,完成状态为COMPLETED_NO。
     * 
     * 
     * @param s the String containing a detail message
     */
    public TRANSACTION_UNAVAILABLE(String s) {
        this(s, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs a <code>TRANSACTION_UNAVAILABLE</code> exception with the
     * specified minor code and completion status.
     * <p>
     *  构造具有指定的次要代码和完成状态的<code> TRANSACTION_UNAVAILABLE </code>异常。
     * 
     * 
     * @param minor the minor code
     * @param completed the completion status
     */
    public TRANSACTION_UNAVAILABLE(int minor, CompletionStatus completed) {
        this("", minor, completed);
    }

    /**
     * Constructs a <code>TRANSACTION_UNAVAILABLE</code> exception with the
     * specified description message, minor code, and completion status.
     * <p>
     *  构造具有指定的描述消息,次要代码和完成状态的<code> TRANSACTION_UNAVAILABLE </code>异常。
     * 
     * @param s the String containing a description message
     * @param minor the minor code
     * @param completed the completion status
     */
    public TRANSACTION_UNAVAILABLE(String s, int minor,
                                   CompletionStatus completed) {
        super(s, minor, completed);
    }
}
