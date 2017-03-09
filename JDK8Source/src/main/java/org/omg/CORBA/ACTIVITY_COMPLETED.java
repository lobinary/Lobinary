/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2006, Oracle and/or its affiliates. All rights reserved.
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
 * The <code>ACTIVITY_COMPLETED</code> system exception may be raised on any
 * method for which Activity context is accessed. It indicates that the
 * Activity context in which the method call was made has been completed due
 * to a timeout of either the Activity itself or a transaction that encompasses
 * the Activity, or that the Activity completed in a manner other than that
 * originally requested.
 *
 * <p>
 *  对于访问Activity上下文的任何方法,可能引发<code> ACTIVITY_COMPLETED </code>系统异常。
 * 它表示由于活动本身或包含活动的事务超时或者活动以原始请求之外的方式完成而导致执行方法调用的Activity上下文已完成。
 * 
 * 
 * @see <A href="../../../../technotes/guides/idl/jidlExceptions.html">documentation on
 *      Java&nbsp;IDL exceptions</A>
 * @since   J2SE 1.5
 */

public final class ACTIVITY_COMPLETED extends SystemException {

    /**
     * Constructs an <code>ACTIVITY_COMPLETED</code> exception with
     * minor code set to 0 and CompletionStatus set to COMPLETED_NO.
     * <p>
     *  构造一个<code> ACTIVITY_COMPLETED </code>异常,其中次要代码设置为0,CompletionStatus设置为COMPLETED_NO。
     * 
     */
    public ACTIVITY_COMPLETED() {
        this("");
    }

    /**
     * Constructs an <code>ACTIVITY_COMPLETED</code> exception with the
     * specified message.
     *
     * <p>
     *  使用指定的消息构造<code> ACTIVITY_COMPLETED </code>异常。
     * 
     * 
     * @param detailMessage string containing a detailed message.
     */
    public ACTIVITY_COMPLETED(String detailMessage) {
        this(detailMessage, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs an <code>ACTIVITY_COMPLETED</code> exception with the
     * specified minor code and completion status.
     *
     * <p>
     *  构造具有指定的次要代码和完成状态的<code> ACTIVITY_COMPLETED </code>异常。
     * 
     * 
     * @param minorCode minor code.
     * @param completionStatus completion status.
     */
    public ACTIVITY_COMPLETED(int minorCode,
                              CompletionStatus completionStatus) {
        this("", minorCode, completionStatus);
    }

    /**
     * Constructs an <code>ACTIVITY_COMPLETED</code> exception with the
     * specified message, minor code, and completion status.
     *
     * <p>
     *  构造具有指定消息,次要代码和完成状态的<code> ACTIVITY_COMPLETED </code>异常。
     * 
     * @param detailMessage string containing a detailed message.
     * @param minorCode minor code.
     * @param completionStatus completion status.
     */
    public ACTIVITY_COMPLETED(String detailMessage,
                              int minorCode,
                              CompletionStatus completionStatus) {
        super(detailMessage, minorCode, completionStatus);
    }
}
