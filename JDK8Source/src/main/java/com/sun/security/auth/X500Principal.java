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
import sun.security.x509.X500Name;

/**
 * <p> This class represents an X.500 <code>Principal</code>.
 * X500Principals have names such as,
 * "CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US"
 * (RFC 1779 style).
 *
 * <p> Principals such as this <code>X500Principal</code>
 * may be associated with a particular <code>Subject</code>
 * to augment that <code>Subject</code> with an additional
 * identity.  Refer to the <code>Subject</code> class for more information
 * on how to achieve this.  Authorization decisions can then be based upon
 * the Principals associated with a <code>Subject</code>.
 *
 * <p>
 *  <p>此类表示X.500 <code> Principal </code>。
 *  X500Principals具有诸如"CN = Duke,OU = JavaSoft,O = Sun Microsystems,C = US"(RFC 1779风格)的名称。
 * 
 *  <p>诸如此<code> X500Principal </code>之类的主体可能与特定的<code> Subject </code>相关联,以增加具有附加标识的<code> Subject </code>
 * 。
 * 有关如何实现这一点的更多信息,请参阅<code> Subject </code>类。授权决定可以基于与<code> Subject </code>相关联的主体。
 * 
 * 
 * @see java.security.Principal
 * @see javax.security.auth.Subject
 * @deprecated A new X500Principal class is available in the Java platform.
 *             This X500Principal classs is entirely deprecated and
 *             is here to allow for a smooth transition to the new
 *             class.
 * @see javax.security.auth.x500.X500Principal
*/
@jdk.Exported(false)
@Deprecated
public class X500Principal implements Principal, java.io.Serializable {

    private static final long serialVersionUID = -8222422609431628648L;

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

    transient private X500Name thisX500Name;

    /**
     * Create a X500Principal with an X.500 Name,
     * such as "CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US"
     * (RFC 1779 style).
     *
     * <p>
     *
     * <p>
     *  创建具有X.500名称的X500Principal,例如"CN = Duke,OU = JavaSoft,O = Sun Microsystems,C = US"(RFC 1779样式)。
     * 
     * <p>
     * 
     * 
     * @param name the X.500 name
     *
     * @exception NullPointerException if the <code>name</code>
     *                  is <code>null</code>. <p>
     *
     * @exception IllegalArgumentException if the <code>name</code>
     *                  is improperly specified.
     */
    public X500Principal(String name) {
        if (name == null)
            throw new NullPointerException(rb.getString("provided.null.name"));

        try {
            thisX500Name = new X500Name(name);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.toString());
        }

        this.name = name;
    }

    /**
     * Return the Unix username for this <code>X500Principal</code>.
     *
     * <p>
     *
     * <p>
     *  返回此<code> X500Principal </code>的Unix用户名。
     * 
     * <p>
     * 
     * 
     * @return the Unix username for this <code>X500Principal</code>
     */
    public String getName() {
        return thisX500Name.getName();
    }

    /**
     * Return a string representation of this <code>X500Principal</code>.
     *
     * <p>
     *
     * <p>
     *  返回此<code> X500Principal </code>的字符串表示形式。
     * 
     * <p>
     * 
     * 
     * @return a string representation of this <code>X500Principal</code>.
     */
    public String toString() {
        return thisX500Name.toString();
    }

    /**
     * Compares the specified Object with this <code>X500Principal</code>
     * for equality.
     *
     * <p>
     *
     * <p>
     *  将指定的对象与此<code> X500Principal </code>进行比较以确保相等。
     * 
     * <p>
     * 
     * 
     * @param o Object to be compared for equality with this
     *          <code>X500Principal</code>.
     *
     * @return true if the specified Object is equal equal to this
     *          <code>X500Principal</code>.
     */
    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (this == o)
            return true;

        if (o instanceof X500Principal) {
            X500Principal that = (X500Principal)o;
            try {
                X500Name thatX500Name = new X500Name(that.getName());
                return thisX500Name.equals(thatX500Name);
            } catch (Exception e) {
                // any parsing exceptions, return false
                return false;
            }
        } else if (o instanceof Principal) {
            // this will return 'true' if 'o' is a sun.security.x509.X500Name
            // and the X500Names are equal
            return o.equals(thisX500Name);
        }

        return false;
    }

    /**
     * Return a hash code for this <code>X500Principal</code>.
     *
     * <p>
     *
     * <p>
     *  返回此<code> X500Principal </code>的哈希码。
     * 
     * <p>
     * 
     * 
     * @return a hash code for this <code>X500Principal</code>.
     */
    public int hashCode() {
        return thisX500Name.hashCode();
    }

    /**
     * Reads this object from a stream (i.e., deserializes it)
     * <p>
     */
    private void readObject(java.io.ObjectInputStream s) throws
                                        java.io.IOException,
                                        java.io.NotActiveException,
                                        ClassNotFoundException {

        s.defaultReadObject();

        // re-create thisX500Name
        thisX500Name = new X500Name(name);
    }
}
