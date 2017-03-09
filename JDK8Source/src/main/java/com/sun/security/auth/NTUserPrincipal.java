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

import java.security.Principal;

/**
 * <p> This class implements the <code>Principal</code> interface
 * and represents a Windows NT user.
 *
 * <p> Principals such as this <code>NTUserPrincipal</code>
 * may be associated with a particular <code>Subject</code>
 * to augment that <code>Subject</code> with an additional
 * identity.  Refer to the <code>Subject</code> class for more information
 * on how to achieve this.  Authorization decisions can then be based upon
 * the Principals associated with a <code>Subject</code>.
 *
 * <p>
 *  <p>此类实现<code> Principal </code>接口,代表一个Windows NT用户。
 * 
 *  <p>这个<code> NTUserPrincipal </code>的主体可以与特定的<code> Subject </code>相关联,以增加具有附加标识的<code> Subject </code>
 * 。
 * 有关如何实现这一点的更多信息,请参阅<code> Subject </code>类。授权决定可以基于与<code> Subject </code>相关联的主体。
 * 
 * 
 * @see java.security.Principal
 * @see javax.security.auth.Subject
 */
@jdk.Exported
public class NTUserPrincipal implements Principal, java.io.Serializable {

    private static final long serialVersionUID = -8737649811939033735L;

    /**
    /* <p>
    /* 
     * @serial
     */
    private String name;

    /**
     * Create an <code>NTUserPrincipal</code> with a Windows NT username.
     *
     * <p>
     *
     * <p>
     *  使用Windows NT用户名创建<code> NTUserPrincipal </code>。
     * 
     * <p>
     * 
     * 
     * @param name the Windows NT username for this user. <p>
     *
     * @exception NullPointerException if the <code>name</code>
     *            is <code>null</code>.
     */
    public NTUserPrincipal(String name) {
        if (name == null) {
            java.text.MessageFormat form = new java.text.MessageFormat
                (sun.security.util.ResourcesMgr.getString
                        ("invalid.null.input.value",
                        "sun.security.util.AuthResources"));
            Object[] source = {"name"};
            throw new NullPointerException(form.format(source));
        }
        this.name = name;
    }

    /**
     * Return the Windows NT username for this <code>NTPrincipal</code>.
     *
     * <p>
     *
     * <p>
     *  返回此<code> NTPrincipal </code>的Windows NT用户名。
     * 
     * <p>
     * 
     * 
     * @return the Windows NT username for this <code>NTPrincipal</code>
     */
    public String getName() {
        return name;
    }

    /**
     * Return a string representation of this <code>NTPrincipal</code>.
     *
     * <p>
     *
     * <p>
     *  返回此<code> NTPrincipal </code>的字符串表示形式。
     * 
     * <p>
     * 
     * 
     * @return a string representation of this <code>NTPrincipal</code>.
     */
    public String toString() {
        java.text.MessageFormat form = new java.text.MessageFormat
                (sun.security.util.ResourcesMgr.getString
                        ("NTUserPrincipal.name",
                        "sun.security.util.AuthResources"));
        Object[] source = {name};
        return form.format(source);
    }

    /**
     * Compares the specified Object with this <code>NTUserPrincipal</code>
     * for equality.  Returns true if the given object is also a
     * <code>NTUserPrincipal</code> and the two NTUserPrincipals
     * have the same name.
     *
     * <p>
     *
     * <p>
     *  将指定的对象与此<code> NTUserPrincipal </code>比较以实现相等。
     * 如果给定对象也是<code> NTUserPrincipal </code>,并且两个NTUserPrincipals具有相同的名称,则返回true。
     * 
     * <p>
     * 
     * 
     * @param o Object to be compared for equality with this
     *          <code>NTPrincipal</code>.
     *
     * @return true if the specified Object is equal equal to this
     *          <code>NTPrincipal</code>.
     */
    public boolean equals(Object o) {
            if (o == null)
                return false;

        if (this == o)
            return true;

        if (!(o instanceof NTUserPrincipal))
            return false;
        NTUserPrincipal that = (NTUserPrincipal)o;

            if (name.equals(that.getName()))
                return true;
            return false;
    }

    /**
     * Return a hash code for this <code>NTUserPrincipal</code>.
     *
     * <p>
     *
     * <p>
     * 
     * @return a hash code for this <code>NTUserPrincipal</code>.
     */
    public int hashCode() {
            return this.getName().hashCode();
    }
}
