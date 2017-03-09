/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.security;

import java.security.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import sun.security.util.SecurityConstants;

/**
 * The AllPermission is a permission that implies all other permissions.
 * <p>
 * <b>Note:</b> Granting AllPermission should be done with extreme care,
 * as it implies all other permissions. Thus, it grants code the ability
 * to run with security
 * disabled.  Extreme caution should be taken before granting such
 * a permission to code.  This permission should be used only during testing,
 * or in extremely rare cases where an application or applet is
 * completely trusted and adding the necessary permissions to the policy
 * is prohibitively cumbersome.
 *
 * <p>
 *  AllPermission是一个意味着所有其他权限的权限。
 * <p>
 *  <b>注意：</b>授予AllPermission应该非常小心,因为它意味着所有其他权限。因此,它授予代码在安全禁用的情况下运行的能力。在授予这样的代码许可之前应该非常小心。
 * 此权限应仅在测试期间使用,或者在极少数情况下,其中应用程序或小程序是完全受信任的,并且对策略添加必要的权限是非常麻烦的。
 * 
 * 
 * @see java.security.Permission
 * @see java.security.AccessController
 * @see java.security.Permissions
 * @see java.security.PermissionCollection
 * @see java.lang.SecurityManager
 *
 *
 * @author Roland Schemers
 *
 * @serial exclude
 */

public final class AllPermission extends Permission {

    private static final long serialVersionUID = -2916474571451318075L;

    /**
     * Creates a new AllPermission object.
     * <p>
     *  创建一个新的AllPermission对象。
     * 
     */
    public AllPermission() {
        super("<all permissions>");
    }


    /**
     * Creates a new AllPermission object. This
     * constructor exists for use by the {@code Policy} object
     * to instantiate new Permission objects.
     *
     * <p>
     *  创建一个新的AllPermission对象。此构造函数存在供{@code Policy}对象用于实例化新的Permission对象。
     * 
     * 
     * @param name ignored
     * @param actions ignored.
     */
    public AllPermission(String name, String actions) {
        this();
    }

    /**
     * Checks if the specified permission is "implied" by
     * this object. This method always returns true.
     *
     * <p>
     *  检查此对象是否"隐含"指定的权限。这个方法总是返回true。
     * 
     * 
     * @param p the permission to check against.
     *
     * @return return
     */
    public boolean implies(Permission p) {
         return true;
    }

    /**
     * Checks two AllPermission objects for equality. Two AllPermission
     * objects are always equal.
     *
     * <p>
     *  检查两个AllPermission对象是否相等。两个AllPermission对象总是相等的。
     * 
     * 
     * @param obj the object we are testing for equality with this object.
     * @return true if <i>obj</i> is an AllPermission, false otherwise.
     */
    public boolean equals(Object obj) {
        return (obj instanceof AllPermission);
    }

    /**
     * Returns the hash code value for this object.
     *
     * <p>
     *  返回此对象的哈希码值。
     * 
     * 
     * @return a hash code value for this object.
     */

    public int hashCode() {
        return 1;
    }

    /**
     * Returns the canonical string representation of the actions.
     *
     * <p>
     *  返回操作的规范字符串表示形式。
     * 
     * 
     * @return the actions.
     */
    public String getActions() {
        return "<all actions>";
    }

    /**
     * Returns a new PermissionCollection object for storing AllPermission
     * objects.
     * <p>
     *
     * <p>
     *  返回用于存储AllPermission对象的新PermissionCollection对象。
     * <p>
     * 
     * 
     * @return a new PermissionCollection object suitable for
     * storing AllPermissions.
     */
    public PermissionCollection newPermissionCollection() {
        return new AllPermissionCollection();
    }

}

/**
 * A AllPermissionCollection stores a collection
 * of AllPermission permissions. AllPermission objects
 * must be stored in a manner that allows them to be inserted in any
 * order, but enable the implies function to evaluate the implies
 * method in an efficient (and consistent) manner.
 *
 * <p>
 *  AllPermissionCollection存储AllPermission权限的集合。
 *  AllPermission对象必须以允许以任何顺序插入的方式存储,但是要启用implies函数以有效(且一致)的方式评估implies方法。
 * 
 * 
 * @see java.security.Permission
 * @see java.security.Permissions
 *
 *
 * @author Roland Schemers
 *
 * @serial include
 */

final class AllPermissionCollection
    extends PermissionCollection
    implements java.io.Serializable
{

    // use serialVersionUID from JDK 1.2.2 for interoperability
    private static final long serialVersionUID = -4023755556366636806L;

    private boolean all_allowed; // true if any all permissions have been added

    /**
     * Create an empty AllPermissions object.
     *
     * <p>
     * 创建一个空的AllPermissions对象。
     * 
     */

    public AllPermissionCollection() {
        all_allowed = false;
    }

    /**
     * Adds a permission to the AllPermissions. The key for the hash is
     * permission.path.
     *
     * <p>
     *  向AllPermissions添加权限。哈希的键是permission.path。
     * 
     * 
     * @param permission the Permission object to add.
     *
     * @exception IllegalArgumentException - if the permission is not a
     *                                       AllPermission
     *
     * @exception SecurityException - if this AllPermissionCollection object
     *                                has been marked readonly
     */

    public void add(Permission permission) {
        if (! (permission instanceof AllPermission))
            throw new IllegalArgumentException("invalid permission: "+
                                               permission);
        if (isReadOnly())
            throw new SecurityException("attempt to add a Permission to a readonly PermissionCollection");

        all_allowed = true; // No sync; staleness OK
    }

    /**
     * Check and see if this set of permissions implies the permissions
     * expressed in "permission".
     *
     * <p>
     *  检查并确定这组权限是否意味着在"权限"中表达的权限。
     * 
     * 
     * @param permission the Permission object to compare
     *
     * @return always returns true.
     */

    public boolean implies(Permission permission) {
        return all_allowed; // No sync; staleness OK
    }

    /**
     * Returns an enumeration of all the AllPermission objects in the
     * container.
     *
     * <p>
     *  返回容器中所有AllPermission对象的枚举。
     * 
     * @return an enumeration of all the AllPermission objects.
     */
    public Enumeration<Permission> elements() {
        return new Enumeration<Permission>() {
            private boolean hasMore = all_allowed;

            public boolean hasMoreElements() {
                return hasMore;
            }

            public Permission nextElement() {
                hasMore = false;
                return SecurityConstants.ALL_PERMISSION;
            }
        };
    }
}
