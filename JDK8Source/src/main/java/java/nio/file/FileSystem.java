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
import java.nio.file.spi.FileSystemProvider;
import java.util.Set;
import java.io.Closeable;
import java.io.IOException;

/**
 * Provides an interface to a file system and is the factory for objects to
 * access files and other objects in the file system.
 *
 * <p> The default file system, obtained by invoking the {@link FileSystems#getDefault
 * FileSystems.getDefault} method, provides access to the file system that is
 * accessible to the Java virtual machine. The {@link FileSystems} class defines
 * methods to create file systems that provide access to other types of (custom)
 * file systems.
 *
 * <p> A file system is the factory for several types of objects:
 *
 * <ul>
 *   <li><p> The {@link #getPath getPath} method converts a system dependent
 *     <em>path string</em>, returning a {@link Path} object that may be used
 *     to locate and access a file. </p></li>
 *   <li><p> The {@link #getPathMatcher  getPathMatcher} method is used
 *     to create a {@link PathMatcher} that performs match operations on
 *     paths. </p></li>
 *   <li><p> The {@link #getFileStores getFileStores} method returns an iterator
 *     over the underlying {@link FileStore file-stores}. </p></li>
 *   <li><p> The {@link #getUserPrincipalLookupService getUserPrincipalLookupService}
 *     method returns the {@link UserPrincipalLookupService} to lookup users or
 *     groups by name. </p></li>
 *   <li><p> The {@link #newWatchService newWatchService} method creates a
 *     {@link WatchService} that may be used to watch objects for changes and
 *     events. </p></li>
 * </ul>
 *
 * <p> File systems vary greatly. In some cases the file system is a single
 * hierarchy of files with one top-level root directory. In other cases it may
 * have several distinct file hierarchies, each with its own top-level root
 * directory. The {@link #getRootDirectories getRootDirectories} method may be
 * used to iterate over the root directories in the file system. A file system
 * is typically composed of one or more underlying {@link FileStore file-stores}
 * that provide the storage for the files. Theses file stores can also vary in
 * the features they support, and the file attributes or <em>meta-data</em> that
 * they associate with files.
 *
 * <p> A file system is open upon creation and can be closed by invoking its
 * {@link #close() close} method. Once closed, any further attempt to access
 * objects in the file system cause {@link ClosedFileSystemException} to be
 * thrown. File systems created by the default {@link FileSystemProvider provider}
 * cannot be closed.
 *
 * <p> A {@code FileSystem} can provide read-only or read-write access to the
 * file system. Whether or not a file system provides read-only access is
 * established when the {@code FileSystem} is created and can be tested by invoking
 * its {@link #isReadOnly() isReadOnly} method. Attempts to write to file stores
 * by means of an object associated with a read-only file system throws {@link
 * ReadOnlyFileSystemException}.
 *
 * <p> File systems are safe for use by multiple concurrent threads. The {@link
 * #close close} method may be invoked at any time to close a file system but
 * whether a file system is <i>asynchronously closeable</i> is provider specific
 * and therefore unspecified. In other words, if a thread is accessing an
 * object in a file system, and another thread invokes the {@code close} method
 * then it may require to block until the first operation is complete. Closing
 * a file system causes all open channels, watch services, and other {@link
 * Closeable closeable} objects associated with the file system to be closed.
 *
 * <p>
 *  提供文件系统的接口,是对象访问文件系统中的文件和其他对象的工厂
 * 
 *  <p>通过调用{@link FileSystems#getDefault FileSystemsgetDefault}方法获得的默认文件系统提供对Java虚拟机可访问的文件系统的访问。
 * {@link FileSystems}类定义了创建文件系统的方法提供对其他类型(自定义)文件系统的访问。
 * 
 *  <p>文件系统是几种类型对象的工厂：
 * 
 * <ul>
 * <li> <p> {@link #getPath getPath}方法转换系统依赖的<em>路径字符串</em>,返回可用于定位和访问文件的{@link Path}对象</p > </li> </li>
 *  </li> </li> </li> <li> <p> {@link #getPathMatcher getPathMatcher}方法用于创建{@link PathMatcher} {@link #getFileStores getFileStores}
 * 方法在底层的{@link FileStore file-stores}中返回一个迭代器</p> </li> <li> <p> {@link #getUserPrincipalLookupService getUserPrincipalLookupService}
 * 方法返回{@链接UserPrincipalLookupService}按名称查找用户或组</p> </li> <li> <p> {@link #newWatchService newWatchService}
 * 方法创建一个{@link WatchService}事件</p> </DO>。
 * </ul>
 * 
 * <p>文件系统差别很大在某些情况下,文件系统是具有一个顶级根目录的文件的单个层次结构在其他情况下,它可能有几个不同的文件层次结构,每个都有自己的顶级根目录{@link #getRootDirectories getRootDirectories}
 * 方法可以用于遍历文件系统中的根目录文件系统通常由一个或多个底层{@link FileStore文件存储}组成,为文件提供存储Theses文件存储也可以改变在他们支持的功能,以及他们与文件相关联的文件属性
 * 或<em>元数据</em>。
 * 
 * <p>文件系统在创建时打开,可以通过调用其{@link #close()close}方法关闭文件系统一旦关闭,任何进一步尝试访问文件系统中的对象都会导致{@link ClosedFileSystemException}
 * 被抛出File无法关闭由缺省{@link FileSystemProvider provider}创建的系统。
 * 
 *  <p> {@code FileSystem}可以提供对文件系统的只读或读写访问在创建{@code FileSystem}时可以建立文件系统是否提供只读访问,并且可以通过调用其{@link #isReadOnly()isReadOnly}
 * 方法尝试通过与只读文件系统关联的对象写入文件存储将抛出{@link ReadOnlyFileSystemException}。
 * 
 * <p>多个并发线程可以安全使用文件系统可以随时调用{@link #close close}方法关闭文件系统,但文件系统是否可异步关闭</i>是提供程序具体的并且因此未指定换句话说,如果线程正在访问文件系
 * 统中的对象,并且另一线程调用{@code close}方法,则它可能需要阻塞,直到第一操作完成。
 * 关闭文件系统导致所有打开频道,观看服务和与要关闭的文件系统相关联的其他{@link Closeable closeable}对象。
 * 
 * 
 * @since 1.7
 */

