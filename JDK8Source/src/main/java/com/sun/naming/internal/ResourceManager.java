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

package com.sun.naming.internal;

import java.io.InputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.List;
import java.util.ArrayList;
import java.util.WeakHashMap;

import javax.naming.*;

/**
  * The ResourceManager class facilitates the reading of JNDI resource files.
  *
  * <p>
  *  ResourceManager类有助于读取JNDI资源文件。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  */

public final class ResourceManager {

    /*
     * Name of provider resource files (without the package-name prefix.)
     * <p>
     *  提供程序资源文件的名称(不带包名称前缀)。
     * 
     */
    private static final String PROVIDER_RESOURCE_FILE_NAME =
            "jndiprovider.properties";

    /*
     * Name of application resource files.
     * <p>
     *  应用程序资源文件的名称。
     * 
     */
    private static final String APP_RESOURCE_FILE_NAME = "jndi.properties";

    /*
     * Name of properties file in <java.home>/lib.
     * <p>
     *  属性文件的名称在<java.home> / lib中。
     * 
     */
    private static final String JRELIB_PROPERTY_FILE_NAME = "jndi.properties";

    /*
     * Internal environment property, that when set to "true", disables
     * application resource files lookup to prevent recursion issues
     * when validating signed JARs.
     * <p>
     *  内部环境属性,设置为"true"时,将禁用应用程序资源文件查找,以防止在验证签名的JAR时出现递归问题。
     * 
     */
    private static final String DISABLE_APP_RESOURCE_FILES =
        "com.sun.naming.disable.app.resource.files";

    /*
     * The standard JNDI properties that specify colon-separated lists.
     * <p>
     *  指定以冒号分隔的列表的标准JNDI属性。
     * 
     */
    private static final String[] listProperties = {
        Context.OBJECT_FACTORIES,
        Context.URL_PKG_PREFIXES,
        Context.STATE_FACTORIES,
        // The following shouldn't create a runtime dependence on ldap package.
        javax.naming.ldap.LdapContext.CONTROL_FACTORIES
    };

    private static final VersionHelper helper =
            VersionHelper.getVersionHelper();

    /*
     * A cache of the properties that have been constructed by
     * the ResourceManager.  A Hashtable from a provider resource
     * file is keyed on a class in the resource file's package.
     * One from application resource files is keyed on the thread's
     * context class loader.
     * <p>
     *  ResourceManager已构建的属性的高速缓存。来自提供程序资源文件的Hashtable以资源文件包中的类为关键。一个来自应用程序资源文件的关键是线程的上下文类加载器。
     * 
     */
    // WeakHashMap<Class | ClassLoader, Hashtable>
    private static final WeakHashMap<Object, Hashtable<? super String, Object>>
            propertiesCache = new WeakHashMap<>(11);

    /*
     * A cache of factory objects (ObjectFactory, StateFactory, ControlFactory).
     *
     * A two-level cache keyed first on context class loader and then
     * on propValue.  Value is a list of class or factory objects,
     * weakly referenced so as not to prevent GC of the class loader.
     * Used in getFactories().
     * <p>
     *  工厂对象的缓存(ObjectFactory,StateFactory,ControlFactory)。
     * 
     *  两级缓存首先在上下文类加载器上键入,然后在propValue上。值是一个类或工厂对象的列表,弱引用,以便不阻止类加载器的GC。用于getFactories()。
     * 
     */
    private static final
        WeakHashMap<ClassLoader, Map<String, List<NamedWeakReference<Object>>>>
            factoryCache = new WeakHashMap<>(11);

    /*
     * A cache of URL factory objects (ObjectFactory).
     *
     * A two-level cache keyed first on context class loader and then
     * on classSuffix+propValue.  Value is the factory itself (weakly
     * referenced so as not to prevent GC of the class loader) or
     * NO_FACTORY if a previous search revealed no factory.  Used in
     * getFactory().
     * <p>
     *  URL工厂对象(ObjectFactory)的缓存。
     * 
     *  一个两级缓存首先在上下文类加载器,然后在classSuffix + propValue。 Value是工厂本身(弱引用,以便不阻止类加载器的GC)或NO_FACTORY,如果以前的搜索没有发现工厂。
     * 用于getFactory()。
     * 
     */
    private static final
        WeakHashMap<ClassLoader, Map<String, WeakReference<Object>>>
            urlFactoryCache = new WeakHashMap<>(11);
    private static final WeakReference<Object> NO_FACTORY =
            new WeakReference<>(null);

