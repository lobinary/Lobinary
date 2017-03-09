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

import java.nio.file.spi.FileSystemProvider;
import java.net.URI;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;
import java.lang.reflect.Constructor;

/**
 * Factory methods for file systems. This class defines the {@link #getDefault
 * getDefault} method to get the default file system and factory methods to
 * construct other types of file systems.
 *
 * <p> The first invocation of any of the methods defined by this class causes
 * the default {@link FileSystemProvider provider} to be loaded. The default
 * provider, identified by the URI scheme "file", creates the {@link FileSystem}
 * that provides access to the file systems accessible to the Java virtual
 * machine. If the process of loading or initializing the default provider fails
 * then an unspecified error is thrown.
 *
 * <p> The first invocation of the {@link FileSystemProvider#installedProviders
 * installedProviders} method, by way of invoking any of the {@code
 * newFileSystem} methods defined by this class, locates and loads all
 * installed file system providers. Installed providers are loaded using the
 * service-provider loading facility defined by the {@link ServiceLoader} class.
 * Installed providers are loaded using the system class loader. If the
 * system class loader cannot be found then the extension class loader is used;
 * if there is no extension class loader then the bootstrap class loader is used.
 * Providers are typically installed by placing them in a JAR file on the
 * application class path or in the extension directory, the JAR file contains a
 * provider-configuration file named {@code java.nio.file.spi.FileSystemProvider}
 * in the resource directory {@code META-INF/services}, and the file lists one or
 * more fully-qualified names of concrete subclass of {@link FileSystemProvider}
 * that have a zero argument constructor.
 * The ordering that installed providers are located is implementation specific.
 * If a provider is instantiated and its {@link FileSystemProvider#getScheme()
 * getScheme} returns the same URI scheme of a provider that was previously
 * instantiated then the most recently instantiated duplicate is discarded. URI
 * schemes are compared without regard to case. During construction a provider
 * may safely access files associated with the default provider but care needs
 * to be taken to avoid circular loading of other installed providers. If
 * circular loading of installed providers is detected then an unspecified error
 * is thrown.
 *
 * <p> This class also defines factory methods that allow a {@link ClassLoader}
 * to be specified when locating a provider. As with installed providers, the
 * provider classes are identified by placing the provider configuration file
 * in the resource directory {@code META-INF/services}.
 *
 * <p> If a thread initiates the loading of the installed file system providers
 * and another thread invokes a method that also attempts to load the providers
 * then the method will block until the loading completes.
 *
 * <p>
 *  文件系统的工厂方法。这个类定义了{@link #getDefault getDefault}方法来获取默认的文件系统和工厂方法来构造其他类型的文件系统。
 * 
 *  <p>对此类定义的任何方法的第一次调用会导致加载默认的{@link FileSystemProvider provider}。
 * 由URI方案"file"标识的默认提供程序创建{@link FileSystem},提供对Java虚拟机可访问的文件系统的访问。如果加载或初始化默认提供程序的过程失败,则抛出一个未指定的错误。
 * 
 * <p>第一次调用{@link FileSystemProvider#installedProviders installedProviders}方法时,通过调用此类定义的任何{@code newFileSystem}
 * 方法,定位并加载所有已安装的文件系统提供程序。
 * 已安装的提供程序使用由{@link ServiceLoader}类定义的服务提供程序加载工具加载。已安装的提供程序使用系统类加载器加载。
 * 如果系统类加载器不能被找到,则使用扩展类加载器;如果没有扩展类加载器,那么使用引导类加载器。
 * 提供程序通常通过将它们放置在应用程序类路径或扩展目录中的JAR文件中来安装,JAR文件在资源目录中包含名为{@code java.nio.file.spi.FileSystemProvider}的提供程
 * 序配置文件{ @code META-INF / services},并且该文件列出了{@link FileSystemProvider}的具有零参数构造函数的一个或多个具体子类的完全限定名。
 * 如果系统类加载器不能被找到,则使用扩展类加载器;如果没有扩展类加载器,那么使用引导类加载器。安装的提供程序所在的顺序是特定于实现的。
 * 如果提供程序被实例化并且其{@link FileSystemProvider#getScheme()getScheme}返回之前实例化的提供程序的相同URI方案,则丢弃最近实例化的副本。
 * 比较URI方案而不考虑情况。在构建期间,提供者可以安全地访问与默认提供者相关联的文件,但需要注意避免其他已安装的提供者的循环加载。如果检测到已安装的提供程序的循环加载,则抛出未指定的错误。
 * 
 * <p>此类还定义了允许在查找提供程序时指定{@link ClassLoader}的工厂方法。
 * 与已安装的提供程序一样,通过将提供程序配置文件放在资源目录{@code META-INF / services}中来标识提供程序类。
 * 
 *  <p>如果线程启动加载已安装的文件系统提供程序,并且另一个线程调用一个也试图加载提供程序的方法,则该方法将阻塞,直到加载完成。
 * 
 * 
 * @since 1.7
 */

