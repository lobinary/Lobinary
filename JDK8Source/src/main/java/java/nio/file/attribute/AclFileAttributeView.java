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
import java.util.List;
import java.io.IOException;

/**
 * A file attribute view that supports reading or updating a file's Access
 * Control Lists (ACL) or file owner attributes.
 *
 * <p> ACLs are used to specify access rights to file system objects. An ACL is
 * an ordered list of {@link AclEntry access-control-entries}, each specifying a
 * {@link UserPrincipal} and the level of access for that user principal. This
 * file attribute view defines the {@link #getAcl() getAcl}, and {@link
 * #setAcl(List) setAcl} methods to read and write ACLs based on the ACL
 * model specified in <a href="http://www.ietf.org/rfc/rfc3530.txt"><i>RFC&nbsp;3530:
 * Network File System (NFS) version 4 Protocol</i></a>. This file attribute view
 * is intended for file system implementations that support the NFSv4 ACL model
 * or have a <em>well-defined</em> mapping between the NFSv4 ACL model and the ACL
 * model used by the file system. The details of such mapping are implementation
 * dependent and are therefore unspecified.
 *
 * <p> This class also extends {@code FileOwnerAttributeView} so as to define
 * methods to get and set the file owner.
 *
 * <p> When a file system provides access to a set of {@link FileStore
 * file-systems} that are not homogeneous then only some of the file systems may
 * support ACLs. The {@link FileStore#supportsFileAttributeView
 * supportsFileAttributeView} method can be used to test if a file system
 * supports ACLs.
 *
 * <h2>Interoperability</h2>
 *
 * RFC&nbsp;3530 allows for special user identities to be used on platforms that
 * support the POSIX defined access permissions. The special user identities
 * are "{@code OWNER@}", "{@code GROUP@}", and "{@code EVERYONE@}". When both
 * the {@code AclFileAttributeView} and the {@link PosixFileAttributeView}
 * are supported then these special user identities may be included in ACL {@link
 * AclEntry entries} that are read or written. The file system's {@link
 * UserPrincipalLookupService} may be used to obtain a {@link UserPrincipal}
 * to represent these special identities by invoking the {@link
 * UserPrincipalLookupService#lookupPrincipalByName lookupPrincipalByName}
 * method.
 *
 * <p> <b>Usage Example:</b>
 * Suppose we wish to add an entry to an existing ACL to grant "joe" access:
 * <pre>
 *     // lookup "joe"
 *     UserPrincipal joe = file.getFileSystem().getUserPrincipalLookupService()
 *         .lookupPrincipalByName("joe");
 *
 *     // get view
 *     AclFileAttributeView view = Files.getFileAttributeView(file, AclFileAttributeView.class);
 *
 *     // create ACE to give "joe" read access
 *     AclEntry entry = AclEntry.newBuilder()
 *         .setType(AclEntryType.ALLOW)
 *         .setPrincipal(joe)
 *         .setPermissions(AclEntryPermission.READ_DATA, AclEntryPermission.READ_ATTRIBUTES)
 *         .build();
 *
 *     // read ACL, insert ACE, re-write ACL
 *     List&lt;AclEntry&gt; acl = view.getAcl();
 *     acl.add(0, entry);   // insert before any DENY entries
 *     view.setAcl(acl);
 * </pre>
 *
 * <h2> Dynamic Access </h2>
 * <p> Where dynamic access to file attributes is required, the attributes
 * supported by this attribute view are as follows:
 * <blockquote>
 * <table border="1" cellpadding="8" summary="Supported attributes">
 *   <tr>
 *     <th> Name </th>
 *     <th> Type </th>
 *   </tr>
 *   <tr>
 *     <td> "acl" </td>
 *     <td> {@link List}&lt;{@link AclEntry}&gt; </td>
 *   </tr>
 *   <tr>
 *     <td> "owner" </td>
 *     <td> {@link UserPrincipal} </td>
 *   </tr>
 * </table>
 * </blockquote>
 *
 * <p> The {@link Files#getAttribute getAttribute} method may be used to read
 * the ACL or owner attributes as if by invoking the {@link #getAcl getAcl} or
 * {@link #getOwner getOwner} methods.
 *
 * <p> The {@link Files#setAttribute setAttribute} method may be used to
 * update the ACL or owner attributes as if by invoking the {@link #setAcl setAcl}
 * or {@link #setOwner setOwner} methods.
 *
 * <h2> Setting the ACL when creating a file </h2>
 *
 * <p> Implementations supporting this attribute view may also support setting
 * the initial ACL when creating a file or directory. The initial ACL
 * may be provided to methods such as {@link Files#createFile createFile} or {@link
 * Files#createDirectory createDirectory} as an {@link FileAttribute} with {@link
 * FileAttribute#name name} {@code "acl:acl"} and a {@link FileAttribute#value
 * value} that is the list of {@code AclEntry} objects.
 *
 * <p> Where an implementation supports an ACL model that differs from the NFSv4
 * defined ACL model then setting the initial ACL when creating the file must
 * translate the ACL to the model supported by the file system. Methods that
 * create a file should reject (by throwing {@link IOException IOException})
 * any attempt to create a file that would be less secure as a result of the
 * translation.
 *
 * <p>
 *  支持读取或更新文件的访问控制列表(ACL)或文件所有者属性的文件属性视图。
 * 
 *  <p> ACL用于指定对文件系统对象的访问权限。
 *  ACL是{@link AclEntry access-control-entries}的有序列表,每个都指定一个{@link UserPrincipal}和该用户主体的访问级别。
 * 此文件属性视图定义基于<a href ="http：// www中指定的ACL模型来读取和写入ACL的{@link #getAcl()getAcl}和{@link #setAcl(List)setAcl}方法.ietf.org / rfc / rfc3530.txt">
 *  <i> RFC 3533：网络文件系统(NFS)版本4协议</i> </a>。
 *  ACL是{@link AclEntry access-control-entries}的有序列表,每个都指定一个{@link UserPrincipal}和该用户主体的访问级别。
 * 此文件属性视图适用于支持NFSv4 ACL模型或在NFSv4 ACL模型和文件系统使用的ACL模型之间具有明确定义的</em>映射的文件系统实现。这种映射的细节是实现相关的,因此未指定。
 * 
 *  <p>此类还扩展了{@code FileOwnerAttributeView},以便定义获取和设置文件所有者的方法。
 * 
 *  <p>当文件系统提供对不是同类的一组{@link FileStore文件系统}的访问时,只有一些文件系统可能支持ACL。
 *  {@link FileStore#supportsFileAttributeView supportsFileAttributeView}方法可用于测试文件系统是否支持ACL。
 * 
 * <h2>互操作性</h2>
 * 
 *  RFC&nbsp; 3530允许在支持POSIX定义的访问权限的平台上使用特殊用户身份。
 * 特殊用户身份是"{@code OWNER @}","{@code GROUP @}"和"{@code EVERYONE @}"。
 * 当支持{@code AclFileAttributeView}和{@link PosixFileAttributeView}时,这些特殊用户标识可能包含在读取或写入的ACL {@link AclEntry entries}
 * 中。
 * 特殊用户身份是"{@code OWNER @}","{@code GROUP @}"和"{@code EVERYONE @}"。
 * 文件系统的{@link UserPrincipalLookupService}可用于通过调用{@link UserPrincipalLookupService#lookupPrincipalByName lookupPrincipalByName}
 * 方法来获取{@link UserPrincipal}来表示这些特殊标识。
 * 特殊用户身份是"{@code OWNER @}","{@code GROUP @}"和"{@code EVERYONE @}"。
 * 
 *  <p> <b>使用示例：</b>假设我们希望向现有ACL添加条目以授予"joe"访问权限：
 * <pre>
 *  // lookup"joe"UserPrincipal joe = file.getFileSystem()。
 * getUserPrincipalLookupService().lookupPrincipalByName("joe");。
 * 
 *  // get view AclFileAttributeView view = Files.getFileAttributeView(file,AclFileAttributeView.class);
 * 。
 * 
 *  //创建ACE以授予"joe"读访问权。
 * AclEntry entry = AclEntry.newBuilder().setType(AclEntryType.ALLOW).setPrincipal(joe).setPermissions(A
 * clEntryPermission.READ_DATA,AclEntryPermission.READ_ATTRIBUTES).build。
 *  //创建ACE以授予"joe"读访问权。
 * 
 *  //读取ACL,插入ACE,重写ACL列表&lt; AclEntry&gt; acl = view.getAcl(); acl.add(0,entry); // insert before any D
 * ENY entries view.setAcl(acl);。
 * </pre>
 * 
 * <h2>动态访问</h2> <p>在需要对文件属性进行动态访问时,此属性视图支持的属性如下：
 * <blockquote>
 * <table border="1" cellpadding="8" summary="Supported attributes">
 * <tr>
 *  <th>名称</th> <th>键入</th>
 * </tr>
 * <tr>
 * 
 * @since 1.7
 */

