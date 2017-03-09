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

/**
 * A {@code UserPrincipal} representing a <em>group identity</em>, used to
 * determine access rights to objects in a file system. The exact definition of
 * a group is implementation specific, but typically, it represents an identity
 * created for administrative purposes so as to determine the access rights for
 * the members of the group. Whether an entity can be a member of multiple
 * groups, and whether groups can be nested, are implementation specified and
 * therefore not specified.
 *
 * <p>
 *  用于表示文件系统中对象的访问权限的表示<em>组标识</em>的{@code UserPrincipal}。组的确切定义是实现特定的,但通常它表示为管理目的而创建的身份,以便确定组的成员的访问权限。
 * 实体是否可以是多个组的成员,以及组是否可以嵌套,是实现指定的,因此不指定。
 * 
 * @since 1.7
 *
 * @see UserPrincipalLookupService#lookupPrincipalByGroupName
 */

public interface GroupPrincipal extends UserPrincipal { }
