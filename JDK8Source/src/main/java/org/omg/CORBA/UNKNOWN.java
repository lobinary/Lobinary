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
 * This exception is raised if an operation implementation
 * throws a non-CORBA exception (such as an exception
 * specific to the implementation's programming language),
 * or if an operation raises a user exception that does not
 * appear in the operation's raises expression. UNKNOWN is
 * also raised if the server returns a system exception that
 * is unknown to the client. (This can happen if the server
 * uses a later version of CORBA than the client and new system
 * exceptions have been added to the later version.)<P>
 * It contains a minor code, which gives more detailed information about
 * what caused the exception, and a completion status. It may also contain
 * a string describing the exception.
 * <P>
 * See the section <A href="../../../../technotes/guides/idl/jidlExceptions.html#minorcodemeanings">Minor
 * Code Meanings</A> to see the minor codes for this exception.
 *
 * <p>
 *  如果操作实现抛出非CORBA异常(例如特定于实现的编程语言的异常),或者操作引发了未出现在操作的raises表达式中的用户异常,则引发此异常。
 * 如果服务器返回客户端未知的系统异常,则也会引发UNKNOWN。 (如果服务器使用比客户端更高版本的CORBA,并且新的系统异常已添加到更高版本,则可能会发生这种情况。
 * )<P>它包含一个次要代码,其中提供了有关导致异常的详细信息,完成状态。它还可以包含描述异常的字符串。
 * <P>
 *  请参阅<A href="../../../../technotes/guides/idl/jidlExceptions.html#minorcodemeanings">小调代码</A>一节,查看此例外
 * 的次要代码。
 * 
 * 
 * @see <A href="../../../../technotes/guides/idl/jidlExceptions.html">documentation on
 * Java&nbsp;IDL exceptions</A>
 */

public final class UNKNOWN extends SystemException {
    /**
     * Constructs an <code>UNKNOWN</code> exception with a default minor code
     * of 0, a completion state of CompletionStatus.COMPLETED_NO,
     * and a null description.
     * <p>
     *  构造一个具有默认次要代码0,完成状态为CompletionStatus.COMPLETED_NO和空描述的<code> UNKNOWN </code>异常。
     * 
     */
    public UNKNOWN() {
        this("");
    }

    /**
     * Constructs an <code>UNKNOWN</code> exception with the specified description message,
     * a minor code of 0, and a completion state of COMPLETED_NO.
     * <p>
     *  构造具有指定的描述消息的<code> UNKNOWN </code>异常,次要代码为0,完成状态为COMPLETED_NO。
     * 
     * 
     * @param s the String containing a detail message
     */
    public UNKNOWN(String s) {
        this(s, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs an <code>UNKNOWN</code> exception with the specified
     * minor code and completion status.
     * <p>
     *  构造具有指定的次要代码和完成状态的<code> UNKNOWN </code>异常。
     * 
     * 
     * @param minor the minor code
     * @param completed the completion status
     */
    public UNKNOWN(int minor, CompletionStatus completed) {
        this("", minor, completed);
    }

    /**
     * Constructs an <code>UNKNOWN</code> exception with the specified description
     * message, minor code, and completion status.
     * <p>
     * 构造具有指定的描述消息,次要代码和完成状态的<code> UNKNOWN </code>异常。
     * 
     * @param s the String containing a description message
     * @param minor the minor code
     * @param completed the completion status
     */
    public UNKNOWN(String s, int minor, CompletionStatus completed) {
        super(s, minor, completed);
    }
}
