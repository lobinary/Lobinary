/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.imageio.spi;

import java.security.PrivilegedAction;
import java.security.AccessController;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Vector;
import com.sun.imageio.spi.FileImageInputStreamSpi;
import com.sun.imageio.spi.FileImageOutputStreamSpi;
import com.sun.imageio.spi.InputStreamImageInputStreamSpi;
import com.sun.imageio.spi.OutputStreamImageOutputStreamSpi;
import com.sun.imageio.spi.RAFImageInputStreamSpi;
import com.sun.imageio.spi.RAFImageOutputStreamSpi;
import com.sun.imageio.plugins.gif.GIFImageReaderSpi;
import com.sun.imageio.plugins.gif.GIFImageWriterSpi;
import com.sun.imageio.plugins.jpeg.JPEGImageReaderSpi;
import com.sun.imageio.plugins.jpeg.JPEGImageWriterSpi;
import com.sun.imageio.plugins.png.PNGImageReaderSpi;
import com.sun.imageio.plugins.png.PNGImageWriterSpi;
import com.sun.imageio.plugins.bmp.BMPImageReaderSpi;
import com.sun.imageio.plugins.bmp.BMPImageWriterSpi;
import com.sun.imageio.plugins.wbmp.WBMPImageReaderSpi;
import com.sun.imageio.plugins.wbmp.WBMPImageWriterSpi;
import sun.awt.AppContext;
import java.util.ServiceLoader;
import java.util.ServiceConfigurationError;

/**
 * A registry for service provider instances.  Service provider
 * classes may be detected at run time by means of meta-information in
 * the JAR files containing them.  The intent is that it be relatively
 * inexpensive to load and inspect all available service provider
 * classes.  These classes may them be used to locate and instantiate
 * more heavyweight classes that will perform actual work, in this
 * case instances of <code>ImageReader</code>,
 * <code>ImageWriter</code>, <code>ImageTranscoder</code>,
 * <code>ImageInputStream</code>, and <code>ImageOutputStream</code>.
 *
 * <p> Service providers found on the system classpath (typically
 * the <code>lib/ext</code> directory in the Java
 * installation directory) are automatically loaded as soon as this class is
 * instantiated.
 *
 * <p> When the <code>registerApplicationClasspathSpis</code> method
 * is called, service provider instances declared in the
 * meta-information section of JAR files on the application class path
 * are loaded.  To declare a service provider, a <code>services</code>
 * subdirectory is placed within the <code>META-INF</code> directory
 * that is present in every JAR file.  This directory contains a file
 * for each service provider interface that has one or more
 * implementation classes present in the JAR file.  For example, if
 * the JAR file contained a class named
 * <code>com.mycompany.imageio.MyFormatReaderSpi</code> which
 * implements the <code>ImageReaderSpi</code> interface, the JAR file
 * would contain a file named:
 *
 * <pre>
 * META-INF/services/javax.imageio.spi.ImageReaderSpi
 * </pre>
 *
 * containing the line:
 *
 * <pre>
 * com.mycompany.imageio.MyFormatReaderSpi
 * </pre>
 *
 * <p> The service provider classes are intended to be lightweight
 * and quick to load.  Implementations of these interfaces
 * should avoid complex dependencies on other classes and on
 * native code.
 *
 * <p> It is also possible to manually add service providers not found
 * automatically, as well as to remove those that are using the
 * interfaces of the <code>ServiceRegistry</code> class.  Thus
 * the application may customize the contents of the registry as it
 * sees fit.
 *
 * <p> For more details on declaring service providers, and the JAR
 * format in general, see the <a
 * href="{@docRoot}/../technotes/guides/jar/jar.html">
 * JAR File Specification</a>.
 *
 * <p>
 *  服务提供程序实例的注册表。服务提供程序类可以在运行时通过包含它们的JAR文件中的元信息来检测。其目的是加载和检查所有可用的服务提供程序类相对便宜。
 * 这些类可以用于定位和实例化将执行实际工作的更重的类,在这种情况下是<code> ImageReader </code>,<code> ImageWriter </code>,<code> ImageTr
 * anscoder </code> ,<code> ImageInputStream </code>和<code> ImageOutputStream </code>。
 *  服务提供程序实例的注册表。服务提供程序类可以在运行时通过包含它们的JAR文件中的元信息来检测。其目的是加载和检查所有可用的服务提供程序类相对便宜。
 * 
 *  <p>系统类路径(通常是Java安装目录中的<code> lib / ext </code>目录)中找到的服务提供程序会在实例化此类时自动加载。
 * 
 * <p>当调用<code> registerApplicationClasspathSpis </code>方法时,会加载在应用程序类路径上的JAR文件的元信息部分中声明的服务提供程序实例。
 * 要声明服务提供程序,将<code> services </code>子目录放在每个JAR文件中存在的<code> META-INF </code>目录中。
 * 此目录包含每个服务提供程序接口的文件,该文件在JAR文件中具有一个或多个实现类。
 * 例如,如果JAR文件包含实现<code> ImageReaderSpi </code>接口的<code> com.mycompany.imageio.MyFormatReaderSpi </code>类
 * ,JAR文件将包含一个名为：。
 * 此目录包含每个服务提供程序接口的文件,该文件在JAR文件中具有一个或多个实现类。
 * 
 * <pre>
 *  META-INF / services / javax.imageio.spi.ImageReaderSpi
 * </pre>
 * 
 *  包含行：
 * 
 * <pre>
 *  MyFormatReaderSpi
 * </pre>
 * 
 *  <p>服务提供程序类旨在实现轻量级和快速加载。这些接口的实现应避免对其他类和本地代码的复杂依赖。
 * 
 *  <p>也可以手动添加未自动找到的服务提供者,以及删除那些使用<code> ServiceRegistry </code>类接口的服务提供者。因此,应用程序可以定制其认为合适的注册表的内容。
 */
