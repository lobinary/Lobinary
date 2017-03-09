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

import javax.naming.Name;

/**
  * This exception is thrown when a method
  * terminates abnormally due to a user or system specified limit.
  * This is different from a InsufficientResourceException in that
  * LimitExceededException is due to a user/system specified limit.
  * For example, running out of memory to complete the request would
  * be an insufficient resource. The client asking for 10 answers and
  * getting back 11 is a size limit exception.
  *<p>
  * Examples of these limits include client and server configuration
  * limits such as size, time, number of hops, etc.
  * <p>
  * Synchronization and serialization issues that apply to NamingException
  * apply directly here.
  *
  * <p>
  *  当方法因用户或系统指定的限制而异常终止时,将抛出此异常。
  * 这与InsufficientResourceException不同,因为LimitExceededException是由于用户/系统指定的限制。例如,内存不足以完成请求将是一个不足的资源。
  * 客户端要求10个答案并回到11是一个大小限制异常。
  * p>
  *  这些限制的示例包括客户端和服务器配置限制,如大小,时间,跳数等。
  * <p>
  *  适用于NamingException的同步和序列化问题直接应用于此处。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  * @since 1.3
  */

public class LimitExceededException extends NamingException {
    /**
     * Constructs a new instance of LimitExceededException with
      * all name resolution fields and explanation initialized to null.
      * <p>
      *  构造一个LimitExceededException的新实例,并将所有名称解析字段和说明初始化为null。
      * 
     */
    public LimitExceededException() {
        super();
    }

    /**
     * Constructs a new instance of LimitExceededException using an
     * explanation. All other fields default to null.
     * <p>
     *  使用说明构造LimitExceededException的新实例。所有其他字段默认为null。
     * 
     * 
     * @param explanation Possibly null detail about this exception.
     * @see java.lang.Throwable#getMessage
     */
    public LimitExceededException(String explanation) {
        super(explanation);
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = -776898738660207856L;
}
