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
 * Exception  thrown
 * when a parameter passed to a call is out of range or
 * otherwise considered illegal. An ORB may raise this exception
 * if null values or null pointers are passed to an operation (for
 * language mappings where the concept of a null pointers or null
 * values applies). BAD_PARAM can also be raised as a result of a
 * client generating requests with incorrect parameters using the DII. <P>
 * It contains a minor code, which gives more detailed information about
 * what caused the exception, and a completion status. It may also contain
 * a string describing the exception.
 *
 * <p>
 *  当传递给调用的参数超出范围或以其他方式被认为是非法时抛出异常。如果空值或空指针传递给操作(对于空指针或空值的概念适用的语言映射),ORB可能引发此异常。
 *  BAD_PARAM也可以作为客户端使用DII生成具有不正确参数的请求的结果。 <P>它包含一个次要代码,它提供有关导致异常的详细信息和完成状态。它还可以包含描述异常的字符串。
 * 
 * 
 * @see <A href="../../../../technotes/guides/idl/jidlExceptions.html">documentation on
 * Java&nbsp;IDL exceptions</A>
 * @see <A href="../../../../technotes/guides/idl/jidlExceptions.html#minorcodemeanings">meaning of
 * minor codes</A>
 * @since       JDK1.2
 */

public final class BAD_PARAM extends SystemException {

    /**
     * Constructs a <code>BAD_PARAM</code> exception with a default
     * minor code of 0 and a completion state of COMPLETED_NO.
     * <p>
     *  构造一个具有默认次要代码0和完成状态COMPLETED_NO的<code> BAD_PARAM </code>异常。
     * 
     */
    public BAD_PARAM() {
        this("");
    }

    /**
     * Constructs a <code>BAD_PARAM</code> exception with the specified detail
     * message, a minor code of 0, and a completion state of COMPLETED_NO.
     *
     * <p>
     *  构造具有指定详细消息的<code> BAD_PARAM </code>异常,次要代码为0,完成状态为COMPLETED_NO。
     * 
     * 
     * @param s the String containing a detail message describing this
     *          exception
     */
    public BAD_PARAM(String s) {
        this(s, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs a <code>BAD_PARAM</code> exception with the specified
     * minor code and completion status.
     * <p>
     *  构造具有指定的次要代码和完成状态的<code> BAD_PARAM </code>异常。
     * 
     * 
     * @param minor the minor code
     * @param completed the completion status
     */
    public BAD_PARAM(int minor, CompletionStatus completed) {
        this("", minor, completed);
    }

    /**
     * Constructs a <code>BAD_PARAM</code> exception with the specified detail
     * message, minor code, and completion status.
     * A detail message is a <code>String</code> that describes
     * this particular exception.
     *
     * <p>
     *  构造具有指定详细消息,次要代码和完成状态的<code> BAD_PARAM </code>异常。详细消息是描述此特殊异常的<code> String </code>。
     * 
     * @param s the <code>String</code> containing a detail message
     * @param minor the minor code
     * @param completed the completion status
     */
    public BAD_PARAM(String s, int minor, CompletionStatus completed) {
        super(s, minor, completed);
    }
}