public final class FileSystems {
    private FileSystems() {
    }

    // lazy initialization of default file system
    private static class DefaultFileSystemHolder {
        static final FileSystem defaultFileSystem = defaultFileSystem();

        // returns default file system
        private static FileSystem defaultFileSystem() {
            // load default provider
            FileSystemProvider provider = AccessController
                .doPrivileged(new PrivilegedAction<FileSystemProvider>() {
                    public FileSystemProvider run() {
                        return getDefaultProvider();
                    }
                });

            // return file system
            return provider.getFileSystem(URI.create("file:///"));
        }

        // returns default provider
        private static FileSystemProvider getDefaultProvider() {
            FileSystemProvider provider = sun.nio.fs.DefaultFileSystemProvider.create();

            // if the property java.nio.file.spi.DefaultFileSystemProvider is
            // set then its value is the name of the default provider (or a list)
            String propValue = System
                .getProperty("java.nio.file.spi.DefaultFileSystemProvider");
            if (propValue != null) {
                for (String cn: propValue.split(",")) {
                    try {
                        Class<?> c = Class
                            .forName(cn, true, ClassLoader.getSystemClassLoader());
                        Constructor<?> ctor = c
                            .getDeclaredConstructor(FileSystemProvider.class);
                        provider = (FileSystemProvider)ctor.newInstance(provider);

                        // must be "file"
                        if (!provider.getScheme().equals("file"))
                            throw new Error("Default provider must use scheme 'file'");

                    } catch (Exception x) {
                        throw new Error(x);
                    }
                }
            }
            return provider;
        }
    }

    /**
     * Returns the default {@code FileSystem}. The default file system creates
     * objects that provide access to the file systems accessible to the Java
     * virtual machine. The <em>working directory</em> of the file system is
     * the current user directory, named by the system property {@code user.dir}.
     * This allows for interoperability with the {@link java.io.File java.io.File}
     * class.
     *
     * <p> The first invocation of any of the methods defined by this class
     * locates the default {@link FileSystemProvider provider} object. Where the
     * system property {@code java.nio.file.spi.DefaultFileSystemProvider} is
     * not defined then the default provider is a system-default provider that
     * is invoked to create the default file system.
     *
     * <p> If the system property {@code java.nio.file.spi.DefaultFileSystemProvider}
     * is defined then it is taken to be a list of one or more fully-qualified
     * names of concrete provider classes identified by the URI scheme
     * {@code "file"}. Where the property is a list of more than one name then
     * the names are separated by a comma. Each class is loaded, using the system
     * class loader, and instantiated by invoking a one argument constructor
     * whose formal parameter type is {@code FileSystemProvider}. The providers
     * are loaded and instantiated in the order they are listed in the property.
     * If this process fails or a provider's scheme is not equal to {@code "file"}
     * then an unspecified error is thrown. URI schemes are normally compared
     * without regard to case but for the default provider, the scheme is
     * required to be {@code "file"}. The first provider class is instantiated
     * by invoking it with a reference to the system-default provider.
     * The second provider class is instantiated by invoking it with a reference
     * to the first provider instance. The third provider class is instantiated
     * by invoking it with a reference to the second instance, and so on. The
     * last provider to be instantiated becomes the default provider; its {@code
     * getFileSystem} method is invoked with the URI {@code "file:///"} to
     * get a reference to the default file system.
     *
     * <p> Subsequent invocations of this method return the file system that was
     * returned by the first invocation.
     *
     * <p>
     *  返回默认的{@code FileSystem}。默认文件系统创建的对象提供对Java虚拟机可访问的文件系统的访问。
     * 文件系统的<em>工作目录</em>是当前用户目录,由系统属性{@code user.dir}命名。这允许与{@link java.io.File java.io.File}类的互操作性。
     * 
     *  <p>对此类定义的任何方法的第一次调用将定位默认的{@link FileSystemProvider provider}对象。
     * 在未定义系统属性{@code java.nio.file.spi.DefaultFileSystemProvider}的情况下,默认提供程序是被调用以创建默认文件系统的系统默认提供程序。
     * 
     * <p>如果定义了系统属性{@code java.nio.file.spi.DefaultFileSystemProvider},则它被视为由URI方案标识的具体提供程序类的一个或多个完全限定名称的列表{@code "文件"}
     * 。
     * 如果属性是多个名称的列表,那么这些名称之间用逗号分隔。使用系统类加载器加载每个类,并通过调用形式参数类型为{@code FileSystemProvider}的一个参数构造函数来实例化。
     * 提供程序按它们在属性中列出的顺序加载和实例化。如果此进程失败或提供程序的方案不等于{@code"file"},则会抛出未指定的错误。
     *  URI方案通常是比较而不考虑情况,但是对于默认提供者,该方案需要是{@code"file"}。第一个提供程序类通过引用系统默认提供程序进行调用来实例化。
     * 第二个提供程序类通过引用第一个提供程序实例来调用它来实例化。第三个提供程序类通过调用它引用第二个实例来实例化,依此类推。
     * 要实例化的最后一个提供程序成为默认提供程序;它的{@code getFileSystem}方法用URI {@code"file：///"}调用以获得对默认文件系统的引用。
     * 
     *  <p>此方法的后续调用返回第一次调用返回的文件系统。
     * 
     * 
     * @return  the default file system
     */
    public static FileSystem getDefault() {
        return DefaultFileSystemHolder.defaultFileSystem;
    }

