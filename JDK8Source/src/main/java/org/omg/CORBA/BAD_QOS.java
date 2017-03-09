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
 * The <code>BAD_QOS</code> exception is raised whenever an object cannot
 * support the quality of service required by an invocation parameter that
 * has a quality of service semantics associated with it.
 *
 * <p>
 *  只要对象不能支持具有与其相关联的服务质量语义的调用参数所需的服务质量,就会引发<code> BAD_QOS </code>异常。
 * 
 * 
 * @see <A href="../../../../technotes/guides/idl/jidlExceptions.html">documentation on
 *      Java&nbsp;IDL exceptions</A>
 * @since   J2SE 1.5
 */

public final class BAD_QOS extends SystemException {

    /**
     * Constructs an <code>BAD_QOS</code> exception with
     * minor code set to 0 and CompletionStatus set to COMPLETED_NO.
     * <p>
     *  构造一个<code> BAD_QOS </code>异常,其中次要代码设置为0,CompletionStatus设置为COMPLETED_NO。
     * 
     */
    public BAD_QOS() {
        this("");
    }

    /**
     * Constructs an <code>BAD_QOS</code> exception with the
     * specified message.
     *
     * <p>
     *  使用指定的消息构造<code> BAD_QOS </code>异常。
     * 
     * 
     * @param detailMessage string containing a detailed message.
     */
    public BAD_QOS(String detailMessage) {
        this(detailMessage, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs an <code>BAD_QOS</code> exception with the
     * specified minor code and completion status.
     *
     * <p>
     *  构造具有指定的次要代码和完成状态的<code> BAD_QOS </code>异常。
     * 
     * 
     * @param minorCode minor code.
     * @param completionStatus completion status.
     */
    public BAD_QOS(int minorCode,
                   CompletionStatus completionStatus) {
        this("", minorCode, completionStatus);
    }

    /**
     * Constructs an <code>BAD_QOS</code> exception with the
     * specified message, minor code, and completion status.
     *
     * <p>
     *  构造具有指定消息,次要代码和完成状态的<code> BAD_QOS </code>异常。
     * 
     * @param detailMessage string containing a detailed message.
     * @param minorCode minor code.
     * @param completionStatus completion status.
     */
    public BAD_QOS(String detailMessage,
                   int minorCode,
                   CompletionStatus completionStatus) {
        super(detailMessage, minorCode, completionStatus);
    }
}
