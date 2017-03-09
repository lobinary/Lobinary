/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.security.auth.module;

import java.security.AccessController;
import java.net.SocketPermission;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Set;

import javax.naming.*;
import javax.naming.directory.*;
import javax.naming.ldap.*;
import javax.security.auth.*;
import javax.security.auth.callback.*;
import javax.security.auth.login.*;
import javax.security.auth.spi.*;

import com.sun.security.auth.LdapPrincipal;
import com.sun.security.auth.UserPrincipal;


/**
 * This {@link LoginModule} performs LDAP-based authentication.
 * A username and password is verified against the corresponding user
 * credentials stored in an LDAP directory.
 * This module requires the supplied {@link CallbackHandler} to support a
 * {@link NameCallback} and a {@link PasswordCallback}.
 * If authentication is successful then a new {@link LdapPrincipal} is created
 * using the user's distinguished name and a new {@link UserPrincipal} is
 * created using the user's username and both are associated
 * with the current {@link Subject}.
 *
 * <p> This module operates in one of three modes: <i>search-first</i>,
 * <i>authentication-first</i> or <i>authentication-only</i>.
 * A mode is selected by specifying a particular set of options.
 *
 * <p> In search-first mode, the LDAP directory is searched to determine the
 * user's distinguished name and then authentication is attempted.
 * An (anonymous) search is performed using the supplied username in
 * conjunction with a specified search filter.
 * If successful then authentication is attempted using the user's
 * distinguished name and the supplied password.
 * To enable this mode, set the <code>userFilter</code> option and omit the
 * <code>authIdentity</code> option.
 * Use search-first mode when the user's distinguished name is not
 * known in advance.
 *
 * <p> In authentication-first mode, authentication is attempted using the
 * supplied username and password and then the LDAP directory is searched.
 * If authentication is successful then a search is performed using the
 * supplied username in conjunction with a specified search filter.
 * To enable this mode, set the <code>authIdentity</code> and the
 * <code>userFilter</code> options.
 * Use authentication-first mode when accessing an LDAP directory
 * that has been configured to disallow anonymous searches.
 *
 * <p> In authentication-only mode, authentication is attempted using the
 * supplied username and password. The LDAP directory is not searched because
 * the user's distinguished name is already known.
 * To enable this mode, set the <code>authIdentity</code> option to a valid
 * distinguished name and omit the <code>userFilter</code> option.
 * Use authentication-only mode when the user's distinguished name is
 * known in advance.
 *
 * <p> The following option is mandatory and must be specified in this
 * module's login {@link Configuration}:
 * <dl><dt></dt><dd>
 * <dl>
 * <dt> <code>userProvider=<b>ldap_urls</b></code>
 * </dt>
 * <dd> This option identifies the LDAP directory that stores user entries.
 *      <b>ldap_urls</b> is a list of space-separated LDAP URLs
 *      (<a href="http://www.ietf.org/rfc/rfc2255.txt">RFC 2255</a>)
 *      that identifies the LDAP server to use and the position in
 *      its directory tree where user entries are located.
 *      When several LDAP URLs are specified then each is attempted,
 *      in turn, until the first successful connection is established.
 *      Spaces in the distinguished name component of the URL must be escaped
 *      using the standard mechanism of percent character ('<code>%</code>')
 *      followed by two hexadecimal digits (see {@link java.net.URI}).
 *      Query components must also be omitted from the URL.
 *
 *      <p>
 *      Automatic discovery of the LDAP server via DNS
 *      (<a href="http://www.ietf.org/rfc/rfc2782.txt">RFC 2782</a>)
 *      is supported (once DNS has been configured to support such a service).
 *      It is enabled by omitting the hostname and port number components from
 *      the LDAP URL. </dd>
 * </dl></dl>
 *
 * <p> This module also recognizes the following optional {@link Configuration}
 *     options:
 * <dl><dt></dt><dd>
 * <dl>
 * <dt> <code>userFilter=<b>ldap_filter</b></code> </dt>
 * <dd> This option specifies the search filter to use to locate a user's
 *      entry in the LDAP directory. It is used to determine a user's
 *      distinguished name.
 *      <code><b>ldap_filter</b></code> is an LDAP filter string
 *      (<a href="http://www.ietf.org/rfc/rfc2254.txt">RFC 2254</a>).
 *      If it contains the special token "<code><b>{USERNAME}</b></code>"
 *      then that token will be replaced with the supplied username value
 *      before the filter is used to search the directory. </dd>
 *
 * <dt> <code>authIdentity=<b>auth_id</b></code> </dt>
 * <dd> This option specifies the identity to use when authenticating a user
 *      to the LDAP directory.
 *      <code><b>auth_id</b></code> may be an LDAP distinguished name string
 *      (<a href="http://www.ietf.org/rfc/rfc2253.txt">RFC 2253</a>) or some
 *      other string name.
 *      It must contain the special token "<code><b>{USERNAME}</b></code>"
 *      which will be replaced with the supplied username value before the
 *      name is used for authentication.
 *      Note that if this option does not contain a distinguished name then
 *      the <code>userFilter</code> option must also be specified. </dd>
 *
 * <dt> <code>authzIdentity=<b>authz_id</b></code> </dt>
 * <dd> This option specifies an authorization identity for the user.
 *      <code><b>authz_id</b></code> is any string name.
 *      If it comprises a single special token with curly braces then
 *      that token is treated as a attribute name and will be replaced with a
 *      single value of that attribute from the user's LDAP entry.
 *      If the attribute cannot be found then the option is ignored.
 *      When this option is supplied and the user has been successfully
 *      authenticated then an additional {@link UserPrincipal}
 *      is created using the authorization identity and it is associated with
 *      the current {@link Subject}. </dd>
 *
 * <dt> <code>useSSL</code> </dt>
 * <dd> if <code>false</code>, this module does not establish an SSL connection
 *      to the LDAP server before attempting authentication. SSL is used to
 *      protect the privacy of the user's password because it is transmitted
 *      in the clear over LDAP.
 *      By default, this module uses SSL. </dd>
 *
 * <dt> <code>useFirstPass</code> </dt>
 * <dd> if <code>true</code>, this module retrieves the username and password
 *      from the module's shared state, using "javax.security.auth.login.name"
 *      and "javax.security.auth.login.password" as the respective keys. The
 *      retrieved values are used for authentication. If authentication fails,
 *      no attempt for a retry is made, and the failure is reported back to
 *      the calling application.</dd>
 *
 * <dt> <code>tryFirstPass</code> </dt>
 * <dd> if <code>true</code>, this module retrieves the username and password
 *      from the module's shared state, using "javax.security.auth.login.name"
 *       and "javax.security.auth.login.password" as the respective keys.  The
 *      retrieved values are used for authentication. If authentication fails,
 *      the module uses the {@link CallbackHandler} to retrieve a new username
 *      and password, and another attempt to authenticate is made. If the
 *      authentication fails, the failure is reported back to the calling
 *      application.</dd>
 *
 * <dt> <code>storePass</code> </dt>
 * <dd> if <code>true</code>, this module stores the username and password
 *      obtained from the {@link CallbackHandler} in the module's shared state,
 *      using
 *      "javax.security.auth.login.name" and
 *      "javax.security.auth.login.password" as the respective keys.  This is
 *      not performed if existing values already exist for the username and
 *      password in the shared state, or if authentication fails.</dd>
 *
 * <dt> <code>clearPass</code> </dt>
 * <dd> if <code>true</code>, this module clears the username and password
 *      stored in the module's shared state after both phases of authentication
 *      (login and commit) have completed.</dd>
 *
 * <dt> <code>debug</code> </dt>
 * <dd> if <code>true</code>, debug messages are displayed on the standard
 *      output stream.
 * </dl>
 * </dl>
 *
 * <p>
 * Arbitrary
 * <a href="{@docRoot}/../../../../../technotes/guides/jndi/jndi-ldap-gl.html#PROP">JNDI properties</a>
 * may also be specified in the {@link Configuration}.
 * They are added to the environment and passed to the LDAP provider.
 * Note that the following four JNDI properties are set by this module directly
 * and are ignored if also present in the configuration:
 * <ul>
 * <li> <code>java.naming.provider.url</code>
 * <li> <code>java.naming.security.principal</code>
 * <li> <code>java.naming.security.credentials</code>
 * <li> <code>java.naming.security.protocol</code>
 * </ul>
 *
 * <p>
 * Three sample {@link Configuration}s are shown below.
 * The first one activates search-first mode. It identifies the LDAP server
 * and specifies that users' entries be located by their <code>uid</code> and
 * <code>objectClass</code> attributes. It also specifies that an identity
 * based on the user's <code>employeeNumber</code> attribute should be created.
 * The second one activates authentication-first mode. It requests that the
 * LDAP server be located dynamically, that authentication be performed using
 * the supplied username directly but without the protection of SSL and that
 * users' entries be located by one of three naming attributes and their
 * <code>objectClass</code> attribute.
 * The third one activates authentication-only mode. It identifies alternative
 * LDAP servers, it specifies the distinguished name to use for
 * authentication and a fixed identity to use for authorization. No directory
 * search is performed.
 *
 * <pre>
 *
 *     ExampleApplication {
 *         com.sun.security.auth.module.LdapLoginModule REQUIRED
 *             userProvider="ldap://ldap-svr/ou=people,dc=example,dc=com"
 *             userFilter="(&(uid={USERNAME})(objectClass=inetOrgPerson))"
 *             authzIdentity="{EMPLOYEENUMBER}"
 *             debug=true;
 *     };
 *
 *     ExampleApplication {
 *         com.sun.security.auth.module.LdapLoginModule REQUIRED
 *             userProvider="ldap:///cn=users,dc=example,dc=com"
 *             authIdentity="{USERNAME}"
 *             userFilter="(&(|(samAccountName={USERNAME})(userPrincipalName={USERNAME})(cn={USERNAME}))(objectClass=user))"
 *             useSSL=false
 *             debug=true;
 *     };
 *
 *     ExampleApplication {
 *         com.sun.security.auth.module.LdapLoginModule REQUIRED
 *             userProvider="ldap://ldap-svr1 ldap://ldap-svr2"
 *             authIdentity="cn={USERNAME},ou=people,dc=example,dc=com"
 *             authzIdentity="staff"
 *             debug=true;
 *     };
 *
 * </pre>
 *
 * <dl>
 * <dt><b>Note:</b> </dt>
 * <dd>When a {@link SecurityManager} is active then an application
 *     that creates a {@link LoginContext} and uses a {@link LoginModule}
 *     must be granted certain permissions.
 *     <p>
 *     If the application creates a login context using an <em>installed</em>
 *     {@link Configuration} then the application must be granted the
 *     {@link AuthPermission} to create login contexts.
 *     For example, the following security policy allows an application in
 *     the user's current directory to instantiate <em>any</em> login context:
 *     <pre>
 *
 *     grant codebase "file:${user.dir}/" {
 *         permission javax.security.auth.AuthPermission "createLoginContext.*";
 *     };
 *     </pre>
 *
 *     Alternatively, if the application creates a login context using a
 *     <em>caller-specified</em> {@link Configuration} then the application
 *     must be granted the permissions required by the {@link LoginModule}.
 *     <em>This</em> module requires the following two permissions:
 *     <p>
 *     <ul>
 *     <li> The {@link SocketPermission} to connect to an LDAP server.
 *     <li> The {@link AuthPermission} to modify the set of {@link Principal}s
 *          associated with a {@link Subject}.
 *     </ul>
 *     <p>
 *     For example, the following security policy grants an application in the
 *     user's current directory all the permissions required by this module:
 *     <pre>
 *
 *     grant codebase "file:${user.dir}/" {
 *         permission java.net.SocketPermission "*:389", "connect";
 *         permission java.net.SocketPermission "*:636", "connect";
 *         permission javax.security.auth.AuthPermission "modifyPrincipals";
 *     };
 *     </pre>
 * </dd>
 * </dl>
 *
 * <p>
 *  此{@link LoginModule}执行基于LDAP的身份验证。将根据存储在LDAP目录中的相应用户凭据验证用户名和密码。
 * 此模块要求提供的{@link CallbackHandler}支持{@link NameCallback}和{@link PasswordCallback}。
 * 如果认证成功,则使用用户的可分辨名称创建新的{@link LdapPrincipal},并使用用户的用户名创建新的{@link UserPrincipal},并且两者都与当前{@link主题}相关联。
 * 
 *  <p>此模块以三种模式之一运行：<i>首先搜索</i>,<i>验证优先</i>或<i>仅验证</i>。通过指定特定的一组选项来选择模式。
 * 
 *  <p>在搜索优先模式下,搜索LDAP目录以确定用户的可分辨名称,然后尝试进行身份验证。使用提供的用户名结合指定的搜索过滤器执行(匿名)搜索。如果成功,则使用用户的可分辨名称和提供的密码尝试认证。
 * 要启用此模式,请设置<code> userFilter </code>选项并忽略<code> authIdentity </code>选项。如果提前不知道用户的可分辨名称,请使用搜索优先模式。
 * 
 * <p>在验证优先模式下,尝试使用提供的用户名和密码进行验证,然后搜索LDAP目录。如果认证成功,则使用所提供的用户名以及指定的搜索过滤器来执行搜索。
 * 要启用此模式,请设置<code> authIdentity </code>和<code> userFilter </code>选项。在访问已配置为禁止匿名搜索的LDAP目录时,使用身份验证优先模式。
 * 
 *  <p>在纯认证模式下,尝试使用提供的用户名和密码进行认证。不搜索LDAP目录,因为用户的可分辨名称是已知的。
 * 要启用此模式,请将<code> authIdentity </code>选项设置为有效的可分辨名称,并省略<code> userFilter </code>选项。
 * 事先知道用户的专有名称时,请使用仅认证模式。
 * 
 *  <p>以下选项是必需的,必须在此模块的登录{@link Configuration}中指定：<dl> <dt> </dt> <dd>
 * <dl>
 *  <dt> <code> userProvider = <b> ldap_urls </b> </code>
 * </dt>
 * <dd>此选项标识用于存储用户条目的LDAP目录。
 *  <b> ldap_urls </b>是由空格分隔的LDAP网址(<a href="http://www.ietf.org/rfc/rfc2255.txt"> RFC 2255 </a>)的列表,用于标
 * 识LDAP服务器以及用户条目所在目录树中的位置。
 * <dd>此选项标识用于存储用户条目的LDAP目录。当指定了几个LDAP URL时,依次尝试每个,直到建立第一个成功的连接。
 * 必须使用百分比字符('<code>％</code>')和两个十六进制数字(参见{@link java.net.URI})的标准机制转义URL的可分辨名称组件中的空格。还必须从URL中省略查询组件。
 * 
 * <p>
 *  支持通过DNS自动发现LDAP服务器(<a href="http://www.ietf.org/rfc/rfc2782.txt"> RFC 2782 </a>)(一旦DNS配置为支持这样的服务)。
 * 它通过从LDAP URL中省略主机名和端口号组件来启用。 </dd> </dl> </dl>。
 * 
 *  <p>此模块还识别以下可选的{@link Configuration}选项：<dl> <dt> </dt> <dd>
 * <dl>
 * <dt> <code> userFilter = <b> ldap_filter </b> </code> </dt> <dd>此选项指定用于在LDAP目录中查找用户条目的搜索过滤器。
 * 它用于确定用户的可分辨名称。
 *  <code> <b> ldap_filter </b> </code>是LDAP过滤字符串(<a href="http://www.ietf.org/rfc/rfc2254.txt"> RFC 225
 * 4 </a>) 。
 * 它用于确定用户的可分辨名称。如果它包含特殊标记"<code> <b> {USERNAME} </b> </code>",那么在过滤器用于搜索目录之前,该标记将替换为提供的用户名值。 </dd>。
 * 
 *  <dt> <code> authIdentity = <b> auth_id </b> </code> </dt> <dd>此选项指定在认证LDAP目录的用户时要使用的标识。
 *  <code> <b> auth_id </b> </code>可能是LDAP专有名称字符串(<a href="http://www.ietf.org/rfc/rfc2253.txt"> RFC 225
 * 3 </a >)或一些其他字符串名称。
 *  <dt> <code> authIdentity = <b> auth_id </b> </code> </dt> <dd>此选项指定在认证LDAP目录的用户时要使用的标识。
 * 它必须包含特殊标记"<code> <b> {USERNAME} </b> </code>",在用于认证之前,将用提供的用户名值替换。
 * 请注意,如果此选项不包含可分辨名称,则还必须指定<code> userFilter </code>选项。 </dd>。
 * 
 * <dt> <code> authzIdentity = <b> authz_id </b> </code> </dt> <dd>此选项指定用户的授权身份。
 *  <code> <b> authz_id </b> </code>是任何字符串名称。如果它包含带花括号的单个特殊令牌,那么该令牌将被视为属性名称,并将被来自用户的LDAP条目的该属性的单个值替换。
 * 如果找不到该属性,则忽略该选项。当提供此选项并且用户已成功通过身份验证时,将使用授权标识创建一个附加{@link UserPrincipal},并将其与当前{@link主题}相关联。 </dd>。
 * 
 *  <dt> <code> useSSL </code> </dt> <dd>如果<code> false </code>,此模块在尝试身份验证之前未建立与LDAP服务器的SSL连接。
 *  SSL用于保护用户密码的隐私,因为它是通过LDAP清除传输的。默认情况下,此模块使用SSL。 </dd>。
 * 
 *  <dt> <code> useFirstPass </code> </dt> <dd>如果<code> true </code>,此模块从模块的共享状态检索用户名和密码,使用"javax.securi
 * ty.auth.login .name"和"javax.security.auth.login.password"作为相应的键。
 * 检索的值用于认证。如果身份验证失败,则不会尝试重试,并将该失败报告给调用应用程序。</dd>。
 * 
 * <dt> <code> tryFirstPass </code> </dt> <dd>如果<code> true </code>,此模块从模块的共享状态检索用户名和密码,使用"javax.securit
 * y.auth.login .name"和"javax.security.auth.login.password"作为相应的键。
 * 检索的值用于认证。如果身份验证失败,模块将使用{@link CallbackHandler}检索新的用户名和密码,并进行另一次身份验证。如果认证失败,则将故障报告回调用的应用程序。</dd>。
 * 
 *  <dt> <code> storePass </code> </dt> <dd>如果<code> true </code>,此模块将从{@link CallbackHandler}获取的用户名和密码存
 * 储在模块的共享状态, "javax.security.auth.login.name"和"javax.security.auth.login.password"作为相应的键。
 * 如果共享状态下的用户名和密码已存在,或者认证失败,则不会执行此操作。</dd>。
 * 
 *  <dt> <code> clearPass </code> </dt> <dd>如果<code> true </code>,此模块清除存储在模块的共享状态中的用户名和密码, )已完成。</dd>
 * 
 *  <dt> <code> debug </code> </dt> <dd>如果<code> true </code>,则调试消息将显示在标准输出流上。
 * </dl>
 * </dl>
 * 
 * <p>
 * 任意<a href="{@docRoot}/../../../../../technotes/guides/jndi/jndi-ldap-gl.html#PROP"> JNDI属性</a>可能也可以在{@link Configuration}
 * 中指定。
 * 它们将添加到环境中并传递到LDAP提供程序。请注意,以下四个JNDI属性由此模块直接设置,如果在配置中也存在,将被忽略：。
 * <ul>
 *  <li> <code> java.naming.provider.url </code> <li> <code> java.naming.security.principal </code> <li>
 *  <code> java.naming.security.credentials </code > <li> <code> java.naming.security.protocol </code>。
 * </ul>
 * 
 * <p>
 *  三个示例{@link配置}如下所示。第一个激活搜索优先模式。
 * 它标识LDAP服务器并指定用户的条目由其<code> uid </code>和<code> objectClass </code>属性定位。
 * 它还指定应创建基于用户的<code> employeeNumber </code>属性的标识。第二个激活认证优先模式。
 * 它要求动态定位LDAP服务器,使用提供的用户名直接执行认证,但没有SSL保护,并且用户的条目由三个命名属性之一及其<code> objectClass </code>属性定位。第三个激活仅认证模式。
 * 它标识备用LDAP服务器,它指定用于认证的可分辨名称和用于授权的固定标识。不执行目录搜索。
 * 
 * <pre>
 * 
 * @since 1.6
 */
