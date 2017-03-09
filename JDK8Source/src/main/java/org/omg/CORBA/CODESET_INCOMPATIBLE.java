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
 * This exception is raised whenever meaningful communication is not possible
 * between client and server native code sets.
 *
 * <p>
 *  只有在客户端和服务器本地代码集之间无法进行有意义的通信时,才会引发此异常。
 * 
 * 
 * @see <A href="../../../../technotes/guides/idl/jidlExceptions.html">documentation on
 *      Java&nbsp;IDL exceptions</A>
 * @since   J2SE 1.5
 */

public final class CODESET_INCOMPATIBLE extends SystemException {

    /**
     * Constructs an <code>CODESET_INCOMPATIBLE</code> exception with
     * minor code set to 0 and CompletionStatus set to COMPLETED_NO.
     * <p>
     *  构造具有次要代码设置为0和CompletionStatus设置为COMPLETED_NO的<code> CODESET_INCOMPATIBLE </code>异常。
     * 
     */
    public CODESET_INCOMPATIBLE() {
        this("");
    }

    /**
     * Constructs an <code>CODESET_INCOMPATIBLE</code> exception with the
     * specified message.
     *
     * <p>
     *  使用指定的消息构造<code> CODESET_INCOMPATIBLE </code>异常。
     * 
     * 
     * @param detailMessage string containing a detailed message.
     */
    public CODESET_INCOMPATIBLE(String detailMessage) {
        this(detailMessage, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs an <code>CODESET_INCOMPATIBLE</code> exception with the
     * specified minor code and completion status.
     *
     * <p>
     *  构造具有指定的次要代码和完成状态的<code> CODESET_INCOMPATIBLE </code>异常。
     * 
     * 
     * @param minorCode minor code.
     * @param completionStatus completion status.
     */
    public CODESET_INCOMPATIBLE(int minorCode,
                                CompletionStatus completionStatus) {
        this("", minorCode, completionStatus);
    }

    /**
     * Constructs an <code>CODESET_INCOMPATIBLE</code> exception with the
     * specified message, minor code, and completion status.
     *
     * <p>
     *  构造具有指定消息,次要代码和完成状态的<code> CODESET_INCOMPATIBLE </code>异常。
     * 
     * @param detailMessage string containing a detailed message.
     * @param minorCode minor code.
     * @param completionStatus completion status.
     */
    public CODESET_INCOMPATIBLE(String detailMessage,
                                int minorCode,
                                CompletionStatus completionStatus) {
        super(detailMessage, minorCode, completionStatus);
    }
}
