/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * This package contains utility classes related to the Kerberos network
 * authentication protocol. They do not provide much Kerberos support
 * themselves.<p>
 *
 * The Kerberos network authentication protocol is defined in
 * <a href=http://www.ietf.org/rfc/rfc4120.txt>RFC 4120</a>. The Java
 * platform contains support for the client side of Kerberos via the
 * {@link org.ietf.jgss} package. There might also be
 * a login module that implements
 * {@link javax.security.auth.spi.LoginModule LoginModule} to authenticate
 * Kerberos principals.<p>
 *
 * You can provide the name of your default realm and Key Distribution
 * Center (KDC) host for that realm using the system properties
 * {@code java.security.krb5.realm} and {@code java.security.krb5.kdc}.
 * Both properties must be set.
 * Alternatively, the {@code java.security.krb5.conf} system property can
 * be set to the location of an MIT style {@code krb5.conf} configuration
 * file. If none of these system properties are set, the {@code krb5.conf}
 * file is searched for in an implementation-specific manner. Typically,
 * an implementation will first look for a {@code krb5.conf} file in
 * {@code <java-home>/lib/security} and failing that, in an OS-specific
 * location.<p>
 *
 * <p>
 *  此程序包包含与Kerberos网络身份验证协议相关的实用程序类。他们自己不提供很多Kerberos支持。<p>
 * 
 *  Kerberos网络身份验证协议在<a href=http://www.ietf.org/rfc/rfc4120.txt> RFC 4120 </a>中定义。
 *  Java平台通过{@link org.ietf.jgss}包支持Kerberos的客户端。
 * 也可能有一个登录模块实现{@link javax.security.auth.spi.LoginModule LoginModule}来验证Kerberos主体。<p>。
 * 
 * 
 * @since JDK1.4
 */
package javax.security.auth.kerberos;