@jdk.Exported
public class LdapLoginModule implements LoginModule {

    // Use the default classloader for this class to load the prompt strings.
    private static final ResourceBundle rb = AccessController.doPrivileged(
            new PrivilegedAction<ResourceBundle>() {
                public ResourceBundle run() {
                    return ResourceBundle.getBundle(
                        "sun.security.util.AuthResources");
                }
            }
        );

    // Keys to retrieve the stored username and password
    private static final String USERNAME_KEY = "javax.security.auth.login.name";
    private static final String PASSWORD_KEY =
        "javax.security.auth.login.password";

    // Option names
    private static final String USER_PROVIDER = "userProvider";
    private static final String USER_FILTER = "userFilter";
    private static final String AUTHC_IDENTITY = "authIdentity";
    private static final String AUTHZ_IDENTITY = "authzIdentity";

    // Used for the username token replacement
    private static final String USERNAME_TOKEN = "{USERNAME}";
    private static final Pattern USERNAME_PATTERN =
        Pattern.compile("\\{USERNAME\\}");

    // Configurable options
    private String userProvider;
    private String userFilter;
    private String authcIdentity;
    private String authzIdentity;
    private String authzIdentityAttr = null;
    private boolean useSSL = true;
    private boolean authFirst = false;
    private boolean authOnly = false;
    private boolean useFirstPass = false;
    private boolean tryFirstPass = false;
    private boolean storePass = false;
    private boolean clearPass = false;
    private boolean debug = false;

