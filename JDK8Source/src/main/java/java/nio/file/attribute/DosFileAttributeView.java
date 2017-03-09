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
 * A file attribute view that provides a view of the legacy "DOS" file attributes.
 * These attributes are supported by file systems such as the File Allocation
 * Table (FAT) format commonly used in <em>consumer devices</em>.
 *
 * <p> A {@code DosFileAttributeView} is a {@link BasicFileAttributeView} that
 * additionally supports access to the set of DOS attribute flags that are used
 * to indicate if the file is read-only, hidden, a system file, or archived.
 *
 * <p> Where dynamic access to file attributes is required, the attributes
 * supported by this attribute view are as defined by {@code
 * BasicFileAttributeView}, and in addition, the following attributes are
 * supported:
 * <blockquote>
 * <table border="1" cellpadding="8" summary="Supported attributes">
 *   <tr>
 *     <th> Name </th>
 *     <th> Type </th>
 *   </tr>
 *   <tr>
 *     <td> readonly </td>
 *     <td> {@link Boolean} </td>
 *   </tr>
 *   <tr>
 *     <td> hidden </td>
 *     <td> {@link Boolean} </td>
 *   </tr>
 *   <tr>
 *     <td> system </td>
 *     <td> {@link Boolean} </td>
 *   </tr>
 *   <tr>
 *     <td> archive </td>
 *     <td> {@link Boolean} </td>
 *   </tr>
 * </table>
 * </blockquote>
 *
 * <p> The {@link java.nio.file.Files#getAttribute getAttribute} method may
 * be used to read any of these attributes, or any of the attributes defined by
 * {@link BasicFileAttributeView} as if by invoking the {@link #readAttributes
 * readAttributes()} method.
 *
 * <p> The {@link java.nio.file.Files#setAttribute setAttribute} method may
 * be used to update the file's last modified time, last access time or create
 * time attributes as defined by {@link BasicFileAttributeView}. It may also be
 * used to update the DOS attributes as if by invoking the {@link #setReadOnly
 * setReadOnly}, {@link #setHidden setHidden}, {@link #setSystem setSystem}, and
 * {@link #setArchive setArchive} methods respectively.
 *
 * <p>
 *  文件属性视图,提供了旧版"DOS"文件属性的视图。这些属性由文件系统支持,例如在消费者设备中常用的文件分配表(FAT)格式</em>。
 * 
 *  <p> {@code DosFileAttributeView}是一个{@link BasicFileAttributeView},它还支持访问用于指示文件是只读,隐藏,系统文件还是归档的一组DOS属
 * 性标志。
 * 
 *  <p>在需要动态访问文件属性时,此属性视图支持的属性由{@code BasicFileAttributeView}定义,此外,还支持以下属性：
 * <blockquote>
 * <table border="1" cellpadding="8" summary="Supported attributes">
 * <tr>
 *  <th>名称</th> <th>键入</th>
 * </tr>
 * <tr>
 *  <td> readonly </td> <td> {@link Boolean} </td>
 * </tr>
 * <tr>
 *  <td> hidden </td> <td> {@link Boolean} </td>
 * </tr>
 * <tr>
 *  <td> system </td> <td> {@link Boolean} </td>
 * </tr>
 * <tr>
 *  <td>归档</td> <td> {@link Boolean} </td>
 * </tr>
 * </table>
 * </blockquote>
 * 
 *  <p> {@link java.nio.file.Files#getAttribute getAttribute}方法可用于读取任何这些属性或由{@link BasicFileAttributeView}
 * 定义的任何属性,如同通过调用{@link# readAttributes readAttributes()}方法。
 * 
 * 
 * @since 1.7
 */

public interface DosFileAttributeView
    extends BasicFileAttributeView
{
    /**
     * Returns the name of the attribute view. Attribute views of this type
     * have the name {@code "dos"}.
     * <p>
     * <p> {@link java.nio.file.Files#setAttribute setAttribute}方法可用于更新文件的最后修改时间,最后访问时间或由{@link BasicFileAttributeView}
     * 定义的创建时间属性。
     * 它还可以用于分别更新DOS属性,如同分别调用{@link #setReadOnly setReadOnly},{@link #setHidden setHidden},{@link #setSystem setSystem}
     * 和{@link #setArchive setArchive}方法。
     * 
     */
    @Override
    String name();

    /**
    /* <p>
    /*  返回属性视图的名称。此类型的属性视图名称为{@code"dos"}。
    /* 
    /* 
     * @throws  IOException                             {@inheritDoc}
     * @throws  SecurityException                       {@inheritDoc}
     */
    @Override
    DosFileAttributes readAttributes() throws IOException;

    /**
     * Updates the value of the read-only attribute.
     *
     * <p> It is implementation specific if the attribute can be updated as an
     * atomic operation with respect to other file system operations. An
     * implementation may, for example, require to read the existing value of
     * the DOS attribute in order to update this attribute.
     *
     * <p>
     * 
     * @param   value
     *          the new value of the attribute
     *
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default, and a security manager is installed,
     *          its  {@link SecurityManager#checkWrite(String) checkWrite} method
     *          is invoked to check write access to the file
     */
    void setReadOnly(boolean value) throws IOException;

    /**
     * Updates the value of the hidden attribute.
     *
     * <p> It is implementation specific if the attribute can be updated as an
     * atomic operation with respect to other file system operations. An
     * implementation may, for example, require to read the existing value of
     * the DOS attribute in order to update this attribute.
     *
     * <p>
     *  更新只读属性的值。
     * 
     *  <p>如果属性可以更新为相对于其他文件系统操作的原子操作,则它是实现特定的。例如,实现可能需要读取DOS属性的现有值,以便更新该属性。
     * 
     * 
     * @param   value
     *          the new value of the attribute
     *
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default, and a security manager is installed,
     *          its  {@link SecurityManager#checkWrite(String) checkWrite} method
     *          is invoked to check write access to the file
     */
    void setHidden(boolean value) throws IOException;

    /**
     * Updates the value of the system attribute.
     *
     * <p> It is implementation specific if the attribute can be updated as an
     * atomic operation with respect to other file system operations. An
     * implementation may, for example, require to read the existing value of
     * the DOS attribute in order to update this attribute.
     *
     * <p>
     *  更新隐藏属性的值。
     * 
     *  <p>如果属性可以更新为相对于其他文件系统操作的原子操作,则它是实现特定的。例如,实现可能需要读取DOS属性的现有值,以便更新该属性。
     * 
     * 
     * @param   value
     *          the new value of the attribute
     *
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default, and a security manager is installed,
     *          its  {@link SecurityManager#checkWrite(String) checkWrite} method
     *          is invoked to check write access to the file
     */
    void setSystem(boolean value) throws IOException;

    /**
     * Updates the value of the archive attribute.
     *
     * <p> It is implementation specific if the attribute can be updated as an
     * atomic operation with respect to other file system operations. An
     * implementation may, for example, require to read the existing value of
     * the DOS attribute in order to update this attribute.
     *
     * <p>
     *  更新系统属性的值。
     * 
     *  <p>如果属性可以更新为相对于其他文件系统操作的原子操作,则它是实现特定的。例如,实现可能需要读取DOS属性的现有值,以便更新该属性。
     * 
     * 
     * @param   value
     *          the new value of the attribute
     *
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default, and a security manager is installed,
     *          its  {@link SecurityManager#checkWrite(String) checkWrite} method
     *          is invoked to check write access to the file
     */
    void setArchive(boolean value) throws IOException;
}
