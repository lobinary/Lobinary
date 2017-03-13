/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.io.*;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.MessageFormat;
import java.util.*;

import javax.security.auth.*;
import javax.security.auth.kerberos.*;
import javax.security.auth.callback.*;
import javax.security.auth.login.*;
import javax.security.auth.spi.*;

import sun.security.krb5.*;
import sun.security.jgss.krb5.Krb5Util;
import sun.security.krb5.Credentials;
import sun.misc.HexDumpEncoder;

/**
 * <p> This <code>LoginModule</code> authenticates users using
 * Kerberos protocols.
 *
 * <p> The configuration entry for <code>Krb5LoginModule</code> has
 * several options that control the authentication process and
 * additions to the <code>Subject</code>'s private credential
 * set. Irrespective of these options, the <code>Subject</code>'s
 * principal set and private credentials set are updated only when
 * <code>commit</code> is called.
 * When <code>commit</code> is called, the <code>KerberosPrincipal</code>
 * is added to the <code>Subject</code>'s principal set (unless the
 * <code>principal</code> is specified as "*"). If <code>isInitiator</code>
 * is true, the <code>KerberosTicket</code> is
 * added to the <code>Subject</code>'s private credentials.
 *
 * <p> If the configuration entry for <code>KerberosLoginModule</code>
 * has the option <code>storeKey</code> set to true, then
 * <code>KerberosKey</code> or <code>KeyTab</code> will also be added to the
 * subject's private credentials. <code>KerberosKey</code>, the principal's
 * key(s) will be derived from user's password, and <code>KeyTab</code> is
 * the keytab used when <code>useKeyTab</code> is set to true. The
 * <code>KeyTab</code> object is restricted to be used by the specified
 * principal unless the principal value is "*".
 *
 * <p> This <code>LoginModule</code> recognizes the <code>doNotPrompt</code>
 * option. If set to true the user will not be prompted for the password.
 *
 * <p> The user can  specify the location of the ticket cache by using
 * the option <code>ticketCache</code> in the configuration entry.
 *
 * <p>The user can specify the keytab location by using
 * the option <code>keyTab</code>
 * in the configuration entry.
 *
 * <p> The principal name can be specified in the configuration entry
 * by using the option <code>principal</code>. The principal name
 * can either be a simple user name, a service name such as
 * <code>host/mission.eng.sun.com</code>, or "*". The principal can also
 * be set using the system property <code>sun.security.krb5.principal</code>.
 * This property is checked during login. If this property is not set, then
 * the principal name from the configuration is used. In the
 * case where the principal property is not set and the principal
 * entry also does not exist, the user is prompted for the name.
 * When this property of entry is set, and <code>useTicketCache</code>
 * is set to true, only TGT belonging to this principal is used.
 *
 * <p> The following is a list of configuration options supported
 * for <code>Krb5LoginModule</code>:
 * <blockquote><dl>
 * <dt><b><code>refreshKrb5Config</code></b>:</dt>
 * <dd> Set this to true, if you want the configuration
 * to be refreshed before the <code>login</code> method is called.</dd>
 * <dt><b><code>useTicketCache</code></b>:</dt>
 * <dd>Set this to true, if you want the
 * TGT to be obtained
 * from the ticket cache. Set this option
 * to false if you do not want this module to use the ticket cache.
 * (Default is False).
 * This module will
 * search for the ticket
 * cache in the following locations:
 * On Solaris and Linux
 * it will look for the ticket cache in /tmp/krb5cc_<code>uid</code>
 * where the uid is numeric user
 * identifier. If the ticket cache is
 * not available in the above location, or if we are on a
 * Windows platform, it will look for the cache as
 * {user.home}{file.separator}krb5cc_{user.name}.
 * You can override the ticket cache location by using
 * <code>ticketCache</code>.
 * For Windows, if a ticket cannot be retrieved from the file ticket cache,
 * it will use Local Security Authority (LSA) API to get the TGT.
 * <dt><b><code>ticketCache</code></b>:</dt>
 * <dd>Set this to the name of the ticket
 * cache that  contains user's TGT.
 * If this is set,  <code>useTicketCache</code>
 * must also be set to true; Otherwise a configuration error will
 * be returned.</dd>
 * <dt><b><code>renewTGT</code></b>:</dt>
 * <dd>Set this to true, if you want to renew
 * the TGT. If this is set, <code>useTicketCache</code> must also be
 * set to true; otherwise a configuration error will be returned.</dd>
 * <dt><b><code>doNotPrompt</code></b>:</dt>
 * <dd>Set this to true if you do not want to be
 * prompted for the password
 * if credentials can not be obtained from the cache, the keytab,
 * or through shared state.(Default is false)
 * If set to true, credential must be obtained through cache, keytab,
 * or shared state. Otherwise, authentication will fail.</dd>
 * <dt><b><code>useKeyTab</code></b>:</dt>
 * <dd>Set this to true if you
 * want the module to get the principal's key from the
 * the keytab.(default value is False)
 * If <code>keytab</code>
 * is not set then
 * the module will locate the keytab from the
 * Kerberos configuration file.
 * If it is not specified in the Kerberos configuration file
 * then it will look for the file
 * <code>{user.home}{file.separator}</code>krb5.keytab.</dd>
 * <dt><b><code>keyTab</code></b>:</dt>
 * <dd>Set this to the file name of the
 * keytab to get principal's secret key.</dd>
 * <dt><b><code>storeKey</code></b>:</dt>
 * <dd>Set this to true to if you want the keytab or the
 * principal's key to be stored in the Subject's private credentials.
 * For <code>isInitiator</code> being false, if <code>principal</code>
 * is "*", the {@link KeyTab} stored can be used by anyone, otherwise,
 * it's restricted to be used by the specified principal only.</dd>
 * <dt><b><code>principal</code></b>:</dt>
 * <dd>The name of the principal that should
 * be used. The principal can be a simple username such as
 * "<code>testuser</code>" or a service name such as
 * "<code>host/testhost.eng.sun.com</code>". You can use the
 * <code>principal</code>  option to set the principal when there are
 * credentials for multiple principals in the
 * <code>keyTab</code> or when you want a specific ticket cache only.
 * The principal can also be set using the system property
 * <code>sun.security.krb5.principal</code>. In addition, if this
 * system property is defined, then it will be used. If this property
 * is not set, then the principal name from the configuration will be
 * used.
 * The principal name can be set to "*" when <code>isInitiator</code> is false.
 * In this case, the acceptor is not bound to a single principal. It can
 * act as any principal an initiator requests if keys for that principal
 * can be found. When <code>isInitiator</code> is true, the principal name
 * cannot be set to "*".
 * </dd>
 * <dt><b><code>isInitiator</code></b>:</dt>
 * <dd>Set this to true, if initiator. Set this to false, if acceptor only.
 * (Default is true).
 * Note: Do not set this value to false for initiators.</dd>
 * </dl></blockquote>
 *
 * <p> This <code>LoginModule</code> also recognizes the following additional
 * <code>Configuration</code>
 * options that enable you to share username and passwords across different
 * authentication modules:
 * <blockquote><dl>
 *
 *    <dt><b><code>useFirstPass</code></b>:</dt>
 *                   <dd>if, true, this LoginModule retrieves the
 *                   username and password from the module's shared state,
 *                   using "javax.security.auth.login.name" and
 *                   "javax.security.auth.login.password" as the respective
 *                   keys. The retrieved values are used for authentication.
 *                   If authentication fails, no attempt for a retry
 *                   is made, and the failure is reported back to the
 *                   calling application.</dd>
 *
 *    <dt><b><code>tryFirstPass</code></b>:</dt>
 *                   <dd>if, true, this LoginModule retrieves the
 *                   the username and password from the module's shared
 *                   state using "javax.security.auth.login.name" and
 *                   "javax.security.auth.login.password" as the respective
 *                   keys.  The retrieved values are used for
 *                   authentication.
 *                   If authentication fails, the module uses the
 *                   CallbackHandler to retrieve a new username
 *                   and password, and another attempt to authenticate
 *                   is made. If the authentication fails,
 *                   the failure is reported back to the calling application</dd>
 *
 *    <dt><b><code>storePass</code></b>:</dt>
 *                   <dd>if, true, this LoginModule stores the username and
 *                   password obtained from the CallbackHandler in the
 *                   modules shared state, using
 *                   "javax.security.auth.login.name" and
 *                   "javax.security.auth.login.password" as the respective
 *                   keys.  This is not performed if existing values already
 *                   exist for the username and password in the shared
 *                   state, or if authentication fails.</dd>
 *
 *    <dt><b><code>clearPass</code></b>:</dt>
 *                   <dd>if, true, this LoginModule clears the
 *                   username and password stored in the module's shared
 *                   state  after both phases of authentication
 *                   (login and commit) have completed.</dd>
 * </dl></blockquote>
 * <p>If the principal system property or key is already provided, the value of
 * "javax.security.auth.login.name" in the shared state is ignored.
 * <p>When multiple mechanisms to retrieve a ticket or key is provided, the
 * preference order is:
 * <ol>
 * <li>ticket cache
 * <li>keytab
 * <li>shared state
 * <li>user prompt
 * </ol>
 * <p>Note that if any step fails, it will fallback to the next step.
 * There's only one exception, if the shared state step fails and
 * <code>useFirstPass</code>=true, no user prompt is made.
 * <p>Examples of some configuration values for Krb5LoginModule in
 * JAAS config file and the results are:
 * <ul>
 * <p> <code>doNotPrompt</code>=true;
 * </ul>
 * <p> This is an illegal combination since none of <code>useTicketCache</code>,
 * <code>useKeyTab</code>, <code>useFirstPass</code> and <code>tryFirstPass</code>
 * is set and the user can not be prompted for the password.
 *<ul>
 * <p> <code>ticketCache</code> = &lt;filename&gt;;
 *</ul>
 * <p> This is an illegal combination since <code>useTicketCache</code>
 * is not set to true and the ticketCache is set. A configuration error
 * will occur.
 * <ul>
 * <p> <code>renewTGT</code>=true;
 *</ul>
 * <p> This is an illegal combination since <code>useTicketCache</code> is
 * not set to true and renewTGT is set. A configuration error will occur.
 * <ul>
 * <p> <code>storeKey</code>=true
 * <code>useTicketCache</code> = true
 * <code>doNotPrompt</code>=true;;
 *</ul>
 * <p> This is an illegal combination since  <code>storeKey</code> is set to
 * true but the key can not be obtained either by prompting the user or from
 * the keytab, or from the shared state. A configuration error will occur.
 * <ul>
 * <p>  <code>keyTab</code> = &lt;filename&gt; <code>doNotPrompt</code>=true ;
 * </ul>
 * <p>This is an illegal combination since useKeyTab is not set to true and
 * the keyTab is set. A configuration error will occur.
 * <ul>
 * <p> <code>debug=true </code>
 *</ul>
 * <p> Prompt the user for the principal name and the password.
 * Use the authentication exchange to get TGT from the KDC and
 * populate the <code>Subject</code> with the principal and TGT.
 * Output debug messages.
 * <ul>
 * <p> <code>useTicketCache</code> = true <code>doNotPrompt</code>=true;
 *</ul>
 * <p>Check the default cache for TGT and populate the <code>Subject</code>
 * with the principal and TGT. If the TGT is not available,
 * do not prompt the user, instead fail the authentication.
 * <ul>
 * <p><code>principal</code>=&lt;name&gt;<code>useTicketCache</code> = true
 * <code>doNotPrompt</code>=true;
 *</ul>
 * <p> Get the TGT from the default cache for the principal and populate the
 * Subject's principal and private creds set. If ticket cache is
 * not available or does not contain the principal's TGT
 * authentication will fail.
 * <ul>
 * <p> <code>useTicketCache</code> = true
 * <code>ticketCache</code>=&lt;file name&gt;<code>useKeyTab</code> = true
 * <code> keyTab</code>=&lt;keytab filename&gt;
 * <code>principal</code> = &lt;principal name&gt;
 * <code>doNotPrompt</code>=true;
 *</ul>
 * <p>  Search the cache for the principal's TGT. If it is not available
 * use the key in the keytab to perform authentication exchange with the
 * KDC and acquire the TGT.
 * The Subject will be populated with the principal and the TGT.
 * If the key is not available or valid then authentication will fail.
 * <ul>
 * <p><code>useTicketCache</code> = true
 * <code>ticketCache</code>=&lt;file name&gt;
 *</ul>
 * <p> The TGT will be obtained from the cache specified.
 * The Kerberos principal name used will be the principal name in
 * the Ticket cache. If the TGT is not available in the
 * ticket cache the user will be prompted for the principal name
 * and the password. The TGT will be obtained using the authentication
 * exchange with the KDC.
 * The Subject will be populated with the TGT.
 *<ul>
 * <p> <code>useKeyTab</code> = true
 * <code>keyTab</code>=&lt;keytab filename&gt;
 * <code>principal</code>= &lt;principal name&gt;
 * <code>storeKey</code>=true;
 *</ul>
 * <p>  The key for the principal will be retrieved from the keytab.
 * If the key is not available in the keytab the user will be prompted
 * for the principal's password. The Subject will be populated
 * with the principal's key either from the keytab or derived from the
 * password entered.
 * <ul>
 * <p> <code>useKeyTab</code> = true
 * <code>keyTab</code>=&lt;keytabname&gt;
 * <code>storeKey</code>=true
 * <code>doNotPrompt</code>=false;
 *</ul>
 * <p>The user will be prompted for the service principal name.
 * If the principal's
 * longterm key is available in the keytab , it will be added to the
 * Subject's private credentials. An authentication exchange will be
 * attempted with the principal name and the key from the Keytab.
 * If successful the TGT will be added to the
 * Subject's private credentials set. Otherwise the authentication will
 * fail.
 * <ul>
 * <p> <code>isInitiator</code> = false <code>useKeyTab</code> = true
 * <code>keyTab</code>=&lt;keytabname&gt;
 * <code>storeKey</code>=true
 * <code>principal</code>=*;
 *</ul>
 * <p>The acceptor will be an unbound acceptor and it can act as any principal
 * as long that principal has keys in the keytab.
 *<ul>
 * <p>
 * <code>useTicketCache</code>=true
 * <code>ticketCache</code>=&lt;file name&gt;;
 * <code>useKeyTab</code> = true
 * <code>keyTab</code>=&lt;file name&gt; <code>storeKey</code>=true
 * <code>principal</code>= &lt;principal name&gt;
 *</ul>
 * <p>
 * The client's TGT will be retrieved from the ticket cache and added to the
 * <code>Subject</code>'s private credentials. If the TGT is not available
 * in the ticket cache, or the TGT's client name does not match the principal
 * name, Java will use a secret key to obtain the TGT using the authentication
 * exchange and added to the Subject's private credentials.
 * This secret key will be first retrieved from the keytab. If the key
 * is not available, the user will be prompted for the password. In either
 * case, the key derived from the password will be added to the
 * Subject's private credentials set.
 * <ul>
 * <p><code>isInitiator</code> = false
 *</ul>
 * <p>Configured to act as acceptor only, credentials are not acquired
 * via AS exchange. For acceptors only, set this value to false.
 * For initiators, do not set this value to false.
 * <ul>
 * <p><code>isInitiator</code> = true
 *</ul>
 * <p>Configured to act as initiator, credentials are acquired
 * via AS exchange. For initiators, set this value to true, or leave this
 * option unset, in which case default value (true) will be used.
 *
 * <p>
 *  <p>此<code> LoginModule </code>使用Kerberos协议验证用户
 * 
 * <p> <code> Krb5LoginModule </code>的配置条目有几个选项,用于控制身份验证过程和添加到<code>主体</code>的私人凭证集不考虑这些选项,<code>主题</code>
 * 的主体集和私有凭证集仅在调用<code> commit </code>时更新当调用<code> commit </code>时,将<code> KerberosPrincipal </code>添加到<code>
 *  Subject </code>的主体集(除非<code> principal </code>被指定为"*")如果<code> isInitiator </code>为true,<code> Kerbe
 * rosTicket <代码>添加到<code> Subject </code>的私人凭据。
 * 
 * <p>如果<code> KerberosLoginModule </code>的配置条目的选项<code> storeKey </code>设置为true,则<code> KerberosKey </code>
 * 或<code> KeyTab </code>也将被添加到主体的私人凭证<code> KerberosKey </code>,主体的密钥将从用户密码派生,<code> KeyTab </code>是<code>
 *  useKeyTab < code>设置为true <code> KeyTab </code>对象被限制为由指定的主体使用,除非主体值为"*"。
 * 
 *  <p>此<code> LoginModule </code>可识别<code> doNotPrompt </code>选项如果设置为true,系统不会提示用户输入密码
 * 
 *  <p>用户可以使用配置项中的选项<code> ticketCache </code>指定票证缓存的位置
 * 
 * <p>用户可以使用配置项中的<code> keyTab </code>选项指定keytab位置
 * 
 * <p>主体名称可以使用选项<code> principal </code>在配置条目中指定。
 * 主体名称可以是简单的用户名,服务名称如<code> host / missionengsuncom </code >或"*"主体也可以使用系统属性设置<code> sunsecuritykrb5prin
 * cipal </code>在登录期间检查此属性如果未设置此属性,则使用配置中的主体名称。
 * <p>主体名称可以使用选项<code> principal </code>在配置条目中指定。
 * 主体属性未设置,主体条目也不存在,则提示用户输入名称当设置了此属性并将<code> useTicketCache </code>设置为true时,只有属于此主体的TGT用来。
 * 
 * <p>以下是<code> Krb5LoginModule </code>支持的配置选项列表：<blockquote> <dl> <dt> <b> <code> refreshKrb5Config </code>
 *  </b>：</dt > <dd>如果您希望在调用<code> login </code>方法之前刷新配置,请将此设置为true </dd> <dt> <b> <code> useTicketCache
 *  </code> b>：</dt> <dd>如果您希望从票证缓存中获取TGT,请将此项设置为true如果不希望此模块使用票证缓存,则将此选项设置为false(默认值为False)模块将在以下位置搜索票证
 * 缓存：在Solaris和Linux上,它将在/ tmp / krb5cc_ <code> uid </code>中查找票证缓存,其中uid是数字用户标识符如果票证缓存在上述位置不可用,或者如果我们在Wi
 * ndows平台上,它将查找缓存为{userhome} {fileseparator} krb5cc_ {username}您可以使用<code>覆盖票证缓存位置, ticketCache </code>
 * 对于Windows,如果无法从文件票证缓存检索票证,它将使用本地安全机构(LSA)API获取TGT <dt> <b> <code> ticketCache </code> b>：</dt> <dd>将此
 * 设置为包含用户的TGT的票证缓存的名称如果设置此选项,还必须将<code> useTicketCache </code>设置为true;否则将返回配置错误</dd> <dt> <b> <code> re
 * newTGT </code> </b>：</dt> <dd>如果您想更新TGT如果这被设置,<code> useTicketCache </code>也必须设置为true;否则将返回配置错误</dd> 
 * <dt> <b> <code> doNotPrompt </code> </b>：将此设置为true如果不想提示如果无法从缓存,密钥表或通过共享状态获取凭据(默认为false),则密码如果设置为true
 * ,则必须通过缓存,keytab或共享状态获取凭据。
 * 否则,身份验证将失败</dd> <dt> <b> <code> useKeyTab </code> </b>：</dt> <dd>如果您希望模块从keytab获取委托人的密钥(默认值为False)如果未
 * 设置<code> keytab </code>,模块将从Kerberos配置文件中找到keytab如果没有在Kerberos配置文件中指定,那么它将查找文件<code> {userhome} {fileseparator}
 *  </code> krb5keytab </dd> <dt> <b> <code> keyTab </code> < / b>：</dt> <dd>将此设置为密钥表的文件名以获取主体的密钥</dd> <dt>
 *  <b> <code> storeKey </code> </dt> <dd>如果您希望将keytab或主体的密钥存储在主体的私有凭据中,请将此属性设置为true。
 * 对于<code> isInitiator </code>为false,如果<code> principal </code> *",任何人都可以使用{@link KeyTab}存储,否则仅限于指定的主体使
 * 用</dd> <dt> <b> <code> principal </code> </b> ：</dt> <dd>应使用的主体的名称主体可以是简单的用户名,例如"<code> testuser </code>
 * "或服务名称如"<code> host / testhostengsuncom </code>"。
 * 您可以使用<code> principal </code>当<code> keyTab </code>中有多个主体的凭证时,或者您只想要一个特定的票证缓存时,也可以使用系统属性<code> sunsec
 * uritykrb5principal </code>来设置主体。
 *  ,如果定义了此系统属性,则将使用如果此属性未设置,则将使用配置中的主体名称。
 * 当<code> isInitiator </code>为时,主体名称可设置为"*" false在这种情况下,接受器不绑定到单个主体如果可以找到该主体的键,则它可以充当发起者请求的任何主体当<code> 
 * isInitiator </code>为true时,主体名称不能设置为"*"。
 *  ,如果定义了此系统属性,则将使用如果此属性未设置,则将使用配置中的主体名称。
 * </dd>
 * <dt> <b> <code> isInitiator </code> </b>：将此设置为true,如果启动器将此设置为false如果仅接受者将启动器</dd> </dl> </blockquote>
 * 的值设置为false。
 * 
 *  <p>此<code> LoginModule </code>还会识别以下附加的<code>配置</code>选项,可用于在不同的身份验证模块之间共享用户名和密码：<blockquote> <dl>
 * 
 * <dt> <b> <code> useFirstPass </code> </b>：</dt> <dd> if,true,此LoginModule从模块的共享状态检索用户名和密码,使用"javaxsec
 * urityauthloginname"和"javaxsecurityauthloginpassword "作为相应的键所检索的值用于认证如果认证失败,则不进行重试的尝试,并且将失败报告回调用应用</dd>
 * 。
 * 
 * <dt> <b> <code> tryFirstPass </code> </b>：</dt> <dd> if,true,此LoginModule从模块的共享状态检索用户名和密码,使用"javaxsec
 * urityauthloginname"和"javaxsecurityauthloginpassword "作为相应的键检索的值用于认证如果认证失败,则模块使用CallbackHandler检索新的用户名
 * 和密码,并进行另一次认证尝试如果认证失败,则将故障报告回调用应用程序</dd>。
 * 
 * <dt> <b> <code> storePass </code> </b>：</dt> <dd> if,true,此LoginModule存储从模块共享状态从CallbackHandler获取的用户名
 * 和密码,使用"javaxsecurityauthloginname "和"javaxsecurityauthloginpassword"作为相应的键如果共享状态下的用户名和密码已存在,或者认证失败,则不
 * 会执行此操作</dd>。
 * 
 * <dt> <b> <code> clearPass </code> </b>：</dt> <dd> if,true,此LoginModule清除存储在模块共享状态中的用户名和密码和提交)已完成</dd>
 *  </dl> </blockquote> <p>如果已提供主体系统属性或键,则忽略共享状态中的"javaxsecurityauthloginname"值<p>检索票证或密钥,则偏好顺序为：。
 * <ol>
 *  <li>票证缓存<li> keytab <li>共享状态<li>用户提示
 * </ol>
 * <p>注意,如果任何步骤失败,它会回退到下一步只有一个异常,如果共享状态步骤失败,并且<code> useFirstPass </code> = true,在JAAS配置文件中Krb5LoginModu
 * le的一些配置值,结果是：。
 * <ul>
 *  <p> <code> doNotPrompt </code> = true;
 * </ul>
 *  <p>这是一种非法组合,因为未设置<code> useTicketCache </code>,<code> useKeyTab </code>,<code> useFirstPass </code>和
 * <code> tryFirstPass </code>用户不能提示输入密码。
 * ul>
 *  <p> <code> ticketCache </code> =&lt; filename&gt ;;
 * /ul>
 *  <p>这是一种非法组合,因为<code> useTicketCache </code>未设置为true,并且已设置ticketCache配置错误
 * <ul>
 *  <p> <code> renewTGT </code> = true;
 * /ul>
 * <p>这是一种非法组合,因为<code> useTicketCache </code>未设置为true,并且renewTGT已设置将出现配置错误
 * <ul>
 *  <p> <code> storeKey </code> = true <code> useTicketCache </code> = true <code> doNotPrompt </code> =
 *  true ;;。
 * /ul>
 *  <p>这是一种非法组合,因为<code> storeKey </code>设置为true,但无法通过提示用户或从keytab获取密钥,或从共享状态获取配置错误
 * <ul>
 *  <p> <code> keyTab </code> =&lt; filename&gt; <code> doNotPrompt </code> = true;
 * </ul>
 *  <p>这是一种非法组合,因为useKeyTab未设置为true,并且keyTab已设置将出现配置错误
 * <ul>
 *  <p> <code> debug = true </code>
 * /ul>
 * <p>提示用户输入主体名称和密码使用身份验证交换从KDC获取TGT,并使用主体和TGT输出调试消息填充<code>主题</code>
 * <ul>
 *  <p> <code> useTicketCache </code> = true <code> doNotPrompt </code> = true;
 * /ul>
 *  <p>检查TGT的默认缓存,并使用主体和TGT填充<code>主题</code>如果TGT不可用,请不要提示用户,
 * <ul>
 *  <p> <code> principal </code> =&lt; name&gt; <code> useTicketCache </code> = true <code> doNotPrompt 
 * </code> = true;。
 * /ul>
 *  <p>从主体的默认缓存获取TGT,并填充主体的主体和私有凭据集如果票据缓存不可用或不包含主体的TGT身份验证将失败
 * <ul>
 * 
 * @author Ram Marti
 */

