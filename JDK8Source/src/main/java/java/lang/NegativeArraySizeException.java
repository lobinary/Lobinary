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
 * Thrown if an application tries to create an array with negative size.
 *
 * <p>
 *  如果应用程序尝试创建具有负大小的数组,则抛出。
 * 
 * 
 * @author  unascribed
 * @since   JDK1.0
 */
public
class NegativeArraySizeException extends RuntimeException {
    private static final long serialVersionUID = -8960118058596991861L;

    /**
     * Constructs a <code>NegativeArraySizeException</code> with no
     * detail message.
     * <p>
     *  构造一个没有详细消息的<code> NegativeArraySizeException </code>。
     * 
     */
    public NegativeArraySizeException() {
        super();
    }

    /**
     * Constructs a <code>NegativeArraySizeException</code> with the
     * specified detail message.
     *
     * <p>
     *  使用指定的详细消息构造一个<code> NegativeArraySizeException </code>。
     * 
     * @param   s   the detail message.
     */
    public NegativeArraySizeException(String s) {
        super(s);
    }
}
