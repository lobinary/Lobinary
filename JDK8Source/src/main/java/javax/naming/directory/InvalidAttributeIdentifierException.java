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
  * made to add to create an attribute with an invalid attribute identifier.
  * The validity of an attribute identifier is directory-specific.
  * <p>
  * Synchronization and serialization issues that apply to NamingException
  * apply directly here.
  *
  * <p>
  *  当尝试添加以创建具有无效属性标识符的属性时,抛出此异常。属性标识符的有效性是特定于目录的。
  * <p>
  *  适用于NamingException的同步和序列化问题直接应用于此处。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  * @since 1.3
  */

public class InvalidAttributeIdentifierException extends NamingException {
    /**
     * Constructs a new instance of InvalidAttributeIdentifierException using the
     * explanation supplied. All other fields set to null.
     * <p>
     *  使用提供的解释构造InvalidAttributeIdentifierException的新实例。所有其他字段设置为null。
     * 
     * 
     * @param   explanation     Possibly null string containing additional detail about this exception.
     * @see java.lang.Throwable#getMessage
     */
    public InvalidAttributeIdentifierException(String explanation) {
        super(explanation);
    }

    /**
      * Constructs a new instance of InvalidAttributeIdentifierException.
      * All fields are set to null.
      * <p>
      *  构造InvalidAttributeIdentifierException的新实例。所有字段都设置为null。
      * 
      */
    public InvalidAttributeIdentifierException() {
        super();
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = -9036920266322999923L;
}
