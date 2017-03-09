/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.sql.rowset;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.sql.SQLException;
import java.util.PropertyPermission;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import javax.sql.rowset.spi.SyncFactoryException;
import sun.reflect.misc.ReflectUtil;

/**
 * A factory API that enables applications to obtain a
 * {@code RowSetFactory} implementation  that can be used to create different
 * types of {@code RowSet} implementations.
 * <p>
 * Example:
 * </p>
 * <pre>
 * RowSetFactory aFactory = RowSetProvider.newFactory();
 * CachedRowSet crs = aFactory.createCachedRowSet();
 * ...
 * RowSetFactory rsf = RowSetProvider.newFactory("com.sun.rowset.RowSetFactoryImpl", null);
 * WebRowSet wrs = rsf.createWebRowSet();
 * </pre>
 *<p>
 * Tracing of this class may be enabled by setting the System property
 * {@code javax.sql.rowset.RowSetFactory.debug} to any value but {@code false}.
 * </p>
 *
 * <p>
 *  工厂API,使应用程序可以获得{@code RowSetFactory}实现,可用于创建不同类型的{@code RowSet}实现。
 * <p>
 *  例：
 * </p>
 * <pre>
 *  RowSetFactory aFactory = RowSetProvider.newFactory(); CachedRowSet crs = aFactory.createCachedRowSet
 * (); ... RowSetFactory rsf = RowSetProvider.newFactory("com.sun.rowset.RowSetFactoryImpl",null); WebRo
 * wSet wrs = rsf.createWebRowSet();。
 * </pre>
 * p>
 *  可以通过将系统属性{@code javax.sql.rowset.RowSetFactory.debug}设置为任何值(但是{@code false})来启用此类的跟踪。
 * </p>
 * 
 * 
 * @author Lance Andersen
 * @since 1.7
 */
public class RowSetProvider {

    private static final String ROWSET_DEBUG_PROPERTY = "javax.sql.rowset.RowSetProvider.debug";
    private static final String ROWSET_FACTORY_IMPL = "com.sun.rowset.RowSetFactoryImpl";
    private static final String ROWSET_FACTORY_NAME = "javax.sql.rowset.RowSetFactory";
    /**
     * Internal debug flag.
     * <p>
     *  内部调试标志。
     * 
     */
    private static boolean debug = true;


    static {
        // Check to see if the debug property is set
        String val = getSystemProperty(ROWSET_DEBUG_PROPERTY);
        // Allow simply setting the prop to turn on debug
        debug = val != null && !"false".equals(val);
    }

    /**
     * RowSetProvider constructor
     * <p>
     *  RowSetProvider构造函数
     * 
     */
    protected RowSetProvider () {
    }

