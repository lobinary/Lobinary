/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2001, Oracle and/or its affiliates. All rights reserved.
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

package javax.naming.ldap;

import java.io.IOException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.HostnameVerifier;

/**
 * This class implements the LDAPv3 Extended Response for StartTLS as
 * defined in
 * <a href="http://www.ietf.org/rfc/rfc2830.txt">Lightweight Directory
 * Access Protocol (v3): Extension for Transport Layer Security</a>
 *
 * The object identifier for StartTLS is 1.3.6.1.4.1.1466.20037
 * and no extended response value is defined.
 *
 *<p>
 * The Start TLS extended request and response are used to establish
 * a TLS connection over the existing LDAP connection associated with
 * the JNDI context on which <tt>extendedOperation()</tt> is invoked.
 * Typically, a JNDI program uses the StartTLS extended request and response
 * classes as follows.
 * <blockquote><pre>
 * import javax.naming.ldap.*;
 *
 * // Open an LDAP association
 * LdapContext ctx = new InitialLdapContext();
 *
 * // Perform a StartTLS extended operation
 * StartTlsResponse tls =
 *     (StartTlsResponse) ctx.extendedOperation(new StartTlsRequest());
 *
 * // Open a TLS connection (over the existing LDAP association) and get details
 * // of the negotiated TLS session: cipher suite, peer certificate, ...
 * SSLSession session = tls.negotiate();
 *
 * // ... use ctx to perform protected LDAP operations
 *
 * // Close the TLS connection (revert back to the underlying LDAP association)
 * tls.close();
 *
 * // ... use ctx to perform unprotected LDAP operations
 *
 * // Close the LDAP association
 * ctx.close;
 * </pre></blockquote>
 *
 * <p>
 *  此类实现了<a href="http://www.ietf.org/rfc/rfc2830.txt">轻量级目录访问协议(v3)中定义的StartTLS的LDAPv3扩展响应：传输层安全性扩展</a >
 * 。
 * 
 *  StartTLS的对象标识符是1.3.6.1.4.1.1466.20037,没有定义扩展响应值。
 * 
 * p>
 *  启动TLS扩展请求和响应用于通过与调用<tt> extendedOperation()</tt>的JNDI上下文相关联的现有LDAP连接建立TLS连接。
 * 通常,JNDI程序如下使用StartTLS扩展的请求和响应类。 <blockquote> <pre> import javax.naming.ldap。*;。
 * 
 *  //打开LDAP关联LdapContext ctx = new InitialLdapContext();
 * 
 *  //执行StartTLS扩展操作StartTlsResponse tls =(StartTlsResponse)ctx.extendedOperation(new StartTlsRequest())
 * ;。
 * 
 *  //打开TLS连接(通过现有的LDAP关联)并获取协商的TLS会话的详细信息//加密套件,对等证书,... SSLSession session = tls.negotiate();
 * 
 *  // ...使用ctx执行受保护的LDAP操作
 * 
 *  //关闭TLS连接(恢复到底层LDAP关联)tls.close();
 * 
 *  // ...使用ctx执行不受保护的LDAP操作
 * 
 *  //关闭LDAP关联ctx.close; </pre> </blockquote>
 * 
 * 
 * @since 1.4
 * @see StartTlsRequest
 * @author Vincent Ryan
 */
public abstract class StartTlsResponse implements ExtendedResponse {

    // Constant

    /**
     * The StartTLS extended response's assigned object identifier
     * is 1.3.6.1.4.1.1466.20037.
     * <p>
     * StartTLS扩展响应的分配的对象标识符是1.3.6.1.4.1.1466.20037。
     * 
     */
    public static final String OID = "1.3.6.1.4.1.1466.20037";


    // Called by subclass

    /**
     * Constructs a StartTLS extended response.
     * A concrete subclass must have a public no-arg constructor.
     * <p>
     *  构造StartTLS扩展响应。一个具体的子类必须有一个公共的非参数构造函数。
     * 
     */
    protected StartTlsResponse() {
    }


    // ExtendedResponse methods

    /**
     * Retrieves the StartTLS response's object identifier string.
     *
     * <p>
     *  检索StartTLS响应的对象标识符字符串。
     * 
     * 
     * @return The object identifier string, "1.3.6.1.4.1.1466.20037".
     */
    public String getID() {
        return OID;
    }

    /**
     * Retrieves the StartTLS response's ASN.1 BER encoded value.
     * Since the response has no defined value, null is always
     * returned.
     *
     * <p>
     *  检索StartTLS响应的ASN.1 BER编码值。由于响应没有定义的值,因此总是返回null。
     * 
     * 
     * @return The null value.
     */
    public byte[] getEncodedValue() {
        return null;
    }

    // StartTls-specific methods

