/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, Oracle and/or its affiliates. All rights reserved.
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

package javax.naming.directory;

import javax.naming.NamingException;

/**
  * This exception is thrown when an attempt is
  * made to add or modify an attribute set that has been specified
  * incompletely or incorrectly. This could happen, for example,
  * when attempting to add or modify a binding, or to create a new
  * subcontext without specifying all the mandatory attributes
  * required for creation of the object.  Another situation in
  * which this exception is thrown is by specification of incompatible
  * attributes within the same attribute set, or attributes in conflict
  * with that specified by the object's schema.
  * <p>
  * Synchronization and serialization issues that apply to NamingException
  * apply directly here.
  *
  * <p>
  *  尝试添加或修改已指定的不完整或不正确的属性集时,将抛出此异常。这可能发生,例如,当尝试添加或修改绑定,或创建新的子上下文,而没有指定创建对象所需的所有必需属性。
  * 抛出此异常的另一种情况是指定同一属性集中的不兼容属性,或与对象模式指定的属性冲突的属性。
  * <p>
  *  适用于NamingException的同步和序列化问题直接应用于此处。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  * @since 1.3
  */

public class InvalidAttributesException extends NamingException {
    /**
     * Constructs a new instance of InvalidAttributesException using an
     * explanation. All other fields are set to null.
     * <p>
     *  使用解释构造InvalidAttributesException的新实例。所有其他字段都设置为null。
     * 
     * 
     * @param   explanation     Additional detail about this exception. Can be null.
     * @see java.lang.Throwable#getMessage
     */
    public InvalidAttributesException(String explanation) {
        super(explanation);
    }

    /**
      * Constructs a new instance of InvalidAttributesException.
      * All fields are set to null.
      * <p>
      *  构造InvalidAttributesException的新实例。所有字段都设置为null。
      * 
      */
    public InvalidAttributesException() {
        super();
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = 2607612850539889765L;
}
