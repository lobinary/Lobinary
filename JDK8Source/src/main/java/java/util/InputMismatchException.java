/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2008, Oracle and/or its affiliates. All rights reserved.
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

package java.util;

/**
 * Thrown by a <code>Scanner</code> to indicate that the token
 * retrieved does not match the pattern for the expected type, or
 * that the token is out of range for the expected type.
 *
 * <p>
 *  由<code> Scanner </code>引发以表示检索的令牌与预期类型的​​模式不匹配,或者令牌超出预期类型的​​范围。
 * 
 * 
 * @author  unascribed
 * @see     java.util.Scanner
 * @since   1.5
 */
public
class InputMismatchException extends NoSuchElementException {
    private static final long serialVersionUID = 8811230760997066428L;

    /**
     * Constructs an <code>InputMismatchException</code> with <tt>null</tt>
     * as its error message string.
     * <p>
     *  使用<tt> null </tt>作为其错误消息字符串构造一个<code> InputMismatchException </code>。
     * 
     */
    public InputMismatchException() {
        super();
    }

    /**
     * Constructs an <code>InputMismatchException</code>, saving a reference
     * to the error message string <tt>s</tt> for later retrieval by the
     * <tt>getMessage</tt> method.
     *
     * <p>
     *  构造<code> InputMismatchException </code>,保存对错误消息字符串<tt> s </tt>的引用,以供以后通过<tt> getMessage </tt>方法检索。
     * 
     * @param   s   the detail message.
     */
    public InputMismatchException(String s) {
        super(s);
    }
}
