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
  * the SearchControls for a search operation is invalid. For example, if the scope is
  * set to a value other than OBJECT_SCOPE, ONELEVEL_SCOPE, SUBTREE_SCOPE,
  * this exception is thrown.
  * <p>
  * Synchronization and serialization issues that apply to NamingException
  * apply directly here.
  *
  * <p>
  *  当搜索操作的SearchControls的规范无效时抛出此异常。例如,如果范围设置为除OBJECT_SCOPE,ONELEVEL_SCOPE,SUBTREE_SCOPE之外的值,则抛出此异常。
  * <p>
  *  适用于NamingException的同步和序列化问题直接应用于此处。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  * @since 1.3
  */
public class InvalidSearchControlsException extends NamingException {
    /**
     * Constructs a new instance of InvalidSearchControlsException.
     * All fields are set to null.
     * <p>
     *  构造InvalidSearchControlsException的新实例。所有字段都设置为null。
     * 
     */
    public InvalidSearchControlsException() {
        super();
    }

    /**
     * Constructs a new instance of InvalidSearchControlsException
     * with an explanation. All other fields set to null.
     * <p>
     *  构造一个带有解释的InvalidSearchControlsException的新实例。所有其他字段设置为null。
     * 
     * 
     * @param msg Detail about this exception. Can be null.
     * @see java.lang.Throwable#getMessage
     */
    public InvalidSearchControlsException(String msg) {
        super(msg);
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = -5124108943352665777L;
}
