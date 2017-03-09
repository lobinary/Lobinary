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
 * Exception  thrown
 * when the request carried an invalid transaction context.
 * For example, this exception could be raised if an error
 * occurred when trying to register a resource.<P>
 * It contains a minor code, which gives more detailed information about
 * what caused the exception, and a completion status. It may also contain
 * a string describing the exception.
 *
 * <p>
 *  当请求携带无效的事务上下文时抛出异常。例如,如果尝试注册资源时发生错误,则可能会引发此异常。<P>它包含一个次要代码,它提供了有关导致异常的详细信息以及完成状态。它还可以包含描述异常的字符串。
 * 
 * 
 * @see <A href="../../../../technotes/guides/idl/jidlExceptions.html">documentation on
 * Java&nbsp;IDL exceptions</A>
 * @since   JDK1.2
 *
 */

public final class INVALID_TRANSACTION extends SystemException {
    /**
     * Constructs an <code>INVALID_TRANSACTION</code> exception with a default minor code
     * of 0 and a completion state of COMPLETED_NO.
     * <p>
     *  构造一个具有默认次要代码0和完成状态COMPLETED_NO的<code> INVALID_TRANSACTION </code>异常。
     * 
     */
    public INVALID_TRANSACTION() {
        this("");
    }

    /**
     * Constructs an <code>INVALID_TRANSACTION</code> exception
     * with the specified detail message.
     * <p>
     *  使用指定的详细消息构造一个<code> INVALID_TRANSACTION </code>异常。
     * 
     * 
     * @param s the String containing a detail message
     */
    public INVALID_TRANSACTION(String s) {
        this(s, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs an <code>INVALID_TRANSACTION</code> exception with the specified
     * minor code and completion status.
     * <p>
     *  构造具有指定的次要代码和完成状态的<code> INVALID_TRANSACTION </code>异常。
     * 
     * 
     * @param minor the minor code
     * @param completed the completion status
     */
    public INVALID_TRANSACTION(int minor, CompletionStatus completed) {
        this("", minor, completed);
    }

    /**
     * Constructs an <code>INVALID_TRANSACTION</code> exception with the specified detail
     * message, minor code, and completion status.
     * A detail message is a String that describes this particular exception.
     * <p>
     *  构造具有指定详细消息,次要代码和完成状态的<code> INVALID_TRANSACTION </code>异常。详细消息是描述此特殊异常的字符串。
     * 
     * @param s the String containing a detail message
     * @param minor the minor code
     * @param completed the completion status
     */
    public INVALID_TRANSACTION(String s, int minor, CompletionStatus completed) {
        super(s, minor, completed);
    }
}
