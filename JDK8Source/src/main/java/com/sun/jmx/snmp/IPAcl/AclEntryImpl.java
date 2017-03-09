/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2007, Oracle and/or its affiliates. All rights reserved.
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



import java.security.acl.Permission;
import java.util.Vector;
import java.util.Enumeration;
import java.io.Serializable;
import java.net.UnknownHostException;

import java.security.Principal;
import java.security.acl.AclEntry;


/**
 * Represent one entry in the Access Control List (ACL).
 * This ACL entry object contains a permission associated with a particular principal.
 * (A principal represents an entity such as an individual machine or a group).
 *
 * <p>
 *  表示访问控制列表(ACL)中的一个条目。此ACL条目对象包含与特定主体关联的权限。 (主体表示诸如单个机器或组的实体)。
 * 
 * 
 * @see java.security.acl.AclEntry
 */

class AclEntryImpl implements AclEntry, Serializable {
  private static final long serialVersionUID = -5047185131260073216L;

  private AclEntryImpl (AclEntryImpl i) throws UnknownHostException {
        setPrincipal(i.getPrincipal());
        permList = new Vector<Permission>();
        commList = new Vector<String>();

        for (Enumeration<String> en = i.communities(); en.hasMoreElements();){
          addCommunity(en.nextElement());
        }

        for (Enumeration<Permission> en = i.permissions(); en.hasMoreElements();){
          addPermission(en.nextElement());
        }
        if (i.isNegative()) setNegativePermissions();
  }

  /**
   * Contructs an empty ACL entry.
   * <p>
   *  构造一个空ACL条目。
   * 
   */
  public AclEntryImpl (){
        princ = null;
        permList = new Vector<Permission>();
        commList = new Vector<String>();
  }

  /**
   * Constructs an ACL entry with a specified principal.
   *
   * <p>
   *  构造具有指定主体的ACL条目。
   * 
   * 
   * @param p the principal to be set for this entry.
   */
  public AclEntryImpl (Principal p) throws UnknownHostException {
        princ = p;
        permList = new Vector<Permission>();
        commList = new Vector<String>();
  }

  /**
   * Clones this ACL entry.
   *
   * <p>
   *  克隆此ACL条目。
   * 
   * 
   * @return a clone of this ACL entry.
   */
  public Object clone() {
        AclEntryImpl i;
        try {
          i = new AclEntryImpl(this);
        }catch (UnknownHostException e) {
          i = null;
        }
        return (Object) i;
  }

  /**
   * Returns true if this is a negative ACL entry (one denying the associated principal
   * the set of permissions in the entry), false otherwise.
   *
   * <p>
   *  如果这是一个否定ACL条目(一个拒绝相关主体的条目中的权限集合),则返回true,否则返回false。
   * 
   * 
   * @return true if this is a negative ACL entry, false if it's not.
   */
  public boolean isNegative(){
        return neg;
  }

  /**
   * Adds the specified permission to this ACL entry. Note: An entry can
   * have multiple permissions.
   *
   * <p>
   *  将指定的权限添加到此ACL条目。注意：条目可以有多个权限。
   * 
   * 
   * @param perm the permission to be associated with the principal in this
   *        entry
   * @return true if the permission is removed, false if the permission was
   *         not part of this entry's permission set.
   *
   */
  public boolean addPermission(java.security.acl.Permission perm){
        if (permList.contains(perm)) return false;
        permList.addElement(perm);
        return true;
  }

  /**
   * Removes the specified permission from this ACL entry.
   *
   * <p>
   *  从此ACL条目中删除指定的权限。
   * 
   * 
   * @param perm the permission to be removed from this entry.
   * @return true if the permission is removed, false if the permission
   *         was not part of this entry's permission set.
   */
  public boolean removePermission(java.security.acl.Permission perm){
        if (!permList.contains(perm)) return false;
        permList.removeElement(perm);
        return true;
  }

  /**
   * Checks if the specified permission is part of the permission set in
   * this entry.
   *
   * <p>
   *  检查指定的权限是否是此条目中设置的权限的一部分。
   * 
   * 
   * @param perm the permission to be checked for.
   * @return true if the permission is part of the permission set in this
   *         entry, false otherwise.
   */

