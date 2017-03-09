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

/**
 * CookiePolicy implementations decide which cookies should be accepted
 * and which should be rejected. Three pre-defined policy implementations
 * are provided, namely ACCEPT_ALL, ACCEPT_NONE and ACCEPT_ORIGINAL_SERVER.
 *
 * <p>See RFC 2965 sec. 3.3 and 7 for more detail.
 *
 * <p>
 *  CookiePolicy实现决定应该接受哪些Cookie,哪些应该被拒绝。提供了三个预定义的策略实现,即ACCEPT_ALL,ACCEPT_NONE和ACCEPT_ORIGINAL_SERVER。
 * 
 *  <p>请参阅RFC 2965 sec。 3.3和7更详细。
 * 
 * 
 * @author Edward Wang
 * @since 1.6
 */
public interface CookiePolicy {
    /**
     * One pre-defined policy which accepts all cookies.
     * <p>
     *  一种预定义的策略,接受所有Cookie。
     * 
     */
    public static final CookiePolicy ACCEPT_ALL = new CookiePolicy(){
        public boolean shouldAccept(URI uri, HttpCookie cookie) {
            return true;
        }
    };

    /**
     * One pre-defined policy which accepts no cookies.
     * <p>
     *  一种预先定义的不接受Cookie的策略。
     * 
     */
    public static final CookiePolicy ACCEPT_NONE = new CookiePolicy(){
        public boolean shouldAccept(URI uri, HttpCookie cookie) {
            return false;
        }
    };

    /**
     * One pre-defined policy which only accepts cookies from original server.
     * <p>
     *  一种预定义的策略,只接受来自原始服务器的Cookie。
     * 
     */
    public static final CookiePolicy ACCEPT_ORIGINAL_SERVER  = new CookiePolicy(){
        public boolean shouldAccept(URI uri, HttpCookie cookie) {
            if (uri == null || cookie == null)
                return false;
            return HttpCookie.domainMatches(cookie.getDomain(), uri.getHost());
        }
    };


    /**
     * Will be called to see whether or not this cookie should be accepted.
     *
     * <p>
     *  将被调用来查看是否应该接受此cookie。
     * 
     * @param uri       the URI to consult accept policy with
     * @param cookie    the HttpCookie object in question
     * @return          {@code true} if this cookie should be accepted;
     *                  otherwise, {@code false}
     */
    public boolean shouldAccept(URI uri, HttpCookie cookie);
}