    /**
     * <p>Creates a new instance of a <code>RowSetFactory</code>
     * implementation.  This method uses the following
     * look up order to determine
     * the <code>RowSetFactory</code> implementation class to load:</p>
     * <ul>
     * <li>
     * The System property {@code javax.sql.rowset.RowSetFactory}.  For example:
     * <ul>
     * <li>
     * -Djavax.sql.rowset.RowSetFactory=com.sun.rowset.RowSetFactoryImpl
     * </li>
     * </ul>
     * <li>
     * The {@link ServiceLoader} API. The {@code ServiceLoader} API will look
     * for a class name in the file
     * {@code META-INF/services/javax.sql.rowset.RowSetFactory}
     * in jars available to the runtime. For example, to have the the RowSetFactory
     * implementation {@code com.sun.rowset.RowSetFactoryImpl } loaded, the
     * entry in {@code META-INF/services/javax.sql.rowset.RowSetFactory} would be:
     *  <ul>
     * <li>
     * {@code com.sun.rowset.RowSetFactoryImpl }
     * </li>
     * </ul>
     * </li>
     * <li>
     * Platform default <code>RowSetFactory</code> instance.
     * </li>
     * </ul>
     *
     * <p>Once an application has obtained a reference to a {@code RowSetFactory},
     * it can use the factory to obtain RowSet instances.</p>
     *
     * <p>
     *  <p>创建<code> RowSetFactory </code>实现的新实例。此方法使用以下查找顺序来确定要加载的<code> RowSetFactory </code>实现类：</p>
     * <ul>
     * <li>
     *  系统属性{@code javax.sql.rowset.RowSetFactory}。例如：
     * <ul>
     * <li>
     *  -Djavax.sql.rowset.RowSetFactory = com.sun.rowset.RowSetFactoryImpl
     * </li>
     * </ul>
     * <li>
     *  {@link ServiceLoader} API。
     *  {@code ServiceLoader} API将在运行时可用的jar中的文件{@code META-INF / services / javax.sql.rowset.RowSetFactory}
     * 中查找类名。
     *  {@link ServiceLoader} API。
     * 例如,要装载RowSetFactory实现{@code com.sun.rowset.RowSetFactoryImpl},{@code META-INF / services / javax.sql.rowset.RowSetFactory}
     * 中的条目将为：。
     *  {@link ServiceLoader} API。
     * <ul>
     * <li>
     * {@code com.sun.rowset.RowSetFactoryImpl}
     * </li>
     * </ul>
     * </li>
     * <li>
     *  平台默认<code> RowSetFactory </code>实例。
     * </li>
     * </ul>
     * 
     * @return New instance of a <code>RowSetFactory</code>
     *
     * @throws SQLException if the default factory class cannot be loaded,
     * instantiated. The cause will be set to actual Exception
     *
     * @see ServiceLoader
     * @since 1.7
     */
    public static RowSetFactory newFactory()
            throws SQLException {
        // Use the system property first
        RowSetFactory factory = null;
        String factoryClassName = null;
        try {
            trace("Checking for Rowset System Property...");
            factoryClassName = getSystemProperty(ROWSET_FACTORY_NAME);
            if (factoryClassName != null) {
                trace("Found system property, value=" + factoryClassName);
                factory = (RowSetFactory) ReflectUtil.newInstance(getFactoryClass(factoryClassName, null, true));
            }
        }  catch (Exception e) {
            throw new SQLException( "RowSetFactory: " + factoryClassName +
                    " could not be instantiated: ", e);
        }

        // Check to see if we found the RowSetFactory via a System property
        if (factory == null) {
            // If the RowSetFactory is not found via a System Property, now
            // look it up via the ServiceLoader API and if not found, use the
            // Java SE default.
            factory = loadViaServiceLoader();
            factory =
                    factory == null ? newFactory(ROWSET_FACTORY_IMPL, null) : factory;
        }
        return (factory);
    }

    /**
     * <p>Creates  a new instance of a <code>RowSetFactory</code> from the
     * specified factory class name.
     * This function is useful when there are multiple providers in the classpath.
     * It gives more control to the application as it can specify which provider
     * should be loaded.</p>
     *
     * <p>Once an application has obtained a reference to a <code>RowSetFactory</code>
     * it can use the factory to obtain RowSet instances.</p>
     *
     * <p>
     * 
     *  <p>一旦应用程序获得对{@code RowSetFactory}的引用,它就可以使用工厂来获取RowSet实例。</p>
     * 
     * 
     * @param factoryClassName fully qualified factory class name that
     * provides  an implementation of <code>javax.sql.rowset.RowSetFactory</code>.
     *
     * @param cl <code>ClassLoader</code> used to load the factory
     * class. If <code>null</code> current <code>Thread</code>'s context
     * classLoader is used to load the factory class.
     *
     * @return New instance of a <code>RowSetFactory</code>
     *
     * @throws SQLException if <code>factoryClassName</code> is
     * <code>null</code>, or the factory class cannot be loaded, instantiated.
     *
     * @see #newFactory()
     *
     * @since 1.7
     */
    public static RowSetFactory newFactory(String factoryClassName, ClassLoader cl)
            throws SQLException {

        trace("***In newInstance()");

        if(factoryClassName == null) {
            throw new SQLException("Error: factoryClassName cannot be null");
        }
        try {
            ReflectUtil.checkPackageAccess(factoryClassName);
        } catch (java.security.AccessControlException e) {
            throw new SQLException("Access Exception",e);
        }

        try {
            Class<?> providerClass = getFactoryClass(factoryClassName, cl, false);
            RowSetFactory instance = (RowSetFactory) providerClass.newInstance();
            if (debug) {
                trace("Created new instance of " + providerClass +
                        " using ClassLoader: " + cl);
            }
            return instance;
        } catch (ClassNotFoundException x) {
            throw new SQLException(
                    "Provider " + factoryClassName + " not found", x);
        } catch (Exception x) {
            throw new SQLException(
                    "Provider " + factoryClassName + " could not be instantiated: " + x,
                    x);
        }
    }

