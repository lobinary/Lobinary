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
 * <code>REBIND</code> is raised when the current effective RebindPolicy,
 * has a value of NO_REBIND or NO_RECONNECT and an invocation on a bound
 * object reference results in a LocateReply message with status
 * OBJECT_FORWARD or a Reply message with status LOCATION_FORWARD.
 * This exception is also raised if the current effective RebindPolicy has
 * a value of NO_RECONNECT and a connection must be reopened.
 *
 * <p>
 *  当当前有效的RebindPolicy具有值NO_REBIND或NO_RECONNECT并且对绑定对象引用的调用导致具有状态为OBJECT_FORWARD或具有状态为LOCATION_FORWARD的回
 * 复消息的LocateReply消息时,引发<code> REBIND </code>。
 * 如果当前有效的RebindPolicy的值为NO_RECONNECT,并且必须重新打开连接,则也会引发此异常。
 * 
 * 
 * @see <A href="../../../../technotes/guides/idl/jidlExceptions.html">documentation on
 *      Java&nbsp;IDL exceptions</A>
 * @since   J2SE 1.5
 */

public final class REBIND extends SystemException {

    /**
     * Constructs an <code>REBIND</code> exception with
     * minor code set to 0 and CompletionStatus set to COMPLETED_NO.
     * <p>
     *  构造一个<code> REBIND </code>异常,其中次要代码设置为0,CompletionStatus设置为COMPLETED_NO。
     * 
     */
    public REBIND() {
        this("");
    }

    /**
     * Constructs an <code>REBIND</code> exception with the
     * specified message.
     *
     * <p>
     *  使用指定的消息构造一个<code> REBIND </code>异常。
     * 
     * 
     * @param detailMessage string containing a detailed message.
     */
    public REBIND(String detailMessage) {
        this(detailMessage, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs an <code>REBIND</code> exception with the
     * specified minor code and completion status.
     *
     * <p>
     *  构造具有指定次要代码和完成状态的<code> REBIND </code>异常。
     * 
     * 
     * @param minorCode minor code.
     * @param completionStatus completion status.
     */
    public REBIND(int minorCode,
                  CompletionStatus completionStatus) {
        this("", minorCode, completionStatus);
    }

    /**
     * Constructs an <code>REBIND</code> exception with the
     * specified message, minor code, and completion status.
     *
     * <p>
     *  构造具有指定消息,次要代码和完成状态的<code> REBIND </code>异常。
     * 
     * @param detailMessage string containing a detailed message.
     * @param minorCode minor code.
     * @param completionStatus completion status.
     */
    public REBIND(String detailMessage,
                  int minorCode,
                  CompletionStatus completionStatus) {
        super(detailMessage, minorCode, completionStatus);
    }
}
