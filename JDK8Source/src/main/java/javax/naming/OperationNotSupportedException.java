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
  * This exception is thrown when a context implementation does not support
  * the operation being invoked.
  * For example, if a server does not support the Context.bind() method
  * it would throw OperationNotSupportedException when the bind() method
  * is invoked on it.
  * <p>
  * Synchronization and serialization issues that apply to NamingException
  * apply directly here.
  *
  * <p>
  *  当上下文实现不支持调用的操作时,抛出此异常。例如,如果服务器不支持Context.bind()方法,当调用bind()方法时,它将抛出OperationNotSupportedException。
  * <p>
  *  适用于NamingException的同步和序列化问题直接应用于此处。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  * @since 1.3
  */

public class OperationNotSupportedException extends NamingException {
    /**
      * Constructs a new instance of OperationNotSupportedException.
      * All fields default to null.
      * <p>
      *  构造OperationNotSupportedException的新实例。所有字段默认为null。
      * 
      */
    public OperationNotSupportedException() {
        super();
    }

    /**
      * Constructs a new instance of OperationNotSupportedException using an
      * explanation. All other fields default to null.
      *
      * <p>
      *  使用解释构造OperationNotSupportedException的新实例。所有其他字段默认为null。
      * 
      * 
      * @param  explanation     Possibly null additional detail about this exception
      * @see java.lang.Throwable#getMessage
      */
    public OperationNotSupportedException(String explanation) {
        super(explanation);
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = 5493232822427682064L;
}
