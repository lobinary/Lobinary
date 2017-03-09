/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1994, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * Thrown to indicate that the code has attempted to cast an object
 * to a subclass of which it is not an instance. For example, the
 * following code generates a <code>ClassCastException</code>:
 * <blockquote><pre>
 *     Object x = new Integer(0);
 *     System.out.println((String)x);
 * </pre></blockquote>
 *
 * <p>
 *  抛出以指示代码已尝试将对象强制转换为其不是实例的子类。
 * 例如,以下代码生成<code> ClassCastException </code>：<blockquote> <pre> Object x = new Integer(0); System.out.p
 * rintln((String)x); </pre> </blockquote>。
 *  抛出以指示代码已尝试将对象强制转换为其不是实例的子类。
 * 
 * 
 * @author  unascribed
 * @since   JDK1.0
 */
public
class ClassCastException extends RuntimeException {
    private static final long serialVersionUID = -9223365651070458532L;

    /**
     * Constructs a <code>ClassCastException</code> with no detail message.
     * <p>
     *  构造一个没有详细消息的<code> ClassCastException </code>。
     * 
     */
    public ClassCastException() {
        super();
    }

    /**
     * Constructs a <code>ClassCastException</code> with the specified
     * detail message.
     *
     * <p>
     *  构造具有指定详细消息的<code> ClassCastException </code>。
     * 
     * @param   s   the detail message.
     */
    public ClassCastException(String s) {
        super(s);
    }
}
