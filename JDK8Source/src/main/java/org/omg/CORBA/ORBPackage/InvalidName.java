/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2000, Oracle and/or its affiliates. All rights reserved.
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

package org.omg.CORBA.ORBPackage;

/**
 * The <code>InvalidName</code> exception is raised when
 * <code>ORB.resolve_initial_references</code> is passed a name
 * for which there is no initial reference.
 *
 * <p>
 *  当<code> ORB.resolve_initial_references </code>传递一个没有初始引用的名称时,会引发<code> InvalidName </code>异常。
 * 
 * 
 * @see org.omg.CORBA.ORB#resolve_initial_references(String)
 * @since   JDK1.2
 */

final public class InvalidName extends org.omg.CORBA.UserException {
    /**
     * Constructs an <code>InvalidName</code> exception with no reason message.
     * <p>
     *  构造一个没有原因消息的<code> InvalidName </code>异常。
     * 
     */
    public InvalidName() {
        super();
    }

    /**
     * Constructs an <code>InvalidName</code> exception with the specified
     * reason message.
     * <p>
     *  使用指定的原因消息构造<code> InvalidName </code>异常。
     * 
     * @param reason the String containing a reason message
     */
    public InvalidName(String reason) {
        super(reason);
    }
}