    // Authentication status
    private boolean succeeded = false;
    private boolean commitSucceeded = false;

    // Supplied username and password
    private String username;
    private char[] password;

    // User's identities
    private LdapPrincipal ldapPrincipal;
    private UserPrincipal userPrincipal;
    private UserPrincipal authzPrincipal;

    // Initial state
    private Subject subject;
    private CallbackHandler callbackHandler;
    private Map<String, Object> sharedState;
    private Map<String, ?> options;
    private LdapContext ctx;
    private Matcher identityMatcher = null;
    private Matcher filterMatcher = null;
    private Hashtable<String, Object> ldapEnvironment;
    private SearchControls constraints = null;

    /**
     * Initialize this <code>LoginModule</code>.
     *
     * <p>
     * 
     * ExampleApplication {com.sun.security.auth.module.LdapLoginModule REQUIRED userProvider ="ldap：// ldap-svr / ou = people,dc = example,dc = com"userFilter ="(&(uid = {USERNAME}
     * ) objectClass = inetOrgPerson))"authzIdentity ="{EMPLOYEENUMBER}"debug = true; };。
     * 
     *  ExampleApplication {com.sun.security.auth.module.LdapLoginModule REQUIRED userProvider ="ldap：/// cn = users,dc = example,dc = com"authIdentity ="{USERNAME}
     * "userFilter ="(&(|(samAccountName = {USERNAME})(userPrincipalName = {USERNAME})(cn = {USERNAME}))(obj
     * ectClass = user))"useSSL = false debug = true; };。
     * 
     *  ExampleApplication {com.sun.security.auth.module.LdapLoginModule REQUIRED userProvider ="ldap：// ldap-svr1 ldap：// ldap-svr2"authIdentity ="cn = {USERNAME}
     * ,ou = people,dc = example,dc = com"authzIdentity ="staff"debug = true; };。
     * 
     * </pre>
     * 
     * <dl>
     *  <dt> <b>注意：</b> </dt> <dd>当{@link SecurityManager}处于活动状态时,必须授予创建{@link LoginContext}并使用{@link LoginModule}
     * 的应用程序某些权限。
     * <p>
     *  如果应用程序使用安装的</em> {@link Configuration}创建登录上下文,则应用程序必须授予{@link AuthPermission}以创建登录上下文。
     * 例如,以下安全策略允许用户当前目录中的应用程序实例化<em>任何</em>登录上下文：。
     * <pre>
     * 
     *  授权代码库"文件：$ {user.dir} /"{permission javax.security.auth.AuthPermission"createLoginContext。*"; };
     * </pre>
     * 
     * 或者,如果应用程序使用<em>调用者指定的</em> {@link Configuration}创建登录上下文,则应用程序必须被授予{@link LoginModule}所需的权限。
     *  <em>此</em>模块需要以下两个权限：。
     * <p>
     * <ul>
     *  <li>用于连接到LDAP服务器的{@link SocketPermission}。 <li> {@link AuthPermission}修改与{@link主题}相关联的{@link主体}集合。
     * </ul>
     * <p>
     *  例如,以下安全策略向用户当前目录中的应用程序授予此模块所需的所有权限：
     * <pre>
     * 
     *  授权代码库"文件：$ {user.dir} /"{permission java.net.SocketPermission"*：389","connect"; permission java.net.SocketPermission"*：636","connect"; permission javax.security.auth.AuthPermission"modifyPrincipals"; }
     * ;。
     * </pre>
     * </dd>
     * </dl>
     * 
     * 
     * @param subject the <code>Subject</code> to be authenticated.
     * @param callbackHandler a <code>CallbackHandler</code> to acquire the
     *                  username and password.
     * @param sharedState shared <code>LoginModule</code> state.
     * @param options options specified in the login
     *                  <code>Configuration</code> for this particular
     *                  <code>LoginModule</code>.
     */
    // Unchecked warning from (Map<String, Object>)sharedState is safe
    // since javax.security.auth.login.LoginContext passes a raw HashMap.
    @SuppressWarnings("unchecked")
    public void initialize(Subject subject, CallbackHandler callbackHandler,
                        Map<String, ?> sharedState, Map<String, ?> options) {

        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.sharedState = (Map<String, Object>)sharedState;
        this.options = options;

        ldapEnvironment = new Hashtable<String, Object>(9);
        ldapEnvironment.put(Context.INITIAL_CONTEXT_FACTORY,
            "com.sun.jndi.ldap.LdapCtxFactory");

        // Add any JNDI properties to the environment
        for (String key : options.keySet()) {
            if (key.indexOf(".") > -1) {
                ldapEnvironment.put(key, options.get(key));
            }
        }

        // initialize any configured options

        userProvider = (String)options.get(USER_PROVIDER);
        if (userProvider != null) {
            ldapEnvironment.put(Context.PROVIDER_URL, userProvider);
        }

        authcIdentity = (String)options.get(AUTHC_IDENTITY);
        if (authcIdentity != null &&
            (authcIdentity.indexOf(USERNAME_TOKEN) != -1)) {
            identityMatcher = USERNAME_PATTERN.matcher(authcIdentity);
        }

        userFilter = (String)options.get(USER_FILTER);
        if (userFilter != null) {
            if (userFilter.indexOf(USERNAME_TOKEN) != -1) {
                filterMatcher = USERNAME_PATTERN.matcher(userFilter);
            }
            constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            constraints.setReturningAttributes(new String[0]); //return no attrs
            constraints.setReturningObjFlag(true); // to get the full DN
        }

        authzIdentity = (String)options.get(AUTHZ_IDENTITY);
        if (authzIdentity != null &&
            authzIdentity.startsWith("{") && authzIdentity.endsWith("}")) {
            if (constraints != null) {
                authzIdentityAttr =
                    authzIdentity.substring(1, authzIdentity.length() - 1);
                constraints.setReturningAttributes(
                    new String[]{authzIdentityAttr});
            }
            authzIdentity = null; // set later, from the specified attribute
        }

        // determine mode
        if (authcIdentity != null) {
            if (userFilter != null) {
                authFirst = true; // authentication-first mode
            } else {
                authOnly = true; // authentication-only mode
            }
        }

        if ("false".equalsIgnoreCase((String)options.get("useSSL"))) {
            useSSL = false;
            ldapEnvironment.remove(Context.SECURITY_PROTOCOL);
        } else {
            ldapEnvironment.put(Context.SECURITY_PROTOCOL, "ssl");
        }

        tryFirstPass =
                "true".equalsIgnoreCase((String)options.get("tryFirstPass"));

        useFirstPass =
                "true".equalsIgnoreCase((String)options.get("useFirstPass"));

        storePass = "true".equalsIgnoreCase((String)options.get("storePass"));

        clearPass = "true".equalsIgnoreCase((String)options.get("clearPass"));

        debug = "true".equalsIgnoreCase((String)options.get("debug"));

        if (debug) {
            if (authFirst) {
                System.out.println("\t\t[LdapLoginModule] " +
                    "authentication-first mode; " +
                    (useSSL ? "SSL enabled" : "SSL disabled"));
            } else if (authOnly) {
                System.out.println("\t\t[LdapLoginModule] " +
                    "authentication-only mode; " +
                    (useSSL ? "SSL enabled" : "SSL disabled"));
            } else {
                System.out.println("\t\t[LdapLoginModule] " +
                    "search-first mode; " +
                    (useSSL ? "SSL enabled" : "SSL disabled"));
            }
        }
    }

