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

/**
 * Basic attributes associated with a file in a file system.
 *
 * <p> Basic file attributes are attributes that are common to many file systems
 * and consist of mandatory and optional file attributes as defined by this
 * interface.
 *
 * <p> <b>Usage Example:</b>
 * <pre>
 *    Path file = ...
 *    BasicFileAttributes attrs = Files.readAttributes(file, BasicFileAttributes.class);
 * </pre>
 *
 * <p>
 *  与文件系统中的文件相关联的基本属性。
 * 
 *  <p>基本文件属性是许多文件系统通用的属性,由此接口定义的强制和可选文件属性组成。
 * 
 *  <p> <b>使用示例：</b>
 * <pre>
 *  Path file = ... BasicFileAttributes attrs = Files.readAttributes(file,BasicFileAttributes.class);
 * </pre>
 * 
 * 
 * @since 1.7
 *
 * @see BasicFileAttributeView
 */

public interface BasicFileAttributes {

    /**
     * Returns the time of last modification.
     *
     * <p> If the file system implementation does not support a time stamp
     * to indicate the time of last modification then this method returns an
     * implementation specific default value, typically a {@code FileTime}
     * representing the epoch (1970-01-01T00:00:00Z).
     *
     * <p>
     *  返回上次修改的时间。
     * 
     *  <p>如果文件系统实现不支持时间戳以指示上次修改的时间,则此方法返回实现特定的默认值,通常为表示时期的{@code FileTime}(1970-01-01T00：00： 00Z)。
     * 
     * 
     * @return  a {@code FileTime} representing the time the file was last
     *          modified
     */
    FileTime lastModifiedTime();

    /**
     * Returns the time of last access.
     *
     * <p> If the file system implementation does not support a time stamp
     * to indicate the time of last access then this method returns
     * an implementation specific default value, typically the {@link
     * #lastModifiedTime() last-modified-time} or a {@code FileTime}
     * representing the epoch (1970-01-01T00:00:00Z).
     *
     * <p>
     *  返回上次访问的时间。
     * 
     *  <p>如果文件系统实现不支持时间戳以指示最后一次访问的时间,则此方法返回一个实现特定的默认值,通常为{@link #lastModifiedTime()last-modified-time}或{代码FileTime}
     * 表示时期(1970-01-01T00：00：00Z)。
     * 
     * 
     * @return  a {@code FileTime} representing the time of last access
     */
    FileTime lastAccessTime();

    /**
     * Returns the creation time. The creation time is the time that the file
     * was created.
     *
     * <p> If the file system implementation does not support a time stamp
     * to indicate the time when the file was created then this method returns
     * an implementation specific default value, typically the {@link
     * #lastModifiedTime() last-modified-time} or a {@code FileTime}
     * representing the epoch (1970-01-01T00:00:00Z).
     *
     * <p>
     *  返回创建时间。创建时间是创建文件的时间。
     * 
     * <p>如果文件系统实现不支持时间戳以指示文件创建的时间,那么此方法将返回一个实现特定的默认值,通常为{@link #lastModifiedTime()last-modified-time}或a {@code FileTime}
     * 表示时代(1970-01-01T00：00：00Z)。
     * 
     * 
     * @return   a {@code FileTime} representing the time the file was created
     */
    FileTime creationTime();

    /**
     * Tells whether the file is a regular file with opaque content.
     *
     * <p>
     *  告诉文件是否是具有不透明内容的常规文件。
     * 
     * 
     * @return {@code true} if the file is a regular file with opaque content
     */
    boolean isRegularFile();

    /**
     * Tells whether the file is a directory.
     *
     * <p>
     *  告诉文件是否是目录。
     * 
     * 
     * @return {@code true} if the file is a directory
     */
    boolean isDirectory();

    /**
     * Tells whether the file is a symbolic link.
     *
     * <p>
     *  告诉文件是否是符号链接。
     * 
     * 
     * @return {@code true} if the file is a symbolic link
     */
    boolean isSymbolicLink();

    /**
     * Tells whether the file is something other than a regular file, directory,
     * or symbolic link.
     *
     * <p>
     *  指示文件是否是除常规文件,目录或符号链接以外的其他文件。
     * 
     * 
     * @return {@code true} if the file something other than a regular file,
     *         directory or symbolic link
     */
    boolean isOther();

    /**
     * Returns the size of the file (in bytes). The size may differ from the
     * actual size on the file system due to compression, support for sparse
     * files, or other reasons. The size of files that are not {@link
     * #isRegularFile regular} files is implementation specific and
     * therefore unspecified.
     *
     * <p>
     *  返回文件的大小(以字节为单位)。由于压缩,支持稀疏文件或其他原因,大小可能与文件系统上的实际大小不同。
     * 不是{@link #isRegularFile regular}文件的文件的大小是实现特定的,因此未指定。
     * 
     * 
     * @return  the file size, in bytes
     */
    long size();

    /**
     * Returns an object that uniquely identifies the given file, or {@code
     * null} if a file key is not available. On some platforms or file systems
     * it is possible to use an identifier, or a combination of identifiers to
     * uniquely identify a file. Such identifiers are important for operations
     * such as file tree traversal in file systems that support <a
     * href="../package-summary.html#links">symbolic links</a> or file systems
     * that allow a file to be an entry in more than one directory. On UNIX file
     * systems, for example, the <em>device ID</em> and <em>inode</em> are
     * commonly used for such purposes.
     *
     * <p> The file key returned by this method can only be guaranteed to be
     * unique if the file system and files remain static. Whether a file system
     * re-uses identifiers after a file is deleted is implementation dependent and
     * therefore unspecified.
     *
     * <p> File keys returned by this method can be compared for equality and are
     * suitable for use in collections. If the file system and files remain static,
     * and two files are the {@link java.nio.file.Files#isSameFile same} with
     * non-{@code null} file keys, then their file keys are equal.
     *
     * <p>
     *  返回唯一标识给定文件的对象,如果文件键不可用,则返回{@code null}。在一些平台或文件系统上,可以使用标识符或标识符的组合来唯一地标识文件。
     * 此类标识符对于诸如支持<a href="../package-summary.html#links">符号链接</a>的文件系统中的文件树遍历或允许文件为条目的文件系统等操作很重要在多个目录中。
     * 例如,在UNIX文件系统上,<em>设备ID </em>和<em> inode </em>通常用于此目的。
     * 
     * <p>如果文件系统和文件保持静态,则此方法返回的文件键只能保证是唯一的。删除文件后文件系统是否重新使用标识符是实现相关的,因此未指定。
     * 
     * @return an object that uniquely identifies the given file, or {@code null}
     *
     * @see java.nio.file.Files#walkFileTree
     */
    Object fileKey();
}
