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
 * Exception raised
 * when an ORB cannot reach the interface
 * repository, or some other failure relating to the interface repository
 * is detected.<P>
 * It contains a minor code, which gives more detailed information about
 * what caused the exception, and a completion status. It may also contain
 * a string describing the exception.
 *
 * <p>
 *  当ORB无法到达接口存储库或检测到与接口存储库相关的其他一些故障时引发的异常。它包含一个次要代码,它提供了有关导致异常的详细信息以及完成状态。它还可以包含描述异常的字符串。
 * 
 * 
 * @see <A href="../../../../technotes/guides/idl/jidlExceptions.html">documentation on
 * Java&nbsp;IDL exceptions</A>
 * @since       JDK1.2
 */

public final class INTF_REPOS extends SystemException {
    /**
     * Constructs an <code>INTF_REPOS</code> exception with a default minor code
     * of 0 and a completion state of COMPLETED_NO.
     * <p>
     *  构造一个具有默认次要代码0和完成状态COMPLETED_NO的<code> INTF_REPOS </code>异常。
     * 
     */
    public INTF_REPOS() {
        this("");
    }

    /**
     * Constructs an <code>INTF_REPOS</code> exception with the specified detail.
     * <p>
     *  构造具有指定详细信息的<code> INTF_REPOS </code>异常。
     * 
     * 
     * @param s the String containing a detail message
     */
    public INTF_REPOS(String s) {
        this(s, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs an <code>INTF_REPOS</code> exception with the specified
     * minor code and completion status.
     * <p>
     *  构造具有指定的次要代码和完成状态的<code> INTF_REPOS </code>异常。
     * 
     * 
     * @param minor the minor code
     * @param completed the completion status
     */
    public INTF_REPOS(int minor, CompletionStatus completed) {
        this("", minor, completed);
    }

    /**
     * Constructs an <code>INTF_REPOS</code> exception with the specified detail
     * message, minor code, and completion status.
     * A detail message is a String that describes this particular exception.
     * <p>
     *  构造具有指定的详细消息,次要代码和完成状态的<code> INTF_REPOS </code>异常。详细消息是描述此特殊异常的字符串。
     * 
     * @param s the String containing a detail message
     * @param minor the minor code
     * @param completed the completion status
     */
    public INTF_REPOS(String s, int minor, CompletionStatus completed) {
        super(s, minor, completed);
    }
}
