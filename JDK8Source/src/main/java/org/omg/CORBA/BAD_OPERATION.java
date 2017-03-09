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
 * Exception thrown when an object reference denotes an existing object,
 * but that the object does not support the operation that was invoked.<P>
 * It contains a minor code, which gives more detailed information about
 * what caused the exception, and a completion status. It may also contain
 * a string describing the exception.
 *
 * <p>
 *  当对象引用表示现有对象,但该对象不支持调用的操作时抛出异常。<P>它包含一个小代码,它提供有关导致异常的详细信息以及完成状态。它还可以包含描述异常的字符串。
 * 
 * 
 * @see <A href="../../../../technotes/guides/idl/jidlExceptions.html">documentation on
 * Java&nbsp;IDL exceptions</A>
 * @since       JDK1.2
 */

public final class BAD_OPERATION extends SystemException {

    /**
     * Constructs a <code>BAD_OPERATION</code> exception with a default
     * minor code of 0 and a completion state of COMPLETED_NO.
     * <p>
     *  构造一个具有默认次要代码0和完成状态COMPLETED_NO的<code> BAD_OPERATION </code>异常。
     * 
     */
    public BAD_OPERATION() {
        this("");
    }

    /**
     * Constructs a <code>BAD_OPERATION</code> exception with the specified detail
     * message, a minor code of 0, and a completion state of COMPLETED_NO.
     * <p>
     *  构造具有指定详细消息的<code> BAD_OPERATION </code>异常,次要代码为0,完成状态为COMPLETED_NO。
     * 
     * 
     * @param s the String containing a detail message
     */
    public BAD_OPERATION(String s) {
        this(s, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs a <code>BAD_OPERATION</code> exception with the specified
     * minor code and completion status.
     * <p>
     *  构造具有指定的次要代码和完成状态的<code> BAD_OPERATION </code>异常。
     * 
     * 
     * @param minor the minor code
     * @param completed an instance of <code>CompletionStatus</code> indicating
     *                  the completion status
     */
    public BAD_OPERATION(int minor, CompletionStatus completed) {
        this("", minor, completed);
    }

    /**
     * Constructs a <code>BAD_OPERATION</code> exception with the specified detail
     * message, minor code, and completion status.
     * A detail message is a String that describes this particular exception.
     * <p>
     *  构造具有指定详细消息,次要代码和完成状态的<code> BAD_OPERATION </code>异常。详细消息是描述此特殊异常的字符串。
     * 
     * @param s the String containing a detail message
     * @param minor the minor code
     * @param completed an instance of <code>CompletionStatus</code> indicating
     *                  the completion status
     */
    public BAD_OPERATION(String s, int minor, CompletionStatus completed) {
        super(s, minor, completed);
    }
}
