/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
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


package com.sun.jmx.snmp.IPAcl;



import java.security.Principal;
import java.security.acl.Acl;
import java.security.acl.AclEntry;
import java.security.acl.NotOwnerException;

import java.io.Serializable;
import java.security.acl.Permission;
import java.util.Vector;
import java.util.Enumeration;


/**
 * Represent an Access Control List (ACL) which is used to guard access to http adaptor.
 * <P>
 * It is a data structure with multiple ACL entries. Each ACL entry, of interface type
 * AclEntry, contains a set of permissions and a set of communities associated with a
 * particular principal. (A principal represents an entity such as a host or a group of host).
 * Additionally, each ACL entry is specified as being either positive or negative.
 * If positive, the permissions are to be granted to the associated principal.
 * If negative, the permissions are to be denied.
 *
 * <p>
 *  表示用于保护对http适配器的访问的访问控制列表(ACL)。
 * <P>
 *  它是一个具有多个ACL条目的数据结构。每个ACL条目,接口类型AclEntry,包含一组权限和与特定主体关联的一组社区。 (主体表示诸如主机或主机组的实体)。另外,每个ACL条目被指定为正或负。
 * 如果为正,则将权限授予相关联的主体。如果为负,则拒绝权限。
 * 
 * 
 * @see java.security.acl.Acl
 */

class AclImpl extends OwnerImpl implements Acl, Serializable {
  private static final long serialVersionUID = -2250957591085270029L;

  private Vector<AclEntry> entryList = null;
  private String aclName = null;

  /**
   * Constructs the ACL with a specified owner
   *
   * <p>
   *  构造具有指定所有者的ACL
   * 
   * 
   * @param owner owner of the ACL.
   * @param name  name of this ACL.
   */
  public AclImpl (PrincipalImpl owner, String name) {
        super(owner);
        entryList = new Vector<>();
        aclName = name;
  }

  /**
   * Sets the name of this ACL.
   *
   * <p>
   *  设置此ACL的名称。
   * 
   * 
   * @param caller the principal invoking this method. It must be an owner
   *        of this ACL.
   * @param name the name to be given to this ACL.
   *
   * @exception NotOwnerException if the caller principal is not an owner
   *            of this ACL.
   * @see java.security.Principal
   */
  @Override
  public void setName(Principal caller, String name)
        throws NotOwnerException {
          if (!isOwner(caller))
                throw new NotOwnerException();
          aclName = name;
  }

  /**
   * Returns the name of this ACL.
   *
   * <p>
   *  返回此ACL的名称。
   * 
   * 
   * @return the name of this ACL.
   */
  @Override
  public String getName(){
        return aclName;
  }

  /**
   * Adds an ACL entry to this ACL. An entry associates a principal (e.g., an individual or a group)
   * with a set of permissions. Each principal can have at most one positive ACL entry
   * (specifying permissions to be granted to the principal) and one negative ACL entry
   * (specifying permissions to be denied). If there is already an ACL entry
   * of the same type (negative or positive) already in the ACL, false is returned.
   *
   * <p>
   *  向此ACL添加ACL条目。条目将主体(例如,个人或组)与一组权限相关联。每个主体最多只能有一个正ACL条目(指定授予主体的权限)和一个否定ACL条目(指定要拒绝的权限)。
   * 如果ACL中已经存在相同类型(否定或肯定)的ACL条目,则返回false。
   * 
   * 
   * @param caller the principal invoking this method. It must be an owner
   *        of this ACL.
   * @param entry the ACL entry to be added to this ACL.
   * @return true on success, false if an entry of the same type (positive
   *       or negative) for the same principal is already present in this ACL.
   * @exception NotOwnerException if the caller principal is not an owner of
   *       this ACL.
   * @see java.security.Principal
   */
  @Override
  public boolean addEntry(Principal caller, AclEntry entry)
        throws NotOwnerException {
          if (!isOwner(caller))
                throw new NotOwnerException();

          if (entryList.contains(entry))
                return false;
          /*
                 for (Enumeration e = entryList.elements();e.hasMoreElements();){
                 AclEntry ent = (AclEntry) e.nextElement();
                 if (ent.getPrincipal().equals(entry.getPrincipal()))
                 return false;
                 }
          /* <p>
          /*  for(Enumeration e = entryList.elements(); e.hasMoreElements();){AclEntry ent =(AclEntry)e.nextElement(); if(ent.getPrincipal()。
          /* equals(entry.getPrincipal()))return false; }}。
          /* 
                 */

          entryList.addElement(entry);
          return true;
  }

  /**
   * Removes an ACL entry from this ACL.
   *
   * <p>
   *  从此ACL中删除ACL条目。
   * 
   * 
   * @param caller the principal invoking this method. It must be an owner
   *        of this ACL.
   * @param entry the ACL entry to be removed from this ACL.
   * @return true on success, false if the entry is not part of this ACL.
   * @exception NotOwnerException if the caller principal is not an owner
   *   of this Acl.
   * @see java.security.Principal
   * @see java.security.acl.AclEntry
   */
  @Override
  public boolean removeEntry(Principal caller, AclEntry entry)
        throws NotOwnerException {
          if (!isOwner(caller))
                throw new NotOwnerException();

          return (entryList.removeElement(entry));
  }

  /**
   * Removes all ACL entries from this ACL.
   *
   * <p>
   *  从此ACL中删除所有ACL条目。
   * 
   * 
   * @param caller the principal invoking this method. It must be an owner
   *        of this ACL.
   * @exception NotOwnerException if the caller principal is not an owner of
   *        this Acl.
   * @see java.security.Principal
   */
  public void removeAll(Principal caller)
        throws NotOwnerException {
          if (!isOwner(caller))
                throw new NotOwnerException();
        entryList.removeAllElements();
  }

