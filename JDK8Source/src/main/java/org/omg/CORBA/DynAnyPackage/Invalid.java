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
 * Invalid is thrown by dynamic any operations when a bad
 * <code>DynAny</code> or <code>Any</code> is passed as a parameter.
 * <p>
 *  当将<code> DynAny </code>或<code> Any </code>作为参数传递时,动态任何操作都会抛出无效。
 * 
 */
public final class Invalid
    extends org.omg.CORBA.UserException {

    /**
     * Constructs an <code>Invalid</code> object.
     * <p>
     *  构造<code>无效</code>对象。
     * 
     */
    public Invalid() {
        super();
    }

    /**
     * Constructs an <code>Invalid</code> object.
     * <p>
     *  构造<code>无效</code>对象。
     * 
     * @param reason a <code>String</code> giving more information
     * regarding the bad parameter passed to a dynamic any operation.
     */
    public Invalid(String reason) {
        super(reason);
    }
}
