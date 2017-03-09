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

import java.io.IOException;

/**
 * A file attribute view that provides a view of a <em>basic set</em> of file
 * attributes common to many file systems. The basic set of file attributes
 * consist of <em>mandatory</em> and <em>optional</em> file attributes as
 * defined by the {@link BasicFileAttributes} interface.

 * <p> The file attributes are retrieved from the file system as a <em>bulk
 * operation</em> by invoking the {@link #readAttributes() readAttributes} method.
 * This class also defines the {@link #setTimes setTimes} method to update the
 * file's time attributes.
 *
 * <p> Where dynamic access to file attributes is required, the attributes
 * supported by this attribute view have the following names and types:
 * <blockquote>
 *  <table border="1" cellpadding="8" summary="Supported attributes">
 *   <tr>
 *     <th> Name </th>
 *     <th> Type </th>
 *   </tr>
 *  <tr>
 *     <td> "lastModifiedTime" </td>
 *     <td> {@link FileTime} </td>
 *   </tr>
 *   <tr>
 *     <td> "lastAccessTime" </td>
 *     <td> {@link FileTime} </td>
 *   </tr>
 *   <tr>
 *     <td> "creationTime" </td>
 *     <td> {@link FileTime} </td>
 *   </tr>
 *   <tr>
 *     <td> "size" </td>
 *     <td> {@link Long} </td>
 *   </tr>
 *   <tr>
 *     <td> "isRegularFile" </td>
 *     <td> {@link Boolean} </td>
 *   </tr>
 *   <tr>
 *     <td> "isDirectory" </td>
 *     <td> {@link Boolean} </td>
 *   </tr>
 *   <tr>
 *     <td> "isSymbolicLink" </td>
 *     <td> {@link Boolean} </td>
 *   </tr>
 *   <tr>
 *     <td> "isOther" </td>
 *     <td> {@link Boolean} </td>
 *   </tr>
 *   <tr>
 *     <td> "fileKey" </td>
 *     <td> {@link Object} </td>
 *   </tr>
 * </table>
 * </blockquote>
 *
 * <p> The {@link java.nio.file.Files#getAttribute getAttribute} method may be
 * used to read any of these attributes as if by invoking the {@link
 * #readAttributes() readAttributes()} method.
 *
 * <p> The {@link java.nio.file.Files#setAttribute setAttribute} method may be
 * used to update the file's last modified time, last access time or create time
 * attributes as if by invoking the {@link #setTimes setTimes} method.
 *
 * <p>
 *  文件属性视图,提供许多文件系统公用的文件属性的基本集</em>视图。
 * 基本文件属性集由{@link BasicFileAttributes}接口定义的<em>强制</em>和<em>可选</em>文件属性组成。
 * 
 *  <p>通过调用{@link #readAttributes()readAttributes}方法,文件属性作为<em>批量操作</em>从文件系统中检索。
 * 此类还定义了{@link #setTimes setTimes}方法来更新文件的时间属性。
 * 
 *  <p>在需要对文件属性进行动态访问时,此属性视图支持的属性具有以下名称和类型：
 * <blockquote>
 * <table border="1" cellpadding="8" summary="Supported attributes">
 * <tr>
 *  <th>名称</th> <th>键入</th>
 * </tr>
 * <tr>
 *  <td>"lastModifiedTime"</td> <td> {@link FileTime} </td>
 * </tr>
 * <tr>
 *  <td>"lastAccessTime"</td> <td> {@link FileTime} </td>
 * </tr>
 * <tr>
 *  <td>"creationTime"</td> <td> {@link FileTime} </td>
 * </tr>
 * <tr>
 *  <td>"size"</td> <td> {@link Long} </td>
 * </tr>
 * <tr>
 *  <td>"isRegularFile"</td> <td> {@link Boolean} </td>
 * </tr>
 * <tr>
 *  <td>"isDirectory"</td> <td> {@link Boolean} </td>
 * </tr>
 * <tr>
 *  <td>"isSymbolicLink"</td> <td> {@link Boolean} </td>
 * </tr>
 * <tr>
 *  <td>"isOther"</td> <td> {@link Boolean} </td>
 * </tr>
 * <tr>
 * 
 * @since 1.7
 */

public interface BasicFileAttributeView
    extends FileAttributeView
{
    /**
     * Returns the name of the attribute view. Attribute views of this type
     * have the name {@code "basic"}.
     * <p>
     *  <td>"fileKey"</td> <td> {@link Object} </td>
     * </tr>
     * </table>
     * </blockquote>
     * 
     * <p> {@link java.nio.file.Files#getAttribute getAttribute}方法可用于读取任何这些属性,如同通过调用{@link #readAttributes()readAttributes()}
     * 方法。
     * 
     *  <p> {@link java.nio.file.Files#setAttribute setAttribute}方法可用于更新文件的上次修改时间,上次访问时间或创建时间属性,如同通过调用{@link #setTimes setTimes}
     * 方法。
     * 
     */
    @Override
    String name();

    /**
     * Reads the basic file attributes as a bulk operation.
     *
     * <p> It is implementation specific if all file attributes are read as an
     * atomic operation with respect to other file system operations.
     *
     * <p>
     *  返回属性视图的名称。此类型的属性视图名称为{@code"basic"}。
     * 
     * 
     * @return  the file attributes
     *
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, a security manager is
     *          installed, its {@link SecurityManager#checkRead(String) checkRead}
     *          method is invoked to check read access to the file
     */
    BasicFileAttributes readAttributes() throws IOException;

    /**
     * Updates any or all of the file's last modified time, last access time,
     * and create time attributes.
     *
     * <p> This method updates the file's timestamp attributes. The values are
     * converted to the epoch and precision supported by the file system.
     * Converting from finer to coarser granularities result in precision loss.
     * The behavior of this method when attempting to set a timestamp that is
     * not supported or to a value that is outside the range supported by the
     * underlying file store is not defined. It may or not fail by throwing an
     * {@code IOException}.
     *
     * <p> If any of the {@code lastModifiedTime}, {@code lastAccessTime},
     * or {@code createTime} parameters has the value {@code null} then the
     * corresponding timestamp is not changed. An implementation may require to
     * read the existing values of the file attributes when only some, but not
     * all, of the timestamp attributes are updated. Consequently, this method
     * may not be an atomic operation with respect to other file system
     * operations. Reading and re-writing existing values may also result in
     * precision loss. If all of the {@code lastModifiedTime}, {@code
     * lastAccessTime} and {@code createTime} parameters are {@code null} then
     * this method has no effect.
     *
     * <p> <b>Usage Example:</b>
     * Suppose we want to change a file's last access time.
     * <pre>
     *    Path path = ...
     *    FileTime time = ...
     *    Files.getFileAttributeView(path, BasicFileAttributeView.class).setTimes(null, time, null);
     * </pre>
     *
     * <p>
     *  将基本文件属性读取为批量操作。
     * 
     *  <p>如果所有文件属性都被读取为相对于其他文件系统操作的原子操作,则它是实现特定的。
     * 
     * 
     * @param   lastModifiedTime
     *          the new last modified time, or {@code null} to not change the
     *          value
     * @param   lastAccessTime
     *          the last access time, or {@code null} to not change the value
     * @param   createTime
     *          the file's create time, or {@code null} to not change the value
     *
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, a security manager is
     *          installed, its  {@link SecurityManager#checkWrite(String) checkWrite}
     *          method is invoked to check write access to the file
     *
     * @see java.nio.file.Files#setLastModifiedTime
     */
    void setTimes(FileTime lastModifiedTime,
                  FileTime lastAccessTime,
                  FileTime createTime) throws IOException;
}
