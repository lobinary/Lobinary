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
 * Interface representing an Access Control List (ACL).  An Access
 * Control List is a data structure used to guard access to
 * resources.<p>
 *
 * An ACL can be thought of as a data structure with multiple ACL
 * entries.  Each ACL entry, of interface type AclEntry, contains a
 * set of permissions associated with a particular principal. (A
 * principal represents an entity such as an individual user or a
 * group). Additionally, each ACL entry is specified as being either
 * positive or negative. If positive, the permissions are to be
 * granted to the associated principal. If negative, the permissions
 * are to be denied.<p>
 *
 * The ACL Entries in each ACL observe the following rules:
 *
 * <ul> <li>Each principal can have at most one positive ACL entry and
 * one negative entry; that is, multiple positive or negative ACL
 * entries are not allowed for any principal.  Each entry specifies
 * the set of permissions that are to be granted (if positive) or
 * denied (if negative).
 *
 * <li>If there is no entry for a particular principal, then the
 * principal is considered to have a null (empty) permission set.
 *
 * <li>If there is a positive entry that grants a principal a
 * particular permission, and a negative entry that denies the
 * principal the same permission, the result is as though the
 * permission was never granted or denied.
 *
 * <li>Individual permissions always override permissions of the
 * group(s) to which the individual belongs. That is, individual
 * negative permissions (specific denial of permissions) override the
 * groups' positive permissions. And individual positive permissions
 * override the groups' negative permissions.
 *
 * </ul>
 *
 * The {@code  java.security.acl } package provides the
 * interfaces to the ACL and related data structures (ACL entries,
 * groups, permissions, etc.), and the {@code  sun.security.acl }
 * classes provide a default implementation of the interfaces. For
 * example, {@code  java.security.acl.Acl } provides the
 * interface to an ACL and the {@code  sun.security.acl.AclImpl }
 * class provides the default implementation of the interface.<p>
 *
 * The {@code  java.security.acl.Acl } interface extends the
 * {@code  java.security.acl.Owner } interface. The Owner
 * interface is used to maintain a list of owners for each ACL.  Only
 * owners are allowed to modify an ACL. For example, only an owner can
 * call the ACL's {@code addEntry} method to add a new ACL entry
 * to the ACL.
 *
 * <p>
 *  表示访问控制列表(ACL)的接口。访问控制列表是用于保护对资源的访问的数据结构。<p>
 * 
 *  ACL可以被认为是具有多个ACL条目的数据结构。每个ACL条目,接口类型AclEntry,包含与特定主体关联的一组权限。 (主体表示诸如单个用户或组的实体)。另外,每个ACL条目被指定为正或负。
 * 如果为正,则将权限授予相关联的主体。如果为负,则会拒绝权限。<p>。
 * 
 *  每个ACL中的ACL条目遵守以下规则：
 * 
 *  <ul> <li>每个主体最多可以有一个正ACL条目和一个负条目;也就是说,不允许任何主体使用多个正或负ACL条目。每个条目指定要授予的权限集(如果为肯定)或拒绝(如果为否定)。
 * 
 *  <li>如果没有特定主体的条目,则认为主体具有空(空)权限集。
 * 
 *  <li>如果有一个肯定的条目授予一个主体一个特定的权限,一个否定条目拒绝主体相同的权限,结果就好像该权限从未被授予或拒绝。
 * 
 * <li>个人权限始终会覆盖个人所属的组的权限。也就是说,个人否定权限(特定拒绝权限)会覆盖组的正权限。而个人的正权限将覆盖组的负权限。
 * 
 * </ul>
 * 
 *  {@code java.security.acl}包提供了ACL和相关数据结构(ACL条目,组,权限等)的接口,{@code sun.security.acl}类提供了一个默认实现接口。
 * 例如,{@code java.security.acl.Acl}提供了ACL的接口,{@code sun.security.acl.AclImpl}类提供了接口的默认实现。<p>。
 * 
 *  {@code java.security.acl.Acl}界面扩展了{@code java.security.acl.Owner}界面。所有者接口用于维护每个ACL的所有者列表。
 * 只有所有者可以修改ACL。例如,只有所有者可以调用ACL的{@code addEntry}方法向ACL添加新的ACL条目。
 * 
 * 
 * @see java.security.acl.AclEntry
 * @see java.security.acl.Owner
 * @see java.security.acl.Acl#getPermissions
 *
 * @author Satish Dharmaraj
 */

public interface Acl extends Owner {

    /**
     * Sets the name of this ACL.
     *
     * <p>
     *  设置此ACL的名称。
     * 
     * 
     * @param caller the principal invoking this method. It must be an
     * owner of this ACL.
     *
     * @param name the name to be given to this ACL.
     *
     * @exception NotOwnerException if the caller principal
     * is not an owner of this ACL.
     *
     * @see #getName
     */
    public void setName(Principal caller, String name)
      throws NotOwnerException;

    /**
     * Returns the name of this ACL.
     *
     * <p>
     *  返回此ACL的名称。
     * 
     * 
     * @return the name of this ACL.
     *
     * @see #setName
     */
    public String getName();

