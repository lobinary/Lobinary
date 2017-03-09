/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.nio.file.*;
import java.util.Set;
import java.io.IOException;

/**
 * A file attribute view that provides a view of the file attributes commonly
 * associated with files on file systems used by operating systems that implement
 * the Portable Operating System Interface (POSIX) family of standards.
 *
 * <p> Operating systems that implement the <a href="http://www.opengroup.org">
 * POSIX</a> family of standards commonly use file systems that have a
 * file <em>owner</em>, <em>group-owner</em>, and related <em>access
 * permissions</em>. This file attribute view provides read and write access
 * to these attributes.
 *
 * <p> The {@link #readAttributes() readAttributes} method is used to read the
 * file's attributes. The file {@link PosixFileAttributes#owner() owner} is
 * represented by a {@link UserPrincipal} that is the identity of the file owner
 * for the purposes of access control. The {@link PosixFileAttributes#group()
 * group-owner}, represented by a {@link GroupPrincipal}, is the identity of the
 * group owner, where a group is an identity created for administrative purposes
 * so as to determine the access rights for the members of the group.
 *
 * <p> The {@link PosixFileAttributes#permissions() permissions} attribute is a
 * set of access permissions. This file attribute view provides access to the nine
 * permission defined by the {@link PosixFilePermission} class.
 * These nine permission bits determine the <em>read</em>, <em>write</em>, and
 * <em>execute</em> access for the file owner, group, and others (others
 * meaning identities other than the owner and members of the group). Some
 * operating systems and file systems may provide additional permission bits
 * but access to these other bits is not defined by this class in this release.
 *
 * <p> <b>Usage Example:</b>
 * Suppose we need to print out the owner and access permissions of a file:
 * <pre>
 *     Path file = ...
 *     PosixFileAttributes attrs = Files.getFileAttributeView(file, PosixFileAttributeView.class)
 *         .readAttributes();
 *     System.out.format("%s %s%n",
 *         attrs.owner().getName(),
 *         PosixFilePermissions.toString(attrs.permissions()));
 * </pre>
 *
 * <h2> Dynamic Access </h2>
 * <p> Where dynamic access to file attributes is required, the attributes
 * supported by this attribute view are as defined by {@link
 * BasicFileAttributeView} and {@link FileOwnerAttributeView}, and in addition,
 * the following attributes are supported:
 * <blockquote>
 * <table border="1" cellpadding="8" summary="Supported attributes">
 *   <tr>
 *     <th> Name </th>
 *     <th> Type </th>
 *   </tr>
 *  <tr>
 *     <td> "permissions" </td>
 *     <td> {@link Set}&lt;{@link PosixFilePermission}&gt; </td>
 *   </tr>
 *   <tr>
 *     <td> "group" </td>
 *     <td> {@link GroupPrincipal} </td>
 *   </tr>
 * </table>
 * </blockquote>
 *
 * <p> The {@link Files#getAttribute getAttribute} method may be used to read
 * any of these attributes, or any of the attributes defined by {@link
 * BasicFileAttributeView} as if by invoking the {@link #readAttributes
 * readAttributes()} method.
 *
 * <p> The {@link Files#setAttribute setAttribute} method may be used to update
 * the file's last modified time, last access time or create time attributes as
 * defined by {@link BasicFileAttributeView}. It may also be used to update
 * the permissions, owner, or group-owner as if by invoking the {@link
 * #setPermissions setPermissions}, {@link #setOwner setOwner}, and {@link
 * #setGroup setGroup} methods respectively.
 *
 * <h2> Setting Initial Permissions </h2>
 * <p> Implementations supporting this attribute view may also support setting
 * the initial permissions when creating a file or directory. The
 * initial permissions are provided to the {@link Files#createFile createFile}
 * or {@link Files#createDirectory createDirectory} methods as a {@link
 * FileAttribute} with {@link FileAttribute#name name} {@code "posix:permissions"}
 * and a {@link FileAttribute#value value} that is the set of permissions. The
 * following example uses the {@link PosixFilePermissions#asFileAttribute
 * asFileAttribute} method to construct a {@code FileAttribute} when creating a
 * file:
 *
 * <pre>
 *     Path path = ...
 *     Set&lt;PosixFilePermission&gt; perms =
 *         EnumSet.of(OWNER_READ, OWNER_WRITE, OWNER_EXECUTE, GROUP_READ);
 *     Files.createFile(path, PosixFilePermissions.asFileAttribute(perms));
 * </pre>
 *
 * <p> When the access permissions are set at file creation time then the actual
 * value of the permissions may differ that the value of the attribute object.
 * The reasons for this are implementation specific. On UNIX systems, for
 * example, a process has a <em>umask</em> that impacts the permission bits
 * of newly created files. Where an implementation supports the setting of
 * the access permissions, and the underlying file system supports access
 * permissions, then it is required that the value of the actual access
 * permissions will be equal or less than the value of the attribute
 * provided to the {@link Files#createFile createFile} or {@link
 * Files#createDirectory createDirectory} methods. In other words, the file may
 * be more secure than requested.
 *
 * <p>
 *  文件属性视图,提供通常与实现便携式操作系统接口(POSIX)标准系列的操作系统使用的文件系统上的文件相关联的文件属性的视图。
 * 
 *  <p>实施<a href="http://www.opengroup.org"> POSIX </a>系列标准的操作系统通常使用具有文件<em>所有者</em>的文件系统< em> group-own
 * er </em>和相关的<em>访问权限<em> </em>。
 * 此文件属性视图提供对这些属性的读取和写入访问。
 * 
 *  <p> {@link #readAttributes()readAttributes}方法用于读取文件的属性。
 * 文件{@link PosixFileAttributes#owner()owner}由{@link UserPrincipal}表示,它是文件所有者的身份,用于访问控制。
 * 由{@link GroupPrincipal}表示的{@link PosixFileAttributes#group()group-owner}是组所有者的身份,其中组是为管理目的而创建的身份,以便确定
 * 小组成员。
 * 文件{@link PosixFileAttributes#owner()owner}由{@link UserPrincipal}表示,它是文件所有者的身份,用于访问控制。
 * 
 * <p> {@link PosixFileAttributes#permissions()permissions}属性是一组访问权限。
 * 此文件属性视图提供对{@link PosixFilePermission}类定义的九个权限的访问。
 * 这9个权限位决定文件所有者,组和其他人的<em>读取</em>,<em>写入</em>和<em>执行</em>访问所有者和组的成员)。
 * 一些操作系统和文件系统可能提供额外的权限位,但是在这个版本中,这个类没有定义对这些其他位的访问。
 * 
 *  <p> <b>使用示例：</b>假设我们需要打印出文件的所有者和访问权限：
 * <pre>
 *  Path file = ... PosixFileAttributes attrs = Files.getFileAttributeView(file,PosixFileAttributeView.c
 * lass).readAttributes(); System.out.format("％s％s％n",attrs.owner()。
 * getName(),PosixFilePermissions.toString(attrs.permissions()));。
 * </pre>
 * 
 *  <h2>动态访问</h2> <p>在需要动态访问文件属性时,此属性视图支持的属性由{@link BasicFileAttributeView}和{@link FileOwnerAttributeView}
 * 定义,此外,以下属性支持：。
 * <blockquote>
 * <table border="1" cellpadding="8" summary="Supported attributes">
 * <tr>
 *  <th>名称</th> <th>键入</th>
 * </tr>
 * <tr>
 *  <td>"permissions"</td> <td> {@link Set}&lt; {@ link PosixFilePermission}&gt; </td>
 * </tr>
 * <tr>
 *  <td>"group"</td> <td> {@link GroupPrincipal} </td>
 * </tr>
 * </table>
 * 
 * @since 1.7
 */

