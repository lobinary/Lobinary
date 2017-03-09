/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.ServiceLoader;

/**
 * A registry for service provider instances.
 *
 * <p> A <i>service</i> is a well-known set of interfaces and (usually
 * abstract) classes.  A <i>service provider</i> is a specific
 * implementation of a service.  The classes in a provider typically
 * implement the interface or subclass the class defined by the
 * service itself.
 *
 * <p> Service providers are stored in one or more <i>categories</i>,
 * each of which is defined by a class of interface (described by a
 * <code>Class</code> object) that all of its members must implement.
 * The set of categories may be changed dynamically.
 *
 * <p> Only a single instance of a given leaf class (that is, the
 * actual class returned by <code>getClass()</code>, as opposed to any
 * inherited classes or interfaces) may be registered.  That is,
 * suppose that the
 * <code>com.mycompany.mypkg.GreenServiceProvider</code> class
 * implements the <code>com.mycompany.mypkg.MyService</code>
 * interface.  If a <code>GreenServiceProvider</code> instance is
 * registered, it will be stored in the category defined by the
 * <code>MyService</code> class.  If a new instance of
 * <code>GreenServiceProvider</code> is registered, it will replace
 * the previous instance.  In practice, service provider objects are
 * usually singletons so this behavior is appropriate.
 *
 * <p> To declare a service provider, a <code>services</code>
 * subdirectory is placed within the <code>META-INF</code> directory
 * that is present in every JAR file.  This directory contains a file
 * for each service provider interface that has one or more
 * implementation classes present in the JAR file.  For example, if
 * the JAR file contained a class named
 * <code>com.mycompany.mypkg.MyServiceImpl</code> which implements the
 * <code>javax.someapi.SomeService</code> interface, the JAR file
 * would contain a file named: <pre>
 * META-INF/services/javax.someapi.SomeService </pre>
 *
 * containing the line:
 *
 * <pre>
 * com.mycompany.mypkg.MyService
 * </pre>
 *
 * <p> The service provider classes should be to be lightweight and
 * quick to load.  Implementations of these interfaces should avoid
 * complex dependencies on other classes and on native code. The usual
 * pattern for more complex services is to register a lightweight
 * proxy for the heavyweight service.
 *
 * <p> An application may customize the contents of a registry as it
 * sees fit, so long as it has the appropriate runtime permission.
 *
 * <p> For more details on declaring service providers, and the JAR
 * format in general, see the <a
 * href="../../../../technotes/guides/jar/jar.html">
 * JAR File Specification</a>.
 *
 * <p>
 *  服务提供程序实例的注册表。
 * 
 *  <p> A <i>服务</i>是一组众所周知的接口和(通常是抽象的)类。服务提供商</i>是服务的具体实现。提供者中的类通常实现由服务本身定义的类的接口或子类。
 * 
 *  <p>服务提供者存储在一个或多个类别</i>中,每个类别由一个接口类(由<code> Class </code>对象描述)定义,实行。类别的集合可以动态地改变。
 * 
 *  <p>只能注册给定叶类的一个实例(即,由<code> getClass()</code>返回的实际类,而不是任何继承的类或接口)。
 * 也就是说,假设<code> com.mycompany.mypkg.GreenServiceProvider </code>类实现<code> com.mycompany.mypkg.MyService
 *  </code>接口。
 *  <p>只能注册给定叶类的一个实例(即,由<code> getClass()</code>返回的实际类,而不是任何继承的类或接口)。
 * 如果注册了一个<code> GreenServiceProvider </code>实例,它将被存储在由<code> MyService </code>类定义的类别中。
 * 如果注册了<code> GreenServiceProvider </code>的新实例,它将替换上一个实例。在实践中,服务提供程序对象通常是单例,因此这种行为是适当的。
 * 
 * <p>要声明服务提供者,<code> services </code>子目录放置在每个JAR文件中的<code> META-INF </code>目录中。
 * 此目录包含每个服务提供程序接口的文件,该文件在JAR文件中具有一个或多个实现类。
 * 例如,如果JAR文件包含实现<code> javax.someapi.SomeService </code>接口的类名为<code> com.mycompany.mypkg.MyServiceImpl 
 * </code>的类,那么JAR文件将包含一个名为：<pre> META-INF / services / javax.someapi.SomeService </pre>。
 * 此目录包含每个服务提供程序接口的文件,该文件在JAR文件中具有一个或多个实现类。
 * 
 *  包含行：
 * 
 * <pre>
 *  com.mycompany.mypkg.MyService
 * </pre>
 * 
 *  <p>服务提供者类应该是轻量级并且快速加载。这些接口的实现应避免对其他类和本地代码的复杂依赖。对于更复杂的服务,通常的模式是为重量级服务注册轻量级代理。
 * 
 *  <p>应用程序可以自定义注册表的内容,只要它具有适当的运行时权限即可。
 * 
 *  <p>有关声明服务提供程序和JAR格式的更多详细信息,请参阅<a
 * href="../../../../technotes/guides/jar/jar.html">
 *  JAR文件规范</a>。
 * 
 * 
 * @see RegisterableService
 *
 */