    /**
     * A class to allow JNDI properties be specified as applet parameters
     * without creating a static dependency on java.applet.
     * <p>
     * 允许将JNDI属性指定为applet参数的类,而不对java.applet创建静态依赖关系。
     * 
     */
    private static class AppletParameter {
        private static final Class<?> clazz = getClass("java.applet.Applet");
        private static final Method getMethod =
            getMethod(clazz, "getParameter", String.class);
        private static Class<?> getClass(String name) {
            try {
                return Class.forName(name, true, null);
            } catch (ClassNotFoundException e) {
                return null;
            }
        }
        private static Method getMethod(Class<?> clazz,
                                        String name,
                                        Class<?>... paramTypes)
        {
            if (clazz != null) {
                try {
                    return clazz.getMethod(name, paramTypes);
                } catch (NoSuchMethodException e) {
                    throw new AssertionError(e);
                }
            } else {
                return null;
            }
        }

        /**
         * Returns the value of the applet's named parameter.
         * <p>
         *  返回小程序的命名参数的值。
         * 
         */
        static Object get(Object applet, String name) {
            // if clazz is null then applet cannot be an Applet.
            if (clazz == null || !clazz.isInstance(applet))
                throw new ClassCastException(applet.getClass().getName());
            try {
                return getMethod.invoke(applet, name);
            } catch (InvocationTargetException |
                     IllegalAccessException e) {
                throw new AssertionError(e);
            }
        }
    }

    // There should be no instances of this class.
    private ResourceManager() {
    }


    // ---------- Public methods ----------

    /*
     * Given the environment parameter passed to the initial context
     * constructor, returns the full environment for that initial
     * context (never null).  This is based on the environment
     * parameter, the applet parameters (where appropriate), the
     * system properties, and all application resource files.
     *
     * <p> This method will modify <tt>env</tt> and save
     * a reference to it.  The caller may no longer modify it.
     *
     * <p>
     *  给定传递给初始上下文构造函数的环境参数,返回该初始上下文的完整环境(从不为空)。这基于环境参数,小程序参数(如果适用),系统属性和所有应用程序资源文件。
     * 
     *  <p>此方法将修改<tt> env </tt>并保存对它的引用。呼叫者可能不再修改它。
     * 
     * 
     * @param env       environment passed to initial context constructor.
     *                  Null indicates an empty environment.
     *
     * @throws NamingException if an error occurs while reading a
     *          resource file
     */
    @SuppressWarnings("unchecked")
    public static Hashtable<?, ?> getInitialEnvironment(
            Hashtable<?, ?> env)
            throws NamingException
    {
        String[] props = VersionHelper.PROPS;   // system/applet properties
        if (env == null) {
            env = new Hashtable<>(11);
        }
        Object applet = env.get(Context.APPLET);

        // Merge property values from env param, applet params, and system
        // properties.  The first value wins:  there's no concatenation of
        // colon-separated lists.
        // Read system properties by first trying System.getProperties(),
        // and then trying System.getProperty() if that fails.  The former
        // is more efficient due to fewer permission checks.
        //
        String[] jndiSysProps = helper.getJndiProperties();
        for (int i = 0; i < props.length; i++) {
            Object val = env.get(props[i]);
            if (val == null) {
                if (applet != null) {
                    val = AppletParameter.get(applet, props[i]);
                }
                if (val == null) {
                    // Read system property.
                    val = (jndiSysProps != null)
                        ? jndiSysProps[i]
                        : helper.getJndiProperty(i);
                }
                if (val != null) {
                    ((Hashtable<String, Object>)env).put(props[i], val);
                }
            }
        }

        // Return without merging if application resource files lookup
        // is disabled.
        String disableAppRes = (String)env.get(DISABLE_APP_RESOURCE_FILES);
        if (disableAppRes != null && disableAppRes.equalsIgnoreCase("true")) {
            return env;
        }

        // Merge the above with the values read from all application
        // resource files.  Colon-separated lists are concatenated.
        mergeTables((Hashtable<Object, Object>)env, getApplicationResources());
        return env;
    }

