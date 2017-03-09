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

package java.nio.file;

import java.nio.file.attribute.*;
import java.io.IOException;

/**
 * Storage for files. A {@code FileStore} represents a storage pool, device,
 * partition, volume, concrete file system or other implementation specific means
 * of file storage. The {@code FileStore} for where a file is stored is obtained
 * by invoking the {@link Files#getFileStore getFileStore} method, or all file
 * stores can be enumerated by invoking the {@link FileSystem#getFileStores
 * getFileStores} method.
 *
 * <p> In addition to the methods defined by this class, a file store may support
 * one or more {@link FileStoreAttributeView FileStoreAttributeView} classes
 * that provide a read-only or updatable view of a set of file store attributes.
 *
 * <p>
 *  文件存储。 {@code FileStore}表示存储池,设备,分区,卷,具体文件系统或其他实现特定的文件存储方式。
 * 通过调用{@link Files#getFileStore getFileStore}方法获得存储文件的{@code FileStore},或者通过调用{@link FileSystem#getFileStores getFileStores}
 * 方法枚举所有文件存储。
 *  文件存储。 {@code FileStore}表示存储池,设备,分区,卷,具体文件系统或其他实现特定的文件存储方式。
 * 
 *  <p>除了此类定义的方法之外,文件存储可以支持一个或多个{@link FileStoreAttributeView FileStoreAttributeView}类,这些类提供一组文件存储属性的只读或
 * 可更新视图。
 * 
 * 
 * @since 1.7
 */

public abstract class FileStore {

    /**
     * Initializes a new instance of this class.
     * <p>
     *  初始化此类的新实例。
     * 
     */
    protected FileStore() {
    }

    /**
     * Returns the name of this file store. The format of the name is highly
     * implementation specific. It will typically be the name of the storage
     * pool or volume.
     *
     * <p> The string returned by this method may differ from the string
     * returned by the {@link Object#toString() toString} method.
     *
     * <p>
     *  返回此文件存储的名称。名称的格式高度具体实现。它通常是存储池或卷的名称。
     * 
     *  <p>此方法返回的字符串可能与{@link Object#toString()toString}方法返回的字符串不同。
     * 
     * 
     * @return  the name of this file store
     */
    public abstract String name();

    /**
     * Returns the <em>type</em> of this file store. The format of the string
     * returned by this method is highly implementation specific. It may
     * indicate, for example, the format used or if the file store is local
     * or remote.
     *
     * <p>
     *  返回此文件存储的<em>类型</em>。此方法返回的字符串的格式高度具体实现。它可以指示例如所使用的格式,或者文件存储是本地还是远程。
     * 
     * 
     * @return  a string representing the type of this file store
     */
    public abstract String type();

    /**
     * Tells whether this file store is read-only. A file store is read-only if
     * it does not support write operations or other changes to files. Any
     * attempt to create a file, open an existing file for writing etc. causes
     * an {@code IOException} to be thrown.
     *
     * <p>
     * 指示此文件存储是否为只读。如果文件存储不支持写操作或对文件的其他更改,那么它是只读的。任何尝试创建文件,打开现有文件以进行写入等都会导致{@code IOException}被抛出。
     * 
     * 
     * @return  {@code true} if, and only if, this file store is read-only
     */
    public abstract boolean isReadOnly();

    /**
     * Returns the size, in bytes, of the file store.
     *
     * <p>
     *  返回文件存储的大小(以字节为单位)。
     * 
     * 
     * @return  the size of the file store, in bytes
     *
     * @throws  IOException
     *          if an I/O error occurs
     */
    public abstract long getTotalSpace() throws IOException;

    /**
     * Returns the number of bytes available to this Java virtual machine on the
     * file store.
     *
     * <p> The returned number of available bytes is a hint, but not a
     * guarantee, that it is possible to use most or any of these bytes.  The
     * number of usable bytes is most likely to be accurate immediately
     * after the space attributes are obtained. It is likely to be made inaccurate
     * by any external I/O operations including those made on the system outside
     * of this Java virtual machine.
     *
     * <p>
     *  返回文件存储上此Java虚拟机可用的字节数。
     * 
     *  <p>返回的可用字节数是一个提示,但不是保证,可以使用大多数或任何这些字节。在获得空间属性之后,可用字节的数量最可能是精确的。
     * 它可能会由于任何外部I / O操作(包括在此Java虚拟机之外的系统上进行的操作)而变得不准确。
     * 
     * 
     * @return  the number of bytes available
     *
     * @throws  IOException
     *          if an I/O error occurs
     */
    public abstract long getUsableSpace() throws IOException;

    /**
     * Returns the number of unallocated bytes in the file store.
     *
     * <p> The returned number of unallocated bytes is a hint, but not a
     * guarantee, that it is possible to use most or any of these bytes.  The
     * number of unallocated bytes is most likely to be accurate immediately
     * after the space attributes are obtained. It is likely to be
     * made inaccurate by any external I/O operations including those made on
     * the system outside of this virtual machine.
     *
     * <p>
     *  返回文件存储中未分配的字节数。
     * 
     *  <p>未分配字节的返回数量是一个提示,但不是保证,可以使用大多数或任何这些字节。在获得空间属性之后,未分配字节的数量最可能是精确的。
     * 它可能由于任何外部I / O操作(包括在该虚拟机之外的系统上进行的操作)而变得不准确。
     * 
     * 
     * @return  the number of unallocated bytes
     *
     * @throws  IOException
     *          if an I/O error occurs
     */
    public abstract long getUnallocatedSpace() throws IOException;

