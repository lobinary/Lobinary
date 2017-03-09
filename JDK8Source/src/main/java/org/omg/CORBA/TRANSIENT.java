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
 * Exception  thrown when the ORB attempted to reach an object and failed.
 * It is not an indication that an object does not exist. Instead, it simply
 * means that no further determination of an object's status was possible
 * because it could not be reached. This exception is raised if an attempt
 * to establish a connection fails, for example, because the server or the
 * implementation repository is down.<P>
 * It contains a minor code, which gives more detailed information about
 * what caused the exception, and a completion status. It may also contain
 * a string describing the exception.
 *
 * <p>
 *  当ORB尝试到达对象并失败时抛出异常。它不表示对象不存在。相反,它只是意味着不可能进一步确定对象的状态,因为无法达到。如果尝试建立连接失败,例如因为服务器或实现存储库已关闭,则会引发此异常。
 * <P>它包含一个次要代码,它提供有关导致异常的详细信息,以及完成状态。它还可以包含描述异常的字符串。
 * 
 * 
 * @see <A href="../../../../technotes/guides/idl/jidlExceptions.html">documentation on
 * Java&nbsp;IDL exceptions</A>
 */

public final class TRANSIENT extends SystemException {
    /**
     * Constructs a <code>TRANSIENT</code> exception with a default minor code
     * of 0, a completion state of CompletionStatus.COMPLETED_NO,
     * and a null description.
     * <p>
     *  构造一个具有默认次要代码0,完成状态CompletionStatus.COMPLETED_NO和空描述的<code> TRANSIENT </code>异常。
     * 
     */
    public TRANSIENT() {
        this("");
    }

    /**
     * Constructs a <code>TRANSIENT</code> exception with the specified description message,
     * a minor code of 0, and a completion state of COMPLETED_NO.
     * <p>
     *  构造具有指定描述消息的<code> TRANSIENT </code>异常,次要代码为0,完成状态为COMPLETED_NO。
     * 
     * 
     * @param s the String containing a detail message
     */
    public TRANSIENT(String s) {
        this(s, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs a <code>TRANSIENT</code> exception with the specified
     * minor code and completion status.
     * <p>
     *  构造具有指定次要代码和完成状态的<code> TRANSIENT </code>异常。
     * 
     * 
     * @param minor the minor code
     * @param completed the completion status
     */
    public TRANSIENT(int minor, CompletionStatus completed) {
        this("", minor, completed);
    }

    /**
     * Constructs a <code>TRANSIENT</code> exception with the specified description
     * message, minor code, and completion status.
     * <p>
     *  构造具有指定描述消息,次要代码和完成状态的<code> TRANSIENT </code>异常。
     * 
     * @param s the String containing a description message
     * @param minor the minor code
     * @param completed the completion status
     */
    public TRANSIENT(String s, int minor, CompletionStatus completed) {
        super(s, minor, completed);
    }
}
