/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * and represents a Unix user.
 *
 * <p> Principals such as this <code>UnixPrincipal</code>
 * may be associated with a particular <code>Subject</code>
 * to augment that <code>Subject</code> with an additional
 * identity.  Refer to the <code>Subject</code> class for more information
 * on how to achieve this.  Authorization decisions can then be based upon
 * the Principals associated with a <code>Subject</code>.
 *
 * <p>
 *  <p>此类实现<code> Principal </code>接口并表示Unix用户。
 * 
 *  <p>诸如此<code> UnixPrincipal </code>之类的主体可以与特定的<code>主题</code>相关联,以增加具有附加标识的<code>主题</code>。
 * 有关如何实现这一点的更多信息,请参阅<code> Subject </code>类。授权决定可以基于与<code> Subject </code>相关联的主体。
 * 
 * 
 * @see java.security.Principal
 * @see javax.security.auth.Subject
 */
@jdk.Exported
public class UnixPrincipal implements Principal, java.io.Serializable {

    private static final long serialVersionUID = -2951667807323493631L;

    /**
    /* <p>
    /* 
     * @serial
     */
    private String name;

    /**
     * Create a UnixPrincipal with a Unix username.
     *
     * <p>
     *
     * <p>
     *  使用Unix用户名创建UnixPrincipal。
     * 
     * <p>
     * 
     * 
     * @param name the Unix username for this user.
     *
     * @exception NullPointerException if the <code>name</code>
     *                  is <code>null</code>.
     */
    public UnixPrincipal(String name) {
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
     * Return the Unix username for this <code>UnixPrincipal</code>.
     *
     * <p>
     *
     * <p>
     *  返回此<code> UnixPrincipal </code>的Unix用户名。
     * 
     * <p>
     * 
     * 
     * @return the Unix username for this <code>UnixPrincipal</code>
     */
    public String getName() {
        return name;
    }

    /**
     * Return a string representation of this <code>UnixPrincipal</code>.
     *
     * <p>
     *
     * <p>
     *  返回此<code> UnixPrincipal </code>的字符串表示形式。
     * 
     * <p>
     * 
     * 
     * @return a string representation of this <code>UnixPrincipal</code>.
     */
    public String toString() {
        java.text.MessageFormat form = new java.text.MessageFormat
                (sun.security.util.ResourcesMgr.getString
                        ("UnixPrincipal.name",
                        "sun.security.util.AuthResources"));
        Object[] source = {name};
        return form.format(source);
    }

    /**
     * Compares the specified Object with this <code>UnixPrincipal</code>
     * for equality.  Returns true if the given object is also a
     * <code>UnixPrincipal</code> and the two UnixPrincipals
     * have the same username.
     *
     * <p>
     *
     * <p>
     *  使用此<code> UnixPrincipal </code>比较指定的对象是否相等。
     * 如果给定对象也是<code> UnixPrincipal </code>,并且两个UnixPrincipals具有相同的用户名,则返回true。
     * 
     * <p>
     * 
     * 
     * @param o Object to be compared for equality with this
     *          <code>UnixPrincipal</code>.
     *
     * @return true if the specified Object is equal equal to this
     *          <code>UnixPrincipal</code>.
     */
    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (this == o)
            return true;

        if (!(o instanceof UnixPrincipal))
            return false;
        UnixPrincipal that = (UnixPrincipal)o;

        if (this.getName().equals(that.getName()))
            return true;
        return false;
    }

    /**
     * Return a hash code for this <code>UnixPrincipal</code>.
     *
     * <p>
     *
     * <p>
     * 
     * @return a hash code for this <code>UnixPrincipal</code>.
     */
    public int hashCode() {
        return name.hashCode();
    }
}
