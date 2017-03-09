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
import java.nio.channels.SeekableByteChannel;
import java.util.Set;
import java.io.IOException;

/**
 * A {@code DirectoryStream} that defines operations on files that are located
 * relative to an open directory. A {@code SecureDirectoryStream} is intended
 * for use by sophisticated or security sensitive applications requiring to
 * traverse file trees or otherwise operate on directories in a race-free manner.
 * Race conditions can arise when a sequence of file operations cannot be
 * carried out in isolation. Each of the file operations defined by this
 * interface specify a relative path. All access to the file is relative
 * to the open directory irrespective of if the directory is moved or replaced
 * by an attacker while the directory is open. A {@code SecureDirectoryStream}
 * may also be used as a virtual <em>working directory</em>.
 *
 * <p> A {@code SecureDirectoryStream} requires corresponding support from the
 * underlying operating system. Where an implementation supports this features
 * then the {@code DirectoryStream} returned by the {@link Files#newDirectoryStream
 * newDirectoryStream} method will be a {@code SecureDirectoryStream} and must
 * be cast to that type in order to invoke the methods defined by this interface.
 *
 * <p> In the case of the default {@link java.nio.file.spi.FileSystemProvider
 * provider}, and a security manager is set, then the permission checks are
 * performed using the path obtained by resolving the given relative path
 * against the <i>original path</i> of the directory (irrespective of if the
 * directory is moved since it was opened).
 *
 * <p>
 *  {@code DirectoryStream},用于定义相对于打开目录的文件的操作。
 *  {@code SecureDirectoryStream}旨在供需要遍历文件树或以无竞争方式在目录上操作的复杂或安全敏感的应用程序使用。当不能孤立地执行文件操作序列时,可能出现竞争条件。
 * 由此接口定义的每个文件操作都指定相对路径。对文件的所有访问都是相对于打开的目录,而不管目录是否被打开时被攻击者移动或替换。
 *  {@code SecureDirectoryStream}也可以用作虚拟工作目录<em> </em>。
 * 
 *  <p> {@code SecureDirectoryStream}需要底层操作系统的相应支持。
 * 如果某个实现支持此功能,则{@link Files#newDirectoryStream newDirectoryStream}方法返回的{@code DirectoryStream}将是一个{@code SecureDirectoryStream}
 * ,并且必须强制转换为该类型,才能调用此接口定义的方法。
 *  <p> {@code SecureDirectoryStream}需要底层操作系统的相应支持。
 * 
 * <p>在默认的{@link java.nio.file.spi.FileSystemProvider provider}的情况下,并且设置了安全管理器,则使用通过将给定的相对路径解析为< i>目录的原始
 * 路径</i>(无论目录是否从打开开始移动)。
 * 
 * 
 * @since   1.7
 */

