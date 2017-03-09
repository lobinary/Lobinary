/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
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
 * Thrown to indicate that an index of some sort (such as to an array, to a
 * string, or to a vector) is out of range.
 * <p>
 * Applications can subclass this class to indicate similar exceptions.
 *
 * <p>
 *  抛出以指示某种类型的索引(例如,数组,字符串或向量)超出范围。
 * <p>
 *  应用程序可以对此类进行子类化以指示类似的异常。
 * 
 * 
 * @author  Frank Yellin
 * @since   JDK1.0
 */
public
class IndexOutOfBoundsException extends RuntimeException {
    private static final long serialVersionUID = 234122996006267687L;

    /**
     * Constructs an <code>IndexOutOfBoundsException</code> with no
     * detail message.
     * <p>
     *  构造一个没有详细消息的<code> IndexOutOfBoundsException </code>。
     * 
     */
    public IndexOutOfBoundsException() {
        super();
    }

    /**
     * Constructs an <code>IndexOutOfBoundsException</code> with the
     * specified detail message.
     *
     * <p>
     *  用指定的详细消息构造一个<code> IndexOutOfBoundsException </code>。
     * 
     * @param   s   the detail message.
     */
    public IndexOutOfBoundsException(String s) {
        super(s);
    }
}
