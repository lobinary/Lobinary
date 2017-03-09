/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.net;

import java.security.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;

/**
 * This class is for various network permissions.
 * A NetPermission contains a name (also referred to as a "target name") but
 * no actions list; you either have the named permission
 * or you don't.
 * <P>
 * The target name is the name of the network permission (see below). The naming
 * convention follows the  hierarchical property naming convention.
 * Also, an asterisk
 * may appear at the end of the name, following a ".", or by itself, to
 * signify a wildcard match. For example: "foo.*" and "*" signify a wildcard
 * match, while "*foo" and "a*b" do not.
 * <P>
 * The following table lists all the possible NetPermission target names,
 * and for each provides a description of what the permission allows
 * and a discussion of the risks of granting code the permission.
 *
 * <table border=1 cellpadding=5 summary="Permission target name, what the permission allows, and associated risks">
 * <tr>
 * <th>Permission Target Name</th>
 * <th>What the Permission Allows</th>
 * <th>Risks of Allowing this Permission</th>
 * </tr>
 * <tr>
 *   <td>allowHttpTrace</td>
 *   <td>The ability to use the HTTP TRACE method in HttpURLConnection.</td>
 *   <td>Malicious code using HTTP TRACE could get access to security sensitive
 *   information in the HTTP headers (such as cookies) that it might not
 *   otherwise have access to.</td>
 *   </tr>
 *
 * <tr>
 *   <td>getCookieHandler</td>
 *   <td>The ability to get the cookie handler that processes highly
 *   security sensitive cookie information for an Http session.</td>
 *   <td>Malicious code can get a cookie handler to obtain access to
 *   highly security sensitive cookie information. Some web servers
 *   use cookies to save user private information such as access
 *   control information, or to track user browsing habit.</td>
 *   </tr>
 *
 * <tr>
 *  <td>getNetworkInformation</td>
 *  <td>The ability to retrieve all information about local network interfaces.</td>
 *  <td>Malicious code can read information about network hardware such as
 *  MAC addresses, which could be used to construct local IPv6 addresses.</td>
 * </tr>
 *
 * <tr>
 *   <td>getProxySelector</td>
 *   <td>The ability to get the proxy selector used to make decisions
 *   on which proxies to use when making network connections.</td>
 *   <td>Malicious code can get a ProxySelector to discover proxy
 *   hosts and ports on internal networks, which could then become
 *   targets for attack.</td>
 * </tr>
 *
 * <tr>
 *   <td>getResponseCache</td>
 *   <td>The ability to get the response cache that provides
 *   access to a local response cache.</td>
 *   <td>Malicious code getting access to the local response cache
 *   could access security sensitive information.</td>
 *   </tr>
 *
 * <tr>
 *   <td>requestPasswordAuthentication</td>
 *   <td>The ability
 * to ask the authenticator registered with the system for
 * a password</td>
 *   <td>Malicious code may steal this password.</td>
 * </tr>
 *
 * <tr>
 *   <td>setCookieHandler</td>
 *   <td>The ability to set the cookie handler that processes highly
 *   security sensitive cookie information for an Http session.</td>
 *   <td>Malicious code can set a cookie handler to obtain access to
 *   highly security sensitive cookie information. Some web servers
 *   use cookies to save user private information such as access
 *   control information, or to track user browsing habit.</td>
 *   </tr>
 *
 * <tr>
 *   <td>setDefaultAuthenticator</td>
 *   <td>The ability to set the
 * way authentication information is retrieved when
 * a proxy or HTTP server asks for authentication</td>
 *   <td>Malicious
 * code can set an authenticator that monitors and steals user
 * authentication input as it retrieves the input from the user.</td>
 * </tr>
 *
 * <tr>
 *   <td>setProxySelector</td>
 *   <td>The ability to set the proxy selector used to make decisions
 *   on which proxies to use when making network connections.</td>
 *   <td>Malicious code can set a ProxySelector that directs network
 *   traffic to an arbitrary network host.</td>
 * </tr>
 *
 * <tr>
 *   <td>setResponseCache</td>
 *   <td>The ability to set the response cache that provides access to
 *   a local response cache.</td>
 *   <td>Malicious code getting access to the local response cache
 *   could access security sensitive information, or create false
 *   entries in the response cache.</td>
 *   </tr>
 *
 * <tr>
 *   <td>specifyStreamHandler</td>
 *   <td>The ability
 * to specify a stream handler when constructing a URL</td>
 *   <td>Malicious code may create a URL with resources that it would
normally not have access to (like file:/foo/fum/), specifying a
stream handler that gets the actual bytes from someplace it does
have access to. Thus it might be able to trick the system into
creating a ProtectionDomain/CodeSource for a class even though
that class really didn't come from that location.</td>
 * </tr>
 * </table>
 *
 * <p>
 *  这个类用于各种网络权限。 NetPermission包含名称(也称为"目标名称"),但没有操作列表;你有命名的权限或你不。
 * <P>
 *  目标名称是网络权限的名称(见下文)。命名约定遵循分层属性命名约定。此外,星号可能出现在名称的末尾,跟在"。"后面,或者它本身,表示通配符匹配。例如："foo。
 * *"和"*"表示通配符匹配,而"* foo"和"a * b"则不匹配。
 * <P>
 *  下表列出了所有可能的NetPermission目标名称,并为每个提供了权限允许的描述以及授予代码权限的风险的讨论。
 * 
 * <table border=1 cellpadding=5 summary="Permission target name, what the permission allows, and associated risks">
 * <tr>
 *  <th>权限目标名称</th> <th>权限允许</th> <th>允许此权限的风险</th>
 * </tr>
 * <tr>
 *  <td> allowHttpTrace </td> <td>在HttpURLConnection中使用HTTP TRACE方法的功能。
 * </td> <td>使用HTTP TRACE的恶意代码可以访问HTTP标头中的安全敏感信息(如Cookie)否则可能无法访问。</td>。
 * </tr>
 * 
 * <tr>
 * <td> getCookieHandler </td> <td>获取处理高度安全敏感的Cookie信息的Cookie处理程序的能力。
 * </td> <td>恶意代码可以获取cookie处理程序以获得高度安全性敏感Cookie信息。某些网络服务器使用Cookie保存用户私人信息,例如访问控制信息,或跟踪用户浏览习惯。</td>。
 * </tr>
 * 
 * <tr>
 *  <td> getNetworkInformation </td> <td>能够检索有关本地网络接口的所有信息。
 * </td> <td>恶意代码可以读取有关网络硬件的信息,例如MAC地址,可用于构建本地IPv6地址。</td>。
 * </tr>
 * 
 * <tr>
 *  <td> getProxySelector </td> <td>获取代理选择器的能力,用于决定在进行网络连接时使用哪些代理。
 * </td> <td>恶意代码可以获取ProxySelector来发现代理主机,内部网络上的端口,然后可能成为攻击的目标。</td>。
 * </tr>
 * 
 * <tr>
 *  <td> getResponseCache </td> <td>获取访问本地响应缓存的响应缓存的能力。</td> <td>获取对本地响应缓存的访问的恶意代码可以访问安全敏感信息。 / td>
 * </tr>
 * 
 * <tr>
 *  <td> requestPasswordAuthentication </td> <td>请求向系统注册的验证码提供密码</td> <td>恶意代码可能会窃取此密码。</td>
 * 
 * @see java.security.BasicPermission
 * @see java.security.Permission
 * @see java.security.Permissions
 * @see java.security.PermissionCollection
 * @see java.lang.SecurityManager
 *
 *
 * @author Marianne Mueller
 * @author Roland Schemers
 */

