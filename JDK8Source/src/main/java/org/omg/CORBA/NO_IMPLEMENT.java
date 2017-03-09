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
 * This exception indicates that even though the operation that
 * was invoked exists (it has an IDL definition), no implementation
 * for that operation exists. <tt>NO_IMPLEMENT</tt> can, for
 * example, be raised by an ORB if a client asks for an object's
 * type definition from the interface repository, but no interface
 * repository is provided by the ORB.<P>
 * It contains a minor code, which gives more detailed information about
 * what caused the exception, and a completion status. It may also contain
 * a string describing the exception.
 * <P>
 * See the section <A href="../../../../technotes/guides/idl/jidlExceptions.html#minorcodemeanings">Minor
 * Code Meanings</A> to see the minor codes for this exception.
 *
 * <p>
 *  此异常指示即使所调用的操作存在(它具有IDL定义),也不存在该操作的实现。
 * 例如,如果客户端从接口存储库请求对象的类型定义,但没有由ORB提供接口存储库,则可以通过ORB引发<tt> NO_IMPLEMENT </tt>。
 * <P> ,其中提供了有关引发异常的详细信息以及完成状态。它还可以包含描述异常的字符串。
 * <P>
 *  请参阅<A href="../../../../technotes/guides/idl/jidlExceptions.html#minorcodemeanings">小调代码</A>一节,查看此例外
 * 的次要代码。
 * 
 * 
 * @since       JDK1.2
 */

public final class NO_IMPLEMENT extends SystemException {
    /**
     * Constructs a <code>NO_IMPLEMENT</code> exception with a default minor code
     * of 0, a completion state of CompletionStatus.COMPLETED_NO,
     * and a null description.
     * <p>
     *  构造具有默认次要代码0,完成状态CompletionStatus.COMPLETED_NO和空描述的<code> NO_IMPLEMENT </code>异常。
     * 
     */
    public NO_IMPLEMENT() {
        this("");
    }

    /**
     * Constructs a <code>NO_IMPLEMENT</code> exception with the specified description message,
     * a minor code of 0, and a completion state of COMPLETED_NO.
     * <p>
     *  构造具有指定的描述消息的<code> NO_IMPLEMENT </code>异常,次要代码为0,完成状态为COMPLETED_NO。
     * 
     * 
     * @param s the String containing a description of the exception
     */
    public NO_IMPLEMENT(String s) {
        this(s, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs a <code>NO_IMPLEMENT</code> exception with the specified
     * minor code and completion status.
     * <p>
     *  构造具有指定的次要代码和完成状态的<code> NO_IMPLEMENT </code>异常。
     * 
     * 
     * @param minor an <code>int</code> specifying the minor code
     * @param completed a <code>CompletionStatus</code> instance indicating
     *                  the completion status
     */
    public NO_IMPLEMENT(int minor, CompletionStatus completed) {
        this("", minor, completed);
    }

    /**
     * Constructs a <code>NO_IMPLEMENT</code> exception with the specified description
     * message, minor code, and completion status.
     * <p>
     *  构造具有指定的描述消息,次要代码和完成状态的<code> NO_IMPLEMENT </code>异常。
     * 
     * @param s the String containing a description message
     * @param minor an <code>int</code> specifying the minor code
     * @param completed a <code>CompletionStatus</code> instance indicating
     *                  the completion status
     */
    public NO_IMPLEMENT(String s, int minor, CompletionStatus completed) {
        super(s, minor, completed);
    }
}
