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

/**
 * Contains class and interfaces for supporting SASL.
 *
 * This package defines classes and interfaces for SASL mechanisms.
 * It is used by developers to add authentication support for
 * connection-based protocols that use SASL.
 *
 * <h3>SASL Overview</h3>
 *
 * Simple Authentication and Security Layer (SASL) specifies a
 * challenge-response protocol in which data is exchanged between the
 * client and the server for the purposes of
 * authentication and (optional) establishment of a security layer on
 * which to carry on subsequent communications.  It is used with
 * connection-based protocols such as LDAPv3 or IMAPv4.  SASL is
 * described in
 * <A HREF="http://www.ietf.org/rfc/rfc2222.txt">RFC 2222</A>.
 *
 *
 * There are various <em>mechanisms</em> defined for SASL.
 * Each mechanism defines the data that must be exchanged between the
 * client and server in order for the authentication to succeed.
 * This data exchange required for a particular mechanism is referred to
 * to as its <em>protocol profile</em>.
 * The following are some examples of mechanisms that have been defined by
 * the Internet standards community.
 * <ul>
 * <li>DIGEST-MD5 (<A HREF="http://www.ietf.org/rfc/rfc2831.txt">RFC 2831</a>).
 * This mechanism defines how HTTP Digest Authentication can be used as a SASL
 * mechanism.
 * <li>Anonymous (<A HREF="http://www.ietf.org/rfc/rfc2245.txt">RFC 2245</a>).
 * This mechanism is anonymous authentication in which no credentials are
 * necessary.
 * <li>External (<A HREF="http://www.ietf.org/rfc/rfc2222.txt">RFC 2222</A>).
 * This mechanism obtains authentication information
 * from an external source (such as TLS or IPsec).
 * <li>S/Key (<A HREF="http://www.ietf.org/rfc/rfc2222.txt">RFC 2222</A>).
 * This mechanism uses the MD4 digest algorithm to exchange data based on
 * a shared secret.
 * <li>GSSAPI (<A HREF="http://www.ietf.org/rfc/rfc2222.txt">RFC 2222</A>).
 * This mechanism uses the
 * <A HREF="http://www.ietf.org/rfc/rfc2078.txt">GSSAPI</A>
 * for obtaining authentication information.
 * </ul>
 *
 * Some of these mechanisms provide both authentication and establishment
 * of a security layer, others only authentication.  Anonymous and
 * S/Key do not provide for any security layers.  GSSAPI and DIGEST-MD5
 * allow negotiation of the security layer.  For External, the
 * security layer is determined by the external protocol.
 *
 * <h3>Usage</h3>
 *
 * Users of this API are typically developers who produce
 * client library implementations for connection-based protocols,
 * such as LDAPv3 and IMAPv4,
 * and developers who write servers (such as LDAP servers and IMAP servers).
 * Developers who write client libraries use the
 * {@code SaslClient} and {@code SaslClientFactory} interfaces.
 * Developers who write servers use the
 * {@code SaslServer} and {@code SaslServerFactory} interfaces.
 *
 * Among these two groups of users, each can be further divided into two groups:
 * those who <em>produce</em> the SASL mechanisms and those
 * who <em>use</em> the SASL mechanisms.
 * The producers of SASL mechanisms need to provide implementations
 * for these interfaces, while users of the SASL mechanisms use
 * the APIs in this package to access those implementations.
 *
 * <h2>Related Documentation</h2>
 *
 * Please refer to the
 * <a href="../../../../technotes/guides/security/sasl/sasl-refguide.html">Java
 * SASL Programming Guide</a> for information on how to use this API.
 *
 * <p>
 *  包含用于支持SASL的类和接口。
 * 
 *  这个包定义了SASL机制的类和接口。开发人员使用它来为使用SASL的基于连接的协议添加认证支持。
 * 
 *  <h3> SASL概述</h3>
 * 
 *  简单认证和安全层(SASL)规定了挑战 - 响应协议,其中在客户端和服务器之间交换数据,以用于认证和(可选)建立在其上进行后续通信的安全层。
 * 它与基于连接的协议(如LDAPv3或IMAPv4)一起使用。
 *  SASL在<A HREF="http://www.ietf.org/rfc/rfc2222.txt"> RFC 2222 </A>中进行了说明。
 * 
 *  为SASL定义了各种<em>机制</em>。每个机制定义必须在客户端和服务器之间交换的数据,以便认证成功。特定机制所需的这种数据交换被称为其协议简档</em>。
 * 以下是互联网标准界定义的机制的一些示例。
 * <ul>
 * <li> DIGEST-MD5(<A HREF="http://www.ietf.org/rfc/rfc2831.txt"> RFC 2831 </a>)。
 * 此机制定义了如何将HTTP摘要身份验证用作SASL机制。 <li>匿名(<A HREF="http://www.ietf.org/rfc/rfc2245.txt"> RFC 2245 </a>)。
 * 这种机制是匿名认证,其中不需要凭证。 <li>外部(<A HREF="http://www.ietf.org/rfc/rfc2222.txt"> RFC 2222 </A>)。
 * 此机制从外部源(例如TLS或IPsec)获取认证信息。 <li> S /键(<A HREF="http://www.ietf.org/rfc/rfc2222.txt"> RFC 2222 </A>)。
 * 此机制使用MD4摘要算法基于共享密钥交换数据。 <li> GSSAPI(<A HREF="http://www.ietf.org/rfc/rfc2222.txt"> RFC 2222 </A>)。
 * 此机制使用<A HREF="http://www.ietf.org/rfc/rfc2078.txt"> GSSAPI </A>来获取身份验证信息。
 * 
 * @since 1.5
 */
package javax.security.sasl;