    /**
      * Retrieves the property from the environment, or from the provider
      * resource file associated with the given context.  The environment
      * may in turn contain values that come from applet parameters,
      * system properties, or application resource files.
      *
      * If <tt>concat</tt> is true and both the environment and the provider
      * resource file contain the property, the two values are concatenated
      * (with a ':' separator).
      *
      * Returns null if no value is found.
      *
      * <p>
      *  从环境或从与给定上下文相关联的提供程序资源文件检索属性。该环境可能依次包含来自小应用程序参数,系统属性或应用程序资源文件的值。
      * 
      *  如果<tt> concat </tt>为true,并且环境和提供程序资源文件都包含属性,则两个值将连接(使用"："分隔符)。
      * 
      *  如果未找到值,则返回null。
      * 
      * 
      * @param propName The non-null property name
      * @param env      The possibly null environment properties
      * @param ctx      The possibly null context
      * @param concat   True if multiple values should be concatenated
      * @return the property value, or null is there is none.
      * @throws NamingException if an error occurs while reading the provider
      * resource file.
      */
    public static String getProperty(String propName, Hashtable<?,?> env,
        Context ctx, boolean concat)
            throws NamingException {

        String val1 = (env != null) ? (String)env.get(propName) : null;
        if ((ctx == null) ||
            ((val1 != null) && !concat)) {
            return val1;
        }
        String val2 = (String)getProviderResource(ctx).get(propName);
        if (val1 == null) {
            return val2;
        } else if ((val2 == null) || !concat) {
            return val1;
        } else {
            return (val1 + ":" + val2);
        }
    }