@jdk.Exported
public class Krb5LoginModule implements LoginModule {

    // initial state
    private Subject subject;
    private CallbackHandler callbackHandler;
    private Map<String, Object> sharedState;
    private Map<String, ?> options;

    // configurable option
    private boolean debug = false;
    private boolean storeKey = false;
    private boolean doNotPrompt = false;
    private boolean useTicketCache = false;
    private boolean useKeyTab = false;
    private String ticketCacheName = null;
    private String keyTabName = null;
    private String princName = null;

    private boolean useFirstPass = false;
    private boolean tryFirstPass = false;
    private boolean storePass = false;
    private boolean clearPass = false;
    private boolean refreshKrb5Config = false;
    private boolean renewTGT = false;

    // specify if initiator.
    // perform authentication exchange if initiator
    private boolean isInitiator = true;

    // the authentication status
    private boolean succeeded = false;
    private boolean commitSucceeded = false;
    private String username;

    // Encryption keys calculated from password. Assigned when storekey == true
    // and useKeyTab == false (or true but not found)
    private EncryptionKey[] encKeys = null;

    KeyTab ktab = null;

    private Credentials cred = null;

    private PrincipalName principal = null;
    private KerberosPrincipal kerbClientPrinc = null;
    private KerberosTicket kerbTicket = null;
    private KerberosKey[] kerbKeys = null;
    private StringBuffer krb5PrincName = null;
    private boolean unboundServer = false;
    private char[] password = null;

