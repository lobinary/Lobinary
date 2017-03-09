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
 * The <code>ACTIVITY_REQUIRED</code> system exception may be raised on any
 * method for which an Activity context is required. It indicates that an
 * Activity context was necessary to perform the invoked operation, but one
 * was not found associated with the calling thread.
 *
 * <p>
 *  对于需要Activity上下文的任何方法,可能会引发<code> ACTIVITY_REQUIRED </code>系统异常。
 * 它表示需要一个Activity上下文来执行被调用的操作,但是没有发现与调用线程相关联的。
 * 
 * 
 * @see <A href="../../../../technotes/guides/idl/jidlExceptions.html">documentation on
 *      Java&nbsp;IDL exceptions</A>
 * @since   J2SE 1.5
 */

public final class ACTIVITY_REQUIRED extends SystemException {

    /**
     * Constructs an <code>ACTIVITY_REQUIRED</code> exception with
     * minor code set to 0 and CompletionStatus set to COMPLETED_NO.
     * <p>
     *  构造一个<code> ACTIVITY_REQUIRED </code>异常,其中次要代码设置为0,CompletionStatus设置为COMPLETED_NO。
     * 
     */
    public ACTIVITY_REQUIRED() {
        this("");
    }

    /**
     * Constructs an <code>ACTIVITY_REQUIRED</code> exception with the
     * specified message.
     *
     * <p>
     *  使用指定的消息构造一个<code> ACTIVITY_REQUIRED </code>异常。
     * 
     * 
     * @param detailMessage string containing a detailed message.
     */
    public ACTIVITY_REQUIRED(String detailMessage) {
        this(detailMessage, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs an <code>ACTIVITY_REQUIRED</code> exception with the
     * specified minor code and completion status.
     *
     * <p>
     *  构造具有指定次要代码和完成状态的<code> ACTIVITY_REQUIRED </code>异常。
     * 
     * 
     * @param minorCode minor code.
     * @param completionStatus completion status.
     */
    public ACTIVITY_REQUIRED(int minorCode,
                             CompletionStatus completionStatus) {
        this("", minorCode, completionStatus);
    }

    /**
     * Constructs an <code>ACTIVITY_REQUIRED</code> exception with the
     * specified message, minor code, and completion status.
     *
     * <p>
     *  构造具有指定消息,次要代码和完成状态的<code> ACTIVITY_REQUIRED </code>异常。
     * 
     * @param detailMessage string containing a detailed message.
     * @param minorCode minor code.
     * @param completionStatus completion status.
     */
    public ACTIVITY_REQUIRED(String detailMessage,
                             int minorCode,
                             CompletionStatus completionStatus) {
        super(detailMessage, minorCode, completionStatus);
    }
}