    /**
     * Retrieves an enumeration of factory classes/object specified by a
     * property.
     *
     * The property is gotten from the environment and the provider
     * resource file associated with the given context and concantenated.
     * See getProperty(). The resulting property value is a list of class names.
     *<p>
     * This method then loads each class using the current thread's context
     * class loader and keeps them in a list. Any class that cannot be loaded
     * is ignored. The resulting list is then cached in a two-level
     * hash table, keyed first by the context class loader and then by
     * the property's value.
     * The next time threads of the same context class loader call this
     * method, they can use the cached list.
     *<p>
     * After obtaining the list either from the cache or by creating one from
     * the property value, this method then creates and returns a
     * FactoryEnumeration using the list. As the FactoryEnumeration is
     * traversed, the cached Class object in the list is instantiated and
     * replaced by an instance of the factory object itself.  Both class
     * objects and factories are wrapped in weak references so as not to
     * prevent GC of the class loader.
     *<p>
     * Note that multiple threads can be accessing the same cached list
     * via FactoryEnumeration, which locks the list during each next().
     * The size of the list will not change,
     * but a cached Class object might be replaced by an instantiated factory
     * object.
     *
     * <p>
     *  检索由属性指定的工厂类/对象的枚举。
     * 
     *  该属性从环境和与给定上下文相关联的提供程序资源文件中获取,并连接。请参阅getProperty()。生成的属性值是类名称的列表。
     * p>
     * 然后,该方法使用当前线程的上下文类加载器加载每个类,并将它们保存在列表中。任何无法加载的类都将被忽略。然后将生成的列表缓存在两级哈希表中,首先由上下文类加载器键入,然后由属性的值键入。
     * 下一次,同一上下文类加载器的线程调用此方法时,他们可以使用缓存的列表。
     * p>
     *  从缓存中获取列表或通过从属性值中创建一个列表之后,此方法将使用列表创建并返回一个FactoryEnumeration。
     * 当遍历FactoryEnumeration时,列表中的缓存的Class对象被实例化并被工厂对象本身的实例替代。类对象和工厂都包装在弱引用中,以防止类加载器的GC。
     * p>
     *  注意,多个线程可以通过FactoryEnumeration访问同一个缓存列表,它在每个next()期间锁定列表。列表的大小不会改变,但缓存的Class对象可能会被实例化的工厂对象替换。
     * 
     * 
     * @param propName  The non-null property name
     * @param env       The possibly null environment properties
     * @param ctx       The possibly null context
     * @return An enumeration of factory classes/objects; null if none.
     * @exception NamingException If encounter problem while reading the provider
     * property file.
     * @see javax.naming.spi.NamingManager#getObjectInstance
     * @see javax.naming.spi.NamingManager#getStateToBind
     * @see javax.naming.spi.DirectoryManager#getObjectInstance
     * @see javax.naming.spi.DirectoryManager#getStateToBind
     * @see javax.naming.ldap.ControlFactory#getControlInstance
     */
    public static FactoryEnumeration getFactories(String propName,
        Hashtable<?,?> env, Context ctx) throws NamingException {

        String facProp = getProperty(propName, env, ctx, true);
        if (facProp == null)
            return null;  // no classes specified; return null

        // Cache is based on context class loader and property val
        ClassLoader loader = helper.getContextClassLoader();

        Map<String, List<NamedWeakReference<Object>>> perLoaderCache = null;
        synchronized (factoryCache) {
            perLoaderCache = factoryCache.get(loader);
            if (perLoaderCache == null) {
                perLoaderCache = new HashMap<>(11);
                factoryCache.put(loader, perLoaderCache);
            }
        }

        synchronized (perLoaderCache) {
            List<NamedWeakReference<Object>> factories =
                    perLoaderCache.get(facProp);
            if (factories != null) {
                // Cached list
                return factories.size() == 0 ? null
                    : new FactoryEnumeration(factories, loader);
            } else {
                // Populate list with classes named in facProp; skipping
                // those that we cannot load
                StringTokenizer parser = new StringTokenizer(facProp, ":");
                factories = new ArrayList<>(5);
                while (parser.hasMoreTokens()) {
                    try {
                        // System.out.println("loading");
                        String className = parser.nextToken();
                        Class<?> c = helper.loadClass(className, loader);
                        factories.add(new NamedWeakReference<Object>(c, className));
                    } catch (Exception e) {
                        // ignore ClassNotFoundException, IllegalArgumentException
                    }
                }
                // System.out.println("adding to cache: " + factories);
                perLoaderCache.put(facProp, factories);
                return new FactoryEnumeration(factories, loader);
            }
        }
    }

