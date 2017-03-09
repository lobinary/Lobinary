/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * Thrown to indicate that an attempt has been made to store the
 * wrong type of object into an array of objects. For example, the
 * following code generates an <code>ArrayStoreException</code>:
 * <blockquote><pre>
 *     Object x[] = new String[3];
 *     x[0] = new Integer(0);
 * </pre></blockquote>
 *
 * <p>
 *  抛出以表示已尝试将错误类型的对象存储到对象数组中。
 * 例如,以下代码生成<code> ArrayStoreException </code>：<blockquote> <pre> Object x [] = new String [3]; x [0] = 
 * new Integer(0); </pre> </blockquote>。
 *  抛出以表示已尝试将错误类型的对象存储到对象数组中。
 * 
 * 
 * @author  unascribed
 * @since   JDK1.0
 */
public
class ArrayStoreException extends RuntimeException {
    private static final long serialVersionUID = -4522193890499838241L;

    /**
     * Constructs an <code>ArrayStoreException</code> with no detail message.
     * <p>
     *  构造一个没有详细消息的<code> ArrayStoreException </code>。
     * 
     */
    public ArrayStoreException() {
        super();
    }

    /**
     * Constructs an <code>ArrayStoreException</code> with the specified
     * detail message.
     *
     * <p>
     *  使用指定的详细消息构造一个<code> ArrayStoreException </code>。
     * 
     * @param   s   the detail message.
     */
    public ArrayStoreException(String s) {
        super(s);
    }
}