    private static final String NAME = "javax.security.auth.login.name";
    private static final String PWD = "javax.security.auth.login.password";
    private static final ResourceBundle rb = AccessController.doPrivileged(
            new PrivilegedAction<ResourceBundle>() {
                public ResourceBundle run() {
                    return ResourceBundle.getBundle(
                            "sun.security.util.AuthResources");
                }
            }
    );

    /**
     * Initialize this <code>LoginModule</code>.
     *
     * <p>
     * <p>
     * <p> <code> useTicketCache </code> = true <code> keyTab </code> = true <code> ticketCache </code> =&lt
     * ; file name&gt; useKeyTab </code> ; <code> principal </code> =&lt; principal name&gt; <code> doNotPro
     * mpt </code> = true;。
     * /ul>
     *  <p>在缓存中搜索主体的TGT如果不可用,请使用keytab中的密钥与KDC执行身份验证交换并获取TGT主体将填充主体和TGT如果密钥不可用或有效,则认证将失败
     * <ul>
     *  <p> <code> useTicketCache </code> = true <code> ticketCache </code> =&lt; file name&gt;
     * /ul>
     * <p> TGT将从指定的缓存中获取。
     * 使用的Kerberos主体名称将是票证缓存中的主体名称如果票据缓存中的TGT不可用,系统将提示用户输入主体名称和密码TGT将使用与KDC的认证交换获得主体将填充TGT。
     * ul>
     *  <p> <code> useKeyTab </code> = true <code> keyTab </code> =&lt; keytab filename&gt; <code> principal
     *  </code> =&lt; principal name&gt; <code> storeKey </code> = true;。
     * /ul>
     * <p>委托人的密钥将从密钥表中检索如果密钥在密钥表中不可用,那么将提示用户输入委托人的密码。主体将使用密钥表中的主体密钥填充或从密码派生输入
     * <ul>
     *  <p> <code> useKeyTab </code> = true <code> keyTab </code> =&lt; keytabname&gt; <code> storeKey </code>
     *  = true <code> doNotPrompt </code> = false;。
     * /ul>
     * <p>将提示用户输入服务主体名称如果主体的longterm键在keytab中可用,则会将其添加到主体的私有凭证中。
     * 将尝试使用主体名称和Keytab中的键进行身份验证交换If成功TGT将被添加到主体的私有凭据集否则认证将失败。
     * <ul>
     *  <p> <code> isInitiator </code> = false <code> useKeyTab </code> = true <code> keyTab </code> =&lt; k
     * eytabname& <code> storeKey </code> = true <code> principal </code> = *;。
     * /ul>
     *  <p>接受者将是未绑定的接受者,并且只要主体在密钥表中具有密钥,它就可以作为任何主体
     * ul>
     * <p>
     * <code> useTicketCache </code> = true <code> ticketCache </code> =&lt; file name&gt ;; <code> useKeyTa
     * b </code> = true <code> keyTab </code> =&lt; file name&gt; <code> storeKey </code> = true <code> prin
     * cipal </code> =&lt; principal name&gt;。
     * /ul>
     * <p>
     * 将从票证缓存中检索客户端的TGT,并将其添加到<code> Subject </code>的专用凭证中如果TGT在票证缓存中不可用,或者TGT的客户端名称与主体名称不匹配,Java将使用秘密密钥来获得使
     * 用认证交换并且添加到主体的私人凭证的TGT。
     * 将首先从密钥表中检索该密钥。如果密钥不可用,则将提示用户输入密码在任一情况下,从密码导出的密钥将被添加到主体的私人凭据集。
     * <ul>
     *  <p> <code> isInitiator </code> = false
     * /ul>
     * <p>配置为仅作为接受方,不通过AS交换获取凭证仅对于接受方,将此值设置为false对于启动器,不要将此值设置为false
     * <ul>
     *  <p> <code> isInitiator </code> = true
     * /ul>
     *  <p>配置为充当启动器,通过AS exchange获取凭证对于启动器,请将此值设置为true,或保留此选项未设置,在这种情况下将使用默认值(true)
     * 
     * 
     * @param subject the <code>Subject</code> to be authenticated. <p>
     *
     * @param callbackHandler a <code>CallbackHandler</code> for
     *                  communication with the end user (prompting for
     *                  usernames and passwords, for example). <p>
     *
     * @param sharedState shared <code>LoginModule</code> state. <p>
     *
     * @param options options specified in the login
     *                  <code>Configuration</code> for this particular
     *                  <code>LoginModule</code>.
     */
    // Unchecked warning from (Map<String, Object>)sharedState is safe
    // since javax.security.auth.login.LoginContext passes a raw HashMap.
    // Unchecked warnings from options.get(String) are safe since we are
    // passing known keys.
    @SuppressWarnings("unchecked")
    public void initialize(Subject subject,
                           CallbackHandler callbackHandler,
                           Map<String, ?> sharedState,
                           Map<String, ?> options) {

        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.sharedState = (Map<String, Object>)sharedState;
        this.options = options;

        // initialize any configured options

        debug = "true".equalsIgnoreCase((String)options.get("debug"));
        storeKey = "true".equalsIgnoreCase((String)options.get("storeKey"));
        doNotPrompt = "true".equalsIgnoreCase((String)options.get
                                              ("doNotPrompt"));
        useTicketCache = "true".equalsIgnoreCase((String)options.get
                                                 ("useTicketCache"));
        useKeyTab = "true".equalsIgnoreCase((String)options.get("useKeyTab"));
        ticketCacheName = (String)options.get("ticketCache");
        keyTabName = (String)options.get("keyTab");
        if (keyTabName != null) {
            keyTabName = sun.security.krb5.internal.ktab.KeyTab.normalize(
                         keyTabName);
        }
        princName = (String)options.get("principal");
        refreshKrb5Config =
            "true".equalsIgnoreCase((String)options.get("refreshKrb5Config"));
        renewTGT =
            "true".equalsIgnoreCase((String)options.get("renewTGT"));

        // check isInitiator value
        String isInitiatorValue = ((String)options.get("isInitiator"));
        if (isInitiatorValue == null) {
            // use default, if value not set
        } else {
            isInitiator = "true".equalsIgnoreCase(isInitiatorValue);
        }

        tryFirstPass =
            "true".equalsIgnoreCase
            ((String)options.get("tryFirstPass"));
        useFirstPass =
            "true".equalsIgnoreCase
            ((String)options.get("useFirstPass"));
        storePass =
            "true".equalsIgnoreCase((String)options.get("storePass"));
        clearPass =
            "true".equalsIgnoreCase((String)options.get("clearPass"));
        if (debug) {
            System.out.print("Debug is  " + debug
                             + " storeKey " + storeKey
                             + " useTicketCache " + useTicketCache
                             + " useKeyTab " + useKeyTab
                             + " doNotPrompt " + doNotPrompt
                             + " ticketCache is " + ticketCacheName
                             + " isInitiator " + isInitiator
                             + " KeyTab is " + keyTabName
                             + " refreshKrb5Config is " + refreshKrb5Config
                             + " principal is " + princName
                             + " tryFirstPass is " + tryFirstPass
                             + " useFirstPass is " + useFirstPass
                             + " storePass is " + storePass
                             + " clearPass is " + clearPass + "\n");
        }
    }


