/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.io.IOException;
import java.util.List;
import sun.security.util.SecurityConstants;

/**
 * Selects the proxy server to use, if any, when connecting to the
 * network resource referenced by a URL. A proxy selector is a
 * concrete sub-class of this class and is registered by invoking the
 * {@link java.net.ProxySelector#setDefault setDefault} method. The
 * currently registered proxy selector can be retrieved by calling
 * {@link java.net.ProxySelector#getDefault getDefault} method.
 *
 * <p> When a proxy selector is registered, for instance, a subclass
 * of URLConnection class should call the {@link #select select}
 * method for each URL request so that the proxy selector can decide
 * if a direct, or proxied connection should be used. The {@link
 * #select select} method returns an iterator over a collection with
 * the preferred connection approach.
 *
 * <p> If a connection cannot be established to a proxy (PROXY or
 * SOCKS) servers then the caller should call the proxy selector's
 * {@link #connectFailed connectFailed} method to notify the proxy
 * selector that the proxy server is unavailable. </p>
 *
 * <P>The default proxy selector does enforce a
 * <a href="doc-files/net-properties.html#Proxies">set of System Properties</a>
 * related to proxy settings.</P>
 *
 * <p>
 *  选择要连接到由URL引用的网络资源时要使用的代理服务器(如果有)。
 * 代理选择器是此类的一个具体子类,并通过调用{@link java.net.ProxySelector#setDefault setDefault}方法进行注册。
 * 可以通过调用{@link java.net.ProxySelector#getDefault getDefault}方法检索当前注册的代理选择器。
 * 
 *  <p>例如,当注册代理选择器时,URLConnection类的子类应为每个URL请求调用{@link #select select}方法,以便代理选择器可以决定是使用直接连接还是使用代理连接。
 *  {@link #select select}方法通过首选连接方法对集合返回迭代器。
 * 
 *  <p>如果无法与代理服务器(PROXY或SOCKS)建立连接,则调用者应调用代理选择器的{@link #connectFailed connectFailed}方法,以通知代理选择器代理服务器不可用。
 *  </p>。
 * 
 *  <P>默认代理选择器会强制执行与代理设置相关的<a href="doc-files/net-properties.html#Proxies">一组系统属性</a>。</P>
 * 
 * 
 * @author Yingxian Wang
 * @author Jean-Christophe Collet
 * @since 1.5
 */
public abstract class ProxySelector {
    /**
     * The system wide proxy selector that selects the proxy server to
     * use, if any, when connecting to a remote object referenced by
     * an URL.
     *
     * <p>
     *  在连接到由URL引用的远程对象时选择要使用的代理服务器(如果有)的系统范围代理选择器。
     * 
     * 
     * @see #setDefault(ProxySelector)
     */
    private static ProxySelector theProxySelector;

    static {
        try {
            Class<?> c = Class.forName("sun.net.spi.DefaultProxySelector");
            if (c != null && ProxySelector.class.isAssignableFrom(c)) {
                theProxySelector = (ProxySelector) c.newInstance();
            }
        } catch (Exception e) {
            theProxySelector = null;
        }
    }

    /**
     * Gets the system-wide proxy selector.
     *
     * <p>
     *  获取系统范围的代理选择器。
     * 
     * 
     * @throws  SecurityException
     *          If a security manager has been installed and it denies
     * {@link NetPermission}{@code ("getProxySelector")}
     * @see #setDefault(ProxySelector)
     * @return the system-wide {@code ProxySelector}
     * @since 1.5
     */
    public static ProxySelector getDefault() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(SecurityConstants.GET_PROXYSELECTOR_PERMISSION);
        }
        return theProxySelector;
    }

    /**
     * Sets (or unsets) the system-wide proxy selector.
     *
     * Note: non-standard protocol handlers may ignore this setting.
     *
     * <p>
     * 设置(或取消)系统范围的代理选择器。
     * 
     *  注意：非标准协议处理程序可能会忽略此设置。
     * 
     * 
     * @param ps The HTTP proxy selector, or
     *          {@code null} to unset the proxy selector.
     *
     * @throws  SecurityException
     *          If a security manager has been installed and it denies
     * {@link NetPermission}{@code ("setProxySelector")}
     *
     * @see #getDefault()
     * @since 1.5
     */
    public static void setDefault(ProxySelector ps) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(SecurityConstants.SET_PROXYSELECTOR_PERMISSION);
        }
        theProxySelector = ps;
    }

    /**
     * Selects all the applicable proxies based on the protocol to
     * access the resource with and a destination address to access
     * the resource at.
     * The format of the URI is defined as follow:
     * <UL>
     * <LI>http URI for http connections</LI>
     * <LI>https URI for https connections
     * <LI>{@code socket://host:port}<br>
     *     for tcp client sockets connections</LI>
     * </UL>
     *
     * <p>
     *  根据访问资源的协议选择所有适用的代理,并使用目标地址访问资源。 URI的格式定义如下：
     * <UL>
     *  <li> http连接的URI </li> <li> https连接的https URI <li> {@ code socket：// host：port} <br>用于tcp客户端套接字连接</li>
     * 。
     * </UL>
     * 
     * @param   uri
     *          The URI that a connection is required to
     *
     * @return  a List of Proxies. Each element in the
     *          the List is of type
     *          {@link java.net.Proxy Proxy};
     *          when no proxy is available, the list will
     *          contain one element of type
     *          {@link java.net.Proxy Proxy}
     *          that represents a direct connection.
     * @throws IllegalArgumentException if the argument is null
     */
    public abstract List<Proxy> select(URI uri);

    /**
     * Called to indicate that a connection could not be established
     * to a proxy/socks server. An implementation of this method can
     * temporarily remove the proxies or reorder the sequence of
     * proxies returned by {@link #select(URI)}, using the address
     * and the IOException caught when trying to connect.
     *
     * <p>
     * 
     * 
     * @param   uri
     *          The URI that the proxy at sa failed to serve.
     * @param   sa
     *          The socket address of the proxy/SOCKS server
     *
     * @param   ioe
     *          The I/O exception thrown when the connect failed.
     * @throws IllegalArgumentException if either argument is null
     */
    public abstract void connectFailed(URI uri, SocketAddress sa, IOException ioe);
}