public class ServiceRegistry {

    // Class -> Registry
    private Map categoryMap = new HashMap();

    /**
     * Constructs a <code>ServiceRegistry</code> instance with a
     * set of categories taken from the <code>categories</code>
     * argument.
     *
     * <p>
     *  使用从<code> categories </code>参数中提取的一组类别构造一个<code> ServiceRegistry </code>实例。
     * 
     * 
     * @param categories an <code>Iterator</code> containing
     * <code>Class</code> objects to be used to define categories.
     *
     * @exception IllegalArgumentException if
     * <code>categories</code> is <code>null</code>.
     */
    public ServiceRegistry(Iterator<Class<?>> categories) {
        if (categories == null) {
            throw new IllegalArgumentException("categories == null!");
        }
        while (categories.hasNext()) {
            Class category = (Class)categories.next();
            SubRegistry reg = new SubRegistry(this, category);
            categoryMap.put(category, reg);
        }
    }

    // The following two methods expose functionality from
    // sun.misc.Service.  If that class is made public, they may be
    // removed.
    //
    // The sun.misc.ServiceConfigurationError class may also be
    // exposed, in which case the references to 'an
    // <code>Error</code>' below should be changed to 'a
    // <code>ServiceConfigurationError</code>'.

    /**
     * Searches for implementations of a particular service class
     * using the given class loader.
     *
     * <p> This method transforms the name of the given service class
     * into a provider-configuration filename as described in the
     * class comment and then uses the <code>getResources</code>
     * method of the given class loader to find all available files
     * with that name.  These files are then read and parsed to
     * produce a list of provider-class names.  The iterator that is
     * returned uses the given class loader to look up and then
     * instantiate each element of the list.
     *
     * <p> Because it is possible for extensions to be installed into
     * a running Java virtual machine, this method may return
     * different results each time it is invoked.
     *
     * <p>
     *  使用给定的类加载器搜索特定服务类的实现。
     * 
     * <p>此方法将给定服务类的名称转换为类注释中描述的提供程序配置文件名,然后使用给定类加载器的<code> getResources </code>方法查找所有可用的文件名称。
     * 然后读取和解析这些文件以生成提供程序类名称的列表。返回的迭代器使用给定的类加载器来查找并实例化列表的每个元素。
     * 
     *  <p>由于扩展可能安装到正在运行的Java虚拟机中,因此每次调用此方法时可能会返回不同的结果。
     * 
     * 
     * @param providerClass a <code>Class</code>object indicating the
     * class or interface of the service providers being detected.
     *
     * @param loader the class loader to be used to load
     * provider-configuration files and instantiate provider classes,
     * or <code>null</code> if the system class loader (or, failing that
     * the bootstrap class loader) is to be used.
     *
     * @param <T> the type of the providerClass.
     *
     * @return An <code>Iterator</code> that yields provider objects
     * for the given service, in some arbitrary order.  The iterator
     * will throw an <code>Error</code> if a provider-configuration
     * file violates the specified format or if a provider class
     * cannot be found and instantiated.
     *
     * @exception IllegalArgumentException if
     * <code>providerClass</code> is <code>null</code>.
     */
    public static <T> Iterator<T> lookupProviders(Class<T> providerClass,
                                                  ClassLoader loader)
    {
        if (providerClass == null) {
            throw new IllegalArgumentException("providerClass == null!");
        }
        return ServiceLoader.load(providerClass, loader).iterator();
    }

