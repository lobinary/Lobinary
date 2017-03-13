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

/**
 * Interfaces and classes providing access to file and file system attributes.
 *
 * <blockquote><table cellspacing=1 cellpadding=0 summary="Attribute views">
 * <tr><th align="left">Attribute views</th><th align="left">Description</th></tr>
 * <tr><td valign=top><tt><i>{@link java.nio.file.attribute.AttributeView}</i></tt></td>
 *     <td>Can read or update non-opaque values associated with objects in a file system</td></tr>
 * <tr><td valign=top><tt>&nbsp;&nbsp;<i>{@link java.nio.file.attribute.FileAttributeView}</i></tt></td>
 *     <td>Can read or update file attributes</td></tr>
 * <tr><td valign=top><tt>&nbsp;&nbsp;&nbsp;&nbsp;<i>{@link java.nio.file.attribute.BasicFileAttributeView}&nbsp;&nbsp;</i></tt></td>
 *     <td>Can read or update a basic set of file attributes</td></tr>
 * <tr><td valign=top><tt>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i>{@link java.nio.file.attribute.PosixFileAttributeView}&nbsp;&nbsp;</i></tt></td>
 *     <td>Can read or update POSIX defined file attributes</td></tr>
 * <tr><td valign=top><tt>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i>{@link java.nio.file.attribute.DosFileAttributeView}&nbsp;&nbsp;</i></tt></td>
 *     <td>Can read or update FAT file attributes</td></tr>
 * <tr><td valign=top><tt>&nbsp;&nbsp;&nbsp;&nbsp;<i>{@link java.nio.file.attribute.FileOwnerAttributeView}&nbsp;&nbsp;</i></tt></td>
 *     <td>Can read or update the owner of a file</td></tr>
 * <tr><td valign=top><tt>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i>{@link java.nio.file.attribute.AclFileAttributeView}&nbsp;&nbsp;</i></tt></td>
 *     <td>Can read or update Access Control Lists</td></tr>
 * <tr><td valign=top><tt>&nbsp;&nbsp;&nbsp;&nbsp;<i>{@link java.nio.file.attribute.UserDefinedFileAttributeView}&nbsp;&nbsp;</i></tt></td>
 *     <td>Can read or update user-defined file attributes</td></tr>
 * <tr><td valign=top><tt>&nbsp;&nbsp;<i>{@link java.nio.file.attribute.FileStoreAttributeView}</i></tt></td>
 *     <td>Can read or update file system attributes</td></tr>
 * </table></blockquote>
 *
 * <p> An attribute view provides a read-only or updatable view of the non-opaque
 * values, or <em>metadata</em>, associated with objects in a file system.
 * The {@link java.nio.file.attribute.FileAttributeView} interface is
 * extended by several other interfaces that that views to specific sets of file
 * attributes. {@code FileAttributeViews} are selected by invoking the {@link
 * java.nio.file.Files#getFileAttributeView} method with a
 * <em>type-token</em> to identify the required view. Views can also be identified
 * by name. The {@link java.nio.file.attribute.FileStoreAttributeView} interface
 * provides access to file store attributes. A {@code FileStoreAttributeView} of
 * a given type is obtained by invoking the {@link
 * java.nio.file.FileStore#getFileStoreAttributeView} method.
 *
 * <p> The {@link java.nio.file.attribute.BasicFileAttributeView}
 * class defines methods to read and update a <em>basic</em> set of file
 * attributes that are common to many file systems.
 *
 * <p> The {@link java.nio.file.attribute.PosixFileAttributeView}
 * interface extends {@code BasicFileAttributeView} by defining methods
 * to access the file attributes commonly used by file systems and operating systems
 * that implement the Portable Operating System Interface (POSIX) family of
 * standards.
 *
 * <p> The {@link java.nio.file.attribute.DosFileAttributeView}
 * class extends {@code BasicFileAttributeView} by defining methods to
 * access the legacy "DOS" file attributes supported on file systems such as File
 * Allocation Tabl (FAT), commonly used in consumer devices.
 *
 * <p> The {@link java.nio.file.attribute.AclFileAttributeView}
 * class defines methods to read and write the Access Control List (ACL)
 * file attribute. The ACL model used by this file attribute view is based
 * on the model defined by <a href="http://www.ietf.org/rfc/rfc3530.txt">
 * <i>RFC&nbsp;3530: Network File System (NFS) version 4 Protocol</i></a>.
 *
 * <p> In addition to attribute views, this package also defines classes and
 * interfaces that are used when accessing attributes:
 *
 * <ul>
 *
 *   <li> The {@link java.nio.file.attribute.UserPrincipal} and
 *   {@link java.nio.file.attribute.GroupPrincipal} interfaces represent an
 *   identity or group identity. </li>
 *
 *   <li> The {@link java.nio.file.attribute.UserPrincipalLookupService}
 *   interface defines methods to lookup user or group principals. </li>
 *
 *   <li> The {@link java.nio.file.attribute.FileAttribute} interface
 *   represents the value of an attribute for cases where the attribute value is
 *   required to be set atomically when creating an object in the file system. </li>
 *
 * </ul>
 *
 *
 * <p> Unless otherwise noted, passing a <tt>null</tt> argument to a constructor
 * or method in any class or interface in this package will cause a {@link
 * java.lang.NullPointerException NullPointerException} to be thrown.
 *
 * <p>
 *  提供对文件和文件系统属性的访问的接口和类
 * 
 * <blockquote> <table cellspacing = 1 cellpadding = 0 summary ="Attribute views"> <tr> <th align ="left">
 * 属性视图</th> <th align ="left">说明</th> tr> <tr> <td valign = top> <tt> <i> {@ link javaniofileattributeAttributeView}
 *  </i> </tt>可以读取或更新与档案系统</td> </tr> <tr> <td valign = top> <tt>&nbsp;&nbsp; <i> {@ link javaniofileattributeFileAttributeView}
 *  </i> </tt>读取或更新文件属性</td> </tr> <tr> <td valign = top> <tt>&nbsp;&nbsp;&nbsp;&nbsp; <i> {@ link javaniofileattributeBasicFileAttributeView}
 * &nbsp; </i> / td> </td> <td>可以读取或更新一组基本的文件属性</td> </tr> <tr> <td valign = top> <tt>&nbsp;&nbsp;&nbsp;
 * &nbsp; &nbsp; <i> {@ link javaniofileattributePosixFileAttributeView}&nbsp;&nbsp; </i> </tt> </td> <td>
 * 可以读取或更新POSIX定义的文件属性</td> </tr> <tr> <td valign = top> <tt> ;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <i> 
 * {@ link javaniofileattributeDosFileAttributeView}&nbsp;&nbsp; </i> </tt> </td> <td>可以读取或更新FAT文件属性</td>
 *  < / tr> <tr> <td valign = top> <tt>&nbsp;&nbsp;&nbsp;&nbsp; <i> {@ link javaniofileattributeFileOwnerAttributeView}
 * &nbsp;&nbsp; </i> </tt> </td> <td>可以读取或更新文件的所有者</td> </tr> <tr> <td valign = top> <tt>&nbsp;&nbsp;&nb
 * sp;&nbsp;&nbsp;&nbsp; <i> {@ link javaniofileattributeAclFileAttributeView}&nbsp; ; </i> </td>可以读取或更新
 * 访问控制列表</td> </tr> <tr> <td valign = top> <tt>&nbsp;&nbsp;&nbsp;&nbsp; ; <i> {@ link javaniofileattribute可以读取或更新用户定义的文件属性</td> </tr> <tr> <td valign = top> <tt> </tt> &nbsp;&nbsp; <i> {@ link javaniofileattributeFileStoreAttributeView}
 *  </i> </tt>可以读取或更新文件系统属性</td> </tr> </table> </blockquote>。
 * 
 * <p>属性视图提供与文件系统中的对象关联的非不透明值或<em>元数据</em>的只读或可更新视图{@link javaniofileattributeFileAttributeView}接口由其他几个通
 * 过使用类型标记</em>调用{@link javaniofileFiles#getFileAttributeView}方法来选择查看特定文件属性集{@code FileAttributeViews}的接
 * 口,以标识所需的视图。
 * 还可以标识视图按名称{@link javaniofileattributeFileStoreAttributeView}接口提供对文件存储库属性的访问通过调用{@link javaniofileFileStore#getFileStoreAttributeView}
 * 方法可获得给定类型的{@code FileStoreAttributeView}。
 * 
 * <p> {@link javaniofileattributeBasicFileAttributeView}类定义了读取和更新许多文件系统常见的<em>基本</em>文件属性集的方法
 * 
 *  <p> {@link javaniofileattributePosixFileAttributeView}接口通过定义访问文件系统和实现便携式操作系统接口(POSIX)系列标准的操作系统常用文件属性
 * 
 * @since 1.7
 */

package java.nio.file.attribute;
