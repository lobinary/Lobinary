/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2004, Oracle and/or its affiliates. All rights reserved.
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

import java.security.cert.Certificate;
import javax.net.ssl.SSLPeerUnverifiedException;
import java.security.Principal;
import java.util.List;

/**
 * Represents a cache response originally retrieved through secure
 * means, such as TLS.
 *
 * <p>
 *  表示最初通过安全方式(如TLS)检索的缓存响应。
 * 
 * 
 * @since 1.5
 */
public abstract class SecureCacheResponse extends CacheResponse {
    /**
     * Returns the cipher suite in use on the original connection that
     * retrieved the network resource.
     *
     * <p>
     *  返回检索网络资源的原始连接上使用的加密套件。
     * 
     * 
     * @return a string representing the cipher suite
     */
    public abstract String getCipherSuite();

    /**
     * Returns the certificate chain that were sent to the server during
     * handshaking of the original connection that retrieved the
     * network resource.  Note: This method is useful only
     * when using certificate-based cipher suites.
     *
     * <p>
     *  返回在检索网络资源的原始连接的握手期间发送到服务器的证书链。注意：此方法仅在使用基于证书的密码套件时有用。
     * 
     * 
     * @return an immutable List of Certificate representing the
     *           certificate chain that was sent to the server. If no
     *           certificate chain was sent, null will be returned.
     * @see #getLocalPrincipal()
     */
    public abstract List<Certificate> getLocalCertificateChain();

    /**
     * Returns the server's certificate chain, which was established as
     * part of defining the session in the original connection that
     * retrieved the network resource, from cache.  Note: This method
     * can be used only when using certificate-based cipher suites;
     * using it with non-certificate-based cipher suites, such as
     * Kerberos, will throw an SSLPeerUnverifiedException.
     *
     * <p>
     *  返回服务器的证书链,该证书链是在从缓存中检索网络资源的原始连接中定义会话的一部分而建立的。
     * 注意：此方法只能在使用基于证书的密码套件时使用;使用它与非基于证书的密码套件(如Kerberos)将抛出SSLPeerUnverifiedException。
     * 
     * 
     * @return an immutable List of Certificate representing the server's
     *         certificate chain.
     * @throws SSLPeerUnverifiedException if the peer is not verified.
     * @see #getPeerPrincipal()
     */
    public abstract List<Certificate> getServerCertificateChain()
        throws SSLPeerUnverifiedException;

    /**
     * Returns the server's principal which was established as part of
     * defining the session during the original connection that
     * retrieved the network resource.
     *
     * <p>
     *  返回在检索网络资源的原始连接期间作为定义会话的一部分而建立的服务器的主体。
     * 
     * 
     * @return the server's principal. Returns an X500Principal of the
     * end-entity certiticate for X509-based cipher suites, and
     * KerberosPrincipal for Kerberos cipher suites.
     *
     * @throws SSLPeerUnverifiedException if the peer was not verified.
     *
     * @see #getServerCertificateChain()
     * @see #getLocalPrincipal()
     */
     public abstract Principal getPeerPrincipal()
             throws SSLPeerUnverifiedException;

    /**
      * Returns the principal that was sent to the server during
      * handshaking in the original connection that retrieved the
      * network resource.
      *
      * <p>
      *  返回在检索网络资源的原始连接中握手期间发送到服务器的主体。
      * 
      * @return the principal sent to the server. Returns an X500Principal
      * of the end-entity certificate for X509-based cipher suites, and
      * KerberosPrincipal for Kerberos cipher suites. If no principal was
      * sent, then null is returned.
      *
      * @see #getLocalCertificateChain()
      * @see #getPeerPrincipal()
      */
     public abstract Principal getLocalPrincipal();
}