    /**
     * Locates and incrementally instantiates the available providers
     * of a given service using the context class loader.  This
     * convenience method is equivalent to:
     *
     * <pre>
     *   ClassLoader cl = Thread.currentThread().getContextClassLoader();
     *   return Service.providers(service, cl);
     * </pre>
     *
     * <p>
     *  使用上下文类加载器定位和增量实例化给定服务的可用提供程序。这种方便的方法等效于：
     * 
     * <pre>
     *  ClassLoader cl = Thread.currentThread()。
     * getContextClassLoader(); return Service.providers(service,cl);。
     * </pre>
     * 
     * 
     * @param providerClass a <code>Class</code>object indicating the
     * class or interface of the service providers being detected.
     *
     * @param <T> the type of the providerClass.
     *
     * @return An <code>Iterator</code> that yields provider objects
     * for the given service, in some arbitrary order.  The iterator
     * will throw an <code>Error</code> if a provider-configuration
     * file violates the specified format or if a provider class
     * cannot be found and instantiated.
     *
     * @exception IllegalArgumentException if
     * <code>providerClass</code> is <code>null</code>.
     */
    public static <T> Iterator<T> lookupProviders(Class<T> providerClass) {
        if (providerClass == null) {
            throw new IllegalArgumentException("providerClass == null!");
        }
        return ServiceLoader.load(providerClass).iterator();
    }

    /**
     * Returns an <code>Iterator</code> of <code>Class</code> objects
     * indicating the current set of categories.  The iterator will be
     * empty if no categories exist.
     *
     * <p>
     *  返回<code>类</code>对象的<code>迭代器</code>,表示当前类别集。如果没有类别,迭代器将为空。
     * 
     * 
     * @return an <code>Iterator</code> containing
     * <code>Class</code>objects.
     */
    public Iterator<Class<?>> getCategories() {
        Set keySet = categoryMap.keySet();
        return keySet.iterator();
    }

    /**
     * Returns an Iterator containing the subregistries to which the
     * provider belongs.
     * <p>
     *  返回包含提供程序所属的子域的迭代器。
     * 
     */
    private Iterator getSubRegistries(Object provider) {
        List l = new ArrayList();
        Iterator iter = categoryMap.keySet().iterator();
        while (iter.hasNext()) {
            Class c = (Class)iter.next();
            if (c.isAssignableFrom(provider.getClass())) {
                l.add((SubRegistry)categoryMap.get(c));
            }
        }
        return l.iterator();
    }

    /**
     * Adds a service provider object to the registry.  The provider
     * is associated with the given category.
     *
     * <p> If <code>provider</code> implements the
     * <code>RegisterableService</code> interface, its
     * <code>onRegistration</code> method will be called.  Its
     * <code>onDeregistration</code> method will be called each time
     * it is deregistered from a category, for example if a
     * category is removed or the registry is garbage collected.
     *
     * <p>
     *  将服务提供程序对象添加到注册表。提供者与给定类别相关联。
     * 
     * <p>如果<code> provider </code>实现<code> RegisterableService </code>接口,它的<code> onRegistration </code>方法将
     * 被调用。
     * 它的<code> onDegistration </code>方法将在每次从类别中注销时调用,例如,如果类别被删除或注册表被垃圾回收。
     * 
     * 
     * @param provider the service provide object to be registered.
     * @param category the category under which to register the
     * provider.
     * @param <T> the type of the provider.
     *
     * @return true if no provider of the same class was previously
     * registered in the same category category.
     *
     * @exception IllegalArgumentException if <code>provider</code> is
     * <code>null</code>.
     * @exception IllegalArgumentException if there is no category
     * corresponding to <code>category</code>.
     * @exception ClassCastException if provider does not implement
     * the <code>Class</code> defined by <code>category</code>.
     */
    public <T> boolean registerServiceProvider(T provider,
                                               Class<T> category) {
        if (provider == null) {
            throw new IllegalArgumentException("provider == null!");
        }
        SubRegistry reg = (SubRegistry)categoryMap.get(category);
        if (reg == null) {
            throw new IllegalArgumentException("category unknown!");
        }
        if (!category.isAssignableFrom(provider.getClass())) {
            throw new ClassCastException();
        }

        return reg.registerServiceProvider(provider);
    }