    /**
     * Begin user authentication.
     *
     * <p> Acquire the user's credentials and verify them against the
     * specified LDAP directory.
     *
     * <p>
     *  初始化此<code> LoginModule </code>。
     * 
     * 
     * @return true always, since this <code>LoginModule</code>
     *          should not be ignored.
     * @exception FailedLoginException if the authentication fails.
     * @exception LoginException if this <code>LoginModule</code>
     *          is unable to perform the authentication.
     */
    public boolean login() throws LoginException {

        if (userProvider == null) {
            throw new LoginException
                ("Unable to locate the LDAP directory service");
        }

        if (debug) {
            System.out.println("\t\t[LdapLoginModule] user provider: " +
                userProvider);
        }

        // attempt the authentication
        if (tryFirstPass) {

            try {
                // attempt the authentication by getting the
                // username and password from shared state
                attemptAuthentication(true);

                // authentication succeeded
                succeeded = true;
                if (debug) {
                    System.out.println("\t\t[LdapLoginModule] " +
                                "tryFirstPass succeeded");
                }
                return true;

            } catch (LoginException le) {
                // authentication failed -- try again below by prompting
                cleanState();
                if (debug) {
                    System.out.println("\t\t[LdapLoginModule] " +
                                "tryFirstPass failed: " + le.toString());
                }
            }

        } else if (useFirstPass) {

            try {
                // attempt the authentication by getting the
                // username and password from shared state
                attemptAuthentication(true);

                // authentication succeeded
                succeeded = true;
                if (debug) {
                    System.out.println("\t\t[LdapLoginModule] " +
                                "useFirstPass succeeded");
                }
                return true;

            } catch (LoginException le) {
                // authentication failed
                cleanState();
                if (debug) {
                    System.out.println("\t\t[LdapLoginModule] " +
                                "useFirstPass failed");
                }
                throw le;
            }
        }

        // attempt the authentication by prompting for the username and pwd
        try {
            attemptAuthentication(false);

            // authentication succeeded
           succeeded = true;
            if (debug) {
                System.out.println("\t\t[LdapLoginModule] " +
                                "authentication succeeded");
            }
            return true;

        } catch (LoginException le) {
            cleanState();
            if (debug) {
                System.out.println("\t\t[LdapLoginModule] " +
                                "authentication failed");
            }
            throw le;
        }
    }