    /**
     * Returns a reference to an existing {@code FileSystem}.
     *
     * <p> This method iterates over the {@link FileSystemProvider#installedProviders()
     * installed} providers to locate the provider that is identified by the URI
     * {@link URI#getScheme scheme} of the given URI. URI schemes are compared
     * without regard to case. The exact form of the URI is highly provider
     * dependent. If found, the provider's {@link FileSystemProvider#getFileSystem
     * getFileSystem} method is invoked to obtain a reference to the {@code
     * FileSystem}.
     *
     * <p> Once a file system created by this provider is {@link FileSystem#close
     * closed} it is provider-dependent if this method returns a reference to
     * the closed file system or throws {@link FileSystemNotFoundException}.
     * If the provider allows a new file system to be created with the same URI
     * as a file system it previously created then this method throws the
     * exception if invoked after the file system is closed (and before a new
     * instance is created by the {@link #newFileSystem newFileSystem} method).
     *
     * <p> If a security manager is installed then a provider implementation
     * may require to check a permission before returning a reference to an
     * existing file system. In the case of the {@link FileSystems#getDefault
     * default} file system, no permission check is required.
     *
     * <p>
     *  返回对现有{@code FileSystem}的引用。
     * 
     * <p>此方法遍历{@link FileSystemProvider#installedProviders()installed}提供程序,以查找由给定URI的URI {@link URI#getScheme scheme}
     * 标识的提供程序。
     * 比较URI方案而不考虑情况。 URI的确切形式高度依赖于提供程序。
     * 如果找到,则调用提供程序的{@link FileSystemProvider#getFileSystem getFileSystem}方法来获取对{@code FileSystem}的引用。
     * 
     *  <p>如果此方法返回对封闭文件系统的引用或抛出{@link FileSystemNotFoundException},则此提供程序创建的文件系统为{@link FileSystem#close Closed}
     * 时,它取决于提供程序。
     * 如果提供程序允许使用与之前创建的文件系统相同的URI创建新的文件系统,则该方法在文件系统关闭后(以及在{@link# newFileSystem newFileSystem}方法)。
     * 
     *  <p>如果安装了安全管理器,则提供程序实现可能需要在返回对现有文件系统的引用之前检查权限。
     * 对于{@link FileSystems#getDefault default}文件系统,不需要进行权限检查。
     * 
     * 
     * @param   uri  the URI to locate the file system
     *
     * @return  the reference to the file system
     *
     * @throws  IllegalArgumentException
     *          if the pre-conditions for the {@code uri} parameter are not met
     * @throws  FileSystemNotFoundException
     *          if the file system, identified by the URI, does not exist
     * @throws  ProviderNotFoundException
     *          if a provider supporting the URI scheme is not installed
     * @throws  SecurityException
     *          if a security manager is installed and it denies an unspecified
     *          permission
     */
    public static FileSystem getFileSystem(URI uri) {
        String scheme = uri.getScheme();
        for (FileSystemProvider provider: FileSystemProvider.installedProviders()) {
            if (scheme.equalsIgnoreCase(provider.getScheme())) {
                return provider.getFileSystem(uri);
            }
        }
        throw new ProviderNotFoundException("Provider \"" + scheme + "\" not found");
    }

