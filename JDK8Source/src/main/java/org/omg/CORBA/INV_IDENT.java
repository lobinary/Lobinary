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
 * This exception indicates that an IDL identifier is syntactically
 * invalid. It may be raised if, for example, an identifier passed
 * to the interface repository does not conform to IDL identifier
 * syntax, or if an illegal operation name is used with the DII.<P>
 * It contains a minor code, which gives more detailed information about
 * what caused the exception, and a completion status. It may also contain
 * a string describing the exception.
 *
 * <p>
 *  此异常表示IDL标识符在语法上无效。例如,如果传递给接口存储库的标识符不符合IDL标识符语法,或者如果非法操作名与DII一起使用,则可能会引发它。
 * 它包含一个次要代码,它提供更详细的信息关于什么导致异常,以及完成状态。它还可以包含描述异常的字符串。
 * 
 * 
 * @see <A href="../../../../technotes/guides/idl/jidlExceptions.html">documentation on
 * Java&nbsp;IDL exceptions</A>
 * @since       JDK1.2
 */

public final class INV_IDENT extends SystemException {
    /**
     * Constructs an <code>INV_IDENT</code> exception with a default
     * minor code of 0 and a completion state of COMPLETED_NO.
     * <p>
     *  构造具有默认次要代码0和完成状态COMPLETED_NO的<code> INV_IDENT </code>异常。
     * 
     */
    public INV_IDENT() {
        this("");
    }

    /**
     * Constructs an <code>INV_IDENT</code> exception with the specified detail
     * message, a minor code of 0, and a completion state of COMPLETED_NO.
     * <p>
     *  构造具有指定详细消息的<code> INV_IDENT </code>异常,次要代码为0,完成状态为COMPLETED_NO。
     * 
     * 
     * @param s the String containing a detail message
     */
    public INV_IDENT(String s) {
        this(s, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs an <code>INV_IDENT</code> exception with the specified
     * minor code and completion status.
     * <p>
     *  构造具有指定的次要代码和完成状态的<code> INV_IDENT </code>异常。
     * 
     * 
     * @param minor the minor code
     * @param completed a <code>CompletionStatus</code> object indicating
     *                  the completion status
     */
    public INV_IDENT(int minor, CompletionStatus completed) {
        this("", minor, completed);
    }

    /**
     * Constructs an <code>INV_IDENT</code> exception with the specified detail
     * message, minor code, and completion status.
     * A detail message is a String that describes this particular exception.
     * <p>
     *  构造具有指定详细消息,次要代码和完成状态的<code> INV_IDENT </code>异常。详细消息是描述此特殊异常的字符串。
     * 
     * @param s the String containing a detail message
     * @param minor the minor code
     * @param completed a <code>CompletionStatus</code> object indicating
     *                  the completion status
     */
    public INV_IDENT(String s, int minor, CompletionStatus completed) {
        super(s, minor, completed);
    }
}
