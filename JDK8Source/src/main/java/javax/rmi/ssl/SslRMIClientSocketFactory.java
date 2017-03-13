/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2008, Oracle and/or its affiliates. All rights reserved.
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

package javax.rmi.ssl;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;
import java.util.StringTokenizer;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * <p>An <code>SslRMIClientSocketFactory</code> instance is used by the RMI
 * runtime in order to obtain client sockets for RMI calls via SSL.</p>
 *
 * <p>This class implements <code>RMIClientSocketFactory</code> over
 * the Secure Sockets Layer (SSL) or Transport Layer Security (TLS)
 * protocols.</p>
 *
 * <p>This class creates SSL sockets using the default
 * <code>SSLSocketFactory</code> (see {@link
 * SSLSocketFactory#getDefault}).  All instances of this class are
 * functionally equivalent.  In particular, they all share the same
 * truststore, and the same keystore when client authentication is
 * required by the server.  This behavior can be modified in
 * subclasses by overriding the {@link #createSocket(String,int)}
 * method; in that case, {@link #equals(Object) equals} and {@link
 * #hashCode() hashCode} may also need to be overridden.</p>
 *
 * <p>If the system property
 * <code>javax.rmi.ssl.client.enabledCipherSuites</code> is specified,
 * the {@link #createSocket(String,int)} method will call {@link
 * SSLSocket#setEnabledCipherSuites(String[])} before returning the
 * socket.  The value of this system property is a string that is a
 * comma-separated list of SSL/TLS cipher suites to enable.</p>
 *
 * <p>If the system property
 * <code>javax.rmi.ssl.client.enabledProtocols</code> is specified,
 * the {@link #createSocket(String,int)} method will call {@link
 * SSLSocket#setEnabledProtocols(String[])} before returning the
 * socket.  The value of this system property is a string that is a
 * comma-separated list of SSL/TLS protocol versions to enable.</p>
 *
 * <p>
 *  <p> RMI运行时使用<code> SslRMIClientSocketFactory </code>实例,以通过SSL获取RMI调用的客户端套接字</p>
 * 
 *  <p>此类通过安全套接字层(SSL)或传输层安全(TLS)协议实现<code> RMIClientSocketFactory </code>
 * 
 * <p>此类使用默认的<code> SSLSocketFactory </code>创建SSL套接字(参见{@link SSLSocketFactory#getDefault})此类的所有实例在功能上是等
 * 价的特别地,它们都共享相同的信任库,服务器需要客户端认证时的密钥库此行为可以通过覆盖{@link #createSocket(String,int)}方法在子类中进行修改;在这种情况下,{@link #equals(Object)equals}
 * 和{@link #hashCode()hashCode}也可能需要重写</p>。
 * 
 * <p>如果指定了系统属性<code> javaxrmisslclientenabledCipherSuites </code>,则{@link #createSocket(String,int)}方法将
 * 在返回套接字之前调用{@link SSLSocket#setEnabledCipherSuites(String [])}此系统属性的值是一个字符串,它是启用</p>的SSL / TLS密码套件的逗号分
 * 隔列表。
 * 
 *  <p>如果指定了系统属性<code> javaxrmisslclientenabledProtocols </code>,则{@link #createSocket(String,int)}方法将在返
 * 回套接字之前调用{@link SSLSocket#setEnabledProtocols(String [])}此系统属性的值是一个字符串,它是要启用的SSL / TLS协议版本的逗号分隔列表。
 * </p>。
 * 
 * 
 * @see javax.net.ssl.SSLSocketFactory
 * @see javax.rmi.ssl.SslRMIServerSocketFactory
 * @since 1.5
 */
public class SslRMIClientSocketFactory
    implements RMIClientSocketFactory, Serializable {

    /**
     * <p>Creates a new <code>SslRMIClientSocketFactory</code>.</p>
     * <p>
     *  <p>创建新的<code> SslRMIClientSocketFactory </code> </p>
     * 
     */
    public SslRMIClientSocketFactory() {
        // We don't force the initialization of the default SSLSocketFactory
        // at construction time - because the RMI client socket factory is
        // created on the server side, where that initialization is a priori
        // meaningless, unless both server and client run in the same JVM.
        // We could possibly override readObject() to force this initialization,
        // but it might not be a good idea to actually mix this with possible
        // deserialization problems.
        // So contrarily to what we do for the server side, the initialization
        // of the SSLSocketFactory will be delayed until the first time
        // createSocket() is called - note that the default SSLSocketFactory
        // might already have been initialized anyway if someone in the JVM
        // already called SSLSocketFactory.getDefault().
        //
    }

    /**
     * <p>Creates an SSL socket.</p>
     *
     * <p>If the system property
     * <code>javax.rmi.ssl.client.enabledCipherSuites</code> is
     * specified, this method will call {@link
     * SSLSocket#setEnabledCipherSuites(String[])} before returning
     * the socket. The value of this system property is a string that
     * is a comma-separated list of SSL/TLS cipher suites to
     * enable.</p>
     *
     * <p>If the system property
     * <code>javax.rmi.ssl.client.enabledProtocols</code> is
     * specified, this method will call {@link
     * SSLSocket#setEnabledProtocols(String[])} before returning the
     * socket. The value of this system property is a string that is a
     * comma-separated list of SSL/TLS protocol versions to
     * enable.</p>
     * <p>
     *  <p>创建SSL套接字</p>
     * 
     * <p>如果指定了系统属性<code> javaxrmisslclientenabledCipherSuites </code>,则此方法将在返回套接字之前调用{@link SSLSocket#setEnabledCipherSuites(String [])}
     * 此系统属性的值是一个字符串,逗号分隔的SSL / TLS密码套件列表以启用</p>。
     * 
     *  <p>如果指定了系统属性<code> javaxrmisslclientenabledProtocols </code>,则此方法将在返回套接字之前调用{@link SSLSocket#setEnabledProtocols(String [])}
     * 此系统属性的值是一个字符串,逗号分隔的SSL / TLS协议版本列表以启用</p>。
     * 
     */
    public Socket createSocket(String host, int port) throws IOException {
        // Retrieve the SSLSocketFactory
        //
        final SocketFactory sslSocketFactory = getDefaultClientSocketFactory();
        // Create the SSLSocket
        //
        final SSLSocket sslSocket = (SSLSocket)
            sslSocketFactory.createSocket(host, port);
        // Set the SSLSocket Enabled Cipher Suites
        //
        final String enabledCipherSuites =
            System.getProperty("javax.rmi.ssl.client.enabledCipherSuites");
        if (enabledCipherSuites != null) {
            StringTokenizer st = new StringTokenizer(enabledCipherSuites, ",");
            int tokens = st.countTokens();
            String enabledCipherSuitesList[] = new String[tokens];
            for (int i = 0 ; i < tokens; i++) {
                enabledCipherSuitesList[i] = st.nextToken();
            }
            try {
                sslSocket.setEnabledCipherSuites(enabledCipherSuitesList);
            } catch (IllegalArgumentException e) {
                throw (IOException)
                    new IOException(e.getMessage()).initCause(e);
            }
        }
        // Set the SSLSocket Enabled Protocols
        //
        final String enabledProtocols =
            System.getProperty("javax.rmi.ssl.client.enabledProtocols");
        if (enabledProtocols != null) {
            StringTokenizer st = new StringTokenizer(enabledProtocols, ",");
            int tokens = st.countTokens();
            String enabledProtocolsList[] = new String[tokens];
            for (int i = 0 ; i < tokens; i++) {
                enabledProtocolsList[i] = st.nextToken();
            }
            try {
                sslSocket.setEnabledProtocols(enabledProtocolsList);
            } catch (IllegalArgumentException e) {
                throw (IOException)
                    new IOException(e.getMessage()).initCause(e);
            }
        }
        // Return the preconfigured SSLSocket
        //
        return sslSocket;
    }

    /**
     * <p>Indicates whether some other object is "equal to" this one.</p>
     *
     * <p>Because all instances of this class are functionally equivalent
     * (they all use the default
     * <code>SSLSocketFactory</code>), this method simply returns
     * <code>this.getClass().equals(obj.getClass())</code>.</p>
     *
     * <p>A subclass should override this method (as well
     * as {@link #hashCode()}) if its instances are not all
     * functionally equivalent.</p>
     * <p>
     *  <p>表示某些其他物件是否「等于」这个</p>
     * 
     * <p>因为这个类的所有实例在功能上是等价的(它们都使用默认的<code> SSLSocketFactory </code>),所以这个方法简单地返回<code> thisgetClass()equals
     * (objgetClass())</code> p>。
     * 
     */
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        return this.getClass().equals(obj.getClass());
    }

    /**
     * <p>Returns a hash code value for this
     * <code>SslRMIClientSocketFactory</code>.</p>
     *
     * <p>
     *  <p>如果子类的实例不是全部功能相同,则子类应重写此方法(以及{@link #hashCode()})</p>
     * 
     * 
     * @return a hash code value for this
     * <code>SslRMIClientSocketFactory</code>.
     */
    public int hashCode() {
        return this.getClass().hashCode();
    }

    // We use a static field because:
    //
    //    SSLSocketFactory.getDefault() always returns the same object
    //    (at least on Sun's implementation), and we want to make sure
    //    that the Javadoc & the implementation stay in sync.
    //
    // If someone needs to have different SslRMIClientSocketFactory factories
    // with different underlying SSLSocketFactory objects using different key
    // and trust stores, he can always do so by subclassing this class and
    // overriding createSocket(String host, int port).
    //
    private static SocketFactory defaultSocketFactory = null;

    private static synchronized SocketFactory getDefaultClientSocketFactory() {
        if (defaultSocketFactory == null)
            defaultSocketFactory = SSLSocketFactory.getDefault();
        return defaultSocketFactory;
    }

    private static final long serialVersionUID = -8310631444933958385L;
}
