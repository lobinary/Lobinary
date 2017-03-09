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

import java.io.IOException;

/**
 * A file attribute view that supports reading or updating the owner of a file.
 * This file attribute view is intended for file system implementations that
 * support a file attribute that represents an identity that is the owner of
 * the file. Often the owner of a file is the identity of the entity that
 * created the file.
 *
 * <p> The {@link #getOwner getOwner} or {@link #setOwner setOwner} methods may
 * be used to read or update the owner of the file.
 *
 * <p> The {@link java.nio.file.Files#getAttribute getAttribute} and
 * {@link java.nio.file.Files#setAttribute setAttribute} methods may also be
 * used to read or update the owner. In that case, the owner attribute is
 * identified by the name {@code "owner"}, and the value of the attribute is
 * a {@link UserPrincipal}.
 *
 * <p>
 *  支持读取或更新文件所有者的文件属性视图。此文件属性视图用于支持表示作为文件所有者的标识的文件属性的文件系统实现。通常文件的所有者是创建文件的实体的标识。
 * 
 *  <p> {@link #getOwner getOwner}或{@link #setOwner setOwner}方法可用于读取或更新文件的所有者。
 * 
 *  <p> {@link java.nio.file.Files#getAttribute getAttribute}和{@link java.nio.file.Files#setAttribute setAttribute}
 * 方法也可用于读取或更新所有者。
 * 在这种情况下,owner属性由名称{@code"owner"}标识,并且该属性的值为{@link UserPrincipal}。
 * 
 * 
 * @since 1.7
 */

public interface FileOwnerAttributeView
    extends FileAttributeView
{
    /**
     * Returns the name of the attribute view. Attribute views of this type
     * have the name {@code "owner"}.
     * <p>
     *  返回属性视图的名称。此类型的属性视图名称为{@code"owner"}。
     * 
     */
    @Override
    String name();

    /**
     * Read the file owner.
     *
     * <p> It it implementation specific if the file owner can be a {@link
     * GroupPrincipal group}.
     *
     * <p>
     *  读取文件所有者。
     * 
     *  <p>如果文件所有者可以是{@link GroupPrincipal group},则它具体实现。
     * 
     * 
     * @return  the file owner
     *
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, a security manager is
     *          installed, and it denies {@link
     *          RuntimePermission}<tt>("accessUserInformation")</tt> or its
     *          {@link SecurityManager#checkRead(String) checkRead} method
     *          denies read access to the file.
     */
    UserPrincipal getOwner() throws IOException;

    /**
     * Updates the file owner.
     *
     * <p> It it implementation specific if the file owner can be a {@link
     * GroupPrincipal group}. To ensure consistent and correct behavior
     * across platforms it is recommended that this method should only be used
     * to set the file owner to a user principal that is not a group.
     *
     * <p>
     *  更新文件所有者。
     * 
     * 
     * @param   owner
     *          the new file owner
     *
     * @throws  IOException
     *          if an I/O error occurs, or the {@code owner} parameter is a
     *          group and this implementation does not support setting the owner
     *          to a group
     * @throws  SecurityException
     *          In the case of the default provider, a security manager is
     *          installed, and it denies {@link
     *          RuntimePermission}<tt>("accessUserInformation")</tt> or its
     *          {@link SecurityManager#checkWrite(String) checkWrite} method
     *          denies write access to the file.
     */
    void setOwner(UserPrincipal owner) throws IOException;
}
