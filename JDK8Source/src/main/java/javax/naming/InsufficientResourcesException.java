/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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
  * This exception is thrown when resources are not available to complete
  * the requested operation. This might due to a lack of resources on
  * the server or on the client. There are no restrictions to resource types,
  * as different services might make use of different resources. Such
  * restrictions might be due to physical limits and/or administrative quotas.
  * Examples of limited resources are internal buffers, memory, network bandwidth.
  *<p>
  * InsufficientResourcesException is different from LimitExceededException in that
  * the latter is due to user/system specified limits. See LimitExceededException
  * for details.
  * <p>
  * Synchronization and serialization issues that apply to NamingException
  * apply directly here.
  *
  * <p>
  *  当资源不可用于完成请求的操作时,抛出此异常。这可能是由于服务器或客户端上缺少资源。对资源类型没有限制,因为不同的服务可能使用不同的资源。此类限制可能是由于物理限制和/或管理配额。
  * 有限资源的示例是内部缓冲器,存储器,网络带宽。
  * p>
  *  InsufficientResourcesException不同于LimitExceededException,因为后者是由于用户/系统指定的限制。
  * 有关详细信息,请参阅LimitExceededException。
  * <p>
  *  适用于NamingException的同步和序列化问题直接应用于此处。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  * @since 1.3
  */

public class InsufficientResourcesException extends NamingException {
    /**
     * Constructs a new instance of InsufficientResourcesException using an
     * explanation. All other fields default to null.
     *
     * <p>
     *  使用说明构造InsufficientResourcesException的新实例。所有其他字段默认为null。
     * 
     * 
     * @param   explanation     Possibly null additional detail about this exception.
     * @see java.lang.Throwable#getMessage
     */
    public InsufficientResourcesException(String explanation) {
        super(explanation);
    }

    /**
      * Constructs a new instance of InsufficientResourcesException with
      * all name resolution fields and explanation initialized to null.
      * <p>
      *  构造一个InsufficientResourcesException的新实例,并将所有名称解析字段和说明初始化为null。
      * 
      */
    public InsufficientResourcesException() {
        super();
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = 6227672693037844532L;
}
