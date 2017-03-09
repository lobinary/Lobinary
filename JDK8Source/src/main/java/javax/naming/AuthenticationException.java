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
  * This exception is thrown when an authentication error occurs while
  * accessing the naming or directory service.
  * An authentication error can happen, for example, when the credentials
  * supplied by the user program is invalid or otherwise fails to
  * authenticate the user to the naming/directory service.
  *<p>
  * If the program wants to handle this exception in particular, it
  * should catch AuthenticationException explicitly before attempting to
  * catch NamingException. After catching AuthenticationException, the
  * program could reattempt the authentication by updating
  * the resolved context's environment properties with the appropriate
  * appropriate credentials.
  * <p>
  * Synchronization and serialization issues that apply to NamingException
  * apply directly here.
  *
  * <p>
  *  在访问命名或目录服务时发生认证错误时抛出此异常。例如,当由用户程序提供的凭证无效或者无法向命名/目录服务认证用户时,可能发生认证错误。
  * p>
  *  如果程序想特别处理这个异常,它应该在捕获NamingException之前显式捕获AuthenticationException。
  * 在捕获AuthenticationException之后,程序可以通过使用适当的适当凭证更新已解析的上下文的环境属性来重新尝试认证。
  * <p>
  *  适用于NamingException的同步和序列化问题直接应用于此处。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  * @since 1.3
  */

public class AuthenticationException extends NamingSecurityException {
    /**
     * Constructs a new instance of AuthenticationException using the
     * explanation supplied. All other fields default to null.
     *
     * <p>
     *  使用提供的解释构造AuthenticationException的新实例。所有其他字段默认为null。
     * 
     * 
     * @param   explanation     A possibly null string containing
     *                          additional detail about this exception.
     * @see java.lang.Throwable#getMessage
     */
    public AuthenticationException(String explanation) {
        super(explanation);
    }

    /**
      * Constructs a new instance of AuthenticationException.
      * All fields are set to null.
      * <p>
      *  构造一个AuthenticationException的新实例。所有字段都设置为null。
      * 
      */
    public AuthenticationException() {
        super();
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = 3678497619904568096L;
}