    /**
     * Constructs a new file system that is identified by a {@link URI}
     *
     * <p> This method iterates over the {@link FileSystemProvider#installedProviders()
     * installed} providers to locate the provider that is identified by the URI
     * {@link URI#getScheme scheme} of the given URI. URI schemes are compared
     * without regard to case. The exact form of the URI is highly provider
     * dependent. If found, the provider's {@link FileSystemProvider#newFileSystem(URI,Map)
     * newFileSystem(URI,Map)} method is invoked to construct the new file system.
     *
     * <p> Once a file system is {@link FileSystem#close closed} it is
     * provider-dependent if the provider allows a new file system to be created
     * with the same URI as a file system it previously created.
     *
     * <p> <b>Usage Example:</b>
     * Suppose there is a provider identified by the scheme {@code "memory"}
     * installed:
     * <pre>
     *   Map&lt;String,String&gt; env = new HashMap&lt;&gt;();
     *   env.put("capacity", "16G");
     *   env.put("blockSize", "4k");
     *   FileSystem fs = FileSystems.newFileSystem(URI.create("memory:///?name=logfs"), env);
     * </pre>
     *
     * <p>
     *  构造一个由{@link URI}标识的新文件系统
     * 
     * <p>此方法遍历{@link FileSystemProvider#installedProviders()installed}提供程序,以查找由给定URI的URI {@link URI#getScheme scheme}
     * 标识的提供程序。
     * 比较URI方案而不考虑情况。 URI的确切形式高度依赖于提供程序。
     * 如果找到,则调用提供程序的{@link FileSystemProvider#newFileSystem(URI,Map)newFileSystem(URI,Map)}方法来构造新的文件系统。
     * 
     *  <p>一旦文件系统是{@link FileSystem#close Closed},它是依赖于提供程序的,如果提供程序允许使用与之前创建的文件系统相同的URI创建新的文件系统。
     * 
     *  <p> <b>使用示例：</b>假设有一个由安装的方案{@code"memory"}标识的提供者：
     * <pre>
     *  Map&lt; String,String&gt; env = new HashMap&lt;&gt;(); env.put("capacity","16G"); env.put("blockSize
     * ","4k"); FileSystem fs = FileSystems.newFileSystem(URI.create("memory：///?name = logfs"),env);。
     * </pre>
     * 
     * 
     * @param   uri
     *          the URI identifying the file system
     * @param   env
     *          a map of provider specific properties to configure the file system;
     *          may be empty
     *
     * @return  a new file system
     *
     * @throws  IllegalArgumentException
     *          if the pre-conditions for the {@code uri} parameter are not met,
     *          or the {@code env} parameter does not contain properties required
     *          by the provider, or a property value is invalid
     * @throws  FileSystemAlreadyExistsException
     *          if the file system has already been created
     * @throws  ProviderNotFoundException
     *          if a provider supporting the URI scheme is not installed
     * @throws  IOException
     *          if an I/O error occurs creating the file system
     * @throws  SecurityException
     *          if a security manager is installed and it denies an unspecified
     *          permission required by the file system provider implementation
     */
    public static FileSystem newFileSystem(URI uri, Map<String,?> env)
        throws IOException
    {
        return newFileSystem(uri, env, null);
    }

