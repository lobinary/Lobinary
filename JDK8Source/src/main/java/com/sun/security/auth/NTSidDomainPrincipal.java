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
 * and represents a Windows NT user's domain SID.
 *
 * <p> An NT user only has a domain SID if in fact they are logged
 * into an NT domain.  If the user is logged into a workgroup or
 * just a standalone configuration, they will NOT have a domain SID.
 *
 * <p> Principals such as this <code>NTSidDomainPrincipal</code>
 * may be associated with a particular <code>Subject</code>
 * to augment that <code>Subject</code> with an additional
 * identity.  Refer to the <code>Subject</code> class for more information
 * on how to achieve this.  Authorization decisions can then be based upon
 * the Principals associated with a <code>Subject</code>.
 *
 * <p>
 *  <p>此类扩展了<code> NTSid </code>,表示Windows NT用户的域SID。
 * 
 *  <p> NT用户只有一个域SID,如果事实上他们登录到NT域。如果用户登录到工作组或只是独立配置,则他们不会有域SID。
 * 
 *  <p>诸如此<code> NTSidDomainPrincipal </code>之类的主体可以与特定的<code> Subject </code>相关联以增加具有附加标识的<code> Subjec
 * t </code>。
 * 有关如何实现这一点的更多信息,请参阅<code> Subject </code>类。授权决定可以基于与<code> Subject </code>相关联的主体。
 * 
 * 
 * @see java.security.Principal
 * @see javax.security.auth.Subject
 */
@jdk.Exported
public class NTSidDomainPrincipal extends NTSid {

    private static final long serialVersionUID = 5247810785821650912L;

    /**
     * Create an <code>NTSidDomainPrincipal</code> with a Windows NT SID.
     *
     * <p>
     *
     * <p>
     *  使用Windows NT SID创建<code> NTSidDomainPrincipal </code>。
     * 
     * <p>
     * 
     * 
     * @param name a string version of the Windows NT SID for this
     *                  user's domain.<p>
     *
     * @exception NullPointerException if the <code>name</code>
     *                  is <code>null</code>.
     */
    public NTSidDomainPrincipal(String name) {
        super(name);
    }

    /**
     * Return a string representation of this <code>NTSidDomainPrincipal</code>.
     *
     * <p>
     *
     * <p>
     *  返回此<code> NTSidDomainPrincipal </code>的字符串表示形式。
     * 
     * <p>
     * 
     * 
     * @return a string representation of this
     *          <code>NTSidDomainPrincipal</code>.
     */
    public String toString() {
        java.text.MessageFormat form = new java.text.MessageFormat
                (sun.security.util.ResourcesMgr.getString
                        ("NTSidDomainPrincipal.name",
                        "sun.security.util.AuthResources"));
        Object[] source = {getName()};
        return form.format(source);
    }

    /**
     * Compares the specified Object with this <code>NTSidDomainPrincipal</code>
     * for equality.  Returns true if the given object is also a
     * <code>NTSidDomainPrincipal</code> and the two NTSidDomainPrincipals
     * have the same SID.
     *
     * <p>
     *
     * <p>
     *  将指定的对象与此<code> NTSidDomainPrincipal </code>比较以实现相等。
     * 如果给定对象也是<code> NTSidDomainPrincipal </code>,并且两个NTSidDomainPrincipals具有相同的SID,则返回true。
     * 
     * 
     * @param o Object to be compared for equality with this
     *          <code>NTSidDomainPrincipal</code>.
     *
     * @return true if the specified Object is equal equal to this
     *          <code>NTSidDomainPrincipal</code>.
     */
    public boolean equals(Object o) {
            if (o == null)
                return false;

        if (this == o)
            return true;

        if (!(o instanceof NTSidDomainPrincipal))
            return false;

        return super.equals(o);
    }
}
