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
  * This exception is thrown when attempting to access
  * an attribute that does not exist.
  * <p>
  * Synchronization and serialization issues that apply to NamingException
  * apply directly here.
  *
  * <p>
  *  尝试访问不存在的属性时抛出此异常。
  * <p>
  *  适用于NamingException的同步和序列化问题直接应用于此处。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  * @since 1.3
  */

public class NoSuchAttributeException extends NamingException {
    /**
     * Constructs a new instance of NoSuchAttributeException using
     * an explanation. All other fields are set to null.
     * <p>
     *  使用解释构造一个新的NoSuchAttributeException实例。所有其他字段都设置为null。
     * 
     * 
     * @param   explanation     Additional detail about this exception. Can be null.
     * @see java.lang.Throwable#getMessage
     */
    public NoSuchAttributeException(String explanation) {
        super(explanation);
    }


    /**
     * Constructs a new instance of NoSuchAttributeException.
     * All fields are initialized to null.
     * <p>
     *  构造一个新的NoSuchAttributeException实例。所有字段都初始化为null。
     * 
     */
    public NoSuchAttributeException() {
        super();
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = 4836415647935888137L;
}
