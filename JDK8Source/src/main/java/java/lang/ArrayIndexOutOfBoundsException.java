/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1994, 2008, Oracle and/or its affiliates. All rights reserved.
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
 * Thrown to indicate that an array has been accessed with an
 * illegal index. The index is either negative or greater than or
 * equal to the size of the array.
 *
 * <p>
 *  抛出以指示已使用非法索引访问数组。索引为负数或大于或等于数组的大小。
 * 
 * 
 * @author  unascribed
 * @since   JDK1.0
 */
public
class ArrayIndexOutOfBoundsException extends IndexOutOfBoundsException {
    private static final long serialVersionUID = -5116101128118950844L;

    /**
     * Constructs an <code>ArrayIndexOutOfBoundsException</code> with no
     * detail message.
     * <p>
     *  构造一个没有详细消息的<code> ArrayIndexOutOfBoundsException </code>。
     * 
     */
    public ArrayIndexOutOfBoundsException() {
        super();
    }

    /**
     * Constructs a new <code>ArrayIndexOutOfBoundsException</code>
     * class with an argument indicating the illegal index.
     *
     * <p>
     *  构造一个新的<code> ArrayIndexOutOfBoundsException </code>类,带有指示非法索引的参数。
     * 
     * 
     * @param   index   the illegal index.
     */
    public ArrayIndexOutOfBoundsException(int index) {
        super("Array index out of range: " + index);
    }

    /**
     * Constructs an <code>ArrayIndexOutOfBoundsException</code> class
     * with the specified detail message.
     *
     * <p>
     *  构造具有指定详细消息的<code> ArrayIndexOutOfBoundsException </code>类。
     * 
     * @param   s   the detail message.
     */
    public ArrayIndexOutOfBoundsException(String s) {
        super(s);
    }
}
