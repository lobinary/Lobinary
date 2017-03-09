/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2007, Oracle and/or its affiliates. All rights reserved.
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

package javax.management.remote;

import java.security.Principal;
import javax.security.auth.Subject;

/**
 * <p>Interface to define how remote credentials are converted into a
 * JAAS Subject.  This interface is used by the RMI Connector Server,
 * and can be used by other connector servers.</p>
 *
 * <p>The user-defined authenticator instance is passed to the
 * connector server in the environment map as the value of the
 * attribute {@link JMXConnectorServer#AUTHENTICATOR}.  For connector
 * servers that use only this authentication system, if this attribute
 * is not present or its value is <code>null</code> then no user
 * authentication will be performed and full access to the methods
 * exported by the <code>MBeanServerConnection</code> object will be
 * allowed.</p>
 *
 * <p>If authentication is successful then an authenticated
 * {@link Subject subject} filled in with its associated
 * {@link Principal principals} is returned. Authorization checks
 * will be then performed based on the given set of principals.</p>
 *
 * <p>
 *  <p>用于定义如何将远程凭据转换为JAAS主题的接口。此接口由RMI连接器服务器使用,可由其他连接器服务器使用。</p>
 * 
 *  <p>用户定义的认证器实例作为属性{@link JMXConnectorServer#AUTHENTICATOR}的值传递到环境映射中的连接器服务器。
 * 对于仅使用此身份验证系统的连接器服务器,如果此属性不存在或其值为<code> null </code>,则不会执行用户身份验证,并且完全访问由<code> MBeanServerConnection <代码>
 * 对象。
 *  <p>用户定义的认证器实例作为属性{@link JMXConnectorServer#AUTHENTICATOR}的值传递到环境映射中的连接器服务器。</p>。
 * 
 * @since 1.5
 */
public interface JMXAuthenticator {

    /**
     * <p>Authenticates the <code>MBeanServerConnection</code> client
     * with the given client credentials.</p>
     *
     * <p>
     * 
     *  <p>如果身份验证成功,那么将返回已与其关联的{@link主体}填写的已验证{@link主题主题}。然后将根据给定的主体集执行授权检查。</p>
     * 
     * 
     * @param credentials the user-defined credentials to be passed
     * into the server in order to authenticate the user before
     * creating the <code>MBeanServerConnection</code>.  The actual
     * type of this parameter, and whether it can be null, depends on
     * the connector.
     *
     * @return the authenticated subject containing its associated principals.
     *
     * @exception SecurityException if the server cannot authenticate the user
     * with the provided credentials.
     */
    public Subject authenticate(Object credentials);
}
