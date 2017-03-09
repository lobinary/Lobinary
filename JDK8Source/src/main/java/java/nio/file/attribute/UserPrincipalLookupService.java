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

import java.io.IOException;

/**
 * An object to lookup user and group principals by name. A {@link UserPrincipal}
 * represents an identity that may be used to determine access rights to objects
 * in a file system. A {@link GroupPrincipal} represents a <em>group identity</em>.
 * A {@code UserPrincipalLookupService} defines methods to lookup identities by
 * name or group name (which are typically user or account names). Whether names
 * and group names are case sensitive or not depends on the implementation.
 * The exact definition of a group is implementation specific but typically a
 * group represents an identity created for administrative purposes so as to
 * determine the access rights for the members of the group. In particular it is
 * implementation specific if the <em>namespace</em> for names and groups is the
 * same or is distinct. To ensure consistent and correct behavior across
 * platforms it is recommended that this API be used as if the namespaces are
 * distinct. In other words, the {@link #lookupPrincipalByName
 * lookupPrincipalByName} should be used to lookup users, and {@link
 * #lookupPrincipalByGroupName lookupPrincipalByGroupName} should be used to
 * lookup groups.
 *
 * <p>
 *  用于按名称查找用户和组主体的对象。 {@link UserPrincipal}表示可用于确定对文件系统中对象的访问权限的标识。
 *  {@link GroupPrincipal}表示<em>群组身份</em>。
 *  {@code UserPrincipalLookupService}定义了按名称或组名称(通常是用户名或帐户名)查找身份的方法。名称和组名称是区分大小写还是不区分大小取决于实施。
 * 组的确切定义是实现特定的,但是通常一个组表示为管理目的而创建的身份,以便确定组的成员的访问权限。特别地,如果名称和组的<em>命名空间</em>相同或不同,它是实现特定的。
 * 为了确保跨平台的一致和正确的行为,建议使用此API,就像命名空间不同。
 * 换句话说,{@link #lookupPrincipalByName lookupPrincipalByName}应该用于查找用户,而{@link #lookupPrincipalByGroupName lookupPrincipalByGroupName}
 * 应该用于查找组。
 * 为了确保跨平台的一致和正确的行为,建议使用此API,就像命名空间不同。
 * 
 * 
 * @since 1.7
 *
 * @see java.nio.file.FileSystem#getUserPrincipalLookupService
 */

public abstract class UserPrincipalLookupService {

    /**
     * Initializes a new instance of this class.
     * <p>
     *  初始化此类的新实例。
     * 
     */
    protected UserPrincipalLookupService() {
    }

    /**
     * Lookup a user principal by name.
     *
     * <p>
     *  按名称查找用户主体。
     * 
     * 
     * @param   name
     *          the string representation of the user principal to lookup
     *
     * @return  a user principal
     *
     * @throws  UserPrincipalNotFoundException
     *          the principal does not exist
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager is
     *          installed, it checks {@link RuntimePermission}<tt>("lookupUserInformation")</tt>
     */
    public abstract UserPrincipal lookupPrincipalByName(String name)
        throws IOException;

    /**
     * Lookup a group principal by group name.
     *
     * <p> Where an implementation does not support any notion of group then
     * this method always throws {@link UserPrincipalNotFoundException}. Where
     * the namespace for user accounts and groups is the same, then this method
     * is identical to invoking {@link #lookupPrincipalByName
     * lookupPrincipalByName}.
     *
     * <p>
     *  按组名查找组主体。
     * 
     * <p>如果实现不支持任何组的概念,则此方法总是抛出{@link UserPrincipalNotFoundException}。
     * 
     * @param   group
     *          the string representation of the group to lookup
     *
     * @return  a group principal
     *
     * @throws  UserPrincipalNotFoundException
     *          the principal does not exist or is not a group
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager is
     *          installed, it checks {@link RuntimePermission}<tt>("lookupUserInformation")</tt>
     */
    public abstract GroupPrincipal lookupPrincipalByGroupName(String group)
        throws IOException;
}
