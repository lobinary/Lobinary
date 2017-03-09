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
 * The <code>INVALID_ACTIVITY</code> system exception may be raised on the
 * Activity or Transaction services' resume methods if a transaction or
 * Activity is resumed in a context different to that from which it was
 * suspended. It is also raised when an attempted invocation is made that
 * is incompatible with the Activity's current state.
 *
 * <p>
 *  如果事务或活动在与其暂停的上下文不同的上下文中恢复,则可能在活动或事务服务的恢复方法上引发<code> INVALID_ACTIVITY </code>系统异常。
 * 当进行与活动的当前状态不兼容的尝试调用时,也会引发此异常。
 * 
 * 
 * @see <A href="../../../../technotes/guides/idl/jidlExceptions.html">documentation on
 *      Java&nbsp;IDL exceptions</A>
 * @since   J2SE 1.5
 */

public final class INVALID_ACTIVITY extends SystemException {

    /**
     * Constructs an <code>INVALID_ACTIVITY</code> exception with
     * minor code set to 0 and CompletionStatus set to COMPLETED_NO.
     * <p>
     *  构造一个<code> INVALID_ACTIVITY </code>异常,其中次要代码设置为0,CompletionStatus设置为COMPLETED_NO。
     * 
     */
    public INVALID_ACTIVITY() {
        this("");
    }

    /**
     * Constructs an <code>INVALID_ACTIVITY</code> exception with the
     * specified message.
     *
     * <p>
     *  使用指定的消息构造<code> INVALID_ACTIVITY </code>异常。
     * 
     * 
     * @param detailMessage string containing a detailed message.
     */
    public INVALID_ACTIVITY(String detailMessage) {
        this(detailMessage, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs an <code>INVALID_ACTIVITY</code> exception with the
     * specified minor code and completion status.
     *
     * <p>
     *  构造具有指定的次要代码和完成状态的<code> INVALID_ACTIVITY </code>异常。
     * 
     * 
     * @param minorCode minor code.
     * @param completionStatus completion status.
     */
    public INVALID_ACTIVITY(int minorCode,
                            CompletionStatus completionStatus) {
        this("", minorCode, completionStatus);
    }

    /**
     * Constructs an <code>INVALID_ACTIVITY</code> exception with the
     * specified message, minor code, and completion status.
     *
     * <p>
     *  构造具有指定消息,次要代码和完成状态的<code> INVALID_ACTIVITY </code>异常。
     * 
     * @param detailMessage string containing a detailed message.
     * @param minorCode minor code.
     * @param completionStatus completion status.
     */
    public INVALID_ACTIVITY(String detailMessage,
                            int minorCode,
                            CompletionStatus completionStatus) {
        super(detailMessage, minorCode, completionStatus);
    }
}
