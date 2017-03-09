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
  * This class is thrown when an attempt is
  * made to add to an attribute a value that conflicts with the attribute's
  * schema definition.  This could happen, for example, if attempting
  * to add an attribute with no value when the attribute is required
  * to have at least one value, or if attempting to add more than
  * one value to a single valued-attribute, or if attempting to
  * add a value that conflicts with the syntax of the attribute.
  * <p>
  * Synchronization and serialization issues that apply to NamingException
  * apply directly here.
  *
  * <p>
  *  当尝试向属性添加与属性的模式定义冲突的值时,会抛出此类。
  * 这可能发生,例如,如果试图添加一个没有值的属性,当属性需要有至少一个值,或者如果试图添加多个值到单个值属性,或者如果尝试添加与属性的语法冲突的值。
  * <p>
  *  适用于NamingException的同步和序列化问题直接应用于此处。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  * @since 1.3
  */

public class InvalidAttributeValueException extends NamingException {
    /**
     * Constructs a new instance of InvalidAttributeValueException using
     * an explanation. All other fields are set to null.
     * <p>
     *  使用解释构造InvalidAttributeValueException的新实例。所有其他字段都设置为null。
     * 
     * 
     * @param   explanation     Additional detail about this exception. Can be null.
     * @see java.lang.Throwable#getMessage
     */
    public InvalidAttributeValueException(String explanation) {
        super(explanation);
    }

    /**
      * Constructs a new instance of InvalidAttributeValueException.
      * All fields are set to null.
      * <p>
      *  构造InvalidAttributeValueException的新实例。所有字段都设置为null。
      * 
      */
    public InvalidAttributeValueException() {
        super();
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = 8720050295499275011L;
}
