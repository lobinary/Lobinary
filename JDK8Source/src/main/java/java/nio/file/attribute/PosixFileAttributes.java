/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2011, Oracle and/or its affiliates. All rights reserved.
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

package java.nio.file.attribute;

import java.util.Set;

/**
 * File attributes associated with files on file systems used by operating systems
 * that implement the Portable Operating System Interface (POSIX) family of
 * standards.
 *
 * <p> The POSIX attributes of a file are retrieved using a {@link
 * PosixFileAttributeView} by invoking its {@link
 * PosixFileAttributeView#readAttributes readAttributes} method.
 *
 * <p>
 *  与实现便携式操作系统接口(POSIX)标准系列的操作系统所使用的文件系统上的文件相关联的文件属性。
 * 
 *  <p>使用{@link PosixFileAttributeView}调用其{@link PosixFileAttributeView#readAttributes readAttributes}方法
 * 来检索文件的POSIX属性。
 * 
 * 
 * @since 1.7
 */

public interface PosixFileAttributes
    extends BasicFileAttributes
{
    /**
     * Returns the owner of the file.
     *
     * <p>
     *  返回文件的所有者。
     * 
     * 
     * @return  the file owner
     *
     * @see PosixFileAttributeView#setOwner
     */
    UserPrincipal owner();

    /**
     * Returns the group owner of the file.
     *
     * <p>
     *  返回文件的组所有者。
     * 
     * 
     * @return  the file group owner
     *
     * @see PosixFileAttributeView#setGroup
     */
    GroupPrincipal group();

    /**
     * Returns the permissions of the file. The file permissions are returned
     * as a set of {@link PosixFilePermission} elements. The returned set is a
     * copy of the file permissions and is modifiable. This allows the result
     * to be modified and passed to the {@link PosixFileAttributeView#setPermissions
     * setPermissions} method to update the file's permissions.
     *
     * <p>
     *  返回文件的权限。文件权限作为一组{@link PosixFilePermission}元素返回。返回的集是文件权限的副本,可以修改。
     * 这允许修改结果并将其传递到{@link PosixFileAttributeView#setPermissions setPermissions}方法以更新文件的权限。
     * 
     * @return  the file permissions
     *
     * @see PosixFileAttributeView#setPermissions
     */
    Set<PosixFilePermission> permissions();
}
