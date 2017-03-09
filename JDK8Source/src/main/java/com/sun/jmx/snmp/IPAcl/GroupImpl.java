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



import java.util.Vector;
import java.util.Enumeration;
import java.io.Serializable;
import java.net.UnknownHostException;


import java.security.Principal;
import java.security.acl.Group;


/**
 * This class is used to represent a subnet mask (a group of hosts
 * matching the same
 * IP mask).
 *
 * <p>
 *  此类用于表示子网掩码(匹配同一IP掩码的一组主机)。
 * 
 */

class GroupImpl extends PrincipalImpl implements Group, Serializable {
  private static final long serialVersionUID = -7777387035032541168L;

  /**
   * Constructs an empty group.
   * <p>
   *  构造一个空组。
   * 
   * 
   * @exception UnknownHostException Not implemented
   */
  public GroupImpl () throws UnknownHostException {
  }

  /**
   * Constructs a group using the specified subnet mask.
   *
   * <p>
   *  使用指定的子网掩码构造组。
   * 
   * 
   * @param mask The subnet mask to use to build the group.
   * @exception UnknownHostException if the subnet mask cann't be built.
   */
  public GroupImpl (String mask) throws UnknownHostException {
        super(mask);
  }

    /**
     * Adds the specified member to the group.
     *
     * <p>
     *  将指定的成员添加到组。
     * 
     * 
     * @param p the principal to add to this group.
     * @return true if the member was successfully added, false if the
     *     principal was already a member.
     */
    public boolean addMember(Principal p) {
        // we don't need to add members because the ip address is a
        // subnet mask
        return true;
    }

  public int hashCode() {
        return super.hashCode();
  }

  /**
   * Compares this group to the specified object. Returns true if the object
   * passed in matches the group represented.
   *
   * <p>
   *  将此组与指定的对象进行比较。如果传入的对象与表示的组匹配,则返回true。
   * 
   * 
   * @param p the object to compare with.
   * @return true if the object passed in matches the subnet mask,
   *   false otherwise.
   */
  public boolean equals (Object p) {
        if (p instanceof PrincipalImpl || p instanceof GroupImpl){
          if ((super.hashCode() & p.hashCode()) == p.hashCode()) return true;
          else return false;
        } else {
          return false;
        }
  }

  /**
   * Returns true if the passed principal is a member of the group.
   *
   * <p>
   *  如果传递的主体是组的成员,那么返回true。
   * 
   * 
   * @param p the principal whose membership is to be checked.
   * @return true if the principal is a member of this group, false otherwise.
   */
  public boolean isMember(Principal p) {
        if ((p.hashCode() & super.hashCode()) == p.hashCode()) return true;
        else return false;
  }

  /**
   * Returns an enumeration which contains the subnet mask.
   *
   * <p>
   *  返回包含子网掩码的枚举。
   * 
   * 
   * @return an enumeration which contains the subnet mask.
   */
  public Enumeration<? extends Principal> members(){
        Vector<Principal> v = new Vector<Principal>(1);
        v.addElement(this);
        return v.elements();
  }

  /**
   * Removes the specified member from the group. (Not implemented)
   *
   * <p>
   *  从组中删除指定的成员。 (未实现)
   * 
   * 
   * @param p the principal to remove from this group.
   * @return allways return true.
   */
  public boolean removeMember(Principal p) {
        return true;
  }

  /**
   * Prints a string representation of this group.
   *
   * <p>
   *  打印此组的字符串表示形式。
   * 
   * @return  a string representation of this group.
   */
  public String toString() {
        return ("GroupImpl :"+super.getAddress().toString());
  }
}