  public boolean checkPermission(java.security.acl.Permission perm){
        return (permList.contains(perm));
  }

  /**
   * Returns an enumeration of the permissions in this ACL entry.
   *
   * <p>
   *  返回此ACL条目中的权限的枚举。
   * 
   * 
   * @return an enumeration of the permissions in this ACL entry.
   */
  public Enumeration<Permission> permissions(){
        return permList.elements();
  }

  /**
   * Sets this ACL entry to be a negative one. That is, the associated principal
   * (e.g., a user or a group) will be denied the permission set specified in the
   * entry. Note: ACL entries are by default positive. An entry becomes a negative
   * entry only if this setNegativePermissions method is called on it.
   *
   * Not Implemented.
   * <p>
   *  将此ACL条目设置为负值。也就是说,相关主体(例如,用户或组)将被拒绝在条目中指定的权限集。注意：ACL条目默认为正。
   * 只有在调用此setNegativePermissions方法时,条目才变为否定条目。
   * 
   *  未实现。
   * 
   */
  public void setNegativePermissions(){
        neg = true;
  }

  /**
   * Returns the principal for which permissions are granted or denied by this ACL
   * entry. Returns null if there is no principal set for this entry yet.
   *
   * <p>
   *  返回此ACL条目授予或拒绝权限的主体。如果此条目没有主体集,则返回null。
   * 
   * 
   * @return the principal associated with this entry.
   */
  public Principal getPrincipal(){
        return princ;
  }

  /**
   * Specifies the principal for which permissions are granted or denied by
   * this ACL entry. If a principal was already set for this ACL entry,
   * false is returned, otherwise true is returned.
   *
   * <p>
   * 指定此ACL条目授予或拒绝许可权的主体。如果已为此ACL条目设置了主体,则返回false,否则返回true。
   * 
   * 
   * @param p the principal to be set for this entry.
   * @return true if the principal is set, false if there was already a
   *         principal set for this entry.
   */
  public boolean setPrincipal(Principal p) {
        if (princ != null )
          return false;
        princ = p;
        return true;
  }

  /**
   * Returns a string representation of the contents of this ACL entry.
   *
   * <p>
   *  返回此ACL条目的内容的字符串表示形式。
   * 
   * 
   * @return a string representation of the contents.
   */
  public String toString(){
        return "AclEntry:"+princ.toString();
  }

  /**
   * Returns an enumeration of the communities in this ACL entry.
   *
   * <p>
   *  返回此ACL条目中社区的枚举。
   * 
   * 
   * @return an enumeration of the communities in this ACL entry.
   */
  public Enumeration<String> communities(){
        return commList.elements();
  }

  /**
   * Adds the specified community to this ACL entry. Note: An entry can
   * have multiple communities.
   *
   * <p>
   *  将指定的社区添加到此ACL条目。注意：条目可以有多个社区。
   * 
   * 
   * @param comm the community to be associated with the principal
   *        in this entry.
   * @return true if the community was added, false if the community was
   *         already part of this entry's community set.
   */
  public boolean addCommunity(String comm){
        if (commList.contains(comm)) return false;
        commList.addElement(comm);
        return true;
  }

  /**
   * Removes the specified community from this ACL entry.
   *
   * <p>
   *  从此ACL条目中删除指定的社区。
   * 
   * 
   * @param comm the community  to be removed from this entry.
   * @return true if the community is removed, false if the community was
   *         not part of this entry's community set.
   */
  public boolean removeCommunity(String comm){
        if (!commList.contains(comm)) return false;
        commList.removeElement(comm);
        return true;
  }

  /**
   * Checks if the specified community is part of the community set in this
   * entry.
   *
   * <p>
   *  检查指定的社区是否是此条目中设置的社区的一部分。
   * 
   * @param  comm the community to be checked for.
   * @return true if the community is part of the community set in this
   *         entry, false otherwise.
   */
  public boolean checkCommunity(String comm){
        return (commList.contains(comm));
  }

  private Principal princ = null;
  private boolean neg     = false;
  private Vector<Permission> permList = null;
  private Vector<String> commList = null;
}