public interface AclFileAttributeView
    extends FileOwnerAttributeView
{
    /**
     * Returns the name of the attribute view. Attribute views of this type
     * have the name {@code "acl"}.
     * <p>
     *  <td>"acl"</td> <td> {@link List}&lt; {@ link AclEntry}&gt; </td>
     * </tr>
     * <tr>
     *  <td>"owner"</td> <td> {@link UserPrincipal} </td>
     * </tr>
     * </table>
     * </blockquote>
     * 
     *  <p> {@link Files#getAttribute getAttribute}方法可用于读取ACL或所有者属性,就像调用{@link #getAcl getAcl}或{@link #getOwner getOwner}
     * 方法一样。
     * 
     *  <p> {@link Files#setAttribute setAttribute}方法可用于更新ACL或所有者属性,就像调用{@link #setAcl setAcl}或{@link #setOwner setOwner}
     * 方法一样。
     * 
     *  <h2>在创建文件时设置ACL </h2>
     * 
     *  <p>支持此属性视图的实现也可以支持在创建文件或目录时设置初始ACL。
     * 可以使用{@link FileAttribute#name name} {@code"acl：acl)将初始ACL提供给诸如{@link Files#createFile createFile}或{@link Files#createDirectory createDirectory}
     * 之类的方法作为{@link FileAttribute} "}和{@link FileAttribute#value value},它是{@code AclEntry}对象的列表。
     *  <p>支持此属性视图的实现也可以支持在创建文件或目录时设置初始ACL。
     * 
     * <p>如果实施支持与NFSv4定义的ACL模型不同的ACL模型,则在创建文件时设置初始ACL必须将ACL转换为文件系统支持的模型。
     * 创建文件的方法应拒绝(通过抛出{@link IOException IOException})任何尝试创建一个由于翻译结果不安全的文件。
     * 
     */
    @Override
    String name();

    /**
     * Reads the access control list.
     *
     * <p> When the file system uses an ACL model that differs from the NFSv4
     * defined ACL model, then this method returns an ACL that is the translation
     * of the ACL to the NFSv4 ACL model.
     *
     * <p> The returned list is modifiable so as to facilitate changes to the
     * existing ACL. The {@link #setAcl setAcl} method is used to update
     * the file's ACL attribute.
     *
     * <p>
     * 
     * @return  an ordered list of {@link AclEntry entries} representing the
     *          ACL
     *
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, a security manager is
     *          installed, and it denies {@link RuntimePermission}<tt>("accessUserInformation")</tt>
     *          or its {@link SecurityManager#checkRead(String) checkRead} method
     *          denies read access to the file.
     */
    List<AclEntry> getAcl() throws IOException;

    /**
     * Updates (replace) the access control list.
     *
     * <p> Where the file system supports Access Control Lists, and it uses an
     * ACL model that differs from the NFSv4 defined ACL model, then this method
     * must translate the ACL to the model supported by the file system. This
     * method should reject (by throwing {@link IOException IOException}) any
     * attempt to write an ACL that would appear to make the file more secure
     * than would be the case if the ACL were updated. Where an implementation
     * does not support a mapping of {@link AclEntryType#AUDIT} or {@link
     * AclEntryType#ALARM} entries, then this method ignores these entries when
     * writing the ACL.
     *
     * <p> If an ACL entry contains a {@link AclEntry#principal user-principal}
     * that is not associated with the same provider as this attribute view then
     * {@link ProviderMismatchException} is thrown. Additional validation, if
     * any, is implementation dependent.
     *
     * <p> If the file system supports other security related file attributes
     * (such as a file {@link PosixFileAttributes#permissions
     * access-permissions} for example), the updating the access control list
     * may also cause these security related attributes to be updated.
     *
     * <p>
     *  返回属性视图的名称。此类型的属性视图名称为{@code"acl"}。
     * 
     * 
     * @param   acl
     *          the new access control list
     *
     * @throws  IOException
     *          if an I/O error occurs or the ACL is invalid
     * @throws  SecurityException
     *          In the case of the default provider, a security manager is
     *          installed, it denies {@link RuntimePermission}<tt>("accessUserInformation")</tt>
     *          or its {@link SecurityManager#checkWrite(String) checkWrite}
     *          method denies write access to the file.
     */
    void setAcl(List<AclEntry> acl) throws IOException;
}
