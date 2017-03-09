/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.AccessController;
import java.security.AccessControlContext;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * A simple service-provider loading facility.
 *
 * <p> A <i>service</i> is a well-known set of interfaces and (usually
 * abstract) classes.  A <i>service provider</i> is a specific implementation
 * of a service.  The classes in a provider typically implement the interfaces
 * and subclass the classes defined in the service itself.  Service providers
 * can be installed in an implementation of the Java platform in the form of
 * extensions, that is, jar files placed into any of the usual extension
 * directories.  Providers can also be made available by adding them to the
 * application's class path or by some other platform-specific means.
 *
 * <p> For the purpose of loading, a service is represented by a single type,
 * that is, a single interface or abstract class.  (A concrete class can be
 * used, but this is not recommended.)  A provider of a given service contains
 * one or more concrete classes that extend this <i>service type</i> with data
 * and code specific to the provider.  The <i>provider class</i> is typically
 * not the entire provider itself but rather a proxy which contains enough
 * information to decide whether the provider is able to satisfy a particular
 * request together with code that can create the actual provider on demand.
 * The details of provider classes tend to be highly service-specific; no
 * single class or interface could possibly unify them, so no such type is
 * defined here.  The only requirement enforced by this facility is that
 * provider classes must have a zero-argument constructor so that they can be
 * instantiated during loading.
 *
 * <p><a name="format"> A service provider is identified by placing a
 * <i>provider-configuration file</i> in the resource directory
 * <tt>META-INF/services</tt>.</a>  The file's name is the fully-qualified <a
 * href="../lang/ClassLoader.html#name">binary name</a> of the service's type.
 * The file contains a list of fully-qualified binary names of concrete
 * provider classes, one per line.  Space and tab characters surrounding each
 * name, as well as blank lines, are ignored.  The comment character is
 * <tt>'#'</tt> (<tt>'&#92;u0023'</tt>,
 * <font style="font-size:smaller;">NUMBER SIGN</font>); on
 * each line all characters following the first comment character are ignored.
 * The file must be encoded in UTF-8.
 *
 * <p> If a particular concrete provider class is named in more than one
 * configuration file, or is named in the same configuration file more than
 * once, then the duplicates are ignored.  The configuration file naming a
 * particular provider need not be in the same jar file or other distribution
 * unit as the provider itself.  The provider must be accessible from the same
 * class loader that was initially queried to locate the configuration file;
 * note that this is not necessarily the class loader from which the file was
 * actually loaded.
 *
 * <p> Providers are located and instantiated lazily, that is, on demand.  A
 * service loader maintains a cache of the providers that have been loaded so
 * far.  Each invocation of the {@link #iterator iterator} method returns an
 * iterator that first yields all of the elements of the cache, in
 * instantiation order, and then lazily locates and instantiates any remaining
 * providers, adding each one to the cache in turn.  The cache can be cleared
 * via the {@link #reload reload} method.
 *
 * <p> Service loaders always execute in the security context of the caller.
 * Trusted system code should typically invoke the methods in this class, and
 * the methods of the iterators which they return, from within a privileged
 * security context.
 *
 * <p> Instances of this class are not safe for use by multiple concurrent
 * threads.
 *
 * <p> Unless otherwise specified, passing a <tt>null</tt> argument to any
 * method in this class will cause a {@link NullPointerException} to be thrown.
 *
 *
 * <p><span style="font-weight: bold; padding-right: 1em">Example</span>
 * Suppose we have a service type <tt>com.example.CodecSet</tt> which is
 * intended to represent sets of encoder/decoder pairs for some protocol.  In
 * this case it is an abstract class with two abstract methods:
 *
 * <blockquote><pre>
 * public abstract Encoder getEncoder(String encodingName);
 * public abstract Decoder getDecoder(String encodingName);</pre></blockquote>
 *
 * Each method returns an appropriate object or <tt>null</tt> if the provider
 * does not support the given encoding.  Typical providers support more than
 * one encoding.
 *
 * <p> If <tt>com.example.impl.StandardCodecs</tt> is an implementation of the
 * <tt>CodecSet</tt> service then its jar file also contains a file named
 *
 * <blockquote><pre>
 * META-INF/services/com.example.CodecSet</pre></blockquote>
 *
 * <p> This file contains the single line:
 *
 * <blockquote><pre>
 * com.example.impl.StandardCodecs    # Standard codecs</pre></blockquote>
 *
 * <p> The <tt>CodecSet</tt> class creates and saves a single service instance
 * at initialization:
 *
 * <blockquote><pre>
 * private static ServiceLoader&lt;CodecSet&gt; codecSetLoader
 *     = ServiceLoader.load(CodecSet.class);</pre></blockquote>
 *
 * <p> To locate an encoder for a given encoding name it defines a static
 * factory method which iterates through the known and available providers,
 * returning only when it has located a suitable encoder or has run out of
 * providers.
 *
 * <blockquote><pre>
 * public static Encoder getEncoder(String encodingName) {
 *     for (CodecSet cp : codecSetLoader) {
 *         Encoder enc = cp.getEncoder(encodingName);
 *         if (enc != null)
 *             return enc;
 *     }
 *     return null;
 * }</pre></blockquote>
 *
 * <p> A <tt>getDecoder</tt> method is defined similarly.
 *
 *
 * <p><span style="font-weight: bold; padding-right: 1em">Usage Note</span> If
 * the class path of a class loader that is used for provider loading includes
 * remote network URLs then those URLs will be dereferenced in the process of
 * searching for provider-configuration files.
 *
 * <p> This activity is normal, although it may cause puzzling entries to be
 * created in web-server logs.  If a web server is not configured correctly,
 * however, then this activity may cause the provider-loading algorithm to fail
 * spuriously.
 *
 * <p> A web server should return an HTTP 404 (Not Found) response when a
 * requested resource does not exist.  Sometimes, however, web servers are
 * erroneously configured to return an HTTP 200 (OK) response along with a
 * helpful HTML error page in such cases.  This will cause a {@link
 * ServiceConfigurationError} to be thrown when this class attempts to parse
 * the HTML page as a provider-configuration file.  The best solution to this
 * problem is to fix the misconfigured web server to return the correct
 * response code (HTTP 404) along with the HTML error page.
 *
 * <p>
 *  一个简单的服务提供商加载设施。
 * 
 *  <p> A <i>服务</i>是一组众所周知的接口和(通常是抽象的)类。服务提供商</i>是服务的具体实现。提供程序中的类通常实现接口并对服务本身中定义的类进行子类化。
 * 服务提供程序可以以扩展的形式安装在Java平台的实现中,即,jar文件放置在任何通常的扩展目录中。提供者也可以通过将它们添加到应用程序的类路径或通过一些其他平台特定的手段。
 * 
 * <p>为了加载,服务由单一类型表示,即单个接口或抽象类。 (可以使用具体类,但不建议这样做。)给定服务的提供者包含一个或多个具体类,它们通过特定于提供者的数据和代码来扩展该<i>服务类型</i>。
 *  <i>提供者类</i>通常不是整个提供者本身,而是包含足够信息的代理,以决定提供者是否能够满足特定请求以及可以根据需要创建实际提供者的代码。
 * 提供者类的细节倾向于高度服务特定;没有单个类或接口可能统一它们,因此这里没有定义这样的类型。此工具强制实施的唯一要求是,提供程序类必须具有零参数构造函数,以便在加载过程中实例化它们。
 * 
 * <p> <a name="format">通过在资源目录<tt> META-INF / services </tt>中放置<i>提供程序配置文件</i>来标识服务提供商。
 * </a >文件名称是服务类型的完全限定的<a href="../lang/ClassLoader.html#name">二进制名称</a>。该文件包含具体提供程序类的完全限定二进制名称列表,每行一个。
 * 忽略每个名称周围的空格和制表符以及空行。
 * 注释字符为<tt>'#'</tt>(<tt>'\ u0023'</tt>,<font style ="font-size：smaller;"> NUMBER SIGN </font>);在每行上,忽略第
 * 一个注释字符后面的所有字符。
 * 忽略每个名称周围的空格和制表符以及空行。该文件必须以UTF-8编码。
 * 
 *  <p>如果特定的具体提供程序类在多个配置文件中命名,或者在同一配置文件中多次命名,那么将忽略重复项。指定特定提供者的配置文件不需要位于与提供者本身相同的jar文件或其他分发单元中。
 * 提供程序必须可以从最初查询以查找配置文件的同一类加载器访问;请注意,这不一定是实际加载文件的类加载器。
 * 
 * <p>提供者可以懒惰地定位和实例化,即按需。服务加载器维护迄今为止已加载的提供程序的高速缓存。
 * 每次调用{@link #iterator iterator}方法都会返回一个迭代器,该迭代器首先以实例化顺序生成高速缓存的所有元素,然后延迟定位并实例化任何剩余的提供程序,依次将每个提供程序添加到高速缓
 * 存中。
 * <p>提供者可以懒惰地定位和实例化,即按需。服务加载器维护迄今为止已加载的提供程序的高速缓存。可以通过{@link #reload reload}方法清除缓存。
 * 
 *  <p>服务加载器总是在调用者的安全上下文中执行。可信系统代码通常应该在特权安全上下文中调用此类中的方法,以及它们返回的迭代器的方法。
 * 
 *  <p>此类的实例不适合由多个并发线程使用。
 * 
 *  <p>除非另有说明,否则将<tt> null </tt>参数传递给此类中的任何方法都会导致抛出{@link NullPointerException}。
 * 
 *  <p> <span style ="font-weight：bold; padding-right：1em">示例</span>假设我们有一个服务类型<tt> com.example.CodecSet
 *  </tt>的编码器/解码器对的一些协议。
 * 在这种情况下,它是一个抽象类,有两个抽象方法：。
 * 
 *  <blockquote> <pre> public abstract Encoder getEncoder(String encodingName); public abstract Decoder 
 * getDecoder(String encodingName); </pre> </blockquote>。
 * 
 * 如果提供程序不支持给定的编码,则每个方法都返回一个适当的对象或<tt> null </tt>。典型的提供程序支持多个编码。
 * 
 *  <p>如果<tt> com.example.impl.StandardCodecs </tt>是<tt> CodecSet </tt>服务的实现,那么它的jar文件还包含一个名为
 * 
 *  <blockquote> <pre> META-INF / services / com.example.CodecSet </pre> </blockquote>
 * 
 *  <p>此文件包含单行：
 * 
 *  <blockquote> <pre> com.example.impl.StandardCodecs#标准编解码器</pre> </blockquote>
 * 
 *  <p> <tt> CodecSet </tt>类在初始化时创建并保存单个服务实例：
 * 
 *  <blockquote> <pre> private static ServiceLoader&lt; CodecSet&gt; codecSetLoader = ServiceLoader.load
 * (CodecSet.class); </pre> </blockquote>。
 * 
 *  <p>要定位给定编码名称的编码器,它定义了一个静态工厂方法,它迭代已知和可用的提供程序,只有当它找到一个合适的编码器或已经耗尽提供程序时才返回。
 * 
 *  <blockquote> <pre> public static Encoder getEncoder(String encodingName){for(CodecSet cp：codecSetLoader){Encoder enc = cp.getEncoder(encodingName); if(enc！= null)return enc; }
 *  return null; } </pre> </blockquote>。
 * 
 *  <p> A <tt> getDecoder </tt>方法的定义类似。
 * 
 * <p> <span style ="font-weight：bold; padding-right：1em">使用注意</span>如果用于提供商加载的类加载器的类路径包含远程网络URL,在搜索提供程序
 * 配置文件的过程中被取消引用。
 * 
 *  <p>此活动是正常的,虽然它可能导致在Web服务器日志中创建令人费解的条目。但是,如果Web服务器配置不正确,则此活动可能会导致提供程序加载算法失败。
 * 
 *  <p>当请求的资源不存在时,网络服务器应返回HTTP 404(未找到)响应。然而,有时,Web服务器被错误地配置为在这种情况下返回HTTP 200(OK)响应以及有用的HTML错误页面。
 * 
 * @param  <S>
 *         The type of the service to be loaded by this loader
 *
 * @author Mark Reinhold
 * @since 1.6
 */

public final class ServiceLoader<S>
    implements Iterable<S>
{

    private static final String PREFIX = "META-INF/services/";

    // The class or interface representing the service being loaded
    private final Class<S> service;

    // The class loader used to locate, load, and instantiate providers
    private final ClassLoader loader;

    // The access control context taken when the ServiceLoader is created
    private final AccessControlContext acc;

    // Cached providers, in instantiation order
    private LinkedHashMap<String,S> providers = new LinkedHashMap<>();

    // The current lazy-lookup iterator
    private LazyIterator lookupIterator;

    /**
     * Clear this loader's provider cache so that all providers will be
     * reloaded.
     *
     * <p> After invoking this method, subsequent invocations of the {@link
     * #iterator() iterator} method will lazily look up and instantiate
     * providers from scratch, just as is done by a newly-created loader.
     *
     * <p> This method is intended for use in situations in which new providers
     * can be installed into a running Java virtual machine.
     * <p>
     * 当此类尝试将HTML页面解析为提供程序配置文件时,将导致{@link ServiceConfigurationError}被抛出。
     * 此问题的最佳解决方案是修复错误配置的Web服务器以返回正确的响应代码(HTTP 404)以及HTML错误页面。
     * 
     */
    public void reload() {
        providers.clear();
        lookupIterator = new LazyIterator(service, loader);
    }

    private ServiceLoader(Class<S> svc, ClassLoader cl) {
        service = Objects.requireNonNull(svc, "Service interface cannot be null");
        loader = (cl == null) ? ClassLoader.getSystemClassLoader() : cl;
        acc = (System.getSecurityManager() != null) ? AccessController.getContext() : null;
        reload();
    }

    private static void fail(Class<?> service, String msg, Throwable cause)
        throws ServiceConfigurationError
    {
        throw new ServiceConfigurationError(service.getName() + ": " + msg,
                                            cause);
    }

    private static void fail(Class<?> service, String msg)
        throws ServiceConfigurationError
    {
        throw new ServiceConfigurationError(service.getName() + ": " + msg);
    }

    private static void fail(Class<?> service, URL u, int line, String msg)
        throws ServiceConfigurationError
    {
        fail(service, u + ":" + line + ": " + msg);
    }

    // Parse a single line from the given configuration file, adding the name
    // on the line to the names list.
    //
    private int parseLine(Class<?> service, URL u, BufferedReader r, int lc,
                          List<String> names)
        throws IOException, ServiceConfigurationError
    {
        String ln = r.readLine();
        if (ln == null) {
            return -1;
        }
        int ci = ln.indexOf('#');
        if (ci >= 0) ln = ln.substring(0, ci);
        ln = ln.trim();
        int n = ln.length();
        if (n != 0) {
            if ((ln.indexOf(' ') >= 0) || (ln.indexOf('\t') >= 0))
                fail(service, u, lc, "Illegal configuration-file syntax");
            int cp = ln.codePointAt(0);
            if (!Character.isJavaIdentifierStart(cp))
                fail(service, u, lc, "Illegal provider-class name: " + ln);
            for (int i = Character.charCount(cp); i < n; i += Character.charCount(cp)) {
                cp = ln.codePointAt(i);
                if (!Character.isJavaIdentifierPart(cp) && (cp != '.'))
                    fail(service, u, lc, "Illegal provider-class name: " + ln);
            }
            if (!providers.containsKey(ln) && !names.contains(ln))
                names.add(ln);
        }
        return lc + 1;
    }

    // Parse the content of the given URL as a provider-configuration file.
    //
    // @param  service
    //         The service type for which providers are being sought;
    //         used to construct error detail strings
    //
    // @param  u
    //         The URL naming the configuration file to be parsed
    //
    // @return A (possibly empty) iterator that will yield the provider-class
    //         names in the given configuration file that are not yet members
    //         of the returned set
    //
    // @throws ServiceConfigurationError
    //         If an I/O error occurs while reading from the given URL, or
    //         if a configuration-file format error is detected
    //
    private Iterator<String> parse(Class<?> service, URL u)
        throws ServiceConfigurationError
    {
        InputStream in = null;
        BufferedReader r = null;
        ArrayList<String> names = new ArrayList<>();
        try {
            in = u.openStream();
            r = new BufferedReader(new InputStreamReader(in, "utf-8"));
            int lc = 1;
            while ((lc = parseLine(service, u, r, lc, names)) >= 0);
        } catch (IOException x) {
            fail(service, "Error reading configuration file", x);
        } finally {
            try {
                if (r != null) r.close();
                if (in != null) in.close();
            } catch (IOException y) {
                fail(service, "Error closing configuration file", y);
            }
        }
        return names.iterator();
    }

    // Private inner class implementing fully-lazy provider lookup
    //
    private class LazyIterator
        implements Iterator<S>
    {

        Class<S> service;
        ClassLoader loader;
        Enumeration<URL> configs = null;
        Iterator<String> pending = null;
        String nextName = null;

        private LazyIterator(Class<S> service, ClassLoader loader) {
            this.service = service;
            this.loader = loader;
        }

        private boolean hasNextService() {
            if (nextName != null) {
                return true;
            }
            if (configs == null) {
                try {
                    String fullName = PREFIX + service.getName();
                    if (loader == null)
                        configs = ClassLoader.getSystemResources(fullName);
                    else
                        configs = loader.getResources(fullName);
                } catch (IOException x) {
                    fail(service, "Error locating configuration files", x);
                }
            }
            while ((pending == null) || !pending.hasNext()) {
                if (!configs.hasMoreElements()) {
                    return false;
                }
                pending = parse(service, configs.nextElement());
            }
            nextName = pending.next();
            return true;
        }

        private S nextService() {
            if (!hasNextService())
                throw new NoSuchElementException();
            String cn = nextName;
            nextName = null;
            Class<?> c = null;
            try {
                c = Class.forName(cn, false, loader);
            } catch (ClassNotFoundException x) {
                fail(service,
                     "Provider " + cn + " not found");
            }
            if (!service.isAssignableFrom(c)) {
                fail(service,
                     "Provider " + cn  + " not a subtype");
            }
            try {
                S p = service.cast(c.newInstance());
                providers.put(cn, p);
                return p;
            } catch (Throwable x) {
                fail(service,
                     "Provider " + cn + " could not be instantiated",
                     x);
            }
            throw new Error();          // This cannot happen
        }

        public boolean hasNext() {
            if (acc == null) {
                return hasNextService();
            } else {
                PrivilegedAction<Boolean> action = new PrivilegedAction<Boolean>() {
                    public Boolean run() { return hasNextService(); }
                };
                return AccessController.doPrivileged(action, acc);
            }
        }

        public S next() {
            if (acc == null) {
                return nextService();
            } else {
                PrivilegedAction<S> action = new PrivilegedAction<S>() {
                    public S run() { return nextService(); }
                };
                return AccessController.doPrivileged(action, acc);
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    /**
     * Lazily loads the available providers of this loader's service.
     *
     * <p> The iterator returned by this method first yields all of the
     * elements of the provider cache, in instantiation order.  It then lazily
     * loads and instantiates any remaining providers, adding each one to the
     * cache in turn.
     *
     * <p> To achieve laziness the actual work of parsing the available
     * provider-configuration files and instantiating providers must be done by
     * the iterator itself.  Its {@link java.util.Iterator#hasNext hasNext} and
     * {@link java.util.Iterator#next next} methods can therefore throw a
     * {@link ServiceConfigurationError} if a provider-configuration file
     * violates the specified format, or if it names a provider class that
     * cannot be found and instantiated, or if the result of instantiating the
     * class is not assignable to the service type, or if any other kind of
     * exception or error is thrown as the next provider is located and
     * instantiated.  To write robust code it is only necessary to catch {@link
     * ServiceConfigurationError} when using a service iterator.
     *
     * <p> If such an error is thrown then subsequent invocations of the
     * iterator will make a best effort to locate and instantiate the next
     * available provider, but in general such recovery cannot be guaranteed.
     *
     * <blockquote style="font-size: smaller; line-height: 1.2"><span
     * style="padding-right: 1em; font-weight: bold">Design Note</span>
     * Throwing an error in these cases may seem extreme.  The rationale for
     * this behavior is that a malformed provider-configuration file, like a
     * malformed class file, indicates a serious problem with the way the Java
     * virtual machine is configured or is being used.  As such it is
     * preferable to throw an error rather than try to recover or, even worse,
     * fail silently.</blockquote>
     *
     * <p> The iterator returned by this method does not support removal.
     * Invoking its {@link java.util.Iterator#remove() remove} method will
     * cause an {@link UnsupportedOperationException} to be thrown.
     *
     * @implNote When adding providers to the cache, the {@link #iterator
     * Iterator} processes resources in the order that the {@link
     * java.lang.ClassLoader#getResources(java.lang.String)
     * ClassLoader.getResources(String)} method finds the service configuration
     * files.
     *
     * <p>
     *  清除此加载程序的提供程序缓存,以便重新加载所有提供程序。
     * 
     *  <p>调用此方法后,{@link #iterator()iterator}方法的后续调用将懒洋洋地查找并从头开始实例化提供程序,就像新创建的加载程序所做的那样。
     * 
     *  <p>此方法适用于新的提供程序可以安装到正在运行的Java虚拟机中的情况。
     * 
     * 
     * @return  An iterator that lazily loads providers for this loader's
     *          service
     */
    public Iterator<S> iterator() {
        return new Iterator<S>() {

            Iterator<Map.Entry<String,S>> knownProviders
                = providers.entrySet().iterator();

            public boolean hasNext() {
                if (knownProviders.hasNext())
                    return true;
                return lookupIterator.hasNext();
            }

            public S next() {
                if (knownProviders.hasNext())
                    return knownProviders.next().getValue();
                return lookupIterator.next();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

        };
    }

    /**
     * Creates a new service loader for the given service type and class
     * loader.
     *
     * <p>
     * Lazily加载此加载器的服务的可用提供程序。
     * 
     *  <p>此方法返回的迭代器首先按实例化顺序生成提供程序缓存的所有元素。然后它懒惰加载和实例化任何剩余的提供程序,依次将每个提交到缓存。
     * 
     *  <p>为了实现惰性,解析可用的提供程序配置文件和实例化提供程序的实际工作必须由迭代器本身完成。
     * 因此,如果提供程序配置文件违反指定的格式,则它的{@link java.util.Iterator#hasNext hasNext}和{@link java.util.Iterator#next next}
     * 方法可能会引发{@link ServiceConfigurationError}命名无法找到和实例化的提供程序类,或者实例化该类的结果不能分配给服务类型,或者在下一个提供者被定位和实例化时抛出任何其他种
     * 类的异常或错误。
     *  <p>为了实现惰性,解析可用的提供程序配置文件和实例化提供程序的实际工作必须由迭代器本身完成。
     * 要编写健壮的代码,只需要在使用服务迭代器时捕获{@link ServiceConfigurationError}。
     * 
     *  <p>如果抛出这样的错误,则迭代器的后续调用将尽最大努力来定位和实例化下一个可用的提供程序,但是通常不能保证这种恢复。
     * 
     * <blockquote style ="font-size：smaller; line-height：1.2"> <span style ="padding-right：1em; font-weight：bold">
     * 设计注意</span>在这些情况下抛出错误极端。
     * 此行为的基本原理是,格式不正确的提供程序配置文件(如格式不正确的类文件)表明Java虚拟机配置或正在使用的方式存在严重问题。因此,最好抛出一个错误,而不是试图恢复,或者更糟糕的是,默默失败。
     * </blockquote>。
     * 
     *  <p>此方法返回的迭代器不支持删除。
     * 调用其{@link java.util.Iterator#remove()remove}方法将导致抛出{@link UnsupportedOperationException}。
     * 
     *  @implNote当将提供者添加到缓存时,{@link #iterator迭代器}会按照{@link java.lang.ClassLoader#getResources(java.lang.String)ClassLoader.getResources(String)}
     * 方法找到的顺序处理资源服务配置文件。
     * 
     * 
     * @param  <S> the class of the service type
     *
     * @param  service
     *         The interface or abstract class representing the service
     *
     * @param  loader
     *         The class loader to be used to load provider-configuration files
     *         and provider classes, or <tt>null</tt> if the system class
     *         loader (or, failing that, the bootstrap class loader) is to be
     *         used
     *
     * @return A new service loader
     */
    public static <S> ServiceLoader<S> load(Class<S> service,
                                            ClassLoader loader)
    {
        return new ServiceLoader<>(service, loader);
    }

    /**
     * Creates a new service loader for the given service type, using the
     * current thread's {@linkplain java.lang.Thread#getContextClassLoader
     * context class loader}.
     *
     * <p> An invocation of this convenience method of the form
     *
     * <blockquote><pre>
     * ServiceLoader.load(<i>service</i>)</pre></blockquote>
     *
     * is equivalent to
     *
     * <blockquote><pre>
     * ServiceLoader.load(<i>service</i>,
     *                    Thread.currentThread().getContextClassLoader())</pre></blockquote>
     *
     * <p>
     *  为给定的服务类型和类装入器创建一个新的服务加载器。
     * 
     * 
     * @param  <S> the class of the service type
     *
     * @param  service
     *         The interface or abstract class representing the service
     *
     * @return A new service loader
     */
    public static <S> ServiceLoader<S> load(Class<S> service) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        return ServiceLoader.load(service, cl);
    }

    /**
     * Creates a new service loader for the given service type, using the
     * extension class loader.
     *
     * <p> This convenience method simply locates the extension class loader,
     * call it <tt><i>extClassLoader</i></tt>, and then returns
     *
     * <blockquote><pre>
     * ServiceLoader.load(<i>service</i>, <i>extClassLoader</i>)</pre></blockquote>
     *
     * <p> If the extension class loader cannot be found then the system class
     * loader is used; if there is no system class loader then the bootstrap
     * class loader is used.
     *
     * <p> This method is intended for use when only installed providers are
     * desired.  The resulting service will only find and load providers that
     * have been installed into the current Java virtual machine; providers on
     * the application's class path will be ignored.
     *
     * <p>
     *  使用当前线程的{@linkplain java.lang.Thread#getContextClassLoader上下文类加载器}为给定的服务类型创建一个新的服务加载器。
     * 
     *  <p>表单的这个方便方法的调用
     * 
     *  <blockquote> <pre> ServiceLoader.load(<i> service </i>)</pre> </blockquote>
     * 
     *  相当于
     * 
     *  <blockquote> <pre> ServiceLoader.load(<i> service </i>,Thread.currentThread()。
     * getContextClassLoader())</pre> </blockquote>。
     * 
     * 
     * @param  <S> the class of the service type
     *
     * @param  service
     *         The interface or abstract class representing the service
     *
     * @return A new service loader
     */
    public static <S> ServiceLoader<S> loadInstalled(Class<S> service) {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        ClassLoader prev = null;
        while (cl != null) {
            prev = cl;
            cl = cl.getParent();
        }
        return ServiceLoader.load(service, prev);
    }

    /**
     * Returns a string describing this service.
     *
     * <p>
     * 使用扩展类加载器为给定的服务类型创建一个新的服务加载器。
     * 
     *  <p>这种方便的方法只需定位扩展类​​加载器,调用<tt> <i> extClassLoader </i> </tt>,然后返回
     * 
     *  <blockquote> <pre> ServiceLoader.load(<i> service </i>,<i> extClassLoader </i>)</pre> </blockquote>。
     * 
     *  <p>如果无法找到扩展类加载器,则使用系统类加载器;如果没有系统类加载器,则使用引导类加载器。
     * 
     * @return  A descriptive string
     */
    public String toString() {
        return "java.util.ServiceLoader[" + service.getName() + "]";
    }

}
