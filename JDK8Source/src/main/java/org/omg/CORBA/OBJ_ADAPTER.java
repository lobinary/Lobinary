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
 * This exception typically indicates an administrative mismatch, for
 * example, a server may have made an attempt to register itself with
 * an implementation repository under a name that is already in use,
 * or is unknown to the repository. <P>
 * It contains a minor code, which gives more detailed information about
 * what caused the exception, and a completion status. It may also contain
 * a string describing the exception.
 * <P>
 * See the section <A href="../../../../technotes/guides/idl/jidlExceptions.html#minorcodemeanings">Minor
 * Code Meanings</A> to see the minor codes for this exception.
 *
 * <p>
 *  此异常通常表示管理不匹配,例如,服务器可能已尝试使用已在使用的名称或存储库未知的名称向实现存储库注册自身。 <P>它包含一个次要代码,它提供有关导致异常的详细信息和完成状态。
 * 它还可以包含描述异常的字符串。
 * <P>
 *  请参阅<A href="../../../../technotes/guides/idl/jidlExceptions.html#minorcodemeanings">小调代码</A>一节,查看此例外
 * 的次要代码。
 * 
 * 
 * @see <A href="../../../../technotes/guides/idl/jidlExceptions.html">documentation on
 * Java&nbsp;IDL exceptions</A>
 * @since       JDK1.2
 */

public final class OBJ_ADAPTER extends SystemException {
    /**
     * Constructs an <code>OBJ_ADAPTER</code> exception with a default minor code
     * of 0, a completion state of CompletionStatus.COMPLETED_NO,
     * and a null description.
     * <p>
     *  构造一个具有默认次要代码0,完成状态CompletionStatus.COMPLETED_NO和空描述的<code> OBJ_ADAPTER </code>异常。
     * 
     */
    public OBJ_ADAPTER() {
        this("");
    }

    /**
     * Constructs an <code>OBJ_ADAPTER</code> exception with the specified description,
     * a minor code of 0, and a completion state of COMPLETED_NO.
     * <p>
     *  构造具有指定描述的<code> OBJ_ADAPTER </code>异常,次要代码为0,完成状态为COMPLETED_NO。
     * 
     * 
     * @param s the String containing a description message
     */
    public OBJ_ADAPTER(String s) {
        this(s, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs an <code>OBJ_ADAPTER</code> exception with the specified
     * minor code and completion status.
     * <p>
     *  构造具有指定的次要代码和完成状态的<code> OBJ_ADAPTER </code>异常。
     * 
     * 
     * @param minor the minor code
     * @param completed the completion status
     */
    public OBJ_ADAPTER(int minor, CompletionStatus completed) {
        this("", minor, completed);
    }

    /**
     * Constructs an <code>OBJ_ADAPTER</code> exception with the specified description
     * message, minor code, and completion status.
     * <p>
     *  构造具有指定的描述消息,次要代码和完成状态的<code> OBJ_ADAPTER </code>异常。
     * 
     * @param s the String containing a description message
     * @param minor the minor code
     * @param completed the completion status
     */
    public OBJ_ADAPTER(String s, int minor, CompletionStatus completed) {
        super(s, minor, completed);
    }
}