    /**
     * Complete user authentication.
     *
     * <p> This method is called if the LoginContext's
     * overall authentication succeeded
     * (the relevant REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL LoginModules
     * succeeded).
     *
     * <p> If this LoginModule's own authentication attempt
     * succeeded (checked by retrieving the private state saved by the
     * <code>login</code> method), then this method associates an
     * <code>LdapPrincipal</code> and one or more <code>UserPrincipal</code>s
     * with the <code>Subject</code> located in the
     * <code>LoginModule</code>.  If this LoginModule's own
     * authentication attempted failed, then this method removes
     * any state that was originally saved.
     *
     * <p>
     *  开始用户身份验证。
     * 
     *  <p>获取用户的凭据,并根据指定的LDAP目录进行验证。
     * 
     * 
     * @exception LoginException if the commit fails
     * @return true if this LoginModule's own login and commit
     *          attempts succeeded, or false otherwise.
     */
    public boolean commit() throws LoginException {

        if (succeeded == false) {
            return false;
        } else {
            if (subject.isReadOnly()) {
                cleanState();
                throw new LoginException ("Subject is read-only");
            }
            // add Principals to the Subject
            Set<Principal> principals = subject.getPrincipals();
            if (! principals.contains(ldapPrincipal)) {
                principals.add(ldapPrincipal);
            }
            if (debug) {
                System.out.println("\t\t[LdapLoginModule] " +
                                   "added LdapPrincipal \"" +
                                   ldapPrincipal +
                                   "\" to Subject");
            }

            if (! principals.contains(userPrincipal)) {
                principals.add(userPrincipal);
            }
            if (debug) {
                System.out.println("\t\t[LdapLoginModule] " +
                                   "added UserPrincipal \"" +
                                   userPrincipal +
                                   "\" to Subject");
            }

            if (authzPrincipal != null &&
                (! principals.contains(authzPrincipal))) {
                principals.add(authzPrincipal);

                if (debug) {
                    System.out.println("\t\t[LdapLoginModule] " +
                                   "added UserPrincipal \"" +
                                   authzPrincipal +
                                   "\" to Subject");
                }
            }
        }
        // in any case, clean out state
        cleanState();
        commitSucceeded = true;
        return true;
    }