    /**
     * Authenticate the user
     *
     * <p>
     *
     * <p>
     *  初始化此<code> LoginModule </code>
     * 
     * <p>
     * 
     * @return true in all cases since this <code>LoginModule</code>
     *          should not be ignored.
     *
     * @exception FailedLoginException if the authentication fails. <p>
     *
     * @exception LoginException if this <code>LoginModule</code>
     *          is unable to perform the authentication.
     */
    public boolean login() throws LoginException {

        if (refreshKrb5Config) {
            try {
                if (debug) {
                    System.out.println("Refreshing Kerberos configuration");
                }
                sun.security.krb5.Config.refresh();
            } catch (KrbException ke) {
                LoginException le = new LoginException(ke.getMessage());
                le.initCause(ke);
                throw le;
            }
        }
        String principalProperty = System.getProperty
            ("sun.security.krb5.principal");
        if (principalProperty != null) {
            krb5PrincName = new StringBuffer(principalProperty);
        } else {
            if (princName != null) {
                krb5PrincName = new StringBuffer(princName);
            }
        }

        validateConfiguration();

        if (krb5PrincName != null && krb5PrincName.toString().equals("*")) {
            unboundServer = true;
        }

        if (tryFirstPass) {
            try {
                attemptAuthentication(true);
                if (debug)
                    System.out.println("\t\t[Krb5LoginModule] " +
                                       "authentication succeeded");
                succeeded = true;
                cleanState();
                return true;
            } catch (LoginException le) {
                // authentication failed -- try again below by prompting
                cleanState();
                if (debug) {
                    System.out.println("\t\t[Krb5LoginModule] " +
                                       "tryFirstPass failed with:" +
                                       le.getMessage());
                }
            }
        } else if (useFirstPass) {
            try {
                attemptAuthentication(true);
                succeeded = true;
                cleanState();
                return true;
            } catch (LoginException e) {
                // authentication failed -- clean out state
                if (debug) {
                    System.out.println("\t\t[Krb5LoginModule] " +
                                       "authentication failed \n" +
                                       e.getMessage());
                }
                succeeded = false;
                cleanState();
                throw e;
            }
        }

        // attempt the authentication by getting the username and pwd
        // by prompting or configuration i.e. not from shared state

        try {
            attemptAuthentication(false);
            succeeded = true;
            cleanState();
            return true;
        } catch (LoginException e) {
            // authentication failed -- clean out state
            if (debug) {
                System.out.println("\t\t[Krb5LoginModule] " +
                                   "authentication failed \n" +
                                   e.getMessage());
            }
            succeeded = false;
            cleanState();
            throw e;
        }
    }
    /**
     * process the configuration options
     * Get the TGT either out of
     * cache or from the KDC using the password entered
     * Check the  permission before getting the TGT
     * <p>
     *  验证用户
     * 
     * <p>
     * 
     */