    /**
     * Adds an ACL entry to this ACL. An entry associates a principal
     * (e.g., an individual or a group) with a set of
     * permissions. Each principal can have at most one positive ACL
     * entry (specifying permissions to be granted to the principal)
     * and one negative ACL entry (specifying permissions to be
     * denied). If there is already an ACL entry of the same type
     * (negative or positive) already in the ACL, false is returned.
     *
     * <p>
     * 向此ACL添加ACL条目。条目将主体(例如,个人或组)与一组权限相关联。每个主体最多只能有一个正ACL条目(指定授予主体的权限)和一个否定ACL条目(指定要拒绝的权限)。
     * 如果ACL中已经存在相同类型(否定或肯定)的ACL条目,则返回false。
     * 
     * 
     * @param caller the principal invoking this method. It must be an
     * owner of this ACL.
     *
     * @param entry the ACL entry to be added to this ACL.
     *
     * @return true on success, false if an entry of the same type
     * (positive or negative) for the same principal is already
     * present in this ACL.
     *
     * @exception NotOwnerException if the caller principal
     *  is not an owner of this ACL.
     */
    public boolean addEntry(Principal caller, AclEntry entry)
      throws NotOwnerException;

    /**
     * Removes an ACL entry from this ACL.
     *
     * <p>
     *  从此ACL中删除ACL条目。
     * 
     * 
     * @param caller the principal invoking this method. It must be an
     * owner of this ACL.
     *
     * @param entry the ACL entry to be removed from this ACL.
     *
     * @return true on success, false if the entry is not part of this ACL.
     *
     * @exception NotOwnerException if the caller principal is not
     * an owner of this Acl.
     */
    public boolean removeEntry(Principal caller, AclEntry entry)
          throws NotOwnerException;

    /**
     * Returns an enumeration for the set of allowed permissions for the
     * specified principal (representing an entity such as an individual or
     * a group). This set of allowed permissions is calculated as
     * follows:
     *
     * <ul>
     *
     * <li>If there is no entry in this Access Control List for the
     * specified principal, an empty permission set is returned.
     *
     * <li>Otherwise, the principal's group permission sets are determined.
     * (A principal can belong to one or more groups, where a group is a
     * group of principals, represented by the Group interface.)
     * The group positive permission set is the union of all
     * the positive permissions of each group that the principal belongs to.
     * The group negative permission set is the union of all
     * the negative permissions of each group that the principal belongs to.
     * If there is a specific permission that occurs in both
     * the positive permission set and the negative permission set,
     * it is removed from both.<p>
     *
     * The individual positive and negative permission sets are also
     * determined. The positive permission set contains the permissions
     * specified in the positive ACL entry (if any) for the principal.
     * Similarly, the negative permission set contains the permissions
     * specified in the negative ACL entry (if any) for the principal.
     * The individual positive (or negative) permission set is considered
     * to be null if there is not a positive (negative) ACL entry for the
     * principal in this ACL.<p>
     *
     * The set of permissions granted to the principal is then calculated
     * using the simple rule that individual permissions always override
     * the group permissions. That is, the principal's individual negative
     * permission set (specific denial of permissions) overrides the group
     * positive permission set, and the principal's individual positive
     * permission set overrides the group negative permission set.
     *
     * </ul>
     *
     * <p>
     *  返回指定主体(表示诸如个人或组的实体)的允许权限集的枚举。此组允许的权限计算如下：
     * 
     * <ul>
     * 
     *  <li>如果此访问控制列表中没有指定主体的条目,则返回空权限集。
     * 
     *  <li>否则,将确定主体的组权限集。 (主体可以属于一个或多个组,其中组是一组主体,由Group接口表示)。组正权限集是主体所属的每个组的所有肯定权限的并集。
     * 组否定权限集是主体所属的每个组的所有否定权限的并集。如果在正权限集和负权限集中都存在特定权限,则会从两个权限集中删除它们。<p>。
     * 
     * 还确定各个正和负权限集。正权限集包含主体的正ACL条目(如果有)中指定的权限。类似地,否定权限集包含主体的否定ACL条目(如果有)中指定的权限。
     * 如果此ACL中的主体没有正(负)ACL条目,则单个正(或负)权限集将被视为null。<p>。
     * 
     *  然后使用简单规则计算授予主体的一组权限,即单个权限始终覆盖组权限。也就是说,委托人的个人负面权限集(特定拒绝权限)覆盖了组正面权限集,委托人的个人正面权限集覆盖了组负面权限集。
     * 
     * 
     * @param user the principal whose permission set is to be returned.
     *
     * @return the permission set specifying the permissions the principal
     * is allowed.
     */
    public Enumeration<Permission> getPermissions(Principal user);

    /**
     * Returns an enumeration of the entries in this ACL. Each element in
     * the enumeration is of type AclEntry.
     *
     * <p>
     * </ul>
     * 
     * 
     * @return an enumeration of the entries in this ACL.
     */
    public Enumeration<AclEntry> entries();

    /**
     * Checks whether or not the specified principal has the specified
     * permission. If it does, true is returned, otherwise false is returned.
     *
     * More specifically, this method checks whether the passed permission
     * is a member of the allowed permission set of the specified principal.
     * The allowed permission set is determined by the same algorithm as is
     * used by the {@code getPermissions} method.
     *
     * <p>
     *  返回此ACL中条目的枚举。枚举中的每个元素都是AclEntry类型。
     * 
     * 
     * @param principal the principal, assumed to be a valid authenticated
     * Principal.
     *
     * @param permission the permission to be checked for.
     *
     * @return true if the principal has the specified permission, false
     * otherwise.
     *
     * @see #getPermissions
     */
    public boolean checkPermission(Principal principal, Permission permission);

    /**
     * Returns a string representation of the
     * ACL contents.
     *
     * <p>
     *  检查指定的主体是否具有指定的权限。如果是,返回true,否则返回false。
     * 
     *  更具体地,该方法检查所传递的许可权是否是指定主体的允许的许可权集合的成员。允许的权限集由{@code getPermissions}方法使用的算法确定。
     * 
     * 
     * @return a string representation of the ACL contents.
     */
    public String toString();
}
