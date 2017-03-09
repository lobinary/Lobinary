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
  * This exception is thrown when an operation attempts
  * to add an attribute that already exists.
  * <p>
  * Synchronization and serialization issues that apply to NamingException
  * apply directly here.
  *
  * <p>
  *  当操作尝试添加已存在的属性时,抛出此异常。
  * <p>
  *  适用于NamingException的同步和序列化问题直接应用于此处。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  *
  * @see DirContext#modifyAttributes
  * @since 1.3
  */
public class AttributeInUseException extends NamingException {
    /**
     * Constructs a new instance of AttributeInUseException with
     * an explanation. All other fields are set to null.
     *
     * <p>
     *  构造一个带有解释的AttributeInUseException的新实例。所有其他字段都设置为null。
     * 
     * 
     * @param   explanation     Possibly null additional detail about this exception.
     * @see java.lang.Throwable#getMessage
     */
    public AttributeInUseException(String explanation) {
        super(explanation);
    }

    /**
      * Constructs a new instance of AttributeInUseException.
      * All fields are initialized to null.
      * <p>
      *  构造AttributeInUseException的新实例。所有字段都初始化为null。
      * 
      */
    public AttributeInUseException() {
        super();
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = 4437710305529322564L;
}
