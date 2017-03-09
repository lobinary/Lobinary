/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 1999, Oracle and/or its affiliates. All rights reserved.
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


package org.omg.CORBA.DynAnyPackage;

/**
 * TypeMismatch is thrown by dynamic any accessor methods when
 * type of the actual contents do not match what is trying to be
 * accessed.
 * <p>
 *  当实际内容的类型与尝试访问的内容不匹配时,通过动态任何访问器方法抛出TypeMismatch。
 * 
 */
public final class TypeMismatch
    extends org.omg.CORBA.UserException {

    /**
     * Constructs a <code>TypeMismatch</code> object.
     * <p>
     *  构造一个<code> TypeMismatch </code>对象。
     * 
     */
    public TypeMismatch() {
        super();
    }

    /**
     * Constructs a <code>TypeMismatch</code> object.
     * <p>
     *  构造一个<code> TypeMismatch </code>对象。
     * 
     * @param reason  a <code>String</code> giving more information
     * regarding the exception.
     */
    public TypeMismatch(String reason) {
        super(reason);
    }
}
