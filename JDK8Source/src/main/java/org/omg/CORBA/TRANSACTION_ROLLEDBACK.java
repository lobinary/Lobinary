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
 * Exception  thrown when the transaction associated with the request has
 * already been rolled back or marked to roll back. Thus, the requested
 * operation either could not be performed or was not performed because
 * further computation on behalf of the transaction would be fruitless.<P>
 * See the OMG Transaction
 * Service specification for details.
 * It contains a minor code, which gives more detailed information about
 * what caused the exception, and a completion status. It may also contain
 * a string describing the exception.
 *
 * <p>
 *  当与请求关联的事务已回滚或标记为回滚时抛出异常。因此,所请求的操作不能被执行或者不被执行,因为代表事务的进一步计算将是徒劳的。参见OMG事务服务规范的细节。
 * 它包含一个次要代码,它提供有关导致异常的详细信息和完成状态。它还可以包含描述异常的字符串。
 * 
 * 
 * @see <A href="../../../../technotes/guides/idl/jidlExceptions.html">documentation on
 * Java&nbsp;IDL exceptions</A>
 */

public final class TRANSACTION_ROLLEDBACK extends SystemException {
    /**
     * Constructs a <code>TRANSACTION_ROLLEDBACK</code> exception with a default minor code
     * of 0, a completion state of CompletionStatus.COMPLETED_NO,
     * and a null description.
     * <p>
     *  构造具有默认次要代码0,完成状态CompletionStatus.COMPLETED_NO和空描述的<code> TRANSACTION_ROLLEDBACK </code>异常。
     * 
     */
    public TRANSACTION_ROLLEDBACK() {
        this("");
    }

    /**
     * Constructs a <code>TRANSACTION_ROLLEDBACK</code> exception with the
     * specified description message,
     * a minor code of 0, and a completion state of COMPLETED_NO.
     * <p>
     *  使用指定的描述消息构造<code> TRANSACTION_ROLLEDBACK </code>异常,次要代码为0,完成状态为COMPLETED_NO。
     * 
     * 
     * @param s the String containing a detail message
     */
    public TRANSACTION_ROLLEDBACK(String s) {
        this(s, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs a <code>TRANSACTION_ROLLEDBACK</code> exception with the specified
     * minor code and completion status.
     * <p>
     *  构造具有指定的次要代码和完成状态的<code> TRANSACTION_ROLLEDBACK </code>异常。
     * 
     * 
     * @param minor the minor code
     * @param completed the completion status
     */
    public TRANSACTION_ROLLEDBACK(int minor, CompletionStatus completed) {
        this("", minor, completed);
    }

    /**
     * Constructs a <code>TRANSACTION_ROLLEDBACK</code> exception with the
     * specified description message, minor code, and completion status.
     * <p>
     *  使用指定的描述消息,次要代码和完成状态构造<code> TRANSACTION_ROLLEDBACK </code>异常。
     * 
     * @param s the String containing a description message
     * @param minor the minor code
     * @param completed the completion status
     */
    public TRANSACTION_ROLLEDBACK(String s, int minor, CompletionStatus completed) {
        super(s, minor, completed);
    }
}
