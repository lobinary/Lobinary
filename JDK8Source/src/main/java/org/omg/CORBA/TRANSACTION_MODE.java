/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2006, Oracle and/or its affiliates. All rights reserved.
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
 * The CORBA <code>TRANSACTION_MODE</code> exception is thrown
 * by the client ORB if it detects a mismatch between the
 * InvocationPolicy in the IOR and the chosen invocation path
 * (i.e, direct or routed invocation).
 * It contains a minor code, which gives information about
 * what caused the exception, and a completion status. It may also contain
 * a string describing the exception.
 * The OMG CORBA core 2.4 specification has details.
 *
 * <p>
 *  如果客户机ORB检测到IOR中的InvocationPolicy与所选择的调用路径(即直接调用或路由调用)之间不匹配,则抛出CORBA <code> TRANSACTION_MODE </code>异
 * 常。
 * 它包含一个次要代码,它提供了导致异常的原因以及完成状态的信息。它还可以包含描述异常的字符串。 OMG CORBA核心2.4规范有细节。
 * 
 * 
 * @see <A href="../../../../technotes/guides/idl/jidlExceptions.html">documentation on
 * Java&nbsp;IDL exceptions</A>
 */

public final class TRANSACTION_MODE extends SystemException {
    /**
     * Constructs a <code>TRANSACTION_MODE</code> exception with a default
     * minor code of 0, a completion state of CompletionStatus.COMPLETED_NO,
     * and a null description.
     * <p>
     *  构造具有默认次要代码0,完成状态CompletionStatus.COMPLETED_NO和空描述的<code> TRANSACTION_MODE </code>异常。
     * 
     */
    public TRANSACTION_MODE() {
        this("");
    }

    /**
     * Constructs a <code>TRANSACTION_MODE</code> exception with the specified
     * description message, a minor code of 0, and a completion state of
     * COMPLETED_NO.
     * <p>
     *  使用指定的描述消息构造一个<code> TRANSACTION_MODE </code>异常,次要代码为0,完成状态为COMPLETED_NO。
     * 
     * 
     * @param s the String containing a detail message
     */
    public TRANSACTION_MODE(String s) {
        this(s, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs a <code>TRANSACTION_MODE</code> exception with the specified
     * minor code and completion status.
     * <p>
     *  构造具有指定的次要代码和完成状态的<code> TRANSACTION_MODE </code>异常。
     * 
     * 
     * @param minor the minor code
     * @param completed the completion status
     */
    public TRANSACTION_MODE(int minor, CompletionStatus completed) {
        this("", minor, completed);
    }

    /**
     * Constructs a <code>TRANSACTION_MODE</code> exception with the specified
     * description message, minor code, and completion status.
     * <p>
     *  使用指定的描述消息,次要代码和完成状态构造<code> TRANSACTION_MODE </code>异常。
     * 
     * @param s the String containing a description message
     * @param minor the minor code
     * @param completed the completion status
     */
    public TRANSACTION_MODE(String s, int minor, CompletionStatus completed) {
        super(s, minor, completed);
    }
}