    /**
     * Tells whether or not this file store supports the file attributes
     * identified by the given file attribute view.
     *
     * <p> Invoking this method to test if the file store supports {@link
     * BasicFileAttributeView} will always return {@code true}. In the case of
     * the default provider, this method cannot guarantee to give the correct
     * result when the file store is not a local storage device. The reasons for
     * this are implementation specific and therefore unspecified.
     *
     * <p>
     *  指示此文件存储是否支持由给定文件属性视图标识的文件属性。
     * 
     * <p>调用此方法以测试文件存储是否支持{@link BasicFileAttributeView}将始终返回{@code true}。
     * 在默认提供程序的情况下,当文件存储不是本地存储设备时,此方法不能保证给出正确的结果。其原因是具体实施,因此未具体说明。
     * 
     * 
     * @param   type
     *          the file attribute view type
     *
     * @return  {@code true} if, and only if, the file attribute view is
     *          supported
     */
    public abstract boolean supportsFileAttributeView(Class<? extends FileAttributeView> type);

    /**
     * Tells whether or not this file store supports the file attributes
     * identified by the given file attribute view.
     *
     * <p> Invoking this method to test if the file store supports {@link
     * BasicFileAttributeView}, identified by the name "{@code basic}" will
     * always return {@code true}. In the case of the default provider, this
     * method cannot guarantee to give the correct result when the file store is
     * not a local storage device. The reasons for this are implementation
     * specific and therefore unspecified.
     *
     * <p>
     *  指示此文件存储是否支持由给定文件属性视图标识的文件属性。
     * 
     *  <p>调用此方法以测试文件存储是否支持{@link BasicFileAttributeView}(由名称"{@code basic}"标识)将始终返回{@code true}。
     * 在默认提供程序的情况下,当文件存储不是本地存储设备时,此方法不能保证给出正确的结果。其原因是具体实施,因此未具体说明。
     * 
     * 
     * @param   name
     *          the {@link FileAttributeView#name name} of file attribute view
     *
     * @return  {@code true} if, and only if, the file attribute view is
     *          supported
     */
    public abstract boolean supportsFileAttributeView(String name);

    /**
     * Returns a {@code FileStoreAttributeView} of the given type.
     *
     * <p> This method is intended to be used where the file store attribute
     * view defines type-safe methods to read or update the file store attributes.
     * The {@code type} parameter is the type of the attribute view required and
     * the method returns an instance of that type if supported.
     *
     * <p>
     *  返回给定类型的{@code FileStoreAttributeView}。
     * 
     *  <p>此方法适用于文件存储属性视图定义读取或更新文件存储属性的类型安全方法的情况。 {@code type}参数是所需的属性视图的类型,如果支持,该方法将返回该类型的实例。
     * 
     * 
     * @param   <V>
     *          The {@code FileStoreAttributeView} type
     * @param   type
     *          the {@code Class} object corresponding to the attribute view
     *
     * @return  a file store attribute view of the specified type or
     *          {@code null} if the attribute view is not available
     */
    public abstract <V extends FileStoreAttributeView> V
        getFileStoreAttributeView(Class<V> type);

    /**
     * Reads the value of a file store attribute.
     *
     * <p> The {@code attribute} parameter identifies the attribute to be read
     * and takes the form:
     * <blockquote>
     * <i>view-name</i><b>:</b><i>attribute-name</i>
     * </blockquote>
     * where the character {@code ':'} stands for itself.
     *
     * <p> <i>view-name</i> is the {@link FileStoreAttributeView#name name} of
     * a {@link FileStore AttributeView} that identifies a set of file attributes.
     * <i>attribute-name</i> is the name of the attribute.
     *
     * <p> <b>Usage Example:</b>
     * Suppose we want to know if ZFS compression is enabled (assuming the "zfs"
     * view is supported):
     * <pre>
     *    boolean compression = (Boolean)fs.getAttribute("zfs:compression");
     * </pre>
     *
     * <p>
     *  读取文件存储属性的值。
     * 
     *  <p> {@code attribute}参数标识要读取的属性,格式如下：
     * <blockquote>
     *  <i> view-name </i> <b>：</b> <i> attribute-name </i>
     * </blockquote>
     *  其中字符{@code'：'}代表自身。
     * 
     * <p> <i> view-name </i>是{@link FileStore AttributeView}的{@link FileStoreAttributeView#name name},用于标识一
     * 
     * @param   attribute
     *          the attribute to read

     * @return  the attribute value; {@code null} may be a valid valid for some
     *          attributes
     *
     * @throws  UnsupportedOperationException
     *          if the attribute view is not available or it does not support
     *          reading the attribute
     * @throws  IOException
     *          if an I/O error occurs
     */
    public abstract Object getAttribute(String attribute) throws IOException;
}
