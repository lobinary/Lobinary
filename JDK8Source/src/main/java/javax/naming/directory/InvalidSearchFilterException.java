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
  * This exception is thrown when the specification of
  * a search filter is invalid.  The expression of the filter may
  * be invalid, or there may be a problem with one of the parameters
  * passed to the filter.
  * <p>
  * Synchronization and serialization issues that apply to NamingException
  * apply directly here.
  *
  * <p>
  *  当搜索过滤器的规范无效时抛出此异常。过滤器的表达式可能无效,或者传递给过滤器的参数之一可能存在问题。
  * <p>
  *  适用于NamingException的同步和序列化问题直接应用于此处。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  * @since 1.3
  */
public class InvalidSearchFilterException extends NamingException {
    /**
     * Constructs a new instance of InvalidSearchFilterException.
     * All fields are set to null.
     * <p>
     *  构造InvalidSearchFilterException的新实例。所有字段都设置为null。
     * 
     */
    public InvalidSearchFilterException() {
        super();
    }

    /**
     * Constructs a new instance of InvalidSearchFilterException
     * with an explanation. All other fields are set to null.
     * <p>
     *  构造一个带有解释的InvalidSearchFilterException的新实例。所有其他字段都设置为null。
     * 
     * 
     * @param msg Detail about this exception. Can be null.
     * @see java.lang.Throwable#getMessage
     */
    public InvalidSearchFilterException(String msg) {
        super(msg);
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = 2902700940682875441L;
}
