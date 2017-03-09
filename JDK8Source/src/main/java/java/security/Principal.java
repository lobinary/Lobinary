/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.security;

import javax.security.auth.Subject;

/**
 * This interface represents the abstract notion of a principal, which
 * can be used to represent any entity, such as an individual, a
 * corporation, and a login id.
 *
 * <p>
 *  此接口代表主体的抽象概念,可用于表示任何实体,如个人,公司和登录ID。
 * 
 * 
 * @see java.security.cert.X509Certificate
 *
 * @author Li Gong
 */
public interface Principal {

    /**
     * Compares this principal to the specified object.  Returns true
     * if the object passed in matches the principal represented by
     * the implementation of this interface.
     *
     * <p>
     *  将此主体与指定的对象进行比较。如果传入的对象与由此接口的实现表示的主体匹配,则返回true。
     * 
     * 
     * @param another principal to compare with.
     *
     * @return true if the principal passed in is the same as that
     * encapsulated by this principal, and false otherwise.
     */
    public boolean equals(Object another);

    /**
     * Returns a string representation of this principal.
     *
     * <p>
     *  返回此主体的字符串表示形式。
     * 
     * 
     * @return a string representation of this principal.
     */
    public String toString();

    /**
     * Returns a hashcode for this principal.
     *
     * <p>
     *  返回此主体的哈希码。
     * 
     * 
     * @return a hashcode for this principal.
     */
    public int hashCode();

    /**
     * Returns the name of this principal.
     *
     * <p>
     *  返回此主体的名称。
     * 
     * 
     * @return the name of this principal.
     */
    public String getName();

    /**
     * Returns true if the specified subject is implied by this principal.
     *
     * <p>The default implementation of this method returns true if
     * {@code subject} is non-null and contains at least one principal that
     * is equal to this principal.
     *
     * <p>Subclasses may override this with a different implementation, if
     * necessary.
     *
     * <p>
     *  如果此主体隐含指定的主题,则返回true。
     * 
     *  <p>如果{@code subject}为非空值,且包含至少一个等于此主体的主体,此方法的默认实现将返回true。
     * 
     * @param subject the {@code Subject}
     * @return true if {@code subject} is non-null and is
     *              implied by this principal, or false otherwise.
     * @since 1.8
     */
    public default boolean implies(Subject subject) {
        if (subject == null)
            return false;
        return subject.getPrincipals().contains(this);
    }
}
