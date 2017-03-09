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
 * Exception thrown when an operation is invoked by a client but the passed
 * context does not contain the context values required by the operation.<P>
 * It contains a minor code, which gives more detailed information about
 * what caused the exception, and a completion status. It may also contain
 * a string describing the exception.
 *
 * <p>
 *  当客户端调用操作但所传递的上下文不包含操作所需的上下文值时抛出异常。<P>它包含一个次要代码,它提供有关导致异常的详细信息和完成状态。它还可以包含描述异常的字符串。
 * 
 * 
 * @see <A href="../../../../technotes/guides/idl/jidlExceptions.html">documentation on
 * Java&nbsp;IDL exceptions</A>
 * @since    JDK1.2
 */

public final class BAD_CONTEXT extends SystemException {

    /**
     * Constructs a <code>BAD_CONTEXT</code> exception
     * with a default minor code
     * of 0 and a completion state of COMPLETED_NO.
     * <p>
     *  构造具有默认次要代码0和完成状态COMPLETED_NO的<code> BAD_CONTEXT </code>异常。
     * 
     */
    public BAD_CONTEXT() {
        this("");
    }

    /**
     * Constructs a <code>BAD_CONTEXT</code> exception
     * with the specified detail message, a minor code
     * of 0 and a completion state of COMPLETED_NO.
     * <p>
     *  构造具有指定详细消息的<code> BAD_CONTEXT </code>异常,次要代码为0,完成状态为COMPLETED_NO。
     * 
     * 
     * @param s a <code>String</code> object containing a detail message
     */
    public BAD_CONTEXT(String s) {
        this(s, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs a <code>BAD_CONTEXT</code> exception
     * with the specified
     * minor code and completion status.
     * <p>
     *  构造具有指定的次要代码和完成状态的<code> BAD_CONTEXT </code>异常。
     * 
     * 
     * @param minor the minor code
     * @param completed an instance of <code>CompletionStatus</code> indicating
     *                  the completion status
     */
    public BAD_CONTEXT(int minor, CompletionStatus completed) {
        this("", minor, completed);
    }

    /**
     * Constructs a <code>BAD_CONTEXT</code> exception
     * with the specified detail
     * message, minor code, and completion status.
     * A detail message is a String that describes this particular exception.
     * <p>
     *  构造具有指定详细消息,次要代码和完成状态的<code> BAD_CONTEXT </code>异常。详细消息是描述此特殊异常的字符串。
     * 
     * @param s the String containing a detail message
     * @param minor the minor code
     * @param completed an instance of <code>CompletionStatus</code> indicating
     *                  the completion status
     */
    public BAD_CONTEXT(String s, int minor, CompletionStatus completed) {
        super(s, minor, completed);
    }
}