public abstract class FileSystem
    implements Closeable
{
    /**
     * Initializes a new instance of this class.
     * <p>
     *  初始化此类的新实例
     * 
     */
    protected FileSystem() {
    }

    /**
     * Returns the provider that created this file system.
     *
     * <p>
     *  返回创建此文件系统的提供程序
     * 
     * 
     * @return  The provider that created this file system.
     */
    public abstract FileSystemProvider provider();

    /**
     * Closes this file system.
     *
     * <p> After a file system is closed then all subsequent access to the file
     * system, either by methods defined by this class or on objects associated
     * with this file system, throw {@link ClosedFileSystemException}. If the
     * file system is already closed then invoking this method has no effect.
     *
     * <p> Closing a file system will close all open {@link
     * java.nio.channels.Channel channels}, {@link DirectoryStream directory-streams},
     * {@link WatchService watch-service}, and other closeable objects associated
     * with this file system. The {@link FileSystems#getDefault default} file
     * system cannot be closed.
     *
     * <p>
     *  关闭此文件系统
     * 
     * <p>在文件系统关闭后,通过此类或与此文件系统相关的对象定义的方法对文件系统的所有后续访问throw {@link ClosedFileSystemException}如果文件系统已关闭,则调用此方法方
     * 法没有效果。
     * 
     *  <p>关闭文件系统将关闭所有打开的{@link javaniochannels频道渠道},{@link DirectoryStream目录流},{@link WatchService watch-service}
     * 和与此文件系统相关联的其他可关闭对象{@link FileSystems #getDefault default}文件系统无法关闭。
     * 
     * 
     * @throws  IOException
     *          If an I/O error occurs
     * @throws  UnsupportedOperationException
     *          Thrown in the case of the default file system
     */
    @Override
    public abstract void close() throws IOException;

    /**
     * Tells whether or not this file system is open.
     *
     * <p> File systems created by the default provider are always open.
     *
     * <p>
     *  指示此文件系统是否已打开
     * 
     *  <p>默认提供程序创建的文件系统始终处于打开状态
     * 
     * 
     * @return  {@code true} if, and only if, this file system is open
     */
    public abstract boolean isOpen();

    /**
     * Tells whether or not this file system allows only read-only access to
     * its file stores.
     *
     * <p>
     *  指示此文件系统是否仅允许对其文件存储进行只读访问
     * 
     * 
     * @return  {@code true} if, and only if, this file system provides
     *          read-only access
     */
    public abstract boolean isReadOnly();

    /**
     * Returns the name separator, represented as a string.
     *
     * <p> The name separator is used to separate names in a path string. An
     * implementation may support multiple name separators in which case this
     * method returns an implementation specific <em>default</em> name separator.
     * This separator is used when creating path strings by invoking the {@link
     * Path#toString() toString()} method.
     *
     * <p> In the case of the default provider, this method returns the same
     * separator as {@link java.io.File#separator}.
     *
     * <p>
     * 返回名称分隔符,表示为字符串
     * 
     *  <p>名称分隔符用于分隔路径字符串中的名称实现可以支持多个名称分隔符,在这种情况下,此方法返回具体的实施<em> default </em> name separator此分隔符在创建路径字符串时使用
     * 调用{@link Path#toString()toString()}方法。
     * 
     *  <p>在默认提供程序的情况下,此方法返回与{@link javaioFile#separator}相同的分隔符,
     * 
     * 
     * @return  The name separator
     */
    public abstract String getSeparator();

    /**
     * Returns an object to iterate over the paths of the root directories.
     *
     * <p> A file system provides access to a file store that may be composed
     * of a number of distinct file hierarchies, each with its own top-level
     * root directory. Unless denied by the security manager, each element in
     * the returned iterator corresponds to the root directory of a distinct
     * file hierarchy. The order of the elements is not defined. The file
     * hierarchies may change during the lifetime of the Java virtual machine.
     * For example, in some implementations, the insertion of removable media
     * may result in the creation of a new file hierarchy with its own
     * top-level directory.
     *
     * <p> When a security manager is installed, it is invoked to check access
     * to the each root directory. If denied, the root directory is not returned
     * by the iterator. In the case of the default provider, the {@link
     * SecurityManager#checkRead(String)} method is invoked to check read access
     * to each root directory. It is system dependent if the permission checks
     * are done when the iterator is obtained or during iteration.
     *
     * <p>
     *  返回要遍历根目录路径的对象
     * 
     * <p>文件系统提供对可由多个不同文件层次结构组成的文件存储的访问,每个文件层次都有自己的顶级根目录。
     * 除非被安全管理器拒绝,返回的迭代器中的每个元素对应于根不同文件层次结构的目录未定义元素的顺序文件层次结构可能在Java虚拟机的生存期内发生变化例如,在某些实现中,插入可移动介质可能导致创建新的文件层次结
     * 构它自己的顶级目录。
     * <p>文件系统提供对可由多个不同文件层次结构组成的文件存储的访问,每个文件层次都有自己的顶级根目录。
     * 
     * <p>安装安全管理器时,会调用它来检查对每个根目录的访问权限如果拒绝,迭代器不会返回根目录在默认提供程序的情况下,{@link SecurityManager#checkRead(String )}方法
     * 来检查对每个根目录的读访问权限如果在获取迭代器时或在迭代期间执行权限检查,则系统依赖。
     * 
     * 
     * @return  An object to iterate over the root directories
     */
    public abstract Iterable<Path> getRootDirectories();

    /**
     * Returns an object to iterate over the underlying file stores.
     *
     * <p> The elements of the returned iterator are the {@link
     * FileStore FileStores} for this file system. The order of the elements is
     * not defined and the file stores may change during the lifetime of the
     * Java virtual machine. When an I/O error occurs, perhaps because a file
     * store is not accessible, then it is not returned by the iterator.
     *
     * <p> In the case of the default provider, and a security manager is
     * installed, the security manager is invoked to check {@link
     * RuntimePermission}<tt>("getFileStoreAttributes")</tt>. If denied, then
     * no file stores are returned by the iterator. In addition, the security
     * manager's {@link SecurityManager#checkRead(String)} method is invoked to
     * check read access to the file store's <em>top-most</em> directory. If
     * denied, the file store is not returned by the iterator. It is system
     * dependent if the permission checks are done when the iterator is obtained
     * or during iteration.
     *
     * <p> <b>Usage Example:</b>
     * Suppose we want to print the space usage for all file stores:
     * <pre>
     *     for (FileStore store: FileSystems.getDefault().getFileStores()) {
     *         long total = store.getTotalSpace() / 1024;
     *         long used = (store.getTotalSpace() - store.getUnallocatedSpace()) / 1024;
     *         long avail = store.getUsableSpace() / 1024;
     *         System.out.format("%-20s %12d %12d %12d%n", store, total, used, avail);
     *     }
     * </pre>
     *
     * <p>
     *  返回要在底层文件存储上迭代的对象
     * 
     * <p>返回的迭代器的元素是此文件系统的{@link FileStore FileStores}元素的顺序未定义,并且文件存储可能在Java虚拟机的生存期内发生更改发生I / O错误时,也许是因为文件存储
     * 不可访问,那么它不会由迭代器返回。
     * 
     * <p>在默认提供程序的情况下,安装了安全管理器,将调用安全管理器检查{@link RuntimePermission} <tt>("getFileStoreAttributes")</tt>如果被拒绝,
     * 由迭代器返回此外,调用安全管理器的{@link SecurityManager#checkRead(String)}方法来检查文件存储的最顶层目录的读访问权如果被拒绝,文件存储不是由迭代器返回如果权限检
     * 查在获取迭代器或迭代期间完成,则系统依赖。
     * 
     *  <p> <b>使用示例：</b>假设我们要打印所有文件存储的空间使用情况：
     * <pre>
     * 对于(文件存储大：FileSystemsgetDefault()getFileStores()){长=总大型私人总面积()/ 1024;苦恼=长(大型私人总面积() -  storegetUnallocatedSpace())/ 1024;长果= storegetUsableSpace()/ 1024; Systemoutformat("％ -  20年代％12D 12D％12D％％N",大,全,滥用,利用); }
     * 。
     * </pre>
     * 
     * 
     * @return  An object to iterate over the backing file stores
     */
    public abstract Iterable<FileStore> getFileStores();

    /**
     * Returns the set of the {@link FileAttributeView#name names} of the file
     * attribute views supported by this {@code FileSystem}.
     *
     * <p> The {@link BasicFileAttributeView} is required to be supported and
     * therefore the set contains at least one element, "basic".
     *
     * <p> The {@link FileStore#supportsFileAttributeView(String)
     * supportsFileAttributeView(String)} method may be used to test if an
     * underlying {@link FileStore} supports the file attributes identified by a
     * file attribute view.
     *
     * <p>
     *  返回集的文件的{@link FileAttributeView#指名道姓}的属性本{@code文件系统支持}意见
     * 
     *  <P>该{@link BasicFileAttributeView}需要得到支持和有一组包含至少一种元素,"基本的"
     * 
     *  <P>而{@link文件存储#supportsFileAttributeView(字符串)supportsFileAttributeView(字符串)}方法根过度使用两个测试如果基础{@link文件存储}
     * 支持该文件通过文件属性查看属性identified`。
     * 
     * 
     * @return  An unmodifiable set of the names of the supported file attribute
     *          views
     */
    public abstract Set<String> supportedFileAttributeViews();

    /**
     * Converts a path string, or a sequence of strings that when joined form
     * a path string, to a {@code Path}. If {@code more} does not specify any
     * elements then the value of the {@code first} parameter is the path string
     * to convert. If {@code more} specifies one or more elements then each
     * non-empty string, including {@code first}, is considered to be a sequence
     * of name elements (see {@link Path}) and is joined to form a path string.
     * The details as to how the Strings are joined is provider specific but
     * typically they will be joined using the {@link #getSeparator
     * name-separator} as the separator. For example, if the name separator is
     * "{@code /}" and {@code getPath("/foo","bar","gus")} is invoked, then the
     * path string {@code "/foo/bar/gus"} is converted to a {@code Path}.
     * A {@code Path} representing an empty path is returned if {@code first}
     * is the empty string and {@code more} does not contain any non-empty
     * strings.
     *
     * <p> The parsing and conversion to a path object is inherently
     * implementation dependent. In the simplest case, the path string is rejected,
     * and {@link InvalidPathException} thrown, if the path string contains
     * characters that cannot be converted to characters that are <em>legal</em>
     * to the file store. For example, on UNIX systems, the NUL (&#92;u0000)
     * character is not allowed to be present in a path. An implementation may
     * choose to reject path strings that contain names that are longer than those
     * allowed by any file store, and where an implementation supports a complex
     * path syntax, it may choose to reject path strings that are <em>badly
     * formed</em>.
     *
     * <p> In the case of the default provider, path strings are parsed based
     * on the definition of paths at the platform or virtual file system level.
     * For example, an operating system may not allow specific characters to be
     * present in a file name, but a specific underlying file store may impose
     * different or additional restrictions on the set of legal
     * characters.
     *
     * <p> This method throws {@link InvalidPathException} when the path string
     * cannot be converted to a path. Where possible, and where applicable,
     * the exception is created with an {@link InvalidPathException#getIndex
     * index} value indicating the first position in the {@code path} parameter
     * that caused the path string to be rejected.
     *
     * <p>
     * 将连接时形成路径字符串的路径字符串或字符串序列转换为{@code Path}如果{@code more}未指定任何元素,则{@code first}参数的值为路径string to convert如果{@code more}
     * 指定一个或多个元素,则每个非空字符串(包括{@code first})都被认为是一个名称元素序列(参见{@link Path}),路径字符串关于字符串如何连接的细节是特定于提供程序的,但是通常它们将使用
     * {@link #getSeparator name-separator}作为分隔符连接。
     * 例如,如果名称分隔符是"{@code /}"和{@code getPath("/ foo","bar","gus")}被调用,则路径字符串{@code"/ foo / bar / gus"}被转换为{@code Path}
     * 如果{@code first}是空字符串,而{@code more}不包含任何非空字符串,则返回表示空路径的{@code Path}。
     * 
     * <p>解析和转换为路径对象本质上是依赖于实现的在最简单的情况下,路径字符串被拒绝,并且{@link InvalidPathException}抛出,如果路径字符串包含不能转换为<em > legal </em>
     * 到文件存储区例如,在UNIX系统上,不允许在路径中存在NUL(\\ u0000)字符实现可以选择拒绝包含长于那些允许的名称的路径字符串通过任何文件存储,并且其中实现支持复杂路径语法,其可以选择拒绝错误地
     * 形成的路径字符串</em>。
     * 
     * <p>在默认提供程序的情况下,基于平台或虚拟文件系统级别的路径定义解析路径字符串。例如,操作系统可能不允许特定字符出现在文件名中,但是特定基础文件存储可以对合法字符集合施加不同的或附加的限制
     * 
     *  <p>当路径字符串无法转换为路径时,此方法会抛出{@link InvalidPathException}在可能的情况下,使用{@link InvalidPathException#getIndex index}
     * 值创建异常,该值指示{ @code path}参数,导致路径字符串被拒绝。
     * 
     * 
     * @param   first
     *          the path string or initial part of the path string
     * @param   more
     *          additional strings to be joined to form the path string
     *
     * @return  the resulting {@code Path}
     *
     * @throws  InvalidPathException
     *          If the path string cannot be converted
     */
    public abstract Path getPath(String first, String... more);

    /**
     * Returns a {@code PathMatcher} that performs match operations on the
     * {@code String} representation of {@link Path} objects by interpreting a
     * given pattern.
     *
     * The {@code syntaxAndPattern} parameter identifies the syntax and the
     * pattern and takes the form:
     * <blockquote><pre>
     * <i>syntax</i><b>:</b><i>pattern</i>
     * </pre></blockquote>
     * where {@code ':'} stands for itself.
     *
     * <p> A {@code FileSystem} implementation supports the "{@code glob}" and
     * "{@code regex}" syntaxes, and may support others. The value of the syntax
     * component is compared without regard to case.
     *
     * <p> When the syntax is "{@code glob}" then the {@code String}
     * representation of the path is matched using a limited pattern language
     * that resembles regular expressions but with a simpler syntax. For example:
     *
     * <blockquote>
     * <table border="0" summary="Pattern Language">
     * <tr>
     *   <td>{@code *.java}</td>
     *   <td>Matches a path that represents a file name ending in {@code .java}</td>
     * </tr>
     * <tr>
     *   <td>{@code *.*}</td>
     *   <td>Matches file names containing a dot</td>
     * </tr>
     * <tr>
     *   <td>{@code *.{java,class}}</td>
     *   <td>Matches file names ending with {@code .java} or {@code .class}</td>
     * </tr>
     * <tr>
     *   <td>{@code foo.?}</td>
     *   <td>Matches file names starting with {@code foo.} and a single
     *   character extension</td>
     * </tr>
     * <tr>
     *   <td><tt>&#47;home&#47;*&#47;*</tt>
     *   <td>Matches <tt>&#47;home&#47;gus&#47;data</tt> on UNIX platforms</td>
     * </tr>
     * <tr>
     *   <td><tt>&#47;home&#47;**</tt>
     *   <td>Matches <tt>&#47;home&#47;gus</tt> and
     *   <tt>&#47;home&#47;gus&#47;data</tt> on UNIX platforms</td>
     * </tr>
     * <tr>
     *   <td><tt>C:&#92;&#92;*</tt>
     *   <td>Matches <tt>C:&#92;foo</tt> and <tt>C:&#92;bar</tt> on the Windows
     *   platform (note that the backslash is escaped; as a string literal in the
     *   Java Language the pattern would be <tt>"C:&#92;&#92;&#92;&#92;*"</tt>) </td>
     * </tr>
     *
     * </table>
     * </blockquote>
     *
     * <p> The following rules are used to interpret glob patterns:
     *
     * <ul>
     *   <li><p> The {@code *} character matches zero or more {@link Character
     *   characters} of a {@link Path#getName(int) name} component without
     *   crossing directory boundaries. </p></li>
     *
     *   <li><p> The {@code **} characters matches zero or more {@link Character
     *   characters} crossing directory boundaries. </p></li>
     *
     *   <li><p> The {@code ?} character matches exactly one character of a
     *   name component.</p></li>
     *
     *   <li><p> The backslash character ({@code \}) is used to escape characters
     *   that would otherwise be interpreted as special characters. The expression
     *   {@code \\} matches a single backslash and "\{" matches a left brace
     *   for example.  </p></li>
     *
     *   <li><p> The {@code [ ]} characters are a <i>bracket expression</i> that
     *   match a single character of a name component out of a set of characters.
     *   For example, {@code [abc]} matches {@code "a"}, {@code "b"}, or {@code "c"}.
     *   The hyphen ({@code -}) may be used to specify a range so {@code [a-z]}
     *   specifies a range that matches from {@code "a"} to {@code "z"} (inclusive).
     *   These forms can be mixed so [abce-g] matches {@code "a"}, {@code "b"},
     *   {@code "c"}, {@code "e"}, {@code "f"} or {@code "g"}. If the character
     *   after the {@code [} is a {@code !} then it is used for negation so {@code
     *   [!a-c]} matches any character except {@code "a"}, {@code "b"}, or {@code
     *   "c"}.
     *   <p> Within a bracket expression the {@code *}, {@code ?} and {@code \}
     *   characters match themselves. The ({@code -}) character matches itself if
     *   it is the first character within the brackets, or the first character
     *   after the {@code !} if negating.</p></li>
     *
     *   <li><p> The {@code { }} characters are a group of subpatterns, where
     *   the group matches if any subpattern in the group matches. The {@code ","}
     *   character is used to separate the subpatterns. Groups cannot be nested.
     *   </p></li>
     *
     *   <li><p> Leading period<tt>&#47;</tt>dot characters in file name are
     *   treated as regular characters in match operations. For example,
     *   the {@code "*"} glob pattern matches file name {@code ".login"}.
     *   The {@link Files#isHidden} method may be used to test whether a file
     *   is considered hidden.
     *   </p></li>
     *
     *   <li><p> All other characters match themselves in an implementation
     *   dependent manner. This includes characters representing any {@link
     *   FileSystem#getSeparator name-separators}. </p></li>
     *
     *   <li><p> The matching of {@link Path#getRoot root} components is highly
     *   implementation-dependent and is not specified. </p></li>
     *
     * </ul>
     *
     * <p> When the syntax is "{@code regex}" then the pattern component is a
     * regular expression as defined by the {@link java.util.regex.Pattern}
     * class.
     *
     * <p>  For both the glob and regex syntaxes, the matching details, such as
     * whether the matching is case sensitive, are implementation-dependent
     * and therefore not specified.
     *
     * <p>
     * 返回{@code PathMatcher},通过解释给定模式,对{@link Path}对象的{@code String}表示执行匹配操作
     * 
     *  {@code syntaxAndPattern}参数标识语法和模式,并采用以下形式：<blockquote> <pre> <i>语法</i> <b>：</b> <i> pre> </blockquote>
     * 其中{@code'：'}代表自身。
     * 
     *  <p> {@code FileSystem}实现支持"{@code glob}"和"{@code regex}"语法,并且可以支持其他语法组件的值在不考虑大小写的情况下进行比较
     * 
     *  <p>当语法是"{@code glob}"时,使用有限模式语言来匹配路径的{@code String}表示,该语言类似于正则表达式,但语法更简单例如：
     * 
     * <blockquote>
     * <table border="0" summary="Pattern Language">
     * <tr>
     * <td> {@ code * java} </td> <td>匹配表示以{@code java}结尾的文件名的路径</td>
     * </tr>
     * <tr>
     *  <td> {@ code **} </td> <td>匹配包含点的文件名</td>
     * </tr>
     * <tr>
     *  <td> {@ code * {java,class}} </td> <td>匹配以{@code java}或{@code class}结尾的文件名</td>
     * </tr>
     * <tr>
     *  <td> {@ code foo?} </td> <td>匹配以{@code foo}开头的文件名和单个字符扩展名</td>
     * </tr>
     * <tr>
     *  <td> <tt> / home / * / * </tt> <td>在UNIX平台上匹配<tt> / home / gus / data </tt>
     * </tr>
     * <tr>
     *  <td> <tt> / home / ** </tt> <td>在UNIX平台上匹配<tt> / home / gus </tt>和<tt> / home / gus / data </tt> >
     * </tr>
     * <tr>
     * 在Windows平台上的<td> <tt> C：\\\\ * </tt> <td>匹配<tt> C：\\ foo </tt>和<tt> C：\\ bar </tt>反斜杠被转义;作为Java语言中的字符
     * 串文字,模式将是<tt>"C：\\\\\\\\ *"</tt>)</td>。
     * </tr>
     * 
     * </table>
     * </blockquote>
     * 
     *  <p>以下规则用于解释glob模式：
     * 
     * <ul>
     *  <li> <p> {@code *}字符匹配{@link Path#getName(int)name}组件中的零个或多个{@link字符},但不跨越目录边界</p>
     * 
     * 
     * @param   syntaxAndPattern
     *          The syntax and pattern
     *
     * @return  A path matcher that may be used to match paths against the pattern
     *
     * @throws  IllegalArgumentException
     *          If the parameter does not take the form: {@code syntax:pattern}
     * @throws  java.util.regex.PatternSyntaxException
     *          If the pattern is invalid
     * @throws  UnsupportedOperationException
     *          If the pattern syntax is not known to the implementation
     *
     * @see Files#newDirectoryStream(Path,String)
     */
    public abstract PathMatcher getPathMatcher(String syntaxAndPattern);

    /**
     * Returns the {@code UserPrincipalLookupService} for this file system
     * <i>(optional operation)</i>. The resulting lookup service may be used to
     * lookup user or group names.
     *
     * <p> <b>Usage Example:</b>
     * Suppose we want to make "joe" the owner of a file:
     * <pre>
     *     UserPrincipalLookupService lookupService = FileSystems.getDefault().getUserPrincipalLookupService();
     *     Files.setOwner(path, lookupService.lookupPrincipalByName("joe"));
     * </pre>
     *
     * <p>
     *  <li> <p> {@code **}个字符与零个或多个{@link字符}交叉目录边界匹配</p> </li>
     * 
     *  <li> <p> {@code?}字符与名称组件的一个字符</p> </li>匹配
     * 
     * <li> <p>反斜杠字符({@code \\})用于转义否则将被解释为特殊字符的字符表达式{@code \\\\}匹配单个反斜杠,"\\ {"匹配左括号例如</p> </li>
     * 
     * <li> <p> {@code []}字符是一个<i>括号表达式</i>,与一组字符中的名称组件的单个字符匹配例如,{@code [abc]}匹配{@code"a"},{@code"b"}或{@code"c"}
     * 连字符({@code  - })可用于指定一个范围,因此{@code [az]一个范围从{@code"a"}到{@code"z"}(含)匹配这些表单可以混合,因此[abce-g]匹配{@code"a"}
     * ,{@code"b"} ,{@code"c"},{@code"e"},{@code"f"}或{@code"g"}如果{@code [})后面的字符是{@code！}它用于否定,因此{@code [！ac]}
     * 匹配除{@code"a"},{@code"b"}或{@code"c"}之外的任何字符。
     *  {@code *},{@code?}和{@code \\}字符匹配如果{@code  - })字符是括号内的第一个字符,或{@code！}如果取反</p> </li>。
     * 
     * <li> <p> {@code {}}字符是一组子模式,如果组中的任何子模式匹配,则匹配。{@code},"}字符用于分隔子模式组不能嵌套</p> </li>
     * 
     *  <li> <p>文件名中的前导周期<tt> / </tt>点字符在匹配操作中被视为常规字符例如,{@code"*"} glob模式匹配文件名{@code} login "} {@link Files#isHidden}
     * 方法可用于测试文件是否被视为隐藏</p> </li>。
     * 
     *  <li> <p>所有其他字符以实现相关方式匹配自己。这包括表示任何{@link FileSystem#getSeparator名称 - 分隔符}的字符</p> </li>
     * 
     * <li> <p> {@link Path#getRoot root}组件的匹配是高度实现相关的,未指定</p> </li>
     * 
     * 
     * @throws  UnsupportedOperationException
     *          If this {@code FileSystem} does not does have a lookup service
     *
     * @return  The {@code UserPrincipalLookupService} for this file system
     */
    public abstract UserPrincipalLookupService getUserPrincipalLookupService();

    /**
     * Constructs a new {@link WatchService} <i>(optional operation)</i>.
     *
     * <p> This method constructs a new watch service that may be used to watch
     * registered objects for changes and events.
     *
     * <p>
     * </ul>
     * 
     *  <p>当语法是"{@code regex}"时,模式组件是由{@link javautilregexPattern}类定义的正则表达式
     * 
     *  <p>对于glob和regex语法,匹配详细信息(例如匹配是否区分大小写)都是与实现相关的,因此未指定
     * 
     * 
     * @return  a new watch service
     *
     * @throws  UnsupportedOperationException
     *          If this {@code FileSystem} does not support watching file system
     *          objects for changes and events. This exception is not thrown
     *          by {@code FileSystems} created by the default provider.
     * @throws  IOException
     *          If an I/O error occurs
     */
    public abstract WatchService newWatchService() throws IOException;
}