public interface PosixFileAttributeView
    extends BasicFileAttributeView, FileOwnerAttributeView
{
    /**
     * Returns the name of the attribute view. Attribute views of this type
     * have the name {@code "posix"}.
     * <p>
     * </blockquote>
     * 
     * <p> {@link Files#getAttribute getAttribute}方法可用于读取这些属性中的任何一个或由{@link BasicFileAttributeView}定义的任何属性,如
     * 同通过调用{@link #readAttributes readAttributes()}方法。
     * 
     *  <p> {@link Files#setAttribute setAttribute}方法可用于更新由{@link BasicFileAttributeView}定义的文件的上次修改时间,上次访问时间
     * 或创建时间属性。
     * 它也可以用于更新权限,所有者或组所有者,如同分别调用{@link #setPermissions setPermissions},{@link #setOwner setOwner}和{@link #setGroup setGroup}
     * 方法。
     * 
     *  <h2>设置初始权限</h2> <p>支持此属性视图的实现也可能支持在创建文件或目录时设置初始权限。
     * 初始权限作为{@link FileAttribute}与{@link FileAttribute#name name} {@code"posix：permissions"}一起提供给{@link Files#createFile createFile}
     * 或{@link Files#createDirectory createDirectory}和一个{@link FileAttribute#value value},这是一组权限。
     *  <h2>设置初始权限</h2> <p>支持此属性视图的实现也可能支持在创建文件或目录时设置初始权限。
     * 以下示例使用{@link PosixFilePermissions#asFileAttribute asFileAttribute}方法在创建文件时构造{@code FileAttribute}：。
     * 
     * <pre>
     *  路径path = ...设置&lt; PosixFilePermission&gt; perms = EnumSet.of(OWNER_READ,OWNER_WRITE,OWNER_EXECUTE,G
     * ROUP_READ); Files.createFile(path,PosixFilePermissions.asFileAttribute(perms));。
     */
    @Override
    String name();

    /**
    /* <p>
    /* </pre>
    /* 
    /* <p>当在文件创建时设置访问权限时,权限的实际值可能与属性对象的值不同。其原因是具体实施。例如,在UNIX系统上,进程具有影响新创建文件的权限位的<em> umask </em>。
    /* 如果实现支持访问权限的设置,并且底层文件系统支持访问权限,则需要实际访问权限的值等于或小于提供给{@link Files的属性的值#createFile createFile}或{@link Files#createDirectory createDirectory}
    /* 方法。
    /* <p>当在文件创建时设置访问权限时,权限的实际值可能与属性对象的值不同。其原因是具体实施。例如,在UNIX系统上,进程具有影响新创建文件的权限位的<em> umask </em>。
    /* 换句话说,文件可能比请求更安全。
    /* 
    /* 
     * @throws  IOException                {@inheritDoc}
     * @throws  SecurityException
     *          In the case of the default provider, a security manager is
     *          installed, and it denies {@link RuntimePermission}<tt>("accessUserInformation")</tt>
     *          or its {@link SecurityManager#checkRead(String) checkRead} method
     *          denies read access to the file.
     */
    @Override
    PosixFileAttributes readAttributes() throws IOException;

    /**
     * Updates the file permissions.
     *
     * <p>
     *  返回属性视图的名称。此类型的属性视图名称为{@code"posix"}。
     * 
     * 
     * @param   perms
     *          the new set of permissions
     *
     * @throws  ClassCastException
     *          if the sets contains elements that are not of type {@code
     *          PosixFilePermission}
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, a security manager is
     *          installed, and it denies {@link RuntimePermission}<tt>("accessUserInformation")</tt>
     *          or its {@link SecurityManager#checkWrite(String) checkWrite}
     *          method denies write access to the file.
     */
    void setPermissions(Set<PosixFilePermission> perms) throws IOException;

    /**
     * Updates the file group-owner.
     *
     * <p>
     * 
     * @param   group
     *          the new file group-owner
     *
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager is
     *          installed, it denies {@link RuntimePermission}<tt>("accessUserInformation")</tt>
     *          or its {@link SecurityManager#checkWrite(String) checkWrite}
     *          method denies write access to the file.
     */
    void setGroup(GroupPrincipal group) throws IOException;
}
