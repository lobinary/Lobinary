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

import java.security.Principal;

/**
 * Interface for managing owners of Access Control Lists (ACLs) or ACL
 * configurations. (Note that the Acl interface in the
 * {@code  java.security.acl} package extends this Owner
 * interface.) The initial owner Principal should be specified as an
 * argument to the constructor of the class implementing this interface.
 *
 * <p>
 *  用于管理访问控制列表(ACL)或ACL配置所有者的接口。 (请注意,{@code java.security.acl}包中的Acl接口扩展了此Owner接口。
 * )初始所有者Principal应指定为实现此接口的类的构造函数的参数。
 * 
 * 
 * @see java.security.acl.Acl
 *
 */
public interface Owner {

    /**
     * Adds an owner. Only owners can modify ACL contents. The caller
     * principal must be an owner of the ACL in order to invoke this method.
     * That is, only an owner can add another owner. The initial owner is
     * configured at ACL construction time.
     *
     * <p>
     *  添加所有者。只有所有者才能修改ACL内容。调用者主体必须是ACL的所有者才能调用此方法。也就是说,只有所有者可以添加其他所有者。初始所有者在ACL构建时配置。
     * 
     * 
     * @param caller the principal invoking this method. It must be an owner
     * of the ACL.
     *
     * @param owner the owner that should be added to the list of owners.
     *
     * @return true if successful, false if owner is already an owner.
     * @exception NotOwnerException if the caller principal is not an owner
     * of the ACL.
     */
    public boolean addOwner(Principal caller, Principal owner)
      throws NotOwnerException;

    /**
     * Deletes an owner. If this is the last owner in the ACL, an exception is
     * raised.<p>
     *
     * The caller principal must be an owner of the ACL in order to invoke
     * this method.
     *
     * <p>
     *  删除所有者。如果这是ACL中的最后一个所有者,则会引发异常。<p>
     * 
     *  调用者主体必须是ACL的所有者才能调用此方法。
     * 
     * 
     * @param caller the principal invoking this method. It must be an owner
     * of the ACL.
     *
     * @param owner the owner to be removed from the list of owners.
     *
     * @return true if the owner is removed, false if the owner is not part
     * of the list of owners.
     *
     * @exception NotOwnerException if the caller principal is not an owner
     * of the ACL.
     *
     * @exception LastOwnerException if there is only one owner left, so that
     * deleteOwner would leave the ACL owner-less.
     */
    public boolean deleteOwner(Principal caller, Principal owner)
      throws NotOwnerException, LastOwnerException;

    /**
     * Returns true if the given principal is an owner of the ACL.
     *
     * <p>
     * 
     * @param owner the principal to be checked to determine whether or not
     * it is an owner.
     *
     * @return true if the passed principal is in the list of owners, false
     * if not.
     */
    public boolean isOwner(Principal owner);

}
