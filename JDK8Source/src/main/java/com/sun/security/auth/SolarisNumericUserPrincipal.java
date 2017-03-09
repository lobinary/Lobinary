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
 * and represents a user's Solaris identification number (UID).
 *
 * <p> Principals such as this <code>SolarisNumericUserPrincipal</code>
 * may be associated with a particular <code>Subject</code>
 * to augment that <code>Subject</code> with an additional
 * identity.  Refer to the <code>Subject</code> class for more information
 * on how to achieve this.  Authorization decisions can then be based upon
 * the Principals associated with a <code>Subject</code>.
 * <p>
 *  <p>此类实现<code> Principal </code>接口并表示用户的Solaris标识号(UID)。
 * 
 *  <p>这种<code> SolarisNumericUserPrincipal </code>之类的主体可以与特定的<code> Subject </code>相关联,以增加具有附加标识的<code>
 *  Subject </code>。
 * 有关如何实现这一点的更多信息,请参阅<code> Subject </code>类。授权决定可以基于与<code> Subject </code>相关联的主体。
 * 
 * 
 * @deprecated As of JDK&nbsp;1.4, replaced by
 *             {@link UnixNumericUserPrincipal}.
 *             This class is entirely deprecated.
 *
 * @see java.security.Principal
 * @see javax.security.auth.Subject
 */
@jdk.Exported(false)
@Deprecated
public class SolarisNumericUserPrincipal implements
                                        Principal,
                                        java.io.Serializable {

    private static final long serialVersionUID = -3178578484679887104L;

    private static final java.util.ResourceBundle rb =
          java.security.AccessController.doPrivileged
          (new java.security.PrivilegedAction<java.util.ResourceBundle>() {
              public java.util.ResourceBundle run() {
                  return (java.util.ResourceBundle.getBundle
                                ("sun.security.util.AuthResources"));
              }
           });


    /**
    /* <p>
    /* 
     * @serial
     */
    private String name;

    /**
     * Create a <code>SolarisNumericUserPrincipal</code> using a
     * <code>String</code> representation of the
     * user's identification number (UID).
     *
     * <p>
     *
     * <p>
     *  使用用户标识号(UID)的<code> String </code>表示创建<code> SolarisNumericUserPrincipal </code>。
     * 
     * <p>
     * 
     * 
     * @param name the user identification number (UID) for this user.
     *
     * @exception NullPointerException if the <code>name</code>
     *                  is <code>null</code>.
     */
    public SolarisNumericUserPrincipal(String name) {
        if (name == null)
            throw new NullPointerException(rb.getString("provided.null.name"));

        this.name = name;
    }

    /**
     * Create a <code>SolarisNumericUserPrincipal</code> using a
     * long representation of the user's identification number (UID).
     *
     * <p>
     *
     * <p>
     *  使用用户标识号(UID)的长表示创建<code> SolarisNumericUserPrincipal </code>。
     * 
     * <p>
     * 
     * 
     * @param name the user identification number (UID) for this user
     *                  represented as a long.
     */
    public SolarisNumericUserPrincipal(long name) {
        this.name = (new Long(name)).toString();
    }

    /**
     * Return the user identification number (UID) for this
     * <code>SolarisNumericUserPrincipal</code>.
     *
     * <p>
     *
     * <p>
     *  返回此<code> SolarisNumericUserPrincipal </code>的用户标识号(UID)。
     * 
     * <p>
     * 
     * 
     * @return the user identification number (UID) for this
     *          <code>SolarisNumericUserPrincipal</code>
     */
    public String getName() {
        return name;
    }

    /**
     * Return the user identification number (UID) for this
     * <code>SolarisNumericUserPrincipal</code> as a long.
     *
     * <p>
     *
     * <p>
     *  将此<code> SolarisNumericUserPrincipal </code>的用户标识号(UID)返回为long。
     * 
     * <p>
     * 
     * 
     * @return the user identification number (UID) for this
     *          <code>SolarisNumericUserPrincipal</code> as a long.
     */
    public long longValue() {
        return ((new Long(name)).longValue());
    }

    /**
     * Return a string representation of this
     * <code>SolarisNumericUserPrincipal</code>.
     *
     * <p>
     *
     * <p>
     *  返回此<code> SolarisNumericUserPrincipal </code>的字符串表示形式。
     * 
     * <p>
     * 
     * 
     * @return a string representation of this
     *          <code>SolarisNumericUserPrincipal</code>.
     */
    public String toString() {
        return(rb.getString("SolarisNumericUserPrincipal.") + name);
    }

    /**
     * Compares the specified Object with this
     * <code>SolarisNumericUserPrincipal</code>
     * for equality.  Returns true if the given object is also a
     * <code>SolarisNumericUserPrincipal</code> and the two
     * SolarisNumericUserPrincipals
     * have the same user identification number (UID).
     *
     * <p>
     *
     * <p>
     *  使用此<code> SolarisNumericUserPrincipal </code>比较指定的对象是否相等。
     * 如果给定对象也是<code> SolarisNumericUserPrincipal </code>,且两个SolarisNumericUserPrincipals具有相同的用户标识号(UID),则返回
     * true。
     *  使用此<code> SolarisNumericUserPrincipal </code>比较指定的对象是否相等。
     * 
     * <p>
     * 
     * @param o Object to be compared for equality with this
     *          <code>SolarisNumericUserPrincipal</code>.
     *
     * @return true if the specified Object is equal equal to this
     *          <code>SolarisNumericUserPrincipal</code>.
     */
    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (this == o)
            return true;

        if (!(o instanceof SolarisNumericUserPrincipal))
            return false;
        SolarisNumericUserPrincipal that = (SolarisNumericUserPrincipal)o;

        if (this.getName().equals(that.getName()))
            return true;
        return false;
    }

    /**
     * Return a hash code for this <code>SolarisNumericUserPrincipal</code>.
     *
     * <p>
     *
     * <p>
     * 
     * 
     * @return a hash code for this <code>SolarisNumericUserPrincipal</code>.
     */
    public int hashCode() {
        return name.hashCode();
    }
}
