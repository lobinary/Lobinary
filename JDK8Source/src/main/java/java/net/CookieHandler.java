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

import java.util.Map;
import java.util.List;
import java.io.IOException;
import sun.security.util.SecurityConstants;

/**
 * A CookieHandler object provides a callback mechanism to hook up a
 * HTTP state management policy implementation into the HTTP protocol
 * handler. The HTTP state management mechanism specifies a way to
 * create a stateful session with HTTP requests and responses.
 *
 * <p>A system-wide CookieHandler that to used by the HTTP protocol
 * handler can be registered by doing a
 * CookieHandler.setDefault(CookieHandler). The currently registered
 * CookieHandler can be retrieved by calling
 * CookieHandler.getDefault().
 *
 * For more information on HTTP state management, see <a
 * href="http://www.ietf.org/rfc/rfc2965.txt"><i>RFC&nbsp;2965: HTTP
 * State Management Mechanism</i></a>
 *
 * <p>
 *  CookieHandler对象提供了一种回调机制,用于将HTTP状态管理策略实现链接到HTTP协议处理程序中。 HTTP状态管理机制指定一种方法来创建与HTTP请求和响应的有状态会话。
 * 
 *  <p>可以通过执行CookieHandler.setDefault(CookieHandler)来注册由HTTP协议处理程序使用的系统范围的CookieHandler。
 * 当前注册的CookieHandler可以通过调用CookieHandler.getDefault()来检索。
 * 
 *  有关HTTP状态管理的详细信息,请参见<a href="http://www.ietf.org/rfc/rfc2965.txt"> <i> RFC&nbsp; 2965：HTTP状态管理机制</i> 
 * </a>。
 * 
 * 
 * @author Yingxian Wang
 * @since 1.5
 */
public abstract class CookieHandler {
    /**
     * The system-wide cookie handler that will apply cookies to the
     * request headers and manage cookies from the response headers.
     *
     * <p>
     *  系统范围的Cookie处理程序,它将Cookie应用于请求标头,并从响应标头管理Cookie。
     * 
     * 
     * @see setDefault(CookieHandler)
     * @see getDefault()
     */
    private static CookieHandler cookieHandler;

    /**
     * Gets the system-wide cookie handler.
     *
     * <p>
     *  获取系统范围的Cookie处理程序。
     * 
     * 
     * @return the system-wide cookie handler; A null return means
     *        there is no system-wide cookie handler currently set.
     * @throws SecurityException
     *       If a security manager has been installed and it denies
     * {@link NetPermission}{@code ("getCookieHandler")}
     * @see #setDefault(CookieHandler)
     */
    public synchronized static CookieHandler getDefault() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(SecurityConstants.GET_COOKIEHANDLER_PERMISSION);
        }
        return cookieHandler;
    }

    /**
     * Sets (or unsets) the system-wide cookie handler.
     *
     * Note: non-standard http protocol handlers may ignore this setting.
     *
     * <p>
     *  设置(或取消设置)系统范围的cookie处理程序。
     * 
     *  注意：非标准http协议处理程序可能会忽略此设置。
     * 
     * 
     * @param cHandler The HTTP cookie handler, or
     *       {@code null} to unset.
     * @throws SecurityException
     *       If a security manager has been installed and it denies
     * {@link NetPermission}{@code ("setCookieHandler")}
     * @see #getDefault()
     */
    public synchronized static void setDefault(CookieHandler cHandler) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(SecurityConstants.SET_COOKIEHANDLER_PERMISSION);
        }
        cookieHandler = cHandler;
    }

    /**
     * Gets all the applicable cookies from a cookie cache for the
     * specified uri in the request header.
     *
     * <P>The {@code URI} passed as an argument specifies the intended use for
     * the cookies. In particular the scheme should reflect whether the cookies
     * will be sent over http, https or used in another context like javascript.
     * The host part should reflect either the destination of the cookies or
     * their origin in the case of javascript.</P>
     * <P>It is up to the implementation to take into account the {@code URI} and
     * the cookies attributes and security settings to determine which ones
     * should be returned.</P>
     *
     * <P>HTTP protocol implementers should make sure that this method is
     * called after all request headers related to choosing cookies
     * are added, and before the request is sent.</P>
     *
     * <p>
     *  从请求标头中指定的URI的Cookie缓存获取所有适用的Cookie。
     * 
     * <P>作为参数传递的{@code URI}指定了Cookie的预期用途。特别是该方案应该反映cookie是否将通过http,https发送或在另一个上下文(如javascript)中使用。
     * 主机部分应反映Cookie的目的地或JavaScript的情况下它们的来源。</P> <P>由实施考虑{@code URI}和Cookie属性和安全设置以确定应返回哪些。</P>。
     * 
     * 
     * @param uri a {@code URI} representing the intended use for the
     *            cookies
     * @param requestHeaders - a Map from request header
     *            field names to lists of field values representing
     *            the current request headers
     * @return an immutable map from state management headers, with
     *            field names "Cookie" or "Cookie2" to a list of
     *            cookies containing state information
     *
     * @throws IOException if an I/O error occurs
     * @throws IllegalArgumentException if either argument is null
     * @see #put(URI, Map)
     */
    public abstract Map<String, List<String>>
        get(URI uri, Map<String, List<String>> requestHeaders)
        throws IOException;

    /**
     * Sets all the applicable cookies, examples are response header
     * fields that are named Set-Cookie2, present in the response
     * headers into a cookie cache.
     *
     * <p>
     *  <P> HTTP协议实现者应确保在添加与选择Cookie相关的所有请求标头之后,以及在发送请求之前调用此方法。</P>
     * 
     * 
     * @param uri a {@code URI} where the cookies come from
     * @param responseHeaders an immutable map from field names to
     *            lists of field values representing the response
     *            header fields returned
     * @throws  IOException if an I/O error occurs
     * @throws  IllegalArgumentException if either argument is null
     * @see #get(URI, Map)
     */
    public abstract void
        put(URI uri, Map<String, List<String>> responseHeaders)
        throws IOException;
}