    private void attemptAuthentication(boolean getPasswdFromSharedState)
        throws LoginException {

        /*
         * Check the creds cache to see whether
         * we have TGT for this client principal
         * <p>
         *  处理配置选项使用输入的密码从缓存或KDC中获取TGT在获取TGT之前检查权限
         * 
         */
        if (krb5PrincName != null) {
            try {
                principal = new PrincipalName
                    (krb5PrincName.toString(),
                     PrincipalName.KRB_NT_PRINCIPAL);
            } catch (KrbException e) {
                LoginException le = new LoginException(e.getMessage());
                le.initCause(e);
                throw le;
            }
        }

        try {
            if (useTicketCache) {
                // ticketCacheName == null implies the default cache
                if (debug)
                    System.out.println("Acquire TGT from Cache");
                cred  = Credentials.acquireTGTFromCache
                    (principal, ticketCacheName);

                if (cred != null) {
                    // check to renew credentials
                    if (!isCurrent(cred)) {
                        if (renewTGT) {
                            cred = renewCredentials(cred);
                        } else {
                            // credentials have expired
                            cred = null;
                            if (debug)
                                System.out.println("Credentials are" +
                                                " no longer valid");
                        }
                    }
                }

                if (cred != null) {
                   // get the principal name from the ticket cache
                   if (principal == null) {
                        principal = cred.getClient();
                   }
                }
                if (debug) {
                    System.out.println("Principal is " + principal);
                    if (cred == null) {
                        System.out.println
                            ("null credentials from Ticket Cache");
                    }
                }
            }

            // cred = null indicates that we didn't get the creds
            // from the cache or useTicketCache was false

            if (cred == null) {
                // We need the principal name whether we use keytab
                // or AS Exchange
                if (principal == null) {
                    promptForName(getPasswdFromSharedState);
                    principal = new PrincipalName
                        (krb5PrincName.toString(),
                         PrincipalName.KRB_NT_PRINCIPAL);
                }

                /*
                 * Before dynamic KeyTab support (6894072), here we check if
                 * the keytab contains keys for the principal. If no, keytab
                 * will not be used and password is prompted for.
                 *
                 * After 6894072, we normally don't check it, and expect the
                 * keys can be populated until a real connection is made. The
                 * check is still done when isInitiator == true, where the keys
                 * will be used right now.
                 *
                 * Probably tricky relations:
                 *
                 * useKeyTab is config flag, but when it's true but the ktab
                 * does not contains keys for principal, we would use password
                 * and keep the flag unchanged (for reuse?). In this method,
                 * we use (ktab != null) to check whether keytab is used.
                 * After this method (and when storeKey == true), we use
                 * (encKeys == null) to check.
                 * <p>
                 *  检查creds缓存以查看我们是否有此客户端主体的TGT
                 * 
                 */
                if (useKeyTab) {
                    if (!unboundServer) {
                        KerberosPrincipal kp =
                                new KerberosPrincipal(principal.getName());
                        ktab = (keyTabName == null)
                                ? KeyTab.getInstance(kp)
                                : KeyTab.getInstance(kp, new File(keyTabName));
                    } else {
                        ktab = (keyTabName == null)
                                ? KeyTab.getUnboundInstance()
                                : KeyTab.getUnboundInstance(new File(keyTabName));
                    }
                    if (isInitiator) {
                        if (Krb5Util.keysFromJavaxKeyTab(ktab, principal).length
                                == 0) {
                            ktab = null;
                            if (debug) {
                                System.out.println
                                    ("Key for the principal " +
                                     principal  +
                                     " not available in " +
                                     ((keyTabName == null) ?
                                      "default key tab" : keyTabName));
                            }
                        }
                    }
                }

                KrbAsReqBuilder builder;

                if (ktab == null) {
                    promptForPass(getPasswdFromSharedState);
                    builder = new KrbAsReqBuilder(principal, password);
                    if (isInitiator) {
                        // XXX Even if isInitiator=false, it might be
                        // better to do an AS-REQ so that keys can be
                        // updated with PA info
                        cred = builder.action().getCreds();
                    }
                    if (storeKey) {
                        encKeys = builder.getKeys(isInitiator);
                        // When encKeys is empty, the login actually fails.
                        // For compatibility, exception is thrown in commit().
                    }
                } else {
                    builder = new KrbAsReqBuilder(principal, ktab);
                    if (isInitiator) {
                        cred = builder.action().getCreds();
                    }
                }
                builder.destroy();

                if (debug) {
                    System.out.println("principal is " + principal);
                    HexDumpEncoder hd = new HexDumpEncoder();
                    if (ktab != null) {
                        System.out.println("Will use keytab");
                    } else if (storeKey) {
                        for (int i = 0; i < encKeys.length; i++) {
                            System.out.println("EncryptionKey: keyType=" +
                                encKeys[i].getEType() +
                                " keyBytes (hex dump)=" +
                                hd.encodeBuffer(encKeys[i].getBytes()));
                        }
                    }
                }

                // we should hava a non-null cred
                if (isInitiator && (cred == null)) {
                    throw new LoginException
                        ("TGT Can not be obtained from the KDC ");
                }

            }
        } catch (KrbException e) {
            LoginException le = new LoginException(e.getMessage());
            le.initCause(e);
            throw le;
        } catch (IOException ioe) {
            LoginException ie = new LoginException(ioe.getMessage());
            ie.initCause(ioe);
            throw ie;
        }
    }