    /**
     * Abort user authentication.
     *
     * <p> This method is called if the overall authentication failed.
     * (the relevant REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL LoginModules
     * did not succeed).
     *
     * <p> If this LoginModule's own authentication attempt
     * succeeded (checked by retrieving the private state saved by the
     * <code>login</code> and <code>commit</code> methods),
     * then this method cleans up any state that was originally saved.
     *
     * <p>
     *  完成用户身份验证。
     * 
     *  <p>如果LoginContext的整体认证成功(相关的REQUIRED,REQUISITE,SUFFICIENT和OPTIONAL LoginModules成功),则调用此方法。
     * 
     * <p>如果此LoginModule自己的身份验证尝试成功(通过检索由<code> login </code>方法保存的私有状态进行检查),则此方法将<code> LdapPrincipal </code>
     * 与一个或多个<code > UserPrincipal </code>与<code> Subject </code>位于<code> LoginModule </code>中。
     * 如果此LoginModule自己的身份验证尝试失败,则此方法将删除最初保存的任何状态。
     * 
     * 
     * @exception LoginException if the abort fails.
     * @return false if this LoginModule's own login and/or commit attempts
     *          failed, and true otherwise.
     */
    public boolean abort() throws LoginException {
        if (debug)
            System.out.println("\t\t[LdapLoginModule] " +
                "aborted authentication");

        if (succeeded == false) {
            return false;
        } else if (succeeded == true && commitSucceeded == false) {

            // Clean out state
            succeeded = false;
            cleanState();

            ldapPrincipal = null;
            userPrincipal = null;
            authzPrincipal = null;
        } else {
            // overall authentication succeeded and commit succeeded,
            // but someone else's commit failed
            logout();
        }
        return true;
    }

