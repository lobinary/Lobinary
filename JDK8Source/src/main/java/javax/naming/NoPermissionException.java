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

package javax.naming;

/**
  * This exception is thrown when attempting to perform an operation
  * for which the client has no permission. The access control/permission
  * model is dictated by the directory/naming server.
  *
  * <p>
  * Synchronization and serialization issues that apply to NamingException
  * apply directly here.
  *
  * <p>
  *  尝试执行客户端没有权限的操作时抛出此异常。访问控制/权限模型由目录/命名服务器指定。
  * 
  * <p>
  *  适用于NamingException的同步和序列化问题直接应用于此处。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  * @since 1.3
  */

public class NoPermissionException extends NamingSecurityException {
    /**
     * Constructs a new instance of NoPermissionException using an
     * explanation. All other fields default to null.
     *
     * <p>
     *  使用解释构造一个NoPermissionException的新实例。所有其他字段默认为null。
     * 
     * 
     * @param   explanation     Possibly null additional detail about this exception.
     */
    public NoPermissionException(String explanation) {
        super(explanation);
    }

    /**
      * Constructs a new instance of NoPermissionException.
      * All fields are initialized to null.
      * <p>
      *  构造一个新的NoPermissionException实例。所有字段都初始化为null。
      * 
      */
    public NoPermissionException() {
        super();
    }
    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = 8395332708699751775L;
}