public final class NetPermission extends BasicPermission {
    private static final long serialVersionUID = -8343910153355041693L;

    /**
     * Creates a new NetPermission with the specified name.
     * The name is the symbolic name of the NetPermission, such as
     * "setDefaultAuthenticator", etc. An asterisk
     * may appear at the end of the name, following a ".", or by itself, to
     * signify a wildcard match.
     *
     * <p>
     * </tr>
     * 
     * <tr>
     * <td> setCookieHandler </td> <td>设置用于处理Http会话的高安全敏感Cookie信息的Cookie处理程序的能力。
     * </td> <td>恶意代码可以设置Cookie处理程序,以获得高度安全敏感Cookie信息。某些网络服务器使用Cookie保存用户私人信息,例如访问控制信息,或跟踪用户浏览习惯。</td>。
     * </tr>
     * 
     * <tr>
     *  <td> setDefaultAuthenticator </td> <td>设置代理或HTTP服务器请求身份验证时身份验证信息检索方式的能力</td> <td>恶意代码可以设置监视和窃取用户身份验证
     * 输入的身份验证器因为它从用户检索输入。
     * </td>。
     * </tr>
     * 
     * <tr>
     *  <td> setProxySelector </td> <td>设置代理选择器的能力,用于决定在进行网络连接时使用哪些代理。
     * </td> <td>恶意代码可以设置ProxySelector,将网络流量定向到任意网络主机。</td>。
     * </tr>
     * 
     * <tr>
     * 
     * @param name the name of the NetPermission.
     *
     * @throws NullPointerException if {@code name} is {@code null}.
     * @throws IllegalArgumentException if {@code name} is empty.
     */

    public NetPermission(String name)
    {
        super(name);
    }

    /**
     * Creates a new NetPermission object with the specified name.
     * The name is the symbolic name of the NetPermission, and the
     * actions String is currently unused and should be null.
     *
     * <p>
     *  <td> setResponseCache </td> <td>设置提供对本地响应缓存访问的响应缓存的能力。
     * </td> <td>获取对本地响应缓存的访问的恶意代码可能访问安全敏感信息,或在响应缓存中创建false条目。</td>。
     * </tr>
     * 
     * <tr>
     * <td> defineStreamHandler </td> <td>在构造URL时指定流处理程序的能力</td> <td>恶意代码可能创建一个包含通常无法访问的资源的URL(如file： foo / 
     * fum /),指定一个流处理程序,从某个地方获取实际的字节,它有权访问。
     * 因此,它可能会欺骗系统为一个类创建一个ProtectionDomain / CodeSource,即使该类真的不是来自该位置。</td>。
     * </tr>
     * 
     * @param name the name of the NetPermission.
     * @param actions should be null.
     *
     * @throws NullPointerException if {@code name} is {@code null}.
     * @throws IllegalArgumentException if {@code name} is empty.
     */

    public NetPermission(String name, String actions)
    {
        super(name, actions);
    }
}
