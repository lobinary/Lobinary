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
 * <p> This class abstracts an NT security token
 * and provides a mechanism to do same-process security impersonation.
 *
 * <p>
 *  <p>此类抽象了NT安全令牌,并提供了一种进行相同进程安全模拟的机制。
 * 
 */

@jdk.Exported
public class NTNumericCredential {

    private long impersonationToken;

    /**
     * Create an <code>NTNumericCredential</code> with an integer value.
     *
     * <p>
     *
     * <p>
     *  使用整数值创建一个<code> NTNumericCredential </code>。
     * 
     * <p>
     * 
     * 
     * @param token the Windows NT security token for this user. <p>
     *
     */
    public NTNumericCredential(long token) {
        this.impersonationToken = token;
    }

    /**
     * Return an integer representation of this
     * <code>NTNumericCredential</code>.
     *
     * <p>
     *
     * <p>
     *  返回此<code> NTNumericCredential </code>的整数表示形式。
     * 
     * <p>
     * 
     * 
     * @return an integer representation of this
     *          <code>NTNumericCredential</code>.
     */
    public long getToken() {
        return impersonationToken;
    }

    /**
     * Return a string representation of this <code>NTNumericCredential</code>.
     *
     * <p>
     *
     * <p>
     *  返回此<code> NTNumericCredential </code>的字符串表示形式。
     * 
     * <p>
     * 
     * 
     * @return a string representation of this <code>NTNumericCredential</code>.
     */
    public String toString() {
        java.text.MessageFormat form = new java.text.MessageFormat
                (sun.security.util.ResourcesMgr.getString
                        ("NTNumericCredential.name",
                        "sun.security.util.AuthResources"));
        Object[] source = {Long.toString(impersonationToken)};
        return form.format(source);
    }

    /**
     * Compares the specified Object with this <code>NTNumericCredential</code>
     * for equality.  Returns true if the given object is also a
     * <code>NTNumericCredential</code> and the two NTNumericCredentials
     * represent the same NT security token.
     *
     * <p>
     *
     * <p>
     *  将指定的对象与此<code> NTNumericCredential </code>进行比较以实现相等。
     * 如果给定对象也是<code> NTNumericCredential </code>,并且两个NTNumericCredentials表示相同的NT安全性令牌,则返回true。
     * 
     * <p>
     * 
     * 
     * @param o Object to be compared for equality with this
     *          <code>NTNumericCredential</code>.
     *
     * @return true if the specified Object is equal equal to this
     *          <code>NTNumericCredential</code>.
     */
    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (this == o)
            return true;

        if (!(o instanceof NTNumericCredential))
            return false;
        NTNumericCredential that = (NTNumericCredential)o;

        if (impersonationToken == that.getToken())
            return true;
        return false;
    }

    /**
     * Return a hash code for this <code>NTNumericCredential</code>.
     *
     * <p>
     *
     * <p>
     * 
     * @return a hash code for this <code>NTNumericCredential</code>.
     */
    public int hashCode() {
        return (int)this.impersonationToken;
    }
}
