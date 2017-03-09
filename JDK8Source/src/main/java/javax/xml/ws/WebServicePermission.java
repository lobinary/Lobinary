/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2011, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.ws;

import java.security.BasicPermission;

/**
 * This class defines web service permissions.
 * <p>
 * Web service Permissions are identified by name (also referred to as
 * a "target name") alone. There are no actions associated
 * with them.
 * <p>
 * The following permission target name is defined:
 * <p>
 * <dl>
 *   <dt>publishEndpoint
 * </dl>
 * <p>
 * The <code>publishEndpoint</code> permission allows publishing a
 * web service endpoint using the <code>publish</code> methods
 * defined by the <code>javax.xml.ws.Endpoint</code> class.
 * <p>
 * Granting <code>publishEndpoint</code> allows the application to be
 * exposed as a network service. Depending on the security of the runtime and
 * the security of the application, this may introduce a security hole that
 * is remotely exploitable.
 *
 * <p>
 *  此类定义Web服务权限。
 * <p>
 *  Web服务权限由名称(也称为"目标名称")单独标识。没有与它们相关联的操作。
 * <p>
 *  定义了以下权限目标名称：
 * <p>
 * <dl>
 *  <dt> publishEndpoint
 * </dl>
 * <p>
 *  <code> publishEndpoint </code>权限允许使用<code> javax.xml.ws.Endpoint </code>类定义的<code> publish </code>方法
 * 发布Web服务端点。
 * 
 * @see javax.xml.ws.Endpoint
 * @see java.security.BasicPermission
 * @see java.security.Permission
 * @see java.security.Permissions
 * @see java.lang.SecurityManager
 * @see java.net.SocketPermission
 */
public final class WebServicePermission extends BasicPermission {

    private static final long serialVersionUID = -146474640053770988L;

    /**
     * Creates a new permission with the specified name.
     *
     * <p>
     * <p>
     *  授予<code> publishEndpoint </code>允许将应用程序公开为网络服务。根据运行时的安全性和应用程序的安全性,这可能引入可远程利用的安全漏洞。
     * 
     * 
     * @param name the name of the <code>WebServicePermission</code>
     */
    public WebServicePermission(String name) {
        super(name);
    }

    /**
     * Creates a new permission with the specified name and actions.
     *
     * The <code>actions</code> parameter is currently unused and
     * it should be <code>null</code>.
     *
     * <p>
     *  创建具有指定名称的新权限。
     * 
     * 
     * @param name the name of the <code>WebServicePermission</code>
     * @param actions should be <code>null</code>
     */
    public WebServicePermission(String name, String actions) {
        super(name, actions);
    }

}