    /**
     * Retrieves a factory from a list of packages specified in a
     * property.
     *
     * The property is gotten from the environment and the provider
     * resource file associated with the given context and concatenated.
     * classSuffix is added to the end of this list.
     * See getProperty(). The resulting property value is a list of package
     * prefixes.
     *<p>
     * This method then constructs a list of class names by concatenating
     * each package prefix with classSuffix and attempts to load and
     * instantiate the class until one succeeds.
     * Any class that cannot be loaded is ignored.
     * The resulting object is then cached in a two-level hash table,
     * keyed first by the context class loader and then by the property's
     * value and classSuffix.
     * The next time threads of the same context class loader call this
     * method, they use the cached factory.
     * If no factory can be loaded, NO_FACTORY is recorded in the table
     * so that next time it'll return quickly.
     *
     * <p>
     *  从属性中指定的包列表中检索工厂。
     * 
     *  该属性是从环境和与给定上下文相关联并连接的提供程序资源文件获取的。 classSuffix被添加到此列表的末尾。请参阅getProperty()。生成的属性值是包前缀列表。
     * p>
     * 然后,此方法通过将每个包前缀与classSuffix连接来构造类名称列表,并尝试加载和实例化该类,直到成功为止。任何无法加载的类都将被忽略。
     * 然后将生成的对象缓存在两级哈希表中,首先由上下文类加载器键入,然后由属性的值和classSuffix键入。下一次,同一上下文类加载器的线程调用此方法时,它们使用缓存的工厂。
     * 如果没有工厂可以加载,NO_FACTORY被记录在表中,以便下次它将迅速返回。
     * 
     * 
     * @param propName  The non-null property name
     * @param env       The possibly null environment properties
     * @param ctx       The possibly null context
     * @param classSuffix The non-null class name
     *                  (e.g. ".ldap.ldapURLContextFactory).
     * @param defaultPkgPrefix The non-null default package prefix.
     *        (e.g., "com.sun.jndi.url").
     * @return An factory object; null if none.
     * @exception NamingException If encounter problem while reading the provider
     * property file, or problem instantiating the factory.
     *
     * @see javax.naming.spi.NamingManager#getURLContext
     * @see javax.naming.spi.NamingManager#getURLObject
     */
    public static Object getFactory(String propName, Hashtable<?,?> env,
            Context ctx, String classSuffix, String defaultPkgPrefix)
            throws NamingException {

        // Merge property with provider property and supplied default
        String facProp = getProperty(propName, env, ctx, true);
        if (facProp != null)
            facProp += (":" + defaultPkgPrefix);
        else
            facProp = defaultPkgPrefix;

        // Cache factory based on context class loader, class name, and
        // property val
        ClassLoader loader = helper.getContextClassLoader();
        String key = classSuffix + " " + facProp;

        Map<String, WeakReference<Object>> perLoaderCache = null;
        synchronized (urlFactoryCache) {
            perLoaderCache = urlFactoryCache.get(loader);
            if (perLoaderCache == null) {
                perLoaderCache = new HashMap<>(11);
                urlFactoryCache.put(loader, perLoaderCache);
            }
        }

        synchronized (perLoaderCache) {
            Object factory = null;

            WeakReference<Object> factoryRef = perLoaderCache.get(key);
            if (factoryRef == NO_FACTORY) {
                return null;
            } else if (factoryRef != null) {
                factory = factoryRef.get();
                if (factory != null) {  // check if weak ref has been cleared
                    return factory;
                }
            }

            // Not cached; find first factory and cache
            StringTokenizer parser = new StringTokenizer(facProp, ":");
            String className;
            while (factory == null && parser.hasMoreTokens()) {
                className = parser.nextToken() + classSuffix;
                try {
                    // System.out.println("loading " + className);
                    factory = helper.loadClass(className, loader).newInstance();
                } catch (InstantiationException e) {
                    NamingException ne =
                        new NamingException("Cannot instantiate " + className);
                    ne.setRootCause(e);
                    throw ne;
                } catch (IllegalAccessException e) {
                    NamingException ne =
                        new NamingException("Cannot access " + className);
                    ne.setRootCause(e);
                    throw ne;
                } catch (Exception e) {
                    // ignore ClassNotFoundException, IllegalArgumentException,
                    // etc.
                }
            }

            // Cache it.
            perLoaderCache.put(key, (factory != null)
                                        ? new WeakReference<>(factory)
                                        : NO_FACTORY);
            return factory;
        }
    }


    // ---------- Private methods ----------

    /*
     * Returns the properties contained in the provider resource file
     * of an object's package.  Returns an empty hash table if the
     * object is null or the resource file cannot be found.  The
     * results are cached.
     *
     * <p>
     *  返回包含在对象包的提供程序资源文件中的属性。如果对象为空或无法找到资源文件,则返回空哈希表。结果被缓存。
     * 
     * 
     * @throws NamingException if an error occurs while reading the file.
     */
    private static Hashtable<? super String, Object>
        getProviderResource(Object obj)
            throws NamingException
    {
        if (obj == null) {
            return (new Hashtable<>(1));
        }
        synchronized (propertiesCache) {
            Class<?> c = obj.getClass();

            Hashtable<? super String, Object> props =
                    propertiesCache.get(c);
            if (props != null) {
                return props;
            }
            props = new Properties();

            InputStream istream =
                helper.getResourceAsStream(c, PROVIDER_RESOURCE_FILE_NAME);

            if (istream != null) {
                try {
                    ((Properties)props).load(istream);
                } catch (IOException e) {
                    NamingException ne = new ConfigurationException(
                            "Error reading provider resource file for " + c);
                    ne.setRootCause(e);
                    throw ne;
                }
            }
            propertiesCache.put(c, props);
            return props;
        }
    }


