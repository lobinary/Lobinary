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

/**
 * The class Authenticator represents an object that knows how to obtain
 * authentication for a network connection.  Usually, it will do this
 * by prompting the user for information.
 * <p>
 * Applications use this class by overriding {@link
 * #getPasswordAuthentication()} in a sub-class. This method will
 * typically use the various getXXX() accessor methods to get information
 * about the entity requesting authentication. It must then acquire a
 * username and password either by interacting with the user or through
 * some other non-interactive means. The credentials are then returned
 * as a {@link PasswordAuthentication} return value.
 * <p>
 * An instance of this concrete sub-class is then registered
 * with the system by calling {@link #setDefault(Authenticator)}.
 * When authentication is required, the system will invoke one of the
 * requestPasswordAuthentication() methods which in turn will call the
 * getPasswordAuthentication() method of the registered object.
 * <p>
 * All methods that request authentication have a default implementation
 * that fails.
 *
 * <p>
 *  类Authenticator表示知道如何获得网络连接的认证的对象。通常,它将通过提示用户提供信息来做到这一点。
 * <p>
 *  应用程序通过在子类中重写{@link #getPasswordAuthentication()}来使用此类。此方法通常使用各种getXXX()访问器方法来获取有关请求认证的实体的信息。
 * 然后它必须通过与用户交互或通过一些其他非交互手段获取用户名和密码。然后凭据作为{@link PasswordAuthentication}返回值返回。
 * <p>
 *  然后通过调用{@link #setDefault(Authenticator)}向系统注册此具体子类的实例。
 * 当需要认证时,系统将调用requestPasswordAuthentication()方法中的一个,它们将调用注册对象的getPasswordAuthentication()方法。
 * <p>
 *  所有请求认证的方法都有一个默认实现失败。
 * 
 * 
 * @see java.net.Authenticator#setDefault(java.net.Authenticator)
 * @see java.net.Authenticator#getPasswordAuthentication()
 *
 * @author  Bill Foote
 * @since   1.2
 */

// There are no abstract methods, but to be useful the user must
// subclass.
public abstract
class Authenticator {

    // The system-wide authenticator object.  See setDefault().
    private static Authenticator theAuthenticator;

    private String requestingHost;
    private InetAddress requestingSite;
    private int requestingPort;
    private String requestingProtocol;
    private String requestingPrompt;
    private String requestingScheme;
    private URL requestingURL;
    private RequestorType requestingAuthType;

    /**
     * The type of the entity requesting authentication.
     *
     * <p>
     *  请求认证的实体的类型。
     * 
     * 
     * @since 1.5
     */
    public enum RequestorType {
        /**
         * Entity requesting authentication is a HTTP proxy server.
         * <p>
         *  请求认证的实体是HTTP代理服务器。
         * 
         */
        PROXY,
        /**
         * Entity requesting authentication is a HTTP origin server.
         * <p>
         *  请求认证的实体是HTTP原始服务器。
         * 
         */
        SERVER
    }

    private void reset() {
        requestingHost = null;
        requestingSite = null;
        requestingPort = -1;
        requestingProtocol = null;
        requestingPrompt = null;
        requestingScheme = null;
        requestingURL = null;
        requestingAuthType = RequestorType.SERVER;
    }


