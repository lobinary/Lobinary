/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2001, Oracle and/or its affiliates. All rights reserved.
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
  * This exception is thrown when attempting to communicate with a
  * directory or naming service and that service is not available.
  * It might be unavailable for different reasons. For example,
  * the server might be too busy to service the request, or the server
  * might not be registered to service any requests, etc.
  * <p>
  * Synchronization and serialization issues that apply to NamingException
  * apply directly here.
  *
  * <p>
  *  尝试与目录或命名服务通信并且该服务不可用时抛出此异常。它可能由于不同的原因不可用。例如,服务器可能太忙,无法为请求提供服务,或者服务器可能未注册为服务任何请求等。
  * <p>
  *  适用于NamingException的同步和序列化问题直接应用于此处。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  *
  * @since 1.3
  */

public class ServiceUnavailableException extends NamingException {
    /**
     * Constructs a new instance of ServiceUnavailableException using an
     * explanation. All other fields default to null.
     *
     * <p>
     *  使用解释构造ServiceUnavailableException的新实例。所有其他字段默认为null。
     * 
     * 
     * @param   explanation     Possibly null additional detail about this exception.
     * @see java.lang.Throwable#getMessage
     */
    public ServiceUnavailableException(String explanation) {
        super(explanation);
    }

    /**
      * Constructs a new instance of ServiceUnavailableException.
      * All fields default to null.
      * <p>
      *  构造ServiceUnavailableException的新实例。所有字段默认为null。
      * 
      */
    public ServiceUnavailableException() {
        super();
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = -4996964726566773444L;
}
