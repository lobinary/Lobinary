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
 * Exception thrown when the ORB has encountered some general resource
 * limitation. For example, the run time may have reached the maximum
 * permissible number of open connections.<P>
 * It contains a minor code, which gives more detailed information about
 * what caused the exception, and a completion status. It may also contain
 * a string describing the exception.
 *
 * <p>
 *  当ORB遇到一些一般资源限制时抛出异常。例如,运行时间可能已达到允许的最大打开连接数。<P>它包含一个次要代码,其中提供有关导致异常的详细信息和完成状态。它还可以包含描述异常的字符串。
 * 
 * 
 * @see <A href="../../../../technotes/guides/idl/jidlExceptions.html">documentation on
 * Java&nbsp;IDL exceptions</A>
 * @since       JDK1.2
 */

public final class NO_RESOURCES extends SystemException {
    /**
     * Constructs a <code>NO_RESOURCES</code> exception with a default minor code
     * of 0, a completion state of CompletionStatus.COMPLETED_NO,
     * and a null description.
     * <p>
     *  构造一个具有默认次要代码0,完成状态为CompletionStatus.COMPLETED_NO和空描述的<code> NO_RESOURCES </code>异常。
     * 
     */
    public NO_RESOURCES() {
        this("");
    }

    /**
     * Constructs a <code>NO_RESOURCES</code> exception with the specified description,
     * a minor code of 0, and a completion state of COMPLETED_NO.
     * <p>
     *  构造具有指定描述的<code> NO_RESOURCES </code>异常,次要代码为0,完成状态为COMPLETED_NO。
     * 
     * 
     * @param s the String containing a description message
     */
    public NO_RESOURCES(String s) {
        this(s, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs a <code>NO_RESOURCES</code> exception with the specified
     * minor code and completion status.
     * <p>
     *  构造具有指定次要代码和完成状态的<code> NO_RESOURCES </code>异常。
     * 
     * 
     * @param minor the minor code
     * @param completed the completion status
     */
    public NO_RESOURCES(int minor, CompletionStatus completed) {
        this("", minor, completed);
    }

    /**
     * Constructs a <code>NO_RESOURCES</code> exception with the specified description
     * message, minor code, and completion status.
     * <p>
     *  构造具有指定的描述消息,次要代码和完成状态的<code> NO_RESOURCES </code>异常。
     * 
     * @param s the String containing a description message
     * @param minor the minor code
     * @param completed the completion status
     */
    public NO_RESOURCES(String s, int minor, CompletionStatus completed) {
        super(s, minor, completed);
    }
}
