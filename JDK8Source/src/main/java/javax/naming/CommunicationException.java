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
  * This exception is thrown when the client is
  * unable to communicate with the directory or naming service.
  * The inability to communicate with the service might be a result
  * of many factors, such as network partitioning, hardware or interface problems,
  * failures on either the client or server side.
  * This exception is meant to be used to capture such communication problems.
  * <p>
  * Synchronization and serialization issues that apply to NamingException
  * apply directly here.
  *
  * <p>
  *  当客户端无法与目录或命名服务通信时抛出此异常。无法与服务通信可能是许多因素的结果,例如网络分区,硬件或接口问题,客户端或服务器端的故障。此异常用于捕获此类通信问题。
  * <p>
  *  适用于NamingException的同步和序列化问题直接应用于此处。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  * @since 1.3
  */
public class CommunicationException extends NamingException {
    /**
     * Constructs a new instance of CommunicationException using the
     * arguments supplied.
     *
     * <p>
     *  使用提供的参数构造CommunicationException的新实例。
     * 
     * 
     * @param   explanation     Additional detail about this exception.
     * @see java.lang.Throwable#getMessage
     */
    public CommunicationException(String explanation) {
        super(explanation);
    }

    /**
      * Constructs a new instance of CommunicationException.
      * <p>
      *  构造CommunicationException的新实例。
      * 
      */
    public CommunicationException() {
        super();
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = 3618507780299986611L;
}
