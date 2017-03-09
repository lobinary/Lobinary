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
  * This exception is thrown when there is a configuration problem.
  * This can arise when installation of a provider was
  * not done correctly, or if there are configuration problems with the
  * server, or if configuration information required to access
  * the provider or service is malformed or missing.
  * For example, a request to use SSL as the security protocol when
  * the service provider software was not configured with the SSL
  * component would cause such an exception. Another example is
  * if the provider requires that a URL be specified as one of the
  * environment properties but the client failed to provide it.
  * <p>
  * Synchronization and serialization issues that apply to NamingException
  * apply directly here.
  *
  * <p>
  *  当有配置问题时抛出此异常。当安装提供程序未正确完成,或者服务器存在配置问题,或者访问提供程序或服务所需的配置信息格式错误或缺失时,可能会出现这种情况。
  * 例如,当服务提供商软件未配置SSL组件时,使用SSL作为安全协议的请求将导致此类异常。另一个示例是,如果提供程序要求将URL指定为其中一个环境属性,但客户端未能提供它。
  * <p>
  *  适用于NamingException的同步和序列化问题直接应用于此处。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  * @since 1.3
  */
public class ConfigurationException extends NamingException {
    /**
     * Constructs a new instance of ConfigurationException using an
     * explanation. All other fields default to null.
     *
     * <p>
     *  使用说明构造ConfigurationException的新实例。所有其他字段默认为null。
     * 
     * 
     * @param   explanation     A possibly null string containing
     *                          additional detail about this exception.
     * @see java.lang.Throwable#getMessage
     */
    public ConfigurationException(String explanation) {
        super(explanation);
    }

    /**
      * Constructs a new instance of ConfigurationException with
      * all name resolution fields and explanation initialized to null.
      * <p>
      *  构造一个ConfigurationException的新实例,并将所有名称解析字段和说明初始化为null。
      * 
      */
    public ConfigurationException() {
        super();
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = -2535156726228855704L;
}
