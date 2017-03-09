/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2006, Oracle and/or its affiliates. All rights reserved.
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

/**
 * <code>RMIClassLoaderSpi</code> is the service provider interface for
 * <code>RMIClassLoader</code>.
 *
 * In particular, an <code>RMIClassLoaderSpi</code> instance provides an
 * implementation of the following static methods of
 * <code>RMIClassLoader</code>:
 *
 * <ul>
 *
 * <li>{@link RMIClassLoader#loadClass(URL,String)}
 * <li>{@link RMIClassLoader#loadClass(String,String)}
 * <li>{@link RMIClassLoader#loadClass(String,String,ClassLoader)}
 * <li>{@link RMIClassLoader#loadProxyClass(String,String[],ClassLoader)}
 * <li>{@link RMIClassLoader#getClassLoader(String)}
 * <li>{@link RMIClassLoader#getClassAnnotation(Class)}
 *
 * </ul>
 *
 * When one of those methods is invoked, its behavior is to delegate
 * to a corresponding method on an instance of this class.
 * The details of how each method delegates to the provider instance is
 * described in the documentation for each particular method.
 * See the documentation for {@link RMIClassLoader} for a description
 * of how a provider instance is chosen.
 *
 * <p>
 *  <code> RMIClassLoaderSpi </code>是<code> RMIClassLoader </code>的服务提供程序接口。
 * 
 *  特别地,<code> RMIClassLoaderSpi </code>实例提供了<code> RMIClassLoader </code>的以下静态方法的实现：
 * 
 * <ul>
 * 
 *  <li> {@ link RMIClassLoader#loadClass(URL,String)} <li> {@ link RMIClassLoader#loadClass(String,String)}
 *  <li> {@ link RMIClassLoader#loadClass(String,String,ClassLoader)} <li> {@link RMIClassLoader#loadProxyClass(String,String [],ClassLoader)}
 *  <li> {@ link RMIClassLoader#getClassLoader(String)} <li> {@ link RMIClassLoader#getClassAnnotation(Class)}
 * 。
 * 
 * </ul>
 * 
 *  当这些方法之一被调用时,它的行为是委托给这个类的实例上的相应方法。每个特定方法的文档中描述了每个方法如何委派给提供程序实例的详细信息。
 * 有关如何选择提供程序实例的说明,请参阅{@link RMIClassLoader}的文档。
 * 
 * 
 * @author      Peter Jones
 * @author      Laird Dornin
 * @see         RMIClassLoader
 * @since       1.4
 */
public abstract class RMIClassLoaderSpi {

    /**
     * Provides the implementation for
     * {@link RMIClassLoader#loadClass(URL,String)},
     * {@link RMIClassLoader#loadClass(String,String)}, and
     * {@link RMIClassLoader#loadClass(String,String,ClassLoader)}.
     *
     * Loads a class from a codebase URL path, optionally using the
     * supplied loader.
     *
     * Typically, a provider implementation will attempt to
     * resolve the named class using the given <code>defaultLoader</code>,
     * if specified, before attempting to resolve the class from the
     * codebase URL path.
     *
     * <p>An implementation of this method must either return a class
     * with the given name or throw an exception.
     *
     * <p>
     *  提供{@link RMIClassLoader#loadClass(URL,String)},{@link RMIClassLoader#loadClass(String,String)}和{@link RMIClassLoader#loadClass(String,String,ClassLoader)}
     * 的实现。
     * 
     *  从代码库URL路径加载类,可选择使用提供的加载器。
     * 
     * 通常,在尝试从代码库URL路径解析类之前,提供程序实现将尝试使用给定的<code> defaultLoader </code>(如果指定)解析命名类。
     * 
     *  <p>此方法的实现必须返回具有给定名称的类或抛出异常。
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
     * @throws  MalformedURLException if <code>codebase</code> is
     * non-<code>null</code> and contains an invalid URL, or
     * if <code>codebase</code> is <code>null</code> and a provider-specific
     * URL used to load classes is invalid
     *
     * @throws  ClassNotFoundException if a definition for the class
     * could not be found at the specified location
     */
    public abstract Class<?> loadClass(String codebase, String name,
                                       ClassLoader defaultLoader)
        throws MalformedURLException, ClassNotFoundException;

    /**
     * Provides the implementation for
     * {@link RMIClassLoader#loadProxyClass(String,String[],ClassLoader)}.
     *
     * Loads a dynamic proxy class (see {@link java.lang.reflect.Proxy}
     * that implements a set of interfaces with the given names
     * from a codebase URL path, optionally using the supplied loader.
     *
     * <p>An implementation of this method must either return a proxy
     * class that implements the named interfaces or throw an exception.
     *
     * <p>
     *  提供{@link RMIClassLoader#loadProxyClass(String,String [],ClassLoader)}的实现。
     * 
     *  加载一个动态代理类(参见{@link java.lang.reflect.Proxy},它从代码库URL路径中实现一组具有给定名称的接口,可选择使用提供的加载器。
     * 
     *  <p>此方法的实现必须返回实现命名接口的代理类或抛出异常。
     * 
     * 
     * @param   codebase the list of URLs (space-separated) to load
     * classes from, or <code>null</code>
     *
     * @param   interfaces the names of the interfaces for the proxy class
     * to implement
     *
     * @return  a dynamic proxy class that implements the named interfaces
     *
     * @param   defaultLoader additional contextual class loader
     * to use, or <code>null</code>
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
     */
    public abstract Class<?> loadProxyClass(String codebase,
                                            String[] interfaces,
                                            ClassLoader defaultLoader)
        throws MalformedURLException, ClassNotFoundException;

    /**
     * Provides the implementation for
     * {@link RMIClassLoader#getClassLoader(String)}.
     *
     * Returns a class loader that loads classes from the given codebase
     * URL path.
     *
     * <p>If there is a security manger, its <code>checkPermission</code>
     * method will be invoked with a
     * <code>RuntimePermission("getClassLoader")</code> permission;
     * this could result in a <code>SecurityException</code>.
     * The implementation of this method may also perform further security
     * checks to verify that the calling context has permission to connect
     * to all of the URLs in the codebase URL path.
     *
     * <p>
     *  提供{@link RMIClassLoader#getClassLoader(String)}的实现。
     * 
     *  返回从给定代码库URL路径加载类的类加载器。
     * 
     *  <p>如果有安全管理器,其<code> checkPermission </code>方法将使用<code> RuntimePermission("getClassLoader")</code>权限调
     * 用;这可能导致<code> SecurityException </code>。
     * 此方法的实现还可以执行进一步的安全检查以验证调用上下文具有连接到代码库URL路径中的所有URL的许可。
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
     */
    public abstract ClassLoader getClassLoader(String codebase)
        throws MalformedURLException; // SecurityException

    /**
     * Provides the implementation for
     * {@link RMIClassLoader#getClassAnnotation(Class)}.
     *
     * Returns the annotation string (representing a location for
     * the class definition) that RMI will use to annotate the class
     * descriptor when marshalling objects of the given class.
     *
     * <p>
     * 
     * 
     * @param   cl the class to obtain the annotation for
     *
     * @return  a string to be used to annotate the given class when
     * it gets marshalled, or <code>null</code>
     *
     * @throws  NullPointerException if <code>cl</code> is <code>null</code>
     */
    public abstract String getClassAnnotation(Class<?> cl);
}
