/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2005, Oracle and/or its affiliates. All rights reserved.
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
 * Unchecked exception thrown when a character with an invalid Unicode code
 * point as defined by {@link Character#isValidCodePoint} is passed to the
 * {@link Formatter}.
 *
 * <p> Unless otherwise specified, passing a <tt>null</tt> argument to any
 * method or constructor in this class will cause a {@link
 * NullPointerException} to be thrown.
 *
 * <p>
 *  如果将{@link Character#isValidCodePoint}定义的具有无效Unicode代码点的字符传递到{@link Formatter},则会抛出未经检查的异常。
 * 
 *  <p>除非另有说明,否则将<tt> null </tt>参数传递给此类中的任何方法或构造函数都会导致抛出{@link NullPointerException}。
 * 
 * 
 * @since 1.5
 */
public class IllegalFormatCodePointException extends IllegalFormatException {

    private static final long serialVersionUID = 19080630L;

    private int c;

    /**
     * Constructs an instance of this class with the specified illegal code
     * point as defined by {@link Character#isValidCodePoint}.
     *
     * <p>
     *  构造具有由{@link Character#isValidCodePoint}定义的指定的非法代码点的类的实例。
     * 
     * 
     * @param  c
     *         The illegal Unicode code point
     */
    public IllegalFormatCodePointException(int c) {
        this.c = c;
    }

    /**
     * Returns the illegal code point as defined by {@link
     * Character#isValidCodePoint}.
     *
     * <p>
     *  返回由{@link Character#isValidCodePoint}定义的非法代码点。
     * 
     * @return  The illegal Unicode code point
     */
    public int getCodePoint() {
        return c;
    }

    public String getMessage() {
        return String.format("Code point = %#x", c);
    }
}
