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
 * This exception indicates that an implementation limit was
 * exceeded in the ORB run time. For example, an ORB may reach
 * the maximum number of references it can hold simultaneously
 * in an address space, the size of a parameter may have
 * exceeded the allowed maximum, or an ORB may impose a maximum
 * on the number of clients or servers that can run simultaneously.<P>
 * It contains a minor code, which gives more detailed information about
 * what caused the exception, and a completion status. It may also contain
 * a string describing the exception.
 *
 * <p>
 *  此异常指示ORB运行时超出了实现限制。
 * 例如,ORB可以达到其在地址空间中可以同时保持的参考的最大数量,参数的大小可能已经超过允许的最大值,或者ORB可以对可以运行的客户端或服务器的数量施加最大值<P>它包含一个次要代码,它提供有关导致异常的
 * 详细信息和完成状态。
 *  此异常指示ORB运行时超出了实现限制。它还可以包含描述异常的字符串。
 * 
 * 
 * @see <A href="../../../../technotes/guides/idl/jidlExceptions.html">documentation on
 * Java&nbsp;IDL exceptions</A>
 * @since       JDK1.2
 */


public final class IMP_LIMIT extends SystemException {
    /**
     * Constructs an <code>IMP_LIMIT</code> exception with a default
     * minor code of 0 and a completion state of COMPLETED_NO.
     * <p>
     *  构造具有默认次要代码0和完成状态COMPLETED_NO的<code> IMP_LIMIT </code>异常。
     * 
     */
    public IMP_LIMIT() {
        this("");
    }

    /**
     * Constructs an <code>IMP_LIMIT</code> exception with the specified detail
     * message, a minor code of 0, and a completion state of COMPLETED_NO.
     *
     * <p>
     *  构造具有指定详细消息的<code> IMP_LIMIT </code>异常,次要代码为0,完成状态为COMPLETED_NO。
     * 
     * 
     * @param s the String containing a detail message
     */
    public IMP_LIMIT(String s) {
        this(s, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs an <code>IMP_LIMIT</code> exception with the specified
     * minor code and completion status.
     * <p>
     *  构造具有指定的次要代码和完成状态的<code> IMP_LIMIT </code>异常。
     * 
     * 
     * @param minor the minor code
     * @param completed the completion status
     */
    public IMP_LIMIT(int minor, CompletionStatus completed) {
        this("", minor, completed);
    }

    /**
     * Constructs an <code>IMP_LIMIT</code> exception with the specified detail
     * message, minor code, and completion status.
     * A detail message is a String that describes this particular exception.
     * <p>
     *  构造具有指定详细消息,次要代码和完成状态的<code> IMP_LIMIT </code>异常。详细消息是描述此特殊异常的字符串。
     * 
     * @param s the String containing a detail message
     * @param minor the minor code
     * @param completed the completion status
     */
    public IMP_LIMIT(String s, int minor, CompletionStatus completed) {
        super(s, minor, completed);
    }
}
