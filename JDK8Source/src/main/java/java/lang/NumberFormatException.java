/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1994, 2012, Oracle and/or its affiliates. All rights reserved.
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

package java.lang;

/**
 * Thrown to indicate that the application has attempted to convert
 * a string to one of the numeric types, but that the string does not
 * have the appropriate format.
 *
 * <p>
 *  抛出以指示应用程序已尝试将字符串转换为其中一个数字类型,但该字符串没有适当的格式。
 * 
 * 
 * @author  unascribed
 * @see     java.lang.Integer#parseInt(String)
 * @since   JDK1.0
 */
public
class NumberFormatException extends IllegalArgumentException {
    static final long serialVersionUID = -2848938806368998894L;

    /**
     * Constructs a <code>NumberFormatException</code> with no detail message.
     * <p>
     *  构造一个没有详细消息的<code> NumberFormatException </code>。
     * 
     */
    public NumberFormatException () {
        super();
    }

    /**
     * Constructs a <code>NumberFormatException</code> with the
     * specified detail message.
     *
     * <p>
     *  使用指定的详细消息构造<code> NumberFormatException </code>。
     * 
     * 
     * @param   s   the detail message.
     */
    public NumberFormatException (String s) {
        super (s);
    }

    /**
     * Factory method for making a <code>NumberFormatException</code>
     * given the specified input which caused the error.
     *
     * <p>
     *  给出引起错误的指定输入的<code> NumberFormatException </code>的工厂方法。
     * 
     * @param   s   the input causing the error
     */
    static NumberFormatException forInputString(String s) {
        return new NumberFormatException("For input string: \"" + s + "\"");
    }
}
