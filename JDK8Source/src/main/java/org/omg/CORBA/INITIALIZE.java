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
 * Exception thrown
 * when an ORB has encountered a failure during its initialization,
 * such as failure to acquire networking resources or detecting a
 * configuration error.<P>
 * It contains a minor code, which gives more detailed information about
 * what caused the exception, and a completion status. It may also contain
 * a string describing the exception.
 *
 * <p>
 *  当ORB在其初始化期间遇到故障时抛出异常,例如无法获取网络资源或检测到配置错误。它包含一个小代码,它提供了有关导致异常的详细信息以及完成状态。它还可以包含描述异常的字符串。
 * 
 * 
 * @see <A href="../../../../technotes/guides/idl/jidlExceptions.html">documentation on
 * Java&nbsp;IDL exceptions</A>
 * @since       JDK1.2
 */

public final class INITIALIZE extends SystemException {
    /**
     * Constructs an <code>INITIALIZE</code> exception with a default
     * minor code of 0 and a completion state of
     * <code>CompletionStatus.COMPLETED_NO</code>.
     * <p>
     *  构造具有默认次要代码0和完成状态为<code> CompletionStatus.COMPLETED_NO </code>的<code> INITIALIZE </code>异常。
     * 
     */
    public INITIALIZE() {
        this("");
    }

    /**
     * Constructs an <code>INITIALIZE</code> exception with the specified detail
     * message, a minor code of 0, and a completion state of
     * <code>CompletionStatus.COMPLETED_NO</code>.
     * <p>
     *  构造具有指定详细消息的<code> INITIALIZE </code>异常,次要代码为0,完成状态为<code> CompletionStatus.COMPLETED_NO </code>。
     * 
     * 
     * @param s the String containing a detail message
     */
    public INITIALIZE(String s) {
        this(s, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs an <code>INITIALIZE</code> exception with the specified
     * minor code and completion status.
     * <p>
     *  构造具有指定的次要代码和完成状态的<code> INITIALIZE </code>异常。
     * 
     * 
     * @param minor the minor code
     * @param completed an instance of <code>CompletionStatus</code>
     *                  indicating the completion status of the method
     *                  that threw this exception
     */
    public INITIALIZE(int minor, CompletionStatus completed) {
        this("", minor, completed);
    }

    /**
     * Constructs an <code>INITIALIZE</code> exception with the specified detail
     * message, minor code, and completion status.
     * A detail message is a String that describes this particular exception.
     * <p>
     *  构造具有指定详细消息,次要代码和完成状态的<code> INITIALIZE </code>异常。详细消息是描述此特殊异常的字符串。
     * 
     * @param s the String containing a detail message
     * @param minor the minor code
     * @param completed an instance of <code>CompletionStatus</code>
     *                  indicating the completion status of the method
     *                  that threw this exception
     */
    public INITIALIZE(String s, int minor, CompletionStatus completed) {
        super(s, minor, completed);
    }
}