public final class IIORegistry extends ServiceRegistry {

    /**
     * A <code>Vector</code> containing the valid IIO registry
     * categories (superinterfaces) to be used in the constructor.
     * <p>
     * 
     *  <p>有关声明服务提供程序和JAR格式的更多详细信息,请参阅<a
     * href="{@docRoot}/../technotes/guides/jar/jar.html">
     *  JAR文件规范</a>。
     * 
     */
    private static final Vector initialCategories = new Vector(5);

    static {
        initialCategories.add(ImageReaderSpi.class);
        initialCategories.add(ImageWriterSpi.class);
        initialCategories.add(ImageTranscoderSpi.class);
        initialCategories.add(ImageInputStreamSpi.class);
        initialCategories.add(ImageOutputStreamSpi.class);
    }

    /**
     * Set up the valid service provider categories and automatically
     * register all available service providers.
     *
     * <p> The constructor is private in order to prevent creation of
     * additional instances.
     * <p>
     * 包含要在构造函数中使用的有效IIO注册表类别(超级接口)的<code> Vector </code>。
     * 
     */
    private IIORegistry() {
        super(initialCategories.iterator());
        registerStandardSpis();
        registerApplicationClasspathSpis();
    }

    /**
     * Returns the default <code>IIORegistry</code> instance used by
     * the Image I/O API.  This instance should be used for all
     * registry functions.
     *
     * <p> Each <code>ThreadGroup</code> will receive its own
     * instance; this allows different <code>Applet</code>s in the
     * same browser (for example) to each have their own registry.
     *
     * <p>
     *  设置有效的服务提供商类别,并自动注册所有可用的服务提供商。
     * 
     *  <p>构造函数是私有的,以防止创建其他实例。
     * 
     * 
     * @return the default registry for the current
     * <code>ThreadGroup</code>.
     */
    public static IIORegistry getDefaultInstance() {
        AppContext context = AppContext.getAppContext();
        IIORegistry registry =
            (IIORegistry)context.get(IIORegistry.class);
        if (registry == null) {
            // Create an instance for this AppContext
            registry = new IIORegistry();
            context.put(IIORegistry.class, registry);
        }
        return registry;
    }

