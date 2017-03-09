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



import java.io.Serializable;


/**
 * Permission is represented as a String.
 *
 * <p>
 *  权限表示为字符串。
 * 
 * 
 * @see java.security.acl.Permission
 */

class PermissionImpl implements java.security.acl.Permission, Serializable {
  private static final long serialVersionUID = 4478110422746916589L;

  private String perm = null;

  /**
   * Constructs a permission.
   *
   * <p>
   *  构造一个权限。
   * 
   * 
   * @param s the string representing the permission.
   */
  public PermissionImpl(String s) {
        perm = s;
  }

  public int hashCode() {
        return super.hashCode();
  }

  /**
   * Returns true if the object passed matches the permission represented in.
   *
   * <p>
   *  如果传递的对象与表示的权限匹配,则返回true。
   * 
   * 
   * @param p the Permission object to compare with.
   * @return true if the Permission objects are equal, false otherwise.
   */
  public boolean equals(Object p){
        if (p instanceof PermissionImpl){
          return perm.equals(((PermissionImpl)p).getString());
        } else {
          return false;
        }
  }

  /**
   * Prints a string representation of this permission.
   *
   * <p>
   *  打印此权限的字符串表示形式。
   * 
   * 
   * @return a string representation of this permission.
   */
  public String toString(){
        return perm;
  }

  /**
   * Prints the permission.
   *
   * <p>
   *  打印权限。
   * 
   * @return a string representation of this permission.
   */
  public String getString(){
        return perm;
  }
}