    /**
     * Logout a user.
     *
     * <p> This method removes the Principals
     * that were added by the <code>commit</code> method.
     *
     * <p>
     *  中止用户身份验证。
     * 
     *  <p>如果整体身份验证失败,则会调用此方法。 (相关的REQUIRED,REQUISITE,SUFFICIENT和OPTIONAL LoginModules没有成功)。
     * 
     *  <p>如果此LoginModule自己的身份验证尝试成功(通过检索由<code> login </code>和<code> commit </code>方法保存的私有状态进行检查),则此方法将清除原来
     * 保存。
     * 
     * 
     * @exception LoginException if the logout fails.
     * @return true in all cases since this <code>LoginModule</code>
     *          should not be ignored.
     */
    public boolean logout() throws LoginException {
        if (subject.isReadOnly()) {
            cleanState();
            throw new LoginException ("Subject is read-only");
        }
        Set<Principal> principals = subject.getPrincipals();
        principals.remove(ldapPrincipal);
        principals.remove(userPrincipal);
        if (authzIdentity != null) {
            principals.remove(authzPrincipal);
        }

        // clean out state
        cleanState();
        succeeded = false;
        commitSucceeded = false;

        ldapPrincipal = null;
        userPrincipal = null;
        authzPrincipal = null;

        if (debug) {
            System.out.println("\t\t[LdapLoginModule] logged out Subject");
        }
        return true;
    }

    /**
     * Attempt authentication
     *
     * <p>
     *  注销用户。
     * 
     *  <p>此方法删除由<code> commit </code>方法添加的Principal。
     * 
     * 
     * @param getPasswdFromSharedState boolean that tells this method whether
     *          to retrieve the password from the sharedState.
     * @exception LoginException if the authentication attempt fails.
     */
    private void attemptAuthentication(boolean getPasswdFromSharedState)
        throws LoginException {

        // first get the username and password
        getUsernamePassword(getPasswdFromSharedState);

        if (password == null || password.length == 0) {
            throw (LoginException)
                new FailedLoginException("No password was supplied");
        }

        String dn = "";

        if (authFirst || authOnly) {

            String id = replaceUsernameToken(identityMatcher, authcIdentity);

            // Prepare to bind using user's username and password
            ldapEnvironment.put(Context.SECURITY_CREDENTIALS, password);
            ldapEnvironment.put(Context.SECURITY_PRINCIPAL, id);

            if (debug) {
                System.out.println("\t\t[LdapLoginModule] " +
                    "attempting to authenticate user: " + username);
            }

            try {
                // Connect to the LDAP server (using simple bind)
                ctx = new InitialLdapContext(ldapEnvironment, null);

            } catch (NamingException e) {
                throw (LoginException)
                    new FailedLoginException("Cannot bind to LDAP server")
                        .initCause(e);
            }

            // Authentication has succeeded

            // Locate the user's distinguished name
            if (userFilter != null) {
                dn = findUserDN(ctx);
            } else {
                dn = id;
            }

        } else {

            try {
                // Connect to the LDAP server (using anonymous bind)
                ctx = new InitialLdapContext(ldapEnvironment, null);

            } catch (NamingException e) {
                throw (LoginException)
                    new FailedLoginException("Cannot connect to LDAP server")
                        .initCause(e);
            }

            // Locate the user's distinguished name
            dn = findUserDN(ctx);

            try {

                // Prepare to bind using user's distinguished name and password
                ctx.addToEnvironment(Context.SECURITY_AUTHENTICATION, "simple");
                ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, dn);
                ctx.addToEnvironment(Context.SECURITY_CREDENTIALS, password);

                if (debug) {
                    System.out.println("\t\t[LdapLoginModule] " +
                        "attempting to authenticate user: " + username);
                }
                // Connect to the LDAP server (using simple bind)
                ctx.reconnect(null);

                // Authentication has succeeded

            } catch (NamingException e) {
                throw (LoginException)
                    new FailedLoginException("Cannot bind to LDAP server")
                        .initCause(e);
            }
        }

        // Save input as shared state only if authentication succeeded
        if (storePass &&
            !sharedState.containsKey(USERNAME_KEY) &&
            !sharedState.containsKey(PASSWORD_KEY)) {
            sharedState.put(USERNAME_KEY, username);
            sharedState.put(PASSWORD_KEY, password);
        }