    /**
     * Overrides the default list of cipher suites enabled for use on the
     * TLS connection. The cipher suites must have already been listed by
     * <tt>SSLSocketFactory.getSupportedCipherSuites()</tt> as being supported.
     * Even if a suite has been enabled, it still might not be used because
     * the peer does not support it, or because the requisite certificates
     * (and private keys) are not available.
     *
     * <p>
     *  覆盖启用以在TLS连接上使用的密码套件的默认列表。密码套件必须已由<tt> SSLSocketFactory.getSupportedCipherSuites()</tt>列为受支持。
     * 即使启用了套件,它仍然可能不会使用,因为对等端不支持套件,或者因为必需的证书(和私钥)不可用。
     * 
     * 
     * @param suites The non-null list of names of all the cipher suites to
     * enable.
     * @see #negotiate
     */
    public abstract void setEnabledCipherSuites(String[] suites);

    /**
     * Sets the hostname verifier used by <tt>negotiate()</tt>
     * after the TLS handshake has completed and the default hostname
     * verification has failed.
     * <tt>setHostnameVerifier()</tt> must be called before
     * <tt>negotiate()</tt> is invoked for it to have effect.
     * If called after
     * <tt>negotiate()</tt>, this method does not do anything.
     *
     * <p>
     *  设置在TLS握手完成并且默认主机名验证失败后由<tt> negotiate()</tt>使用的主机名验证器。
     * 必须在调用<tt> negotiate()</tt>之前调用<tt> setHostnameVerifier()</tt>才能生效。
     * 如果在<tt> negotiate()</tt>之后调用,此方法不执行任何操作。
     * 
     * 
     * @param verifier The non-null hostname verifier callback.
     * @see #negotiate
     */
    public abstract void setHostnameVerifier(HostnameVerifier verifier);

    /**
     * Negotiates a TLS session using the default SSL socket factory.
     * <p>
     * This method is equivalent to <tt>negotiate(null)</tt>.
     *
     * <p>
     *  使用默认SSL套接字工厂协商TLS会话。
     * <p>
     *  此方法等效于<tt> negotiate(null)</tt>。
     * 
     * 
     * @return The negotiated SSL session
     * @throws IOException If an IO error was encountered while establishing
     * the TLS session.
     * @see #setEnabledCipherSuites
     * @see #setHostnameVerifier
     */
    public abstract SSLSession negotiate() throws IOException;

    /**
     * Negotiates a TLS session using an SSL socket factory.
     * <p>
     * Creates an SSL socket using the supplied SSL socket factory and
     * attaches it to the existing connection. Performs the TLS handshake
     * and returns the negotiated session information.
     * <p>
     * If cipher suites have been set via <tt>setEnabledCipherSuites</tt>
     * then they are enabled before the TLS handshake begins.
     * <p>
     * Hostname verification is performed after the TLS handshake completes.
     * The default hostname verification performs a match of the server's
     * hostname against the hostname information found in the server's certificate.
     * If this verification fails and no callback has been set via
     * <tt>setHostnameVerifier</tt> then the negotiation fails.
     * If this verification fails and a callback has been set via
     * <tt>setHostnameVerifier</tt>, then the callback is used to determine whether
     * the negotiation succeeds.
     * <p>
     * If an error occurs then the SSL socket is closed and an IOException
     * is thrown. The underlying connection remains intact.
     *
     * <p>
     *  使用SSL套接字工厂协商TLS会话。
     * <p>
     *  使用提供的SSL套接字工厂创建SSL套接字,并将其附加到现有连接。执行TLS握手,并返回协商的会话信息。
     * <p>
     * 如果通过<tt> setEnabledCipherSuites </tt>设置了密码套件,则在TLS握手开始之前启用它们。
     * <p>
     *  在TLS握手完成后执行主机名验证。默认主机名验证执行服务器的主机名与在服务器证书中找到的主机名信息的匹配。
     * 如果此验证失败,并且没有通过<tt> setHostnameVerifier </tt>设置回调,则协商失败。
     * 
     * @param factory The possibly null SSL socket factory to use.
     * If null, the default SSL socket factory is used.
     * @return The negotiated SSL session
     * @throws IOException If an IO error was encountered while establishing
     * the TLS session.
     * @see #setEnabledCipherSuites
     * @see #setHostnameVerifier
     */
    public abstract SSLSession negotiate(SSLSocketFactory factory)
        throws IOException;

    /**
     * Closes the TLS connection gracefully and reverts back to the underlying
     * connection.
     *
     * <p>
     * 如果此验证失败,并通过<tt> setHostnameVerifier </tt>设置回调,则回调用于确定协商是否成功。
     * <p>
     *  如果发生错误,则SSL套接字关闭,并抛出IOException。底层连接保持不变。
     * 
     * 
     * @throws IOException If an IO error was encountered while closing the
     * TLS connection
     */
    public abstract void close() throws IOException;

    private static final long serialVersionUID = 8372842182579276418L;
}
