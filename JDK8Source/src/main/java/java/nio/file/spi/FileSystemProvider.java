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

package java.nio.file.spi;

import java.nio.file.*;
import java.nio.file.attribute.*;
import java.nio.channels.*;
import java.net.URI;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Service-provider class for file systems. The methods defined by the {@link
 * java.nio.file.Files} class will typically delegate to an instance of this
 * class.
 *
 * <p> A file system provider is a concrete implementation of this class that
 * implements the abstract methods defined by this class. A provider is
 * identified by a {@code URI} {@link #getScheme() scheme}. The default provider
 * is identified by the URI scheme "file". It creates the {@link FileSystem} that
 * provides access to the file systems accessible to the Java virtual machine.
 * The {@link FileSystems} class defines how file system providers are located
 * and loaded. The default provider is typically a system-default provider but
 * may be overridden if the system property {@code
 * java.nio.file.spi.DefaultFileSystemProvider} is set. In that case, the
 * provider has a one argument constructor whose formal parameter type is {@code
 * FileSystemProvider}. All other providers have a zero argument constructor
 * that initializes the provider.
 *
 * <p> A provider is a factory for one or more {@link FileSystem} instances. Each
 * file system is identified by a {@code URI} where the URI's scheme matches
 * the provider's {@link #getScheme scheme}. The default file system, for example,
 * is identified by the URI {@code "file:///"}. A memory-based file system,
 * for example, may be identified by a URI such as {@code "memory:///?name=logfs"}.
 * The {@link #newFileSystem newFileSystem} method may be used to create a file
 * system, and the {@link #getFileSystem getFileSystem} method may be used to
 * obtain a reference to an existing file system created by the provider. Where
 * a provider is the factory for a single file system then it is provider dependent
 * if the file system is created when the provider is initialized, or later when
 * the {@code newFileSystem} method is invoked. In the case of the default
 * provider, the {@code FileSystem} is created when the provider is initialized.
 *
 * <p> All of the methods in this class are safe for use by multiple concurrent
 * threads.
 *
 * <p>
 *  文件系统的服务提供程序类。 {@link java.nio.file.Files}类定义的方法通常会委托给这个类的实例。
 * 
 *  <p>文件系统提供程序是实现此类定义的抽象方法的此类的具体实现。提供程序由{@code URI} {@link #getScheme()scheme}标识。默认提供程序由URI方案"file"标识。
 * 它创建{@link FileSystem},提供对Java虚拟机可访问的文件系统的访问。 {@link FileSystems}类定义如何定位和加载文件系统提供程序。
 * 默认提供程序通常是系统默认提供程序,但如果设置了系统属性{@code java.nio.file.spi.DefaultFileSystemProvider},则可以覆盖默认提供程序。
 * 在这种情况下,提供程序具有一个参数构造函数,其形式参数类型为{@code FileSystemProvider}。所有其他提供程序具有初始化提供程序的零参数构造函数。
 * 
 * <p>提供程序是一个或多个{@link FileSystem}实例的工厂。每个文件系统由{@code URI}标识,其中URI的方案与提供商的{@link #getScheme scheme}匹配。
 * 例如,默认文件系统由URI {@code"file：///"}标识。例如,基于存储器的文件系统可以由诸如{@code"memory：///?name = logfs"}之类的URI标识。
 *  {@link #newFileSystem newFileSystem}方法可用于创建文件系统,{@link #getFileSystem getFileSystem}方法可用于获取对提供程序创建的现
 * 有文件系统的引用。
 * 例如,默认文件系统由URI {@code"file：///"}标识。例如,基于存储器的文件系统可以由诸如{@code"memory：///?name = logfs"}之类的URI标识。
 * 如果提供程序是单个文件系统的工厂,那么如果在初始化提供程序时创建文件系统,或者稍后调用{@code newFileSystem}方法时,它是与提供程序相关的。
 * 在默认提供程序的情况下,{@code FileSystem}在提供程序初始化时创建。
 * 
 *  <p>此类中的所有方法都可安全地用于多个并发线程。
 * 
 * 
 * @since 1.7
 */

public abstract class FileSystemProvider {
    // lock using when loading providers
    private static final Object lock = new Object();

    // installed providers
    private static volatile List<FileSystemProvider> installedProviders;

    // used to avoid recursive loading of instaled providers
    private static boolean loadingProviders  = false;

    private static Void checkPermission() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null)
            sm.checkPermission(new RuntimePermission("fileSystemProvider"));
        return null;
    }
    private FileSystemProvider(Void ignore) { }

    /**
     * Initializes a new instance of this class.
     *
     * <p> During construction a provider may safely access files associated
     * with the default provider but care needs to be taken to avoid circular
     * loading of other installed providers. If circular loading of installed
     * providers is detected then an unspecified error is thrown.
     *
     * <p>
     *  初始化此类的新实例。
     * 
     *  <p>在构建期间,提供者可以安全地访问与默认提供者相关联的文件,但需要注意避免其他已安装的提供者的循环加载。如果检测到已安装的提供程序的循环加载,则抛出未指定的错误。
     * 
     * 
     * @throws  SecurityException
     *          If a security manager has been installed and it denies
     *          {@link RuntimePermission}<tt>("fileSystemProvider")</tt>
     */
    protected FileSystemProvider() {
        this(checkPermission());
    }

    // loads all installed providers
    private static List<FileSystemProvider> loadInstalledProviders() {
        List<FileSystemProvider> list = new ArrayList<FileSystemProvider>();

        ServiceLoader<FileSystemProvider> sl = ServiceLoader
            .load(FileSystemProvider.class, ClassLoader.getSystemClassLoader());

        // ServiceConfigurationError may be throw here
        for (FileSystemProvider provider: sl) {
            String scheme = provider.getScheme();

            // add to list if the provider is not "file" and isn't a duplicate
            if (!scheme.equalsIgnoreCase("file")) {
                boolean found = false;
                for (FileSystemProvider p: list) {
                    if (p.getScheme().equalsIgnoreCase(scheme)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    list.add(provider);
                }
            }
        }
        return list;
    }

    /**
     * Returns a list of the installed file system providers.
     *
     * <p> The first invocation of this method causes the default provider to be
     * initialized (if not already initialized) and loads any other installed
     * providers as described by the {@link FileSystems} class.
     *
     * <p>
     *  返回已安装的文件系统提供程序的列表。
     * 
     * <p>此方法的第一次调用导致默认提供程序被初始化(如果尚未初始化),并加载任何其他已安装的提供程序,如{@link FileSystems}类所述。
     * 
     * 
     * @return  An unmodifiable list of the installed file system providers. The
     *          list contains at least one element, that is the default file
     *          system provider
     *
     * @throws  ServiceConfigurationError
     *          When an error occurs while loading a service provider
     */
    public static List<FileSystemProvider> installedProviders() {
        if (installedProviders == null) {
            // ensure default provider is initialized
            FileSystemProvider defaultProvider = FileSystems.getDefault().provider();

            synchronized (lock) {
                if (installedProviders == null) {
                    if (loadingProviders) {
                        throw new Error("Circular loading of installed providers detected");
                    }
                    loadingProviders = true;

                    List<FileSystemProvider> list = AccessController
                        .doPrivileged(new PrivilegedAction<List<FileSystemProvider>>() {
                            @Override
                            public List<FileSystemProvider> run() {
                                return loadInstalledProviders();
                        }});

                    // insert the default provider at the start of the list
                    list.add(0, defaultProvider);

                    installedProviders = Collections.unmodifiableList(list);
                }
            }
        }
        return installedProviders;
    }

    /**
     * Returns the URI scheme that identifies this provider.
     *
     * <p>
     *  返回标识此提供程序的URI方案。
     * 
     * 
     * @return  The URI scheme
     */
    public abstract String getScheme();

    /**
     * Constructs a new {@code FileSystem} object identified by a URI. This
     * method is invoked by the {@link FileSystems#newFileSystem(URI,Map)}
     * method to open a new file system identified by a URI.
     *
     * <p> The {@code uri} parameter is an absolute, hierarchical URI, with a
     * scheme equal (without regard to case) to the scheme supported by this
     * provider. The exact form of the URI is highly provider dependent. The
     * {@code env} parameter is a map of provider specific properties to configure
     * the file system.
     *
     * <p> This method throws {@link FileSystemAlreadyExistsException} if the
     * file system already exists because it was previously created by an
     * invocation of this method. Once a file system is {@link
     * java.nio.file.FileSystem#close closed} it is provider-dependent if the
     * provider allows a new file system to be created with the same URI as a
     * file system it previously created.
     *
     * <p>
     *  构造由URI标识的新{@code FileSystem}对象。此方法由{@link FileSystems#newFileSystem(URI,Map)}方法调用,以打开由URI标识的新文件系统。
     * 
     *  <p> {@code uri}参数是一个绝对的分层URI,方案与此提供商支持的方案相同(不考虑大小写)。 URI的确切形式高度依赖于提供程序。
     *  {@code env}参数是用于配置文件系统的特定于提供程序的属性的映射。
     * 
     *  <p>如果文件系统已经存在,此方法会抛出{@link FileSystemAlreadyExistsException},因为它之前是通过调用此方法创建的。
     * 一旦文件系统是{@link java.nio.file.FileSystem#close Closed},它是依赖于提供程序的,如果提供程序允许使用与之前创建的文件系统相同的URI创建一个新文件系统。
     * 
     * 
     * @param   uri
     *          URI reference
     * @param   env
     *          A map of provider specific properties to configure the file system;
     *          may be empty
     *
     * @return  A new file system
     *
     * @throws  IllegalArgumentException
     *          If the pre-conditions for the {@code uri} parameter aren't met,
     *          or the {@code env} parameter does not contain properties required
     *          by the provider, or a property value is invalid
     * @throws  IOException
     *          An I/O error occurs creating the file system
     * @throws  SecurityException
     *          If a security manager is installed and it denies an unspecified
     *          permission required by the file system provider implementation
     * @throws  FileSystemAlreadyExistsException
     *          If the file system has already been created
     */
    public abstract FileSystem newFileSystem(URI uri, Map<String,?> env)
        throws IOException;

    /**
     * Returns an existing {@code FileSystem} created by this provider.
     *
     * <p> This method returns a reference to a {@code FileSystem} that was
     * created by invoking the {@link #newFileSystem(URI,Map) newFileSystem(URI,Map)}
     * method. File systems created the {@link #newFileSystem(Path,Map)
     * newFileSystem(Path,Map)} method are not returned by this method.
     * The file system is identified by its {@code URI}. Its exact form
     * is highly provider dependent. In the case of the default provider the URI's
     * path component is {@code "/"} and the authority, query and fragment components
     * are undefined (Undefined components are represented by {@code null}).
     *
     * <p> Once a file system created by this provider is {@link
     * java.nio.file.FileSystem#close closed} it is provider-dependent if this
     * method returns a reference to the closed file system or throws {@link
     * FileSystemNotFoundException}. If the provider allows a new file system to
     * be created with the same URI as a file system it previously created then
     * this method throws the exception if invoked after the file system is
     * closed (and before a new instance is created by the {@link #newFileSystem
     * newFileSystem} method).
     *
     * <p> If a security manager is installed then a provider implementation
     * may require to check a permission before returning a reference to an
     * existing file system. In the case of the {@link FileSystems#getDefault
     * default} file system, no permission check is required.
     *
     * <p>
     *  返回此提供程序创建的现有{@code FileSystem}。
     * 
     * <p>此方法返回对通过调用{@link #newFileSystem(URI,Map)newFileSystem(URI,Map)}方法创建的{@code FileSystem}的引用。
     * 文件系统创建的{@link #newFileSystem(Path,Map)newFileSystem(Path,Map)}方法不会返回此方法。文件系统由其{@code URI}标识。
     * 其确切的形式高度依赖于供应商。在默认提供者的情况下,URI的路径组件是{@code"/"},权限,查询和片段组件是未定义的(未定义的组件由{@code null}表示)。
     * 
     *  <p>如果此方法返回对封闭文件系统的引用或抛出{@link FileSystemNotFoundException},则此提供程序创建的文件系统为{@link java.nio.file.FileSystem#close Closed}
     * 时,它是与提供程序相关的。
     * 如果提供程序允许使用与之前创建的文件系统相同的URI创建新的文件系统,则该方法在文件系统关闭后(以及在{@link# newFileSystem newFileSystem}方法)。
     * 
     *  <p>如果安装了安全管理器,则提供程序实现可能需要在返回对现有文件系统的引用之前检查权限。
     * 对于{@link FileSystems#getDefault default}文件系统,不需要进行权限检查。
     * 
     * 
     * @param   uri
     *          URI reference
     *
     * @return  The file system
     *
     * @throws  IllegalArgumentException
     *          If the pre-conditions for the {@code uri} parameter aren't met
     * @throws  FileSystemNotFoundException
     *          If the file system does not exist
     * @throws  SecurityException
     *          If a security manager is installed and it denies an unspecified
     *          permission.
     */
    public abstract FileSystem getFileSystem(URI uri);

    /**
     * Return a {@code Path} object by converting the given {@link URI}. The
     * resulting {@code Path} is associated with a {@link FileSystem} that
     * already exists or is constructed automatically.
     *
     * <p> The exact form of the URI is file system provider dependent. In the
     * case of the default provider, the URI scheme is {@code "file"} and the
     * given URI has a non-empty path component, and undefined query, and
     * fragment components. The resulting {@code Path} is associated with the
     * default {@link FileSystems#getDefault default} {@code FileSystem}.
     *
     * <p> If a security manager is installed then a provider implementation
     * may require to check a permission. In the case of the {@link
     * FileSystems#getDefault default} file system, no permission check is
     * required.
     *
     * <p>
     * 通过转换给定的{@link URI}返回{@code Path}对象。生成的{@code Path}与已存在的或自动构建的{@link FileSystem}相关联。
     * 
     *  <p> URI的确切形式与文件系统提供程序相关。在默认提供者的情况下,URI方案是{@code"file"},并且给定的URI具有非空路径组件,未定义的查询和片段组件。
     * 生成的{@code Path}与默认的{@link FileSystems#getDefault default} {@code FileSystem}相关联。
     * 
     *  <p>如果安装了安全管理器,则提供程序实现可能需要检查权限。对于{@link FileSystems#getDefault default}文件系统,不需要进行权限检查。
     * 
     * 
     * @param   uri
     *          The URI to convert
     *
     * @return  The resulting {@code Path}
     *
     * @throws  IllegalArgumentException
     *          If the URI scheme does not identify this provider or other
     *          preconditions on the uri parameter do not hold
     * @throws  FileSystemNotFoundException
     *          The file system, identified by the URI, does not exist and
     *          cannot be created automatically
     * @throws  SecurityException
     *          If a security manager is installed and it denies an unspecified
     *          permission.
     */
    public abstract Path getPath(URI uri);

    /**
     * Constructs a new {@code FileSystem} to access the contents of a file as a
     * file system.
     *
     * <p> This method is intended for specialized providers of pseudo file
     * systems where the contents of one or more files is treated as a file
     * system. The {@code env} parameter is a map of provider specific properties
     * to configure the file system.
     *
     * <p> If this provider does not support the creation of such file systems
     * or if the provider does not recognize the file type of the given file then
     * it throws {@code UnsupportedOperationException}. The default implementation
     * of this method throws {@code UnsupportedOperationException}.
     *
     * <p>
     *  构造新的{@code FileSystem}以作为文件系统访问文件的内容。
     * 
     *  <p>此方法适用于伪文件系统的专业提供者,其中一个或多个文件的内容被视为文件系统。 {@code env}参数是用于配置文件系统的特定于提供程序的属性的映射。
     * 
     *  <p>如果此提供程序不支持创建此类文件系统,或者提供程序无法识别给定文件的文件类型,则会抛出{@code UnsupportedOperationException}。
     * 此方法的默认实现引发{@code UnsupportedOperationException}。
     * 
     * 
     * @param   path
     *          The path to the file
     * @param   env
     *          A map of provider specific properties to configure the file system;
     *          may be empty
     *
     * @return  A new file system
     *
     * @throws  UnsupportedOperationException
     *          If this provider does not support access to the contents as a
     *          file system or it does not recognize the file type of the
     *          given file
     * @throws  IllegalArgumentException
     *          If the {@code env} parameter does not contain properties required
     *          by the provider, or a property value is invalid
     * @throws  IOException
     *          If an I/O error occurs
     * @throws  SecurityException
     *          If a security manager is installed and it denies an unspecified
     *          permission.
     */
    public FileSystem newFileSystem(Path path, Map<String,?> env)
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Opens a file, returning an input stream to read from the file. This
     * method works in exactly the manner specified by the {@link
     * Files#newInputStream} method.
     *
     * <p> The default implementation of this method opens a channel to the file
     * as if by invoking the {@link #newByteChannel} method and constructs a
     * stream that reads bytes from the channel. This method should be overridden
     * where appropriate.
     *
     * <p>
     * 打开文件,返回输入流以从文件读取。此方法完全按照{@link Files#newInputStream}方法指定的方式工作。
     * 
     *  <p>此方法的默认实现打开了一个文件通道,就像调用{@link #newByteChannel}方法,并构造一个从通道读取字节的流。在适当的情况下应该覆盖此方法。
     * 
     * 
     * @param   path
     *          the path to the file to open
     * @param   options
     *          options specifying how the file is opened
     *
     * @return  a new input stream
     *
     * @throws  IllegalArgumentException
     *          if an invalid combination of options is specified
     * @throws  UnsupportedOperationException
     *          if an unsupported option is specified
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager is
     *          installed, the {@link SecurityManager#checkRead(String) checkRead}
     *          method is invoked to check read access to the file.
     */
    public InputStream newInputStream(Path path, OpenOption... options)
        throws IOException
    {
        if (options.length > 0) {
            for (OpenOption opt: options) {
                // All OpenOption values except for APPEND and WRITE are allowed
                if (opt == StandardOpenOption.APPEND ||
                    opt == StandardOpenOption.WRITE)
                    throw new UnsupportedOperationException("'" + opt + "' not allowed");
            }
        }
        return Channels.newInputStream(Files.newByteChannel(path, options));
    }

    /**
     * Opens or creates a file, returning an output stream that may be used to
     * write bytes to the file. This method works in exactly the manner
     * specified by the {@link Files#newOutputStream} method.
     *
     * <p> The default implementation of this method opens a channel to the file
     * as if by invoking the {@link #newByteChannel} method and constructs a
     * stream that writes bytes to the channel. This method should be overridden
     * where appropriate.
     *
     * <p>
     *  打开或创建文件,返回可用于将字节写入文件的输出流。此方法完全按照{@link Files#newOutputStream}方法指定的方式工作。
     * 
     *  <p>此方法的默认实现打开一个通道到文件,如同通过调用{@link #newByteChannel}方法,并构造一个流写入字节到通道。在适当的情况下应该覆盖此方法。
     * 
     * 
     * @param   path
     *          the path to the file to open or create
     * @param   options
     *          options specifying how the file is opened
     *
     * @return  a new output stream
     *
     * @throws  IllegalArgumentException
     *          if {@code options} contains an invalid combination of options
     * @throws  UnsupportedOperationException
     *          if an unsupported option is specified
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager is
     *          installed, the {@link SecurityManager#checkWrite(String) checkWrite}
     *          method is invoked to check write access to the file. The {@link
     *          SecurityManager#checkDelete(String) checkDelete} method is
     *          invoked to check delete access if the file is opened with the
     *          {@code DELETE_ON_CLOSE} option.
     */
    public OutputStream newOutputStream(Path path, OpenOption... options)
        throws IOException
    {
        int len = options.length;
        Set<OpenOption> opts = new HashSet<OpenOption>(len + 3);
        if (len == 0) {
            opts.add(StandardOpenOption.CREATE);
            opts.add(StandardOpenOption.TRUNCATE_EXISTING);
        } else {
            for (OpenOption opt: options) {
                if (opt == StandardOpenOption.READ)
                    throw new IllegalArgumentException("READ not allowed");
                opts.add(opt);
            }
        }
        opts.add(StandardOpenOption.WRITE);
        return Channels.newOutputStream(newByteChannel(path, opts));
    }

    /**
     * Opens or creates a file for reading and/or writing, returning a file
     * channel to access the file. This method works in exactly the manner
     * specified by the {@link FileChannel#open(Path,Set,FileAttribute[])
     * FileChannel.open} method. A provider that does not support all the
     * features required to construct a file channel throws {@code
     * UnsupportedOperationException}. The default provider is required to
     * support the creation of file channels. When not overridden, the default
     * implementation throws {@code UnsupportedOperationException}.
     *
     * <p>
     *  打开或创建用于读取和/或写入的文件,返回文件通道以访问文件。
     * 此方法完全按照{@link FileChannel#open(Path,Set,FileAttribute [])FileChannel.open}方法指定的方式工作。
     * 不支持构造文件通道所需的所有功能的提供程序会抛出{@code UnsupportedOperationException}。默认提供程序需要支持创建文件通道。
     * 当不重写时,默认实现会抛出{@code UnsupportedOperationException}。
     * 
     * 
     * @param   path
     *          the path of the file to open or create
     * @param   options
     *          options specifying how the file is opened
     * @param   attrs
     *          an optional list of file attributes to set atomically when
     *          creating the file
     *
     * @return  a new file channel
     *
     * @throws  IllegalArgumentException
     *          If the set contains an invalid combination of options
     * @throws  UnsupportedOperationException
     *          If this provider that does not support creating file channels,
     *          or an unsupported open option or file attribute is specified
     * @throws  IOException
     *          If an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default file system, the {@link
     *          SecurityManager#checkRead(String)} method is invoked to check
     *          read access if the file is opened for reading. The {@link
     *          SecurityManager#checkWrite(String)} method is invoked to check
     *          write access if the file is opened for writing
     */
    public FileChannel newFileChannel(Path path,
                                      Set<? extends OpenOption> options,
                                      FileAttribute<?>... attrs)
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Opens or creates a file for reading and/or writing, returning an
     * asynchronous file channel to access the file. This method works in
     * exactly the manner specified by the {@link
     * AsynchronousFileChannel#open(Path,Set,ExecutorService,FileAttribute[])
     * AsynchronousFileChannel.open} method.
     * A provider that does not support all the features required to construct
     * an asynchronous file channel throws {@code UnsupportedOperationException}.
     * The default provider is required to support the creation of asynchronous
     * file channels. When not overridden, the default implementation of this
     * method throws {@code UnsupportedOperationException}.
     *
     * <p>
     * 打开或创建用于读取和/或写入的文件,返回异步文件通道以访问文件。
     * 此方法完全按照{@link AsynchronousFileChannel#open(Path,Set,ExecutorService,FileAttribute [])AsynchronousFileChannel.open}
     * 方法指定的方式工作。
     * 打开或创建用于读取和/或写入的文件,返回异步文件通道以访问文件。不支持构造异步文件通道所需的所有功能的提供程序会抛出{@code UnsupportedOperationException}。
     * 默认提供程序需要支持异步文件通道的创建。当不重写时,此方法的默认实现将抛出{@code UnsupportedOperationException}。
     * 
     * 
     * @param   path
     *          the path of the file to open or create
     * @param   options
     *          options specifying how the file is opened
     * @param   executor
     *          the thread pool or {@code null} to associate the channel with
     *          the default thread pool
     * @param   attrs
     *          an optional list of file attributes to set atomically when
     *          creating the file
     *
     * @return  a new asynchronous file channel
     *
     * @throws  IllegalArgumentException
     *          If the set contains an invalid combination of options
     * @throws  UnsupportedOperationException
     *          If this provider that does not support creating asynchronous file
     *          channels, or an unsupported open option or file attribute is
     *          specified
     * @throws  IOException
     *          If an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default file system, the {@link
     *          SecurityManager#checkRead(String)} method is invoked to check
     *          read access if the file is opened for reading. The {@link
     *          SecurityManager#checkWrite(String)} method is invoked to check
     *          write access if the file is opened for writing
     */
    public AsynchronousFileChannel newAsynchronousFileChannel(Path path,
                                                              Set<? extends OpenOption> options,
                                                              ExecutorService executor,
                                                              FileAttribute<?>... attrs)
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Opens or creates a file, returning a seekable byte channel to access the
     * file. This method works in exactly the manner specified by the {@link
     * Files#newByteChannel(Path,Set,FileAttribute[])} method.
     *
     * <p>
     *  打开或创建文件,返回可查找的字节通道以访问文件。此方法完全按照{@link Files#newByteChannel(Path,Set,FileAttribute [])}方法指定的方式工作。
     * 
     * 
     * @param   path
     *          the path to the file to open or create
     * @param   options
     *          options specifying how the file is opened
     * @param   attrs
     *          an optional list of file attributes to set atomically when
     *          creating the file
     *
     * @return  a new seekable byte channel
     *
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
     *          method is invoked to check read access to the path if the file is
     *          opened for reading. The {@link SecurityManager#checkWrite(String)
     *          checkWrite} method is invoked to check write access to the path
     *          if the file is opened for writing. The {@link
     *          SecurityManager#checkDelete(String) checkDelete} method is
     *          invoked to check delete access if the file is opened with the
     *          {@code DELETE_ON_CLOSE} option.
     */
    public abstract SeekableByteChannel newByteChannel(Path path,
        Set<? extends OpenOption> options, FileAttribute<?>... attrs) throws IOException;

    /**
     * Opens a directory, returning a {@code DirectoryStream} to iterate over
     * the entries in the directory. This method works in exactly the manner
     * specified by the {@link
     * Files#newDirectoryStream(java.nio.file.Path, java.nio.file.DirectoryStream.Filter)}
     * method.
     *
     * <p>
     *  打开目录,返回{@code DirectoryStream}以遍历目录中的条目。
     * 此方法完全按照{@link Files#newDirectoryStream(java.nio.file.Path,java.nio.file.DirectoryStream.Filter)}方法指定的
     * 方式工作。
     *  打开目录,返回{@code DirectoryStream}以遍历目录中的条目。
     * 
     * 
     * @param   dir
     *          the path to the directory
     * @param   filter
     *          the directory stream filter
     *
     * @return  a new and open {@code DirectoryStream} object
     *
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
    public abstract DirectoryStream<Path> newDirectoryStream(Path dir,
         DirectoryStream.Filter<? super Path> filter) throws IOException;

    /**
     * Creates a new directory. This method works in exactly the manner
     * specified by the {@link Files#createDirectory} method.
     *
     * <p>
     *  创建新目录。此方法完全按照{@link Files#createDirectory}方法指定的方式工作。
     * 
     * 
     * @param   dir
     *          the directory to create
     * @param   attrs
     *          an optional list of file attributes to set atomically when
     *          creating the directory
     *
     * @throws  UnsupportedOperationException
     *          if the array contains an attribute that cannot be set atomically
     *          when creating the directory
     * @throws  FileAlreadyExistsException
     *          if a directory could not otherwise be created because a file of
     *          that name already exists <i>(optional specific exception)</i>
     * @throws  IOException
     *          if an I/O error occurs or the parent directory does not exist
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager is
     *          installed, the {@link SecurityManager#checkWrite(String) checkWrite}
     *          method is invoked to check write access to the new directory.
     */
    public abstract void createDirectory(Path dir, FileAttribute<?>... attrs)
        throws IOException;

    /**
     * Creates a symbolic link to a target. This method works in exactly the
     * manner specified by the {@link Files#createSymbolicLink} method.
     *
     * <p> The default implementation of this method throws {@code
     * UnsupportedOperationException}.
     *
     * <p>
     *  创建到目标的符号链接。此方法完全按照{@link Files#createSymbolicLink}方法指定的方式工作。
     * 
     *  <p>此方法的默认实现引发{@code UnsupportedOperationException}。
     * 
     * 
     * @param   link
     *          the path of the symbolic link to create
     * @param   target
     *          the target of the symbolic link
     * @param   attrs
     *          the array of attributes to set atomically when creating the
     *          symbolic link
     *
     * @throws  UnsupportedOperationException
     *          if the implementation does not support symbolic links or the
     *          array contains an attribute that cannot be set atomically when
     *          creating the symbolic link
     * @throws  FileAlreadyExistsException
     *          if a file with the name already exists <i>(optional specific
     *          exception)</i>
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager
     *          is installed, it denies {@link LinkPermission}<tt>("symbolic")</tt>
     *          or its {@link SecurityManager#checkWrite(String) checkWrite}
     *          method denies write access to the path of the symbolic link.
     */
    public void createSymbolicLink(Path link, Path target, FileAttribute<?>... attrs)
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Creates a new link (directory entry) for an existing file. This method
     * works in exactly the manner specified by the {@link Files#createLink}
     * method.
     *
     * <p> The default implementation of this method throws {@code
     * UnsupportedOperationException}.
     *
     * <p>
     * 为现有文件创建新的链接(目录条目)。此方法完全按照{@link Files#createLink}方法指定的方式工作。
     * 
     *  <p>此方法的默认实现引发{@code UnsupportedOperationException}。
     * 
     * 
     * @param   link
     *          the link (directory entry) to create
     * @param   existing
     *          a path to an existing file
     *
     * @throws  UnsupportedOperationException
     *          if the implementation does not support adding an existing file
     *          to a directory
     * @throws  FileAlreadyExistsException
     *          if the entry could not otherwise be created because a file of
     *          that name already exists <i>(optional specific exception)</i>
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager
     *          is installed, it denies {@link LinkPermission}<tt>("hard")</tt>
     *          or its {@link SecurityManager#checkWrite(String) checkWrite}
     *          method denies write access to either the  link or the
     *          existing file.
     */
    public void createLink(Path link, Path existing) throws IOException {
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes a file. This method works in exactly the  manner specified by the
     * {@link Files#delete} method.
     *
     * <p>
     *  删除文件。此方法的工作方式与{@link Files#delete}方法指定的完全相同。
     * 
     * 
     * @param   path
     *          the path to the file to delete
     *
     * @throws  NoSuchFileException
     *          if the file does not exist <i>(optional specific exception)</i>
     * @throws  DirectoryNotEmptyException
     *          if the file is a directory and could not otherwise be deleted
     *          because the directory is not empty <i>(optional specific
     *          exception)</i>
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager is
     *          installed, the {@link SecurityManager#checkDelete(String)} method
     *          is invoked to check delete access to the file
     */
    public abstract void delete(Path path) throws IOException;

    /**
     * Deletes a file if it exists. This method works in exactly the manner
     * specified by the {@link Files#deleteIfExists} method.
     *
     * <p> The default implementation of this method simply invokes {@link
     * #delete} ignoring the {@code NoSuchFileException} when the file does not
     * exist. It may be overridden where appropriate.
     *
     * <p>
     *  删除文件(如果存在)。此方法完全按照{@link Files#deleteIfExists}方法指定的方式工作。
     * 
     *  <p>此方法的默认实现只是在文件不存在时调用{@link #delete}忽略{@code NoSuchFileException}。它可以在适当的地方覆盖。
     * 
     * 
     * @param   path
     *          the path to the file to delete
     *
     * @return  {@code true} if the file was deleted by this method; {@code
     *          false} if the file could not be deleted because it did not
     *          exist
     *
     * @throws  DirectoryNotEmptyException
     *          if the file is a directory and could not otherwise be deleted
     *          because the directory is not empty <i>(optional specific
     *          exception)</i>
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager is
     *          installed, the {@link SecurityManager#checkDelete(String)} method
     *          is invoked to check delete access to the file
     */
    public boolean deleteIfExists(Path path) throws IOException {
        try {
            delete(path);
            return true;
        } catch (NoSuchFileException ignore) {
            return false;
        }
    }

    /**
     * Reads the target of a symbolic link. This method works in exactly the
     * manner specified by the {@link Files#readSymbolicLink} method.
     *
     * <p> The default implementation of this method throws {@code
     * UnsupportedOperationException}.
     *
     * <p>
     *  读取符号链接的目标。此方法完全按照{@link Files#readSymbolicLink}方法指定的方式工作。
     * 
     *  <p>此方法的默认实现引发{@code UnsupportedOperationException}。
     * 
     * 
     * @param   link
     *          the path to the symbolic link
     *
     * @return  The target of the symbolic link
     *
     * @throws  UnsupportedOperationException
     *          if the implementation does not support symbolic links
     * @throws  NotLinkException
     *          if the target could otherwise not be read because the file
     *          is not a symbolic link <i>(optional specific exception)</i>
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager
     *          is installed, it checks that {@code FilePermission} has been
     *          granted with the "{@code readlink}" action to read the link.
     */
    public Path readSymbolicLink(Path link) throws IOException {
        throw new UnsupportedOperationException();
    }

    /**
     * Copy a file to a target file. This method works in exactly the manner
     * specified by the {@link Files#copy(Path,Path,CopyOption[])} method
     * except that both the source and target paths must be associated with
     * this provider.
     *
     * <p>
     *  将文件复制到目标文件。此方法完全按照{@link Files#copy(Path,Path,CopyOption [])}方法指定的方式工作,只是源路径和目标路径都必须与此提供程序相关联。
     * 
     * 
     * @param   source
     *          the path to the file to copy
     * @param   target
     *          the path to the target file
     * @param   options
     *          options specifying how the copy should be done
     *
     * @throws  UnsupportedOperationException
     *          if the array contains a copy option that is not supported
     * @throws  FileAlreadyExistsException
     *          if the target file exists but cannot be replaced because the
     *          {@code REPLACE_EXISTING} option is not specified <i>(optional
     *          specific exception)</i>
     * @throws  DirectoryNotEmptyException
     *          the {@code REPLACE_EXISTING} option is specified but the file
     *          cannot be replaced because it is a non-empty directory
     *          <i>(optional specific exception)</i>
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager is
     *          installed, the {@link SecurityManager#checkRead(String) checkRead}
     *          method is invoked to check read access to the source file, the
     *          {@link SecurityManager#checkWrite(String) checkWrite} is invoked
     *          to check write access to the target file. If a symbolic link is
     *          copied the security manager is invoked to check {@link
     *          LinkPermission}{@code ("symbolic")}.
     */
    public abstract void copy(Path source, Path target, CopyOption... options)
        throws IOException;

    /**
     * Move or rename a file to a target file. This method works in exactly the
     * manner specified by the {@link Files#move} method except that both the
     * source and target paths must be associated with this provider.
     *
     * <p>
     *  将文件移动或重命名为目标文件。此方法完全按照{@link Files#move}方法指定的方式工作,除了源路径和目标路径都必须与此提供程序相关联。
     * 
     * 
     * @param   source
     *          the path to the file to move
     * @param   target
     *          the path to the target file
     * @param   options
     *          options specifying how the move should be done
     *
     * @throws  UnsupportedOperationException
     *          if the array contains a copy option that is not supported
     * @throws  FileAlreadyExistsException
     *          if the target file exists but cannot be replaced because the
     *          {@code REPLACE_EXISTING} option is not specified <i>(optional
     *          specific exception)</i>
     * @throws  DirectoryNotEmptyException
     *          the {@code REPLACE_EXISTING} option is specified but the file
     *          cannot be replaced because it is a non-empty directory
     *          <i>(optional specific exception)</i>
     * @throws  AtomicMoveNotSupportedException
     *          if the options array contains the {@code ATOMIC_MOVE} option but
     *          the file cannot be moved as an atomic file system operation.
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager is
     *          installed, the {@link SecurityManager#checkWrite(String) checkWrite}
     *          method is invoked to check write access to both the source and
     *          target file.
     */
    public abstract void move(Path source, Path target, CopyOption... options)
        throws IOException;

    /**
     * Tests if two paths locate the same file. This method works in exactly the
     * manner specified by the {@link Files#isSameFile} method.
     *
     * <p>
     *  测试两个路径是否找到相同的文件。此方法完全按照{@link Files#isSameFile}方法指定的方式工作。
     * 
     * 
     * @param   path
     *          one path to the file
     * @param   path2
     *          the other path
     *
     * @return  {@code true} if, and only if, the two paths locate the same file
     *
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager is
     *          installed, the {@link SecurityManager#checkRead(String) checkRead}
     *          method is invoked to check read access to both files.
     */
    public abstract boolean isSameFile(Path path, Path path2)
        throws IOException;

    /**
     * Tells whether or not a file is considered <em>hidden</em>. This method
     * works in exactly the manner specified by the {@link Files#isHidden}
     * method.
     *
     * <p> This method is invoked by the {@link Files#isHidden isHidden} method.
     *
     * <p>
     * 指出文件是否被视为"隐藏"</em>。此方法完全按照{@link Files#isHidden}方法指定的方式工作。
     * 
     *  <p>此方法由{@link Files#isHidden isHidden}方法调用。
     * 
     * 
     * @param   path
     *          the path to the file to test
     *
     * @return  {@code true} if the file is considered hidden
     *
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager is
     *          installed, the {@link SecurityManager#checkRead(String) checkRead}
     *          method is invoked to check read access to the file.
     */
    public abstract boolean isHidden(Path path) throws IOException;

    /**
     * Returns the {@link FileStore} representing the file store where a file
     * is located. This method works in exactly the manner specified by the
     * {@link Files#getFileStore} method.
     *
     * <p>
     *  返回表示文件所在文件存储区的{@link FileStore}。此方法的工作方式与{@link Files#getFileStore}方法指定的完全相同。
     * 
     * 
     * @param   path
     *          the path to the file
     *
     * @return  the file store where the file is stored
     *
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager is
     *          installed, the {@link SecurityManager#checkRead(String) checkRead}
     *          method is invoked to check read access to the file, and in
     *          addition it checks {@link RuntimePermission}<tt>
     *          ("getFileStoreAttributes")</tt>
     */
    public abstract FileStore getFileStore(Path path) throws IOException;

    /**
     * Checks the existence, and optionally the accessibility, of a file.
     *
     * <p> This method may be used by the {@link Files#isReadable isReadable},
     * {@link Files#isWritable isWritable} and {@link Files#isExecutable
     * isExecutable} methods to check the accessibility of a file.
     *
     * <p> This method checks the existence of a file and that this Java virtual
     * machine has appropriate privileges that would allow it access the file
     * according to all of access modes specified in the {@code modes} parameter
     * as follows:
     *
     * <table border=1 cellpadding=5 summary="">
     * <tr> <th>Value</th> <th>Description</th> </tr>
     * <tr>
     *   <td> {@link AccessMode#READ READ} </td>
     *   <td> Checks that the file exists and that the Java virtual machine has
     *     permission to read the file. </td>
     * </tr>
     * <tr>
     *   <td> {@link AccessMode#WRITE WRITE} </td>
     *   <td> Checks that the file exists and that the Java virtual machine has
     *     permission to write to the file, </td>
     * </tr>
     * <tr>
     *   <td> {@link AccessMode#EXECUTE EXECUTE} </td>
     *   <td> Checks that the file exists and that the Java virtual machine has
     *     permission to {@link Runtime#exec execute} the file. The semantics
     *     may differ when checking access to a directory. For example, on UNIX
     *     systems, checking for {@code EXECUTE} access checks that the Java
     *     virtual machine has permission to search the directory in order to
     *     access file or subdirectories. </td>
     * </tr>
     * </table>
     *
     * <p> If the {@code modes} parameter is of length zero, then the existence
     * of the file is checked.
     *
     * <p> This method follows symbolic links if the file referenced by this
     * object is a symbolic link. Depending on the implementation, this method
     * may require to read file permissions, access control lists, or other
     * file attributes in order to check the effective access to the file. To
     * determine the effective access to a file may require access to several
     * attributes and so in some implementations this method may not be atomic
     * with respect to other file system operations.
     *
     * <p>
     *  检查文件的存在性和可选的可访问性。
     * 
     *  <p>此方法可由{@link Files#isReadable isReadable},{@link Files#isWritable isWritable}和{@link Files#isExecutable isExecutable}
     * 方法使用,以检查文件的辅助功能。
     * 
     *  <p>此方法检查文件的存在,并且此Java虚拟机具有适当的权限,以允许根据{@code modes}参数中指定的所有访问模式访问该文件,如下所示：
     * 
     * <table border=1 cellpadding=5 summary="">
     *  <tr> <th>价值</th> <th>描述</th> </tr>
     * <tr>
     *  <td> {@link AccessMode#READ READ} </td> <td>检查文件是否存在,以及Java虚拟机是否有读取文件的权限。 </td>
     * </tr>
     * <tr>
     *  <td> {@link AccessMode#WRITE WRITE} </td> <td>检查文件是否存在,并且Java虚拟机有权写入文件</td>
     * </tr>
     * <tr>
     * <td> {@link AccessMode#EXECUTE EXECUTE} </td> <td>检查文件是否存在,以及Java虚拟机是否具有{@link Runtime#exec execute}文
     * 件的权限。
     * 检查对目录的访问时,语义可能不同。例如,在UNIX系统上,检查{@code EXECUTE}访问权限检查Java虚拟机是否有权搜索目录以访问文件或子目录。 </td>。
     * </tr>
     * 
     * @param   path
     *          the path to the file to check
     * @param   modes
     *          The access modes to check; may have zero elements
     *
     * @throws  UnsupportedOperationException
     *          an implementation is required to support checking for
     *          {@code READ}, {@code WRITE}, and {@code EXECUTE} access. This
     *          exception is specified to allow for the {@code Access} enum to
     *          be extended in future releases.
     * @throws  NoSuchFileException
     *          if a file does not exist <i>(optional specific exception)</i>
     * @throws  AccessDeniedException
     *          the requested access would be denied or the access cannot be
     *          determined because the Java virtual machine has insufficient
     *          privileges or other reasons. <i>(optional specific exception)</i>
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager is
     *          installed, the {@link SecurityManager#checkRead(String) checkRead}
     *          is invoked when checking read access to the file or only the
     *          existence of the file, the {@link SecurityManager#checkWrite(String)
     *          checkWrite} is invoked when checking write access to the file,
     *          and {@link SecurityManager#checkExec(String) checkExec} is invoked
     *          when checking execute access.
     */
    public abstract void checkAccess(Path path, AccessMode... modes)
        throws IOException;

    /**
     * Returns a file attribute view of a given type. This method works in
     * exactly the manner specified by the {@link Files#getFileAttributeView}
     * method.
     *
     * <p>
     * </table>
     * 
     *  <p>如果{@code modes}参数的长度为零,则检查文件的存在。
     * 
     *  <p>如果此对象引用的文件是符号链接,此方法遵循符号链接。根据实现,此方法可能需要读取文件权限,访问控制列表或其他文件属性,以便检查对文件的有效访问。
     * 为了确定对文件的有效访问可能需要访问几个属性,因此在一些实现中,该方法相对于其他文件系统操作可能不是原子的。
     * 
     * 
     * @param   <V>
     *          The {@code FileAttributeView} type
     * @param   path
     *          the path to the file
     * @param   type
     *          the {@code Class} object corresponding to the file attribute view
     * @param   options
     *          options indicating how symbolic links are handled
     *
     * @return  a file attribute view of the specified type, or {@code null} if
     *          the attribute view type is not available
     */
    public abstract <V extends FileAttributeView> V
        getFileAttributeView(Path path, Class<V> type, LinkOption... options);

    /**
     * Reads a file's attributes as a bulk operation. This method works in
     * exactly the manner specified by the {@link
     * Files#readAttributes(Path,Class,LinkOption[])} method.
     *
     * <p>
     *  返回给定类型的文件属性视图。此方法完全按照{@link Files#getFileAttributeView}方法指定的方式工作。
     * 
     * 
     * @param   <A>
     *          The {@code BasicFileAttributes} type
     * @param   path
     *          the path to the file
     * @param   type
     *          the {@code Class} of the file attributes required
     *          to read
     * @param   options
     *          options indicating how symbolic links are handled
     *
     * @return  the file attributes
     *
     * @throws  UnsupportedOperationException
     *          if an attributes of the given type are not supported
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, a security manager is
     *          installed, its {@link SecurityManager#checkRead(String) checkRead}
     *          method is invoked to check read access to the file
     */
    public abstract <A extends BasicFileAttributes> A
        readAttributes(Path path, Class<A> type, LinkOption... options) throws IOException;

    /**
     * Reads a set of file attributes as a bulk operation. This method works in
     * exactly the manner specified by the {@link
     * Files#readAttributes(Path,String,LinkOption[])} method.
     *
     * <p>
     *  作为批量操作读取文件的属性。此方法完全按照{@link Files#readAttributes(Path,Class,LinkOption [])}方法指定的方式工作。
     * 
     * 
     * @param   path
     *          the path to the file
     * @param   attributes
     *          the attributes to read
     * @param   options
     *          options indicating how symbolic links are handled
     *
     * @return  a map of the attributes returned; may be empty. The map's keys
     *          are the attribute names, its values are the attribute values
     *
     * @throws  UnsupportedOperationException
     *          if the attribute view is not available
     * @throws  IllegalArgumentException
     *          if no attributes are specified or an unrecognized attributes is
     *          specified
     * @throws  IOException
     *          If an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager is
     *          installed, its {@link SecurityManager#checkRead(String) checkRead}
     *          method denies read access to the file. If this method is invoked
     *          to read security sensitive attributes then the security manager
     *          may be invoke to check for additional permissions.
     */
    public abstract Map<String,Object> readAttributes(Path path, String attributes,
                                                      LinkOption... options)
        throws IOException;

    /**
     * Sets the value of a file attribute. This method works in exactly the
     * manner specified by the {@link Files#setAttribute} method.
     *
     * <p>
     *  将一组文件属性作为批量操作读取。此方法完全按照{@link Files#readAttributes(Path,String,LinkOption [])}方法指定的方式工作。
     * 
     * 
     * @param   path
     *          the path to the file
     * @param   attribute
     *          the attribute to set
     * @param   value
     *          the attribute value
     * @param   options
     *          options indicating how symbolic links are handled
     *
     * @throws  UnsupportedOperationException
     *          if the attribute view is not available
     * @throws  IllegalArgumentException
     *          if the attribute name is not specified, or is not recognized, or
     *          the attribute value is of the correct type but has an
     *          inappropriate value
     * @throws  ClassCastException
     *          If the attribute value is not of the expected type or is a
     *          collection containing elements that are not of the expected
     *          type
     * @throws  IOException
     *          If an I/O error occurs
     * @throws  SecurityException
     *          In the case of the default provider, and a security manager is
     *          installed, its {@link SecurityManager#checkWrite(String) checkWrite}
     *          method denies write access to the file. If this method is invoked
     *          to set security sensitive attributes then the security manager
     *          may be invoked to check for additional permissions.
     */
    public abstract void setAttribute(Path path, String attribute,
                                      Object value, LinkOption... options)
        throws IOException;
}