    /**
     * Adds a service provider object to the registry.  The provider
     * is associated within each category present in the registry
     * whose <code>Class</code> it implements.
     *
     * <p> If <code>provider</code> implements the
     * <code>RegisterableService</code> interface, its
     * <code>onRegistration</code> method will be called once for each
     * category it is registered under.  Its
     * <code>onDeregistration</code> method will be called each time
     * it is deregistered from a category or when the registry is
     * finalized.
     *
     * <p>
     *  将服务提供程序对象添加到注册表。提供程序在实现其<code> Class </code>的注册表中的每个类别中相关联。
     * 
     *  <p>如果<code> provider </code>实现<code> RegisterableService </code>接口,它的<code> onRegistration </code>方法
     * 将被调用一次。
     * 它的<code> onDegistration </code>方法将在每次从类别中注销或在注册表完成时被调用。
     * 
     * 
     * @param provider the service provider object to be registered.
     *
     * @exception IllegalArgumentException if
     * <code>provider</code> is <code>null</code>.
     */
    public void registerServiceProvider(Object provider) {
        if (provider == null) {
            throw new IllegalArgumentException("provider == null!");
        }
        Iterator regs = getSubRegistries(provider);
        while (regs.hasNext()) {
            SubRegistry reg = (SubRegistry)regs.next();
            reg.registerServiceProvider(provider);
        }
    }

    /**
     * Adds a set of service provider objects, taken from an
     * <code>Iterator</code> to the registry.  Each provider is
     * associated within each category present in the registry whose
     * <code>Class</code> it implements.
     *
     * <p> For each entry of <code>providers</code> that implements
     * the <code>RegisterableService</code> interface, its
     * <code>onRegistration</code> method will be called once for each
     * category it is registered under.  Its
     * <code>onDeregistration</code> method will be called each time
     * it is deregistered from a category or when the registry is
     * finalized.
     *
     * <p>
     *  添加一组服务提供程序对象,取自<code> Iterator </code>到注册表。每个提供者在其实现的<code> Class </code>的注册表中存在的每个类别内相关联。
     * 
     *  <p>对于实现<code> RegisterableService </code>接口的<code> providers </code>的每个条目,其<code> onRegistration </code>
     * 方法将为其注册的每个类别调用一次。
     * 它的<code> onDegistration </code>方法将在每次从类别中注销或在注册表完成时被调用。
     * 
     * 
     * @param providers an Iterator containing service provider
     * objects to be registered.
     *
     * @exception IllegalArgumentException if <code>providers</code>
     * is <code>null</code> or contains a <code>null</code> entry.
     */
    public void registerServiceProviders(Iterator<?> providers) {
        if (providers == null) {
            throw new IllegalArgumentException("provider == null!");
        }
        while (providers.hasNext()) {
            registerServiceProvider(providers.next());
        }
    }

    /**
     * Removes a service provider object from the given category.  If
     * the provider was not previously registered, nothing happens and
     * <code>false</code> is returned.  Otherwise, <code>true</code>
     * is returned.  If an object of the same class as
     * <code>provider</code> but not equal (using <code>==</code>) to
     * <code>provider</code> is registered, it will not be
     * deregistered.
     *
     * <p> If <code>provider</code> implements the
     * <code>RegisterableService</code> interface, its
     * <code>onDeregistration</code> method will be called.
     *
     * <p>
     * 从给定类别中删除服务提供程序对象。如果提供程序以前未注册,则不会发生任何操作,并返回<code> false </code>。否则,返回<code> true </code>。
     * 如果注册与<code> provider </code>相同类但不等于(使用<code> == </code>)到<code> provider </code>的对象,则不会注销。
     * 
     *  <p>如果<code> provider </code>实现<code> RegisterableService </code>接口,则会调用<code> onDeregistration </code>
     * 方法。
     * 
     * 
     * @param provider the service provider object to be deregistered.
     * @param category the category from which to deregister the
     * provider.
     * @param <T> the type of the provider.
     *
     * @return <code>true</code> if the provider was previously
     * registered in the same category category,
     * <code>false</code> otherwise.
     *
     * @exception IllegalArgumentException if <code>provider</code> is
     * <code>null</code>.
     * @exception IllegalArgumentException if there is no category
     * corresponding to <code>category</code>.
     * @exception ClassCastException if provider does not implement
     * the class defined by <code>category</code>.
     */
    public <T> boolean deregisterServiceProvider(T provider,
                                                 Class<T> category) {
        if (provider == null) {
            throw new IllegalArgumentException("provider == null!");
        }
        SubRegistry reg = (SubRegistry)categoryMap.get(category);
        if (reg == null) {
            throw new IllegalArgumentException("category unknown!");
        }
        if (!category.isAssignableFrom(provider.getClass())) {
            throw new ClassCastException();
        }
        return reg.deregisterServiceProvider(provider);
    }

