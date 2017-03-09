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
 * Thrown to indicate that the <code>clone</code> method in class
 * <code>Object</code> has been called to clone an object, but that
 * the object's class does not implement the <code>Cloneable</code>
 * interface.
 * <p>
 * Applications that override the <code>clone</code> method can also
 * throw this exception to indicate that an object could not or
 * should not be cloned.
 *
 * <p>
 *  抛出以指示类<code> Object </code>中的<code> clone </code>方法已被调用以克隆对象,但该对象的类不实现<code> Cloneable </code>接口。
 * <p>
 *  覆盖<code> clone </code>方法的应用程序也可以抛出此异常,以指示不能或不应克隆对象。
 * 
 * 
 * @author  unascribed
 * @see     java.lang.Cloneable
 * @see     java.lang.Object#clone()
 * @since   JDK1.0
 */

public
class CloneNotSupportedException extends Exception {
    private static final long serialVersionUID = 5195511250079656443L;

    /**
     * Constructs a <code>CloneNotSupportedException</code> with no
     * detail message.
     * <p>
     *  构造一个没有详细消息的<code> CloneNotSupportedException </code>。
     * 
     */
    public CloneNotSupportedException() {
        super();
    }

    /**
     * Constructs a <code>CloneNotSupportedException</code> with the
     * specified detail message.
     *
     * <p>
     *  使用指定的详细消息构造一个<code> CloneNotSupportedException </code>。
     * 
     * @param   s   the detail message.
     */
    public CloneNotSupportedException(String s) {
        super(s);
    }
}