  /**
   * Returns an enumeration for the set of allowed permissions for
   * the specified principal
   * (representing an entity such as an individual or a group).
   * This set of allowed permissions is calculated as follows:
   * <UL>
   * <LI>If there is no entry in this Access Control List for the specified
   * principal, an empty permission set is returned.</LI>
   * <LI>Otherwise, the principal's group permission sets are determined.
   * (A principal can belong to one or more groups, where a group is a group
   * of principals, represented by the Group interface.)</LI>
   * </UL>
   * <p>
   * 返回指定主体(表示诸如个人或组的实体)的允许权限集的枚举。此组允许的权限计算如下：
   * <UL>
   *  <LI>如果在此访问控制列表中没有指定主体的条目,则返回空权限集。</LI> <LI>否则,将确定主体的组权限集。 (主体可以属于一个或多个组,其中一个组是一组主体,由Group接口表示。
   * )</LI>。
   * </UL>
   * 
   * @param user the principal whose permission set is to be returned.
   * @return the permission set specifying the permissions the principal
   *     is allowed.
   * @see java.security.Principal
   */
  @Override
  public Enumeration<Permission> getPermissions(Principal user){
        Vector<Permission> empty = new Vector<>();
        for (Enumeration<AclEntry> e = entryList.elements();e.hasMoreElements();){
          AclEntry ent = e.nextElement();
          if (ent.getPrincipal().equals(user))
                return ent.permissions();
        }
        return empty.elements();
  }

  /**
   * Returns an enumeration of the entries in this ACL. Each element in the
   * enumeration is of type AclEntry.
   *
   * <p>
   *  返回此ACL中条目的枚举。枚举中的每个元素都是AclEntry类型。
   * 
   * 
   * @return an enumeration of the entries in this ACL.
   */
  @Override
  public Enumeration<AclEntry> entries(){
        return entryList.elements();
  }

  /**
   * Checks whether or not the specified principal has the specified
   * permission.
   * If it does, true is returned, otherwise false is returned.
   * More specifically, this method checks whether the passed permission
   * is a member of the allowed permission set of the specified principal.
   * The allowed permission set is determined by the same algorithm as is
   * used by the getPermissions method.
   *
   * <p>
   *  检查指定的主体是否具有指定的权限。如果是,返回true,否则返回false。更具体地,该方法检查所传递的许可权是否是指定主体的允许的许可权集合的成员。
   * 允许的权限集由与getPermissions方法使用的算法相同的算法确定。
   * 
   * 
   * @param user the principal, assumed to be a valid authenticated Principal.
   * @param perm the permission to be checked for.
   * @return true if the principal has the specified permission,
   *         false otherwise.
   * @see java.security.Principal
   * @see java.security.Permission
   */
  @Override
  public boolean checkPermission(Principal user,
                                 java.security.acl.Permission perm) {
        for (Enumeration<AclEntry> e = entryList.elements();e.hasMoreElements();){
          AclEntry ent = e.nextElement();
          if (ent.getPrincipal().equals(user))
                if (ent.checkPermission(perm)) return true;
        }
        return false;
  }

  /**
   * Checks whether or not the specified principal has the specified
   * permission.
   * If it does, true is returned, otherwise false is returned.
   * More specifically, this method checks whether the passed permission
   * is a member of the allowed permission set of the specified principal.
   * The allowed permission set is determined by the same algorithm as is
   * used by the getPermissions method.
   *
   * <p>
   *  检查指定的主体是否具有指定的权限。如果是,返回true,否则返回false。更具体地,该方法检查所传递的许可权是否是指定主体的允许的许可权集合的成员。
   * 允许的权限集由与getPermissions方法使用的算法相同的算法确定。
   * 
   * 
   * @param user the principal, assumed to be a valid authenticated Principal.
   * @param community the community name associated with the principal.
   * @param perm the permission to be checked for.
   * @return true if the principal has the specified permission, false
   *        otherwise.
   * @see java.security.Principal
   * @see java.security.Permission
   */
  public boolean checkPermission(Principal user, String community,
                                 java.security.acl.Permission perm) {
        for (Enumeration<AclEntry> e = entryList.elements();e.hasMoreElements();){
          AclEntryImpl ent = (AclEntryImpl) e.nextElement();
          if (ent.getPrincipal().equals(user))
                if (ent.checkPermission(perm) && ent.checkCommunity(community)) return true;
        }
        return false;
  }

  /**
   * Checks whether or not the specified community string is defined.
   *
   * <p>
   *  检查是否定义了指定的团体字符串。
   * 
   * 
   * @param community the community name associated with the principal.
   *
   * @return true if the specified community string is defined, false
   *      otherwise.
   * @see java.security.Principal
   * @see java.security.Permission
   */
  public boolean checkCommunity(String community) {
        for (Enumeration<AclEntry> e = entryList.elements();e.hasMoreElements();){
          AclEntryImpl ent = (AclEntryImpl) e.nextElement();
          if (ent.checkCommunity(community)) return true;
        }
        return false;
  }

  /**
   * Returns a string representation of the ACL contents.
   *
   * <p>
   * 返回ACL内容的字符串表示形式。
   * 
   * @return a string representation of the ACL contents.
   */
  @Override
  public String toString(){
        return ("AclImpl: "+ getName());
  }
}