    /**
     * Removes a service provider object from all categories that
     * contain it.
     *
     * <p>
     *  从包含它的所有类别中删除服务提供程序对象。
     * 
     * 
     * @param provider the service provider object to be deregistered.
     *
     * @exception IllegalArgumentException if <code>provider</code> is
     * <code>null</code>.
     */
    public void deregisterServiceProvider(Object provider) {
        if (provider == null) {
            throw new IllegalArgumentException("provider == null!");
        }
        Iterator regs = getSubRegistries(provider);
        while (regs.hasNext()) {
            SubRegistry reg = (SubRegistry)regs.next();
            reg.deregisterServiceProvider(provider);
        }
    }

    /**
     * Returns <code>true</code> if <code>provider</code> is currently
     * registered.
     *
     * <p>
     *  如果<code>提供程序</code>当前已注册,则返回<code> true </code>。
     * 
     * 
     * @param provider the service provider object to be queried.
     *
     * @return <code>true</code> if the given provider has been
     * registered.
     *
     * @exception IllegalArgumentException if <code>provider</code> is
     * <code>null</code>.
     */
    public boolean contains(Object provider) {
        if (provider == null) {
            throw new IllegalArgumentException("provider == null!");
        }
        Iterator regs = getSubRegistries(provider);
        while (regs.hasNext()) {
            SubRegistry reg = (SubRegistry)regs.next();
            if (reg.contains(provider)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns an <code>Iterator</code> containing all registered
     * service providers in the given category.  If
     * <code>useOrdering</code> is <code>false</code>, the iterator
     * will return all of the server provider objects in an arbitrary
     * order.  Otherwise, the ordering will respect any pairwise
     * orderings that have been set.  If the graph of pairwise
     * orderings contains cycles, any providers that belong to a cycle
     * will not be returned.
     *
     * <p>
     *  返回包含给定类别中所有注册服务提供商的<code>迭代器</code>。
     * 如果<code> useOrdering </code>是<code> false </code>,则迭代器将以任意顺序返回所有服务器提供程序对象。否则,排序将遵守已设置的任何成对顺序。
     * 如果成对排序的图包含循环,则不会返回属于循环的任何提供程序。
     * 
     * 
     * @param category the category to be retrieved from.
     * @param useOrdering <code>true</code> if pairwise orderings
     * should be taken account in ordering the returned objects.
     * @param <T> the type of the category.
     *
     * @return an <code>Iterator</code> containing service provider
     * objects from the given category, possibly in order.
     *
     * @exception IllegalArgumentException if there is no category
     * corresponding to <code>category</code>.
     */
    public <T> Iterator<T> getServiceProviders(Class<T> category,
                                               boolean useOrdering) {
        SubRegistry reg = (SubRegistry)categoryMap.get(category);
        if (reg == null) {
            throw new IllegalArgumentException("category unknown!");
        }
        return reg.getServiceProviders(useOrdering);
    }

    /**
     * A simple filter interface used by
     * <code>ServiceRegistry.getServiceProviders</code> to select
     * providers matching an arbitrary criterion.  Classes that
     * implement this interface should be defined in order to make use
     * of the <code>getServiceProviders</code> method of
     * <code>ServiceRegistry</code> that takes a <code>Filter</code>.
     *
     * <p>
     *  由<code> ServiceRegistry.getServiceProviders </code>使用的简单过滤器接口来选择与任意条件匹配的提供程序。
     * 应该定义实现此接口的类,以便利用<code> ServiceRegistry </code>的<code> getServiceProviders </code>方法,该方法需要一个<code> Fil
     * ter </code>。
     *  由<code> ServiceRegistry.getServiceProviders </code>使用的简单过滤器接口来选择与任意条件匹配的提供程序。
     * 
     * 
     * @see ServiceRegistry#getServiceProviders(Class, ServiceRegistry.Filter, boolean)
     */
    public interface Filter {

        /**
         * Returns <code>true</code> if the given
         * <code>provider</code> object matches the criterion defined
         * by this <code>Filter</code>.
         *
         * <p>
         * 如果给定的<code> provider </code>对象符合此<code> Filter </code>定义的条件,则返回<code> true </code>。
         * 
         * 
         * @param provider a service provider <code>Object</code>.
         *
         * @return true if the provider matches the criterion.
         */
        boolean filter(Object provider);
    }

    /**
     * Returns an <code>Iterator</code> containing service provider
     * objects within a given category that satisfy a criterion
     * imposed by the supplied <code>ServiceRegistry.Filter</code>
     * object's <code>filter</code> method.
     *
     * <p> The <code>useOrdering</code> argument controls the
     * ordering of the results using the same rules as
     * <code>getServiceProviders(Class, boolean)</code>.
     *
     * <p>
     *  返回一个包含给定类别中的服务提供程序对象的<code> Iterator </code>,它们满足由<code> ServiceRegistry.Filter </code>对象的<code> fil
     * ter </code>方法强加的标准。
     * 
     *  <p> <code> useOrdering </code>参数使用与<code> getServiceProviders(Class,boolean)</code>相同的规则控制结果的排序。
     * 
     * 
     * @param category the category to be retrieved from.
     * @param filter an instance of <code>ServiceRegistry.Filter</code>
     * whose <code>filter</code> method will be invoked.
     * @param useOrdering <code>true</code> if pairwise orderings
     * should be taken account in ordering the returned objects.
     * @param <T> the type of the category.
     *
     * @return an <code>Iterator</code> containing service provider
     * objects from the given category, possibly in order.
     *
     * @exception IllegalArgumentException if there is no category
     * corresponding to <code>category</code>.
     */
    public <T> Iterator<T> getServiceProviders(Class<T> category,
                                               Filter filter,
                                               boolean useOrdering) {
        SubRegistry reg = (SubRegistry)categoryMap.get(category);
        if (reg == null) {
            throw new IllegalArgumentException("category unknown!");
        }
        Iterator iter = getServiceProviders(category, useOrdering);
        return new FilterIterator(iter, filter);
    }

    /**
     * Returns the currently registered service provider object that
     * is of the given class type.  At most one object of a given
     * class is allowed to be registered at any given time.  If no
     * registered object has the desired class type, <code>null</code>
     * is returned.
     *
     * <p>
     *  返回当前注册的属于给定类类型的服务提供程序对象。允许给定类的至多一个对象在任何给定时间被注册。如果没有注册对象具有所需的类类型,则返回<code> null </code>。
     * 
     * 
     * @param providerClass the <code>Class</code> of the desired
     * service provider object.
     * @param <T> the type of the provider.
     *
     * @return a currently registered service provider object with the
     * desired <code>Class</code>type, or <code>null</code> is none is
     * present.
     *
     * @exception IllegalArgumentException if <code>providerClass</code> is
     * <code>null</code>.
     */
    public <T> T getServiceProviderByClass(Class<T> providerClass) {
        if (providerClass == null) {
            throw new IllegalArgumentException("providerClass == null!");
        }
        Iterator iter = categoryMap.keySet().iterator();
        while (iter.hasNext()) {
            Class c = (Class)iter.next();
            if (c.isAssignableFrom(providerClass)) {
                SubRegistry reg = (SubRegistry)categoryMap.get(c);
                T provider = reg.getServiceProviderByClass(providerClass);
                if (provider != null) {
                    return provider;
                }
            }
        }
        return null;
    }

    /**
     * Sets a pairwise ordering between two service provider objects
     * within a given category.  If one or both objects are not
     * currently registered within the given category, or if the
     * desired ordering is already set, nothing happens and
     * <code>false</code> is returned.  If the providers previously
     * were ordered in the reverse direction, that ordering is
     * removed.
     *
     * <p> The ordering will be used by the
     * <code>getServiceProviders</code> methods when their
     * <code>useOrdering</code> argument is <code>true</code>.
     *
     * <p>
     *  在给定类别中的两个服务提供程序对象之间设置成对顺序。如果一个或两个对象当前未在给定类别中注册,或者如果期望的排序已经设置,则什么都不发生,并且返回<code> false </code>。
     * 如果提供商以前是按相反方向排序的,那么该排序将被删除。
     * 
     *  <p>当<code> useOrdering </code>参数为<code> true </code>时,<code> getServiceProviders </code>方法将使用该顺序。
     * 
     * 
     * @param category a <code>Class</code> object indicating the
     * category under which the preference is to be established.
     * @param firstProvider the preferred provider.
     * @param secondProvider the provider to which
     * <code>firstProvider</code> is preferred.
     * @param <T> the type of the category.
     *
     * @return <code>true</code> if a previously unset ordering
     * was established.
     *
     * @exception IllegalArgumentException if either provider is
     * <code>null</code> or they are the same object.
     * @exception IllegalArgumentException if there is no category
     * corresponding to <code>category</code>.
     */
    public <T> boolean setOrdering(Class<T> category,
                                   T firstProvider,
                                   T secondProvider) {
        if (firstProvider == null || secondProvider == null) {
            throw new IllegalArgumentException("provider is null!");
        }
        if (firstProvider == secondProvider) {
            throw new IllegalArgumentException("providers are the same!");
        }
        SubRegistry reg = (SubRegistry)categoryMap.get(category);
        if (reg == null) {
            throw new IllegalArgumentException("category unknown!");
        }
        if (reg.contains(firstProvider) &&
            reg.contains(secondProvider)) {
            return reg.setOrdering(firstProvider, secondProvider);
        }
        return false;
    }

    /**
     * Sets a pairwise ordering between two service provider objects
     * within a given category.  If one or both objects are not
     * currently registered within the given category, or if no
     * ordering is currently set between them, nothing happens
     * and <code>false</code> is returned.
     *
     * <p> The ordering will be used by the
     * <code>getServiceProviders</code> methods when their
     * <code>useOrdering</code> argument is <code>true</code>.
     *
     * <p>
     * 在给定类别中的两个服务提供程序对象之间设置成对顺序。如果一个或两个对象当前未在给定类别中注册,或者当前未在它们之间设置排序,则不发生任何事情,并返回<code> false </code>。
     * 
     *  <p>当<code> useOrdering </code>参数为<code> true </code>时,<code> getServiceProviders </code>方法将使用该顺序。
     * 
     * 
     * @param category a <code>Class</code> object indicating the
     * category under which the preference is to be disestablished.
     * @param firstProvider the formerly preferred provider.
     * @param secondProvider the provider to which
     * <code>firstProvider</code> was formerly preferred.
     * @param <T> the type of the category.
     *
     * @return <code>true</code> if a previously set ordering was
     * disestablished.
     *
     * @exception IllegalArgumentException if either provider is
     * <code>null</code> or they are the same object.
     * @exception IllegalArgumentException if there is no category
     * corresponding to <code>category</code>.
     */
    public <T> boolean unsetOrdering(Class<T> category,
                                     T firstProvider,
                                     T secondProvider) {
        if (firstProvider == null || secondProvider == null) {
            throw new IllegalArgumentException("provider is null!");
        }
        if (firstProvider == secondProvider) {
            throw new IllegalArgumentException("providers are the same!");
        }
        SubRegistry reg = (SubRegistry)categoryMap.get(category);
        if (reg == null) {
            throw new IllegalArgumentException("category unknown!");
        }
        if (reg.contains(firstProvider) &&
            reg.contains(secondProvider)) {
            return reg.unsetOrdering(firstProvider, secondProvider);
        }
        return false;
    }

    /**
     * Deregisters all service provider object currently registered
     * under the given category.
     *
     * <p>
     *  取消注册当前在给定类别下注册的所有服务提供程序对象。
     * 
     * 
     * @param category the category to be emptied.
     *
     * @exception IllegalArgumentException if there is no category
     * corresponding to <code>category</code>.
     */
    public void deregisterAll(Class<?> category) {
        SubRegistry reg = (SubRegistry)categoryMap.get(category);
        if (reg == null) {
            throw new IllegalArgumentException("category unknown!");
        }
        reg.clear();
    }

    /**
     * Deregisters all currently registered service providers from all
     * categories.
     * <p>
     *  取消注册所有类别的所有当前注册的服务提供商。
     * 
     */
    public void deregisterAll() {
        Iterator iter = categoryMap.values().iterator();
        while (iter.hasNext()) {
            SubRegistry reg = (SubRegistry)iter.next();
            reg.clear();
        }
    }

    /**
     * Finalizes this object prior to garbage collection.  The
     * <code>deregisterAll</code> method is called to deregister all
     * currently registered service providers.  This method should not
     * be called from application code.
     *
     * <p>
     *  在垃圾收集之前完成此对象。调用<code> deregisterAll </code>方法注销所有当前注册的服务提供程序。不应该从应用程序代码调用此方法。
     * 
     * 
     * @exception Throwable if an error occurs during superclass
     * finalization.
     */
    public void finalize() throws Throwable {
        deregisterAll();
        super.finalize();
    }
}


/**
 * A portion of a registry dealing with a single superclass or
 * interface.
 * <p>
 *  处理单个超类或接口的注册表的一部分。
 * 
 */
class SubRegistry {

    ServiceRegistry registry;

    Class category;

    // Provider Objects organized by partial oridering
    PartiallyOrderedSet poset = new PartiallyOrderedSet();

    // Class -> Provider Object of that class
    Map<Class<?>,Object> map = new HashMap();

    public SubRegistry(ServiceRegistry registry, Class category) {
        this.registry = registry;
        this.category = category;
    }

    public boolean registerServiceProvider(Object provider) {
        Object oprovider = map.get(provider.getClass());
        boolean present =  oprovider != null;

        if (present) {
            deregisterServiceProvider(oprovider);
        }
        map.put(provider.getClass(), provider);
        poset.add(provider);
        if (provider instanceof RegisterableService) {
            RegisterableService rs = (RegisterableService)provider;
            rs.onRegistration(registry, category);
        }

        return !present;
    }

    /**
     * If the provider was not previously registered, do nothing.
     *
     * <p>
     *  如果提供者以前没有注册,则什么也不做。
     * 
     * 
     * @return true if the provider was previously registered.
     */
    public boolean deregisterServiceProvider(Object provider) {
        Object oprovider = map.get(provider.getClass());

        if (provider == oprovider) {
            map.remove(provider.getClass());
            poset.remove(provider);
            if (provider instanceof RegisterableService) {
                RegisterableService rs = (RegisterableService)provider;
                rs.onDeregistration(registry, category);
            }

            return true;
        }
        return false;
    }

    public boolean contains(Object provider) {
        Object oprovider = map.get(provider.getClass());
        return oprovider == provider;
    }

    public boolean setOrdering(Object firstProvider,
                               Object secondProvider) {
        return poset.setOrdering(firstProvider, secondProvider);
    }

    public boolean unsetOrdering(Object firstProvider,
                                 Object secondProvider) {
        return poset.unsetOrdering(firstProvider, secondProvider);
    }

    public Iterator getServiceProviders(boolean useOrdering) {
        if (useOrdering) {
            return poset.iterator();
        } else {
            return map.values().iterator();
        }
    }

    public <T> T getServiceProviderByClass(Class<T> providerClass) {
        return (T)map.get(providerClass);
    }

    public void clear() {
        Iterator iter = map.values().iterator();
        while (iter.hasNext()) {
            Object provider = iter.next();
            iter.remove();

            if (provider instanceof RegisterableService) {
                RegisterableService rs = (RegisterableService)provider;
                rs.onDeregistration(registry, category);
            }
        }
        poset.clear();
    }

    public void finalize() {
        clear();
    }
}


/**
 * A class for wrapping <code>Iterators</code> with a filter function.
 * This provides an iterator for a subset without duplication.
 * <p>
 *  用于包装带有过滤器功能的<code>迭代器</code>的类。这为不具有重复的子集提供了迭代器。
 */
class FilterIterator<T> implements Iterator<T> {

    private Iterator<T> iter;
    private ServiceRegistry.Filter filter;

    private T next = null;

    public FilterIterator(Iterator<T> iter,
                          ServiceRegistry.Filter filter) {
        this.iter = iter;
        this.filter = filter;
        advance();
    }

    private void advance() {
        while (iter.hasNext()) {
            T elt = iter.next();
            if (filter.filter(elt)) {
                next = elt;
                return;
            }
        }

        next = null;
    }

    public boolean hasNext() {
        return next != null;
    }

    public T next() {
        if (next == null) {
            throw new NoSuchElementException();
        }
        T o = next;
        advance();
        return o;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
