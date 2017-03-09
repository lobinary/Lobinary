/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 1999, Oracle and/or its affiliates. All rights reserved.
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

package org.omg.CORBA.TypeCodePackage;

/**
 * The exception <code>BadKind</code> is thrown when
 * an inappropriate operation is invoked on a <code>TypeCode</code> object. For example,
 * invoking the method <code>discriminator_type()</code> on an instance of
 * <code>TypeCode</code> that does not represent an IDL union will cause the
 * exception <code>BadKind</code> to be thrown.
 *
 * <p>
 *  在<code> TypeCode </code>对象上调用不当操作时抛出异常<code> BadKind </code>。
 * 例如,调用不代表IDL联合的<code> TypeCode </code>实例上的方法<code> discriminator_type()</code>将导致抛出异常<code> BadKind </code>
 *  。
 *  在<code> TypeCode </code>对象上调用不当操作时抛出异常<code> BadKind </code>。
 * 
 * 
 * @see org.omg.CORBA.TypeCode
 * @since   JDK1.2
 */

public final class BadKind extends org.omg.CORBA.UserException {
    /**
     * Constructs a <code>BadKind</code> exception with no reason message.
     * <p>
     *  构造一个没有原因消息的<code> BadKind </code>异常。
     * 
     */
    public BadKind() {
        super();
    }

    /**
     * Constructs a <code>BadKind</code> exception with the specified
     * reason message.
     * <p>
     *  使用指定的原因消息构造一个<code> BadKind </code>异常。
     * 
     * @param reason the String containing a reason message
     */
    public BadKind(String reason) {
        super(reason);
    }
}
