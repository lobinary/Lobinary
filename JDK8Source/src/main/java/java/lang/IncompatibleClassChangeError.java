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
 * Thrown when an incompatible class change has occurred to some class
 * definition. The definition of some class, on which the currently
 * executing method depends, has since changed.
 *
 * <p>
 *  当一些类定义发生不兼容的类更改时抛出。当前执行的方法所依赖的一些类的定义已经改变。
 * 
 * 
 * @author  unascribed
 * @since   JDK1.0
 */
public
class IncompatibleClassChangeError extends LinkageError {
    private static final long serialVersionUID = -4914975503642802119L;

    /**
     * Constructs an <code>IncompatibleClassChangeError</code> with no
     * detail message.
     * <p>
     *  构造一个没有详细消息的<code> IncompatibleClassChangeError </code>。
     * 
     */
    public IncompatibleClassChangeError () {
        super();
    }

    /**
     * Constructs an <code>IncompatibleClassChangeError</code> with the
     * specified detail message.
     *
     * <p>
     *  使用指定的详细消息构造<code> IncompatibleClassChangeError </code>。
     * 
     * @param   s   the detail message.
     */
    public IncompatibleClassChangeError(String s) {
        super(s);
    }
}
