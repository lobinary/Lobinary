/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2009, Oracle and/or its affiliates. All rights reserved.
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

import java.security.Principal;

/**
 * A {@code Principal} representing an identity used to determine access rights
 * to objects in a file system.
 *
 * <p> On many platforms and file systems an entity requires appropriate access
 * rights or permissions in order to access objects in a file system. The
 * access rights are generally performed by checking the identity of the entity.
 * For example, on implementations that use Access Control Lists (ACLs) to
 * enforce privilege separation then a file in the file system may have an
 * associated ACL that determines the access rights of identities specified in
 * the ACL.
 *
 * <p> A {@code UserPrincipal} object is an abstract representation of an
 * identity. It has a {@link #getName() name} that is typically the username or
 * account name that it represents. User principal objects may be obtained using
 * a {@link UserPrincipalLookupService}, or returned by {@link
 * FileAttributeView} implementations that provide access to identity related
 * attributes. For example, the {@link AclFileAttributeView} and {@link
 * PosixFileAttributeView} provide access to a file's {@link
 * PosixFileAttributes#owner owner}.
 *
 * <p>
 *  表示用于确定对文件系统中对象的访问权限的身份的{@code Principal}。
 * 
 *  <p>在许多平台和文件系统上,实体需要适当的访问权限或权限才能访问文件系统中的对象。访问权限通常通过检查实体的身份来执行。
 * 例如,在使用访问控制列表(ACL)强制执行权限分离的实现上,文件系统中的文件可以具有确定在ACL中指定的标识的访问权限的关联ACL。
 * 
 *  <p> {@code UserPrincipal}对象是身份的抽象表示。它有一个{@link #getName()name},通常是它所代表的用户名或帐户名。
 * 
 * @since 1.7
 */

public interface UserPrincipal extends Principal { }
