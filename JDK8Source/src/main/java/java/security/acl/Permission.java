/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, Oracle and/or its affiliates. All rights reserved.
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


/**
 * This interface represents a permission, such as that used to grant
 * a particular type of access to a resource.
 *
 * <p>
 *  此接口表示一个权限,例如用于授予对资源的特定类型访问的权限。
 * 
 * 
 * @author Satish Dharmaraj
 */
public interface Permission {

    /**
     * Returns true if the object passed matches the permission represented
     * in this interface.
     *
     * <p>
     *  如果传递的对象匹配此接口中表示的权限,则返回true。
     * 
     * 
     * @param another the Permission object to compare with.
     *
     * @return true if the Permission objects are equal, false otherwise
     */
    public boolean equals(Object another);

    /**
     * Prints a string representation of this permission.
     *
     * <p>
     *  打印此权限的字符串表示形式。
     * 
     * @return the string representation of the permission.
     */
    public String toString();

}
