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
 * Thrown by {@code String} methods to indicate that an index
 * is either negative or greater than the size of the string.  For
 * some methods such as the charAt method, this exception also is
 * thrown when the index is equal to the size of the string.
 *
 * <p>
 *  由{@code String}方法引发以指示索引为负数或大于字符串的大小。对于某些方法(如charAt方法),当索引等于字符串大小时,也会抛出此异常。
 * 
 * 
 * @author  unascribed
 * @see     java.lang.String#charAt(int)
 * @since   JDK1.0
 */
public
class StringIndexOutOfBoundsException extends IndexOutOfBoundsException {
    private static final long serialVersionUID = -6762910422159637258L;

    /**
     * Constructs a {@code StringIndexOutOfBoundsException} with no
     * detail message.
     *
     * <p>
     *  构造一个没有详细消息的{@code StringIndexOutOfBoundsException}。
     * 
     * 
     * @since   JDK1.0.
     */
    public StringIndexOutOfBoundsException() {
        super();
    }

    /**
     * Constructs a {@code StringIndexOutOfBoundsException} with
     * the specified detail message.
     *
     * <p>
     *  构造具有指定详细消息的{@code StringIndexOutOfBoundsException}。
     * 
     * 
     * @param   s   the detail message.
     */
    public StringIndexOutOfBoundsException(String s) {
        super(s);
    }

    /**
     * Constructs a new {@code StringIndexOutOfBoundsException}
     * class with an argument indicating the illegal index.
     *
     * <p>
     *  构造一个带有指示非法索引的参数的新{@code StringIndexOutOfBoundsException}类。
     * 
     * @param   index   the illegal index.
     */
    public StringIndexOutOfBoundsException(int index) {
        super("String index out of range: " + index);
    }
}
