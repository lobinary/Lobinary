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
 * Exception raised whenever an invocation on a deleted object was
 * performed. It is an authoritative  "hard"  fault report. Anyone
 * receiving it is allowed (even expected) to delete all copies of
 * this object reference and to perform other appropriate  "final
 * recovery"  style procedures. Bridges forward this exception to
 * clients, also destroying any records they may hold (for example,
 * proxy objects used in reference translation). The clients could
 * in turn purge any of their own data structures.
 * <P>
 * It contains a minor code, which gives more detailed information about
 * what caused the exception, and a completion status. It may also contain
 * a string describing the exception.
 * <P>
 * See the section <A href="../../../../technotes/guides/idl/jidlExceptions.html#minorcodemeanings">Minor
 * Code Meanings</A> to see the minor codes for this exception.
 *
 * <p>
 *  每当对已删除对象执行调用时抛出异常。这是一个权威的"硬"故障报告。任何接收它的人都被允许(甚至预期)删除该对象引用的所有副本,并执行其他适当的"最终恢复"样式过程。
 * 桥接将此异常转发给客户端,也会销毁它们可能持有的任何记录(例如,在参考转换中使用的代理对象)。客户端可以反过来清除自己的任何数据结构。
 * <P>
 *  它包含一个次要代码,它提供有关导致异常的详细信息和完成状态。它还可以包含描述异常的字符串。
 * <P>
 *  请参阅<A href="../../../../technotes/guides/idl/jidlExceptions.html#minorcodemeanings">小调代码</A>一节,查看此例外
 * 的次要代码。
 * 
 * 
 * @see <A href="../../../../technotes/guides/idl/jidlExceptions.html">documentation on
 * Java&nbsp;IDL exceptions</A>
 * @since       JDK1.2
 */

public final class OBJECT_NOT_EXIST extends SystemException {
    /**
     * Constructs an <code>OBJECT_NOT_EXIST</code> exception with a default minor code
     * of 0, a completion state of CompletionStatus.COMPLETED_NO,
     * and a null description.
     * <p>
     *  构造一个具有默认次要代码0,完成状态CompletionStatus.COMPLETED_NO和空描述的<code> OBJECT_NOT_EXIST </code>异常。
     * 
     */
    public OBJECT_NOT_EXIST() {
        this("");
    }

    /**
     * Constructs an <code>OBJECT_NOT_EXIST</code> exception with the specified description,
     * a minor code of 0, and a completion state of COMPLETED_NO.
     * <p>
     *  构造具有指定描述的<code> OBJECT_NOT_EXIST </code>异常,次要代码为0,完成状态为COMPLETED_NO。
     * 
     * 
     * @param s the String containing a description message
     */
    public OBJECT_NOT_EXIST(String s) {
        this(s, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs an <code>OBJECT_NOT_EXIST</code> exception with the specified
     * minor code and completion status.
     * <p>
     *  构造具有指定的次要代码和完成状态的<code> OBJECT_NOT_EXIST </code>异常。
     * 
     * 
     * @param minor the minor code
     * @param completed the completion status
     */
    public OBJECT_NOT_EXIST(int minor, CompletionStatus completed) {
        this("", minor, completed);
    }

    /**
     * Constructs an <code>OBJECT_NOT_EXIST</code> exception with the specified description
     * message, minor code, and completion status.
     * <p>
     * 构造具有指定的描述消息,次要代码和完成状态的<code> OBJECT_NOT_EXIST </code>异常。
     * 
     * @param s the String containing a description message
     * @param minor the minor code
     * @param completed the completion status
     */
    public OBJECT_NOT_EXIST(String s, int minor, CompletionStatus completed) {
        super(s, minor, completed);
    }
}