        // Create the user principals
        userPrincipal = new UserPrincipal(username);
        if (authzIdentity != null) {
            authzPrincipal = new UserPrincipal(authzIdentity);
        }

        try {

            ldapPrincipal = new LdapPrincipal(dn);

        } catch (InvalidNameException e) {
            if (debug) {
                System.out.println("\t\t[LdapLoginModule] " +
                                   "cannot create LdapPrincipal: bad DN");
            }
            throw (LoginException)
                new FailedLoginException("Cannot create LdapPrincipal")
                    .initCause(e);
        }
    }

    /**
     * Search for the user's entry.
     * Determine the distinguished name of the user's entry and optionally
     * an authorization identity for the user.
     *
     * <p>
     *  尝试认证
     * 
     * 
     * @param ctx an LDAP context to use for the search
     * @return the user's distinguished name or an empty string if none
     *         was found.
     * @exception LoginException if the user's entry cannot be found.
     */
    private String findUserDN(LdapContext ctx) throws LoginException {

        String userDN = "";

        // Locate the user's LDAP entry
        if (userFilter != null) {
            if (debug) {
                System.out.println("\t\t[LdapLoginModule] " +
                    "searching for entry belonging to user: " + username);
            }
        } else {
            if (debug) {
                System.out.println("\t\t[LdapLoginModule] " +
                    "cannot search for entry belonging to user: " + username);
            }
            throw (LoginException)
                new FailedLoginException("Cannot find user's LDAP entry");
        }

        try {
            NamingEnumeration<SearchResult> results = ctx.search("",
                replaceUsernameToken(filterMatcher, userFilter), constraints);

            // Extract the distinguished name of the user's entry
            // (Use the first entry if more than one is returned)
            if (results.hasMore()) {
                SearchResult entry = results.next();

                // %%% - use the SearchResult.getNameInNamespace method
                //        available in JDK 1.5 and later.
                //        (can remove call to constraints.setReturningObjFlag)
                userDN = ((Context)entry.getObject()).getNameInNamespace();

                if (debug) {
                    System.out.println("\t\t[LdapLoginModule] found entry: " +
                        userDN);
                }

                // Extract a value from user's authorization identity attribute
                if (authzIdentityAttr != null) {
                    Attribute attr =
                        entry.getAttributes().get(authzIdentityAttr);
                    if (attr != null) {
                        Object val = attr.get();
                        if (val instanceof String) {
                            authzIdentity = (String) val;
                        }
                    }
                }

                results.close();

            } else {
                // Bad username
                if (debug) {
                    System.out.println("\t\t[LdapLoginModule] user's entry " +
                        "not found");
                }
            }

        } catch (NamingException e) {
            // ignore
        }

        if (userDN.equals("")) {
            throw (LoginException)
                new FailedLoginException("Cannot find user's LDAP entry");
        } else {
            return userDN;
        }
    }

    /**
     * Replace the username token
     *
     * <p>
     *  搜索用户的条目。确定用户条目的可分辨名称以及可选的用户的授权身份。
     * 
     * 
     * @param string the target string
     * @return the modified string
     */
    private String replaceUsernameToken(Matcher matcher, String string) {
        return matcher != null ? matcher.replaceAll(username) : string;
    }

    /**
     * Get the username and password.
     * This method does not return any value.
     * Instead, it sets global name and password variables.
     *
     * <p> Also note that this method will set the username and password
     * values in the shared state in case subsequent LoginModules
     * want to use them via use/tryFirstPass.
     *
     * <p>
     *  替换用户名令牌
     * 
     * 
     * @param getPasswdFromSharedState boolean that tells this method whether
     *          to retrieve the password from the sharedState.
     * @exception LoginException if the username/password cannot be acquired.
     */
    private void getUsernamePassword(boolean getPasswdFromSharedState)
        throws LoginException {

        if (getPasswdFromSharedState) {
            // use the password saved by the first module in the stack
            username = (String)sharedState.get(USERNAME_KEY);
            password = (char[])sharedState.get(PASSWORD_KEY);
            return;
        }

        // prompt for a username and password
        if (callbackHandler == null)
            throw new LoginException("No CallbackHandler available " +
                "to acquire authentication information from the user");

        Callback[] callbacks = new Callback[2];
        callbacks[0] = new NameCallback(rb.getString("username."));
        callbacks[1] = new PasswordCallback(rb.getString("password."), false);

        try {
            callbackHandler.handle(callbacks);
            username = ((NameCallback)callbacks[0]).getName();
            char[] tmpPassword = ((PasswordCallback)callbacks[1]).getPassword();
            password = new char[tmpPassword.length];
            System.arraycopy(tmpPassword, 0,
                                password, 0, tmpPassword.length);
            ((PasswordCallback)callbacks[1]).clearPassword();

        } catch (java.io.IOException ioe) {
            throw new LoginException(ioe.toString());

        } catch (UnsupportedCallbackException uce) {
            throw new LoginException("Error: " + uce.getCallback().toString() +
                        " not available to acquire authentication information" +
                        " from the user");
        }
    }

    /**
     * Clean out state because of a failed authentication attempt
     * <p>
     *  获取用户名和密码。此方法不返回任何值。相反,它设置全局名称和密码变量。
     * 
     *  <p>另请注意,此方法将在共享状态下设置用户名和密码值,以防后续LoginModules想通过use / tryFirstPass使用它们。
     * 
     */
    private void cleanState() {
        username = null;
        if (password != null) {
            Arrays.fill(password, ' ');
            password = null;
        }
        try {
            if (ctx != null) {
                ctx.close();
            }
        } catch (NamingException e) {
            // ignore
        }
        ctx = null;

        if (clearPass) {
            sharedState.remove(USERNAME_KEY);
            sharedState.remove(PASSWORD_KEY);
        }
    }
}
