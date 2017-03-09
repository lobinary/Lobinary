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

package java.security.acl;

import java.util.Enumeration;
import java.security.Principal;

/**
 * This is the interface used for representing one entry in an Access
 * Control List (ACL).<p>
 *
 * An ACL can be thought of as a data structure with multiple ACL entry
 * objects. Each ACL entry object contains a set of permissions associated
 * with a particular principal. (A principal represents an entity such as
 * an individual user or a group). Additionally, each ACL entry is specified
 * as being either positive or negative. If positive, the permissions are
 * to be granted to the associated principal. If negative, the permissions
 * are to be denied. Each principal can have at most one positive ACL entry
 * and one negative entry; that is, multiple positive or negative ACL
 * entries are not allowed for any principal.
 *
 * Note: ACL entries are by default positive. An entry becomes a
 * negative entry only if the
 * {@link #setNegativePermissions() setNegativePermissions}
 * method is called on it.
 *
 * <p>
 *  这是用于表示访问控制列表(ACL)中的一个条目的接口。<p>
 * 
 *  ACL可以被认为是具有多个ACL条目对象的数据结构。每个ACL条目对象包含与特定主体关联的一组权限。 (主体表示诸如单个用户或组的实体)。另外,每个ACL条目被指定为正或负。
 * 如果为正,则将权限授予相关联的主体。如果为负,则拒绝权限。每个主体最多可以有一个正ACL条目和一个负条目;也就是说,不允许任何主体使用多个正或负ACL条目。
 * 
 *  注意：ACL条目默认为正。只有在调用{@link #setNegativePermissions()setNegativePermissions}方法时,条目才会变为否定条目。
 * 
 * 
 * @see java.security.acl.Acl
 *
 * @author      Satish Dharmaraj
 */
public interface AclEntry extends Cloneable {

    /**
     * Specifies the principal for which permissions are granted or denied
     * by this ACL entry. If a principal was already set for this ACL entry,
     * false is returned, otherwise true is returned.
     *
     * <p>
     *  指定此ACL条目授予或拒绝许可权的主体。如果已为此ACL条目设置了主体,则返回false,否则返回true。
     * 
     * 
     * @param user the principal to be set for this entry.
     *
     * @return true if the principal is set, false if there was
     * already a principal set for this entry.
     *
     * @see #getPrincipal
     */
    public boolean setPrincipal(Principal user);

    /**
     * Returns the principal for which permissions are granted or denied by
     * this ACL entry. Returns null if there is no principal set for this
     * entry yet.
     *
     * <p>
     *  返回此ACL条目授予或拒绝权限的主体。如果此条目没有主体集,则返回null。
     * 
     * 
     * @return the principal associated with this entry.
     *
     * @see #setPrincipal
     */
    public Principal getPrincipal();

    /**
     * Sets this ACL entry to be a negative one. That is, the associated
     * principal (e.g., a user or a group) will be denied the permission set
     * specified in the entry.
     *
     * Note: ACL entries are by default positive. An entry becomes a
     * negative entry only if this {@code setNegativePermissions}
     * method is called on it.
     * <p>
     * 将此ACL条目设置为负值。也就是说,相关主体(例如,用户或组)将被拒绝在条目中指定的权限集。
     * 
     *  注意：ACL条目默认为正。只有在调用此{@code setNegativePermissions}方法时,条目才会变为否定条目。
     * 
     */
    public void setNegativePermissions();

    /**
     * Returns true if this is a negative ACL entry (one denying the
     * associated principal the set of permissions in the entry), false
     * otherwise.
     *
     * <p>
     *  如果这是一个否定ACL条目(一个拒绝相关主体的条目中的权限集),则返回true,否则返回false。
     * 
     * 
     * @return true if this is a negative ACL entry, false if it's not.
     */
    public boolean isNegative();

    /**
     * Adds the specified permission to this ACL entry. Note: An entry can
     * have multiple permissions.
     *
     * <p>
     *  将指定的权限添加到此ACL条目。注意：条目可以有多个权限。
     * 
     * 
     * @param permission the permission to be associated with
     * the principal in this entry.
     *
     * @return true if the permission was added, false if the
     * permission was already part of this entry's permission set.
     */
    public boolean addPermission(Permission permission);

    /**
     * Removes the specified permission from this ACL entry.
     *
     * <p>
     *  从此ACL条目中删除指定的权限。
     * 
     * 
     * @param permission the permission to be removed from this entry.
     *
     * @return true if the permission is removed, false if the
     * permission was not part of this entry's permission set.
     */
    public boolean removePermission(Permission permission);

    /**
     * Checks if the specified permission is part of the
     * permission set in this entry.
     *
     * <p>
     *  检查指定的权限是否是此条目中设置的权限的一部分。
     * 
     * 
     * @param permission the permission to be checked for.
     *
     * @return true if the permission is part of the
     * permission set in this entry, false otherwise.
     */
    public boolean checkPermission(Permission permission);

    /**
     * Returns an enumeration of the permissions in this ACL entry.
     *
     * <p>
     *  返回此ACL条目中的权限的枚举。
     * 
     * 
     * @return an enumeration of the permissions in this ACL entry.
     */
    public Enumeration<Permission> permissions();

    /**
     * Returns a string representation of the contents of this ACL entry.
     *
     * <p>
     *  返回此ACL条目的内容的字符串表示形式。
     * 
     * 
     * @return a string representation of the contents.
     */
    public String toString();

    /**
     * Clones this ACL entry.
     *
     * <p>
     *  克隆此ACL条目。
     * 
     * @return a clone of this ACL entry.
     */
    public Object clone();
}
