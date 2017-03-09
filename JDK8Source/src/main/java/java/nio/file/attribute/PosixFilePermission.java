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

/**
 * Defines the bits for use with the {@link PosixFileAttributes#permissions()
 * permissions} attribute.
 *
 * <p> The {@link PosixFilePermissions} class defines methods for manipulating
 * set of permissions.
 *
 * <p>
 *  定义用于{@link PosixFileAttributes#permissions()permissions}属性的位。
 * 
 *  <p> {@link PosixFilePermissions}类定义了操作权限集的方法。
 * 
 * 
 * @since 1.7
 */

public enum PosixFilePermission {

    /**
     * Read permission, owner.
     * <p>
     *  读取权限,所有者。
     * 
     */
    OWNER_READ,

    /**
     * Write permission, owner.
     * <p>
     *  写权限,所有者。
     * 
     */
    OWNER_WRITE,

    /**
     * Execute/search permission, owner.
     * <p>
     *  执行/搜索权限,所有者。
     * 
     */
    OWNER_EXECUTE,

    /**
     * Read permission, group.
     * <p>
     *  读权限,组。
     * 
     */
    GROUP_READ,

    /**
     * Write permission, group.
     * <p>
     *  写权限,组。
     * 
     */
    GROUP_WRITE,

    /**
     * Execute/search permission, group.
     * <p>
     *  执行/搜索权限,组。
     * 
     */
    GROUP_EXECUTE,

    /**
     * Read permission, others.
     * <p>
     *  读权限,其他。
     * 
     */
    OTHERS_READ,

    /**
     * Write permission, others.
     * <p>
     *  写权限,其他。
     * 
     */
    OTHERS_WRITE,

    /**
     * Execute/search permission, others.
     * <p>
     *  执行/搜索权限,其他。
     */
    OTHERS_EXECUTE;
}
