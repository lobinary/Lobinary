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
 * An object that implements the <code>java.security.Principal</code>
 * interface typically also implements this interface to provide
 * a means for comparing that object to a specified <code>Subject</code>.
 *
 * <p> The comparison is achieved via the <code>implies</code> method.
 * The implementation of the <code>implies</code> method determines
 * whether this object "implies" the specified <code>Subject</code>.
 * One example application of this method may be for
 * a "group" object to imply a particular <code>Subject</code>
 * if that <code>Subject</code> belongs to the group.
 * Another example application of this method would be for
 * "role" object to imply a particular <code>Subject</code>
 * if that <code>Subject</code> is currently acting in that role.
 *
 * <p> Although classes that implement this interface typically
 * also implement the <code>java.security.Principal</code> interface,
 * it is not required.  In other words, classes may implement the
 * <code>java.security.Principal</code> interface by itself,
 * the <code>PrincipalComparator</code> interface by itself,
 * or both at the same time.
 *
 * <p>
 *  实现<code> java.security.Principal </code>接口的对象通常还实现此接口,以提供将该对象与指定的<code> Subject </code>进行比较的方法。
 * 
 *  <p>比较是通过<code> implicate </code>方法实现的。实现<code>意味着</code>方法确定这个对象是否"暗示"指定的<code> Subject </code>。
 * 如果<code> Subject </code>属于该组,则该方法的一个示例应用可以是用于"组"对象来暗示特定的<code> Subject </code>。
 * 该方法的另一个示例应用是"角色"对象,如果<code> Subject </code>当前在该角色中操作,则暗示特定的<code> Subject </code>。
 * 
 *  <p>虽然实现此接口的类通常也实现<code> java.security.Principal </code>接口,但不是必需的。
 * 
 * @see java.security.Principal
 * @see javax.security.auth.Subject
 */
@jdk.Exported
public interface PrincipalComparator {
    /**
     * Check if the specified <code>Subject</code> is implied by
     * this object.
     *
     * <p>
     *
     * <p>
     * 换句话说,类可以单独实现<code> java.security.Principal </code>接口,也可以同时实现<code> PrincipalComparator </code>接口或两者。
     * 
     * 
     * @return true if the specified <code>Subject</code> is implied by
     *          this object, or false otherwise.
     */
    boolean implies(javax.security.auth.Subject subject);
}
