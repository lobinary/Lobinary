/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2010, Oracle and/or its affiliates. All rights reserved.
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

import javax.naming.NamingException;

/**
 * This interface represents an unsolicited notification as defined in
 * <A HREF="http://www.ietf.org/rfc/rfc2251.txt">RFC 2251</A>.
 * An unsolicited notification is sent by the LDAP server to the LDAP
 * client without any provocation from the client.
 * Its format is that of an extended response (<tt>ExtendedResponse</tt>).
 *
 * <p>
 *  此接口代表<A HREF="http://www.ietf.org/rfc/rfc2251.txt"> RFC 2251 </A>中定义的非请求通知。
 *  LDAP服务器会将未经请求的通知发送到LDAP客户端,而不会从客户端进行任何挑衅。它的格式是扩展响应(<tt> ExtendedResponse </tt>)。
 * 
 * 
 * @author Rosanna Lee
 * @author Scott Seligman
 * @author Vincent Ryan
 *
 * @see ExtendedResponse
 * @see UnsolicitedNotificationEvent
 * @see UnsolicitedNotificationListener
 * @since 1.3
 */

public interface UnsolicitedNotification extends ExtendedResponse, HasControls {
    /**
     * Retrieves the referral(s) sent by the server.
     *
     * <p>
     *  检索由服务器发送的引荐。
     * 
     * 
     * @return A possibly null array of referrals, each of which is represented
     * by a URL string. If null, no referral was sent by the server.
     */
    public String[] getReferrals();

    /**
     * Retrieves the exception as constructed using information
     * sent by the server.
     * <p>
     *  使用服务器发送的信息检索构造的异常。
     * 
     * @return A possibly null exception as constructed using information
     * sent by the server. If null, a "success" status was indicated by
     * the server.
     */
    public NamingException getException();
}
