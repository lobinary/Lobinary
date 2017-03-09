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
 * and represents one of the groups to which a Windows NT user belongs.
 *
 * <p> Principals such as this <code>NTSidGroupPrincipal</code>
 * may be associated with a particular <code>Subject</code>
 * to augment that <code>Subject</code> with an additional
 * identity.  Refer to the <code>Subject</code> class for more information
 * on how to achieve this.  Authorization decisions can then be based upon
 * the Principals associated with a <code>Subject</code>.
 *
 * <p>
 *  <p>此类扩展了<code> NTSid </code>,表示Windows NT用户所属的组之一。
 * 
 *  <p>这样的<code> NTSidGroupPrincipal </code>之类的主体可以与特定的<code> Subject </code>相关联以增加具有附加标识的<code> Subject
 *  </code>。
 * 有关如何实现这一点的更多信息,请参阅<code> Subject </code>类。授权决定可以基于与<code> Subject </code>相关联的主体。
 * 
 * 
 * @see java.security.Principal
 * @see javax.security.auth.Subject
 * @see com.sun.security.auth.NTSid
 */
@jdk.Exported
public class NTSidGroupPrincipal extends NTSid {

    private static final long serialVersionUID = -1373347438636198229L;

    /**
     * Create an <code>NTSidGroupPrincipal</code> with a Windows NT group name.
     *
     * <p>
     *
     * <p>
     *  使用Windows NT组名创建<code> NTSidGroupPrincipal </code>。
     * 
     * <p>
     * 
     * 
     * @param name the Windows NT group SID for this user. <p>
     *
     * @exception NullPointerException if the <code>name</code>
     *                  is <code>null</code>.
     */
    public NTSidGroupPrincipal(String name) {
        super(name);
    }

    /**
     * Return a string representation of this <code>NTSidGroupPrincipal</code>.
     *
     * <p>
     *
     * <p>
     *  返回此<code> NTSidGroupPrincipal </code>的字符串表示形式。
     * 
     * <p>
     * 
     * 
     * @return a string representation of this <code>NTSidGroupPrincipal</code>.
     */
    public String toString() {
        java.text.MessageFormat form = new java.text.MessageFormat
                (sun.security.util.ResourcesMgr.getString
                        ("NTSidGroupPrincipal.name",
                        "sun.security.util.AuthResources"));
        Object[] source = {getName()};
        return form.format(source);
    }

    /**
     * Compares the specified Object with this <code>NTSidGroupPrincipal</code>
     * for equality.  Returns true if the given object is also a
     * <code>NTSidGroupPrincipal</code> and the two NTSidGroupPrincipals
     * have the same SID.
     *
     * <p>
     *
     * <p>
     *  将指定的对象与此<code> NTSidGroupPrincipal </code>进行比较以实现相等。
     * 如果给定对象也是<code> NTSidGroupPrincipal </code>,并且两个NTSidGroupPrincipals具有相同的SID,则返回true。
     * 
     * 
     * @param o Object to be compared for equality with this
     *          <code>NTSidGroupPrincipal</code>.
     *
     * @return true if the specified Object is equal equal to this
     *          <code>NTSidGroupPrincipal</code>.
     */
    public boolean equals(Object o) {
            if (o == null)
                return false;

        if (this == o)
            return true;

        if (!(o instanceof NTSidGroupPrincipal))
            return false;

        return super.equals(o);
    }

}
