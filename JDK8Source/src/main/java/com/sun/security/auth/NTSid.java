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
 * and represents information about a Windows NT user, group or realm.
 *
 * <p> Windows NT chooses to represent users, groups and realms (or domains)
 * with not only common names, but also relatively unique numbers.  These
 * numbers are called Security IDentifiers, or SIDs.  Windows NT
 * also provides services that render these SIDs into string forms.
 * This class represents these string forms.
 *
 * <p> Principals such as this <code>NTSid</code>
 * may be associated with a particular <code>Subject</code>
 * to augment that <code>Subject</code> with an additional
 * identity.  Refer to the <code>Subject</code> class for more information
 * on how to achieve this.  Authorization decisions can then be based upon
 * the Principals associated with a <code>Subject</code>.
 *
 * <p>
 *  <p>此类实现<code> Principal </code>接口,并表示有关Windows NT用户,组或领域的信息。
 * 
 *  <p> Windows NT选择代表使用者,群组和领域(或网域),不仅具有通用名称,而且具有相对唯一的数字。这些数字称为安全标识符或SID。
 *  Windows NT还提供将这些SID呈现为字符串形式的服务。这个类表示这些字符串形式。
 * 
 *  <p>诸如此<code> NTSid </code>之类的主体可以与特定的<code> Subject </code>相关联以增加具有附加标识的<code> Subject </code>。
 * 有关如何实现这一点的更多信息,请参阅<code> Subject </code>类。授权决定可以基于与<code> Subject </code>相关联的主体。
 * 
 * 
 * @see java.security.Principal
 * @see javax.security.auth.Subject
 */
@jdk.Exported
public class NTSid implements Principal, java.io.Serializable {

    private static final long serialVersionUID = 4412290580770249885L;

    /**
    /* <p>
    /* 
     * @serial
     */
    private String sid;

    /**
     * Create an <code>NTSid</code> with a Windows NT SID.
     *
     * <p>
     *
     * <p>
     *  使用Windows NT SID创建<code> NTSid </code>。
     * 
     * <p>
     * 
     * 
     * @param stringSid the Windows NT SID. <p>
     *
     * @exception NullPointerException if the <code>String</code>
     *                  is <code>null</code>.
     *
     * @exception IllegalArgumentException if the <code>String</code>
     *                  has zero length.
     */
    public NTSid (String stringSid) {
        if (stringSid == null) {
            java.text.MessageFormat form = new java.text.MessageFormat
                (sun.security.util.ResourcesMgr.getString
                        ("invalid.null.input.value",
                        "sun.security.util.AuthResources"));
            Object[] source = {"stringSid"};
            throw new NullPointerException(form.format(source));
        }
        if (stringSid.length() == 0) {
            throw new IllegalArgumentException
                (sun.security.util.ResourcesMgr.getString
                        ("Invalid.NTSid.value",
                        "sun.security.util.AuthResources"));
        }
        sid = new String(stringSid);
    }

    /**
     * Return a string version of this <code>NTSid</code>.
     *
     * <p>
     *
     * <p>
     *  返回此<code> NTSid </code>的字符串版本。
     * 
     * <p>
     * 
     * 
     * @return a string version of this <code>NTSid</code>
     */
    public String getName() {
        return sid;
    }

    /**
     * Return a string representation of this <code>NTSid</code>.
     *
     * <p>
     *
     * <p>
     *  返回此<code> NTSid </code>的字符串表示形式。
     * 
     * <p>
     * 
     * 
     * @return a string representation of this <code>NTSid</code>.
     */
    public String toString() {
        java.text.MessageFormat form = new java.text.MessageFormat
                (sun.security.util.ResourcesMgr.getString
                        ("NTSid.name",
                        "sun.security.util.AuthResources"));
        Object[] source = {sid};
        return form.format(source);
    }

    /**
     * Compares the specified Object with this <code>NTSid</code>
     * for equality.  Returns true if the given object is also a
     * <code>NTSid</code> and the two NTSids have the same String
     * representation.
     *
     * <p>
     *
     * <p>
     *  将指定的对象与此<code> NTSid </code>进行比较以实现相等。
     * 如果给定对象也是一个<code> NTSid </code>,并且两个NTSids具有相同的String表示,则返回true。
     * 
     * <p>
     * 
     * 
     * @param o Object to be compared for equality with this
     *          <code>NTSid</code>.
     *
     * @return true if the specified Object is equal to this
     *          <code>NTSid</code>.
     */
    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (this == o)
            return true;

        if (!(o instanceof NTSid))
            return false;
        NTSid that = (NTSid)o;

        if (sid.equals(that.sid)) {
            return true;
        }
        return false;
    }

    /**
     * Return a hash code for this <code>NTSid</code>.
     *
     * <p>
     *
     * <p>
     * 
     * @return a hash code for this <code>NTSid</code>.
     */
    public int hashCode() {
        return sid.hashCode();
    }
}