    /*
     * Returns the class loader to be used.
     * <p>
     *  <p>从指定的工厂类名称创建<code> RowSetFactory </code>的新实例。当类路径中有多个提供程序时,此函数很有用。
     * 它为应用程序提供了更多的控制,因为它可以指定应该加载哪个提供程序。</p>。
     * 
     *  <p>一旦应用程序获得对<code> RowSetFactory </code>的引用,它就可以使用工厂获取RowSet实例。</p>
     * 
     * 
     * @return The ClassLoader to use.
     *
     */
    static private ClassLoader getContextClassLoader() throws SecurityException {
        return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {

            public ClassLoader run() {
                ClassLoader cl = null;

                cl = Thread.currentThread().getContextClassLoader();

                if (cl == null) {
                    cl = ClassLoader.getSystemClassLoader();
                }

                return cl;
            }
        });
    }

    /**
     * Attempt to load a class using the class loader supplied. If that fails
     * and fall back is enabled, the current (i.e. bootstrap) class loader is
     * tried.
     *
     * If the class loader supplied is <code>null</code>, first try using the
     * context class loader followed by the current class loader.
     * <p>
     *  返回要使用的类加载器。
     * 
     * 
     *  @return The class which was loaded
     */
    static private Class<?> getFactoryClass(String factoryClassName, ClassLoader cl,
            boolean doFallback) throws ClassNotFoundException {
        try {
            if (cl == null) {
                cl = getContextClassLoader();
                if (cl == null) {
                    throw new ClassNotFoundException();
                } else {
                    return cl.loadClass(factoryClassName);
                }
            } else {
                return cl.loadClass(factoryClassName);
            }
        } catch (ClassNotFoundException e) {
            if (doFallback) {
                // Use current class loader
                return Class.forName(factoryClassName, true, RowSetFactory.class.getClassLoader());
            } else {
                throw e;
            }
        }
    }

    /**
     * Use the ServiceLoader mechanism to load  the default RowSetFactory
     * <p>
     *  尝试使用提供的类加载器加载类。如果失败并且回退被启用,则尝试当前(即引导)类装载器。
     * 
     *  如果提供的类加载器是<code> null </code>,首先尝试使用上下文类加载器,然后是当前类加载器。
     * 
     * 
     * @return default RowSetFactory Implementation
     */
    static private RowSetFactory loadViaServiceLoader() throws SQLException {
        RowSetFactory theFactory = null;
        try {
            trace("***in loadViaServiceLoader():");
            for (RowSetFactory factory : ServiceLoader.load(javax.sql.rowset.RowSetFactory.class)) {
                trace(" Loading done by the java.util.ServiceLoader :" + factory.getClass().getName());
                theFactory = factory;
                break;
            }
        } catch (ServiceConfigurationError e) {
            throw new SQLException(
                    "RowSetFactory: Error locating RowSetFactory using Service "
                    + "Loader API: " + e, e);
        }
        return theFactory;

    }

    /**
     * Returns the requested System Property.  If a {@code SecurityException}
     * occurs, just return NULL
     * <p>
     *  使用ServiceLoader机制加载默认的RowSetFactory
     * 
     * 
     * @param propName - System property to retrieve
     * @return The System property value or NULL if the property does not exist
     * or a {@code SecurityException} occurs.
     */
    static private String getSystemProperty(final String propName) {
        String property = null;
        try {
            property = AccessController.doPrivileged(new PrivilegedAction<String>() {

                public String run() {
                    return System.getProperty(propName);
                }
            }, null, new PropertyPermission(propName, "read"));
        } catch (SecurityException se) {
            trace("error getting " + propName + ":  "+ se);
            if (debug) {
                se.printStackTrace();
            }
        }
        return property;
    }

    /**
     * Debug routine which will output tracing if the System Property
     * -Djavax.sql.rowset.RowSetFactory.debug is set
     * <p>
     *  返回请求的系统属性。如果发生{@code SecurityException},只返回NULL
     * 
     * 
     * @param msg - The debug message to display
     */
    private static void trace(String msg) {
        if (debug) {
            System.err.println("###RowSets: " + msg);
        }
    }
}
