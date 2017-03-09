/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.util.List;
import java.util.Map;

/**
 * A CookieStore object represents a storage for cookie. Can store and retrieve
 * cookies.
 *
 * <p>{@link CookieManager} will call {@code CookieStore.add} to save cookies
 * for every incoming HTTP response, and call {@code CookieStore.get} to
 * retrieve cookie for every outgoing HTTP request. A CookieStore
 * is responsible for removing HttpCookie instances which have expired.
 *
 * <p>
 *  CookieStore对象表示Cookie的存储。可以存储和检索Cookie。
 * 
 *  <p> {@ link CookieManager}会调用{@code CookieStore.add}为每个传入的HTTP响应保存Cookie,并调用{@code CookieStore.get}来
 * 为每个传出的HTTP请求检索Cookie。
 *  CookieStore负责删除已过期的HttpCookie实例。
 * 
 * 
 * @author Edward Wang
 * @since 1.6
 */
public interface CookieStore {
    /**
     * Adds one HTTP cookie to the store. This is called for every
     * incoming HTTP response.
     *
     * <p>A cookie to store may or may not be associated with an URI. If it
     * is not associated with an URI, the cookie's domain and path attribute
     * will indicate where it comes from. If it is associated with an URI and
     * its domain and path attribute are not specified, given URI will indicate
     * where this cookie comes from.
     *
     * <p>If a cookie corresponding to the given URI already exists,
     * then it is replaced with the new one.
     *
     * <p>
     *  向存储添加一个HTTP Cookie。这是为每个传入的HTTP响应调用。
     * 
     *  <p>存储的Cookie可能与URI相关联,也可能不会与URI相关联。如果它不与URI相关联,则cookie的domain和path属性将指示它来自哪里。
     * 如果它与一个URI相关联,并且未指定其域和路径属性,则给定URI将指示此Cookie来自哪里。
     * 
     *  <p>如果与给定URI对应的Cookie已经存在,那么它将被替换为新的。
     * 
     * 
     * @param uri       the uri this cookie associated with.
     *                  if {@code null}, this cookie will not be associated
     *                  with an URI
     * @param cookie    the cookie to store
     *
     * @throws NullPointerException if {@code cookie} is {@code null}
     *
     * @see #get
     *
     */
    public void add(URI uri, HttpCookie cookie);


    /**
     * Retrieve cookies associated with given URI, or whose domain matches the
     * given URI. Only cookies that have not expired are returned.
     * This is called for every outgoing HTTP request.
     *
     * <p>
     *  检索与给定URI或其域与给定URI相匹配的Cookie。仅返回未过期的Cookie。这是为每个传出的HTTP请求调用。
     * 
     * 
     * @return          an immutable list of HttpCookie,
     *                  return empty list if no cookies match the given URI
     *
     * @param uri       the uri associated with the cookies to be returned
     *
     * @throws NullPointerException if {@code uri} is {@code null}
     *
     * @see #add
     *
     */
    public List<HttpCookie> get(URI uri);


    /**
     * Get all not-expired cookies in cookie store.
     *
     * <p>
     *  获取cookie存储中的所有未过期的Cookie。
     * 
     * 
     * @return          an immutable list of http cookies;
     *                  return empty list if there's no http cookie in store
     */
    public List<HttpCookie> getCookies();


    /**
     * Get all URIs which identify the cookies in this cookie store.
     *
     * <p>
     *  获取标识此Cookie存储中的Cookie的所有URI。
     * 
     * 
     * @return          an immutable list of URIs;
     *                  return empty list if no cookie in this cookie store
     *                  is associated with an URI
     */
    public List<URI> getURIs();


    /**
     * Remove a cookie from store.
     *
     * <p>
     *  从商店删除Cookie。
     * 
     * 
     * @param uri       the uri this cookie associated with.
     *                  if {@code null}, the cookie to be removed is not associated
     *                  with an URI when added; if not {@code null}, the cookie
     *                  to be removed is associated with the given URI when added.
     * @param cookie    the cookie to remove
     *
     * @return          {@code true} if this store contained the specified cookie
     *
     * @throws NullPointerException if {@code cookie} is {@code null}
     */
    public boolean remove(URI uri, HttpCookie cookie);


    /**
     * Remove all cookies in this cookie store.
     *
     * <p>
     *  删除此Cookie存储中的所有Cookie。
     * 
     * @return          {@code true} if this store changed as a result of the call
     */
    public boolean removeAll();
}
