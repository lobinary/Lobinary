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
 * Defines interfaces and classes for the Java virtual machine to access files,
 * file attributes, and file systems.
 *
 * <p> The java.nio.file package defines classes to access files and file
 * systems. The API to access file and file system attributes is defined in the
 * {@link java.nio.file.attribute} package. The {@link java.nio.file.spi}
 * package is used by service provider implementors wishing to extend the
 * platform default provider, or to construct other provider implementations. </p>
 *
 * <h3><a name="links">Symbolic Links</a></h3>
 * <p> Many operating systems and file systems support for <em>symbolic links</em>.
 * A symbolic link is a special file that serves as a reference to another file.
 * For the most part, symbolic links are transparent to applications and
 * operations on symbolic links are automatically redirected to the <em>target</em>
 * of the link. Exceptions to this are when a symbolic link is deleted or
 * renamed/moved in which case the link is deleted or removed rather than the
 * target of the link. This package includes support for symbolic links where
 * implementations provide these semantics. File systems may support other types
 * that are semantically close but support for these other types of links is
 * not included in this package. </p>
 *
 * <h3><a name="interop">Interoperability</a></h3>
 * <p> The {@link java.io.File} class defines the {@link java.io.File#toPath
 * toPath} method to construct a {@link java.nio.file.Path} by converting
 * the abstract path represented by the {@code java.io.File} object. The resulting
 * {@code Path} can be used to operate on the same file as the {@code File}
 * object. The {@code Path} specification provides further information
 * on the <a href="Path.html#interop">interoperability</a> between {@code Path}
 * and {@code java.io.File} objects. </p>
 *
 * <h3>Visibility</h3>
 * <p> The view of the files and file system provided by classes in this package are
 * guaranteed to be consistent with other views provided by other instances in the
 * same Java virtual machine.  The view may or may not, however, be consistent with
 * the view of the file system as seen by other concurrently running programs due
 * to caching performed by the underlying operating system and delays induced by
 * network-filesystem protocols. This is true regardless of the language in which
 * these other programs are written, and whether they are running on the same machine
 * or on some other machine.  The exact nature of any such inconsistencies are
 * system-dependent and are therefore unspecified. </p>
 *
 * <h3><a name="integrity">Synchronized I/O File Integrity</a></h3>
 * <p> The {@link java.nio.file.StandardOpenOption#SYNC SYNC} and {@link
 * java.nio.file.StandardOpenOption#DSYNC DSYNC} options are used when opening a file
 * to require that updates to the file are written synchronously to the underlying
 * storage device. In the case of the default provider, and the file resides on
 * a local storage device, and the {@link java.nio.channels.SeekableByteChannel
 * seekable} channel is connected to a file that was opened with one of these
 * options, then an invocation of the {@link
 * java.nio.channels.WritableByteChannel#write(java.nio.ByteBuffer) write}
 * method is only guaranteed to return when all changes made to the file
 * by that invocation have been written to the device. These options are useful
 * for ensuring that critical information is not lost in the event of a system
 * crash. If the file does not reside on a local device then no such guarantee
 * is made. Whether this guarantee is possible with other {@link
 * java.nio.file.spi.FileSystemProvider provider} implementations is provider
 * specific. </p>
 *
 * <h3>General Exceptions</h3>
 * <p> Unless otherwise noted, passing a {@code null} argument to a constructor
 * or method of any class or interface in this package will cause a {@link
 * java.lang.NullPointerException NullPointerException} to be thrown. Additionally,
 * invoking a method with a collection containing a {@code null} element will
 * cause a {@code NullPointerException}, unless otherwise specified. </p>
 *
 * <p> Unless otherwise noted, methods that attempt to access the file system
 * will throw {@link java.nio.file.ClosedFileSystemException} when invoked on
 * objects associated with a {@link java.nio.file.FileSystem} that has been
 * {@link java.nio.file.FileSystem#close closed}. Additionally, any methods
 * that attempt write access to a file system will throw {@link
 * java.nio.file.ReadOnlyFileSystemException} when invoked on an object associated
 * with a {@link java.nio.file.FileSystem} that only provides read-only
 * access. </p>
 *
 * <p> Unless otherwise noted, invoking a method of any class or interface in
 * this package created by one {@link java.nio.file.spi.FileSystemProvider
 * provider} with a parameter that is an object created by another provider,
 * will throw {@link java.nio.file.ProviderMismatchException}. </p>
 *
 * <h3>Optional Specific Exceptions</h3>
 * Most of the methods defined by classes in this package that access the
 * file system specify that {@link java.io.IOException} be thrown when an I/O
 * error occurs. In some cases, these methods define specific I/O exceptions
 * for common cases. These exceptions, noted as <i>optional specific exceptions</i>,
 * are thrown by the implementation where it can detect the specific error.
 * Where the specific error cannot be detected then the more general {@code
 * IOException} is thrown.
 *
 * <p>
 *  定义Java虚拟机访问文件,文件属性和文件系统的接口和类。
 * 
 *  <p> java.nio.file包定义了访问文件和文件系统的类。用于访问文件和文件系统属性的API在{@link java.nio.file.attribute}包中定义。
 *  {@link java.nio.file.spi}包由希望扩展平台缺省提供程序的服务提供程序实现者使用,或者用于构建其他提供程序实现。 </p>。
 * 
 *  <h3> <a name="links">符号链接</a> </h3> <p>许多操作系统和文件系统支持<em>符号链接</em>。符号链接是用作对另一个文件的引用的特殊文件。
 * 在大多数情况下,符号链接对于应用程序是透明的,并且符号链接上的操作被自动重定向到链接的<em>目标</em>。例外情况是,当符号链接被删除或重命名/移动时,链接被删除或删除,而不是链接的目标。
 * 这个包包括对实现提供这些语义的符号链接的支持。文件系统可以支持语义上接近的其他类型,但是对这些其他类型的链接的支持不包括在此包中。 </p>。
 * 
 * <h3> <a name="interop">互操作性</a> </h3> <p> {@link java.io.File}类定义了{@link java.io.File#toPath toPath}方
 * 法,通过转换由{@code java.io.File}对象表示的抽象路径构造一个{@link java.nio.file.Path}。
 * 生成的{@code Path}可以用于在与{@code File}对象相同的文件上操作。
 *  {@code Path}规范提供了有关{@code Path}和{@code java.io.File}对象之间的<a href="Path.html#interop">互操作性</a>的详细信息。
 *  </p>。
 * 
 *  <h3>可见性</h3> <p>此包中的类提供的文件和文件系统的视图保证与同一Java虚拟机中其他实例提供的其他视图一致。
 * 然而,由于由底层操作系统执行的高速缓存和由网络文件系统协议引起的延迟,该视图可能或可能不与其它并发运行的程序所看到的文件系统的视图一致。
 * 无论这些其他程序的编写语言,以及它们是在同一台机器上运行还是在其他机器上运行,都是如此。任何这种不一致的确切性质是系统依赖性的,因此未指定。 </p>。
 * 
 * <h3> <a name="integrity">同步I / O文件完整性</a> </h3> <p> {@link java.nio.file.StandardOpenOption#SYNC SYNC}
 * 和{@link java.nio .file.StandardOpenOption#DSYNC DSYNC}选项用于打开文件以要求对文件的更新与底层存储设备同步写入时。
 * 在默认提供程序的情况下,并且文件驻留在本地存储设备上,并且{@link java.nio.channels.SeekableByteChannel seekable}通道连接到使用这些选项之一打开的文件
 * ,则调用的{@link java.nio.channels.WritableByteChannel#write(java.nio.ByteBuffer)write}方法只有在该调用对文件所做的所有更改都
 * 
 * @since 1.7
 */
package java.nio.file;
