/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.rmi.server;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * <code>RMIClassLoader</code> comprises static methods to support
 * dynamic class loading with RMI.  Included are methods for loading
 * classes from a network location (one or more URLs) and obtaining
 * the location from which an existing class should be loaded by
 * remote parties.  These methods are used by the RMI runtime when
 * marshalling and unmarshalling classes contained in the arguments
 * and return values of remote method calls, and they also may be
 * invoked directly by applications in order to mimic RMI's dynamic
 * class loading behavior.
 *
 * <p>The implementation of the following static methods
 *
 * <ul>
 *
 * <li>{@link #loadClass(URL,String)}
 * <li>{@link #loadClass(String,String)}
 * <li>{@link #loadClass(String,String,ClassLoader)}
 * <li>{@link #loadProxyClass(String,String[],ClassLoader)}
 * <li>{@link #getClassLoader(String)}
 * <li>{@link #getClassAnnotation(Class)}
 *
 * </ul>
 *
 * is provided by an instance of {@link RMIClassLoaderSpi}, the
 * service provider interface for those methods.  When one of the
 * methods is invoked, its behavior is to delegate to a corresponding
 * method on the service provider instance.  The details of how each
 * method delegates to the provider instance is described in the
 * documentation for each particular method.
 *
 * <p>The service provider instance is chosen as follows:
 *
 * <ul>
 *
 * <li>If the system property
 * <code>java.rmi.server.RMIClassLoaderSpi</code> is defined, then if
 * its value equals the string <code>"default"</code>, the provider
 * instance will be the value returned by an invocation of the {@link
 * #getDefaultProviderInstance()} method, and for any other value, if
 * a class named with the value of the property can be loaded by the
 * system class loader (see {@link ClassLoader#getSystemClassLoader})
 * and that class is assignable to {@link RMIClassLoaderSpi} and has a
 * public no-argument constructor, then that constructor will be
 * invoked to create the provider instance.  If the property is
 * defined but any other of those conditions are not true, then an
 * unspecified <code>Error</code> will be thrown to code that attempts
 * to use <code>RMIClassLoader</code>, indicating the failure to
 * obtain a provider instance.
 *
 * <li>If a resource named
 * <code>META-INF/services/java.rmi.server.RMIClassLoaderSpi</code> is
 * visible to the system class loader, then the contents of that
 * resource are interpreted as a provider-configuration file, and the
 * first class name specified in that file is used as the provider
 * class name.  If a class with that name can be loaded by the system
 * class loader and that class is assignable to {@link
 * RMIClassLoaderSpi} and has a public no-argument constructor, then
 * that constructor will be invoked to create the provider instance.
 * If the resource is found but a provider cannot be instantiated as
 * described, then an unspecified <code>Error</code> will be thrown to
 * code that attempts to use <code>RMIClassLoader</code>, indicating
 * the failure to obtain a provider instance.
 *
 * <li>Otherwise, the provider instance will be the value returned by
 * an invocation of the {@link #getDefaultProviderInstance()} method.
 *
 * </ul>
 *
 * <p>
 *  <code> RMIClassLoader </code>包含静态方法以支持使用RMI进行动态类加载。包括用于从网络位置(一个或多个URL)加载类并且获得远程方应当加载现有类的位置的方法。
 * 当编组和解组远程方法调用的参数和返回值中的类时,这些方法由RMI运行时使用,并且它们也可以由应用程序直接调用以模仿RMI的动态类加载行为。
 * 
 *  <p>执行以下静态方法
 * 
 * <ul>
 * 
 *  <li> {@ link #loadClass(URL,String)} <li> {@ link #loadClass(String,String)} <li> {@ link #loadClass(String,String,ClassLoader)}
 *  <li> {@ link #loadProxyClass(String,String [],ClassLoader)} <li> {@ link #getClassLoader(String)} <li>
 *  {@ link #getClassAnnotation(Class)}。
 * 
 * </ul>
 * 
 *  由{@link RMIClassLoaderSpi}的实例提供,这是这些方法的服务提供程序接口。当调用其中一个方法时,其行为是委托给服务提供程序实例上的相应方法。
 * 每个特定方法的文档中描述了每个方法如何委派给提供程序实例的详细信息。
 * 
 *  <p>服务提供程序实例选择如下：
 * 
 * <ul>
 * 
 * <li>如果定义了系统属性<code> java.rmi.server.RMIClassLoaderSpi </code>,则如果其值等于<code>"default"</code>字符串,则提供程序实
 * 例将是返回的值通过调用{@link #getDefaultProviderInstance()}方法,对于任何其他值,如果使用属性值命名的类可以由系统类加载器加载(参见{@link ClassLoader#getSystemClassLoader}
 * ),该类可分配给{@link RMIClassLoaderSpi}并具有公共无参构造函数,那么将调用该构造函数来创建提供者实例。
 * 如果定义了该属性,但这些条件中的任何其他条件都不为真,则会向尝试使用<code> RMIClassLoader </code>的代码抛出未指定的<code> Error </code>,表示无法获取提供
 * 程序实例。
 * 
 * <li>如果名为<code> META-INF / services / java.rmi.server.RMIClassLoaderSpi </code>的资源对系统类加载器可见,那么该资源的内容将被
 * 解释为提供程序配置文件,并将该文件中指定的第一个类名用作提供程序类名。
 * 如果具有该名称的类可以由系统类加载器加载,并且该类可分配给{@link RMIClassLoaderSpi}并具有公共无参构造函数,那么将调用该构造函数来创建提供者实例。
 * 如果找到资源,但是提供者不能如所描述的那样被实例化,那么将试图使用<code> RMIClassLoader </code>的代码抛出未指定的<code> Error </code>,表示无法获取提供者
 * 实例。
 * 如果具有该名称的类可以由系统类加载器加载,并且该类可分配给{@link RMIClassLoaderSpi}并具有公共无参构造函数,那么将调用该构造函数来创建提供者实例。
 * 
 *  <li>否则,提供程序实例将是调用{@link #getDefaultProviderInstance()}方法时返回的值。
 * 
 * </ul>
 * 
 * 
 * @author      Ann Wollrath
 * @author      Peter Jones
 * @author      Laird Dornin
 * @see         RMIClassLoaderSpi
 * @since       JDK1.1
 */
public class RMIClassLoader {

    /** "default" provider instance */
    private static final RMIClassLoaderSpi defaultProvider =
        newDefaultProviderInstance();

    /** provider instance */
    private static final RMIClassLoaderSpi provider =
        AccessController.doPrivileged(
            new PrivilegedAction<RMIClassLoaderSpi>() {
                public RMIClassLoaderSpi run() { return initializeProvider(); }
            });

    /*
     * Disallow anyone from creating one of these.
     * <p>
     *  禁止任何人创建其中的一个。
     * 
     */
    private RMIClassLoader() {}

    /**
     * Loads the class with the specified <code>name</code>.
     *
     * <p>This method delegates to {@link #loadClass(String,String)},
     * passing <code>null</code> as the first argument and
     * <code>name</code> as the second argument.
     *
     * <p>
     *  使用指定的<code> name </code>加载类。
     * 
     *  <p>此方法委托{@link #loadClass(String,String)},传递<code> null </code>作为第一个参数,并将<code> name </code>作为第二个参数。
     * 
     * 
     * @param   name the name of the class to load
     *
     * @return  the <code>Class</code> object representing the loaded class
     *
     * @throws MalformedURLException if a provider-specific URL used
     * to load classes is invalid
     *
     * @throws  ClassNotFoundException if a definition for the class
     * could not be found at the codebase location
     *
     * @deprecated replaced by <code>loadClass(String,String)</code> method
     * @see #loadClass(String,String)
     */
    @Deprecated
    public static Class<?> loadClass(String name)
        throws MalformedURLException, ClassNotFoundException
    {
        return loadClass((String) null, name);
    }

    /**
     * Loads a class from a codebase URL.
     *
     * If <code>codebase</code> is <code>null</code>, then this method
     * will behave the same as {@link #loadClass(String,String)} with a
     * <code>null</code> <code>codebase</code> and the given class name.
     *
     * <p>This method delegates to the
     * {@link RMIClassLoaderSpi#loadClass(String,String,ClassLoader)}
     * method of the provider instance, passing the result of invoking
     * {@link URL#toString} on the given URL (or <code>null</code> if
     * <code>codebase</code> is null) as the first argument,
     * <code>name</code> as the second argument,
     * and <code>null</code> as the third argument.
     *
     * <p>
     *  从代码库URL加载类。
     * 
     *  如果<code> codebase </code>是<code> null </code>,那么此方法将与{@link #loadClass(String,String)}一样具有<code> nul
     * l </code> <code > codebase </code>和给定的类名。
     * 
     * <p>此方法委托提供者实例的{@link RMIClassLoaderSpi#loadClass(String,String,ClassLoader)}方法,将调用{@link URL#toString}
     * 的结果传递给给定的URL(或<code> null </code>如果<code> codebase </code>为null)作为第一个参数,<code> name </code>作为第二个参数,<code>
     *  null </code>。
     * 
     * 
     * @param   codebase the URL to load the class from, or <code>null</code>
     *
     * @param   name the name of the class to load
     *
     * @return  the <code>Class</code> object representing the loaded class
     *
     * @throws MalformedURLException if <code>codebase</code> is
     * <code>null</code> and a provider-specific URL used
     * to load classes is invalid
     *
     * @throws  ClassNotFoundException if a definition for the class
     * could not be found at the specified URL
     */
    public static Class<?> loadClass(URL codebase, String name)
        throws MalformedURLException, ClassNotFoundException
    {
        return provider.loadClass(
            codebase != null ? codebase.toString() : null, name, null);
    }

    /**
     * Loads a class from a codebase URL path.
     *
     * <p>This method delegates to the
     * {@link RMIClassLoaderSpi#loadClass(String,String,ClassLoader)}
     * method of the provider instance, passing <code>codebase</code>
     * as the first argument, <code>name</code> as the second argument,
     * and <code>null</code> as the third argument.
     *
     * <p>
     *  从代码库URL路径加载类。
     * 
     *  <p>此方法委托提供者实例的{@link RMIClassLoaderSpi#loadClass(String,String,ClassLoader)}方法,将<code> codebase </code>
     * 作为第一个参数<code> name </code>作为第二个参数,<code> null </code>作为第三个参数。
     * 
     * 
     * @param   codebase the list of URLs (separated by spaces) to load
     * the class from, or <code>null</code>
     *
     * @param   name the name of the class to load
     *
     * @return  the <code>Class</code> object representing the loaded class
     *
     * @throws MalformedURLException if <code>codebase</code> is
     * non-<code>null</code> and contains an invalid URL, or if
     * <code>codebase</code> is <code>null</code> and a provider-specific
     * URL used to load classes is invalid
     *
     * @throws  ClassNotFoundException if a definition for the class
     * could not be found at the specified location
     *
     * @since   1.2
     */
    public static Class<?> loadClass(String codebase, String name)
        throws MalformedURLException, ClassNotFoundException
    {
        return provider.loadClass(codebase, name, null);
    }

    /**
     * Loads a class from a codebase URL path, optionally using the
     * supplied loader.
     *
     * This method should be used when the caller would like to make
     * available to the provider implementation an additional contextual
     * class loader to consider, such as the loader of a caller on the
     * stack.  Typically, a provider implementation will attempt to
     * resolve the named class using the given <code>defaultLoader</code>,
     * if specified, before attempting to resolve the class from the
     * codebase URL path.
     *
     * <p>This method delegates to the
     * {@link RMIClassLoaderSpi#loadClass(String,String,ClassLoader)}
     * method of the provider instance, passing <code>codebase</code>
     * as the first argument, <code>name</code> as the second argument,
     * and <code>defaultLoader</code> as the third argument.
     *
     * <p>
     *  从代码库URL路径加载类,可选择使用提供的加载器。
     * 
     *  当调用者想要向提供者实现提供一个额外的上下文类加载器来考虑时,例如堆栈上的调用者的加载器时,应该使用这个方法。
     * 通常,在尝试从代码库URL路径解析类之前,提供程序实现将尝试使用给定的<code> defaultLoader </code>(如果指定)解析命名类。
     * 
     *  <p>此方法委托提供者实例的{@link RMIClassLoaderSpi#loadClass(String,String,ClassLoader)}方法,将<code> codebase </code>
     * 作为第一个参数<code> name </code>作为第二个参数,<code> defaultLoader </code>作为第三个参数。
     * 
     * 
     * @param   codebase the list of URLs (separated by spaces) to load
     * the class from, or <code>null</code>
     *
     * @param   name the name of the class to load
     *
     * @param   defaultLoader additional contextual class loader
     * to use, or <code>null</code>
     *
     * @return  the <code>Class</code> object representing the loaded class
     *
     * @throws MalformedURLException if <code>codebase</code> is
     * non-<code>null</code> and contains an invalid URL, or if
     * <code>codebase</code> is <code>null</code> and a provider-specific
     * URL used to load classes is invalid
     *
     * @throws  ClassNotFoundException if a definition for the class
     * could not be found at the specified location
     *
     * @since   1.4
     */
    public static Class<?> loadClass(String codebase, String name,
                                     ClassLoader defaultLoader)
        throws MalformedURLException, ClassNotFoundException
    {
        return provider.loadClass(codebase, name, defaultLoader);
    }

    /**
     * Loads a dynamic proxy class (see {@link java.lang.reflect.Proxy})
     * that implements a set of interfaces with the given names
     * from a codebase URL path.
     *
     * <p>The interfaces will be resolved similar to classes loaded via
     * the {@link #loadClass(String,String)} method using the given
     * <code>codebase</code>.
     *
     * <p>This method delegates to the
     * {@link RMIClassLoaderSpi#loadProxyClass(String,String[],ClassLoader)}
     * method of the provider instance, passing <code>codebase</code>
     * as the first argument, <code>interfaces</code> as the second argument,
     * and <code>defaultLoader</code> as the third argument.
     *
     * <p>
     * 加载一个动态代理类(参见{@link java.lang.reflect.Proxy}),它从代码库URL路径实现一组具有给定名称的接口。
     * 
     *  <p>类似于通过{@link #loadClass(String,String)}方法使用给定的<code> codebase </code>加载的类来解析接口。
     * 
     *  <p>此方法委托提供者实例的{@link RMIClassLoaderSpi#LoadProxyClass(String,String [],ClassLoader)}方法,将<code> codeb
     * ase </code>作为第一个参数<code> interfaces < code>作为第二个参数,<code> defaultLoader </code>作为第三个参数。
     * 
     * 
     * @param   codebase the list of URLs (space-separated) to load
     * classes from, or <code>null</code>
     *
     * @param   interfaces the names of the interfaces for the proxy class
     * to implement
     *
     * @param   defaultLoader additional contextual class loader
     * to use, or <code>null</code>
     *
     * @return  a dynamic proxy class that implements the named interfaces
     *
     * @throws  MalformedURLException if <code>codebase</code> is
     * non-<code>null</code> and contains an invalid URL, or
     * if <code>codebase</code> is <code>null</code> and a provider-specific
     * URL used to load classes is invalid
     *
     * @throws  ClassNotFoundException if a definition for one of
     * the named interfaces could not be found at the specified location,
     * or if creation of the dynamic proxy class failed (such as if
     * {@link java.lang.reflect.Proxy#getProxyClass(ClassLoader,Class[])}
     * would throw an <code>IllegalArgumentException</code> for the given
     * interface list)
     *
     * @since   1.4
     */
    public static Class<?> loadProxyClass(String codebase, String[] interfaces,
                                          ClassLoader defaultLoader)
        throws ClassNotFoundException, MalformedURLException
    {
        return provider.loadProxyClass(codebase, interfaces, defaultLoader);
    }

    /**
     * Returns a class loader that loads classes from the given codebase
     * URL path.
     *
     * <p>The class loader returned is the class loader that the
     * {@link #loadClass(String,String)} method would use to load classes
     * for the same <code>codebase</code> argument.
     *
     * <p>This method delegates to the
     * {@link RMIClassLoaderSpi#getClassLoader(String)} method
     * of the provider instance, passing <code>codebase</code> as the argument.
     *
     * <p>If there is a security manger, its <code>checkPermission</code>
     * method will be invoked with a
     * <code>RuntimePermission("getClassLoader")</code> permission;
     * this could result in a <code>SecurityException</code>.
     * The provider implementation of this method may also perform further
     * security checks to verify that the calling context has permission to
     * connect to all of the URLs in the codebase URL path.
     *
     * <p>
     *  返回从给定代码库URL路径加载类的类加载器。
     * 
     *  <p>返回的类加载器是类加载器,{@link #loadClass(String,String)}方法将用于加载同一个<code> codebase </code>参数的类。
     * 
     *  <p>此方法委托提供者实例的{@link RMIClassLoaderSpi#getClassLoader(String)}方法,传递<code> codebase </code>作为参数。
     * 
     *  <p>如果有安全管理器,其<code> checkPermission </code>方法将使用<code> RuntimePermission("getClassLoader")</code>权限调
     * 用;这可能导致<code> SecurityException </code>。
     * 此方法的提供者实现还可以执行进一步的安全检查以验证调用上下文具有连接到代码库URL路径中的所有URL的权限。
     * 
     * 
     * @param   codebase the list of URLs (space-separated) from which
     * the returned class loader will load classes from, or <code>null</code>
     *
     * @return a class loader that loads classes from the given codebase URL
     * path
     *
     * @throws  MalformedURLException if <code>codebase</code> is
     * non-<code>null</code> and contains an invalid URL, or
     * if <code>codebase</code> is <code>null</code> and a provider-specific
     * URL used to identify the class loader is invalid
     *
     * @throws  SecurityException if there is a security manager and the
     * invocation of its <code>checkPermission</code> method fails, or
     * if the caller does not have permission to connect to all of the
     * URLs in the codebase URL path
     *
     * @since   1.3
     */
    public static ClassLoader getClassLoader(String codebase)
        throws MalformedURLException, SecurityException
    {
        return provider.getClassLoader(codebase);
    }

    /**
     * Returns the annotation string (representing a location for
     * the class definition) that RMI will use to annotate the class
     * descriptor when marshalling objects of the given class.
     *
     * <p>This method delegates to the
     * {@link RMIClassLoaderSpi#getClassAnnotation(Class)} method
     * of the provider instance, passing <code>cl</code> as the argument.
     *
     * <p>
     * 返回在编组给定类的对象时RMI将用来注释类描述符的注释字符串(表示类定义的位置)。
     * 
     *  <p>此方法委托提供者实例的{@link RMIClassLoaderSpi#getClassAnnotation(Class)}方法,传递<code> cl </code>作为参数。
     * 
     * 
     * @param   cl the class to obtain the annotation for
     *
     * @return  a string to be used to annotate the given class when
     * it gets marshalled, or <code>null</code>
     *
     * @throws  NullPointerException if <code>cl</code> is <code>null</code>
     *
     * @since   1.2
     */
    /*
     * REMIND: Should we say that the returned class annotation will or
     * should be a (space-separated) list of URLs?
     * <p>
     *  REMIND：我们应该说返回的类注释是否应该是一个(空格分隔的)URL列表?
     * 
     */
    public static String getClassAnnotation(Class<?> cl) {
        return provider.getClassAnnotation(cl);
    }

    /**
     * Returns the canonical instance of the default provider
     * for the service provider interface {@link RMIClassLoaderSpi}.
     * If the system property <code>java.rmi.server.RMIClassLoaderSpi</code>
     * is not defined, then the <code>RMIClassLoader</code> static
     * methods
     *
     * <ul>
     *
     * <li>{@link #loadClass(URL,String)}
     * <li>{@link #loadClass(String,String)}
     * <li>{@link #loadClass(String,String,ClassLoader)}
     * <li>{@link #loadProxyClass(String,String[],ClassLoader)}
     * <li>{@link #getClassLoader(String)}
     * <li>{@link #getClassAnnotation(Class)}
     *
     * </ul>
     *
     * will use the canonical instance of the default provider
     * as the service provider instance.
     *
     * <p>If there is a security manager, its
     * <code>checkPermission</code> method will be invoked with a
     * <code>RuntimePermission("setFactory")</code> permission; this
     * could result in a <code>SecurityException</code>.
     *
     * <p>The default service provider instance implements
     * {@link RMIClassLoaderSpi} as follows:
     *
     * <blockquote>
     *
     * <p>The <b>{@link RMIClassLoaderSpi#getClassAnnotation(Class)
     * getClassAnnotation}</b> method returns a <code>String</code>
     * representing the codebase URL path that a remote party should
     * use to download the definition for the specified class.  The
     * format of the returned string is a path of URLs separated by
     * spaces.
     *
     * The codebase string returned depends on the defining class
     * loader of the specified class:
     *
     * <ul>
     *
     * <li><p>If the class loader is the system class loader (see
     * {@link ClassLoader#getSystemClassLoader}), a parent of the
     * system class loader such as the loader used for installed
     * extensions, or the bootstrap class loader (which may be
     * represented by <code>null</code>), then the value of the
     * <code>java.rmi.server.codebase</code> property (or possibly an
     * earlier cached value) is returned, or
     * <code>null</code> is returned if that property is not set.
     *
     * <li><p>Otherwise, if the class loader is an instance of
     * <code>URLClassLoader</code>, then the returned string is a
     * space-separated list of the external forms of the URLs returned
     * by invoking the <code>getURLs</code> methods of the loader.  If
     * the <code>URLClassLoader</code> was created by this provider to
     * service an invocation of its <code>loadClass</code> or
     * <code>loadProxyClass</code> methods, then no permissions are
     * required to get the associated codebase string.  If it is an
     * arbitrary other <code>URLClassLoader</code> instance, then if
     * there is a security manager, its <code>checkPermission</code>
     * method will be invoked once for each URL returned by the
     * <code>getURLs</code> method, with the permission returned by
     * invoking <code>openConnection().getPermission()</code> on each
     * URL; if any of those invocations throws a
     * <code>SecurityException</code> or an <code>IOException</code>,
     * then the value of the <code>java.rmi.server.codebase</code>
     * property (or possibly an earlier cached value) is returned, or
     * <code>null</code> is returned if that property is not set.
     *
     * <li><p>Finally, if the class loader is not an instance of
     * <code>URLClassLoader</code>, then the value of the
     * <code>java.rmi.server.codebase</code> property (or possibly an
     * earlier cached value) is returned, or
     * <code>null</code> is returned if that property is not set.
     *
     * </ul>
     *
     * <p>For the implementations of the methods described below,
     * which all take a <code>String</code> parameter named
     * <code>codebase</code> that is a space-separated list of URLs,
     * each invocation has an associated <i>codebase loader</i> that
     * is identified using the <code>codebase</code> argument in
     * conjunction with the current thread's context class loader (see
     * {@link Thread#getContextClassLoader()}).  When there is a
     * security manager, this provider maintains an internal table of
     * class loader instances (which are at least instances of {@link
     * java.net.URLClassLoader}) keyed by the pair of their parent
     * class loader and their codebase URL path (an ordered list of
     * URLs).  If the <code>codebase</code> argument is <code>null</code>,
     * the codebase URL path is the value of the system property
     * <code>java.rmi.server.codebase</code> or possibly an
     * earlier cached value.  For a given codebase URL path passed as the
     * <code>codebase</code> argument to an invocation of one of the
     * below methods in a given context, the codebase loader is the
     * loader in the table with the specified codebase URL path and
     * the current thread's context class loader as its parent.  If no
     * such loader exists, then one is created and added to the table.
     * The table does not maintain strong references to its contained
     * loaders, in order to allow them and their defined classes to be
     * garbage collected when not otherwise reachable.  In order to
     * prevent arbitrary untrusted code from being implicitly loaded
     * into a virtual machine with no security manager, if there is no
     * security manager set, the codebase loader is just the current
     * thread's context class loader (the supplied codebase URL path
     * is ignored, so remote class loading is disabled).
     *
     * <p>The <b>{@link RMIClassLoaderSpi#getClassLoader(String)
     * getClassLoader}</b> method returns the codebase loader for the
     * specified codebase URL path.  If there is a security manager,
     * then if the calling context does not have permission to connect
     * to all of the URLs in the codebase URL path, a
     * <code>SecurityException</code> will be thrown.
     *
     * <p>The <b>{@link
     * RMIClassLoaderSpi#loadClass(String,String,ClassLoader)
     * loadClass}</b> method attempts to load the class with the
     * specified name as follows:
     *
     * <blockquote>
     *
     * If the <code>defaultLoader</code> argument is
     * non-<code>null</code>, it first attempts to load the class with the
     * specified <code>name</code> using the
     * <code>defaultLoader</code>, such as by evaluating
     *
     * <pre>
     *     Class.forName(name, false, defaultLoader)
     * </pre>
     *
     * If the class is successfully loaded from the
     * <code>defaultLoader</code>, that class is returned.  If an
     * exception other than <code>ClassNotFoundException</code> is
     * thrown, that exception is thrown to the caller.
     *
     * <p>Next, the <code>loadClass</code> method attempts to load the
     * class with the specified <code>name</code> using the codebase
     * loader for the specified codebase URL path.
     * If there is a security manager, then the calling context
     * must have permission to connect to all of the URLs in the
     * codebase URL path; otherwise, the current thread's context
     * class loader will be used instead of the codebase loader.
     *
     * </blockquote>
     *
     * <p>The <b>{@link
     * RMIClassLoaderSpi#loadProxyClass(String,String[],ClassLoader)
     * loadProxyClass}</b> method attempts to return a dynamic proxy
     * class with the named interface as follows:
     *
     * <blockquote>
     *
     * <p>If the <code>defaultLoader</code> argument is
     * non-<code>null</code> and all of the named interfaces can be
     * resolved through that loader, then,
     *
     * <ul>
     *
     * <li>if all of the resolved interfaces are <code>public</code>,
     * then it first attempts to obtain a dynamic proxy class (using
     * {@link
     * java.lang.reflect.Proxy#getProxyClass(ClassLoader,Class[])
     * Proxy.getProxyClass}) for the resolved interfaces defined in
     * the codebase loader; if that attempt throws an
     * <code>IllegalArgumentException</code>, it then attempts to
     * obtain a dynamic proxy class for the resolved interfaces
     * defined in the <code>defaultLoader</code>.  If both attempts
     * throw <code>IllegalArgumentException</code>, then this method
     * throws a <code>ClassNotFoundException</code>.  If any other
     * exception is thrown, that exception is thrown to the caller.
     *
     * <li>if all of the non-<code>public</code> resolved interfaces
     * are defined in the same class loader, then it attempts to
     * obtain a dynamic proxy class for the resolved interfaces
     * defined in that loader.
     *
     * <li>otherwise, a <code>LinkageError</code> is thrown (because a
     * class that implements all of the specified interfaces cannot be
     * defined in any loader).
     *
     * </ul>
     *
     * <p>Otherwise, if all of the named interfaces can be resolved
     * through the codebase loader, then,
     *
     * <ul>
     *
     * <li>if all of the resolved interfaces are <code>public</code>,
     * then it attempts to obtain a dynamic proxy class for the
     * resolved interfaces in the codebase loader.  If the attempt
     * throws an <code>IllegalArgumentException</code>, then this
     * method throws a <code>ClassNotFoundException</code>.
     *
     * <li>if all of the non-<code>public</code> resolved interfaces
     * are defined in the same class loader, then it attempts to
     * obtain a dynamic proxy class for the resolved interfaces
     * defined in that loader.
     *
     * <li>otherwise, a <code>LinkageError</code> is thrown (because a
     * class that implements all of the specified interfaces cannot be
     * defined in any loader).
     *
     * </ul>
     *
     * <p>Otherwise, a <code>ClassNotFoundException</code> is thrown
     * for one of the named interfaces that could not be resolved.
     *
     * </blockquote>
     *
     * </blockquote>
     *
     * <p>
     *  返回服务提供程序接口{@link RMIClassLoaderSpi}的默认提供程序的规范实例。
     * 如果未定义系统属性<code> java.rmi.server.RMIClassLoaderSpi </code>,则<code> RMIClassLoader </code>静态方法。
     * 
     * <ul>
     * 
     *  <li> {@ link #loadClass(URL,String)} <li> {@ link #loadClass(String,String)} <li> {@ link #loadClass(String,String,ClassLoader)}
     *  <li> {@ link #loadProxyClass(String,String [],ClassLoader)} <li> {@ link #getClassLoader(String)} <li>
     *  {@ link #getClassAnnotation(Class)}。
     * 
     * </ul>
     * 
     *  将使用默认提供程序的规范实例作为服务提供程序实例。
     * 
     *  <p>如果有安全管理器,其<code> checkPermission </code>方法将使用<code> RuntimePermission("setFactory")</code>权限调用;这可
     * 能导致<code> SecurityException </code>。
     * 
     *  <p>默认服务提供程序实例实现{@link RMIClassLoaderSpi},如下所示：
     * 
     * <blockquote>
     * 
     * <p> <b> {@ link RMIClassLoaderSpi#getClassAnnotation(Class)getClassAnnotation} </b>方法会传回代表远端程式用来下载定义的
     * 代码库网址路径的<code> String </code>指定类。
     * 返回的字符串的格式是用空格分隔的URL的路径。
     * 
     *  返回的代码库字符串取决于指定类的定义类加载器：
     * 
     * <ul>
     * 
     *  <li> <p>如果类加载器是系统类加载器(参见{@link ClassLoader#getSystemClassLoader}),系统类加载器的父代,例如用于已安装扩展的加载器,或引导类加载器由<code>
     *  null </code>表示),则返回<code> java.rmi.server.codebase </code>属性的值(或者可能是之前缓存的值),或<code> null <如果未设置该属性,则返回/ code>
     * 。
     * 
     * <li> <p>否则,如果类加载器是<code> URLClassLoader </code>的实例,则返回的字符串是通过调用<code> getURLs返回的URL的外部形式的空格分隔列表</code>
     * 加载器的方法。
     * 如果此提供程序创建了<code> URLClassLoader </code>以调用其<code> loadClass </code>或<code> loadProxyClass </code>方法,则
     * 不需要任何权限来获取关联的代码库串。
     * 如果是任意的其他<code> URLClassLoader </code>实例,那么如果有安全管理器,它的<code> checkPermission </code>方法将被<code> getURLs
     *  </code>代码>方法,通过在每个URL上调用<code> openConnection()。
     * getPermission()</code>如果任何这些调用抛出一个<code> SecurityException </code>或<code> IOException </code>,那么<code>
     *  java.rmi.server.codebase </code>属性的值返回早期缓存值),如果未设置此属性,则返回<code> null </code>。
     * 
     *  <li> <p>最后,如果类加载器不是<code> URLClassLoader </code>的实例,那么<code> java.rmi.server.codebase </code>属性的值返回早
     * 期缓存值),如果未设置此属性,则返回<code> null </code>。
     * 
     * </ul>
     * 
     * <p>对于下面描述的方法的实现,这些方法都采用名为<code> codebase </code>的<code> String </code>参数,这是一个空格分隔的URL列表, i>代码库加载器</i>
     * ,它使用<code> codebase </code>参数与当前线程的上下文类加载器(参见{@link Thread#getContextClassLoader()})进行标识。
     * 当有安全管理器时,此提供程序维护一个类加载器实例(至少是{@link java.net.URLClassLoader}的实例)的内部表,它由父类加载器及其代码库URL路径(一个URL的有序列表)。
     * 如果<code> codebase </code>参数是<code> null </code>,则代码库URL路径是系统属性<code> java.rmi.server.codebase </code>
     * 的值,缓存值。
     * 当有安全管理器时,此提供程序维护一个类加载器实例(至少是{@link java.net.URLClassLoader}的实例)的内部表,它由父类加载器及其代码库URL路径(一个URL的有序列表)。
     * 对于作为给定上下文中的下列方法之一的调用的<code> codebase </code>参数传递的给定代码库URL路径,代码库加载器是具有指定的代码库URL路径和当前线程的上下文类加载器作为其父类。
     * 如果不存在这样的加载器,则创建一个并将其添加到表中。该表不保持对其所包含加载器的强引用,以便允许它们和它们所定义的类在不可访问时被垃圾收集。
     * 为了防止任意不受信任的代码被隐式地加载到没有安全管理器的虚拟机中,如果没有安全管理器设置,则代码库加载器只是当前线程的上下文类加载器(提供的代码库URL路径被忽略,所以远程类加载被禁用)。
     * 
     * <p> <b> {@ link RMIClassLoaderSpi#getClassLoader(String)getClassLoader} </b>方法会返回指定代码库网址路径的代码库加载器。
     * 如果有安全管理器,那么如果调用上下文没有连接到代码库URL路径中的所有URL的权限,将抛出一个<code> SecurityException </code>。
     * 
     *  <p> <b> {@ link RMIClassLoaderSpi#loadClass(String,String,ClassLoader)loadClass} </b>方法尝试载入具有指定名称的类别
     * ,如下所示：。
     * 
     * <blockquote>
     * 
     *  如果<code> defaultLoader </code>参数是非<code> null </code>,它首先尝试使用<code> defaultLoader </code>加载指定的<code>
     *  name </code> >,例如通过评估。
     * 
     * <pre>
     *  Class.forName(name,false,defaultLoader)
     * </pre>
     * 
     * @return  the canonical instance of the default service provider
     *
     * @throws  SecurityException if there is a security manager and the
     * invocation of its <code>checkPermission</code> method fails
     *
     * @since   1.4
     */
    public static RMIClassLoaderSpi getDefaultProviderInstance() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(new RuntimePermission("setFactory"));
        }
        return defaultProvider;
    }

    /**
     * Returns the security context of the given class loader.
     *
     * <p>
     * 
     *  如果类从<code> defaultLoader </code>成功加载,则返回该类。
     * 如果抛出<code> ClassNotFoundException </code>之外的异常,那么该异常将抛出给调用者。
     * 
     *  <p>接下来,<code> loadClass </code>方法尝试使用指定代码库URL路径的代码库加载器加载指定了<code> name </code>的类。
     * 如果有安全管理器,则调用上下文必须具有连接到代码库URL路径中的所有URL的权限;否则,将使用当前线程的上下文类加载器而不是代码库加载器。
     * 
     * </blockquote>
     * 
     * <p> <b> {@ link RMIClassLoaderSpi#loadProxyClass(String,String [],ClassLoader)loadProxyClass} </b>方法尝
     * 试使用命名接口返回动态代理类,如下所示：。
     * 
     * <blockquote>
     * 
     *  <p>如果<code> defaultLoader </code>参数是非<code> null </code>,并且所有命名接口都可以通过该加载器解析,
     * 
     * <ul>
     * 
     *  <li>如果所有解析的接口都是<code> public </code>,那么它首先尝试获取一个动态代理类(使用{@link java.lang.reflect.Proxy#getProxyClass(ClassLoader,Class []) Proxy.getProxyClass}
     * )用于在代码库加载器中定义的解析接口;如果该尝试抛出一个<code> IllegalArgumentException </code>,它会尝试为<code> defaultLoader </code>
     * 中定义的解析接口获取一个动态代理类。
     * 如果两个尝试都抛出<code> IllegalArgumentException </code>,那么这个方法会抛出一个<code> ClassNotFoundException </code>。
     * 如果抛出任何其他异常,那么该异常将抛出给调用者。
     * 
     *  <li>如果所有非<code> public </code>解析的接口在同一个类加载器中定义,则它尝试为该加载器中定义的已解析接口获取一个动态代理类。
     * 
     *  <li>否则,会抛出<code> LinkageError </code>(因为实现所有指定接口的类不能在任何加载器中定义)。
     * 
     * </ul>
     * 
     *  <p>否则,如果所有命名接口都可以通过代码库加载器解析,
     * 
     * <ul>
     * 
     * <li>如果所有已解析的接口都是<code> public </code>,那么它会尝试为已解析的代码库加载器中的接口获取一个动态代理类。
     * 
     * @param   loader a class loader from which to get the security context
     *
     * @return  the security context
     *
     * @deprecated no replacement.  As of the Java 2 platform v1.2, RMI no
     * longer uses this method to obtain a class loader's security context.
     * @see java.lang.SecurityManager#getSecurityContext()
     */
    @Deprecated
    public static Object getSecurityContext(ClassLoader loader)
    {
        return sun.rmi.server.LoaderHandler.getSecurityContext(loader);
    }

    /**
     * Creates an instance of the default provider class.
     * <p>
     * 如果尝试抛出一个<code> IllegalArgumentException </code>,那么这个方法会抛出一个<code> ClassNotFoundException </code>。
     * 
     *  <li>如果所有非<code> public </code>解析的接口在同一个类加载器中定义,则它尝试为该加载器中定义的已解析接口获取一个动态代理类。
     * 
     *  <li>否则,会抛出<code> LinkageError </code>(因为实现所有指定接口的类不能在任何加载器中定义)。
     * 
     * </ul>
     * 
     *  <p>否则,为无法解析的命名接口之一抛出<code> ClassNotFoundException </code>。
     * 
     * </blockquote>
     * 
     * </blockquote>
     * 
     */
    private static RMIClassLoaderSpi newDefaultProviderInstance() {
        return new RMIClassLoaderSpi() {
            public Class<?> loadClass(String codebase, String name,
                                      ClassLoader defaultLoader)
                throws MalformedURLException, ClassNotFoundException
            {
                return sun.rmi.server.LoaderHandler.loadClass(
                    codebase, name, defaultLoader);
            }

            public Class<?> loadProxyClass(String codebase,
                                           String[] interfaces,
                                           ClassLoader defaultLoader)
                throws MalformedURLException, ClassNotFoundException
            {
                return sun.rmi.server.LoaderHandler.loadProxyClass(
                    codebase, interfaces, defaultLoader);
            }

            public ClassLoader getClassLoader(String codebase)
                throws MalformedURLException
            {
                return sun.rmi.server.LoaderHandler.getClassLoader(codebase);
            }

            public String getClassAnnotation(Class<?> cl) {
                return sun.rmi.server.LoaderHandler.getClassAnnotation(cl);
            }
        };
    }

    /**
     * Chooses provider instance, following above documentation.
     *
     * This method assumes that it has been invoked in a privileged block.
     * <p>
     *  返回给定类加载器的安全上下文。
     * 
     */
    private static RMIClassLoaderSpi initializeProvider() {
        /*
         * First check for the system property being set:
         * <p>
         *  创建默认提供程序类的实例。
         * 
         */
        String providerClassName =
            System.getProperty("java.rmi.server.RMIClassLoaderSpi");

        if (providerClassName != null) {
            if (providerClassName.equals("default")) {
                return defaultProvider;
            }

            try {
                Class<? extends RMIClassLoaderSpi> providerClass =
                    Class.forName(providerClassName, false,
                                  ClassLoader.getSystemClassLoader())
                    .asSubclass(RMIClassLoaderSpi.class);
                return providerClass.newInstance();

            } catch (ClassNotFoundException e) {
                throw new NoClassDefFoundError(e.getMessage());
            } catch (IllegalAccessException e) {
                throw new IllegalAccessError(e.getMessage());
            } catch (InstantiationException e) {
                throw new InstantiationError(e.getMessage());
            } catch (ClassCastException e) {
                Error error = new LinkageError(
                    "provider class not assignable to RMIClassLoaderSpi");
                error.initCause(e);
                throw error;
            }
        }

        /*
         * Next look for a provider configuration file installed:
         * <p>
         *  选择提供程序实例,如上所述。
         * 
         *  此方法假定它已在特权块中调用。
         * 
         */
        Iterator<RMIClassLoaderSpi> iter =
            ServiceLoader.load(RMIClassLoaderSpi.class,
                               ClassLoader.getSystemClassLoader()).iterator();
        if (iter.hasNext()) {
            try {
                return iter.next();
            } catch (ClassCastException e) {
                Error error = new LinkageError(
                    "provider class not assignable to RMIClassLoaderSpi");
                error.initCause(e);
                throw error;
            }
        }

        /*
         * Finally, return the canonical instance of the default provider.
         * <p>
         *  首先检查所设置的系统属性：
         * 
         */
        return defaultProvider;
    }
}
