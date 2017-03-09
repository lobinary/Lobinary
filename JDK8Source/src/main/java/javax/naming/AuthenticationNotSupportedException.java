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
  * This exception is thrown when
  * the particular flavor of authentication requested is not supported.
  * For example, if the program
  * is attempting to use strong authentication but the directory/naming
  * supports only simple authentication, this exception would be thrown.
  * Identification of a particular flavor of authentication is
  * provider- and server-specific. It may be specified using
  * specific authentication schemes such
  * those identified using SASL, or a generic authentication specifier
  * (such as "simple" and "strong").
  *<p>
  * If the program wants to handle this exception in particular, it
  * should catch AuthenticationNotSupportedException explicitly before
  * attempting to catch NamingException. After catching
  * <code>AuthenticationNotSupportedException</code>, the program could
  * reattempt the authentication using a different authentication flavor
  * by updating the resolved context's environment properties accordingly.
  * <p>
  * Synchronization and serialization issues that apply to NamingException
  * apply directly here.
  *
  * <p>
  *  当不支持所请求的身份验证的特定风格时,抛出此异常。例如,如果程序尝试使用强身份验证,但目录/命名仅支持简单身份验证,则会抛出此异常。识别特定的认证风格是特定于提供者和服务器的。
  * 它可以使用诸如使用SASL标识的特定认证方案或通用认证说明符(诸如"简单"和"强")来指定。
  * p>
  *  如果程序想特别处理这个异常,它应该在捕获NamingException之前显式地捕获AuthenticationNotSupportedException。
  * 捕获<code> AuthenticationNotSupportedException </code>之后,程序可以通过相应地更新已解析的上下文的环境属性,使用不同的身份验证风格重新尝试身份验证。
  * <p>
  *  适用于NamingException的同步和序列化问题直接应用于此处。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  * @since 1.3
  */

public class AuthenticationNotSupportedException extends NamingSecurityException {
    /**
     * Constructs a new instance of AuthenticationNotSupportedException using
     * an explanation. All other fields default to null.
     *
     * <p>
     *  使用解释构造AuthenticationNotSupportedException的新实例。所有其他字段默认为null。
     * 
     * 
     * @param   explanation     A possibly null string containing additional
     *                          detail about this exception.
     * @see java.lang.Throwable#getMessage
     */
    public AuthenticationNotSupportedException(String explanation) {
        super(explanation);
    }

    /**
      * Constructs a new instance of AuthenticationNotSupportedException
      * all name resolution fields and explanation initialized to null.
      * <p>
      *  构造一个AuthenticationNotSupportedException的新实例,将所有名称解析字段和说明初始化为null。
      * 
      */
    public AuthenticationNotSupportedException() {
        super();
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = -7149033933259492300L;
}
