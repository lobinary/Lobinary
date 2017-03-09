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
import java.util.Map;
import java.util.List;
import sun.security.util.SecurityConstants;

/**
 * Represents implementations of URLConnection caches. An instance of
 * such a class can be registered with the system by doing
 * ResponseCache.setDefault(ResponseCache), and the system will call
 * this object in order to:
 *
 *    <ul><li>store resource data which has been retrieved from an
 *            external source into the cache</li>
 *         <li>try to fetch a requested resource that may have been
 *            stored in the cache</li>
 *    </ul>
 *
 * The ResponseCache implementation decides which resources
 * should be cached, and for how long they should be cached. If a
 * request resource cannot be retrieved from the cache, then the
 * protocol handlers will fetch the resource from its original
 * location.
 *
 * The settings for URLConnection#useCaches controls whether the
 * protocol is allowed to use a cached response.
 *
 * For more information on HTTP caching, see <a
 * href="http://www.ietf.org/rfc/rfc2616.txt"><i>RFC&nbsp;2616: Hypertext
 * Transfer Protocol -- HTTP/1.1</i></a>
 *
 * <p>
 *  表示URLConnection缓存的实现。可以通过执行ResponseCache.setDefault(ResponseCache)向系统注册此类的实例,系统将调用此对象以便：
 * 
 *  <ul> <li>将从外部来源检索的资源数据存储到缓存中</li> <li>尝试提取可能已存储在缓存中的请求资源</li>
 * </ul>
 * 
 *  ResponseCache实现决定哪些资源应该缓存,以及缓存多长时间。如果无法从高速缓存检索请求资源,则协议处理程序将从其原始位置获取资源。
 * 
 *  URLConnection#useCaches的设置控制是否允许协议使用缓存的响应。
 * 
 *  有关HTTP缓存的详细信息,请参见<a href="http://www.ietf.org/rfc/rfc2616.txt"> <i> RFC 2616：超文本传输​​协议 -  HTTP / 1.1
 *  </i> <// a>。
 * 
 * 
 * @author Yingxian Wang
 * @since 1.5
 */
public abstract class ResponseCache {

    /**
     * The system wide cache that provides access to a url
     * caching mechanism.
     *
     * <p>
     *  提供对url缓存机制的访问的系统级缓存。
     * 
     * 
     * @see #setDefault(ResponseCache)
     * @see #getDefault()
     */
    private static ResponseCache theResponseCache;

    /**
     * Gets the system-wide response cache.
     *
     * <p>
     *  获取系统范围的响应缓存。
     * 
     * 
     * @throws  SecurityException
     *          If a security manager has been installed and it denies
     * {@link NetPermission}{@code ("getResponseCache")}
     *
     * @see #setDefault(ResponseCache)
     * @return the system-wide {@code ResponseCache}
     * @since 1.5
     */
    public synchronized  static ResponseCache getDefault() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(SecurityConstants.GET_RESPONSECACHE_PERMISSION);
        }
        return theResponseCache;
    }

    /**
     * Sets (or unsets) the system-wide cache.
     *
     * Note: non-standard procotol handlers may ignore this setting.
     *
     * <p>
     *  设置(或取消)系统级缓存。
     * 
     *  注意：非标准的procotol处理程序可能会忽略此设置。
     * 
     * 
     * @param responseCache The response cache, or
     *          {@code null} to unset the cache.
     *
     * @throws  SecurityException
     *          If a security manager has been installed and it denies
     * {@link NetPermission}{@code ("setResponseCache")}
     *
     * @see #getDefault()
     * @since 1.5
     */
    public synchronized static void setDefault(ResponseCache responseCache) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(SecurityConstants.SET_RESPONSECACHE_PERMISSION);
        }
        theResponseCache = responseCache;
    }

    /**
     * Retrieve the cached response based on the requesting uri,
     * request method and request headers. Typically this method is
     * called by the protocol handler before it sends out the request
     * to get the network resource. If a cached response is returned,
     * that resource is used instead.
     *
     * <p>
     * 根据请求的uri,请求方法和请求标头检索缓存的响应。通常,该方法在它发出获得网络资源的请求之前由协议处理器调用。如果返回缓存的响应,则使用该资源。
     * 
     * 
     * @param uri a {@code URI} used to reference the requested
     *            network resource
     * @param rqstMethod a {@code String} representing the request
     *            method
     * @param rqstHeaders - a Map from request header
     *            field names to lists of field values representing
     *            the current request headers
     * @return a {@code CacheResponse} instance if available
     *          from cache, or null otherwise
     * @throws  IOException if an I/O error occurs
     * @throws  IllegalArgumentException if any one of the arguments is null
     *
     * @see     java.net.URLConnection#setUseCaches(boolean)
     * @see     java.net.URLConnection#getUseCaches()
     * @see     java.net.URLConnection#setDefaultUseCaches(boolean)
     * @see     java.net.URLConnection#getDefaultUseCaches()
     */
    public abstract CacheResponse
        get(URI uri, String rqstMethod, Map<String, List<String>> rqstHeaders)
        throws IOException;

    /**
     * The protocol handler calls this method after a resource has
     * been retrieved, and the ResponseCache must decide whether or
     * not to store the resource in its cache. If the resource is to
     * be cached, then put() must return a CacheRequest object which
     * contains an OutputStream that the protocol handler will
     * use to write the resource into the cache. If the resource is
     * not to be cached, then put must return null.
     *
     * <p>
     *  协议处理程序在检索资源后调用此方法,并且ResponseCache必须决定是否将资源存储在其高速缓存中。
     * 如果资源要被缓存,则put()必须返回一个CacheRequest对象,该对象包含一个OutputStream,协议处理程序将使用它来将资源写入缓存。如果资源不被缓存,则put必须返回null。
     * 
     * @param uri a {@code URI} used to reference the requested
     *            network resource
     * @param conn - a URLConnection instance that is used to fetch
     *            the response to be cached
     * @return a {@code CacheRequest} for recording the
     *            response to be cached. Null return indicates that
     *            the caller does not intend to cache the response.
     * @throws IOException if an I/O error occurs
     * @throws IllegalArgumentException if any one of the arguments is
     *            null
     */
    public abstract CacheRequest put(URI uri, URLConnection conn)  throws IOException;
}