    private void promptForName(boolean getPasswdFromSharedState)
        throws LoginException {
        krb5PrincName = new StringBuffer("");
        if (getPasswdFromSharedState) {
            // use the name saved by the first module in the stack
            username = (String)sharedState.get(NAME);
            if (debug) {
                System.out.println
                    ("username from shared state is " + username + "\n");
            }
            if (username == null) {
                System.out.println
                    ("username from shared state is null\n");
                throw new LoginException
                    ("Username can not be obtained from sharedstate ");
            }
            if (debug) {
                System.out.println
                    ("username from shared state is " + username + "\n");
            }
            if (username != null && username.length() > 0) {
                krb5PrincName.insert(0, username);
                return;
            }
        }

        if (doNotPrompt) {
            throw new LoginException
                ("Unable to obtain Principal Name for authentication ");
        } else {
            if (callbackHandler == null)
                throw new LoginException("No CallbackHandler "
                                         + "available "
                                         + "to garner authentication "
                                         + "information from the user");
            try {
                String defUsername = System.getProperty("user.name");

                Callback[] callbacks = new Callback[1];
                MessageFormat form = new MessageFormat(
                                       rb.getString(
                                       "Kerberos.username.defUsername."));
                Object[] source =  {defUsername};
                callbacks[0] = new NameCallback(form.format(source));
                callbackHandler.handle(callbacks);
                username = ((NameCallback)callbacks[0]).getName();
                if (username == null || username.length() == 0)
                    username = defUsername;
                krb5PrincName.insert(0, username);

            } catch (java.io.IOException ioe) {
                throw new LoginException(ioe.getMessage());
            } catch (UnsupportedCallbackException uce) {
                throw new LoginException
                    (uce.getMessage()
                     +" not available to garner "
                     +" authentication information "
                     +" from the user");
            }
        }
    }

