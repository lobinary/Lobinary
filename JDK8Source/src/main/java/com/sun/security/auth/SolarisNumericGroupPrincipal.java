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
 * and represents a user's Solaris group identification number (GID).
 *
 * <p> Principals such as this <code>SolarisNumericGroupPrincipal</code>
 * may be associated with a particular <code>Subject</code>
 * to augment that <code>Subject</code> with an additional
 * identity.  Refer to the <code>Subject</code> class for more information
 * on how to achieve this.  Authorization decisions can then be based upon
 * the Principals associated with a <code>Subject</code>.

 * <p>
 *  <p>此类实现<code> Principal </code>接口,表示用户的Solaris组标识号(GID)。
 * 
 *  <p>这些<code> SolarisNumericGroupPrincipal </code>之类的主体可能与特定的<code> Subject </code>相关联,以增加具有附加标识的<code>
 *  Subject </code>。
 * 有关如何实现这一点的更多信息,请参阅<code> Subject </code>类。授权决定可以基于与<code> Subject </code>相关联的主体。
 * 
 * 
 * @deprecated As of JDK&nbsp;1.4, replaced by
 *             {@link UnixNumericGroupPrincipal}.
 *             This class is entirely deprecated.
 *
 * @see java.security.Principal
 * @see javax.security.auth.Subject
 */
@jdk.Exported(false)
@Deprecated
public class SolarisNumericGroupPrincipal implements
                                        Principal,
                                        java.io.Serializable {

    private static final long serialVersionUID = 2345199581042573224L;

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
    /* <p>
    /* 
     * @serial
     */
    private boolean primaryGroup;

    /**
     * Create a <code>SolarisNumericGroupPrincipal</code> using a
     * <code>String</code> representation of the user's
     * group identification number (GID).
     *
     * <p>
     *
     * <p>
     *  使用用户的组标识号(GID)的<code> String </code>表示创建<code> SolarisNumericGroupPrincipal </code>。
     * 
     * <p>
     * 
     * 
     * @param name the user's group identification number (GID)
     *                  for this user. <p>
     *
     * @param primaryGroup true if the specified GID represents the
     *                  primary group to which this user belongs.
     *
     * @exception NullPointerException if the <code>name</code>
     *                  is <code>null</code>.
     */
    public SolarisNumericGroupPrincipal(String name, boolean primaryGroup) {
        if (name == null)
            throw new NullPointerException(rb.getString("provided.null.name"));

        this.name = name;
        this.primaryGroup = primaryGroup;
    }

    /**
     * Create a <code>SolarisNumericGroupPrincipal</code> using a
     * long representation of the user's group identification number (GID).
     *
     * <p>
     *
     * <p>
     *  使用用户的组标识号(GID)的长表示创建<code> SolarisNumericGroupPrincipal </code>。
     * 
     * <p>
     * 
     * 
     * @param name the user's group identification number (GID) for this user
     *                  represented as a long. <p>
     *
     * @param primaryGroup true if the specified GID represents the
     *                  primary group to which this user belongs.
     *
     */
    public SolarisNumericGroupPrincipal(long name, boolean primaryGroup) {
        this.name = (new Long(name)).toString();
        this.primaryGroup = primaryGroup;
    }

    /**
     * Return the user's group identification number (GID) for this
     * <code>SolarisNumericGroupPrincipal</code>.
     *
     * <p>
     *
     * <p>
     *  返回此<code> SolarisNumericGroupPrincipal </code>的用户组标识号(GID)。
     * 
     * <p>
     * 
     * 
     * @return the user's group identification number (GID) for this
     *          <code>SolarisNumericGroupPrincipal</code>
     */
    public String getName() {
        return name;
    }

    /**
     * Return the user's group identification number (GID) for this
     * <code>SolarisNumericGroupPrincipal</code> as a long.
     *
     * <p>
     *
     * <p>
     *  将此<code> SolarisNumericGroupPrincipal </code>的用户组标识号(GID)返回为long。
     * 
     * <p>
     * 
     * 
     * @return the user's group identification number (GID) for this
     *          <code>SolarisNumericGroupPrincipal</code> as a long.
     */
    public long longValue() {
        return ((new Long(name)).longValue());
    }

    /**
     * Return whether this group identification number (GID) represents
     * the primary group to which this user belongs.
     *
     * <p>
     *
     * <p>
     *  返回此组标识号(GID)是否表示此用户所属的主组。
     * 
     * <p>
     * 
     * 
     * @return true if this group identification number (GID) represents
     *          the primary group to which this user belongs,
     *          or false otherwise.
     */
    public boolean isPrimaryGroup() {
        return primaryGroup;
    }

    /**
     * Return a string representation of this
     * <code>SolarisNumericGroupPrincipal</code>.
     *
     * <p>
     *
     * <p>
     *  返回此<code> SolarisNumericGroupPrincipal </code>的字符串表示形式。
     * 
     * <p>
     * 
     * 
     * @return a string representation of this
     *          <code>SolarisNumericGroupPrincipal</code>.
     */
    public String toString() {
        return((primaryGroup ?
            rb.getString
            ("SolarisNumericGroupPrincipal.Primary.Group.") + name :
            rb.getString
            ("SolarisNumericGroupPrincipal.Supplementary.Group.") + name));
    }

    /**
     * Compares the specified Object with this
     * <code>SolarisNumericGroupPrincipal</code>
     * for equality.  Returns true if the given object is also a
     * <code>SolarisNumericGroupPrincipal</code> and the two
     * SolarisNumericGroupPrincipals
     * have the same group identification number (GID).
     *
     * <p>
     *
     * <p>
     * 将指定的对象与此<code> SolarisNumericGroupPrincipal </code>比较以确保相等。
     * 如果给定对象也是<code> SolarisNumericGroupPrincipal </code>,并且两个SolarisNumericGroupPrincipals具有相同的组标识号(GID),则
     * 返回true。
     * 将指定的对象与此<code> SolarisNumericGroupPrincipal </code>比较以确保相等。
     * 
     * <p>
     * 
     * @param o Object to be compared for equality with this
     *          <code>SolarisNumericGroupPrincipal</code>.
     *
     * @return true if the specified Object is equal equal to this
     *          <code>SolarisNumericGroupPrincipal</code>.
     */
    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (this == o)
            return true;

        if (!(o instanceof SolarisNumericGroupPrincipal))
            return false;
        SolarisNumericGroupPrincipal that = (SolarisNumericGroupPrincipal)o;

        if (this.getName().equals(that.getName()) &&
            this.isPrimaryGroup() == that.isPrimaryGroup())
            return true;
        return false;
    }

    /**
     * Return a hash code for this <code>SolarisNumericGroupPrincipal</code>.
     *
     * <p>
     *
     * <p>
     * 
     * 
     * @return a hash code for this <code>SolarisNumericGroupPrincipal</code>.
     */
    public int hashCode() {
        return toString().hashCode();
    }
}
