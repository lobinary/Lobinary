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
 * This exception indicates a persistent storage failure, for example,
 * failure to establish a database connection or corruption of a
 * database.<P>
 * It contains a minor code, which gives more detailed information about
 * what caused the exception, and a completion status. It may also contain
 * a string describing the exception.
 *
 * <p>
 *  此异常表示持久性存储器故障,例如,无法建立数据库连接或数据库损坏。它包含一个次要代码,它提供有关导致异常的详细信息和完成状态。它还可以包含描述异常的字符串。
 * 
 * 
 * @see <A href="../../../../technotes/guides/idl/jidlExceptions.html">documentation on
 * Java&nbsp;IDL exceptions</A>
 */

public final class PERSIST_STORE extends SystemException {
    /**
     * Constructs a <code>PERSIST_STORE</code> exception with a default minor code
     * of 0, a completion state of CompletionStatus.COMPLETED_NO,
     * and a null description.
     * <p>
     *  构造具有默认次要代码0,完成状态CompletionStatus.COMPLETED_NO和空描述的<code> PERSIST_STORE </code>异常。
     * 
     */
    public PERSIST_STORE() {
        this("");
    }

    /**
     * Constructs a <code>PERSIST_STORE</code> exception with the specified description message,
     * a minor code of 0, and a completion state of COMPLETED_NO.
     * <p>
     *  使用指定的描述消息构造一个<code> PERSIST_STORE </code>异常,次要代码为0,完成状态为COMPLETED_NO。
     * 
     * 
     * @param s the String containing a detail message
     */
    public PERSIST_STORE(String s) {
        this(s, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs a <code>PERSIST_STORE</code> exception with the specified
     * minor code and completion status.
     * <p>
     *  构造具有指定的次要代码和完成状态的<code> PERSIST_STORE </code>异常。
     * 
     * 
     * @param minor the minor code
     * @param completed the completion status
     */
    public PERSIST_STORE(int minor, CompletionStatus completed) {
        this("", minor, completed);
    }

    /**
     * Constructs a <code>PERSIST_STORE</code> exception with the specified description
     * message, minor code, and completion status.
     * <p>
     *  构造具有指定的描述消息,次要代码和完成状态的<code> PERSIST_STORE </code>异常。
     * 
     * @param s the String containing a description message
     * @param minor the minor code
     * @param completed the completion status
     */
    public PERSIST_STORE(String s, int minor, CompletionStatus completed) {
        super(s, minor, completed);
    }
}
