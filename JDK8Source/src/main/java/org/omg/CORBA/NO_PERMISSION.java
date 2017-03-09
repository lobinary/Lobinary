/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2006, Oracle and/or its affiliates. All rights reserved.
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
 * Exception  thrown when an invocation failed because the caller
 * has insufficient privileges.<P>
 * It contains a minor code, which gives more detailed information about
 * what caused the exception, and a completion status. It may also contain
 * a string describing the exception.
 *
 * <p>
 *  调用失败时抛出异常,因为调用者权限不足。<P>它包含一个次要代码,它提供有关导致异常的详细信息和完成状态。它还可以包含描述异常的字符串。
 * 
 * 
 * @see <A href="../../../../technotes/guides/idl/jidlExceptions.html">documentation on
 * Java&nbsp;IDL exceptions</A>
 * @since       JDK1.2
 */

public final class NO_PERMISSION extends SystemException {
    /**
     * Constructs a <code>NO_PERMISSION</code> exception with a default minor code
     * of 0 and a completion state of CompletionStatus.COMPLETED_NO,
     * and a null description.
     * <p>
     *  构造一个具有默认次要代码0和完成状态CompletionStatus.COMPLETED_NO的<code> NO_PERMISSION </code>异常和空描述。
     * 
     */
    public NO_PERMISSION() {
        this("");
    }

    /**
     * Constructs a <code>NO_PERMISSION</code> exception with the specified description,
     * a minor code of 0, and a completion state of COMPLETED_NO.
     * <p>
     *  构造具有指定描述的<code> NO_PERMISSION </code>异常,次要代码为0,完成状态为COMPLETED_NO。
     * 
     * 
     * @param s the String containing a description message
     */
    public NO_PERMISSION(String s) {
        this(s, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs a <code>NO_PERMISSION</code> exception with the specified
     * minor code and completion status.
     * <p>
     *  构造具有指定的次要代码和完成状态的<code> NO_PERMISSION </code>异常。
     * 
     * 
     * @param minor the minor code
     * @param completed the completion status
     */
    public NO_PERMISSION(int minor, CompletionStatus completed) {
        this("", minor, completed);
    }

    /**
     * Constructs a <code>NO_PERMISSION</code> exception with the specified description
     * message, minor code, and completion status.
     * <p>
     *  构造具有指定的描述消息,次要代码和完成状态的<code> NO_PERMISSION </code>异常。
     * 
     * @param s the String containing a description message
     * @param minor the minor code
     * @param completed the completion status
     */
    public NO_PERMISSION(String s, int minor, CompletionStatus completed) {
        super(s, minor, completed);
    }
}