    private void registerStandardSpis() {
        // Hardwire standard SPIs
        registerServiceProvider(new GIFImageReaderSpi());
        registerServiceProvider(new GIFImageWriterSpi());
        registerServiceProvider(new BMPImageReaderSpi());
        registerServiceProvider(new BMPImageWriterSpi());
        registerServiceProvider(new WBMPImageReaderSpi());
        registerServiceProvider(new WBMPImageWriterSpi());
        registerServiceProvider(new PNGImageReaderSpi());
        registerServiceProvider(new PNGImageWriterSpi());
        registerServiceProvider(new JPEGImageReaderSpi());
        registerServiceProvider(new JPEGImageWriterSpi());
        registerServiceProvider(new FileImageInputStreamSpi());
        registerServiceProvider(new FileImageOutputStreamSpi());
        registerServiceProvider(new InputStreamImageInputStreamSpi());
        registerServiceProvider(new OutputStreamImageOutputStreamSpi());
        registerServiceProvider(new RAFImageInputStreamSpi());
        registerServiceProvider(new RAFImageOutputStreamSpi());

        registerInstalledProviders();
    }

    /**
     * Registers all available service providers found on the
     * application class path, using the default
     * <code>ClassLoader</code>.  This method is typically invoked by
     * the <code>ImageIO.scanForPlugins</code> method.
     *
     * <p>
     *  返回由Image I / O API使用的默认<code> IIORegistry </code>实例。此实例应用于所有注册表函数。
     * 
     *  <p>每个<code> ThreadGroup </code>都会收到自己的实例;这允许不同的<code> Applet </code>在同一个浏览器(例如)每个都有自己的注册表。
     * 
     * 
     * @see javax.imageio.ImageIO#scanForPlugins
     * @see ClassLoader#getResources
     */
    public void registerApplicationClasspathSpis() {
        // FIX: load only from application classpath

        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        Iterator categories = getCategories();
        while (categories.hasNext()) {
            Class<IIOServiceProvider> c = (Class)categories.next();
            Iterator<IIOServiceProvider> riter =
                    ServiceLoader.load(c, loader).iterator();
            while (riter.hasNext()) {
                try {
                    // Note that the next() call is required to be inside
                    // the try/catch block; see 6342404.
                    IIOServiceProvider r = riter.next();
                    registerServiceProvider(r);
                } catch (ServiceConfigurationError err) {
                    if (System.getSecurityManager() != null) {
                        // In the applet case, we will catch the  error so
                        // registration of other plugins can  proceed
                        err.printStackTrace();
                    } else {
                        // In the application case, we will  throw the
                        // error to indicate app/system  misconfiguration
                        throw err;
                    }
                }
            }
        }
    }

    private void registerInstalledProviders() {
        /*
          We need to load installed providers from the
          system classpath (typically the <code>lib/ext</code>
          directory in in the Java installation directory)
          in the privileged mode in order to
          be able read corresponding jar files even if
          file read capability is restricted (like the
          applet context case).
        /* <p>
        /*  注册应用程序类路径上找到的所有可用服务提供程序,使用默认的<code> ClassLoader </code>。
        /* 此方法通常由<code> ImageIO.scanForPlugins </code>方法调用。
        /* 
         */
        PrivilegedAction doRegistration =
            new PrivilegedAction() {
                public Object run() {
                    Iterator categories = getCategories();
                    while (categories.hasNext()) {
                        Class<IIOServiceProvider> c = (Class)categories.next();
                        for (IIOServiceProvider p : ServiceLoader.loadInstalled(c)) {
                            registerServiceProvider(p);
                        }
                    }
                    return this;
                }
            };

        AccessController.doPrivileged(doRegistration);
    }
}