    /**
     * Sets the authenticator that will be used by the networking code
     * when a proxy or an HTTP server asks for authentication.
     * <p>
     * First, if there is a security manager, its {@code checkPermission}
     * method is called with a
     * {@code NetPermission("setDefaultAuthenticator")} permission.
     * This may result in a java.lang.SecurityException.
     *
     * <p>
     *  设置当代理或HTTP服务器请求身份验证时将由网络代码使用的身份验证器。
     * <p>
     * 首先,如果有安全管理器,则会使用{@code NetPermission("setDefaultAuthenticator")}权限调用其{@code checkPermission}方法。
     * 这可能会导致java.lang.SecurityException。
     * 
     * 
     * @param   a       The authenticator to be set. If a is {@code null} then
     *                  any previously set authenticator is removed.
     *
     * @throws SecurityException
     *        if a security manager exists and its
     *        {@code checkPermission} method doesn't allow
     *        setting the default authenticator.
     *
     * @see SecurityManager#checkPermission
     * @see java.net.NetPermission
     */
    public synchronized static void setDefault(Authenticator a) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            NetPermission setDefaultPermission
                = new NetPermission("setDefaultAuthenticator");
            sm.checkPermission(setDefaultPermission);
        }

        theAuthenticator = a;
    }

    /**
     * Ask the authenticator that has been registered with the system
     * for a password.
     * <p>
     * First, if there is a security manager, its {@code checkPermission}
     * method is called with a
     * {@code NetPermission("requestPasswordAuthentication")} permission.
     * This may result in a java.lang.SecurityException.
     *
     * <p>
     *  请向系统注册的验证器询问密码。
     * <p>
     *  首先,如果有安全管理器,则会使用{@code NetPermission("requestPasswordAuthentication")}权限调用其{@code checkPermission}方法
     * 。
     * 这可能会导致java.lang.SecurityException。
     * 
     * 
     * @param addr The InetAddress of the site requesting authorization,
     *             or null if not known.
     * @param port the port for the requested connection
     * @param protocol The protocol that's requesting the connection
     *          ({@link java.net.Authenticator#getRequestingProtocol()})
     * @param prompt A prompt string for the user
     * @param scheme The authentication scheme
     *
     * @return The username/password, or null if one can't be gotten.
     *
     * @throws SecurityException
     *        if a security manager exists and its
     *        {@code checkPermission} method doesn't allow
     *        the password authentication request.
     *
     * @see SecurityManager#checkPermission
     * @see java.net.NetPermission
     */
    public static PasswordAuthentication requestPasswordAuthentication(
                                            InetAddress addr,
                                            int port,
                                            String protocol,
                                            String prompt,
                                            String scheme) {

        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            NetPermission requestPermission
                = new NetPermission("requestPasswordAuthentication");
            sm.checkPermission(requestPermission);
        }

        Authenticator a = theAuthenticator;
        if (a == null) {
            return null;
        } else {
            synchronized(a) {
                a.reset();
                a.requestingSite = addr;
                a.requestingPort = port;
                a.requestingProtocol = protocol;
                a.requestingPrompt = prompt;
                a.requestingScheme = scheme;
                return a.getPasswordAuthentication();
            }
        }
    }

    /**
     * Ask the authenticator that has been registered with the system
     * for a password. This is the preferred method for requesting a password
     * because the hostname can be provided in cases where the InetAddress
     * is not available.
     * <p>
     * First, if there is a security manager, its {@code checkPermission}
     * method is called with a
     * {@code NetPermission("requestPasswordAuthentication")} permission.
     * This may result in a java.lang.SecurityException.
     *
     * <p>
     *  请向系统注册的验证器询问密码。这是请求密码的首选方法,因为在InetAddress不可用的情况下可以提供主机名。
     * <p>
     *  首先,如果有安全管理器,则会使用{@code NetPermission("requestPasswordAuthentication")}权限调用其{@code checkPermission}方法
     * 。
     * 这可能会导致java.lang.SecurityException。
     * 
     * 
     * @param host The hostname of the site requesting authentication.
     * @param addr The InetAddress of the site requesting authentication,
     *             or null if not known.
     * @param port the port for the requested connection.
     * @param protocol The protocol that's requesting the connection
     *          ({@link java.net.Authenticator#getRequestingProtocol()})
     * @param prompt A prompt string for the user which identifies the authentication realm.
     * @param scheme The authentication scheme
     *
     * @return The username/password, or null if one can't be gotten.
     *
     * @throws SecurityException
     *        if a security manager exists and its
     *        {@code checkPermission} method doesn't allow
     *        the password authentication request.
     *
     * @see SecurityManager#checkPermission
     * @see java.net.NetPermission
     * @since 1.4
     */
    public static PasswordAuthentication requestPasswordAuthentication(
                                            String host,
                                            InetAddress addr,
                                            int port,
                                            String protocol,
                                            String prompt,
                                            String scheme) {

        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            NetPermission requestPermission
                = new NetPermission("requestPasswordAuthentication");
            sm.checkPermission(requestPermission);
        }

        Authenticator a = theAuthenticator;
        if (a == null) {
            return null;
        } else {
            synchronized(a) {
                a.reset();
                a.requestingHost = host;
                a.requestingSite = addr;
                a.requestingPort = port;
                a.requestingProtocol = protocol;
                a.requestingPrompt = prompt;
                a.requestingScheme = scheme;
                return a.getPasswordAuthentication();
            }
        }
    }

    /**
     * Ask the authenticator that has been registered with the system
     * for a password.
     * <p>
     * First, if there is a security manager, its {@code checkPermission}
     * method is called with a
     * {@code NetPermission("requestPasswordAuthentication")} permission.
     * This may result in a java.lang.SecurityException.
     *
     * <p>
     *  请向系统注册的验证器询问密码。
     * <p>
     *  首先,如果有安全管理器,则会使用{@code NetPermission("requestPasswordAuthentication")}权限调用其{@code checkPermission}方法
     * 。
     * 这可能会导致java.lang.SecurityException。
     * 
     * 
     * @param host The hostname of the site requesting authentication.
     * @param addr The InetAddress of the site requesting authorization,
     *             or null if not known.
     * @param port the port for the requested connection
     * @param protocol The protocol that's requesting the connection
     *          ({@link java.net.Authenticator#getRequestingProtocol()})
     * @param prompt A prompt string for the user
     * @param scheme The authentication scheme
     * @param url The requesting URL that caused the authentication
     * @param reqType The type (server or proxy) of the entity requesting
     *              authentication.
     *
     * @return The username/password, or null if one can't be gotten.
     *
     * @throws SecurityException
     *        if a security manager exists and its
     *        {@code checkPermission} method doesn't allow
     *        the password authentication request.
     *
     * @see SecurityManager#checkPermission
     * @see java.net.NetPermission
     *
     * @since 1.5
     */
    public static PasswordAuthentication requestPasswordAuthentication(
                                    String host,
                                    InetAddress addr,
                                    int port,
                                    String protocol,
                                    String prompt,
                                    String scheme,
                                    URL url,
                                    RequestorType reqType) {

        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            NetPermission requestPermission
                = new NetPermission("requestPasswordAuthentication");
            sm.checkPermission(requestPermission);
        }

        Authenticator a = theAuthenticator;
        if (a == null) {
            return null;
        } else {
            synchronized(a) {
                a.reset();
                a.requestingHost = host;
                a.requestingSite = addr;
                a.requestingPort = port;
                a.requestingProtocol = protocol;
                a.requestingPrompt = prompt;
                a.requestingScheme = scheme;
                a.requestingURL = url;
                a.requestingAuthType = reqType;
                return a.getPasswordAuthentication();
            }
        }
    }

    /**
     * Gets the {@code hostname} of the
     * site or proxy requesting authentication, or {@code null}
     * if not available.
     *
     * <p>
     *  获取请求身份验证的网站或代理的{@code hostname},如果不可用,则为{@code null}。
     * 
     * 
     * @return the hostname of the connection requiring authentication, or null
     *          if it's not available.
     * @since 1.4
     */
    protected final String getRequestingHost() {
        return requestingHost;
    }

    /**
     * Gets the {@code InetAddress} of the
     * site requesting authorization, or {@code null}
     * if not available.
     *
     * <p>
     *  获取请求授权的网站的{@code InetAddress},如果不可用,则返回{@code null}。
     * 
     * 
     * @return the InetAddress of the site requesting authorization, or null
     *          if it's not available.
     */
    protected final InetAddress getRequestingSite() {
        return requestingSite;
    }

    /**
     * Gets the port number for the requested connection.
     * <p>
     *  获取所请求连接的端口号。
     * 
     * 
     * @return an {@code int} indicating the
     * port for the requested connection.
     */
    protected final int getRequestingPort() {
        return requestingPort;
    }

    /**
     * Give the protocol that's requesting the connection.  Often this
     * will be based on a URL, but in a future JDK it could be, for
     * example, "SOCKS" for a password-protected SOCKS5 firewall.
     *
     * <p>
     * 提供请求连接的协议。通常这将基于URL,但在未来的JDK中,它可以是例如对于受密码保护的SOCKS5防火墙的"SOCKS"。
     * 
     * 
     * @return the protocol, optionally followed by "/version", where
     *          version is a version number.
     *
     * @see java.net.URL#getProtocol()
     */
    protected final String getRequestingProtocol() {
        return requestingProtocol;
    }

    /**
     * Gets the prompt string given by the requestor.
     *
     * <p>
     *  获取请求者提供的提示字符串。
     * 
     * 
     * @return the prompt string given by the requestor (realm for
     *          http requests)
     */
    protected final String getRequestingPrompt() {
        return requestingPrompt;
    }

    /**
     * Gets the scheme of the requestor (the HTTP scheme
     * for an HTTP firewall, for example).
     *
     * <p>
     *  获取请求者的方案(例如,HTTP防火墙的HTTP方案)。
     * 
     * 
     * @return the scheme of the requestor
     *
     */
    protected final String getRequestingScheme() {
        return requestingScheme;
    }

    /**
     * Called when password authorization is needed.  Subclasses should
     * override the default implementation, which returns null.
     * <p>
     *  需要密码授权时调用。子类应该覆盖默认实现,它返回null。
     * 
     * 
     * @return The PasswordAuthentication collected from the
     *          user, or null if none is provided.
     */
    protected PasswordAuthentication getPasswordAuthentication() {
        return null;
    }

    /**
     * Returns the URL that resulted in this
     * request for authentication.
     *
     * <p>
     *  返回导致此请求进行身份验证的网址。
     * 
     * 
     * @since 1.5
     *
     * @return the requesting URL
     *
     */
    protected URL getRequestingURL () {
        return requestingURL;
    }

    /**
     * Returns whether the requestor is a Proxy or a Server.
     *
     * <p>
     *  返回请求者是代理还是服务器。
     * 
     * @since 1.5
     *
     * @return the authentication type of the requestor
     *
     */
    protected RequestorType getRequestorType () {
        return requestingAuthType;
    }
}