    private void promptForPass(boolean getPasswdFromSharedState)
        throws LoginException {

        if (getPasswdFromSharedState) {
            // use the password saved by the first module in the stack
            password = (char[])sharedState.get(PWD);
            if (password == null) {
                if (debug) {
                    System.out.println
                        ("Password from shared state is null");
                }
                throw new LoginException
                    ("Password can not be obtained from sharedstate ");
            }
            if (debug) {
                System.out.println
                    ("password is " + new String(password));
            }
            return;
        }
        if (doNotPrompt) {
            throw new LoginException
                ("Unable to obtain password from user\n");
        } else {
            if (callbackHandler == null)
                throw new LoginException("No CallbackHandler "
                                         + "available "
                                         + "to garner authentication "
                                         + "information from the user");
            try {
                Callback[] callbacks = new Callback[1];
                String userName = krb5PrincName.toString();
                MessageFormat form = new MessageFormat(
                                         rb.getString(
                                         "Kerberos.password.for.username."));
                Object[] source = {userName};
                callbacks[0] = new PasswordCallback(
                                                    form.format(source),
                                                    false);
                callbackHandler.handle(callbacks);
                char[] tmpPassword = ((PasswordCallback)
                                      callbacks[0]).getPassword();
                if (tmpPassword == null) {
                    throw new LoginException("No password provided");
                }
                password = new char[tmpPassword.length];
                System.arraycopy(tmpPassword, 0,
                                 password, 0, tmpPassword.length);
                ((PasswordCallback)callbacks[0]).clearPassword();


                // clear tmpPassword
                for (int i = 0; i < tmpPassword.length; i++)
                    tmpPassword[i] = ' ';
                tmpPassword = null;
                if (debug) {
                    System.out.println("\t\t[Krb5LoginModule] " +
                                       "user entered username: " +
                                       krb5PrincName);
                    System.out.println();
                }
            } catch (java.io.IOException ioe) {
                throw new LoginException(ioe.getMessage());
            } catch (UnsupportedCallbackException uce) {
                throw new LoginException(uce.getMessage()
                                         +" not available to garner "
                                         +" authentication information "
                                         + "from the user");
            }
        }
    }

    private void validateConfiguration() throws LoginException {
        if (doNotPrompt && !useTicketCache && !useKeyTab
                && !tryFirstPass && !useFirstPass)
            throw new LoginException
                ("Configuration Error"
                 + " - either doNotPrompt should be "
                 + " false or at least one of useTicketCache, "
                 + " useKeyTab, tryFirstPass and useFirstPass"
                 + " should be true");
        if (ticketCacheName != null && !useTicketCache)
            throw new LoginException
                ("Configuration Error "
                 + " - useTicketCache should be set "
                 + "to true to use the ticket cache"
                 + ticketCacheName);
        if (keyTabName != null & !useKeyTab)
            throw new LoginException
                ("Configuration Error - useKeyTab should be set to true "
                 + "to use the keytab" + keyTabName);
        if (storeKey && doNotPrompt && !useKeyTab
                && !tryFirstPass && !useFirstPass)
            throw new LoginException
                ("Configuration Error - either doNotPrompt should be set to "
                 + " false or at least one of tryFirstPass, useFirstPass "
                 + "or useKeyTab must be set to true for storeKey option");
        if (renewTGT && !useTicketCache)
            throw new LoginException
                ("Configuration Error"
                 + " - either useTicketCache should be "
                 + " true or renewTGT should be false");
        if (krb5PrincName != null && krb5PrincName.toString().equals("*")) {
            if (isInitiator) {
                throw new LoginException
                    ("Configuration Error"
                    + " - principal cannot be * when isInitiator is true");
            }
        }
    }

    private boolean isCurrent(Credentials creds)
    {
        Date endTime = creds.getEndTime();
        if (endTime != null) {
            return (System.currentTimeMillis() <= endTime.getTime());
        }
        return true;
    }

    private Credentials renewCredentials(Credentials creds)
    {
        Credentials lcreds;
        try {
            if (!creds.isRenewable())
                throw new RefreshFailedException("This ticket" +
                                " is not renewable");
            if (System.currentTimeMillis() > cred.getRenewTill().getTime())
                throw new RefreshFailedException("This ticket is past "
                                             + "its last renewal time.");
            lcreds = creds.renew();
            if (debug)
                System.out.println("Renewed Kerberos Ticket");
        } catch (Exception e) {
            lcreds = null;
            if (debug)
                System.out.println("Ticket could not be renewed : "
                                + e.getMessage());
        }
        return lcreds;
    }

    /**
     * <p> This method is called if the LoginContext's
     * overall authentication succeeded
     * (the relevant REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL
     * LoginModules succeeded).
     *
     * <p> If this LoginModule's own authentication attempt
     * succeeded (checked by retrieving the private state saved by the
     * <code>login</code> method), then this method associates a
     * <code>Krb5Principal</code>
     * with the <code>Subject</code> located in the
     * <code>LoginModule</code>. It adds Kerberos Credentials to the
     *  the Subject's private credentials set. If this LoginModule's own
     * authentication attempted failed, then this method removes
     * any state that was originally saved.
     *
     * <p>
     *
     * <p>
     * 在动态KeyTab支持(6894072)之前,这里我们检查keytab是否包含主体的密钥如果没有,keytab将不会使用,并提示密码
     * 
     *  6894072之后,我们通常不检查它,并期望键可以填充,直到一个真正的连接做检查仍然完成当isInitiator == true,其中的键将立即使用
     * 
     *  可能棘手的关系：
     * 
     *  useKeyTab是config标志,但是当它为真,但是ktab不包含主体的密钥时,我们将使用密码并保持标志不变(用于重用?)在此方法中,我们使用(ktab！= null)检查keytab是否used
     * 在这个方法之后(当storeKey == true时),我们使用(encKeys == null)来检查。
     * 
     * 
     * @exception LoginException if the commit fails.
     *
     * @return true if this LoginModule's own login and commit
     *          attempts succeeded, or false otherwise.
     */

