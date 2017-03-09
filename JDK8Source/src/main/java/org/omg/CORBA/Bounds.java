/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2006, Oracle and/or its affiliates. All rights reserved.
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

package org.omg.CORBA;

/**
 * A user exception thrown when a parameter is not within
 * the legal bounds for the object that a method is trying
 * to access.
 *
 * <p>
 *  当参数不在方法尝试访问的对象的法律边界内时抛出的用户异常。
 * 
 * 
 * @see <A href="../../../../technotes/guides/idl/jidlExceptions.html">documentation on
 * Java&nbsp;IDL exceptions</A>
 */

public final class Bounds extends org.omg.CORBA.UserException {

    /**
     * Constructs an <code>Bounds</code> with no specified detail message.
     * <p>
     *  构造一个没有指定详细消息的<code> Bounds </code>。
     * 
     */
    public Bounds() {
        super();
    }

    /**
     * Constructs an <code>Bounds</code> with the specified detail message.
     *
     * <p>
     *  用指定的详细消息构造一个<code> Bounds </code>。
     * 
     * @param   reason   the detail message.
     */
    public Bounds(String reason) {
        super(reason);
    }
}