    /*
     * Returns the Hashtable (never null) that results from merging
     * all application resource files available to this thread's
     * context class loader.  The properties file in <java.home>/lib
     * is also merged in.  The results are cached.
     *
     * SECURITY NOTES:
     * 1.  JNDI needs permission to read the application resource files.
     * 2.  Any class will be able to use JNDI to view the contents of
     * the application resource files in its own classpath.  Give
     * careful consideration to this before storing sensitive
     * information there.
     *
     * <p>
     *  返回从合并所有可用于此线程的上下文类加载器的应用程序资源文件产生的Hashtable(从不为null)。 <java.home> / lib中的属性文件也被合并。结果被缓存。
     * 
     *  安全注意事项：1. JNDI需要读取应用程序资源文件的权限。 2.任何类都将能够使用JNDI在其自己的类路径中查看应用程序资源文件的内容。在存储敏感信息之前,请仔细考虑这一点。
     * 
     * 
     * @throws NamingException if an error occurs while reading a resource
     *  file.
     */
    private static Hashtable<? super String, Object> getApplicationResources()
            throws NamingException {

        ClassLoader cl = helper.getContextClassLoader();

        synchronized (propertiesCache) {
            Hashtable<? super String, Object> result = propertiesCache.get(cl);
            if (result != null) {
                return result;
            }

            try {
                NamingEnumeration<InputStream> resources =
                    helper.getResources(cl, APP_RESOURCE_FILE_NAME);
                try {
                    while (resources.hasMore()) {
                        Properties props = new Properties();
                        InputStream istream = resources.next();
                        try {
                            props.load(istream);
                        } finally {
                            istream.close();
                        }

                        if (result == null) {
                            result = props;
                        } else {
                            mergeTables(result, props);
                        }
                    }
                } finally {
                    while (resources.hasMore()) {
                        resources.next().close();
                    }
                }

                // Merge in properties from file in <java.home>/lib.
                InputStream istream =
                    helper.getJavaHomeLibStream(JRELIB_PROPERTY_FILE_NAME);
                if (istream != null) {
                    try {
                        Properties props = new Properties();
                        props.load(istream);

                        if (result == null) {
                            result = props;
                        } else {
                            mergeTables(result, props);
                        }
                    } finally {
                        istream.close();
                    }
                }

            } catch (IOException e) {
                NamingException ne = new ConfigurationException(
                        "Error reading application resource file");
                ne.setRootCause(e);
                throw ne;
            }
            if (result == null) {
                result = new Hashtable<>(11);
            }
            propertiesCache.put(cl, result);
            return result;
        }
    }

    /*
     * Merge the properties from one hash table into another.  Each
     * property in props2 that is not in props1 is added to props1.
     * For each property in both hash tables that is one of the
     * standard JNDI properties that specify colon-separated lists,
     * the values are concatenated and stored in props1.
     * <p>
     * 将属性从一个哈希表合并到另一个哈希表。 props2中不在props1中的每个属性都添加到props1。
     * 对于作为指定以冒号分隔的列表的标准JNDI属性之一的两个哈希表中的每个属性,值连接并存储在props1中。
     * 
     */
    private static void mergeTables(Hashtable<? super String, Object> props1,
                                    Hashtable<? super String, Object> props2) {
        for (Object key : props2.keySet()) {
            String prop = (String)key;
            Object val1 = props1.get(prop);
            if (val1 == null) {
                props1.put(prop, props2.get(prop));
            } else if (isListProperty(prop)) {
                String val2 = (String)props2.get(prop);
                props1.put(prop, ((String)val1) + ":" + val2);
            }
        }
    }

    /*
     * Is a property one of the standard JNDI properties that specify
     * colon-separated lists?
     * <p>
     */
    private static boolean isListProperty(String prop) {
        prop = prop.intern();
        for (int i = 0; i < listProperties.length; i++) {
            if (prop == listProperties[i]) {
                return true;
            }
        }
        return false;
    }
}