public interface SecureDirectoryStream<T>
    extends DirectoryStream<T>
{
    /**
     * Opens the directory identified by the given path, returning a {@code
     * SecureDirectoryStream} to iterate over the entries in the directory.
     *
     * <p> This method works in exactly the manner specified by the {@link
     * Files#newDirectoryStream(Path) newDirectoryStream} method for the case that
     * the {@code path} parameter is an {@link Path#isAbsolute absolute} path.
     * When the parameter is a relative path then the directory to open is
     * relative to this open directory. The {@link
     * LinkOption#NOFOLLOW_LINKS NOFOLLOW_LINKS} option may be used to
     * ensure that this method fails if the file is a symbolic link.
     *
     * <p> The new directory stream, once created, is not dependent upon the
     * directory stream used to create it. Closing this directory stream has no
     * effect upon newly created directory stream.
     *
     * <p>
     *  打开给定路径标识的目录,返回{@code SecureDirectoryStream}以遍历目录中的条目。
     * 
     *  <p>对于{@code path}参数是{@link Path#isAbsolute absolute}路径的情况,此方法完全按照{@link Files#newDirectoryStream(Path)newDirectoryStream}
     * 方法指定的方式工作。
     * 当参数是相对路径时,要打开的目录是相对于此打开目录。如果文件是符号链接,则可以使用{@link LinkOption#NOFOLLOW_LINKS NOFOLLOW_LINKS}选项确保此方法失败。
     * 
     *  <p>新目录流创建后,不依赖于用于创建目录流。关闭此目录流对新创建的目录流没有任何影响。
     * 
     * 
     * @param   path
     *          the path to the directory to open
     * @param   options
     *          options indicating how symbolic links are handled
     *
     * @return  a new and open {@code SecureDirectoryStream} object
     *
     * @throws  ClosedDirectoryStreamException
     *          if the directory stream is closed
     * @throws  NotDirectoryException
     *          if the file could not otherwise be opened because it is not
     *          a directory <i>(optional specific exception)</i>
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager is
     *          installed, the {@link SecurityManager#checkRead(String) checkRead}
     *          method is invoked to check read access to the directory.
     */
    SecureDirectoryStream<T> newDirectoryStream(T path, LinkOption... options)
        throws IOException;

    /**
     * Opens or creates a file in this directory, returning a seekable byte
     * channel to access the file.
     *
     * <p> This method works in exactly the manner specified by the {@link
     * Files#newByteChannel Files.newByteChannel} method for the
     * case that the {@code path} parameter is an {@link Path#isAbsolute absolute}
     * path. When the parameter is a relative path then the file to open or
     * create is relative to this open directory. In addition to the options
     * defined by the {@code Files.newByteChannel} method, the {@link
     * LinkOption#NOFOLLOW_LINKS NOFOLLOW_LINKS} option may be used to
     * ensure that this method fails if the file is a symbolic link.
     *
     * <p> The channel, once created, is not dependent upon the directory stream
     * used to create it. Closing this directory stream has no effect upon the
     * channel.
     *
     * <p>
     *  在此目录中打开或创建文件,返回可查找的字节通道以访问文件。
     * 
     * <p>对于{@code path}参数是{@link Path#isAbsolute absolute}路径的情况,此方法完全按照{@link Files#newByteChannel Files.newByteChannel}
     * 方法指定的方式工作。
     * 当参数是相对路径时,要打开或创建的文件是相对于此打开目录的。
     * 除了{@code Files.newByteChannel}方法定义的选项之外,如果文件是符号链接,可以使用{@link LinkOption#NOFOLLOW_LINKS NOFOLLOW_LINKS}
     * 选项确保此方法失败。
     * 当参数是相对路径时,要打开或创建的文件是相对于此打开目录的。
     * 
     *  <p>频道创建后,不依赖于用于创建它的目录流。关闭此目录流对通道没有影响。
     * 
     * 
     * @param   path
     *          the path of the file to open open or create
     * @param   options
     *          options specifying how the file is opened
     * @param   attrs
     *          an optional list of attributes to set atomically when creating
     *          the file
     *
     * @return  the seekable byte channel
     *
     * @throws  ClosedDirectoryStreamException
     *          if the directory stream is closed
     * @throws  IllegalArgumentException
     *          if the set contains an invalid combination of options
     * @throws  UnsupportedOperationException
     *          if an unsupported open option is specified or the array contains
     *          attributes that cannot be set atomically when creating the file
     * @throws  FileAlreadyExistsException
     *          if a file of that name already exists and the {@link
     *          StandardOpenOption#CREATE_NEW CREATE_NEW} option is specified
     *          <i>(optional specific exception)</i>
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager is
     *          installed, the {@link SecurityManager#checkRead(String) checkRead}
     *          method is invoked to check read access to the path if the file
     *          is opened for reading. The {@link SecurityManager#checkWrite(String)
     *          checkWrite} method is invoked to check write access to the path
     *          if the file is opened for writing.
     */
    SeekableByteChannel newByteChannel(T path,
                                       Set<? extends OpenOption> options,
                                       FileAttribute<?>... attrs)
        throws IOException;

    /**
     * Deletes a file.
     *
     * <p> Unlike the {@link Files#delete delete()} method, this method does
     * not first examine the file to determine if the file is a directory.
     * Whether a directory is deleted by this method is system dependent and
     * therefore not specified. If the file is a symbolic link, then the link
     * itself, not the final target of the link, is deleted. When the
     * parameter is a relative path then the file to delete is relative to
     * this open directory.
     *
     * <p>
     *  删除文件。
     * 
     *  <p>与{@link Files#delete delete()}方法不同,此方法不会首先检查文件以确定文件是否为目录。该方法是否删除目录是系统相关的,因此不指定。
     * 如果文件是符号链接,则链接本身,而不是链接的最终目标,被删除。当参数是相对路径时,要删除的文件是相对于此打开目录的。
     * 
     * 
     * @param   path
     *          the path of the file to delete
     *
     * @throws  ClosedDirectoryStreamException
     *          if the directory stream is closed
     * @throws  NoSuchFileException
     *          if the file does not exist <i>(optional specific exception)</i>
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager is
     *          installed, the {@link SecurityManager#checkDelete(String) checkDelete}
     *          method is invoked to check delete access to the file
     */
    void deleteFile(T path) throws IOException;

    /**
     * Deletes a directory.
     *
     * <p> Unlike the {@link Files#delete delete()} method, this method
     * does not first examine the file to determine if the file is a directory.
     * Whether non-directories are deleted by this method is system dependent and
     * therefore not specified. When the parameter is a relative path then the
     * directory to delete is relative to this open directory.
     *
     * <p>
     *  删除目录。
     * 
     *  <p>与{@link Files#delete delete()}方法不同,此方法不会首先检查文件以确定文件是否为目录。是否通过此方法删除非目录是系统相关的,因此不指定。
     * 当参数是相对路径时,要删除的目录是相对于此打开目录的。
     * 
     * 
     * @param   path
     *          the path of the directory to delete
     *
     * @throws  ClosedDirectoryStreamException
     *          if the directory stream is closed
     * @throws  NoSuchFileException
     *          if the directory does not exist <i>(optional specific exception)</i>
     * @throws  DirectoryNotEmptyException
     *          if the directory could not otherwise be deleted because it is
     *          not empty <i>(optional specific exception)</i>
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager is
     *          installed, the {@link SecurityManager#checkDelete(String) checkDelete}
     *          method is invoked to check delete access to the directory
     */
    void deleteDirectory(T path) throws IOException;

    /**
     * Move a file from this directory to another directory.
     *
     * <p> This method works in a similar manner to {@link Files#move move}
     * method when the {@link StandardCopyOption#ATOMIC_MOVE ATOMIC_MOVE} option
     * is specified. That is, this method moves a file as an atomic file system
     * operation. If the {@code srcpath} parameter is an {@link Path#isAbsolute
     * absolute} path then it locates the source file. If the parameter is a
     * relative path then it is located relative to this open directory. If
     * the {@code targetpath} parameter is absolute then it locates the target
     * file (the {@code targetdir} parameter is ignored). If the parameter is
     * a relative path it is located relative to the open directory identified
     * by the {@code targetdir} parameter. In all cases, if the target file
     * exists then it is implementation specific if it is replaced or this
     * method fails.
     *
     * <p>
     * 将文件从此目录移动到另一个目录。
     * 
     *  <p>当指定{@link StandardCopyOption#ATOMIC_MOVE ATOMIC_MOVE}选项时,此方法的工作方式与{@link Files#move move}方法类似。
     * 也就是说,此方法将文件作为原子文件系统操作移动。如果{@code srcpath}参数是{@link Path#isAbsolute absolute}路径,那么它将定位源文件。
     * 如果参数是相对路径,则它相对于此打开目录定位。如果{@code targetpath}参数是绝对的,那么它将定位目标文件(忽略{@code targetdir}参数)。
     * 如果参数是相对路径,它相对于由{@code targetdir}参数标识的开放目录。在所有情况下,如果目标文件存在,则它是实现特定的,如果它被替换或该方法失败。
     * 
     * 
     * @param   srcpath
     *          the name of the file to move
     * @param   targetdir
     *          the destination directory
     * @param   targetpath
     *          the name to give the file in the destination directory
     *
     * @throws  ClosedDirectoryStreamException
     *          if this or the target directory stream is closed
     * @throws  FileAlreadyExistsException
     *          if the file already exists in the target directory and cannot
     *          be replaced <i>(optional specific exception)</i>
     * @throws  AtomicMoveNotSupportedException
     *          if the file cannot be moved as an atomic file system operation
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager is
     *          installed, the {@link SecurityManager#checkWrite(String) checkWrite}
     *          method is invoked to check write access to both the source and
     *          target file.
     */
    void move(T srcpath, SecureDirectoryStream<T> targetdir, T targetpath)
        throws IOException;

    /**
     * Returns a new file attribute view to access the file attributes of this
     * directory.
     *
     * <p> The resulting file attribute view can be used to read or update the
     * attributes of this (open) directory. The {@code type} parameter specifies
     * the type of the attribute view and the method returns an instance of that
     * type if supported. Invoking this method to obtain a {@link
     * BasicFileAttributeView} always returns an instance of that class that is
     * bound to this open directory.
     *
     * <p> The state of resulting file attribute view is intimately connected
     * to this directory stream. Once the directory stream is {@link #close closed},
     * then all methods to read or update attributes will throw {@link
     * ClosedDirectoryStreamException ClosedDirectoryStreamException}.
     *
     * <p>
     *  返回一个新的文件属性视图,以访问此目录的文件属性。
     * 
     *  <p>生成的文件属性视图可用于读取或更新此(打开的)目录的属性。 {@code type}参数指定属性视图的类型,如果支持,该方法将返回该类型的实例。
     * 调用此方法以获取{@link BasicFileAttributeView}总是返回绑定到此打开目录的类的实例。
     * 
     * <p>生成的文件属性视图的状态与此目录流密切相关。
     * 一旦目录流是{@link #close closed},那么所有读取或更新属性的方法都会抛出{@link ClosedDirectoryStreamException ClosedDirectoryStreamException}
     * 。
     * <p>生成的文件属性视图的状态与此目录流密切相关。
     * 
     * 
     * @param   <V>
     *          The {@code FileAttributeView} type
     * @param   type
     *          the {@code Class} object corresponding to the file attribute view
     *
     * @return  a new file attribute view of the specified type bound to
     *          this directory stream, or {@code null} if the attribute view
     *          type is not available
     */
    <V extends FileAttributeView> V getFileAttributeView(Class<V> type);

    /**
     * Returns a new file attribute view to access the file attributes of a file
     * in this directory.
     *
     * <p> The resulting file attribute view can be used to read or update the
     * attributes of file in this directory. The {@code type} parameter specifies
     * the type of the attribute view and the method returns an instance of that
     * type if supported. Invoking this method to obtain a {@link
     * BasicFileAttributeView} always returns an instance of that class that is
     * bound to the file in the directory.
     *
     * <p> The state of resulting file attribute view is intimately connected
     * to this directory stream. Once the directory stream {@link #close closed},
     * then all methods to read or update attributes will throw {@link
     * ClosedDirectoryStreamException ClosedDirectoryStreamException}. The
     * file is not required to exist at the time that the file attribute view
     * is created but methods to read or update attributes of the file will
     * fail when invoked and the file does not exist.
     *
     * <p>
     *  返回一个新的文件属性视图,以访问此目录中文件的文件属性。
     * 
     *  <p>生成的文件属性视图可用于读取或更新此目录中文件的属性。 {@code type}参数指定属性视图的类型,如果支持,该方法将返回该类型的实例。
     * 调用此方法以获取{@link BasicFileAttributeView}总是返回绑定到目录中文件的类的实例。
     * 
     * 
     * @param   <V>
     *          The {@code FileAttributeView} type
     * @param   path
     *          the path of the file
     * @param   type
     *          the {@code Class} object corresponding to the file attribute view
     * @param   options
     *          options indicating how symbolic links are handled
     *
     * @return  a new file attribute view of the specified type bound to a
     *          this directory stream, or {@code null} if the attribute view
     *          type is not available
     *
     */
    <V extends FileAttributeView> V getFileAttributeView(T path,
                                                         Class<V> type,
                                                         LinkOption... options);
}
