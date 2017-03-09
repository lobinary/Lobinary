/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2006, 2013, Oracle and/or its affiliates. All rights reserved.
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
import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;

/**
 * A principal identified by a distinguished name as specified by
 * <a href="http://www.ietf.org/rfc/rfc2253.txt">RFC 2253</a>.
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
 *  由<a href="http://www.ietf.org/rfc/rfc2253.txt"> RFC 2253 </a>指定的专有名称标识的主体。
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
public final class LdapPrincipal implements Principal, java.io.Serializable {

    private static final long serialVersionUID = 6820120005580754861L;

    /**
     * The principal's string name
     *
     * <p>
     *  主体的字符串名称
     * 
     * 
     * @serial
     */
    private final String nameString;

    /**
     * The principal's name
     *
     * <p>
     *  委托人的名字
     * 
     * 
     * @serial
     */
    private final LdapName name;

    /**
     * Creates an LDAP principal.
     *
     * <p>
     *  创建LDAP主体。
     * 
     * 
     * @param name The principal's string distinguished name.
     * @throws InvalidNameException If a syntax violation is detected.
     * @exception NullPointerException If the <code>name</code> is
     * <code>null</code>.
     */
    public LdapPrincipal(String name) throws InvalidNameException {
        if (name == null) {
            throw new NullPointerException("null name is illegal");
        }
        this.name = getLdapName(name);
        nameString = name;
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
        if (object instanceof LdapPrincipal) {
            try {

                return
                    name.equals(getLdapName(((LdapPrincipal)object).getName()));

            } catch (InvalidNameException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * Computes the hash code for this principal.
     *
     * <p>
     *  计算此主体的散列码。
     * 
     * 
     * @return The principal's hash code.
     */
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Returns the name originally used to create this principal.
     *
     * <p>
     *  返回最初用于创建此主体的名称。
     * 
     * 
     * @return The principal's string name.
     */
    public String getName() {
        return nameString;
    }

    /**
     * Creates a string representation of this principal's name in the format
     * defined by <a href="http://www.ietf.org/rfc/rfc2253.txt">RFC 2253</a>.
     * If the name has zero components an empty string is returned.
     *
     * <p>
     *  使用<a href="http://www.ietf.org/rfc/rfc2253.txt"> RFC 2253 </a>定义的格式创建此主体名称的字符串表示形式。
     * 如果名称具有零个组件,则返回空字符串。
     * 
     * @return The principal's string name.
     */
    public String toString() {
        return name.toString();
    }

    // Create an LdapName object from a string distinguished name.
    private LdapName getLdapName(String name) throws InvalidNameException {
        return new LdapName(name);
    }
}
