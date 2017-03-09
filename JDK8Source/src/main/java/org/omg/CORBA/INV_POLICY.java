/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2006, Oracle and/or its affiliates. All rights reserved.
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
 * Standard exception  thrown
 * when an invocation cannot be made because of an incompatibility between
 * <tt>Policy</tt> overrides that apply to the particular invocation.
 * It contains a minor code, which gives more detailed information about
 * what caused the exception, and a completion status. It may also contain
 * a string describing the exception.
 *
 * <p>
 *  由于适用于特定调用的<tt> Policy </tt>覆盖不兼容而无法进行调用时抛出的标准异常。它包含一个次要代码,它提供有关导致异常的详细信息和完成状态。它还可以包含描述异常的字符串。
 * 
 * 
 * @see <A href="../../../../technotes/guides/idl/jidlExceptions.html">documentation on
 * Java&nbsp;IDL exceptions</A>
 */

public final class INV_POLICY extends SystemException {
    /**
     * Constructs a <code>INV_POLICY</code> exception with a default minor code
     * of 0, a completion state of CompletionStatus.COMPLETED_NO,
     * and a null description.
     * <p>
     *  构造一个具有默认次要代码0,完成状态为CompletionStatus.COMPLETED_NO和空描述的<code> INV_POLICY </code>异常。
     * 
     */
    public INV_POLICY() {
        this("");
    }

    /**
     * Constructs a <code>INV_POLICY</code> exception with the
     * specified description message,
     * a minor code of 0, and a completion state of COMPLETED_NO.
     * <p>
     *  构造具有指定的描述消息的<code> INV_POLICY </code>异常,次要代码为0,完成状态为COMPLETED_NO。
     * 
     * 
     * @param s the String containing a detail message
     */
    public INV_POLICY(String s) {
        this(s, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs a <code>INV_POLICY</code> exception with the specified
     * minor code and completion status.
     * <p>
     *  构造具有指定的次要代码和完成状态的<code> INV_POLICY </code>异常。
     * 
     * 
     * @param minor the minor code
     * @param completed the completion status
     */
    public INV_POLICY(int minor, CompletionStatus completed) {
        this("", minor, completed);
    }

    /**
     * Constructs a <code>INV_POLICY</code> exception with the
     * specified description message, minor code, and completion status.
     * <p>
     *  构造具有指定的描述消息,次要代码和完成状态的<code> INV_POLICY </code>异常。
     * 
     * @param s the String containing a description message
     * @param minor the minor code
     * @param completed the completion status
     */
    public INV_POLICY(String s, int minor, CompletionStatus completed) {
        super(s, minor, completed);
    }
}
