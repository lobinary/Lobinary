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

package com.sun.security.auth;

import java.security.Principal;

/**
 * A user principal identified by a username or account name.
 *
 * <p>
 * After successful authentication, a user {@link java.security.Principal}
 * can be associated with a particular {@link javax.security.auth.Subject}
 * to augment that <code>Subject</code> with an additional identity.
 * Authorization decisions can then be based upon the
 * <code>Principal</code>s that are associated with a <code>Subject</code>.
 *
 * <p>
 * This class is immutable.
 *
 * <p>
 *  通过用户名或帐户名标识的用户主体。
 * 
 * <p>
 *  成功认证后,用户{@link java.security.Principal}可以与特定的{@link javax.security.auth.Subject}相关联,以增加具有附加标识的<code>
 * 主题</code>。
 * 授权决定可以基于与<code> Subject </code>相关联的<code> Principal </code>。
 * 
 * <p>
 *  这个类是不可变的。
 * 
 * 
 * @since 1.6
 */
@jdk.Exported
public final class UserPrincipal implements Principal, java.io.Serializable {

    private static final long serialVersionUID = 892106070870210969L;

    /**
     * The principal's name
     *
     * <p>
     *  委托人的名字
     * 
     * 
     * @serial
     */
    private final String name;

    /**
     * Creates a principal.
     *
     * <p>
     *  创建主体。
     * 
     * 
     * @param name The principal's string name.
     * @exception NullPointerException If the <code>name</code> is
     * <code>null</code>.
     */
    public UserPrincipal(String name) {
        if (name == null) {
            throw new NullPointerException("null name is illegal");
        }
        this.name = name;
    }

    /**
     * Compares this principal to the specified object.
     *
     * <p>
     *  将此主体与指定的对象进行比较。
     * 
     * 
     * @param object The object to compare this principal against.
     * @return true if they are equal; false otherwise.
     */
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof UserPrincipal) {
            return name.equals(((UserPrincipal)object).getName());
        }
        return false;
    }

    /**
     * Returns a hash code for this principal.
     *
     * <p>
     *  返回此主体的哈希码。
     * 
     * 
     * @return The principal's hash code.
     */
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Returns the name of this principal.
     *
     * <p>
     *  返回此主体的名称。
     * 
     * 
     * @return The principal's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a string representation of this principal.
     *
     * <p>
     *  返回此主体的字符串表示形式。
     * 
     * @return The principal's name.
     */
    public String toString() {
        return name;
    }
}