    /**
     * Constructs a new file system that is identified by a {@link URI}
     *
     * <p> This method first attempts to locate an installed provider in exactly
     * the same manner as the {@link #newFileSystem(URI,Map) newFileSystem(URI,Map)}
     * method. If none of the installed providers support the URI scheme then an
     * attempt is made to locate the provider using the given class loader. If a
     * provider supporting the URI scheme is located then its {@link
     * FileSystemProvider#newFileSystem(URI,Map) newFileSystem(URI,Map)} is
     * invoked to construct the new file system.
     *
     * <p>
     *  构造一个由{@link URI}标识的新文件系统
     * 
     *  <p>此方法首先尝试以与{@link #newFileSystem(URI,Map)newFileSystem(URI,Map)}方法完全相同的方式查找已安装的提供程序。
     * 如果没有安装的提供程序支持URI方案,则尝试使用给定的类加载器来定位提供程序。
     * 如果支持URI方案的提供者被定位,则调用其{@link FileSystemProvider#newFileSystem(URI,Map)newFileSystem(URI,Map)}来构造新的文件系统
     * 。
     * 如果没有安装的提供程序支持URI方案,则尝试使用给定的类加载器来定位提供程序。
     * 
     * 
     * @param   uri
     *          the URI identifying the file system
     * @param   env
     *          a map of provider specific properties to configure the file system;
     *          may be empty
     * @param   loader
     *          the class loader to locate the provider or {@code null} to only
     *          attempt to locate an installed provider
     *
     * @return  a new file system
     *
     * @throws  IllegalArgumentException
     *          if the pre-conditions for the {@code uri} parameter are not met,
     *          or the {@code env} parameter does not contain properties required
     *          by the provider, or a property value is invalid
     * @throws  FileSystemAlreadyExistsException
     *          if the URI scheme identifies an installed provider and the file
     *          system has already been created
     * @throws  ProviderNotFoundException
     *          if a provider supporting the URI scheme is not found
     * @throws  ServiceConfigurationError
     *          when an error occurs while loading a service provider
     * @throws  IOException
     *          an I/O error occurs creating the file system
     * @throws  SecurityException
     *          if a security manager is installed and it denies an unspecified
     *          permission required by the file system provider implementation
     */
    public static FileSystem newFileSystem(URI uri, Map<String,?> env, ClassLoader loader)
        throws IOException
    {
        String scheme = uri.getScheme();

        // check installed providers
        for (FileSystemProvider provider: FileSystemProvider.installedProviders()) {
            if (scheme.equalsIgnoreCase(provider.getScheme())) {
                return provider.newFileSystem(uri, env);
            }
        }

        // if not found, use service-provider loading facility
        if (loader != null) {
            ServiceLoader<FileSystemProvider> sl = ServiceLoader
                .load(FileSystemProvider.class, loader);
            for (FileSystemProvider provider: sl) {
                if (scheme.equalsIgnoreCase(provider.getScheme())) {
                    return provider.newFileSystem(uri, env);
                }
            }
        }

        throw new ProviderNotFoundException("Provider \"" + scheme + "\" not found");
    }

    /**
     * Constructs a new {@code FileSystem} to access the contents of a file as a
     * file system.
     *
     * <p> This method makes use of specialized providers that create pseudo file
     * systems where the contents of one or more files is treated as a file
     * system.
     *
     * <p> This method iterates over the {@link FileSystemProvider#installedProviders()
     * installed} providers. It invokes, in turn, each provider's {@link
     * FileSystemProvider#newFileSystem(Path,Map) newFileSystem(Path,Map)} method
     * with an empty map. If a provider returns a file system then the iteration
     * terminates and the file system is returned. If none of the installed
     * providers return a {@code FileSystem} then an attempt is made to locate
     * the provider using the given class loader. If a provider returns a file
     * system then the lookup terminates and the file system is returned.
     *
     * <p>
     * 构造新的{@code FileSystem}以作为文件系统访问文件的内容。
     * 
     *  <p>此方法使用创建伪文件系统的专用提供程序,其中一个或多个文件的内容被视为文件系统。
     * 
     *  <p>此方法遍历{@link FileSystemProvider#installedProviders()installed}提供程序。
     * 
     * @param   path
     *          the path to the file
     * @param   loader
     *          the class loader to locate the provider or {@code null} to only
     *          attempt to locate an installed provider
     *
     * @return  a new file system
     *
     * @throws  ProviderNotFoundException
     *          if a provider supporting this file type cannot be located
     * @throws  ServiceConfigurationError
     *          when an error occurs while loading a service provider
     * @throws  IOException
     *          if an I/O error occurs
     * @throws  SecurityException
     *          if a security manager is installed and it denies an unspecified
     *          permission
     */
    public static FileSystem newFileSystem(Path path,
                                           ClassLoader loader)
        throws IOException
    {
        if (path == null)
            throw new NullPointerException();
        Map<String,?> env = Collections.emptyMap();

        // check installed providers
        for (FileSystemProvider provider: FileSystemProvider.installedProviders()) {
            try {
                return provider.newFileSystem(path, env);
            } catch (UnsupportedOperationException uoe) {
            }
        }

        // if not found, use service-provider loading facility
        if (loader != null) {
            ServiceLoader<FileSystemProvider> sl = ServiceLoader
                .load(FileSystemProvider.class, loader);
            for (FileSystemProvider provider: sl) {
                try {
                    return provider.newFileSystem(path, env);
                } catch (UnsupportedOperationException uoe) {
                }
            }
        }

        throw new ProviderNotFoundException("Provider not found");
    }
}
