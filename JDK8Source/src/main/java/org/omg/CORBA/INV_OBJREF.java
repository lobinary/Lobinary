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
 * This exception indicates that an object reference is internally
 * malformed. For example, the repository ID may have incorrect
 * syntax or the addressing information may be invalid. This
 * exception is raised by ORB::string_to_object if the passed
 * string does not decode correctly. An ORB may choose to detect
 * calls via nil references (but is not obliged to do detect them).
 * <tt>INV_OBJREF</tt> is used to indicate this.<P>
 * It contains a minor code, which gives more detailed information about
 * what caused the exception, and a completion status. It may also contain
 * a string describing the exception.
 * <P>
 * See the section <A href="../../../../technotes/guides/idl/jidlExceptions.html#minorcodemeanings">Minor
 * Code Meanings</A> to see the minor codes for this exception.
 *
 * <p>
 *  此异常表示对象引用内部格式错误。例如,存储库ID可能具有不正确的语法,或者寻址信息可能是无效的。如果传递的字符串无法正确解码,则由ORB :: string_to_object引发此异常。
 *  ORB可以选择通过nil引用检测调用(但是没有义务检测它们)。 <tt> INV_OBJREF </tt>用于指示此情况。<P>它包含一个次要代码,其中提供有关导致异常的详细信息以及完成状态。
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

public final class INV_OBJREF extends SystemException {
    /**
     * Constructs an <code>INV_OBJREF</code> exception with a default
     * minor code of 0 and a completion state of COMPLETED_NO.
     * <p>
     *  构造具有默认次要代码0和完成状态COMPLETED_NO的<code> INV_OBJREF </code>异常。
     * 
     */
    public INV_OBJREF() {
        this("");
    }

    /**
     * Constructs an <code>INV_OBJREF</code> exception with the specified detail
     * message, a minor code of 0, and a completion state of COMPLETED_NO.
     * <p>
     *  构造具有指定详细消息的<code> INV_OBJREF </code>异常,次要代码为0,完成状态为COMPLETED_NO。
     * 
     * 
     * @param s the String containing a detail message
     */
    public INV_OBJREF(String s) {
        this(s, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs an <code>INV_OBJREF</code> exception with the specified
     * minor code and completion status.
     * <p>
     *  构造具有指定的次要代码和完成状态的<code> INV_OBJREF </code>异常。
     * 
     * 
     * @param minor the minor code
     * @param completed a <code>CompletionStatus</code> instance indicating
     *                  the completion status
     */
    public INV_OBJREF(int minor, CompletionStatus completed) {
        this("", minor, completed);
    }

    /**
     * Constructs an <code>INV_OBJREF</code> exception with the specified detail
     * message, minor code, and completion status.
     * A detail message is a String that describes this particular exception.
     * <p>
     *  构造具有指定详细消息,次要代码和完成状态的<code> INV_OBJREF </code>异常。详细消息是描述此特殊异常的字符串。
     * 
     * @param s the String containing a detail message
     * @param minor the minor code
     * @param completed a <code>CompletionStatus</code> instance indicating
     *                  the completion status
     */
    public INV_OBJREF(String s, int minor, CompletionStatus completed) {
        super(s, minor, completed);
    }
}
