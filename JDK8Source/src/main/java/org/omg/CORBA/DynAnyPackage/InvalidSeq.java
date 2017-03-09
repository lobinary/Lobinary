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
 * The InvalidSeq exception is thrown by all operations on dynamic
 * anys that take a sequence (Java array) as an argument, when that
 * sequence is invalid.
 * <p>
 *  对于将序列(Java数组)用作参数的动态任何操作,当序列无效时,将抛出InvalidSeq异常。
 * 
 */
public final class InvalidSeq
    extends org.omg.CORBA.UserException {

    /**
     * Constructs an <code>InvalidSeq</code> object.
     * <p>
     *  构造一个<code> InvalidSeq </code>对象。
     * 
     */
    public InvalidSeq() {
        super();
    }

    /**
     * Constructs an <code>InvalidSeq</code> object.
     * <p>
     *  构造一个<code> InvalidSeq </code>对象。
     * 
     * @param reason  a <code>String</code> giving more information
     * regarding the exception.
     */
    public InvalidSeq(String reason) {
        super(reason);
    }
}
