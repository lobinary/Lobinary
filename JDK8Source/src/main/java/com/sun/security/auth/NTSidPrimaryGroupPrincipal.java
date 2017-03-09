/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.security.auth;

/**
 * <p> This class extends <code>NTSid</code>
 * and represents a Windows NT user's primary group SID.
 *
 * <p> Principals such as this <code>NTSidPrimaryGroupPrincipal</code>
 * may be associated with a particular <code>Subject</code>
 * to augment that <code>Subject</code> with an additional
 * identity.  Refer to the <code>Subject</code> class for more information
 * on how to achieve this.  Authorization decisions can then be based upon
 * the Principals associated with a <code>Subject</code>.
 *
 * <p>
 *  <p>此类扩展了<code> NTSid </code>,表示Windows NT用户的主组SID。
 * 
 *  <p>诸如<code> NTSidPrimaryGroupPrincipal </code>之类的主体可以与特定的<code> Subject </code>相关联以增加具有附加标识的<code> S
 * ubject </code>。
 * 有关如何实现这一点的更多信息,请参阅<code> Subject </code>类。授权决定可以基于与<code> Subject </code>相关联的主体。
 * 
 * 
 * @see java.security.Principal
 * @see javax.security.auth.Subject
 */
@jdk.Exported
public class NTSidPrimaryGroupPrincipal extends NTSid {

    private static final long serialVersionUID = 8011978367305190527L;

    /**
     * Create an <code>NTSidPrimaryGroupPrincipal</code> with a Windows NT
     * group SID.
     *
     * <p>
     *
     * <p>
     *  使用Windows NT组SID创建<code> NTSidPrimaryGroupPrincipal </code>。
     * 
     * <p>
     * 
     * 
     * @param name the primary Windows NT group SID for this user. <p>
     *
     * @exception NullPointerException if the <code>name</code>
     *            is <code>null</code>.
     */
    public NTSidPrimaryGroupPrincipal(String name) {
        super(name);
    }

    /**
     * Return a string representation of this
     * <code>NTSidPrimaryGroupPrincipal</code>.
     *
     * <p>
     *
     * <p>
     *  返回此<code> NTSidPrimaryGroupPrincipal </code>的字符串表示形式。
     * 
     * <p>
     * 
     * 
     * @return a string representation of this
     *          <code>NTSidPrimaryGroupPrincipal</code>.
     */
    public String toString() {
        java.text.MessageFormat form = new java.text.MessageFormat
                (sun.security.util.ResourcesMgr.getString
                        ("NTSidPrimaryGroupPrincipal.name",
                        "sun.security.util.AuthResources"));
        Object[] source = {getName()};
        return form.format(source);
    }

    /**
     * Compares the specified Object with this
     * <code>NTSidPrimaryGroupPrincipal</code>
     * for equality.  Returns true if the given object is also a
     * <code>NTSidPrimaryGroupPrincipal</code> and the two
     * NTSidPrimaryGroupPrincipals have the same SID.
     *
     * <p>
     *
     * <p>
     *  将指定的对象与此<code> NTSidPrimaryGroupPrincipal </code>比较以实现相等。
     * 如果给定对象也是<code> NTSidPrimaryGroupPrincipal </code>,并且两个NTSidPrimaryGroupPrincipals具有相同的SID,则返回true。
     * 
     * 
     * @param o Object to be compared for equality with this
     *          <code>NTSidPrimaryGroupPrincipal</code>.
     *
     * @return true if the specified Object is equal equal to this
     *          <code>NTSidPrimaryGroupPrincipal</code>.
     */
    public boolean equals(Object o) {
            if (o == null)
                return false;

        if (this == o)
            return true;

        if (!(o instanceof NTSidPrimaryGroupPrincipal))
            return false;

        return super.equals(o);
    }

}
