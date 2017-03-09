/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
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


package javax.management.remote;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.security.Principal;

/**
 * <p>The identity of a remote client of the JMX Remote API.</p>
 *
 * <p>Principals such as this <code>JMXPrincipal</code>
 * may be associated with a particular <code>Subject</code>
 * to augment that <code>Subject</code> with an additional
 * identity.  Refer to the {@link javax.security.auth.Subject}
 * class for more information on how to achieve this.
 * Authorization decisions can then be based upon
 * the Principals associated with a <code>Subject</code>.
 *
 * <p>
 *  <p> JMX远程API的远程客户端的身份。</p>
 * 
 *  <p>这个<code> JMXPrincipal </code>的主体可以与特定的<code> Subject </code>相关联,以增加具有额外标识的<code> Subject </code>。
 * 有关如何实现这一点的更多信息,请参阅{@link javax.security.auth.Subject}类。授权决定可以基于与<code> Subject </code>相关联的主体。
 * 
 * 
 * @see java.security.Principal
 * @see javax.security.auth.Subject
 * @since 1.5
 */
public class JMXPrincipal implements Principal, Serializable {

    private static final long serialVersionUID = -4184480100214577411L;

    /**
    /* <p>
    /* 
     * @serial The JMX Remote API name for the identity represented by
     * this <code>JMXPrincipal</code> object.
     * @see #getName()
     */
    private String name;

    /**
     * <p>Creates a JMXPrincipal for a given identity.</p>
     *
     * <p>
     *  <p>为给定的身份创建JMXPrincipal。</p>
     * 
     * 
     * @param name the JMX Remote API name for this identity.
     *
     * @exception NullPointerException if the <code>name</code> is
     * <code>null</code>.
     */
    public JMXPrincipal(String name) {
        validate(name);
        this.name = name;
    }

    /**
     * Returns the name of this principal.
     *
     * <p>
     *
     * <p>
     *  返回此主体的名称。
     * 
     * <p>
     * 
     * 
     * @return the name of this <code>JMXPrincipal</code>.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a string representation of this <code>JMXPrincipal</code>.
     *
     * <p>
     *
     * <p>
     *  返回此<code> JMXPrincipal </code>的字符串表示形式。
     * 
     * <p>
     * 
     * 
     * @return a string representation of this <code>JMXPrincipal</code>.
     */
    public String toString() {
        return("JMXPrincipal:  " + name);
    }

    /**
     * Compares the specified Object with this <code>JMXPrincipal</code>
     * for equality.  Returns true if the given object is also a
     * <code>JMXPrincipal</code> and the two JMXPrincipals
     * have the same name.
     *
     * <p>
     *
     * <p>
     *  将指定的对象与此<code> JMXPrincipal </code>进行比较以实现相等。
     * 如果给定对象也是一个<code> JMXPrincipal </code>,并且两个JMXPrincipals具有相同的名称,则返回true。
     * 
     * <p>
     * 
     * 
     * @param o Object to be compared for equality with this
     * <code>JMXPrincipal</code>.
     *
     * @return true if the specified Object is equal to this
     * <code>JMXPrincipal</code>.
     */
    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (this == o)
            return true;

        if (!(o instanceof JMXPrincipal))
            return false;
        JMXPrincipal that = (JMXPrincipal)o;

        return (this.getName().equals(that.getName()));
    }

    /**
     * Returns a hash code for this <code>JMXPrincipal</code>.
     *
     * <p>
     *
     * <p>
     * 
     * @return a hash code for this <code>JMXPrincipal</code>.
     */
    public int hashCode() {
        return name.hashCode();
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ObjectInputStream.GetField gf = ois.readFields();
        String principalName = (String)gf.get("name", null);
        try {
            validate(principalName);
            this.name = principalName;
        } catch (NullPointerException e) {
            throw new InvalidObjectException(e.getMessage());
        }
    }

    private static void validate(String name) throws NullPointerException {
        if (name == null)
            throw new NullPointerException("illegal null input");
    }
}