    public boolean commit() throws LoginException {

        /*
         * Let us add the Krb5 Creds to the Subject's
         * private credentials. The credentials are of type
         * KerberosKey or KerberosTicket
         * <p>
         * <p>如果LoginContext的整体认证成功(相关的REQUIRED,REQUISITE,SUFFICIENT和OPTIONAL LoginModules成功),则调用此方法。
         * 
         *  <p>如果此LoginModule自己的验证尝试成功(通过检索由<code> login </code>方法保存的私有状态进行检查),则此方法将<code> Krb5Principal </code>
         * 与<code> Subject </code>位于<code> LoginModule </code>它将Kerberos凭据添加到主体的私有凭据集如果此LoginModule自己的身份验证尝试失败,则
         * 此方法将删除最初保存的任何状态。
         * 
         * <p>
         * 
         */
        if (succeeded == false) {
            return false;
        } else {

            if (isInitiator && (cred == null)) {
                succeeded = false;
                throw new LoginException("Null Client Credential");
            }

            if (subject.isReadOnly()) {
                cleanKerberosCred();
                throw new LoginException("Subject is Readonly");
            }

            /*
             * Add the Principal (authenticated identity)
             * to the Subject's principal set and
             * add the credentials (TGT or Service key) to the
             * Subject's private credentials
             * <p>
             *  让我们将Krb5 Creds添加到主体的私有凭证凭据的类型为KerberosKey或KerberosTicket
             * 
             */

            Set<Object> privCredSet =  subject.getPrivateCredentials();
            Set<java.security.Principal> princSet  = subject.getPrincipals();
            kerbClientPrinc = new KerberosPrincipal(principal.getName());

            // create Kerberos Ticket
            if (isInitiator) {
                kerbTicket = Krb5Util.credsToTicket(cred);
            }

            if (storeKey && encKeys != null) {
                if (encKeys.length == 0) {
                    succeeded = false;
                    throw new LoginException("Null Server Key ");
                }

                kerbKeys = new KerberosKey[encKeys.length];
                for (int i = 0; i < encKeys.length; i ++) {
                    Integer temp = encKeys[i].getKeyVersionNumber();
                    kerbKeys[i] = new KerberosKey(kerbClientPrinc,
                                          encKeys[i].getBytes(),
                                          encKeys[i].getEType(),
                                          (temp == null?
                                          0: temp.intValue()));
                }

            }
            // Let us add the kerbClientPrinc,kerbTicket and KeyTab/KerbKey (if
            // storeKey is true)

            // We won't add "*" as a KerberosPrincipal
            if (!unboundServer &&
                    !princSet.contains(kerbClientPrinc)) {
                princSet.add(kerbClientPrinc);
            }

            // add the TGT
            if (kerbTicket != null) {
                if (!privCredSet.contains(kerbTicket))
                    privCredSet.add(kerbTicket);
            }

            if (storeKey) {
                if (encKeys == null) {
                    if (ktab != null) {
                        if (!privCredSet.contains(ktab)) {
                            privCredSet.add(ktab);
                        }
                    } else {
                        succeeded = false;
                        throw new LoginException("No key to store");
                    }
                } else {
                    for (int i = 0; i < kerbKeys.length; i ++) {
                        if (!privCredSet.contains(kerbKeys[i])) {
                            privCredSet.add(kerbKeys[i]);
                        }
                        encKeys[i].destroy();
                        encKeys[i] = null;
                        if (debug) {
                            System.out.println("Added server's key"
                                            + kerbKeys[i]);
                            System.out.println("\t\t[Krb5LoginModule] " +
                                           "added Krb5Principal  " +
                                           kerbClientPrinc.toString()
                                           + " to Subject");
                        }
                    }
                }
            }
        }
        commitSucceeded = true;
        if (debug)
            System.out.println("Commit Succeeded \n");
        return true;
    }

    /**
     * <p> This method is called if the LoginContext's
     * overall authentication failed.
     * (the relevant REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL
     * LoginModules did not succeed).
     *
     * <p> If this LoginModule's own authentication attempt
     * succeeded (checked by retrieving the private state saved by the
     * <code>login</code> and <code>commit</code> methods),
     * then this method cleans up any state that was originally saved.
     *
     * <p>
     *
     * <p>
     * 将主体(经过身份验证的身份)添加到主体的主体集,并将凭据(TGT或服务密钥)添加到主体的专用凭证
     * 
     * 
     * @exception LoginException if the abort fails.
     *
     * @return false if this LoginModule's own login and/or commit attempts
     *          failed, and true otherwise.
     */

    public boolean abort() throws LoginException {
        if (succeeded == false) {
            return false;
        } else if (succeeded == true && commitSucceeded == false) {
            // login succeeded but overall authentication failed
            succeeded = false;
            cleanKerberosCred();
        } else {
            // overall authentication succeeded and commit succeeded,
            // but someone else's commit failed
            logout();
        }
        return true;
    }

    /**
     * Logout the user.
     *
     * <p> This method removes the <code>Krb5Principal</code>
     * that was added by the <code>commit</code> method.
     *
     * <p>
     *
     * <p>
     *  <p>如果LoginContext的整体身份验证失败(相关的REQUIRED,REQUISITE,SUFEICIENT和OPTIONAL LoginModules没有成功),则调用此方法。
     * 
     *  <p>如果此LoginModule自己的身份验证尝试成功(通过检索由<code> login </code>和<code> commit </code>方法保存的私有状态进行检查),则此方法将清除原来
     * 保存。
     * 
     * <p>
     * 
     * 
     * @exception LoginException if the logout fails.
     *
     * @return true in all cases since this <code>LoginModule</code>
     *          should not be ignored.
     */
    public boolean logout() throws LoginException {

        if (debug) {
            System.out.println("\t\t[Krb5LoginModule]: " +
                "Entering logout");
        }

        if (subject.isReadOnly()) {
            cleanKerberosCred();
            throw new LoginException("Subject is Readonly");
        }

        subject.getPrincipals().remove(kerbClientPrinc);
           // Let us remove all Kerberos credentials stored in the Subject
        Iterator<Object> it = subject.getPrivateCredentials().iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof KerberosTicket ||
                    o instanceof KerberosKey ||
                    o instanceof KeyTab) {
                it.remove();
            }
        }
        // clean the kerberos ticket and keys
        cleanKerberosCred();

        succeeded = false;
        commitSucceeded = false;
        if (debug) {
            System.out.println("\t\t[Krb5LoginModule]: " +
                               "logged out Subject");
        }
        return true;
    }

    /**
     * Clean Kerberos credentials
     * <p>
     *  注销用户
     * 
     *  <p>此方法删除由<code> commit </code>方法添加的<code> Krb5Principal </code>
     * 
     * <p>
     * 
     */
    private void cleanKerberosCred() throws LoginException {
        // Clean the ticket and server key
        try {
            if (kerbTicket != null)
                kerbTicket.destroy();
            if (kerbKeys != null) {
                for (int i = 0; i < kerbKeys.length; i++) {
                    kerbKeys[i].destroy();
                }
            }
        } catch (DestroyFailedException e) {
            throw new LoginException
                ("Destroy Failed on Kerberos Private Credentials");
        }
        kerbTicket = null;
        kerbKeys = null;
        kerbClientPrinc = null;
    }

    /**
     * Clean out the state
     * <p>
     */
    private void cleanState() {

        // save input as shared state only if
        // authentication succeeded
        if (succeeded) {
            if (storePass &&
                !sharedState.containsKey(NAME) &&
                !sharedState.containsKey(PWD)) {
                sharedState.put(NAME, username);
                sharedState.put(PWD, password);
            }
        } else {
            // remove temp results for the next try
            encKeys = null;
            ktab = null;
            principal = null;
        }
        username = null;
        password = null;
        if (krb5PrincName != null && krb5PrincName.length() != 0)
            krb5PrincName.delete(0, krb5PrincName.length());
        krb5PrincName = null;
        if (clearPass) {
            sharedState.remove(NAME);
            sharedState.remove(PWD);
        }
    }
}
